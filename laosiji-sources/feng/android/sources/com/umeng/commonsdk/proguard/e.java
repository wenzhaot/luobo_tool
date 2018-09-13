package com.umeng.commonsdk.proguard;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.text.TextUtils;
import com.umeng.commonsdk.internal.a;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: UMSysLocationCache */
public class e {
    public static final String a = "lng";
    public static final String b = "lat";
    public static final String c = "ts";
    public static final long d = 30000;
    public static final int e = 200;
    private static final String f = "UMSysLocationCache";
    private static boolean g = true;

    public static void a(final Context context) {
        com.umeng.commonsdk.statistics.common.e.a(f, "begin location");
        if (context != null) {
            try {
                new Thread(new Runnable() {
                    public void run() {
                        while (e.g) {
                            try {
                                JSONArray b = e.b(context);
                                if (b == null || b.length() < 200) {
                                    com.umeng.commonsdk.statistics.common.e.a(e.f, "location status is ok, time is " + System.currentTimeMillis());
                                    final d dVar = new d(context);
                                    dVar.a(new f() {
                                        public void a(Location location) {
                                            if (location != null) {
                                                double longitude = location.getLongitude();
                                                double latitude = location.getLatitude();
                                                float accuracy = location.getAccuracy();
                                                double altitude = location.getAltitude();
                                                com.umeng.commonsdk.statistics.common.e.a(e.f, "lon is " + longitude + ", lat is " + latitude + ", acc is " + accuracy + ", alt is " + altitude);
                                                if (!(longitude == 0.0d || latitude == 0.0d)) {
                                                    long time = location.getTime();
                                                    JSONObject jSONObject = new JSONObject();
                                                    try {
                                                        jSONObject.put("lng", longitude);
                                                        jSONObject.put("lat", latitude);
                                                        jSONObject.put("ts", time);
                                                        jSONObject.put("acc", (double) accuracy);
                                                        jSONObject.put("alt", altitude);
                                                    } catch (JSONException e) {
                                                        com.umeng.commonsdk.statistics.common.e.a(e.f, "e is " + e);
                                                    } catch (Throwable th) {
                                                        com.umeng.commonsdk.statistics.common.e.a(e.f, "" + th.getMessage());
                                                    }
                                                    com.umeng.commonsdk.statistics.common.e.a(e.f, "locationJSONObject is " + jSONObject.toString());
                                                    SharedPreferences sharedPreferences = context.getSharedPreferences(a.p, 0);
                                                    if (sharedPreferences != null) {
                                                        Object string = sharedPreferences.getString(a.r, "");
                                                        Object string2 = sharedPreferences.getString(a.s, "");
                                                        com.umeng.commonsdk.statistics.common.e.a(e.f, "--->>> get lon is " + string + ", lat is " + string2);
                                                        if (TextUtils.isEmpty(string) || Double.parseDouble(string) != longitude || TextUtils.isEmpty(string2) || Double.parseDouble(string2) != latitude) {
                                                            JSONArray b = e.b(context);
                                                            if (b == null) {
                                                                b = new JSONArray();
                                                            }
                                                            b.put(jSONObject);
                                                            Editor edit = sharedPreferences.edit();
                                                            edit.putString(a.r, String.valueOf(longitude));
                                                            edit.putString(a.s, String.valueOf(latitude));
                                                            edit.putString(a.q, b.toString());
                                                            edit.commit();
                                                            com.umeng.commonsdk.statistics.common.e.a(e.f, "location put is ok~~");
                                                        } else {
                                                            com.umeng.commonsdk.statistics.common.e.a(e.f, "location same");
                                                        }
                                                    }
                                                }
                                            }
                                            dVar.a();
                                        }
                                    });
                                    try {
                                        Thread.sleep(30000);
                                    } catch (Exception e) {
                                    }
                                } else {
                                    e.g = false;
                                    return;
                                }
                            } catch (Throwable th) {
                                return;
                            }
                        }
                    }
                }).start();
            } catch (Exception e) {
            }
        }
    }

    public static JSONArray b(Context context) {
        JSONArray jSONArray = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(a.p, 0);
        if (sharedPreferences != null) {
            try {
                JSONArray jSONArray2;
                Object string = sharedPreferences.getString(a.q, "");
                if (TextUtils.isEmpty(string)) {
                    jSONArray2 = null;
                } else {
                    jSONArray2 = new JSONArray(string);
                }
                jSONArray = jSONArray2;
            } catch (JSONException e) {
                com.umeng.commonsdk.statistics.common.e.a(f, "e is " + e);
            } catch (Throwable th) {
                com.umeng.commonsdk.statistics.common.e.a(f, "e is " + th);
            }
            if (jSONArray != null) {
                com.umeng.commonsdk.statistics.common.e.a(f, "get json str is " + jSONArray.toString());
            }
        }
        return jSONArray;
    }

    public static void c(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(a.p, 0);
            if (sharedPreferences != null) {
                Editor edit = sharedPreferences.edit();
                edit.putString(a.q, "");
                edit.commit();
                com.umeng.commonsdk.statistics.common.e.a(f, "delete is ok~~");
            }
        } catch (Throwable th) {
        }
    }
}
