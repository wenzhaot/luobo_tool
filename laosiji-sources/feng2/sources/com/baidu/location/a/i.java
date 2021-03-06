package com.baidu.location.a;

import android.content.Context;
import android.util.Log;
import com.baidu.lbsapi.auth.LBSAuthManager;
import com.baidu.lbsapi.auth.LBSAuthManagerListener;
import com.baidu.location.h.a;
import org.json.JSONObject;

public class i implements LBSAuthManagerListener {
    private static Object a = new Object();
    private static i b = null;
    private int c = 0;
    private Context d = null;
    private long e = 0;
    private String f = null;

    public static i a() {
        i iVar;
        synchronized (a) {
            if (b == null) {
                b = new i();
            }
            iVar = b;
        }
        return iVar;
    }

    public static String b(Context context) {
        try {
            return LBSAuthManager.getInstance(context).getPublicKey(context);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String c(Context context) {
        try {
            return LBSAuthManager.getInstance(context).getMCode();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void a(Context context) {
        this.d = context;
        LBSAuthManager.getInstance(this.d).authenticate(false, "lbs_locsdk", null, this);
        this.e = System.currentTimeMillis();
    }

    public boolean b() {
        boolean z = true;
        if (!(this.c == 0 || this.c == LBSAuthManager.CODE_AUTHENTICATING || this.c == LBSAuthManager.CODE_UNAUTHENTICATE || this.c == -10 || this.c == -11)) {
            z = false;
        }
        if (this.d != null) {
            long currentTimeMillis = System.currentTimeMillis() - this.e;
            if (z) {
                if (currentTimeMillis > 86400000) {
                    LBSAuthManager.getInstance(this.d).authenticate(false, "lbs_locsdk", null, this);
                    this.e = System.currentTimeMillis();
                }
            } else if (currentTimeMillis < 0 || currentTimeMillis > 10000) {
                LBSAuthManager.getInstance(this.d).authenticate(false, "lbs_locsdk", null, this);
                this.e = System.currentTimeMillis();
            }
        }
        return z;
    }

    public void onAuthResult(int i, String str) {
        this.c = i;
        Log.i(a.a, "LocationAuthManager Authentication Error errorcode = " + i + " , msg = " + str);
        if (str != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject != null && jSONObject.getString("token") != null) {
                    this.f = jSONObject.getString("token");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
