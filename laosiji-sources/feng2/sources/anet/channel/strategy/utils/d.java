package anet.channel.strategy.utils;

import android.text.TextUtils;
import anet.channel.strategy.dispatch.DispatchConstants;
import anet.channel.util.ALog;
import anet.channel.util.StringUtils;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: Taobao */
public class d {
    public static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        char[] toCharArray = str.toCharArray();
        if (toCharArray.length < 7 || toCharArray.length > 15) {
            return false;
        }
        int i = 0;
        for (char c : toCharArray) {
            if (c >= '0' && c <= '9') {
                i = ((i * 10) + c) - 48;
                if (i > 255) {
                    return false;
                }
            } else if (c != '.') {
                return false;
            } else {
                i = 0;
            }
        }
        return true;
    }

    public static boolean b(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        char[] toCharArray = str.toCharArray();
        if (toCharArray.length <= 0 || toCharArray.length > 255) {
            return false;
        }
        int i = 0;
        boolean z = false;
        while (i < toCharArray.length) {
            if ((toCharArray[i] >= 'A' && toCharArray[i] <= 'Z') || ((toCharArray[i] >= 'a' && toCharArray[i] <= 'z') || toCharArray[i] == '*')) {
                z = true;
            } else if (!((toCharArray[i] >= '0' && toCharArray[i] <= '9') || toCharArray[i] == '.' || toCharArray[i] == '-')) {
                return false;
            }
            i++;
        }
        return z;
    }

    public static String a(Map<String, List<String>> map, String str) {
        if (map == null || map.isEmpty() || TextUtils.isEmpty(str)) {
            return null;
        }
        List list = null;
        for (Entry entry : map.entrySet()) {
            List list2;
            if (str.equalsIgnoreCase((String) entry.getKey())) {
                list2 = (List) entry.getValue();
            } else {
                list2 = list;
            }
            list = list2;
        }
        if (list != null) {
            return (String) list.get(0);
        }
        return null;
    }

    public static String c(String str) {
        return str == null ? "" : str;
    }

    public static String b(Map<String, String> map, String str) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(64);
        try {
            for (Entry entry : map.entrySet()) {
                if (entry.getKey() != null) {
                    stringBuilder.append(URLEncoder.encode((String) entry.getKey(), str)).append("=").append(URLEncoder.encode(StringUtils.stringNull2Empty((String) entry.getValue()), str).replace("+", "%20")).append(DispatchConstants.SIGN_SPLIT_SYMBOL);
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        } catch (Throwable e) {
            ALog.e("Request", "format params failed", null, e, new Object[0]);
        }
        return stringBuilder.toString();
    }
}
