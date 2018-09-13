package com.talkingdata.sdk;

import java.util.Comparator;

/* compiled from: td */
class ch implements Comparator {
    final /* synthetic */ cg this$0;

    ch(cg cgVar) {
        this.this$0 = cgVar;
    }

    public int compare(a aVar, a aVar2) {
        if (aVar.score == aVar2.score) {
            return 0;
        }
        return aVar.score < aVar2.score ? 1 : -1;
    }
}
