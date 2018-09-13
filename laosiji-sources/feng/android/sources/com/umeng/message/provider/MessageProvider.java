package com.umeng.message.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.umeng.message.proguard.l;
import com.umeng.message.proguard.m;
import java.util.ArrayList;
import java.util.Iterator;

public class MessageProvider extends ContentProvider {
    private static final String a = MessageProvider.class.getSimpleName();
    private static final UriMatcher b = new UriMatcher(-1);
    private static final int g = 1;
    private static final int h = 2;
    private static final int i = 3;
    private static final int j = 4;
    private static final int k = 5;
    private static final int l = 6;
    private static final int m = 7;
    private static final int n = 8;
    private static final int o = 9;
    private static final int p = 10;
    private static Context q;
    private a c;
    private SQLiteDatabase d;
    private b e;
    private SQLiteDatabase f;

    private class a extends SQLiteOpenHelper {
        public a(Context context) {
            super(context, l.b, null, 3);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            Log.i(MessageProvider.a, "MessageStoreHelper-->onCreate-->start");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS MessageStore(_id Integer  PRIMARY KEY  AUTOINCREMENT  , MsdId Varchar  , Json Varchar  , SdkVersion Varchar  , ArrivalTime Long  , ActionType Integer )");
            sQLiteDatabase.execSQL("create table if not exists MsgTemp(id INTEGER AUTO_INCREMENT,tempkey varchar default NULL, tempvalue varchar default NULL,PRIMARY KEY(id))");
            sQLiteDatabase.execSQL("create table if not exists MsgAlias(time long,type varchar default NULL,alias varchar default NULL,exclusive int,error int,message varchar,PRIMARY KEY(time))");
            Log.i(MessageProvider.a, "MessageStoreHelper-->onCreate-->end");
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            if (i <= 2) {
                sQLiteDatabase.execSQL("drop table MsgTemp");
            }
            onCreate(sQLiteDatabase);
            Log.i(MessageProvider.a, "MessageStoreHelper-->onUpgrade");
        }
    }

    private class b extends SQLiteOpenHelper {
        public b(Context context) {
            super(context, m.b, null, 7);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL("create table if not exists MsgLogStore (MsgId varchar, ActionType Integer, Time long, pa varchar, PRIMARY KEY(MsgId, ActionType))");
            sQLiteDatabase.execSQL("create table if not exists MsgLogIdTypeStore (MsgId varchar, MsgType varchar, PRIMARY KEY(MsgId))");
            sQLiteDatabase.execSQL("create table if not exists MsgLogStoreForAgoo (MsgId varchar, TaskId varchar, MsgStatus varchar, Time long, PRIMARY KEY(MsgId, MsgStatus))");
            sQLiteDatabase.execSQL("create table if not exists MsgLogIdTypeStoreForAgoo (MsgId varchar, TaskId varchar, MsgStatus varchar, PRIMARY KEY(MsgId))");
            sQLiteDatabase.execSQL("create table if not exists MsgConfigInfo (SerialNo integer default 1, AppLaunchAt long default 0, UpdateResponse varchar default NULL)");
            sQLiteDatabase.execSQL("create table if not exists InAppLogStore (Time long, MsgId varchar, MsgType Integer, NumDisplay Integer, NumOpenFull Integer, NumOpenTop Integer, NumOpenBottom Integer, NumClose Integer, NumDuration Integer, NumCustom Integer, PRIMARY KEY(Time))");
            Log.i(MessageProvider.a, "MsgLogStoreHelper-->onCreate");
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            Log.i(MessageProvider.a, "oldVersion:" + i + ",newVersion:" + i2);
            if (i <= 4) {
                Log.i(MessageProvider.a, "MsgLogStoreHelper-->drop delete");
                sQLiteDatabase.execSQL("drop table MsgConfigInfo");
            }
            if (i <= 5) {
                Log.i(MessageProvider.a, "MsgLogStoreHelper-->drop MsgLogStore");
                sQLiteDatabase.execSQL("ALTER TABLE MsgLogStore ADD COLUMN pa varchar");
            }
            if (i <= 6 && a(sQLiteDatabase, m.i)) {
                Log.i(MessageProvider.a, "MsgLogStoreHelper-->alter InAppLogStore");
                sQLiteDatabase.execSQL("ALTER TABLE InAppLogStore ADD COLUMN NumCustom Integer");
            }
            onCreate(sQLiteDatabase);
            Log.i(MessageProvider.a, "MsgLogStoreHelper-->onUpgrade");
        }

        private boolean a(SQLiteDatabase sQLiteDatabase, String str) {
            boolean z = false;
            if (!TextUtils.isEmpty(str)) {
                try {
                    Cursor rawQuery = sQLiteDatabase.rawQuery("select count(*) as c from sqlite_master where type = 'table' and name = '" + str.trim() + "'", null);
                    if (rawQuery.moveToNext() && rawQuery.getInt(0) > 0) {
                        z = true;
                    }
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                } catch (Exception e) {
                }
            }
            return z;
        }
    }

    public boolean onCreate() {
        q = getContext();
        b();
        UriMatcher uriMatcher = b;
        a.a(q);
        uriMatcher.addURI(a.a, "MessageStores", 1);
        uriMatcher = b;
        a.a(q);
        uriMatcher.addURI(a.a, "MsgTemps", 2);
        uriMatcher = b;
        a.a(q);
        uriMatcher.addURI(a.a, l.e, 3);
        uriMatcher = b;
        a.a(q);
        uriMatcher.addURI(a.a, "MsgAliasDeleteAll", 4);
        uriMatcher = b;
        a.a(q);
        uriMatcher.addURI(a.a, "MsgLogStores", 5);
        uriMatcher = b;
        a.a(q);
        uriMatcher.addURI(a.a, "MsgLogIdTypeStores", 6);
        uriMatcher = b;
        a.a(q);
        uriMatcher.addURI(a.a, "MsgLogStoreForAgoos", 7);
        uriMatcher = b;
        a.a(q);
        uriMatcher.addURI(a.a, "MsgLogIdTypeStoreForAgoos", 8);
        uriMatcher = b;
        a.a(q);
        uriMatcher.addURI(a.a, "MsgConfigInfos", 9);
        uriMatcher = b;
        a.a(q);
        uriMatcher.addURI(a.a, "InAppLogStores", 10);
        return true;
    }

    private void b() {
        try {
            synchronized (this) {
                this.c = new a(getContext());
                this.e = new b(getContext());
                if (this.d == null) {
                    this.d = this.c.getWritableDatabase();
                }
                if (this.f == null) {
                    this.f = this.e.getWritableDatabase();
                }
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
            }
        }
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        Cursor cursor = null;
        switch (b.match(uri)) {
            case 2:
                cursor = this.d.query(l.d, strArr, str, strArr2, null, null, str2);
                break;
            case 3:
                cursor = this.d.query(l.e, strArr, str, strArr2, null, null, str2);
                break;
            case 5:
                cursor = this.f.query(m.d, strArr, str, strArr2, null, null, str2);
                break;
            case 7:
                cursor = this.f.query(m.f, strArr, str, strArr2, null, null, str2);
                break;
            case 8:
                cursor = this.f.query(m.g, strArr, str, strArr2, null, null, str2);
                break;
            case 9:
                cursor = this.f.query(m.h, strArr, str, strArr2, null, null, str2);
                break;
            case 10:
                cursor = this.f.query(m.i, strArr, str, strArr2, null, null, str2);
                break;
        }
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    public String getType(Uri uri) {
        switch (b.match(uri)) {
            case 1:
            case 2:
            case 3:
            case 5:
            case 7:
            case 8:
            case 9:
                return com.umeng.message.provider.a.a.k;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        long insertWithOnConflict;
        Uri withAppendedId;
        switch (b.match(uri)) {
            case 1:
                insertWithOnConflict = this.d.insertWithOnConflict(l.c, null, contentValues, 5);
                if (insertWithOnConflict > 0) {
                    a.a(q);
                    withAppendedId = ContentUris.withAppendedId(a.b, insertWithOnConflict);
                    getContext().getContentResolver().notifyChange(withAppendedId, null);
                    return withAppendedId;
                }
                break;
            case 2:
                insertWithOnConflict = this.d.insertWithOnConflict(l.d, null, contentValues, 5);
                if (insertWithOnConflict > 0) {
                    a.a(q);
                    withAppendedId = ContentUris.withAppendedId(a.b, insertWithOnConflict);
                    getContext().getContentResolver().notifyChange(withAppendedId, null);
                    return withAppendedId;
                }
                break;
            case 3:
                insertWithOnConflict = this.d.insertWithOnConflict(l.e, null, contentValues, 5);
                if (insertWithOnConflict > 0) {
                    a.a(q);
                    withAppendedId = ContentUris.withAppendedId(a.d, insertWithOnConflict);
                    getContext().getContentResolver().notifyChange(withAppendedId, null);
                    return withAppendedId;
                }
                break;
            case 5:
                insertWithOnConflict = this.f.insertWithOnConflict(m.d, null, contentValues, 5);
                if (insertWithOnConflict > 0) {
                    a.a(q);
                    withAppendedId = ContentUris.withAppendedId(a.f, insertWithOnConflict);
                    getContext().getContentResolver().notifyChange(withAppendedId, null);
                    return withAppendedId;
                }
                break;
            case 6:
                insertWithOnConflict = this.f.insertWithOnConflict(m.e, null, contentValues, 5);
                if (insertWithOnConflict > 0) {
                    a.a(q);
                    return ContentUris.withAppendedId(a.g, insertWithOnConflict);
                }
                break;
            case 7:
                insertWithOnConflict = this.f.insertWithOnConflict(m.f, null, contentValues, 5);
                if (insertWithOnConflict > 0) {
                    a.a(q);
                    return ContentUris.withAppendedId(a.h, insertWithOnConflict);
                }
                break;
            case 8:
                insertWithOnConflict = this.f.insertWithOnConflict(m.g, null, contentValues, 5);
                if (insertWithOnConflict > 0) {
                    a.a(q);
                    return ContentUris.withAppendedId(a.i, insertWithOnConflict);
                }
                break;
            case 9:
                insertWithOnConflict = this.f.insertWithOnConflict(m.h, null, contentValues, 5);
                if (insertWithOnConflict > 0) {
                    a.a(q);
                    return ContentUris.withAppendedId(a.j, insertWithOnConflict);
                }
                break;
            case 10:
                insertWithOnConflict = this.f.insertWithOnConflict(m.i, null, contentValues, 5);
                if (insertWithOnConflict > 0) {
                    a.a(q);
                    return ContentUris.withAppendedId(a.k, insertWithOnConflict);
                }
                break;
        }
        return null;
    }

    public int delete(Uri uri, String str, String[] strArr) {
        int i = 0;
        switch (b.match(uri)) {
            case 2:
                i = this.d.delete(l.d, str, strArr);
                break;
            case 3:
                i = this.d.delete(l.e, str, strArr);
                break;
            case 4:
                i = this.d.delete(l.e, null, null);
                break;
            case 5:
                i = this.f.delete(m.d, str, strArr);
                break;
            case 6:
                i = this.f.delete(m.e, str, strArr);
                break;
            case 7:
                i = this.f.delete(m.f, str, strArr);
                break;
            case 8:
                i = this.f.delete(m.g, str, strArr);
                break;
            case 10:
                i = this.f.delete(m.i, str, strArr);
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return i;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        int updateWithOnConflict;
        switch (b.match(uri)) {
            case 1:
                updateWithOnConflict = this.d.updateWithOnConflict(l.c, contentValues, str, strArr, 5);
                break;
            case 2:
                updateWithOnConflict = this.d.updateWithOnConflict(l.d, contentValues, str, strArr, 5);
                break;
            case 3:
                this.d.updateWithOnConflict(l.e, contentValues, str, strArr, 5);
                updateWithOnConflict = 0;
                break;
            case 9:
                updateWithOnConflict = this.f.updateWithOnConflict(m.h, contentValues, str, strArr, 5);
                break;
            case 10:
                updateWithOnConflict = this.f.updateWithOnConflict(m.i, contentValues, str, strArr, 5);
                break;
            default:
                updateWithOnConflict = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return updateWithOnConflict;
    }

    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> arrayList) throws OperationApplicationException {
        this.f.beginTransaction();
        Iterator it;
        Object obj;
        ContentProviderOperation contentProviderOperation;
        try {
            ContentProviderResult[] applyBatch = super.applyBatch(arrayList);
            this.f.setTransactionSuccessful();
            this.f.endTransaction();
            it = arrayList.iterator();
            obj = null;
            while (it.hasNext()) {
                contentProviderOperation = (ContentProviderOperation) it.next();
                if (!contentProviderOperation.getUri().equals(obj)) {
                    obj = contentProviderOperation.getUri();
                    getContext().getContentResolver().notifyChange(contentProviderOperation.getUri(), null);
                    Log.i(a, "notifychange endTransaction..uri:" + contentProviderOperation.getUri());
                }
            }
            return applyBatch;
        } catch (Throwable th) {
            Throwable th2 = th;
            this.f.endTransaction();
            it = arrayList.iterator();
            obj = null;
            while (it.hasNext()) {
                contentProviderOperation = (ContentProviderOperation) it.next();
                if (!contentProviderOperation.getUri().equals(obj)) {
                    obj = contentProviderOperation.getUri();
                    getContext().getContentResolver().notifyChange(contentProviderOperation.getUri(), null);
                    Log.i(a, "notifychange endTransaction..uri:" + contentProviderOperation.getUri());
                }
            }
        }
    }
}
