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
    /**
     * Finds all metrics that match the given name
     * @param metricName
     * @return
     */
    Optional<MetricEntity> findByName(String metricName);

    /**
     * Checks if a user with the given id is already counted in a metric with the given id.
     * @param userId
     * @param metricId
     * @return true if the user is counted, false otherwise
     */
    @Query(value = "SELECT EXISTS(SELECT 1 FROM metric_counted_user " +
            "WHERE user_id = :uid AND metric_id = :mid)", nativeQuery = true)
    Boolean checkIfUserIsCountedInMetric(@Param("uid") Integer userId, @Param("mid") Integer metricId);

    /**
     * Counts a user in a metric
     * @param userId
     * @param metricId
     */
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO metric_counted_user VALUES (:uid, :mid)")
    void countUserInMetric(@Param("uid") Integer userId, @Param("mid") Integer metricId);


}
