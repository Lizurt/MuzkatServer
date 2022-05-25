package com.muzkat.server.repository;

import com.muzkat.server.model.entity.MetricEntity;
import com.muzkat.server.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface MetricRepository extends JpaRepository<MetricEntity, Integer> {
    Optional<MetricEntity> findByName(String login);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM metric_counted_user " +
            "WHERE user_id = :uid AND metric_id = :mid)", nativeQuery = true)
    Boolean checkIfUserIsCountedInMetric(@Param("uid") Integer userId, @Param("mid") Integer metricId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO metric_counted_user VALUES (:uid, :mid)")
    void countUserInMetric(@Param("uid") Integer userId, @Param("mid") Integer metricId);


}
