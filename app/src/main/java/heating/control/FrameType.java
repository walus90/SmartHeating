package heating.control;

/**
 * Created by Wojtek on 2016-07-23.
 */
public enum FrameType {
    SEND_PARAMETERS(1),
    UNITS_LIST(2);

    private final int number;
    FrameType(int i) {
        number=i;
    }

    public int getNumber(){
        return number;
    }
}
