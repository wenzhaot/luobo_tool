package com.huawei.android.pushselfshow.richpush.html.a;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.richpush.html.a.a.a;
import com.huawei.android.pushselfshow.richpush.html.a.a.b;
import com.huawei.android.pushselfshow.richpush.html.api.NativeToJsMessageQueue;
import com.huawei.android.pushselfshow.richpush.html.api.d;
import com.stub.StubApp;
import org.json.JSONException;
import org.json.JSONObject;

public class j implements h {
    public boolean a = false;
    public boolean b = false;
    public long c = 2000;
    public int d = 0;
    public String e;
    public NativeToJsMessageQueue f;
    private a g;
    private b h;
    private LocationManager i;
    private Activity j = null;

    public j(Activity activity) {
        try {
            c.e("PushSelfShowLog", "init GeoBroker");
            this.j = activity;
        } catch (Throwable e) {
            c.d("PushSelfShowLog", "init GeoBroker error ", e);
        }
    }

    private void a() {
        d();
    }

    private void e() {
        Location mark;
        if (this.a) {
            if (this.g != null) {
                this.g.a(this.c, (float) this.d);
            }
            if (this.i != null) {
                mark = StubApp.mark(this.i, "gps");
                if (mark != null) {
                    b(mark);
                    return;
                }
                return;
            }
            return;
        }
        if (this.h != null) {
            this.h.a(this.c, (float) this.d);
        }
        if (this.i != null) {
            mark = StubApp.mark(this.i, "network");
            if (mark == null) {
                mark = StubApp.mark(this.i, "gps");
            }
            if (mark != null) {
                b(mark);
            }
        }
    }

    public String a(String str, JSONObject jSONObject) {
        return null;
    }

    public JSONObject a(Location location) {
        Object obj = null;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("latitude", location.getLatitude());
            jSONObject.put("longitude", location.getLongitude());
            jSONObject.put("altitude", location.hasAltitude() ? Double.valueOf(location.getAltitude()) : null);
            jSONObject.put("accuracy", (double) location.getAccuracy());
            String str = "heading";
            if (location.hasBearing() && location.hasSpeed()) {
                obj = Float.valueOf(location.getBearing());
            }
            jSONObject.put(str, obj);
            jSONObject.put("velocity", (double) location.getSpeed());
            jSONObject.put("timestamp", location.getTime());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public void a(int i, int i2, Intent intent) {
    }

    public void a(NativeToJsMessageQueue nativeToJsMessageQueue, String str, String str2, JSONObject jSONObject) {
        try {
            c.e("PushSelfShowLog", "run into geo broker exec");
            d();
            this.i = (LocationManager) this.j.getSystemService("location");
            this.h = new b(this.i, this);
            this.g = new a(this.i, this);
            this.e = str2;
            if (nativeToJsMessageQueue == null) {
                c.a("PushSelfShowLog", "jsMessageQueue is null while run into App exec");
                return;
            }
            this.f = nativeToJsMessageQueue;
            if ("getLocation".equals(str)) {
                try {
                    if (jSONObject.has("useGps")) {
                        this.a = jSONObject.getBoolean("useGps");
                    }
                    if (jSONObject.has("keepLoc")) {
                        this.b = jSONObject.getBoolean("keepLoc");
                        if (this.b) {
                            if (jSONObject.has("minTime")) {
                                this.c = jSONObject.getLong("minTime");
                            }
                            if (jSONObject.has("minDistance")) {
                                this.d = jSONObject.getInt("minDistance");
                            }
                        }
                    }
                } catch (JSONException e) {
                    a(d.a.JSON_EXCEPTION);
                }
                e();
            } else if ("clearWatch".equals(str)) {
                c.e("PushSelfShowLog", "call method clearWatch");
                a();
            } else {
                a(d.a.METHOD_NOT_FOUND_EXCEPTION);
            }
        } catch (Throwable e2) {
            c.d("PushSelfShowLog", "run into geo broker exec error ", e2);
        }
    }

    public void a(d.a aVar) {
        c.a("PushSelfShowLog", "geo broker fail ,reason is %s", d.c()[aVar.ordinal()]);
        if (this.f != null) {
            this.f.a(this.e, aVar, "error", null);
        }
    }

    public void b() {
    }

    public void b(Location location) {
        if (this.f != null) {
            this.f.a(this.e, d.a.OK, "success", a(location));
        }
    }

    public void c() {
        d();
    }

    public void d() {
        c.e("PushSelfShowLog", "call geo broker reset");
        try {
            if (this.h != null) {
                this.h.a();
                this.h = null;
            }
            if (this.g != null) {
                this.g.a();
                this.g = null;
            }
            this.i = null;
            this.a = false;
            this.b = false;
            this.c = 2000;
            this.d = 0;
            this.e = null;
            this.f = null;
        } catch (Throwable e) {
            c.d("PushSelfShowLog", "call GeoBroker reset error", e);
        }
    }
}
