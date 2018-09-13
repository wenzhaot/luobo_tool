package com.feng.car.entity.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageVideoInfoList {
    private Map<String, Integer> dataIndex = new HashMap();
    private List<ImageVideoInfo> dataList = new ArrayList();
    private int position = 0;

    public List<ImageVideoInfo> getImageVideoInfoList() {
        return this.dataList;
    }

    public ImageVideoInfo getLastImageVideoInfo() {
        if (this.dataList.size() <= 0) {
            return null;
        }
        return (ImageVideoInfo) this.dataList.get(this.dataList.size() - 1);
    }

    public ImageVideoInfo get(int pos) {
        return (ImageVideoInfo) this.dataList.get(pos);
    }

    public int getPosition(String id) {
        if (!this.dataIndex.containsKey(id)) {
            return -1;
        }
        int pos = ((Integer) this.dataIndex.get(id)).intValue();
        if (pos >= this.dataList.size()) {
            return -1;
        }
        if (id.equals(get(pos).id)) {
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

    public void add(ImageVideoInfo object) {
        if (!this.dataIndex.containsKey(object.id)) {
            this.dataList.add(object);
            Map map = this.dataIndex;
            String str = object.id;
            int i = this.position;
            this.position = i + 1;
            map.put(str, Integer.valueOf(i));
        }
    }

    public void add(int location, ImageVideoInfo object) {
        if (!this.dataIndex.containsKey(object.id)) {
            this.dataList.add(location, object);
            resetDataIndex();
        }
    }

    public void addAll(List<ImageVideoInfo> list) {
        for (ImageVideoInfo info : list) {
            if (!(info == null || this.dataIndex.containsKey(info.id))) {
                Map map = this.dataIndex;
                String str = info.id;
                int i = this.position;
                this.position = i + 1;
                map.put(str, Integer.valueOf(i));
                this.dataList.add(info);
            }
        }
    }

    public void addAll(int location, List<ImageVideoInfo> list) {
        int size = list.size();
        int i = 0;
        while (i < size) {
            ImageVideoInfo info = (ImageVideoInfo) list.get(i);
            if (this.dataIndex.containsKey(info.id)) {
                list.remove(info);
                i--;
                size--;
            }
            i++;
        }
        this.dataList.addAll(location, list);
        resetDataIndex();
    }

    public void remove(ImageVideoInfo object) {
        this.dataList.remove(object);
        resetDataIndex();
    }

    public void remove(int pos) {
        this.dataList.remove(pos);
        resetDataIndex();
    }

    public void removeAll(List<ImageVideoInfo> list) {
        if (list.size() > 0) {
            this.dataList.removeAll(list);
            resetDataIndex();
        }
    }

    private void dataIndexClear() {
        this.dataIndex.clear();
        this.position = 0;
    }

    private void resetDataIndex() {
        dataIndexClear();
        for (ImageVideoInfo info : this.dataList) {
            Map map = this.dataIndex;
            String str = info.id;
            int i = this.position;
            this.position = i + 1;
            map.put(str, Integer.valueOf(i));
        }
    }
}
