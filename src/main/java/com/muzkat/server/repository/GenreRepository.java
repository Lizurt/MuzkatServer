package com.muzkat.server.repository;

import com.muzkat.server.model.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {
    @Query("SELECT ge FROM GenreEntity ge WHERE ge.name = :gename")
    GenreEntity findByGenreName(@Param("gename") String genreName);
}
