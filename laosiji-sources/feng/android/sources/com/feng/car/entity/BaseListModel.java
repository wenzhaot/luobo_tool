package com.feng.car.entity;

import com.feng.car.utils.FengConstant;
import com.feng.car.utils.HttpConstant;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class BaseListModel<T extends BaseInfo> {
    public int count = 0;
    public List<T> list = new ArrayList();
    public List<T> list1 = new ArrayList();
    public List<T> list2 = new ArrayList();
    public List<T> list3 = new ArrayList();
    public int pagecount = 0;
    public int photocount = 0;
    public int size = 0;
    public int videocount = 0;

    public void parser(Class<T> cls, JSONObject object) {
        JSONArray jsonArray;
        int size;
        int i;
        BaseInfo t;
        try {
            if (object.has(HttpConstant.COUNT)) {
                this.count = object.getInt(HttpConstant.COUNT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("pagecount")) {
                this.pagecount = object.getInt("pagecount");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has(FengConstant.SIZE)) {
                this.size = object.getInt(FengConstant.SIZE);
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("photocount")) {
                this.photocount = object.getInt("photocount");
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("videocount")) {
                this.videocount = object.getInt("videocount");
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
        try {
            if (object.has("list")) {
                jsonArray = object.getJSONArray("list");
                size = jsonArray.length();
                this.list.clear();
                for (i = 0; i < size; i++) {
                    t = (BaseInfo) cls.newInstance();
                    t.parser(jsonArray.getJSONObject(i));
                    this.list.add(t);
                }
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
        }
        try {
            if (object.has("list1")) {
                jsonArray = object.getJSONArray("list1");
                size = jsonArray.length();
                this.list1.clear();
                for (i = 0; i < size; i++) {
                    t = (BaseInfo) cls.newInstance();
                    t.parser(jsonArray.getJSONObject(i));
                    this.list1.add(t);
                }
            }
        } catch (Exception e222222) {
            e222222.printStackTrace();
        }
        try {
            if (object.has("list2")) {
                jsonArray = object.getJSONArray("list2");
                size = jsonArray.length();
                this.list2.clear();
                for (i = 0; i < size; i++) {
                    t = (BaseInfo) cls.newInstance();
                    t.parser(jsonArray.getJSONObject(i));
                    this.list2.add(t);
                }
            }
        } catch (Exception e2222222) {
            e2222222.printStackTrace();
        }
        try {
            if (object.has("list3")) {
                jsonArray = object.getJSONArray("list3");
                size = jsonArray.length();
                this.list3.clear();
                for (i = 0; i < size; i++) {
                    t = (BaseInfo) cls.newInstance();
                    t.parser(jsonArray.getJSONObject(i));
                    this.list3.add(t);
                }
            }
        } catch (Exception e22222222) {
            e22222222.printStackTrace();
        }
    }
}
