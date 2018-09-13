package anet.channel.util;

import anet.channel.request.Request;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Taobao */
public class e {
    private static Map<String, Integer> a = new HashMap();

    static {
        a.put("jar", Integer.valueOf(2));
        a.put("json", Integer.valueOf(3));
        a.put("html", Integer.valueOf(4));
        a.put("htm", Integer.valueOf(4));
        a.put("css", Integer.valueOf(5));
        a.put("js", Integer.valueOf(5));
        a.put("webp", Integer.valueOf(6));
        a.put("png", Integer.valueOf(6));
        a.put("jpg", Integer.valueOf(6));
        a.put("do", Integer.valueOf(6));
        a.put("zip", Integer.valueOf(9));
        a.put("bin", Integer.valueOf(9));
    }

    public static int a(Request request) {
        if (request == null) {
            throw new NullPointerException("url is null!");
        } else if (request.getHeaders().containsKey(HttpConstant.X_PV)) {
            return 1;
        } else {
            String a = a.a(request.getHttpUrl().c());
            if (a == null) {
                return 6;
            }
            Integer num = (Integer) a.get(a);
            return num != null ? num.intValue() : 6;
        }
    }
}
