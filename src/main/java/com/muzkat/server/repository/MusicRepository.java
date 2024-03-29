package com.muzkat.server.repository;

import com.muzkat.server.model.entity.AuthorEntity;
import com.muzkat.server.model.entity.GenreEntity;
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
    @Query(value = "SELECT me FROM MusicEntity me WHERE me.name = :men AND me.author = :ae AND me.genre = :ge")
    List<MusicEntity> findAllByValues(@Param("men") String musicName,
                                  @Param("ae") AuthorEntity authorEntity,
                                  @Param("ge") GenreEntity genreEntity);

    @Query(value = "SELECT me FROM MusicEntity me")
    List<MusicEntity> findRandomMusic(Pageable pageable);

    @Query(value = "SELECT mus FROM MusicEntity mus WHERE mus.author.id = :aid")
    Set<MusicEntity> findByAuthorId(@Param("aid") int authorId);

    @Query(value = "SELECT mus FROM MusicEntity mus WHERE mus.genre.id = :gid")
    Set<MusicEntity> findByGenreId(@Param("gid") int genreId);

    @Query(value = "SELECT mus FROM MusicEntity mus WHERE mus.author.id = :aid")
    List<MusicEntity> findByAuthorId(@Param("aid") int authorId, Pageable pageable);

    @Query(value = "SELECT mus FROM MusicEntity mus WHERE mus.genre.id = :gid")
    List<MusicEntity> findByGenreId(@Param("gid") int genreId, Pageable pageable);

    // can't transform this into non-native query - not sure if hql supports cases
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
