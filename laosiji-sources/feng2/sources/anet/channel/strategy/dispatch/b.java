package anet.channel.strategy.dispatch;

import android.support.graphics.drawable.PathInterpolatorCompat;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.util.ALog;
import com.baidu.mapapi.UIMsg.d_ResultType;
import java.util.Map;
import java.util.Set;

/* compiled from: Taobao */
class b {
    public static final String TAG = "awcn.AmdcThreadPoolExecutor";
    private static int b = 2;
    private Map<String, Object> a;

    /* compiled from: Taobao */
    private class a implements Runnable {
        private Map<String, Object> b;

        a(Map<String, Object> map) {
            this.b = map;
        }

        a() {
        }

        public void run() {
            try {
                Map map = this.b;
                if (map == null) {
                    synchronized (b.class) {
                        map = b.this.a;
                        b.this.a = null;
                    }
                }
                if (!NetworkStatusHelper.g()) {
                    return;
                }
                if (GlobalAppRuntimeInfo.getEnv() != map.get("Env")) {
                    ALog.w(b.TAG, "task's env changed", null, new Object[0]);
                } else {
                    c.a(e.a(map));
                }
            } catch (Throwable e) {
                ALog.e(b.TAG, "exec amdc task failed.", null, e, new Object[0]);
            }
        }
    }

    b() {
    }

    public void a(Map<String, Object> map) {
        map.put("Env", GlobalAppRuntimeInfo.getEnv());
        synchronized (this) {
            if (this.a == null) {
                this.a = map;
                int i = b;
                b = i - 1;
                ALog.i(TAG, "merge amdc request", null, "delay", Integer.valueOf(i > 0 ? d_ResultType.SHORT_URL : PathInterpolatorCompat.MAX_NUM_POINTS));
                anet.channel.strategy.utils.a.a(new a(), (long) i);
            } else {
                Set set = (Set) this.a.get(DispatchConstants.HOSTS);
                Set set2 = (Set) map.get(DispatchConstants.HOSTS);
                if (map.get("Env") != this.a.get("Env")) {
                    this.a = map;
                } else if (set.size() + set2.size() <= 40) {
                    set2.addAll(set);
                    this.a = map;
                } else {
                    anet.channel.strategy.utils.a.a(new a(map));
                }
            }
        }
    }
}
