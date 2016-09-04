package com.smartheting.smartheating;

/*
Activity setting many users preferences
So far does not contains buttons to save, because it's too troublesome
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.androidannotations.annotations.AfterPreferences;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.PreferenceByKey;
import org.androidannotations.annotations.PreferenceChange;
import org.androidannotations.annotations.PreferenceScreen;

import java.io.File;
import java.io.IOException;

@EActivity
public class SettingsActivity extends PreferenceActivity {

    public static final String SETTINGS_NAME = "HeatingPrefs";

    public static  final String BIN_PATH_KEY = "Path to save binary units";
    @PreferenceByKey(R.string.pref_save_unit_address)
    Preference binPath;

    @PreferenceByKey(R.string.pref_connect_auto)
    CheckBoxPreference autoConnect;

    public static  final String TEMPERATURE_SCALE = "Scale of temperature";
    @PreferenceByKey(R.string.pref_chose_scale)
    ListPreference temperatureScale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getPreferenceManager().setSharedPreferencesName(SETTINGS_NAME);
        // Opening the layout
        addPreferencesFromResource(R.xml.settings);
    }

    @PreferenceChange(R.string.pref_save_unit_address)
    public void binPath(String newPath){
        File p = new File(newPath);
        if(!p.exists()){
            Toast.makeText(this, "File does not exist, I will create it...", Toast.LENGTH_LONG).show();
            try {
                p.createNewFile();
                Toast.makeText(this, "File created!", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(this, "Failed to create the file", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    @AfterPreferences
    void defaultAppPath(){
        String defAppDir = MainActivity_.getAppContext().getFilesDir().getPath();
        SharedPreferences.Editor editor = binPath.getEditor();
        editor.putString(BIN_PATH_KEY, defAppDir);
        editor.apply();
        binPath.setDefaultValue(defAppDir);
    }

}
