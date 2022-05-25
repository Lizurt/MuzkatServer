package com.muzkat.server.controller;

import com.muzkat.server.model.request.CountInMetricRequest;
import com.muzkat.server.model.request.IncreaseMetricRequest;
import com.muzkat.server.repository.MetricRepository;
import com.muzkat.server.service.MetricService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Metric controller", description = "Controls all metric-related HTTP requests.")
@RestController
public class MetricController {
    @Autowired
    private MetricService metricService;

    @PostMapping("/metric/count")
    public void tryCountInMetric(@RequestBody CountInMetricRequest countInMetricRequest) {
        metricService.tryCountInMetric(countInMetricRequest);
    }

    @GetMapping("/metric/count/{name}")
    public Integer getCount(@PathVariable String name) {
        return metricService.getCount(name);
    }

    @PutMapping("/metric/inc")
    public void increaseMetric(@RequestBody IncreaseMetricRequest increaseMetricRequest) {
        metricService.increaseMetric(increaseMetricRequest.getMetricName());
    }
}
