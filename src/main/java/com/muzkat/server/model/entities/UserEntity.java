package com.muzkat.server.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Table(name = "user")
@Data
public class UserEntity {
    @Id
    @Column(name = "login")
    private String login;
    @Column(name = "pswrd")
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_author_preferences",
            joinColumns = @JoinColumn(name = "login"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<AuthorEntity> favoriteAuthors;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_genre_preferences",
            joinColumns = @JoinColumn(name = "login"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<GenreEntity> favoriteGenres;
}
