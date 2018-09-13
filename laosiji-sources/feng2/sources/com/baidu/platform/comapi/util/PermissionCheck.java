package com.baidu.platform.comapi.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.baidu.lbsapi.auth.LBSAuthManager;
import com.baidu.lbsapi.auth.LBSAuthManagerListener;
import java.util.Hashtable;
import org.json.JSONException;
import org.json.JSONObject;

public class PermissionCheck {
    private static final String a = PermissionCheck.class.getSimpleName();
    private static Context b;
    private static String c;
    private static Hashtable<String, String> d;
    private static LBSAuthManager e = null;
    private static LBSAuthManagerListener f = null;
    private static c g = null;

    public interface c {
        void a(b bVar);
    }

    private static class a implements LBSAuthManagerListener {
        private a() {
        }

        public void onAuthResult(int i, String str) {
            if (str == null) {
                Log.e(PermissionCheck.a, "The result is null");
                Log.d(PermissionCheck.a, "onAuthResult try permissionCheck result is: " + PermissionCheck.permissionCheck());
                return;
            }
            b bVar = new b();
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has(NotificationCompat.CATEGORY_STATUS)) {
                    bVar.a = jSONObject.optInt(NotificationCompat.CATEGORY_STATUS);
                }
                if (jSONObject.has("appid")) {
                    bVar.c = jSONObject.optString("appid");
                }
                if (jSONObject.has("uid")) {
                    bVar.b = jSONObject.optString("uid");
                }
                if (jSONObject.has("message")) {
                    bVar.d = jSONObject.optString("message");
                }
                if (jSONObject.has("token")) {
                    bVar.e = jSONObject.optString("token");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (PermissionCheck.g != null) {
                PermissionCheck.g.a(bVar);
            }
        }
    }

    public static class b {
        public int a = 0;
        public String b = "-1";
        public String c = "-1";
        public String d = "";
        public String e;

        public String toString() {
            return String.format("=============================================\n----------------- 鉴权错误信息 ------------\nsha1;package:%s\nkey:%s\nerrorcode: %d uid: %s appid %s msg: %s\n请仔细核查 SHA1、package与key申请信息是否对应，key是否删除，平台是否匹配\nerrorcode为230时，请参考论坛链接：\nhttp://bbs.lbsyun.baidu.com/forum.php?mod=viewthread&tid=106461\n=============================================\n", new Object[]{a.a(PermissionCheck.b), PermissionCheck.c, Integer.valueOf(this.a), this.b, this.c, this.d});
        }
    }

    public static void destory() {
        g = null;
        b = null;
        f = null;
    }

    public static void init(Context context) {
        ApplicationInfo applicationInfo;
        b = context;
        try {
            applicationInfo = b.getPackageManager().getApplicationInfo(b.getPackageName(), 128);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            applicationInfo = null;
        }
        if (applicationInfo != null) {
            c = applicationInfo.metaData.getString("com.baidu.lbsapi.API_KEY");
        }
        if (d == null) {
            d = new Hashtable();
        }
        if (e == null) {
            e = LBSAuthManager.getInstance(b);
        }
        if (f == null) {
            f = new a();
        }
        Object obj = "";
        try {
            obj = context.getPackageManager().getPackageInfo(b.getPackageName(), 0).applicationInfo.loadLabel(b.getPackageManager()).toString();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        Bundle b = e.b();
        d.put("mb", b.getString("mb"));
        d.put("os", b.getString("os"));
        d.put("sv", b.getString("sv"));
        d.put("imt", "1");
        d.put("net", b.getString("net"));
        d.put("cpu", b.getString("cpu"));
        d.put("glr", b.getString("glr"));
        d.put("glv", b.getString("glv"));
        d.put("resid", b.getString("resid"));
        d.put("appid", "-1");
        d.put("ver", "1");
        d.put("screen", String.format("(%d,%d)", new Object[]{Integer.valueOf(b.getInt("screen_x")), Integer.valueOf(b.getInt("screen_y"))}));
        d.put("dpi", String.format("(%d,%d)", new Object[]{Integer.valueOf(b.getInt("dpi_x")), Integer.valueOf(b.getInt("dpi_y"))}));
        d.put("pcn", b.getString("pcn"));
        d.put("cuid", b.getString("cuid"));
        d.put("name", obj);
    }

    public static synchronized int permissionCheck() {
        int i = 0;
        synchronized (PermissionCheck.class) {
            if (e == null || f == null || b == null) {
                Log.e(a, "The authMamager is: " + e + "; the authCallback is: " + f + "; the mContext is: " + b);
            } else {
                i = e.authenticate(false, "lbs_androidsdk", d, f);
                if (i != 0) {
                    Log.e(a, "permission check result is: " + i);
                }
            }
        }
        return i;
    }

    public static void setPermissionCheckResultListener(c cVar) {
        g = cVar;
    }
}
