package com.ppab1.dreamsaver.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.adapter.IntroAdapter;
import com.ppab1.dreamsaver.model.Intro;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {
    private IntroAdapter adapter;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        setupItemIntros();

        ViewPager2 viewPager = findViewById(R.id.introViewPager);
        viewPager.setAdapter(adapter);

        DotsIndicator dots = findViewById(R.id.dots_target_intro);
        dots.setViewPager2(viewPager);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position < adapter.getItemCount()-1){
                    btnNext.setText("Lanjut");
                } else {
                    btnNext.setText("Mulai");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        btnNext = findViewById(R.id.btn_next_intro);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnNext.getText() == "Lanjut"){
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
                } else {
                    startActivity(new Intent(IntroActivity.this, WelcomeActivity.class));
                    finish();
                }
            }
        });

        ImageButton btnPrev = findViewById(R.id.btn_prev_intro);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1, true);
            }
        });
    }

    private void setupItemIntros(){
        List<Intro> introList = new ArrayList<>();

        Intro itemStar = new Intro();
        itemStar.setTitle("Raih Mimpimu Mulai Sekarang");
        itemStar.setDescription("Dream Saver membantumu membuat rencana tabungan impian sesuai keinginanmu.");
        itemStar.setImage(R.drawable.ic_star);

        Intro itemTarget = new Intro();
        itemTarget.setTitle("Atur dan Kelola Targetmu");
        itemTarget.setDescription("Tidak perlu khawatir! Dream Saver akan memudahkanmu dalam mengatur target tabunganmu.");
        itemTarget.setImage(R.drawable.ic_target);

        Intro itemNotification = new Intro();
        itemNotification.setTitle("Tidak Lagi Lupa Menabung");
        itemNotification.setDescription("Dream Saver siap mengirimkan notifikasi sebagai pengingat progres tabunganmu setiap hari.");
        itemNotification.setImage(R.drawable.ic_notification);

        introList.add(itemStar);
        introList.add(itemTarget);
        introList.add(itemNotification);

        adapter = new IntroAdapter(introList);

    }
}