package com.feng.car.entity.comment;

import com.feng.car.entity.thread.CommentInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentInfoList {
    private Map<Integer, Integer> dataIndex = new HashMap();
    private List<CommentInfo> dataList = new ArrayList();
    private boolean isCheckSns = true;
    private int position = 0;

    public CommentInfoList(boolean isCheckSns) {
        this.isCheckSns = isCheckSns;
    }

    public List<CommentInfo> getCommentList() {
        return this.dataList;
    }

    public CommentInfo getLastCommentInfo() {
        if (this.dataList.size() <= 0) {
            return null;
        }
        return (CommentInfo) this.dataList.get(this.dataList.size() - 1);
    }

    public CommentInfo get(int pos) {
        return (CommentInfo) this.dataList.get(pos);
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

    public void add(CommentInfo object) {
        if (!this.dataIndex.containsKey(Integer.valueOf(object.id)) && object.id > 0 && object.isdel == 0 && cheeckSns(object)) {
            this.dataList.add(object);
            Map map = this.dataIndex;
            Integer valueOf = Integer.valueOf(object.id);
            int i = this.position;
            this.position = i + 1;
            map.put(valueOf, Integer.valueOf(i));
        }
    }

    public void add(int location, CommentInfo object) {
        if (!this.dataIndex.containsKey(Integer.valueOf(object.id)) && object.id > 0 && object.isdel == 0 && cheeckSns(object)) {
            this.dataList.add(location, object);
            resetDataIndex();
        }
    }

    public void addAll(List<CommentInfo> list) {
        int size = list.size();
        int i = 0;
        while (i < size) {
            CommentInfo info = (CommentInfo) list.get(i);
            if (this.dataIndex.containsKey(Integer.valueOf(info.id)) || info.id <= 0 || info.isdel != 0 || !cheeckSns(info)) {
                list.remove(info);
                i--;
                size--;
            } else {
                Map map = this.dataIndex;
                Integer valueOf = Integer.valueOf(info.id);
                int i2 = this.position;
                this.position = i2 + 1;
                map.put(valueOf, Integer.valueOf(i2));
                this.dataList.add(info);
            }
            i++;
        }
    }

    public void addAll(int location, List<CommentInfo> list) {
        int size = list.size();
        int i = 0;
        while (i < size) {
            CommentInfo info = (CommentInfo) list.get(i);
            if (this.dataIndex.containsKey(Integer.valueOf(info.id)) && info.id > 0 && info.isdel == 0 && cheeckSns(info)) {
                list.remove(info);
                i--;
                size--;
            }
            i++;
        }
        this.dataList.addAll(location, list);
        resetDataIndex();
    }

    private boolean cheeckSns(CommentInfo info) {
        if (!this.isCheckSns) {
            return true;
        }
        if (info.sns.resourceid <= 0 || info.sns.isflag != 0) {
            return false;
        }
        return true;
    }

    public void remove(CommentInfo object) {
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
        for (CommentInfo info : this.dataList) {
            Map map = this.dataIndex;
            Integer valueOf = Integer.valueOf(info.id);
            int i = this.position;
            this.position = i + 1;
            map.put(valueOf, Integer.valueOf(i));
        }
    }
}
