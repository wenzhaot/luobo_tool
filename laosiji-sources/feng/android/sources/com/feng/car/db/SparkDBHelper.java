package com.feng.car.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SparkDBHelper extends SQLiteOpenHelper {
    public static final String CAR_COMPARISON = "car_comparison";
    public static final String FENG_DBNAME = "feng.db";
    public static final String POST_DRAFTS = "post_drafts";
    public static final String RECOMMEND_HISTORY = "recommend_history";
    public static final String SEARCH_RECORDS = "search_records";
    public static final String VIDEO_CACHE = "video_cache";

    public SparkDBHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SparkDBHelper(Context context) {
        this(context, FENG_DBNAME, null, 8);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE table IF NOT EXISTS post_drafts (_id INTEGER PRIMARY KEY AUTOINCREMENT,resources_id TEXT,title TEXT, description TEXT,coverjson TEXT, postjson TEXT,goodsjson TEXT,servelistjson TEXT,topiclistjson TEXT,user_id INTEGER,type INTEGER,check_meanwhile INTEGER,date DATETIME DEFAULT (datetime('now','localtime')))");
        db.execSQL("CREATE table IF NOT EXISTS search_records (_id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT,content_id INTEGER, type INTEGER, date DATETIME DEFAULT (datetime('now','localtime')))");
        db.execSQL("CREATE table IF NOT EXISTS car_comparison (_id INTEGER PRIMARY KEY AUTOINCREMENT,id INTEGER, name TEXT, date DATETIME DEFAULT (datetime('now','localtime')))");
        db.execSQL("CREATE table IF NOT EXISTS video_cache (_id INTEGER PRIMARY KEY AUTOINCREMENT, url TEXT,mediaid INTEGER, data TEXT)");
        db.execSQL("CREATE table IF NOT EXISTS recommend_history (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER,json_data TEXT)");
    }

    /* JADX WARNING: Missing block: B:2:0x000a, code:
            r3.execSQL("ALTER TABLE post_drafts ADD coverjson TEXT");
     */
    /* JADX WARNING: Missing block: B:3:0x0010, code:
            r3.execSQL("ALTER TABLE search_records ADD content_id INTEGER");
     */
    /* JADX WARNING: Missing block: B:4:0x0016, code:
            r3.execSQL("CREATE table IF NOT EXISTS video_cache (_id INTEGER PRIMARY KEY AUTOINCREMENT, url TEXT,mediaid INTEGER, data TEXT)");
     */
    /* JADX WARNING: Missing block: B:5:0x001c, code:
            r3.execSQL("ALTER TABLE post_drafts ADD goodsjson TEXT");
            r3.execSQL("ALTER TABLE post_drafts ADD servelistjson TEXT");
     */
    /* JADX WARNING: Missing block: B:6:0x0028, code:
            r3.execSQL("CREATE table IF NOT EXISTS recommend_history (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER,json_data TEXT)");
     */
    /* JADX WARNING: Missing block: B:7:0x002e, code:
            r3.execSQL("ALTER TABLE post_drafts ADD topiclistjson TEXT");
            r3.execSQL("delete from post_drafts");
     */
    /* JADX WARNING: Missing block: B:9:?, code:
            return;
     */
    public void onUpgrade(android.database.sqlite.SQLiteDatabase r3, int r4, int r5) {
        /*
        r2 = this;
        switch(r4) {
            case 1: goto L_0x0004;
            case 2: goto L_0x000a;
            case 3: goto L_0x0010;
            case 4: goto L_0x0016;
            case 5: goto L_0x001c;
            case 6: goto L_0x0028;
            case 7: goto L_0x002e;
            default: goto L_0x0003;
        };
    L_0x0003:
        return;
    L_0x0004:
        r1 = "CREATE table IF NOT EXISTS car_comparison (_id INTEGER PRIMARY KEY AUTOINCREMENT,id INTEGER, name TEXT, date DATETIME DEFAULT (datetime('now','localtime')))";
        r3.execSQL(r1);
    L_0x000a:
        r0 = "ALTER TABLE post_drafts ADD coverjson TEXT";
        r3.execSQL(r0);
    L_0x0010:
        r0 = "ALTER TABLE search_records ADD content_id INTEGER";
        r3.execSQL(r0);
    L_0x0016:
        r1 = "CREATE table IF NOT EXISTS video_cache (_id INTEGER PRIMARY KEY AUTOINCREMENT, url TEXT,mediaid INTEGER, data TEXT)";
        r3.execSQL(r1);
    L_0x001c:
        r0 = "ALTER TABLE post_drafts ADD goodsjson TEXT";
        r3.execSQL(r0);
        r0 = "ALTER TABLE post_drafts ADD servelistjson TEXT";
        r3.execSQL(r0);
    L_0x0028:
        r1 = "CREATE table IF NOT EXISTS recommend_history (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER,json_data TEXT)";
        r3.execSQL(r1);
    L_0x002e:
        r0 = "ALTER TABLE post_drafts ADD topiclistjson TEXT";
        r3.execSQL(r0);
        r0 = "delete from post_drafts";
        r3.execSQL(r0);
        goto L_0x0003;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.db.SparkDBHelper.onUpgrade(android.database.sqlite.SQLiteDatabase, int, int):void");
    }
}
