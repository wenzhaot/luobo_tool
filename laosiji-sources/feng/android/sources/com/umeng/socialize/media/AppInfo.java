package com.umeng.socialize.media;

import android.text.TextUtils;

public class AppInfo {
    private String a = "com.sina.weibo";
    private String b = "com.sina.weibo.SSOActivity";
    private int c;

    public String a() {
        return this.a;
    }

    public void a(int i) {
        this.c = i;
    }

    public void a(String str) {
        this.a = str;
    }

    public String b() {
        return this.b;
    }

    public void b(String str) {
        this.b = str;
    }

    public int c() {
        return this.c;
    }

    public boolean d() {
        return !TextUtils.isEmpty(this.a) && this.c > 0;
    }
}
