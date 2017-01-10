package com.intel.swiss.microservices.nbocollectorservice.controllers;

import com.google.common.collect.Maps;
import com.intel.swiss.micro.framework.test.web.RestControllerTestUtils;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.CollectedJobStatus;
import com.intel.swiss.microservices.nbocollectorservice.iface.IJobsCollectorService;
import com.intel.swiss.microservices.nbocollectorservice.iface.domain.CollectedDataPoint;
import com.intel.swiss.microservices.nbocollectorservice.iface.domain.CollectedJob;
import com.intel.swiss.microservices.nbocollectorservice.iface.domain.FinishedCollectedJob;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

import static com.intel.swiss.micro.framework.test.json.JsonTestUtils.convertObjectToJsonBytes;
import static com.intel.swiss.micro.framework.test.json.JsonTestUtils.createDtoFromMap;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by rezra3 on 9/13/16.
 */
@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { JobsController.class, JobsControllerTestConf.class })
public class JobsControllerTest
{

    @Inject
    private JobsController controller;

    @Inject
    private IJobsCollectorService jobsCollectorService;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception
    {
        this.mvc = RestControllerTestUtils.createMVCMock(controller);
    }

    @Test
    public void failTpoUpdateJobWithEmptyMetricsList() throws Exception
    {
        String strDto = convertObjectToJsonBytes(createUpdateJobDto());
        mvc.perform(MockMvcRequestBuilders.post("/api/jobs/tester2.1/2").contentType(MediaType.APPLICATION_JSON).content(strDto)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
            .andExpect(jsonPath("$.message", is("error.validation")))
            .andExpect(jsonPath("$.fieldErrors[0].field", is("metrics")))
            .andExpect(jsonPath("$.fieldErrors[0].message", is("Size")));
    }

    @Test
    public void testCreateJob() throws Exception
    {
        HashMap<String, Object> collectedJobDto = createCollectedJobDto("tester2.1", 1);
        when(jobsCollectorService.collectJob(any())).thenReturn(createDtoFromMap(CollectedJob.class, collectedJobDto));
        String strDto = convertObjectToJsonBytes(collectedJobDto);
        mvc.perform(MockMvcRequestBuilders.post("/api/jobs").contentType(MediaType.APPLICATION_JSON).content(strDto)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json(strDto, true));
    }

    @Test
    public void testUpdateJob() throws Exception
    {
        HashMap<String, Object> dataPointDto = createCollectedDataPointDto();
        String strDto = convertObjectToJsonBytes(dataPointDto);
        String fullId = "tester2.1";
        int iteration = 1;
        when(jobsCollectorService.addDataPoint(eq(fullId), eq(iteration), any()))
            .thenReturn(createDtoFromMap(CollectedDataPoint.class, dataPointDto));
        mvc.perform(MockMvcRequestBuilders.post("/api/jobs/" + fullId + "/" + iteration).contentType(MediaType.APPLICATION_JSON)
            .content(strDto)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json(strDto));
    }

    @Test
    public void testJobFinished() throws Exception
    {
        HashMap<String, Object> finishedCollectedJobDto = createFinishedCollectedJobDto();
        String strDto = convertObjectToJsonBytes(finishedCollectedJobDto);
        String fullId = "tester2.1";
        int iteration = 1;
        when(jobsCollectorService.markFinished(eq(fullId), eq(iteration), any()))
            .thenReturn(createDtoFromMap(FinishedCollectedJob.class, finishedCollectedJobDto));
        mvc.perform(MockMvcRequestBuilders.put("/api/jobs/" + fullId + "/" + iteration).contentType(MediaType.APPLICATION_JSON)
            .content(strDto)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json(strDto));

    }

    private HashMap<String, Object> createFinishedCollectedJobDto()
    {
        HashMap<String, Object> $ = Maps.newHashMap();
        $.put("exitStatus", 1);
        $.put("finishTimeInMilli", 1L);
        return $;
    }

    private HashMap<String, Object> createCollectedDataPointDto()
    {
        HashMap<String, Object> $ = Maps.newHashMap();
        $.put("relativeSampleTimeInSeconds", 1L);
        $.put("status", CollectedJobStatus.RUN);
        Map<String, Double> metrics = Maps.newHashMap();
        metrics.put("key", 1.0);
        $.put("metrics", metrics);
        return $;
    }

    private HashMap<String, Object> createCollectedJobDto(String fullId, Integer iteration)
    {
        HashMap<String, Object> $ = Maps.newHashMap();
        $.put("iteration", iteration);
        $.put("fullId", fullId);
        $.put("cmdName", "cmdName");
        $.put("cmdLine", "cmdLine");
        $.put("pool", "pool");
        $.put("startTimeInMilli", 1L);
        $.put("status", CollectedJobStatus.RUN);
        $.put("exitStatus", null);
        $.put("finishTimeInMilli", null);
        HashMap<String, String> additionalFields = Maps.newHashMap();
        additionalFields.put("key", "value");
        $.put("additionalFields", additionalFields);
        return $;
    }

    private HashMap<String, Object> createUpdateJobDto()
    {
        HashMap<String, Object> $ = Maps.newHashMap();
        $.put("relativeSampleTimeInSeconds", 1L);
        $.put("status", CollectedJobStatus.RUN);
        $.put("metrics", Maps.newHashMap());
        return $;
    }
}
