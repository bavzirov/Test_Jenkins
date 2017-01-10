package com.intel.swiss.microservices.nbocollectorservice.dto;

import com.google.common.collect.Maps;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.CollectedJobDto;
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
public class CollectedJobDtoTest {

    private CollectedJobDto collectedJobDto;

    @Inject
    private JacksonTester<CollectedJobDto> jacksonTester;

    @Before
    public void setUp() throws Exception {
        collectedJobDto = new CollectedJobDto(1, "iil1.12334", "cmdName",
            "cmdLine", "iil1", 1L, CollectedJobStatus.RUN);
    }

    @Test
    public void serializeJson() throws Exception {
        assertThat(jacksonTester
            .write(collectedJobDto))
            .isEqualToJson("CollectedJobDto.json");
    }

    @Test
    public void deserializeJson() throws Exception {
        assertThat(jacksonTester.read("CollectedJobDto.json"))
            .isEqualToComparingFieldByField(collectedJobDto);
    }

    @Test
    public void serializeJsonWithExitStatusAndAdditionalFields() throws Exception {
        collectedJobDto.setExitStatus(1);
        HashMap<String, String> additionalFields = Maps.newHashMap();
        additionalFields.put("qslot", "/abc");
        collectedJobDto.setAdditionalFields(additionalFields);
        assertThat(jacksonTester
            .write(collectedJobDto))
            .isEqualToJson("CollectedJobDtoFull.json");
    }

    @Test
    public void deserializeJsonWithExitStatusAndAdditionalFields() throws Exception {
        collectedJobDto.setExitStatus(1);
        HashMap<String, String> additionalFields = Maps.newHashMap();
        additionalFields.put("qslot", "/abc");
        collectedJobDto.setAdditionalFields(additionalFields);
        assertThat(jacksonTester.read("CollectedJobDtoFull.json"))
            .isEqualToComparingFieldByField(collectedJobDto);
    }
}

