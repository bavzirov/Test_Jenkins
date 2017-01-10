package com.intel.swiss.microservices.nbocollectorservice.api.dto.job;

/**
 * Created by rezra3 on 9/11/16.
 */
public enum CollectedJobStatus {
    RUN,
    REMOTE_APPROVED, //we are never supposed to see this, it will show up as RUN
    SUSP,
    DISC_RUN,
    DISC_SUSP,  //we are never supposed to see this, it will show up as JOB_STATUS
    COMP, // we are never supposed to see this, can be used for sanity check
    UNKNOWN,
}
