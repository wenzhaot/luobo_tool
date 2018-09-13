package com.talkingdata.sdk;

import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.Map;
import java.util.TreeMap;

/* compiled from: td */
final class ct implements Runnable {
    final /* synthetic */ Throwable val$throwable;

    ct(Throwable th) {
        this.val$throwable = th;
    }

    public void run() {
        try {
            String c = bo.c(this.val$throwable.getMessage());
            if (!cs.a.contains(c)) {
                dd ddVar = new dd();
                ddVar.b = "sdk";
                ddVar.c = "error";
                Map treeMap = new TreeMap();
                treeMap.put("type", this.val$throwable.toString());
                treeMap.put(SocializeProtocolConstants.PROTOCOL_KEY_MSG, this.val$throwable.getMessage());
                treeMap.put("stack", cs.b(this.val$throwable));
                ddVar.d = treeMap;
                ddVar.a = a.ENV;
                br.a().post(ddVar);
                cs.a.add(c);
            }
        } catch (Throwable th) {
        }
    }
}
