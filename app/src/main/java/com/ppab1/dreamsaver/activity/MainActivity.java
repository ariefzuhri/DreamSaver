package com.ppab1.dreamsaver.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import com.ppab1.dreamsaver.R;
import com.ppab1.dreamsaver.adapter.TargetAdapter;
import com.ppab1.dreamsaver.model.Target;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import java.util.ArrayList;

import static com.ppab1.dreamsaver.testing.DummyTarget.getDummyTarget;
import static com.ppab1.dreamsaver.utils.AppUtils.hideStatusBar;
import static com.ppab1.dreamsaver.utils.AppUtils.showToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private PopupMenu menu;
    private ImageButton btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideStatusBar(this, getSupportActionBar());

        TargetAdapter adapter = new TargetAdapter(this);
        adapter.notifyDataSetChanged();
        loadDummyTarget(adapter);

        ViewPager2 viewPager = findViewById(R.id.vp_target_main);
        viewPager.setAdapter(adapter);

        DotsIndicator dots = findViewById(R.id.dots_target_main);
        dots.setViewPager2(viewPager);

        // Disable overscroll animation
        View child = viewPager.getChildAt(0);
        if (child instanceof RecyclerView) child.setOverScrollMode(View.OVER_SCROLL_NEVER);

        ImageButton btnAdd = findViewById(R.id.btn_add_main);
        btnAdd.setOnClickListener(this);
        btnMenu = findViewById(R.id.btn_menu_main);
        btnMenu.setOnClickListener(this);

        initializeMenu();
    }

    private void initializeMenu() {
        menu = new PopupMenu(this, btnMenu);
        MenuInflater inflater = menu.getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_manage_target_main:
                        showToast(MainActivity.this, getString(R.string.title_manage_target));
                        break;

                    case R.id.menu_settings_main:
                        showToast(MainActivity.this, getString(R.string.title_settings));
                        break;

                    case R.id.menu_about_main:
                        showToast(MainActivity.this, getString(R.string.title_about));
                        break;
                }
                return false;
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_main:
                showToast(MainActivity.this, getString(R.string.title_add_aut));
                break;

            case R.id.btn_menu_main:
                menu.show();
                break;
        }
    }

    private void loadDummyTarget(TargetAdapter adapter) {
        ArrayList<Target> dummyData = getDummyTarget();
        ArrayList<Target> dummyTarget = new ArrayList<>();
        for (Target target : dummyData){
            if (target.getTotalSavings() < target.getSavingsTarget()) dummyTarget.add(target);
        }

        /*ArrayList<History> dummyHistory = getDummyHistory();
        for (Target target : dummyTarget){
            for (History history : dummyHistory){
                if (target.getId() == history.getId_target()){
                    target.setTotalSavings(target.getTotalSavings()+history.getNominal());
                }
            }
        }*/

        adapter.setData(dummyTarget);
    }
}