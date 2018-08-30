package com.ecru.infographic;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Harkamal on 12/11/2015.
 */
public class LineGraphTests  extends ActivityInstrumentationTestCase2<MainActivity> {
    public LineGraphTests() {
        super(MainActivity.class);
    }
    public void testActivityExists() {
        MainActivity mainActivity = getActivity();
        assertNotNull(mainActivity);
    }

    public void testGraphExists(){
        MainActivity mainActivity = getActivity();
        assertNotNull(mainActivity.getGraph());
    }

    public void testGraphAxisMaxValue() {
        MainActivity mainActivity = getActivity();
        float n = 100f;
        assertEquals(n, mainActivity.getGraph().getAxisLeft().getAxisMaxValue());
    }

    public void testGraphAxisMinValue() {
        MainActivity mainActivity = getActivity();
        float m = -10f;
        assertEquals(m, mainActivity.getGraph().getAxisLeft().getAxisMinValue());

    }

    public void testAxisLeftIsEnabled() {
        MainActivity mainActivity = getActivity();
        assertFalse(mainActivity.getGraph().getAxisLeft().isEnabled());
    }

    public void testXAxisIsEnabled(){
        MainActivity mainActivity = getActivity();
        assertTrue(mainActivity.getGraph().getXAxis().isEnabled());
    }

}
