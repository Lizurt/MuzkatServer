package com.muzkat.server.repositories;

import com.muzkat.server.model.entities.MusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends JpaRepository<MusicEntity, Integer> {
}
