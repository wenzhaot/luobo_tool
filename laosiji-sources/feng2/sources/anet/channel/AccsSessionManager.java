package anet.channel;

import android.text.TextUtils;
import anet.channel.entity.ConnType.TypeLevel;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.StrategyCenter;
import anet.channel.util.ALog;
import anet.channel.util.HttpConstant;
import anet.channel.util.StringUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/* compiled from: Taobao */
class AccsSessionManager {
    private static final String TAG = "awcn.AccsSessionManager";
    SessionCenter instance = null;
    Set<String> lastKeys = Collections.EMPTY_SET;

    AccsSessionManager(SessionCenter sessionCenter) {
        this.instance = sessionCenter;
    }

    public synchronized void checkAndStartSession() {
        Collection<SessionInfo> a = this.instance.attributeManager.a();
        Set set = Collections.EMPTY_SET;
        Set set2;
        if (a.isEmpty()) {
            set2 = set;
        } else {
            set2 = new TreeSet();
        }
        for (SessionInfo sessionInfo : a) {
            if (sessionInfo.isKeepAlive) {
                set2.add(StringUtils.concatString(StrategyCenter.getInstance().getSchemeByHost(sessionInfo.host, sessionInfo.isAccs ? "https" : "http"), HttpConstant.SCHEME_SPLIT, sessionInfo.host));
            }
        }
        for (String str : this.lastKeys) {
            if (!set2.contains(str)) {
                closeSessions(str);
            }
        }
        if (isNeedCheckSession()) {
            for (String str2 : set2) {
                try {
                    this.instance.get(str2, TypeLevel.SPDY, 0);
                } catch (Exception e) {
                    ALog.e("start session failed", null, "host", str2);
                }
            }
            this.lastKeys = set2;
        }
    }

    public synchronized void forceReCreateSession() {
        forceCloseSession(true);
    }

    public synchronized void forceCloseSession(boolean z) {
        if (ALog.isPrintLog(1)) {
            ALog.d(TAG, "forceCloseSession", this.instance.seqNum, "reCreate", Boolean.valueOf(z));
        }
        for (String closeSessions : this.lastKeys) {
            closeSessions(closeSessions);
        }
        if (z) {
            checkAndStartSession();
        }
    }

    private boolean isNeedCheckSession() {
        if (GlobalAppRuntimeInfo.isAppBackground()) {
            ALog.d(TAG, "app is background not need check accs session, return", this.instance.seqNum, "bg", Boolean.valueOf(true));
            return false;
        } else if (NetworkStatusHelper.g()) {
            return true;
        } else {
            ALog.d(TAG, "network is not available, not need check accs session, return", this.instance.seqNum, "network", Boolean.valueOf(NetworkStatusHelper.g()));
            return false;
        }
    }

    private void closeSessions(String str) {
        if (!TextUtils.isEmpty(str)) {
            ALog.d(TAG, "closeSessions", this.instance.seqNum, "host", str);
            this.instance.getSessionRequest(str).b(false);
        }
    }
}
