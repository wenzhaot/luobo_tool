package com.talkingdata.sdk;

/* compiled from: td */
class cm implements Runnable {
    final /* synthetic */ ck this$0;
    final /* synthetic */ String val$appId;
    final /* synthetic */ String val$channelId;
    final /* synthetic */ a val$features;

    cm(ck ckVar, String str, String str2, a aVar) {
        this.this$0 = ckVar;
        this.val$appId = str;
        this.val$channelId = str2;
        this.val$features = aVar;
    }

    public void run() {
        try {
            if (ab.q || this.this$0.e()) {
                this.this$0.b(this.val$appId, this.val$channelId, this.val$features);
            }
        } catch (Throwable th) {
        }
    }
}
