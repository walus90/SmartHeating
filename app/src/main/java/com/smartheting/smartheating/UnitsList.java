package com.smartheting.smartheating;

import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import heating.control.ConnectionHandler;
import heating.control.HeatingControlUnit;
import heating.control.LoadUnitBinary;
import module.control.BaseUnit;

/**
 * Created by Wojtek on 2016-06-22.
 */
@EBean(scope = EBean.Scope.Singleton)
public class UnitsList {
    public static ArrayList<BaseUnit> sUnitsList = new ArrayList<BaseUnit>();
    public static ArrayList<InetAddress> sInetAddresses = null;
    //TODO think about implementing IUnitLoader
    @Bean LoadUnitBinary mBinaryLoader;

    @Bean
    ConnectionHandler dataHandler = new ConnectionHandler();

    @Bean
    public void setLoader(LoadUnitBinary loader){
        loader.setCurrentFileName("");
        if(MainActivity_.getAppContext()!=null) {
            loader.setContext(MainActivity_.getAppContext());
        }
    }

    public UnitsList() {
    }

    /*
    temporary method for testing purpose
     */
    void addSampleUnits() {
        sUnitsList.clear();
        InetAddress a1 = null, a2 = null, a3 = null, a4 = null;
        try {
            a1 = InetAddress.getByName("127.0.0.1");
            a2 = InetAddress.getByName("127.0.0.2");
            a3 = InetAddress.getByName("127.0.0.3");
            a4 = InetAddress.getByName("127.0.0.4");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        HeatingControlUnit u1 = new HeatingControlUnit(), u2 = new HeatingControlUnit();
        HeatingControlUnit u3 = new HeatingControlUnit(), u4 = new HeatingControlUnit();
        u1.setName("Living room"); u1.setUnitAddress(a1);
        u2.setName("Kitchen"); u2.setUnitAddress(a2);
        u3.setName("Bedroom"); u3.setUnitAddress(a3);
        u4.setName("My room"); u4.setUnitAddress(a4);
        sUnitsList.add(u1); sUnitsList.add(u2);
        sUnitsList.add(u3); sUnitsList.add(u4);
    }

    // should return something
    void discoverUnits(){
        boolean readingUnitsReady = false;
        addSampleUnits();
        if(readingUnitsReady) {
            if (sInetAddresses == null) {
//                //TODO handle null values
//                ConnectionHandler connectionHandler = new ConnectionHandler();

            }
        }
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

    // Central unit is always on first place
    public BaseUnit getCentralUnit(){
        return getUnitList().get(0);
    }

    /*
    todo think about own exception, when there is no unit
     */
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

    /*
    Takes path as parameter, returns number of founded units
     */
    public int readUnitsFromBinary() {
        sUnitsList.clear();
        sUnitsList = this.mBinaryLoader.readAllUnitsBinary();
        Log.i(this.getClass().getName(), "Units restored form binary files!\n");
        return sUnitsList.size();
    }
}
