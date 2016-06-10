package com.smartheting.smartheating;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.smartheating.model.HeatingData;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;

import heating.control.ConnectionHandler;
import heating.control.HeatingControlUnit;
import heating.control.HeatingSystemConnector;
import heating.control.LoadUnitBinary;
import heating.control.LoadUnitBinary_;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import wifihotspotutils.WifiApManager;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    // TODO check how to clear elements
    public static ArrayList<HeatingControlUnit> sUnitsList = new ArrayList<HeatingControlUnit>();
    private LoadUnitBinary mBinaryLoader;
    public static ConnectionHandler sWifiConnectionHandler = new ConnectionHandler();
    //need to think how to use context
    public static HeatingSystemConnector sHeatingSystemConnector = new HeatingSystemConnector();

    //sample unit for tests
    public HeatingControlUnit sampleUnit = new HeatingControlUnit("sample");

    private WifiApManager wifiApManager;
    // to turn WiFi on if it was enabled before launching application
    private boolean mFormerWifiState;

    private Realm realm;
    private RealmConfiguration realmConfig;

    @ViewById(R.id.tvUnit)
    TextView tvUnits;
    @ViewById(R.id.tvConfiguration)
    TextView tvConfiguration;
    @ViewById(R.id.tvStatistic)
    TextView tvStatistics;
    @ViewById(R.id.tvSettings)
    TextView tvSettings;
    @ViewById(R.id.tbHotSpot)
    ToggleButton tbHotSpot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinaryLoader = new LoadUnitBinary();
        mBinaryLoader.setContext(this);
        mBinaryLoader.readAllUnitsBinary();

        // Create the Realm configuration
        realmConfig = new RealmConfiguration.Builder(this).build();
        // Open the Realm for the UI thread.
        realm = Realm.getInstance(realmConfig);

//        sUnitsList.add(sampleUnit);
//        sUnitsList.add(new HeatingControlUnit("ejemplo"));
//        Log.i(this.toString(), "units added to sUnitsList");


//        sHotSpot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(!buttonView.isChecked()){
//                    wifiApManager.setWifiApEnabled(wifiApManager.getWifiApConfiguration(), false);
//                    Toast.makeText(MainActivity.this, "turning " + wifiApManager.getWifiApConfiguration().SSID + " off", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    wifiApManager.setWifiApEnabled(wifiApManager.getWifiApConfiguration(), true);
//                    Toast.makeText(MainActivity.this, "turning " + wifiApManager.getWifiApConfiguration().SSID + " on", Toast.LENGTH_SHORT).show();
//                }
//                Log.i(MainActivity.this.getClass().toString(), wifiApManager.getWifiApConfiguration().wepKeys.toString());
//            }
//        });

    }

    @Click
    public void tbHotSpot(CompoundButton compoundButton){
        if(!compoundButton.isChecked()){
            wifiApManager.setWifiApEnabled(wifiApManager.getWifiApConfiguration(), false);
            Toast.makeText(MainActivity.this, "turning " + wifiApManager.getWifiApConfiguration().SSID + " off", Toast.LENGTH_SHORT).show();
        }
        else{
            wifiApManager.setWifiApEnabled(wifiApManager.getWifiApConfiguration(), true);
            Toast.makeText(MainActivity.this, "turning " + wifiApManager.getWifiApConfiguration().SSID + " on", Toast.LENGTH_SHORT).show();
        }
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
        addHeatingData(realm);
        showRecord(realm);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    // I think it is enough to add object
    public void addHeatingData(Realm realm){
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
                HeatingData heatingData = realm.createObject(HeatingData.class);
                heatingData.setCurrentTemperature(25.0);
                // not sure about the time
                Long tsLong = System.currentTimeMillis()/1000;
                heatingData.setTimestamp(tsLong);
                heatingData.setUnitId(1);
            }
        });
    }

    public void showRecord(Realm realm){
        HeatingData heatingData = realm.where(HeatingData.class).findFirst();
        Toast.makeText(this, "in time "+ heatingData.getTimestamp() + "temperature is " + heatingData.getCurrentTemperature(),
                Toast.LENGTH_SHORT).show();
    }

}
