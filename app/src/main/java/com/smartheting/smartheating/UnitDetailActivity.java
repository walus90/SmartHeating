package com.smartheting.smartheating;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
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
    @ViewById UnitPropertyView upvId;
    @ViewById UnitPropertyView upvName;
    @ViewById UnitPropertyView upvType;

    @Extra(UnitsActivity.UNIT_ID)
    int mUnitId;
    @Extra(UnitsActivity.UNIT_NAME)
    String mUnitName;
    @Extra(UnitsActivity.EDITABLE)
    boolean mEdition;

    private BaseUnit unitToShow;
    private boolean edited=false;

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
        upvType.setView("Unit type:", HeatingControlUnit.TYPE, false);
        upvName.setView("Unit name:", unitToShow.getName(), true);
        upvId.setView("Id: ", Integer.toString(unitToShow.getId()), false);

    }

    private void FillViewWithData(BaseUnit baseUnit) {
        if(baseUnit instanceof HeatingControlUnit){
            HeatingControlUnit hcu = (HeatingControlUnit)baseUnit;
            FillViewWithData(hcu);
        }else {

        }
    }

    @Click(R.id.bSave)
    public void saveChangedUnit(View view){
        //if(edited){
            if(unitToShow instanceof HeatingControlUnit){
                HeatingControlUnit hcu = (HeatingControlUnit)unitToShow;
                hcu.setName(upvName.getPropertyValue());
            }
        //}
    }

}
