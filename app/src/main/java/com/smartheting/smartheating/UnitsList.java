package com.smartheting.smartheating;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import heating.control.HeatingControlUnit;
import heating.control.LoadUnitBinary;
import module.control.BaseUnit;

/**
 * Created by Wojtek on 2016-06-22.
 */
public class UnitsList {
    public static ArrayList<BaseUnit> sUnitsList = new ArrayList<BaseUnit>();
    private LoadUnitBinary mBinaryLoader;

    public UnitsList(Context context) {
        mBinaryLoader = new LoadUnitBinary();
        mBinaryLoader.setContext(context);
//        mBinaryLoader.readAllUnitsBinary();

        sUnitsList.add(new HeatingControlUnit("sample"));
        sUnitsList.add(new HeatingControlUnit("ejemplo"));
    }

    public static ArrayList<BaseUnit> getUnitList(){
        return sUnitsList;
    }

    // returns true if unit was added succesfully
    public boolean addUnit(BaseUnit unit){
        boolean valid = true;
        // check if the name is taken
        for (BaseUnit bu : sUnitsList){
            if(bu.getName().equals(unit.getName())) {
                valid = false;
                break;
            }
        }
        return valid;
    }

    public static BaseUnit getUnitById(int id){
        return sUnitsList.get(id);
    }

    public static BaseUnit getUnitByName(String name){
        BaseUnit bu = null;
        for(int i=0; i<sUnitsList.size(); i++){
            if(sUnitsList.get(i).getName().equals(name)){
                bu = sUnitsList.get(i);
                break;
            }
        }
        return bu;
    }
}
