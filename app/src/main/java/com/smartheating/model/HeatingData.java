package com.smartheating.model;

import io.realm.RealmObject;

/**
 * Created by Wojtek on 2016-06-10.
 */
public class HeatingData extends RealmObject {
    private long timestamp;
    private int unitId;
    private double currentTemperature;

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


}
