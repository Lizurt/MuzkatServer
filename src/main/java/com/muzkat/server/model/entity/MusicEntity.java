package com.muzkat.server.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "music")
@Entity
@Getter
@Setter
@Schema(title = "Music entity")
public class MusicEntity {
    @Id
    @Column(name = "music_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "123")
    private int id;
    @Column(name = "name", nullable = false)
    @Schema(example = "Ivan Petrov")
    private String name;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", nullable = false)
    @Schema(example = "123")
    private AuthorEntity author;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_id", nullable = false)
    @Schema(example = "123")
    private GenreEntity genre;
}
