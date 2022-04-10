package com.muzkat.server.controller;

import com.muzkat.server.entity.GenreEntity;
import com.muzkat.server.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GenreController {
    @Autowired
    private GenreRepository genreRepository;

    @GetMapping("/genre/get-all")
    public List<GenreEntity> getAllGenres() {
        return genreRepository.findAll();
    }

    @PostMapping("/genre/save")
    public GenreEntity saveGenre(@RequestBody GenreEntity genreEntity) {
        return genreRepository.save(genreEntity);
    }
}
