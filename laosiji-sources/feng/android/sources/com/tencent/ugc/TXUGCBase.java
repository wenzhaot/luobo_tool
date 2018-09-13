package com.tencent.ugc;

import android.content.Context;
import com.tencent.liteav.g.e;
import com.tencent.liteav.g.f;

public class TXUGCBase {
    private static TXUGCBase sInstance;
    private f mUGCLicenseNewCheck;

    public static TXUGCBase getInstance() {
        if (sInstance == null) {
            synchronized (TXUGCBase.class) {
                if (sInstance == null) {
                    sInstance = new TXUGCBase();
                }
            }
        }
        return sInstance;
    }

    private TXUGCBase() {
    }

    public void setLicence(Context context, String str, String str2) {
        this.mUGCLicenseNewCheck = f.a();
        this.mUGCLicenseNewCheck.a(context, str, str2);
    }

    public String getLicenceInfo(Context context) {
        e eVar = new e();
        f.a().a(eVar, context);
        return eVar.a;
    }
}
