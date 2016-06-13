package com.smartheting.smartheating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import heating.control.ConnectionHandler;

@EActivity
public class CheckConnectionActivity extends AppCompatActivity {

    @ViewById(R.id.bConnect)
    Button bConnect;
    @ViewById(R.id.etIp)
    EditText etIp;
    @ViewById(R.id.etPort)
    EditText etPort;

    ConnectionHandler mConnectionHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_connection);
    }
    @Click(R.id.bConnect)
    public void connectToWifi(View v){
        MainActivity.sHeatingSystemConnector.setmContext(this);
        MainActivity.sHeatingSystemConnector.establishConnection();
    }

    @Background
    @Click
    public void bSendHello(View v){
        mConnectionHandler = new ConnectionHandler();
        mConnectionHandler.setServerIp(this.etIp.getText().toString());
        mConnectionHandler.setHeatingPort(Integer.parseInt(etPort.getText().toString()));
        mConnectionHandler.connectWithUnit();
        mConnectionHandler.sendData();
    }
}