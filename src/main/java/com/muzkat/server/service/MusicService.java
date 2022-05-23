package com.muzkat.server.service;

import com.muzkat.server.model.entity.AuthorEntity;
import com.muzkat.server.model.entity.GenreEntity;
import com.muzkat.server.model.entity.MusicEntity;
import com.muzkat.server.model.entity.UserEntity;
import com.muzkat.server.model.request.GetMatchingMusicRequest;
import com.muzkat.server.repository.MusicRepository;
import com.muzkat.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MusicService {
    @Autowired
    private MusicRepository musicRepository;
    @Autowired
    private UserRepository userRepository;
    private static final Random random = new Random();

    public Boolean saveMusic(MusicEntity musicEntity) {
        musicRepository.save(musicEntity);
        return true;
    }

    public List<MusicEntity> getMatchingMusic(GetMatchingMusicRequest getMatchingMusicRequest) {
        Optional<UserEntity> possibleUser = userRepository.findByLogin(getMatchingMusicRequest.getLogin());
        if (possibleUser.isEmpty()) {
            return Collections.emptyList();
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

        List<MusicEntity> matchingMusic = musicRepository.findSelfMatching(
                favAuthorsIds,
                favGenresIds,
                getMatchingMusicRequest.getAmount()
        );

        return matchingMusic;
    }

    public List<MusicEntity> getRandomMusic(int amount) {
        return shuffleMusicList(musicRepository.findRandomMusic(amount));
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
