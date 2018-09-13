package com.baidu.platform.base;

import com.baidu.mapapi.http.HttpClient.HttpStateError;
import com.baidu.mapapi.http.HttpClient.ProtoResultCallback;

class b extends ProtoResultCallback {
    final /* synthetic */ d a;
    final /* synthetic */ Object b;
    final /* synthetic */ a c;

    b(a aVar, d dVar, Object obj) {
        this.c = aVar;
        this.a = dVar;
        this.b = obj;
    }

    public void onFailed(HttpStateError httpStateError) {
        this.c.a(httpStateError, this.a, this.b);
    }

    public void onSuccess(String str) {
        this.c.a(str);
        this.c.a(str, this.a, this.b, this.c.b, this);
    }
}
