package com.feng.car.entity.car;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OldDriverVoiceInfoList {
    private List<OldDriverVoiceInfo> dataList = new ArrayList();
    private int position = 0;
    private Map<Integer, List<Integer>> userSnsPos = new HashMap();

    public List<OldDriverVoiceInfo> getOldDriverVoiceList() {
        return this.dataList;
    }

    public OldDriverVoiceInfo getLastOldDriverInfo() {
        if (this.dataList.size() <= 0) {
            return null;
        }
        return (OldDriverVoiceInfo) this.dataList.get(this.dataList.size() - 1);
    }

    public OldDriverVoiceInfo get(int pos) {
        return (OldDriverVoiceInfo) this.dataList.get(pos);
    }

    public int size() {
        return this.dataList.size();
    }

    public void clear() {
        this.dataList.clear();
        this.userSnsPos.clear();
        this.position = 0;
    }

    public void add(OldDriverVoiceInfo object) {
        this.dataList.add(object);
        addUserSnsPos(object);
        this.position++;
    }

    public void add(int location, OldDriverVoiceInfo object) {
        this.dataList.add(location, object);
        resetDataIndex();
    }

    public void addAll(List<OldDriverVoiceInfo> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            OldDriverVoiceInfo info = (OldDriverVoiceInfo) list.get(i);
            addUserSnsPos(info);
            this.dataList.add(info);
            this.position++;
        }
    }

    public void addAll(int location, List<OldDriverVoiceInfo> list) {
        this.dataList.addAll(location, list);
        resetDataIndex();
    }

    private void addUserSnsPos(OldDriverVoiceInfo info) {
        if (info.authoruser.id > 0) {
            if (this.userSnsPos.containsKey(Integer.valueOf(info.authoruser.id))) {
                ((List) this.userSnsPos.get(Integer.valueOf(info.authoruser.id))).add(Integer.valueOf(this.position));
                return;
            }
            List<Integer> list = new ArrayList();
            list.add(Integer.valueOf(this.position));
            this.userSnsPos.put(Integer.valueOf(info.authoruser.id), list);
        }
    }

    public List<Integer> getUserOldVoicePosByUserID(int userID) {
        if (this.userSnsPos.containsKey(Integer.valueOf(userID))) {
            return (List) this.userSnsPos.get(Integer.valueOf(userID));
        }
        return new ArrayList();
    }

    public void remove(OldDriverVoiceInfo object) {
        this.dataList.remove(object);
        resetDataIndex();
    }

    public void remove(int pos) {
        this.dataList.remove(pos);
        resetDataIndex();
    }

    private void dataIndexClear() {
        this.userSnsPos.clear();
        this.position = 0;
    }

    private void resetDataIndex() {
        dataIndexClear();
        for (OldDriverVoiceInfo info : this.dataList) {
            addUserSnsPos(info);
            this.position++;
        }
    }
}
