package com.baidu.platform.comapi;

import android.app.Application;
import android.content.Context;
import com.baidu.mapapi.JNIInitializer;
import com.baidu.mapapi.common.EnvironmentUtilities;
import java.io.File;

public class c {
    private static boolean a;

    public static void a(String str, Context context) {
        if (!a) {
            if (context == null) {
                throw new IllegalArgumentException("context can not be null");
            } else if (context instanceof Application) {
                NativeLoader.getInstance();
                NativeLoader.setContext(context);
                a.a().a(context);
                a.a().c();
                JNIInitializer.setContext((Application) context);
                if (!(str == null || str.equals(""))) {
                    try {
                        File file = new File(str + "/test.0");
                        if (file.exists()) {
                            file.delete();
                        }
                        file.createNewFile();
                        if (file.exists()) {
                            file.delete();
                        }
                        EnvironmentUtilities.setSDCardPath(str);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new IllegalArgumentException("provided sdcard path can not used.");
                    }
                }
                a = true;
            } else {
                throw new RuntimeException("context must be an Application Context");
            }
        }
    }
}
