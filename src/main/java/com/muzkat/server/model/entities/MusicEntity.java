package com.muzkat.server.model.entities;

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
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", nullable = false)
    private AuthorEntity author;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_id", nullable = false)
    private GenreEntity genre;
}
