package com.smartheting.smartheating;

import android.app.Activity;
import android.content.Intent;
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

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import heating.control.HeatingControlUnit;

@EActivity(R.layout.activity_units)
public class UnitsActivity extends Activity {

    @ViewById(R.id.lvUnits)
    ListView mUnitsLv;

    static final String UNIT_ID = "unit mId";
    static final String UNIT_NAME = "unit mName";
    static final String EDITABLE = "editable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void generateUnitList() {
        ArrayList<String> l = new ArrayList<String>();
        for(HeatingControlUnit u : MainActivity.sUnitsList) {
            l.add(u.getName());
        }
        ListAdapter listAdapter = new ArrayAdapter<String>(this, R.layout.activity_unit_overview, l);
        mUnitsLv.setAdapter(listAdapter);
        registerForContextMenu(mUnitsLv);
    }

    @ItemClick(R.id.lvUnits)
    public void onItemClick(int position) {
        Intent i = new Intent(UnitsActivity.this, UnitDetailActivity_.class);
        i.putExtra(UNIT_ID, Integer.toString(MainActivity.sUnitsList.get(position).getId()));
        i.putExtra(UNIT_NAME, MainActivity.sUnitsList.get(position).getName());
        i.putExtra(EDITABLE, false);
        startActivity(i);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        Log.i("onCreateContextMenu", "Entered");
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String unitName = (String) mUnitsLv.getAdapter().getItem(info.position);
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
                i.putExtra(UNIT_ID, Integer.toString(MainActivity.sUnitsList.get(position).getId()));
                i.putExtra(UNIT_NAME, MainActivity.sUnitsList.get(position).getName());
                i.putExtra(EDITABLE, true);
                startActivity(i);
            default:
                return super.onContextItemSelected(item);
        }
    }


}
