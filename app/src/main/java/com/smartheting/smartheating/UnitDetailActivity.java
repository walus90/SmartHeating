package com.smartheting.smartheating;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UnitDetailActivity extends AppCompatActivity {

    private String unitId;
    private String unitName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unit_detail);
        TextView tvId = (TextView)findViewById(R.id.tvId);
        TextView tvName = (TextView)findViewById(R.id.tvName);
        Intent i = getIntent();
        unitId = i.getStringExtra(UnitsActivity.UNIT_ID);
        unitName = i.getStringExtra(UnitsActivity.UNIT_NAME);
        tvId.setText(tvId.getText() + unitId);
        tvName.setText(tvName.getText() + unitName);
    }

}
