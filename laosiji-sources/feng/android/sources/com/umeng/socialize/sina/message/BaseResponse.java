package com.umeng.socialize.sina.message;

import android.content.Context;
import android.os.Bundle;
import com.umeng.socialize.media.Base;

public abstract class BaseResponse extends Base {
    public int errCode;
    public String errMsg;
    public String reqPackageName;

    abstract boolean check(Context context);

    public void fromBundle(Bundle bundle) {
        this.errCode = bundle.getInt("_weibo_resp_errcode");
        this.errMsg = bundle.getString("_weibo_resp_errstr");
        this.transaction = bundle.getString("_weibo_transaction");
        this.reqPackageName = bundle.getString("_weibo_appPackage");
    }

    public void toBundle(Bundle bundle) {
        bundle.putInt("_weibo_command_type", getType());
        bundle.putInt("_weibo_resp_errcode", this.errCode);
        bundle.putString("_weibo_resp_errstr", this.errMsg);
        bundle.putString("_weibo_transaction", this.transaction);
    }
}
