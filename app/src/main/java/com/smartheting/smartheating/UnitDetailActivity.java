package com.smartheting.smartheating;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import heating.control.HeatingControlUnit;
import module.control.BaseUnit;
import module.control.UnitPropertyView;
import module.control.UnitPropertyView_;

@EActivity(R.layout.activity_unit_detail)
public class UnitDetailActivity extends AppCompatActivity {

    @ViewById(R.id.bSave)
    Button bSave;
    @ViewById(R.id.upvId)
    UnitPropertyView upvId;

    @Extra(UnitsActivity.UNIT_ID)
    int mUnitId;
    @Extra(UnitsActivity.UNIT_NAME)
    String mUnitName;
    @Extra(UnitsActivity.EDITABLE)
    boolean mEdition;

    private BaseUnit unitToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @AfterExtras
    void setUnit(){
        unitToShow = UnitsList.getUnitById(mUnitId);
    }

    @AfterViews
    void setUnitViews() {
        FillViewWithData(unitToShow);
    }

    private void FillViewWithData(HeatingControlUnit heatingControlUnit) {
        upvId.setView("Id: ", Integer.toString(unitToShow.getId()), false);
        setForEdition(mEdition);
    }

    private void FillViewWithData(BaseUnit baseUnit) {
        if(baseUnit instanceof HeatingControlUnit){
            HeatingControlUnit hcu = (HeatingControlUnit)baseUnit;
            FillViewWithData(hcu);
        }else {
            setForEdition(mEdition);
        }
    }


    private void setForEdition(boolean toEdit){
        if(toEdit) {
            bSave.setVisibility(View.VISIBLE);
            bSave.setClickable(true);
        }
    }

}
