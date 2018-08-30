package com.ecru.infographic;

import android.app.Activity;
import android.util.Log;

import com.ecru.data.GetDataValues;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

import org.json.JSONException;

/**
 * Created by Ayman on 06/12/2015.
 */
public class ExportsGraph {

    Activity activity;
    LineChart lineChart;
    GetDataValues dataValues;



    /**
     * Constructor for setting LineGraph
     * @param activity  context of graph
     * @param dataValues data for graph
     */
    public ExportsGraph(Activity activity, GetDataValues dataValues) {
        this.activity = activity;
        this.dataValues = dataValues;
        this.lineChart = (LineChart) activity.findViewById(R.id.exportLineChart);

        //get data for graph
        try {
            lineChart.setData(dataValues.exportsChart());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //set up the table
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setDrawGridBackground(true);
        Log.d("Chart focus", ""+lineChart.hasFocus());
        lineChart.setDescription("");

        //alter X axis
        XAxis x = lineChart.getXAxis();
        x.setDrawAxisLine(true);
        x.setDrawLabels(true);
        x.setEnabled(true);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setLabelsToSkip(0);
        //alter Yaxis
        YAxis y = lineChart.getAxisLeft();
        y.setDrawAxisLine(true);
        y.setAxisMaxValue(50);
        y.setAxisMinValue(30f);
        y.setDrawLabels(true);
        y.setEnabled(true);
        y.setDrawGridLines(true);
        y.setStartAtZero(false);
    }

    /**
     * get LineChart
     * @return lineChart
     */
    public LineChart getLineChart() {
        return lineChart;
    }
}