package com.taobao.accs.client;

import android.content.Context;
import com.taobao.accs.ChannelService;
import com.taobao.accs.IProcessName;
import com.taobao.accs.client.AccsConfig.ACCS_GROUP;
import com.taobao.accs.data.Message;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.i;

/* compiled from: Taobao */
public class GlobalConfig {
    public static boolean enableCookie = true;
    public static ACCS_GROUP mGroup = ACCS_GROUP.TAOBAO;

    public static void setControlFrameMaxRetry(int i) {
        Message.a = i;
    }

    public static void setMainProcessName(String str) {
        a.d = str;
    }

    public static void setChannelProcessName(String str) {
        a.e = str;
    }

    public static void setCurrProcessNameImpl(IProcessName iProcessName) {
        a.f = iProcessName;
    }

    public static void setChannelReuse(boolean z, ACCS_GROUP accs_group) {
        GlobalClientInfo.d = z;
        mGroup = accs_group;
    }

    public static void setEnableForground(Context context, boolean z) {
        int i = 0;
        ALog.i("GlobalConfig", "setEnableForground", "enable", Boolean.valueOf(z));
        if (z) {
            i = 24;
        }
        i.a(context, ChannelService.SUPPORT_FOREGROUND_VERSION_KEY, i);
    }
}
