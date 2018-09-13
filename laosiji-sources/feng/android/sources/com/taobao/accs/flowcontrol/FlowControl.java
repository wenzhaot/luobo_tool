package com.taobao.accs.flowcontrol;

import android.content.Context;
import android.text.TextUtils;
import com.taobao.accs.base.TaoBaseService.ExtHeaderType;
import com.taobao.accs.utl.ALog;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: Taobao */
public class FlowControl {
    public static final int DELAY_MAX = -1;
    public static final int DELAY_MAX_BRUSH = -1000;
    public static final int HIGH_FLOW_CTRL = 2;
    public static final int HIGH_FLOW_CTRL_BRUSH = 3;
    public static final int LOW_FLOW_CTRL = 1;
    public static final int NO_FLOW_CTRL = 0;
    public static final String SERVICE_ALL = "ALL";
    public static final String SERVICE_ALL_BRUSH = "ALL_BRUSH";
    public static final int STATUS_FLOW_CTRL_ALL = 420;
    public static final int STATUS_FLOW_CTRL_BRUSH = 422;
    public static final int STATUS_FLOW_CTRL_CUR = 421;
    private Context a;
    private FlowCtrlInfoHolder b;

    /* compiled from: Taobao */
    public static class FlowControlInfo implements Serializable {
        private static final long serialVersionUID = -2259991484877844919L;
        public String bizId;
        public long delayTime;
        public long expireTime;
        public String serviceId;
        public long startTime;
        public int status;

        public FlowControlInfo(String str, String str2, int i, long j, long j2, long j3) {
            this.serviceId = str;
            this.bizId = str2;
            this.status = i;
            this.delayTime = j;
            if (j2 <= 0) {
                j2 = 0;
            }
            this.expireTime = j2;
            if (j3 <= 0) {
                j3 = 0;
            }
            this.startTime = j3;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() - (this.startTime + this.expireTime) > 0;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("flow ctrl serviceId:").append(this.serviceId).append(" bizId:").append(this.bizId).append(" status:").append(this.status).append(" delayTime:").append(this.delayTime).append(" startTime:").append(this.startTime).append(" expireTime:").append(this.expireTime);
            return stringBuffer.toString();
        }
    }

    /* compiled from: Taobao */
    public static class FlowCtrlInfoHolder implements Serializable {
        private static final long serialVersionUID = 6307563052429742524L;
        Map<String, FlowControlInfo> flowCtrlMap = null;

        public void put(String str, String str2, FlowControlInfo flowControlInfo) {
            Object str3;
            if (!TextUtils.isEmpty(str2)) {
                str3 = str3 + "_" + str2;
            }
            if (this.flowCtrlMap == null) {
                this.flowCtrlMap = new HashMap();
            }
            this.flowCtrlMap.put(str3, flowControlInfo);
        }

        public FlowControlInfo get(String str, String str2) {
            if (this.flowCtrlMap == null) {
                return null;
            }
            Object str3;
            if (!TextUtils.isEmpty(str2)) {
                str3 = str3 + "_" + str2;
            }
            return (FlowControlInfo) this.flowCtrlMap.get(str3);
        }
    }

    public FlowControl(Context context) {
        this.a = context;
    }

    public int a(Map<Integer, String> map, String str) {
        long j = 0;
        int i = 0;
        if (map != null) {
            try {
                String str2 = (String) map.get(Integer.valueOf(ExtHeaderType.TYPE_STATUS.ordinal()));
                String str3 = (String) map.get(Integer.valueOf(ExtHeaderType.TYPE_DELAY.ordinal()));
                String str4 = (String) map.get(Integer.valueOf(ExtHeaderType.TYPE_EXPIRE.ordinal()));
                String str5 = (String) map.get(Integer.valueOf(ExtHeaderType.TYPE_BUSINESS.ordinal()));
                i = TextUtils.isEmpty(str2) ? 0 : Integer.valueOf(str2).intValue();
                j = TextUtils.isEmpty(str3) ? 0 : Long.valueOf(str3).longValue();
                long longValue = TextUtils.isEmpty(str4) ? 0 : Long.valueOf(str4).longValue();
                if ((i != STATUS_FLOW_CTRL_ALL && i != STATUS_FLOW_CTRL_CUR && i != STATUS_FLOW_CTRL_BRUSH) || !a(j, longValue)) {
                    return 0;
                }
                synchronized (this) {
                    if (this.b == null) {
                        this.b = new FlowCtrlInfoHolder();
                    }
                    FlowControlInfo flowControlInfo = null;
                    if (i == STATUS_FLOW_CTRL_ALL) {
                        flowControlInfo = new FlowControlInfo(SERVICE_ALL, "", i, j, longValue, System.currentTimeMillis());
                        this.b.put(SERVICE_ALL, "", flowControlInfo);
                    } else if (i == STATUS_FLOW_CTRL_BRUSH) {
                        flowControlInfo = new FlowControlInfo(SERVICE_ALL_BRUSH, "", i, j, longValue, System.currentTimeMillis());
                        this.b.put(SERVICE_ALL_BRUSH, "", flowControlInfo);
                    } else if (i == STATUS_FLOW_CTRL_CUR) {
                        if (!TextUtils.isEmpty(str)) {
                            flowControlInfo = new FlowControlInfo(str, str5, i, j, longValue, System.currentTimeMillis());
                            this.b.put(str, str5, flowControlInfo);
                        }
                    }
                    if (flowControlInfo != null) {
                        ALog.e("FlowControl", "updateFlowCtrlInfo " + flowControlInfo.toString(), new Object[0]);
                    }
                }
            } catch (Throwable th) {
                ALog.e("FlowControl", "updateFlowCtrlInfo", th, new Object[0]);
            }
        }
        if (j > 0) {
            return 1;
        }
        if (j == 0) {
            return 0;
        }
        if (STATUS_FLOW_CTRL_BRUSH == i) {
            return 3;
        }
        return 2;
    }

    private boolean a(long j, long j2) {
        if (j != 0 && j2 > 0) {
            return true;
        }
        ALog.e("FlowControl", "error flow ctrl info", new Object[0]);
        return false;
    }

    public long a(String str, String str2) {
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        long j4 = 0;
        long j5 = 0;
        if (!(this.b == null || this.b.flowCtrlMap == null || TextUtils.isEmpty(str))) {
            synchronized (this) {
                FlowControlInfo flowControlInfo = this.b.get(SERVICE_ALL, null);
                FlowControlInfo flowControlInfo2 = this.b.get(SERVICE_ALL_BRUSH, null);
                FlowControlInfo flowControlInfo3 = this.b.get(str, null);
                FlowControlInfo flowControlInfo4 = this.b.get(str, str2);
                if (flowControlInfo != null) {
                    j2 = flowControlInfo.isExpired() ? 0 : flowControlInfo.delayTime;
                }
                if (flowControlInfo2 != null) {
                    j3 = flowControlInfo2.isExpired() ? 0 : flowControlInfo2.delayTime;
                }
                if (flowControlInfo3 != null) {
                    j4 = flowControlInfo3.isExpired() ? 0 : flowControlInfo3.delayTime;
                }
                if (flowControlInfo4 != null) {
                    j = flowControlInfo4.isExpired() ? 0 : flowControlInfo4.delayTime;
                }
                if (j2 == -1 || j == -1 || j4 == -1) {
                    j5 = -1;
                } else if (j3 == -1) {
                    j5 = -1000;
                } else {
                    if (j2 > j) {
                        j5 = j2;
                    } else {
                        j5 = j;
                    }
                    if (j5 <= j4) {
                        j5 = j4;
                    }
                }
                if ((flowControlInfo4 != null && flowControlInfo4.isExpired()) || (flowControlInfo != null && flowControlInfo.isExpired())) {
                    a();
                }
            }
            ALog.e("FlowControl", "getFlowCtrlDelay service " + str + " biz " + str2 + " result:" + j5 + " global:" + j2 + " serviceDelay:" + j4 + " bidDelay:" + j, new Object[0]);
        }
        return j5;
    }

    private void a() {
        if (this.b != null && this.b.flowCtrlMap != null) {
            synchronized (this) {
                Iterator it = this.b.flowCtrlMap.entrySet().iterator();
                while (it.hasNext()) {
                    if (((FlowControlInfo) ((Entry) it.next()).getValue()).isExpired()) {
                        it.remove();
                    }
                }
            }
        }
    }
}
