package com.tencent.liteav;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.datareport.TXCDRExtInfo;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.basic.util.a;

/* compiled from: TXCVodPlayCollection */
public class f {
    private final String a = "TXCVodPlayCollection";
    private Context b;
    private String c = null;
    private long d = 0;
    private long e = 0;
    private boolean f = false;
    private int g = 0;
    private int h = 0;
    private int i = 0;
    private int j = 0;
    private int k = 0;
    private int l = 0;
    private int m;
    private String n;
    private boolean o = false;
    private boolean p = false;
    private int q = 0;
    private int r = 0;
    private int s = 0;
    private int t = 0;
    private int u = 0;
    private int v;
    private int w;
    private int x;

    public f(Context context) {
        this.b = context;
        this.n = TXCCommonUtil.getAppVersion();
    }

    public void a(String str) {
        this.c = str;
    }

    public String a() {
        Context context = this.b;
        ApplicationInfo applicationInfo = this.b.getApplicationInfo();
        int i = applicationInfo.labelRes;
        return i == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(i);
    }

    private void m() {
        int i;
        String c = a.c();
        TXCDRExtInfo tXCDRExtInfo = new TXCDRExtInfo();
        tXCDRExtInfo.report_common = false;
        tXCDRExtInfo.report_status = false;
        tXCDRExtInfo.url = this.c;
        TXCDRApi.InitEvent(this.b, c, com.tencent.liteav.basic.datareport.a.ad, com.tencent.liteav.basic.datareport.a.as, tXCDRExtInfo);
        TXCDRApi.txSetEventIntValue(c, com.tencent.liteav.basic.datareport.a.ad, "u32_timeuse", (long) this.h);
        TXCDRApi.txSetEventValue(c, com.tencent.liteav.basic.datareport.a.ad, "str_stream_url", this.c);
        TXCDRApi.txSetEventIntValue(c, com.tencent.liteav.basic.datareport.a.ad, "u32_videotime", (long) this.g);
        TXCDRApi.txSetEventValue(c, com.tencent.liteav.basic.datareport.a.ad, "str_device_type", a.b());
        TXCDRApi.txSetEventIntValue(c, com.tencent.liteav.basic.datareport.a.ad, "u32_network_type", (long) a.c(this.b));
        TXCDRApi.txSetEventValue(c, com.tencent.liteav.basic.datareport.a.ad, "str_user_id", a.a(this.b));
        TXCDRApi.txSetEventValue(c, com.tencent.liteav.basic.datareport.a.ad, "str_package_name", a.b(this.b));
        TXCDRApi.txSetEventValue(c, com.tencent.liteav.basic.datareport.a.ad, "str_app_version", this.n);
        TXCDRApi.txSetEventValue(c, com.tencent.liteav.basic.datareport.a.ad, "dev_uuid", a.d(this.b));
        TXCDRApi.txSetEventIntValue(c, com.tencent.liteav.basic.datareport.a.ad, "u32_first_i_frame", (long) this.i);
        TXCDRApi.txSetEventIntValue(c, com.tencent.liteav.basic.datareport.a.ad, "u32_isp2p", (long) this.j);
        TXCDRApi.txSetEventIntValue(c, com.tencent.liteav.basic.datareport.a.ad, "u32_avg_load", this.k == 0 ? 0 : (long) (this.l / this.k));
        TXCDRApi.txSetEventIntValue(c, com.tencent.liteav.basic.datareport.a.ad, "u32_load_cnt", (long) this.k);
        TXCDRApi.txSetEventIntValue(c, com.tencent.liteav.basic.datareport.a.ad, "u32_max_load", (long) this.m);
        TXCDRApi.txSetEventIntValue(c, com.tencent.liteav.basic.datareport.a.ad, "u32_player_type", (long) this.r);
        TXCDRApi.txSetEventValue(c, com.tencent.liteav.basic.datareport.a.ad, "str_app_name", a());
        if (this.t > 0) {
            TXCDRApi.txSetEventIntValue(c, com.tencent.liteav.basic.datareport.a.ad, "u32_dns_time", (long) this.t);
        } else {
            TXCDRApi.txSetEventIntValue(c, com.tencent.liteav.basic.datareport.a.ad, "u32_dns_time", -1);
        }
        if (this.s > 0) {
            TXCDRApi.txSetEventIntValue(c, com.tencent.liteav.basic.datareport.a.ad, "u32_tcp_did_connect", (long) this.s);
        } else {
            TXCDRApi.txSetEventIntValue(c, com.tencent.liteav.basic.datareport.a.ad, "u32_tcp_did_connect", -1);
        }
        if (this.u > 0) {
            TXCDRApi.txSetEventIntValue(c, com.tencent.liteav.basic.datareport.a.ad, "u32_first_video_packet", (long) this.u);
        } else {
            TXCDRApi.txSetEventIntValue(c, com.tencent.liteav.basic.datareport.a.ad, "u32_first_video_packet", -1);
        }
        TXCDRApi.nativeReportEvent(c, com.tencent.liteav.basic.datareport.a.ad);
        String str = "TXCVodPlayCollection";
        StringBuilder append = new StringBuilder().append("report evt 40301: token=").append(c).append(" ").append("u32_timeuse").append("=").append(this.h).append(" ").append("str_stream_url").append("=").append(this.c).append(" ").append("u32_videotime").append("=").append(this.g).append(" ").append("str_device_type").append("=").append(a.b()).append(" ").append("u32_network_type").append("=").append(a.c(this.b)).append(" ").append("str_user_id").append("=").append(a.a(this.b)).append(" ").append("str_package_name").append("=").append(a.b(this.b)).append(" ").append("str_app_version").append("=").append(this.n).append(" ").append("dev_uuid").append("=").append(a.d(this.b)).append(" ").append("u32_first_i_frame").append("=").append(this.i).append(" ").append("u32_isp2p").append("=").append(this.j).append(" ").append("u32_avg_load").append("=");
        if (this.k == 0) {
            i = 0;
        } else {
            i = this.l / this.k;
        }
        TXCLog.w(str, append.append(i).append(" ").append("u32_load_cnt").append("=").append(this.k).append(" ").append("u32_max_load").append("=").append(this.m).append(" ").append("u32_player_type").append("=").append(this.r).append(" ").append("u32_dns_time").append("=").append(this.t).append(" ").append("u32_tcp_did_connect").append("=").append(this.s).append(" ").append("u32_first_video_packet").append("=").append(this.u).toString());
    }

    public void a(int i) {
        this.g = i;
    }

    public void b() {
        this.f = true;
        this.d = System.currentTimeMillis();
    }

    public void c() {
        if (this.f) {
            this.h = (int) ((System.currentTimeMillis() - this.d) / 1000);
            m();
            this.f = false;
        }
        this.o = false;
        this.p = false;
    }

    public void d() {
        if (this.i != 0 && this.p) {
            int currentTimeMillis = (int) (System.currentTimeMillis() - this.e);
            this.l += currentTimeMillis;
            this.k++;
            if (this.m < currentTimeMillis) {
                this.m = currentTimeMillis;
            }
            this.p = false;
        }
        if (this.o) {
            this.o = false;
        }
    }

    public void e() {
        if (this.i == 0) {
            this.i = (int) (System.currentTimeMillis() - this.d);
        }
    }

    public void f() {
        if (this.s == 0) {
            this.s = (int) (System.currentTimeMillis() - this.d);
        }
    }

    public void g() {
        if (this.t == 0) {
            this.t = (int) (System.currentTimeMillis() - this.d);
        }
    }

    public void h() {
        if (this.u == 0) {
            this.u = (int) (System.currentTimeMillis() - this.d);
        }
    }

    public void i() {
        this.e = System.currentTimeMillis();
        this.p = true;
    }

    public void j() {
        this.o = true;
        this.q++;
        TXCDRApi.txReportDAU(this.b, com.tencent.liteav.basic.datareport.a.bu);
    }

    public void b(int i) {
        this.r = i;
    }

    public void a(boolean z) {
        if (z) {
            this.v = 1;
            TXCDRApi.txReportDAU(this.b, com.tencent.liteav.basic.datareport.a.bw);
            return;
        }
        this.v = 0;
    }

    public void k() {
        this.x++;
        TXCDRApi.txReportDAU(this.b, com.tencent.liteav.basic.datareport.a.bx);
    }

    public void l() {
        this.w++;
        TXCDRApi.txReportDAU(this.b, com.tencent.liteav.basic.datareport.a.bv);
    }
}
