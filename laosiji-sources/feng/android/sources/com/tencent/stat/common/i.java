package com.tencent.stat.common;

class i extends h {
    private static final int[] c = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    private static final int[] d = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    private int e;
    private int f;
    private final int[] g;

    public i(int i, byte[] bArr) {
        this.a = bArr;
        this.g = (i & 8) == 0 ? c : d;
        this.e = 0;
        this.f = 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:63:0x005c A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0105  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x005f  */
    public boolean a(byte[] r11, int r12, int r13, boolean r14) {
        /*
        r10 = this;
        r0 = r10.e;
        r1 = 6;
        if (r0 != r1) goto L_0x0007;
    L_0x0005:
        r0 = 0;
    L_0x0006:
        return r0;
    L_0x0007:
        r4 = r13 + r12;
        r3 = r10.e;
        r1 = r10.f;
        r0 = 0;
        r5 = r10.a;
        r6 = r10.g;
        r2 = r12;
    L_0x0013:
        if (r2 >= r4) goto L_0x0133;
    L_0x0015:
        if (r3 != 0) goto L_0x0067;
    L_0x0017:
        r7 = r2 + 4;
        if (r7 > r4) goto L_0x005a;
    L_0x001b:
        r1 = r11[r2];
        r1 = r1 & 255;
        r1 = r6[r1];
        r1 = r1 << 18;
        r7 = r2 + 1;
        r7 = r11[r7];
        r7 = r7 & 255;
        r7 = r6[r7];
        r7 = r7 << 12;
        r1 = r1 | r7;
        r7 = r2 + 2;
        r7 = r11[r7];
        r7 = r7 & 255;
        r7 = r6[r7];
        r7 = r7 << 6;
        r1 = r1 | r7;
        r7 = r2 + 3;
        r7 = r11[r7];
        r7 = r7 & 255;
        r7 = r6[r7];
        r1 = r1 | r7;
        if (r1 < 0) goto L_0x005a;
    L_0x0044:
        r7 = r0 + 2;
        r8 = (byte) r1;
        r5[r7] = r8;
        r7 = r0 + 1;
        r8 = r1 >> 8;
        r8 = (byte) r8;
        r5[r7] = r8;
        r7 = r1 >> 16;
        r7 = (byte) r7;
        r5[r0] = r7;
        r0 = r0 + 3;
        r2 = r2 + 4;
        goto L_0x0017;
    L_0x005a:
        if (r2 < r4) goto L_0x0067;
    L_0x005c:
        r2 = r1;
    L_0x005d:
        if (r14 != 0) goto L_0x0105;
    L_0x005f:
        r10.e = r3;
        r10.f = r2;
        r10.b = r0;
        r0 = 1;
        goto L_0x0006;
    L_0x0067:
        r12 = r2 + 1;
        r2 = r11[r2];
        r2 = r2 & 255;
        r2 = r6[r2];
        switch(r3) {
            case 0: goto L_0x0076;
            case 1: goto L_0x0086;
            case 2: goto L_0x0097;
            case 3: goto L_0x00b7;
            case 4: goto L_0x00ed;
            case 5: goto L_0x00fc;
            default: goto L_0x0072;
        };
    L_0x0072:
        r2 = r3;
    L_0x0073:
        r3 = r2;
        r2 = r12;
        goto L_0x0013;
    L_0x0076:
        if (r2 < 0) goto L_0x007e;
    L_0x0078:
        r1 = r3 + 1;
        r9 = r2;
        r2 = r1;
        r1 = r9;
        goto L_0x0073;
    L_0x007e:
        r7 = -1;
        if (r2 == r7) goto L_0x0072;
    L_0x0081:
        r0 = 6;
        r10.e = r0;
        r0 = 0;
        goto L_0x0006;
    L_0x0086:
        if (r2 < 0) goto L_0x008e;
    L_0x0088:
        r1 = r1 << 6;
        r1 = r1 | r2;
        r2 = r3 + 1;
        goto L_0x0073;
    L_0x008e:
        r7 = -1;
        if (r2 == r7) goto L_0x0072;
    L_0x0091:
        r0 = 6;
        r10.e = r0;
        r0 = 0;
        goto L_0x0006;
    L_0x0097:
        if (r2 < 0) goto L_0x009f;
    L_0x0099:
        r1 = r1 << 6;
        r1 = r1 | r2;
        r2 = r3 + 1;
        goto L_0x0073;
    L_0x009f:
        r7 = -2;
        if (r2 != r7) goto L_0x00ae;
    L_0x00a2:
        r2 = r0 + 1;
        r3 = r1 >> 4;
        r3 = (byte) r3;
        r5[r0] = r3;
        r0 = 4;
        r9 = r2;
        r2 = r0;
        r0 = r9;
        goto L_0x0073;
    L_0x00ae:
        r7 = -1;
        if (r2 == r7) goto L_0x0072;
    L_0x00b1:
        r0 = 6;
        r10.e = r0;
        r0 = 0;
        goto L_0x0006;
    L_0x00b7:
        if (r2 < 0) goto L_0x00d1;
    L_0x00b9:
        r1 = r1 << 6;
        r1 = r1 | r2;
        r2 = r0 + 2;
        r3 = (byte) r1;
        r5[r2] = r3;
        r2 = r0 + 1;
        r3 = r1 >> 8;
        r3 = (byte) r3;
        r5[r2] = r3;
        r2 = r1 >> 16;
        r2 = (byte) r2;
        r5[r0] = r2;
        r0 = r0 + 3;
        r2 = 0;
        goto L_0x0073;
    L_0x00d1:
        r7 = -2;
        if (r2 != r7) goto L_0x00e4;
    L_0x00d4:
        r2 = r0 + 1;
        r3 = r1 >> 2;
        r3 = (byte) r3;
        r5[r2] = r3;
        r2 = r1 >> 10;
        r2 = (byte) r2;
        r5[r0] = r2;
        r0 = r0 + 2;
        r2 = 5;
        goto L_0x0073;
    L_0x00e4:
        r7 = -1;
        if (r2 == r7) goto L_0x0072;
    L_0x00e7:
        r0 = 6;
        r10.e = r0;
        r0 = 0;
        goto L_0x0006;
    L_0x00ed:
        r7 = -2;
        if (r2 != r7) goto L_0x00f3;
    L_0x00f0:
        r2 = r3 + 1;
        goto L_0x0073;
    L_0x00f3:
        r7 = -1;
        if (r2 == r7) goto L_0x0072;
    L_0x00f6:
        r0 = 6;
        r10.e = r0;
        r0 = 0;
        goto L_0x0006;
    L_0x00fc:
        r7 = -1;
        if (r2 == r7) goto L_0x0072;
    L_0x00ff:
        r0 = 6;
        r10.e = r0;
        r0 = 0;
        goto L_0x0006;
    L_0x0105:
        switch(r3) {
            case 0: goto L_0x0108;
            case 1: goto L_0x010f;
            case 2: goto L_0x0115;
            case 3: goto L_0x011e;
            case 4: goto L_0x012d;
            default: goto L_0x0108;
        };
    L_0x0108:
        r10.e = r3;
        r10.b = r0;
        r0 = 1;
        goto L_0x0006;
    L_0x010f:
        r0 = 6;
        r10.e = r0;
        r0 = 0;
        goto L_0x0006;
    L_0x0115:
        r1 = r0 + 1;
        r2 = r2 >> 4;
        r2 = (byte) r2;
        r5[r0] = r2;
        r0 = r1;
        goto L_0x0108;
    L_0x011e:
        r1 = r0 + 1;
        r4 = r2 >> 10;
        r4 = (byte) r4;
        r5[r0] = r4;
        r0 = r1 + 1;
        r2 = r2 >> 2;
        r2 = (byte) r2;
        r5[r1] = r2;
        goto L_0x0108;
    L_0x012d:
        r0 = 6;
        r10.e = r0;
        r0 = 0;
        goto L_0x0006;
    L_0x0133:
        r2 = r1;
        goto L_0x005d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.stat.common.i.a(byte[], int, int, boolean):boolean");
    }
}
