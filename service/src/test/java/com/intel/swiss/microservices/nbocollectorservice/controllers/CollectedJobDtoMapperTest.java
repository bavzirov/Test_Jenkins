package com.intel.swiss.microservices.nbocollectorservice.controllers;

import com.google.common.collect.Maps;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.CollectedDataPointDto;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.CollectedJobDto;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.CollectedJobStatus;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.FinishedCollectedJobDto;
import com.intel.swiss.microservices.nbocollectorservice.iface.domain.CollectedDataPoint;
import com.intel.swiss.microservices.nbocollectorservice.iface.domain.CollectedJob;
import com.intel.swiss.microservices.nbocollectorservice.iface.domain.FinishedCollectedJob;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by rezra3 on 11/28/16.
 */
public class CollectedJobDtoMapperTest {

    private static final String CORES = "cores";
    private static final String CMD_LINE = "cmdLine";
    private static final String CMD_NAME = "cmdName";
    private static final String POOL = "pool";
    private static final String FULL_ID = "full_id";
    private static final int ITERATION = 2;
    private static final String FIELD = "field";
    private CollectedJobDtoMapper target = Mappers.getMapper(CollectedJobDtoMapper.class);


    @Test
    public void testToDtoCollectedDataPoint() throws Exception {
        CollectedDataPoint collectedDataPoint = getCollectedDataPoint();
        CollectedDataPointDto res = target.toDto(collectedDataPoint);
        assertThat(res.getStatus(), is(CollectedJobStatus.RUN));
        assertThat(res.getRelativeSampleTimeInSeconds(), is(1));
        assertMetrics(res.getMetrics().size(), res.getMetrics());
    }

    @Test
    public void testFromDtoCollectedDataPointDto() throws Exception {
        CollectedDataPointDto collectedDataPointDto = new CollectedDataPointDto();
        collectedDataPointDto.setRelativeSampleTimeInSeconds(1);
        collectedDataPointDto.setStatus(CollectedJobStatus.RUN);
        collectedDataPointDto.setMetrics(getMetrics());
        CollectedDataPoint res = target.fromDto(collectedDataPointDto);
        assertThat(res.getStatus(), is(CollectedJobStatus.RUN));
        assertThat(res.getRelativeSampleTimeInSeconds(), is(1));
        assertMetrics(res.getMetrics().size(), res.getMetrics());
    }

    @Test
    public void testFromDtoCollectedJobDto() throws Exception {
        CollectedJobDto collectedJobDto = getCollectedJobDto();
        CollectedJob res = target.fromDto(collectedJobDto);
        assertThat(res.getStatus(), is(CollectedJobStatus.RUN));
        assertThat(res.getCmdLine(), is(CMD_LINE));
        assertThat(res.getCmdName(), is(CMD_NAME));
        assertThat(res.getExitStatus(), is(1));
        assertThat(res.getPool(), is(POOL));
        assertThat(res.getIteration(), is(ITERATION));
        assertThat(res.getFullId(), is(FULL_ID));
        assertThat(res.getStartTimeInMilli(), is(1L));
        assertThat(res.getFinishTimeInMilli(), is(10L));
        assertAdditionalFields(res.getAdditionalFields());
    }

    @Test
    public void testToDtoCollectedJob() throws Exception {
        CollectedJob collectedJob = getCollectedJob();
        CollectedJobDto res = target.toDto(collectedJob);
        assertThat(res.getExitStatus(), is(1));
        assertThat(res.getCmdLine(), is(CMD_LINE));
        assertThat(res.getCmdName(), is(CMD_NAME));
        assertThat(res.getStatus(), is(CollectedJobStatus.RUN));
        assertThat(res.getFullId(), is(FULL_ID));
        assertThat(res.getStartTimeInMilli(), is(1L));
        assertThat(res.getFinishTimeInMilli(), is(10L));
        assertAdditionalFields(res.getAdditionalFields());
    }

    @Test
    public void testFromDtoFinishedCollectedJobDto() throws Exception {
        FinishedCollectedJobDto finishedCollectedJobDto = new FinishedCollectedJobDto();
        finishedCollectedJobDto.setExitStatus(1);
        finishedCollectedJobDto.setFinishTimeInMilli(10L);
        FinishedCollectedJob res = target.fromDto(finishedCollectedJobDto);
        assertThat(res.getExitStatus(), is(1));
        assertThat(res.getFinishTimeInMilli(), is(10L));
    }

    @Test
    public void testToDtoFinishedCollectedJob() throws Exception {
        FinishedCollectedJob finishedCollectedJob = new FinishedCollectedJob();
        finishedCollectedJob.setExitStatus(1);
        finishedCollectedJob.setFinishTimeInMilli(10L);
        FinishedCollectedJobDto res = target.toDto(finishedCollectedJob);
        assertThat(res.getExitStatus(), is(1));
        assertThat(res.getFinishTimeInMilli(), is(10L));
    }

    private CollectedJob getCollectedJob() {
        CollectedJob collectedJob = new CollectedJob();
        collectedJob.setExitStatus(1);
        collectedJob.setStatus(CollectedJobStatus.RUN);
        collectedJob.setFullId(FULL_ID);
        collectedJob.setPool(POOL);
        collectedJob.setIteration(ITERATION);
        collectedJob.setCmdLine(CMD_LINE);
        collectedJob.setCmdName(CMD_NAME);
        collectedJob.setStartTimeInMilli(1L);
        collectedJob.setFinishTimeInMilli(10L);
        collectedJob.setAdditionalFields(getAdditionalFields());
        return collectedJob;
    }


    private CollectedJobDto getCollectedJobDto() {
        CollectedJobDto collectedJobDto = new CollectedJobDto();
        collectedJobDto.setExitStatus(1);
        collectedJobDto.setFinishTimeInMilli(10L);
        collectedJobDto.setStatus(CollectedJobStatus.RUN);
        collectedJobDto.setStartTimeInMilli(1L);
        collectedJobDto.setIteration(ITERATION);
        collectedJobDto.setCmdLine(CMD_LINE);
        collectedJobDto.setCmdName(CMD_NAME);
        collectedJobDto.setPool(POOL);
        collectedJobDto.setFullId(FULL_ID);
        collectedJobDto.setAdditionalFields(getAdditionalFields());
        return collectedJobDto;
    }

    private HashMap<String, String> getAdditionalFields() {
        HashMap<String, String> additionalFields = Maps.newHashMap();
        additionalFields.put(FIELD, "A");
        return additionalFields;
    }

    private CollectedDataPoint getCollectedDataPoint() {
        CollectedDataPoint collectedDataPoint = new CollectedDataPoint();
        collectedDataPoint.setRelativeSampleTimeInSeconds(1);
        collectedDataPoint.setStatus(CollectedJobStatus.RUN);
        HashMap<String, Float> metrics = getMetrics();
        collectedDataPoint.setMetrics(metrics);
        return collectedDataPoint;
    }

    private HashMap<String, Float> getMetrics() {
        HashMap<String, Float> metrics = Maps.newHashMap();
        metrics.put(CORES, 1F);
        return metrics;
    }

    private void assertMetrics(int size, Map<String, Float> metrics) {
        assertThat(size, is(1));
        assertThat(metrics, Matchers.hasKey(CORES));
    }

    private void assertAdditionalFields(Map<String, String> res) {
        assertThat(res.size(), is(1));
        assertThat(res, Matchers.hasKey(FIELD));
    }
}
