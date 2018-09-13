package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.strategy.dispatch.DispatchConstants;
import anet.channel.strategy.utils.d;
import anet.channel.util.ALog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: Taobao */
class a {
    final ConcurrentHashMap<String, List<IPConnStrategy>> a = new ConcurrentHashMap();
    final HashMap<String, Object> b = new HashMap();

    a() {
    }

    List a(String str) {
        if (TextUtils.isEmpty(str) || !d.b(str) || DispatchConstants.getAmdcServerDomain().equalsIgnoreCase(str)) {
            return Collections.EMPTY_LIST;
        }
        if (ALog.isPrintLog(1)) {
            ALog.d("awcn.LocalDnsStrategyTable", "try resolve ip with local dns", null, "host", str);
        }
        List list = Collections.EMPTY_LIST;
        if (!this.a.containsKey(str)) {
            Object obj;
            synchronized (this.b) {
                if (this.b.containsKey(str)) {
                    obj = this.b.get(str);
                } else {
                    Object obj2 = new Object();
                    this.b.put(str, obj2);
                    a(str, obj2);
                    obj = obj2;
                }
            }
            if (obj != null) {
                try {
                    synchronized (obj) {
                        obj.wait(500);
                    }
                } catch (InterruptedException e) {
                }
            }
        }
        List list2 = (List) this.a.get(str);
        if (list2 == null || list2 == Collections.EMPTY_LIST) {
            return list;
        }
        return new ArrayList(list2);
    }

    void a(String str, IConnStrategy iConnStrategy, ConnEvent connEvent) {
        if (!connEvent.isSuccess && !TextUtils.isEmpty(str)) {
            List list = (List) this.a.get(str);
            if (list != null && list != Collections.EMPTY_LIST) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (it.next() == iConnStrategy) {
                        it.remove();
                    }
                }
                if (list.isEmpty()) {
                    this.a.put(str, Collections.EMPTY_LIST);
                }
            }
        }
    }

    private void a(String str, Object obj) {
        anet.channel.strategy.utils.a.a(new b(this, str, obj));
    }
}
