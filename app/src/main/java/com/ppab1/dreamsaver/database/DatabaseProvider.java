package com.ppab1.dreamsaver.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.ppab1.dreamsaver.database.DatabaseContract.TargetColumns;
import static com.ppab1.dreamsaver.database.DatabaseContract.HistoryColumns;
import static com.ppab1.dreamsaver.database.DatabaseContract.AUTHORITY;

public class DatabaseProvider extends ContentProvider {
    private static final int TARGET = 100;
    private static final int TARGET_ID = 101;
    private static final int HISTORY = 200;
    private static final int HISTORY_ID = 201;
    private static final int HISTORY_TARGET_ID = 202;
    private static final int HISTORY_TARGET_ID_TODAY = 203;

    private TargetHelper targetHelper;
    private HistoryHelper historyHelper;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, TargetColumns.TABLE_NAME, TARGET);
        uriMatcher.addURI(AUTHORITY, TargetColumns.TABLE_NAME + "/#", TARGET_ID);
        uriMatcher.addURI(AUTHORITY, HistoryColumns.TABLE_NAME, HISTORY);
        uriMatcher.addURI(AUTHORITY, HistoryColumns.TABLE_NAME + "/#", HISTORY_ID);
        uriMatcher.addURI(AUTHORITY, HistoryColumns.TABLE_NAME + "/targetId/#", HISTORY_TARGET_ID);
        uriMatcher.addURI(AUTHORITY, HistoryColumns.TABLE_NAME + "/targetId/today/#", HISTORY_TARGET_ID_TODAY);
    }

    public DatabaseProvider(){}

    @Override
    public boolean onCreate() {
        targetHelper = TargetHelper.getInstance(getContext());
        targetHelper.open();

        historyHelper = HistoryHelper.getInstance(getContext());
        historyHelper.open();

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor cursor;

        switch (uriMatcher.match(uri)){
            case TARGET:
                cursor = targetHelper.queryAll();
                break;

            case TARGET_ID:
                cursor = targetHelper.queryById(uri.getLastPathSegment());
                break;

            case HISTORY:
                cursor = historyHelper.queryAll();
                break;

            case HISTORY_ID:
                cursor = historyHelper.queryById(uri.getLastPathSegment());
                break;

            case HISTORY_TARGET_ID:
                cursor = historyHelper.queryByTargetId(uri.getLastPathSegment());
                break;

            case HISTORY_TARGET_ID_TODAY:
                cursor = historyHelper.queryByTargetIdToday(uri.getLastPathSegment());
                break;

            default:
                cursor = null;
                break;
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long added;

        switch (uriMatcher.match(uri)){
            case TARGET:
                added = targetHelper.insert(contentValues);

                contentValues.put(TargetColumns.POSITION, added);
                targetHelper.update(String.valueOf(added), contentValues);
                break;

            case HISTORY:
                added = historyHelper.insert(contentValues);
                break;

            default:
                added = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(uri + "/" + added);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int updated;

        switch (uriMatcher.match(uri)){
            case TARGET_ID:
                updated = targetHelper.update(uri.getLastPathSegment(), contentValues);
                break;

            case HISTORY_ID:
                updated = historyHelper.update(uri.getLastPathSegment(), contentValues);
                break;

            default:
                updated = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int deleted;

        switch (uriMatcher.match(uri)){
            case TARGET_ID:
                deleted = targetHelper.delete(uri.getLastPathSegment());
                break;

            case HISTORY_ID:
                deleted = historyHelper.delete(uri.getLastPathSegment());
                break;

            default:
                deleted = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return deleted;
    }
}
