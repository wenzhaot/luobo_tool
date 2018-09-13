package com.umeng.socialize.net.dplus.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText.CACHE;

public class StandardDBHelper extends SQLiteOpenHelper {
    private static Context a = null;
    private String b = null;

    public StandardDBHelper(Context context) {
        super(context, DBConfig.DB_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        a(sQLiteDatabase);
        b(sQLiteDatabase);
        c(sQLiteDatabase);
        d(sQLiteDatabase);
        e(sQLiteDatabase);
    }

    private void a(SQLiteDatabase sQLiteDatabase) {
        try {
            this.b = "create table if not exists stats (Id integer primary key,_json TEXT)";
            sQLiteDatabase.execSQL(this.b);
        } catch (Throwable e) {
            SLog.error(CACHE.CACHEFILE, e);
        }
    }

    private void b(SQLiteDatabase sQLiteDatabase) {
        try {
            this.b = "create table if not exists s_e (Id integer primary key,_json TEXT)";
            sQLiteDatabase.execSQL(this.b);
        } catch (Throwable e) {
            SLog.error(CACHE.CACHEFILE, e);
        }
    }

    private void c(SQLiteDatabase sQLiteDatabase) {
        try {
            this.b = "create table if not exists auth (Id integer primary key,_json TEXT)";
            sQLiteDatabase.execSQL(this.b);
        } catch (Throwable e) {
            SLog.error(CACHE.CACHEFILE, e);
        }
    }

    private void d(SQLiteDatabase sQLiteDatabase) {
        try {
            this.b = "create table if not exists userinfo (Id integer primary key,_json TEXT)";
            sQLiteDatabase.execSQL(this.b);
        } catch (Throwable e) {
            SLog.error(CACHE.CACHEFILE, e);
        }
    }

    private void e(SQLiteDatabase sQLiteDatabase) {
        try {
            this.b = "create table if not exists dau (Id integer primary key,_json TEXT)";
            sQLiteDatabase.execSQL(this.b);
        } catch (Throwable e) {
            SLog.error(CACHE.CACHEFILE, e);
        }
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}
