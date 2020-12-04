package com.ppab1.dreamsaver.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.ppab1.dreamsaver.database.DatabaseContract.TargetColumns;
import static com.ppab1.dreamsaver.database.DatabaseContract.HistoryColumns;

// Kelas ini disebut DDL (Data Definition Language)
// Perintah SQL yang berhubungan dengan pendefinisian skema suatu database
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_dream_saver";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_TARGET = String.format(
            "CREATE TABLE %s" + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s INTEGER NOT NULL," +
                    " %s INTEGER NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s INTEGER NOT NULL," +
                    " %s INTEGER)",
            TargetColumns.TABLE_NAME,
            TargetColumns._ID,
            TargetColumns.NAME,
            TargetColumns.SAVINGS_TARGET,
            TargetColumns.DAILY_TARGET,
            TargetColumns.DATE_TARGET,
            TargetColumns.TOTAL_SAVINGS,
            TargetColumns.POSITION
    );

    private static final String SQL_CREATE_TABLE_HISTORY = String.format(
            "CREATE TABLE %s" + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s INTEGER REFERENCES %s(%s)," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s INTEGER NOT NULL," +
                    " %s TEXT)",
            HistoryColumns.TABLE_NAME,
            HistoryColumns._ID,
            HistoryColumns.ID_TARGET,
            TargetColumns.TABLE_NAME,
            TargetColumns._ID,
            HistoryColumns.DATE,
            HistoryColumns.TIME,
            HistoryColumns.NOMINAL,
            HistoryColumns.DESC
    );

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TARGET);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TargetColumns.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HistoryColumns.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
