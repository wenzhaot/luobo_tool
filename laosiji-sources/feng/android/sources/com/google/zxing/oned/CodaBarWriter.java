package com.google.zxing.oned;

public final class CodaBarWriter extends OneDimensionalCodeWriter {
    private static final char[] ALT_START_END_CHARS = new char[]{'T', 'N', '*', 'E'};
    private static final char[] CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED = new char[]{'/', ':', '+', '.'};
    private static final char[] START_END_CHARS = new char[]{'A', 'B', 'C', 'D'};

    /* JADX WARNING: Removed duplicated region for block: B:53:0x0133  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x016a A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0165  */
    public boolean[] encode(java.lang.String r19) {
        /*
        r18 = this;
        r15 = r19.length();
        r16 = 2;
        r0 = r16;
        if (r15 >= r0) goto L_0x0013;
    L_0x000a:
        r15 = new java.lang.IllegalArgumentException;
        r16 = "Codabar should start/end with start/stop symbols";
        r15.<init>(r16);
        throw r15;
    L_0x0013:
        r15 = 0;
        r0 = r19;
        r15 = r0.charAt(r15);
        r6 = java.lang.Character.toUpperCase(r15);
        r15 = r19.length();
        r15 = r15 + -1;
        r0 = r19;
        r15 = r0.charAt(r15);
        r9 = java.lang.Character.toUpperCase(r15);
        r15 = START_END_CHARS;
        r15 = com.google.zxing.oned.CodaBarReader.arrayContains(r15, r6);
        if (r15 == 0) goto L_0x0085;
    L_0x0036:
        r15 = START_END_CHARS;
        r15 = com.google.zxing.oned.CodaBarReader.arrayContains(r15, r9);
        if (r15 == 0) goto L_0x0085;
    L_0x003e:
        r14 = 1;
    L_0x003f:
        r15 = ALT_START_END_CHARS;
        r15 = com.google.zxing.oned.CodaBarReader.arrayContains(r15, r6);
        if (r15 == 0) goto L_0x0087;
    L_0x0047:
        r15 = ALT_START_END_CHARS;
        r15 = com.google.zxing.oned.CodaBarReader.arrayContains(r15, r9);
        if (r15 == 0) goto L_0x0087;
    L_0x004f:
        r13 = 1;
    L_0x0050:
        if (r14 != 0) goto L_0x0089;
    L_0x0052:
        if (r13 != 0) goto L_0x0089;
    L_0x0054:
        r15 = new java.lang.IllegalArgumentException;
        r16 = new java.lang.StringBuilder;
        r16.<init>();
        r17 = "Codabar should start/end with ";
        r16 = r16.append(r17);
        r17 = START_END_CHARS;
        r17 = java.util.Arrays.toString(r17);
        r16 = r16.append(r17);
        r17 = ", or start/end with ";
        r16 = r16.append(r17);
        r17 = ALT_START_END_CHARS;
        r17 = java.util.Arrays.toString(r17);
        r16 = r16.append(r17);
        r16 = r16.toString();
        r15.<init>(r16);
        throw r15;
    L_0x0085:
        r14 = 0;
        goto L_0x003f;
    L_0x0087:
        r13 = 0;
        goto L_0x0050;
    L_0x0089:
        r12 = 20;
        r7 = 1;
    L_0x008c:
        r15 = r19.length();
        r15 = r15 + -1;
        if (r7 >= r15) goto L_0x00f4;
    L_0x0094:
        r0 = r19;
        r15 = r0.charAt(r7);
        r15 = java.lang.Character.isDigit(r15);
        if (r15 != 0) goto L_0x00b8;
    L_0x00a0:
        r0 = r19;
        r15 = r0.charAt(r7);
        r16 = 45;
        r0 = r16;
        if (r15 == r0) goto L_0x00b8;
    L_0x00ac:
        r0 = r19;
        r15 = r0.charAt(r7);
        r16 = 36;
        r0 = r16;
        if (r15 != r0) goto L_0x00bd;
    L_0x00b8:
        r12 = r12 + 9;
    L_0x00ba:
        r7 = r7 + 1;
        goto L_0x008c;
    L_0x00bd:
        r15 = CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED;
        r0 = r19;
        r16 = r0.charAt(r7);
        r15 = com.google.zxing.oned.CodaBarReader.arrayContains(r15, r16);
        if (r15 == 0) goto L_0x00ce;
    L_0x00cb:
        r12 = r12 + 10;
        goto L_0x00ba;
    L_0x00ce:
        r15 = new java.lang.IllegalArgumentException;
        r16 = new java.lang.StringBuilder;
        r16.<init>();
        r17 = "Cannot encode : '";
        r16 = r16.append(r17);
        r0 = r19;
        r17 = r0.charAt(r7);
        r16 = r16.append(r17);
        r17 = 39;
        r16 = r16.append(r17);
        r16 = r16.toString();
        r15.<init>(r16);
        throw r15;
    L_0x00f4:
        r15 = r19.length();
        r15 = r15 + -1;
        r12 = r12 + r15;
        r11 = new boolean[r12];
        r10 = 0;
        r8 = 0;
    L_0x00ff:
        r15 = r19.length();
        if (r8 >= r15) goto L_0x016d;
    L_0x0105:
        r0 = r19;
        r15 = r0.charAt(r8);
        r2 = java.lang.Character.toUpperCase(r15);
        if (r8 == 0) goto L_0x0119;
    L_0x0111:
        r15 = r19.length();
        r15 = r15 + -1;
        if (r8 != r15) goto L_0x011c;
    L_0x0119:
        switch(r2) {
            case 42: goto L_0x014f;
            case 69: goto L_0x0152;
            case 78: goto L_0x014c;
            case 84: goto L_0x0149;
            default: goto L_0x011c;
        };
    L_0x011c:
        r3 = 0;
        r7 = 0;
    L_0x011e:
        r15 = com.google.zxing.oned.CodaBarReader.ALPHABET;
        r15 = r15.length;
        if (r7 >= r15) goto L_0x012d;
    L_0x0123:
        r15 = com.google.zxing.oned.CodaBarReader.ALPHABET;
        r15 = r15[r7];
        if (r2 != r15) goto L_0x0155;
    L_0x0129:
        r15 = com.google.zxing.oned.CodaBarReader.CHARACTER_ENCODINGS;
        r3 = r15[r7];
    L_0x012d:
        r4 = 1;
        r5 = 0;
        r1 = 0;
    L_0x0130:
        r15 = 7;
        if (r1 >= r15) goto L_0x015d;
    L_0x0133:
        r11[r10] = r4;
        r10 = r10 + 1;
        r15 = 6 - r1;
        r15 = r3 >> r15;
        r15 = r15 & 1;
        if (r15 == 0) goto L_0x0142;
    L_0x013f:
        r15 = 1;
        if (r5 != r15) goto L_0x015a;
    L_0x0142:
        if (r4 != 0) goto L_0x0158;
    L_0x0144:
        r4 = 1;
    L_0x0145:
        r1 = r1 + 1;
        r5 = 0;
        goto L_0x0130;
    L_0x0149:
        r2 = 65;
        goto L_0x011c;
    L_0x014c:
        r2 = 66;
        goto L_0x011c;
    L_0x014f:
        r2 = 67;
        goto L_0x011c;
    L_0x0152:
        r2 = 68;
        goto L_0x011c;
    L_0x0155:
        r7 = r7 + 1;
        goto L_0x011e;
    L_0x0158:
        r4 = 0;
        goto L_0x0145;
    L_0x015a:
        r5 = r5 + 1;
        goto L_0x0130;
    L_0x015d:
        r15 = r19.length();
        r15 = r15 + -1;
        if (r8 >= r15) goto L_0x016a;
    L_0x0165:
        r15 = 0;
        r11[r10] = r15;
        r10 = r10 + 1;
    L_0x016a:
        r8 = r8 + 1;
        goto L_0x00ff;
    L_0x016d:
        return r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.CodaBarWriter.encode(java.lang.String):boolean[]");
    }
}
