package com.talkingdata.sdk;

/* compiled from: td */
class cz implements Runnable {
    final /* synthetic */ cy this$0;

    cz(cy cyVar) {
        this.this$0 = cyVar;
    }

    public void run() {
        try {
            this.this$0.f = System.currentTimeMillis();
            if (this.this$0.f - this.this$0.g > this.this$0.h) {
                this.this$0.g = this.this$0.f;
                this.this$0.d = this.this$0.b();
                if (this.this$0.d == null) {
                    this.this$0.a();
                    this.this$0.d = this.this$0.c();
                }
                this.this$0.e = this.this$0.c();
                if (this.this$0.d != null && this.this$0.e != null && this.this$0.a.a(this.this$0.d, this.this$0.e) < 0.8d) {
                    this.this$0.a();
                }
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }
}
