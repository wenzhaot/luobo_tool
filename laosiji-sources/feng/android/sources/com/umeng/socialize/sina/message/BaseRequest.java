package com.umeng.socialize.sina.message;

import android.content.Context;
import android.os.Bundle;
import com.umeng.socialize.media.Base;

public abstract class BaseRequest extends Base {
    public String packageName;

    abstract boolean check(Context context);

    public void fromBundle(Bundle bundle) {
        this.transaction = bundle.getString("_weibo_transaction");
        this.packageName = bundle.getString("_weibo_appPackage");
    }

    public void toBundle(Bundle bundle) {
        bundle.putInt("_weibo_command_type", getType());
        bundle.putString("_weibo_transaction", this.transaction);
    }
}
