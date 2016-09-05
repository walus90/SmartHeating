package com.smartheting.smartheating;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_configuration)
public class ConfigurationActivity extends AppCompatActivity {
    @ViewById TextView tvCheckConnection;
    @ViewById TextView tvStoringUnits;
    @ViewById Button bDiscoverUnitsConfiguration;
    @ViewById Button bClearAllSettings;

    @Bean
    public UnitsList mUnitsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Click
    public void tvCheckConnection(View v){
        Intent checkCon = new Intent(this, CheckConnectionActivity_.class);
        startActivity(checkCon);
    }

    @Click
    public void tvStoringUnits(View v){
        Intent storageActiv = new Intent(this, SaveUnitsActivity_.class);
        startActivity(storageActiv);
    }
    @Click
    public void bDiscoverUnitsConfiguration(View v){
        confirmDiscoveryRequest();
    }

    private void confirmDiscoveryRequest() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?")
                .setTitle("Discover new units")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id){
                                mUnitsList.discoverUnits();
                            }
                        }
                ).setNegativeButton("No",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id){
                    }
                }).show();
    }

    @Click
    public void bClearAllSettings(View v){
        SharedPreferences chartPref = getSharedPreferences(ChartOptionsActivity_.PREF_NAME, Context.MODE_PRIVATE);
        chartPref.edit().clear().apply();
        SharedPreferences appPref = getSharedPreferences(SettingsActivity_.SETTINGS_NAME, Context.MODE_PRIVATE);
        appPref.edit().clear().apply();
    }

}
