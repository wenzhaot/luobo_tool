package com.feng.library.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedUtil {
    private static final String SHARED_ARTICLE_POSITION_RECORDS_KEY = "article_position_records_key";
    private static final String SHARED_ARTICLE_POSITION_RECORD_PATH = "article_position_record_sp";
    private static final String SHARED_DEFAULT_PATH = "feng_sp";
    private static String SHARED_PATH = SHARED_DEFAULT_PATH;

    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        if (context == null) {
            return null;
        }
        return context.getSharedPreferences(SHARED_PATH, 0);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        if (sharedPreferences != null) {
            Editor edit = sharedPreferences.edit();
            edit.putInt(key, value);
            edit.commit();
        }
    }

    public static void putArticlePositionRecords(Context context, String value) {
        SHARED_PATH = SHARED_ARTICLE_POSITION_RECORD_PATH;
        putString(context, SHARED_ARTICLE_POSITION_RECORDS_KEY, value);
        SHARED_PATH = SHARED_DEFAULT_PATH;
    }

    public static String getArticlePositionRecords(Context context) {
        SHARED_PATH = SHARED_ARTICLE_POSITION_RECORD_PATH;
        String value = getString(context, SHARED_ARTICLE_POSITION_RECORDS_KEY);
        SHARED_PATH = SHARED_DEFAULT_PATH;
        return value;
    }

    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        if (sharedPreferences == null) {
            return 0;
        }
        return sharedPreferences.getInt(key, defValue);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        if (sharedPreferences != null) {
            Editor edit = sharedPreferences.edit();
            edit.putString(key, value);
            edit.commit();
        }
    }

    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        if (sharedPreferences == null) {
            return "";
        }
        return sharedPreferences.getString(key, "");
    }

    public static void putLong(Context context, String key, long value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        if (sharedPreferences != null) {
            Editor edit = sharedPreferences.edit();
            edit.putLong(key, value);
            edit.commit();
        }
    }

    public static long getLong(Context context, String key) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        if (sharedPreferences == null) {
            return 0;
        }
        return sharedPreferences.getLong(key, 0);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        if (sharedPreferences != null) {
            Editor edit = sharedPreferences.edit();
            edit.putBoolean(key, value);
            edit.commit();
        }
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sharedPreferences = getDefaultSharedPreferences(context);
        if (sharedPreferences == null) {
            return false;
        }
        return sharedPreferences.getBoolean(key, defValue);
    }
}
