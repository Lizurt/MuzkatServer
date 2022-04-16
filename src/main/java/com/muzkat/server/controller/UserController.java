package com.muzkat.server.controller;

import com.muzkat.server.entity.UserEntity;
import com.muzkat.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user/try-login")
    public Boolean tryLogin(@RequestBody UserEntity userEntity) {
        return userService.tryLogin(userEntity);
    }

    @PostMapping("/user/try-logon")
    public Boolean tryLogon(@RequestBody UserEntity userEntity) {
        return userService.tryLogon(userEntity);
    }
}
