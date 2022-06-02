package com.muzkat.server.controller;

import com.muzkat.server.model.entity.AuthorEntity;
import com.muzkat.server.repository.AuthorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Author controller", description = "Controls all author-related HTTP requests.")
@RestController
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Returns all available authors.
     * @return
     */
    @GetMapping("/author/get-all")
    @Operation(summary = "Returns all available authors.")
    public List<AuthorEntity> getAllAuthors() {
        return authorRepository.findAll();
    }

    /**
     * Tries to store a request body specified author entity to a database.
     * @param authorEntity
     * @return
     */
    @PutMapping("/author/save")
    @Operation(summary = "Tries to store a request body specified author entity to a database.")
    public AuthorEntity saveAuthor(@RequestBody AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }
}
