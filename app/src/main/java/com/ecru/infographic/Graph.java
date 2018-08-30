package com.ecru.infographic;

import android.app.Activity;
import android.graphics.Color;

import com.ecru.data.GetDataValues;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

/**
 * Created by Rami on 04/12/2015.
 */
public class Graph {

    Activity activity;

    LineChart lineChart;
    GetDataValues dataValues;

    /**
     *
     * @param activity gets MainActivity context
     * @param dataValues GetDataValue class data for the LineChart
     */
    public Graph(Activity activity, GetDataValues dataValues) {
        this.activity = activity;
        this.lineChart = (LineChart)activity.findViewById(R.id.lineChart);
        this.dataValues = dataValues;
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription("");


        // Assigns data to the chart
        try {
            lineChart.setData(dataValues.LineGraphSectorData());

        } catch (Exception e) {

        }


        // xAxis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setDrawAxisLine(true);
        xAxis.setLabelsToSkip(0);
        xAxis.setTextSize(8.f);
        xAxis.setGridColor(Color.parseColor("#95a5a6"));


        // yAxis
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.setEnabled(true);
        leftAxis.setAxisMaxValue(100f);
        leftAxis.setAxisMinValue(-10f);
        leftAxis.setStartAtZero(false);
        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        // Legend
        Legend l = lineChart.getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);
        lineChart.getAxisRight().setEnabled(false);

        lineChart.setClickable(false);
        lineChart.invalidate();


    }

    /**
     *
     * @return LineChart
     */
    public LineChart getLineChart() {
        return lineChart;
    }
}
