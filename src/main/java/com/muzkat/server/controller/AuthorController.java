package com.muzkat.server.controller;

import com.muzkat.server.model.entity.AuthorEntity;
import com.muzkat.server.repository.AuthorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Author controller", description = "Controls all author-related HTTP requests.")
@RestController
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("/author/get-all")
    @Operation(summary = "Returns all available authors.")
    public List<AuthorEntity> getAllAuthors() {
        return authorRepository.findAll();
    }

    @PostMapping("/author/save")
    @Operation(summary = "Tries to store a request body specified author entity to a database.")
    public AuthorEntity saveAuthor(@RequestBody AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }
}
