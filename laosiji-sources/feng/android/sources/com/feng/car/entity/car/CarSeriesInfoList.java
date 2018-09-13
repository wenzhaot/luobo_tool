package com.feng.car.entity.car;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarSeriesInfoList {
    private Map<Integer, Integer> dataIndex = new HashMap();
    private List<CarSeriesInfo> dataList = new ArrayList();
    private Map<String, Integer> factoryName = new HashMap();
    private int position = 0;

    public List<CarSeriesInfo> getCarSeriesInfoList() {
        return this.dataList;
    }

    public CarSeriesInfo getLastCarSeriesInfo() {
        if (this.dataList.size() <= 0) {
            return null;
        }
        return (CarSeriesInfo) this.dataList.get(this.dataList.size() - 1);
    }

    public CarSeriesInfo get(int pos) {
        return (CarSeriesInfo) this.dataList.get(pos);
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
        this.factoryName.clear();
        this.position = 0;
    }

    public void add(CarSeriesInfo object) {
        if (!this.dataIndex.containsKey(Integer.valueOf(object.id)) && object.id > 0) {
            this.dataList.add(object);
            addFactoryPos(object);
            Map map = this.dataIndex;
            Integer valueOf = Integer.valueOf(object.id);
            int i = this.position;
            this.position = i + 1;
            map.put(valueOf, Integer.valueOf(i));
        }
    }

    public void add(int location, CarSeriesInfo object) {
        if (!this.dataIndex.containsKey(Integer.valueOf(object.id)) && object.id > 0) {
            this.dataList.add(location, object);
            resetDataIndex();
        }
    }

    public void addAll(List<CarSeriesInfo> list) {
        for (CarSeriesInfo info : list) {
            if (!this.dataIndex.containsKey(Integer.valueOf(info.id)) && info.id > 0) {
                addFactoryPos(info);
                Map map = this.dataIndex;
                Integer valueOf = Integer.valueOf(info.id);
                int i = this.position;
                this.position = i + 1;
                map.put(valueOf, Integer.valueOf(i));
                this.dataList.add(info);
            }
        }
    }

    public void addAll(int location, List<CarSeriesInfo> list) {
        int size = list.size();
        int i = 0;
        while (i < size) {
            CarSeriesInfo info = (CarSeriesInfo) list.get(i);
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

    private void addFactoryPos(CarSeriesInfo info) {
        if (this.factoryName.containsKey(info.factory.name)) {
            info.factory.factoryPosition = ((Integer) this.factoryName.get(info.factory.name)).intValue();
            return;
        }
        this.factoryName.put(info.factory.name, Integer.valueOf(this.position));
        info.factory.factoryPosition = this.position;
    }

    public void remove(CarSeriesInfo object) {
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
        for (CarSeriesInfo info : this.dataList) {
            addFactoryPos(info);
            Map map = this.dataIndex;
            Integer valueOf = Integer.valueOf(info.id);
            int i = this.position;
            this.position = i + 1;
            map.put(valueOf, Integer.valueOf(i));
        }
    }
}
