package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;

/* compiled from: UMTTFourTracker */
public class l extends a {
    private static final String a = "umtt4";
    private Context b;

    public l(Context context) {
        super(a);
        this.b = context;
    }

    public String f() {
        try {
            String str;
            Class cls = Class.forName("com.umeng.commonsdk.internal.utils.SDStorageAgent");
            if (cls != null) {
                str = (String) cls.getMethod("getUmtt4", new Class[]{Context.class}).invoke(cls, new Object[]{this.b});
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
