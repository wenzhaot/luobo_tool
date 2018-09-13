package com.feng.car.entity.sns;

import android.content.Context;
import android.text.TextUtils;
import com.feng.car.utils.JsonUtil;
import com.feng.library.utils.SharedUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;

public class SnsReadRecordList {
    private static SnsReadRecordList mInstance;
    private Map<Integer, Integer> dataIndex;
    private List<SnsReadRecord> dataList;
    private Context mContext;
    private int position = 0;

    private SnsReadRecordList(Context context) {
        this.mContext = context;
        this.dataList = new ArrayList();
        this.dataIndex = new HashMap();
        try {
            String recordsJson = SharedUtil.getArticlePositionRecords(this.mContext);
            if (!TextUtils.isEmpty(recordsJson)) {
                parserRecords(new JSONArray(recordsJson));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void parserRecords(JSONArray jsonArray) {
        List<SnsReadRecord> list = new ArrayList();
        if (jsonArray != null) {
            try {
                int size = jsonArray.length();
                for (int i = 0; i < size; i++) {
                    SnsReadRecord snsReadRecord = new SnsReadRecord();
                    snsReadRecord.parser(jsonArray.getJSONObject(i));
                    list.add(snsReadRecord);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            addAll(list);
        }
    }

    public void writeRecords() {
        SharedUtil.putArticlePositionRecords(this.mContext, JsonUtil.toJson(this.dataList));
    }

    public void addAll(List<SnsReadRecord> list) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                addReadRecord((SnsReadRecord) list.get(i));
            }
        }
    }

    public static SnsReadRecordList getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SnsReadRecordList(context);
        }
        return mInstance;
    }

    public SnsReadRecord getReadRecord(int snsId) {
        int position = getPosition(snsId);
        if (position != -1) {
            return (SnsReadRecord) this.dataList.get(position);
        }
        return null;
    }

    public int getPosition(int id) {
        if (!this.dataIndex.containsKey(Integer.valueOf(id))) {
            return -1;
        }
        int pos = ((Integer) this.dataIndex.get(Integer.valueOf(id))).intValue();
        if (pos >= this.dataList.size()) {
            return -1;
        }
        if (id != ((SnsReadRecord) this.dataList.get(pos)).id) {
            return -1;
        }
        return pos;
    }

    public int size() {
        return this.dataList.size();
    }

    public void clear() {
        this.dataList.clear();
        this.dataIndex.clear();
        this.position = 0;
    }

    public void remove(int pos) {
        if (pos != -1) {
            this.dataList.remove(pos);
            resetDataIndex();
        }
    }

    private void dataIndexClear() {
        this.dataIndex.clear();
        this.position = 0;
    }

    private void resetDataIndex() {
        dataIndexClear();
        for (SnsReadRecord info : this.dataList) {
            Map map = this.dataIndex;
            Integer valueOf = Integer.valueOf(info.id);
            int i = this.position;
            this.position = i + 1;
            map.put(valueOf, Integer.valueOf(i));
        }
    }

    public void addReadRecord(SnsReadRecord snsReadRecord) {
        if (this.dataList.size() >= 500) {
            remove(0);
        }
        this.dataList.add(snsReadRecord);
        Map map = this.dataIndex;
        Integer valueOf = Integer.valueOf(snsReadRecord.id);
        int i = this.position;
        this.position = i + 1;
        map.put(valueOf, Integer.valueOf(i));
    }
}
