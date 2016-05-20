package heating.control;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Wojtek on 2016-05-17.
 */

@EBean
public class ConnectionHandler {

    @Bean
    HeatingSystemConnector heatingSystemConnector;

    private Protocol mProtocol;
    private Socket mSocket;
    private static final int SERVERPORT = 5000;
    private static final String SERVER_IP = "10.0.2.2";

    private boolean established;

    public void sendProtocol(Protocol p){
        try{
            InetAddress intendtAddress = InetAddress.getByName(SERVER_IP);
            mSocket = new Socket(intendtAddress, SERVERPORT);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





}
