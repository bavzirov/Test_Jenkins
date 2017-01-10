package com.intel.swiss.microservices.nbocollectorservice.iface.domain;

import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.CollectedJobStatus;

import java.util.Map;

/**
 * Created by rezra3 on 9/15/16.
 */
public class CollectedJob {
    private int iteration;
    private String fullId;
    private String cmdName;
    private String cmdLine;
    private String pool;
    private long startTimeInMilli;
    private CollectedJobStatus status;
    private Map<String, String> additionalFields;
    private Integer exitStatus;
    private Long finishTimeInMilli;

    public CollectedJob() {
        // For mapper
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
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

    public long getStartTimeInMilli() {
        return startTimeInMilli;
    }

    public void setStartTimeInMilli(long startTimeInMilli) {
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
