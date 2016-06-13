package com.smartheting.smartheating;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_unit_detail)
public class UnitDetailActivity extends AppCompatActivity {

    @ViewById(R.id.etId)
    TextView etId;
    @ViewById(R.id.etName)
    TextView etName;
    @ViewById(R.id.bSave)
    Button bSave;

    @Extra(UnitsActivity.UNIT_ID)
    int mUnitId;
    @Extra(UnitsActivity.UNIT_NAME)
    String mUnitName;
    @Extra(UnitsActivity.EDITABLE)
    boolean mEdition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void setUnitViews() {
        etId.setText(Integer.toString(mUnitId));
        etName.setText(mUnitName);
        setForEdition(mEdition);
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
