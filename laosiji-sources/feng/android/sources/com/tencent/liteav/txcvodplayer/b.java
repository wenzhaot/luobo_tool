package com.tencent.liteav.txcvodplayer;

import android.view.View;
import java.lang.ref.WeakReference;

/* compiled from: MeasureHelper */
public final class b {
    private WeakReference<View> a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i = 0;

    public b(View view) {
        this.a = new WeakReference(view);
    }

    public void a(int i, int i2) {
        this.b = i;
        this.c = i2;
    }

    public void b(int i, int i2) {
        this.d = i;
        this.e = i2;
    }

    public void a(int i) {
        this.f = i;
    }

    /* JADX WARNING: Missing block: B:66:0x0109, code:
            if (r12 > r2) goto L_0x0028;
     */
    public void c(int r12, int r13) {
        /*
        r11 = this;
        r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r8 = 270; // 0x10e float:3.78E-43 double:1.334E-321;
        r7 = 90;
        r6 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r5 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r0 = r11.f;
        if (r0 == r7) goto L_0x0012;
    L_0x000e:
        r0 = r11.f;
        if (r0 != r8) goto L_0x0015;
    L_0x0012:
        r10 = r12;
        r12 = r13;
        r13 = r10;
    L_0x0015:
        r0 = r11.b;
        r2 = android.view.View.getDefaultSize(r0, r12);
        r0 = r11.c;
        r1 = android.view.View.getDefaultSize(r0, r13);
        r0 = r11.i;
        r3 = 3;
        if (r0 != r3) goto L_0x002d;
    L_0x0026:
        r1 = r13;
        r2 = r12;
    L_0x0028:
        r11.g = r2;
        r11.h = r1;
        return;
    L_0x002d:
        r0 = r11.b;
        if (r0 <= 0) goto L_0x0028;
    L_0x0031:
        r0 = r11.c;
        if (r0 <= 0) goto L_0x0028;
    L_0x0035:
        r3 = android.view.View.MeasureSpec.getMode(r12);
        r2 = android.view.View.MeasureSpec.getSize(r12);
        r4 = android.view.View.MeasureSpec.getMode(r13);
        r1 = android.view.View.MeasureSpec.getSize(r13);
        if (r3 != r5) goto L_0x00c8;
    L_0x0047:
        if (r4 != r5) goto L_0x00c8;
    L_0x0049:
        r0 = (float) r2;
        r3 = (float) r1;
        r4 = r0 / r3;
        r0 = r11.i;
        switch(r0) {
            case 4: goto L_0x0082;
            case 5: goto L_0x0091;
            default: goto L_0x0052;
        };
    L_0x0052:
        r0 = r11.b;
        r0 = (float) r0;
        r3 = r11.c;
        r3 = (float) r3;
        r0 = r0 / r3;
        r3 = r11.d;
        if (r3 <= 0) goto L_0x012c;
    L_0x005d:
        r3 = r11.e;
        if (r3 <= 0) goto L_0x012c;
    L_0x0061:
        r3 = r11.d;
        r3 = (float) r3;
        r0 = r0 * r3;
        r3 = r11.e;
        r3 = (float) r3;
        r0 = r0 / r3;
        r3 = r0;
    L_0x006a:
        r0 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1));
        if (r0 <= 0) goto L_0x00a0;
    L_0x006e:
        r0 = 1;
    L_0x006f:
        r4 = r11.i;
        switch(r4) {
            case 0: goto L_0x00a2;
            case 1: goto L_0x00b0;
            case 2: goto L_0x0074;
            case 3: goto L_0x0074;
            case 4: goto L_0x00a2;
            case 5: goto L_0x00a2;
            default: goto L_0x0074;
        };
    L_0x0074:
        if (r0 == 0) goto L_0x00be;
    L_0x0076:
        r0 = r11.b;
        r1 = java.lang.Math.min(r0, r2);
        r0 = (float) r1;
        r0 = r0 / r3;
        r0 = (int) r0;
    L_0x007f:
        r2 = r1;
        r1 = r0;
        goto L_0x0028;
    L_0x0082:
        r0 = 1071877689; // 0x3fe38e39 float:1.7777778 double:5.295779427E-315;
        r3 = r11.f;
        if (r3 == r7) goto L_0x008d;
    L_0x0089:
        r3 = r11.f;
        if (r3 != r8) goto L_0x012c;
    L_0x008d:
        r0 = r9 / r0;
        r3 = r0;
        goto L_0x006a;
    L_0x0091:
        r0 = 1068149419; // 0x3faaaaab float:1.3333334 double:5.277359326E-315;
        r3 = r11.f;
        if (r3 == r7) goto L_0x009c;
    L_0x0098:
        r3 = r11.f;
        if (r3 != r8) goto L_0x012c;
    L_0x009c:
        r0 = r9 / r0;
        r3 = r0;
        goto L_0x006a;
    L_0x00a0:
        r0 = 0;
        goto L_0x006f;
    L_0x00a2:
        if (r0 == 0) goto L_0x00a9;
    L_0x00a4:
        r0 = (float) r2;
        r0 = r0 / r3;
        r0 = (int) r0;
        r1 = r2;
        goto L_0x007f;
    L_0x00a9:
        r0 = (float) r1;
        r0 = r0 * r3;
        r0 = (int) r0;
        r10 = r1;
        r1 = r0;
        r0 = r10;
        goto L_0x007f;
    L_0x00b0:
        if (r0 == 0) goto L_0x00b9;
    L_0x00b2:
        r0 = (float) r1;
        r0 = r0 * r3;
        r0 = (int) r0;
        r10 = r1;
        r1 = r0;
        r0 = r10;
        goto L_0x007f;
    L_0x00b9:
        r0 = (float) r2;
        r0 = r0 / r3;
        r0 = (int) r0;
        r1 = r2;
        goto L_0x007f;
    L_0x00be:
        r0 = r11.c;
        r0 = java.lang.Math.min(r0, r1);
        r1 = (float) r0;
        r1 = r1 * r3;
        r1 = (int) r1;
        goto L_0x007f;
    L_0x00c8:
        if (r3 != r6) goto L_0x00ee;
    L_0x00ca:
        if (r4 != r6) goto L_0x00ee;
    L_0x00cc:
        r0 = r11.b;
        r0 = r0 * r1;
        r3 = r11.c;
        r3 = r3 * r2;
        if (r0 >= r3) goto L_0x00dd;
    L_0x00d4:
        r0 = r11.b;
        r0 = r0 * r1;
        r2 = r11.c;
        r2 = r0 / r2;
        goto L_0x0028;
    L_0x00dd:
        r0 = r11.b;
        r0 = r0 * r1;
        r3 = r11.c;
        r3 = r3 * r2;
        if (r0 <= r3) goto L_0x0028;
    L_0x00e5:
        r0 = r11.c;
        r0 = r0 * r2;
        r1 = r11.b;
        r1 = r0 / r1;
        goto L_0x0028;
    L_0x00ee:
        if (r3 != r6) goto L_0x00fe;
    L_0x00f0:
        r0 = r11.c;
        r0 = r0 * r2;
        r3 = r11.b;
        r13 = r0 / r3;
        if (r4 != r5) goto L_0x00fb;
    L_0x00f9:
        if (r13 > r1) goto L_0x0028;
    L_0x00fb:
        r1 = r13;
        goto L_0x0028;
    L_0x00fe:
        if (r4 != r6) goto L_0x010e;
    L_0x0100:
        r0 = r11.b;
        r0 = r0 * r1;
        r4 = r11.c;
        r12 = r0 / r4;
        if (r3 != r5) goto L_0x010b;
    L_0x0109:
        if (r12 > r2) goto L_0x0028;
    L_0x010b:
        r2 = r12;
        goto L_0x0028;
    L_0x010e:
        r12 = r11.b;
        r0 = r11.c;
        if (r4 != r5) goto L_0x012a;
    L_0x0114:
        if (r0 <= r1) goto L_0x012a;
    L_0x0116:
        r0 = r11.b;
        r0 = r0 * r1;
        r4 = r11.c;
        r12 = r0 / r4;
    L_0x011d:
        if (r3 != r5) goto L_0x010b;
    L_0x011f:
        if (r12 <= r2) goto L_0x010b;
    L_0x0121:
        r0 = r11.c;
        r0 = r0 * r2;
        r1 = r11.b;
        r1 = r0 / r1;
        goto L_0x0028;
    L_0x012a:
        r1 = r0;
        goto L_0x011d;
    L_0x012c:
        r3 = r0;
        goto L_0x006a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.txcvodplayer.b.c(int, int):void");
    }

    public int a() {
        return this.g;
    }

    public int b() {
        return this.h;
    }

    public void b(int i) {
        this.i = i;
    }
}
