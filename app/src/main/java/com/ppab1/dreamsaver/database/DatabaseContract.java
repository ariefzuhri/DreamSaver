package com.ppab1.dreamsaver.database;

import android.net.Uri;
import android.provider.BaseColumns;

// Kelas ini untuk mempermudah akses nama tabel dan nama kolom di dalam database
public class DatabaseContract {
    public static final String AUTHORITY = "com.ppab1.dreamsaver";
    private static final String SCHEME = "content";

    // Kolom _ID sudah ada secara otomatis di dalam kelas BaseColumns
    public static final class TargetColumns implements BaseColumns{
        public static String TABLE_NAME = "target";
        public static String NAME = "name";
        public static String SAVINGS_TARGET = "savings_target";
        public static String DAILY_TARGET = "daily_target";
        public static String DATE_TARGET = "date_target";
        public static String TOTAL_SAVINGS = "total_savings";
        public static String POSITION = "position";

        // Definisikan content uri (content://com.ppab1.dreamsaver/target)
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }

    public static final class HistoryColumns implements BaseColumns{
        public static String TABLE_NAME = "history";
        public static String ID_TARGET = "id_target";
        public static String DATE = "date";
        public static String TIME = "time";
        public static String NOMINAL = "nominal";
        public static String DESC = "desc";

        // Definisikan content uri (content://com.ppab1.dreamsaver/history)
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
