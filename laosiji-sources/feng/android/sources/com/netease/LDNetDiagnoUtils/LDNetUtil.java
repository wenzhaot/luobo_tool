package com.netease.LDNetDiagnoUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.feng.car.utils.HttpConstant;
import com.stub.StubApp;
import com.taobao.accs.utl.UtilityImpl;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@SuppressLint({"DefaultLocale"})
public class LDNetUtil {
    public static final String NETWORKTYPE_INVALID = "UNKNOWN";
    public static final String NETWORKTYPE_WAP = "WAP";
    public static final String NETWORKTYPE_WIFI = "WIFI";
    public static String OPEN_IP = "laosiji.com";
    public static final String OPERATOR_URL = "";

    public static String getNetWorkType(Context context) {
        String mNetWorkType = null;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService("connectivity");
        if (manager == null) {
            return "ConnectivityManager not found";
        }
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            mNetWorkType = NETWORKTYPE_INVALID;
        } else {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase(NETWORKTYPE_WIFI)) {
                mNetWorkType = NETWORKTYPE_WIFI;
            } else if (type.equalsIgnoreCase("MOBILE")) {
                if (TextUtils.isEmpty(Proxy.getDefaultHost())) {
                    mNetWorkType = mobileNetworkType(context);
                } else {
                    mNetWorkType = NETWORKTYPE_WAP;
                }
            }
        }
        return mNetWorkType;
    }

    public static Boolean isNetworkConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) StubApp.getOrigApplicationContext(context.getApplicationContext()).getSystemService("connectivity");
        if (manager == null) {
            return Boolean.valueOf(false);
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
            return Boolean.valueOf(false);
        }
        return Boolean.valueOf(true);
    }

    public static String getMobileOperator(Context context) {
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
        if (telManager == null) {
            return "未知运营商";
        }
        String operator = telManager.getSimOperator();
        if (operator != null) {
            if (operator.equals("46000") || operator.equals("46002") || operator.equals("46007")) {
                return "中国移动";
            }
            if (operator.equals("46001")) {
                return "中国联通";
            }
            if (operator.equals("46003")) {
                return "中国电信";
            }
        }
        return "未知运营商";
    }

    public static String getLocalIpByWifi(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
        if (wifiManager == null) {
            return "wifiManager not found";
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo == null) {
            return "wifiInfo not found";
        }
        int ipAddress = wifiInfo.getIpAddress();
        return String.format("%d.%d.%d.%d", new Object[]{Integer.valueOf(ipAddress & 255), Integer.valueOf((ipAddress >> 8) & 255), Integer.valueOf((ipAddress >> 16) & 255), Integer.valueOf((ipAddress >> 24) & 255)});
    }

    public static String getLocalIpBy3G() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                Enumeration<InetAddress> enumIpAddr = ((NetworkInterface) en.nextElement()).getInetAddresses();
                while (enumIpAddr.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String pingGateWayInWifi(Context context) {
        String gateWay = null;
        WifiManager wifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
        if (wifiManager == null) {
            return "wifiManager not found";
        }
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        if (dhcpInfo != null) {
            int tmp = dhcpInfo.gateway;
            gateWay = String.format("%d.%d.%d.%d", new Object[]{Integer.valueOf(tmp & 255), Integer.valueOf((tmp >> 8) & 255), Integer.valueOf((tmp >> 16) & 255), Integer.valueOf((tmp >> 24) & 255)});
        }
        return gateWay;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0065 A:{SYNTHETIC, Splitter: B:22:0x0065} */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0074 A:{SYNTHETIC, Splitter: B:30:0x0074} */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0080 A:{SYNTHETIC, Splitter: B:36:0x0080} */
    public static java.lang.String getLocalDns(java.lang.String r9) {
        /*
        r2 = 0;
        r5 = "";
        r3 = 0;
        r6 = java.lang.Runtime.getRuntime();	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r7 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r7.<init>();	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r8 = "getprop net.";
        r7 = r7.append(r8);	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r7 = r7.append(r9);	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r7 = r7.toString();	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r2 = r6.exec(r7);	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r4 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r6 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r7 = r2.getInputStream();	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r6.<init>(r7);	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r4.<init>(r6);	 Catch:{ IOException -> 0x005f, InterruptedException -> 0x006e }
        r1 = 0;
    L_0x0030:
        r1 = r4.readLine();	 Catch:{ IOException -> 0x008f, InterruptedException -> 0x008c, all -> 0x0089 }
        if (r1 == 0) goto L_0x0048;
    L_0x0036:
        r6 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x008f, InterruptedException -> 0x008c, all -> 0x0089 }
        r6.<init>();	 Catch:{ IOException -> 0x008f, InterruptedException -> 0x008c, all -> 0x0089 }
        r6 = r6.append(r5);	 Catch:{ IOException -> 0x008f, InterruptedException -> 0x008c, all -> 0x0089 }
        r6 = r6.append(r1);	 Catch:{ IOException -> 0x008f, InterruptedException -> 0x008c, all -> 0x0089 }
        r5 = r6.toString();	 Catch:{ IOException -> 0x008f, InterruptedException -> 0x008c, all -> 0x0089 }
        goto L_0x0030;
    L_0x0048:
        r4.close();	 Catch:{ IOException -> 0x008f, InterruptedException -> 0x008c, all -> 0x0089 }
        r2.waitFor();	 Catch:{ IOException -> 0x008f, InterruptedException -> 0x008c, all -> 0x0089 }
        if (r4 == 0) goto L_0x0053;
    L_0x0050:
        r4.close();	 Catch:{ Exception -> 0x005c }
    L_0x0053:
        r2.destroy();	 Catch:{ Exception -> 0x005c }
        r3 = r4;
    L_0x0057:
        r6 = r5.trim();
        return r6;
    L_0x005c:
        r6 = move-exception;
        r3 = r4;
        goto L_0x0057;
    L_0x005f:
        r0 = move-exception;
    L_0x0060:
        r0.printStackTrace();	 Catch:{ all -> 0x007d }
        if (r3 == 0) goto L_0x0068;
    L_0x0065:
        r3.close();	 Catch:{ Exception -> 0x006c }
    L_0x0068:
        r2.destroy();	 Catch:{ Exception -> 0x006c }
        goto L_0x0057;
    L_0x006c:
        r6 = move-exception;
        goto L_0x0057;
    L_0x006e:
        r0 = move-exception;
    L_0x006f:
        r0.printStackTrace();	 Catch:{ all -> 0x007d }
        if (r3 == 0) goto L_0x0077;
    L_0x0074:
        r3.close();	 Catch:{ Exception -> 0x007b }
    L_0x0077:
        r2.destroy();	 Catch:{ Exception -> 0x007b }
        goto L_0x0057;
    L_0x007b:
        r6 = move-exception;
        goto L_0x0057;
    L_0x007d:
        r6 = move-exception;
    L_0x007e:
        if (r3 == 0) goto L_0x0083;
    L_0x0080:
        r3.close();	 Catch:{ Exception -> 0x0087 }
    L_0x0083:
        r2.destroy();	 Catch:{ Exception -> 0x0087 }
    L_0x0086:
        throw r6;
    L_0x0087:
        r7 = move-exception;
        goto L_0x0086;
    L_0x0089:
        r6 = move-exception;
        r3 = r4;
        goto L_0x007e;
    L_0x008c:
        r0 = move-exception;
        r3 = r4;
        goto L_0x006f;
    L_0x008f:
        r0 = move-exception;
        r3 = r4;
        goto L_0x0060;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.netease.LDNetDiagnoUtils.LDNetUtil.getLocalDns(java.lang.String):java.lang.String");
    }

    public static Map<String, Object> getDomainIp(String _dormain) {
        Map<String, Object> map = new HashMap();
        String time = null;
        InetAddress[] remoteInet = null;
        try {
            long start = System.currentTimeMillis();
            remoteInet = InetAddress.getAllByName(_dormain);
            if (remoteInet != null) {
                time = (System.currentTimeMillis() - start) + "";
            }
            map.put("remoteInet", remoteInet);
            map.put("useTime", time);
        } catch (UnknownHostException e) {
            time = (System.currentTimeMillis() - 0) + "";
            remoteInet = null;
            e.printStackTrace();
            map.put("remoteInet", null);
            map.put("useTime", time);
        } catch (Throwable th) {
            map.put("remoteInet", remoteInet);
            map.put("useTime", time);
            throw th;
        }
        return map;
    }

    private static String mobileNetworkType(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
        if (telephonyManager == null) {
            return "TM==null";
        }
        switch (telephonyManager.getNetworkType()) {
            case 0:
                return NETWORKTYPE_INVALID;
            case 1:
                return "2G";
            case 2:
                return "2G";
            case 3:
                return "3G";
            case 4:
                return "2G";
            case 5:
                return "3G";
            case 6:
                return "3G";
            case 7:
                return "2G";
            case 8:
                return "3G";
            case 9:
                return "3G";
            case 10:
                return "3G";
            case 11:
                return "2G";
            case 12:
                return "3G";
            case 13:
                return "4G";
            case 14:
                return "3G";
            case 15:
                return "3G";
            default:
                return "4G";
        }
    }

    public static String getStringFromStream(InputStream is) {
        byte[] bytes = new byte[1024];
        String res = "";
        while (true) {
            try {
                int len = is.read(bytes);
                if (len == -1) {
                    break;
                }
                res = res + new String(bytes, 0, len, "gbk");
            } catch (IOException e) {
                e.printStackTrace();
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (Throwable th) {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e22) {
                        e22.printStackTrace();
                    }
                }
            }
        }
        if (is != null) {
            try {
                is.close();
            } catch (IOException e222) {
                e222.printStackTrace();
            }
        }
        return res;
    }
}
