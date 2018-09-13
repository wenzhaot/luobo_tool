package com.umeng.weixin.handler;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.umeng.socialize.utils.SocializeUtils;
import java.util.Map;

class m implements Runnable {
    final /* synthetic */ StringBuilder a;
    final /* synthetic */ UMAuthListener b;
    final /* synthetic */ UmengWXHandler c;

    m(UmengWXHandler umengWXHandler, StringBuilder stringBuilder, UMAuthListener uMAuthListener) {
        this.c = umengWXHandler;
        this.a = stringBuilder;
        this.b = uMAuthListener;
    }

    public void run() {
        String a = r.a(this.a.toString());
        try {
            Map jsonToMap = SocializeUtils.jsonToMap(a);
            if (jsonToMap == null || jsonToMap.size() == 0) {
                this.c.f();
            }
            this.c.a(this.c.d(a));
            jsonToMap.put("aid", this.c.h.appId);
            jsonToMap.put("as", this.c.h.appkey);
            jsonToMap.put("uid", jsonToMap.get("openid"));
            jsonToMap.put(CommonNetImpl.UNIONID, jsonToMap.get(CommonNetImpl.UNIONID));
            QueuedWork.runInMain(new n(this, jsonToMap));
        } catch (Exception e) {
        }
    }
}
