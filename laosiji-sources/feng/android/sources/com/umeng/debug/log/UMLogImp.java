package com.umeng.debug.log;

import android.os.Bundle;
import com.umeng.commonsdk.debug.UMDebugLog;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class UMLogImp implements UMDebugLog {
    public static Map logMap;

    public UMLogImp() {
        int i;
        int i2 = 0;
        if (logMap == null) {
            logMap = new HashMap();
        }
        Field[] fields = UMLogCommon.class.getFields();
        if (fields != null) {
            for (i = 0; i < fields.length; i++) {
                try {
                    logMap.put(fields[i].getName(), fields[i].get(fields[i].getName()).toString());
                } catch (Exception e) {
                }
            }
        }
        fields = UMLogAnalytics.class.getFields();
        if (fields != null) {
            for (i = 0; i < fields.length; i++) {
                try {
                    logMap.put(fields[i].getName(), fields[i].get(fields[i].getName()).toString());
                } catch (Exception e2) {
                }
            }
        }
        fields = UMLogPush.class.getFields();
        if (fields != null) {
            for (i = 0; i < fields.length; i++) {
                try {
                    logMap.put(fields[i].getName(), fields[i].get(fields[i].getName()).toString());
                } catch (Exception e3) {
                }
            }
        }
        fields = UMLogShare.class.getFields();
        if (fields != null) {
            for (i = 0; i < fields.length; i++) {
                try {
                    logMap.put(fields[i].getName(), fields[i].get(fields[i].getName()).toString());
                } catch (Exception e4) {
                }
            }
        }
        Field[] fields2 = UMLogError.class.getFields();
        if (fields2 != null) {
            while (i2 < fields2.length) {
                try {
                    logMap.put(fields2[i2].getName(), fields2[i2].get(fields2[i2].getName()).toString());
                } catch (Exception e5) {
                }
                i2++;
            }
        }
    }

    public void aq(int i, String str, String str2) {
        UMLog.aq(i, str, str2);
    }

    public void aq(String str, int i, String str2) {
        UMLog.aq(str, i, str2);
    }

    public void aq(String str, int i, String str2, String[] strArr, String[] strArr2, String[] strArr3, String[] strArr4) {
        UMLog.aq(str, i, str2, strArr, strArr2, strArr3, strArr4);
    }

    public void aq(String str, int i, String str2, String str3) {
        UMLog.aq(str, i, str2, str3);
    }

    public void aq(String str, String str2, int i, String str3) {
        UMLog.aq(str, str2, i, str3);
    }

    public void aq(String str, String str2, int i, String str3, String[] strArr, String[] strArr2, String[] strArr3, String[] strArr4) {
        UMLog.aq(str, str2, i, str3, strArr, strArr2, strArr3, strArr4);
    }

    public void mutlInfo(int i, String... strArr) {
        UMLog.mutlInfo(i, strArr);
    }

    public void mutlInfo(String str, int i, String str2) {
        UMLog.mutlInfo(str, i, str2);
    }

    public void mutlInfo(String str, int i, String str2, String[] strArr, String[] strArr2) {
        UMLog.mutlInfo(str, i, str2, strArr, strArr2);
    }

    public void mutlInfo(String str, int i, String... strArr) {
        UMLog.mutlInfo(str, i, strArr);
    }

    public void mutlInfo(String str, String str2, int i, String str3) {
        UMLog.mutlInfo(str, str2, i, str3);
    }

    public void mutlInfo(String str, String str2, int i, String str3, String[] strArr, String[] strArr2) {
        UMLog.mutlInfo(str, str2, i, str3, strArr, strArr2);
    }

    public void bundle(int i, Bundle bundle) {
        UMLog.bundle(i, bundle);
    }

    public void bundle(String str, int i, Bundle bundle) {
        UMLog.bundle(str, i, bundle);
    }

    public void jsonObject(JSONObject jSONObject) {
        UMLog.jsonObject(jSONObject);
    }

    public void jsonObject(String str, JSONObject jSONObject) {
        UMLog.jsonObject(str, jSONObject);
    }

    public void jsonArry(JSONArray jSONArray) {
        UMLog.jsonArry(jSONArray);
    }

    public void jsonArry(String str, JSONArray jSONArray) {
        UMLog.jsonArry(str, jSONArray);
    }
}
