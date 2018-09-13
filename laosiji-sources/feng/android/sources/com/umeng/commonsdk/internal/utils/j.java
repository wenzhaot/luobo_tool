package com.umeng.commonsdk.internal.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import com.stub.StubApp;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.framework.b;
import com.umeng.commonsdk.proguard.g;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.e;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: SystemLayerUtil */
public class j {
    private static final String a = "info";
    private static final String b = "stat";
    private static boolean c = false;
    private static HandlerThread d = null;
    private static Context e = null;
    private static int f = 0;
    private static int g = 0;
    private static int h = 0;
    private static int i = 1;
    private static long j = 0;
    private static long k = 0;
    private static final int l = 40;
    private static final int m = 50000;
    private static SensorManager n;
    private static ArrayList<float[]> o = new ArrayList();
    private static SensorEventListener p = new SensorEventListener() {
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (j.g < 15) {
                j.c();
                return;
            }
            if (j.f < 20) {
                j.e();
                j.o.add(sensorEvent.values.clone());
            }
            if (j.f == 20) {
                j.e();
                if (j.i == 1) {
                    j.j = System.currentTimeMillis();
                }
                if (j.i == 2) {
                    j.k = System.currentTimeMillis();
                }
                j.h();
                j.l();
            }
        }

        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

    /* compiled from: SystemLayerUtil */
    public static class a {
        public int a;
        public int b;
        public long c;
    }

    static /* synthetic */ int c() {
        int i = g;
        g = i + 1;
        return i;
    }

    static /* synthetic */ int e() {
        int i = f;
        f = i + 1;
        return i;
    }

    static /* synthetic */ int h() {
        int i = i;
        i = i + 1;
        return i;
    }

    public static List<Sensor> a(Context context) {
        if (context == null) {
            return null;
        }
        return ((SensorManager) context.getSystemService(g.aa)).getSensorList(-1);
    }

    public static void b(Context context) {
        if (context != null && !a()) {
            c = true;
            e = StubApp.getOrigApplicationContext(context.getApplicationContext());
            String a = b.a(context);
            String packageName = context.getPackageName();
            if (a != null && a.equals(packageName)) {
                n = (SensorManager) context.getSystemService(g.aa);
                final Sensor defaultSensor = n.getDefaultSensor(4);
                final Sensor defaultSensor2 = n.getDefaultSensor(1);
                if (defaultSensor != null) {
                    h = 4;
                    n.registerListener(p, defaultSensor, 50000);
                } else if (defaultSensor2 != null) {
                    h = 1;
                    n.registerListener(p, defaultSensor2, 50000);
                }
                int nextInt = (new Random().nextInt(3) * 1000) + 4000;
                d = new HandlerThread("sensor_thread");
                d.start();
                new Handler(d.getLooper()).postDelayed(new Runnable() {
                    public void run() {
                        try {
                            j.f = 0;
                            if (defaultSensor != null) {
                                j.n.registerListener(j.p, defaultSensor, 50000);
                            } else if (defaultSensor2 != null) {
                                j.n.registerListener(j.p, defaultSensor2, 50000);
                            }
                        } catch (Exception e) {
                            e.c("sensor exception");
                        }
                    }
                }, (long) nextInt);
            }
        }
    }

    private static void l() {
        if (n != null) {
            n.unregisterListener(p);
        }
        if (o.size() == 40) {
            f(e);
            if (o != null) {
                o.clear();
            }
            if (d != null) {
                d.quit();
                d = null;
            }
            e = null;
            c = false;
        }
    }

    public static JSONArray c(Context context) {
        SharedPreferences sharedPreferences = StubApp.getOrigApplicationContext(context.getApplicationContext()).getSharedPreferences("info", 0);
        if (sharedPreferences != null) {
            String string = sharedPreferences.getString(b, null);
            if (string != null) {
                try {
                    return new JSONArray(string);
                } catch (JSONException e) {
                    return null;
                }
            }
        }
        return null;
    }

    private static void f(Context context) {
        if (context != null) {
            try {
                JSONArray jSONArray = new JSONArray();
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 < 2) {
                        int i3;
                        int i4;
                        JSONObject jSONObject = new JSONObject();
                        JSONArray jSONArray2 = new JSONArray();
                        if (i2 == 1) {
                            i3 = 20;
                            i4 = 40;
                        } else {
                            i3 = 0;
                            i4 = 20;
                        }
                        while (i3 < i4) {
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put("x", (double) ((float[]) o.get(i3))[0]);
                            jSONObject2.put("y", (double) ((float[]) o.get(i3))[1]);
                            jSONObject2.put("z", (double) ((float[]) o.get(i3))[2]);
                            jSONArray2.put(jSONObject2);
                            i3++;
                        }
                        if (h == 4) {
                            jSONObject.put("g", jSONArray2);
                        } else if (h == 1) {
                            jSONObject.put(g.al, jSONArray2);
                        }
                        if (i2 == 0) {
                            jSONObject.put("ts", j);
                        } else {
                            jSONObject.put("ts", k);
                        }
                        jSONArray.put(jSONObject);
                        UMWorkDispatch.sendEvent(context, com.umeng.commonsdk.internal.a.l, com.umeng.commonsdk.internal.b.a(context).a(), jSONArray.toString());
                        i = i2 + 1;
                    } else {
                        return;
                    }
                }
            } catch (Throwable e) {
                com.umeng.commonsdk.proguard.b.a(context, e);
            }
        }
    }

    public static void d(Context context) {
        if (context != null) {
            StubApp.getOrigApplicationContext(context.getApplicationContext()).getSharedPreferences("info", 0).edit().remove(b).commit();
        }
    }

    public static synchronized boolean a() {
        boolean z;
        synchronized (j.class) {
            z = c;
        }
        return z;
    }

    public static List<a> e(Context context) {
        if (context == null || !DeviceConfig.checkPermission(context, "android.permission.CAMERA")) {
            return null;
        }
        List<a> arrayList = new ArrayList();
        try {
            if (VERSION.SDK_INT >= 21) {
                CameraManager cameraManager = (CameraManager) context.getSystemService("camera");
                String[] cameraIdList = cameraManager.getCameraIdList();
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 >= cameraIdList.length) {
                        break;
                    }
                    Size size = (Size) cameraManager.getCameraCharacteristics(cameraIdList[i2]).get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE);
                    if (size != null) {
                        a aVar = new a();
                        aVar.a = size.getWidth();
                        aVar.b = size.getHeight();
                        aVar.c = System.currentTimeMillis();
                        arrayList.add(aVar);
                    }
                    i = i2 + 1;
                }
            }
        } catch (Exception e) {
            e.c("camera access exception");
        }
        return arrayList;
    }
}
