package com.umeng.socialize.sina.message;

import android.content.Context;
import android.os.Bundle;

public class SendMessageToWeiboResponse extends BaseResponse {
    public SendMessageToWeiboResponse(Bundle bundle) {
        fromBundle(bundle);
    }

    final boolean check(Context context) {
        return true;
    }

    public int getType() {
        return 1;
    }
}
