package com.umeng.message.common;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import com.qiniu.android.common.Constants;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.proguard.g;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.proguard.c;
import com.umeng.message.proguard.k;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import org.json.JSONObject;

/* compiled from: Header */
public class b {
    public static final String a = "umid";
    private static final String ak = "Android";
    public static final String b = "Android";
    private static final String c = b.class.getName();
    private int A;
    private String B;
    private String C;
    private String D;
    private String E;
    private String F;
    private String G;
    private String H;
    private final String I = "appkey";
    private final String J = "channel";
    private final String K = "device_id";
    private final String L = "idmd5";
    private final String M = "mc";
    private final String N = "din";
    private final String O = "push_switch";
    private final String P = g.T;
    private final String Q = SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID;
    private final String R = "serial_number";
    private final String S = "device_model";
    private final String T = "os";
    private final String U = "os_version";
    private final String V = "resolution";
    private final String W = g.v;
    private final String X = "gpu_vender";
    private final String Y = "gpu_renderer";
    private final String Z = "app_version";
    private final String aa = "version_code";
    private final String ab = g.n;
    private final String ac = g.t;
    private final String ad = "sdk_version";
    private final String ae = g.L;
    private final String af = g.N;
    private final String ag = "language";
    private final String ah = g.P;
    private final String ai = g.Q;
    private final String aj = g.O;
    private final String al = "wrapper_type";
    private final String am = "wrapper_version";
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private long l;
    private String m;
    private String n;
    private String o;
    private String p;
    private String q;
    private String r;
    private String s;
    private String t;
    private String u;
    private String v;
    private String w;
    private String x;
    private String y;
    private String z;

    public b(String str, String str2) {
        this.d = str;
        this.e = str2;
    }

    private void d(JSONObject jSONObject) throws Exception {
        this.d = jSONObject.getString("appkey");
        this.f = jSONObject.getString("device_id");
        this.g = jSONObject.getString("idmd5");
        if (jSONObject.has("mc")) {
            this.h = jSONObject.getString("mc");
        }
        if (jSONObject.has("channel")) {
            this.e = jSONObject.getString("channel");
        }
        if (jSONObject.has(g.T)) {
            this.l = jSONObject.getLong(g.T);
        }
    }

    private void e(JSONObject jSONObject) throws Exception {
        String string;
        String str = null;
        this.o = jSONObject.has("device_model") ? jSONObject.getString("device_model") : null;
        if (jSONObject.has("os")) {
            string = jSONObject.getString("os");
        } else {
            string = null;
        }
        this.p = string;
        if (jSONObject.has("os_version")) {
            string = jSONObject.getString("os_version");
        } else {
            string = null;
        }
        this.q = string;
        if (jSONObject.has("resolution")) {
            string = jSONObject.getString("resolution");
        } else {
            string = null;
        }
        this.r = string;
        if (jSONObject.has(g.v)) {
            string = jSONObject.getString(g.v);
        } else {
            string = null;
        }
        this.s = string;
        if (jSONObject.has("gpu_vender")) {
            string = jSONObject.getString("gpu_vender");
        } else {
            string = null;
        }
        this.t = string;
        if (jSONObject.has("gpu_renderer")) {
            string = jSONObject.getString("gpu_renderer");
        } else {
            string = null;
        }
        this.u = string;
        if (jSONObject.has(SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID)) {
            string = jSONObject.getString(SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
        } else {
            string = null;
        }
        this.m = string;
        if (jSONObject.has("serial_number")) {
            str = jSONObject.getString("serial_number");
        }
        this.n = str;
    }

    private void f(JSONObject jSONObject) throws Exception {
        String string;
        String str = null;
        if (jSONObject.has("app_version")) {
            string = jSONObject.getString("app_version");
        } else {
            string = null;
        }
        this.v = string;
        if (jSONObject.has("version_code")) {
            string = jSONObject.getString("version_code");
        } else {
            string = null;
        }
        this.w = string;
        if (jSONObject.has(g.n)) {
            str = jSONObject.getString(g.n);
        }
        this.x = str;
    }

    private void g(JSONObject jSONObject) throws Exception {
        this.y = jSONObject.getString(g.t);
        this.z = jSONObject.getString("sdk_version");
    }

    private void h(JSONObject jSONObject) throws Exception {
        String string;
        String str = null;
        this.A = jSONObject.has(g.L) ? jSONObject.getInt(g.L) : 8;
        if (jSONObject.has(g.N)) {
            string = jSONObject.getString(g.N);
        } else {
            string = null;
        }
        this.B = string;
        if (jSONObject.has("language")) {
            str = jSONObject.getString("language");
        }
        this.C = str;
    }

    private void i(JSONObject jSONObject) throws Exception {
        String string;
        String str = null;
        if (jSONObject.has(g.P)) {
            string = jSONObject.getString(g.P);
        } else {
            string = null;
        }
        this.D = string;
        if (jSONObject.has(g.Q)) {
            string = jSONObject.getString(g.Q);
        } else {
            string = null;
        }
        this.E = string;
        if (jSONObject.has(g.O)) {
            str = jSONObject.getString(g.O);
        }
        this.F = str;
    }

    private void j(JSONObject jSONObject) throws Exception {
        String string;
        String str = null;
        if (jSONObject.has("wrapper_type")) {
            string = jSONObject.getString("wrapper_type");
        } else {
            string = null;
        }
        this.G = string;
        if (jSONObject.has("wrapper_version")) {
            str = jSONObject.getString("wrapper_version");
        }
        this.H = str;
    }

    public void a(JSONObject jSONObject) throws Exception {
        if (jSONObject != null) {
            d(jSONObject);
            e(jSONObject);
            f(jSONObject);
            g(jSONObject);
            h(jSONObject);
            i(jSONObject);
            j(jSONObject);
        }
    }

    private void k(JSONObject jSONObject) throws Exception {
        jSONObject.put("appkey", this.d);
        if (this.d == null || 24 != this.d.length()) {
            this.f = c.a(this.f, Constants.UTF_8);
            this.j = c.a(this.j, Constants.UTF_8);
        } else {
            this.f = c.a(this.f, Constants.UTF_8, this.d.substring(0, 16));
            this.j = c.a(this.j, Constants.UTF_8, this.d.substring(0, 16));
        }
        jSONObject.put("device_id", this.f);
        jSONObject.put("idmd5", this.g);
        if (this.e != null) {
            jSONObject.put("channel", this.e);
        }
        if (this.h != null) {
            jSONObject.put("mc", this.h);
        }
        if (this.l > 0) {
            jSONObject.put(g.T, this.l);
        }
        if (this.m != null) {
            jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID, this.m);
        }
        if (this.n != null) {
            jSONObject.put("serial_number", this.n);
        }
        jSONObject.put("umid", this.i);
        jSONObject.put("din", this.j);
        jSONObject.put("push_switch", this.k);
    }

    private void l(JSONObject jSONObject) throws Exception {
        jSONObject.put("appkey", this.d);
        if (this.d == null || 24 != this.d.length()) {
            this.j = c.a(this.j, Constants.UTF_8);
        } else {
            this.j = c.a(this.j, Constants.UTF_8, this.d.substring(0, 16));
        }
        if (this.e != null) {
            jSONObject.put("channel", this.e);
        }
        jSONObject.put("umid", this.i);
        jSONObject.put("din", this.j);
        jSONObject.put("push_switch", this.k);
    }

    private void m(JSONObject jSONObject) throws Exception {
        if (this.o != null) {
            jSONObject.put("device_model", this.o);
        }
        if (this.p != null) {
            jSONObject.put("os", this.p);
        }
        if (this.q != null) {
            jSONObject.put("os_version", this.q);
        }
        if (this.r != null) {
            jSONObject.put("resolution", this.r);
        }
        if (this.s != null) {
            jSONObject.put(g.v, this.s);
        }
        if (this.t != null) {
            jSONObject.put("gpu_vender", this.t);
        }
        if (this.u != null) {
            jSONObject.put("gpu_vender", this.u);
        }
    }

    private void n(JSONObject jSONObject) throws Exception {
        if (this.o != null) {
            jSONObject.put("device_model", this.o);
        }
        if (this.p != null) {
            jSONObject.put("os", this.p);
        }
        if (this.q != null) {
            jSONObject.put("os_version", this.q);
        }
    }

    private void o(JSONObject jSONObject) throws Exception {
        if (this.v != null) {
            jSONObject.put("app_version", this.v);
        }
        if (this.w != null) {
            jSONObject.put("version_code", this.w);
        }
        if (this.x != null) {
            jSONObject.put(g.n, this.x);
        }
    }

    private void p(JSONObject jSONObject) throws Exception {
        if (this.v != null) {
            jSONObject.put("app_version", this.v);
        }
        if (this.w != null) {
            jSONObject.put("version_code", this.w);
        }
    }

    private void q(JSONObject jSONObject) throws Exception {
        jSONObject.put(g.t, this.y);
        jSONObject.put("sdk_version", this.z);
    }

    private void r(JSONObject jSONObject) throws Exception {
        jSONObject.put(g.L, this.A);
        if (this.B != null) {
            jSONObject.put(g.N, this.B);
        }
        if (this.C != null) {
            jSONObject.put("language", this.C);
        }
    }

    private void s(JSONObject jSONObject) throws Exception {
        if (this.D != null) {
            jSONObject.put(g.P, this.D);
        }
        if (this.E != null) {
            jSONObject.put(g.Q, this.E);
        }
        if (this.F != null) {
            jSONObject.put(g.O, this.F);
        }
    }

    private void t(JSONObject jSONObject) throws Exception {
        if (this.G != null) {
            jSONObject.put("wrapper_type", this.G);
        }
        if (this.H != null) {
            jSONObject.put("wrapper_version", this.H);
        }
    }

    public void b(JSONObject jSONObject) throws Exception {
        k(jSONObject);
        m(jSONObject);
        o(jSONObject);
        q(jSONObject);
        r(jSONObject);
        s(jSONObject);
        t(jSONObject);
    }

    public void c(JSONObject jSONObject) throws Exception {
        l(jSONObject);
        n(jSONObject);
        p(jSONObject);
        q(jSONObject);
        s(jSONObject);
    }

    public boolean a() {
        UMLog uMLog;
        if (this.d == null) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(c, 0, "missing appkey");
            return false;
        } else if (this.f != null && this.g != null) {
            return true;
        } else {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(c, 0, "missing device id");
            return false;
        }
    }

    public void a(Context context, String... strArr) {
        if (strArr != null && strArr.length == 2) {
            this.d = strArr[0];
            this.e = strArr[1];
        }
        if (this.d == null) {
            this.d = PushAgent.getInstance(context).getMessageAppkey();
        }
        if (this.e == null) {
            this.e = PushAgent.getInstance(context).getMessageChannel();
        }
        this.f = UmengMessageDeviceConfig.getDeviceId(context);
        this.g = UmengMessageDeviceConfig.getDeviceIdMD5(context);
        this.h = UmengMessageDeviceConfig.getMac(context);
        this.j = UmengMessageDeviceConfig.getDIN(context);
        this.i = UmengMessageDeviceConfig.getUmid(context);
        this.k = UmengMessageDeviceConfig.isNotificationEnabled(context);
        if ("false".equals(this.k)) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.aq(k.c, 0, "\\|");
        }
    }

    private void a(Context context) {
        this.o = Build.MODEL;
        this.p = "Android";
        this.q = VERSION.RELEASE;
        this.r = UmengMessageDeviceConfig.getResolution(context);
        this.s = UmengMessageDeviceConfig.getCPU();
        this.m = UmengMessageDeviceConfig.getAndroidId(context);
        this.n = UmengMessageDeviceConfig.getSerial_number();
    }

    private void b(Context context) {
        this.v = UmengMessageDeviceConfig.getAppVersionName(context);
        this.w = UmengMessageDeviceConfig.getAppVersionCode(context);
        this.x = UmengMessageDeviceConfig.getPackageName(context);
    }

    private void c(Context context) {
        this.y = "Android";
        this.z = MsgConstant.SDK_VERSION;
    }

    private void d(Context context) {
        this.A = UmengMessageDeviceConfig.getTimeZone(context);
        String[] localeInfo = UmengMessageDeviceConfig.getLocaleInfo(context);
        this.B = localeInfo[0];
        this.C = localeInfo[1];
    }

    private void e(Context context) {
        String[] networkAccessMode = UmengMessageDeviceConfig.getNetworkAccessMode(context);
        this.D = networkAccessMode[0];
        this.E = networkAccessMode[1];
        this.F = UmengMessageDeviceConfig.getOperator(context);
    }

    public void b(Context context, String... strArr) {
        a(context, strArr);
        a(context);
        b(context);
        c(context);
        d(context);
        e(context);
    }

    public void c(Context context, String... strArr) {
        a(context, strArr);
        a(context);
        b(context);
        c(context);
        e(context);
    }

    public boolean b() {
        return (this.d == null || this.f == null) ? false : true;
    }
}
