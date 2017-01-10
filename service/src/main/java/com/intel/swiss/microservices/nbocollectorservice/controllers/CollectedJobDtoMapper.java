package com.intel.swiss.microservices.nbocollectorservice.controllers;

import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.CollectedDataPointDto;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.CollectedJobDto;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.FinishedCollectedJobDto;
import com.intel.swiss.microservices.nbocollectorservice.iface.domain.CollectedDataPoint;
import com.intel.swiss.microservices.nbocollectorservice.iface.domain.CollectedJob;
import com.intel.swiss.microservices.nbocollectorservice.iface.domain.FinishedCollectedJob;
import org.mapstruct.Mapper;

/**
 * Created by rezra3 on 9/15/16.
 */
@Mapper(componentModel = "jsr330")
public interface CollectedJobDtoMapper {
    CollectedJob fromDto(CollectedJobDto collectedJobDto);
    CollectedJobDto toDto(CollectedJob collectedJob);

    CollectedDataPoint fromDto(CollectedDataPointDto collectedDataPointDto);
    CollectedDataPointDto toDto(CollectedDataPoint collectedDataPoint);

    FinishedCollectedJob fromDto(FinishedCollectedJobDto finishedCollectedJobDto);
    FinishedCollectedJobDto toDto(FinishedCollectedJob finishedCollectedJob);

}
