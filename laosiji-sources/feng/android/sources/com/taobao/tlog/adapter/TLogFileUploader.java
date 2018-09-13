package com.taobao.tlog.adapter;

import android.content.Context;
import com.meizu.cloud.pushsdk.handler.impl.model.Statics;
import com.meizu.cloud.pushsdk.pushtracer.constant.Parameters;
import com.taobao.accs.common.Constants;
import com.taobao.tao.log.TLogConstant;
import com.taobao.tao.log.TLogUtils;
import com.taobao.tao.log.collect.LogFileUploadManager;
import com.umeng.analytics.pro.b;
import java.util.HashMap;
import java.util.Map;

public class TLogFileUploader {
    private static boolean isValid;

    static {
        isValid = false;
        try {
            Class.forName("com.taobao.tao.log.TLog");
            isValid = true;
        } catch (ClassNotFoundException e) {
            isValid = false;
        }
    }

    public static void uploadLogFile(Context context, Map<String, Object> map) {
        uploadLogFile(context, map, null);
    }

    public static void uploadLogFile(Context context, Map<String, Object> map, String str) {
        if (isValid) {
            if (map != null) {
                Object obj = map.get("type");
                if ((obj instanceof String) && b.ao.equalsIgnoreCase((String) obj)) {
                    return;
                }
            }
            LogFileUploadManager instances = LogFileUploadManager.getInstances(context);
            if (str == null) {
                instances.addFiles(TLogUtils.getFilePath(TLogConstant.FILE_PREFIX, 1, null));
            } else {
                instances.addFiles(TLogUtils.getFilePath(str, 1, null));
            }
            instances.setType("client");
            instances.setExtData(map);
            Map hashMap = new HashMap();
            hashMap.put(Parameters.SESSION_USER_ID, "-1");
            hashMap.put(Constants.KEY_SERVICE_ID, "motu-remote");
            hashMap.put("serialNumber", "-1");
            hashMap.put(Statics.TASK_ID, "-1");
            instances.setReportParams(hashMap);
            instances.startUpload();
        }
    }
}
