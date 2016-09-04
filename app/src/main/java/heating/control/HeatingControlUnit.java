package heating.control;

import org.androidannotations.annotations.EBean;

import java.net.InetAddress;

import module.control.BaseUnit;

/**
 * Created by Wojtek on 2016-04-04.
 */
@EBean
public class HeatingControlUnit extends BaseUnit implements java.io.Serializable{
    static final long serialVersionUID = 201608131229L;

    public static final String TYPE = "Heating_unit";
    // indicates if there are specific data for heating unit, maybe only for Local
    private boolean mAdvice;
    private boolean mValveOpen;
    private double mCurrentTemperature;
    private double mTargetTemperature;
    private double mCurrentHumidity;
    private InetAddress mUnitAddress;

    public InetAddress getUnitAddress() {
        return mUnitAddress;
    }
    public void setUnitAddress(InetAddress mUnitAddress) {
        this.mUnitAddress = mUnitAddress;
    }

    public HeatingControlUnit(){
    }

//    public HeatingControlUnit(String name, InetAddress mUnitAdress){
//        this.mUnitAdress = mUnitAdress;
//        this.mId = BaseUnit.sMaxId++;
//        this.mName = name;
//    }
    //public String getName() { return mName; }
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

    public double getmCurrentHumidity() {
        return mCurrentHumidity;
    }

    public void setmCurrentHumidity(double mCurrentHumidity) {
        this.mCurrentHumidity = mCurrentHumidity;
    }

}
