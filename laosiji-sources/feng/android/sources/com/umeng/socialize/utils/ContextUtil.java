package com.umeng.socialize.utils;

import android.content.Context;
import com.umeng.socialize.net.dplus.db.DBConfig;
import com.umeng.socialize.utils.UmengText.INTER;
import java.io.File;

public class ContextUtil {
    private static Context context;

    public static Context getContext() {
        if (context == null) {
            SLog.E(INTER.CONTEXT_ERROR);
        }
        return context;
    }

    public static File getDataFile(String str) {
        if (context != null) {
            return context.getDatabasePath(DBConfig.DB_NAME);
        }
        return null;
    }

    public static void setContext(Context context) {
        context = context;
    }

    public static final String getPackageName() {
        return context == null ? "" : context.getPackageName();
    }

    public static final int getIcon() {
        return context == null ? 0 : context.getApplicationInfo().icon;
    }
}
