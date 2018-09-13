package com.feng.car.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.feng.car.activity.TransparentActivity;
import com.stub.StubApp;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram.Resp;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX.Req;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {
    private IWXAPI mIwxapi;

    static {
        StubApp.interface11(4716);
    }

    public native void onCreate(@Nullable Bundle bundle);

    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case 3:
                goToGetMsg();
                return;
            case 4:
                goToShowMsg((Req) req);
                return;
            default:
                return;
        }
    }

    public void onResp(BaseResp resp) {
        if (resp.getType() == 19 && (resp instanceof Resp) && ((Resp) resp).extMsg.equals("back")) {
            Intent intent = new Intent(this, TransparentActivity.class);
            intent.putExtra("url", "");
            startActivity(intent);
        }
        finish();
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        this.mIwxapi.handleIntent(intent, this);
    }

    private void goToGetMsg() {
        finish();
    }

    private void goToShowMsg(Req req) {
        if (req.message.mediaObject.extInfo.equals("back")) {
            Intent intent = new Intent(this, TransparentActivity.class);
            intent.putExtra("url", "");
            startActivity(intent);
        }
        finish();
    }
}
