package com.muzkat.server.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "music")
@Entity
@Getter
@Setter
public class MusicEntity {
    @Id
    @Column(name = "music_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", nullable = false)
    private AuthorEntity author;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_id", nullable = false)
    private GenreEntity genre;
}
