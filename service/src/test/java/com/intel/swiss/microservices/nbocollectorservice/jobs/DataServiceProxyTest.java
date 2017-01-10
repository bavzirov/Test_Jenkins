package com.intel.swiss.microservices.nbocollectorservice.jobs;

import com.intel.swiss.micro.framework.cloud.failedrequests.FailedHttpRequestHolder;
import com.intel.swiss.microservices.nbocollectorservice.iface.IDataJobService;
import com.intel.swiss.microservices.nbodataservice.api.dto.jobdata.DataJobStatus;
import com.intel.swiss.microservices.nbodataservice.api.dto.jobdata.FinishedJobDataDto;
import com.intel.swiss.microservices.nbodataservice.api.dto.jobdata.NewJobDataDto;
import com.intel.swiss.microservices.nbodataservice.api.dto.metrics.DataPointDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.actuate.metrics.CounterService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by rezra3 on 9/29/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class DataServiceProxyTest {

    private DataServiceProxy target;

    @Mock
    private IDataJobService dataJobService;


    @Before
    public void setUp() throws Exception {
        target = new DataServiceProxy(dataJobService);
    }

    @Test
    public void testAddJobData() throws Exception {
        NewJobDataDto newJobDataDto = new NewJobDataDto();
        target.addJobData(newJobDataDto);
        verify(dataJobService, times(1)).addJobData(newJobDataDto);
    }

    @Test
    public void testAddDataPoint() throws Exception {
        DataPointDto dataPointDto = new DataPointDto(1, DataJobStatus.COMP);
        String fullId = "iil_1.2_1";
        target.addDataPoint(fullId, dataPointDto);
        verify(dataJobService, times(1)).addDataPoint(fullId, dataPointDto);
    }

    @Test
    public void testMarkJobAsFinished() throws Exception {
        FinishedJobDataDto finishedJobDataDto = new FinishedJobDataDto();
        finishedJobDataDto.setExitStatus(1);
        finishedJobDataDto.setFinishTime(1L);
        String fullId = "iil_1.2_1";
        target.markJobAsFinished(fullId, finishedJobDataDto);
        verify(dataJobService, times(1)).markJobAsFinished(fullId, finishedJobDataDto);
    }
}
