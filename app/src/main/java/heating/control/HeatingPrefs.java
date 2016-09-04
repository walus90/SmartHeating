package heating.control;

import com.smartheting.smartheating.R;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.DefaultStringSet;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

import java.util.Set;

/**
 * Created by Wojtek on 2016-07-26.
 */

@SharedPref(value=SharedPref.Scope.UNIQUE)
public interface HeatingPrefs {

    @DefaultString(value = "/data/data/com.smartheting.smartheating/files", keyRes = R.string.pref_save_unit_address)
    String pathToBinUnits();

    @DefaultBoolean(value = false, keyRes = R.string.pref_connect_auto)
    boolean connectAuto();

    @DefaultStringSet(value = {"Celsius", "Fahrenheit"}, keyRes = R.string.pref_chose_scale)
    Set<String> temperatureScale();

}
