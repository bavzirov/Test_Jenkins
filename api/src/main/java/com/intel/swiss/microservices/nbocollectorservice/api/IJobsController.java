package com.intel.swiss.microservices.nbocollectorservice.api;

import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.CollectedDataPointDto;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.CollectedJobDto;
import com.intel.swiss.microservices.nbocollectorservice.api.dto.job.FinishedCollectedJobDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by rezra3 on 9/11/16.
 */
public interface IJobsController {

    @ApiOperation(value = "", nickname = "createJob")
    @ApiResponse(code = 201, message = "Job was created")
    @RequestMapping(value = "/api/jobs", method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    CollectedJobDto createJob(@Valid @RequestBody CollectedJobDto newCollectedJobDto);

    @ApiOperation(value = "", nickname = "updateJob")
    @ApiResponse(code = 200, message = "Job was updated")
    @RequestMapping(value = "/api/jobs/{fullId}/{iteration}", method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    CollectedDataPointDto addDataPoint(@PathVariable(value = "fullId") String fullId, @PathVariable(value = "iteration") Integer iteration,
                                 @Valid @RequestBody CollectedDataPointDto collectedDataPointDto);

    @ApiOperation(value = "", nickname = "jobFinished")
    @ApiResponse(code = 200, message = "Job was marked as finished")
    @RequestMapping(value = "/api/jobs/{fullId}/{iteration}", method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    FinishedCollectedJobDto jobFinished(@PathVariable(value = "fullId") String fullId,
                                @PathVariable(value = "iteration") Integer iteration,
                                @Valid @RequestBody FinishedCollectedJobDto finishedCollectedJobDto);
}
