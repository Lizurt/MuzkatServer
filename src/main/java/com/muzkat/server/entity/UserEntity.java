package com.muzkat.server.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "app_user")
@Entity
@Getter
@Setter
public class UserEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "login", nullable = false, unique = true)
    private String login;
    @Column(name = "pswrd", nullable = false)
    private String password;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_author_preferences",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<AuthorEntity> favoriteAuthors;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_genre_preferences",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<GenreEntity> favoriteGenres;
}
