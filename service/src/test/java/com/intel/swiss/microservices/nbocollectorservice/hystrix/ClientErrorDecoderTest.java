package com.intel.swiss.microservices.nbocollectorservice.hystrix;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.intel.swiss.micro.framework.exceptions.CustomParameterizedException;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.FeignException;
import feign.Response;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.json.Json;
import javax.json.JsonWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by rezra3 on 11/30/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ClientErrorDecoderTest {

    private ClientErrorDecoder target;

    private ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();


    @Before
    public void setUp() throws Exception {
        target = new ClientErrorDecoder(mapper);
    }

    @Test
    public void testDecodeWith4xxError() throws Exception {
        StringWriter stringWriter = getBodyJsonString();
        Response response = Response.builder().status(404).reason("reason")
            .headers(Maps.newHashMap())
            .body(stringWriter.getBuffer().toString(), Charset.defaultCharset()).build();
        Exception exception = target.decode("method", response);
        assertThat(exception, CoreMatchers.instanceOf(HystrixBadRequestException.class));
        assertThat(exception.getMessage(), is("message"));
    }

    @Test
    public void testDecodeWith5xxError() throws Exception {
        StringWriter stringWriter = getBodyJsonString();
        Response response = Response.builder().status(500).reason("reason")
            .headers(Maps.newHashMap())
            .body(stringWriter.getBuffer().toString(), Charset.defaultCharset()).build();
        Exception exception = target.decode("method", response);
        assertThat(exception, CoreMatchers.instanceOf(CustomParameterizedException.class));
        assertThat(exception.getMessage(), is("message"));
    }

    @Test
    public void testDecodeWithOtherError() throws Exception {
        StringWriter stringWriter = getBodyJsonString();
        Response response = Response.builder().status(999).reason("reason")
            .headers(Maps.newHashMap())
            .body(stringWriter.getBuffer().toString(), Charset.defaultCharset()).build();
        Exception exception = target.decode("method", response);
        assertThat(exception, CoreMatchers.instanceOf(FeignException.class));
    }

    @Test
    public void testDecodeWithEmptyMessageInBody() throws Exception {
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = Json.createWriter(stringWriter);
        writer.writeObject(Json.createObjectBuilder().build());
        writer.close();
        Response response = Response.builder().status(404).reason("reason")
            .headers(Maps.newHashMap())
            .body(stringWriter.getBuffer().toString(), Charset.defaultCharset()).build();
        Exception exception = target.decode("method", response);
        assertThat(exception, CoreMatchers.instanceOf(HystrixBadRequestException.class));
        assertThat(exception.getMessage(), is("Remote call failed and called service did not return a message"));
    }

    private StringWriter getBodyJsonString() {
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = Json.createWriter(stringWriter);
        writer.writeObject(Json.createObjectBuilder().add("message", "message").build());
        writer.close();
        return stringWriter;
    }

}
