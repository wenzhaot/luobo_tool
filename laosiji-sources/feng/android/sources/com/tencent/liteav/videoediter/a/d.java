package com.tencent.liteav.videoediter.a;

import android.annotation.TargetApi;
import android.media.MediaFormat;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.c.e;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: TXMultiMediaExtractor */
public class d extends b {
    private static final String a = d.class.getName();
    private ArrayList<String> b = new ArrayList();
    private int c = -1;
    private long d = 0;
    private long e = 0;

    public synchronized void a(List<String> list) {
        if (list != null) {
            if (list.size() > 0) {
                this.b.addAll(list);
            }
        }
    }

    public synchronized void a(long j) {
        if (j <= 0) {
            g();
        } else {
            g();
            if (this.b.size() > 0) {
                int i;
                b bVar = new b();
                int i2 = 0;
                while (true) {
                    i = i2;
                    if (i >= this.b.size()) {
                        break;
                    }
                    try {
                        bVar.a((String) this.b.get(i));
                        if (bVar.c() + 0 > j) {
                            break;
                        }
                        i2 = i + 1;
                    } catch (IOException e) {
                        e.printStackTrace();
                        TXCLog.e(a, "setDataSource IOException: " + e);
                    }
                }
                bVar.e();
                if (i < this.b.size()) {
                    this.c = i;
                    this.d = 0;
                    try {
                        super.a((String) this.b.get(this.c));
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        TXCLog.e(a, "setDataSource IOException: " + e2);
                    }
                    super.a(j - this.d);
                    this.e = super.d();
                }
            }
        }
    }

    @TargetApi(16)
    public int f() {
        MediaFormat mediaFormat = null;
        if (this.b.size() <= 0) {
            return -1;
        }
        b bVar = new b();
        Iterator it = this.b.iterator();
        MediaFormat mediaFormat2 = null;
        while (it.hasNext()) {
            try {
                bVar.a((String) it.next());
                MediaFormat a = bVar.a();
                MediaFormat b = bVar.b();
                if (mediaFormat2 == null && mediaFormat == null) {
                    mediaFormat = a;
                } else if ((mediaFormat2 != null && a == null) || ((mediaFormat2 == null && a != null) || ((mediaFormat != null && b == null) || (mediaFormat == null && b != null)))) {
                    return -2;
                } else {
                    if (mediaFormat2 != null && a != null) {
                        try {
                            if (Math.abs(mediaFormat2.getInteger("frame-rate") - a.getInteger("frame-rate")) > 3) {
                                return -4;
                            }
                            if (mediaFormat2.getInteger("width") != a.getInteger("width")) {
                                return -5;
                            }
                            if (mediaFormat2.getInteger("height") != a.getInteger("height")) {
                                return -6;
                            }
                        } catch (NullPointerException e) {
                            return -3;
                        }
                    } else if (!(mediaFormat == null || b == null)) {
                        if (mediaFormat.getInteger("sample-rate") != b.getInteger("sample-rate")) {
                            return -7;
                        }
                        if (mediaFormat.getInteger("channel-count") != b.getInteger("channel-count")) {
                            return -8;
                        }
                    }
                    b = mediaFormat;
                    mediaFormat = mediaFormat2;
                }
                mediaFormat2 = mediaFormat;
                mediaFormat = b;
            } catch (IOException e2) {
                e2.printStackTrace();
                TXCLog.e(a, "setDataSource IOException: " + e2);
            }
        }
        bVar.e();
        return 0;
    }

    public synchronized long c() {
        long j;
        j = 0;
        if (this.b.size() > 0) {
            b bVar = new b();
            int i = 0;
            while (i < this.b.size()) {
                long c;
                try {
                    bVar.a((String) this.b.get(i));
                    c = bVar.c() + j;
                } catch (IOException e) {
                    e.printStackTrace();
                    TXCLog.e(a, "setDataSource IOException: " + e);
                    c = j;
                }
                i++;
                j = c;
            }
            bVar.e();
        }
        return j;
    }

    public synchronized int a(e eVar) {
        int a;
        a = super.a(eVar);
        while (a < 0 && this.c < this.b.size() - 1) {
            this.d = this.e + 1000;
            this.c++;
            try {
                a((String) this.b.get(this.c));
                a = super.a(eVar);
            } catch (IOException e) {
                TXCLog.e(a, "setDataSource IOException: " + e);
                e.printStackTrace();
            }
        }
        if (a >= 0) {
            long e2 = eVar.e() + this.d;
            eVar.a(e2);
            if (this.e < e2) {
                this.e = e2;
            }
        } else {
            TXCLog.d(a, "readSampleData length = " + a);
        }
        return a;
    }

    private synchronized void g() {
        super.e();
        this.c = -1;
        this.d = 0;
        this.e = 0;
    }

    public synchronized void e() {
        super.e();
        this.b.clear();
        this.c = -1;
        this.d = 0;
        this.e = 0;
    }
}
