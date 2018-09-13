package com.umeng.socialize.sina.message;

import android.content.Context;
import android.os.Bundle;
import com.sina.weibo.sdk.api.WeiboMultiMessage;

public class SendMultiMessageToWeiboRequest extends BaseRequest {
    public WeiboMultiMessage multiMessage;

    public SendMultiMessageToWeiboRequest(Bundle bundle) {
        fromBundle(bundle);
    }

    final boolean check(Context context) {
        return true;
    }

    public void fromBundle(Bundle bundle) {
        super.fromBundle(bundle);
        this.multiMessage = new WeiboMultiMessage(bundle);
    }

    public int getType() {
        return 1;
    }

    public void toBundle(Bundle bundle) {
        super.toBundle(bundle);
        bundle.putAll(this.multiMessage.toBundle(bundle));
    }
}
