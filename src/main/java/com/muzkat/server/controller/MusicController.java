package com.muzkat.server.controller;

import com.muzkat.server.entity.MusicEntity;
import com.muzkat.server.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MusicController {
    @Autowired
    private MusicRepository musicRepository;

    @GetMapping("/music/get-all")
    public List<MusicEntity> getAllMusic() {
        return musicRepository.findAll();
    }

    @PostMapping("/music/save")
    public MusicEntity saveMusic(@RequestBody MusicEntity musicEntity) {
        return musicRepository.save(musicEntity);
    }
}
