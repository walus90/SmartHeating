package com.smartheting.smartheating;

/*
Activity setting many users preferences
So far does not contains buttons to save, because it's too troublesome
 */

import android.content.SharedPreferences;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.PreferenceByKey;
import org.androidannotations.annotations.PreferenceClick;
import org.androidannotations.annotations.PreferenceScreen;

import heating.control.SaveUnitBinary;
import module.control.UnitSaver;

@PreferenceScreen(R.xml.settings)
@EActivity
public class SettingsActivity extends PreferenceActivity {

    @PreferenceByKey(R.string.pref_path_to_bin_units)
    Preference binPath;

    @PreferenceByKey(R.string.pref_connect_auto)
    CheckBoxPreference autoConnect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
