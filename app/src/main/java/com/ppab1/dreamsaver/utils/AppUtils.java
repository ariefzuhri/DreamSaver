package com.ppab1.dreamsaver.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import java.text.NumberFormat;
import java.util.Locale;

public class AppUtils {
    public static Locale locale = new Locale("in", "ID");

    public static void hideStatusBar(Activity activity, ActionBar actionBar){
        if (actionBar != null){
            actionBar.hide();
            if (Build.VERSION.SDK_INT >= 21) { // Mengubah notif bar menjadi transparan
                Window window = activity.getWindow();
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static String getRupiahFormat(long amount){
        String country = "ID", language = "in";
        return "Rp" + NumberFormat.getNumberInstance(new Locale(language, country)).format(amount);
    }

    // Mendapatkan sisa hari
    public static long getRemainingDays(long savingTarget, long totalSavings, long dailyTarget){
        return (int) Math.ceil(((double) savingTarget - (double) totalSavings)/(double) dailyTarget);
    }
}
