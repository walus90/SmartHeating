package com.smartheating.model;

import java.util.Date;

import io.realm.RealmObject;
import module.control.DataSample;

/**
 * Created by Wojtek on 2016-06-10.
 */
public class HeatingData extends RealmObject implements DataSample<Double> {
    private long timestamp;
    private int unitId;
    private double currentTemperature;
    private Date date;

    public long getTimestamp() {
        return timestamp;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public Double getValue() {
        return getCurrentTemperature();
    }

    // change to view real time
    @Override
    public long getTime() {
        return getTimestamp();
    }
}
