package com.ppab1.dreamsaver.database;

import android.database.Cursor;

import com.ppab1.dreamsaver.model.Target;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Target> mapCursorToArrayList(Cursor cursor){
        ArrayList<Target> targetList = new ArrayList<>();

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TargetColumns._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TargetColumns.NAME));
            int savingsTarget = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TargetColumns.SAVINGS_TARGET));
            int dailyTarget = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TargetColumns.DAILY_TARGET));
            String date_target = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TargetColumns.DATE_TARGET));
            int totalSavings = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TargetColumns.TOTAL_SAVINGS));
            int position = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TargetColumns.POSITION));

            targetList.add(new Target(id, name, savingsTarget, dailyTarget, date_target, totalSavings, position));
        }

        return targetList;
    }
}
