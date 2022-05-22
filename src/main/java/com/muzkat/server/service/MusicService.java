package com.muzkat.server.service;

import com.muzkat.server.model.entity.AuthorEntity;
import com.muzkat.server.model.entity.GenreEntity;
import com.muzkat.server.model.entity.MusicEntity;
import com.muzkat.server.model.entity.UserEntity;
import com.muzkat.server.model.request.AddMusicRequest;
import com.muzkat.server.repository.AuthorRepository;
import com.muzkat.server.repository.GenreRepository;
import com.muzkat.server.repository.MusicRepository;
import com.muzkat.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Boolean saveMusic(MusicEntity musicEntity) {
        musicRepository.save(musicEntity);
        return true;
    }

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

    public List<MusicEntity> getMatchingMusic(int amount, String login) {
        Optional<UserEntity> userEntity = userRepository.findByLogin(login);
        if (userEntity.isEmpty()) {
            return Collections.emptyList();
        }
        Set<AuthorEntity> favoriteAuthors = userEntity.get().getFavoriteAuthors();
        Set<GenreEntity> favoriteGenres = userEntity.get().getFavoriteGenres();

        List<MusicEntity> matchingMusic = new ArrayList<>();

        // could be more optimised. Will probably be
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
