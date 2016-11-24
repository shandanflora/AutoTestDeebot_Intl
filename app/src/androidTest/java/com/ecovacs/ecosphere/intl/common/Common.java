package com.ecovacs.ecosphere.intl.common;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.robotium.solo.Solo;

/**
 * Created by lily.shan on 2016/6/15.
 * common function in this class
 */
public class Common {

    private static Common common = null;
    private Solo solo = null;

    public static Common getInstance(){
        if(common == null){
            common = new Common();
        }
        return common;
    }

    public void init(Solo solo){
        this.solo = solo;
    }

    /**
     * click view by id
     * @param strID String
     * @param iTime int
     * @return int
     */
    public int clickCtrlById(String strID, int iTime){

        int ctrl;
        View v;
        //Log.e("clickCtrlById", "clickCtrlById0");
        if(strID.equals("")){
            return -1;
        }
        Log.e("clickCtrlById", "clickCtrlById1");
        ctrl = solo.getCurrentActivity().getResources().getIdentifier(strID, "id", solo.getCurrentActivity().getPackageName());
        Log.e("clickCtrlById", Integer.toString(ctrl));
        v = solo.getView(ctrl);

        solo.clickOnView(v);

        if(iTime != 0){
            solo.sleep(iTime);
        }

        return 0;
    }

    /**
     *
     * @param strID String
     * @return View
     */
    public View getView(String strID){
        int ctrl;

        if(strID.equals("")){
            return null;
        }
        //Log.e("clickCtrlById", "clickCtrlById1");
        ctrl = solo.getCurrentActivity().getResources().getIdentifier(strID, "id", solo.getCurrentActivity().getPackageName());
        //Log.e("clickCtrlById", Integer.toString(ctrl));

        return solo.getView(ctrl);
    }

    public int getCtrlId(String strID){

        int ctrl;
        if(strID.equals("")){
            return -1;
        }
        ctrl = solo.getCurrentActivity().getResources().getIdentifier(strID, "id", solo.getCurrentActivity().getPackageName());
        Log.i("getCtrlId", Integer.toString(ctrl));

        return ctrl;
    }

    /**
     * Enter text to view by id
     * @param strID String
     * @param s String
     * @param iTime int
     * @return int
     */

    public int enterTextById(String strID, String s, int iTime ){

        int ctrl;
        EditText v;

        if(s.equals("") || strID.equals("")){
            return -1;
        }

        ctrl = solo.getCurrentActivity().getResources().getIdentifier(strID, "id",solo.getCurrentActivity().getPackageName());
        v = (EditText) solo.getView(ctrl);

        solo.clearEditText(v);
        solo.enterText(v, s) ;

        if(iTime != 0){
            solo.sleep(iTime);
        }

        return 0;
    }

    /**
     * get string of Toast
     * @return String
     */
    public String getToast(int timeout){
        TextView toastTextView;
        String toastText = "";
        long endTime = SystemClock.uptimeMillis() + timeout;
        while(SystemClock.uptimeMillis() < endTime){
            toastTextView = (TextView)solo.getView("message", 0);
            if(null != toastTextView){
                toastText = toastTextView.getText().toString();
                break;
            }
        }

        return toastText;
    }

    public void logout(int iSleep){

        Common.getInstance().clickCtrlById("right", iSleep);
        int i = 0;
        String strUserName;
        do {
            TextView textView = (TextView)solo.getView("username");
            strUserName = textView.getText().toString();
            solo.sleep(1000);
            i++;
            if(i > 20){
                Log.e("AutoTest—logout", "(AutoTest)Can not return personal center!!!");
                return;
            }
        }while (strUserName.length() == 0);

        Common.getInstance().clickCtrlById("shipping_address", iSleep);
        if(!solo.waitForText(PropertyData.getProperty("MyInformation"))){
            Log.e("AutoTest—logout", "(AutoTest)Can not return my information!!!");
            return;
        }
        Common.getInstance().clickCtrlById("exitLogin", iSleep);
        solo.sleep(1000);
        solo.clickOnButton("确定");
        if(!solo.waitForText(PropertyData.getProperty("Welcome_Regi"))){
            Log.e("AutoTest—logout", "(AutoTest)Can not return my information!!!");
        }
    }

    public void login(int iSleep){

        Common.getInstance().clickCtrlById("login", 0);
        Common.getInstance().enterTextById("username", PropertyData.getProperty("mobile"), 0);
        Common.getInstance().enterTextById("password", PropertyData.getProperty("pwd"), 0);
        Common.getInstance().clickCtrlById("login", iSleep);

    }

    public void goBack(int iNumber, int iTime){
        for (int i = 0; i < iNumber; i++){
            solo.goBack();
            solo.sleep(iTime);
        }
    }
}
