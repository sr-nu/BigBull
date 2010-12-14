package com.tw.activities;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.tw.activities.TabWidgetTest \
 * com.tw.activities.tests/android.test.InstrumentationTestRunner
 */
public class TabWidgetTest extends ActivityInstrumentationTestCase2<TabWidget> {

    public TabWidgetTest() {
        super("com.tw.activities", TabWidget.class);
    }

}
