package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;

/* compiled from: UMTTZeroTracker */
public class p extends a {
    private static final String a = "umtt0";
    private Context b;

    public p(Context context) {
        super(a);
        this.b = context;
    }

    public String f() {
        try {
            String str;
            Class cls = Class.forName("com.umeng.commonsdk.internal.utils.SDStorageAgent");
            if (cls != null) {
                str = (String) cls.getMethod("getUmtt0", new Class[]{Context.class}).invoke(cls, new Object[]{this.b});
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
