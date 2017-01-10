package com.intel.swiss.microservices.nbocollectorservice.hystrix;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intel.swiss.micro.framework.exceptions.CustomParameterizedException;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;

import static feign.FeignException.errorStatus;

/**
 * Created by rezra3 on 11/30/16.
 */
@Component
public class ClientErrorDecoder implements ErrorDecoder {

    private static final Logger log = LoggerFactory.getLogger(ClientErrorDecoder.class);
    private final ObjectMapper mapper;

    @Inject
    public ClientErrorDecoder(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String body = Util.toString(response.body().asReader());
            log.debug("Method key: {}, http status: {}, body: {}", methodKey, response.status(), body);
            String message = StringUtils.EMPTY;
            if (!StringUtils.isEmpty(body)) {
                Map<String, String> map = mapper.readValue(body, new TypeReference<Map<String, String>>() {});
                message = map.get("message");
            }
            if (StringUtils.isEmpty(message)) {
                message = "Remote call failed and called service did not return a message";
            }
            if (response.status() >= 400 && response.status() <= 499) {
                return new HystrixBadRequestException(message,
                    new HttpClientErrorException(HttpStatus.valueOf(response.status())));
            }
            if (response.status() >= 500 && response.status() <= 599) {
                return new CustomParameterizedException(message);
            }
        } catch (IOException e) {
            log.error("error during reading buffer in error decoder", e);
        }
        return errorStatus(methodKey, response);
    }
}
