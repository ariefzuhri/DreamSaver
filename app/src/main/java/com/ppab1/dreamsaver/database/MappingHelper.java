package com.ppab1.dreamsaver.database;

import android.database.Cursor;

import com.ppab1.dreamsaver.model.History;
import com.ppab1.dreamsaver.model.Target;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Target> mapCursorToTargetList(Cursor cursor){
        ArrayList<Target> targetList = new ArrayList<>();

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TargetColumns._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TargetColumns.NAME));
            int savingsTarget = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TargetColumns.SAVINGS_TARGET));
            int dailyTarget = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TargetColumns.DAILY_TARGET));
            String dateTarget = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TargetColumns.DATE_TARGET));
            int totalSavings = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TargetColumns.TOTAL_SAVINGS));
            int position = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TargetColumns.POSITION));

            targetList.add(new Target(id, name, savingsTarget, dailyTarget, dateTarget, totalSavings, position));
        }

        return targetList;
    }
    public static ArrayList<History> mapCursorToHistoryList(Cursor cursor){
        ArrayList<History> historyList = new ArrayList<>();

        while (cursor.moveToNext()){
            int id_history = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.HistoryColumns._ID));
            int id_target = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.HistoryColumns.ID_TARGET));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.HistoryColumns.DATE));
            String time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.HistoryColumns.TIME));
            int nominal = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.HistoryColumns.NOMINAL));
            String desc = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.HistoryColumns.DESC));

            historyList.add(new History(id_history, id_target, date, time, nominal, desc));
        }

        return historyList;
    }
}
