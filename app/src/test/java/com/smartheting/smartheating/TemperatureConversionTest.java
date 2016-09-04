package com.smartheting.smartheating;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import heating.control.HeatingUtils;

/**
 * Created by Wojtek on 2016-08-19.
 */
//@RunWith(MockitoJUnitRunner.class)
public class TemperatureConversionTest {
    @Test
    public void testTemperatureConversion(){
        Assert.assertEquals(HeatingUtils.celsiusToFahrenheit(37.0d), 98.6d);
    }
}
