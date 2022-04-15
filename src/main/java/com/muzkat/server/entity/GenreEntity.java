package com.muzkat.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.provider.HibernateUtils;

import javax.persistence.*;
import java.util.Set;

@Table(name = "genre")
@Entity
@Getter
@Setter
public class GenreEntity {
    @Id
    @Column(name = "genre_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favoriteGenres")
    private Set<UserEntity> favoredUsers;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "genre")
    @JsonIgnore
    private Set<MusicEntity> musicsByGenre;
}
