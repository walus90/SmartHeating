package com.smartheting.smartheating;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.smartheating.model.HeatingData;
import com.smartheating.model.HumidityData;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.Calendar;
import java.util.Date;

import heating.control.ConnectionHandler;
import heating.control.HeatingPrefs_;
import heating.control.LoadUnitBinary;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import module.control.IUnitLoader;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @Bean
    ConnectionHandler dataHandler = new ConnectionHandler();

    @Bean(LoadUnitBinary.class)
    IUnitLoader loader;

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
    @ViewById(R.id.bUpdateSystem)
    Button bUpdateSystem;

    long lastUpdateTime;
    boolean addOnlySample = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();
        // Create the Realm configuration
        realmConfig = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);
        // Open the Realm for the UI thread.
        //realm = Realm.getInstance(realmConfig);
        realm = Realm.getDefaultInstance();
        clearDefaultRealm();
        lastUpdateTime = heatingPrefs.milsSinceUpdate().get();
        if(lastUpdateTime==0) {
            // sample data
            Calendar c =Calendar.getInstance();
            lastUpdateTime = c.getTimeInMillis();
            updateHeatingSystemData();
        }else {
            updateHeatingSystemData();
        }
    }

    // updating data, from recent time, dummy implementation
    void updateHeatingSystemData(){
        //dummy function call
        //dataHandler.sendData(new byte[] {(byte)lastUpdate});
        // generating sample data
        //dataHandler.colectData();
        lastUpdateTime = Calendar.getInstance().getTime().getTime();
        addHeatingData(realm);
        addHumidityData(realm);
    }

    @AfterInject
    void loadUnits() {
        Log.i(MainActivity_.class.getName(), heatingPrefs.pathToBinUnits().get());
        String pathBin = heatingPrefs.pathToBinUnits().get();
        ((LoadUnitBinary)loader).setPathToFiles(pathBin);
        mUnitsList.setLoader((LoadUnitBinary) loader);
        mUnitsList.mBinaryLoader.setPathToFiles(pathBin);
        // Check if there are saved units, if no ask user for discovery
        if (mUnitsList.readUnitsFromBinary() == 0) {
            askForUnitDiscovery();
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
                                updateHeatingSystemData();
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
    }

    @Click(R.id.tvSettings)
    public void settingsActiv(View v){
        Intent settingsIntent = new Intent(this, SettingsActivity_.class);
        startActivity(settingsIntent);
    }

    @Click(R.id.tvUnit)
    public void unitActiv(View v){
        Intent showIntent = new Intent(this, UnitsActivity_.class);
        startActivity(showIntent);
    }

    @Click(R.id.tvConfiguration)
    public void tvConfiguration(View v){
        Intent configIntent = new Intent(this, ConfigurationActivity_.class);
        startActivity(configIntent);
    }

    @Click(R.id.bUpdateSystem)
    public void bUpdateSystem(View v){
        updateHeatingSystemData();
    }

    private void clearDefaultRealm(){
        this.realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        });
    }

    public void addHeatingData(Realm realm){
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm)
            {
                long lastUpdate;
                if((realm.where(HeatingData.class).count()!=0))
                    lastUpdate = realm.where(HeatingData.class).maximumDate("date").getTime();
                else {
                    //adding sample
                    lastUpdate = MainActivity.this.lastUpdateTime - (1000l * 3600l * 50l);
                }
                long timeDifference = MainActivity.this.lastUpdateTime - lastUpdate;
                // I assume update every hour
                int numberOfSamples = (int)timeDifference/(1000*3600);
                if(numberOfSamples==0)
                    return;
                double minimum = 22, maximum = 28;
                // add sample data for all units
                for(int j=0; j<mUnitsList.getUnitList().size(); j++) {
                    for (int i = 1; i < numberOfSamples; i++) {
                        HeatingData heatingData = realm.createObject(HeatingData.class);
                        double val = minimum + Math.random() * (maximum - minimum);
                        double targetVal = val + 3*(Math.random()-0.5);
                        heatingData.setCurrentTemperature(val);
                        heatingData.setTargetTemperature(targetVal);
                        Calendar c = Calendar.getInstance();
                        c.set(c.get(Calendar.YEAR),
                                c.get(Calendar.MONTH),
                                c.get(Calendar.DAY_OF_MONTH)-i/24,
                                i%24, 0);
                        heatingData.setDate(c.getTime());
                        heatingData.setUnitId(j);
                        realm.copyToRealm(heatingData);
                    }
                }
            }
        });
    }

    public void addHumidityData(Realm realm){
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                long lastUpdate;
                if(realm.where(HumidityData.class).count()!=0)
                    lastUpdate = realm.where(HeatingData.class).maximumDate("date").getTime();
                else {
                    // just to add sample data
                    lastUpdate = MainActivity.this.lastUpdateTime - (1000l * 3600l * 50l);
                }
                long timeDifference = MainActivity.this.lastUpdateTime - lastUpdate;
                // I assume update every hour
                int numberOfSamples = (int)timeDifference/(1000*3600);
                if(numberOfSamples==0)
                    return;
                double minimum = 35, maximum = 60;
                // add sample data for all units
                for(int j=0; j<mUnitsList.getUnitList().size(); j++) {
                    for (int i = 1; i < numberOfSamples; i++) {
                        HumidityData humidityData = realm.createObject(HumidityData.class);
                        double val = minimum + Math.random() * (maximum - minimum);
                        humidityData.setCurrentHumidity(val);
                        Calendar c = Calendar.getInstance();
                        c.set(c.get(Calendar.YEAR),
                                c.get(Calendar.MONTH),
                                c.get(Calendar.DAY_OF_MONTH)-i/24, // 24 hours in day
                                i%24, 0);
                        humidityData.setDate(c.getTime());
                        humidityData.setUnitId(j);
                        realm.copyToRealm(humidityData);
                    }
                }
            }
        });
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
