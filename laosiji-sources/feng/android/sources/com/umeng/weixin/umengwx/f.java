package com.umeng.weixin.umengwx;

import android.os.Bundle;

public class f {
    public static int a(Bundle bundle, String str) {
        int i = -1;
        if (bundle == null) {
            return i;
        }
        try {
            return bundle.getInt(str, -1);
        } catch (Exception e) {
            return i;
        }
    }

    public static String b(Bundle bundle, String str) {
        String str2 = null;
        if (bundle == null) {
            return str2;
        }
        try {
            return bundle.getString(str);
        } catch (Exception e) {
            return str2;
        }
    }
}
