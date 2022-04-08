package com.muzkat.server.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Table(name = "author")
@Data
public class AuthorEntity {
    @Id
    @Column(name = "author_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favoriteAuthors")
    private Set<UserEntity> favoredUsers;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private Set<MusicEntity> musicsByAuthor;
}
