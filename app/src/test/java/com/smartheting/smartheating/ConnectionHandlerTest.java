package com.smartheting.smartheating;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import heating.control.ConnectionHandler;

import static org.mockito.Mockito.when;

/**
 * Created by Wojtek on 2016-08-19.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConnectionHandlerTest {

    // should be ConnectionHandler_ but not sure how to do it
    @InjectMocks
    ConnectionHandler connectionHandler = Mockito.spy(ConnectionHandler.class);

    class SystemAnswer implements Answer<Void> {

        @Override
        public Void answer(InvocationOnMock invocation) throws Throwable {
            return null;
        }
    }

    @Test
    public void sendParametersRequest(){
        //when(connectionHandler.requestUnitsAdresses()).then(new SystemAnswer());
        when(connectionHandler.colectData()).thenReturn(new byte[]{});
        Assert.assertArrayEquals(connectionHandler.colectData(), new byte[]{});
    }

}
