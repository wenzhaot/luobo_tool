package com.huawei.android.pushselfshow.b;

import android.text.TextUtils;
import com.feng.car.utils.HttpConstant;
import com.huawei.android.pushagent.a.a.c;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.taobao.accs.common.Constants;
import com.taobao.agoo.a.a.b;
import java.io.Serializable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a implements Serializable {
    public String A;
    public String B;
    public String C;
    public String D;
    public String E;
    public String F = "";
    public int G = 1;
    public int H = 0;
    public String I;
    public String J;
    public String K;
    public int L = com.huawei.android.pushselfshow.c.a.STYLE_1.ordinal();
    public int M = 0;
    public String[] N = null;
    public String[] O = null;
    public String[] P = null;
    public int Q = 0;
    public String[] R = null;
    public String S = "";
    public String T = "";
    public String a = "";
    public String b;
    public String c;
    public String d;
    public String e;
    public int f;
    public String g;
    public int h;
    public String i;
    public int j;
    public int k;
    public String l;
    public String m = "";
    public String n;
    public String o;
    public String p;
    public String q;
    public String r = "";
    public String s;
    public String t;
    public String u;
    public String v;
    public String w;
    public String x;
    public String y;
    public String z = "";

    public a(byte[] bArr, byte[] bArr2) {
        try {
            this.J = new String(bArr, "UTF-8");
            this.K = new String(bArr2, "UTF-8");
        } catch (Exception e) {
            c.d("PushSelfShowLog", "get msg byte arr error");
        }
    }

    private boolean a(JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("param");
            if (jSONObject2.has("autoClear")) {
                this.f = jSONObject2.getInt("autoClear");
            } else {
                this.f = 0;
            }
            if (PushConstants.EXTRA_APPLICATION_PENDING_INTENT.equals(this.p) || "cosa".equals(this.p)) {
                if (jSONObject2.has("acn")) {
                    this.B = jSONObject2.getString("acn");
                    this.g = this.B;
                }
                if (jSONObject2.has("intentUri")) {
                    this.g = jSONObject2.getString("intentUri");
                }
                if (jSONObject2.has("appPackageName")) {
                    this.A = jSONObject2.getString("appPackageName");
                } else {
                    c.a("PushSelfShowLog", "appPackageName is null");
                    return false;
                }
            } else if ("email".equals(this.p)) {
                if (jSONObject2.has("emailAddr") && jSONObject2.has("emailSubject")) {
                    this.x = jSONObject2.getString("emailAddr");
                    this.y = jSONObject2.getString("emailSubject");
                    if (jSONObject2.has("emailContent")) {
                        this.z = jSONObject2.getString("emailContent");
                    }
                } else {
                    c.a("PushSelfShowLog", "emailAddr or emailSubject is null");
                    return false;
                }
            } else if (HttpConstant.PHONE.equals(this.p)) {
                if (jSONObject2.has("phoneNum")) {
                    this.w = jSONObject2.getString("phoneNum");
                } else {
                    c.a("PushSelfShowLog", "phoneNum is null");
                    return false;
                }
            } else if ("url".equals(this.p)) {
                if (jSONObject2.has("url")) {
                    this.C = jSONObject2.getString("url");
                    if (jSONObject2.has("inBrowser")) {
                        this.G = jSONObject2.getInt("inBrowser");
                    }
                    if (jSONObject2.has("needUserId")) {
                        this.H = jSONObject2.getInt("needUserId");
                    }
                    if (jSONObject2.has(Constants.KEY_SECURITY_SIGN)) {
                        this.I = jSONObject2.getString(Constants.KEY_SECURITY_SIGN);
                    }
                    if (jSONObject2.has("rpt") && jSONObject2.has("rpl")) {
                        this.D = jSONObject2.getString("rpl");
                        this.E = jSONObject2.getString("rpt");
                        if (jSONObject2.has("rpct")) {
                            this.F = jSONObject2.getString("rpct");
                        }
                    }
                } else {
                    c.a("PushSelfShowLog", "url is null");
                    return false;
                }
            } else if ("rp".equals(this.p)) {
                if (jSONObject2.has("rpt") && jSONObject2.has("rpl")) {
                    this.D = jSONObject2.getString("rpl");
                    this.E = jSONObject2.getString("rpt");
                    if (jSONObject2.has("rpct")) {
                        this.F = jSONObject2.getString("rpct");
                    }
                    if (jSONObject2.has("needUserId")) {
                        this.H = jSONObject2.getInt("needUserId");
                    }
                } else {
                    c.a("PushSelfShowLog", "rpl or rpt is null");
                    return false;
                }
            }
            return true;
        } catch (Throwable e) {
            c.c("PushSelfShowLog", "ParseParam error ", e);
            return false;
        }
    }

    private boolean b(JSONObject jSONObject) {
        c.a("PushSelfShowLog", "enter parseNotifyParam");
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("notifyParam");
            if (!jSONObject2.has("style")) {
                return false;
            }
            int i;
            String str;
            this.L = jSONObject2.getInt("style");
            c.a("PushSelfShowLog", "style:" + this.L);
            if (jSONObject2.has("btnCount")) {
                this.M = jSONObject2.getInt("btnCount");
            }
            if (this.M > 0) {
                if (this.M > 3) {
                    this.M = 3;
                }
                c.a("PushSelfShowLog", "btnCount:" + this.M);
                this.N = new String[this.M];
                this.O = new String[this.M];
                this.P = new String[this.M];
                for (i = 0; i < this.M; i++) {
                    str = "btn" + (i + 1) + "Text";
                    String str2 = "btn" + (i + 1) + "Image";
                    String str3 = "btn" + (i + 1) + "Event";
                    if (jSONObject2.has(str)) {
                        this.N[i] = jSONObject2.getString(str);
                    }
                    if (jSONObject2.has(str2)) {
                        this.O[i] = jSONObject2.getString(str2);
                    }
                    if (jSONObject2.has(str3)) {
                        this.P[i] = jSONObject2.getString(str3);
                    }
                }
            }
            com.huawei.android.pushselfshow.c.a aVar = com.huawei.android.pushselfshow.c.a.STYLE_1;
            if (this.L >= 0 && this.L < com.huawei.android.pushselfshow.c.a.values().length) {
                aVar = com.huawei.android.pushselfshow.c.a.values()[this.L];
            }
            switch (aVar) {
                case STYLE_4:
                    if (jSONObject2.has("iconCount")) {
                        this.Q = jSONObject2.getInt("iconCount");
                    }
                    if (this.Q > 0) {
                        if (this.Q > 6) {
                            this.Q = 6;
                        }
                        c.a("PushSelfShowLog", "iconCount:" + this.Q);
                        this.R = new String[this.Q];
                        for (i = 0; i < this.Q; i++) {
                            str = "icon" + (i + 1);
                            if (jSONObject2.has(str)) {
                                this.R[i] = jSONObject2.getString(str);
                            }
                        }
                        break;
                    }
                    break;
                case STYLE_5:
                    if (jSONObject2.has("subTitle")) {
                        this.S = jSONObject2.getString("subTitle");
                        c.a("PushSelfShowLog", "subTitle:" + this.S);
                        break;
                    }
                    break;
                case STYLE_6:
                case STYLE_8:
                    if (jSONObject2.has("bigPic")) {
                        this.T = jSONObject2.getString("bigPic");
                        c.a("PushSelfShowLog", "bigPicUrl:" + this.T);
                        break;
                    }
                    break;
            }
            return true;
        } catch (JSONException e) {
            c.b("PushSelfShowLog", e.toString());
            return false;
        }
    }

    public String a() {
        c.a("PushSelfShowLog", "msgId =" + this.m);
        return this.m;
    }

    public boolean b() {
        try {
            if (this.K == null || this.K.length() == 0) {
                c.a("PushSelfShowLog", "token is null");
                return false;
            }
            this.i = this.K;
            if (this.J == null || this.J.length() == 0) {
                c.a("PushSelfShowLog", "msg is null");
                return false;
            }
            JSONObject jSONObject = new JSONObject(this.J);
            this.h = jSONObject.getInt("msgType");
            if (this.h != 1) {
                c.a("PushSelfShowLog", "not a selefShowMsg");
                return false;
            }
            if (jSONObject.has("group")) {
                this.a = jSONObject.getString("group");
                c.a("PushSelfShowLog", "NOTIFY_GROUP:" + this.a);
            }
            JSONObject jSONObject2 = jSONObject.getJSONObject("msgContent");
            if (jSONObject2 == null) {
                c.b("PushSelfShowLog", "msgObj == null");
                return false;
            } else if (jSONObject2.has("msgId")) {
                Object obj = jSONObject2.get("msgId");
                if (obj instanceof String) {
                    this.m = (String) obj;
                } else if (obj instanceof Integer) {
                    this.m = String.valueOf(((Integer) obj).intValue());
                }
                if (jSONObject2.has("dispPkgName")) {
                    this.n = jSONObject2.getString("dispPkgName");
                }
                if (jSONObject2.has("rtn")) {
                    this.k = jSONObject2.getInt("rtn");
                } else {
                    this.k = 1;
                }
                if (jSONObject2.has("fm")) {
                    this.j = jSONObject2.getInt("fm");
                } else {
                    this.j = 1;
                }
                if (jSONObject2.has("ap")) {
                    String string = jSONObject2.getString("ap");
                    StringBuilder stringBuilder = new StringBuilder();
                    if (TextUtils.isEmpty(string) || string.length() >= 48) {
                        this.l = string.substring(0, 48);
                    } else {
                        for (int i = 0; i < 48 - string.length(); i++) {
                            stringBuilder.append(PushConstants.PUSH_TYPE_NOTIFY);
                        }
                        stringBuilder.append(string);
                        this.l = stringBuilder.toString();
                    }
                }
                if (jSONObject2.has("extras")) {
                    this.o = jSONObject2.getJSONArray("extras").toString();
                }
                if (!jSONObject2.has("psContent")) {
                    return false;
                }
                jSONObject = jSONObject2.getJSONObject("psContent");
                if (jSONObject == null) {
                    return false;
                }
                this.p = jSONObject.getString(b.JSON_CMD);
                if (jSONObject.has("content")) {
                    this.q = jSONObject.getString("content");
                } else {
                    this.q = "";
                }
                if (jSONObject.has("notifyIcon")) {
                    this.r = jSONObject.getString("notifyIcon");
                } else {
                    this.r = "" + this.m;
                }
                if (jSONObject.has("statusIcon")) {
                    this.t = jSONObject.getString("statusIcon");
                }
                if (jSONObject.has("notifyTitle")) {
                    this.s = jSONObject.getString("notifyTitle");
                }
                if (jSONObject.has("notifyParam")) {
                    b(jSONObject);
                }
                return jSONObject.has("param") ? a(jSONObject) : false;
            } else {
                c.b("PushSelfShowLog", "msgId == null");
                return false;
            }
        } catch (Throwable e) {
            c.a("PushSelfShowLog", e.toString(), e);
            return false;
        }
    }

    public byte[] c() {
        try {
            String str = "";
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObject3 = new JSONObject();
            JSONObject jSONObject4 = new JSONObject();
            jSONObject4.put("autoClear", this.f);
            jSONObject4.put("s", this.b);
            jSONObject4.put("r", this.c);
            jSONObject4.put("smsC", this.d);
            jSONObject4.put("mmsUrl", this.e);
            jSONObject4.put("url", this.C);
            jSONObject4.put("inBrowser", this.G);
            jSONObject4.put("needUserId", this.H);
            jSONObject4.put(Constants.KEY_SECURITY_SIGN, this.I);
            jSONObject4.put("rpl", this.D);
            jSONObject4.put("rpt", this.E);
            jSONObject4.put("rpct", this.F);
            jSONObject4.put("appPackageName", this.A);
            jSONObject4.put("acn", this.B);
            jSONObject4.put("intentUri", this.g);
            jSONObject4.put("emailAddr", this.x);
            jSONObject4.put("emailSubject", this.y);
            jSONObject4.put("emailContent", this.z);
            jSONObject4.put("phoneNum", this.w);
            jSONObject4.put("replyToSms", this.v);
            jSONObject4.put("smsNum", this.u);
            jSONObject3.put(b.JSON_CMD, this.p);
            jSONObject3.put("content", this.q);
            jSONObject3.put("notifyIcon", this.r);
            jSONObject3.put("notifyTitle", this.s);
            jSONObject3.put("statusIcon", this.t);
            jSONObject3.put("param", jSONObject4);
            jSONObject2.put("dispPkgName", this.n);
            jSONObject2.put("msgId", this.m);
            jSONObject2.put("fm", this.j);
            jSONObject2.put("ap", this.l);
            jSONObject2.put("rtn", this.k);
            jSONObject2.put("psContent", jSONObject3);
            if (this.o != null && this.o.length() > 0) {
                jSONObject2.put("extras", new JSONArray(this.o));
            }
            jSONObject.put("msgType", this.h);
            jSONObject.put("msgContent", jSONObject2);
            jSONObject.put("group", this.a);
            return jSONObject.toString().getBytes("UTF-8");
        } catch (Throwable e) {
            c.a("PushSelfShowLog", "getMsgData failed JSONException:", e);
            return new byte[0];
        } catch (Throwable e2) {
            c.a("PushSelfShowLog", "getMsgData failed UnsupportedEncodingException:", e2);
            return new byte[0];
        }
    }

    public byte[] d() {
        try {
            if (this.i != null && this.i.length() > 0) {
                return this.i.getBytes("UTF-8");
            }
        } catch (Throwable e) {
            c.a("PushSelfShowLog", "getToken getByte failed ", e);
        }
        return new byte[0];
    }
}
