package com.taobao.agoo;

import android.content.Intent;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.BaseNotifyClickActivity.INotifyListener;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import org.android.agoo.common.AgooConstants;

/* compiled from: Taobao */
public class d implements INotifyListener {
    public String parseMsgFromIntent(Intent intent) {
        String str = null;
        if (intent == null) {
            ALog.e("DefaultOppoMsgParseImpl", "parseMsgFromIntent null", new Object[0]);
            return null;
        }
        try {
            str = intent.getStringExtra(AgooConstants.MESSAGE_OPPO_PAYLOAD);
            ALog.i("DefaultOppoMsgParseImpl", "parseMsgFromIntent", SocializeProtocolConstants.PROTOCOL_KEY_MSG, str);
            return str;
        } catch (Throwable th) {
            ALog.e("DefaultOppoMsgParseImpl", "parseMsgFromIntent", th, new Object[0]);
            return str;
        }
    }

    public String getMsgSource() {
        return AgooConstants.MESSAGE_SYSTEM_SOURCE_OPPO;
    }
}
