package heating.control;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

import module.control.ConnectionEstablisher;

/**
 * Created by Wojtek on 2016-05-03.
 * I change former assumptions, I will connect to existing WiFi network
 */

@EBean
public class HeatingSystemConnector implements ConnectionEstablisher {

    // need to be set up at first run, also possible to change them
    String mNtworkSSID = "Waliszek";
    String mNetworkPass = "94laskowicka111";
    Context mContext;

    public String gemNtworkSSID() {
        return mNtworkSSID;
    }

    public void setNtworkSSID(String mNtworkSSID) {
        this.mNtworkSSID = mNtworkSSID;
    }

    public void setNetworkPass(String mNetworkPass) {
        this.mNetworkPass = mNetworkPass;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public HeatingSystemConnector(Context context) {
        this.mNtworkSSID = mNtworkSSID;
        this.mNetworkPass = mNetworkPass;
        this.mContext = context;
    }

    @Override
    public void establishConnection() {
        connectToWifi();
    }

    @Background
    public void connectToWifi(){
        if(mContext==null){
            Log.i("connectToWifi()", "you have to set Context first!");
            return;
        }
        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + mNtworkSSID + "\"";
        //I assume WPE connection
        conf.wepKeys[0] = "\"" + mNetworkPass + "\"";
        conf.wepTxKeyIndex = 0;
        //not sure about passwords
        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);

        WifiManager wifiManager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
        wifiManager.addNetwork(conf);

        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for( WifiConfiguration i : list ) {
            if(i.SSID != null && i.SSID.equals("\"" + mNtworkSSID + "\"")) {
                wifiManager.disconnect();
                wifiManager.enableNetwork(i.networkId, true);
                wifiManager.reconnect();
                Toast.makeText(this.mContext, "connected to " + mNtworkSSID, Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    public void requestUnitsAdresses(){
        // Hack Prevent crash (sending should be done using an async task), not sure if needed
        StrictMode.ThreadPolicy policy = new   StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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

        WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcp = wifi.getDhcpInfo();
        // handle null somehow

        int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
        byte[] quads = new byte[4];
        for (int k = 0; k < 4; k++)
            quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
        return InetAddress.getByAddress(quads);
    }

}
