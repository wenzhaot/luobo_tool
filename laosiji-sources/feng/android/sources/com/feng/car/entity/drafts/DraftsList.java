package com.feng.car.entity.drafts;

import com.feng.car.entity.sns.SnsInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DraftsList {
    private Map<Integer, Integer> dataIndex = new HashMap();
    private List<SnsInfo> dataList = new ArrayList();
    private int position = 0;

    public List<SnsInfo> getSnsList() {
        return this.dataList;
    }

    public SnsInfo getLastSnsInfo() {
        if (this.dataList.size() <= 0) {
            return null;
        }
        return (SnsInfo) this.dataList.get(this.dataList.size() - 1);
    }

    public SnsInfo get(int pos) {
        return (SnsInfo) this.dataList.get(pos);
    }

    public int getPosition(int id) {
        if (!this.dataIndex.containsKey(Integer.valueOf(id))) {
            return -1;
        }
        int pos = ((Integer) this.dataIndex.get(Integer.valueOf(id))).intValue();
        if (pos >= this.dataList.size()) {
            return -1;
        }
        if (id == get(pos).resourceid) {
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

    public void add(SnsInfo object) {
        if (object.resourceid >= 0 && !this.dataIndex.containsKey(Integer.valueOf(object.resourceid))) {
            this.dataList.add(object);
            Map map = this.dataIndex;
            Integer valueOf = Integer.valueOf(object.resourceid);
            int i = this.position;
            this.position = i + 1;
            map.put(valueOf, Integer.valueOf(i));
        }
    }

    public void add(int location, SnsInfo object) {
        if (object.resourceid >= 0 && !this.dataIndex.containsKey(Integer.valueOf(object.resourceid))) {
            this.dataList.add(location, object);
            resetDataIndex();
        }
    }

    public void addAll(List<SnsInfo> list) {
        for (SnsInfo info : list) {
            if (info.resourceid >= 0 && !this.dataIndex.containsKey(Integer.valueOf(info.resourceid))) {
                Map map = this.dataIndex;
                Integer valueOf = Integer.valueOf(info.resourceid);
                int i = this.position;
                this.position = i + 1;
                map.put(valueOf, Integer.valueOf(i));
                this.dataList.add(info);
            }
        }
    }

    public void addAll(int location, List<SnsInfo> list) {
        int size = list.size();
        int i = 0;
        while (i < size) {
            SnsInfo info = (SnsInfo) list.get(i);
            if (info.resourceid >= 0 && this.dataIndex.containsKey(Integer.valueOf(info.resourceid))) {
                list.remove(info);
                i--;
                size--;
            }
            i++;
        }
        this.dataList.addAll(location, list);
        resetDataIndex();
    }

    public void remove(SnsInfo object) {
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
        for (SnsInfo info : this.dataList) {
            Map map = this.dataIndex;
            Integer valueOf = Integer.valueOf(info.resourceid);
            int i = this.position;
            this.position = i + 1;
            map.put(valueOf, Integer.valueOf(i));
        }
    }
}
