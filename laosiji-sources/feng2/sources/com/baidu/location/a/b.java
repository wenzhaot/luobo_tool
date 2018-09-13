package com.baidu.location.a;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import com.baidu.android.bbalbs.common.util.CommonParam;
import com.baidu.location.BDLocation;
import com.baidu.location.Jni;
import com.baidu.location.LocationClientOption;
import com.baidu.location.h.f;
import com.baidu.location.h.k;
import com.baidu.platform.comapi.location.CoordinateType;
import com.stub.StubApp;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class b {
    private static Method g = null;
    private static Method h = null;
    private static Method i = null;
    private static Method j = null;
    private static Method k = null;
    private static Class<?> l = null;
    String a = null;
    String b = null;
    c c = new c();
    private Context d = null;
    private TelephonyManager e = null;
    private a f = new a();
    private WifiManager m = null;
    private d n = null;
    private String o = null;
    private LocationClientOption p;
    private b q;
    private String r = null;
    private String s = null;
    private String t = null;

    public interface b {
        void onReceiveLocation(BDLocation bDLocation);
    }

    private class a {
        public int a;
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public char g;

        private a() {
            this.a = -1;
            this.b = -1;
            this.c = -1;
            this.d = -1;
            this.e = Integer.MAX_VALUE;
            this.f = Integer.MAX_VALUE;
            this.g = 0;
        }

        private boolean d() {
            return this.a > -1 && this.b > 0;
        }

        public int a() {
            return (this.c <= 0 || !d()) ? 2 : (this.c == 460 || this.c == 454 || this.c == 455 || this.c == 466) ? 1 : 0;
        }

        public String b() {
            if (!d()) {
                return null;
            }
            StringBuffer stringBuffer = new StringBuffer(128);
            stringBuffer.append("&nw=");
            stringBuffer.append(this.g);
            stringBuffer.append(String.format(Locale.CHINA, "&cl=%d|%d|%d|%d", new Object[]{Integer.valueOf(this.c), Integer.valueOf(this.d), Integer.valueOf(this.a), Integer.valueOf(this.b)}));
            if (this.e < Integer.MAX_VALUE && this.f < Integer.MAX_VALUE) {
                stringBuffer.append(String.format(Locale.CHINA, "&cdmall=%.6f|%.6f", new Object[]{Double.valueOf(((double) this.f) / 14400.0d), Double.valueOf(((double) this.e) / 14400.0d)}));
            }
            return stringBuffer.toString();
        }

        public String c() {
            if (!d()) {
                return null;
            }
            StringBuffer stringBuffer = new StringBuffer(128);
            stringBuffer.append(this.b + 23);
            stringBuffer.append("H");
            stringBuffer.append(this.a + 45);
            stringBuffer.append("K");
            stringBuffer.append(this.d + 54);
            stringBuffer.append("Q");
            stringBuffer.append(this.c + 203);
            return stringBuffer.toString();
        }
    }

    class c extends f {
        String a;

        c() {
            this.a = null;
            this.k = new HashMap();
        }

        public void a() {
            this.h = k.c();
            if (!(b.this.s == null || b.this.t == null)) {
                this.a += String.format(Locale.CHINA, "&ki=%s&sn=%s", new Object[]{b.this.s, b.this.t});
            }
            String encodeTp4 = Jni.encodeTp4(this.a);
            this.a = null;
            this.k.put("bloc", encodeTp4);
            this.k.put("trtm", String.format(Locale.CHINA, "%d", new Object[]{Long.valueOf(System.currentTimeMillis())}));
        }

        public void a(String str) {
            this.a = str;
            c(k.f);
        }

        public void a(boolean z) {
            BDLocation bDLocation;
            if (z && this.j != null) {
                try {
                    try {
                        bDLocation = new BDLocation(this.j);
                    } catch (Exception e) {
                        bDLocation = new BDLocation();
                        bDLocation.setLocType(63);
                    }
                    if (bDLocation != null) {
                        if (bDLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                            bDLocation.setCoorType(b.this.p.coorType);
                            bDLocation.setLocationID(Jni.en1(b.this.a + ";" + b.this.b + ";" + bDLocation.getTime()));
                            b.this.q.onReceiveLocation(bDLocation);
                        }
                    }
                } catch (Exception e2) {
                }
            }
            if (this.k != null) {
                this.k.clear();
            }
        }
    }

    protected class d {
        public List<ScanResult> a = null;
        private long c = 0;

        public d(List<ScanResult> list) {
            this.a = list;
            this.c = System.currentTimeMillis();
            c();
        }

        private String b() {
            WifiInfo connectionInfo = b.this.m.getConnectionInfo();
            if (connectionInfo == null) {
                return null;
            }
            try {
                String bssid = connectionInfo.getBSSID();
                String replace = bssid != null ? bssid.replace(":", "") : null;
                return (replace == null || replace.length() == 12) ? new String(replace) : null;
            } catch (Exception e) {
                return null;
            }
        }

        private void c() {
            if (a() >= 1) {
                Object obj = 1;
                for (int size = this.a.size() - 1; size >= 1 && obj != null; size--) {
                    int i = 0;
                    obj = null;
                    while (i < size) {
                        Object obj2;
                        if (((ScanResult) this.a.get(i)).level < ((ScanResult) this.a.get(i + 1)).level) {
                            ScanResult scanResult = (ScanResult) this.a.get(i + 1);
                            this.a.set(i + 1, this.a.get(i));
                            this.a.set(i, scanResult);
                            obj2 = 1;
                        } else {
                            obj2 = obj;
                        }
                        i++;
                        obj = obj2;
                    }
                }
            }
        }

        public int a() {
            return this.a == null ? 0 : this.a.size();
        }

        public String a(int i) {
            if (a() < 2) {
                return null;
            }
            StringBuffer stringBuffer = new StringBuffer(512);
            int size = this.a.size();
            Object obj = 1;
            int i2 = 0;
            String b = b();
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            while (i5 < size) {
                int i6;
                if (((ScanResult) this.a.get(i5)).level == 0) {
                    i6 = i2;
                } else {
                    i3++;
                    if (obj != null) {
                        stringBuffer.append("&wf=");
                        obj = null;
                    } else {
                        stringBuffer.append("|");
                    }
                    String replace = ((ScanResult) this.a.get(i5)).BSSID.replace(":", "");
                    stringBuffer.append(replace);
                    if (b != null && replace.equals(b)) {
                        i4 = i3;
                    }
                    i6 = ((ScanResult) this.a.get(i5)).level;
                    if (i6 < 0) {
                        i6 = -i6;
                    }
                    stringBuffer.append(String.format(Locale.CHINA, ";%d;", new Object[]{Integer.valueOf(i6)}));
                    i6 = i2 + 1;
                    if (i6 > i) {
                        break;
                    }
                }
                i5++;
                i2 = i6;
            }
            if (i4 > 0) {
                stringBuffer.append("&wf_n=");
                stringBuffer.append(i4);
            }
            return obj != null ? null : stringBuffer.toString();
        }
    }

    public b(Context context, LocationClientOption locationClientOption, b bVar) {
        String deviceId;
        this.d = StubApp.getOrigApplicationContext(context.getApplicationContext());
        this.p = locationClientOption;
        this.q = bVar;
        this.a = this.d.getPackageName();
        this.b = null;
        try {
            this.e = (TelephonyManager) this.d.getSystemService("phone");
            deviceId = this.e.getDeviceId();
        } catch (Exception e) {
            deviceId = null;
        }
        try {
            this.b = CommonParam.a(this.d);
        } catch (Exception e2) {
            this.b = null;
        }
        if (this.b != null) {
            this.o = "&prod=" + this.p.prodName + ":" + this.a + "|&cu=" + this.b + "&coor=" + locationClientOption.getCoorType();
        } else {
            this.o = "&prod=" + this.p.prodName + ":" + this.a + "|&im=" + deviceId + "&coor=" + locationClientOption.getCoorType();
        }
        StringBuffer stringBuffer = new StringBuffer(256);
        stringBuffer.append("&fw=");
        stringBuffer.append("7.21");
        stringBuffer.append("&lt=1");
        stringBuffer.append("&mb=");
        stringBuffer.append(Build.MODEL);
        stringBuffer.append("&resid=");
        stringBuffer.append("12");
        if (locationClientOption.getAddrType() != null) {
        }
        if (locationClientOption.getAddrType() != null && locationClientOption.getAddrType().equals("all")) {
            this.o += "&addr=allj";
        }
        if (locationClientOption.isNeedAptag || locationClientOption.isNeedAptagd) {
            this.o += "&sema=";
            if (locationClientOption.isNeedAptag) {
                this.o += "aptag|";
            }
            if (locationClientOption.isNeedAptagd) {
                this.o += "aptagd|";
            }
            this.s = i.b(this.d);
            this.t = i.c(this.d);
        }
        stringBuffer.append("&first=1");
        stringBuffer.append("&os=A");
        stringBuffer.append(VERSION.SDK);
        this.o += stringBuffer.toString();
        this.m = (WifiManager) StubApp.getOrigApplicationContext(this.d.getApplicationContext()).getSystemService("wifi");
        deviceId = a();
        if (!TextUtils.isEmpty(deviceId)) {
            deviceId = deviceId.replace(":", "");
        }
        if (!(TextUtils.isEmpty(deviceId) || deviceId.equals("020000000000"))) {
            this.o += "&mac=" + deviceId;
        }
        b();
    }

    private String a(int i) {
        String b;
        String a;
        if (i < 3) {
            i = 3;
        }
        try {
            a(this.e.getCellLocation());
            b = this.f.b();
        } catch (Exception e) {
            b = null;
        }
        try {
            this.n = null;
            this.n = new d(this.m.getScanResults());
            a = this.n.a(i);
        } catch (Exception e2) {
            a = null;
        }
        if (b == null && a == null) {
            this.r = null;
            return null;
        }
        if (a != null) {
            b = b + a;
        }
        if (b == null) {
            return null;
        }
        this.r = b;
        if (this.o != null) {
            this.r += this.o;
        }
        return b + this.o;
    }

    private void a(CellLocation cellLocation) {
        int i = 0;
        if (cellLocation != null && this.e != null) {
            a aVar = new a();
            String networkOperator = this.e.getNetworkOperator();
            if (networkOperator != null && networkOperator.length() > 0) {
                try {
                    if (networkOperator.length() >= 3) {
                        int intValue = Integer.valueOf(networkOperator.substring(0, 3)).intValue();
                        if (intValue < 0) {
                            intValue = this.f.c;
                        }
                        aVar.c = intValue;
                    }
                    String substring = networkOperator.substring(3);
                    if (substring != null) {
                        char[] toCharArray = substring.toCharArray();
                        while (i < toCharArray.length && Character.isDigit(toCharArray[i])) {
                            i++;
                        }
                    }
                    i = Integer.valueOf(substring.substring(0, i)).intValue();
                    if (i < 0) {
                        i = this.f.d;
                    }
                    aVar.d = i;
                } catch (Exception e) {
                }
            }
            if (cellLocation instanceof GsmCellLocation) {
                aVar.a = ((GsmCellLocation) cellLocation).getLac();
                aVar.b = ((GsmCellLocation) cellLocation).getCid();
                aVar.g = 'g';
            } else if (cellLocation instanceof CdmaCellLocation) {
                aVar.g = 'c';
                if (l == null) {
                    try {
                        l = Class.forName("android.telephony.cdma.CdmaCellLocation");
                        g = l.getMethod("getBaseStationId", new Class[0]);
                        h = l.getMethod("getNetworkId", new Class[0]);
                        i = l.getMethod("getSystemId", new Class[0]);
                        j = l.getMethod("getBaseStationLatitude", new Class[0]);
                        k = l.getMethod("getBaseStationLongitude", new Class[0]);
                    } catch (Exception e2) {
                        l = null;
                        return;
                    }
                }
                if (l != null && l.isInstance(cellLocation)) {
                    try {
                        i = ((Integer) i.invoke(cellLocation, new Object[0])).intValue();
                        if (i < 0) {
                            i = this.f.d;
                        }
                        aVar.d = i;
                        aVar.b = ((Integer) g.invoke(cellLocation, new Object[0])).intValue();
                        aVar.a = ((Integer) h.invoke(cellLocation, new Object[0])).intValue();
                        Object invoke = j.invoke(cellLocation, new Object[0]);
                        if (((Integer) invoke).intValue() < Integer.MAX_VALUE) {
                            aVar.e = ((Integer) invoke).intValue();
                        }
                        invoke = k.invoke(cellLocation, new Object[0]);
                        if (((Integer) invoke).intValue() < Integer.MAX_VALUE) {
                            aVar.f = ((Integer) invoke).intValue();
                        }
                    } catch (Exception e3) {
                        return;
                    }
                }
            }
            if (aVar.d()) {
                this.f = aVar;
            } else {
                this.f = null;
            }
        }
    }

    public String a() {
        try {
            WifiInfo connectionInfo = this.m.getConnectionInfo();
            return connectionInfo != null ? connectionInfo.getMacAddress() : null;
        } catch (Exception e) {
            return null;
        } catch (Error e2) {
            return null;
        }
    }

    public String b() {
        try {
            return a(15);
        } catch (Exception e) {
            return null;
        }
    }

    public void c() {
        if (this.r != null && this.f != null && this.f.a() == 1) {
            BDLocation bDLocation;
            if (this.m == null || this.p.scanSpan < 1000 || this.p.getAddrType().equals("all") || this.p.isNeedAptag || this.p.isNeedAptagd) {
                bDLocation = null;
            } else {
                try {
                    bDLocation = com.baidu.location.e.a.a().a(this.f.c(), this.m.getScanResults(), false);
                    if (!this.p.coorType.equals(CoordinateType.GCJ02)) {
                        double longitude = bDLocation.getLongitude();
                        double latitude = bDLocation.getLatitude();
                        if (!(longitude == Double.MIN_VALUE || latitude == Double.MIN_VALUE)) {
                            double[] coorEncrypt = Jni.coorEncrypt(longitude, latitude, this.p.coorType);
                            bDLocation.setLongitude(coorEncrypt[0]);
                            bDLocation.setLatitude(coorEncrypt[1]);
                            bDLocation.setCoorType(this.p.coorType);
                        }
                    }
                    if (bDLocation.getLocType() == 66) {
                        this.q.onReceiveLocation(bDLocation);
                    }
                } catch (Exception e) {
                    bDLocation = null;
                }
            }
            if (bDLocation == null) {
                this.c.a(this.r);
            }
        }
    }
}
