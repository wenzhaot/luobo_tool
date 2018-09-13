package com.feng.car.entity.car;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeriesPriceVoList {
    private Map<Integer, Integer> dataIndex = new HashMap();
    private List<SeriesPriceVo> dataList = new ArrayList();
    private int position = 0;

    public List<SeriesPriceVo> getSeriesPriceVoList() {
        return this.dataList;
    }

    public SeriesPriceVo getLastSeriesPriceVo() {
        if (this.dataList.size() <= 0) {
            return null;
        }
        return (SeriesPriceVo) this.dataList.get(this.dataList.size() - 1);
    }

    public SeriesPriceVo get(int pos) {
        return (SeriesPriceVo) this.dataList.get(pos);
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

    public void add(SeriesPriceVo object) {
        if (!this.dataIndex.containsKey(Integer.valueOf(object.id)) && object.id > 0) {
            this.dataList.add(object);
            Map map = this.dataIndex;
            Integer valueOf = Integer.valueOf(object.id);
            int i = this.position;
            this.position = i + 1;
            map.put(valueOf, Integer.valueOf(i));
        }
    }

    public void add(int location, SeriesPriceVo object) {
        if (!this.dataIndex.containsKey(Integer.valueOf(object.id)) && object.id > 0) {
            this.dataList.add(location, object);
            resetDataIndex();
        }
    }

    public void addAll(List<SeriesPriceVo> list) {
        for (SeriesPriceVo info : list) {
            if (!this.dataIndex.containsKey(Integer.valueOf(info.id)) && info.id > 0) {
                Map map = this.dataIndex;
                Integer valueOf = Integer.valueOf(info.id);
                int i = this.position;
                this.position = i + 1;
                map.put(valueOf, Integer.valueOf(i));
                this.dataList.add(info);
            }
        }
    }

    public void addAll(int location, List<SeriesPriceVo> list) {
        int size = list.size();
        int i = 0;
        while (i < size) {
            SeriesPriceVo info = (SeriesPriceVo) list.get(i);
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

    public void remove(SeriesPriceVo object) {
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
        for (SeriesPriceVo info : this.dataList) {
            Map map = this.dataIndex;
            Integer valueOf = Integer.valueOf(info.id);
            int i = this.position;
            this.position = i + 1;
            map.put(valueOf, Integer.valueOf(i));
        }
    }
}
