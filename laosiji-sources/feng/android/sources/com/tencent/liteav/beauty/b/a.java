package com.tencent.liteav.beauty.b;

/* compiled from: SemaphoreHandle */
public class a {
    private boolean a = false;

    public synchronized void a() {
        this.a = true;
        notify();
    }

    public synchronized void b() throws InterruptedException {
        while (!this.a) {
            wait();
        }
        this.a = false;
    }
}
