package com.smartheting.smartheating;

/**
 * Created by Wojtek on 2016-04-04.
 */

// TODO create children: CentralUnit and LocalUnit
public class HeatingControlUnit {
    private static int maxId=0;

    //unchanged id of unit, might be based on real hardware
    private int id;

    public String getName() {
        return name;
    }

    private String name;
    // indicates if there are specific data for heating unit, maby only for Local
    private boolean advice;
    private boolean valveOpen;
    private int currentTemperature;
    private int targetTemperature;
    private int environmentTemperature;

    public HeatingControlUnit(String name){
        this.name = name;
    }
    public int getId() {
        return id;
    }

}
