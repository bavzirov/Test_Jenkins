package com.intel.swiss.microservices.nbocollectorservice.controllers;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.intel.swiss.micro.framework.test.json.JsonTestUtils;
import com.intel.swiss.micro.framework.test.reflection.ReflectionTestUtils;
import com.intel.swiss.micro.framework.test.web.AbstractControllerNotNullFieldsTest;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.CollectedDataPointDto;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.CollectedJobStatus;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collection;
import java.util.HashMap;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by rezra3 on 9/14/16.
 */
@SpringBootTest(classes = { JobsController.class, JobsControllerTestConf.class })
public class JobsControllerUpdateJobNotNullFieldsTest
    extends AbstractControllerNotNullFieldsTest<JobsController, CollectedDataPointDto>
{

    private String field;

    public JobsControllerUpdateJobNotNullFieldsTest(String field)
    {
        this.field = field;
    }

    @Parameterized.Parameters(name = "{index} : {0}")
    public static Collection<String> parameters()
    {
        return ReflectionTestUtils.getFields(CollectedDataPointDto.class, Sets.newHashSet());
    }

    @Override
    protected CollectedDataPointDto createDto()
    {
        CollectedDataPointDto dto = new CollectedDataPointDto();
        dto.setStatus(CollectedJobStatus.RUN);
        dto.setRelativeSampleTimeInSeconds(1);
        HashMap<String, Float> metrics = Maps.newHashMap();
        metrics.put("key", 1.0F);
        dto.setMetrics(metrics);
        return dto;
    }

    @Test
    public void testNotNullFields() throws Exception
    {
        CollectedDataPointDto dto = createDto();
        String strDto = JsonTestUtils.removeFieldAndConvertToJson(dto, field);
        getMvc().perform(MockMvcRequestBuilders.post("/api/jobs/tester2.1/2").contentType(MediaType.APPLICATION_JSON).content(strDto)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
            .andExpect(jsonPath("$.message", is("error.validation")))
            .andExpect(jsonPath("$.fieldErrors[0].field", is(field)))
            .andExpect(jsonPath("$.fieldErrors[0].message", is("NotNull")));
    }
}
