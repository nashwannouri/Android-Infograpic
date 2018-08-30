package com.ecru.infographic;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Harkamal on 12/11/2015.
 */
public class ExportsGraphTests extends ActivityInstrumentationTestCase2<MainActivity> {
    public ExportsGraphTests() {
        super(MainActivity.class);
    }

    public void testActivityExists() {
        MainActivity mainActivity = getActivity();
        assertNotNull(mainActivity);
    }

    public void testExportsGraphExists(){
        MainActivity mainActivity = getActivity();
        assertNotNull(mainActivity.getExportsGraph());
    }

    public void testExportsGraphLineDataExists() {
        MainActivity mainActivity = getActivity();
        assertNotNull(mainActivity.getExportsGraph().getLineData());
    }

    public void testAxisLineIsEnabled() {
        MainActivity mainActivity = getActivity();
        assertTrue(mainActivity.getExportsGraph().getXAxis().isDrawAxisLineEnabled());
    }

    public void testExportsGraphYAxisMaxValue() {
        MainActivity mainActivity = getActivity();
        float m = 80;
        assertEquals(m, mainActivity.getExportsGraph().getAxisLeft().getAxisMaxValue());
    }

    public void testExportsGraphYAxisMinValue() {
        MainActivity mainActivity = getActivity();
        float n = 33.4f;
        assertEquals(n, mainActivity.getExportsGraph().getAxisLeft().getAxisMinValue());

    }

}
