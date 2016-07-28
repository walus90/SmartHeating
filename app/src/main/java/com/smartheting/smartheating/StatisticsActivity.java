package com.smartheting.smartheating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.smartheating.model.HeatingData;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

@EActivity
public class StatisticsActivity extends AppCompatActivity {

    @ViewById(R.id.lineChart)
    LineChart chart;
    private Realm realm;
    private RealmConfiguration realmConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_chart);

        realmConfig = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        realm = Realm.getInstance(realmConfig);

        RealmResults<HeatingData> latestResults = realm.where(HeatingData.class).equalTo("unitId", 1).findAll();
        ArrayList<Entry> sampleEntries = new ArrayList<>();
        ArrayList<String> as = new ArrayList<>();
        for(int i=0; i<latestResults.size(); i++) {
            sampleEntries.add(new Entry(new Float(latestResults.get(i).getValue()), i));
            as.add(Integer.toString(i));
        }
        LineDataSet sampleData = new LineDataSet(sampleEntries, "Unit1");
        LineData lineData = new LineData(as, sampleData);
        chart.setData(lineData);
        chart.invalidate();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }
}
