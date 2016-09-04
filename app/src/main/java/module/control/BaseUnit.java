package module.control;

import org.androidannotations.annotations.EBean;

/**
 * Created by Wojtek on 2016-04-30.
 * Base class for all units used in system, control and measure units
 */
@EBean
public abstract class BaseUnit implements java.io.Serializable{
    static final long serialVersionUID = 999201608131237999L;

    protected static int sMaxId =0;
    //unchanged mId of unit
    final protected int mId = sMaxId++;
    protected String mName;

    public String getName() {
        return mName;
    }

    public int getId() {
        return mId;
    }
}
