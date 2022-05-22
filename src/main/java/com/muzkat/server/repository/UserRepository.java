package com.muzkat.server.repository;

import com.muzkat.server.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByLogin(String login);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_author_preferences VALUES (:uid, :aid)", nativeQuery = true)
    void addFavAuthor(@Param("uid") Integer userId, @Param("aid") Integer authorId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO user_genre_preferences VALUES (:uid, :gid)", nativeQuery = true)
    void addFavGenre(@Param("uid") Integer userId, @Param("gid") Integer genreId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_author_preferences WHERE user_id = :uid AND author_id = :aid", nativeQuery = true)
    void delFavAuthor(@Param("uid") Integer userId, @Param("aid") Integer authorId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_genre_preferences WHERE user_id = :uid AND genre_id = :gid", nativeQuery = true)
    void delFavGenre(@Param("uid") Integer userId, @Param("gid") Integer genreId);
}
