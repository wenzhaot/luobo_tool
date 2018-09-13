package com.feng.car.entity.car;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarModelInfoList {
    private Map<Integer, Integer> dataIndex = new HashMap();
    private List<CarModelInfo> dataList = new ArrayList();
    private int mType = 0;
    private int position = 0;

    public CarModelInfoList(int type) {
        this.mType = type;
    }

    public List<CarModelInfo> getCarModelInfoList() {
        return this.dataList;
    }

    public CarModelInfo getLastCarModelInfo() {
        if (this.dataList.size() <= 0) {
            return null;
        }
        return (CarModelInfo) this.dataList.get(this.dataList.size() - 1);
    }

    public CarModelInfo get(int pos) {
        return (CarModelInfo) this.dataList.get(pos);
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

    public void add(CarModelInfo object) {
        if (!this.dataIndex.containsKey(Integer.valueOf(object.id)) && object.id > 0 && checkData(object)) {
            this.dataList.add(object);
            Map map = this.dataIndex;
            Integer valueOf = Integer.valueOf(object.id);
            int i = this.position;
            this.position = i + 1;
            map.put(valueOf, Integer.valueOf(i));
        }
    }

    public void add(int location, CarModelInfo object) {
        if (!this.dataIndex.containsKey(Integer.valueOf(object.id)) && object.id > 0 && checkData(object)) {
            this.dataList.add(location, object);
            resetDataIndex();
        }
    }

    public void addAll(List<CarModelInfo> list) {
        for (CarModelInfo info : list) {
            if (!this.dataIndex.containsKey(Integer.valueOf(info.id)) && info.id > 0 && checkData(info)) {
                Map map = this.dataIndex;
                Integer valueOf = Integer.valueOf(info.id);
                int i = this.position;
                this.position = i + 1;
                map.put(valueOf, Integer.valueOf(i));
                this.dataList.add(info);
            }
        }
    }

    public void addAll(int location, List<CarModelInfo> list) {
        int size = list.size();
        int i = 0;
        while (i < size) {
            CarModelInfo info = (CarModelInfo) list.get(i);
            if (this.dataIndex.containsKey(Integer.valueOf(info.id))) {
                list.remove(info);
                i--;
                size--;
            }
            i++;
        }
        this.dataList.addAll(location, list);
        resetDataIndex();
    }

    private boolean checkData(CarModelInfo info) {
        if (this.mType != 0 && info.isconfig == 0) {
            return false;
        }
        return true;
    }

    public void remove(CarModelInfo object) {
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
        for (CarModelInfo info : this.dataList) {
            Map map = this.dataIndex;
            Integer valueOf = Integer.valueOf(info.id);
            int i = this.position;
            this.position = i + 1;
            map.put(valueOf, Integer.valueOf(i));
        }
    }
}
