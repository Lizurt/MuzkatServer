package com.muzkat.server.service;

import com.muzkat.server.model.entity.MetricEntity;
import com.muzkat.server.model.entity.UserEntity;
import com.muzkat.server.model.request.CountInMetricRequest;
import com.muzkat.server.repository.MetricRepository;
import com.muzkat.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MetricService {
    @Autowired
    private MetricRepository metricRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Tries to count a user in a metric using the user's login and metric's name
     * @param login
     * @param metricName
     */
    public void tryCountInMetric(String login, String metricName) {
        Optional<MetricEntity> metricEntity = metricRepository.findByName(metricName);
        if (metricEntity.isEmpty()) {
            return;
        }
        Optional<UserEntity> userEntity = userRepository.findByLogin(login);
        if (userEntity.isEmpty()) {
            return;
        }
        if (metricRepository.checkIfUserIsCountedInMetric(userEntity.get().getId(), metricEntity.get().getId())) {
            return;
        }
        metricRepository.countUserInMetric(userEntity.get().getId(), metricEntity.get().getId());
        increaseMetric(metricEntity.get());
    }

    /**
     * Tries to count a user in a metric using the request
     * @param countInMetricRequest
     */
    public void tryCountInMetric(CountInMetricRequest countInMetricRequest) {
        tryCountInMetric(countInMetricRequest.getLogin(), countInMetricRequest.getMetricName());
    }

    /**
     * Increases the reached users counter in a metric
     * @param metricEntity
     */
    public void increaseMetric(MetricEntity metricEntity) {
        metricEntity.setReachedAmt(metricEntity.getReachedAmt() + 1);
        metricRepository.save(metricEntity);
    }

    /**
     * Increases the reached users counter in a metric using the metric name
     * @param metricName
     */
    public void increaseMetric(String metricName) {
        Optional<MetricEntity> metricEntity = metricRepository.findByName(metricName);
        if (metricEntity.isEmpty()) {
            return;
        }
        increaseMetric(metricEntity.get());
    }

    /**
     * Gets amount of users that reached the metric
     * @param metricName
     * @return
     */
    public Integer getCount(String metricName) {
        Optional<MetricEntity> metricEntity = metricRepository.findByName(metricName);
        return metricEntity.isEmpty() ? -1 : metricEntity.get().getReachedAmt();
    }
}
