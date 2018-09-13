package com.feng.car.entity.sns;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourcesInfoList {
    private Map<String, Integer> dataIndex = new HashMap();
    private List<SnsPostResources> dataList = new ArrayList();
    private int position = 0;

    public List<SnsPostResources> getResourcesList() {
        return this.dataList;
    }

    public SnsPostResources getLastSnsPostResources() {
        if (this.dataList.size() <= 0) {
            return null;
        }
        return (SnsPostResources) this.dataList.get(this.dataList.size() - 1);
    }

    public SnsPostResources get(int pos) {
        return (SnsPostResources) this.dataList.get(pos);
    }

    public int getPosition(String url) {
        if (!this.dataIndex.containsKey(url)) {
            return -1;
        }
        int pos = ((Integer) this.dataIndex.get(url)).intValue();
        if (pos >= this.dataList.size()) {
            return -1;
        }
        if (url.equals(get(pos).url)) {
            return pos;
        }
        return -1;
    }

    public int size() {
        return this.dataList.size();
    }

    public void clear() {
        this.dataList.clear();
        this.dataIndex.clear();
        this.position = 0;
    }

    public void add(SnsPostResources object) {
        if (!TextUtils.isEmpty(object.url) && !this.dataIndex.containsKey(object.url)) {
            this.dataList.add(object);
            Map map = this.dataIndex;
            String str = object.url;
            int i = this.position;
            this.position = i + 1;
            map.put(str, Integer.valueOf(i));
        }
    }

    public void add(int location, SnsPostResources object) {
        if (!TextUtils.isEmpty(object.url) && !this.dataIndex.containsKey(object.url)) {
            this.dataList.add(location, object);
            resetDataIndex();
        }
    }

    public void addAll(List<SnsPostResources> list) {
        for (SnsPostResources info : list) {
            if (!(TextUtils.isEmpty(info.url) || this.dataIndex.containsKey(info.url))) {
                Map map = this.dataIndex;
                String str = info.url;
                int i = this.position;
                this.position = i + 1;
                map.put(str, Integer.valueOf(i));
                this.dataList.add(info);
            }
        }
    }

    public void addAll(int location, List<SnsPostResources> list) {
        int size = list.size();
        int i = 0;
        while (i < size) {
            SnsPostResources info = (SnsPostResources) list.get(i);
            if (!(TextUtils.isEmpty(info.url) || this.dataIndex.containsKey(info.url))) {
                list.remove(info);
                i--;
                size--;
            }
            i++;
        }
        this.dataList.addAll(location, list);
        resetDataIndex();
    }

    public void remove(SnsPostResources object) {
        this.dataList.remove(object);
        resetDataIndex();
    }

    public void remove(int pos) {
        this.dataList.remove(pos);
        resetDataIndex();
    }

    private void dataIndexClear() {
        this.dataIndex.clear();
        this.position = 0;
    }

    private void resetDataIndex() {
        dataIndexClear();
        for (SnsPostResources info : this.dataList) {
            Map map = this.dataIndex;
            String str = info.url;
            int i = this.position;
            this.position = i + 1;
            map.put(str, Integer.valueOf(i));
        }
    }
}
