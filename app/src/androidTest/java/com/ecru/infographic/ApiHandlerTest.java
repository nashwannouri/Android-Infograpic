package com.ecru.infographic;

import android.test.ActivityInstrumentationTestCase2;

import com.ecru.data.ApiHandler;
import com.ecru.data.GetDataValues;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by nashwan on 12/8/2015.
 */
public class ApiHandlerTest extends ActivityInstrumentationTestCase2<MainActivity>

{
    public ApiHandlerTest() {
        super(MainActivity.class);
    }

    public void testActivityExists() {
        MainActivity mainActivity = getActivity();
        assertNotNull(mainActivity);
    }

    /**
     * Tests to see if string is returned
     */
    public void testStringUpdatedFunction() {

        MainActivity activity = getActivity();
        //
        ApiHandler apiHandler = new ApiHandler("testFileString", "http://api.worldbank.org/countries/GBR/indicators/SL.SRV.EMPL.ZS?per_page=100&date=1982:2012&format=json", activity);
        try {
            //if the string returns is empty or null test fails, else test passes
            String testFileString = apiHandler.execute().get();
            if (testFileString == null || testFileString.equals("")) {
                assertTrue(false);
            } else {
                assertTrue(true);
            }
        } catch (InterruptedException | ExecutionException e) {
            assertTrue(false);
        }
    }

    /**
     * Tests the load function to see if it is equal to the data saved
     */
    public void testLoadFunction() {

        MainActivity activity = getActivity();
        String testFileStringLoad = "";
        ApiHandler apiHandler = new ApiHandler("testFileStringLoad", "http://api.worldbank.org/countries/GBR/indicators/SL.SRV.EMPL.ZS?per_page=100&date=1982:2012&format=json", activity);
        try {
            testFileStringLoad = apiHandler.execute().get();
            String loadedFile = apiHandler.loadCachedData();
            assertEquals(testFileStringLoad, loadedFile);
        } catch (Exception e) {
            assertTrue(false);
        }

    }

    /**
     * Tests the Save function to see if it saves the correct data
     */
    public void testSaveFunction() {

        MainActivity activity = getActivity();

        ApiHandler apiHandler = new ApiHandler("testFileStringSave", "http://api.worldbank.org/countries/GBR/indicators/SL.SRV.EMPL.ZS?per_page=100&date=1982:2012&format=json", activity);
        //will call from URL, tests fail if unable to do so
        try {
            apiHandler.execute().get();
            String loadedFile = apiHandler.loadCachedData();
            //copied from the url link used for apiHandler
            assertEquals("[{\"page\":1,\"pages\":1,\"per_page\":\"100\",\"total\":31},[{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"78.9000015258789\",\"decimal\":\"0\",\"date\":\"2012\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"79\",\"decimal\":\"0\",\"date\":\"2011\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"78.9000015258789\",\"decimal\":\"0\",\"date\":\"2010\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"78.6999969482422\",\"decimal\":\"0\",\"date\":\"2009\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"76.5999984741211\",\"decimal\":\"0\",\"date\":\"2008\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"76\",\"decimal\":\"0\",\"date\":\"2007\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"76.4000015258789\",\"decimal\":\"0\",\"date\":\"2006\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"76.3000030517578\",\"decimal\":\"0\",\"date\":\"2005\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"76.3000030517578\",\"decimal\":\"0\",\"date\":\"2004\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"75.4000015258789\",\"decimal\":\"0\",\"date\":\"2003\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"74.6999969482422\",\"decimal\":\"0\",\"date\":\"2002\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"73.8000030517578\",\"decimal\":\"0\",\"date\":\"2001\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"73.0999984741211\",\"decimal\":\"0\",\"date\":\"2000\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"72.5999984741211\",\"decimal\":\"0\",\"date\":\"1999\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"71.5\",\"decimal\":\"0\",\"date\":\"1998\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"71.0999984741211\",\"decimal\":\"0\",\"date\":\"1997\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"70.3000030517578\",\"decimal\":\"0\",\"date\":\"1996\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"70.1999969482422\",\"decimal\":\"0\",\"date\":\"1995\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"69.8000030517578\",\"decimal\":\"0\",\"date\":\"1994\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"68\",\"decimal\":\"0\",\"date\":\"1993\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"67.0999984741211\",\"decimal\":\"0\",\"date\":\"1992\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"65.6999969482422\",\"decimal\":\"0\",\"date\":\"1991\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"64.9000015258789\",\"decimal\":\"0\",\"date\":\"1990\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"64.5\",\"decimal\":\"0\",\"date\":\"1989\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"64.3000030517578\",\"decimal\":\"0\",\"date\":\"1988\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"66.5999984741211\",\"decimal\":\"0\",\"date\":\"1987\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"65.8000030517578\",\"decimal\":\"0\",\"date\":\"1986\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"64.9000015258789\",\"decimal\":\"0\",\"date\":\"1985\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"64.3000030517578\",\"decimal\":\"0\",\"date\":\"1984\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"63\",\"decimal\":\"0\",\"date\":\"1983\"},{\"indicator\":{\"id\":\"SL.SRV.EMPL.ZS\",\"value\":\"Employment in services (% of total employment)\"},\"country\":{\"id\":\"GB\",\"value\":\"United Kingdom\"},\"value\":\"61.7999992370605\",\"decimal\":\"0\",\"date\":\"1982\"}]]", loadedFile);
        } catch (Exception e) {
            assertTrue(false);
        }
    }


}
