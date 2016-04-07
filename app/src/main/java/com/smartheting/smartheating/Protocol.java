package com.smartheting.smartheating;
import java.net.InetSocketAddress;

/**
 * Created by Wojtek on 2016-04-03.
 */
public class Protocol {
    class Header{
        // type of fields are not sure
        private int key;
        private int cmd;
        private int pw;
        private int dataSize;
        private boolean parity;
    }
    class Command{
        private int _cmd;
        private int _dataSize;
        private InetSocketAddress sourceSocket;
        private InetSocketAddress destinationSocket;

    }
}
