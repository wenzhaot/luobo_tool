package com.baidu.location.c;

import android.location.Location;
import java.util.ArrayList;
import java.util.List;

public class i {
    private List<Location> a;
    private String b;
    private Location c = null;

    i(String str, Location[] locationArr) {
        if (locationArr != null && locationArr.length > 0) {
            a(locationArr);
            this.b = str;
        }
    }

    private void a(Location[] locationArr) {
        double d = 0.0d;
        if (locationArr != null && locationArr.length > 0) {
            if (this.a == null) {
                this.a = new ArrayList();
            }
            double d2 = 0.0d;
            for (int i = 0; i < locationArr.length; i++) {
                d2 += locationArr[i].getLatitude();
                d += locationArr[i].getLongitude();
                this.a.add(locationArr[i]);
            }
            if (this.c == null) {
                this.c = new Location("gps");
                this.c.setLatitude(d2 / ((double) locationArr.length));
                this.c.setLongitude(d / ((double) locationArr.length));
            }
        }
    }

    public String a() {
        return this.b;
    }

    public boolean a(double d, double d2) {
        boolean z = false;
        if (this.a == null) {
            return false;
        }
        int size = this.a.size();
        int i = (int) (((double) 1000000) * d2);
        int i2 = (int) (((double) 1000000) * d);
        int i3 = 0;
        int i4 = size - 1;
        while (i3 < size) {
            int longitude = (int) (((Location) this.a.get(i3)).getLongitude() * ((double) 1000000));
            int latitude = (int) (((Location) this.a.get(i3)).getLatitude() * ((double) 1000000));
            int longitude2 = (int) (((Location) this.a.get(i4)).getLongitude() * ((double) 1000000));
            int latitude2 = (int) (((Location) this.a.get(i4)).getLatitude() * ((double) 1000000));
            if ((i == longitude && i2 == latitude) || (i == longitude2 && i2 == latitude2)) {
                return true;
            }
            boolean z2;
            if ((latitude < i2 && latitude2 >= i2) || (latitude >= i2 && latitude2 < i2)) {
                latitude2 = (((i2 - latitude) * (longitude2 - longitude)) / (latitude2 - latitude)) + longitude;
                if (latitude2 == i) {
                    return true;
                }
                if (latitude2 > i) {
                    z2 = !z;
                    i4 = i3;
                    i3++;
                    z = z2;
                }
            }
            z2 = z;
            i4 = i3;
            i3++;
            z = z2;
        }
        return z;
    }
}
