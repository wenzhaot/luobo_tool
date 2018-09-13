package com.taobao.accs.data;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.feng.car.utils.HttpConstant;
import com.taobao.accs.ACCSManager.AccsRequest;
import com.taobao.accs.base.TaoBaseService.ExtHeaderType;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.ut.monitor.NetPerformanceMonitor;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.accs.utl.d;
import com.taobao.accs.utl.g;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: Taobao */
public class Message {
    public static final int EXT_HEADER_VALUE_MAX_LEN = 1023;
    public static final int FLAG_ACK_TYPE = 32;
    public static final int FLAG_BIZ_RET = 64;
    public static final int FLAG_DATA_TYPE = 32768;
    public static final int FLAG_ERR = 4096;
    public static final int FLAG_REQ_BIT1 = 16384;
    public static final int FLAG_REQ_BIT2 = 8192;
    public static final int FLAG_RET = 2048;
    public static final String KEY_BINDAPP = "ctrl_bindapp";
    public static final String KEY_BINDSERVICE = "ctrl_bindservice";
    public static final String KEY_BINDUSER = "ctrl_binduser";
    public static final String KEY_UNBINDAPP = "ctrl_unbindapp";
    public static final String KEY_UNBINDSERVICE = "ctrl_unbindservice";
    public static final String KEY_UNBINDUSER = "ctrl_unbinduser";
    public static final int MAX_RETRY_TIMES = 3;
    public static int a = 5;
    static long b = 1;
    String A = null;
    Integer B = null;
    String C = null;
    String D = null;
    public String E = null;
    public String F = null;
    String G = null;
    String H = null;
    String I = null;
    String J = null;
    String K = null;
    byte[] L;
    public String M;
    int N;
    public long O = 0;
    public int P = 0;
    public int Q = com.taobao.accs.net.b.ACCS_RECEIVE_TIMEOUT;
    public long R;
    long S;
    public String T = null;
    transient NetPerformanceMonitor U;
    String V = null;
    public boolean c = false;
    public boolean d = false;
    public boolean e = false;
    public URL f;
    byte g = (byte) 0;
    byte h = (byte) 0;
    short i;
    short j;
    short k;
    byte l;
    byte m;
    String n;
    String o;
    int p = -1;
    public String q;
    Map<Integer, String> r;
    String s = null;
    public Integer t = null;
    Integer u = Integer.valueOf(0);
    String v = null;
    public String w = null;
    Integer x = null;
    String y = null;
    String z = null;

    /* compiled from: Taobao */
    public enum ReqType {
        DATA,
        ACK,
        REQ,
        RES;

        public static ReqType valueOf(int i) {
            switch (i) {
                case 0:
                    return DATA;
                case 1:
                    return ACK;
                case 2:
                    return REQ;
                case 3:
                    return RES;
                default:
                    return DATA;
            }
        }
    }

    /* compiled from: Taobao */
    public static class a {
        public static final int INVALID = -1;
        public static final int NEED_ACK = 1;
        public static final int NO_ACK = 0;

        public static int a(int i) {
            switch (i) {
                case 0:
                    return 0;
                default:
                    return 1;
            }
        }

        public static String b(int i) {
            switch (i) {
                case 0:
                    return "NO_ACK";
                case 1:
                    return "NEED_ACK";
                default:
                    return "INVALID";
            }
        }
    }

    /* compiled from: Taobao */
    public static class b {
        public static final int CONTROL = 0;
        public static final int DATA = 1;
        public static final int HANDSHAKE = 3;
        public static final int INVALID = -1;
        public static final int PING = 2;

        public static int a(int i) {
            switch (i) {
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 3;
                default:
                    return 0;
            }
        }

        public static String b(int i) {
            switch (i) {
                case 0:
                    return "CONTROL";
                case 1:
                    return "DATA";
                case 2:
                    return "PING";
                case 3:
                    return "HANDSHAKE";
                default:
                    return "INVALID";
            }
        }
    }

    private Message() {
        synchronized (Message.class) {
            long j = b;
            b = 1 + j;
            this.q = String.valueOf(j);
        }
        this.R = System.currentTimeMillis();
    }

    public int a() {
        return this.p;
    }

    public String b() {
        return this.q;
    }

    public boolean c() {
        return Constants.TARGET_CONTROL.equals(this.n);
    }

    public int d() {
        try {
            if (this.c) {
                return -((int) b);
            }
            return Integer.valueOf(this.q).intValue();
        } catch (Exception e) {
            ALog.w("Msg_", "parse int dataId error " + this.q, new Object[0]);
            return -1;
        }
    }

    public void a(long j) {
        this.S = j;
    }

    public NetPerformanceMonitor e() {
        return this.U;
    }

    private String j() {
        return "Msg_" + this.V;
    }

    public String f() {
        return this.s == null ? "" : this.s;
    }

    public boolean g() {
        boolean z = (System.currentTimeMillis() - this.R) + this.O >= ((long) this.Q);
        if (z) {
            ALog.e(j(), "delay time:" + this.O + " beforeSendTime:" + (System.currentTimeMillis() - this.R) + " timeout" + this.Q, new Object[0]);
        }
        return z;
    }

    public byte[] a(Context context, int i) {
        String str;
        String str2;
        byte[] bytes;
        int i2;
        try {
            i();
        } catch (Throwable e) {
            ALog.e(j(), "build1", e, new Object[0]);
        } catch (Throwable e2) {
            ALog.e(j(), "build2", e2, new Object[0]);
        }
        if (this.L != null) {
            str = new String(this.L);
        } else {
            str = "";
        }
        h();
        if (!this.c) {
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder append = stringBuilder.append(UtilityImpl.getDeviceId(context)).append("|").append(this.s).append("|").append(this.F == null ? "" : this.F).append("|");
            if (this.E == null) {
                str2 = "";
            } else {
                str2 = this.E;
            }
            append.append(str2);
            this.o = stringBuilder.toString();
        }
        try {
            bytes = (this.q + "").getBytes(com.qiniu.android.common.Constants.UTF_8);
            this.m = (byte) this.o.getBytes(com.qiniu.android.common.Constants.UTF_8).length;
            this.l = (byte) this.n.getBytes(com.qiniu.android.common.Constants.UTF_8).length;
        } catch (Throwable e22) {
            e22.printStackTrace();
            ALog.e(j(), "build3", e22, new Object[0]);
            bytes = (this.q + "").getBytes();
            this.m = (byte) this.o.getBytes().length;
            this.l = (byte) this.n.getBytes().length;
        }
        short a = a(this.r);
        int length = bytes.length + ((((this.l + 3) + 1) + this.m) + 1);
        if (this.L == null) {
            i2 = 0;
        } else {
            i2 = this.L.length;
        }
        this.j = (short) (((i2 + length) + a) + 2);
        this.i = (short) (this.j + 2);
        g gVar = new g((this.i + 2) + 4);
        ALog.d(j(), "Build Message", new Object[0]);
        try {
            gVar.a((byte) (this.g | 32));
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(j(), "\tversion:2 compress:" + this.g, new Object[0]);
            }
            if (i == 0) {
                gVar.a(Byte.MIN_VALUE);
                ALog.d(j(), "\tflag: 0x80", new Object[0]);
            } else {
                gVar.a((byte) 64);
                ALog.d(j(), "\tflag: 0x40", new Object[0]);
            }
            gVar.a(this.i);
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(j(), "\ttotalLength:" + this.i, new Object[0]);
            }
            gVar.a(this.j);
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(j(), "\tdataLength:" + this.j, new Object[0]);
            }
            gVar.a(this.k);
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(j(), "\tflags:" + Integer.toHexString(this.k), new Object[0]);
            }
            gVar.a(this.l);
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(j(), "\ttargetLength:" + this.l, new Object[0]);
            }
            gVar.write(this.n.getBytes(com.qiniu.android.common.Constants.UTF_8));
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(j(), "\ttarget:" + new String(this.n), new Object[0]);
            }
            gVar.a(this.m);
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(j(), "\tsourceLength:" + this.m, new Object[0]);
            }
            gVar.write(this.o.getBytes(com.qiniu.android.common.Constants.UTF_8));
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(j(), "\tsource:" + new String(this.o), new Object[0]);
            }
            gVar.a((byte) bytes.length);
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(j(), "\tdataIdLength:" + bytes.length, new Object[0]);
            }
            gVar.write(bytes);
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(j(), "\tdataId:" + new String(bytes), new Object[0]);
            }
            gVar.a(a);
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(j(), "\textHeader len:" + a, new Object[0]);
            }
            if (this.r != null) {
                for (Integer intValue : this.r.keySet()) {
                    length = intValue.intValue();
                    str2 = (String) this.r.get(Integer.valueOf(length));
                    if (!TextUtils.isEmpty(str2)) {
                        gVar.a((short) ((((short) length) << 10) | ((short) (str2.getBytes(com.qiniu.android.common.Constants.UTF_8).length & EXT_HEADER_VALUE_MAX_LEN))));
                        gVar.write(str2.getBytes(com.qiniu.android.common.Constants.UTF_8));
                        if (ALog.isPrintLog(Level.D)) {
                            ALog.d(j(), "\textHeader key:" + length + " value:" + str2, new Object[0]);
                        }
                    }
                }
            }
            if (this.L != null) {
                gVar.write(this.L);
            }
            if (ALog.isPrintLog(Level.D)) {
                ALog.d(j(), "\toriData:" + str, new Object[0]);
            }
            gVar.flush();
        } catch (Throwable e222) {
            ALog.e(j(), "build4", e222, new Object[0]);
        }
        bytes = gVar.toByteArray();
        a(bytes);
        try {
            gVar.close();
        } catch (Throwable e3) {
            ALog.e(j(), "build5", e3, new Object[0]);
        }
        return bytes;
    }

    short a(Map<Integer, String> map) {
        short s = (short) 0;
        if (map != null) {
            try {
                for (Integer intValue : map.keySet()) {
                    short s2;
                    String str = (String) map.get(Integer.valueOf(intValue.intValue()));
                    if (TextUtils.isEmpty(str)) {
                        s2 = s;
                    } else {
                        s2 = (short) ((((short) (str.getBytes(com.qiniu.android.common.Constants.UTF_8).length & EXT_HEADER_VALUE_MAX_LEN)) + 2) + s);
                    }
                    s = s2;
                }
            } catch (Exception e) {
                e.toString();
            }
        }
        return s;
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x0064 A:{SYNTHETIC, Splitter: B:38:0x0064} */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0069 A:{Catch:{ Exception -> 0x006d }} */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0054 A:{SYNTHETIC, Splitter: B:30:0x0054} */
    /* JADX WARNING: Removed duplicated region for block: B:57:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0059 A:{Catch:{ Exception -> 0x005d }} */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0064 A:{SYNTHETIC, Splitter: B:38:0x0064} */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0069 A:{Catch:{ Exception -> 0x006d }} */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0054 A:{SYNTHETIC, Splitter: B:30:0x0054} */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0059 A:{Catch:{ Exception -> 0x005d }} */
    /* JADX WARNING: Removed duplicated region for block: B:57:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0064 A:{SYNTHETIC, Splitter: B:38:0x0064} */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0069 A:{Catch:{ Exception -> 0x006d }} */
    void h() {
        /*
        r5 = this;
        r2 = 0;
        r0 = 0;
        r1 = 0;
        r3 = r5.L;	 Catch:{ Throwable -> 0x0042, all -> 0x005f }
        if (r3 != 0) goto L_0x0012;
    L_0x0007:
        if (r2 == 0) goto L_0x000c;
    L_0x0009:
        r1.close();	 Catch:{ Exception -> 0x007e }
    L_0x000c:
        if (r2 == 0) goto L_0x0011;
    L_0x000e:
        r0.close();	 Catch:{ Exception -> 0x007e }
    L_0x0011:
        return;
    L_0x0012:
        r3 = new java.io.ByteArrayOutputStream;	 Catch:{ Throwable -> 0x0042, all -> 0x005f }
        r3.<init>();	 Catch:{ Throwable -> 0x0042, all -> 0x005f }
        r1 = new java.util.zip.GZIPOutputStream;	 Catch:{ Throwable -> 0x0077, all -> 0x006f }
        r1.<init>(r3);	 Catch:{ Throwable -> 0x0077, all -> 0x006f }
        r0 = r5.L;	 Catch:{ Throwable -> 0x007b, all -> 0x0072 }
        r1.write(r0);	 Catch:{ Throwable -> 0x007b, all -> 0x0072 }
        r1.finish();	 Catch:{ Throwable -> 0x007b, all -> 0x0072 }
        r0 = r3.toByteArray();	 Catch:{ Throwable -> 0x007b, all -> 0x0072 }
        if (r0 == 0) goto L_0x0035;
    L_0x002a:
        r2 = r0.length;	 Catch:{ Throwable -> 0x007b, all -> 0x0072 }
        r4 = r5.L;	 Catch:{ Throwable -> 0x007b, all -> 0x0072 }
        r4 = r4.length;	 Catch:{ Throwable -> 0x007b, all -> 0x0072 }
        if (r2 >= r4) goto L_0x0035;
    L_0x0030:
        r5.L = r0;	 Catch:{ Throwable -> 0x007b, all -> 0x0072 }
        r0 = 1;
        r5.g = r0;	 Catch:{ Throwable -> 0x007b, all -> 0x0072 }
    L_0x0035:
        if (r1 == 0) goto L_0x003a;
    L_0x0037:
        r1.close();	 Catch:{ Exception -> 0x0040 }
    L_0x003a:
        if (r3 == 0) goto L_0x0011;
    L_0x003c:
        r3.close();	 Catch:{ Exception -> 0x0040 }
        goto L_0x0011;
    L_0x0040:
        r0 = move-exception;
        goto L_0x0011;
    L_0x0042:
        r0 = move-exception;
        r1 = r2;
    L_0x0044:
        r3 = r5.j();	 Catch:{ all -> 0x0074 }
        r4 = r0.toString();	 Catch:{ all -> 0x0074 }
        android.util.Log.e(r3, r4);	 Catch:{ all -> 0x0074 }
        r0.printStackTrace();	 Catch:{ all -> 0x0074 }
        if (r1 == 0) goto L_0x0057;
    L_0x0054:
        r1.close();	 Catch:{ Exception -> 0x005d }
    L_0x0057:
        if (r2 == 0) goto L_0x0011;
    L_0x0059:
        r2.close();	 Catch:{ Exception -> 0x005d }
        goto L_0x0011;
    L_0x005d:
        r0 = move-exception;
        goto L_0x0011;
    L_0x005f:
        r0 = move-exception;
        r1 = r2;
        r3 = r2;
    L_0x0062:
        if (r1 == 0) goto L_0x0067;
    L_0x0064:
        r1.close();	 Catch:{ Exception -> 0x006d }
    L_0x0067:
        if (r3 == 0) goto L_0x006c;
    L_0x0069:
        r3.close();	 Catch:{ Exception -> 0x006d }
    L_0x006c:
        throw r0;
    L_0x006d:
        r1 = move-exception;
        goto L_0x006c;
    L_0x006f:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0062;
    L_0x0072:
        r0 = move-exception;
        goto L_0x0062;
    L_0x0074:
        r0 = move-exception;
        r3 = r2;
        goto L_0x0062;
    L_0x0077:
        r0 = move-exception;
        r1 = r2;
        r2 = r3;
        goto L_0x0044;
    L_0x007b:
        r0 = move-exception;
        r2 = r3;
        goto L_0x0044;
    L_0x007e:
        r0 = move-exception;
        goto L_0x0011;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.data.Message.h():void");
    }

    void i() throws JSONException, UnsupportedEncodingException {
        if (this.t != null && this.t.intValue() != 100 && this.t.intValue() != 102 && this.t.intValue() != 105) {
            this.L = new com.taobao.accs.utl.e.a().a("command", this.t.intValue() == 100 ? null : this.t).a(Constants.KEY_APP_KEY, this.v).a(Constants.KEY_OS_TYPE, this.x).a(Constants.KEY_SECURITY_SIGN, this.w).a(Constants.KEY_SDK_VERSION, this.B).a(Constants.KEY_APP_VERSION, this.A).a(Constants.KEY_TTID, this.C).a("model", this.G).a("brand", this.H).a("imei", this.I).a(Constants.KEY_IMSI, this.J).a("os", this.y).a(Constants.KEY_EXTS, this.z).a().toString().getBytes(com.qiniu.android.common.Constants.UTF_8);
        }
    }

    void a(byte[] bArr) {
        String str = "";
        if (ALog.isPrintLog(Level.D)) {
            ALog.d(j(), "len:" + bArr.length, new Object[0]);
            if (bArr.length < 512) {
                String str2 = str;
                for (byte b : bArr) {
                    str2 = str2 + Integer.toHexString(b & 255) + " ";
                }
                ALog.d(j(), str2, new Object[0]);
            }
        }
    }

    public static Message a(boolean z, int i) {
        Message message = new Message();
        message.p = 2;
        message.t = Integer.valueOf(Constants.COMMAND_PING);
        message.d = z;
        message.O = (long) i;
        return message;
    }

    public static Message a(com.taobao.accs.net.b bVar, Context context, Intent intent) {
        Message a;
        Throwable e;
        try {
            String stringExtra = intent.getStringExtra(Constants.KEY_PACKAGE_NAME);
            intent.getStringExtra(Constants.KEY_USER_ID);
            String stringExtra2 = intent.getStringExtra(Constants.KEY_APP_KEY);
            String stringExtra3 = intent.getStringExtra(Constants.KEY_TTID);
            intent.getStringExtra("sid");
            intent.getStringExtra(Constants.KEY_ANTI_BRUSH_COOKIE);
            String stringExtra4 = intent.getStringExtra(Constants.KEY_APP_VERSION);
            Context context2 = context;
            a = a(context2, bVar.m, stringExtra2, intent.getStringExtra("app_sercet"), stringExtra, stringExtra3, stringExtra4);
            try {
                a(bVar, a);
            } catch (Exception e2) {
                e = e2;
                ALog.e("Msg_", "buildBindApp", e, new Object[0]);
                e.printStackTrace();
                return a;
            }
        } catch (Throwable e3) {
            e = e3;
            a = null;
        }
        return a;
    }

    public static Message a(Context context, String str, String str2, String str3, String str4, String str5, String str6) {
        String str7 = null;
        if (TextUtils.isEmpty(str4)) {
            return null;
        }
        Message message = new Message();
        try {
            message.N = 1;
            message.a(1, ReqType.DATA, 1);
            message.x = Integer.valueOf(1);
            message.y = VERSION.SDK_INT + "";
            message.s = str4;
            message.n = Constants.TARGET_CONTROL;
            message.t = Integer.valueOf(1);
            message.v = str2;
            message.w = UtilityImpl.getAppsign(context, str2, str3, UtilityImpl.getDeviceId(context), str);
            message.B = Integer.valueOf(Constants.SDK_VERSION_CODE);
            message.A = str6;
            message.s = str4;
            message.C = str5;
            message.G = Build.MODEL;
            message.H = Build.BRAND;
            message.M = KEY_BINDAPP;
            message.V = str;
            message.z = new com.taobao.accs.utl.e.a().a("notifyEnable", UtilityImpl.isNotificationEnabled(context)).a("romInfo", new d().a()).a().toString();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
            message.I = telephonyManager != null ? telephonyManager.getDeviceId() : null;
            if (telephonyManager != null) {
                str7 = telephonyManager.getSubscriberId();
            }
            message.J = str7;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    public static Message b(com.taobao.accs.net.b bVar, Context context, Intent intent) {
        Message a;
        Throwable e;
        ALog.e("Msg_", "buildUnbindApp1" + UtilityImpl.getStackMsg(new Exception()), new Object[0]);
        try {
            String stringExtra = intent.getStringExtra(Constants.KEY_PACKAGE_NAME);
            intent.getStringExtra(Constants.KEY_USER_ID);
            intent.getStringExtra("sid");
            intent.getStringExtra(Constants.KEY_ANTI_BRUSH_COOKIE);
            a = a(bVar, stringExtra);
            try {
                a(bVar, a);
            } catch (Exception e2) {
                e = e2;
                ALog.e("Msg_", "buildUnbindApp1", e, new Object[0]);
                e.printStackTrace();
                return a;
            }
        } catch (Throwable e3) {
            Throwable th = e3;
            a = null;
            e = th;
        }
        return a;
    }

    public static Message a(com.taobao.accs.net.b bVar, String str) {
        Throwable e;
        Message message;
        try {
            ALog.e("Msg_", "buildUnbindApp" + UtilityImpl.getStackMsg(new Exception()), new Object[0]);
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            message = new Message();
            try {
                message.N = 1;
                message.a(1, ReqType.DATA, 1);
                message.s = str;
                message.n = Constants.TARGET_CONTROL;
                message.t = Integer.valueOf(2);
                message.s = str;
                message.B = Integer.valueOf(Constants.SDK_VERSION_CODE);
                message.M = KEY_UNBINDAPP;
                a(bVar, message);
                return message;
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable e3) {
            Throwable th = e3;
            message = null;
            e = th;
            ALog.e("Msg_", "buildUnbindApp", e, new Object[0]);
            e.printStackTrace();
            return message;
        }
    }

    public static Message a(com.taobao.accs.net.b bVar, Intent intent) {
        Message a;
        Throwable e;
        try {
            String stringExtra = intent.getStringExtra(Constants.KEY_PACKAGE_NAME);
            String stringExtra2 = intent.getStringExtra(Constants.KEY_SERVICE_ID);
            intent.getStringExtra(Constants.KEY_USER_ID);
            intent.getStringExtra(Constants.KEY_APP_KEY);
            intent.getStringExtra("sid");
            intent.getStringExtra(Constants.KEY_ANTI_BRUSH_COOKIE);
            a = a(stringExtra, stringExtra2);
            try {
                a.V = bVar.m;
                a(bVar, a);
            } catch (Exception e2) {
                e = e2;
                ALog.e("Msg_", "buildBindService", e, new Object[0]);
                e.printStackTrace();
                return a;
            }
        } catch (Throwable e3) {
            Throwable th = e3;
            a = null;
            e = th;
        }
        return a;
    }

    public static Message a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        Message message = new Message();
        message.N = 1;
        message.a(1, ReqType.DATA, 1);
        message.s = str;
        message.F = str2;
        message.n = Constants.TARGET_CONTROL;
        message.t = Integer.valueOf(5);
        message.s = str;
        message.F = str2;
        message.B = Integer.valueOf(Constants.SDK_VERSION_CODE);
        message.M = KEY_BINDSERVICE;
        return message;
    }

    public static Message b(com.taobao.accs.net.b bVar, Intent intent) {
        Message b;
        Throwable e;
        try {
            String stringExtra = intent.getStringExtra(Constants.KEY_PACKAGE_NAME);
            String stringExtra2 = intent.getStringExtra(Constants.KEY_SERVICE_ID);
            intent.getStringExtra(Constants.KEY_USER_ID);
            intent.getStringExtra(Constants.KEY_APP_KEY);
            intent.getStringExtra("sid");
            intent.getStringExtra(Constants.KEY_ANTI_BRUSH_COOKIE);
            b = b(stringExtra, stringExtra2);
            try {
                b.V = bVar.m;
                a(bVar, b);
            } catch (Exception e2) {
                e = e2;
                ALog.e("Msg_", "buildUnbindService", e, new Object[0]);
                e.printStackTrace();
                return b;
            }
        } catch (Throwable e3) {
            Throwable th = e3;
            b = null;
            e = th;
        }
        return b;
    }

    public static Message b(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        Message message = new Message();
        message.N = 1;
        message.a(1, ReqType.DATA, 1);
        message.s = str;
        message.F = str2;
        message.n = Constants.TARGET_CONTROL;
        message.t = Integer.valueOf(6);
        message.s = str;
        message.F = str2;
        message.B = Integer.valueOf(Constants.SDK_VERSION_CODE);
        message.M = KEY_UNBINDSERVICE;
        return message;
    }

    public static Message c(com.taobao.accs.net.b bVar, Intent intent) {
        Message c;
        Throwable e;
        try {
            String stringExtra = intent.getStringExtra(Constants.KEY_PACKAGE_NAME);
            String stringExtra2 = intent.getStringExtra(Constants.KEY_USER_ID);
            intent.getStringExtra(Constants.KEY_APP_KEY);
            intent.getStringExtra("sid");
            intent.getStringExtra(Constants.KEY_ANTI_BRUSH_COOKIE);
            c = c(stringExtra, stringExtra2);
            try {
                c.V = bVar.m;
                a(bVar, c);
            } catch (Exception e2) {
                e = e2;
                ALog.e("Msg_", "buildBindUser", e, new Object[0]);
                e.printStackTrace();
                return c;
            }
        } catch (Throwable e3) {
            Throwable th = e3;
            c = null;
            e = th;
        }
        return c;
    }

    public static Message c(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        Message message = new Message();
        message.N = 1;
        message.a(1, ReqType.DATA, 1);
        message.s = str;
        message.E = str2;
        message.n = Constants.TARGET_CONTROL;
        message.t = Integer.valueOf(3);
        message.s = str;
        message.E = str2;
        message.B = Integer.valueOf(Constants.SDK_VERSION_CODE);
        message.M = KEY_BINDUSER;
        return message;
    }

    public static Message d(com.taobao.accs.net.b bVar, Intent intent) {
        Message a;
        Throwable e;
        try {
            String stringExtra = intent.getStringExtra(Constants.KEY_PACKAGE_NAME);
            intent.getStringExtra(Constants.KEY_USER_ID);
            intent.getStringExtra(Constants.KEY_APP_KEY);
            intent.getStringExtra("sid");
            intent.getStringExtra(Constants.KEY_ANTI_BRUSH_COOKIE);
            a = a(stringExtra);
            try {
                a.V = bVar.m;
                a(bVar, a);
            } catch (Exception e2) {
                e = e2;
                ALog.e("Msg_", "buildUnbindUser", e, new Object[0]);
                e.printStackTrace();
                return a;
            }
        } catch (Throwable e3) {
            Throwable th = e3;
            a = null;
            e = th;
        }
        return a;
    }

    public static Message a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Message message = new Message();
        message.N = 1;
        message.a(1, ReqType.DATA, 1);
        message.s = str;
        message.n = Constants.TARGET_CONTROL;
        message.t = Integer.valueOf(4);
        message.B = Integer.valueOf(Constants.SDK_VERSION_CODE);
        message.M = KEY_UNBINDUSER;
        return message;
    }

    public static Message a(String str, Map<String, Integer> map) {
        if (map == null) {
            return null;
        }
        Message message = new Message();
        try {
            message.a(1, ReqType.DATA, 1);
            message.N = 1;
            message.s = str;
            message.n = Constants.TARGET_ELECTION;
            message.t = Integer.valueOf(105);
            JSONArray jSONArray = new JSONArray();
            for (Entry entry : map.entrySet()) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(Constants.KEY_ELECTION_PKG, entry.getKey());
                jSONObject.put("sdkv", entry.getValue());
                jSONArray.put(jSONObject);
            }
            message.L = new com.taobao.accs.utl.e.a().a("sdkv", Integer.valueOf(Constants.SDK_VERSION_CODE)).a(Constants.KEY_ELECTION_PACKS, jSONArray).a().toString().getBytes(com.qiniu.android.common.Constants.UTF_8);
        } catch (Throwable th) {
            ALog.e("Msg_", "buildElection", th, new Object[0]);
        }
        return message;
    }

    public static Message a(com.taobao.accs.net.b bVar, Context context, String str, AccsRequest accsRequest) {
        return a(bVar, context, str, accsRequest, true);
    }

    public static Message a(com.taobao.accs.net.b bVar, Context context, String str, AccsRequest accsRequest, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Message message = new Message();
        message.N = 1;
        message.a(1, ReqType.DATA, 1);
        message.t = Integer.valueOf(100);
        message.s = str;
        message.F = accsRequest.serviceId;
        message.E = accsRequest.userId;
        message.L = accsRequest.data;
        message.n = Constants.TARGET_SERVICE_PRE + (TextUtils.isEmpty(accsRequest.targetServiceName) ? accsRequest.serviceId : accsRequest.targetServiceName) + "|" + (accsRequest.target == null ? "" : accsRequest.target);
        message.M = accsRequest.dataId;
        message.T = accsRequest.businessId;
        if (accsRequest.timeout > 0) {
            message.Q = accsRequest.timeout;
        }
        if (z) {
            a(bVar, message, accsRequest);
        } else {
            message.f = accsRequest.host;
        }
        a(message, GlobalClientInfo.getInstance(context).getSid(bVar.m), GlobalClientInfo.getInstance(context).getUserId(bVar.m), GlobalClientInfo.c, accsRequest.businessId, accsRequest.tag);
        message.U = new NetPerformanceMonitor();
        message.U.setDataId(accsRequest.dataId);
        message.U.setServiceId(accsRequest.serviceId);
        message.U.setHost(message.f.toString());
        message.V = bVar.m;
        return message;
    }

    public static Message b(com.taobao.accs.net.b bVar, Context context, String str, AccsRequest accsRequest, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Message message = new Message();
        message.N = 1;
        message.a(1, ReqType.REQ, 1);
        message.t = Integer.valueOf(100);
        message.s = str;
        message.F = accsRequest.serviceId;
        message.E = accsRequest.userId;
        message.L = accsRequest.data;
        message.n = Constants.TARGET_SERVICE_PRE + (TextUtils.isEmpty(accsRequest.targetServiceName) ? accsRequest.serviceId : accsRequest.targetServiceName) + "|" + (accsRequest.target == null ? "" : accsRequest.target);
        message.M = accsRequest.dataId;
        message.T = accsRequest.businessId;
        message.V = bVar.m;
        if (accsRequest.timeout > 0) {
            message.Q = accsRequest.timeout;
        }
        if (z) {
            a(bVar, message, accsRequest);
        } else {
            message.f = accsRequest.host;
        }
        a(message, GlobalClientInfo.getInstance(context).getSid(bVar.m), GlobalClientInfo.getInstance(context).getUserId(bVar.m), GlobalClientInfo.c, accsRequest.businessId, accsRequest.tag);
        message.U = new NetPerformanceMonitor();
        message.U.setDataId(accsRequest.dataId);
        message.U.setServiceId(accsRequest.serviceId);
        message.U.setHost(message.f.toString());
        message.V = bVar.m;
        return message;
    }

    private static void a(com.taobao.accs.net.b bVar, Message message, AccsRequest accsRequest) {
        if (accsRequest.host == null) {
            try {
                message.f = new URL(bVar.b(null));
                return;
            } catch (Throwable e) {
                ALog.e("Msg_", "setUnit", e, new Object[0]);
                e.printStackTrace();
                return;
            }
        }
        message.f = accsRequest.host;
    }

    private static void a(com.taobao.accs.net.b bVar, Message message) {
        try {
            message.f = new URL(bVar.b(null));
        } catch (Throwable e) {
            ALog.e("Msg_", "setControlHost", e, new Object[0]);
        }
    }

    public static Message a(com.taobao.accs.net.b bVar, String str, String str2, String str3, boolean z, short s, String str4, Map<Integer, String> map) {
        Message message = new Message();
        message.N = 1;
        message.a(s, z);
        message.o = str;
        message.n = str2;
        message.q = str3;
        message.c = true;
        message.r = map;
        try {
            if (TextUtils.isEmpty(str4)) {
                GlobalClientInfo.getContext();
                message.f = new URL(bVar.b(null));
            } else {
                message.f = new URL(str4);
            }
            message.V = bVar.m;
            if (message.f == null) {
                try {
                    GlobalClientInfo.getContext();
                    message.f = new URL(bVar.b(null));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        } catch (Throwable th) {
            if (message.f == null) {
                try {
                    GlobalClientInfo.getContext();
                    message.f = new URL(bVar.b(null));
                } catch (MalformedURLException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return message;
    }

    public static Message a(String str, int i) {
        Message message = new Message();
        message.a(1, ReqType.ACK, 0);
        message.t = Integer.valueOf(i);
        message.s = str;
        return message;
    }

    private static void a(Message message, String str, String str2, String str3, String str4, String str5) {
        if (!TextUtils.isEmpty(str4) || !TextUtils.isEmpty(str) || !TextUtils.isEmpty(str2) || !TextUtils.isEmpty(str5) || str3 != null) {
            message.r = new HashMap();
            if (str4 != null && UtilityImpl.getByteLen(str4) <= EXT_HEADER_VALUE_MAX_LEN) {
                message.r.put(Integer.valueOf(ExtHeaderType.TYPE_BUSINESS.ordinal()), str4);
            }
            if (str != null && UtilityImpl.getByteLen(str) <= EXT_HEADER_VALUE_MAX_LEN) {
                message.r.put(Integer.valueOf(ExtHeaderType.TYPE_SID.ordinal()), str);
            }
            if (str2 != null && UtilityImpl.getByteLen(str2) <= EXT_HEADER_VALUE_MAX_LEN) {
                message.r.put(Integer.valueOf(ExtHeaderType.TYPE_USERID.ordinal()), str2);
            }
            if (str5 != null && UtilityImpl.getByteLen(str5) <= EXT_HEADER_VALUE_MAX_LEN) {
                message.r.put(Integer.valueOf(ExtHeaderType.TYPE_TAG.ordinal()), str5);
            }
            if (str3 != null && UtilityImpl.getByteLen(str3) <= EXT_HEADER_VALUE_MAX_LEN) {
                message.r.put(Integer.valueOf(ExtHeaderType.TYPE_COOKIE.ordinal()), str3);
            }
        }
    }

    private void a(int i, ReqType reqType, int i2) {
        this.p = i;
        if (i != 2) {
            this.k = (short) (((((i & 1) << 4) | (reqType.ordinal() << 2)) | i2) << 11);
        }
    }

    private void a(short s, boolean z) {
        this.p = 1;
        this.k = s;
        this.k = (short) (this.k & -16385);
        this.k = (short) (this.k | 8192);
        this.k = (short) (this.k & -2049);
        this.k = (short) (this.k & -65);
        if (z) {
            this.k = (short) (this.k | 32);
        }
    }
}
