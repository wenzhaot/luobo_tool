package com.taobao.agoo;

import android.content.Intent;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.BaseNotifyClickActivity.INotifyListener;
import org.android.agoo.common.AgooConstants;

/* compiled from: Taobao */
public class b implements INotifyListener {
    public String parseMsgFromIntent(Intent intent) {
        String str = null;
        if (intent == null) {
            ALog.e("DefaultHuaweiMsgParseImpl", "parseMsgFromIntent null", new Object[0]);
            return str;
        }
        try {
            return intent.getStringExtra("extras");
        } catch (Throwable th) {
            ALog.e("DefaultHuaweiMsgParseImpl", "parseMsgFromIntent", th, new Object[0]);
            return str;
        }
    }

    public String getMsgSource() {
        return AgooConstants.MESSAGE_SYSTEM_SOURCE_HUAWEI;
    }
}
