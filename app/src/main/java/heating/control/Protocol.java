package heating.control;
import org.androidannotations.annotations.EBean;

import java.net.InetSocketAddress;

/**
 * Created by Wojtek on 2016-04-03.
 * Based on Santiago Pasqual master thesis
 */
@EBean
public class Protocol {

    class Header{
        // type of fields are not sure
        int mFrameId;
        int mOrigin;
        int Length;
        int mDestination;
        int mPort;
    }
    class Data{
        // Data differs, depending on frame type, maybe byte[] to decode?
        int mCmd;
        int mDataSize;
    }
}
