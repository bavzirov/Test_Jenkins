package com.intel.swiss.microservices.nbocollectorservice.controllers;

import com.intel.swiss.micro.framework.exceptions.GeneralErrorDTO;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

/**
 * Created by rezra3 on 12/2/16.
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler(HystrixBadRequestException.class)
    protected ResponseEntity<GeneralErrorDTO> hystrixBadRequest(HystrixBadRequestException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        log.debug("bodyOfResponse: {}", bodyOfResponse);
        Assert.isInstanceOf(HttpClientErrorException.class, ex.getCause());
        HttpClientErrorException exception = (HttpClientErrorException)ex.getCause();
        log.debug("statusCode: {}", exception.getStatusCode());
        return new ResponseEntity<>(new GeneralErrorDTO(bodyOfResponse), exception.getStatusCode());
    }
}
