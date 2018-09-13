package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;

/* compiled from: UMTTOneTracker */
public class m extends a {
    private static final String a = "umtt1";
    private Context b;

    public m(Context context) {
        super(a);
        this.b = context;
    }

    public String f() {
        try {
            String str;
            Class cls = Class.forName("com.umeng.commonsdk.internal.utils.SDStorageAgent");
            if (cls != null) {
                str = (String) cls.getMethod("getUmtt1", new Class[]{Context.class}).invoke(cls, new Object[]{this.b});
            } else {
                str = null;
            }
            return str;
        } catch (ClassNotFoundException e) {
            return null;
        } catch (Throwable th) {
            return null;
        }
    }
}
