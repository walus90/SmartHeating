package com.smartheting.smartheating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity
public class StatisticsActivity extends AppCompatActivity {

    @ViewById(R.id.lineChart)
    LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_chart);
        //setContentView(chart);
        ArrayList<Entry> sampleEntries = new ArrayList<>();
        sampleEntries.add(new Entry(2.3f, 1));
        sampleEntries.add(new Entry(43.2f, 2));
        sampleEntries.add(new Entry(33.5f, 3));
        sampleEntries.add(new Entry(45f, 4));
        sampleEntries.add(new Entry(52.223f, 5));
        sampleEntries.add(new Entry(39.93f, 6));
        LineDataSet sampleData = new LineDataSet(sampleEntries, "some shity data");
        ArrayList<String> as = new ArrayList<>();
        as.add("nie");
        as.add("ogarniam");
        as.add("do");
        as.add("konca");
        as.add("5");
        as.add("6");
        as.add("7");
        LineData lineData = new LineData(as, sampleData);
        chart.setData(lineData);
        chart.invalidate();
    }
}
