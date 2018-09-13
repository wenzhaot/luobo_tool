package com.umeng.commonsdk.proguard;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* compiled from: TUnion */
public abstract class v<T extends v<?, ?>, F extends s> implements l<T, F> {
    private static final Map<Class<? extends as>, at> c = new HashMap();
    protected Object a;
    protected F b;

    /* compiled from: TUnion */
    private static class a extends au<v> {
        private a() {
        }

        /* renamed from: a */
        public void b(ak akVar, v vVar) throws r {
            vVar.b = null;
            vVar.a = null;
            akVar.j();
            af l = akVar.l();
            vVar.a = vVar.a(akVar, l);
            if (vVar.a != null) {
                vVar.b = vVar.a(l.c);
            }
            akVar.m();
            akVar.l();
            akVar.k();
        }

        /* renamed from: b */
        public void a(ak akVar, v vVar) throws r {
            if (vVar.a() == null || vVar.b() == null) {
                throw new al("Cannot write a TUnion with no set value!");
            }
            akVar.a(vVar.d());
            akVar.a(vVar.c(vVar.b));
            vVar.a(akVar);
            akVar.c();
            akVar.d();
            akVar.b();
        }
    }

    /* compiled from: TUnion */
    private static class b implements at {
        private b() {
        }

        /* renamed from: a */
        public a b() {
            return new a();
        }
    }

    /* compiled from: TUnion */
    private static class c extends av<v> {
        private c() {
        }

        /* renamed from: a */
        public void b(ak akVar, v vVar) throws r {
            vVar.b = null;
            vVar.a = null;
            short v = akVar.v();
            vVar.a = vVar.a(akVar, v);
            if (vVar.a != null) {
                vVar.b = vVar.a(v);
            }
        }

        /* renamed from: b */
        public void a(ak akVar, v vVar) throws r {
            if (vVar.a() == null || vVar.b() == null) {
                throw new al("Cannot write a TUnion with no set value!");
            }
            akVar.a(vVar.b.a());
            vVar.b(akVar);
        }
    }

    /* compiled from: TUnion */
    private static class d implements at {
        private d() {
        }

        /* renamed from: a */
        public c b() {
            return new c();
        }
    }

    protected abstract F a(short s);

    protected abstract Object a(ak akVar, af afVar) throws r;

    protected abstract Object a(ak akVar, short s) throws r;

    protected abstract void a(ak akVar) throws r;

    protected abstract void b(ak akVar) throws r;

    protected abstract void b(F f, Object obj) throws ClassCastException;

    protected abstract af c(F f);

    protected abstract ap d();

    protected v() {
        this.b = null;
        this.a = null;
    }

    static {
        c.put(au.class, new b());
        c.put(av.class, new d());
    }

    protected v(F f, Object obj) {
        a((s) f, obj);
    }

    protected v(v<T, F> vVar) {
        if (vVar.getClass().equals(getClass())) {
            this.b = vVar.b;
            this.a = a(vVar.a);
            return;
        }
        throw new ClassCastException();
    }

    private static Object a(Object obj) {
        if (obj instanceof l) {
            return ((l) obj).deepCopy();
        }
        if (obj instanceof ByteBuffer) {
            return m.d((ByteBuffer) obj);
        }
        if (obj instanceof List) {
            return a((List) obj);
        }
        if (obj instanceof Set) {
            return a((Set) obj);
        }
        if (obj instanceof Map) {
            return a((Map) obj);
        }
        return obj;
    }

    private static Map a(Map<Object, Object> map) {
        Map hashMap = new HashMap();
        for (Entry entry : map.entrySet()) {
            hashMap.put(a(entry.getKey()), a(entry.getValue()));
        }
        return hashMap;
    }

    private static Set a(Set set) {
        Set hashSet = new HashSet();
        for (Object a : set) {
            hashSet.add(a(a));
        }
        return hashSet;
    }

    private static List a(List list) {
        List arrayList = new ArrayList(list.size());
        for (Object a : list) {
            arrayList.add(a(a));
        }
        return arrayList;
    }

    public F a() {
        return this.b;
    }

    public Object b() {
        return this.a;
    }

    public Object a(F f) {
        if (f == this.b) {
            return b();
        }
        throw new IllegalArgumentException("Cannot get the value of field " + f + " because union's set field is " + this.b);
    }

    public Object a(int i) {
        return a(a((short) i));
    }

    public boolean c() {
        return this.b != null;
    }

    public boolean b(F f) {
        return this.b == f;
    }

    public boolean b(int i) {
        return b(a((short) i));
    }

    public void read(ak akVar) throws r {
        ((at) c.get(akVar.D())).b().b(akVar, this);
    }

    public void a(F f, Object obj) {
        b(f, obj);
        this.b = f;
        this.a = obj;
    }

    public void a(int i, Object obj) {
        a(a((short) i), obj);
    }

    public void write(ak akVar) throws r {
        ((at) c.get(akVar.D())).b().a(akVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<");
        stringBuilder.append(getClass().getSimpleName());
        stringBuilder.append(" ");
        if (a() != null) {
            Object b = b();
            stringBuilder.append(c(a()).a);
            stringBuilder.append(":");
            if (b instanceof ByteBuffer) {
                m.a((ByteBuffer) b, stringBuilder);
            } else {
                stringBuilder.append(b.toString());
            }
        }
        stringBuilder.append(">");
        return stringBuilder.toString();
    }

    public final void clear() {
        this.b = null;
        this.a = null;
    }
}
