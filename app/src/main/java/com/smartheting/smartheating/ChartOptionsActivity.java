package com.smartheting.smartheating;

import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.androidannotations.annotations.AfterPreferences;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.PreferenceByKey;
import org.androidannotations.annotations.PreferenceChange;
import org.androidannotations.annotations.PreferenceScreen;

import java.util.ArrayList;
import java.util.HashSet;

import module.control.BaseUnit;

@EActivity
public class ChartOptionsActivity extends PreferenceActivity {

    // both string are the same, but needed for ChartPreferences class
    public static final String PREF_NAME = "ChartPreferences";

    public static final String DATA_TYPE_TO_SHOW = "Data type to show on chart";
    @PreferenceByKey(R.string.pref_data_type_to_show)
    ListPreference dataTypeToShow;

    @PreferenceByKey(R.string.pref_show_target)
    CheckBoxPreference showTarget;

    public static final String PREF_UNITS_ON_CHART = "Units to show on chart";
    @PreferenceByKey(R.string.pref_units_on_chart)
    MultiSelectListPreference unitsOnChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Using your PREF_NAME values
        this.getPreferenceManager().setSharedPreferencesName(PREF_NAME);
        // Opening the layout
        addPreferencesFromResource(R.xml.chart_settings);
    }

    @PreferenceChange(R.string.pref_show_target)
    void setShowTarget(Preference preference){
        Log.i("ChartOptionsActivity", "preference changed");
        Log.i("ChartOptionsActivity",
                String.valueOf(preference.getPreferenceManager().getSharedPreferences().getBoolean(preference.getKey(), false)));
    }

    @AfterPreferences
    void setViews(){
        int size = UnitsList.getUnitList().size();
        String[] unitsNames = new String[size];
        String[] unitsNums = new String[size];
        for(int i =0; i<size; i++) {
            unitsNames[i] = UnitsList.getUnitList().get(i).getName();
            unitsNums[i] = Integer.toString(UnitsList.getUnitList().get(i).getId());
        }
        unitsOnChart.setEntryValues(unitsNums);
        unitsOnChart.setEntries(unitsNames);
    }
}
