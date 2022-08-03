package com.muzkat.server.repository;

import com.muzkat.server.model.entity.AuthorEntity;
import com.muzkat.server.model.entity.GenreEntity;
import com.muzkat.server.model.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

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

    @Query(value = "SELECT * FROM user_author_preferences WHERE author_id = :aid", nativeQuery = true)
    Set<Integer> findIdsByFavAuthorId(@Param("aid") int authorId);

    @Query(value = "SELECT * FROM user_genre_preferences WHERE genre_id = :gid", nativeQuery = true)
    Set<Integer> findIdsByFavGenreId(@Param("gid") int genreId);

    @Query(nativeQuery = true, value = "SELECT app_user.user_id, login, pswrd FROM user_genre_preferences " +
            "JOIN app_user ON app_user.user_id = user_genre_preferences.user_id " +
            "WHERE user_genre_preferences.genre_id = :gid AND user_genre_preferences.user_id != :sid")
    Set<UserEntity> findSimilarTasteUsersByGenreId(@Param("gid") int genreId, @Param("sid") int selfId);

    @Query(nativeQuery = true, value = "SELECT app_user.user_id, login, pswrd FROM user_author_preferences " +
            "JOIN app_user ON app_user.user_id = user_author_preferences.user_id " +
            "WHERE user_author_preferences.author_id = :aid AND user_author_preferences.user_id != :sid")
    Set<UserEntity> findSimilarTasteUsersByAuthorId(@Param("aid") int genreId, @Param("sid") int selfId);
}
