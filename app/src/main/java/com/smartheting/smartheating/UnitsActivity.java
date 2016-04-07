package com.smartheting.smartheating;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class UnitsActivity extends AppCompatActivity {

    private ListView unitsLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_units);
        unitsLv = (ListView)findViewById(R.id.unitsListView);
        ArrayList<String> l = new ArrayList<String>();
        //ListAdapter listAdapter = new ArrayAdapter<HeatingControlUnit>(MainActivity.unitsList);
        for(HeatingControlUnit u : MainActivity.unitsList)
            l.add(u.getName());
        // TODO find how to populate layout
        ListAdapter listAdapter = new ArrayAdapter<String>(this, R.layout.unit_overview, l);
        unitsLv.setAdapter(listAdapter);
    }
}
