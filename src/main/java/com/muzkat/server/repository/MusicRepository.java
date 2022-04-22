package com.muzkat.server.repository;

import com.muzkat.server.model.entity.MusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MusicRepository extends JpaRepository<MusicEntity, Integer> {
    @Query(value = "SELECT * FROM music LIMIT :amt OFFSET GREATEST(0, RANDOM() * (SELECT COUNT(*) FROM music) - :amt)", nativeQuery = true)
    Set<MusicEntity> findRandomMusic(@Param("amt") int amount);

    @Query(value = "SELECT mus FROM MusicEntity mus WHERE mus.author.id = :aid")
    Set<MusicEntity> findByAuthorId(@Param("aid") int authorId);

    @Query(value = "SELECT mus FROM MusicEntity mus WHERE mus.genre.id = :gid")
    Set<MusicEntity> findByGenreId(@Param("gid") int genreId);

    @Query(value = "SELECT mus FROM MusicEntity mus WHERE mus.author.id = :aid AND mus.genre.id = :gid")
    Set<MusicEntity> findByGenreAndAuthorIds(@Param("aid") int authorId, @Param("gid") int genreId);
}
