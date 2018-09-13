package com.talkingdata.sdk;

import android.accounts.Account;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.pushtracer.constant.Parameters;
import com.stub.StubApp;
import com.umeng.analytics.pro.b;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: td */
public class bn {
    public static int a = 0;

    public static List a(Context context) {
        List arrayList = new ArrayList();
        try {
            if (!bo.b) {
                return arrayList;
            }
            if (bo.a(23) && context.checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") != 0) {
                return arrayList;
            }
            Context context2 = ab.g;
            Context context3 = ab.g;
            LocationManager locationManager = (LocationManager) context2.getSystemService("location");
            boolean isProviderEnabled = locationManager.isProviderEnabled("gps");
            boolean isProviderEnabled2 = locationManager.isProviderEnabled("network");
            if (isProviderEnabled || isProviderEnabled2) {
                arrayList.add(StubApp.mark(locationManager, "passive"));
            }
            return arrayList;
        } catch (Throwable th) {
        }
    }

    public static String b(Context context) {
        try {
            List<Location> a = a(context);
            StringBuffer stringBuffer = new StringBuffer();
            for (Location location : a) {
                stringBuffer.append(location.getLatitude()).append(',').append(location.getLongitude()).append(',').append(location.hasAltitude() ? Double.valueOf(location.getAltitude()) : "").append(',').append(location.getTime()).append(',').append(location.hasAccuracy() ? Float.valueOf(location.getAccuracy()) : "").append(',').append(location.hasBearing() ? Float.valueOf(location.getBearing()) : "").append(',').append(location.hasSpeed() ? Float.valueOf(location.getSpeed()) : "").append(',').append(location.getProvider()).append(':');
            }
            return stringBuffer.toString();
        } catch (Throwable th) {
            return null;
        }
    }

    public static JSONArray c(Context context) {
        JSONArray jSONArray = new JSONArray();
        if (!bo.b) {
            return jSONArray;
        }
        if (bo.a(23) && context.checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") != 0) {
            return jSONArray;
        }
        try {
            Context context2 = ab.g;
            Context context3 = ab.g;
            LocationManager locationManager = (LocationManager) context2.getSystemService("location");
            boolean isProviderEnabled = locationManager.isProviderEnabled("gps");
            boolean isProviderEnabled2 = locationManager.isProviderEnabled("network");
            if (isProviderEnabled || isProviderEnabled2) {
                Location mark = StubApp.mark(locationManager, "passive");
                if (mark != null) {
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("lat", mark.getLatitude());
                        jSONObject.put("lng", mark.getLongitude());
                        jSONObject.put("ts", mark.getTime());
                        if (bo.a(17)) {
                            jSONObject.put("elapsed", mark.getElapsedRealtimeNanos());
                        }
                        if (mark.hasAltitude()) {
                            jSONObject.put("altitude", mark.getAltitude());
                        }
                        if (mark.hasAccuracy()) {
                            jSONObject.put("hAccuracy", (double) mark.getAccuracy());
                        }
                        if (mark.hasBearing()) {
                            jSONObject.put("bearing", (double) mark.getBearing());
                        }
                        if (mark.hasSpeed()) {
                            jSONObject.put(Parameters.SPEED, (double) mark.getSpeed());
                        }
                        jSONObject.put(b.H, mark.getProvider());
                        jSONArray.put(jSONObject);
                    } catch (Throwable th) {
                    }
                }
            }
        } catch (Throwable th2) {
            cs.postSDKError(th2);
        }
        return jSONArray;
    }

    public static JSONArray d(Context context) {
        try {
            JSONArray jSONArray = new JSONArray();
            Account[] e = e(context);
            if (e != null) {
                for (Account account : e) {
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("type", account.type);
                        jSONObject.put("name", account.name);
                        jSONArray.put(jSONObject);
                    } catch (Throwable th) {
                    }
                }
            }
            JSONArray z = bd.z(context);
            a = z.length();
            if (z.length() > 0) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("type", "sim");
                jSONObject2.put(PushConstants.EXTRA, z);
                jSONArray.put(jSONObject2);
            }
            if (jSONArray.length() > 0) {
                return jSONArray;
            }
        } catch (Throwable th2) {
            cs.postSDKError(th2);
        }
        return null;
    }

    public static Account[] e(Context context) {
        if (bo.b(context, "android.permission.GET_ACCOUNTS")) {
        }
        return null;
    }

    public static Long[][] f(Context context) {
        return new Long[3][];
    }
}
