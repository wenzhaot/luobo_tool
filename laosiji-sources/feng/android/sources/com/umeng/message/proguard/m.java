package com.umeng.message.proguard;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.stub.StubApp;
import com.umeng.message.MessageSharedPrefs;
import com.umeng.message.MsgConstant;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONObject;

/* compiled from: MsgLogStore */
public class m {
    private static final String A = m.class.getName();
    private static m B = null;
    private static final String D = " Asc ";
    private static final String E = " Desc ";
    public static final String a = " And ";
    public static final String b = "MsgLogStore.db";
    public static final int c = 7;
    public static final String d = "MsgLogStore";
    public static final String e = "MsgLogIdTypeStore";
    public static final String f = "MsgLogStoreForAgoo";
    public static final String g = "MsgLogIdTypeStoreForAgoo";
    public static final String h = "MsgConfigInfo";
    public static final String i = "InAppLogStore";
    public static final String j = "MsgId";
    public static final String k = "MsgType";
    public static final String l = "ActionType";
    public static final String m = "pa";
    public static final String n = "Time";
    public static final String o = "TaskId";
    public static final String p = "MsgStatus";
    public static final String q = "SerialNo";
    public static final String r = "AppLaunchAt";
    public static final String s = "UpdateResponse";
    public static final String t = "NumDisplay";
    public static final String u = "NumOpenFull";
    public static final String v = "NumOpenTop";
    public static final String w = "NumOpenBottom";
    public static final String x = "NumClose";
    public static final String y = "NumDuration";
    public static final String z = "NumCustom";
    private Context C;

    /* compiled from: MsgLogStore */
    public class a {
        public String a;
        public long b;
        public int c;
        public String d;

        public a(String str, int i, long j, String str2) {
            this.a = str;
            this.c = i;
            this.b = j;
            this.d = str2;
        }

        public a(Cursor cursor) {
            this.a = cursor.getString(cursor.getColumnIndex(m.j));
            this.b = cursor.getLong(cursor.getColumnIndex(m.n));
            this.c = cursor.getInt(cursor.getColumnIndex("ActionType"));
            this.d = cursor.getString(cursor.getColumnIndex("pa"));
        }

        public ContentValues a() {
            ContentValues contentValues = new ContentValues();
            contentValues.put(m.j, this.a);
            contentValues.put(m.n, Long.valueOf(this.b));
            contentValues.put("ActionType", Integer.valueOf(this.c));
            contentValues.put("pa", this.d);
            return contentValues;
        }
    }

    /* compiled from: MsgLogStore */
    public class b {
        public String a;
        public String b;
        public String c;
        public long d;

        public b(String str, String str2, String str3, long j) {
            this.a = str;
            this.b = str2;
            this.c = str3;
            this.d = j;
        }

        public b(Cursor cursor) {
            this.a = cursor.getString(cursor.getColumnIndex(m.j));
            this.b = cursor.getString(cursor.getColumnIndex(m.o));
            this.c = cursor.getString(cursor.getColumnIndex(m.p));
            this.d = cursor.getLong(cursor.getColumnIndex(m.n));
        }

        public ContentValues a() {
            ContentValues contentValues = new ContentValues();
            contentValues.put(m.j, this.a);
            contentValues.put(m.o, this.b);
            contentValues.put(m.p, this.c);
            contentValues.put(m.n, Long.valueOf(this.d));
            return contentValues;
        }
    }

    /* compiled from: MsgLogStore */
    public class c {
        public String a;
        public String b;

        public c(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public c(Cursor cursor) {
            this.a = cursor.getString(cursor.getColumnIndex(m.j));
            this.b = cursor.getString(cursor.getColumnIndex(m.k));
        }

        public ContentValues a() {
            ContentValues contentValues = new ContentValues();
            contentValues.put(m.j, this.a);
            contentValues.put(m.k, this.b);
            return contentValues;
        }
    }

    /* compiled from: MsgLogStore */
    public class d {
        public String a;
        public String b;
        public String c;

        public d(String str, String str2, String str3) {
            this.a = str;
            this.b = str2;
            this.c = str3;
        }

        public d(Cursor cursor) {
            this.a = cursor.getString(cursor.getColumnIndex(m.j));
            this.b = cursor.getString(cursor.getColumnIndex(m.o));
            this.c = cursor.getString(cursor.getColumnIndex(m.p));
        }

        public ContentValues a() {
            ContentValues contentValues = new ContentValues();
            contentValues.put(m.j, this.a);
            contentValues.put(m.o, this.b);
            contentValues.put(m.p, this.c);
            return contentValues;
        }
    }

    public static m a(Context context) {
        if (B == null) {
            B = new m(context);
            B.h();
        }
        return B;
    }

    private m(Context context) {
        this.C = StubApp.getOrigApplicationContext(context.getApplicationContext());
    }

    private void h() {
        if (!MessageSharedPrefs.getInstance(this.C).hasTransferedCacheFileDataToSQL()) {
            File[] listFiles = this.C.getCacheDir().listFiles(new FilenameFilter() {
                public boolean accept(File file, String str) {
                    if (TextUtils.isEmpty(str) || !str.startsWith(MsgConstant.CACHE_LOG_FILE_PREFIX)) {
                        return false;
                    }
                    return true;
                }
            });
            if (listFiles != null) {
                for (File file : listFiles) {
                    a(file);
                    file.delete();
                }
            }
            MessageSharedPrefs.getInstance(this.C).finishTransferedCacheFileDataToSQL();
        }
    }

    private void a(File file) {
        try {
            JSONObject jSONObject = new JSONObject(b(file));
            a(jSONObject.optString("msg_id"), jSONObject.optInt(MsgConstant.KEY_ACTION_TYPE), jSONObject.optLong("ts"), jSONObject.optString("pa"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String b(File file) throws IOException {
        Throwable th;
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            try {
                String str = "";
                StringBuilder stringBuilder = new StringBuilder();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    stringBuilder.append(readLine);
                }
                str = stringBuilder.toString();
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return str;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            throw th;
        }
    }

    public boolean a(String str, int i, long j, String str2) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String str3;
        if (str2 == null) {
            str3 = "";
        } else {
            str3 = str2;
        }
        a aVar = new a(str, i, j, str3);
        ContentResolver contentResolver = this.C.getContentResolver();
        com.umeng.message.provider.a.a(this.C);
        return contentResolver.insert(com.umeng.message.provider.a.f, aVar.a()) != null;
    }

    public boolean a(String str, int i) {
        boolean z = true;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String[] strArr = new String[]{str, i + ""};
        ContentResolver contentResolver = this.C.getContentResolver();
        com.umeng.message.provider.a.a(this.C);
        if (contentResolver.delete(com.umeng.message.provider.a.f, "MsgId=? And ActionType=?", strArr) != 1) {
            z = false;
        }
        return z;
    }

    public a a(String str) {
        a aVar = null;
        if (!TextUtils.isEmpty(str)) {
            String[] strArr = new String[0];
            ContentResolver contentResolver = this.C.getContentResolver();
            com.umeng.message.provider.a.a(this.C);
            Cursor query = contentResolver.query(com.umeng.message.provider.a.f, null, "MsgId=?", strArr, null);
            if (query.moveToFirst()) {
                aVar = new a(query);
            }
            if (query != null) {
                query.close();
            }
        }
        return aVar;
    }

    public ArrayList<a> a(int i) {
        if (i < 1) {
            return null;
        }
        ArrayList<a> arrayList = new ArrayList();
        String str = "Time Asc  limit " + i;
        com.umeng.message.provider.a.a(this.C);
        Cursor query = this.C.getContentResolver().query(com.umeng.message.provider.a.f, null, null, null, str);
        for (boolean moveToFirst = query.moveToFirst(); moveToFirst; moveToFirst = query.moveToNext()) {
            arrayList.add(new a(query));
        }
        if (query != null) {
            query.close();
        }
        return arrayList;
    }

    public ArrayList<a> a() {
        ArrayList<a> arrayList = new ArrayList();
        ContentResolver contentResolver = this.C.getContentResolver();
        com.umeng.message.provider.a.a(this.C);
        Cursor query = contentResolver.query(com.umeng.message.provider.a.f, null, null, null, "Time Asc ");
        if (query == null) {
            return arrayList;
        }
        for (boolean moveToFirst = query.moveToFirst(); moveToFirst; moveToFirst = query.moveToNext()) {
            arrayList.add(new a(query));
        }
        if (query != null) {
            query.close();
        }
        return arrayList;
    }

    public boolean a(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        c cVar = new c(str, str2);
        ContentResolver contentResolver = this.C.getContentResolver();
        com.umeng.message.provider.a.a(this.C);
        if (contentResolver.insert(com.umeng.message.provider.a.g, cVar.a()) != null) {
            return true;
        }
        return false;
    }

    public boolean b(String str) {
        boolean z = true;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String[] strArr = new String[]{str};
        ContentResolver contentResolver = this.C.getContentResolver();
        com.umeng.message.provider.a.a(this.C);
        if (contentResolver.delete(com.umeng.message.provider.a.g, "MsgId=?", strArr) != 1) {
            z = false;
        }
        return z;
    }

    public ArrayList<c> b(int i) {
        if (i < 1) {
            return null;
        }
        ArrayList<c> arrayList = new ArrayList();
        com.umeng.message.provider.a.a(this.C);
        Uri build = com.umeng.message.provider.a.g.buildUpon().appendQueryParameter("limit", i + "").build();
        Cursor query = this.C.getContentResolver().query(build, null, null, null, "MsgId Asc ");
        for (boolean moveToFirst = query.moveToFirst(); moveToFirst; moveToFirst = query.moveToNext()) {
            arrayList.add(new c(query));
        }
        if (query != null) {
            query.close();
        }
        return arrayList;
    }

    public ArrayList<c> b() {
        ArrayList<c> arrayList = new ArrayList();
        ContentResolver contentResolver = this.C.getContentResolver();
        com.umeng.message.provider.a.a(this.C);
        Cursor query = contentResolver.query(com.umeng.message.provider.a.g, null, null, null, "MsgId Asc ");
        for (boolean moveToFirst = query.moveToFirst(); moveToFirst; moveToFirst = query.moveToNext()) {
            arrayList.add(new c(query));
        }
        if (query != null) {
            query.close();
        }
        return arrayList;
    }

    public boolean a(String str, String str2, String str3, long j) {
        return false;
    }

    public boolean b(String str, String str2) {
        boolean z = true;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String[] strArr = new String[]{str, str2};
        ContentResolver contentResolver = this.C.getContentResolver();
        com.umeng.message.provider.a.a(this.C);
        if (contentResolver.delete(com.umeng.message.provider.a.h, "MsgId=? And MsgStatus=?", strArr) != 1) {
            z = false;
        }
        return z;
    }

    public b c(String str) {
        b bVar = null;
        if (!TextUtils.isEmpty(str)) {
            String[] strArr = new String[]{str};
            ContentResolver contentResolver = this.C.getContentResolver();
            com.umeng.message.provider.a.a(this.C);
            Cursor query = contentResolver.query(com.umeng.message.provider.a.h, null, "MsgId=?", strArr, null);
            if (query.moveToFirst()) {
                bVar = new b(query);
            }
            if (query != null) {
                query.close();
            }
        }
        return bVar;
    }

    public ArrayList<b> c(int i) {
        if (i < 1) {
            return null;
        }
        ArrayList<b> arrayList = new ArrayList();
        com.umeng.message.provider.a.a(this.C);
        Uri build = com.umeng.message.provider.a.h.buildUpon().appendQueryParameter("limit", i + "").build();
        Cursor query = this.C.getContentResolver().query(build, null, null, null, "Time Asc ");
        for (boolean moveToFirst = query.moveToFirst(); moveToFirst; moveToFirst = query.moveToNext()) {
            arrayList.add(new b(query));
        }
        if (query != null) {
            query.close();
        }
        return arrayList;
    }

    public ArrayList<b> c() {
        ArrayList<b> arrayList = new ArrayList();
        ContentResolver contentResolver = this.C.getContentResolver();
        com.umeng.message.provider.a.a(this.C);
        Cursor query = contentResolver.query(com.umeng.message.provider.a.h, null, null, null, "Time Asc ");
        for (boolean moveToFirst = query.moveToFirst(); moveToFirst; moveToFirst = query.moveToNext()) {
            arrayList.add(new b(query));
        }
        if (query != null) {
            query.close();
        }
        return arrayList;
    }

    public boolean a(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        d dVar = new d(str, str2, str3);
        ContentResolver contentResolver = this.C.getContentResolver();
        com.umeng.message.provider.a.a(this.C);
        if (contentResolver.insert(com.umeng.message.provider.a.i, dVar.a()) != null) {
            return true;
        }
        return false;
    }

    public boolean d(String str) {
        boolean z = true;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String[] strArr = new String[]{str};
        ContentResolver contentResolver = this.C.getContentResolver();
        com.umeng.message.provider.a.a(this.C);
        if (contentResolver.delete(com.umeng.message.provider.a.i, "MsgId=?", strArr) != 1) {
            z = false;
        }
        return z;
    }

    public ArrayList<d> d(int i) {
        if (i < 1) {
            return null;
        }
        ArrayList<d> arrayList = new ArrayList();
        com.umeng.message.provider.a.a(this.C);
        Uri build = com.umeng.message.provider.a.i.buildUpon().appendQueryParameter("limit", i + "").build();
        Cursor query = this.C.getContentResolver().query(build, null, null, null, "MsgId Asc ");
        for (boolean moveToFirst = query.moveToFirst(); moveToFirst; moveToFirst = query.moveToNext()) {
            arrayList.add(new d(query));
        }
        if (query != null) {
            query.close();
        }
        return arrayList;
    }

    public ArrayList<d> d() {
        ArrayList<d> arrayList = new ArrayList();
        ContentResolver contentResolver = this.C.getContentResolver();
        com.umeng.message.provider.a.a(this.C);
        Cursor query = contentResolver.query(com.umeng.message.provider.a.i, null, null, null, "MsgId Asc ");
        for (boolean moveToFirst = query.moveToFirst(); moveToFirst; moveToFirst = query.moveToNext()) {
            arrayList.add(new d(query));
        }
        if (query != null) {
            query.close();
        }
        return arrayList;
    }

    public int e() {
        int i;
        ContentResolver contentResolver = this.C.getContentResolver();
        com.umeng.message.provider.a.a(this.C);
        Cursor query = contentResolver.query(com.umeng.message.provider.a.j, new String[]{q}, null, null, null);
        if (query.moveToFirst()) {
            i = query.getInt(query.getColumnIndex(q));
        } else {
            i = 0;
        }
        if (query != null) {
            query.close();
        }
        return i;
    }

    public void e(int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(q, i + "");
        ContentResolver contentResolver = this.C.getContentResolver();
        com.umeng.message.provider.a.a(this.C);
        contentResolver.update(com.umeng.message.provider.a.j, contentValues, null, null);
    }

    public long f() {
        ContentResolver contentResolver = this.C.getContentResolver();
        com.umeng.message.provider.a.a(this.C);
        Cursor query = contentResolver.query(com.umeng.message.provider.a.j, new String[]{r}, null, null, null);
        if (query == null) {
            return 0;
        }
        long j;
        if (query.moveToFirst()) {
            j = query.getLong(query.getColumnIndex(r));
        } else {
            j = 0;
        }
        if (query != null) {
            query.close();
        }
        Log.d(A, "appLaunchAt=" + j);
        return j;
    }

    public void a(long j) {
        ContentResolver contentResolver = this.C.getContentResolver();
        com.umeng.message.provider.a.a(this.C);
        Cursor query = contentResolver.query(com.umeng.message.provider.a.j, new String[]{r}, null, null, null);
        int count = query.getCount();
        if (query != null) {
            query.close();
        }
        ContentValues contentValues;
        ContentResolver contentResolver2;
        if (count > 0) {
            contentValues = new ContentValues();
            contentValues.put(r, j + "");
            contentResolver2 = this.C.getContentResolver();
            com.umeng.message.provider.a.a(this.C);
            contentResolver2.update(com.umeng.message.provider.a.j, contentValues, null, null);
            return;
        }
        contentValues = new ContentValues();
        contentValues.put(r, j + "");
        contentResolver2 = this.C.getContentResolver();
        com.umeng.message.provider.a.a(this.C);
        contentResolver2.insert(com.umeng.message.provider.a.j, contentValues);
    }

    public Object g() {
        String str = null;
        ContentResolver contentResolver = this.C.getContentResolver();
        com.umeng.message.provider.a.a(this.C);
        Cursor query = contentResolver.query(com.umeng.message.provider.a.j, new String[]{s}, null, null, null);
        if (query.moveToFirst()) {
            str = query.getString(query.getColumnIndex(s));
        }
        if (query != null) {
            query.close();
        }
        Log.d(A, "updateResponse=" + str);
        return h.f(str);
    }

    public void a(Object obj) {
        String a = h.a(obj);
        ContentValues contentValues = new ContentValues();
        contentValues.put(s, a);
        ContentResolver contentResolver = this.C.getContentResolver();
        com.umeng.message.provider.a.a(this.C);
        contentResolver.update(com.umeng.message.provider.a.j, contentValues, null, null);
    }
}
