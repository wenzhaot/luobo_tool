package com.taobao.accs.utl;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import com.ta.utdid2.device.UTDevice;
import com.ut.mini.UTAnalytics;
import com.ut.mini.internal.UTOriginalCustomHitBuilder;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: Taobao */
public final class UTMini implements UT {
    public static final int EVENTID_AGOO = 19999;
    public static final int EVENTID_EXCPTION = 66003;
    public static final String PAGE_AGOO = "Page_Push";
    public static final String PAGE_REQUEST_ERROR = "REQ_ERROR";
    private static final String TAG = "UTMini";
    private static UTMini instance = new UTMini();

    public static UTMini getInstance() {
        return instance;
    }

    public final void start(Application application, String str, String str2, String str3) {
        try {
            UTAnalytics.getInstance().setAppApplicationInstance(application, new k(this, str3, str));
            ALog.d(TAG, "start success", new Object[0]);
        } catch (Throwable th) {
            ALog.e(TAG, "start fail ", th, new Object[0]);
        }
    }

    public final void stop(Context context) {
    }

    public final void onCaughException(Throwable th) {
    }

    public final void commitEvent(int i, String str, Object obj) {
        try {
            UTAnalytics.getInstance().getTracker("accs").send(new UTOriginalCustomHitBuilder(str, i, convertObjectToString(obj), null, null, null).build());
        } catch (Throwable th) {
            ALog.d(TAG, "commitEvent fail " + th.toString(), new Object[0]);
        }
    }

    public final void commitEvent(int i, String str, Object obj, Object obj2) {
        try {
            UTAnalytics.getInstance().getTracker("accs").send(new UTOriginalCustomHitBuilder(str, i, convertObjectToString(obj), convertObjectToString(obj2), null, null).build());
        } catch (Throwable th) {
            ALog.d(TAG, "commitEvent fail " + th.toString(), new Object[0]);
        }
    }

    public final void commitEvent(int i, String str, Object obj, Object obj2, Object obj3) {
        try {
            UTAnalytics.getInstance().getTracker("accs").send(new UTOriginalCustomHitBuilder(str, i, convertObjectToString(obj), convertObjectToString(obj2), convertObjectToString(obj3), null).build());
        } catch (Throwable th) {
            ALog.d(TAG, "commitEvent fail " + th.toString(), new Object[0]);
        }
    }

    public final void commitEvent(int i, String str, Object obj, Object obj2, Object obj3, String... strArr) {
        try {
            UTOriginalCustomHitBuilder uTOriginalCustomHitBuilder = new UTOriginalCustomHitBuilder(str, i, convertObjectToString(obj), convertObjectToString(obj2), convertObjectToString(obj3), null);
            uTOriginalCustomHitBuilder.setProperty("_field_args", _convertStringAToKVSString(strArr));
            UTAnalytics.getInstance().getTracker("accs").send(uTOriginalCustomHitBuilder.build());
        } catch (Throwable th) {
            ALog.d(TAG, "commitEvent fail " + th.toString(), new Object[0]);
        }
    }

    public final void commitEvent(int i, String str, Object obj, Object obj2, Object obj3, Map<String, String> map) {
        try {
            commitEvent(i, str, obj, obj2, obj3, mapToArray(map));
        } catch (Throwable th) {
            ALog.d(TAG, "commitEvent fail " + th.toString(), new Object[0]);
        }
    }

    public final String getUtdId(Context context) {
        try {
            return UTDevice.getUtdid(context);
        } catch (Throwable th) {
            return null;
        }
    }

    public static String convertObjectToString(Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof String) {
            return ((String) obj).toString();
        }
        if (obj instanceof Integer) {
            return "" + ((Integer) obj).intValue();
        }
        if (obj instanceof Long) {
            return "" + ((Long) obj).longValue();
        }
        if (obj instanceof Double) {
            return "" + ((Double) obj).doubleValue();
        }
        if (obj instanceof Float) {
            return "" + ((Float) obj).floatValue();
        }
        if (obj instanceof Short) {
            return "" + ((Short) obj).shortValue();
        }
        if (obj instanceof Byte) {
            return "" + ((Byte) obj).byteValue();
        }
        if (obj instanceof Boolean) {
            return ((Boolean) obj).toString();
        }
        if (obj instanceof Character) {
            return ((Character) obj).toString();
        }
        return obj.toString();
    }

    private String _convertStringAToKVSString(String... strArr) {
        int i = 0;
        if (strArr != null && strArr.length == 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (strArr != null && strArr.length > 0) {
            int i2 = 0;
            while (i < strArr.length) {
                if (!TextUtils.isEmpty(strArr[i])) {
                    if (i2 != 0) {
                        stringBuffer.append(MiPushClient.ACCEPT_TIME_SEPARATOR);
                    }
                    stringBuffer.append(strArr[i]);
                    i2 = 1;
                }
                i++;
            }
        }
        return stringBuffer.toString();
    }

    private static String[] mapToArray(Map<String, String> map) {
        int i = 0;
        if (map == null || map.isEmpty()) {
            return new String[0];
        }
        String[] strArr = new String[map.size()];
        Iterator it = map.entrySet().iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return strArr;
            }
            Entry entry = (Entry) it.next();
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            if (str == null) {
                strArr[i2] = "";
            } else {
                strArr[i2] = str + "=" + str2;
            }
            i = i2 + 1;
        }
    }

    public static String getCommitInfo(int i, String str, String str2, String str3, Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("eventId=").append(i).append(";arg1=").append(str).append(";arg2=").append(str2).append(";arg3=").append(str3);
        if (map != null) {
            stringBuilder.append(";").append("args=").append(map.toString());
        }
        return stringBuilder.toString();
    }

    public static String getCommitInfo(int i, String str, String str2, String str3, String str4) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("eventId=").append(i).append(";arg1=").append(str).append(";arg2=").append(str2).append(";arg3=").append(str3);
        if (str4 != null) {
            stringBuilder.append(";").append("args=").append(str4);
        }
        return stringBuilder.toString();
    }
}
