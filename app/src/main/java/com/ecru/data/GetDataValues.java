package com.ecru.data;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;

import com.ecru.infographic.Pie;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Ayman on 29/11/2015.
 */
public class GetDataValues {
    public static String conclusion = "";
    String empServJson;
    String empAgrJson;
    String empIndJson;
    String exportsJson;
    String exportsGdpValueJson;
    int[] years;

    //arrays containing the actual values
    float[] agricultureVals;
    float[] serviceVals;
    float[] industryVals;
    float[] ictExports;
    float[] exportGdpVals;
    int  selected =0;
    /**
     * Method to get the data for various charts/graphs needed
     * @param activity
     */
    public GetDataValues(Activity activity){
        try {
            //to get data from url, append statement within try
            empServJson = new ApiHandler("empServJson", "http://api.worldbank.org/countries/GBR/indicators/SL.SRV.EMPL.ZS?per_page=100&date=1982:2012&format=json", activity).execute().get();
            empAgrJson = new ApiHandler("empAgrJson", "http://api.worldbank.org/countries/GBR/indicators/SL.AGR.EMPL.ZS?per_page=100&date=1982:2012&format=json", activity).execute().get();
            empIndJson = new ApiHandler("empIndJson", "http://api.worldbank.org/countries/GBR/indicators/SL.IND.EMPL.ZS?per_page=100&date=1982:2012&format=json", activity).execute().get();
            exportsJson = new ApiHandler("exportsJson", "http://api.worldbank.org/countries/GBR/indicators/BM.GSR.CMCP.ZS?per_page=50&date=2005:2014&format=json", activity).execute().get();
            exportsGdpValueJson= new ApiHandler("exportsGdpValueJson","http://api.worldbank.org/countries/GBR/indicators/BX.GSR.CCIS.CD?per_page=100&date=2005%3A2014&format=json\n",activity).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d("GetDataValues", "Data loading interrupted");
            e.printStackTrace();
        }
        try {
            years = (int[]) parseData(empAgrJson)[0];
            //arrays containing the actual values
            agricultureVals = (float[]) parseData(empAgrJson)[1];
            serviceVals = (float[]) parseData(empServJson)[1];
            industryVals = (float[]) parseData(empIndJson)[1];
            ictExports = (float[]) parseData(exportsJson)[1];
            exportGdpVals =(float[]) parseData(exportsGdpValueJson)[1];
            setConclusion();
        }
        catch (JSONException e){
            Log.d("GetDataValues", "Data was not parsed");
        }
    }

    /**
     * Method to extract data for the employment pie chart
     * @param year the year to return data for
     * @return an array list containing the values for a specified year
     * @throws JSONException
     */
    public ArrayList employmentPieData(int year) throws JSONException {

        //Create a new arraylist to contain the dataset
        ArrayList<Float> values = new ArrayList<>();

        //add the value to the array list for the year specified
        values.add(agricultureVals[year]);
        values.add(serviceVals[year]);
        values.add(industryVals[year]);


        //Temp, show values in log, used for debugging purposes
        Log.d("YEAR", years[year] + "");
        Log.d("VAL_AGRI", agricultureVals[year] + "");
        Log.d("VAL_SERV", serviceVals[year] + "");
        Log.d("VAL_INDS", industryVals[year] + "");
        Pie.yr = years[year];
        //return the arrayList containing the values
        return values;
    }
    /**
     * Method to extract data for sector LineChart
     * @return  LineData containing all sectors for allyears
     * @throws JSONException
     */
    public LineData LineGraphSectorData() throws JSONException{

        //Create a new arraylist to contain the dataset
        ArrayList<Entry> agriComp = new ArrayList<Entry>();
        ArrayList<Entry> servComp = new ArrayList<Entry>();
        ArrayList<Entry> indComp = new ArrayList<Entry>();
        ArrayList<String> yearNumberLabels = new ArrayList<>();
        for(int i=0; i< 21;++i) {
            //add the value to the array list for the year specified
            agriComp.add(new Entry(agricultureVals[20-i], i));
            servComp.add(new Entry(serviceVals[20-i], i));
            indComp.add(new Entry(industryVals[20-i], i));
            yearNumberLabels.add(i+ 1992 + "");
        }

        //Line Data Sets Are Created
        LineDataSet agriValues= new LineDataSet(agriComp,"AgriCulture");
        LineDataSet servicesValues = new LineDataSet(servComp,"Services");
        LineDataSet indValues = new LineDataSet(indComp,"Industry");

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
        agriValues.setValueTextSize(10f);

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
        servicesValues.setValueTextSize(10f);

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
        indValues.setValueTextSize(10f);

        ArrayList<LineDataSet> LineDataArray = new ArrayList<>();
        LineDataArray.add(agriValues);
        LineDataArray.add(servicesValues);
        LineDataArray.add(indValues);
        LineData data = new LineData(yearNumberLabels, LineDataArray);


        //return the arrayList containing the values
        return data;
    }

    /**
     * Creates Data for Communications LineChart
     * @return LineData for Communications LineChart
     * @throws JSONException
     */
    public LineData exportsChart() throws JSONException {

        //years the data exists for
        int[] years = (int[]) parseData(exportsJson)[0];

        //Create a new arraylist to contain the dataset
        ArrayList<Entry> exportsArrList = new ArrayList<Entry>();
        ArrayList<String> yearNumberLabels = new ArrayList<>();

        //data is extracted
        for (int i = 0; i < years.length; ++i) {
            //add the value to the array list for the year specified

            exportsArrList.add(new Entry(ictExports[i], ((years.length - 1) - i)));
            yearNumberLabels.add(String.valueOf(years[((years.length - 1) - i)]));

        }

        //graph setting are selected
        int red = Color.parseColor("#e74c3c");
        //Line Data Sets Are Created
        LineDataSet values = new LineDataSet(exportsArrList, "Communication and computer exports % of service exports");
        values.setDrawCubic(true);
        values.setCubicIntensity(0.3f);
        values.setDrawFilled(true);
        values.setDrawCircles(false);
        values.setLineWidth(3f);
        values.setValueTextSize(10f);
        values.setHighLightColor(red);
        values.setColor(red);
        values.setFillAlpha(0);
        values.setDrawHorizontalHighlightIndicator(false);

        ArrayList<LineDataSet> LineDataArray = new ArrayList<>();
        LineDataArray.add(values);
        LineData data = new LineData(yearNumberLabels, LineDataArray);

        return data;
    }

    /**
     * Creates Data for ICT service LineChart
     * @return LineData for Communications LineChart
     * @throws JSONException
     */
    public LineData exportsGdpChart() throws JSONException {

        int[] years = (int[]) parseData(exportsGdpValueJson)[0];
        //Create a new arraylist to contain the dataset
        ArrayList<Entry> exportsArrList = new ArrayList<Entry>();
        ArrayList<String> yearNumberLabels = new ArrayList<>();

        for (int i = 0; i < years.length; ++i) {
            //add the value to the array list for the year specified
            exportsArrList.add(new Entry((Float.parseFloat(String.format("%.2f",exportGdpVals[i]/1000000000 ))), (years.length - 1 - i)));
            yearNumberLabels.add(String.valueOf(years[((years.length - 1) - i)]));

        }
        int red = Color.parseColor("#e74c3c");
        //Line Data Sets Are Created
        LineDataSet values = new LineDataSet(exportsArrList, "ICT service exports (BoP, current US$)");
        values.setDrawCubic(true);
        values.setCubicIntensity(0.3f);
        values.setDrawFilled(true);
        values.setDrawCircles(false);
        values.setLineWidth(1.8f);
        values.setValueTextSize(10f);
        values.setHighLightColor(red);
        values.setColor(red);
        values.setFillColor(red);
        values.setFillAlpha(100);
        values.setDrawHorizontalHighlightIndicator(false);

        ArrayList<LineDataSet> LineDataArray = new ArrayList<>();
        LineDataArray.add(values);
        LineData data = new LineData(yearNumberLabels, LineDataArray);

        return data;
    }

    /**
     * Creates data for overall average change for sectors
     * @return float index 0=service index 1 =agriculture index 2 = industry
     */
    public float[] getCircleValues(){
        float[] temp = new float[3];
        temp[0] = (serviceVals[0]-serviceVals[20])/20;
        temp[1] = (agricultureVals[0]-agricultureVals[20])/20;
        temp[2] = (industryVals[0]-industryVals[20])/20;
        return temp;
    }


    /**
     * Method to parse the data
     * @param jsonString the json string to parse
     * @return Returns an object of two arrays, call 0 for years, 1 for values
     * @throws JSONException
     */
    public Object[] parseData(String jsonString) throws JSONException {

        JSONArray jsonArray = new JSONArray(jsonString);
        JSONArray list = jsonArray.getJSONArray(1);

        int length = list.length();

        int[] year = new int[length];
        float[] value = new float[length];

        //parsing code
        if (jsonArray != null) {
            try {
                JSONObject object;

                for (int i = 0; i < list.length(); ++i) {
                    object = list.getJSONObject(i);

                    year[i] = Integer.parseInt(object.getString("date"));
                    value[i] = Float.parseFloat(object.getString("value"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new Object[]{year, value};
    }
    public void setConclusion(){
        conclusion = "ICT sector in 2012 is worth $"+String.format("%.2f",exportGdpVals[2]/1000000000 )+"BIL. In the Uk " +serviceVals[0]+"% of the total employment is in the service sector. This sector is growing at a rate of "+getCircleValues()[0]+"% every year(average growth from 1992-2012)";
    }

}