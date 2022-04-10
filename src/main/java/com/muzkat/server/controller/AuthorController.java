package com.muzkat.server.controller;

import com.muzkat.server.entity.AuthorEntity;
import com.muzkat.server.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("/author/get-all")
    public List<AuthorEntity> getAllAuthors() {
        return authorRepository.findAll();
    }

    @PostMapping("/author/save")
    public AuthorEntity saveAuthor(@RequestBody AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }
}
