package anet.channel.session;

import anet.channel.IAuth.AuthCallback;
import anet.channel.Session.Status;
import anet.channel.util.ALog;

/* compiled from: Taobao */
class i implements AuthCallback {
    final /* synthetic */ TnetSpdySession a;

    i(TnetSpdySession tnetSpdySession) {
        this.a = tnetSpdySession;
    }

    public void onAuthSuccess() {
        this.a.a(Status.AUTH_SUCC, null);
        this.a.mLastPingTime = System.currentTimeMillis();
        if (this.a.heartbeat != null) {
            this.a.heartbeat.start();
        }
        this.a.mSessionStat.ret = 1;
        ALog.d("awcn.TnetSpdySession", "spdyOnStreamResponse", this.a.mSeq, "authTime", Long.valueOf(this.a.mSessionStat.authTime));
        if (this.a.mConnectedTime > 0) {
            this.a.mSessionStat.authTime = System.currentTimeMillis() - this.a.mConnectedTime;
        }
    }

    public void onAuthFail(int i, String str) {
        this.a.a(Status.AUTH_FAIL, null);
        if (this.a.mSessionStat != null) {
            this.a.mSessionStat.closeReason = "Accs_Auth_Fail:" + i;
            this.a.mSessionStat.errorCode = (long) i;
        }
        this.a.close();
    }
}
