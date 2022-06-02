package com.muzkat.server.repository;

import com.muzkat.server.model.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {
    /**
     * Finds all genres that match the given name
     * @param genreName
     * @return
     */
    @Query("SELECT ge FROM GenreEntity ge WHERE ge.name = :gename")
    Optional<GenreEntity> findByGenreName(@Param("gename") String genreName);
}
