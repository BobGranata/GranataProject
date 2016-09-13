package com.bobgranata.linkservice.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.bobgranata.linkservice.R;

import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends Activity {

    // это будет именем файла настроек
    public static final String APP_PREFERENCES = "ls_param";

    Timer SplashTimer;
    SharedPreferences mSettings;
    Context mmContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        mmContext = getApplicationContext();
        SplashTimer = new Timer();

//        if (NetworkManager.isOnline(this)) {
            SplashTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    MainScreen();
                }
            }, 1500);
//        } else {
//            Toast.makeText(this, "ERROR_OFFLINE",
//                    Toast.LENGTH_SHORT).show();
//            SplashTimer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    finish();
//                }
//            }, 4000);
//        }
    }

    private void MainScreen() {
        SplashTimer.cancel();
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
