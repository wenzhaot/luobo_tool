package com.taobao.accs.init;

import android.text.TextUtils;
import com.alibaba.analytics.utils.AppInfoUtil;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.IAppReceiver;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import java.util.Map;

/* compiled from: Taobao */
final class d implements IAppReceiver {
    d() {
    }

    public void onUnbindUser(int i) {
        if (ALog.isPrintLog(Level.D)) {
            ALog.d("Launcher_InitAccs", "onUnbindUser, errorCode:" + i, new Object[0]);
        }
    }

    public void onUnbindApp(int i) {
        if (ALog.isPrintLog(Level.D)) {
            ALog.d("Launcher_InitAccs", "onUnbindApp,  errorCode:" + i, new Object[0]);
        }
    }

    public void onSendData(String str, int i) {
        if (ALog.isPrintLog(Level.D)) {
            ALog.d("Launcher_InitAccs", "onSendData,  dataId:" + str + " errorCode:" + i, new Object[0]);
        }
    }

    public void onData(String str, String str2, byte[] bArr) {
        if (ALog.isPrintLog(Level.D)) {
            ALog.d("Launcher_InitAccs", "onData,  userId:" + str + "dataId:" + str2 + " dataLen:" + (bArr == null ? 0 : bArr.length), new Object[0]);
        }
    }

    public void onBindUser(String str, int i) {
        if (ALog.isPrintLog(Level.D)) {
            ALog.d("Launcher_InitAccs", "onBindUser, userId:" + str + " errorCode:" + i, new Object[0]);
        }
        if (i == 300) {
            ACCSManager.bindApp(Launcher_InitAccs.mContext, AppInfoUtil.getAppkey(), Launcher_InitAccs.mTtid, null);
        }
    }

    public void onBindApp(int i) {
        if (ALog.isPrintLog(Level.D)) {
            ALog.d("Launcher_InitAccs", "onBindApp,  errorCode:" + i, new Object[0]);
        }
        if (i != 200) {
            return;
        }
        if (!TextUtils.isEmpty(Launcher_InitAccs.mUserId)) {
            ACCSManager.bindUser(Launcher_InitAccs.mContext, Launcher_InitAccs.mUserId, Launcher_InitAccs.mForceBindUser);
            Launcher_InitAccs.mForceBindUser = false;
        } else if (ALog.isPrintLog(Level.D)) {
            ALog.d("Launcher_InitAccs", "onBindApp,  bindUser userid :" + Launcher_InitAccs.mUserId, new Object[0]);
        }
    }

    public String getService(String str) {
        return (String) Launcher_InitAccs.a.get(str);
    }

    public Map<String, String> getAllServices() {
        return Launcher_InitAccs.a;
    }
}
