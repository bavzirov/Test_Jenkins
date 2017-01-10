package com.intel.swiss.microservices.nbocollectorservice.controllers;

import com.intel.swiss.microservices.nbocollectorservice.iface.IJobsCollectorService;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Bean;

/**
 * Created by rezra3 on 9/22/16.
 */
class JobsControllerTestConf {

    @Mock
    private IJobsCollectorService jobsCollectorService;

    public JobsControllerTestConf() {
        MockitoAnnotations.initMocks(this);
    }

    @Bean
    public IJobsCollectorService jobsCollectorService() {
        return jobsCollectorService;
    }

    @Bean
    public CollectedJobDtoMapper collectedJobDtoMapper() {
        return Mappers.getMapper(CollectedJobDtoMapper.class);
    }
}
