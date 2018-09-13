package com.talkingdata.sdk;

import java.util.concurrent.ConcurrentLinkedQueue;

/* compiled from: td */
class bs extends ThreadLocal {
    final /* synthetic */ br this$0;

    bs(br brVar) {
        this.this$0 = brVar;
    }

    protected ConcurrentLinkedQueue initialValue() {
        return new ConcurrentLinkedQueue();
    }
}
