package com.eysoft.eyhost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.eysoft.eyhost.R;

/**
 * Created by EySoft IT Solution on 2/24/2016.
 */
public class SplashScreen extends Activity {

    Utility utility;
    String prefFileName = "login_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        utility = new Utility(SplashScreen.this);

        /*
        * This condition check if Shared Preference file is exists or not.
        * If exists then it do nothing
        * If not exists then it creates one with default value
        * */
        if(utility.checkPrefFile()==false){
            utility.updatePreFile("no",0,"","");
        }

        /*
        * This is used to stay on the following page for 3 sec(s).
        * Then it will check network status on the device.
        * If network is available, then it will take user to LogIn Activity
        * Else it will take user to NoInternet Activity, where user can try again to connect.
        * */
        new CountDownTimer(1000, 3000) {
            @Override
            public void onTick(long l) {
                //DO Nothing
            }

            @Override
            public void onFinish() {
                /*
                * Here network availability has checked
                * */
                if(utility.isNetworkAvailable()) {
                    /*
                    * Here login status has been checked
                    * */
                    if (utility.checkLogInStatus()) {
                        Intent i = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else{
                        Intent i = new Intent(SplashScreen.this, LogIn.class);
                        startActivity(i);
                        finish();
                    }
                }
                else{
                    Intent i = new Intent(SplashScreen.this, NoInternet.class);
                    startActivity(i);
                    finish();
                }
            }
        }.start();
    }
}
