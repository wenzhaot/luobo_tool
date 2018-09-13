package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;

/* compiled from: UMTTThreeTracker */
public class n extends a {
    private static final String a = "umtt3";
    private Context b;

    public n(Context context) {
        super(a);
        this.b = context;
    }

    public String f() {
        try {
            String str;
            Class cls = Class.forName("com.umeng.commonsdk.internal.utils.SDStorageAgent");
            if (cls != null) {
                str = (String) cls.getMethod("getUmtt3", new Class[]{Context.class}).invoke(cls, new Object[]{this.b});
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
