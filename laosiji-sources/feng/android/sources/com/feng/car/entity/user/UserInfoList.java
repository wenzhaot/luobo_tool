package com.feng.car.entity.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfoList {
    private Map<Integer, Integer> dataIndex = new HashMap();
    private List<UserInfo> dataList = new ArrayList();
    private int position = 0;

    public List<UserInfo> getUserInfoList() {
        return this.dataList;
    }

    public UserInfo getLastUserInfo() {
        if (this.dataList.size() <= 0) {
            return null;
        }
        return (UserInfo) this.dataList.get(this.dataList.size() - 1);
    }

    public UserInfo get(int pos) {
        return (UserInfo) this.dataList.get(pos);
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

    public void add(UserInfo object) {
        if (!this.dataIndex.containsKey(Integer.valueOf(object.id)) && object.id > 0) {
            this.dataList.add(object);
            Map map = this.dataIndex;
            Integer valueOf = Integer.valueOf(object.id);
            int i = this.position;
            this.position = i + 1;
            map.put(valueOf, Integer.valueOf(i));
        }
    }

    public void add(int location, UserInfo object) {
        if (!this.dataIndex.containsKey(Integer.valueOf(object.id)) && object.id > 0) {
            this.dataList.add(location, object);
            resetDataIndex();
        }
    }

    public void addAll(List<UserInfo> list) {
        for (UserInfo info : list) {
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

    public void addAll(int location, List<UserInfo> list) {
        int size = list.size();
        int i = 0;
        while (i < size) {
            UserInfo info = (UserInfo) list.get(i);
            if (this.dataIndex.containsKey(Integer.valueOf(info.id)) && info.id > 0) {
                list.remove(info);
                i--;
                size--;
            }
            i++;
        }
        this.dataList.addAll(location, list);
        resetDataIndex();
    }

    public void remove(UserInfo object) {
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
        for (UserInfo info : this.dataList) {
            Map map = this.dataIndex;
            Integer valueOf = Integer.valueOf(info.id);
            int i = this.position;
            this.position = i + 1;
            map.put(valueOf, Integer.valueOf(i));
        }
    }
}
