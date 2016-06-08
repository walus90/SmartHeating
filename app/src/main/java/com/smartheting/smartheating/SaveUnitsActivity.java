package com.smartheting.smartheating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import heating.control.SaveUnitBinary;

@EActivity
public class SaveUnitsActivity extends AppCompatActivity {

    SaveUnitBinary saver;
    @ViewById EditText etSaveUnitAdress;
    @ViewById Button bSaveUnits;

    String imionaUnitow = new String("");
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
        for(int i=0; i<MainActivity_.sUnitsList.size(); i++) {
            saver.saveUnit(MainActivity_.sUnitsList.get(i));
        }

    }
}
