package com.smartheting.smartheating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UnitDetailActivity extends AppCompatActivity {

    private String unitId;

    public UnitDetailActivity(HeatingControlUnit heatingControlUnit) {
        unitId = Integer.toString(heatingControlUnit.getId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unit_detail);

        ((TextView)findViewById(R.id.tvId)).setText(((TextView)findViewById(R.id.tvId)).getText() + unitId);
    }
}
