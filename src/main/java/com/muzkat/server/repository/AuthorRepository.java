package com.muzkat.server.repository;

import com.muzkat.server.model.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Integer> {
    @Query("SELECT ae FROM AuthorEntity ae WHERE ae.name = :aename")
    AuthorEntity findByAuthorName(@Param("aename") String authorName);
}
