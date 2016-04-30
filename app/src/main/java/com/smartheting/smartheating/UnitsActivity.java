package com.smartheting.smartheating;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;

import heating.control.HeatingControlUnit;

@EActivity
public class UnitsActivity extends AppCompatActivity {

    static String UNIT_ID = "unit id";
    static String UNIT_NAME = "unit name";
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
        ListAdapter listAdapter = new ArrayAdapter<String>(this, R.layout.unit_overview, l);
        unitsLv.setAdapter(listAdapter);

        unitsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //open activity with detailed data
                //TextView tv = (TextView) unitsLv.getItemAtPosition(position);
                String tv = (String) unitsLv.getItemAtPosition(position);

                Intent i = new Intent(UnitsActivity.this, UnitDetailActivity_.class);
                // better to pass int or string ?
                i.putExtra(UNIT_ID, Integer.toString(MainActivity.unitsList.get(position).getId()));
                i.putExtra(UNIT_NAME, MainActivity.unitsList.get(position).getName());
                startActivity(i);
            }
        });
    }
}
