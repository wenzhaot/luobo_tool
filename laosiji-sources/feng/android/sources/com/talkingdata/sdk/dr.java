package com.talkingdata.sdk;

import com.feng.car.utils.UmengConstans;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: td */
public class dr extends dq {
    public static final String a = "TalkingData";
    public static final String c = "SaaS";
    public static final int d = 0;
    public static final int e = 1;
    public static final int f = 2;
    public static final int g = 3;
    public static final int h = 4;
    private static String j = "";
    private static int k;
    private final String i = "Android";

    static {
        k = 0;
        k = 3;
    }

    public dr() {
        a("version", (Object) Integer.valueOf(4));
        a("minorVersion", (Object) Integer.valueOf(0));
        a("build", (Object) Integer.valueOf(21));
        a("partner", (Object) c);
        a("platform", (Object) "Android");
        a("type", (Object) a);
        a("framework", (Object) j);
        if (k > 0) {
            a(UmengConstans.FROM, (Object) Integer.valueOf(k));
        }
    }

    public void setFrameWork(String str) {
        j = str;
    }

    public String b() {
        return j;
    }

    public void a(String str, String str2, String str3) {
        JSONArray jSONArray = null;
        if (this.b.isNull("features")) {
            jSONArray = new JSONArray();
        } else {
            try {
                jSONArray = this.b.getJSONArray("features");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (jSONArray != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("name", str);
                jSONObject.put("version", str2);
                jSONObject.put("minorVersion", str3);
                jSONArray.put(jSONObject);
            } catch (Throwable e2) {
                e2.printStackTrace();
                cs.postSDKError(e2);
            }
            a("features", (Object) jSONArray);
        }
    }
}
