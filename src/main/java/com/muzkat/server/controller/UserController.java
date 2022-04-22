package com.muzkat.server.controller;

import com.muzkat.server.model.entity.UserEntity;
import com.muzkat.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User controller", description = "Controls all user-related HTTP requests.")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user/try-login")
    @Operation(summary = "Checks request body specified login data and returns true if it matches, false otherwise.")
    public Boolean tryLogin(@RequestBody UserEntity userEntity) {
        return userService.tryLogin(userEntity);
    }

    @PostMapping("/user/try-logon")
    @Operation(summary = "Tries to store a request body specified user entity into a database " +
            "and returns true if succeed, false otherwise.")
    public Boolean tryLogon(@RequestBody UserEntity userEntity) {
        return userService.tryLogon(userEntity);
    }
}
