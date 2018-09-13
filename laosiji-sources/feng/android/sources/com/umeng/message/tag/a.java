package com.umeng.message.tag;

import com.meizu.cloud.pushsdk.handler.impl.AbstractMessageHandler;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;

/* compiled from: TagLengthFilter */
public class a implements TagFilter {
    private static final String a = a.class.getName();
    private static int b = AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS;

    public boolean filter(String str) {
        if (str == null || "".equals(str.trim())) {
            return false;
        }
        if (str == null || str.length() <= b) {
            return true;
        }
        UMLog uMLog = UMConfigure.umDebugLog;
        String str2 = a;
        String[] strArr = new String[1];
        strArr[0] = String.format("The length of %s exceeds allowed max length %i", new Object[]{str, Integer.valueOf(b)});
        UMLog.mutlInfo(str2, 0, strArr);
        return false;
    }
}
