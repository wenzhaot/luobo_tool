package com.feng.car.operation;

import android.content.Context;
import android.text.TextUtils;
import com.feng.car.FengApplication;
import com.feng.car.entity.ad.AdvertInfo;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.LogGatherReadUtil;

public class AdvertOperation {
    private AdvertInfo mAdvertInfo;

    public AdvertOperation(AdvertInfo info) {
        this.mAdvertInfo = info;
    }

    public void adClickHandle(Context context) {
        if (!TextUtils.isEmpty(this.mAdvertInfo.landingurl)) {
            FengApplication.getInstance().handlerUrlSkip(context, this.mAdvertInfo.landingurl, this.mAdvertInfo.tmpmap.title, true, this.mAdvertInfo);
        }
    }

    public void adPvHandle(Context context, boolean isStart) {
        LogGatherReadUtil.getInstance().addAdPvLog(this.mAdvertInfo.adid, this.mAdvertInfo.seat, TextUtils.isEmpty(this.mAdvertInfo.backjson) ? JsonUtil.toJson(this.mAdvertInfo) : this.mAdvertInfo.backjson, isStart);
    }
}
