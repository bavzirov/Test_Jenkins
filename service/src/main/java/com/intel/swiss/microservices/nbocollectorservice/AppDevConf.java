package com.intel.swiss.microservices.nbocollectorservice;

import com.intel.swiss.micro.framework.configuration.Constants;
import com.intel.swiss.microservices.nbocollectorservice.iface.IDataJobService;
import com.intel.swiss.microservices.nbodataservice.api.dto.jobdata.FinishedJobDataDto;
import com.intel.swiss.microservices.nbodataservice.api.dto.jobdata.JobDataDto;
import com.intel.swiss.microservices.nbodataservice.api.dto.jobdata.NewJobDataDto;
import com.intel.swiss.microservices.nbodataservice.api.dto.metrics.DataPointDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * Created by rezra3 on 10/9/16.
 */
@Configuration
@Profile(Constants.SPRING_PROFILE_DEVELOPMENT)
public class AppDevConf {
    @Bean
    public IDataJobService dataJobService() {
        return new IDataJobService() {
            @Override
            public JobDataDto addJobData(@Valid @RequestBody NewJobDataDto newJobDataDto) {
                return null;
            }

            @Override
            public JobDataDto markJobAsFinished(@PathVariable("fullIdIteration") String s, @Valid @RequestBody FinishedJobDataDto finishedJobDataDto) {
                return null;
            }

            @Override
            public DataPointDto addDataPoint(@PathVariable("fullIdIteration") String s, @Valid @RequestBody DataPointDto dataPointDto) {
                return null;
            }
        };
    }
}
