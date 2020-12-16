package com.ppab1.dreamsaver.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.ppab1.dreamsaver.R;

public class RencanaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_rencana);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Berjalan"));
        tabLayout.addTab(tabLayout.newTab().setText("Selesai"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        TextView textTitle = findViewById(R.id.tv_title_title);
        textTitle.setText("Kelola Rencana");

        ImageButton buttonTitle = findViewById(R.id.btn_back_title);
        buttonTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final ViewPager viewPager = findViewById(R.id.pager);
        PagerAdapter adapter = new com.ppab1.dreamsaver.adapter.PagerAdapter()
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.
                TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab){

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }
}