package com.qiniu.android.utils;

import java.util.Collection;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public final class Json {
    public static String encodeMap(Map map) {
        return new JSONObject(map).toString();
    }

    public static String encodeList(Collection collection) {
        return new JSONArray(collection).toString();
    }
}
