package com.smartheting.smartheating;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import wifihotspotutils.WifiApManager;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<HeatingControlUnit> unitsList = new ArrayList<HeatingControlUnit>();
    //sample unit for tests
    public HeatingControlUnit sampleUnit = new HeatingControlUnit("sample");

    private WifiApManager wifiApManager;
    // to turn WiFi on if it was enabled before launching application
    private boolean formerWifiState;

    private TextView tvUnits;
    private TextView tvConfiguration;
    private TextView tvStatistics;
    private TextView tvSettings;
    private Switch sHotSpot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        unitsList.add(sampleUnit);
        unitsList.add(new HeatingControlUnit("ejemplo"));
        tvUnits = (TextView)findViewById(R.id.unitTv);
        tvConfiguration = (TextView)findViewById(R.id.configurationTv);
        tvStatistics = (TextView)findViewById(R.id.statisticTv);
        tvSettings = (TextView)findViewById(R.id.settingsTv);
        sHotSpot = (Switch)findViewById(R.id.switchHotSpot);

        wifiApManager = new WifiApManager(this);
        wifiApManager.setWifiApEnabled(wifiApManager.getWifiApConfiguration(), true);
        formerWifiState = wifiApManager.getWifiManager().isWifiEnabled();

        sHotSpot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        });

        tvUnits.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showIntent = new Intent(MainActivity.this, UnitsActivity.class);
                MainActivity.this.startActivity(showIntent);
            }
        });

        tvStatistics.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                LineGraph lineGraph = new LineGraph();
                Intent i = lineGraph.getIntent(MainActivity.this);
                startActivity(i);
//                Intent statisitcsIntent = new Intent(MainActivity.this, StatisticsActivity.class);
//                MainActivity.this.startActivity(statisitcsIntent);
            }
        });
    }

    // think how to solve it
//    @Override
//    protected void onStop(){
//        super.onStop();
//        Log.i("MainActivity", "Accesing Stop");
//        // TODO create option to let user decide what to do. Now turn WiFi on if was before
//        if(formerWifiState){
//            this.wifiApManager.getWifiManager().setWifiEnabled(true);
//            Toast.makeText(MainActivity.this, "Accesssing MainActivity.onPause", Toast.LENGTH_SHORT).show();
//        }
//    }

}
