package module.control;

/**
 * Created by Wojtek on 2016-04-30.
 * Base class for all units used in system, control and measure units
 */
public abstract class BaseUnit {
    private static int maxId=0;
    //unchanged id of unit
    protected int id;
    protected String name;
    public abstract void sendData();
}
