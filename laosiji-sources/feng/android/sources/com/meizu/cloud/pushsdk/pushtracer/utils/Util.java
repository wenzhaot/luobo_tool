package com.meizu.cloud.pushsdk.pushtracer.utils;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.util.Base64;
import com.feng.car.utils.HttpConstant;
import com.stub.StubApp;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Util {
    private static final String TAG = Util.class.getSimpleName();

    public static JSONObject mapToJSONObject(Map map) {
        if (VERSION.SDK_INT >= 19) {
            return new JSONObject(map);
        }
        JSONObject retObject = new JSONObject();
        for (Entry entry : map.entrySet()) {
            try {
                retObject.put((String) entry.getKey(), getJsonSafeObject(entry.getValue()));
            } catch (JSONException e) {
                Logger.e(TAG, "Could not put key '%s' and value '%s' into new JSONObject: %s", key, value, e);
                e.printStackTrace();
            }
        }
        return retObject;
    }

    private static Object getJsonSafeObject(Object o) {
        if (VERSION.SDK_INT >= 19) {
            return o;
        }
        if (o == null) {
            return JSONObject.NULL;
        }
        if ((o instanceof JSONObject) || (o instanceof JSONArray)) {
            return o;
        }
        JSONArray retArray;
        if (o instanceof Collection) {
            retArray = new JSONArray();
            for (Object entry : (Collection) o) {
                retArray.put(getJsonSafeObject(entry));
            }
            return retArray;
        } else if (o.getClass().isArray()) {
            retArray = new JSONArray();
            int length = Array.getLength(o);
            for (int i = 0; i < length; i++) {
                retArray.put(getJsonSafeObject(Array.get(o, i)));
            }
            return retArray;
        } else if (o instanceof Map) {
            return mapToJSONObject((Map) o);
        } else {
            if ((o instanceof Boolean) || (o instanceof Byte) || (o instanceof Character) || (o instanceof Double) || (o instanceof Float) || (o instanceof Integer) || (o instanceof Long) || (o instanceof Short) || (o instanceof String)) {
                return o;
            }
            if (o.getClass().getPackage().getName().startsWith("java.")) {
                return o.toString();
            }
            return null;
        }
    }

    public static long getUTF8Length(String s) {
        long len = 0;
        int i = 0;
        while (i < s.length()) {
            char code = s.charAt(i);
            if (code <= 127) {
                len++;
            } else if (code <= 2047) {
                len += 2;
            } else if (code >= 55296 && code <= 57343) {
                len += 4;
                i++;
            } else if (code < 65535) {
                len += 3;
            } else {
                len += 4;
            }
            i++;
        }
        return len;
    }

    public static String base64Encode(String string) {
        return Base64.encodeToString(string.getBytes(), 2);
    }

    public static String getTimestamp() {
        return Long.toString(System.currentTimeMillis());
    }

    public static boolean isOnline(Context context) {
        Logger.i(TAG, "Checking tracker internet connectivity.", new Object[0]);
        try {
            boolean connected;
            NetworkInfo ni = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (ni == null || !ni.isConnected()) {
                connected = false;
            } else {
                connected = true;
            }
            Logger.d(TAG, "Tracker connection online: %s", Boolean.valueOf(connected));
            return connected;
        } catch (SecurityException e) {
            Logger.e(TAG, "Security exception checking connection: %s", e.toString());
            return true;
        }
    }

    public static String getCarrier(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
        if (telephonyManager != null) {
            return telephonyManager.getNetworkOperatorName();
        }
        return null;
    }

    public static Location getLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(1);
        criteria.setAccuracy(2);
        String provider = locationManager.getBestProvider(criteria, true);
        if (provider != null) {
            try {
                Logger.d(TAG, "Location found: %s", StubApp.mark(locationManager, provider));
                return StubApp.mark(locationManager, provider);
            } catch (SecurityException ex) {
                Logger.e(TAG, "Failed to retrieve location: %s", ex.toString());
                return null;
            }
        }
        Logger.e(TAG, "Location Manager provider is null.", new Object[0]);
        return null;
    }

    public static boolean isTimeInRange(long startTime, long checkTime, long range) {
        return startTime > checkTime - range;
    }

    public static String getEventId() {
        return UUID.randomUUID().toString();
    }
}
