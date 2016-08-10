package com.smartheting.smartheating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import heating.control.HeatingControlUnit;
import heating.control.SaveUnitBinary;
import module.control.BaseUnit;
import module.control.UnitPropertyView;

@EActivity(R.layout.activity_unit_detail)
public class UnitDetailActivity extends AppCompatActivity {

    @ViewById(R.id.bSave) Button bSave;
    @ViewById UnitPropertyView upvId;
    @ViewById UnitPropertyView upvName;
    @ViewById UnitPropertyView upvType;
    @ViewById UnitPropertyView upvCurrentTemp;
    @ViewById UnitPropertyView upvTargetTemp;
    @ViewById UnitPropertyView upvValve;
    @ViewById UnitPropertyView upvAddress;

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
        HeatingControlUnit hcu = (HeatingControlUnit)unitToShow;
        upvType.setView("Unit type:", HeatingControlUnit.TYPE, false);
        upvName.setView("Unit name:", hcu.getName(), true);
        upvId.setView("Id: ", Integer.toString(hcu.getId()), false);
        upvCurrentTemp.setView("Current temperature [\u00b0 C]", Double.toString(hcu.getCurrentTemperature()), false);
        upvTargetTemp.setView("Target temperature [\u00b0 C]", Double.toString(hcu.getTargetTemperature()), true);
        upvValve.setView("Valve status", hcu.isValveOpen() ? "Open" : "Closed", false);
        upvAddress.setView("Unit address: ", hcu.getUnitAdress().toString(), false);
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
        edited = true;
            if(unitToShow instanceof HeatingControlUnit){
                HeatingControlUnit hcu = (HeatingControlUnit)unitToShow;
                hcu.setName(upvName.getPropertyValue());
            }
    }

    void showSaveButton(boolean v){
        bSave.setVisibility(v ? View.VISIBLE :View.GONE);
    }


    // Not sure if this is right place to save units to binary
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        SaveUnitBinary saveUnitBinary = new SaveUnitBinary();
//        saveUnitBinary.saveUnit(this.unitToShow);
    }
}
