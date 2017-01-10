package com.intel.swiss.microservices.nbocollectorservice.jobs;

import com.codahale.metrics.annotation.Counted;
import com.google.common.collect.Lists;
import com.intel.swiss.microservices.nbocollectorservice.iface.IJobsCollectorService;
import com.intel.swiss.microservices.nbocollectorservice.iface.domain.CollectedDataPoint;
import com.intel.swiss.microservices.nbocollectorservice.iface.domain.CollectedJob;
import com.intel.swiss.microservices.nbocollectorservice.iface.domain.FinishedCollectedJob;
import com.intel.swiss.microservices.nbodataservice.api.dto.jobdata.DataJobStatus;
import com.intel.swiss.microservices.nbodataservice.api.dto.jobdata.FinishedJobDataDto;
import com.intel.swiss.microservices.nbodataservice.api.dto.jobdata.NewJobDataDto;
import com.intel.swiss.microservices.nbodataservice.api.dto.metrics.DataPointDto;
import com.intel.swiss.microservices.nbodataservice.api.dto.metrics.JobMetricDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by rezra3 on 9/15/16.
 */
@Service
class JobsCollectorService implements IJobsCollectorService {

    private static final Logger log = LoggerFactory.getLogger(JobsCollectorService.class);
    private final DataServiceProxy dataJobService;
    private final JobIdConverter jobIdConverter;
    private final CounterService counterService;

    @Inject
    public JobsCollectorService(DataServiceProxy dataJobService, JobIdConverter jobIdConverter,
                                CounterService counterService) {
        this.dataJobService = dataJobService;
        this.jobIdConverter = jobIdConverter;
        this.counterService = counterService;
    }

    @Override
    @Counted
    public CollectedJob collectJob(CollectedJob job) {
        log.info("Got job {} from {}", job.getFullId(), job.getPool());
        counterService.increment("meter-collectJob");
        NewJobDataDto newJobDataDto = new NewJobDataDto();
        newJobDataDto.setStatus(DataJobStatus.valueOf(job.getStatus().toString()));
        newJobDataDto.setCmdLine(job.getCmdLine());
        newJobDataDto.setCmdName(job.getCmdName());
        newJobDataDto.setFullIdIteration(jobIdConverter.getFullIterationId(job.getFullId(), job.getIteration()));
        newJobDataDto.setPoolMaster(job.getPool());
        newJobDataDto.setStartTime(job.getStartTimeInMilli());
        if (job.getAdditionalFields() != null) {
            newJobDataDto.setAdditionalFields(job.getAdditionalFields());
        }
        dataJobService.addJobData(newJobDataDto);
        return job;
    }

    @Override
    @Counted
    public CollectedDataPoint addDataPoint(String fullId, int iteration, CollectedDataPoint collectedDataPoint) {
        String fullIterationId = jobIdConverter.getFullIterationId(fullId, iteration);
        log.info("Got data point for job {} with {} metrics", fullIterationId, collectedDataPoint.getMetrics().size());
        counterService.increment("meter-addDataPoint");
        DataPointDto dataPointDto = new DataPointDto(collectedDataPoint.getRelativeSampleTimeInSeconds(), DataJobStatus.valueOf(collectedDataPoint.getStatus().toString()));
        List<JobMetricDto> metrics = Lists.newArrayList();
        collectedDataPoint.getMetrics().forEach((k,v) -> metrics.add(new JobMetricDto(k, v)));
        dataPointDto.setMetrics(metrics);
        dataJobService.addDataPoint(fullIterationId, dataPointDto);
        return collectedDataPoint;
    }

    @Override
    @Counted
    public FinishedCollectedJob markFinished(String fullId, int iteration, FinishedCollectedJob finishedCollectedJob) {
        String fullIterationId = jobIdConverter.getFullIterationId(fullId, iteration);
        log.info("Got mark job as finished for job {}", fullIterationId);
        counterService.increment("meter-markFinished");
        FinishedJobDataDto finishedJobDataDto = new FinishedJobDataDto();
        finishedJobDataDto.setFinishTime(finishedCollectedJob.getFinishTimeInMilli());
        finishedJobDataDto.setExitStatus(finishedCollectedJob.getExitStatus());
        dataJobService.markJobAsFinished(fullIterationId, finishedJobDataDto);
        return finishedCollectedJob;
    }
}
