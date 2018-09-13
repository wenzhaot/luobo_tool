package com.umeng.commonsdk.statistics.noise;

import android.content.Context;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.talkingdata.sdk.ab;
import com.umeng.commonsdk.proguard.g;
import com.umeng.commonsdk.statistics.idtracking.ImprintHandler;
import com.umeng.commonsdk.statistics.idtracking.ImprintHandler.a;
import com.umeng.commonsdk.statistics.internal.StatTracer;
import com.umeng.commonsdk.statistics.internal.d;

public class ImLatent implements d {
    private static ImLatent instanse = null;
    private final int LATENT_MAX = ab.I;
    private final int LATENT_WINDOW = 10;
    private final long _360HOURS_IN_MS = 1296000000;
    private final long _36HOURS_IN_MS = 129600000;
    private final int _DEFAULT_HOURS = 360;
    private final int _DEFAULT_MAX_LATENT = 1800;
    private final int _DEFAULT_MIN_HOURS = 36;
    private final int _DEFAULT_MIN_LATENT = 1;
    private final long _ONE_HOURS_IN_MS = 3600000;
    private Context context;
    private long latentHour = 1296000000;
    private int latentWindow = 10;
    private long mDelay = 0;
    private long mElapsed = 0;
    private boolean mLatentActivite = false;
    private Object mLatentLock = new Object();
    private StatTracer statTracer;
    private com.umeng.commonsdk.statistics.common.d storeHelper;

    public static synchronized ImLatent getService(Context context, StatTracer statTracer) {
        ImLatent imLatent;
        synchronized (ImLatent.class) {
            if (instanse == null) {
                instanse = new ImLatent(context, statTracer);
                instanse.onImprintChanged(ImprintHandler.getImprintService(context).b());
            }
            imLatent = instanse;
        }
        return imLatent;
    }

    private ImLatent(Context context, StatTracer statTracer) {
        this.context = context;
        this.storeHelper = com.umeng.commonsdk.statistics.common.d.a(context);
        this.statTracer = statTracer;
    }

    /* JADX WARNING: Missing block: B:15:0x0020, code:
            r2 = java.lang.System.currentTimeMillis() - r8.statTracer.getLastReqTime();
     */
    /* JADX WARNING: Missing block: B:16:0x0030, code:
            if (r2 <= r8.latentHour) goto L_0x004f;
     */
    /* JADX WARNING: Missing block: B:17:0x0032, code:
            r0 = com.umeng.commonsdk.statistics.idtracking.Envelope.getSignature(r8.context);
            r4 = r8.mLatentLock;
     */
    /* JADX WARNING: Missing block: B:18:0x003a, code:
            monitor-enter(r4);
     */
    /* JADX WARNING: Missing block: B:20:?, code:
            r8.mDelay = (long) com.umeng.commonsdk.statistics.common.DataHelper.random(r8.latentWindow, r0);
            r8.mElapsed = r2;
            r8.mLatentActivite = true;
     */
    /* JADX WARNING: Missing block: B:21:0x0049, code:
            monitor-exit(r4);
     */
    /* JADX WARNING: Missing block: B:27:0x0054, code:
            if (r2 <= 129600000) goto L_?;
     */
    /* JADX WARNING: Missing block: B:28:0x0056, code:
            r4 = r8.mLatentLock;
     */
    /* JADX WARNING: Missing block: B:29:0x0058, code:
            monitor-enter(r4);
     */
    /* JADX WARNING: Missing block: B:32:?, code:
            r8.mDelay = 0;
            r8.mElapsed = r2;
            r8.mLatentActivite = true;
     */
    /* JADX WARNING: Missing block: B:33:0x0062, code:
            monitor-exit(r4);
     */
    /* JADX WARNING: Missing block: B:40:?, code:
            return true;
     */
    /* JADX WARNING: Missing block: B:41:?, code:
            return false;
     */
    /* JADX WARNING: Missing block: B:42:?, code:
            return true;
     */
    public boolean shouldStartLatency() {
        /*
        r8 = this;
        r1 = 1;
        r0 = 0;
        r2 = r8.storeHelper;
        r2 = r2.c();
        if (r2 == 0) goto L_0x000b;
    L_0x000a:
        return r0;
    L_0x000b:
        r2 = r8.statTracer;
        r2 = r2.isFirstRequest();
        if (r2 != 0) goto L_0x000a;
    L_0x0013:
        r2 = r8.mLatentLock;
        monitor-enter(r2);
        r3 = r8.mLatentActivite;	 Catch:{ all -> 0x001c }
        if (r3 == 0) goto L_0x001f;
    L_0x001a:
        monitor-exit(r2);	 Catch:{ all -> 0x001c }
        goto L_0x000a;
    L_0x001c:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x001c }
        throw r0;
    L_0x001f:
        monitor-exit(r2);	 Catch:{ all -> 0x001c }
        r2 = r8.statTracer;
        r2 = r2.getLastReqTime();
        r4 = java.lang.System.currentTimeMillis();
        r2 = r4 - r2;
        r4 = r8.latentHour;
        r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r4 <= 0) goto L_0x004f;
    L_0x0032:
        r0 = r8.context;
        r0 = com.umeng.commonsdk.statistics.idtracking.Envelope.getSignature(r0);
        r4 = r8.mLatentLock;
        monitor-enter(r4);
        r5 = r8.latentWindow;	 Catch:{ all -> 0x004c }
        r0 = com.umeng.commonsdk.statistics.common.DataHelper.random(r5, r0);	 Catch:{ all -> 0x004c }
        r6 = (long) r0;	 Catch:{ all -> 0x004c }
        r8.mDelay = r6;	 Catch:{ all -> 0x004c }
        r8.mElapsed = r2;	 Catch:{ all -> 0x004c }
        r0 = 1;
        r8.mLatentActivite = r0;	 Catch:{ all -> 0x004c }
        monitor-exit(r4);	 Catch:{ all -> 0x004c }
        r0 = r1;
        goto L_0x000a;
    L_0x004c:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x004c }
        throw r0;
    L_0x004f:
        r4 = 129600000; // 0x7b98a00 float:2.7916815E-34 double:6.40309077E-316;
        r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r4 <= 0) goto L_0x000a;
    L_0x0056:
        r4 = r8.mLatentLock;
        monitor-enter(r4);
        r6 = 0;
        r8.mDelay = r6;	 Catch:{ all -> 0x0065 }
        r8.mElapsed = r2;	 Catch:{ all -> 0x0065 }
        r0 = 1;
        r8.mLatentActivite = r0;	 Catch:{ all -> 0x0065 }
        monitor-exit(r4);	 Catch:{ all -> 0x0065 }
        r0 = r1;
        goto L_0x000a;
    L_0x0065:
        r0 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0065 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.statistics.noise.ImLatent.shouldStartLatency():boolean");
    }

    public boolean isLatentActivite() {
        boolean z;
        synchronized (this.mLatentLock) {
            z = this.mLatentActivite;
        }
        return z;
    }

    public void latentDeactivite() {
        synchronized (this.mLatentLock) {
            this.mLatentActivite = false;
        }
    }

    public long getDelayTime() {
        long j;
        synchronized (this.mLatentLock) {
            j = this.mDelay;
        }
        return j;
    }

    public long getElapsedTime() {
        return this.mElapsed;
    }

    public void onImprintChanged(a aVar) {
        int i = 360;
        int intValue = Integer.valueOf(aVar.a("latent_hours", String.valueOf(360))).intValue();
        if (intValue > 36) {
            i = intValue;
        }
        this.latentHour = ((long) i) * 3600000;
        i = Integer.valueOf(aVar.a(g.ax, PushConstants.PUSH_TYPE_NOTIFY)).intValue();
        if (i < 1 || i > 1800) {
            i = 0;
        }
        if (i != 0) {
            this.latentWindow = i;
        } else if (com.umeng.commonsdk.statistics.a.c <= 0 || com.umeng.commonsdk.statistics.a.c > ab.I) {
            this.latentWindow = 10;
        } else {
            this.latentWindow = com.umeng.commonsdk.statistics.a.c;
        }
    }
}
