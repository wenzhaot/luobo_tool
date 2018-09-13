package com.talkingdata.sdk;

import com.taobao.accs.utl.BaseMonitor;
import com.umeng.qq.handler.QQConstant;
import java.util.Map;

/* compiled from: td */
final class cu implements Runnable {
    final /* synthetic */ Map val$data;
    final /* synthetic */ boolean val$sendStatusSuccess;

    cu(Map map, boolean z) {
        this.val$data = map;
        this.val$sendStatusSuccess = z;
    }

    public void run() {
        try {
            if (this.val$data != null && !this.val$data.isEmpty()) {
                if (!this.val$sendStatusSuccess || !String.valueOf(this.val$data.get(QQConstant.SHARE_TO_QQ_TARGET_URL)).contains(aa.h)) {
                    aq.dForInternal(this.val$data.toString());
                    dd ddVar = new dd();
                    ddVar.b = "sdk";
                    ddVar.c = this.val$sendStatusSuccess ? "send_ok" : BaseMonitor.ALARM_POINT_REQ_ERROR;
                    ddVar.d = this.val$data;
                    ddVar.a = a.ENV;
                    br.a().post(ddVar);
                }
            }
        } catch (Throwable th) {
        }
    }
}
