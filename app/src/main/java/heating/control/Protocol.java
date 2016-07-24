package heating.control;
import java.net.InetSocketAddress;

/**
 * Created by Wojtek on 2016-04-03.
 */
public class Protocol {

    class Header{
        // type of fields are not sure
        private int mKey;
        private int mCmd;
        private int mPw;
        private int mDataSize;
        private boolean mParity;
    }
    class Command{
        private int mCmd;
        private int mDataSize;
        private InetSocketAddress mSurceSocket;
        private InetSocketAddress mDestinationSocket;
    }
}
