package com.baidu.location.b;

import android.os.Bundle;
import com.baidu.location.a.a;

public class f {
    private static Object a = new Object();
    private static f b = null;
    private int c = -1;

    public static f a() {
        f fVar;
        synchronized (a) {
            if (b == null) {
                b = new f();
            }
            fVar = b;
        }
        return fVar;
    }

    public void a(int i, int i2, String str) {
        if (i2 != this.c) {
            this.c = i2;
            Bundle bundle = new Bundle();
            bundle.putInt("loctype", i);
            bundle.putInt("diagtype", i2);
            bundle.putByteArray("diagmessage", str.getBytes());
            a.a().a(bundle, 303);
        }
    }

    public void b() {
        this.c = -1;
    }
}
