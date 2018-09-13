package com.baidu.platform.comjni.map.cloud;

import com.baidu.mapapi.http.HttpClient.HttpStateError;
import com.baidu.mapapi.http.HttpClient.ProtoResultCallback;

class b extends ProtoResultCallback {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    public void onFailed(HttpStateError httpStateError) {
        if (httpStateError == HttpStateError.NETWORK_ERROR) {
            this.a.a(-3);
        } else {
            this.a.a(1);
        }
    }

    public void onSuccess(String str) {
        this.a.h = str;
        if (this.a.a()) {
            this.a.f(str);
        } else {
            this.a.g.post(new c(this, str));
        }
    }
}
