package com.huawei.android.pushselfshow.utils.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.feng.car.utils.HttpConstant;
import com.huawei.android.pushselfshow.richpush.a.b;
import com.huawei.android.pushselfshow.richpush.favorites.e;
import com.huawei.android.pushselfshow.richpush.provider.RichMediaProvider.a;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.ArrayList;

public class c {
    public static ArrayList a(Context context, String str) {
        String str2;
        String[] strArr;
        Cursor cursor = null;
        ArrayList arrayList = new ArrayList();
        String str3 = "";
        if (str == null) {
            str2 = "SELECT pushmsg._id,pushmsg.msg,pushmsg.token,pushmsg.url,notify.bmp  FROM pushmsg LEFT OUTER JOIN notify ON pushmsg.url = notify.url order by pushmsg._id desc limit 1000;";
            strArr = null;
        } else {
            str2 = "SELECT pushmsg._id,pushmsg.msg,pushmsg.token,pushmsg.url,notify.bmp  FROM pushmsg LEFT OUTER JOIN notify ON pushmsg.url = notify.url and pushmsg.url = ? order by pushmsg._id desc";
            strArr = new String[]{str};
        }
        try {
            cursor = b.a().a(context, a.f, str2, strArr);
        } catch (Throwable e) {
            com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", e.toString(), e);
        }
        if (cursor == null) {
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "cursor is null.");
        } else {
            while (cursor.moveToNext()) {
                try {
                    int i = cursor.getInt(0);
                    byte[] blob = cursor.getBlob(1);
                    if (blob == null) {
                        com.huawei.android.pushagent.a.a.c.d("PushSelfShowLog", "msg is null");
                    } else {
                        com.huawei.android.pushselfshow.b.a aVar = new com.huawei.android.pushselfshow.b.a(blob, " ".getBytes("UTF-8"));
                        if (!aVar.b()) {
                            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "parseMessage failed");
                        }
                        str2 = cursor.getString(3);
                        e eVar = new e();
                        eVar.a(i);
                        eVar.a(str2);
                        eVar.a(aVar);
                        arrayList.add(eVar);
                    }
                } catch (Throwable e2) {
                    com.huawei.android.pushagent.a.a.c.c("TAG", "query favo error " + e2.toString(), e2);
                } finally {
                    cursor.close();
                }
            }
            com.huawei.android.pushagent.a.a.c.e("PushSelfShowLog", "query favo size is " + arrayList.size());
        }
        return arrayList;
    }

    public static void a(Context context, int i) {
        try {
            Context context2 = context;
            b.a().a(context2, a.g, "pushmsg", "_id = ?", new String[]{"" + i});
        } catch (Throwable e) {
            com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", e.toString(), e);
        }
    }

    public static boolean a(Context context, String str, com.huawei.android.pushselfshow.b.a aVar) {
        if (context == null || str == null || aVar == null) {
            try {
                com.huawei.android.pushagent.a.a.c.e("PushSelfShowLog", "insertPushMsginfo ilegle param");
                return false;
            } catch (Throwable e) {
                com.huawei.android.pushagent.a.a.c.d("PushSelfShowLog", "insertBmpinfo error", e);
                return false;
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("url", str);
        contentValues.put(SocializeProtocolConstants.PROTOCOL_KEY_MSG, aVar.c());
        contentValues.put(HttpConstant.TOKEN, " ".getBytes("UTF-8"));
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "insertPushMsginfo select url is %s ,rpl is %s", str, aVar.D);
        ArrayList a = a(context, str);
        String str2 = aVar.D;
        int i = 0;
        while (i < a.size()) {
            if (((e) a.get(i)).b() == null || !str2.equals(((e) a.get(i)).b().D)) {
                i++;
            } else {
                com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", str2 + " already exist");
                return true;
            }
        }
        com.huawei.android.pushagent.a.a.c.e("PushSelfShowLog", "insertPushMsginfo " + contentValues.toString());
        b.a().a(context, a.e, "pushmsg", contentValues);
        return true;
    }
}
