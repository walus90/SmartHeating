package com.smartheting.smartheating;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;


import heating.control.HeatingControlUnit;
import heating.control.HeatingPrefs_;
import heating.control.LoadUnitBinary;
import heating.control.SaveUnitBinary;
import module.control.IUnitLoader;

@EActivity(R.layout.activity_save_units)
public class SaveUnitsActivity extends AppCompatActivity {

    @Bean
    LoadUnitBinary loader;

    SaveUnitBinary saver;
    @ViewById EditText etSaveUnitAddress;
    @ViewById Button bSaveUnitsBin;
    @ViewById Button bDeleteUnitsBin;
    @Pref HeatingPrefs_ heatingPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void createSaver(){
        saver = new SaveUnitBinary();
        saver.setContext(this);
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
//        SharedPreferences sp = getSharedPreferences(SettingsActivity_.SETTINGS_NAME, Context.MODE_PRIVATE);
//        String pathToBin = sp.getString(SettingsActivity_.BIN_PATH_KEY, "");
        String pathToBin = heatingPrefs.pathToBinUnits().get();
        Log.i(SettingsActivity.class.toString(), "Bin path: " + pathToBin);
        etSaveUnitAddress.setText(pathToBin);
        saver.setPath(pathToBin);
    }

    @Click
    public void bSaveUnitsBin(View v){
        // path is set in putPath in @AfterViews
        for(int i=0; i<UnitsList.getUnitList().size(); i++) {
            saver.saveUnit((HeatingControlUnit)UnitsList.getUnitList().get(i));
            Toast.makeText(this, "Unit with id = " + i + " saved!", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "All units saved!", Toast.LENGTH_SHORT).show();
    }

    @Click
    public void bDeleteUnitsBin(View v){
        confirmRemoving();
    }

    private void confirmRemoving() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?")
                .setTitle("Delete binary units")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id){
                                loader.deleteAllBinariesUnits();
                                Toast.makeText(SaveUnitsActivity.this, "All binary units removed", Toast.LENGTH_SHORT).show();
                            }
                        }
                ).setNegativeButton("No",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id){
                    }
                }).show();
    }

}
