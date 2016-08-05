package heating.control;

import java.net.InetAddress;

import module.control.BaseUnit;

/**
 * Created by Wojtek on 2016-04-04.
 */

public class HeatingControlUnit extends BaseUnit {

    public static final String TYPE = "Heating unit";
    // indicates if there are specific data for heating unit, maybe only for Local
    private boolean mAdvice;
    private boolean mValveOpen;
    private double mCurrentTemperature;
    private double mTargetTemperature;

    public InetAddress getUnitAdress() {
        return mUnitAdress;
    }

    public void setUnitAdress(InetAddress mUnitAdress) {
        this.mUnitAdress = mUnitAdress;
    }

    private InetAddress mUnitAdress;

    public HeatingControlUnit(String name, InetAddress mUnitAdress){
        this.mUnitAdress = mUnitAdress;
        this.mId = BaseUnit.sMaxId++;
        this.mName = name;
    }
    public String getName() { return mName; }
    public void setName(String name) { this.mName = name; };
    public int getId() { return mId; }

    public boolean isAdvice() {
        return mAdvice;
    }

    public void setAdvice(boolean mAdvice) {
        this.mAdvice = mAdvice;
    }

    public boolean isValveOpen() {
        return mValveOpen;
    }

    public void setValveOpen(boolean mValveOpen) {
        this.mValveOpen = mValveOpen;
    }

    public double getCurrentTemperature() {
        return mCurrentTemperature;
    }

    public void setCurrentTemperature(double mCurrentTemperature) {
        this.mCurrentTemperature = mCurrentTemperature;
    }

    public double getTargetTemperature() {
        return mTargetTemperature;
    }

    public void setTargetTemperature(double mTargetTemperature) {
        this.mTargetTemperature = mTargetTemperature;
    }
}
