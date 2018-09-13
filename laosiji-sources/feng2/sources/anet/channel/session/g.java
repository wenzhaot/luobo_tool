package anet.channel.session;

import anet.channel.RequestCb;
import anet.channel.bytes.ByteArray;
import anet.channel.entity.EventType;
import anet.channel.entity.d;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.HttpConstant;
import java.util.List;
import java.util.Map;

/* compiled from: Taobao */
class g implements RequestCb {
    final /* synthetic */ f a;

    g(f fVar) {
        this.a = fVar;
    }

    public void onResponseCode(int i, Map<String, List<String>> map) {
        if (i <= 0) {
            this.a.d.b(EventType.DISCONNECTED, new d(EventType.DISCONNECTED, 0, "Http connect fail"));
        }
        try {
            List list = (List) map.get(HttpConstant.SERVER_RT);
            if (!(list == null || list.isEmpty())) {
                this.a.b.serverRT = Long.parseLong((String) list.get(0));
            }
        } catch (NumberFormatException e) {
        }
        this.a.c.onResponseCode(i, map);
    }

    public void onDataReceive(ByteArray byteArray, boolean z) {
        this.a.c.onDataReceive(byteArray, z);
    }

    public void onFinish(int i, String str, RequestStatistic requestStatistic) {
        this.a.c.onFinish(i, str, requestStatistic);
    }
}
