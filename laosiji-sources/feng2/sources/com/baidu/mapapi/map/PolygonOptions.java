package com.baidu.mapapi.map;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import com.baidu.mapapi.model.LatLng;
import java.util.List;

public final class PolygonOptions extends OverlayOptions {
    int a;
    boolean b = true;
    Bundle c;
    private Stroke d;
    private int e = ViewCompat.MEASURED_STATE_MASK;
    private List<LatLng> f;

    Overlay a() {
        Overlay polygon = new Polygon();
        polygon.r = this.b;
        polygon.q = this.a;
        polygon.s = this.c;
        if (this.f == null || this.f.size() < 2) {
            throw new IllegalStateException("when you add polyline, you must at least supply 2 points");
        }
        polygon.c = this.f;
        polygon.b = this.e;
        polygon.a = this.d;
        return polygon;
    }

    public PolygonOptions extraInfo(Bundle bundle) {
        this.c = bundle;
        return this;
    }

    public PolygonOptions fillColor(int i) {
        this.e = i;
        return this;
    }

    public Bundle getExtraInfo() {
        return this.c;
    }

    public int getFillColor() {
        return this.e;
    }

    public List<LatLng> getPoints() {
        return this.f;
    }

    public Stroke getStroke() {
        return this.d;
    }

    public int getZIndex() {
        return this.a;
    }

    public boolean isVisible() {
        return this.b;
    }

    public PolygonOptions points(List<LatLng> list) {
        if (list == null) {
            throw new IllegalArgumentException("points list can not be null");
        } else if (list.size() <= 2) {
            throw new IllegalArgumentException("points count can not less than three");
        } else if (list.contains(null)) {
            throw new IllegalArgumentException("points list can not contains null");
        } else {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < list.size()) {
                    i = i2 + 1;
                    while (true) {
                        int i3 = i;
                        if (i3 >= list.size()) {
                            break;
                        } else if (((LatLng) list.get(i2)) == ((LatLng) list.get(i3))) {
                            throw new IllegalArgumentException("points list can not has same points");
                        } else {
                            i = i3 + 1;
                        }
                    }
                } else {
                    this.f = list;
                    return this;
                }
                i = i2 + 1;
            }
        }
    }

    public PolygonOptions stroke(Stroke stroke) {
        this.d = stroke;
        return this;
    }

    public PolygonOptions visible(boolean z) {
        this.b = z;
        return this;
    }

    public PolygonOptions zIndex(int i) {
        this.a = i;
        return this;
    }
}
