package com.umeng.socialize.net.dplus.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.umeng.socialize.utils.ContextUtil;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DBManager {
    private static DBManager a = null;
    private static StandardDBHelper b = null;

    public static synchronized DBManager get(Context context) {
        DBManager dBManager;
        synchronized (DBManager.class) {
            if (a == null) {
                a = new DBManager();
            }
            dBManager = a;
        }
        return dBManager;
    }

    private DBManager() {
        b = new StandardDBHelper(ContextUtil.getContext());
    }

    public synchronized void insertS_E(JSONObject jSONObject) {
        if (jSONObject != null) {
            SQLiteDatabase writableDatabase = b.getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBConfig.VALUE, jSONObject.toString());
            writableDatabase.insert("s_e", null, contentValues);
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
        }
    }

    public synchronized void insertAuth(JSONObject jSONObject) {
        if (jSONObject != null) {
            SQLiteDatabase writableDatabase = b.getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBConfig.VALUE, jSONObject.toString());
            writableDatabase.insert("auth", null, contentValues);
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
        }
    }

    public synchronized void insertUserInfo(JSONObject jSONObject) {
        if (jSONObject != null) {
            SQLiteDatabase writableDatabase = b.getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBConfig.VALUE, jSONObject.toString());
            writableDatabase.insert("userinfo", null, contentValues);
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
        }
    }

    public synchronized void insertDau(JSONObject jSONObject) {
        if (jSONObject != null) {
            SQLiteDatabase writableDatabase = b.getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBConfig.VALUE, jSONObject.toString());
            writableDatabase.insert("dau", null, contentValues);
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
        }
    }

    public synchronized void insertStats(JSONObject jSONObject) {
        if (jSONObject != null) {
            SQLiteDatabase writableDatabase = b.getWritableDatabase();
            writableDatabase.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBConfig.VALUE, jSONObject.toString());
            writableDatabase.insert("stats", null, contentValues);
            writableDatabase.setTransactionSuccessful();
            writableDatabase.endTransaction();
        }
    }

    public synchronized void deleteTable(String str) {
        SQLiteDatabase writableDatabase = b.getWritableDatabase();
        writableDatabase.beginTransaction();
        writableDatabase.execSQL("DELETE FROM " + str + ";");
        writableDatabase.setTransactionSuccessful();
        writableDatabase.endTransaction();
    }

    public synchronized void delete(ArrayList<Integer> arrayList, String str) {
        SQLiteDatabase writableDatabase = b.getWritableDatabase();
        writableDatabase.beginTransaction();
        for (int i = 0; i < arrayList.size(); i++) {
            writableDatabase.execSQL("delete from " + str + " where Id='" + arrayList.get(i) + "' ");
        }
        writableDatabase.setTransactionSuccessful();
        writableDatabase.endTransaction();
    }

    public synchronized JSONArray select(String str, ArrayList<Integer> arrayList, double d, boolean z) throws JSONException {
        JSONArray jSONArray;
        SQLiteDatabase writableDatabase = b.getWritableDatabase();
        jSONArray = new JSONArray();
        writableDatabase.beginTransaction();
        Cursor rawQuery = writableDatabase.rawQuery("select * from " + str, null);
        while (rawQuery.moveToNext()) {
            int i = rawQuery.getInt(0);
            String string = rawQuery.getString(1);
            if (z && ((double) (jSONArray.toString().getBytes().length + string.getBytes().length)) > d) {
                break;
            }
            jSONArray.put(new JSONObject(string));
            if (!arrayList.contains(Integer.valueOf(i))) {
                arrayList.add(Integer.valueOf(i));
            }
        }
        rawQuery.close();
        writableDatabase.setTransactionSuccessful();
        writableDatabase.endTransaction();
        return jSONArray;
    }

    public synchronized void closeDatabase() {
        b.close();
    }
}
