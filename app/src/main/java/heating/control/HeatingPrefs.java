package heating.control;

import com.smartheting.smartheating.R;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by Wojtek on 2016-07-26.
 */

@SharedPref
public interface HeatingPrefs {

    @DefaultString(value = "//data//data//com.smartheting.smartheating//units", keyRes = R.string.pref_path_to_bin_units)
    String pathToBinUnits();

    @DefaultBoolean(value = false, keyRes = R.string.pref_connect_auto)
    boolean connectAuto();

}
