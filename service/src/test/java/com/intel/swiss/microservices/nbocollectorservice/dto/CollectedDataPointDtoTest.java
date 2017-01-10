package com.intel.swiss.microservices.nbocollectorservice.dto;

import com.google.common.collect.Maps;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.CollectedDataPointDto;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.CollectedJobStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by rezra3 on 12/30/16.
 */
@RunWith(SpringRunner.class)
@JsonTest
public class CollectedDataPointDtoTest {

    private CollectedDataPointDto collectedDataPointDto;
    @Inject
    private JacksonTester<CollectedDataPointDto> jacksonTester;

    @Before
    public void setUp() throws Exception {
        HashMap<String, Float> metrics = Maps.newHashMap();
        metrics.put("K", 1F);
        collectedDataPointDto = new CollectedDataPointDto(1, CollectedJobStatus.RUN, metrics);

    }

    @Test
    public void serializeJson() throws Exception {
        assertThat(jacksonTester
            .write(collectedDataPointDto))
            .isEqualToJson("CollectedDataPointDto.json");
    }

    @Test
    public void deserializeJson() throws Exception {
        assertThat(jacksonTester.read("CollectedDataPointDto.json"))
            .isEqualToComparingFieldByField(collectedDataPointDto);
    }
}

