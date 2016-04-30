package heating.control;

import module.control.ControlUnit;

/**
 * Created by Wojtek on 2016-04-04.
 */

// TODO create children: CentralUnit and LocalUnit
public class HeatingControlUnit extends ControlUnit{
    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; };

    // indicates if there are specific data for heating unit, maybe only for Local
    private boolean advice;
    private boolean valveOpen;
    private int currentTemperature;
    private int targetTemperature;
    private int environmentTemperature;

    public HeatingControlUnit(String name){
        this.name = name;
    }
    public int getId() { return id; }

}
