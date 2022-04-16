package com.muzkat.server.repository;

import com.muzkat.server.entity.MusicEntity;
import com.muzkat.server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<MusicEntity, Integer> {
    @Query(value = "SELECT * FROM music LIMIT :amt OFFSET GREATEST(0, RANDOM() * (SELECT COUNT(*) FROM music) - :amt)", nativeQuery = true)
    List<MusicEntity> findRandomMusic(@Param("amt") int amount);

    @Query(value = "SELECT mus FROM MusicEntity mus WHERE mus.author.id = :aid")
    List<MusicEntity> findByAuthorId(@Param("aid") int authorId);

    @Query(value = "SELECT mus FROM MusicEntity mus WHERE mus.genre.id = :gid")
    List<MusicEntity> findByGenreId(@Param("gid") int genreId);

    @Query(value = "SELECT mus FROM MusicEntity mus WHERE mus.author.id = :aid AND mus.genre.id = :gid")
    List<MusicEntity> findByGenreAndAuthorIds(@Param("aid") int authorId, @Param("gid") int genreId);
}
