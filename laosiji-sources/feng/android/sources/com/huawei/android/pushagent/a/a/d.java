package com.huawei.android.pushagent.a.a;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.android.pushagent.a.a.a.c;

public class d {
    public static String a(Context context, String str, String str2) {
        String str3 = "";
        if (!(TextUtils.isEmpty(str) || TextUtils.isEmpty(str2))) {
            try {
                str3 = c.b(context, new e(context, str).b(str2 + "_v2"));
            } catch (Throwable e) {
                c.c("PushLogSC2816", e.toString(), e);
            }
            if (TextUtils.isEmpty(str3)) {
                c.a("PushLogSC2816", "not exist for:" + str2);
            }
        }
        return str3;
    }

    public static boolean a(Context context, String str, String str2, String str3) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        return new e(context, str).a(str2 + "_v2", c.a(context, str3));
    }
}
