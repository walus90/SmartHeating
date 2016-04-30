package module.control;

/**
 * Created by Wojtek on 2016-04-30.
 */
public abstract class ControlUnit {
    private static int maxId=0;
    //unchanged id of unit, think should be unique in the world
    protected int id;
    // Custom name, editable by user, change to comment ?
    protected String name;
}
