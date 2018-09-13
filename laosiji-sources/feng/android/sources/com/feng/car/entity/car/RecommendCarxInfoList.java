package com.feng.car.entity.car;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendCarxInfoList {
    private Map<Integer, Integer> dataIndex = new HashMap();
    private List<RecommendCarxInfo> dataList = new ArrayList();
    private int position = 0;

    public List<RecommendCarxInfo> getRecommendCarxInfoList() {
        return this.dataList;
    }

    public RecommendCarxInfo getLastRecommendCarxInfo() {
        if (this.dataList.size() <= 0) {
            return null;
        }
        return (RecommendCarxInfo) this.dataList.get(this.dataList.size() - 1);
    }

    public RecommendCarxInfo get(int pos) {
        return (RecommendCarxInfo) this.dataList.get(pos);
    }

    public int getPosition(int id) {
        if (!this.dataIndex.containsKey(Integer.valueOf(id))) {
            return -1;
        }
        int pos = ((Integer) this.dataIndex.get(Integer.valueOf(id))).intValue();
        if (pos >= this.dataList.size()) {
            return -1;
        }
        if (id == get(pos).carx.id) {
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

    public void add(RecommendCarxInfo object) {
        if (!this.dataIndex.containsKey(Integer.valueOf(object.carx.id)) && object.carx.id > 0) {
            this.dataList.add(object);
            Map map = this.dataIndex;
            Integer valueOf = Integer.valueOf(object.carx.id);
            int i = this.position;
            this.position = i + 1;
            map.put(valueOf, Integer.valueOf(i));
        }
    }

    public void add(int location, RecommendCarxInfo object) {
        if (!this.dataIndex.containsKey(Integer.valueOf(object.carx.id)) && object.carx.id > 0) {
            this.dataList.add(location, object);
            resetDataIndex();
        }
    }

    public void addAll(List<RecommendCarxInfo> list) {
        for (RecommendCarxInfo info : list) {
            if (!this.dataIndex.containsKey(Integer.valueOf(info.carx.id)) && info.carx.id > 0) {
                Map map = this.dataIndex;
                Integer valueOf = Integer.valueOf(info.carx.id);
                int i = this.position;
                this.position = i + 1;
                map.put(valueOf, Integer.valueOf(i));
                this.dataList.add(info);
            }
        }
    }

    public void addAll(int location, List<RecommendCarxInfo> list) {
        int size = list.size();
        int i = 0;
        while (i < size) {
            RecommendCarxInfo info = (RecommendCarxInfo) list.get(i);
            if (this.dataIndex.containsKey(Integer.valueOf(info.carx.id))) {
                list.remove(info);
                i--;
                size--;
            }
            i++;
        }
        this.dataList.addAll(location, list);
        resetDataIndex();
    }

    public void remove(RecommendCarxInfo object) {
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
        for (RecommendCarxInfo info : this.dataList) {
            Map map = this.dataIndex;
            Integer valueOf = Integer.valueOf(info.carx.id);
            int i = this.position;
            this.position = i + 1;
            map.put(valueOf, Integer.valueOf(i));
        }
    }
}
