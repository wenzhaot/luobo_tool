package com.umeng.qq.handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class UmengQQPreferences {
    private static final String ACCESS_TOKEN = "access_token";
    private static final String EXPIRES_IN = "expires_in";
    private static final String OPENID = "openid";
    private static final String UID = "uid";
    private static final String UNIONID = "unionid";
    private static long mtl = 0;
    private String mAccessToken = null;
    private String mOpenid = null;
    private String mUID = null;
    private SharedPreferences sharedPreferences = null;
    private String unionid = null;

    public UmengQQPreferences(Context context, String platform) {
        this.sharedPreferences = context.getSharedPreferences(platform + "simplify", 0);
        this.mAccessToken = this.sharedPreferences.getString("access_token", null);
        this.mUID = this.sharedPreferences.getString("uid", null);
        mtl = this.sharedPreferences.getLong("expires_in", 0);
        this.mOpenid = this.sharedPreferences.getString("openid", null);
        this.unionid = this.sharedPreferences.getString("unionid", null);
    }

    public String getmAccessToken() {
        return this.mAccessToken;
    }

    public void setmUID(String mUID) {
        this.mUID = mUID;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getUnionid() {
        return this.unionid;
    }

    public void setmOpenid(String mOpenid) {
        this.mOpenid = mOpenid;
    }

    public UmengQQPreferences setAuthData(Bundle b) {
        this.mAccessToken = b.getString("access_token");
        mtl = (Long.valueOf(b.getString("expires_in")).longValue() * 1000) + System.currentTimeMillis();
        this.mOpenid = b.getString("openid");
        this.mUID = b.getString("openid");
        this.unionid = b.getString("unionid");
        return this;
    }

    public String getuid() {
        return this.mUID;
    }

    public boolean isAuthValid() {
        boolean isExpired;
        if (mtl - System.currentTimeMillis() <= 0) {
            isExpired = true;
        } else {
            isExpired = false;
        }
        return (this.mAccessToken == null || isExpired) ? false : true;
    }

    public long getMtl() {
        return mtl;
    }

    public void commit() {
        this.sharedPreferences.edit().putString("access_token", this.mAccessToken).putLong("expires_in", mtl).putString("uid", this.mUID).putString("openid", this.mOpenid).putString("unionid", this.unionid).commit();
    }

    public void delete() {
        this.sharedPreferences.edit().clear().commit();
        this.mAccessToken = null;
        mtl = 0;
        this.mUID = null;
    }
}
