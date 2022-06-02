package com.muzkat.server.repository;

import com.muzkat.server.model.entity.MusicEntity;
import com.muzkat.server.model.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MusicRepository extends JpaRepository<MusicEntity, Integer> {
    /**
     * Finds random music
     * @param pageable
     * @return
     */
    @Query(value = "SELECT me FROM MusicEntity me")
    List<MusicEntity> findRandomMusic(Pageable pageable);

    /**
     * Finds music that have an author with the matching author id
     * @param authorId
     * @return
     */
    @Query(value = "SELECT mus FROM MusicEntity mus WHERE mus.author.id = :aid")
    Set<MusicEntity> findByAuthorId(@Param("aid") int authorId);

    /**
     * Finds music that have a genre with the matching genre id
     * @param genreId
     * @return
     */
    @Query(value = "SELECT mus FROM MusicEntity mus WHERE mus.genre.id = :gid")
    Set<MusicEntity> findByGenreId(@Param("gid") int genreId);

    /**
     * Finds music that have an author with the matching author id. It has paging capabilities
     * @param authorId
     * @param pageable
     * @return
     */
    @Query(value = "SELECT mus FROM MusicEntity mus WHERE mus.author.id = :aid")
    List<MusicEntity> findByAuthorId(@Param("aid") int authorId, Pageable pageable);

    /**
     * Finds music that have a genre with the matching genre id. It has paging capabilities
     * @param genreId
     * @param pageable
     * @return
     */
    @Query(value = "SELECT mus FROM MusicEntity mus WHERE mus.genre.id = :gid")
    List<MusicEntity> findByGenreId(@Param("gid") int genreId, Pageable pageable);

    /**
     * Finds music with the given author and genre ids, including that ones that have a match only in 1 parameter -
     * author OR genre. Author AND genre are more prioritized. Has paging capabilities
     * @param authorIds
     * @param genreIds
     * @param pageable
     * @return
     */
    // can't transform this into non-native query - not sure if hql can save condition results.
    @Query(nativeQuery = true, value = "SELECT * FROM music " +
            "WHERE author_id IN (:aids) OR genre_id IN (:gids)" +
            "ORDER BY (CASE " +
            "WHEN author_id IN (:aids) AND genre_id IN (:gids) THEN 1 " +
            "ELSE 2 END) "
    )
    List<MusicEntity> findMatchingPrioritized(
            @Param("aids") int[] authorIds,
            @Param("gids") int[] genreIds,
            Pageable pageable
    );
}
