package com.talkingdata.sdk;

import java.util.Calendar;

/* compiled from: td */
final class df implements Runnable {
    df() {
    }

    public void run() {
        try {
            if (bd.c(ab.g)) {
                Calendar instance;
                if (bd.h(ab.g) && de.f()) {
                    as.a("http://i.tddmp.com/a/" + au.a(ab.g), false);
                    instance = Calendar.getInstance();
                    bi.a(ab.g, ab.u, ab.y, (long) (instance.get(3) + (instance.get(6) * 1000)));
                }
                if (bd.g(ab.g) && de.e()) {
                    as.a("http://i.tddmp.com/a/" + au.a(ab.g), false);
                    instance = Calendar.getInstance();
                    bi.a(ab.g, ab.u, ab.x, (long) (instance.get(3) + (instance.get(6) * 1000)));
                }
            }
        } catch (Throwable th) {
        }
    }
}
