package com.intel.swiss.microservices.nbocollectorservice.jobs;

import com.google.common.collect.Maps;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.CollectedJobStatus;
import com.intel.swiss.microservices.nbocollectorservice.iface.domain.CollectedDataPoint;
import com.intel.swiss.microservices.nbocollectorservice.iface.domain.CollectedJob;
import com.intel.swiss.microservices.nbocollectorservice.iface.domain.FinishedCollectedJob;
import com.intel.swiss.microservices.nbodataservice.api.dto.jobdata.DataJobStatus;
import com.intel.swiss.microservices.nbodataservice.api.dto.jobdata.FinishedJobDataDto;
import com.intel.swiss.microservices.nbodataservice.api.dto.jobdata.NewJobDataDto;
import com.intel.swiss.microservices.nbodataservice.api.dto.metrics.DataPointDto;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.actuate.metrics.CounterService;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by rezra3 on 9/29/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class JobsCollectorServiceTest {

    @Mock
    private DataServiceProxy dataServiceProxy;

    @Mock
    private CounterService counterService;

    private JobIdConverter jobIdConverter = new JobIdConverter();

    private JobsCollectorService target;

    @Before
    public void setUp() throws Exception {
        target = new JobsCollectorService(dataServiceProxy, jobIdConverter, counterService);
    }

    @Test
    public void testCollectJobNoAdditionalFields() throws Exception {
        CollectedJob job = getCollectedJob();
        CollectedJob res = target.collectJob(job);
        assertThat(job, is(res));
        ArgumentCaptor<NewJobDataDto> arg = ArgumentCaptor.forClass(NewJobDataDto.class);
        verify(dataServiceProxy, times(1)).addJobData(arg.capture());
        validateNewJobDataDto(job, arg);
        assertThat(arg.getValue().getAdditionalFields(), Matchers.nullValue());
    }

    @Test
    public void testCollectJobAdditionalFields() throws Exception {
        CollectedJob job = getCollectedJob();
        HashMap<String, String> additionalFields = Maps.newHashMap();
        additionalFields.put("A", "s");
        job.setAdditionalFields(additionalFields);
        CollectedJob res = target.collectJob(job);
        assertThat(job, is(res));
        ArgumentCaptor<NewJobDataDto> arg = ArgumentCaptor.forClass(NewJobDataDto.class);
        verify(dataServiceProxy, times(1)).addJobData(arg.capture());
        validateNewJobDataDto(job, arg);
        assertThat(arg.getValue().getAdditionalFields().size(), is(1));
    }

    @Test
    public void testAddDataPointWithEmptyMetrics() throws Exception {
        CollectedDataPoint collectedDataPoint = getCollectedDataPoint(Maps.newHashMap());
        CollectedDataPoint res = target.addDataPoint("tester2_1.1", 1, collectedDataPoint);
        assertThat(res, Matchers.sameInstance(collectedDataPoint));
        ArgumentCaptor<DataPointDto> arg = ArgumentCaptor.forClass(DataPointDto.class);
        verify(dataServiceProxy, times(1)).addDataPoint(org.mockito.Matchers.eq("tester2_1.1_1"), arg.capture());
        assertThat(arg.getValue().getTimestampInSeconds(), Matchers.is(1));
        assertThat(arg.getValue().getStatus(), Matchers.is(DataJobStatus.COMP));
        assertThat(arg.getValue().getMetrics(), Matchers.empty());
    }

    @Test
    public void testAddDataPointWithMetrics() throws Exception {
        HashMap<String, Float> metrics = Maps.newHashMap();
        metrics.put("A", 1F);
        CollectedDataPoint collectedDataPoint = getCollectedDataPoint(metrics);
        CollectedDataPoint res = target.addDataPoint("tester2_1.1", 1, collectedDataPoint);
        assertThat(res, Matchers.sameInstance(collectedDataPoint));
        ArgumentCaptor<DataPointDto> arg = ArgumentCaptor.forClass(DataPointDto.class);
        verify(dataServiceProxy, times(1)).addDataPoint(org.mockito.Matchers.eq("tester2_1.1_1"), arg.capture());
        assertThat(arg.getValue().getTimestampInSeconds(), Matchers.is(1));
        assertThat(arg.getValue().getStatus(), Matchers.is(DataJobStatus.COMP));
        assertThat(arg.getValue().getMetrics().size(), Matchers.is(1));
    }

    @Test
    public void testMarkAsFinished() throws Exception {
        FinishedCollectedJob finishedCollectedJob = new FinishedCollectedJob();
        finishedCollectedJob.setFinishTimeInMilli(1L);
        finishedCollectedJob.setExitStatus(1);
        FinishedCollectedJob res = target.markFinished("tester2_1.1", 1, finishedCollectedJob);
        assertThat(res, Matchers.sameInstance(finishedCollectedJob));
        ArgumentCaptor<FinishedJobDataDto> arg = ArgumentCaptor.forClass(FinishedJobDataDto.class);
        verify(dataServiceProxy, times(1)).markJobAsFinished(org.mockito.Matchers.eq("tester2_1.1_1"), arg.capture());
        assertThat(arg.getValue().getExitStatus(), Matchers.is(1));
        assertThat(arg.getValue().getFinishTime(), Matchers.is(1L));
    }

    private CollectedDataPoint getCollectedDataPoint(HashMap<String, Float> metrics) {
        CollectedDataPoint collectedDataPoint = new CollectedDataPoint();
        collectedDataPoint.setStatus(CollectedJobStatus.COMP);
        collectedDataPoint.setRelativeSampleTimeInSeconds(1);
        collectedDataPoint.setMetrics(metrics);
        return collectedDataPoint;
    }

    private void validateNewJobDataDto(CollectedJob job, ArgumentCaptor<NewJobDataDto> arg) {
        assertThat(arg.getValue().getCmdLine(), is(job.getCmdLine()));
        assertThat(arg.getValue().getCmdName(), is(job.getCmdName()));
        assertThat(arg.getValue().getPoolMaster(), is(job.getPool()));
        assertThat(arg.getValue().getStartTime(), is(job.getStartTimeInMilli()));
        assertThat(arg.getValue().getFullIdIteration(),
            is(jobIdConverter.getFullIterationId(job.getFullId(), job.getIteration())));
        assertThat(arg.getValue().getStatus().toString(), is(job.getStatus().toString()));
    }

    private CollectedJob getCollectedJob() {
        CollectedJob collectedJob = new CollectedJob();
        collectedJob.setStartTimeInMilli(1L);
        collectedJob.setCmdLine("cmdLine");
        collectedJob.setCmdName("cmdName");
        collectedJob.setExitStatus(1);
        collectedJob.setFinishTimeInMilli(1L);
        collectedJob.setFullId("iil_1.2");
        collectedJob.setIteration(1);
        collectedJob.setPool("iil_1");
        collectedJob.setStatus(CollectedJobStatus.RUN);
        return collectedJob;
    }

}
