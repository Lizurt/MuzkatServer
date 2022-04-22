package com.muzkat.server.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "genre")
@Entity
@Getter
@Setter
@Schema(title = "Genre entity")
public class GenreEntity {
    @Id
    @Column(name = "genre_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "123")
    private int id;
    @Column(name = "name", nullable = false)
    @Schema(example = "Ivan Petrov")
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favoriteGenres")
    @JsonIgnore
    private Set<UserEntity> favoredUsers;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "genre")
    @JsonIgnore
    private Set<MusicEntity> musicsByGenre;
}
