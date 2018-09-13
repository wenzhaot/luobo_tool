package com.talkingdata.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellLocation;
import android.telephony.CellSignalStrength;
import android.telephony.NeighboringCellInfo;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import com.feng.car.utils.HttpConstant;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.pushtracer.constant.Parameters;
import com.netease.LDNetDiagnoUtils.LDNetUtil;
import com.taobao.accs.utl.UtilityImpl;
import com.umeng.commonsdk.proguard.g;
import com.umeng.message.MsgConstant;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: td */
public class bd {
    static TelephonyManager a = null;
    static final String b = "www.talkingdata.net";
    static final int c = 80;
    static boolean d = false;
    static final long e = 300000;
    static long f = -300000;
    private static final String[] g = new String[]{LDNetUtil.NETWORKTYPE_INVALID, "GPRS", "EDGE", "UMTS", "CDMA", "EVDO_0", "EVDO_A", "1xRTT", "HSDPA", "HSUPA", "HSPA", "IDEN", "EVDO_B", "LTE", "EHRPD", "HSPAP"};
    private static final String[] h = new String[]{"NONE", "GSM", "CDMA", "SIP"};
    private static a i = null;

    /* compiled from: td */
    public static class a implements Runnable {
        private Context context;
        private Object lock;
        private BroadcastReceiver receiver;

        public a(Context context, Object obj, BroadcastReceiver broadcastReceiver) {
            this.context = context;
            this.lock = obj;
            this.receiver = broadcastReceiver;
        }

        public void unRegisterReceiver() {
            if (this.receiver != null) {
                try {
                    this.context.unregisterReceiver(this.receiver);
                } catch (Throwable th) {
                }
            }
        }

        public void run() {
            try {
                synchronized (this.lock) {
                    try {
                        this.lock.notifyAll();
                        this.context.unregisterReceiver(this.receiver);
                    } catch (Throwable th) {
                        this.context.unregisterReceiver(this.receiver);
                    }
                }
            } catch (Throwable th2) {
            }
        }
    }

    static void a(Context context) {
        try {
            a = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
        } catch (Throwable th) {
        }
    }

    public static String b(Context context) {
        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration inetAddresses = ((NetworkInterface) networkInterfaces.nextElement()).getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0073 A:{Splitter: B:15:0x0046, ExcHandler: all (th java.lang.Throwable), PHI: r0 } */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0085 A:{SYNTHETIC, Splitter: B:42:0x0085} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:34:?, code:
            d = false;
     */
    /* JADX WARNING: Missing block: B:35:0x0077, code:
            if (r0 != null) goto L_0x0079;
     */
    /* JADX WARNING: Missing block: B:37:?, code:
            r0.close();
     */
    /* JADX WARNING: Missing block: B:39:0x007f, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:40:0x0080, code:
            r4 = r1;
            r1 = null;
            r0 = r4;
     */
    /* JADX WARNING: Missing block: B:43:?, code:
            r1.close();
     */
    public static boolean c(android.content.Context r5) {
        /*
        r1 = 0;
        r0 = "android.permission.ACCESS_NETWORK_STATE";
        r0 = com.talkingdata.sdk.bo.b(r5, r0);	 Catch:{ Throwable -> 0x0089 }
        if (r0 == 0) goto L_0x0031;
    L_0x000a:
        r0 = "connectivity";
        r0 = r5.getSystemService(r0);	 Catch:{ Throwable -> 0x0089 }
        r0 = (android.net.ConnectivityManager) r0;	 Catch:{ Throwable -> 0x0089 }
        r2 = r0.getActiveNetworkInfo();	 Catch:{ Throwable -> 0x0089 }
        if (r2 == 0) goto L_0x001e;
    L_0x0019:
        r0 = r2.isConnected();	 Catch:{ Throwable -> 0x0089 }
    L_0x001d:
        return r0;
    L_0x001e:
        r2 = 0;
        r0 = r0.getNetworkInfo(r2);	 Catch:{ Throwable -> 0x0089 }
        if (r0 == 0) goto L_0x0065;
    L_0x0025:
        r0 = r0.getState();	 Catch:{ Throwable -> 0x0089 }
        r2 = android.net.NetworkInfo.State.UNKNOWN;	 Catch:{ Throwable -> 0x0089 }
        r0 = r0.equals(r2);	 Catch:{ Throwable -> 0x0089 }
        if (r0 == 0) goto L_0x0065;
    L_0x0031:
        r0 = android.os.SystemClock.elapsedRealtime();	 Catch:{ Throwable -> 0x0089 }
        r2 = f;	 Catch:{ Throwable -> 0x0089 }
        r0 = r0 - r2;
        r2 = 300000; // 0x493e0 float:4.2039E-40 double:1.482197E-318;
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 <= 0) goto L_0x0062;
    L_0x003f:
        r0 = android.os.SystemClock.elapsedRealtime();	 Catch:{ Throwable -> 0x0089 }
        f = r0;	 Catch:{ Throwable -> 0x0089 }
        r0 = 0;
        r1 = a();	 Catch:{ Throwable -> 0x0073, all -> 0x007f }
        if (r1 == 0) goto L_0x0067;
    L_0x004c:
        r1 = new java.net.Socket;	 Catch:{ Throwable -> 0x0073, all -> 0x007f }
        r2 = android.net.Proxy.getDefaultHost();	 Catch:{ Throwable -> 0x0073, all -> 0x007f }
        r3 = android.net.Proxy.getDefaultPort();	 Catch:{ Throwable -> 0x0073, all -> 0x007f }
        r1.<init>(r2, r3);	 Catch:{ Throwable -> 0x0073, all -> 0x007f }
        r0 = r1;
    L_0x005a:
        r1 = 1;
        d = r1;	 Catch:{ Throwable -> 0x0073 }
        if (r0 == 0) goto L_0x0062;
    L_0x005f:
        r0.close();	 Catch:{ Throwable -> 0x008e }
    L_0x0062:
        r0 = d;
        goto L_0x001d;
    L_0x0065:
        r0 = r1;
        goto L_0x001d;
    L_0x0067:
        r1 = new java.net.Socket;	 Catch:{ Throwable -> 0x0073, all -> 0x007f }
        r2 = "www.talkingdata.net";
        r3 = 80;
        r1.<init>(r2, r3);	 Catch:{ Throwable -> 0x0073, all -> 0x007f }
        r0 = r1;
        goto L_0x005a;
    L_0x0073:
        r1 = move-exception;
        r1 = 0;
        d = r1;	 Catch:{ all -> 0x0092 }
        if (r0 == 0) goto L_0x0062;
    L_0x0079:
        r0.close();	 Catch:{ Throwable -> 0x007d }
        goto L_0x0062;
    L_0x007d:
        r0 = move-exception;
        goto L_0x0062;
    L_0x007f:
        r1 = move-exception;
        r4 = r1;
        r1 = r0;
        r0 = r4;
    L_0x0083:
        if (r1 == 0) goto L_0x0088;
    L_0x0085:
        r1.close();	 Catch:{ Throwable -> 0x0090 }
    L_0x0088:
        throw r0;	 Catch:{ Throwable -> 0x0089 }
    L_0x0089:
        r0 = move-exception;
        com.talkingdata.sdk.cs.postSDKError(r0);
        goto L_0x0062;
    L_0x008e:
        r0 = move-exception;
        goto L_0x0062;
    L_0x0090:
        r1 = move-exception;
        goto L_0x0088;
    L_0x0092:
        r1 = move-exception;
        r4 = r1;
        r1 = r0;
        r0 = r4;
        goto L_0x0083;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.talkingdata.sdk.bd.c(android.content.Context):boolean");
    }

    public static boolean d(Context context) {
        try {
            if (bo.b(context, MsgConstant.PERMISSION_ACCESS_NETWORK_STATE)) {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
            }
        } catch (Throwable th) {
        }
        return false;
    }

    public static boolean e(Context context) {
        try {
            if (g(context)) {
                return true;
            }
            return ((WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI)).isWifiEnabled();
        } catch (Throwable th) {
            cs.postSDKError(th);
            return false;
        }
    }

    public static boolean f(Context context) {
        try {
            if (a == null) {
                a(context);
            }
            if (a.getSimState() == 5) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean g(Context context) {
        if (context == null) {
            if (ab.g == null) {
                return false;
            }
            context = ab.g;
        }
        try {
            if (!bo.b(context, MsgConstant.PERMISSION_ACCESS_NETWORK_STATE)) {
                return false;
            }
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            boolean z = activeNetworkInfo != null && 1 == activeNetworkInfo.getType() && activeNetworkInfo.isConnected();
            return z;
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean h(Context context) {
        if (context == null) {
            if (ab.g == null) {
                return false;
            }
            context = ab.g;
        }
        try {
            if (a == null) {
                a(context);
            }
            if (a.getDataState() == 2) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean a() {
        try {
            if (bo.a(11)) {
                if (TextUtils.isEmpty(System.getProperty("http.proxyHost"))) {
                    return false;
                }
                return true;
            } else if (TextUtils.isEmpty(Proxy.getDefaultHost())) {
                return false;
            } else {
                return true;
            }
        } catch (Throwable th) {
            return false;
        }
    }

    public static String i(Context context) {
        if (!c(context)) {
            return "OFFLINE";
        }
        if (g(context)) {
            return LDNetUtil.NETWORKTYPE_WIFI;
        }
        return a(l(context));
    }

    public static String j(Context context) {
        if (!c(context)) {
            return "offline";
        }
        if (g(context)) {
            return UtilityImpl.NET_TYPE_WIFI;
        }
        return "cellular";
    }

    public static String k(Context context) {
        if (context == null) {
            if (ab.g == null) {
                return LDNetUtil.NETWORKTYPE_INVALID;
            }
            context = ab.g;
        }
        if (!c(context)) {
            return LDNetUtil.NETWORKTYPE_INVALID;
        }
        if (g(context)) {
            return LDNetUtil.NETWORKTYPE_WIFI;
        }
        return b(l(context));
    }

    public static int l(Context context) {
        if (context == null) {
            try {
                if (ab.g == null) {
                    return 0;
                }
                context = ab.g;
            } catch (Throwable th) {
                return 0;
            }
        }
        if (a == null) {
            a(context);
        }
        return a.getNetworkType();
    }

    private static String a(int i) {
        if (i < 0 || i >= g.length) {
            return String.valueOf(i);
        }
        return g[i];
    }

    private static String b(int i) {
        String str = LDNetUtil.NETWORKTYPE_INVALID;
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
            case 16:
                try {
                    return "2G";
                } catch (Throwable th) {
                    return str;
                }
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
            case 17:
                return "3G";
            case 13:
            case 18:
                return "4G";
            default:
                return str;
        }
    }

    private static String c(int i) {
        if (i < 0 || i >= h.length) {
            return String.valueOf(i);
        }
        return h[i];
    }

    public static String m(Context context) {
        try {
            if (a == null) {
                a(context);
            }
            return a.getNetworkOperator();
        } catch (Throwable th) {
            return null;
        }
    }

    public static String n(Context context) {
        try {
            if (a == null) {
                a(context);
            }
            return a.getSimOperator();
        } catch (Throwable th) {
            return null;
        }
    }

    public static String o(Context context) {
        try {
            if (a == null) {
                a(context);
            }
            return a.getNetworkCountryIso();
        } catch (Throwable th) {
            return null;
        }
    }

    public static String p(Context context) {
        try {
            if (a == null) {
                a(context);
            }
            return a.getSimCountryIso();
        } catch (Throwable th) {
            return null;
        }
    }

    public static String q(Context context) {
        try {
            if ((bo.a(23) && context.checkSelfPermission(MsgConstant.PERMISSION_READ_PHONE_STATE) != 0) || !bo.b(context, MsgConstant.PERMISSION_READ_PHONE_STATE) || VERSION.SDK_INT < 18) {
                return null;
            }
            if (a == null) {
                a(context);
            }
            return a.getGroupIdLevel1();
        } catch (Throwable th) {
            return null;
        }
    }

    public static String r(Context context) {
        try {
            if (a == null) {
                a(context);
            }
            return a.getNetworkOperatorName();
        } catch (Throwable th) {
            return null;
        }
    }

    public static String s(Context context) {
        try {
            if (a == null) {
                a(context);
            }
            return a.getSimOperatorName();
        } catch (Throwable th) {
            return null;
        }
    }

    public static JSONArray t(Context context) {
        JSONObject jSONObject;
        JSONArray jSONArray = new JSONArray();
        try {
            jSONObject = new JSONObject();
            jSONObject.put("type", UtilityImpl.NET_TYPE_WIFI);
            jSONObject.put("available", e(context));
            jSONObject.put("connected", g(context));
            jSONObject.put("current", x(context));
            jSONObject.put("scannable", y(context));
            jSONObject.put("configured", w(context));
            jSONArray.put(jSONObject);
        } catch (Throwable th) {
        }
        try {
            jSONObject = new JSONObject();
            jSONObject.put("type", "cellular");
            jSONObject.put("available", f(context));
            jSONObject.put("connected", h(context));
            jSONObject.put("current", a(context, false));
            jSONObject.put("scannable", u(context));
            jSONArray.put(jSONObject);
        } catch (Throwable th2) {
        }
        return jSONArray.length() > 0 ? jSONArray : null;
    }

    public static JSONArray a(Context context, boolean z) {
        Object obj = null;
        try {
            if (!bo.b) {
                return null;
            }
            if (context == null) {
                if (ab.g == null) {
                    return null;
                }
                context = ab.g;
            }
            Object obj2 = 1;
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", l(context));
            jSONObject.put("mcc", m(context));
            jSONObject.put("operator", r(context));
            jSONObject.put(g.N, o(context));
            if (!(!bo.a(23) || context.checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0 || context.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0)) {
                obj2 = null;
            }
            if (bo.b(context, "android.permission.ACCESS_COARSE_LOCATION") || bo.b(context, "android.permission.ACCESS_FINE_LOCATION")) {
                obj = obj2;
            }
            if (obj != null) {
                if (a == null) {
                    a(context);
                }
                if (bo.d || bo.e || z) {
                    CellLocation cellLocation = a.getCellLocation();
                    if (cellLocation instanceof GsmCellLocation) {
                        GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                        if (gsmCellLocation != null) {
                            jSONObject.put("systemId", gsmCellLocation.getLac());
                            jSONObject.put("networkId", gsmCellLocation.getCid());
                            if (bo.a(9)) {
                                jSONObject.put("basestationId", gsmCellLocation.getPsc());
                            }
                        }
                    } else if (cellLocation instanceof CdmaCellLocation) {
                        CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
                        if (cdmaCellLocation != null) {
                            jSONObject.put("systemId", cdmaCellLocation.getSystemId());
                            jSONObject.put("networkId", cdmaCellLocation.getNetworkId());
                            jSONObject.put("basestationId", cdmaCellLocation.getBaseStationId());
                            jSONObject.put("location", a(cdmaCellLocation.getBaseStationLatitude(), cdmaCellLocation.getBaseStationLongitude()));
                        }
                    }
                }
            }
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(jSONObject);
            return jSONArray;
        } catch (Throwable th) {
            cs.postSDKError(th);
            return null;
        }
    }

    public static JSONArray u(Context context) {
        if (!bo.b) {
            return null;
        }
        if (context == null) {
            if (ab.g == null) {
                return null;
            }
            context = ab.g;
        }
        if (bo.a(23) && context.checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") != 0) {
            return null;
        }
        if (bo.b(context, "android.permission.ACCESS_COARSE_LOCATION") || bo.b(context, "android.permission.ACCESS_FINE_LOCATION")) {
            try {
                if (a == null) {
                    a(context);
                }
                JSONArray jSONArray = new JSONArray();
                if (bo.a(17)) {
                    List<CellInfo> allCellInfo = a.getAllCellInfo();
                    if (allCellInfo != null) {
                        for (CellInfo cellInfo : allCellInfo) {
                            try {
                                int lac;
                                int cid;
                                int mcc;
                                int mnc;
                                int i;
                                Object obj;
                                CellSignalStrength cellSignalStrength;
                                JSONObject jSONObject = new JSONObject();
                                jSONObject.put("registered", cellInfo.isRegistered());
                                jSONObject.put("ts", cellInfo.getTimeStamp());
                                String obj2;
                                if (cellInfo instanceof CellInfoGsm) {
                                    CellInfoGsm cellInfoGsm = (CellInfoGsm) cellInfo;
                                    CellIdentityGsm cellIdentity = cellInfoGsm.getCellIdentity();
                                    lac = cellIdentity.getLac();
                                    cid = cellIdentity.getCid();
                                    mcc = cellIdentity.getMcc();
                                    mnc = cellIdentity.getMnc();
                                    i = lac;
                                    lac = cid;
                                    cid = -1;
                                    CellSignalStrength cellSignalStrength2 = cellInfoGsm.getCellSignalStrength();
                                    obj2 = "GSM";
                                    cellSignalStrength = cellSignalStrength2;
                                } else if (cellInfo instanceof CellInfoCdma) {
                                    CellInfoCdma cellInfoCdma = (CellInfoCdma) cellInfo;
                                    CellIdentityCdma cellIdentity2 = cellInfoCdma.getCellIdentity();
                                    cid = cellIdentity2.getSystemId();
                                    mcc = cellIdentity2.getNetworkId();
                                    mnc = cellIdentity2.getBasestationId();
                                    CellSignalStrength cellSignalStrength3 = cellInfoCdma.getCellSignalStrength();
                                    jSONObject.put("cdmaDbm", cellSignalStrength3.getCdmaDbm());
                                    jSONObject.put("cdmaDbm", cellSignalStrength3.getCdmaDbm());
                                    jSONObject.put("cdmaEcio", cellSignalStrength3.getCdmaEcio());
                                    jSONObject.put("evdoDbm", cellSignalStrength3.getEvdoDbm());
                                    jSONObject.put("evdoEcio", cellSignalStrength3.getEvdoEcio());
                                    jSONObject.put("evdoSnr", cellSignalStrength3.getEvdoSnr());
                                    jSONObject.put("location", a(cellIdentity2.getLatitude(), cellIdentity2.getLongitude()));
                                    lac = mcc;
                                    i = cid;
                                    mcc = -1;
                                    cid = mnc;
                                    mnc = -1;
                                    cellSignalStrength = cellSignalStrength3;
                                    obj2 = "CDMA";
                                } else if (cellInfo instanceof CellInfoWcdma) {
                                    CellInfoWcdma cellInfoWcdma = (CellInfoWcdma) cellInfo;
                                    CellIdentityWcdma cellIdentity3 = cellInfoWcdma.getCellIdentity();
                                    i = cellIdentity3.getLac();
                                    lac = cellIdentity3.getCid();
                                    cid = cellIdentity3.getPsc();
                                    mcc = cellIdentity3.getMcc();
                                    mnc = cellIdentity3.getMnc();
                                    cellSignalStrength = cellInfoWcdma.getCellSignalStrength();
                                    obj2 = "WCDMA";
                                } else if (cellInfo instanceof CellInfoLte) {
                                    String str = "LTE";
                                    CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
                                    CellIdentityLte cellIdentity4 = cellInfoLte.getCellIdentity();
                                    i = cellIdentity4.getTac();
                                    lac = cellIdentity4.getPci();
                                    cid = cellIdentity4.getCi();
                                    mcc = cellIdentity4.getMcc();
                                    mnc = cellIdentity4.getMnc();
                                    String str2 = str;
                                    cellSignalStrength = cellInfoLte.getCellSignalStrength();
                                    obj2 = str2;
                                } else {
                                    obj2 = null;
                                    cellSignalStrength = null;
                                    mnc = -1;
                                    mcc = -1;
                                    cid = -1;
                                    lac = -1;
                                    i = -1;
                                }
                                if (i != -1) {
                                    jSONObject.put("systemId", i);
                                }
                                if (lac != -1) {
                                    jSONObject.put("networkId", lac);
                                }
                                if (cid != -1) {
                                    jSONObject.put("basestationId", cid);
                                }
                                if (mcc != -1) {
                                    jSONObject.put("mcc", mcc);
                                }
                                if (mnc != -1) {
                                    jSONObject.put("mnc", mnc);
                                }
                                if (cellSignalStrength != null) {
                                    jSONObject.put("asuLevel", cellSignalStrength.getAsuLevel());
                                    jSONObject.put("dbm", cellSignalStrength.getDbm());
                                }
                                jSONObject.put("type", obj2);
                                jSONArray.put(jSONObject);
                            } catch (Throwable th) {
                            }
                        }
                    }
                } else if (bo.a(5) && (bo.d || bo.e)) {
                    List<NeighboringCellInfo> neighboringCellInfo = a.getNeighboringCellInfo();
                    if (neighboringCellInfo != null) {
                        for (NeighboringCellInfo neighboringCellInfo2 : neighboringCellInfo) {
                            try {
                                JSONObject jSONObject2 = new JSONObject();
                                jSONObject2.put("systemId", neighboringCellInfo2.getLac());
                                jSONObject2.put("netId", neighboringCellInfo2.getCid());
                                jSONObject2.put("basestationId", neighboringCellInfo2.getPsc());
                                jSONObject2.put("asuLevel", neighboringCellInfo2.getRssi());
                                jSONObject2.put("type", neighboringCellInfo2.getNetworkType());
                                jSONArray.put(jSONObject2);
                            } catch (Throwable th2) {
                            }
                        }
                    }
                }
                return a(jSONArray, 20);
            } catch (Throwable th3) {
                cs.postSDKError(th3);
            }
        }
        return null;
    }

    public static JSONArray a(JSONArray jSONArray, int i) {
        try {
            if (jSONArray.length() > 0) {
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    arrayList.add(jSONArray.optJSONObject(i2));
                }
                Collections.sort(arrayList, new be());
                if (arrayList.size() <= i) {
                    i = arrayList.size();
                }
                return new JSONArray(arrayList.subList(0, i));
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public static JSONObject a(int i, int i2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("lat", i);
            jSONObject.put("lng", i2);
            jSONObject.put("unit", "qd");
        } catch (Throwable th) {
        }
        return jSONObject;
    }

    public static String v(Context context) {
        String ssid;
        try {
            if (!bo.b) {
                return null;
            }
            if (bo.b(context, MsgConstant.PERMISSION_ACCESS_WIFI_STATE)) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
                if (wifiManager.isWifiEnabled() && g(context)) {
                    WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                    if (!(connectionInfo == null || connectionInfo.getBSSID() == null)) {
                        try {
                            ssid = connectionInfo.getSSID();
                        } catch (Throwable th) {
                            ssid = null;
                        }
                        return ssid;
                    }
                }
            }
            ssid = null;
            return ssid;
        } catch (Throwable th2) {
            ssid = null;
        }
    }

    public static JSONArray w(Context context) {
        try {
            if (!bo.b) {
                return null;
            }
            if (bo.b(context, MsgConstant.PERMISSION_ACCESS_WIFI_STATE)) {
                List<WifiConfiguration> configuredNetworks = ((WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI)).getConfiguredNetworks();
                if (configuredNetworks != null) {
                    JSONArray jSONArray = new JSONArray();
                    for (WifiConfiguration wifiConfiguration : configuredNetworks) {
                        try {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("networkId", wifiConfiguration.networkId);
                            jSONObject.put("priority", wifiConfiguration.priority);
                            jSONObject.put("name", wifiConfiguration.SSID);
                            jSONObject.put("id", wifiConfiguration.BSSID);
                            jSONObject.put("allowedKeyManagement", a(wifiConfiguration.allowedKeyManagement));
                            jSONObject.put("allowedAuthAlgorithms", a(wifiConfiguration.allowedAuthAlgorithms));
                            jSONObject.put("allowedGroupCiphers", a(wifiConfiguration.allowedGroupCiphers));
                            jSONObject.put("allowedPairwiseCiphers", a(wifiConfiguration.allowedPairwiseCiphers));
                            jSONArray.put(jSONObject);
                        } catch (Throwable th) {
                        }
                    }
                    return b(jSONArray, 30);
                }
            }
            return null;
        } catch (Throwable th2) {
        }
    }

    public static JSONArray b(JSONArray jSONArray, int i) {
        try {
            if (jSONArray.length() > 0) {
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    arrayList.add(jSONArray.optJSONObject(i2));
                }
                Collections.sort(arrayList, new bf());
                if (arrayList.size() <= i) {
                    i = arrayList.size();
                }
                return new JSONArray(arrayList.subList(0, i));
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public static JSONArray x(Context context) {
        try {
            if (!bo.b) {
                return null;
            }
            if (bo.b(context, MsgConstant.PERMISSION_ACCESS_WIFI_STATE)) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
                if (wifiManager.isWifiEnabled()) {
                    WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                    if (!(connectionInfo == null || connectionInfo.getBSSID() == null)) {
                        String bssid = connectionInfo.getBSSID();
                        JSONArray jSONArray = new JSONArray();
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put("name", connectionInfo.getSSID());
                            jSONObject.put("id", bssid);
                            jSONObject.put("level", connectionInfo.getRssi());
                            jSONObject.put("hidden", connectionInfo.getHiddenSSID());
                            jSONObject.put("ip", connectionInfo.getIpAddress());
                            jSONObject.put(Parameters.SPEED, connectionInfo.getLinkSpeed());
                            jSONObject.put("networkId", connectionInfo.getNetworkId());
                            jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_MAC, connectionInfo.getMacAddress());
                            DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
                            if (dhcpInfo != null) {
                                JSONObject jSONObject2 = new JSONObject();
                                jSONObject2.put("dns1", dhcpInfo.dns1);
                                jSONObject2.put("dns2", dhcpInfo.dns2);
                                jSONObject2.put("gw", dhcpInfo.gateway);
                                jSONObject2.put("ip", dhcpInfo.ipAddress);
                                jSONObject2.put("mask", dhcpInfo.netmask);
                                jSONObject2.put("server", dhcpInfo.serverAddress);
                                jSONObject2.put("leaseDuration", dhcpInfo.leaseDuration);
                                jSONObject.put("dhcp", jSONObject2);
                            }
                        } catch (Throwable th) {
                        }
                        jSONArray.put(jSONObject);
                        return jSONArray;
                    }
                }
            }
            return null;
        } catch (Throwable th2) {
        }
    }

    public static JSONArray y(Context context) {
        if (!bo.b || (!bo.d && !bo.e)) {
            return null;
        }
        try {
            if (bo.b(context, MsgConstant.PERMISSION_ACCESS_WIFI_STATE)) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
                if (wifiManager.isWifiEnabled() || (VERSION.SDK_INT >= 18 && wifiManager.isScanAlwaysAvailable())) {
                    if (bo.b(context, "android.permission.CHANGE_WIFI_STATE")) {
                        try {
                            Object obj = new Object();
                            context.registerReceiver(new bg(context, obj), new IntentFilter("android.net.wifi.SCAN_RESULTS"));
                            wifiManager.startScan();
                            synchronized (obj) {
                                obj.wait(2000);
                            }
                        } catch (Throwable th) {
                        }
                    }
                    List<ScanResult> scanResults = wifiManager.getScanResults();
                    if (scanResults != null) {
                        ArrayList arrayList = new ArrayList();
                        for (ScanResult scanResult : scanResults) {
                            if (scanResult.level >= -85) {
                                JSONObject jSONObject = new JSONObject();
                                try {
                                    jSONObject.put("id", scanResult.BSSID);
                                    jSONObject.put("name", scanResult.SSID);
                                    jSONObject.put("level", scanResult.level);
                                    jSONObject.put("freq", scanResult.frequency);
                                    if (bo.a(17)) {
                                        jSONObject.put("ts", scanResult.timestamp);
                                        jSONObject.put("scanTs", (System.currentTimeMillis() - SystemClock.elapsedRealtime()) + (scanResult.timestamp / 1000));
                                    }
                                    arrayList.add(jSONObject);
                                } catch (Throwable th2) {
                                }
                            }
                        }
                        return a(arrayList, 20);
                    }
                }
            }
        } catch (Throwable th3) {
        }
        return null;
    }

    public static JSONArray a(ArrayList arrayList, int i) {
        try {
            Collections.sort(arrayList, new bh());
            if (arrayList.size() <= i) {
                i = arrayList.size();
            }
            return new JSONArray(arrayList.subList(0, i));
        } catch (Throwable th) {
            return null;
        }
    }

    private static JSONArray a(BitSet bitSet) {
        if (bitSet != null && bitSet.cardinality() >= 1) {
            JSONArray jSONArray = new JSONArray();
            int nextSetBit = bitSet.nextSetBit(0);
            while (nextSetBit >= 0) {
                jSONArray.put(nextSetBit);
                nextSetBit = bitSet.nextSetBit(nextSetBit + 1);
            }
        }
        return null;
    }

    public static JSONArray z(Context context) {
        JSONArray jSONArray = new JSONArray();
        if (context == null) {
            if (ab.g == null) {
                return jSONArray;
            }
            context = ab.g;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
            List arrayList = new ArrayList();
            JSONObject a;
            String str;
            if (bo.a(22)) {
                SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService("telephony_subscription_service");
                try {
                    JSONObject a2 = a(telephonyManager, subscriptionManager, 0);
                    if (bo.b(context, MsgConstant.PERMISSION_READ_PHONE_STATE) && telephonyManager != null) {
                        a2.put("imei", telephonyManager.getDeviceId());
                    }
                    jSONArray.put(a2);
                } catch (Throwable th) {
                }
                try {
                    a = a(telephonyManager, subscriptionManager, 1);
                    if (bo.b(context, MsgConstant.PERMISSION_READ_PHONE_STATE) && telephonyManager != null) {
                        str = "imei";
                        Object deviceId = (bo.a(23) && telephonyManager.getPhoneCount() == 2) ? telephonyManager.getDeviceId(1) : null;
                        a.put(str, deviceId);
                    }
                    if (a.length() > 0) {
                        jSONArray.put(a);
                    }
                } catch (Throwable th2) {
                }
            } else {
                JSONObject a3;
                str = "";
                if (bo.b(context, MsgConstant.PERMISSION_READ_PHONE_STATE) && telephonyManager != null) {
                    str = telephonyManager.getDeviceId();
                }
                if (b(str.trim()).booleanValue()) {
                    arrayList.add(str.trim());
                    a3 = a(telephonyManager, str);
                    if (a3 != null) {
                        jSONArray.put(a3);
                    }
                }
                try {
                    telephonyManager = (TelephonyManager) context.getSystemService("phone1");
                    str = "";
                    if (bo.b(context, MsgConstant.PERMISSION_READ_PHONE_STATE) && telephonyManager != null) {
                        str = telephonyManager.getDeviceId();
                    }
                    if (!(str == null || !b(str).booleanValue() || arrayList.contains(str))) {
                        arrayList.add(str);
                        a3 = a(telephonyManager, str);
                        if (a3 != null) {
                            jSONArray.put(a3);
                        }
                    }
                } catch (Throwable th3) {
                }
                try {
                    telephonyManager = (TelephonyManager) context.getSystemService("phone2");
                    str = "";
                    if (bo.b(context, MsgConstant.PERMISSION_READ_PHONE_STATE) && telephonyManager != null) {
                        str = telephonyManager.getDeviceId();
                    }
                    if (!(str == null || !b(str).booleanValue() || arrayList.contains(str))) {
                        arrayList.add(str);
                        a3 = a(telephonyManager, str);
                        if (a3 != null) {
                            jSONArray.put(a3);
                        }
                    }
                } catch (Throwable th4) {
                }
                JSONArray E = E(context);
                JSONArray D = D(context);
                if (D == null) {
                    D = E;
                }
                E = C(context);
                if (E == null) {
                    E = D;
                }
                D = B(context);
                if (D != null) {
                    E = D;
                }
                if (E != null && E.length() > 0) {
                    for (int i = 0; i < E.length(); i++) {
                        a = E.getJSONObject(i);
                        String string = a.getString("imei");
                        if (!arrayList.contains(string)) {
                            arrayList.add(string);
                            jSONArray.put(a);
                        }
                    }
                }
            }
        } catch (Throwable th5) {
        }
        return jSONArray;
    }

    private static JSONObject a(TelephonyManager telephonyManager, String str) {
        try {
            Object obj;
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("imei", str.trim());
            jSONObject.put("subscriberId", telephonyManager.getSubscriberId() == null ? "" : telephonyManager.getSubscriberId());
            String str2 = "simSerialNumber";
            if (telephonyManager.getSimSerialNumber() == null) {
                obj = "";
            } else {
                obj = telephonyManager.getSimSerialNumber();
            }
            jSONObject.put(str2, obj);
            jSONObject.put("dataState", telephonyManager.getDataState());
            jSONObject.put("networkType", a(telephonyManager.getNetworkType()));
            jSONObject.put("networkOperator", telephonyManager.getNetworkOperator());
            jSONObject.put("phoneType", c(telephonyManager.getPhoneType()));
            jSONObject.put("simOperator", telephonyManager.getSimOperator() == null ? "" : telephonyManager.getSimOperator());
            str2 = "simOperatorName";
            if (telephonyManager.getSimOperatorName() == null) {
                obj = "";
            } else {
                obj = telephonyManager.getSimOperatorName();
            }
            jSONObject.put(str2, obj);
            jSONObject.put("simCountryIso", telephonyManager.getSimCountryIso() == null ? "" : telephonyManager.getSimCountryIso());
            return jSONObject;
        } catch (Throwable th) {
            return null;
        }
    }

    private static JSONObject a(TelephonyManager telephonyManager, SubscriptionManager subscriptionManager, int i) {
        JSONObject jSONObject = new JSONObject();
        try {
            if (bo.a(22)) {
                SubscriptionInfo activeSubscriptionInfoForSimSlotIndex = subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(i);
                if (activeSubscriptionInfoForSimSlotIndex != null) {
                    Object obj;
                    String str = "simSerialNumber";
                    if (activeSubscriptionInfoForSimSlotIndex.getIccId() == null) {
                        obj = "";
                    } else {
                        obj = activeSubscriptionInfoForSimSlotIndex.getIccId();
                    }
                    jSONObject.put(str, obj);
                    jSONObject.put("simOperator", activeSubscriptionInfoForSimSlotIndex.getMcc() + PushConstants.PUSH_TYPE_NOTIFY + activeSubscriptionInfoForSimSlotIndex.getMnc());
                    str = "simOperatorName";
                    if (activeSubscriptionInfoForSimSlotIndex.getCarrierName() == null) {
                        obj = "";
                    } else {
                        obj = activeSubscriptionInfoForSimSlotIndex.getCarrierName();
                    }
                    jSONObject.put(str, obj);
                    jSONObject.put("simCountryIso", activeSubscriptionInfoForSimSlotIndex.getCountryIso() == null ? "" : activeSubscriptionInfoForSimSlotIndex.getCountryIso());
                    int subscriptionId = activeSubscriptionInfoForSimSlotIndex.getSubscriptionId();
                    try {
                        Method method = telephonyManager.getClass().getMethod("getSubscriberId", new Class[]{Integer.TYPE});
                        method.setAccessible(true);
                        obj = method.invoke(telephonyManager, new Object[]{Integer.valueOf(subscriptionId)});
                        String str2 = "subscriberId";
                        if (obj == null) {
                            obj = "";
                        }
                        jSONObject.put(str2, obj);
                    } catch (Throwable th) {
                    }
                }
            }
        } catch (Throwable th2) {
        }
        return jSONObject;
    }

    public static JSONObject A(Context context) {
        try {
            JSONObject jSONObject = new JSONObject();
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
                jSONObject.put("dataState", telephonyManager.getDataState());
                jSONObject.put("networkType", a(telephonyManager.getNetworkType()));
                jSONObject.put("networkOperator", telephonyManager.getNetworkOperator());
                jSONObject.put("phoneType", c(telephonyManager.getPhoneType()));
                return jSONObject;
            } catch (Throwable th) {
                return jSONObject;
            }
        } catch (Throwable th2) {
            return null;
        }
    }

    private static Boolean a(String str) {
        try {
            char charAt;
            if (str.length() > 0) {
                charAt = str.charAt(0);
            } else {
                charAt = '0';
            }
            Boolean valueOf = Boolean.valueOf(true);
            for (int i = 0; i < str.length(); i++) {
                if (charAt != str.charAt(i)) {
                    return Boolean.valueOf(false);
                }
            }
            return valueOf;
        } catch (Throwable th) {
            return Boolean.valueOf(false);
        }
    }

    private static Boolean b(String str) {
        try {
            Integer valueOf = Integer.valueOf(str.length());
            if (valueOf.intValue() > 10 && valueOf.intValue() < 20 && !a(str.trim()).booleanValue()) {
                return Boolean.valueOf(true);
            }
        } catch (Throwable th) {
        }
        return Boolean.valueOf(false);
    }

    private static JSONArray B(Context context) {
        JSONArray jSONArray;
        TelephonyManager telephonyManager;
        Integer num;
        Integer num2;
        Integer num3;
        try {
            jSONArray = new JSONArray();
            telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
            Class cls = Class.forName("com.android.internal.telephony.Phone");
            Field field = cls.getField("GEMINI_SIM_1");
            field.setAccessible(true);
            num3 = (Integer) field.get(null);
            Field field2 = cls.getField("GEMINI_SIM_2");
            field2.setAccessible(true);
            num = (Integer) field2.get(null);
            num2 = num3;
        } catch (Throwable th) {
            return null;
        }
        Method declaredMethod = TelephonyManager.class.getDeclaredMethod("getDeviceIdGemini", new Class[]{Integer.TYPE});
        if (telephonyManager == null || declaredMethod == null) {
            return null;
        }
        String trim = ((String) declaredMethod.invoke(telephonyManager, new Object[]{num2})).trim();
        String trim2 = ((String) declaredMethod.invoke(telephonyManager, new Object[]{num})).trim();
        if (b(trim).booleanValue()) {
            jSONArray.put(a(TelephonyManager.class, telephonyManager, num2, trim, "Gemini"));
        }
        if (b(trim2).booleanValue()) {
            jSONArray.put(a(TelephonyManager.class, telephonyManager, num, trim2, "Gemini"));
        }
        return jSONArray;
    }

    private static JSONArray C(Context context) {
        JSONArray jSONArray;
        TelephonyManager telephonyManager;
        Integer num;
        Integer num2;
        try {
            jSONArray = new JSONArray();
            telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
            Class cls = Class.forName("com.android.internal.telephony.Phone");
            Field field = cls.getField("GEMINI_SIM_1");
            field.setAccessible(true);
            num = (Integer) field.get(null);
            Field field2 = cls.getField("GEMINI_SIM_2");
            field2.setAccessible(true);
            num2 = (Integer) field2.get(null);
        } catch (Throwable th) {
            return null;
        }
        Method method = TelephonyManager.class.getMethod("getDefault", new Class[]{Integer.TYPE});
        TelephonyManager telephonyManager2 = (TelephonyManager) method.invoke(telephonyManager, new Object[]{num});
        telephonyManager = (TelephonyManager) method.invoke(telephonyManager, new Object[]{num2});
        String trim = telephonyManager2.getDeviceId().trim();
        String trim2 = telephonyManager.getDeviceId().trim();
        if (b(trim).booleanValue()) {
            JSONObject a = a(telephonyManager2, trim);
            if (a != null) {
                jSONArray.put(a);
            }
        }
        if (b(trim2).booleanValue()) {
            JSONObject a2 = a(telephonyManager, trim2);
            if (a2 != null) {
                jSONArray.put(a2);
            }
        }
        return jSONArray;
    }

    private static JSONArray D(Context context) {
        try {
            JSONArray jSONArray = new JSONArray();
            Class cls = Class.forName("com.android.internal.telephony.PhoneFactory");
            String str = (String) cls.getMethod("getServiceName", new Class[]{String.class, Integer.TYPE}).invoke(cls, new Object[]{HttpConstant.PHONE, Integer.valueOf(1)});
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
            String trim = telephonyManager.getDeviceId().trim();
            TelephonyManager telephonyManager2 = (TelephonyManager) context.getSystemService(str);
            String trim2 = telephonyManager2.getDeviceId().trim();
            if (b(trim).booleanValue()) {
                JSONObject a = a(telephonyManager, trim);
                if (a != null) {
                    jSONArray.put(a);
                }
            }
            if (b(trim2).booleanValue()) {
                JSONObject a2 = a(telephonyManager2, trim2);
                if (a2 != null) {
                    jSONArray.put(a2);
                }
            }
            return jSONArray;
        } catch (Throwable th) {
            return null;
        }
    }

    private static JSONArray E(Context context) {
        try {
            JSONArray jSONArray = new JSONArray();
            Class cls = Class.forName("android.telephony.MSimTelephonyManager");
            Object systemService = context.getSystemService("phone_msim");
            Integer valueOf = Integer.valueOf(0);
            Integer valueOf2 = Integer.valueOf(1);
            Method method = cls.getMethod("getDeviceId", new Class[]{Integer.TYPE});
            String trim = ((String) method.invoke(systemService, new Object[]{valueOf})).trim();
            String trim2 = ((String) method.invoke(systemService, new Object[]{valueOf2})).trim();
            if (b(trim).booleanValue()) {
                jSONArray.put(a(cls, systemService, valueOf, trim, ""));
            }
            if (b(trim2).booleanValue()) {
                jSONArray.put(a(cls, systemService, valueOf2, trim2, ""));
            }
            return jSONArray;
        } catch (Throwable th) {
            return null;
        }
    }

    private static JSONObject a(Class cls, Object obj, Integer num, String str, String str2) {
        Method method;
        String str3;
        Object obj2;
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("imei", str);
        try {
            method = cls.getMethod("getSubscriberId" + str2, new Class[]{Integer.TYPE});
            str3 = "subscriberId";
            if (method.invoke(obj, new Object[]{num}) == null) {
                obj2 = "";
            } else {
                obj2 = ((String) method.invoke(obj, new Object[]{num})).trim();
            }
            jSONObject.put(str3, obj2);
        } catch (Throwable th) {
        }
        try {
            method = cls.getMethod("getSimSerialNumber" + str2, new Class[]{Integer.TYPE});
            str3 = "simSerialNumber";
            if (method.invoke(obj, new Object[]{num}) == null) {
                obj2 = "";
            } else {
                obj2 = ((String) method.invoke(obj, new Object[]{num})).trim();
            }
            jSONObject.put(str3, obj2);
        } catch (Throwable th2) {
        }
        try {
            jSONObject.put("dataState", (Integer) cls.getMethod("getDataState" + str2, new Class[]{Integer.TYPE}).invoke(obj, new Object[]{num}));
        } catch (Throwable th3) {
        }
        try {
            jSONObject.put("networkType", a(((Integer) cls.getMethod("getNetworkType" + str2, new Class[]{Integer.TYPE}).invoke(obj, new Object[]{num})).intValue()));
        } catch (Throwable th4) {
        }
        try {
            jSONObject.put("networkOperator", (String) cls.getMethod("getNetworkOperator" + str2, new Class[]{Integer.TYPE}).invoke(obj, new Object[]{num}));
        } catch (Throwable th5) {
        }
        try {
            jSONObject.put("phoneType", c(((Integer) cls.getMethod("getPhoneType" + str2, new Class[]{Integer.TYPE}).invoke(obj, new Object[]{num})).intValue()));
        } catch (Throwable th6) {
        }
        try {
            method = cls.getMethod("getSimOperator" + str2, new Class[]{Integer.TYPE});
            str3 = "simOperator";
            if (method.invoke(obj, new Object[]{num}) == null) {
                obj2 = "";
            } else {
                obj2 = ((String) method.invoke(obj, new Object[]{num})).trim();
            }
            jSONObject.put(str3, obj2);
        } catch (Throwable th7) {
        }
        try {
            method = cls.getMethod("getSimOperatorName" + str2, new Class[]{Integer.TYPE});
            str3 = "simOperatorName";
            if (method.invoke(obj, new Object[]{num}) == null) {
                obj2 = "";
            } else {
                obj2 = ((String) method.invoke(obj, new Object[]{num})).trim();
            }
            jSONObject.put(str3, obj2);
        } catch (Throwable th8) {
        }
        return jSONObject;
    }
}
