package com.intel.swiss.microservices.nbocollectorservice.controllers;

import com.codahale.metrics.annotation.Timed;
import com.intel.swiss.microservices.nbocollectorservice.api.IJobsController;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.CollectedDataPointDto;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.CollectedJobDto;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.FinishedCollectedJobDto;
import com.intel.swiss.microservices.nbocollectorservice.iface.IJobsCollectorService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Created by rezra3 on 9/11/16.
 */
@RestController
public class JobsController implements IJobsController {

    private IJobsCollectorService jobsCollectorService;
    private final CollectedJobDtoMapper mapper;

    @Inject
    public JobsController(IJobsCollectorService jobsCollectorService, CollectedJobDtoMapper mapper) {
        this.jobsCollectorService = jobsCollectorService;
        this.mapper = mapper;
    }

    @Override
    @Timed
    public CollectedJobDto createJob(@Valid @RequestBody CollectedJobDto newCollectedJobDto) {
        return mapper.toDto(jobsCollectorService.collectJob(mapper.fromDto(newCollectedJobDto)));
    }

    @Override
    @Timed
    public CollectedDataPointDto addDataPoint(@PathVariable(value = "fullId") String fullId,
                                        @PathVariable(value = "iteration") Integer iteration,
                                        @Valid @RequestBody CollectedDataPointDto collectedDataPointDto) {
        return mapper.toDto(jobsCollectorService
            .addDataPoint(fullId, iteration, mapper.fromDto(collectedDataPointDto)));
    }

    @Override
    @Timed
    public FinishedCollectedJobDto jobFinished(@PathVariable(value = "fullId") String fullId,
                                            @PathVariable(value = "iteration") Integer iteration,
                                            @Valid @RequestBody FinishedCollectedJobDto finishedCollectedJobDto) {
        return mapper.toDto(jobsCollectorService
            .markFinished(fullId, iteration, mapper.fromDto(finishedCollectedJobDto)));
    }
}
