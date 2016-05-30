package com.smartheting.smartheating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.ViewById;

import heating.control.SaveUnitBinary;
import module.control.BaseUnit;

@EBean
public class SaveUnitsActivity extends AppCompatActivity {

    SaveUnitBinary saver;
    @ViewById EditText etSaveUnitAdress;
    @ViewById
    Button bSaveUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_units);
        saver = new SaveUnitBinary();
        saver.setContext(this);
        // looks perfect for data binding
        etSaveUnitAdress.setText(this.getFilesDir().toString());
    }

    @Click
    public void bSaveUnits(View v){
        for(BaseUnit unit : MainActivity_.sUnitsList){
            saver.saveUnit(unit);
        }
    }
}
