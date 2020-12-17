package com.ppab1.dreamsaver.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.adapter.IntroAdapter;
import com.ppab1.dreamsaver.data.ItemIntro;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private IntroAdapter introAdapter;
    private LinearLayout layoutIntroIndicators;
    private MaterialButton buttonIntroAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        layoutIntroIndicators = findViewById(R.id.layoutIntroIndicators);
        //buttonIntroAction = findViewById(R.id.buttonIntroAction);

        setupItemIntros();

        ViewPager2 introViewPager = findViewById(R.id.introViewPager);
        introViewPager.setAdapter(introAdapter);

        DotsIndicator dots = findViewById(R.id.dots_target_intro);
        dots.setViewPager2(introViewPager);
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

    /*private void setupIntroIndicators() {
        ImageView[] indicators = new ImageView[introAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i = 0; i < indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable
            ));
        } */

}