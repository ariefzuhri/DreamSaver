package com.ppab1.dreamsaver.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.adapter.IntroAdapter;
import com.ppab1.dreamsaver.data.ItemIntro;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private IntroAdapter introAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        setupItemIntros();

        ViewPager2 introViewPager = findViewById(R.id.introViewPager);
        introViewPager.setAdapter(introAdapter);
    }

    private void setupItemIntros(){

        List<ItemIntro> itemIntros = new ArrayList<>();

        ItemIntro itemStar = new ItemIntro();
        itemStar.setTitle("Raih Mimpimu Mulai Sekarang");
        itemStar.setDescription("Dream Saver membantumu membuat rencana tabungan impian sesuai keinginanmu.");
        itemStar.setImage(R.drawable.ic_star);

        ItemIntro itemTarget = new ItemIntro();
        itemTarget.setTitle("Atur dan Kelola Targetmu");
        itemTarget.setDescription("Tidak perlu khawatir! Dream Saver akan memudahkanmu dalam mengatur target tabunganmu.");
        itemTarget.setImage(R.drawable.ic_target);

        ItemIntro itemNotification = new ItemIntro();
        itemNotification.setTitle("Tidak Lagi Lupa Menabung");
        itemNotification.setDescription("Dream Saver siap mengirimkan notifikasi sebagai pengingat progres tabunganmu setiap hari.");
        itemNotification.setImage(R.drawable.ic_notification);

        itemIntros.add(itemStar);
        itemIntros.add(itemTarget);
        itemIntros.add(itemNotification);

        introAdapter = new IntroAdapter(itemIntros);


    }
}