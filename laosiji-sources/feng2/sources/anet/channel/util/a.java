package anet.channel.util;

import android.text.TextUtils;
import anet.channel.request.Request;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

/* compiled from: Taobao */
public class a {
    static final Pattern a = Pattern.compile("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");

    public static Map<String, List<String>> a(Map<String, List<String>> map) {
        if (map == null) {
            return null;
        }
        if (map.isEmpty()) {
            return Collections.EMPTY_MAP;
        }
        HashMap hashMap = new HashMap(map.size());
        for (Entry entry : map.entrySet()) {
            hashMap.put(entry.getKey(), new ArrayList((Collection) entry.getValue()));
        }
        return hashMap;
    }

    public static List<String> a(Map<String, List<String>> map, String str) {
        if (map == null || map.isEmpty() || TextUtils.isEmpty(str)) {
            return null;
        }
        for (Entry entry : map.entrySet()) {
            if (str.equalsIgnoreCase((String) entry.getKey())) {
                return (List) entry.getValue();
            }
        }
        return null;
    }

    public static String b(Map<String, List<String>> map, String str) {
        List a = a((Map) map, str);
        if (a == null || a.isEmpty()) {
            return null;
        }
        return (String) a.get(0);
    }

    public static void c(Map<String, List<String>> map, String str) {
        if (str != null) {
            for (String equalsIgnoreCase : map.keySet()) {
                if (str.equalsIgnoreCase(equalsIgnoreCase)) {
                    break;
                }
            }
            Object str2 = null;
            if (str2 != null) {
                map.remove(str2);
            }
        }
    }

    public static boolean a(Request request, int i) {
        return request.isRedirectEnable() && i >= GenericDraweeHierarchyBuilder.DEFAULT_FADE_DURATION && i < 400 && i != HttpConstant.SC_NOT_MODIFIED && request.getRedirectTimes() < 10;
    }

    public static boolean b(Map<String, List<String>> map) {
        try {
            if (HttpConstant.GZIP.equalsIgnoreCase(b(map, HttpConstant.CONTENT_ENCODING))) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static int c(Map<String, List<String>> map) {
        int i = 0;
        try {
            return Integer.parseInt(b(map, HttpConstant.CONTENT_LENGTH));
        } catch (Exception e) {
            return i;
        }
    }

    public static String a(String str) {
        if (str == null) {
            return null;
        }
        try {
            int length = str.length();
            if (length <= 1) {
                return null;
            }
            int lastIndexOf = str.lastIndexOf(47);
            if (lastIndexOf == -1 || lastIndexOf == length - 1) {
                return null;
            }
            int lastIndexOf2 = str.lastIndexOf(46);
            if (lastIndexOf2 == -1 || lastIndexOf2 <= lastIndexOf) {
                return null;
            }
            return str.substring(lastIndexOf2 + 1, length);
        } catch (Exception e) {
            return null;
        }
    }

    @Deprecated
    public static String a(URL url) {
        return a(url.getPath());
    }
}
