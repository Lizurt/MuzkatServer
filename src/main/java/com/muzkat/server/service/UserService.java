package com.muzkat.server.service;

import com.muzkat.server.entity.UserEntity;
import com.muzkat.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Boolean tryLogin(UserEntity userEntity) {
        UserEntity actualUser = userRepository.findByLogin(userEntity.getLogin());
        return actualUser != null && actualUser.getPassword().equals(userEntity.getPassword());
    }

    public Boolean tryLogon(UserEntity userEntity) {
        if (userRepository.findByLogin(userEntity.getLogin()) != null) {
            return false;
        }
        UserEntity newUser = new UserEntity();
        newUser.setLogin(userEntity.getLogin());
        newUser.setPassword(userEntity.getPassword());
        userRepository.save(newUser);
        return true;
    }
}
