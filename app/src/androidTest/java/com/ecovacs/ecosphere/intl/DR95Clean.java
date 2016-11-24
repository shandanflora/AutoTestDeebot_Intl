package com.ecovacs.ecosphere.intl;

import android.util.Log;
import android.widget.TextView;

import com.ecovacs.ecosphere.intl.common.Common;
import com.ecovacs.ecosphere.intl.common.PropertyData;
import com.robotium.solo.Solo;

/**
 * Created by lily.shan on 2016/9/22.
 *
 */
public class DR95Clean {

    private static DR95Clean dr95Clean = null;
    private Solo solo = null;

    private DR95Clean(){

    }

    public static DR95Clean getInstance(){
        if(dr95Clean == null){
            dr95Clean = new DR95Clean();
        }
        return dr95Clean;
    }

    public void init(Solo solo){
        this.solo = solo;
    }

    private boolean showView(String strID){
        return solo.waitForView(solo.getView(strID));
    }

    private boolean showViewTry(String text, String viewName){
        int i = 0;

        while(!solo.waitForText(text)){
            solo.sleep(500);
            if(i > 20){
                Log.e("AutoTest", "(showViewTry)Not show " + viewName);
                return false;
            }
            i++;
            Log.e("AutoTest", "(showViewTry)wait 500*" + Integer.toString(i));
        }
        Log.i("AutoTest", "(showViewTry)Show " + viewName + "!!!");
        return true;
    }

    private boolean selectCountry(){
        /*View cellView = Common.getInstance().getView("lly_select_country");
        solo.clickOnView(cellView);
        solo.clickOnText(PropertyData.getProperty("country"), 1, true);
        solo.sleep(500);
        Common.getInstance().clickCtrlById("right", 0);*/

        TextView textCountry = (TextView)Common.getInstance().getView("tv_guoJia_diQu");
        String strCountry = textCountry.getText().toString().trim();
        if(!strCountry.equals(PropertyData.getProperty("country"))){
            Log.e("AutoTest", "(Login)Select Wrong country/region!!! " +  strCountry);
            return false;
        }
        Log.i("AutoTest", "(Login)Select country--" + PropertyData.getProperty("country"));
        return true;
    }

    public boolean login(){
        if(!showView("login")){
            Log.e("AutoTest", "(Login)Not show Welcome activity!!!");
            return false;
        }
        Common.getInstance().clickCtrlById("login", 0);
        Log.i("AutoTest", "(Login)Click login in login activity!!!");

        if(!showView("btn_login")){
            Log.e("AutoTest", "(Login)Not show Login activity");
            return false;
        }
        Log.i("AutoTest", "(Login)Show Login activity!!!");

        /*if(!selectCountry()){
            return false;
        }*/

        Common.getInstance().enterTextById("edt_email", PropertyData.getProperty("email"), 0);
        Common.getInstance().enterTextById("edt_pass", PropertyData.getProperty("pwd"), 0);

        Common.getInstance().clickCtrlById("btn_login", 0);
        Log.i("AutoTest", "(Login)Click button LogIn!!!");

        return tryLoginFail();
    }

    private boolean tryLoginFail(){
        int iLoop = 0;
        while(!showViewTry(PropertyData.getProperty("add"), "Login activity")){
            if (solo.searchText(PropertyData.getProperty("loginErrorOk"))){
                solo.clickOnButton(PropertyData.getProperty("loginErrorOk"));
                Log.i("AutoTest", "login in xmpp failed" + Integer.toString(iLoop + 1));
            }else if(solo.getView("btn_login").isEnabled()){
                Common.getInstance().clickCtrlById("btn_login", 0);
                Log.i("AutoTest", "login in http failed" + Integer.toString(iLoop + 1));
            }
            solo.sleep(500);
            if(iLoop > 20){
                Log.e("AutoTest", "Login in failed!!!");
                return false;
            }
            iLoop++;
        }
        return true;
    }

    public boolean logout(){
        Common.getInstance().clickCtrlById("right", 0);
        if(!showView("btn_exit")){
            Log.e("AutoTest", "(logout)Not show personal center activity!!!");
        }
        Log.i("AutoTest", "(logout)Show personal center activity!!!");
        Common.getInstance().clickCtrlById("btn_exit", 500);
        Log.i("AutoTest", "(logout)ConfirmTrue " + PropertyData.getProperty("ConfirmTrue"));
        solo.clickOnButton(PropertyData.getProperty("ConfirmTrue"));
        return showView("login");
    }

    public boolean regressLogin(){

        for(int i = 0; i < 1000; i++){
            Log.i("AutoTest", "(regessLogin)************************************************");
            if(!login()){
                Log.e("AutoTest", "(regessLogin)Login failed--No." + Integer.toString(i + 1));
                return false;
            }
            Log.i("AutoTest", "(regessLogin)Login successfully--No." + Integer.toString(i + 1));
            if(!logout()){
                Log.e("AutoTest", "(regessLogin)Logout failed--No." + Integer.toString(i + 1));
            }
            Log.i("AutoTest", "(regessLogin)Logout successfully--No." + Integer.toString(i + 1));
        }
        return true;
    }
}
