package com.talkingdata.sdk;

import android.os.SystemClock;

/* compiled from: td */
public class dx extends dq {
    public dx() {
        a("bootTime", (Object) Long.valueOf(c()));
        a("activeTime", (Object) Long.valueOf(SystemClock.elapsedRealtime()));
        a("freeDiskSpace", (Object) Integer.valueOf(b()));
    }

    public static int b() {
        try {
            int[] s = aw.s();
            if (s != null) {
                return s[1];
            }
        } catch (Throwable th) {
        }
        return 0;
    }

    public static long c() {
        try {
            return System.currentTimeMillis() - SystemClock.elapsedRealtime();
        } catch (Throwable th) {
            return -1;
        }
    }
}
