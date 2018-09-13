package com.umeng.socialize.sina.message;

import android.content.Context;
import android.os.Bundle;

public class SendMessageToWeiboRequest extends BaseRequest {
    public Bundle message;

    public SendMessageToWeiboRequest(Bundle bundle) {
        fromBundle(bundle);
    }

    final boolean check(Context context) {
        return true;
    }

    public void fromBundle(Bundle bundle) {
        super.fromBundle(bundle);
        this.message = new Bundle();
    }

    public int getType() {
        return 1;
    }

    public void toBundle(Bundle bundle) {
        super.toBundle(bundle);
        bundle.putAll(this.message);
    }
}
