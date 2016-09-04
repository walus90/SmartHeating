package com.smartheting.smartheating;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultStringSet;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

import java.util.Set;

/**
 * Created by Wojtek on 2016-08-23.
 */
@SharedPref
public interface ChartPreferences {

    @DefaultStringSet(value={""}, keyRes = R.string.pref_data_type_to_show)
    Set<String> dataTypeToShow();

    @DefaultBoolean(value=false, keyRes = R.string.pref_show_target)
    boolean showTarget();

    @DefaultStringSet(value = {"No units available"}  ,keyRes = R.string.pref_units_on_chart)
    Set<String> unitsOnChart();
}
