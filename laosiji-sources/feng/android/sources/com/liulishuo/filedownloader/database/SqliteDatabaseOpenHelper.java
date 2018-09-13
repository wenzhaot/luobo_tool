package com.liulishuo.filedownloader.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;

public class SqliteDatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "filedownloader.db";
    private static final int DATABASE_VERSION = 3;

    public SqliteDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (VERSION.SDK_INT >= 16) {
            setWriteAheadLoggingEnabled(true);
        } else if (VERSION.SDK_INT >= 11) {
            db.enableWriteAheadLogging();
        }
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS filedownloader( _id INTEGER PRIMARY KEY, url VARCHAR, path VARCHAR, status TINYINT(7), sofar INTEGER, total INTEGER, errMsg VARCHAR, etag VARCHAR, pathAsDirectory TINYINT(1) DEFAULT 0, filename VARCHAR, connectionCount INTEGER DEFAULT 1)");
        db.execSQL("CREATE TABLE IF NOT EXISTS filedownloaderConnection( id INTEGER, connectionIndex INTEGER, startOffset INTEGER, currentOffset INTEGER, endOffset INTEGER, PRIMARY KEY ( id, connectionIndex ))");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE filedownloader ADD COLUMN pathAsDirectory TINYINT(1) DEFAULT 0");
            db.execSQL("ALTER TABLE filedownloader ADD COLUMN filename VARCHAR");
        }
        if (oldVersion < 3) {
            String addConnectionCount = "ALTER TABLE filedownloader ADD COLUMN connectionCount INTEGER DEFAULT 1";
            db.execSQL("ALTER TABLE filedownloader ADD COLUMN connectionCount INTEGER DEFAULT 1");
            db.execSQL("CREATE TABLE IF NOT EXISTS filedownloaderConnection( id INTEGER, connectionIndex INTEGER, startOffset INTEGER, currentOffset INTEGER, endOffset INTEGER, PRIMARY KEY ( id, connectionIndex ))");
        }
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.delete(SqliteDatabaseImpl.TABLE_NAME, null, null);
        db.delete(SqliteDatabaseImpl.CONNECTION_TABLE_NAME, null, null);
    }
}
