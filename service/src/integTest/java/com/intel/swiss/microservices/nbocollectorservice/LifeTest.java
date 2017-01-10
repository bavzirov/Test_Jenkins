package com.intel.swiss.microservices.nbocollectorservice;

import com.google.common.collect.Maps;
import com.intel.swiss.micro.framework.configuration.Constants;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.CollectedDataPointDto;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.CollectedJobDto;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.CollectedJobStatus;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.FinishedCollectedJobDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by rezra3 on 12/8/16.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureStubRunner(ids = {"com.intel.swiss.microservices.nbodataservice:NBODataService-stubs:+:stubs"},
    repositoryRoot = "https://ubit-artifactory-il.intel.com/artifactory/swce-nightly-local")
@ActiveProfiles({Constants.SPRING_PROFILE_PRODUCTION, "integration"})
public class LifeTest {

    @Inject
    private TestRestTemplate anonymousRestTemplate;

    @Test
    public void lifeTest() {
        checkHealth();

        String fullId = "iil_1.1";
        int iteration = 1;
        ResponseEntity<String> res = addJob(fullId, iteration);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ResponseEntity<String> dataPointRes = addDataPoint(fullId, iteration);
        assertThat(dataPointRes.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> jobFinishedResult = markJobAsFinished(fullId, iteration);
        assertThat(jobFinishedResult.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testMarkFinishedJobFinished() throws Exception {
        checkHealth();

        ResponseEntity<String> res = markJobAsFinished("finished_job", 1);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(res.getBody()).isNotEmpty();
    }

    @Test
    public void testInvalidJobInfo() throws Exception {
        checkHealth();

        String fullId = "iil_1.1";
        int iteration = 1;

        CollectedJobDto invalidCollectedJobDto = getInvalidCollectedJobDto(fullId, iteration);
        HttpEntity<CollectedJobDto> jobEntity = new HttpEntity<>(invalidCollectedJobDto);
        ResponseEntity<String> res = anonymousRestTemplate.exchange("/api/jobs", HttpMethod.POST, jobEntity, String.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(res.getBody()).isNotEmpty();

        invalidCollectedJobDto.setPool("iil_1");
        invalidCollectedJobDto.setCmdName("cmdName");
        invalidCollectedJobDto.setCmdLine("cmdLine");
        jobEntity = new HttpEntity<>(invalidCollectedJobDto);
        res = anonymousRestTemplate.exchange("/api/jobs", HttpMethod.POST, jobEntity, String.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void testAddingAlreadyExistingJob() throws Exception {
        checkHealth();

        String fullId = "already_existing_job_id";
        int iteration = 1;

        HttpEntity<CollectedJobDto> jobEntity = new HttpEntity<>(getCollectedJobDto(fullId, iteration));
        ResponseEntity<String> res = anonymousRestTemplate.exchange("/api/jobs", HttpMethod.POST, jobEntity, String.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(res.getBody()).isNotEmpty();
    }

    @Test
    public void testAddDataPointToNonExistingJob() throws Exception {
        checkHealth();

        String fullId = "not_existing_job";
        int iteration = 1;

        ResponseEntity<String> res = addDataPoint(fullId, iteration);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(res.getBody()).isNotEmpty();
    }

    private void checkHealth()
    {
        ResponseEntity<String> $ = anonymousRestTemplate.getForEntity("/manage/health", String.class);
        assertThat($.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private ResponseEntity<String> addJob(String fullId, int iteration)
    {
        HttpEntity<CollectedJobDto> jobEntity = new HttpEntity<>(getCollectedJobDto(fullId, iteration));
        return anonymousRestTemplate.exchange("/api/jobs", HttpMethod.POST, jobEntity, String.class);
    }

    private CollectedJobDto getCollectedJobDto(String fullId, int iteration)
    {
        CollectedJobDto collectedJobDto = new CollectedJobDto();
        collectedJobDto.setPool("iil_1");
        collectedJobDto.setStatus(CollectedJobStatus.RUN);
        collectedJobDto.setCmdLine("cmdLine");
        collectedJobDto.setCmdName("cmdName");
        collectedJobDto.setIteration(iteration);
        collectedJobDto.setFullId(fullId);
        collectedJobDto.setStartTimeInMilli(1L);
        return collectedJobDto;
    }

    private CollectedJobDto getInvalidCollectedJobDto(String fullId, int iteration)
    {
        CollectedJobDto collectedJobDto = new CollectedJobDto();
        collectedJobDto.setStatus(CollectedJobStatus.RUN);
        collectedJobDto.setIteration(iteration);
        collectedJobDto.setFullId(fullId);
        collectedJobDto.setStartTimeInMilli(1L);
        return collectedJobDto;
    }

    private ResponseEntity<String> addDataPoint(String fullId, int iteration)
    {
        HttpEntity<CollectedDataPointDto> jobEntity = new HttpEntity<>(getCollectedDataPointDto());
        return anonymousRestTemplate.exchange("/api/jobs/" +
            fullId + "/" + iteration, HttpMethod.POST, jobEntity, String.class);
    }

    private CollectedDataPointDto getCollectedDataPointDto()
    {
        CollectedDataPointDto collectedDataPointDto = new CollectedDataPointDto();
        collectedDataPointDto.setStatus(CollectedJobStatus.RUN);
        collectedDataPointDto.setRelativeSampleTimeInSeconds(2);
        HashMap<String, Float> metrics = Maps.newHashMap();
        metrics.put("metricA", 10F);
        collectedDataPointDto.setMetrics(metrics);
        return collectedDataPointDto;
    }

    private ResponseEntity<String> markJobAsFinished(String fullId, int iteration)
    {
        HttpEntity<FinishedCollectedJobDto> jobEntity = new HttpEntity<>(getFinishedCollectedJobDto());
        return anonymousRestTemplate.exchange("/api/jobs/" +
            fullId + "/" + iteration, HttpMethod.PUT, jobEntity, String.class);
    }

    private FinishedCollectedJobDto getFinishedCollectedJobDto()
    {
        FinishedCollectedJobDto finishedCollectedJobDto = new FinishedCollectedJobDto();
        finishedCollectedJobDto.setFinishTimeInMilli(10L);
        finishedCollectedJobDto.setExitStatus(101);
        return finishedCollectedJobDto;
    }

}
