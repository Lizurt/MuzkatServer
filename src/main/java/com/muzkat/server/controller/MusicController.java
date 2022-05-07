package com.muzkat.server.controller;

import com.muzkat.server.model.entity.MusicEntity;
import com.muzkat.server.model.entity.UserEntity;
import com.muzkat.server.model.request.AddMusicRequest;
import com.muzkat.server.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Tag(name = "Music controller", description = "Controls all music-related HTTP requests.")
@RestController
public class MusicController {
    @Autowired
    private MusicService musicService;

    @PostMapping("/music/get-random")
    @Operation(
            summary = "Returns \"random\" music entities from a database. Amount is request body specified.",
            description = "It's not actually random, but close to it, because it gets music entities starting from a " +
                    "random position with amount of entities N (which is specified in a request body), so music " +
                    "entities order is predetermined, and first ones and last ones entities have lower chances to " +
                    "appear than middle ones. Won't return the necessary amount of entities if there aren't enough in " +
                    "a database. Otherwise, returns the exact amount of entities."
    )
    public Set<MusicEntity> getRandomMusic(@RequestBody Integer amount) {
        return musicService.getRandomMusic(amount);
    }

    @PostMapping("/music/get-matching")
    @Operation(
            summary = "Returns all filter-matching music entities.",
            description = "Amount of entities and a user are request body specified. Genre and author preferences " +
                    "are being taken from other database tables depending on the user's data."
    )
    public List<MusicEntity> getMatchingMusic(@RequestBody Integer amount, @RequestBody UserEntity userEntity) {
        return musicService.getMatchingMusic(amount, userEntity);
    }

    @PutMapping("/music/save")
    @Operation(summary = "Tries to store a request body specified music entity into a database " +
            "and returns true if succeed, false otherwise.")
    public Boolean saveMusic(@RequestBody MusicEntity musicEntity) {
        return musicService.saveMusic(musicEntity);
    }

    @PutMapping("/music/save-using-names")
    @Operation(summary = "Tries to store a request body specified music, author and genre names into a database " +
            "with new music entity creation. Returns true if succeed, false otherwise."
    )
    public Boolean saveMusic(@RequestBody AddMusicRequest addMusicRequest) {
        return musicService.saveMusic(addMusicRequest);
    }
}
