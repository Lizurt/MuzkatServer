package com.muzkat.server.service;

import com.muzkat.server.model.entity.AuthorEntity;
import com.muzkat.server.model.entity.GenreEntity;
import com.muzkat.server.model.entity.UserEntity;
import com.muzkat.server.model.request.*;
import com.muzkat.server.repository.AuthorRepository;
import com.muzkat.server.repository.GenreRepository;
import com.muzkat.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;

    public Boolean tryLogin(UserEntity userEntity) {
        Optional<UserEntity> actualUser = userRepository.findByLogin(userEntity.getLogin());
        return actualUser.isPresent() && actualUser.get().getPassword().equals(userEntity.getPassword());
    }

    public Boolean tryLogon(UserEntity userEntity) {
        if (userRepository.findByLogin(userEntity.getLogin()).isPresent()) {
            return false;
        }
        UserEntity newUser = new UserEntity();
        newUser.setLogin(userEntity.getLogin());
        newUser.setPassword(userEntity.getPassword());
        userRepository.save(newUser);
        return true;
    }

    public Set<GenreEntity> getFavGenres(String login) {
        Optional<UserEntity> userEntity = userRepository.findByLogin(login);
        if (userEntity.isEmpty()) {
            return Collections.emptySet();
        }
        return userEntity.get().getFavoriteGenres();
    }

    public Set<AuthorEntity> getFavAuthors(String login) {
        Optional<UserEntity> userEntity = userRepository.findByLogin(login);
        if (userEntity.isEmpty()) {
            return Collections.emptySet();
        }
        return userEntity.get().getFavoriteAuthors();
    }

    public AuthorEntity addFavAuthor(AddFavAuthorRequest addFavAuthorRequest) {
        Optional<UserEntity> possibleUserEntity = userRepository.findByLogin(addFavAuthorRequest.getLogin());
        if (possibleUserEntity.isEmpty()) {
            return null;
        }

        Optional<AuthorEntity> possibleAuthorEntity = authorRepository.findByAuthorName(
                addFavAuthorRequest.getAuthorName()
        );
        AuthorEntity authorEntity;
        if (possibleAuthorEntity.isEmpty()) {
            return null;
        }
        authorEntity = possibleAuthorEntity.get();

        userRepository.addFavAuthor(possibleUserEntity.get().getId(), authorEntity.getId());
        return authorEntity;
    }

    public GenreEntity addFavGenre(AddFavGenreRequest addFavGenreRequest) {
        Optional<UserEntity> possibleUserEntity = userRepository.findByLogin(addFavGenreRequest.getLogin());
        if (possibleUserEntity.isEmpty()) {
            return null;
        }

        Optional<GenreEntity> possibleAuthorEntity = genreRepository.findByGenreName(
                addFavGenreRequest.getGenreName()
        );
        GenreEntity genreEntity;
        if (possibleAuthorEntity.isEmpty()) {
            return null;
        }
        genreEntity = possibleAuthorEntity.get();

        userRepository.addFavGenre(possibleUserEntity.get().getId(), genreEntity.getId());
        return genreEntity;
    }

    public void deleteFavAuthor(DeleteFavAuthorRequest deleteFavAuthorRequest) {
        Optional<UserEntity> possibleUserEntity = userRepository.findByLogin(deleteFavAuthorRequest.getLogin());
        if (possibleUserEntity.isEmpty()) {
            return;
        }
        Optional<AuthorEntity> possibleAuthorEntity = authorRepository.findByAuthorName(
                deleteFavAuthorRequest.getAuthorName()
        );
        if (possibleAuthorEntity.isEmpty()) {
            return;
        }

        userRepository.delFavAuthor(possibleUserEntity.get().getId(), possibleAuthorEntity.get().getId());
    }

    public void deleteFavGenre(DeleteFavGenreRequest deleteFavGenreRequest) {
        Optional<UserEntity> possibleUserEntity = userRepository.findByLogin(deleteFavGenreRequest.getLogin());
        if (possibleUserEntity.isEmpty()) {
            return;
        }
        Optional<GenreEntity> possibleGenreEntity = genreRepository.findByGenreName(
                deleteFavGenreRequest.getGenreName()
        );
        if (possibleGenreEntity.isEmpty()) {
            return;
        }

        userRepository.delFavGenre(possibleUserEntity.get().getId(), possibleGenreEntity.get().getId());
    }
}
