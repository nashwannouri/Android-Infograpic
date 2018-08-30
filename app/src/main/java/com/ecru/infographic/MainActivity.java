package com.ecru.infographic;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bartoszlipinski.viewpropertyobjectanimator.ViewPropertyObjectAnimator;
import com.ecru.data.GetDataValues;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONException;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static Typeface bigJoe, fontAws;
    private TextView title, about, play, backBtn;
    private ImageView seekbar_info;
    private ArrayList<LineData> lineDatas, lineDatasRight;
    private DrawerLayout drawerLayout;
    private ListView leftListView, rightListView;
    private CircleDisplay agriCir, indusCir, servCir;
    private ImageView rise, fall0, fall1;
    private GetDataValues dataValues;
    private Pie pieChart;
    private Graph graph;
    private ExportsGraph exports;
    private HorizontalScrollView yscroll;
    private boolean animateOnce;
    private int animateCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ((findViewById(R.id.small_ScreenTXT) == null)) {

            bigJoe = Typeface.createFromAsset(getAssets(), "fonts/Track.otf");
            fontAws = Typeface.createFromAsset(getAssets(), "fonts/FontAwesome.otf");
            // TEXT VIEWS
            title = (TextView) findViewById(R.id.title);
            title.setTypeface(bigJoe);
            about = (TextView) findViewById(R.id.about);
            about.setTypeface(fontAws);
            play = (TextView) findViewById(R.id.play);
            play.setTypeface(fontAws);
            backBtn = (TextView) findViewById(R.id.backBtn);
            backBtn.setTypeface(fontAws);

            // YSCROLL
            yscroll = (HorizontalScrollView) findViewById(R.id.horizontalScroll);
            scrollEvent();

            dataValues = new GetDataValues(this);

            // DRAWER LAYOUT
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

            // IMAGE VIEW
            seekbar_info = (ImageView) findViewById(R.id.seekbar_info);

            // CHARTS
            pieChart = new Pie(this, dataValues);
            graph = new Graph(this, dataValues);
            exports = new ExportsGraph(this, dataValues);

            float[] circleValues = dataValues.getCircleValues();

            // THREE CIRCLES
            agrCircle(circleValues[1]);
            serviceCircle(circleValues[0]);
            industryCircle(circleValues[2]);
            hideInfos();

            // For Scroll Animation
            animateOnce = true;
            animateCount = 0;

            animateArrows(circleValues);

            //sidePanels
            createLeftSidePanel();
            createRightSidePanel();
        }
    }

    /**
     * return Piechart
     * @return PieChart
     */
    public PieChart getPie() {
        return pieChart.getPieChart();
    }

    /**
     * Return Employment lineChart
     * @return
     */
    public LineChart getGraph() {
        return graph.getLineChart();
    }

    /**
     * Return seekbar for pieChart
     * @return
     */
    public SeekBar getPieSeekBar() {
        return pieChart.getPieSeekBar();
    }

    /**
     * Return dataValues
     * @return
     */
    public GetDataValues getDataValues() {
        return dataValues;
    }

    /**
     * Return Exports lineChart
     * @return
     */
    public LineChart getExportsGraph(){
        return exports.getLineChart();
    }

    /**
     * Creates right side panel
     */
    public void createRightSidePanel(){
        lineDatasRight = new ArrayList<>();

        try {
            lineDatasRight.add(dataValues.exportsGdpChart());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        rightListView = (ListView) findViewById(R.id.right_drawer);
        ExportsValueGraph exportsValueGraph = new ExportsValueGraph(getApplicationContext(), lineDatasRight);
        rightListView.setAdapter(exportsValueGraph);

    }

    /**
     * Creates left side panel
     */
    public void createLeftSidePanel() {

        lineDatas = new ArrayList<>();
        lineDatas.add(generateData());
        leftListView = (ListView) findViewById(R.id.left_drawer);
        LineChartAdapter lineChartAdapter = new LineChartAdapter(getApplicationContext(), lineDatas);
        leftListView.setAdapter(lineChartAdapter);

    }

    /**
     * Resets button to original state
     */
    public void hideInfos() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator seekbarInfo = ViewPropertyObjectAnimator
                        .animate(seekbar_info)
                        .setDuration(500)
                        .alpha(0)
                        .get();
                seekbarInfo.start();
            }
        }, 10000);
    }

    /**
     * Sets average value of agriculture employment circle
     * @param value
     */
    public void agrCircle(float value) {
        int yellow = getResources().getColor(R.color.yellow);
        agriCir = (CircleDisplay) findViewById(R.id.overallAgri);
        agriCir.setColor(yellow);
        agriCir.setValueWidthPercent(10f);
        agriCir.setTextSize(25f);
        agriCir.setDrawText(true);
        agriCir.setDrawInnerCircle(true);
        agriCir.setFormatDigits(3);
        agriCir.setUnit("%");
        agriCir.setStepSize(2f);
        agriCir.setTouchEnabled(false);
        // cd.setCustomText(...); // sets a custom array of text
        float x = Math.abs(value);
        agriCir.showValue(x, 1f, true);

    }

    /**
     * Sets average value of service employment circle
     * @param value
     */
    public void serviceCircle(float value) {
        int red = getResources().getColor(R.color.red);
        servCir = (CircleDisplay) findViewById(R.id.overallServ);
        servCir.setColor(red);
        servCir.setValueWidthPercent(10f);
        servCir.setTextSize(25f);
        servCir.setDrawText(true);
        servCir.setDrawInnerCircle(true);
        servCir.setFormatDigits(3);
        servCir.setUnit("%");
        servCir.setStepSize(2f);
        servCir.setTouchEnabled(false);
        // cd.setCustomText(...); // sets a custom array of text
        float x = Math.abs(value);
        servCir.showValue(x, 1f, true);
    }

    /**
     * Sets average value of industry employment circle
     * @param value
     */
    public void industryCircle(float value) {
        int blue = getResources().getColor(R.color.blue);
        indusCir = (CircleDisplay) findViewById(R.id.overallInd);
        indusCir.setColor(blue);
        indusCir.setValueWidthPercent(10f);
        indusCir.setTextSize(25f);
        indusCir.setDrawText(true);
        indusCir.setDrawInnerCircle(true);
        indusCir.setFormatDigits(3);
        indusCir.setUnit("%");
        indusCir.setStepSize(2f);
        indusCir.setTouchEnabled(false);
        // cd.setCustomText(...); // sets a custom array of text
        float x = Math.abs(value);
        indusCir.showValue(x, 1f, true);
    }

    /**
     * Animates arrows to indicate increase/descrease
     * @param mm
     */
    public void animateArrows(float[] mm) {

        ImageView[] imageViews = {
                (ImageView) findViewById(R.id.rise),
                (ImageView) findViewById(R.id.fall0),
                (ImageView) findViewById(R.id.fall1)};
        for(int i =0;i < mm.length; i++){
            if (mm[i] > 0){
                ObjectAnimator arrow = ViewPropertyObjectAnimator
                        .animate(imageViews[i])
                        .setDuration(1200)
                        .alpha(0)
                        .translationY(-9f)
                        .get();
                arrow.start();
                arrow.setRepeatCount(ValueAnimator.INFINITE);
            } else {
                ObjectAnimator arrow1 = ViewPropertyObjectAnimator
                        .animate(imageViews[i])
                        .setDuration(1200)
                        .alpha(0)
                        .translationY(8f)
                        .get();
                arrow1.start();
                arrow1.setRepeatCount(ValueAnimator.INFINITE);
            }
        }
    }

    /**
     * Generates the predicted data for use in the predicted employment line graph
     * @return LineData for graph
     */
    private LineData generateData() {
        //arrays containing the actual values
        float[] industryVals = new float[10];
        for (int i = 0; i < 10; i++) {
            float predValue = (float) (18.9 * Math.pow((1 + -0.055), i));
            industryVals[i] = predValue;
            Log.d("generateData", "Industry Value: " + predValue);
        }

        float[] agricultureVals = new float[10];
        for (int i = 0; i < 10; i++) {
            float predValue = (float) (1.2 * Math.pow((1 + -0.006), i));
            agricultureVals[i] = predValue;
            Log.d("generateData", "Agriculture Value: " + predValue);
        }

        float[] serviceVals = new float[10];
        for (int i = 0; i < 10; i++) {
            float predValue = 100-(agricultureVals[i]+ industryVals[i]);
            serviceVals[i] = predValue;
            Log.d("generateData", "Service Value: " + predValue);
        }

        //Create a new arraylist to contain the dataset
        ArrayList<Entry> agriComp = new ArrayList<Entry>();
        ArrayList<Entry> servComp = new ArrayList<Entry>();
        ArrayList<Entry> indComp = new ArrayList<Entry>();
        ArrayList<String> yearNumberLabels = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            //add the value to the array list for the year specified
            agriComp.add(new Entry(agricultureVals[i], i));
            servComp.add(new Entry(serviceVals[i], i));
            indComp.add(new Entry(industryVals[i], i));
            yearNumberLabels.add(i + 2013 + "");
        }

        //Line Data Sets Are Created
        LineDataSet agriValues = new LineDataSet(agriComp, "Agriculture");
        LineDataSet servicesValues = new LineDataSet(servComp, "Services");
        LineDataSet indValues = new LineDataSet(indComp, "Industry");

        /////////// Agriculture Styling
        int yellow = Color.parseColor("#f1c40f");
        agriValues.setAxisDependency(YAxis.AxisDependency.LEFT);
        agriValues.setColor(yellow);
        agriValues.setCircleColor(yellow);
        agriValues.setLineWidth(5f);
        agriValues.setCircleSize(10f);
        agriValues.setFillAlpha(65);
        agriValues.setFillColor(yellow);
        agriValues.setDrawCircleHole(false);
        agriValues.setHighLightColor(yellow);

        /////////// Services Styling
        int red = Color.parseColor("#e74c3c");
        servicesValues.setAxisDependency(YAxis.AxisDependency.LEFT);
        servicesValues.setColor(red);
        servicesValues.setCircleColor(red);
        servicesValues.setLineWidth(5f);
        servicesValues.setCircleSize(10f);
        servicesValues.setFillAlpha(65);
        servicesValues.setFillColor(red);
        servicesValues.setDrawCircleHole(false);
        servicesValues.setHighLightColor(red);

        /////////// Industry Styling
        int blue = Color.parseColor("#3498db");
        indValues.setAxisDependency(YAxis.AxisDependency.LEFT);
        indValues.setColor(blue);
        indValues.setCircleColor(blue);
        indValues.setLineWidth(5f);
        indValues.setCircleSize(10f);
        indValues.setFillAlpha(65);
        indValues.setFillColor(blue);
        indValues.setDrawCircleHole(false);
        indValues.setHighLightColor(blue);

        ArrayList<LineDataSet> LineDataArray = new ArrayList<>();
        LineDataArray.add(agriValues);
        LineDataArray.add(servicesValues);
        LineDataArray.add(indValues);
        LineData data = new LineData(yearNumberLabels, LineDataArray);

        //return the arrayList containing the values
        return data;

    }

    /**
     * Displays the right side panel
     * @param v
     */
    public void displayRightSidePanel(View v){
        drawerLayout.openDrawer(Gravity.RIGHT);
    }

    /**
     * Displays the left side panel
     * @param v
     */
    public void displayLeftSidePanel(View v){
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    /**
     * Displays About dialog box
     * @param v
     */
    public void displayAboutDiag(View v){
        AboutDialog dialogFragment = new AboutDialog();
        dialogFragment.show(getSupportFragmentManager(), "About");
    }

    /**
     * Starts autoScroll animation
     * @param v
     */
    public void autoScroll(View v){
        if (yscroll.getScrollX() < 100) {
            ObjectAnimator ys = ViewPropertyObjectAnimator.animate(yscroll)
                    .scrollX(4000)
                    .setDuration(15000)
                    .get();
            ys.start();
        }

    }

    /**
     * Animates the objects when the user scrolls to a certain point
     */
    public void scrollEvent() {
        yscroll.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollX = yscroll.getScrollX(); //for horizontalScrollView
                if (animateOnce) {
                    if (scrollX > 400 && scrollX < 720 && animateCount == 0) {
                        graph.getLineChart().animateX(2000); animateCount++;
                    }
                    if (scrollX > 1600 && scrollX < 1720 && animateCount == 1) {
                        indusCir.setAnimDuration(2000);
                        indusCir.startAnim();
                        agriCir.setAnimDuration(2000);
                        agriCir.startAnim();
                        servCir.setAnimDuration(2000);
                        servCir.startAnim();
                        animateCount++;
                    }
                    if (scrollX > 1810 && scrollX < 2700 && animateCount == 2) {
                        exports.getLineChart().animateX(2000);
                        animateCount++;
                        animateOnce = false;
                    }
                }
            }
        });
    }

    /**
     * Return to start of the application
     * @param v
     */
    public void resetScroll(View v){
        yscroll.setScrollX(0);
    }

    /**
     * Expands the conclusion button
     * @param v
     */
    public void expandConclusionBtn(View v){
        int red = Color.parseColor("#ecf0f1");
        Button temp = (Button)v;
        temp.setAllCaps(false);
        ObjectAnimator expand = ViewPropertyObjectAnimator.animate(temp)
                .bottomPadding(120)
                .setDuration(1000)
                .get();
        expand.start();
        temp.setBackgroundColor(red);
        temp.setTextSize(20f);
        temp.setText(GetDataValues.conclusion);
    }

}
