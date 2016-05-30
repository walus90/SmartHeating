package com.smartheting.smartheating;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class ConfigurationActivity extends AppCompatActivity {
    @ViewById TextView tvCheckConnection;
    @ViewById TextView tvStroringUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
    }

    @Click
    public void tvCheckConnection(View v){
        Intent checkCon = new Intent(this, CheckConnectionActivity_.class);
        startActivity(checkCon);
    }
}
