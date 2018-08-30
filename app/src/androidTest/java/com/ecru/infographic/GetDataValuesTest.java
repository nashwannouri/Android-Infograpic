package com.ecru.infographic;

import android.test.ActivityInstrumentationTestCase2;

import com.ecru.data.ApiHandler;
import com.ecru.data.GetDataValues;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by nashwan on 12/6/2015.
 */
public class GetDataValuesTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public GetDataValuesTest() {
        super(MainActivity.class);
    }


    public void testActivityExists() {
        MainActivity mainActivity = getActivity();
        assertNotNull(mainActivity);
    }

    /**
     * Tests to see if the GetDataValuesTest exists;
     */
    public void testGetDataValuesExists() {
        MainActivity mainActivity = getActivity();
        assertNotNull(mainActivity.getDataValues());
    }


    /**
     * Tests if pie chart is given correct data
     */
    public void testCorrectDataSavedPieChart2002(){
        MainActivity mainActivity =getActivity();
        GetDataValues  dd = mainActivity.getDataValues();
        //checks 1992 data is properly applied to pie chart
        try {
            //year 10
            ArrayList<Float> pieData = dd.employmentPieData(10);
            //data from the worlddata website
            if(pieData.get(0)==1.39999997615814f&&pieData.get(1)==74.6999969482422f&&pieData.get(2)==23.7000007629395f){
                assertTrue(true);
            }
            else {
                assertTrue(false);
            }
        }
        catch(JSONException e){
            assertTrue(false);
        }
    }

    /**
     * Tests if the Parse is working properly
     */
    public void testParseData(){
        MainActivity activity =getActivity();
        GetDataValues dataValues =activity.getDataValues();

        //http://api.worldbank.org/countries/GBR/indicators/SL.IND.EMPL.ZS?per_page=100&date=1982:2012&format=json    used as reference
        String jSonTestString ="[{\"page\":1,\"pages\":1,\"per_page\":\"100\",\"total\":31},[{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"18.8999996185303\",\"decimal\":\"0\",\"date\":\"2012\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"19.1000003814697\",\"decimal\":\"0\",\"date\":\"2011\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"19.1000003814697\",\"decimal\":\"0\",\"date\":\"2010\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"19.5\",\"decimal\":\"0\",\"date\":\"2009\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"21.8999996185303\",\"decimal\":\"0\",\"date\":\"2008\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"22.2999992370605\",\"decimal\":\"0\",\"date\":\"2007\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"22\",\"decimal\":\"0\",\"date\":\"2006\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"22.2000007629395\",\"decimal\":\"0\",\"date\":\"2005\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"22.2000007629395\",\"decimal\":\"0\",\"date\":\"2004\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"23.2000007629395\",\"decimal\":\"0\",\"date\":\"2003\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"23.7000007629395\",\"decimal\":\"0\",\"date\":\"2002\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"24.5\",\"decimal\":\"0\",\"date\":\"2001\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"25.1000003814697\",\"decimal\":\"0\",\"date\":\"2000\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"25.6000003814697\",\"decimal\":\"0\",\"date\":\"1999\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"26.5\",\"decimal\":\"0\",\"date\":\"1998\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"26.7000007629395\",\"decimal\":\"0\",\"date\":\"1997\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"27.2999992370605\",\"decimal\":\"0\",\"date\":\"1996\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"27.2999992370605\",\"decimal\":\"0\",\"date\":\"1995\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"27.6000003814697\",\"decimal\":\"0\",\"date\":\"1994\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"29.3999996185303\",\"decimal\":\"0\",\"date\":\"1993\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"30\",\"decimal\":\"0\",\"date\":\"1992\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"31.1000003814697\",\"decimal\":\"0\",\"date\":\"1991\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"32.2999992370605\",\"decimal\":\"0\",\"date\":\"1990\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"32.5999984741211\",\"decimal\":\"0\",\"date\":\"1989\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"32.7999992370605\",\"decimal\":\"0\",\"date\":\"1988\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"29.7999992370605\",\"decimal\":\"0\",\"date\":\"1987\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"30.3999996185303\",\"decimal\":\"0\",\"date\":\"1986\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"31.2000007629395\",\"decimal\":\"0\",\"date\":\"1985\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"31.8999996185303\",\"decimal\":\"0\",\"date\":\"1984\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"33\",\"decimal\":\"0\",\"date\":\"1983\"},{\"indicator\":{\"id\":\"SL.IND.EMPL.ZS\",\"value\":\"Employment in industry (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"34.2000007629395\",\"decimal\":\"0\",\"date\":\"1982\"}]]";
        try {
            Object[] ff=dataValues.parseData(jSonTestString);
            //if length of data is 0, test fails
            if(ff.length==0){
                assertTrue(false);
            }

            int[] yearArray =(int[]) ff[0];
            //if array for years is empty then test fails
            if(yearArray==null ||yearArray.length==0){
                assertTrue(false);
            }

            float[] valueArray = (float[]) ff[1];
            //if array for values is empty then test fails
            if(valueArray==null ||valueArray.length==0){
                assertTrue(false);
            }
            //checks to see if year and corresponding value is correctly parsed -fails test otherwise
            if(!(yearArray[0]==2012 &&valueArray[0]==18.8999996185303f && yearArray[20]==1992 && valueArray[20]==30f)){
                assertTrue(false);
            }


        }catch (JSONException e){
            assertTrue(false);
        }
        assertTrue(true);
    }

}