package heating.control;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;

import com.smartheting.smartheating.MainActivity;
import com.smartheting.smartheating.UnitsList;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.SupposeBackground;

import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;


import module.control.IDataHandler;

/**
 * Created by Wojtek on 2016-05-17.
 */

@EBean(scope = EBean.Scope.Singleton)
public class ConnectionHandler implements IDataHandler {

    private Protocol mProtocol;
    private Socket mSocket;

    // temporary hardcoded values
    private static int mHeatingPort = 8080;
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

    /*
    Will have to mock, because of unknown implementation of Spanish
     */
    public void sendProtocol(Protocol p){

    }


    @Override
    public byte[] colectData() {
        // need to do other operations, with decoding byte array
        return sampleAddresses();
    }

    /**
     * fake implementation to simulate sending units addresses
     * @return byte array of IP addresses
     */
    private byte[] sampleAddresses() {
        // a1 - central unit cause it's first in array
        InetAddress a1 = null, a2 = null, a3 = null;
        try {
            a1 = InetAddress.getByName("127.0.0.1");
            a2 = InetAddress.getByName("127.0.0.2");
            a3 = InetAddress.getByName("127.0.0.3");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        byte[] res = concatenateArrays(a1.getAddress(), a2.getAddress());
        res = concatenateArrays(res, a3.getAddress());
        return res;
    }

    @SupposeBackground
    @Override
    public void sendData(byte[] msg) {
        try {
            OutputStream outputStream = null;
            if(mSocket!=null){
                outputStream = mSocket.getOutputStream();
            }
            else{
                Log.i("sendData()", "socket field is null!");
                return;
            }
            // not sure about length, because String was here before
            outputStream.write(msg, 0, msg.length);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Background
    public void requestUnitsAdresses(){
        try {
            DatagramSocket ds = new DatagramSocket();
            ds.setBroadcast(true);
            // according to instructions, empty message is sent to get list of addresses
            byte[] emptyData = new byte[0];
            int port = mHeatingPort;
            DatagramPacket datagramPacket = new DatagramPacket(emptyData, emptyData.length, getBroadcastAddress(), port);
            ds.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // first prototype of receiving list of units,
    // should be implemented as a method from inferface for more abstraction
    // need to return void to use annotation, probably use Async Task
    @Background
    public void receiveUnitList(){
        ArrayList<InetAddress> listOfUnits=null;
        try{
            // length is guessed, but probably big enough
            int arraySize = 2048;
            byte[] message = new byte[arraySize];
            DatagramPacket datagramPacket = new DatagramPacket(message, message.length);
            DatagramSocket datagramSocket = new DatagramSocket(mHeatingPort);
            datagramSocket.receive(datagramPacket);
            if(checkUnitListPackage(datagramPacket)){
                InetAddress centralUnitAdress = datagramPacket.getAddress();
                listOfUnits.add(centralUnitAdress);
                listOfUnits.addAll(extractUnitsFromProtocol(datagramPacket));
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
        UnitsList.sInetAddresses = listOfUnits;
    }

    private byte[] concatenateArrays(byte[] a, byte[] b) {
        int aLen = a.length;
        int bLen = b.length;
        byte[] c= new byte[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    public class ReceiveUnitListAsyncTask extends AsyncTask<Void,Void, byte[]>{
        @Override
        protected byte[] doInBackground(Void... params) {
            // length is guessed, but probably big enough, update: probably not needed
            // int arraySize = 2048;
            byte[] message = null;
            byte[] res = null;
            try{
                // close socket?
                DatagramPacket datagramPacket = new DatagramPacket(message, message.length);
                DatagramSocket datagramSocket = new DatagramSocket(mHeatingPort);
                datagramSocket.receive(datagramPacket);
                if(checkUnitListPackage(datagramPacket)) {
                    //get address of central unit in byte[]
                    byte[] centralUnitBytes = datagramPacket.getAddress().getAddress();
                    message = datagramPacket.getData();
                    res = concatenateArrays(centralUnitBytes, message);
                }
                return res;
            }  catch (IOException e) {
                e.printStackTrace();
            }
            return res;
        }
    }

    /*
    Extracting units, packet should be checked before with checkUnitListPackage
     */
    public ArrayList<InetAddress> extractUnitsFromProtocol(DatagramPacket datagramPacket) {
        ArrayList<InetAddress> locals = null;
        byte[] rawData = datagramPacket.getData();
        // length of each field in bytes, I suspect this values
        int idSize = 1;
        int originSize = 4;
        int lenghtSize = 1;
        int destinationSize = 4;
        int portSize = 2;
        int totalHeaderSize =idSize + originSize + lenghtSize + destinationSize + portSize;
        int numerOfLocals = rawData[originSize+lenghtSize]/4;
        InetAddress address;
        byte[] bytesOfAdress;
        for(int i = 0; i<numerOfLocals; i++){
            int beginning = totalHeaderSize+i*4, end = totalHeaderSize+i*4+4;
            bytesOfAdress = Arrays.copyOfRange(rawData, beginning, end);
            try {
                address = Inet4Address.getByAddress(bytesOfAdress);
                locals.add(address);
            } catch (UnknownHostException e) {
                Log.e("extractUnitsFromProtoco", e.getMessage());
                e.printStackTrace();
            }
        }
        return locals;
    }

    public ArrayList<InetAddress> extractUnitsFromBinary(byte[] rawAddresses) {
        ArrayList<InetAddress> allUnits = null;
        // length of each field in bytes, I suspect this values
        int idSize = 1;
        int originSize = 4;
        int lenghtSize = 1;
        int destinationSize = 4;
        int portSize = 2;
        int totalHeaderSize =idSize + originSize + lenghtSize + destinationSize + portSize;
        int numerOfLocals = rawAddresses[originSize+lenghtSize]/4;
        InetAddress address;
        byte[] bytesOfAdress;
        for(int i = 0; i<numerOfLocals; i++){
            int beginning = totalHeaderSize+i*4, end = totalHeaderSize+i*4+4;
            bytesOfAdress = Arrays.copyOfRange(rawAddresses, beginning, end);
            try {
                address = Inet4Address.getByAddress(bytesOfAdress);
                allUnits.add(address);
            } catch (UnknownHostException e) {
                Log.e("extractUnitsFromProtoco", e.getMessage());
                e.printStackTrace();
            }
        }
        return allUnits;
    }

    /**
    verifies if list of units have proper data
    I assume, all information of "frame" are in data part of packet
    sequence of this information is in master thesis
     */
    private boolean checkUnitListPackage(DatagramPacket datagramPacket) {
        if(datagramPacket.getLength()==0)
            return false;
        byte[] rawData = datagramPacket.getData();
        if(rawData[0] != FrameType.UNITS_LIST.getNumber())
            return false;
        // to suppress error
        return true;
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
        if(mSocket!=null)
            mSocket.close();
        super.finalize();
    }
}
