package com.umeng.commonsdk.statistics.proto;

import com.umeng.commonsdk.proguard.ae;
import com.umeng.commonsdk.proguard.af;
import com.umeng.commonsdk.proguard.ak;
import com.umeng.commonsdk.proguard.al;
import com.umeng.commonsdk.proguard.an;
import com.umeng.commonsdk.proguard.ap;
import com.umeng.commonsdk.proguard.aq;
import com.umeng.commonsdk.proguard.as;
import com.umeng.commonsdk.proguard.at;
import com.umeng.commonsdk.proguard.au;
import com.umeng.commonsdk.proguard.av;
import com.umeng.commonsdk.proguard.aw;
import com.umeng.commonsdk.proguard.i;
import com.umeng.commonsdk.proguard.l;
import com.umeng.commonsdk.proguard.r;
import com.umeng.commonsdk.proguard.s;
import com.umeng.commonsdk.proguard.x;
import com.umeng.commonsdk.proguard.y;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: IdSnapshot */
public class b implements l<b, e>, Serializable, Cloneable {
    public static final Map<e, x> d;
    private static final long e = -6496538196005191531L;
    private static final ap f = new ap("IdSnapshot");
    private static final af g = new af("identity", (byte) 11, (short) 1);
    private static final af h = new af("ts", (byte) 10, (short) 2);
    private static final af i = new af("version", (byte) 8, (short) 3);
    private static final Map<Class<? extends as>, at> j = new HashMap();
    private static final int k = 0;
    private static final int l = 1;
    public String a;
    public long b;
    public int c;
    private byte m;

    /* compiled from: IdSnapshot */
    private static class a extends au<b> {
        private a() {
        }

        /* renamed from: a */
        public void b(ak akVar, b bVar) throws r {
            akVar.j();
            while (true) {
                af l = akVar.l();
                if (l.b == (byte) 0) {
                    akVar.k();
                    if (!bVar.g()) {
                        throw new al("Required field 'ts' was not found in serialized data! Struct: " + toString());
                    } else if (bVar.j()) {
                        bVar.k();
                        return;
                    } else {
                        throw new al("Required field 'version' was not found in serialized data! Struct: " + toString());
                    }
                }
                switch (l.c) {
                    case (short) 1:
                        if (l.b != (byte) 11) {
                            an.a(akVar, l.b);
                            break;
                        }
                        bVar.a = akVar.z();
                        bVar.a(true);
                        break;
                    case (short) 2:
                        if (l.b != (byte) 10) {
                            an.a(akVar, l.b);
                            break;
                        }
                        bVar.b = akVar.x();
                        bVar.b(true);
                        break;
                    case (short) 3:
                        if (l.b != (byte) 8) {
                            an.a(akVar, l.b);
                            break;
                        }
                        bVar.c = akVar.w();
                        bVar.c(true);
                        break;
                    default:
                        an.a(akVar, l.b);
                        break;
                }
                akVar.m();
            }
        }

        /* renamed from: b */
        public void a(ak akVar, b bVar) throws r {
            bVar.k();
            akVar.a(b.f);
            if (bVar.a != null) {
                akVar.a(b.g);
                akVar.a(bVar.a);
                akVar.c();
            }
            akVar.a(b.h);
            akVar.a(bVar.b);
            akVar.c();
            akVar.a(b.i);
            akVar.a(bVar.c);
            akVar.c();
            akVar.d();
            akVar.b();
        }
    }

    /* compiled from: IdSnapshot */
    private static class b implements at {
        private b() {
        }

        /* renamed from: a */
        public a b() {
            return new a();
        }
    }

    /* compiled from: IdSnapshot */
    private static class c extends av<b> {
        private c() {
        }

        public void a(ak akVar, b bVar) throws r {
            aq aqVar = (aq) akVar;
            aqVar.a(bVar.a);
            aqVar.a(bVar.b);
            aqVar.a(bVar.c);
        }

        public void b(ak akVar, b bVar) throws r {
            aq aqVar = (aq) akVar;
            bVar.a = aqVar.z();
            bVar.a(true);
            bVar.b = aqVar.x();
            bVar.b(true);
            bVar.c = aqVar.w();
            bVar.c(true);
        }
    }

    /* compiled from: IdSnapshot */
    private static class d implements at {
        private d() {
        }

        /* renamed from: a */
        public c b() {
            return new c();
        }
    }

    /* compiled from: IdSnapshot */
    public enum e implements s {
        IDENTITY((short) 1, "identity"),
        TS((short) 2, "ts"),
        VERSION((short) 3, "version");
        
        private static final Map<String, e> d = null;
        private final short e;
        private final String f;

        static {
            d = new HashMap();
            Iterator it = EnumSet.allOf(e.class).iterator();
            while (it.hasNext()) {
                e eVar = (e) it.next();
                d.put(eVar.b(), eVar);
            }
        }

        public static e a(int i) {
            switch (i) {
                case 1:
                    return IDENTITY;
                case 2:
                    return TS;
                case 3:
                    return VERSION;
                default:
                    return null;
            }
        }

        public static e b(int i) {
            e a = a(i);
            if (a != null) {
                return a;
            }
            throw new IllegalArgumentException("Field " + i + " doesn't exist!");
        }

        public static e a(String str) {
            return (e) d.get(str);
        }

        private e(short s, String str) {
            this.e = s;
            this.f = str;
        }

        public short a() {
            return this.e;
        }

        public String b() {
            return this.f;
        }
    }

    static {
        j.put(au.class, new b());
        j.put(av.class, new d());
        Map enumMap = new EnumMap(e.class);
        enumMap.put(e.IDENTITY, new x("identity", (byte) 1, new y((byte) 11)));
        enumMap.put(e.TS, new x("ts", (byte) 1, new y((byte) 10)));
        enumMap.put(e.VERSION, new x("version", (byte) 1, new y((byte) 8)));
        d = Collections.unmodifiableMap(enumMap);
        x.a(b.class, d);
    }

    public b() {
        this.m = (byte) 0;
    }

    public b(String str, long j, int i) {
        this();
        this.a = str;
        this.b = j;
        b(true);
        this.c = i;
        c(true);
    }

    public b(b bVar) {
        this.m = (byte) 0;
        this.m = bVar.m;
        if (bVar.d()) {
            this.a = bVar.a;
        }
        this.b = bVar.b;
        this.c = bVar.c;
    }

    /* renamed from: a */
    public b deepCopy() {
        return new b(this);
    }

    public void clear() {
        this.a = null;
        b(false);
        this.b = 0;
        c(false);
        this.c = 0;
    }

    public String b() {
        return this.a;
    }

    public b a(String str) {
        this.a = str;
        return this;
    }

    public void c() {
        this.a = null;
    }

    public boolean d() {
        return this.a != null;
    }

    public void a(boolean z) {
        if (!z) {
            this.a = null;
        }
    }

    public long e() {
        return this.b;
    }

    public b a(long j) {
        this.b = j;
        b(true);
        return this;
    }

    public void f() {
        this.m = i.b(this.m, 0);
    }

    public boolean g() {
        return i.a(this.m, 0);
    }

    public void b(boolean z) {
        this.m = i.a(this.m, 0, z);
    }

    public int h() {
        return this.c;
    }

    public b a(int i) {
        this.c = i;
        c(true);
        return this;
    }

    public void i() {
        this.m = i.b(this.m, 1);
    }

    public boolean j() {
        return i.a(this.m, 1);
    }

    public void c(boolean z) {
        this.m = i.a(this.m, 1, z);
    }

    /* renamed from: b */
    public e fieldForId(int i) {
        return e.a(i);
    }

    public void read(ak akVar) throws r {
        ((at) j.get(akVar.D())).b().b(akVar, this);
    }

    public void write(ak akVar) throws r {
        ((at) j.get(akVar.D())).b().a(akVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("IdSnapshot(");
        stringBuilder.append("identity:");
        if (this.a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.a);
        }
        stringBuilder.append(", ");
        stringBuilder.append("ts:");
        stringBuilder.append(this.b);
        stringBuilder.append(", ");
        stringBuilder.append("version:");
        stringBuilder.append(this.c);
        stringBuilder.append(com.umeng.message.proguard.l.t);
        return stringBuilder.toString();
    }

    public void k() throws r {
        if (this.a == null) {
            throw new al("Required field 'identity' was not present! Struct: " + toString());
        }
    }

    private void a(ObjectOutputStream objectOutputStream) throws IOException {
        try {
            write(new ae(new aw((OutputStream) objectOutputStream)));
        } catch (r e) {
            throw new IOException(e.getMessage());
        }
    }

    private void a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.m = (byte) 0;
            read(new ae(new aw((InputStream) objectInputStream)));
        } catch (r e) {
            throw new IOException(e.getMessage());
        }
    }
}
