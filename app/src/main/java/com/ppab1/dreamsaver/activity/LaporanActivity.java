package com.ppab1.dreamsaver.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ppab1.dreamsaver.R;

public class LaporanActivity extends AppCompatActivity {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);

        TextView tvTitle = findViewById(R.id.tv_title_title);
        tvTitle.setText(R.id.tv_name_target);

        ImageButton buttonTitle = findViewById(R.id.ib_back_title);
        buttonTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }
}