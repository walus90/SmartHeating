package heating.control;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.smartheting.smartheating.MainActivity;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SupposeBackground;

import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import module.control.DataHandler;

/**
 * Created by Wojtek on 2016-05-17.
 */

@EBean
public class ConnectionHandler implements DataHandler{

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

    @Background
    public void requestUnitsAdresses(){
        // Hack Prevent crash (sending should be done using an async task), not sure if needed
        //StrictMode.ThreadPolicy policy = new   StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
        try {
            DatagramSocket ds = new DatagramSocket();
            ds.setBroadcast(true);
            // according to instructions, empty message is sent to get list of adresses
            byte[] emptyData = {0};
            int port = 8080;
            DatagramPacket datagramPacket = new DatagramPacket(emptyData, emptyData.length, getBroadcastAddress(), port);
            ds.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    InetAddress getBroadcastAddress() throws IOException {

        //todo handle null value of getAppContext
        WifiManager wifi = (WifiManager) MainActivity.getAppContext().getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcp = wifi.getDhcpInfo();
        // handle null somehow

        int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
        byte[] quads = new byte[4];
        for (int k = 0; k < 4; k++)
            quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
        return InetAddress.getByAddress(quads);
    }

    @Override
    protected void finalize() throws Throwable {
        mSocket.close();
        super.finalize();
    }
}
