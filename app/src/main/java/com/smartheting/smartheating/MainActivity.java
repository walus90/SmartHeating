package com.smartheting.smartheating;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;

import heating.control.ConnectionHandler;
import heating.control.HeatingControlUnit;
import heating.control.HeatingSystemConnector;
import wifihotspotutils.WifiApManager;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    public static ArrayList<HeatingControlUnit> sUnitsList = new ArrayList<HeatingControlUnit>();
    public static ConnectionHandler sWifiConnectionHandler = new ConnectionHandler();
    //need to think how to use context
    public static HeatingSystemConnector sHeatingSystemConnector = new HeatingSystemConnector();

    //sample unit for tests
    public HeatingControlUnit sampleUnit = new HeatingControlUnit("sample");

    private WifiApManager wifiApManager;
    // to turn WiFi on if it was enabled before launching application
    private boolean mFormerWifiState;

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
        setContentView(R.layout.activity_main);

        sUnitsList.add(sampleUnit);
        sUnitsList.add(new HeatingControlUnit("ejemplo"));
        Log.i(this.toString(), "units added to sUnitsList");

        //wifiApManager = new WifiApManager(this);
        //wifiApManager.setWifiApEnabled(wifiApManager.getWifiApConfiguration(), true);
        //mFormerWifiState = wifiApManager.getWifiManager().isWifiEnabled();

/*        sHotSpot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!buttonView.isChecked()){
                    wifiApManager.setWifiApEnabled(wifiApManager.getWifiApConfiguration(), false);
                    Toast.makeText(MainActivity.this, "turning " + wifiApManager.getWifiApConfiguration().SSID + " off", Toast.LENGTH_SHORT).show();
                }
                else{
                    wifiApManager.setWifiApEnabled(wifiApManager.getWifiApConfiguration(), true);
                    Toast.makeText(MainActivity.this, "turning " + wifiApManager.getWifiApConfiguration().SSID + " on", Toast.LENGTH_SHORT).show();
                }
                Log.i(MainActivity.this.getClass().toString(), wifiApManager.getWifiApConfiguration().wepKeys.toString());
            }
        });*/

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
        Toast.makeText(MainActivity.this, "Statistic", Toast.LENGTH_SHORT).show();
        Intent statisticIntent = new Intent(MainActivity.this, StatisticsActivity_.class);
        MainActivity.this.startActivity(statisticIntent);
    }

    @Click(R.id.tvSettings)
    public void settingsActiv(View v){
        Toast.makeText(MainActivity.this, "Setitngs", Toast.LENGTH_SHORT).show();
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

/*
turning WiFi back, think how to solve it
@Override
protected void onStop(){
super.onStop();
Log.i("MainActivity", "Accesing Stop");
// TODO create option to let user decide what to do. Now turn WiFi on if was before
if(mFormerWifiState){
this.wifiApManager.getWifiManager().setWifiEnabled(true);
Toast.makeText(MainActivity.this, "Accesssing MainActivity.onPause", Toast.LENGTH_SHORT).show();
}
}
*/

}
