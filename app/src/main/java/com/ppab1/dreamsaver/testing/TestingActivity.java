package com.ppab1.dreamsaver.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.activity.MainActivity;

import static com.ppab1.dreamsaver.utils.AppUtils.getRemainingDays;
import static com.ppab1.dreamsaver.utils.AppUtils.hideStatusBar;

public class TestingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        hideStatusBar(this, getSupportActionBar());
        Log.d(getClass().getSimpleName(), "Sisa hari: " + getRemainingDays(10000, 500, 700));

        Button btnStart = findViewById(R.id.btn_next_intro_testing);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestingActivity.this, MainActivity.class));
            }
        });
    }
}