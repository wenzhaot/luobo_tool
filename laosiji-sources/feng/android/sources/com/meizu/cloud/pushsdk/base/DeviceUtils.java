package com.meizu.cloud.pushsdk.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.taobao.accs.utl.UtilityImpl;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class DeviceUtils {
    private static final String TAG = "DeviceUtils";
    private static String sDeviceId = "";
    private static String sMacAddr = "";

    public static boolean isPhone() {
        String value = SystemProperties.get("ro.target.product");
        if (TextUtils.isEmpty(value)) {
            DebugLogger.i(TAG, "current product is phone");
            return true;
        }
        DebugLogger.i(TAG, "current product is " + value);
        return false;
    }

    public static String getDeviceType() {
        String deviceType = SystemProperties.get("ro.target.product");
        if (!TextUtils.isEmpty(deviceType)) {
            DebugLogger.i(TAG, "current product is " + deviceType);
        }
        return deviceType;
    }

    public static String getDeviceId(Context context) {
        if (TextUtils.isEmpty(sDeviceId)) {
            if (isPhone()) {
                sDeviceId = MzTelephoneManager.getDeviceId(context);
            } else if (TextUtils.isEmpty(sDeviceId)) {
                StringBuilder nonce = new StringBuilder();
                String sn = Build.SERIAL;
                DebugLogger.i(TAG, "device serial " + sn);
                if (TextUtils.isEmpty(sn)) {
                    return null;
                }
                nonce.append(sn);
                String mac = getMACAddress(context);
                DebugLogger.e(TAG, "mac address " + mac);
                if (TextUtils.isEmpty(mac)) {
                    return null;
                }
                nonce.append(mac.replace(":", "").toUpperCase());
                sDeviceId = nonce.toString();
            }
        }
        return sDeviceId;
    }

    public static String getMACAddress(Context context) {
        if (!TextUtils.isEmpty(sMacAddr)) {
            return sMacAddr;
        }
        String address = null;
        if (VERSION.SDK_INT >= 23) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
            if (cm != null) {
                NetworkInfo info = cm.getActiveNetworkInfo();
                if (info == null) {
                    address = getMacAddressWithIfName("wlan0");
                    if (TextUtils.isEmpty(address)) {
                        address = getMacAddressWithIfName("eth0");
                    }
                } else if (info.getType() == 1) {
                    address = getMacAddressWithIfName("wlan0");
                } else if (info.getType() == 9) {
                    address = getMacAddressWithIfName("eth0");
                }
            }
        } else {
            WifiManager wifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
            if (wifiManager != null) {
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                address = wifiInfo == null ? null : wifiInfo.getMacAddress();
            }
        }
        sMacAddr = address;
        return sMacAddr;
    }

    private static String getMacAddressWithIfName(String name) {
        String address = null;
        try {
            InputStream in = new FileInputStream("/sys/class/net/" + name + "/address");
            Scanner reader = new Scanner(in);
            if (reader.hasNextLine()) {
                address = reader.nextLine().trim();
            }
            in.close();
        } catch (FileNotFoundException e) {
            DebugLogger.e(TAG, "getMacAddressWithIfName File not found Exception");
        } catch (IOException e2) {
            DebugLogger.e(TAG, "getMacAddressWithIfName IOException");
        }
        return address;
    }
}
