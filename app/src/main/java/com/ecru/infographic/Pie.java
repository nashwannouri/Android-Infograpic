package com.ecru.infographic;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bartoszlipinski.viewpropertyobjectanimator.ViewPropertyObjectAnimator;
import com.ecru.data.GetDataValues;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Rami on 04/12/2015.
 */
public class Pie implements SeekBar.OnSeekBarChangeListener {
    public static int yr = 1980;
    public PieChart pieChart;
    public int counter, delayTime;
    private Activity activity;
    private SeekBar pieSeekBar;
    private GetDataValues dataValues;

    /**
     *
     * @return pieChart
     */
    public PieChart getPieChart() {
        return pieChart;
    }

    /**
     *
     * @return pieSeekBar
     */
    public SeekBar getPieSeekBar(){
        return pieSeekBar;
    }

    /**
     *
     * @param activity get MainActivity context
     * @param dataValues get GetDataValues for PieChart
     */
    public Pie(final Activity activity, GetDataValues dataValues) {
        this.activity = activity;
        this.pieChart = (PieChart) activity.findViewById(R.id.pieChart);

        this.dataValues = dataValues;
        pieSeekBar = (SeekBar) activity.findViewById(R.id.pieSeekBar);
        // Sets listener on Seekbar
        pieSeekBar.setOnSeekBarChangeListener(this);
        pieSeekBar.setMax(30);
        pieSeekBar.setProgress(30);
        pieChart.animateY(750, Easing.EasingOption.EaseInOutExpo);
        Button btn0 = (Button) activity.findViewById(R.id.button0);
        Button btn1 = (Button) activity.findViewById(R.id.button1);
        Button btn2 = (Button) activity.findViewById(R.id.button2);

        btn0.setText("Use the slider below to select the years you want to see data for\n");
        btn1.setText("To view the percentage change from the previous year, tap on the chart segments");
        btn2.setText("This pie chart shows the percentage of uk employment in each sector");

        // Assign values to the pie chart
        try {
            setData(dataValues.employmentPieData(0));
        } catch (JSONException e) {
            String message = "No connection available, please connect to the Internet and restart the app";
            Toast toast = Toast.makeText(activity, message, Toast.LENGTH_LONG);
            toast.show();
        }

        // SEEKBAR LISTENERS
        assignListeners();

    }

    /**
     * Assigns OnTouchListener on Seekbar as ScrollView was causing issues so had to override
     */
    public void assignListeners() {
        pieSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle Seekbar touch events.
                v.onTouchEvent(event);
                return true;
            }

        });
        // Click events for PieChart to change boxes value
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                String temp[] = String.valueOf(entry).split(":");
                int buttonNum = Integer.parseInt(temp[1].replaceAll("[^\\d.]", ""));
                String buttonId = "button" + buttonNum;
                int resId = activity.getResources().getIdentifier(buttonId, "id", activity.getPackageName());
                Button pressBtn = (Button) activity.findViewById(resId);
                replace(pressBtn);
                try {
                    pressBtn.setText(getButtonString(buttonNum));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    /**
     *
     * @param values takes values from GetDataValue class for PieChart
     *
     */
    public void setData(ArrayList values) {

        ArrayList<Entry> yVals1 = new ArrayList<>();

        //get values in arrayList and pass them to here
        for (int i = 0; i < values.size(); i++) {
            yVals1.add(new Entry((Float) values.get(i), i));
        }

        ArrayList<String> titles = new ArrayList<>();
        titles.add("Agriculture");
        titles.add("Services");
        titles.add("Industry");

        PieDataSet dataSet = new PieDataSet(yVals1, "Employment sectors");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(8f);
        dataSet.setColors(new int[]{Color.parseColor("#f1c40f"), Color.parseColor("#e74c3c"), Color.parseColor("#3498db")});

        PieData data = new PieData(titles, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setHoleColorTransparent(true);
        pieChart.setDescription("");
        pieChart.setCenterText("2012");
        pieChart.setCenterTextSize(30.f);
        pieChart.setCenterTextTypeface(MainActivity.bigJoe);

        pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }


    /**
     * Animation for Buttons
     *
     * @param btn Animates boxes
     */
    public void replace(Button btn) {
        ObjectAnimator buttonAni = ViewPropertyObjectAnimator
                .animate(btn)
                .height(150)
                .setDuration(500)
                .rotationX(360)
                .get();
        buttonAni.start();
    }

    /**
     *
     * @param btn resets buttons when seek bar value changes
     */
    public void resetBtnSize(Button btn) {

        ObjectAnimator buttonA = ViewPropertyObjectAnimator
                .animate(btn)
                .height(50)
                .setDuration(delayTime)
                .rotationX(360)
                .get();
        buttonA.start();


    }

    /**
     *
     * This method is used to reset boxes to default position,
     * @throws JSONException to Assign Values when click event takes place
     */
    public void getAllBtns() throws JSONException {
        delayTime = 250;
        counter = 0;
        while (counter < 3) {
            String buttonId = "button" + counter;
            int resId = activity.getResources().getIdentifier(buttonId, "id", activity.getPackageName());
            Button pressBtn = (Button) activity.findViewById(resId);
            resetBtnSize(pressBtn);
            pressBtn.setText("");
            counter++;
            delayTime += 250;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    /**
     *
     * @param seekBar assign min/max values
     */
    public void onStopTrackingTouch(SeekBar seekBar) {

        int year = 30 - pieSeekBar.getProgress();
        try {
            setData(dataValues.employmentPieData(year));
            pieChart.setCenterText("" + yr);
            Log.d("year", "" + year);
            pieChart.invalidate();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pieChart.animateY(750, Easing.EasingOption.EaseInOutExpo);
        try {
            getAllBtns();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param buttonIndex get ButtonIndex from the View
     * @return data from GetDataValue class for each sector
     * @throws JSONException
     */
    public String getButtonString(int buttonIndex) throws JSONException {
        DecimalFormat decimalPoints = new DecimalFormat("0.###");
        int year = 30 - pieSeekBar.getProgress();
        String returnString;

        if (year < 30) {
            year += 1;

            if (buttonIndex == 0) {
                //AGRICULTURE
                float valChange = (float) dataValues.employmentPieData(year - 1).get(0) -
                        (float) dataValues.employmentPieData(year).get(0);

                if (valChange > 0) {
                    returnString = decimalPoints.format(valChange) + "% increase from " + (Pie.yr);
                } else if (valChange == 0){
                    returnString = "No change "+(Pie.yr);
                }
                else {
                    returnString = decimalPoints.format(-valChange) + "% decrease from " + (Pie.yr);
                }

            } else if (buttonIndex == 1) {
                //SERVICE
                float valChange = (float) dataValues.employmentPieData(year - 1).get(1) -
                        (float) dataValues.employmentPieData(year).get(1);

                if (valChange > 0) {
                    returnString = decimalPoints.format(valChange) + "% increase from " + (Pie.yr);
                }else if (valChange == 0){
                    returnString = "No change "+(Pie.yr);
                }
                else {
                    returnString = decimalPoints.format(-valChange) + "% decrease from " + (Pie.yr);
                }

            } else {
                //INDUSTRY
                float valChange = (float) dataValues.employmentPieData(year - 1).get(2) -
                        (float) dataValues.employmentPieData(year).get(2);

                if (valChange > 0) {
                    returnString = decimalPoints.format(valChange) + "% increase from " + (Pie.yr);
                }else if (valChange == 0){
                    returnString = "No change "+(Pie.yr);
                }
                else {
                    returnString = decimalPoints.format(-valChange) + "% decrease from " + (Pie.yr);
                }
            }

        } else {
            returnString = "N/A";
        }
        return returnString;
    }


}
