package com.smartheting.smartheating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import heating.control.HeatingSystemConnector;

@EActivity
public class SettingsActivity extends AppCompatActivity {

    @ViewById(R.id.bConnect)
    Button bConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
    @Click(R.id.bConnect)
    public void connectToWifi(View v){
        // need to think about how to implement
        HeatingSystemConnector connector = new HeatingSystemConnector("Waliszek", "94laskowicka111",this);
        connector.establishConnection();
    }
}
