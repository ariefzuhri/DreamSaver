package com.ppab1.dreamsaver.preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    private static final String PREFERENCE_NAME = "user_preference";
    private static final String IS_ENABLE_REMINDER = "is_enable_reminder";
    private static final String REMINDER = "reminder";

    @SuppressLint("CommitPrefEdits")
    public UserPreference(Context context){
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setIsEnableReminder(boolean isEnableReminder){
        editor.putBoolean(IS_ENABLE_REMINDER, isEnableReminder);
        editor.apply();
    }

    public boolean isEnableReminder(){
        return sharedPreferences.getBoolean(IS_ENABLE_REMINDER, true);
    }

    public void setReminder(String reminder){
        editor.putString(REMINDER, reminder);
        editor.apply();
    }

    public String getReminder(){
        return sharedPreferences.getString(REMINDER, "12:00");
    }
}
