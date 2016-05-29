package heating.control;

import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SupposeBackground;

import java.io.IOException;
import java.io.OutputStream;
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
    private int mHeatingPort = 8080;
    //lenovo ip
    private String mServerIp = "192.168.1.51";

    public void setServerIp(String mServerIp) {
        this.mServerIp = mServerIp;
    }

    public void setHeatingPort(int mHeatingPort) {
        this.mHeatingPort = mHeatingPort;
    }

    private boolean established;

    @SupposeBackground
    public void connectWithUnit(){
        // establishing connection
        try{
            InetAddress intendtAddress = InetAddress.getByName(mServerIp);
            mSocket = new Socket(intendtAddress, mHeatingPort);
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

    @SupposeBackground
    @Override
    public void sendData() {
        try {
            String msg = "Hello";
            OutputStream outputStream = null;
            if(mSocket!=null){
                outputStream = mSocket.getOutputStream();
            }
            else{
                Log.i("sendData()", "socket field is null!");
                return;
            }
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
