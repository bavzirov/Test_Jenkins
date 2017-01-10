package com.intel.swiss.microservices.nbocollectorservice.dto;

import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.FinishedCollectedJobDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by rezra3 on 12/30/16.
 */
@RunWith(SpringRunner.class)
@JsonTest
public class FinishedCollectedJobDtoTest {

    private FinishedCollectedJobDto finishedCollectedJobDto;

    @Inject
    private JacksonTester<FinishedCollectedJobDto> jacksonTester;

    @Before
    public void setUp() throws Exception {
        finishedCollectedJobDto = new FinishedCollectedJobDto(1, 10L);
    }

    @Test
    public void serializeJson() throws Exception {
        assertThat(jacksonTester
            .write(finishedCollectedJobDto))
            .isEqualToJson("FinishedCollectedJobDto.json");
    }

    @Test
    public void deserializeJson() throws Exception {
        assertThat(jacksonTester.read("FinishedCollectedJobDto.json"))
            .isEqualToComparingFieldByField(finishedCollectedJobDto);
    }
}

