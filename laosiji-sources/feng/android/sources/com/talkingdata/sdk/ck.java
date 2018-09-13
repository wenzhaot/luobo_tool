package com.talkingdata.sdk;

import android.os.Handler;
import android.os.HandlerThread;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.meizu.cloud.pushsdk.pushtracer.constant.Parameters;
import com.talkingdata.sdk.as.e;
import com.talkingdata.sdk.bb.b;
import com.umeng.commonsdk.proguard.g;
import com.umeng.message.util.HttpRequest;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: td */
public class ck {
    private static String f = null;
    private static String g = "TDpref_cloudcontrol";
    private static final String h = "TDpref_config";
    private static final String i = "TDpref_last_request_time";
    private static final String j = "TDpref_cloud_cv";
    private static String[] k = new String[]{"232", "206", "284", "280", "219", "230", "238", "248", "244", "208", "308", "340", "543", "546", "547", "647", "742", "262", "202", "216", "272", "222", "247", "246", "270", "278", "204", "363", "362", "260", "268", "226", "231", "293", "214", "240", "234", "235", "266", "346", "348", "350", "354", "376", "750", "454", "455", "466", "525", "310", "311", "312", "313", "314", "315", "316", "330", "332", "534", "535", "544", "302", "505", "530", "548"};
    private static boolean l;
    private static String m;
    private static String n;
    private static String o;
    private static String p;
    private static boolean q = false;
    private static boolean t = true;
    private static volatile ck u = null;
    private long a = 720;
    private long b = 604800000;
    private HashMap c = new HashMap();
    private HashSet d = new HashSet();
    private JSONObject e = new JSONObject();
    private int r;
    private Handler s = null;

    public static ck a() {
        if (u == null) {
            synchronized (ck.class) {
                if (u == null) {
                    u = new ck();
                }
            }
        }
        return u;
    }

    private ck() {
        HandlerThread handlerThread = new HandlerThread("ModuleCloudControl");
        handlerThread.start();
        this.s = new cl(this, handlerThread.getLooper());
    }

    public void a(String str, String str2, a aVar) {
        boolean z;
        this.r = a(aVar);
        String n = bd.n(ab.g);
        String m = bd.m(ab.g);
        m = bo.b(n) ? "" : n.substring(0, 3);
        n = bo.b(n) ? "" : n.substring(3);
        o = bo.b(m) ? "" : m.substring(0, 3);
        p = bo.b(m) ? "" : m.substring(3);
        if (b(m) || b(o)) {
            z = true;
        } else {
            z = false;
        }
        l = z;
        d();
        g();
        try {
            bl.a.execute(new cm(this, str, str2, aVar));
        } catch (Throwable th) {
        }
        q = true;
    }

    private void d() {
        try {
            g += this.r;
            f = b.Cloud_Control_Lock_File.toString() + this.r;
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    private boolean e() {
        long b = bi.b(ab.g, g, i, 0);
        long currentTimeMillis = System.currentTimeMillis();
        if (b == 0 || currentTimeMillis - b > (this.a * 60) * 1000 || currentTimeMillis - b > this.b) {
            return true;
        }
        return false;
    }

    private void b(String str, String str2, a aVar) {
        try {
            JSONObject jSONObject = new JSONObject();
            a(jSONObject, "bl", Integer.valueOf(this.r));
            a(jSONObject, "pl", Integer.valueOf(1));
            a(jSONObject, "sv", (Object) "4.0.21");
            a(jSONObject, "smcc", m);
            a(jSONObject, "smnc", n);
            a(jSONObject, "bmcc", o);
            a(jSONObject, "bmnc", p);
            a(jSONObject, "dt", (Object) "Mobile");
            a(jSONObject, Parameters.OS_VERSION, aw.a());
            a(jSONObject, "av", ar.k());
            a(jSONObject, "px", aw.c(ab.g));
            a(jSONObject, "nt", bd.j(ab.g));
            a(jSONObject, "op", bd.r(ab.g));
            a(jSONObject, "ch", (Object) str2);
            a(jSONObject, "cv", Long.valueOf(bi.b(ab.g, g, j, 0)));
            a(jSONObject, "TDID", au.a(ab.g));
            a(jSONObject, "AppID", (Object) str);
            e a = as.a(ab.g, aa.n, aa.p, "https://cloud.xdrig.com/configcloud/rest/sdk/match", "", jSONObject.toString().getBytes(), HttpRequest.ENCODING_GZIP, "application/json");
            bb.getFileLock(f);
            if (a.getStatusCode() == 200) {
                b(a(new JSONObject(a.getResponseMsg())));
                bi.a(ab.g, g, i, System.currentTimeMillis());
            } else if (t) {
                HashMap hashMap = new HashMap();
                hashMap.put("appId", str);
                hashMap.put("channelId", str2);
                hashMap.put("Features", aVar);
                this.s.sendMessageDelayed(this.s.obtainMessage(0, hashMap), 5000);
            }
            bb.releaseFileLock(f);
        } catch (Throwable th) {
            bb.releaseFileLock(f);
            throw th;
        }
    }

    private void a(JSONObject jSONObject, String str, Object obj) {
        if (obj != null) {
            try {
                if (!(obj instanceof String) || !obj.equals("")) {
                    jSONObject.put(str, obj);
                }
            } catch (Throwable th) {
            }
        }
    }

    private JSONObject a(JSONObject jSONObject) {
        try {
            String b = bi.b(ab.g, g, h, "");
            if (bo.b(b)) {
                bi.a(ab.g, g, h, jSONObject.toString());
                return jSONObject;
            }
            JSONObject jSONObject2 = new JSONObject(b);
            if (jSONObject.has("cv") && jSONObject2.getInt("cv") == jSONObject.getInt("cv")) {
                jSONObject2.put("r", jSONObject.getJSONArray("r"));
                bi.a(ab.g, g, h, jSONObject2.toString());
                return jSONObject2;
            }
            bi.a(ab.g, g, h, jSONObject.toString());
            if (!jSONObject.has("cv")) {
                return jSONObject;
            }
            bi.a(ab.g, g, j, jSONObject.getLong("cv"));
            return jSONObject;
        } catch (Throwable th) {
            cs.postSDKError(th);
            return null;
        }
    }

    private void f() {
        try {
            if (this.e.length() == 0) {
                String a = a(bb.b(f));
                JSONObject jSONObject = null;
                if (!bo.b(a)) {
                    jSONObject = new JSONObject(a);
                }
                if (jSONObject != null && jSONObject.length() > 0) {
                    this.e = jSONObject;
                }
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    private void g() {
        try {
            bb.getFileLock(f);
            String b = bi.b(ab.g, g, h, "");
            JSONObject jSONObject = null;
            if (!bo.b(b)) {
                jSONObject = new JSONObject(b);
            }
            b(jSONObject);
        } catch (Throwable th) {
            cs.postSDKError(th);
        } finally {
            bb.releaseFileLock(f);
        }
    }

    private void b(JSONObject jSONObject) {
        if (jSONObject != null) {
            try {
                if (jSONObject.has(g.aq)) {
                    this.a = (long) jSONObject.getInt(g.aq);
                }
                this.d = new HashSet();
                if (jSONObject.has("c") && jSONObject.has("r")) {
                    JSONArray jSONArray = jSONObject.getJSONArray("c");
                    JSONArray jSONArray2 = jSONObject.getJSONArray("r");
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                        int i2 = jSONObject2.getInt("id");
                        for (int i3 = 0; i3 < jSONArray2.length(); i3++) {
                            if (i2 == jSONArray2.getInt(i3)) {
                                a(jSONObject2, String.valueOf(i2));
                            }
                            this.d.add(String.valueOf(i2));
                        }
                    }
                    if (jSONArray.length() == 0) {
                        this.c = new HashMap();
                    }
                    f();
                    h();
                }
            } catch (Throwable th) {
                cs.postSDKError(th);
            }
        }
    }

    private void a(JSONObject jSONObject, String str) {
        try {
            JSONArray jSONArray = (JSONArray) jSONObject.remove("clt");
            jSONObject.remove("id");
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < jSONArray.length()) {
                    String string = jSONArray.getString(i2);
                    if (!bo.b(string)) {
                        if (this.c.containsKey(string)) {
                            ((JSONObject) this.c.get(string)).put(str, jSONObject);
                        } else {
                            this.c.put(string, new JSONObject().put(str, jSONObject));
                        }
                    }
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public JSONArray a(String str) {
        return a(str, null, null);
    }

    public JSONArray a(String str, JSONObject jSONObject, JSONObject jSONObject2) {
        JSONArray jSONArray = new JSONArray();
        if (!q) {
            return jSONArray;
        }
        try {
            if (ab.q) {
                return jSONArray;
            }
            if (this.c.containsKey(str)) {
                if (!(jSONObject == null || jSONObject2 == null)) {
                    if (jSONObject.has(str)) {
                        return jSONArray;
                    }
                    if (jSONObject2.has(str)) {
                        return null;
                    }
                    jSONObject2.put(str, true);
                }
                JSONObject jSONObject3 = (JSONObject) this.c.get(str);
                Iterator keys = jSONObject3.keys();
                List arrayList = new ArrayList();
                f();
                while (keys.hasNext()) {
                    String str2 = (String) keys.next();
                    arrayList.add(str2);
                    JSONObject jSONObject4 = jSONObject3.getJSONObject(str2);
                    long j = jSONObject4.getLong("st");
                    long j2 = jSONObject4.getLong("et");
                    int i = jSONObject4.getInt(AdvanceSetting.CLEAR_NOTIFICATION);
                    long currentTimeMillis = System.currentTimeMillis();
                    if (currentTimeMillis >= j && currentTimeMillis <= j2 && i > 0) {
                        if (this.e.has(str)) {
                            JSONObject jSONObject5 = this.e.getJSONObject(str);
                            if (!jSONObject5.has(str2)) {
                                this.e.put(str, jSONObject5.put(str2, 1));
                                jSONArray.put(Integer.parseInt(str2));
                            } else if (jSONObject5.getInt(str2) < i) {
                                jSONArray.put(Integer.parseInt(str2));
                                jSONObject5.put(str2, jSONObject5.getInt(str2) + 1);
                                this.e.put(str, jSONObject5);
                            }
                        } else {
                            this.e.put(str, new JSONObject().put(str2, 1));
                            jSONArray.put(Integer.parseInt(str2));
                        }
                    }
                }
                if (jSONArray.length() == 0) {
                    return null;
                }
                return jSONArray;
            } else if (l) {
                return jSONArray;
            } else {
                return null;
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    private void h() {
        try {
            Iterator keys = this.e.keys();
            while (keys.hasNext()) {
                JSONObject jSONObject = this.e.getJSONObject((String) keys.next());
                Iterator keys2 = jSONObject.keys();
                ArrayList arrayList = new ArrayList();
                while (keys2.hasNext()) {
                    String str = (String) keys2.next();
                    if (!this.d.contains(str)) {
                        arrayList.add(str);
                    }
                }
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 < arrayList.size()) {
                        jSONObject.remove((String) arrayList.get(i2));
                        i = i2 + 1;
                    }
                }
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    private int a(a aVar) {
        switch (aVar.index()) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 3:
                return 3;
            default:
                return -1;
        }
    }

    private void a(RandomAccessFile randomAccessFile, String str) {
        try {
            randomAccessFile.setLength(0);
            randomAccessFile.seek(0);
            randomAccessFile.write(str.getBytes());
        } catch (Throwable th) {
        }
    }

    private String a(RandomAccessFile randomAccessFile) {
        try {
            byte[] bArr = new byte[((int) randomAccessFile.length())];
            randomAccessFile.seek(0);
            randomAccessFile.read(bArr);
            return new String(bArr);
        } catch (Throwable th) {
            return null;
        }
    }

    public String b() {
        return f;
    }

    private boolean b(String str) {
        try {
            if (bo.b(str)) {
                return false;
            }
            for (Object equals : k) {
                if (str.equals(equals)) {
                    return false;
                }
            }
            return true;
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public void c() {
        a(bb.b(f), this.e.toString());
        this.e = new JSONObject();
    }
}
