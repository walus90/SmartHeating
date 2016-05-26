package heating.control;

import android.widget.Toast;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import module.control.DataHandler;

/**
 * Created by Wojtek on 2016-05-17.
 */

@EBean
public class ConnectionHandler implements DataHandler{

//    @Bean(HeatingDataHandler.class)
//    HeatingDataHandler heatingDataHandler;

    private Protocol mProtocol;
    private Socket mSocket;
    private static final int HEATINGPORT = 5000;
    //j5 ip
    private static final String SERVER_IP = "10.175.229.241";

    private boolean established;

    public ConnectionHandler(){
        // establishing connection
        try{
            InetAddress intendtAddress = InetAddress.getByName(SERVER_IP);
            mSocket = new Socket(intendtAddress, HEATINGPORT);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendProtocol(Protocol p){

    }


    @Override
    public void colectData() {

    }

    @Background
    @Override
    public void sendData() {
        try {
            String msg = "Hello";
            OutputStream outputStream = null;
            outputStream = mSocket.getOutputStream();
            outputStream.write(msg.getBytes(), 0, msg.length());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        mSocket.close();
        super.finalize();
    }
}
