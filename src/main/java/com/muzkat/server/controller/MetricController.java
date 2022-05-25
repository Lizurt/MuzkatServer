package com.muzkat.server.controller;

import com.muzkat.server.model.entity.MetricEntity;
import com.muzkat.server.model.request.CountInMetricRequest;
import com.muzkat.server.model.request.IncreaseMetricRequest;
import com.muzkat.server.repository.MetricRepository;
import com.muzkat.server.service.MetricService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Metric controller", description = "Controls all metric-related HTTP requests.")
@RestController
public class MetricController {
    @Autowired
    private MetricService metricService;
    @Autowired
    private MetricRepository metricRepository;

    @PostMapping("/metric/count")
    @Operation(summary = "Tries to count a use in some metric.")
    public void tryCountInMetric(@RequestBody CountInMetricRequest countInMetricRequest) {
        metricService.tryCountInMetric(countInMetricRequest);
    }

    @GetMapping("/metric/count/{name}")
    @Operation(summary = "Gets amount of users reached the metric.")
    public Integer getCount(@PathVariable String name) {
        return metricService.getCount(name);
    }

    @GetMapping("/metric/get-all")
    @Operation(summary = "Gets all possible metrics and their statistics.")
    public List<MetricEntity> getAll() {
        return metricRepository.findAll();
    }

    @PutMapping("/metric/inc")
    @Operation(summary = "Increases a metric's counter by 1.")
    public void increaseMetric(@RequestBody IncreaseMetricRequest increaseMetricRequest) {
        metricService.increaseMetric(increaseMetricRequest.getMetricName());
    }
}
