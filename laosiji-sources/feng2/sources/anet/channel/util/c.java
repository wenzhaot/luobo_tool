package anet.channel.util;

import java.net.MalformedURLException;
import java.net.URL;

/* compiled from: Taobao */
public class c {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private volatile boolean f = false;

    private c() {
    }

    public c(c cVar) {
        this.a = cVar.a;
        this.b = cVar.b;
        this.c = cVar.c;
        this.d = cVar.d;
        this.e = cVar.e;
        this.f = cVar.f;
    }

    /* JADX WARNING: Removed duplicated region for block: B:41:0x0096  */
    public static anet.channel.util.c a(java.lang.String r9) {
        /*
        r5 = 6;
        r6 = 5;
        r1 = 1;
        r7 = 0;
        r2 = 0;
        r0 = android.text.TextUtils.isEmpty(r9);
        if (r0 == 0) goto L_0x000d;
    L_0x000b:
        r0 = r7;
    L_0x000c:
        return r0;
    L_0x000d:
        r0 = r9.trim();
        r8 = new anet.channel.util.c;
        r8.<init>();
        r8.d = r0;
        r3 = "//";
        r3 = r0.startsWith(r3);
        if (r3 == 0) goto L_0x0051;
    L_0x0021:
        r8.a = r7;
        r5 = r2;
    L_0x0024:
        r4 = r0.length();
        r1 = r5 + 2;
        r3 = r1;
    L_0x002b:
        if (r3 >= r4) goto L_0x0047;
    L_0x002d:
        r5 = r0.charAt(r3);
        r6 = 47;
        if (r5 == r6) goto L_0x0041;
    L_0x0035:
        r6 = 58;
        if (r5 == r6) goto L_0x0041;
    L_0x0039:
        r6 = 63;
        if (r5 == r6) goto L_0x0041;
    L_0x003d:
        r6 = 35;
        if (r5 != r6) goto L_0x0075;
    L_0x0041:
        r5 = r0.substring(r1, r3);
        r8.b = r5;
    L_0x0047:
        if (r3 != r4) goto L_0x0078;
    L_0x0049:
        r0 = r0.substring(r1);
        r8.b = r0;
        r0 = r8;
        goto L_0x000c;
    L_0x0051:
        r3 = "https:";
        r4 = r2;
        r3 = r0.regionMatches(r1, r2, r3, r4, r5);
        if (r3 == 0) goto L_0x0061;
    L_0x005b:
        r1 = "https";
        r8.a = r1;
        goto L_0x0024;
    L_0x0061:
        r3 = "http:";
        r4 = r2;
        r5 = r6;
        r1 = r0.regionMatches(r1, r2, r3, r4, r5);
        if (r1 == 0) goto L_0x0073;
    L_0x006c:
        r1 = "http";
        r8.a = r1;
        r5 = r6;
        goto L_0x0024;
    L_0x0073:
        r0 = r7;
        goto L_0x000c;
    L_0x0075:
        r3 = r3 + 1;
        goto L_0x002b;
    L_0x0078:
        r1 = r2;
    L_0x0079:
        if (r3 >= r4) goto L_0x0094;
    L_0x007b:
        r5 = r0.charAt(r3);
        r6 = 47;
        if (r5 != r6) goto L_0x0089;
    L_0x0083:
        if (r1 != 0) goto L_0x0089;
    L_0x0085:
        r1 = r3;
    L_0x0086:
        r3 = r3 + 1;
        goto L_0x0079;
    L_0x0089:
        r6 = 63;
        if (r5 == r6) goto L_0x0091;
    L_0x008d:
        r6 = 35;
        if (r5 != r6) goto L_0x0086;
    L_0x0091:
        if (r1 == 0) goto L_0x0094;
    L_0x0093:
        r2 = r3;
    L_0x0094:
        if (r1 == 0) goto L_0x009e;
    L_0x0096:
        if (r2 == 0) goto L_0x00a1;
    L_0x0098:
        r0 = r0.substring(r1, r2);
        r8.c = r0;
    L_0x009e:
        r0 = r8;
        goto L_0x000c;
    L_0x00a1:
        r2 = r4;
        goto L_0x0098;
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.util.c.a(java.lang.String):anet.channel.util.c");
    }

    public String a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public String c() {
        return this.c;
    }

    public String d() {
        if (this.e == null) {
            this.e = StringUtils.concatString(this.a, HttpConstant.SCHEME_SPLIT, this.b);
        }
        return this.e;
    }

    public String e() {
        return this.d;
    }

    public URL f() {
        try {
            return new URL(this.d);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public void g() {
        this.f = true;
        if (!"http".equals(this.a)) {
            this.a = "http";
            this.d = StringUtils.concatString(this.a, ":", this.d.substring(this.d.indexOf("//")));
            this.e = null;
        }
    }

    public boolean h() {
        return this.f;
    }

    public void i() {
        this.f = true;
    }

    public void b(String str) {
        if (!this.f && !this.a.equalsIgnoreCase(str)) {
            this.a = str;
            this.d = StringUtils.concatString(str, ":", this.d.substring(this.d.indexOf("//")));
            this.e = null;
        }
    }

    public void a(String str, int i) {
        if (i != 0 && str != null) {
            int indexOf = this.d.indexOf("//") + 2;
            while (indexOf < this.d.length() && this.d.charAt(indexOf) != '/') {
                indexOf++;
            }
            StringBuilder stringBuilder = new StringBuilder(this.d.length() + str.length());
            stringBuilder.append(this.a).append(HttpConstant.SCHEME_SPLIT).append(str).append(':').append(i).append(this.d.substring(indexOf));
            this.d = stringBuilder.toString();
        }
    }

    public String toString() {
        return this.d;
    }
}
