package com.feng.car.entity.sns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SnsInfoList {
    private List<SnsInfo> adSns = new ArrayList();
    private Map<String, Integer> dataIndex = new HashMap();
    private List<SnsInfo> dataList = new ArrayList();
    private boolean isRecommend = false;
    private int mLastid = 0;
    private int position = 0;
    private Map<Integer, List<Integer>> userSnsPos = new HashMap();

    public void setRecommend(boolean recommend) {
        this.isRecommend = recommend;
    }

    public int getLastid() {
        return this.mLastid;
    }

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

    public int getPosition(String localKey) {
        if (!this.dataIndex.containsKey(localKey)) {
            return -1;
        }
        int pos = ((Integer) this.dataIndex.get(localKey)).intValue();
        if (pos >= this.dataList.size()) {
            return -1;
        }
        if (localKey.equals(get(pos).getLocalkey())) {
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
        this.userSnsPos.clear();
        this.position = 0;
    }

    public void add(SnsInfo object) {
        if (object.snstype != 1000) {
            if (!this.dataIndex.containsKey(object.getLocalkey()) && object.id > 0 && object.resourceid > 0) {
                if (!(object.isflag == 0 || object.isflag == -2 || object.isflag == -3)) {
                    return;
                }
            }
            return;
        }
        this.dataList.add(object);
        addUserSnsPos(object);
        Map map = this.dataIndex;
        String localkey = object.getLocalkey();
        int i = this.position;
        this.position = i + 1;
        map.put(localkey, Integer.valueOf(i));
    }

    public void add(int location, SnsInfo object) {
        if (object.snstype != 1000) {
            if (!this.dataIndex.containsKey(object.getLocalkey()) && object.id > 0 && object.resourceid > 0) {
                if (!(object.isflag == 0 || object.isflag == -2 || object.isflag == -3)) {
                    return;
                }
            }
            return;
        }
        this.dataList.add(location, object);
        resetDataIndex();
    }

    public void addAll(List<SnsInfo> list) {
        int size = list.size();
        int i = 0;
        while (i < size) {
            SnsInfo info = (SnsInfo) list.get(i);
            if (info.snstype == 1000 || (!this.dataIndex.containsKey(info.getLocalkey()) && info.id > 0 && info.resourceid > 0 && (info.isflag == 0 || info.isflag == -2 || info.isflag == -3))) {
                addUserSnsPos(info);
                Map map = this.dataIndex;
                String localkey = info.getLocalkey();
                int i2 = this.position;
                this.position = i2 + 1;
                map.put(localkey, Integer.valueOf(i2));
                if (info.ishistory == 1) {
                    this.mLastid = info.id;
                }
                this.dataList.add(info);
            } else {
                list.remove(info);
                i--;
                size--;
            }
            i++;
        }
    }

    public void addAll(int location, List<SnsInfo> list) {
        int size = list.size();
        Map<String, Integer> childMap = new HashMap();
        int i = 0;
        while (i < size) {
            SnsInfo info = (SnsInfo) list.get(i);
            if (this.isRecommend) {
                if (info.id <= 0 || info.resourceid <= 0 || info.isflag == 1 || info.isflag == -1) {
                    list.remove(info);
                    i--;
                    size--;
                } else if (this.dataIndex.containsKey(info.getLocalkey())) {
                    SnsInfo snsInfo = new SnsInfo();
                    snsInfo.id = info.id;
                    snsInfo.snstype = info.snstype;
                    this.dataList.remove(snsInfo);
                } else if (childMap.containsKey(info.getLocalkey())) {
                    list.remove(info);
                    i--;
                    size--;
                } else {
                    childMap.put(info.getLocalkey(), Integer.valueOf(info.id));
                    if (info.ishistory == 1) {
                        this.mLastid = info.id;
                    }
                }
            } else if (this.dataIndex.containsKey(info.getLocalkey()) || info.id <= 0 || info.resourceid <= 0 || info.isflag == 1 || info.isflag == -1) {
                list.remove(info);
                i--;
                size--;
            }
            i++;
        }
        this.dataList.addAll(location, list);
        resetDataIndex();
    }

    private void addUserSnsPos(SnsInfo info) {
        if (info.user.id > 0) {
            if (this.userSnsPos.containsKey(Integer.valueOf(info.user.id))) {
                ((List) this.userSnsPos.get(Integer.valueOf(info.user.id))).add(Integer.valueOf(this.position));
                return;
            }
            List<Integer> list = new ArrayList();
            list.add(Integer.valueOf(this.position));
            this.userSnsPos.put(Integer.valueOf(info.user.id), list);
        }
    }

    private void addAdPos(SnsInfo info) {
        if (info.snstype == 1000) {
            this.adSns.add(info);
        }
    }

    public List<Integer> getUserSnsPosByUserID(int userID) {
        if (this.userSnsPos.containsKey(Integer.valueOf(userID))) {
            return (List) this.userSnsPos.get(Integer.valueOf(userID));
        }
        return new ArrayList();
    }

    public void remove(SnsInfo object) {
        this.dataList.remove(object);
        resetDataIndex();
    }

    public void remove(int pos) {
        this.dataList.remove(pos);
        resetDataIndex();
    }

    public void removeAllAd() {
        int size = this.adSns.size();
        for (int i = 0; i < size; i++) {
            this.dataList.remove((SnsInfo) this.adSns.get(i));
        }
        this.adSns.clear();
        resetDataIndex();
    }

    private void dataIndexClear() {
        this.dataIndex.clear();
        this.userSnsPos.clear();
        this.adSns.clear();
        this.position = 0;
    }

    private void resetDataIndex() {
        dataIndexClear();
        for (SnsInfo info : this.dataList) {
            addUserSnsPos(info);
            addAdPos(info);
            Map map = this.dataIndex;
            String localkey = info.getLocalkey();
            int i = this.position;
            this.position = i + 1;
            map.put(localkey, Integer.valueOf(i));
            if (info.ishistory == 1) {
                this.mLastid = info.id;
            }
        }
    }
}
