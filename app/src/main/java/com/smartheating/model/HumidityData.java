package com.smartheating.model;

import java.util.Date;

import io.realm.RealmObject;
import module.control.IDataSample;

/**
 * Created by Wojtek on 2016-09-04.
 */
public class HumidityData  extends RealmObject implements IDataSample<Double> {

    private int unitId;
    private double currentHumidity;
    private Date date;

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public double getCurrentHumidity() {
        return currentHumidity;
    }

    public void setCurrentHumidity(double currentHumidity) {
        this.currentHumidity = currentHumidity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override

    public Double getValue() {
        return getCurrentHumidity();
    }

    @Override
    public Date getTime() {
        return getDate();
    }
}
