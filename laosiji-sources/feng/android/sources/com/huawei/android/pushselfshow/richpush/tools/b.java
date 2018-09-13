package com.huawei.android.pushselfshow.richpush.tools;

import com.feng.car.utils.FengConstant;

public class b {
    public static String a(String str) {
        return ("application/zip".equals(str) || "application/zip_local".equals(str)) ? ".zip" : "text/html".equals(str) ? ".html" : FengConstant.JPG.equals(str) ? ".jpg" : ".unknow";
    }
}
