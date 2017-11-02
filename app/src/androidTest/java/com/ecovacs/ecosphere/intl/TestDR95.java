package com.ecovacs.ecosphere.intl;

import android.app.Activity;
import android.test.SingleLaunchActivityTestCase;
import android.util.Log;

import com.ecovacs.ecosphere.intl.common.Common;
import com.ecovacs.ecosphere.intl.common.LogcatHelper;
import com.ecovacs.ecosphere.intl.common.PropertyData;
import com.robotium.solo.Solo;

/**
 * Created by lily.shan on 2016/9/22.
 *
 */
public class TestDR95 extends SingleLaunchActivityTestCase {
    //private Activity mActivity;

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.ecovacs.ecosphere.ui.WelcomeActivity";
    private static final String packageName = "com.ecovacs.ecosphere.intl";
    private static Solo solo = null;
    private static int run = 0;
    private static final int NUMBER_TOTAL_CASES = 2;
    private Activity mActivity;

    private static Class<?> launcherActivityClass;
    static{
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public TestDR95() throws ClassNotFoundException {
        super(packageName, launcherActivityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        /*if(solo == null) {
            ToastTest.solo = new Solo(getInstrumentation(), mActivity);
        }*/
        mActivity = getActivity();
        solo = new Solo(getInstrumentation(), mActivity);
        //init
        Common.getInstance().init(solo);
        PropertyData.setFile("commonData.properties");
        DR95Clean.getInstance().init(solo);
        ActivityTranslate.getInstance().init(solo);
        Log.e("AutoTest", "(setUp)end setUp");
    }

    @Override
    public void tearDown() throws Exception {
        run += countTestCases();
        Log.e("tearDown", "(tearDown)run test case: " + Integer.toString(run));
        if(run > NUMBER_TOTAL_CASES) {
            solo.finishOpenedActivities();
        }
        super.tearDown();
        solo.sleep(5000);
        Log.e("AutoTest", "(tearDown)end teardown");
        LogcatHelper.getInstance(mActivity).stop();

    }

    /*public void test001(){
       LogcatHelper.getInstance(mActivity).start();
       assertTrue(DR95Clean.getInstance().regressLogin());
    }

    public void test002(){
        assertTrue(DR95Clean.getInstance().login_multiCountry());
    }*/

    public void test003(){
        assertTrue(ActivityTranslate.getInstance().translateWelcome());
    }


}
