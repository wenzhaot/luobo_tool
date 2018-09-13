package com.baidu.mapapi.map;

import android.os.Bundle;
import android.util.Log;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.h;
import java.util.List;

public final class Polyline extends Overlay {
    int a;
    List<LatLng> b;
    int[] c;
    int[] d;
    int e;
    boolean f;
    boolean g;
    boolean h;
    BitmapDescriptor i;
    List<BitmapDescriptor> j;

    Polyline() {
        this.g = false;
        this.h = true;
        this.type = h.polyline;
    }

    private Bundle a(boolean z) {
        return z ? BitmapDescriptorFactory.fromAsset("lineDashTexture.png").b() : this.i.b();
    }

    static void a(int[] iArr, Bundle bundle) {
        if (iArr != null && iArr.length > 0) {
            bundle.putIntArray("traffic_array", iArr);
        }
    }

    private Bundle b(boolean z) {
        int i = 0;
        if (z) {
            Bundle bundle = new Bundle();
            bundle.putInt("total", 1);
            bundle.putBundle("texture_0", BitmapDescriptorFactory.fromAsset("lineDashTexture.png").b());
            return bundle;
        }
        Bundle bundle2 = new Bundle();
        int i2 = 0;
        while (true) {
            int i3 = i;
            if (i2 < this.j.size()) {
                if (this.j.get(i2) != null) {
                    bundle2.putBundle("texture_" + String.valueOf(i3), ((BitmapDescriptor) this.j.get(i2)).b());
                    i3++;
                }
                i = i2 + 1;
            } else {
                bundle2.putInt("total", i3);
                return bundle2;
            }
        }
    }

    static void b(int[] iArr, Bundle bundle) {
        if (iArr != null && iArr.length > 0) {
            bundle.putIntArray("color_array", iArr);
            bundle.putInt("total", 1);
        }
    }

    Bundle a(Bundle bundle) {
        int i = 1;
        super.a(bundle);
        GeoPoint ll2mc = CoordUtil.ll2mc((LatLng) this.b.get(0));
        bundle.putDouble("location_x", ll2mc.getLongitudeE6());
        bundle.putDouble("location_y", ll2mc.getLatitudeE6());
        bundle.putInt("width", this.e);
        Overlay.a(this.b, bundle);
        Overlay.a(this.a, bundle);
        a(this.c, bundle);
        b(this.d, bundle);
        if (this.c != null && this.c.length > 0 && this.c.length > this.b.size() - 1) {
            Log.e("baidumapsdk", "the size of textureIndexs is larger than the size of points");
        }
        if (this.f) {
            bundle.putInt("dotline", 1);
        } else {
            bundle.putInt("dotline", 0);
        }
        bundle.putInt("focus", this.g ? 1 : 0);
        try {
            if (this.i != null) {
                bundle.putInt("custom", 1);
                bundle.putBundle("image_info", a(false));
            } else {
                if (this.f) {
                    bundle.putBundle("image_info", a(true));
                }
                bundle.putInt("custom", 0);
            }
            if (this.j != null) {
                bundle.putInt("customlist", 1);
                bundle.putBundle("image_info_list", b(false));
            } else {
                if (this.f && ((this.c != null && this.c.length > 0) || (this.d != null && this.d.length > 0))) {
                    bundle.putBundle("image_info_list", b(true));
                }
                bundle.putInt("customlist", 0);
            }
            String str = "keep";
            if (!this.h) {
                i = 0;
            }
            bundle.putInt(str, i);
        } catch (Exception e) {
            Log.e("baidumapsdk", "load texture resource failed!");
            bundle.putInt("dotline", 0);
        }
        return bundle;
    }

    public int getColor() {
        return this.a;
    }

    public List<LatLng> getPoints() {
        return this.b;
    }

    public int getWidth() {
        return this.e;
    }

    public boolean isDottedLine() {
        return this.f;
    }

    public boolean isFocus() {
        return this.g;
    }

    public void setColor(int i) {
        this.a = i;
        this.listener.b(this);
    }

    public void setDottedLine(boolean z) {
        this.f = z;
        this.listener.b(this);
    }

    public void setFocus(boolean z) {
        this.g = z;
        this.listener.b(this);
    }

    public void setPoints(List<LatLng> list) {
        if (list == null) {
            throw new IllegalArgumentException("points list can not be null");
        } else if (list.size() < 2) {
            throw new IllegalArgumentException("points count can not less than 2 or more than 10000");
        } else if (list.contains(null)) {
            throw new IllegalArgumentException("points list can not contains null");
        } else {
            this.b = list;
            this.listener.b(this);
        }
    }

    public void setWidth(int i) {
        if (i > 0) {
            this.e = i;
            this.listener.b(this);
        }
    }
}
