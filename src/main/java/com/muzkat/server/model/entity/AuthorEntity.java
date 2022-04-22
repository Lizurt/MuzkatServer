package com.muzkat.server.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "author")
@Entity
@Getter
@Setter
@Schema(title = "Author entity")
public class AuthorEntity {
    @Id
    @Column(name = "author_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "123")
    private int id;
    @Column(name = "name", nullable = false)
    @Schema(example = "Ivan Petrov")
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favoriteAuthors")
    @JsonIgnore
    private Set<UserEntity> favoredUsers;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    @JsonIgnore
    private Set<MusicEntity> musicsByAuthor;
}
