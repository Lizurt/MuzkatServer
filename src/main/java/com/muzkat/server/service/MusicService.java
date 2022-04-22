package com.muzkat.server.service;

import com.muzkat.server.model.entity.AuthorEntity;
import com.muzkat.server.model.entity.GenreEntity;
import com.muzkat.server.model.entity.MusicEntity;
import com.muzkat.server.model.entity.UserEntity;
import com.muzkat.server.repository.MusicRepository;
import com.muzkat.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MusicService {
    @Autowired
    private MusicRepository musicRepository;
    @Autowired
    private UserRepository userRepository;

    public Boolean saveMusic(MusicEntity musicEntity) {
        musicRepository.save(musicEntity);
        return true;
    }

    public List<MusicEntity> getMatchingMusic(int amount, UserEntity userEntity) {
        Set<AuthorEntity> favoriteAuthors = userEntity.getFavoriteAuthors();
        Set<GenreEntity> favoriteGenres = userEntity.getFavoriteGenres();

        List<MusicEntity> matchingMusic = new ArrayList<>();

        // no one said about how optimised the app should be, hehehe
        for (AuthorEntity authorEntity : favoriteAuthors) {
            for (GenreEntity genreEntity : favoriteGenres) {
                if (matchingMusic.size() > amount) {
                    return matchingMusic;
                }
                Set<MusicEntity> selfFullMatchMusic = musicRepository.findByGenreAndAuthorIds(
                        authorEntity.getId(), genreEntity.getId()
                );
                for (MusicEntity musicEntity : selfFullMatchMusic) {
                    if (matchingMusic.size() > amount) {
                        return matchingMusic;
                    }
                    matchingMusic.add(musicEntity);
                }
            }
        }
        // todo: add not only full matching music, but also half matching music and other user's semi matching music
        return matchingMusic;
    }

    public Set<MusicEntity> getRandomMusic(int amount) {
        return musicRepository.findRandomMusic(amount);
    }
}
