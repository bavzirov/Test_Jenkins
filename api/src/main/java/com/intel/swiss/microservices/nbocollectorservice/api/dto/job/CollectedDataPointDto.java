package com.intel.swiss.microservices.nbocollectorservice.api.dto.job;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

/**
 * Created by rezra3 on 9/11/16.
 */
public class CollectedDataPointDto {

    @NotNull
    @ApiModelProperty(notes = "How much seconds passed since the job started")
    private Integer relativeSampleTimeInSeconds;

    @NotNull
    private CollectedJobStatus status;

    @NotNull
    @Size(min = 1)
    private Map<String, Float> metrics;

    public CollectedDataPointDto() {
        // Needed for JSON serialization
    }

    public CollectedDataPointDto(Integer sampleTime, CollectedJobStatus status, Map<String, Float> metrics) {
        this.relativeSampleTimeInSeconds = sampleTime;
        this.status = status;
        this.metrics = metrics;
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
