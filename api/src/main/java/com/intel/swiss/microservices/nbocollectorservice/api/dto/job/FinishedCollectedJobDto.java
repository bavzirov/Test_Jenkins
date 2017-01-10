package com.intel.swiss.microservices.nbocollectorservice.api.dto.job;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by rezra3 on 9/11/16.
 */
public class FinishedCollectedJobDto {

    @NotNull
    private Integer exitStatus;

    @NotNull
    @ApiModelProperty(notes = "Finish time of the job in milli")
    private Long finishTimeInMilli;

    public FinishedCollectedJobDto() {
        // Needed for JSON serialization
    }

    public FinishedCollectedJobDto(Integer exitStatus, Long finishTimeInMilli) {
        this.exitStatus = exitStatus;
        this.finishTimeInMilli = finishTimeInMilli;
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
