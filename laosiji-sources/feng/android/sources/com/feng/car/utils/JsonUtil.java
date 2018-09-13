package com.feng.car.utils;

import android.text.TextUtils;
import com.feng.car.FengApplication;
import com.feng.car.entity.user.UserInfo;
import com.feng.library.utils.DateUtil;
import com.feng.library.utils.SharedUtil;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtil {
    public static String parameterToJson(Map<String, Object> map, String strInterface, String devicemark) {
        String json = "";
        try {
            String str = "devicemark";
            if (TextUtils.isEmpty(devicemark)) {
                devicemark = "";
            }
            map.put(str, devicemark);
            map.put("time", DateUtil.getCurrentDate(DateUtil.dateFormatYMDHMS));
            if (FengApplication.getInstance().getUserInfo() != null) {
                if (TextUtils.isEmpty(FengApplication.getInstance().getUserInfo().token)) {
                    map.put(HttpConstant.USERTOKEN, SharedUtil.getString(FengApplication.getInstance(), UserInfo.TOKEN_KEY));
                } else {
                    map.put(HttpConstant.USERTOKEN, FengApplication.getInstance().getUserInfo().token);
                }
            } else if (strInterface.equals(HttpConstant.ADD_USER_CONNECT) || strInterface.equals(HttpConstant.LOGIN_BY_5X)) {
                map.put(HttpConstant.USERTOKEN, "");
            } else if (map.containsKey(HttpConstant.TOKEN)) {
                if (TextUtils.isEmpty(map.get(HttpConstant.TOKEN).toString())) {
                    map.remove(HttpConstant.TOKEN);
                }
            } else if (!(map.containsKey(HttpConstant.PHONENUMBER) || map.containsKey(HttpConstant.CONNECTPROY))) {
                map.put(HttpConstant.USERTOKEN, FengConstant.USERTOURISTTOKEN);
            }
            return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson((Object) map);
        } catch (Exception e) {
            e.printStackTrace();
            return json;
        }
    }

    public static String toJson(Object src) {
        String json = "";
        try {
            return new GsonBuilder().create().toJson(src);
        } catch (Exception e) {
            e.printStackTrace();
            return json;
        }
    }

    public static String toJson(List<?> list) {
        String json = null;
        try {
            return new GsonBuilder().create().toJson((Object) list);
        } catch (Exception e) {
            e.printStackTrace();
            return json;
        }
    }

    public static List<?> fromJson(String json, TypeToken<?> typeToken) {
        List<?> list = null;
        try {
            return (List) new GsonBuilder().create().fromJson(json, typeToken.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
    }

    public static Map<String, Object> fromJsonToMap(String json, TypeToken<?> typeToken) {
        Map<String, Object> map = null;
        try {
            map = (Map) new GsonBuilder().create().fromJson(json, typeToken.getType());
            System.out.println(map.toString());
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }
    }

    public static Object fromJson(String json, Class<?> clazz) {
        try {
            return new GsonBuilder().create().fromJson(json, (Class) clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> getMapForJson(String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            Iterator<String> keyIter = jsonObject.keys();
            Map<String, Object> hashMap = new HashMap();
            while (keyIter.hasNext()) {
                String key = (String) keyIter.next();
                hashMap.put(key, jsonObject.get(key));
            }
            return hashMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static LinkedHashMap<Integer, Integer> RecommendHistory(String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            Iterator<String> keyIter = jsonObject.keys();
            LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap();
            while (keyIter.hasNext()) {
                String key = (String) keyIter.next();
                linkedHashMap.put(Integer.valueOf(Integer.parseInt(key)), Integer.valueOf(jsonObject.getInt(key)));
            }
            return linkedHashMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Map<String, Object>> getListForJson(String jsonStr) {
        List<Map<String, Object>> list = new ArrayList();
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(getMapForJson(((JSONObject) jsonArray.get(i)).toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
