package com.umeng.commonsdk.statistics.proto;

import com.umeng.commonsdk.proguard.ac;
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
import com.umeng.commonsdk.proguard.g;
import com.umeng.commonsdk.proguard.i;
import com.umeng.commonsdk.proguard.l;
import com.umeng.commonsdk.proguard.r;
import com.umeng.commonsdk.proguard.s;
import com.umeng.commonsdk.proguard.x;
import com.umeng.commonsdk.proguard.y;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
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

public class Response implements l<Response, e>, Serializable, Cloneable {
    private static final af IMPRINT_FIELD_DESC = new af(g.U, (byte) 12, (short) 3);
    private static final af MSG_FIELD_DESC = new af(SocializeProtocolConstants.PROTOCOL_KEY_MSG, (byte) 11, (short) 2);
    private static final af RESP_CODE_FIELD_DESC = new af("resp_code", (byte) 8, (short) 1);
    private static final ap STRUCT_DESC = new ap("Response");
    private static final int __RESP_CODE_ISSET_ID = 0;
    public static final Map<e, x> metaDataMap;
    private static final Map<Class<? extends as>, at> schemes = new HashMap();
    private static final long serialVersionUID = -4549277923241195391L;
    private byte __isset_bitfield;
    public d imprint;
    public String msg;
    private e[] optionals;
    public int resp_code;

    private static class a extends au<Response> {
        private a() {
        }

        /* renamed from: a */
        public void b(ak akVar, Response response) throws r {
            akVar.j();
            while (true) {
                af l = akVar.l();
                if (l.b == (byte) 0) {
                    akVar.k();
                    if (response.isSetResp_code()) {
                        response.validate();
                        return;
                    }
                    throw new al("Required field 'resp_code' was not found in serialized data! Struct: " + toString());
                }
                switch (l.c) {
                    case (short) 1:
                        if (l.b != (byte) 8) {
                            an.a(akVar, l.b);
                            break;
                        }
                        response.resp_code = akVar.w();
                        response.setResp_codeIsSet(true);
                        break;
                    case (short) 2:
                        if (l.b != (byte) 11) {
                            an.a(akVar, l.b);
                            break;
                        }
                        response.msg = akVar.z();
                        response.setMsgIsSet(true);
                        break;
                    case (short) 3:
                        if (l.b != (byte) 12) {
                            an.a(akVar, l.b);
                            break;
                        }
                        response.imprint = new d();
                        response.imprint.read(akVar);
                        response.setImprintIsSet(true);
                        break;
                    default:
                        an.a(akVar, l.b);
                        break;
                }
                akVar.m();
            }
        }

        /* renamed from: b */
        public void a(ak akVar, Response response) throws r {
            response.validate();
            akVar.a(Response.STRUCT_DESC);
            akVar.a(Response.RESP_CODE_FIELD_DESC);
            akVar.a(response.resp_code);
            akVar.c();
            if (response.msg != null && response.isSetMsg()) {
                akVar.a(Response.MSG_FIELD_DESC);
                akVar.a(response.msg);
                akVar.c();
            }
            if (response.imprint != null && response.isSetImprint()) {
                akVar.a(Response.IMPRINT_FIELD_DESC);
                response.imprint.write(akVar);
                akVar.c();
            }
            akVar.d();
            akVar.b();
        }
    }

    private static class b implements at {
        private b() {
        }

        /* renamed from: a */
        public a b() {
            return new a();
        }
    }

    private static class c extends av<Response> {
        private c() {
        }

        public void a(ak akVar, Response response) throws r {
            aq aqVar = (aq) akVar;
            aqVar.a(response.resp_code);
            BitSet bitSet = new BitSet();
            if (response.isSetMsg()) {
                bitSet.set(0);
            }
            if (response.isSetImprint()) {
                bitSet.set(1);
            }
            aqVar.a(bitSet, 2);
            if (response.isSetMsg()) {
                aqVar.a(response.msg);
            }
            if (response.isSetImprint()) {
                response.imprint.write(aqVar);
            }
        }

        public void b(ak akVar, Response response) throws r {
            aq aqVar = (aq) akVar;
            response.resp_code = aqVar.w();
            response.setResp_codeIsSet(true);
            BitSet b = aqVar.b(2);
            if (b.get(0)) {
                response.msg = aqVar.z();
                response.setMsgIsSet(true);
            }
            if (b.get(1)) {
                response.imprint = new d();
                response.imprint.read(aqVar);
                response.setImprintIsSet(true);
            }
        }
    }

    private static class d implements at {
        private d() {
        }

        /* renamed from: a */
        public c b() {
            return new c();
        }
    }

    public enum e implements s {
        RESP_CODE((short) 1, "resp_code"),
        MSG((short) 2, SocializeProtocolConstants.PROTOCOL_KEY_MSG),
        IMPRINT((short) 3, g.U);
        
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
                    return RESP_CODE;
                case 2:
                    return MSG;
                case 3:
                    return IMPRINT;
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
        schemes.put(au.class, new b());
        schemes.put(av.class, new d());
        Map enumMap = new EnumMap(e.class);
        enumMap.put(e.RESP_CODE, new x("resp_code", (byte) 1, new y((byte) 8)));
        enumMap.put(e.MSG, new x(SocializeProtocolConstants.PROTOCOL_KEY_MSG, (byte) 2, new y((byte) 11)));
        enumMap.put(e.IMPRINT, new x(g.U, (byte) 2, new ac((byte) 12, d.class)));
        metaDataMap = Collections.unmodifiableMap(enumMap);
        x.a(Response.class, metaDataMap);
    }

    public Response() {
        this.__isset_bitfield = (byte) 0;
        this.optionals = new e[]{e.MSG, e.IMPRINT};
    }

    public Response(int i) {
        this();
        this.resp_code = i;
        setResp_codeIsSet(true);
    }

    public Response(Response response) {
        this.__isset_bitfield = (byte) 0;
        this.optionals = new e[]{e.MSG, e.IMPRINT};
        this.__isset_bitfield = response.__isset_bitfield;
        this.resp_code = response.resp_code;
        if (response.isSetMsg()) {
            this.msg = response.msg;
        }
        if (response.isSetImprint()) {
            this.imprint = new d(response.imprint);
        }
    }

    public Response deepCopy() {
        return new Response(this);
    }

    public void clear() {
        setResp_codeIsSet(false);
        this.resp_code = 0;
        this.msg = null;
        this.imprint = null;
    }

    public int getResp_code() {
        return this.resp_code;
    }

    public Response setResp_code(int i) {
        this.resp_code = i;
        setResp_codeIsSet(true);
        return this;
    }

    public void unsetResp_code() {
        this.__isset_bitfield = i.b(this.__isset_bitfield, 0);
    }

    public boolean isSetResp_code() {
        return i.a(this.__isset_bitfield, 0);
    }

    public void setResp_codeIsSet(boolean z) {
        this.__isset_bitfield = i.a(this.__isset_bitfield, 0, z);
    }

    public String getMsg() {
        return this.msg;
    }

    public Response setMsg(String str) {
        this.msg = str;
        return this;
    }

    public void unsetMsg() {
        this.msg = null;
    }

    public boolean isSetMsg() {
        return this.msg != null;
    }

    public void setMsgIsSet(boolean z) {
        if (!z) {
            this.msg = null;
        }
    }

    public d getImprint() {
        return this.imprint;
    }

    public Response setImprint(d dVar) {
        this.imprint = dVar;
        return this;
    }

    public void unsetImprint() {
        this.imprint = null;
    }

    public boolean isSetImprint() {
        return this.imprint != null;
    }

    public void setImprintIsSet(boolean z) {
        if (!z) {
            this.imprint = null;
        }
    }

    public e fieldForId(int i) {
        return e.a(i);
    }

    public void read(ak akVar) throws r {
        ((at) schemes.get(akVar.D())).b().b(akVar, this);
    }

    public void write(ak akVar) throws r {
        ((at) schemes.get(akVar.D())).b().a(akVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Response(");
        stringBuilder.append("resp_code:");
        stringBuilder.append(this.resp_code);
        if (isSetMsg()) {
            stringBuilder.append(", ");
            stringBuilder.append("msg:");
            if (this.msg == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.msg);
            }
        }
        if (isSetImprint()) {
            stringBuilder.append(", ");
            stringBuilder.append("imprint:");
            if (this.imprint == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.imprint);
            }
        }
        stringBuilder.append(com.umeng.message.proguard.l.t);
        return stringBuilder.toString();
    }

    public void validate() throws r {
        if (this.imprint != null) {
            this.imprint.l();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        try {
            write(new ae(new aw((OutputStream) objectOutputStream)));
        } catch (r e) {
            throw new IOException(e.getMessage());
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.__isset_bitfield = (byte) 0;
            read(new ae(new aw((InputStream) objectInputStream)));
        } catch (r e) {
            throw new IOException(e.getMessage());
        }
    }
}
