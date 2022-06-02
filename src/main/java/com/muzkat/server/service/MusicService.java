package com.muzkat.server.service;

import com.muzkat.server.Metrics;
import com.muzkat.server.model.entity.AuthorEntity;
import com.muzkat.server.model.entity.GenreEntity;
import com.muzkat.server.model.entity.MusicEntity;
import com.muzkat.server.model.entity.UserEntity;
import com.muzkat.server.model.request.AddMusicRequest;
import com.muzkat.server.model.request.GetMatchingMusicRequest;
import com.muzkat.server.repository.AuthorRepository;
import com.muzkat.server.repository.GenreRepository;
import com.muzkat.server.repository.MusicRepository;
import com.muzkat.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * A spring service that handles all music-related buisness logic
 */
@Service
public class MusicService {
    @Autowired
    private MusicRepository musicRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private MetricService metricService;

    private static final Random random = new Random();

    /**
     * Puts or updates a music in MusicRepository (and does so in a database). Uses a request object
     * @param addMusicRequest
     * @return
     */
    public Boolean saveMusic(AddMusicRequest addMusicRequest) {
        return saveMusic(addMusicRequest.getMusicName(), addMusicRequest.getAuthorName(), addMusicRequest.getGenreName());
    }

    /**
     * Puts or updates a music in MusicRepository (and does so in a database). Uses 3 params that define a music
     * @param musicName
     * @param authorName
     * @param genreName
     * @return
     */
    public Boolean saveMusic(String musicName, String authorName, String genreName) {
        Optional<AuthorEntity> possibleAuthorEntity = authorRepository.findByAuthorName(authorName);
        AuthorEntity authorEntity;
        if (possibleAuthorEntity.isEmpty()) {
            authorEntity = new AuthorEntity();
            authorEntity.setName(authorName);
            authorEntity = authorRepository.save(authorEntity);
        } else {
            authorEntity = possibleAuthorEntity.get();
        }
        Optional<GenreEntity> possibleGenreEntity = genreRepository.findByGenreName(genreName);
        GenreEntity genreEntity;
        if (possibleGenreEntity.isEmpty()) {
            genreEntity = new GenreEntity();
            genreEntity.setName(genreName);
            genreEntity = genreRepository.save(genreEntity);
        } else {
            genreEntity = possibleGenreEntity.get();
        }
        MusicEntity musicEntity = new MusicEntity();
        musicEntity.setName(musicName);
        musicEntity.setAuthor(authorEntity);
        musicEntity.setGenre(genreEntity);
        musicRepository.save(musicEntity);
        return true;
    }

    /**
     * Gets all music entities matching the given request. That means it will firstly return musics that matches
     * all the favorite authors and genres lists, then partly matching ones. Includes music of users with similar
     * tastes in genres and authors
     * @param getMatchingMusicRequest
     * @return
     */
    public Set<MusicEntity> getMatchingMusic(GetMatchingMusicRequest getMatchingMusicRequest) {
        Optional<UserEntity> possibleUser = userRepository.findByLogin(getMatchingMusicRequest.getLogin());
        if (possibleUser.isEmpty()) {
            return Collections.emptySet();
        }
        Set<AuthorEntity> favoriteAuthors = possibleUser.get().getFavoriteAuthors();
        Set<GenreEntity> favoriteGenres = possibleUser.get().getFavoriteGenres();

        int[] favAuthorsIds = new int[favoriteAuthors.size()];
        int[] favGenresIds = new int[favoriteGenres.size()];

        int i = 0;
        for (AuthorEntity favAuthor : favoriteAuthors) {
            favAuthorsIds[i++] = favAuthor.getId();
        }
        i = 0;
        for (GenreEntity favGenre : favoriteGenres) {
            favGenresIds[i++] = favGenre.getId();
        }

        Set<MusicEntity> matchingMusic = new HashSet<>(musicRepository.findMatchingPrioritized(
                favAuthorsIds,
                favGenresIds,
                PageRequest.of(getMatchingMusicRequest.getPage(), getMatchingMusicRequest.getAmount())
        ));

        metricService.tryCountInMetric(possibleUser.get().getLogin(), Metrics.SEARCHED);

        if (matchingMusic.size() >= getMatchingMusicRequest.getAmount()) {
            return matchingMusic;
        }

        // oh, finally. An O(n^99999) algorithm
        for (GenreEntity genreEntity : favoriteGenres) {
            Set<UserEntity> buddies = userRepository.findSimilarTasteUsersByGenreId(
                    genreEntity.getId(),
                    possibleUser.get().getId()
            );
            for (UserEntity buddy : buddies) {
                for (GenreEntity buddyFavGenre : buddy.getFavoriteGenres()) {
                    matchingMusic.addAll(
                            musicRepository.findByGenreId(
                                    buddyFavGenre.getId(),
                                    Pageable.ofSize(getMatchingMusicRequest.getAmount() - matchingMusic.size())
                            )
                    );
                    if (matchingMusic.size() >= getMatchingMusicRequest.getAmount()) {
                        return matchingMusic;
                    }
                }
            }
        }

        for (AuthorEntity authorEntity : favoriteAuthors) {
            Set<UserEntity> buddies = userRepository.findSimilarTasteUsersByAuthorId(
                    authorEntity.getId(),
                    possibleUser.get().getId()
            );
            for (UserEntity buddy : buddies) {
                for (AuthorEntity buddyFavAuthor : buddy.getFavoriteAuthors()) {
                    matchingMusic.addAll(
                            musicRepository.findByAuthorId(
                                    buddyFavAuthor.getId(),
                                    Pageable.ofSize(getMatchingMusicRequest.getAmount() - matchingMusic.size())
                            )
                    );
                    if (matchingMusic.size() >= getMatchingMusicRequest.getAmount()) {
                        return matchingMusic;
                    }
                }
            }
        }

        // the last attempt to fill the matching music set...
        int amt = getMatchingMusicRequest.getAmount() - matchingMusic.size();
        long total = musicRepository.count();
        int page = getMatchingMusicRequest.getPage() > 0 ? getMatchingMusicRequest.getPage() - 1 : 1;
        matchingMusic.addAll(musicRepository.findRandomMusic(PageRequest.of(page, amt)));

        return matchingMusic;
    }

    /**
     * Gets "random" music
     * @param amount
     * @return
     */
    public List<MusicEntity> getRandomMusic(int amount) {
        long total = musicRepository.count();
        return shuffleMusicList(musicRepository.findRandomMusic(
                PageRequest.of(
                        (int) (Math.random() * total / amount),
                        amount
                )
        ));
    }

    /**
     * Shuffles music entity list chaotically
     * @param musics
     * @return
     */
    public List<MusicEntity> shuffleMusicList(List<MusicEntity> musics) {
        for (int i = musics.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            MusicEntity tmp = musics.get(j);
            musics.set(j, musics.get(i));
            musics.set(i, tmp);
        }
        return musics;
    }
}
