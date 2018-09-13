package com.talkingdata.sdk;

import java.util.Comparator;

/* compiled from: td */
class ce implements Comparator {
    final /* synthetic */ cd this$0;

    ce(cd cdVar) {
        this.this$0 = cdVar;
    }

    public int compare(d dVar, d dVar2) {
        if (Double.doubleToLongBits(dVar.score) == Double.doubleToLongBits(dVar2.score)) {
            return 0;
        }
        return Double.doubleToLongBits(dVar.score) < Double.doubleToLongBits(dVar2.score) ? 1 : -1;
    }
}
