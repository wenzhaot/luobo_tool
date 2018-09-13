package com.umeng.message.proguard;

import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/* compiled from: NetUtil */
public class i {
    public static String a(Map<String, Object> map, String str) {
        if (map == null || map.isEmpty()) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder(str);
        Set<String> keySet = map.keySet();
        if (!str.endsWith("?")) {
            stringBuilder.append("?");
        }
        for (String str2 : keySet) {
            String str22;
            StringBuilder append = new StringBuilder().append(URLEncoder.encode(str22)).append("=");
            if (map.get(str22) == null) {
                str22 = "";
            } else {
                str22 = map.get(str22).toString();
            }
            stringBuilder.append(append.append(URLEncoder.encode(str22)).append("&").toString());
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
