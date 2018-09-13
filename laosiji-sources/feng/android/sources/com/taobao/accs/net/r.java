package com.taobao.accs.net;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.strategy.IConnStrategy;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.ErrorCode;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.data.Message;
import com.taobao.accs.ut.monitor.SessionMonitor;
import com.taobao.accs.ut.statistics.c;
import com.taobao.accs.ut.statistics.d;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.accs.utl.b;
import com.taobao.accs.utl.f;
import com.taobao.accs.utl.i;
import com.taobao.accs.utl.l;
import com.umeng.message.util.HttpRequest;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.android.spdy.RequestPriority;
import org.android.spdy.SessionCb;
import org.android.spdy.SessionInfo;
import org.android.spdy.SpdyAgent;
import org.android.spdy.SpdyByteArray;
import org.android.spdy.SpdyDataProvider;
import org.android.spdy.SpdyRequest;
import org.android.spdy.SpdySession;
import org.android.spdy.SpdySessionKind;
import org.android.spdy.SpdyVersion;
import org.android.spdy.Spdycb;
import org.android.spdy.SuperviseConnectInfo;
import org.android.spdy.SuperviseData;

/* compiled from: Taobao */
public class r extends b implements SessionCb, Spdycb {
    private Object A = new Object();
    private long B;
    private long C;
    private long D;
    private long E;
    private int F = -1;
    private String G = null;
    private SessionMonitor H;
    private c I;
    private boolean J = false;
    private String K = "";
    private boolean L = false;
    private h M = new h(m());
    private String N;
    protected ScheduledFuture<?> n;
    protected String o;
    protected int p;
    protected String q;
    protected int r;
    private int s = 3;
    private LinkedList<Message> t = new LinkedList();
    private a u;
    private boolean v = true;
    private String w;
    private String x;
    private SpdyAgent y = null;
    private SpdySession z = null;

    /* compiled from: Taobao */
    private class a extends Thread {
        public int a = 0;
        long b;
        private final String d = getName();

        public a(String str) {
            super(str);
        }

        private void a(boolean z) {
            if (r.this.s != 1) {
                if (UtilityImpl.isNetworkConnected(r.this.d)) {
                    if (z) {
                        this.a = 0;
                    }
                    ALog.e(this.d, r.this.c + " try connect, force = " + z + " failTimes = " + this.a, new Object[0]);
                    if (r.this.s != 1 && this.a >= 4) {
                        r.this.J = true;
                        ALog.e(this.d, r.this.c + " try connect fail " + 4 + " times", new Object[0]);
                        return;
                    } else if (r.this.s != 1) {
                        if (r.this.c == 1 && this.a == 0) {
                            ALog.i(this.d, r.this.c + " try connect in app, no sleep", new Object[0]);
                        } else {
                            ALog.i(this.d, r.this.c + " try connect, need sleep", new Object[0]);
                            try {
                                sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        r.this.K = "";
                        if (this.a == 3) {
                            r.this.M.b(r.this.m());
                        }
                        r.this.d(null);
                        r.this.H.setRetryTimes(this.a);
                        if (r.this.s != 1) {
                            this.a++;
                            ALog.e(this.d, r.this.c + " try connect fail, ready for reconnect", new Object[0]);
                            a(false);
                            return;
                        }
                        this.b = System.currentTimeMillis();
                        return;
                    } else {
                        return;
                    }
                }
                ALog.e(this.d, r.this.c + " Network not available", new Object[0]);
            } else if (r.this.s == 1 && System.currentTimeMillis() - this.b > 5000) {
                this.a = 0;
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:172:0x050e A:{SYNTHETIC, Splitter: B:172:0x050e} */
        /* JADX WARNING: Removed duplicated region for block: B:118:0x03dc A:{SYNTHETIC, Splitter: B:118:0x03dc} */
        public void run() {
            /*
            r13 = this;
            r12 = 100;
            r11 = 2;
            r7 = 1;
            r8 = 0;
            r0 = r13.d;
            r1 = new java.lang.StringBuilder;
            r1.<init>();
            r2 = com.taobao.accs.net.r.this;
            r2 = r2.c;
            r1 = r1.append(r2);
            r2 = " NetworkThread run";
            r1 = r1.append(r2);
            r1 = r1.toString();
            r2 = new java.lang.Object[r8];
            com.taobao.accs.utl.ALog.i(r0, r1, r2);
            r0 = 0;
            r13.a = r8;
        L_0x0027:
            r1 = com.taobao.accs.net.r.this;
            r1 = r1.v;
            if (r1 == 0) goto L_0x009a;
        L_0x002f:
            r1 = r13.d;
            r2 = "ready to get message";
            r3 = new java.lang.Object[r8];
            com.taobao.accs.utl.ALog.d(r1, r2, r3);
            r1 = com.taobao.accs.net.r.this;
            r1 = r1.t;
            monitor-enter(r1);
            r2 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x00a6 }
            r2 = r2.t;	 Catch:{ all -> 0x00a6 }
            r2 = r2.size();	 Catch:{ all -> 0x00a6 }
            if (r2 != 0) goto L_0x0060;
        L_0x004c:
            r2 = r13.d;	 Catch:{ InterruptedException -> 0x00a0 }
            r3 = "no message, wait";
            r4 = 0;
            r4 = new java.lang.Object[r4];	 Catch:{ InterruptedException -> 0x00a0 }
            com.taobao.accs.utl.ALog.d(r2, r3, r4);	 Catch:{ InterruptedException -> 0x00a0 }
            r2 = com.taobao.accs.net.r.this;	 Catch:{ InterruptedException -> 0x00a0 }
            r2 = r2.t;	 Catch:{ InterruptedException -> 0x00a0 }
            r2.wait();	 Catch:{ InterruptedException -> 0x00a0 }
        L_0x0060:
            r2 = r13.d;	 Catch:{ all -> 0x00a6 }
            r3 = "try get message";
            r4 = 0;
            r4 = new java.lang.Object[r4];	 Catch:{ all -> 0x00a6 }
            com.taobao.accs.utl.ALog.d(r2, r3, r4);	 Catch:{ all -> 0x00a6 }
            r2 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x00a6 }
            r2 = r2.t;	 Catch:{ all -> 0x00a6 }
            r2 = r2.size();	 Catch:{ all -> 0x00a6 }
            if (r2 == 0) goto L_0x0090;
        L_0x0077:
            r0 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x00a6 }
            r0 = r0.t;	 Catch:{ all -> 0x00a6 }
            r0 = r0.getFirst();	 Catch:{ all -> 0x00a6 }
            r0 = (com.taobao.accs.data.Message) r0;	 Catch:{ all -> 0x00a6 }
            r2 = r0.e();	 Catch:{ all -> 0x00a6 }
            if (r2 == 0) goto L_0x0090;
        L_0x0089:
            r2 = r0.e();	 Catch:{ all -> 0x00a6 }
            r2.onTakeFromQueue();	 Catch:{ all -> 0x00a6 }
        L_0x0090:
            r6 = r0;
            monitor-exit(r1);	 Catch:{ all -> 0x00a6 }
            r0 = com.taobao.accs.net.r.this;
            r0 = r0.v;
            if (r0 != 0) goto L_0x00a9;
        L_0x009a:
            r0 = com.taobao.accs.net.r.this;
            r0.l();
            return;
        L_0x00a0:
            r0 = move-exception;
            r0.printStackTrace();	 Catch:{ all -> 0x00a6 }
            monitor-exit(r1);	 Catch:{ all -> 0x00a6 }
            goto L_0x009a;
        L_0x00a6:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x00a6 }
            throw r0;
        L_0x00a9:
            if (r6 == 0) goto L_0x05fd;
        L_0x00ab:
            r0 = r13.d;
            r1 = "send message not null";
            r2 = new java.lang.Object[r8];
            com.taobao.accs.utl.ALog.d(r0, r1, r2);
            r0 = r6.a();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = r13.d;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2.<init>();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r3 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r3 = r3.c;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = r2.append(r3);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r3 = " send:";
            r2 = r2.append(r3);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r3 = com.taobao.accs.data.Message.b.b(r0);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = r2.append(r3);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r3 = " status:";
            r2 = r2.append(r3);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r3 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r3 = r3.s;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = r2.append(r3);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = r2.toString();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r3 = 0;
            r3 = new java.lang.Object[r3];	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            com.taobao.accs.utl.ALog.i(r1, r2, r3);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            if (r0 != r11) goto L_0x0289;
        L_0x00f4:
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r0.c;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            if (r0 != r7) goto L_0x0135;
        L_0x00fa:
            r0 = r13.d;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = "INAPP ping, skip";
            r2 = 0;
            r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            com.taobao.accs.utl.ALog.d(r0, r1, r2);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r13.d;	 Catch:{ Throwable -> 0x0127 }
            r1 = "send succ, remove it";
            r2 = 0;
            r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x0127 }
            com.taobao.accs.utl.ALog.d(r0, r1, r2);	 Catch:{ Throwable -> 0x0127 }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x0127 }
            r1 = r0.t;	 Catch:{ Throwable -> 0x0127 }
            monitor-enter(r1);	 Catch:{ Throwable -> 0x0127 }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x0124 }
            r0 = r0.t;	 Catch:{ all -> 0x0124 }
            r0.remove(r6);	 Catch:{ all -> 0x0124 }
            monitor-exit(r1);	 Catch:{ all -> 0x0124 }
            r0 = r6;
            goto L_0x0027;
        L_0x0124:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0124 }
            throw r0;	 Catch:{ Throwable -> 0x0127 }
        L_0x0127:
            r0 = move-exception;
            r1 = r13.d;
            r2 = " run finally error";
            r3 = new java.lang.Object[r8];
            com.taobao.accs.utl.ALog.e(r1, r2, r0, r3);
            r0 = r6;
            goto L_0x0027;
        L_0x0135:
            r0 = java.lang.System.currentTimeMillis();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = r2.B;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r0 - r2;
            r2 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = r2.d;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = com.taobao.accs.net.g.a(r2);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = r2.b();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = r2 + -1;
            r2 = r2 * 1000;
            r2 = (long) r2;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
            if (r0 >= 0) goto L_0x0159;
        L_0x0155:
            r0 = r6.d;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            if (r0 == 0) goto L_0x0283;
        L_0x0159:
            r0 = r13.d;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1.<init>();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = "ms:";
            r1 = r1.append(r2);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = java.lang.System.currentTimeMillis();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r4 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r4 = r4.B;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = r2 - r4;
            r1 = r1.append(r2);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = " force:";
            r1 = r1.append(r2);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = r6.d;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = r1.append(r2);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = r1.toString();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = 0;
            r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            com.taobao.accs.utl.ALog.d(r0, r1, r2);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = 1;
            r13.a(r0);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r0.z;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            if (r0 == 0) goto L_0x0281;
        L_0x0199:
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r0.s;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            if (r0 != r7) goto L_0x0281;
        L_0x01a1:
            r0 = java.lang.System.currentTimeMillis();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = r2.B;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r0 - r2;
            r2 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = r2.d;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = com.taobao.accs.net.g.a(r2);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = r2.b();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = r2 + -1;
            r2 = r2 * 1000;
            r2 = (long) r2;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
            if (r0 < 0) goto L_0x0475;
        L_0x01c1:
            r0 = r13.d;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1.<init>();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = r2.c;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = r1.append(r2);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = " onSendPing";
            r1 = r1.append(r2);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = r1.toString();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = 0;
            r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            com.taobao.accs.utl.ALog.i(r0, r1, r2);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r0.e;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0.a();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r0.z;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0.submitPing();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r0.H;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0.onSendPing();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = java.lang.System.currentTimeMillis();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0.B = r2;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = java.lang.System.nanoTime();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0.C = r2;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0.f();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = r7;
        L_0x0212:
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x05fa }
            r0.q();	 Catch:{ Throwable -> 0x05fa }
            if (r1 != 0) goto L_0x04b4;
        L_0x0219:
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x04a8 }
            r0.l();	 Catch:{ Throwable -> 0x04a8 }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x04a8 }
            r0 = r0.H;	 Catch:{ Throwable -> 0x04a8 }
            if (r0 == 0) goto L_0x0232;
        L_0x0226:
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x04a8 }
            r0 = r0.H;	 Catch:{ Throwable -> 0x04a8 }
            r1 = "send fail";
            r0.setCloseReason(r1);	 Catch:{ Throwable -> 0x04a8 }
        L_0x0232:
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x04a8 }
            r2 = r0.t;	 Catch:{ Throwable -> 0x04a8 }
            monitor-enter(r2);	 Catch:{ Throwable -> 0x04a8 }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x04a5 }
            r0 = r0.t;	 Catch:{ all -> 0x04a5 }
            r0 = r0.size();	 Catch:{ all -> 0x04a5 }
            r0 = r0 + -1;
            r1 = r0;
        L_0x0246:
            if (r1 < 0) goto L_0x0478;
        L_0x0248:
            r0 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x04a5 }
            r0 = r0.t;	 Catch:{ all -> 0x04a5 }
            r0 = r0.get(r1);	 Catch:{ all -> 0x04a5 }
            r0 = (com.taobao.accs.data.Message) r0;	 Catch:{ all -> 0x04a5 }
            if (r0 == 0) goto L_0x027d;
        L_0x0256:
            r3 = r0.t;	 Catch:{ all -> 0x04a5 }
            if (r3 == 0) goto L_0x027d;
        L_0x025a:
            r3 = r0.t;	 Catch:{ all -> 0x04a5 }
            r3 = r3.intValue();	 Catch:{ all -> 0x04a5 }
            if (r3 == r12) goto L_0x026c;
        L_0x0262:
            r3 = r0.t;	 Catch:{ all -> 0x04a5 }
            r3 = r3.intValue();	 Catch:{ all -> 0x04a5 }
            r4 = 201; // 0xc9 float:2.82E-43 double:9.93E-322;
            if (r3 != r4) goto L_0x027d;
        L_0x026c:
            r3 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x04a5 }
            r3 = r3.e;	 Catch:{ all -> 0x04a5 }
            r4 = -1;
            r3.a(r0, r4);	 Catch:{ all -> 0x04a5 }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x04a5 }
            r0 = r0.t;	 Catch:{ all -> 0x04a5 }
            r0.remove(r1);	 Catch:{ all -> 0x04a5 }
        L_0x027d:
            r0 = r1 + -1;
            r1 = r0;
            goto L_0x0246;
        L_0x0281:
            r1 = r8;
            goto L_0x0212;
        L_0x0283:
            r0 = 0;
            r13.a(r0);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = r7;
            goto L_0x0212;
        L_0x0289:
            if (r0 != r7) goto L_0x044d;
        L_0x028b:
            r0 = 1;
            r13.a(r0);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r0.s;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            if (r0 != r7) goto L_0x044a;
        L_0x0297:
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r0.z;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            if (r0 == 0) goto L_0x044a;
        L_0x029f:
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r0.d;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = r1.c;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r5 = r6.a(r0, r1);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = java.lang.System.currentTimeMillis();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r6.a(r0);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r5.length;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = 16384; // 0x4000 float:2.2959E-41 double:8.0948E-320;
            if (r0 <= r1) goto L_0x02cc;
        L_0x02b7:
            r0 = r6.t;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r0.intValue();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
            if (r0 == r1) goto L_0x02cc;
        L_0x02c1:
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r0.e;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = -4;
            r0.a(r6, r1);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
        L_0x02c9:
            r1 = r7;
            goto L_0x0212;
        L_0x02cc:
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r0.z;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = r6.d();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
            r3 = 0;
            if (r5 != 0) goto L_0x0444;
        L_0x02db:
            r4 = r8;
        L_0x02dc:
            r0.sendCustomControlFrame(r1, r2, r3, r4, r5);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = r13.d;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0.<init>();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = r2.c;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r0.append(r2);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = " send data len";
            r0 = r0.append(r2);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = r0.toString();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = 5;
            r3 = new java.lang.Object[r0];	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r4 = 0;
            if (r5 != 0) goto L_0x0447;
        L_0x02ff:
            r0 = r8;
        L_0x0300:
            r0 = java.lang.Integer.valueOf(r0);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r3[r4] = r0;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = 1;
            r4 = "dataId";
            r3[r0] = r4;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = 2;
            r4 = r6.b();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r3[r0] = r4;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = 3;
            r4 = "utdid";
            r3[r0] = r4;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = 4;
            r4 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r4 = r4.j;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r3[r0] = r4;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            com.taobao.accs.utl.ALog.e(r1, r2, r3);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r0.e;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0.a(r6);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r6.c;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            if (r0 == 0) goto L_0x036e;
        L_0x032e:
            r0 = r13.d;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1.<init>();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = r2.c;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = r1.append(r2);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = " sendCFrame end ack";
            r1 = r1.append(r2);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = r1.toString();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = 2;
            r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r3 = 0;
            r4 = "dataId";
            r2[r3] = r4;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r3 = 1;
            r4 = r6.d();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r4 = java.lang.Integer.valueOf(r4);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2[r3] = r4;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            com.taobao.accs.utl.ALog.e(r0, r1, r2);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r0.l;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = r6.d();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = java.lang.Integer.valueOf(r1);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0.put(r1, r6);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
        L_0x036e:
            r0 = r6.e();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            if (r0 == 0) goto L_0x037b;
        L_0x0374:
            r0 = r6.e();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0.onSendData();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
        L_0x037b:
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = r6.b();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = r6.Q;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = (long) r2;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0.a(r1, r2);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r9 = r0.e;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = new com.taobao.accs.ut.monitor.TrafficsMonitor$a;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = r6.F;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = anet.channel.GlobalAppRuntimeInfo.isAppBackground();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r3 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r3 = r3.m();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r4 = r5.length;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r4 = (long) r4;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0.<init>(r1, r2, r3, r4);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r9.a(r0);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            goto L_0x02c9;
        L_0x03a3:
            r0 = move-exception;
            r1 = r7;
        L_0x03a5:
            r2 = "accs";
            r3 = "send_fail";
            r4 = r6.F;	 Catch:{ all -> 0x05f5 }
            r5 = "1";
            r9 = new java.lang.StringBuilder;	 Catch:{ all -> 0x05f5 }
            r9.<init>();	 Catch:{ all -> 0x05f5 }
            r10 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x05f5 }
            r10 = r10.c;	 Catch:{ all -> 0x05f5 }
            r9 = r9.append(r10);	 Catch:{ all -> 0x05f5 }
            r10 = r0.toString();	 Catch:{ all -> 0x05f5 }
            r9 = r9.append(r10);	 Catch:{ all -> 0x05f5 }
            r9 = r9.toString();	 Catch:{ all -> 0x05f5 }
            com.taobao.accs.utl.b.a(r2, r3, r4, r5, r9);	 Catch:{ all -> 0x05f5 }
            r0.printStackTrace();	 Catch:{ all -> 0x05f5 }
            r2 = r13.d;	 Catch:{ all -> 0x05f5 }
            r3 = "service connection run";
            r4 = 0;
            r4 = new java.lang.Object[r4];	 Catch:{ all -> 0x05f5 }
            com.taobao.accs.utl.ALog.e(r2, r3, r0, r4);	 Catch:{ all -> 0x05f5 }
            if (r1 != 0) goto L_0x050e;
        L_0x03dc:
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x0502 }
            r0.l();	 Catch:{ Throwable -> 0x0502 }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x0502 }
            r0 = r0.H;	 Catch:{ Throwable -> 0x0502 }
            if (r0 == 0) goto L_0x03f5;
        L_0x03e9:
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x0502 }
            r0 = r0.H;	 Catch:{ Throwable -> 0x0502 }
            r1 = "send fail";
            r0.setCloseReason(r1);	 Catch:{ Throwable -> 0x0502 }
        L_0x03f5:
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x0502 }
            r2 = r0.t;	 Catch:{ Throwable -> 0x0502 }
            monitor-enter(r2);	 Catch:{ Throwable -> 0x0502 }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x04ff }
            r0 = r0.t;	 Catch:{ all -> 0x04ff }
            r0 = r0.size();	 Catch:{ all -> 0x04ff }
            r0 = r0 + -1;
            r1 = r0;
        L_0x0409:
            if (r1 < 0) goto L_0x04d4;
        L_0x040b:
            r0 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x04ff }
            r0 = r0.t;	 Catch:{ all -> 0x04ff }
            r0 = r0.get(r1);	 Catch:{ all -> 0x04ff }
            r0 = (com.taobao.accs.data.Message) r0;	 Catch:{ all -> 0x04ff }
            if (r0 == 0) goto L_0x0440;
        L_0x0419:
            r3 = r0.t;	 Catch:{ all -> 0x04ff }
            if (r3 == 0) goto L_0x0440;
        L_0x041d:
            r3 = r0.t;	 Catch:{ all -> 0x04ff }
            r3 = r3.intValue();	 Catch:{ all -> 0x04ff }
            if (r3 == r12) goto L_0x042f;
        L_0x0425:
            r3 = r0.t;	 Catch:{ all -> 0x04ff }
            r3 = r3.intValue();	 Catch:{ all -> 0x04ff }
            r4 = 201; // 0xc9 float:2.82E-43 double:9.93E-322;
            if (r3 != r4) goto L_0x0440;
        L_0x042f:
            r3 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x04ff }
            r3 = r3.e;	 Catch:{ all -> 0x04ff }
            r4 = -1;
            r3.a(r0, r4);	 Catch:{ all -> 0x04ff }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x04ff }
            r0 = r0.t;	 Catch:{ all -> 0x04ff }
            r0.remove(r1);	 Catch:{ all -> 0x04ff }
        L_0x0440:
            r0 = r1 + -1;
            r1 = r0;
            goto L_0x0409;
        L_0x0444:
            r4 = r5.length;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            goto L_0x02dc;
        L_0x0447:
            r0 = r5.length;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            goto L_0x0300;
        L_0x044a:
            r1 = r8;
            goto L_0x0212;
        L_0x044d:
            r1 = 0;
            r13.a(r1);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r1 = r13.d;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2.<init>();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r3 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r3 = r3.c;	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = r2.append(r3);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r3 = " skip msg ";
            r2 = r2.append(r3);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r2.append(r0);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r0 = r0.toString();	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            r2 = 0;
            r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
            com.taobao.accs.utl.ALog.e(r1, r0, r2);	 Catch:{ Throwable -> 0x03a3, all -> 0x052f }
        L_0x0475:
            r1 = r7;
            goto L_0x0212;
        L_0x0478:
            r0 = r13.d;	 Catch:{ all -> 0x04a5 }
            r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x04a5 }
            r1.<init>();	 Catch:{ all -> 0x04a5 }
            r3 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x04a5 }
            r3 = r3.c;	 Catch:{ all -> 0x04a5 }
            r1 = r1.append(r3);	 Catch:{ all -> 0x04a5 }
            r3 = " network disconnected, wait";
            r1 = r1.append(r3);	 Catch:{ all -> 0x04a5 }
            r1 = r1.toString();	 Catch:{ all -> 0x04a5 }
            r3 = 0;
            r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x04a5 }
            com.taobao.accs.utl.ALog.e(r0, r1, r3);	 Catch:{ all -> 0x04a5 }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x04a5 }
            r0 = r0.t;	 Catch:{ all -> 0x04a5 }
            r0.wait();	 Catch:{ all -> 0x04a5 }
            monitor-exit(r2);	 Catch:{ all -> 0x04a5 }
        L_0x04a2:
            r0 = r6;
            goto L_0x0027;
        L_0x04a5:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x04a5 }
            throw r0;	 Catch:{ Throwable -> 0x04a8 }
        L_0x04a8:
            r0 = move-exception;
            r1 = r13.d;
            r2 = " run finally error";
            r3 = new java.lang.Object[r8];
            com.taobao.accs.utl.ALog.e(r1, r2, r0, r3);
            goto L_0x04a2;
        L_0x04b4:
            r0 = r13.d;	 Catch:{ Throwable -> 0x04a8 }
            r1 = "send succ, remove it";
            r2 = 0;
            r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x04a8 }
            com.taobao.accs.utl.ALog.d(r0, r1, r2);	 Catch:{ Throwable -> 0x04a8 }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x04a8 }
            r1 = r0.t;	 Catch:{ Throwable -> 0x04a8 }
            monitor-enter(r1);	 Catch:{ Throwable -> 0x04a8 }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x04d1 }
            r0 = r0.t;	 Catch:{ all -> 0x04d1 }
            r0.remove(r6);	 Catch:{ all -> 0x04d1 }
            monitor-exit(r1);	 Catch:{ all -> 0x04d1 }
            goto L_0x04a2;
        L_0x04d1:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x04d1 }
            throw r0;	 Catch:{ Throwable -> 0x04a8 }
        L_0x04d4:
            r0 = r13.d;	 Catch:{ all -> 0x04ff }
            r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x04ff }
            r1.<init>();	 Catch:{ all -> 0x04ff }
            r3 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x04ff }
            r3 = r3.c;	 Catch:{ all -> 0x04ff }
            r1 = r1.append(r3);	 Catch:{ all -> 0x04ff }
            r3 = " network disconnected, wait";
            r1 = r1.append(r3);	 Catch:{ all -> 0x04ff }
            r1 = r1.toString();	 Catch:{ all -> 0x04ff }
            r3 = 0;
            r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x04ff }
            com.taobao.accs.utl.ALog.e(r0, r1, r3);	 Catch:{ all -> 0x04ff }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x04ff }
            r0 = r0.t;	 Catch:{ all -> 0x04ff }
            r0.wait();	 Catch:{ all -> 0x04ff }
            monitor-exit(r2);	 Catch:{ all -> 0x04ff }
            goto L_0x04a2;
        L_0x04ff:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x04ff }
            throw r0;	 Catch:{ Throwable -> 0x0502 }
        L_0x0502:
            r0 = move-exception;
            r1 = r13.d;
            r2 = " run finally error";
            r3 = new java.lang.Object[r8];
            com.taobao.accs.utl.ALog.e(r1, r2, r0, r3);
            goto L_0x04a2;
        L_0x050e:
            r0 = r13.d;	 Catch:{ Throwable -> 0x0502 }
            r1 = "send succ, remove it";
            r2 = 0;
            r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x0502 }
            com.taobao.accs.utl.ALog.d(r0, r1, r2);	 Catch:{ Throwable -> 0x0502 }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x0502 }
            r1 = r0.t;	 Catch:{ Throwable -> 0x0502 }
            monitor-enter(r1);	 Catch:{ Throwable -> 0x0502 }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x052c }
            r0 = r0.t;	 Catch:{ all -> 0x052c }
            r0.remove(r6);	 Catch:{ all -> 0x052c }
            monitor-exit(r1);	 Catch:{ all -> 0x052c }
            goto L_0x04a2;
        L_0x052c:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x052c }
            throw r0;	 Catch:{ Throwable -> 0x0502 }
        L_0x052f:
            r0 = move-exception;
            r1 = r0;
        L_0x0531:
            if (r7 != 0) goto L_0x05d5;
        L_0x0533:
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x05c9 }
            r0.l();	 Catch:{ Throwable -> 0x05c9 }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x05c9 }
            r0 = r0.H;	 Catch:{ Throwable -> 0x05c9 }
            if (r0 == 0) goto L_0x054c;
        L_0x0540:
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x05c9 }
            r0 = r0.H;	 Catch:{ Throwable -> 0x05c9 }
            r2 = "send fail";
            r0.setCloseReason(r2);	 Catch:{ Throwable -> 0x05c9 }
        L_0x054c:
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x05c9 }
            r3 = r0.t;	 Catch:{ Throwable -> 0x05c9 }
            monitor-enter(r3);	 Catch:{ Throwable -> 0x05c9 }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x05c6 }
            r0 = r0.t;	 Catch:{ all -> 0x05c6 }
            r0 = r0.size();	 Catch:{ all -> 0x05c6 }
            r0 = r0 + -1;
            r2 = r0;
        L_0x0560:
            if (r2 < 0) goto L_0x059b;
        L_0x0562:
            r0 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x05c6 }
            r0 = r0.t;	 Catch:{ all -> 0x05c6 }
            r0 = r0.get(r2);	 Catch:{ all -> 0x05c6 }
            r0 = (com.taobao.accs.data.Message) r0;	 Catch:{ all -> 0x05c6 }
            if (r0 == 0) goto L_0x0597;
        L_0x0570:
            r4 = r0.t;	 Catch:{ all -> 0x05c6 }
            if (r4 == 0) goto L_0x0597;
        L_0x0574:
            r4 = r0.t;	 Catch:{ all -> 0x05c6 }
            r4 = r4.intValue();	 Catch:{ all -> 0x05c6 }
            if (r4 == r12) goto L_0x0586;
        L_0x057c:
            r4 = r0.t;	 Catch:{ all -> 0x05c6 }
            r4 = r4.intValue();	 Catch:{ all -> 0x05c6 }
            r5 = 201; // 0xc9 float:2.82E-43 double:9.93E-322;
            if (r4 != r5) goto L_0x0597;
        L_0x0586:
            r4 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x05c6 }
            r4 = r4.e;	 Catch:{ all -> 0x05c6 }
            r5 = -1;
            r4.a(r0, r5);	 Catch:{ all -> 0x05c6 }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x05c6 }
            r0 = r0.t;	 Catch:{ all -> 0x05c6 }
            r0.remove(r2);	 Catch:{ all -> 0x05c6 }
        L_0x0597:
            r0 = r2 + -1;
            r2 = r0;
            goto L_0x0560;
        L_0x059b:
            r0 = r13.d;	 Catch:{ all -> 0x05c6 }
            r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x05c6 }
            r2.<init>();	 Catch:{ all -> 0x05c6 }
            r4 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x05c6 }
            r4 = r4.c;	 Catch:{ all -> 0x05c6 }
            r2 = r2.append(r4);	 Catch:{ all -> 0x05c6 }
            r4 = " network disconnected, wait";
            r2 = r2.append(r4);	 Catch:{ all -> 0x05c6 }
            r2 = r2.toString();	 Catch:{ all -> 0x05c6 }
            r4 = 0;
            r4 = new java.lang.Object[r4];	 Catch:{ all -> 0x05c6 }
            com.taobao.accs.utl.ALog.e(r0, r2, r4);	 Catch:{ all -> 0x05c6 }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x05c6 }
            r0 = r0.t;	 Catch:{ all -> 0x05c6 }
            r0.wait();	 Catch:{ all -> 0x05c6 }
            monitor-exit(r3);	 Catch:{ all -> 0x05c6 }
        L_0x05c5:
            throw r1;
        L_0x05c6:
            r0 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x05c6 }
            throw r0;	 Catch:{ Throwable -> 0x05c9 }
        L_0x05c9:
            r0 = move-exception;
            r2 = r13.d;
            r3 = " run finally error";
            r4 = new java.lang.Object[r8];
            com.taobao.accs.utl.ALog.e(r2, r3, r0, r4);
            goto L_0x05c5;
        L_0x05d5:
            r0 = r13.d;	 Catch:{ Throwable -> 0x05c9 }
            r2 = "send succ, remove it";
            r3 = 0;
            r3 = new java.lang.Object[r3];	 Catch:{ Throwable -> 0x05c9 }
            com.taobao.accs.utl.ALog.d(r0, r2, r3);	 Catch:{ Throwable -> 0x05c9 }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ Throwable -> 0x05c9 }
            r2 = r0.t;	 Catch:{ Throwable -> 0x05c9 }
            monitor-enter(r2);	 Catch:{ Throwable -> 0x05c9 }
            r0 = com.taobao.accs.net.r.this;	 Catch:{ all -> 0x05f2 }
            r0 = r0.t;	 Catch:{ all -> 0x05f2 }
            r0.remove(r6);	 Catch:{ all -> 0x05f2 }
            monitor-exit(r2);	 Catch:{ all -> 0x05f2 }
            goto L_0x05c5;
        L_0x05f2:
            r0 = move-exception;
            monitor-exit(r2);	 Catch:{ all -> 0x05f2 }
            throw r0;	 Catch:{ Throwable -> 0x05c9 }
        L_0x05f5:
            r0 = move-exception;
            r7 = r1;
            r1 = r0;
            goto L_0x0531;
        L_0x05fa:
            r0 = move-exception;
            goto L_0x03a5;
        L_0x05fd:
            r0 = r6;
            goto L_0x0027;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.net.r.a.run():void");
        }
    }

    public r(Context context, int i, String str) {
        super(context, i, str);
        r();
    }

    public void a() {
        this.v = true;
        a(this.d);
        if (this.u == null) {
            ALog.i(d(), this.c + " start thread", new Object[0]);
            this.u = new a("NetworkThread_" + this.m);
            this.u.setPriority(2);
            this.u.start();
        }
        a(false, false);
    }

    protected void a(Message message, boolean z) {
        if (!this.v || message == null) {
            ALog.e(d(), "not running or msg null! " + this.v, new Object[0]);
            return;
        }
        try {
            if (ThreadPoolExecutorFactory.getScheduledExecutor().getQueue().size() > 1000) {
                throw new RejectedExecutionException("accs");
            }
            ScheduledFuture schedule = ThreadPoolExecutorFactory.getScheduledExecutor().schedule(new s(this, message, z), message.O, TimeUnit.MILLISECONDS);
            if (message.a() == 1 && message.M != null) {
                if (message.c()) {
                    a(message.M);
                }
                this.e.a.put(message.M, schedule);
            }
            if (message.e() != null) {
                message.e().setDeviceId(UtilityImpl.getDeviceId(this.d));
                message.e().setConnType(this.c);
                message.e().onEnterQueueData();
            }
        } catch (RejectedExecutionException e) {
            this.e.a(message, (int) ErrorCode.MESSAGE_QUEUE_FULL);
            ALog.e(d(), this.c + "send queue full count:" + ThreadPoolExecutorFactory.getScheduledExecutor().getQueue().size(), new Object[0]);
        } catch (Throwable th) {
            this.e.a(message, -8);
            ALog.e(d(), this.c + "send error", th, new Object[0]);
        }
    }

    public void e() {
        super.e();
        this.v = false;
        l();
        if (this.H != null) {
            this.H.setCloseReason("shut down");
        }
        synchronized (this.t) {
            try {
                this.t.notifyAll();
            } catch (Exception e) {
            }
        }
        ALog.e(d(), this.c + "shut down", new Object[0]);
    }

    public void a(boolean z, boolean z2) {
        ALog.d(d(), "try ping, force:" + z, new Object[0]);
        if (this.c == 1) {
            ALog.d(d(), "INAPP, skip", new Object[0]);
        } else {
            b(Message.a(z, (int) (z2 ? (Math.random() * 10.0d) * 1000.0d : 0.0d)), z);
        }
    }

    public void l() {
        ALog.e(d(), this.c + " force close!", new Object[0]);
        try {
            this.z.closeSession();
            this.H.setCloseType(1);
        } catch (Exception e) {
        }
        c(3);
    }

    public c c() {
        int i = 0;
        if (this.I == null) {
            this.I = new c();
        }
        this.I.b = this.c;
        this.I.d = this.t.size();
        this.I.i = UtilityImpl.isNetworkConnected(this.d);
        this.I.f = this.K;
        this.I.a = this.s;
        this.I.c = this.H == null ? false : this.H.getRet();
        this.I.j = n();
        c cVar = this.I;
        if (this.e != null) {
            i = this.e.d();
        }
        cVar.e = i;
        this.I.g = this.x;
        return this.I;
    }

    private void a(Message message) {
        if (message.t != null && this.t.size() != 0) {
            for (int size = this.t.size() - 1; size >= 0; size--) {
                Message message2 = (Message) this.t.get(size);
                if (!(message2 == null || message2.t == null || !message2.f().equals(message.f()))) {
                    switch (message.t.intValue()) {
                        case 1:
                        case 2:
                            if (message2.t.intValue() == 1 || message2.t.intValue() == 2) {
                                this.t.remove(size);
                                break;
                            }
                        case 3:
                        case 4:
                            if (message2.t.intValue() == 3 || message2.t.intValue() == 4) {
                                this.t.remove(size);
                                break;
                            }
                        case 5:
                        case 6:
                            if (message2.t.intValue() == 5 || message2.t.intValue() == 6) {
                                this.t.remove(size);
                                break;
                            }
                    }
                    ALog.d(d(), "clearRepeatControlCommand message:" + message2.t + "/" + message2.f(), new Object[0]);
                }
            }
            if (this.e != null) {
                this.e.b(message);
            }
        }
    }

    private void d(String str) {
        int i = Constants.PORT;
        if (this.s != 2 && this.s != 1) {
            if (this.M == null) {
                this.M = new h(m());
            }
            List<IConnStrategy> a = this.M.a(m());
            if (a == null || a.size() <= 0) {
                if (str != null) {
                    this.o = str;
                } else {
                    this.o = m();
                }
                if (System.currentTimeMillis() % 2 == 0) {
                    i = 80;
                }
                this.p = i;
                b.a("accs", BaseMonitor.COUNT_POINT_DNS, "localdns", 0.0d);
                ALog.i(d(), this.c + " get ip from amdc fail!!", new Object[0]);
            } else {
                for (IConnStrategy iConnStrategy : a) {
                    if (iConnStrategy != null) {
                        ALog.e(d(), this.c + " connect strategys ip:" + iConnStrategy.getIp() + " port:" + iConnStrategy.getPort(), new Object[0]);
                    }
                }
                if (this.L) {
                    this.M.b();
                    this.L = false;
                }
                IConnStrategy a2 = this.M.a();
                this.o = a2 == null ? m() : a2.getIp();
                this.p = a2 == null ? Constants.PORT : a2.getPort();
                b.a("accs", BaseMonitor.COUNT_POINT_DNS, "httpdns", 0.0d);
                ALog.e(d(), this.c + " get ip from amdc succ:" + this.o + ":" + this.p + " originPos:" + this.M.c(), new Object[0]);
            }
            this.w = "https://" + this.o + ":" + this.p + "/accs/";
            ALog.e(d(), this.c + " connect URL:" + this.w, new Object[0]);
            this.N = String.valueOf(System.currentTimeMillis());
            if (this.H != null) {
                AppMonitor.getInstance().commitStat(this.H);
            }
            this.H = new SessionMonitor();
            this.H.setConnectType(this.c == 0 ? "service" : "inapp");
            if (this.y != null) {
                try {
                    this.D = System.currentTimeMillis();
                    this.E = System.nanoTime();
                    this.q = UtilityImpl.getProxyHost(this.d);
                    this.r = UtilityImpl.getProxyPort(this.d);
                    this.B = System.currentTimeMillis();
                    this.H.onStartConnect();
                    synchronized (this.A) {
                        try {
                            SessionInfo sessionInfo;
                            if (TextUtils.isEmpty(this.q) || this.r < 0 || !this.J) {
                                ALog.e(d(), this.c + " connect normal", new Object[0]);
                                sessionInfo = new SessionInfo(this.o, this.p, m() + "_" + this.b, null, 0, this.N, this, 4226);
                                this.K = "";
                            } else {
                                ALog.e(d(), this.c + " connect with proxy:" + this.q + ":" + this.r, new Object[0]);
                                sessionInfo = new SessionInfo(this.o, this.p, m() + "_" + this.b, this.q, this.r, this.N, this, 4226);
                                this.K = this.q + ":" + this.r;
                            }
                            sessionInfo.setPubKeySeqNum(o());
                            sessionInfo.setConnectionTimeoutMs(b.ACCS_RECEIVE_TIMEOUT);
                            this.z = this.y.createSession(sessionInfo);
                            c(2);
                            this.H.connection_stop_date = 0;
                            this.A.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            this.J = false;
                        }
                    }
                } catch (Exception e22) {
                    e22.printStackTrace();
                }
            }
        }
    }

    private int o() {
        boolean k = k();
        if (AccsClientConfig.mEnv == 2) {
            if (k) {
            }
            return 0;
        }
        int channelPubKey = this.i.getChannelPubKey();
        if (channelPubKey > 0) {
            ALog.i(d(), "use custom pub key", "pubKey", Integer.valueOf(channelPubKey));
            return channelPubKey;
        } else if (k) {
            return 4;
        } else {
            return 3;
        }
    }

    private void p() {
        if (this.z == null) {
            c(3);
            return;
        }
        try {
            String encode = URLEncoder.encode(UtilityImpl.getDeviceId(this.d));
            String appsign = UtilityImpl.getAppsign(this.d, i(), this.i.getAppSecret(), UtilityImpl.getDeviceId(this.d), this.m);
            String c = c(this.w);
            ALog.e(d(), this.c + " auth URL:" + c, new Object[0]);
            this.x = c;
            if (a(encode, i(), appsign)) {
                URL url = new URL(c);
                SpdyRequest spdyRequest = new SpdyRequest(new URL(c), HttpRequest.METHOD_GET, RequestPriority.DEFAULT_PRIORITY, 80000, b.ACCS_RECEIVE_TIMEOUT);
                spdyRequest.setDomain(m());
                this.z.submitRequest(spdyRequest, new SpdyDataProvider((byte[]) null), m(), this);
                return;
            }
            ALog.e(d(), this.c + " auth param error!", new Object[0]);
            d(-6);
        } catch (Throwable th) {
            ALog.e(d(), this.c + " auth exception ", th, new Object[0]);
            d(-7);
        }
    }

    private boolean a(String str, String str2, String str3) {
        if (l.a(this.d) == 2) {
            return true;
        }
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            return true;
        }
        int i;
        int i2;
        c(3);
        if (TextUtils.isEmpty(str)) {
            i = 1;
        } else if (TextUtils.isEmpty(str2)) {
            i = 2;
        } else if (TextUtils.isEmpty(str3)) {
            i = 3;
        } else {
            i = 1;
        }
        this.H.setFailReason(i);
        this.H.onConnectStop();
        String str4 = this.c == 0 ? "service" : "inapp";
        if (this.u != null) {
            i2 = this.u.a;
        } else {
            i2 = 0;
        }
        UTMini.getInstance().commitEvent(66001, "DISCONNECT " + str4, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(Constants.SDK_VERSION_CODE), this.x, this.K);
        b.a("accs", BaseMonitor.ALARM_POINT_CONNECT, "retrytimes:" + i2, i + "", "");
        return false;
    }

    private synchronized void q() {
        if (this.c != 1) {
            this.B = System.currentTimeMillis();
            this.C = System.nanoTime();
            g.a(this.d).a();
        }
    }

    private synchronized void c(int i) {
        ALog.e(d(), this.c + " notifyStatus:" + a(i), new Object[0]);
        if (i == this.s) {
            ALog.i(d(), this.c + " ignore notifyStatus", new Object[0]);
        } else {
            this.s = i;
            switch (i) {
                case 1:
                    g.a(this.d).f();
                    q();
                    if (this.n != null) {
                        this.n.cancel(true);
                    }
                    synchronized (this.A) {
                        try {
                            this.A.notifyAll();
                        } catch (Exception e) {
                        }
                    }
                    synchronized (this.t) {
                        try {
                            this.t.notifyAll();
                        } catch (Exception e2) {
                        }
                    }
                case 2:
                    if (this.n != null) {
                        this.n.cancel(true);
                    }
                    ThreadPoolExecutorFactory.getScheduledExecutor().schedule(new t(this, this.N), 120000, TimeUnit.MILLISECONDS);
                    break;
                case 3:
                    q();
                    g.a(this.d).d();
                    synchronized (this.A) {
                        try {
                            this.A.notifyAll();
                        } catch (Exception e3) {
                        }
                    }
                    this.e.a(-10);
                    a(false, true);
                    break;
            }
            ALog.i(d(), this.c + " notifyStatus:" + a(i) + " handled", new Object[0]);
        }
    }

    public String m() {
        String channelHost = this.i.getChannelHost();
        ALog.i(d(), this.c + " getChannelHost:" + channelHost, new Object[0]);
        return channelHost == null ? "" : channelHost;
    }

    private void r() {
        try {
            SpdyAgent.enableDebug = true;
            this.y = SpdyAgent.getInstance(this.d, SpdyVersion.SPDY3, SpdySessionKind.NONE_SESSION);
            if (SpdyAgent.checkLoadSucc()) {
                f.a();
                if (!k()) {
                    this.y.setAccsSslCallback(new u(this));
                }
                if (!i.a(false)) {
                    String str = this.c == 0 ? "service" : "inapp";
                    ALog.d(d(), "into--[setTnetLogPath]", new Object[0]);
                    Object tnetLogFilePath = UtilityImpl.getTnetLogFilePath(this.d, str);
                    ALog.d(d(), "config tnet log path:" + tnetLogFilePath, new Object[0]);
                    if (!TextUtils.isEmpty(tnetLogFilePath)) {
                        this.y.configLogFile(tnetLogFilePath, 5242880, 5);
                        return;
                    }
                    return;
                }
                return;
            }
            ALog.e(d(), "loadSoFail", new Object[0]);
            f.b();
        } catch (Throwable th) {
            ALog.e(d(), "loadSoFail", th, new Object[0]);
        }
    }

    public boolean n() {
        return this.v;
    }

    public void spdySessionFailedError(SpdySession spdySession, int i, Object obj) {
        int i2;
        if (spdySession != null) {
            try {
                spdySession.cleanUp();
            } catch (Exception e) {
                ALog.e(d(), "session cleanUp has exception: " + e, new Object[0]);
            }
        }
        if (this.u != null) {
            i2 = this.u.a;
        } else {
            i2 = 0;
        }
        ALog.e(d(), this.c + " spdySessionFailedError, retryTimes:" + i2 + " errorId:" + i, new Object[0]);
        this.J = false;
        this.L = true;
        c(3);
        this.H.setFailReason(i);
        this.H.onConnectStop();
        UTMini.getInstance().commitEvent(66001, "DISCONNECT " + (this.c == 0 ? "service" : "inapp"), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(Constants.SDK_VERSION_CODE), this.x, this.K);
        b.a("accs", BaseMonitor.ALARM_POINT_CONNECT, "retrytimes:" + i2, i + "", "");
    }

    public void spdySessionConnectCB(SpdySession spdySession, SuperviseConnectInfo superviseConnectInfo) {
        this.F = superviseConnectInfo.connectTime;
        int i = superviseConnectInfo.handshakeTime;
        ALog.e(d(), this.c + " spdySessionConnectCB sessionConnectInterval:" + this.F + " sslTime:" + i + " reuse:" + superviseConnectInfo.sessionTicketReused, new Object[0]);
        p();
        if (this.u != null) {
            int i2 = this.u.a;
        }
        this.H.setRet(true);
        this.H.onConnectStop();
        this.H.tcp_time = (long) this.F;
        this.H.ssl_time = (long) i;
        UTMini.getInstance().commitEvent(66001, "CONNECTED " + (this.c == 0 ? "service" : "inapp") + " " + superviseConnectInfo.sessionTicketReused, String.valueOf(this.F), String.valueOf(i), Integer.valueOf(Constants.SDK_VERSION_CODE), String.valueOf(superviseConnectInfo.sessionTicketReused), this.x, this.K);
        b.a("accs", BaseMonitor.ALARM_POINT_CONNECT, "");
    }

    public void spdySessionCloseCallback(SpdySession spdySession, Object obj, SuperviseConnectInfo superviseConnectInfo, int i) {
        ALog.e(d(), this.c + " spdySessionCloseCallback, errorCode:" + i, new Object[0]);
        if (spdySession != null) {
            try {
                spdySession.cleanUp();
            } catch (Exception e) {
                ALog.e(d(), "session cleanUp has exception: " + e, new Object[0]);
            }
        }
        c(3);
        this.H.onCloseConnect();
        if (this.H.getConCloseDate() > 0 && this.H.getConStopDate() > 0) {
            this.H.getConCloseDate();
            this.H.getConStopDate();
        }
        this.H.setCloseReason(this.H.getCloseReason() + "tnet error:" + i);
        if (superviseConnectInfo != null) {
            this.H.live_time = (long) superviseConnectInfo.keepalive_period_second;
        }
        AppMonitor.getInstance().commitStat(this.H);
        for (Message message : this.e.e()) {
            if (message.e() != null) {
                message.e().setFailReason("session close");
                AppMonitor.getInstance().commitStat(message.e());
            }
        }
        String str = this.c == 0 ? "service" : "inapp";
        ALog.d(d(), "spdySessionCloseCallback, conKeepTime:" + this.H.live_time + " connectType:" + str, new Object[0]);
        UTMini.getInstance().commitEvent(66001, "DISCONNECT CLOSE " + str, Integer.valueOf(i), Long.valueOf(this.H.live_time), Integer.valueOf(Constants.SDK_VERSION_CODE), this.x, this.K);
    }

    public void spdyPingRecvCallback(SpdySession spdySession, long j, Object obj) {
        ALog.d(d(), "spdyPingRecvCallback uniId:" + j, new Object[0]);
        if (j >= 0) {
            this.e.b();
            g.a(this.d).e();
            g.a(this.d).a();
            this.H.onPingCBReceive();
            if (this.H.ping_rec_times % 2 == 0) {
                UtilityImpl.setServiceTime(this.d, Constants.SP_KEY_SERVICE_END, System.currentTimeMillis());
            }
        }
    }

    public void spdyCustomControlFrameRecvCallback(SpdySession spdySession, Object obj, int i, int i2, int i3, int i4, byte[] bArr) {
        q();
        ALog.e(d(), this.c + " onFrame, type:" + i2 + " len:" + bArr.length, new Object[0]);
        String str = "";
        if (ALog.isPrintLog(Level.D) && bArr.length < 512) {
            long currentTimeMillis = System.currentTimeMillis();
            String str2 = str;
            for (byte b : bArr) {
                str2 = str2 + Integer.toHexString(b & 255) + " ";
            }
            ALog.d(d(), str2 + " log time:" + (System.currentTimeMillis() - currentTimeMillis), new Object[0]);
        }
        if (i2 == 200) {
            try {
                long currentTimeMillis2 = System.currentTimeMillis();
                this.e.a(bArr);
                d f = this.e.f();
                if (f != null) {
                    f.c = String.valueOf(currentTimeMillis2);
                    f.g = this.c == 0 ? "service" : "inapp";
                    f.commitUT();
                }
            } catch (Throwable th) {
                ALog.e(d(), "onDataReceive ", th, new Object[0]);
                UTMini.getInstance().commitEvent(66001, "SERVICE_DATA_RECEIVE", UtilityImpl.getStackMsg(th));
            }
            ALog.d(d(), "try handle msg", new Object[0]);
            g();
        } else {
            ALog.e(d(), this.c + " drop frame" + " len:" + bArr.length, new Object[0]);
        }
        ALog.d(d(), "spdyCustomControlFrameRecvCallback", new Object[0]);
    }

    public void spdyStreamCloseCallback(SpdySession spdySession, long j, int i, Object obj, SuperviseData superviseData) {
        ALog.d(d(), "spdyStreamCloseCallback", new Object[0]);
        if (i != 0) {
            ALog.e(d(), "spdyStreamCloseCallback", "statusCode", Integer.valueOf(i));
            d(i);
        }
    }

    public void spdyRequestRecvCallback(SpdySession spdySession, long j, Object obj) {
        ALog.d(d(), "spdyRequestRecvCallback", new Object[0]);
    }

    public void spdyOnStreamResponse(SpdySession spdySession, long j, Map<String, List<String>> map, Object obj) {
        this.B = System.currentTimeMillis();
        this.C = System.nanoTime();
        try {
            Map header = UtilityImpl.getHeader(map);
            int parseInt = Integer.parseInt((String) header.get(":status"));
            ALog.e(d(), this.c + " spdyOnStreamResponse httpStatusCode: " + parseInt, new Object[0]);
            if (parseInt == 200) {
                c(1);
                String str = (String) header.get("x-at");
                if (!TextUtils.isEmpty(str)) {
                    this.k = str;
                }
                this.H.auth_time = this.H.connection_stop_date > 0 ? System.currentTimeMillis() - this.H.connection_stop_date : 0;
                UTMini.getInstance().commitEvent(66001, "CONNECTED 200 " + (this.c == 0 ? "service" : "inapp"), this.x, this.K, Integer.valueOf(Constants.SDK_VERSION_CODE), PushConstants.PUSH_TYPE_NOTIFY);
                b.a("accs", "auth", "");
            } else {
                d(parseInt);
            }
        } catch (Exception e) {
            ALog.e(d(), e.toString(), new Object[0]);
            l();
            this.H.setCloseReason(com.umeng.analytics.pro.b.ao);
        }
        ALog.d(d(), "spdyOnStreamResponse", new Object[0]);
    }

    private void d(int i) {
        int i2;
        this.k = null;
        l();
        if (this.u != null) {
            i2 = this.u.a;
        } else {
            i2 = 0;
        }
        this.H.setCloseReason("code not 200 is" + i);
        this.L = true;
        UTMini.getInstance().commitEvent(66001, "CONNECTED NO 200 " + (this.c == 0 ? "service" : "inapp"), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(Constants.SDK_VERSION_CODE), this.x, this.K);
        b.a("accs", "auth", "", i + "", "");
    }

    public void spdyDataSendCallback(SpdySession spdySession, boolean z, long j, int i, Object obj) {
        ALog.d(d(), "spdyDataSendCallback", new Object[0]);
    }

    public void spdyDataRecvCallback(SpdySession spdySession, boolean z, long j, int i, Object obj) {
        ALog.d(d(), "spdyDataRecvCallback", new Object[0]);
    }

    public void b() {
        this.J = false;
        this.f = 0;
    }

    public void bioPingRecvCallback(SpdySession spdySession, int i) {
        ALog.w(d(), "bioPingRecvCallback uniId:" + i, new Object[0]);
    }

    protected void a(String str, String str2) {
        try {
            c(4);
            l();
            this.H.setCloseReason(str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean a(String str) {
        boolean z;
        synchronized (this.t) {
            for (int size = this.t.size() - 1; size >= 0; size--) {
                Message message = (Message) this.t.get(size);
                if (message != null && message.a() == 1 && message.M != null && message.M.equals(str)) {
                    this.t.remove(size);
                    z = true;
                    break;
                }
            }
            z = false;
        }
        return z;
    }

    public byte[] getSSLMeta(SpdySession spdySession) {
        return UtilityImpl.SecurityGuardGetSslTicket2(this.d, this.m, this.b, spdySession.getDomain());
    }

    public int putSSLMeta(SpdySession spdySession, byte[] bArr) {
        return UtilityImpl.SecurityGuardPutSslTicket2(this.d, this.m, this.b, spdySession.getDomain(), bArr);
    }

    public void spdyDataChunkRecvCB(SpdySession spdySession, boolean z, long j, SpdyByteArray spdyByteArray, Object obj) {
        ALog.d(d(), "spdyDataChunkRecvCB", new Object[0]);
    }

    protected String d() {
        return "SilenceConn_" + this.m;
    }

    public void spdyCustomControlFrameFailCallback(SpdySession spdySession, Object obj, int i, int i2) {
        b(i);
    }

    protected boolean h() {
        return false;
    }

    protected void a(Context context) {
        if (!this.g) {
            super.a(context);
            GlobalAppRuntimeInfo.setBackground(false);
            this.g = true;
            ALog.e(d(), "init awcn success!", new Object[0]);
        }
    }
}
