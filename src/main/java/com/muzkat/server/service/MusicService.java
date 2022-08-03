package com.muzkat.server.service;

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

    private static final Random random = new Random();

    public Boolean saveMusic(AddMusicRequest addMusicRequest) {
        return saveMusic(addMusicRequest.getMusicName(), addMusicRequest.getAuthorName(), addMusicRequest.getGenreName());
    }

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

    public List<MusicEntity> getRandomMusic(int amount) {
        long total = musicRepository.count();
        return shuffleMusicList(musicRepository.findRandomMusic(
                PageRequest.of(
                        (int) (Math.random() * total / amount),
                        amount
                )
        ));
    }

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
