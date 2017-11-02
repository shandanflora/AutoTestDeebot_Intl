package com.ecovacs.ecosphere.intl;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.ecovacs.ecosphere.intl.common.JsonParse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dalvik.annotation.TestTarget;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
        //getCountryList();
    }

}