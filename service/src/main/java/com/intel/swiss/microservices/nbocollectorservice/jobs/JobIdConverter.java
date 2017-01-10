package com.intel.swiss.microservices.nbocollectorservice.jobs;

import org.springframework.stereotype.Service;

/**
 * Created by rezra3 on 9/29/16.
 */
@Service
public class JobIdConverter {

    public String getFullIterationId(String fullId, int iteration) {
        return fullId + '_' + iteration;
    }
}
