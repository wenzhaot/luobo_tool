package com.taobao.accs.init;

import android.app.Application;
import android.text.TextUtils;
import anet.channel.util.AppLifecycle;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.umeng.analytics.pro.b;
import java.io.Serializable;
import java.util.HashMap;

/* compiled from: Taobao */
public class Launcher_CrossActivityStopped implements Serializable {
    public void init(Application application, HashMap<String, Object> hashMap) {
        try {
            if (ALog.isPrintLog(Level.I)) {
                ALog.i("Launcher_CrossActivityStopped", "onStopped", new Object[0]);
            }
            AppLifecycle.onBackground();
            if (TextUtils.isEmpty(Launcher_InitAccs.mAppkey) || Launcher_InitAccs.mContext == null) {
                ALog.e("Launcher_CrossActivityStopped", "params null!!!", "appkey", Launcher_InitAccs.mAppkey, b.M, Launcher_InitAccs.mContext);
            } else if (Launcher_InitAccs.mIsInited) {
                ThreadPoolExecutorFactory.execute(new b(this));
            }
        } catch (Throwable th) {
            ALog.e("Launcher_CrossActivityStopped", "onStoped", th, new Object[0]);
            th.printStackTrace();
        }
    }
}
