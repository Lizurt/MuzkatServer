package com.muzkat.server.controller;

import com.muzkat.server.entity.MusicEntity;
import com.muzkat.server.entity.UserEntity;
import com.muzkat.server.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MusicController {
    @Autowired
    private MusicService musicService;

    @PostMapping("/music/get-random")
    public List<MusicEntity> getRandomMusic(@RequestBody Integer amount) {
        return musicService.getRandomMusic(amount);
    }

    @PostMapping("/music/get-matching")
    public List<MusicEntity> getMatchingMusic(@RequestBody Integer amount, @RequestBody UserEntity userEntity) {
        return musicService.getMatchingMusic(amount, userEntity);
    }

    @PostMapping("/music/save")
    public Boolean saveMusic(@RequestBody MusicEntity musicEntity) {
        return musicService.saveMusic(musicEntity);
    }
}
