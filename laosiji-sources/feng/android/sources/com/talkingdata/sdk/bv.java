package com.talkingdata.sdk;

import java.lang.reflect.Method;

/* compiled from: td */
final class bv {
    private final Object a;
    private final Method b;
    private final int c;
    private boolean d = true;

    bv(Object obj, Method method) {
        if (obj == null) {
            throw new NullPointerException("EventHandler target cannot be null.");
        } else if (method == null) {
            throw new NullPointerException("EventHandler method cannot be null.");
        } else {
            this.a = obj;
            this.b = method;
            method.setAccessible(true);
            this.c = ((method.hashCode() + 31) * 31) + obj.hashCode();
        }
    }

    public boolean a() {
        return this.d;
    }

    public void b() {
        this.d = false;
    }

    public void handleEvent(Object obj) {
        if (!this.d) {
            aq.eForInternal(toString() + " has been invalidated and can no longer handle events.");
        }
        try {
            this.b.invoke(this.a, new Object[]{obj});
        } catch (Throwable th) {
        }
    }

    public String toString() {
        return "[EventHandler " + this.b + "]";
    }

    public int hashCode() {
        return this.c;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        try {
            if (getClass() != obj.getClass()) {
                return false;
            }
            bv bvVar = (bv) obj;
            if (!(this.b.equals(bvVar.b) && this.a == bvVar.a)) {
                z = false;
            }
            return z;
        } catch (Throwable th) {
            cs.postSDKError(th);
            return false;
        }
    }
}
