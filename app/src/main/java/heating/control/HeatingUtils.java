package heating.control;

import org.androidannotations.annotations.EBean;

/**
 * Created by Wojtek on 2016-08-19.
 */
@EBean
public class HeatingUtils {
    public static double celsiusToFahrenheit(double celsiusTemp){
        return (celsiusTemp*9)/5+32;
    }
    public static double fahrenheitToCelsius(double farTemp){
        return (5.0/9.0)*(farTemp-32);
    }
}
