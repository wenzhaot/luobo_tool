package com.qiniu.android.bigdata.pipeline;

import com.qiniu.android.utils.FastDatePrinter;
import com.qiniu.android.utils.Json;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public final class Points {

    private static class LazyHolder {
        private static final FastDatePrinter INSTANCE = new FastDatePrinter("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Calendar.getInstance().getTimeZone(), Locale.getDefault());

        private LazyHolder() {
        }
    }

    private static String buildString(Object v) {
        if (v == null) {
            return null;
        }
        if ((v instanceof Integer) || (v instanceof Long) || (v instanceof Float) || (v instanceof Double) || (v instanceof Boolean)) {
            return v.toString();
        }
        if (v instanceof String) {
            return ((String) v).replace("\n", "\\n").replace("\t", "\\t");
        }
        if (v instanceof Collection) {
            return Json.encodeList((Collection) v);
        }
        if (v instanceof Map) {
            return Json.encodeMap((Map) v);
        }
        if (v instanceof Date) {
            return LazyHolder.INSTANCE.format((Date) v);
        }
        return v.toString();
    }

    public static <V> StringBuilder formatPoint(Map<String, V> data, StringBuilder builder) {
        for (Entry<String, V> it : data.entrySet()) {
            builder.append((String) it.getKey()).append("=").append(buildString(it.getValue())).append("\t");
        }
        builder.replace(builder.length() - 1, builder.length(), "\n");
        return builder;
    }

    public static StringBuilder formatPoint(Object obj, StringBuilder builder) {
        Field[] fields = obj.getClass().getDeclaredFields();
        Points p = new Points();
        Map map = new HashMap();
        for (Field f : fields) {
            try {
                map.put(f.getName(), f.get(obj));
            } catch (IllegalAccessException e) {
            }
        }
        return formatPoint(map, builder);
    }

    public static <V> StringBuilder formatPoints(Map<String, V>[] data) {
        StringBuilder builder = new StringBuilder();
        for (Map aData : data) {
            formatPoint(aData, builder);
        }
        return builder;
    }

    public static StringBuilder formatPoints(Object[] data) {
        StringBuilder builder = new StringBuilder();
        for (Object aData : data) {
            formatPoint(aData, builder);
        }
        return builder;
    }

    public static <V> StringBuilder formatPoints(List<Map<String, V>> data) {
        StringBuilder builder = new StringBuilder();
        for (Map aData : data) {
            formatPoint(aData, builder);
        }
        return builder;
    }

    public static <V> StringBuilder formatPointsObjects(List<V> data) {
        StringBuilder builder = new StringBuilder();
        for (V aData : data) {
            formatPoint((Object) aData, builder);
        }
        return builder;
    }
}
