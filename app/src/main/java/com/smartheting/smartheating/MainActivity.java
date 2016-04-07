package com.smartheting.smartheating;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<HeatingControlUnit> unitsList = new ArrayList<HeatingControlUnit>();
    //sample unit for tests
    public HeatingControlUnit sampleUnit = new HeatingControlUnit("sample");


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

        // turn hot spot on
        if(!HotSpot.isApOn(this))
            HotSpot.configApState(this);


        sHotSpot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    if(HotSpot.isApOn(MainActivity.this))
                        HotSpot.configApState(MainActivity.this);
                    buttonView.setText(R.string.hotspot_switch_turn_on);
                    Toast.makeText(MainActivity.this, "turning wifi off", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(!HotSpot.isApOn(MainActivity.this))
                        HotSpot.configApState(MainActivity.this);
                    buttonView.setText(R.string.hotspot_switch_turn_off);
                    Toast.makeText(MainActivity.this, "turning wifi on", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvUnits.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO probably there is better way of passing Context
                Intent showIntent = new Intent(MainActivity.this, UnitsActivity.class);
                MainActivity.this.startActivity(showIntent);
            }
        });

    }


}
