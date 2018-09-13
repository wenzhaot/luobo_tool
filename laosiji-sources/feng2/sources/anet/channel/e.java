package anet.channel;

import anet.channel.util.AppLifecycle;

/* compiled from: Taobao */
class e implements Runnable {
    final /* synthetic */ a a;

    e(a aVar) {
        this.a = aVar;
    }

    public void run() {
        try {
            if (AppLifecycle.lastEnterBackgroundTime == 0 || System.currentTimeMillis() - AppLifecycle.lastEnterBackgroundTime <= 300000) {
                SessionCenter.this.accsSessionManager.checkAndStartSession();
            } else {
                SessionCenter.this.accsSessionManager.forceCloseSession(true);
            }
            this.a.a = false;
        } catch (Exception e) {
            e.printStackTrace();
            this.a.a = false;
        } catch (Throwable th) {
            this.a.a = false;
            throw th;
        }
    }
}
