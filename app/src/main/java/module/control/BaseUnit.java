package module.control;

/**
 * Created by Wojtek on 2016-04-30.
 * Base class for all units used in system, control and measure units
 */
public abstract class BaseUnit implements java.io.Serializable{
    protected static int sMaxId =0;
    //unchanged mId of unit
    protected int mId;
    protected String mName;
}
