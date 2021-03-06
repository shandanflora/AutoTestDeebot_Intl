package com.ecovacs.ecosphere.intl;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ecovacs.ecosphere.intl.common.Common;
import com.ecovacs.ecosphere.intl.common.JsonParse;
import com.ecovacs.ecosphere.intl.common.PropertyData;
import com.robotium.solo.Solo;

import junit.framework.AssertionFailedError;

import java.util.List;

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

    private boolean selectCountry(String strCountry){
        View cellView = Common.getInstance().getView("lly_select_country");
        solo.clickOnView(cellView);
        solo.scrollListToTop(0);
        solo.clickOnText(strCountry, 1, true);
        solo.sleep(500);
        Common.getInstance().clickCtrlById("right", 0);

        TextView textCountry = (TextView)Common.getInstance().getView("tv_guoJia_diQu");
        String strTextCountry = textCountry.getText().toString().trim();
        if(!strTextCountry.equals(strCountry)){
            Log.e("AutoTest", "(Login)Select Wrong country/region!!! " +  strTextCountry);
            return false;
        }
        Log.i("AutoTest", "(Login)Select country--" + strCountry);
        return true;
    }

    private boolean login(String strCountry){
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

        if(!selectCountry(strCountry)){
            return false;
        }

        Common.getInstance().enterTextById("edt_email", PropertyData.getProperty("email"), 0);
        Common.getInstance().enterTextById("edt_pass", PropertyData.getProperty("pwd"), 0);

        Common.getInstance().clickCtrlById("btn_login", 0);
        Log.i("AutoTest", "(Login)Click button LogIn!!!");

        return tryLoginFail();
    }

    private boolean showView(String strID){
        boolean bRes = false;
        try {
            if(solo.getView(strID) != null){
                //Log.i("AutoTest", "(showViewTry)title_back null");
                bRes = true;
            }
        }catch (AssertionFailedError e){
            Log.e("AutoTest", "(showViewTry)" + e.toString());
        }
        return bRes;
    }

    private boolean showViewTry(String viewName){
        int i = 0;
        while(!showView("title_back") && !showView("right") &&
                !showView("titleContent")){
            i++;
            solo.sleep(500);
            if(i > 0){
                Log.e("AutoTest", "(showViewTry)Not show " + viewName);
                return false;
            }
            Log.e("AutoTest", "(showViewTry)wait 500*" + Integer.toString(i));
        }
        Log.i("AutoTest", "(showViewTry)Show " + viewName + "!!!");
        return true;
    }

    private boolean tryLoginFail(){
        int iLoop = 0;
        if (!showViewTry("Login activity")){
            if (solo.searchText(PropertyData.getProperty("loginErrorOk"))){
                solo.clickOnButton(PropertyData.getProperty("loginErrorOk"));
                Log.i("AutoTest", "login in xmpp failed" + Integer.toString(iLoop + 1));
            }else if(solo.getView("btn_login").isEnabled()){
                Common.getInstance().clickCtrlById("btn_login", 0);
                Log.i("AutoTest", "login in http failed" + Integer.toString(iLoop + 1));
            }
            /*solo.sleep(500);
            if(iLoop > 20){
                Log.e("AutoTest", "Login in failed!!!");
                return false;
            }
            iLoop++;*/
            return false;
        }
        return true;
    }

    private boolean logout(){
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
            if(!login("Japan")){
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

    public boolean register(String strCountry){
        Common.getInstance().clickViewById("register");
        Log.i("AutoTest", "(register)Click register in welcome activity!!!");

        if(!showView("tv_youBian")){
            Log.e("AutoTest", "(Login)Not show Registered activity");
            return false;
        }
        Log.i("AutoTest", "(Login)Show Registered activity!!!");

        if(!selectCountry(strCountry)){
            return false;
        }
        Common.getInstance().enterTextById("edt_email", PropertyData.getProperty("email"), 0);
        Common.getInstance().enterTextById("edt_pass", PropertyData.getProperty("pwd"), 0);
        Common.getInstance().enterTextById("edt_pass_repeat", PropertyData.getProperty("pwd"), 0);
        Common.getInstance().clickViewById("btn_register");
        return true;
    }


    public boolean login_multiCountry(){
        Log.i("AutoTest", "(login_multiCountry)run login multiple Country!!!");
        List countryList = JsonParse.getJsonParse().readDataFromJson("config.json", "countries");
        int iSize = countryList.size();
        boolean bLogin;
        for(int i = 0; i < iSize; i++){
            bLogin = false;
            Log.i("AutoTest", "(login_multiCountry)begin to login " + countryList.get(i));
            if (login(countryList.get(i).toString())){
                Log.i("AutoTest", "Login " + countryList.get(i) + " successfully!!!");
                bLogin = true;
            }else {
                Log.e("AutoTest", "Login " + countryList.get(i) + " failed!!!");
            }
            if(!bLogin){
                Log.e("AutoTest", "Login*****");
                solo.goBack();
                continue;
            }
            if (logout()){
                Log.i("AutoTest", "Logout " + countryList.get(i) + "successfully!!!");
            }else {
                Log.e("AutoTest", "Logout " + countryList.get(i) + "failed!!!");
            }
        }
        return true;
    }

    public boolean translate_intl(){
        return true;
    }
}
