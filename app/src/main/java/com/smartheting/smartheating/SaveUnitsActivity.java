package com.smartheting.smartheating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;


import heating.control.HeatingControlUnit;
import heating.control.HeatingPrefs_;
import heating.control.SaveUnitBinary;

@EActivity(R.layout.activity_save_units)
public class SaveUnitsActivity extends AppCompatActivity {

    SaveUnitBinary saver;
    @ViewById EditText etSaveUnitAddress;
    @ViewById Button bSaveUnits_depricated;
    @Pref HeatingPrefs_ prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void createSaver(){
        saver = new SaveUnitBinary();
        saver.setContext(this);
        saver.setPath(prefs.pathToBinUnits().get());
        putPath();
        etSaveUnitAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //TODO copy files
                    Toast.makeText(SaveUnitsActivity.this, "Units left in old location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void putPath(){
        String pathToBin = prefs.pathToBinUnits().get();
        etSaveUnitAddress.setText(pathToBin);
    }

    @Click
    public void bSaveUnits_depricated(View v){
        for(int i=0; i<UnitsList.getUnitList().size(); i++) {
            saver.saveUnit((HeatingControlUnit)UnitsList.getUnitList().get(i));
            Toast.makeText(this, "Unit with id = " + i + " saved!", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "All units saved!", Toast.LENGTH_SHORT).show();
    }

}
