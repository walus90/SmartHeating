package com.smartheating.model;

import java.util.Date;

import io.realm.RealmObject;
import module.control.IDataSample;

/**
 * Created by Wojtek on 2016-06-10.
 */
public class HeatingData extends RealmObject implements IDataSample<Double> {

    private int unitId;
    private double currentTemperature;
    private double targetTemperature;
    private Date date;

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public double getCurrentTemperature() {
        return currentTemperature;
    }
    public void setCurrentTemperature(double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }
    public double getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }
    public int getUnitId() {
        return unitId;
    }
    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    @Override
    public Double getValue() {
        return getCurrentTemperature();
    }

    // change to view real time
    @Override
    public Date getTime() {
        return getDate();
    }
}
