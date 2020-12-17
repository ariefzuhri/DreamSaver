package com.ppab1.dreamsaver.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.ppab1.dreamsaver.database.DatabaseContract.TargetColumns;
import static com.ppab1.dreamsaver.database.DatabaseContract.TargetColumns.POSITION;
import static com.ppab1.dreamsaver.database.DatabaseContract.TargetColumns.SAVINGS_TARGET;
import static com.ppab1.dreamsaver.database.DatabaseContract.TargetColumns.TOTAL_SAVINGS;

// Kelas ini disebut DML (Data Manipulation Language)
// Perintah SQL yang berhubungan dengan pengolahan data dalam tabel
public class TargetHelper {
    private static final String DATABASE_TABLE = TargetColumns.TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static TargetHelper INSTANCE;
    private static SQLiteDatabase database;

    TargetHelper(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    static TargetHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null) INSTANCE = new TargetHelper(context);
            }
        }
        return INSTANCE;
    }

    void open() throws SQLException{
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();
        if (database.isOpen()) database.close();
    }

    Cursor queryAll(){
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                POSITION + " ASC"
        );
    }

    Cursor queryById(String id){
        return database.query(
                DATABASE_TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                POSITION + " ASC"
        );
    }

    Cursor queryByStatus(boolean isFinished){
        String selection;
        if (isFinished) selection = TOTAL_SAVINGS + " - " + SAVINGS_TARGET + " >= ?";
        else selection =  TOTAL_SAVINGS + " - " + SAVINGS_TARGET + " < ?";

        return database.query(
                DATABASE_TABLE,
                null,
                selection,
                new String[]{"0"},
                null,
                null,
                POSITION + " ASC"
        );
    }

    long insert(ContentValues contentValues){
        return database.insert(
                DATABASE_TABLE,
                null,
                contentValues
        );
    }

    int update(String id, ContentValues contentValues){
        return database.update(
                DATABASE_TABLE,
                contentValues,
                _ID + " = ?",
                new String[]{id}
        );
    }

    int delete(String id){
        return database.delete(
                DATABASE_TABLE,
                _ID + " = ?",
                new String[]{id}
        );
    }
}
