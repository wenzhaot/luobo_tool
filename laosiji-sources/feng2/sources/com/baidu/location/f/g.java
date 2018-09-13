package com.baidu.location.f;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Handler;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.baidu.location.a.j;
import com.baidu.location.a.r;
import com.baidu.location.a.s;
import com.baidu.location.c.e;
import com.baidu.location.f;
import com.stub.StubApp;
import java.util.LinkedList;
import java.util.List;

public class g {
    public static long a = 0;
    private static g b = null;
    private WifiManager c = null;
    private a d = null;
    private f e = null;
    private long f = 0;
    private long g = 0;
    private boolean h = false;
    private Handler i = new Handler();
    private long j = 0;
    private long k = 0;

    private class a extends BroadcastReceiver {
        private long b;
        private boolean c;

        private a() {
            this.b = 0;
            this.c = false;
        }

        public void onReceive(Context context, Intent intent) {
            if (context != null) {
                String action = intent.getAction();
                if (action.equals("android.net.wifi.SCAN_RESULTS")) {
                    g.a = System.currentTimeMillis() / 1000;
                    g.this.t();
                    j.c().i();
                    if (e.a().f()) {
                        e.a().c.obtainMessage(41).sendToTarget();
                    }
                    g.this.r();
                    if (System.currentTimeMillis() - r.b() <= 5000) {
                        s.a(r.c(), g.this.o(), r.d(), r.a());
                    }
                } else if (action.equals("android.net.wifi.STATE_CHANGE") && ((NetworkInfo) intent.getParcelableExtra("networkInfo")).getState().equals(State.CONNECTED) && System.currentTimeMillis() - this.b >= 5000) {
                    this.b = System.currentTimeMillis();
                    if (!this.c) {
                        this.c = true;
                    }
                }
            }
        }
    }

    private g() {
    }

    public static synchronized g a() {
        g gVar;
        synchronized (g.class) {
            if (b == null) {
                b = new g();
            }
            gVar = b;
        }
        return gVar;
    }

    private String a(long j) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.valueOf((int) (j & 255)));
        stringBuffer.append('.');
        stringBuffer.append(String.valueOf((int) ((j >> 8) & 255)));
        stringBuffer.append('.');
        stringBuffer.append(String.valueOf((int) ((j >> 16) & 255)));
        stringBuffer.append('.');
        stringBuffer.append(String.valueOf((int) ((j >> 24) & 255)));
        return stringBuffer.toString();
    }

    public static boolean a(f fVar, f fVar2) {
        return a(fVar, fVar2, 0.7f);
    }

    public static boolean a(f fVar, f fVar2, float f) {
        if (fVar == null || fVar2 == null) {
            return false;
        }
        List list = fVar.a;
        List list2 = fVar2.a;
        if (list == list2) {
            return true;
        }
        if (list == null || list2 == null) {
            return false;
        }
        List linkedList = new LinkedList();
        int size = list.size();
        int size2 = list2.size();
        if (size == 0 && size2 == 0) {
            return true;
        }
        if (size == 0 || size2 == 0) {
            return false;
        }
        int i = 0;
        long j = 0;
        int i2 = 0;
        while (i2 < size) {
            int i3;
            long j2;
            String str = ((ScanResult) list.get(i2)).BSSID;
            if (str == null) {
                long j3 = j;
                i3 = i;
                j2 = j3;
            } else {
                int i4;
                int i5 = 0;
                while (i5 < size2) {
                    if (str.equals(((ScanResult) list2.get(i5)).BSSID)) {
                        i4 = i + 1;
                        j += (long) ((((ScanResult) list.get(i2)).level - ((ScanResult) list2.get(i5)).level) * (((ScanResult) list.get(i2)).level - ((ScanResult) list2.get(i5)).level));
                        break;
                    }
                    i5++;
                }
                i4 = i;
                if (i5 == size2) {
                    linkedList.add(list.get(i2));
                    j2 = ((long) ((((ScanResult) list.get(i2)).level + 100) * (((ScanResult) list.get(i2)).level + 100))) + j;
                    i3 = i4;
                } else {
                    j2 = j;
                    i3 = i4;
                }
            }
            i2++;
            i = i3;
            j = j2;
        }
        double sqrt = Math.sqrt((double) j) / ((double) size);
        return ((float) i) >= ((float) size) * f;
    }

    public static boolean j() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) f.getServiceContext().getSystemService("connectivity")).getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.getType() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    private void t() {
        if (this.c != null) {
            try {
                List scanResults = this.c.getScanResults();
                if (scanResults != null) {
                    f fVar = new f(scanResults, System.currentTimeMillis());
                    if (this.e == null || !fVar.a(this.e)) {
                        this.e = fVar;
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public void b() {
        this.j = 0;
    }

    public synchronized void c() {
        if (!this.h) {
            if (f.isServing) {
                this.c = (WifiManager) StubApp.getOrigApplicationContext(f.getServiceContext().getApplicationContext()).getSystemService("wifi");
                this.d = new a();
                try {
                    f.getServiceContext().registerReceiver(this.d, new IntentFilter("android.net.wifi.SCAN_RESULTS"));
                } catch (Exception e) {
                }
                this.h = true;
            }
        }
    }

    public List<WifiConfiguration> d() {
        try {
            return this.c != null ? this.c.getConfiguredNetworks() : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public synchronized void e() {
        if (this.h) {
            try {
                f.getServiceContext().unregisterReceiver(this.d);
                a = 0;
            } catch (Exception e) {
            }
            this.d = null;
            this.c = null;
            this.h = false;
        }
    }

    public boolean f() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.g > 0 && currentTimeMillis - this.g <= 5000) {
            return false;
        }
        this.g = currentTimeMillis;
        b();
        return g();
    }

    public boolean g() {
        if (this.c == null) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.f > 0) {
            if (currentTimeMillis - this.f <= this.j + 5000 || currentTimeMillis - (a * 1000) <= this.j + 5000) {
                return false;
            }
            if (j() && currentTimeMillis - this.f <= 10000 + this.j) {
                return false;
            }
        }
        return i();
    }

    @SuppressLint({"NewApi"})
    public String h() {
        String str = "";
        if (this.c == null) {
            return str;
        }
        try {
            return (this.c.isWifiEnabled() || (VERSION.SDK_INT > 17 && this.c.isScanAlwaysAvailable())) ? "&wifio=1" : str;
        } catch (NoSuchMethodError e) {
            return str;
        } catch (Exception e2) {
            return str;
        }
    }

    @SuppressLint({"NewApi"})
    public boolean i() {
        long currentTimeMillis = System.currentTimeMillis() - this.k;
        if (currentTimeMillis >= 0 && currentTimeMillis <= 2000) {
            return false;
        }
        this.k = System.currentTimeMillis();
        try {
            if (!this.c.isWifiEnabled() && (VERSION.SDK_INT <= 17 || !this.c.isScanAlwaysAvailable())) {
                return false;
            }
            this.c.startScan();
            this.f = System.currentTimeMillis();
            return true;
        } catch (NoSuchMethodError e) {
            return false;
        } catch (Exception e2) {
            return false;
        }
    }

    @SuppressLint({"NewApi"})
    public boolean k() {
        try {
            if ((!this.c.isWifiEnabled() && (VERSION.SDK_INT <= 17 || !this.c.isScanAlwaysAvailable())) || j()) {
                return false;
            }
            f fVar = new f(this.c.getScanResults(), 0);
            return fVar != null && fVar.e();
        } catch (NoSuchMethodError e) {
            return false;
        } catch (Exception e2) {
            return false;
        }
    }

    public WifiInfo l() {
        if (this.c == null) {
            return null;
        }
        try {
            WifiInfo connectionInfo = this.c.getConnectionInfo();
            if (connectionInfo == null || connectionInfo.getBSSID() == null || connectionInfo.getRssi() <= -100) {
                return null;
            }
            String bssid = connectionInfo.getBSSID();
            if (bssid != null) {
                bssid = bssid.replace(":", "");
                if ("000000000000".equals(bssid) || "".equals(bssid)) {
                    return null;
                }
            }
            return connectionInfo;
        } catch (Exception e) {
            return null;
        } catch (Error e2) {
            return null;
        }
    }

    public String m() {
        StringBuffer stringBuffer = new StringBuffer();
        WifiInfo l = a().l();
        if (l == null || l.getBSSID() == null) {
            return null;
        }
        String replace = l.getBSSID().replace(":", "");
        int rssi = l.getRssi();
        String n = a().n();
        if (rssi < 0) {
            rssi = -rssi;
        }
        if (replace == null || rssi >= 100) {
            return null;
        }
        stringBuffer.append("&wf=");
        stringBuffer.append(replace);
        stringBuffer.append(";");
        stringBuffer.append("" + rssi + ";");
        String ssid = l.getSSID();
        if (ssid != null && (ssid.contains(DispatchConstants.SIGN_SPLIT_SYMBOL) || ssid.contains(";"))) {
            ssid = ssid.replace(DispatchConstants.SIGN_SPLIT_SYMBOL, "_");
        }
        stringBuffer.append(ssid);
        stringBuffer.append("&wf_n=1");
        if (n != null) {
            stringBuffer.append("&wf_gw=");
            stringBuffer.append(n);
        }
        return stringBuffer.toString();
    }

    public String n() {
        if (this.c == null) {
            return null;
        }
        DhcpInfo dhcpInfo = this.c.getDhcpInfo();
        return dhcpInfo != null ? a((long) dhcpInfo.gateway) : null;
    }

    public f o() {
        return (this.e == null || !this.e.i()) ? q() : this.e;
    }

    public f p() {
        return (this.e == null || !this.e.j()) ? q() : this.e;
    }

    public f q() {
        if (this.c != null) {
            try {
                return new f(this.c.getScanResults(), this.f);
            } catch (Exception e) {
            }
        }
        return new f(null, 0);
    }

    public void r() {
    }

    public String s() {
        try {
            WifiInfo connectionInfo = this.c.getConnectionInfo();
            return connectionInfo != null ? connectionInfo.getMacAddress() : null;
        } catch (Exception e) {
            return null;
        } catch (Error e2) {
            return null;
        }
    }
}
