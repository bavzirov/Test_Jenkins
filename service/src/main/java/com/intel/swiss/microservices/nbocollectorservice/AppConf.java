package com.intel.swiss.microservices.nbocollectorservice;

import com.intel.swiss.micro.framework.configuration.Constants;
import feign.Logger;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by rezra3 on 10/9/16.
 */
@Configuration
@EnableFeignClients
@EnableHystrixDashboard
@EnableDiscoveryClient
@Profile(Constants.SPRING_PROFILE_PRODUCTION)
public class AppConf {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

}
