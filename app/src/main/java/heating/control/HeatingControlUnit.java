package heating.control;

import module.control.BaseUnit;

/**
 * Created by Wojtek on 2016-04-04.
 */

public class HeatingControlUnit extends BaseUnit {
    public String getName() { return mName; }
    public void setName(String name) { this.mName = name; };

    // indicates if there are specific data for heating unit, maybe only for Local
    private boolean mAdvice;
    private boolean mValveOpen;
    private int mCurrentTemperature;
    private int mTargetTemperature;
    private int mEnvironmentTemperature;

    public HeatingControlUnit(String name){
        this.mName = name;
    }
    public int getId() { return mId; }

}
