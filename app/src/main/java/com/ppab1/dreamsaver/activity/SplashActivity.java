package com.ppab1.dreamsaver.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.preference.FirstTimeLaunchPreference;
import com.ppab1.dreamsaver.testing.TestingActivity;

public class SplashActivity extends AppCompatActivity {
    private FirstTimeLaunchPreference firstTimeLaunchPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firstTimeLaunchPreference = new FirstTimeLaunchPreference(this);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (firstTimeLaunchPreference.isFirstTimeLaunch()){
                    startActivity (new Intent(SplashActivity.this, IntroActivity.class));
                } else {
                    startActivity (new Intent(SplashActivity.this, MainActivity.class));
                }
                finish();
            }
        }, 1000L); //3000L = 3 detik
    }
}