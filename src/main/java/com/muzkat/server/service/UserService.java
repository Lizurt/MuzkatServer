package com.muzkat.server.service;

import com.muzkat.server.model.entity.AuthorEntity;
import com.muzkat.server.model.entity.GenreEntity;
import com.muzkat.server.model.entity.UserEntity;
import com.muzkat.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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
}
