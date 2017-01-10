package com.intel.swiss.microservices.nbocollectorservice.iface;

import com.intel.swiss.microservices.nbocollectorservice.iface.domain.CollectedDataPoint;
import com.intel.swiss.microservices.nbocollectorservice.iface.domain.CollectedJob;
import com.intel.swiss.microservices.nbocollectorservice.iface.domain.FinishedCollectedJob;

/**
 * Created by rezra3 on 9/15/16.
 */
public interface IJobsCollectorService {

    CollectedJob collectJob(CollectedJob job);

    CollectedDataPoint addDataPoint(String fullId, int iteration, CollectedDataPoint collectedDataPoint);

    FinishedCollectedJob markFinished(String fullId, int iteration, FinishedCollectedJob finishedCollectedJob);
}
