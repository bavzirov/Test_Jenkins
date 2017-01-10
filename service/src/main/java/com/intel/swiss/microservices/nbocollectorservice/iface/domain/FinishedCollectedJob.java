package com.intel.swiss.microservices.nbocollectorservice.iface.domain;

/**
 * Created by rezra3 on 9/15/16.
 */
public class FinishedCollectedJob {

    private Integer exitStatus;
    private Long finishTimeInMilli;

    public FinishedCollectedJob() {
        // For mapper
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
