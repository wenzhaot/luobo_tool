package com.feng.car.entity.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageVideoBucketList {
    private Map<String, Integer> dataIndex = new HashMap();
    private List<ImageVideoBucket> dataList = new ArrayList();
    private int position = 0;

    public List<ImageVideoBucket> getBucketList() {
        return this.dataList;
    }

    public ImageVideoBucket getLastBucketInfo() {
        if (this.dataList.size() <= 0) {
            return null;
        }
        return (ImageVideoBucket) this.dataList.get(this.dataList.size() - 1);
    }

    public ImageVideoBucket get(int pos) {
        return (ImageVideoBucket) this.dataList.get(pos);
    }

    public int getPosition(String id) {
        if (!this.dataIndex.containsKey(id)) {
            return -1;
        }
        int pos = ((Integer) this.dataIndex.get(id)).intValue();
        if (pos >= this.dataList.size()) {
            return -1;
        }
        if (id.equals(get(pos).bucketId)) {
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

    public void add(ImageVideoBucket object) {
        if (!this.dataIndex.containsKey(object.bucketId)) {
            this.dataList.add(object);
            Map map = this.dataIndex;
            String str = object.bucketId;
            int i = this.position;
            this.position = i + 1;
            map.put(str, Integer.valueOf(i));
        }
    }

    public void add(int location, ImageVideoBucket object) {
        if (!this.dataIndex.containsKey(object.bucketId)) {
            this.dataList.add(location, object);
            resetDataIndex();
        }
    }

    public void addAll(List<ImageVideoBucket> list) {
        for (ImageVideoBucket info : list) {
            if (!this.dataIndex.containsKey(info.bucketId)) {
                Map map = this.dataIndex;
                String str = info.bucketId;
                int i = this.position;
                this.position = i + 1;
                map.put(str, Integer.valueOf(i));
                this.dataList.add(info);
            }
        }
    }

    public void addAll(int location, List<ImageVideoBucket> list) {
        int size = list.size();
        int i = 0;
        while (i < size) {
            ImageVideoBucket info = (ImageVideoBucket) list.get(i);
            if (this.dataIndex.containsKey(info.bucketId)) {
                list.remove(info);
                i--;
                size--;
            }
            i++;
        }
        this.dataList.addAll(location, list);
        resetDataIndex();
    }

    public void remove(ImageVideoBucket object) {
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
        for (ImageVideoBucket info : this.dataList) {
            Map map = this.dataIndex;
            String str = info.bucketId;
            int i = this.position;
            this.position = i + 1;
            map.put(str, Integer.valueOf(i));
        }
    }
}
