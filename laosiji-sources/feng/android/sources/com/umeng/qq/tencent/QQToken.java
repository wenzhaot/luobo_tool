package com.umeng.qq.tencent;

public class QQToken {
    private String a;
    private String b;
    private String c;
    private long e = -1;

    public QQToken(String var1) {
        this.a = var1;
    }

    public boolean isSessionValid() {
        return this.b != null && System.currentTimeMillis() < this.e;
    }

    public String getAppId() {
        return this.a;
    }

    public String getAccessToken() {
        return this.b;
    }

    public void setAccessToken(String var1, String var2) throws NumberFormatException {
        this.b = var1;
        this.e = 0;
        if (var2 != null) {
            this.e = System.currentTimeMillis() + (Long.parseLong(var2) * 1000);
        }
    }

    public String getOpenId() {
        return this.c;
    }

    public void setOpenId(String var1) {
        this.c = var1;
    }
}
