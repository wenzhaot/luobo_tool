package com.feng.car.utils;

import com.feng.car.FengApplication;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram.Req;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

public class WXUtils {
    private static WXUtils mInstance;
    private IWXAPI mWXApi = WXAPIFactory.createWXAPI(FengApplication.getInstance(), HttpConstant.BASE_WX_APP_ID, false);

    public static WXUtils getInstance() {
        if (mInstance == null) {
            mInstance = new WXUtils();
        }
        return mInstance;
    }

    private WXUtils() {
        this.mWXApi.registerApp(HttpConstant.BASE_WX_APP_ID);
    }

    public void lunchMiniProgram(String path) {
        if (UMShareAPI.get(FengApplication.getInstance()).isInstall(ActivityManager.getInstance().getCurrentActivity(), SHARE_MEDIA.WEIXIN)) {
            Req req = new Req();
            req.userName = HttpConstant.BASE_WX_MIN_ORIGINAL_ID;
            req.path = path;
            if (FengApplication.getInstance().getIsOpenTest()) {
                req.miniprogramType = 2;
            } else {
                req.miniprogramType = 0;
            }
            this.mWXApi.sendReq(req);
            return;
        }
        ActivityManager.getInstance().getCurrentActivity().showThirdTypeToast(2131231325);
    }
}
