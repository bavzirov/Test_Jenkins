package com.intel.swiss.microservices.nbocollectorservice.jobs;

import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by rezra3 on 9/29/16.
 */
public class JobIdConverterTest {

    private JobIdConverter target = new JobIdConverter();

    @Test
    public void testConvert() throws Exception {
        assertThat(target.getFullIterationId("iil_1.2", 2), Matchers.is("iil_1.2_2"));
    }
}
