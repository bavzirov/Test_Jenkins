package com.intel.swiss.microservices.nbocollectorservice.iface.domain;

import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.CollectedJobStatus;

import java.util.Map;

/**
 * Created by rezra3 on 9/15/16.
 */
public class CollectedDataPoint {

    private Integer relativeSampleTimeInSeconds;
    private CollectedJobStatus status;
    private Map<String, Float> metrics;

    public CollectedDataPoint() {
        // For Mapper
    }

    public Integer getRelativeSampleTimeInSeconds() {
        return relativeSampleTimeInSeconds;
    }

    public void setRelativeSampleTimeInSeconds(Integer relativeSampleTimeInSeconds) {
        this.relativeSampleTimeInSeconds = relativeSampleTimeInSeconds;
    }

    public CollectedJobStatus getStatus() {
        return status;
    }

    public void setStatus(CollectedJobStatus status) {
        this.status = status;
    }

    public Map<String, Float> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, Float> metrics) {
        this.metrics = metrics;
    }
}
