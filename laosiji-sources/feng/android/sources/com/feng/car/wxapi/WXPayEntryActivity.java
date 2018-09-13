package com.feng.car.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.feng.car.event.RechargeEvent;
import com.stub.StubApp;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    static {
        StubApp.interface11(4717);
    }

    public native void onCreate(Bundle bundle);

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        this.api.handleIntent(intent, this);
    }

    public void onReq(BaseReq req) {
    }

    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case -2:
                EventBus.getDefault().post(new RechargeEvent(3, resp.errCode, "用户取消"));
                finish();
                return;
            case -1:
                EventBus.getDefault().post(new RechargeEvent(0, resp.errCode, "错误"));
                finish();
                return;
            case 0:
                EventBus.getDefault().post(new RechargeEvent(1, resp.errCode, "成功"));
                finish();
                return;
            default:
                return;
        }
    }
}
