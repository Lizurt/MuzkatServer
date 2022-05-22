package com.muzkat.server.controller;

import com.muzkat.server.model.entity.AuthorEntity;
import com.muzkat.server.model.entity.GenreEntity;
import com.muzkat.server.model.entity.UserEntity;
import com.muzkat.server.model.request.AddFavAuthorRequest;
import com.muzkat.server.model.request.AddFavGenreRequest;
import com.muzkat.server.model.request.DeleteFavAuthorRequest;
import com.muzkat.server.model.request.DeleteFavGenreRequest;
import com.muzkat.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Tag(name = "User controller", description = "Controls all user-related HTTP requests.")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/get-fav-genres/{login}")
    @Operation(summary = "Gets all user favorite genres.")
    public Set<GenreEntity> getFavGenres(@PathVariable String login) {
        return userService.getFavGenres(login);
    }

    @GetMapping("/user/get-fav-authors/{login}")
    @Operation(summary = "Gets all user favorite genres.")
    public Set<AuthorEntity> getFavAuthors(@PathVariable String login) {
        return userService.getFavAuthors(login);
    }

    @PostMapping("/user/try-login")
    @Operation(summary = "Checks request body specified login data and returns true if it matches, false otherwise.")
    public Boolean tryLogin(@RequestBody UserEntity userEntity) {
        return userService.tryLogin(userEntity);
    }

    @PutMapping("/user/try-logon")
    @Operation(summary = "Tries to store a request body specified user entity into a database " +
            "and returns true if succeed, false otherwise.")
    public Boolean tryLogon(@RequestBody UserEntity userEntity) {
        return userService.tryLogon(userEntity);
    }

    @PostMapping("/user/add-fav-author")
    @Operation(summary = "Adds an author to user's favorite authors.")
    public void addFavAuthor(@RequestBody AddFavAuthorRequest addFavAuthorRequest) {
        userService.addFavAuthor(addFavAuthorRequest);
    }

    @PostMapping("/user/add-fav-genre")
    @Operation(summary = "Adds a genre to user's favorite genres.")
    public void addFavGenre(@RequestBody AddFavGenreRequest addFavGenreRequest) {
        userService.addFavGenre(addFavGenreRequest);
    }

    @PostMapping("/user/del-fav-author")
    @Operation(summary = "Removes an author from user's favorite authors.")
    public void delFavAuthor(@RequestBody DeleteFavAuthorRequest deleteFavAuthorRequest) {
        userService.deleteFavAuthor(deleteFavAuthorRequest);

    }

    @PostMapping("/user/del-fav-genre")
    @Operation(summary = "Removes a genre from user's favorite authors.")
    public void delFavGenre(@RequestBody DeleteFavGenreRequest deleteFavGenreRequest) {
        userService.deleteFavGenre(deleteFavGenreRequest);
    }
}
