package com.talkingdata.sdk;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.talkingdata.sdk.ba.c;
import com.umeng.commonsdk.proguard.g;

/* compiled from: td */
public class am {
    public static final Creator e = new an();
    public boolean a;
    public int b;
    public final String c;
    public final int d;
    private final a f;

    public am(int i) {
        boolean z = true;
        this.d = i;
        this.c = a(i);
        this.f = a.get(i);
        try {
            b group = this.f.getGroup("cpuacct");
            if (this.f.getGroup(g.v).group.contains("bg_non_interactive")) {
                z = false;
            }
            this.a = z;
            this.b = Integer.parseInt(group.group.split("/")[1].replace("uid_", ""));
        } catch (Throwable th) {
            cs.postSDKError(th);
            if (d() != null) {
                this.b = d().getUid();
            }
        }
    }

    public String a() {
        try {
            return this.c.split(":")[0];
        } catch (Throwable th) {
            return "";
        }
    }

    public String b() {
        try {
            if (this.c.split(":").length > 1) {
                return ":" + this.c.split(":")[1];
            }
        } catch (Throwable th) {
        }
        return "";
    }

    public a c() {
        return this.f;
    }

    protected am(Parcel parcel) {
        this.c = parcel.readString();
        this.d = parcel.readInt();
        this.f = (a) parcel.readParcelable(a.class.getClassLoader());
        this.a = parcel.readByte() != (byte) 0;
    }

    static String a(int i) {
        Throwable th;
        String trim;
        try {
            trim = ba.readFile(String.format("/proc/%d/cmdline", new Object[]{Integer.valueOf(i)})).trim();
            try {
                if (TextUtils.isEmpty(trim)) {
                    return c.get(i).getComm();
                }
                return trim;
            } catch (Throwable th2) {
                th = th2;
                cs.postSDKError(th);
                return trim;
            }
        } catch (Throwable th3) {
            Throwable th4 = th3;
            trim = null;
            th = th4;
        }
    }

    public d d() {
        try {
            return d.get(this.d);
        } catch (Throwable th) {
            cs.postSDKError(th);
            return null;
        }
    }

    public c e() {
        try {
            return c.get(this.d);
        } catch (Throwable th) {
            return null;
        }
    }
}
