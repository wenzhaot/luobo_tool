package com.feng.library.okhttp.builder;

import com.feng.library.okhttp.request.OtherRequest;
import com.feng.library.okhttp.request.RequestCall;

public class HeadBuilder extends GetBuilder {
    public RequestCall build() {
        return new OtherRequest(null, null, "HEAD", this.url, this.tag, this.params, this.headers).build();
    }
}
