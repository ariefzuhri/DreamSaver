package com.ppab1.dreamsaver.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.ppab1.dreamsaver.R;

import static com.ppab1.dreamsaver.utils.AppUtils.getRemainingDays;
import static com.ppab1.dreamsaver.utils.AppUtils.hideStatusBar;

public class TestingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        hideStatusBar(this, getSupportActionBar());
        Log.d(getClass().getSimpleName(), "Sisa hari: " + getRemainingDays(10000, 500, 700));
    }
}