package com.baidu.location.e;

import java.util.List;
import java.util.concurrent.Callable;

class c implements Callable<String> {
    final /* synthetic */ String a;
    final /* synthetic */ List b;
    final /* synthetic */ a c;

    c(a aVar, String str, List list) {
        this.c = aVar;
        this.a = str;
        this.b = list;
    }

    /* renamed from: a */
    public String call() {
        this.c.a(this.a, this.b);
        return this.c.b(true);
    }
}
