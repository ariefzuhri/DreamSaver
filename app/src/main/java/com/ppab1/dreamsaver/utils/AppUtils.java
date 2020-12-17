package com.ppab1.dreamsaver.utils;

import android.content.Context;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class AppUtils {
    public static Locale locale = new Locale("in", "ID");

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static String getRupiahFormat(long amount){
        String country = "ID", language = "in";
        return "Rp" + NumberFormat.getNumberInstance(new Locale(language, country)).format(amount);
    }

    // Mendapatkan sisa hari
    public static int getRemainingDays(long savingsTarget, long totalSavings, long dailyTarget){
        int remainingDays = (int) Math.ceil(((double) savingsTarget - (double) totalSavings)/(double) dailyTarget);
        if (remainingDays < 0) remainingDays = 0;
        return remainingDays;
    }
}
