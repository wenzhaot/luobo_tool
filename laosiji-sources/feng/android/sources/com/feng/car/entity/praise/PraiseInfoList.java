package com.feng.car.entity.praise;

import com.feng.car.entity.thread.GratuityRecordInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PraiseInfoList {
    private Map<Integer, Integer> dataIndex = new HashMap();
    private List<GratuityRecordInfo> dataList = new ArrayList();
    private int position = 0;

    public List<GratuityRecordInfo> getPraiseList() {
        return this.dataList;
    }

    public GratuityRecordInfo getLastPraiseInfo() {
        if (this.dataList.size() <= 0) {
            return null;
        }
        return (GratuityRecordInfo) this.dataList.get(this.dataList.size() - 1);
    }

    public GratuityRecordInfo get(int pos) {
        return (GratuityRecordInfo) this.dataList.get(pos);
    }

    public int getPosition(int id) {
        if (!this.dataIndex.containsKey(Integer.valueOf(id))) {
            return -1;
        }
        int pos = ((Integer) this.dataIndex.get(Integer.valueOf(id))).intValue();
        if (pos >= this.dataList.size()) {
            return -1;
        }
        if (id == get(pos).id) {
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

    public void add(GratuityRecordInfo object) {
        if (checkData(object)) {
            this.dataList.add(object);
            Map map = this.dataIndex;
            Integer valueOf = Integer.valueOf(object.id);
            int i = this.position;
            this.position = i + 1;
            map.put(valueOf, Integer.valueOf(i));
        }
    }

    public void add(int location, GratuityRecordInfo object) {
        if (checkData(object)) {
            this.dataList.add(location, object);
            resetDataIndex();
        }
    }

    public void addAll(List<GratuityRecordInfo> list) {
        int size = list.size();
        int i = 0;
        while (i < size) {
            GratuityRecordInfo info = (GratuityRecordInfo) list.get(i);
            if (checkData(info)) {
                Map map = this.dataIndex;
                Integer valueOf = Integer.valueOf(info.id);
                int i2 = this.position;
                this.position = i2 + 1;
                map.put(valueOf, Integer.valueOf(i2));
                this.dataList.add(info);
            } else {
                list.remove(info);
                i--;
                size--;
            }
            i++;
        }
    }

    public void addAll(int location, List<GratuityRecordInfo> list) {
        int size = list.size();
        int i = 0;
        while (i < size) {
            GratuityRecordInfo info = (GratuityRecordInfo) list.get(i);
            if (checkData(info)) {
                list.remove(info);
                i--;
                size--;
            }
            i++;
        }
        this.dataList.addAll(location, list);
        resetDataIndex();
    }

    private boolean checkData(GratuityRecordInfo info) {
        if (this.dataIndex.containsKey(Integer.valueOf(info.id)) || info.id <= 0 || ((info.resourcetype != 5 || info.comment.id <= 0 || info.comment.isdel != 0 || info.comment.sns.id <= 0 || info.comment.sns.isflag != 0) && (info.sns.resourceid <= 0 || info.sns.isflag != 0))) {
            return false;
        }
        return true;
    }

    public void remove(GratuityRecordInfo object) {
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
        for (GratuityRecordInfo info : this.dataList) {
            Map map = this.dataIndex;
            Integer valueOf = Integer.valueOf(info.id);
            int i = this.position;
            this.position = i + 1;
            map.put(valueOf, Integer.valueOf(i));
        }
    }
}
