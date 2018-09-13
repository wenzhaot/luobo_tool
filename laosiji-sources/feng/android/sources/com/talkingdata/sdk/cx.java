package com.talkingdata.sdk;

/* compiled from: td */
class cx implements Runnable {
    final /* synthetic */ cw this$0;

    cx(cw cwVar) {
        this.this$0 = cwVar;
    }

    public void run() {
        try {
            this.this$0.b = System.currentTimeMillis();
            if (this.this$0.d != this.this$0.e && this.this$0.d > 1 && this.this$0.b - this.this$0.c > 180000) {
                dd ddVar = new dd();
                ddVar.b = "env";
                ddVar.c = "cellUpdate";
                ddVar.a = a.ENV;
                br.a().post(ddVar);
                this.this$0.c = this.this$0.b;
                this.this$0.e = this.this$0.d;
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }
}
