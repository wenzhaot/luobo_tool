package com.umeng.message.common;

import android.content.Context;
import android.text.TextUtils;
import com.stub.StubApp;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.PushAgent;
import com.umeng.message.proguard.k;
import com.umeng.message.proguard.l;

/* compiled from: Res */
public class c {
    private static final String a = c.class.getName();
    private static c b;
    private static Class e = null;
    private static Class f = null;
    private static Class g = null;
    private static Class h = null;
    private static Class i = null;
    private static Class j = null;
    private static Class k = null;
    private static Class l = null;
    private Context c;
    private String d;

    private c(Context context) {
        UMLog uMLog;
        this.c = StubApp.getOrigApplicationContext(context.getApplicationContext());
        UMLog uMLog2 = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 2, "packageName=" + this.c.getPackageName());
        try {
            f = Class.forName((!TextUtils.isEmpty(PushAgent.getInstance(this.c).getResourcePackageName()) ? PushAgent.getInstance(this.c).getResourcePackageName() : this.c.getPackageName()) + ".R$drawable");
        } catch (ClassNotFoundException e) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, e.getMessage());
            uMLog2 = UMConfigure.umDebugLog;
            UMLog.aq(k.c, 0, "\\|");
        }
        try {
            g = Class.forName((!TextUtils.isEmpty(PushAgent.getInstance(this.c).getResourcePackageName()) ? PushAgent.getInstance(this.c).getResourcePackageName() : this.c.getPackageName()) + ".R$layout");
        } catch (ClassNotFoundException e2) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, e2.getMessage());
        }
        try {
            e = Class.forName((!TextUtils.isEmpty(PushAgent.getInstance(this.c).getResourcePackageName()) ? PushAgent.getInstance(this.c).getResourcePackageName() : this.c.getPackageName()) + ".R$id");
        } catch (ClassNotFoundException e22) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, e22.getMessage());
        }
        try {
            h = Class.forName((!TextUtils.isEmpty(PushAgent.getInstance(this.c).getResourcePackageName()) ? PushAgent.getInstance(this.c).getResourcePackageName() : this.c.getPackageName()) + ".R$anim");
        } catch (ClassNotFoundException e222) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, e222.getMessage());
        }
        try {
            i = Class.forName((!TextUtils.isEmpty(PushAgent.getInstance(this.c).getResourcePackageName()) ? PushAgent.getInstance(this.c).getResourcePackageName() : this.c.getPackageName()) + ".R$style");
        } catch (ClassNotFoundException e2222) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, e2222.getMessage());
        }
        try {
            j = Class.forName((!TextUtils.isEmpty(PushAgent.getInstance(this.c).getResourcePackageName()) ? PushAgent.getInstance(this.c).getResourcePackageName() : this.c.getPackageName()) + ".R$string");
        } catch (ClassNotFoundException e22222) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, e22222.getMessage());
        }
        try {
            k = Class.forName((!TextUtils.isEmpty(PushAgent.getInstance(this.c).getResourcePackageName()) ? PushAgent.getInstance(this.c).getResourcePackageName() : this.c.getPackageName()) + ".R$array");
        } catch (ClassNotFoundException e222222) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, e222222.getMessage());
        }
        try {
            l = Class.forName((!TextUtils.isEmpty(PushAgent.getInstance(this.c).getResourcePackageName()) ? PushAgent.getInstance(this.c).getResourcePackageName() : this.c.getPackageName()) + ".R$raw");
        } catch (ClassNotFoundException e2222222) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, e2222222.getMessage());
        }
    }

    public static c a(Context context) {
        if (b == null) {
            b = new c(context);
        }
        return b;
    }

    public int a(String str) {
        return a(h, str);
    }

    public int b(String str) {
        return a(e, str);
    }

    public int c(String str) throws Exception {
        return b(e, str);
    }

    public int d(String str) {
        return a(f, str);
    }

    public int e(String str) {
        return a(g, str);
    }

    public int f(String str) throws Exception {
        return b(g, str);
    }

    public int g(String str) {
        return a(i, str);
    }

    public int h(String str) {
        return a(j, str);
    }

    public int i(String str) {
        return a(k, str);
    }

    public int j(String str) {
        return a(l, str);
    }

    private int a(Class<?> cls, String str) {
        if (cls == null) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, "getRes(null," + str + l.t);
            throw new IllegalArgumentException("ResClass未初始化，请确保你已经添加了必要的资源。同时确保你在混淆文件中添加了" + this.c.getPackageName() + ".R$* 。 field=" + str);
        }
        try {
            return cls.getField(str).getInt(str);
        } catch (Exception e) {
            UMLog uMLog2 = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, "getRes(" + cls.getName() + ", " + str + l.t);
            uMLog2 = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, "获取资源错误，确保你已经把res目录下的所有资源从SDK拷贝到了你的主工程");
            uMLog2 = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, e.getMessage());
            return -1;
        }
    }

    private int b(Class<?> cls, String str) throws Exception {
        if (cls == null) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, "getRes(null," + str + l.t);
            throw new IllegalArgumentException("ResClass未初始化，请确保你已经添加了必要的资源。同时确保你在混淆文件中添加了" + this.c.getPackageName() + ".R$* 。 field=" + str);
        }
        int i = cls.getField(str).getInt(str);
        UMLog uMLog2 = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 0, "getRes(" + cls.getName() + ", " + str + l.t);
        uMLog2 = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 0, "获取资源错误，确保你已经把res目录下的所有资源从SDK拷贝到了你的主工程");
        return i;
    }

    public void k(String str) {
        this.d = str;
    }

    public String a() {
        if (TextUtils.isEmpty(this.d)) {
            return this.c.getPackageName();
        }
        return this.d;
    }
}
