package heating.control;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

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
    //looks like I have to add context
    Context mContext;

    public HeatingSystemConnector(Context context) {
        //this.mNtworkSSID = mNtworkSSID;
        //this.mNetworkPass = mNetworkPass;
        this.mContext = context;
    }

    @Override
    public void establishConnection() {
        connectToWifi();
    }

    @Background
    public void connectToWifi(){
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
}
