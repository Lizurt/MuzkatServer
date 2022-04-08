package com.muzkat.server.model.entities;

import lombok.Data;

import javax.persistence.*;

@Table(name = "music")
@Data
public class MusicEntity {
    @Id
    @Column(name = "music_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private AuthorEntity author;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private GenreEntity genre;
}
