package com.huawei.android.pushselfshow.richpush.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public interface a {
    Cursor a(Context context, Uri uri, String str, String[] strArr);

    void a(Context context, Uri uri, String str, ContentValues contentValues);

    void a(Context context, Uri uri, String str, String str2, String[] strArr);
}
