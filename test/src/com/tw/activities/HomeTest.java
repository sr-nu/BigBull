package com.tw.activities;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;
import com.tw.activities.Home;
import junit.framework.Assert;


public class HomeTest extends ActivityInstrumentationTestCase2<Home>{
    private Home activity;
    private EditText txtFindQuote;

    public HomeTest() {
        super("com.tw.activities.Home",Home.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        activity = getActivity();
        txtFindQuote = (EditText) activity.findViewById(R.id.stock_symbol);
    }

    public void testPreConditions() {
        Assert.assertEquals(txtFindQuote.getText().toString(),"");
    }


}
