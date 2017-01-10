package com.intel.swiss.microservices.nbocollectorservice.jobs;

import com.intel.swiss.microservices.nbocollectorservice.iface.IDataJobService;
import com.intel.swiss.microservices.nbodataservice.api.IJobsWriteController;
import com.intel.swiss.microservices.nbodataservice.api.dto.jobdata.FinishedJobDataDto;
import com.intel.swiss.microservices.nbodataservice.api.dto.jobdata.JobDataDto;
import com.intel.swiss.microservices.nbodataservice.api.dto.jobdata.NewJobDataDto;
import com.intel.swiss.microservices.nbodataservice.api.dto.metrics.DataPointDto;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by rezra3 on 9/15/16.
 */
@Service
public class DataServiceProxy implements IJobsWriteController {

    private final IDataJobService dataJobService;

    @Inject
    public DataServiceProxy(IDataJobService dataJobService) {
        this.dataJobService = dataJobService;
    }

    @Override
    public JobDataDto addJobData(NewJobDataDto job) {
        return dataJobService.addJobData(job);
    }

    @Override
    public JobDataDto markJobAsFinished(String fullIdIteration, FinishedJobDataDto finishedJobDataDto) {
        return dataJobService.markJobAsFinished(fullIdIteration, finishedJobDataDto);
    }

    @Override
    public DataPointDto addDataPoint(String fullIdIteration, DataPointDto dataPointDto) {
        return dataJobService.addDataPoint(fullIdIteration, dataPointDto);
    }

}
