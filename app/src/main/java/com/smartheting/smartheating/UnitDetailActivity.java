package com.smartheting.smartheating;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class UnitDetailActivity extends AppCompatActivity {

    private String unitId;
    private String unitName;
    private boolean edition;

    @ViewById(R.id.etId)
    TextView etId;
    @ViewById(R.id.etName)
    TextView etName;
    @ViewById(R.id.bSave)
    Button bSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unit_detail);
        Intent i = getIntent();
        unitId = i.getStringExtra(UnitsActivity.UNIT_ID);
        unitName = i.getStringExtra(UnitsActivity.UNIT_NAME);
        etId.setText(unitId);
        etName.setText(unitName);
        setForEdition(edition);
    }

    private void setForEdition(boolean toEdit){
        etId.setEnabled(toEdit);
        etName.setEnabled(toEdit);
        if(toEdit) {
            bSave.setVisibility(View.VISIBLE);
            bSave.setClickable(true);
        }
    }

}
