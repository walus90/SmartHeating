package com.smartheting.smartheating;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;

import heating.control.HeatingControlUnit;

@EActivity
public class UnitsActivity extends Activity {

    static String UNIT_ID = "unit id";
    static String UNIT_NAME = "unit name";
    static String EDITABLE = "editable";
    private ListView unitsLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_units);
        unitsLv = (ListView)findViewById(R.id.unitsListView);
        ArrayList<String> l = new ArrayList<String>();
        //ListAdapter listAdapter = new ArrayAdapter<HeatingControlUnit>(MainActivity.unitsList);
        for(HeatingControlUnit u : MainActivity.unitsList) {
            l.add(u.getName());
        }
        ListAdapter listAdapter = new ArrayAdapter<String>(this, R.layout.unit_overview, l);
        unitsLv.setAdapter(listAdapter);
        registerForContextMenu(unitsLv);

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        Log.i("onCreateContextMenu", "Entered");
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String unitName = (String) unitsLv.getAdapter().getItem(info.position);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.unit_context, menu);
        menu.setHeaderTitle(unitName);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.edit:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                int position = info.position;
                Intent i = new Intent(UnitsActivity.this, UnitDetailActivity_.class);
                i.putExtra(UNIT_ID, Integer.toString(MainActivity.unitsList.get(position).getId()));
                i.putExtra(UNIT_NAME, MainActivity.unitsList.get(position).getName());
                i.putExtra(EDITABLE, true);
                startActivity(i);
            default:
                return super.onContextItemSelected(item);
        }
    }


}
