package com.huawei.android.pushselfshow.richpush.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class c implements a {
    public Cursor a(Context context, Uri uri, String str, String[] strArr) {
        return context.getContentResolver().query(uri, null, null, strArr, null);
    }

    public void a(Context context, Uri uri, String str, ContentValues contentValues) {
        context.getContentResolver().insert(uri, contentValues);
    }

    public void a(Context context, Uri uri, String str, String str2, String[] strArr) {
        context.getContentResolver().delete(uri, str2, strArr);
    }
}
