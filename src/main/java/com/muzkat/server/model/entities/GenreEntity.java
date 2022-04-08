package com.muzkat.server.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Table(name = "genre")
@Data
public class GenreEntity {
    @Id
    @Column(name = "genre_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favoriteGenres")
    private Set<UserEntity> favoredUsers;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "genre")
    private Set<MusicEntity> musicsByGenre;
}
