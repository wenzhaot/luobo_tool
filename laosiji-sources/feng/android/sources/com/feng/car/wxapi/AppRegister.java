package com.feng.car.wxapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.feng.car.utils.HttpConstant;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class AppRegister extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        WXAPIFactory.createWXAPI(context, null).registerApp(HttpConstant.BASE_WX_APP_ID);
    }
}
