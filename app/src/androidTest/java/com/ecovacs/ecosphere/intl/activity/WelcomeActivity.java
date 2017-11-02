package com.ecovacs.ecosphere.intl.activity;

import android.util.Log;
import android.widget.TextView;

import com.ecovacs.ecosphere.intl.common.ParseExcel;
import com.robotium.solo.Solo;

import java.util.Map;

/**
 * Created by ecosqa on 17/2/6.
 *
 */

public class WelcomeActivity {
    private static WelcomeActivity welcomeActivity = null;
    private Solo solo = null;

    private WelcomeActivity(){

    }

    public void init(Solo solo){
        this.solo = solo;
    }

    public static WelcomeActivity getInstance(){
        if(welcomeActivity == null){
            welcomeActivity = new WelcomeActivity();
        }
        return welcomeActivity;
    }

    public void translate(){
        Map<String, String> tranMap = ParseExcel.getInstance().readExcel("Translate.xlsx", "en");
        TextView textView = (TextView) solo.getView("register");
        String str = textView.getText().toString();
        Log.i("AutoTest", "-- " + tranMap.get("register"));
        if(!str.equals(tranMap.get("register"))){
            Log.e("AutoTest", "translate is not matched!!!" + tranMap.get("register"));
            Log.e("AutoTest", "App string--" + str);
            Log.e("AutoTest", "Src string--" + tranMap.get("register"));
        }

        textView = (TextView) solo.getView("register");
        str = textView.getText().toString();
        Log.i("AutoTest", "-- " + tranMap.get("register"));
        if(!str.equals(tranMap.get("register"))){
            Log.e("AutoTest", "translate is not matched!!!" + tranMap.get("register"));
            Log.e("AutoTest", "App string--" + str);
            Log.e("AutoTest", "Src string--" + tranMap.get("register"));
        }


    }

}
