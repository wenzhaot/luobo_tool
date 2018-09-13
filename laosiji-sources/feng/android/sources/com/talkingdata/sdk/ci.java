package com.talkingdata.sdk;

import com.netease.LDNetDiagnoUtils.LDNetUtil;
import com.taobao.accs.AccsClientConfig;
import com.tencent.tauth.AuthActivity;
import com.umeng.message.entity.UMessage;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: td */
public class ci {
    private static volatile ci a = null;
    private static String b = "account";
    private static String c = "accountId";
    private static String d = "name";
    private static String e = "gender";
    private static String f = "age";
    private static String g = "type";
    private static String h = "accountCus";
    private static String j = AccsClientConfig.DEFAULT_CONFIGTAG;
    private static JSONObject p;
    private String i;
    private a k = a.UNKNOWN;
    private String l;
    private int m;
    private String n;
    private JSONObject o;

    /* compiled from: td */
    public enum a {
        MALE,
        FEMALE,
        UNKNOWN
    }

    public final void onTDEBEventAccount(com.talkingdata.sdk.zz.a aVar) {
        if (aVar != null) {
            try {
                if (aVar.paraMap != null && Integer.parseInt(String.valueOf(aVar.paraMap.get("apiType"))) == 9) {
                    aVar.paraMap.get("account");
                    a aVar2 = (a) aVar.paraMap.get("service");
                    Object obj = aVar.paraMap.get("data");
                    Object obj2 = aVar.paraMap.get("domain");
                    Object obj3 = aVar.paraMap.get(AuthActivity.ACTION_KEY);
                    Object obj4 = aVar.paraMap.get("immediate");
                    if (obj3 == null) {
                        return;
                    }
                    if (obj3.equals("login") || obj3.equals("register") || obj3.equals("logout")) {
                        a(String.valueOf(aVar.paraMap.get("accountId")), obj3, obj2, aVar2);
                    } else if (obj3.equals("roleCreate")) {
                        a(String.valueOf(aVar.paraMap.get("parameter")), aVar2);
                    } else {
                        a(obj2, obj3, obj, aVar2);
                        if (a(obj4)) {
                            a(aVar2);
                        }
                    }
                }
            } catch (Throwable th) {
                cs.postSDKError(th);
            }
        }
    }

    private boolean a(Object obj) {
        if (obj != null) {
            try {
                return ((Boolean) obj).booleanValue();
            } catch (Throwable th) {
            }
        }
        return false;
    }

    private void a(a aVar) {
        try {
            dc dcVar = new dc();
            dcVar.b = com.talkingdata.sdk.dc.a.IMMEDIATELY;
            dcVar.a = aVar;
            br.a().post(dcVar);
        } catch (Throwable th) {
        }
    }

    protected static void a(Object obj, Object obj2, Object obj3, a aVar) {
        if (aVar != null && obj != null && obj2 != null && (obj instanceof String) && (obj2 instanceof String)) {
            dd ddVar = new dd();
            ddVar.b = String.valueOf(obj);
            ddVar.c = String.valueOf(obj2);
            if (obj3 != null && (obj3 instanceof Map)) {
                ddVar.d = (Map) obj3;
            }
            ddVar.a = aVar;
            br.a().post(ddVar);
        }
    }

    public void a(String str, Object obj, Object obj2, a aVar) {
        try {
            a(str);
            Object d = d();
            dl.a().setAccount(new JSONObject(d));
            a(obj2, obj, d, aVar);
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    private void b() {
        try {
            Map d = d();
            com.talkingdata.sdk.zz.a aVar = new com.talkingdata.sdk.zz.a();
            aVar.paraMap.put("apiType", Integer.valueOf(9));
            aVar.paraMap.put("domain", b);
            aVar.paraMap.put(AuthActivity.ACTION_KEY, "update");
            aVar.paraMap.put("data", d);
            zz.c().obtainMessage(102, aVar).sendToTarget();
            dl.a().setAccount(new JSONObject(d));
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    private void a(String str) {
        try {
            this.i = str;
            String a = ar.a(this.i);
            if (a != null) {
                try {
                    JSONObject jSONObject = new JSONObject(a);
                    if (jSONObject.has(d)) {
                        this.l = jSONObject.getString(d);
                    }
                    if (jSONObject.has(e)) {
                        this.k = a.valueOf(jSONObject.getString(e));
                    }
                    if (jSONObject.has(f)) {
                        this.m = jSONObject.getInt(f);
                    }
                    if (jSONObject.has(g)) {
                        this.n = jSONObject.getString(g);
                    }
                    if (jSONObject.has(h)) {
                        this.o = jSONObject.getJSONObject(h);
                    }
                } catch (Throwable th) {
                }
            }
        } catch (Throwable th2) {
            cs.postSDKError(th2);
        }
    }

    public void setName(String str) {
        try {
            if (this.l == null || !this.l.equalsIgnoreCase(str)) {
                this.l = str;
                b();
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public void setGender(a aVar) {
        try {
            if (this.k != aVar) {
                this.k = aVar;
                b();
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public void setAge(int i) {
        try {
            if (this.m != i) {
                this.m = i;
                b();
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public void setAccountType(String str) {
        try {
            if (this.n == null || !this.n.equalsIgnoreCase(str)) {
                this.n = str;
                b();
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public static synchronized void a(String str, a aVar) {
        synchronized (ci.class) {
            try {
                ar.setLastRoleName(str);
                p = null;
                j = str;
                String b = ar.b(str);
                if (b != null) {
                    try {
                        p = new JSONObject(b);
                        b(aVar);
                    } catch (JSONException e) {
                        aq.dForInternal(e.getMessage());
                    }
                } else {
                    p = new JSONObject();
                    c();
                    Object e2 = e();
                    a(b, (Object) "roleCreate", e2, aVar);
                    dl.a().setSubaccount(new JSONObject(e2));
                }
            } catch (Throwable th) {
            }
        }
    }

    public synchronized void a(String str, String str2) {
        if (p == null) {
            p = new JSONObject();
        }
        try {
            p.put(str, str2);
            c();
            f();
        } catch (Throwable th) {
        }
    }

    public synchronized void a(String str, int i) {
        if (p == null) {
            p = new JSONObject();
        }
        try {
            p.put(str, i);
            c();
            f();
        } catch (Throwable th) {
        }
    }

    public synchronized void b(String str, String str2) {
        if (this.o == null) {
            this.o = new JSONObject();
        }
        try {
            this.o.put(str, str2);
            b();
        } catch (Throwable th) {
        }
    }

    public synchronized void b(String str, int i) {
        if (this.o == null) {
            this.o = new JSONObject();
        }
        try {
            this.o.put(str, i);
            b();
        } catch (Throwable th) {
        }
    }

    private static void c() {
        ar.setLastRoleName(j);
        ar.b(j, p.toString());
    }

    private Map d() {
        Map treeMap = new TreeMap();
        try {
            treeMap.put(c, this.i);
            if (this.m != 0) {
                treeMap.put(f, Integer.valueOf(this.m));
            }
            if (!LDNetUtil.NETWORKTYPE_INVALID.equals(this.k.name())) {
                treeMap.put(e, this.k.name());
            }
            if (this.l != null) {
                treeMap.put(d, this.l);
            }
            if (this.n != null) {
                treeMap.put(g, this.n);
            }
            if (this.o != null && this.o.length() > 0) {
                treeMap.put(UMessage.DISPLAY_TYPE_CUSTOM, this.o);
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
        return treeMap;
    }

    private static Map e() {
        Map treeMap = new TreeMap();
        try {
            treeMap.put("name", j);
            if (p != null && p.length() > 0) {
                treeMap.put(UMessage.DISPLAY_TYPE_CUSTOM, p);
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
        return treeMap;
    }

    private static void f() {
        try {
            dl.a().setSubaccount(new JSONObject(e()));
            b(null);
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    private static void b(a aVar) {
        try {
            Object e = e();
            dl.a().setSubaccount(new JSONObject(e));
            a(b, (Object) "roleUpdate", e, aVar);
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    static {
        try {
            br.a().register(a());
        } catch (Throwable th) {
        }
    }

    public static ci a() {
        if (a == null) {
            synchronized (ci.class) {
                if (a == null) {
                    a = new ci();
                }
            }
        }
        return a;
    }

    private ci() {
    }
}
