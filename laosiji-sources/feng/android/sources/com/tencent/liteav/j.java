package com.tencent.liteav;

import android.content.Context;
import com.tencent.liteav.basic.log.TXCLog;

/* compiled from: TXSDKUtil */
public class j {
    public static h a(Context context, int i) {
        if (i == 2 || i == 4 || i == 4 || i == 6 || i == 3) {
            return new g(context);
        }
        TXCLog.e("TXSDKUtil", "create player error not support type : " + i);
        return null;
    }

    public static String a() {
        return "";
    }
}
