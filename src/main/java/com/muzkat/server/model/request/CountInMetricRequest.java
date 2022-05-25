package com.muzkat.server.model.request;

import lombok.Data;

@Data
public class CountInMetricRequest {
    private String login;
    private String metricName;
}
