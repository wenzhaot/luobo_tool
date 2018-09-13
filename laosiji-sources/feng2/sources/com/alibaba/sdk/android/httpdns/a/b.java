package com.alibaba.sdk.android.httpdns.a;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class b {
    private static f a;
    /* renamed from: a */
    private static h f13a;
    private static boolean f = false;

    public static List<e> a() {
        Object arrayList = new ArrayList();
        if (f) {
            arrayList.addAll(a.a());
        }
        return arrayList;
    }

    public static void a(Context context) {
        a(context, null);
    }

    public static void a(Context context, h hVar) {
        a = new a(context);
        a = hVar;
        if (a == null) {
            a = new h();
        }
    }

    public static void a(e eVar) {
        if (eVar != null) {
            a.a(eVar);
        }
    }

    /* renamed from: a */
    public static boolean m8a() {
        return f;
    }

    public static void b(Context context) {
        if (context != null) {
            a.c(context);
        }
    }

    public static void b(e eVar) {
        if (eVar != null) {
            a.b(eVar);
        }
    }

    public static void c(boolean z) {
        f = z;
    }

    public static String g() {
        return a.g();
    }
}
