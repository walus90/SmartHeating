package com.smartheting.smartheating;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.smartheating.model.HeatingData;
import com.smartheating.model.HumidityData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.IntArrayRes;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmResults;


@EActivity(R.layout.line_chart)
public class StatisticsActivity extends AppCompatActivity {

    @IntArrayRes(R.array.chart_color)
    int[] colourList;
    @StringRes(R.string.pref_units_on_chart)
    String unitsToPrintKey;

    @ViewById(R.id.lineChart)
    LineChart chart;
    @ViewById
    Button bChartOptions;
    @ViewById
    Button bRefresh;
    @ViewById
    TextView tvDataType;

    Realm realm;
    LineData lineData;
    List<ILineDataSet> dataToView;
    ArrayList<Integer> unitsIdToView;
    ArrayList<String> unitsNameToView;
    ArrayList<Integer> colorsToView;

    String dataType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void setChart() {
        SharedPreferences sp = getSharedPreferences(ChartOptionsActivity_.PREF_NAME, Context.MODE_PRIVATE);
        dataType = sp.getString(ChartOptionsActivity_.DATA_TYPE_TO_SHOW, "Temperature");

        realm = Realm.getDefaultInstance();
        Utils.init(this);
        dataToView = new ArrayList<ILineDataSet>();
        unitsIdToView = new ArrayList<>();
        colorsToView = new ArrayList<>();
        unitsNameToView = new ArrayList<>();

        prepareChart();
        chart.invalidate();
    }

    private void prepareChart() {
        SharedPreferences sp = getSharedPreferences(ChartOptionsActivity_.PREF_NAME, Context.MODE_PRIVATE);
        Set<String> namesSet = sp.getStringSet(unitsToPrintKey, null);
        unitsIdToView.clear();
        unitsNameToView.clear();
        colorsToView.clear();
        if(namesSet==null)
            return;
        for(String s : namesSet) {
            Integer uId = Integer.valueOf(s);
            unitsIdToView.add(uId);
            unitsNameToView.add(UnitsList.getUnitById(uId).getName());
            // should create enum, array or something
            Log.i(StatisticsActivity_.class.toString(), "Data type = " + dataType);
            if(dataType.equals("Temperature"))
                prepareHeatingDataSetsForUnit(uId);
            else if(dataType.equals("Humidity"))
                prepareHumidityDataSetsForUnit(uId);
            else{
                Toast.makeText(this, "Invalid data type!", Toast.LENGTH_SHORT).show();
            }
            int color = colourList[uId];
            colorsToView.add(color);
        }
        lineData = new LineData(dataToView);
        putDataType(dataType);
        prepareLegend();
        prepareAxises();
        chart.setData(lineData);
    }

    private void putDataType(String dataType) {
        tvDataType.setText(dataType);
    }

    private void prepareLegend() {
        //should be clear what are we showing
        Legend legend =  chart.getLegend();
        // probably cuts a bit of chart at the bottom
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        legend.setTextSize(12f);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setCustom(colorsToView, unitsNameToView);
    }

    private void prepareAxises(){
        YAxis yAxis = chart.getAxisLeft();
        XAxis xAxis = chart.getXAxis();
        yAxis.setEnabled(true);
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
    }

    void prepareHumidityDataSetsForUnit(Integer unitId) {
        LineDataSet unit = getHumidityDataForChart(unitId);
        unit.setColor(colourList[unitId]);
        dataToView.add(unit);
    }

    void prepareHeatingDataSetsForUnit(int unitId){
        LineDataSet unit = getHeatingDataForChart(unitId);
        unit.setColor(colourList[unitId]);
        String key = getResources().getString(R.string.pref_show_target);
        SharedPreferences  sp = getSharedPreferences(ChartOptionsActivity_.PREF_NAME, Context.MODE_PRIVATE);
        boolean showTargetValue = sp.getBoolean(key, false);
        Log.i(StatisticsActivity.class.toString(), "chartOptions.showTarget().get() = " + showTargetValue);
        dataToView.add(unit);
        if(showTargetValue) {
            LineDataSet target = getTargetData(unitId);
            target.setColor(colourList[unitId]);
            target.enableDashedLine(5.0f, 5.0f, 0.0f);
            dataToView.add(target);
        }
    }


    private LineDataSet getHeatingDataForChart(int unitId) {
        // need to rewrite axis formater for line chart, this one is for bar
        //chart.getAxisLeft().setValueFormatter(new DayAxisValueFormatter());
        RealmResults<HeatingData> latestResults = realm.where(HeatingData.class).equalTo("unitId", unitId).findAll();
        ArrayList<Entry> sampleEntries = new ArrayList<>();
        ArrayList<String> as = new ArrayList<>();
        for(int i=0; i<latestResults.size(); i++) {
            sampleEntries.add(new Entry(latestResults.get(i).getTime().getHours(), new Float(latestResults.get(i).getValue())));
            as.add(Integer.toString(i));
        }
        LineDataSet sampleData = new LineDataSet(sampleEntries, "Unit " + unitId);
        return sampleData;
    }

    private LineDataSet getHumidityDataForChart(int unitId) {
        // need to rewrite axis formater for line chart, this one is for bar
        //chart.getAxisLeft().setValueFormatter(new DayAxisValueFormatter());
        RealmResults<HumidityData> latestResults = realm.where(HumidityData.class).equalTo("unitId", unitId).findAll();
        ArrayList<Entry> sampleEntries = new ArrayList<>();
        ArrayList<String> as = new ArrayList<>();
        for(int i=0; i<latestResults.size(); i++) {
            sampleEntries.add(new Entry(latestResults.get(i).getTime().getHours(), new Float(latestResults.get(i).getValue())));
            as.add(Integer.toString(i));
        }
        LineDataSet sampleData = new LineDataSet(sampleEntries, "Unit " + unitId);
        return sampleData;
    }

    private LineDataSet getTargetData(int unitId){
        RealmResults<HeatingData> latestResults = realm.where(HeatingData.class).equalTo("unitId", unitId).findAll();
        ArrayList<Entry> sampleEntries = new ArrayList<>();
        ArrayList<String> as = new ArrayList<>();
        for(int i=0; i<latestResults.size(); i++) {
            sampleEntries.add(new Entry(latestResults.get(i).getTime().getHours(), new Float(latestResults.get(i).getTargetTemperature())));
            as.add(Integer.toString(i));
        }
        LineDataSet sampleData = new LineDataSet(sampleEntries, "Unit " + unitId + " target");
        return sampleData;
    }

    @Click
    public void bChartOptions(View v){
        Intent chartOptionsIntent = new Intent(this, ChartOptionsActivity_.class);
        startActivity(chartOptionsIntent);
    }

    @Click
    public void bRefresh(View v){
        chart.clear();
        dataToView.clear();
        SharedPreferences sp = getSharedPreferences(ChartOptionsActivity_.PREF_NAME, Context.MODE_PRIVATE);
        dataType = sp.getString(ChartOptionsActivity_.DATA_TYPE_TO_SHOW, "Temperature");
        putDataType(dataType);
        prepareChart();
        prepareLegend();
        chart.invalidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }


    @Override
    public void onRestart(){
        super.onRestart();
        // invalidate changed preferences
        chart.clear();
        dataToView.clear();
        SharedPreferences sp = getSharedPreferences(ChartOptionsActivity_.PREF_NAME, Context.MODE_PRIVATE);
        dataType = sp.getString(ChartOptionsActivity_.DATA_TYPE_TO_SHOW, "Temperature");
        putDataType(dataType);
        prepareChart();
        prepareLegend();
        chart.invalidate();
    }
}
