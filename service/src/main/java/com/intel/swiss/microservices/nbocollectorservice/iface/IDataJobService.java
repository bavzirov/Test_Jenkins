package com.intel.swiss.microservices.nbocollectorservice.iface;

import com.intel.swiss.microservices.nbodataservice.api.IJobsWriteController;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * Created by rezra3 on 9/15/16.
 */
@FeignClient(value = "NBODataService")
public interface IDataJobService extends IJobsWriteController {
}
