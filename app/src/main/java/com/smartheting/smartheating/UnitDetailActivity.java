package com.smartheting.smartheating;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.Map;
import java.util.Set;

import heating.control.HeatingControlUnit;
import heating.control.HeatingPrefs_;
import heating.control.HeatingUtils_;
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
    @ViewById UnitPropertyView upvCurrentHumidity;
    @ViewById UnitPropertyView upvValve;
    @ViewById UnitPropertyView upvAddress;

    @Pref
    HeatingPrefs_ heatingPrefs;

    @Extra(UnitsActivity.UNIT_ID)
    int mUnitId;
    @Extra(UnitsActivity.UNIT_NAME)
    String mUnitName;
    @Extra(UnitsActivity.EDITABLE)
    boolean mEdition;

    @Bean(HeatingControlUnit.class)
    BaseUnit unitToShow;
    boolean edited=false;

    boolean fahrenheit = false;
    String scaleChar = "C";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = heatingPrefs.getSharedPreferences();
        String val = sp.getString(SettingsActivity_.TEMPERATURE_SCALE, "C");
        if(!val.equals("C")) {
            scaleChar = "F";
            fahrenheit = true;
        }
    }

    @AfterExtras
    void setUnit(){
        unitToShow = UnitsList.getUnitById(mUnitId);
    }

    @AfterViews
    void setUnitViews() {
        FillViewWithData(unitToShow);
    }

    void FillViewWithData(HeatingControlUnit heatingControlUnit) {
        HeatingControlUnit hcu = (HeatingControlUnit)unitToShow;
        upvType.setView("Unit type:", HeatingControlUnit.TYPE, false);
        upvName.setView("Unit name:", hcu.getName(), true);
        upvId.setView("Id: ", Integer.toString(hcu.getId()), false);
        Double currTempToView = fahrenheit ?
            HeatingUtils_.celsiusToFahrenheit(hcu.getCurrentTemperature()):
                hcu.getCurrentTemperature();
        Double targTempToView = fahrenheit ?
                HeatingUtils_.celsiusToFahrenheit(hcu.getTargetTemperature()):
                hcu.getTargetTemperature();
        upvCurrentTemp.setView("Current temperature [\u00b0" + scaleChar + "]", currTempToView.toString(), false);
        upvTargetTemp.setView("Target temperature [\u00b0" + scaleChar + "]",targTempToView.toString(), true);
        upvCurrentHumidity.setView("Current humidity [%]", Double.toString(hcu.getmCurrentHumidity()), false);
        upvValve.setView("Valve status", hcu.isValveOpen() ? "Open" : "Closed", false);
        upvAddress.setView("Unit address: ", hcu.getUnitAddress().toString(), false);
    }

    void FillViewWithData(BaseUnit baseUnit) {
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
                // values are stored in Celsius
                Double targetVal = Double.parseDouble(upvTargetTemp.getPropertyValue());
                if(fahrenheit) {
                    targetVal = HeatingUtils_.fahrenheitToCelsius(targetVal);
                }
                hcu.setName(upvName.getPropertyValue());
                hcu.setTargetTemperature(targetVal);
                //update of target value should be sent here
            }
    }

    void showSaveButton(boolean v){
        bSave.setVisibility(v ? View.VISIBLE :View.GONE);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if(checkIfPropertyChanged())
                askForSave();
            else{
                super.onBackPressed();
            }
        }
        return false;
    }

    // Check if any property was changed by user
    // returns true if something changed, false otherwise
    boolean checkIfPropertyChanged() {
        Double targetVal = Double.parseDouble(upvTargetTemp.getPropertyValue());
        if(fahrenheit) {
            targetVal = HeatingUtils_.fahrenheitToCelsius(targetVal);
        }
        return !upvName.getPropertyValue().equals(unitToShow.getName()) ||
                !(targetVal == ((HeatingControlUnit)unitToShow).getTargetTemperature());
    }

    void askForSave(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to keep changes?")
                .setTitle("Property changed")
                .setCancelable(false)
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id){
                                // don't need view, but I have to pass it
                                View v = null;
                                saveChangedUnit(v);
                                UnitDetailActivity.this.onBackPressed();
                            }
                        }
                ).setNegativeButton("Don't save",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id){
                        UnitDetailActivity.this.onBackPressed();
                    }
                }).show();
    }

}
