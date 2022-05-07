package com.muzkat.server.controller;

import com.muzkat.server.model.entity.GenreEntity;
import com.muzkat.server.repository.GenreRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Genre controller", description = "Controls all genre-related HTTP requests.")
public class GenreController {
    @Autowired
    private GenreRepository genreRepository;

    @GetMapping("/genre/get-all")
    @Operation(summary = "Returns all available genres.")
    public List<GenreEntity> getAllGenres() {
        return genreRepository.findAll();
    }

    @PutMapping("/genre/save")
    @Operation(summary = "Tries to store a request body specified genre entity into a database.")
    public GenreEntity saveGenre(@RequestBody GenreEntity genreEntity) {
        return genreRepository.save(genreEntity);
    }
}
