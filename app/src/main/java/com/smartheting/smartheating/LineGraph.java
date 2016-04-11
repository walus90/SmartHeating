package com.smartheting.smartheating;

import android.content.Context;
import android.content.Intent;

import org.achartengine.ChartFactory;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

/**
 * Created by Wojtek on 2016-04-11.
 */
public class LineGraph {
    public Intent getIntent(Context context){
        int[] x = {0,1,2,3,4,5,6,7,8,9};
        int[] y = {20,21,24,24,24,25,28,27,12,9};

        TimeSeries series = new TimeSeries("Sample line");
        for(int i =0; i<x.length; i++){
            series.add(x[i], y[i]);
        }
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series);

        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        XYSeriesRenderer seriesRenderer = new XYSeriesRenderer();
        renderer.addSeriesRenderer(seriesRenderer);

        Intent intent  = ChartFactory.getLineChartIntent(context, dataset, renderer, "sample graph");
        return  intent;
    }
}
