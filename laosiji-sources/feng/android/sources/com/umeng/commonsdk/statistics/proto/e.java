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
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: ImprintValue */
public class e implements l<e, e>, Serializable, Cloneable {
    public static final Map<e, x> d;
    private static final long e = 7501688097813630241L;
    private static final ap f = new ap("ImprintValue");
    private static final af g = new af("value", (byte) 11, (short) 1);
    private static final af h = new af("ts", (byte) 10, (short) 2);
    private static final af i = new af("guid", (byte) 11, (short) 3);
    private static final Map<Class<? extends as>, at> j = new HashMap();
    private static final int k = 0;
    public String a;
    public long b;
    public String c;
    private byte l;
    private e[] m;

    /* compiled from: ImprintValue */
    private static class a extends au<e> {
        private a() {
        }

        /* renamed from: a */
        public void b(ak akVar, e eVar) throws r {
            akVar.j();
            while (true) {
                af l = akVar.l();
                if (l.b == (byte) 0) {
                    akVar.k();
                    if (eVar.g()) {
                        eVar.k();
                        return;
                    }
                    throw new al("Required field 'ts' was not found in serialized data! Struct: " + toString());
                }
                switch (l.c) {
                    case (short) 1:
                        if (l.b != (byte) 11) {
                            an.a(akVar, l.b);
                            break;
                        }
                        eVar.a = akVar.z();
                        eVar.a(true);
                        break;
                    case (short) 2:
                        if (l.b != (byte) 10) {
                            an.a(akVar, l.b);
                            break;
                        }
                        eVar.b = akVar.x();
                        eVar.b(true);
                        break;
                    case (short) 3:
                        if (l.b != (byte) 11) {
                            an.a(akVar, l.b);
                            break;
                        }
                        eVar.c = akVar.z();
                        eVar.c(true);
                        break;
                    default:
                        an.a(akVar, l.b);
                        break;
                }
                akVar.m();
            }
        }

        /* renamed from: b */
        public void a(ak akVar, e eVar) throws r {
            eVar.k();
            akVar.a(e.f);
            if (eVar.a != null && eVar.d()) {
                akVar.a(e.g);
                akVar.a(eVar.a);
                akVar.c();
            }
            akVar.a(e.h);
            akVar.a(eVar.b);
            akVar.c();
            if (eVar.c != null) {
                akVar.a(e.i);
                akVar.a(eVar.c);
                akVar.c();
            }
            akVar.d();
            akVar.b();
        }
    }

    /* compiled from: ImprintValue */
    private static class b implements at {
        private b() {
        }

        /* renamed from: a */
        public a b() {
            return new a();
        }
    }

    /* compiled from: ImprintValue */
    private static class c extends av<e> {
        private c() {
        }

        public void a(ak akVar, e eVar) throws r {
            aq aqVar = (aq) akVar;
            aqVar.a(eVar.b);
            aqVar.a(eVar.c);
            BitSet bitSet = new BitSet();
            if (eVar.d()) {
                bitSet.set(0);
            }
            aqVar.a(bitSet, 1);
            if (eVar.d()) {
                aqVar.a(eVar.a);
            }
        }

        public void b(ak akVar, e eVar) throws r {
            aq aqVar = (aq) akVar;
            eVar.b = aqVar.x();
            eVar.b(true);
            eVar.c = aqVar.z();
            eVar.c(true);
            if (aqVar.b(1).get(0)) {
                eVar.a = aqVar.z();
                eVar.a(true);
            }
        }
    }

    /* compiled from: ImprintValue */
    private static class d implements at {
        private d() {
        }

        /* renamed from: a */
        public c b() {
            return new c();
        }
    }

    /* compiled from: ImprintValue */
    public enum e implements s {
        VALUE((short) 1, "value"),
        TS((short) 2, "ts"),
        GUID((short) 3, "guid");
        
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
                    return VALUE;
                case 2:
                    return TS;
                case 3:
                    return GUID;
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
        enumMap.put(e.VALUE, new x("value", (byte) 2, new y((byte) 11)));
        enumMap.put(e.TS, new x("ts", (byte) 1, new y((byte) 10)));
        enumMap.put(e.GUID, new x("guid", (byte) 1, new y((byte) 11)));
        d = Collections.unmodifiableMap(enumMap);
        x.a(e.class, d);
    }

    public e() {
        this.l = (byte) 0;
        this.m = new e[]{e.VALUE};
    }

    public e(long j, String str) {
        this();
        this.b = j;
        b(true);
        this.c = str;
    }

    public e(e eVar) {
        this.l = (byte) 0;
        this.m = new e[]{e.VALUE};
        this.l = eVar.l;
        if (eVar.d()) {
            this.a = eVar.a;
        }
        this.b = eVar.b;
        if (eVar.j()) {
            this.c = eVar.c;
        }
    }

    /* renamed from: a */
    public e deepCopy() {
        return new e(this);
    }

    public void clear() {
        this.a = null;
        b(false);
        this.b = 0;
        this.c = null;
    }

    public String b() {
        return this.a;
    }

    public e a(String str) {
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

    public e a(long j) {
        this.b = j;
        b(true);
        return this;
    }

    public void f() {
        this.l = i.b(this.l, 0);
    }

    public boolean g() {
        return i.a(this.l, 0);
    }

    public void b(boolean z) {
        this.l = i.a(this.l, 0, z);
    }

    public String h() {
        return this.c;
    }

    public e b(String str) {
        this.c = str;
        return this;
    }

    public void i() {
        this.c = null;
    }

    public boolean j() {
        return this.c != null;
    }

    public void c(boolean z) {
        if (!z) {
            this.c = null;
        }
    }

    /* renamed from: a */
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
        StringBuilder stringBuilder = new StringBuilder("ImprintValue(");
        Object obj = 1;
        if (d()) {
            stringBuilder.append("value:");
            if (this.a == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.a);
            }
            obj = null;
        }
        if (obj == null) {
            stringBuilder.append(", ");
        }
        stringBuilder.append("ts:");
        stringBuilder.append(this.b);
        stringBuilder.append(", ");
        stringBuilder.append("guid:");
        if (this.c == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.c);
        }
        stringBuilder.append(com.umeng.message.proguard.l.t);
        return stringBuilder.toString();
    }

    public void k() throws r {
        if (this.c == null) {
            throw new al("Required field 'guid' was not present! Struct: " + toString());
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
            this.l = (byte) 0;
            read(new ae(new aw((InputStream) objectInputStream)));
        } catch (r e) {
            throw new IOException(e.getMessage());
        }
    }
}
