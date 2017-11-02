package com.ecovacs.ecosphere.intl;

import com.ecovacs.ecosphere.intl.activity.WelcomeActivity;
import com.robotium.solo.Solo;

/**
 * Created by ecosqa on 17/2/7.
 *
 */

public class ActivityTranslate {
    private static ActivityTranslate activityTranslate = null;
    private Solo solo = null;

    private ActivityTranslate(){

    }

    public static ActivityTranslate getInstance(){
        if (activityTranslate == null){
            activityTranslate = new ActivityTranslate();
        }
        return activityTranslate;
    }

    public void init(Solo solo){
        this.solo = solo;
    }

    public boolean translateWelcome(){
        WelcomeActivity.getInstance().init(solo);
        WelcomeActivity.getInstance().translate();
        return true;
    }
}
