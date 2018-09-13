package com.feng.car.utils;

import com.feng.car.FengApplication;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsRecommendRecord;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

public class RecommendRecordUtil {
    private static RecommendRecordUtil mInstance;
    private final int EVERY_COUNT_NUM = 10;
    private Map<String, SnsRecommendRecord> mDataLogMap = new LinkedHashMap();

    public static RecommendRecordUtil getInstance() {
        if (mInstance == null) {
            mInstance = new RecommendRecordUtil();
        }
        return mInstance;
    }

    public void addData(SnsInfo info, boolean isClick) {
        if (info != null && info.snstype != 1000) {
            SnsRecommendRecord record;
            if (info.snstype == 3 || info.snstype == 2) {
                if (!this.mDataLogMap.containsKey(Integer.valueOf(info.id))) {
                    record = new SnsRecommendRecord();
                    record.snsid = info.id + "";
                    record.feedtype = info.feedtype + "";
                    record.feedstatus = AgooConstants.ACK_PACK_ERROR;
                    this.mDataLogMap.put(record.snsid, record);
                } else {
                    return;
                }
            } else if (info.snstype == 0 || info.snstype == 1 || info.snstype == 9) {
                record = new SnsRecommendRecord();
                record.snsid = info.id + "";
                record.feedtype = info.feedtype + "";
                if (this.mDataLogMap.containsKey(Integer.valueOf(info.id))) {
                    if (isClick) {
                        ((SnsRecommendRecord) this.mDataLogMap.get(record.snsid)).feedstatus = "20";
                        return;
                    }
                } else if (isClick) {
                    record.feedstatus = "20";
                    this.mDataLogMap.put(record.snsid, record);
                    return;
                } else {
                    record.feedstatus = AgooConstants.ACK_PACK_ERROR;
                    this.mDataLogMap.put(record.snsid, record);
                }
            }
            if (this.mDataLogMap.size() > 10) {
                addRecommendLog();
            }
        }
    }

    private List<SnsRecommendRecord> getDataList() {
        List<SnsRecommendRecord> list = new ArrayList();
        Iterator<Entry<String, SnsRecommendRecord>> it;
        Entry<String, SnsRecommendRecord> entry;
        if (this.mDataLogMap.size() > 10) {
            int i = 0;
            it = this.mDataLogMap.entrySet().iterator();
            while (it.hasNext() && i <= 10) {
                entry = (Entry) it.next();
                list.add(entry.getValue());
                it.remove();
                this.mDataLogMap.remove(entry.getKey());
                i++;
            }
        } else {
            it = this.mDataLogMap.entrySet().iterator();
            while (it.hasNext()) {
                entry = (Entry) it.next();
                list.add(entry.getValue());
                it.remove();
                this.mDataLogMap.remove(entry.getKey());
            }
        }
        return list;
    }

    public void addRecommendLog() {
        if (this.mDataLogMap.size() > 0) {
            Map<String, Object> map = new HashMap();
            map.put("datalog", getDataList());
            FengApplication.getInstance().httpRequest(HttpConstant.RECOMMEND_ADD_LOG, map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                }

                public void onStart() {
                }

                public void onFinish() {
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        int code = new JSONObject(content).getInt("code");
                        if (code != 1) {
                            FengApplication.getInstance().upLoadLog(true, "recommend/addlog/  " + code);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.RECOMMEND_ADD_LOG, content, e);
                    }
                }
            });
        }
    }

    public void clear() {
        this.mDataLogMap.clear();
    }
}
