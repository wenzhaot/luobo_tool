package com.umeng.socialize.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.feng.car.utils.HttpConstant;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.taobao.accs.utl.UtilityImpl;
import com.umeng.message.MsgConstant;
import com.umeng.message.entity.UInAppMessage;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.net.utils.URequest;
import com.umeng.socialize.net.utils.URequest.RequestMethod;
import com.umeng.socialize.sina.helper.Base64;
import com.umeng.socialize.sina.params.ShareRequestParam;
import com.umeng.socialize.sina.util.Utility;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.DeviceConfig;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import org.android.agoo.common.AgooConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class a extends URequest {
    public a(String str) {
        super("https://api.weibo.com/oauth2/getaid.json");
        this.mMethod = RequestMethod.GET;
        String packageName = ContextUtil.getPackageName();
        String sign = Utility.getSign(ContextUtil.getContext(), packageName);
        String a = a(ContextUtil.getContext());
        addStringParams("appkey", str);
        addStringParams("mfp", a);
        addStringParams(ShareRequestParam.REQ_PARAM_PACKAGENAME, packageName);
        addStringParams(ShareRequestParam.REQ_PARAM_KEY_HASH, sign);
        this.mResponseClz = b.class;
    }

    private static int a(byte[] bArr, int i, int i2) {
        return i >= bArr.length ? -1 : Math.min(bArr.length - i, i2);
    }

    private static String a() {
        try {
            return "Android 1";
        } catch (Exception e) {
            return "";
        }
    }

    private static String a(Context context) {
        String str;
        String str2 = "";
        try {
            str = new String(b(context).getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            str = str2;
        }
        try {
            return a(str, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDHHM0Fi2Z6+QYKXqFUX2Cy6AaWq3cPi+GSn9oeAwQbPZR75JB7Netm0HtBVVbtPhzT7UO2p1JhFUKWqrqoYuAjkgMVPmA0sFrQohns5EE44Y86XQopD4ZO+dE5KjUZFE6vrPO3rWW3np2BqlgKpjnYZri6TJApmIpGcQg9/G/3zQIDAQAB");
        } catch (Exception e2) {
            return "";
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0063 A:{SYNTHETIC, Splitter: B:17:0x0063} */
    private static java.lang.String a(java.lang.String r6, java.lang.String r7) {
        /*
        r0 = "RSA/ECB/PKCS1Padding";
        r3 = javax.crypto.Cipher.getInstance(r0);
        r0 = a(r7);
        r1 = 1;
        r3.init(r1, r0);
        r2 = 0;
        r0 = "UTF-8";
        r4 = r6.getBytes(r0);
        r1 = new java.io.ByteArrayOutputStream;	 Catch:{ all -> 0x005f }
        r1.<init>();	 Catch:{ all -> 0x005f }
        r0 = 0;
    L_0x001d:
        r2 = 117; // 0x75 float:1.64E-43 double:5.8E-322;
        r2 = a(r4, r0, r2);	 Catch:{ all -> 0x006b }
        r5 = -1;
        if (r2 == r5) goto L_0x002f;
    L_0x0026:
        r5 = r3.doFinal(r4, r0, r2);	 Catch:{ all -> 0x006b }
        r1.write(r5);	 Catch:{ all -> 0x006b }
        r0 = r0 + r2;
        goto L_0x001d;
    L_0x002f:
        r1.flush();	 Catch:{ all -> 0x006b }
        r0 = r1.toByteArray();	 Catch:{ all -> 0x006b }
        r0 = com.umeng.socialize.sina.helper.Base64.encodebyte(r0);	 Catch:{ all -> 0x006b }
        r2 = "01";
        r2 = new java.lang.String;	 Catch:{ all -> 0x006b }
        r3 = "UTF-8";
        r2.<init>(r0, r3);	 Catch:{ all -> 0x006b }
        r0 = new java.lang.StringBuilder;	 Catch:{ all -> 0x006b }
        r0.<init>();	 Catch:{ all -> 0x006b }
        r3 = "01";
        r0 = r0.append(r3);	 Catch:{ all -> 0x006b }
        r0 = r0.append(r2);	 Catch:{ all -> 0x006b }
        r0 = r0.toString();	 Catch:{ all -> 0x006b }
        if (r1 == 0) goto L_0x005e;
    L_0x005b:
        r1.close();	 Catch:{ IOException -> 0x0067 }
    L_0x005e:
        return r0;
    L_0x005f:
        r0 = move-exception;
        r1 = r2;
    L_0x0061:
        if (r1 == 0) goto L_0x0066;
    L_0x0063:
        r1.close();	 Catch:{ IOException -> 0x0069 }
    L_0x0066:
        throw r0;
    L_0x0067:
        r1 = move-exception;
        goto L_0x005e;
    L_0x0069:
        r1 = move-exception;
        goto L_0x0066;
    L_0x006b:
        r0 = move-exception;
        goto L_0x0061;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.socialize.net.a.a(java.lang.String, java.lang.String):java.lang.String");
    }

    private static PublicKey a(String str) {
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(str.getBytes())));
    }

    private static String b() {
        String str = "";
        try {
            Class cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", new Class[]{String.class, String.class}).invoke(cls, new Object[]{"ro.serialno", "unknown"});
        } catch (Exception e) {
            return str;
        }
    }

    private static String b(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            CharSequence a = a();
            if (!TextUtils.isEmpty(a)) {
                jSONObject.put(PushConstants.PUSH_TYPE_THROUGH_MESSAGE, a);
            }
            a = DeviceConfig.getDeviceId(context);
            if (!TextUtils.isEmpty(a)) {
                jSONObject.put(PushConstants.PUSH_TYPE_UPLOAD_LOG, a);
            }
            a = c(context);
            if (!TextUtils.isEmpty(a)) {
                jSONObject.put("3", a);
            }
            a = d(context);
            if (!TextUtils.isEmpty(a)) {
                jSONObject.put("4", a);
            }
            a = e(context);
            if (!TextUtils.isEmpty(a)) {
                jSONObject.put("5", a);
            }
            a = f(context);
            if (!TextUtils.isEmpty(a)) {
                jSONObject.put("6", a);
            }
            a = b();
            if (!TextUtils.isEmpty(a)) {
                jSONObject.put(MsgConstant.MESSAGE_NOTIFY_ARRIVAL, a);
            }
            a = g(context);
            if (!TextUtils.isEmpty(a)) {
                jSONObject.put(AgooConstants.ACK_REMOVE_PACKAGE, a);
            }
            a = c();
            if (!TextUtils.isEmpty(a)) {
                jSONObject.put(AgooConstants.ACK_FLAG_NULL, a);
            }
            a = d();
            if (!TextUtils.isEmpty(a)) {
                jSONObject.put(AgooConstants.ACK_PACK_NOBIND, a);
            }
            a = e();
            if (!TextUtils.isEmpty(a)) {
                jSONObject.put(AgooConstants.ACK_PACK_ERROR, a);
            }
            a = h(context);
            if (!TextUtils.isEmpty(a)) {
                jSONObject.put("16", a);
            }
            a = i(context);
            if (!TextUtils.isEmpty(a)) {
                jSONObject.put("17", a);
            }
            a = f();
            if (!TextUtils.isEmpty(a)) {
                jSONObject.put("18", a);
            }
            a = j(context);
            if (!TextUtils.isEmpty(a)) {
                jSONObject.put("19", a);
            }
            return jSONObject.toString();
        } catch (JSONException e) {
            return "";
        }
    }

    private static String c() {
        try {
            return Build.CPU_ABI;
        } catch (Exception e) {
            return "";
        }
    }

    private static String c(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService(HttpConstant.PHONE)).getDeviceId();
        } catch (Exception e) {
            return "";
        }
    }

    private static String d() {
        try {
            return Build.MODEL;
        } catch (Exception e) {
            return "";
        }
    }

    private static String d(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService(HttpConstant.PHONE)).getSubscriberId();
        } catch (Exception e) {
            return "";
        }
    }

    private static String e() {
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return Long.toString(((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize()));
        } catch (Exception e) {
            return "";
        }
    }

    private static String e(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
            if (wifiManager == null) {
                return "";
            }
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            return connectionInfo != null ? connectionInfo.getMacAddress() : "";
        } catch (Exception e) {
            return "";
        }
    }

    private static String f() {
        try {
            return Build.BRAND;
        } catch (Exception e) {
            return "";
        }
    }

    private static String f(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService(HttpConstant.PHONE)).getSimSerialNumber();
        } catch (Exception e) {
            return "";
        }
    }

    private static String g(Context context) {
        try {
            return Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
        } catch (Exception e) {
            return "";
        }
    }

    private static String h(Context context) {
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            return String.valueOf(displayMetrics.widthPixels) + "*" + displayMetrics.heightPixels;
        } catch (Exception e) {
            return "";
        }
    }

    private static String i(Context context) {
        try {
            WifiInfo connectionInfo = ((WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI)).getConnectionInfo();
            if (connectionInfo != null) {
                return connectionInfo.getSSID();
            }
        } catch (Exception e) {
        }
        return "";
    }

    private static String j(Context context) {
        String str = UInAppMessage.NONE;
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (activeNetworkInfo.getType() == 0) {
                    switch (activeNetworkInfo.getSubtype()) {
                        case 1:
                        case 2:
                        case 4:
                        case 7:
                        case 11:
                            return "2G";
                        case 3:
                        case 5:
                        case 6:
                        case 8:
                        case 9:
                        case 10:
                        case 12:
                        case 14:
                        case 15:
                            return "3G";
                        case 13:
                            return "4G";
                        default:
                            return UInAppMessage.NONE;
                    }
                } else if (activeNetworkInfo.getType() == 1) {
                    return UtilityImpl.NET_TYPE_WIFI;
                }
            }
            return str;
        } catch (Exception e) {
            return str;
        }
    }

    public Map<String, Object> buildParams() {
        Map<String, Object> hashMap = new HashMap();
        hashMap.putAll(this.mParams);
        return hashMap;
    }

    public String toGetUrl() {
        return generateGetURL(getBaseUrl(), buildParams());
    }

    public JSONObject toJson() {
        return null;
    }
}
