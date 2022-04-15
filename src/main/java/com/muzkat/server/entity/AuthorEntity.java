package com.muzkat.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "author")
@Entity
@Getter
@Setter
public class AuthorEntity {
    @Id
    @Column(name = "author_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favoriteAuthors")
    private Set<UserEntity> favoredUsers;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    @JsonIgnore
    private Set<MusicEntity> musicsByAuthor;
}
