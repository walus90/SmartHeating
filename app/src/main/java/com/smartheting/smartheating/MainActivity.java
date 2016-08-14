package com.smartheting.smartheating;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.smartheating.model.HeatingData;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

import heating.control.HeatingPrefs_;
import heating.control.HeatingPrefs;
import heating.control.LoadUnitBinary;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import module.control.UnitLoader;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @Bean(LoadUnitBinary.class)
    UnitLoader loader;

    @Bean
    public UnitsList mUnitsList;
    static Context context;

    Realm realm;
    RealmConfiguration realmConfig;

    @Pref
    HeatingPrefs_ heatingPrefs;

    @ViewById(R.id.tvUnit)
    TextView tvUnits;
    @ViewById(R.id.tvConfiguration)
    TextView tvConfiguration;
    @ViewById(R.id.tvStatistic)
    TextView tvStatistics;
    @ViewById(R.id.tvSettings)
    TextView tvSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        // Create the Realm configuration
        realmConfig = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        // Open the Realm for the UI thread.
        realm = Realm.getInstance(realmConfig);
        //PERFECT FOR DI
        // first run
//        LoadUnitBinary l = new LoadUnitBinary();
//        l.setCurrentFileName(heatingPrefs.pathToBinUnits().get());
        //mUnitsList = new UnitsList(this, l);
    }

    @AfterInject
    void loadUnits() {
        ((LoadUnitBinary)loader).setPathToFiles(heatingPrefs.pathToBinUnits().get());
        mUnitsList.setLoader((LoadUnitBinary) loader);
        // not sure whats going on
        mUnitsList.mBinaryLoader.setPathToFiles(heatingPrefs.pathToBinUnits().get());
        // Check if there are saved units, if no ask user for discovery
        if (mUnitsList.readUnitsFromBinary() == 0) {
            //askForUnitDiscovery();
            mUnitsList.addSampleUnits();
        }
    }

    @Bean
    void setPathUnits(UnitsList unitsList){
//        ((LoadUnitBinary)loader).setPathToFiles(heatingPrefs.pathToBinUnits().get());
//        unitsList.setLoader((LoadUnitBinary) loader);
    }

    void askForUnitDiscovery(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No units found on your device, do you want to discover them now? " +
                "You have to be connected to network with units, " +
                "You can also do it later from Settings")
                .setTitle("No units found")
                .setCancelable(false)
                .setPositiveButton("Discover now",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id){
                                mUnitsList.discoverUnits();
                            }
                        }
                ).setNegativeButton("Later",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id){
                    }
                }).show();
    }

    @Click(R.id.tvStatistic)
    public void statisticActiv(View v){
        Toast.makeText(this, "Statistic", Toast.LENGTH_SHORT).show();
        Intent statisticIntent = new Intent(this, StatisticsActivity_.class);
        startActivity(statisticIntent);
        addHeatingData(realm);
        showRecord(realm);
    }

    @Click(R.id.tvSettings)
    public void settingsActiv(View v){
        Intent settingsIntent = new Intent(this, SettingsActivity_.class);
        startActivity(settingsIntent);
    }

    @Click(R.id.tvUnit)
    public void unitActiv(View v){
        Intent showIntent = new Intent(MainActivity.this, UnitsActivity_.class);
        MainActivity.this.startActivity(showIntent);
    }

    @Click(R.id.tvConfiguration)
    public void tvConfiguration(View v){
        Intent configIntent = new Intent(this, ConfigurationActivity_.class);
        MainActivity.this.startActivity(configIntent);
    }

    // I think it is enough to add object
    public void addHeatingData(Realm realm){
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                HeatingData heatingData = realm.createObject(HeatingData.class);
                heatingData.setCurrentTemperature(23.7);
                // not sure about the time
                Long tsLong = System.currentTimeMillis()/1000;
                heatingData.setTimestamp(tsLong);
                heatingData.setUnitId(1);
            }
        });
    }

    public void showRecord(Realm realm){
        HeatingData heatingData = realm.where(HeatingData.class).findFirst();
        String addedVal = "in time "+ heatingData.getTimestamp() + " temperature is " + heatingData.getCurrentTemperature();
        Toast.makeText(this, addedVal,Toast.LENGTH_SHORT).show();
        RealmResults<HeatingData> allValues = realm.where(HeatingData.class).findAll();
        for(HeatingData h: allValues) {
            addedVal = "in time "+ h.getTimestamp() + " temperature is " + h.getCurrentTemperature();
            Log.i(MainActivity.class.getName(), addedVal);
        }
    }

    // TODO maybe run in constructor to ensure it's not null?
    public static Context getAppContext(){
        return MainActivity.context;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }

}
