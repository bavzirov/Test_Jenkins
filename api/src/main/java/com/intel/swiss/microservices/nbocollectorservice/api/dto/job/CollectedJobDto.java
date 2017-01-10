package com.intel.swiss.microservices.nbocollectorservice.api.dto.job;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Created by rezra3 on 9/11/16.
 */
public class CollectedJobDto {

    @NotNull
    private Integer iteration;

    @NotNull
    private String fullId;

    @NotNull
    private String cmdName;

    @NotNull
    private String cmdLine;

    @NotNull
    private String pool;

    @NotNull
    @ApiModelProperty(notes = "Start time of the job in milli")
    private Long startTimeInMilli;

    @NotNull
    private CollectedJobStatus status;

    private Map<String, String> additionalFields;

    private Integer exitStatus;

    @ApiModelProperty(notes = "Finish time of the job in milli")
    private Long finishTimeInMilli;

    public CollectedJobDto() {
        // Needed for JSON serialization
    }

    public CollectedJobDto(Integer iteration, String fullId, String cmdName, String cmdLine, String pool,
                           Long startTimeInMilli, CollectedJobStatus status) {
        this.iteration = iteration;
        this.fullId = fullId;
        this.cmdName = cmdName;
        this.cmdLine = cmdLine;
        this.pool = pool;
        this.startTimeInMilli = startTimeInMilli;
        this.status = status;
    }

    public Integer getIteration() {
        return iteration;
    }

    public void setIteration(Integer iteration) {
        this.iteration = iteration;
    }

    public String getFullId() {
        return fullId;
    }

    public void setFullId(String fullId) {
        this.fullId = fullId;
    }

    public String getCmdName() {
        return cmdName;
    }

    public void setCmdName(String cmdName) {
        this.cmdName = cmdName;
    }

    public String getCmdLine() {
        return cmdLine;
    }

    public void setCmdLine(String cmdLine) {
        this.cmdLine = cmdLine;
    }

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }

    public Long getStartTimeInMilli() {
        return startTimeInMilli;
    }

    public void setStartTimeInMilli(Long startTimeInMilli) {
        this.startTimeInMilli = startTimeInMilli;
    }

    public CollectedJobStatus getStatus() {
        return status;
    }

    public void setStatus(CollectedJobStatus status) {
        this.status = status;
    }

    public Map<String, String> getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(Map<String, String> additionalFields) {
        this.additionalFields = additionalFields;
    }

    public Integer getExitStatus() {
        return exitStatus;
    }

    public void setExitStatus(Integer exitStatus) {
        this.exitStatus = exitStatus;
    }

    public Long getFinishTimeInMilli() {
        return finishTimeInMilli;
    }

    public void setFinishTimeInMilli(Long finishTimeInMilli) {
        this.finishTimeInMilli = finishTimeInMilli;
    }
}
