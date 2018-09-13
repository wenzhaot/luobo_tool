package com.taobao.accs.ut.monitor;

import anet.channel.statist.Dimension;
import anet.channel.statist.Measure;
import anet.channel.statist.Monitor;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.BaseMonitor;
import com.umeng.message.entity.UInAppMessage;

@Monitor(module = "accs", monitorPoint = "netperformance")
/* compiled from: Taobao */
public class NetPerformanceMonitor extends BaseMonitor {
    private long a;
    @Dimension
    public int accs_sdk_version;
    @Dimension
    public int accs_type;
    private long b;
    private long c;
    private long d;
    public String data_id;
    public String device_id;
    @Dimension
    public int error_code;
    @Dimension
    public String fail_reasons;
    @Dimension
    public String host;
    @Measure(constantValue = 0.0d, max = 60000.0d, min = 0.0d)
    public long in_queue_time;
    @Measure(constantValue = 0.0d, max = 60000.0d, min = 0.0d)
    public long receive_to_call_back_time;
    @Dimension
    public String ret;
    @Dimension
    public int retry_times;
    @Measure(constantValue = 0.0d, max = 60000.0d, min = 0.0d)
    public long send_to_receive_time;
    @Dimension
    public String service_id = UInAppMessage.NONE;
    @Measure(constantValue = 0.0d, max = 60000.0d, min = 0.0d)
    public long start_to_enter_queue_time;
    public long take_date;
    @Measure(constantValue = 0.0d, max = 60000.0d, min = 0.0d)
    public long talk_to_send_time;
    public long to_tnet_date;
    @Measure(constantValue = 0.0d, max = 60000.0d, min = 0.0d)
    public long total_time;

    public void setDeviceId(String str) {
        this.device_id = str;
    }

    public void setServiceId(String str) {
        this.service_id = str;
    }

    public void setDataId(String str) {
        this.data_id = str;
    }

    public void onSend() {
        this.a = System.currentTimeMillis();
    }

    public void onEnterQueueData() {
        this.b = System.currentTimeMillis();
    }

    public void onTakeFromQueue() {
        this.take_date = System.currentTimeMillis();
    }

    public void onSendData() {
        this.to_tnet_date = System.currentTimeMillis();
    }

    public void setRet(boolean z) {
        this.ret = z ? "y" : "n";
    }

    public void setFailReason(String str) {
        this.fail_reasons = str;
    }

    public void setFailReason(int i) {
        this.error_code = i;
        switch (i) {
            case -4:
                setFailReason("msg too large");
                return;
            case -3:
                setFailReason("service not available");
                return;
            case -2:
                setFailReason("param error");
                return;
            case -1:
                setFailReason("network fail");
                return;
            case 200:
                return;
            case 300:
                setFailReason("app not bind");
                return;
            default:
                setFailReason(String.valueOf(i));
                return;
        }
    }

    public void setHost(String str) {
        this.host = str;
    }

    public void onRecAck() {
        this.c = System.currentTimeMillis();
    }

    public void onToBizDate() {
        this.d = System.currentTimeMillis();
    }

    public void setConnType(int i) {
        this.accs_type = i;
    }

    public boolean beforeCommit() {
        this.accs_sdk_version = Constants.SDK_VERSION_CODE;
        this.total_time = a(this.a, this.d);
        this.start_to_enter_queue_time = a(this.a, this.b);
        this.in_queue_time = a(this.b, this.take_date);
        this.talk_to_send_time = a(this.take_date, this.to_tnet_date);
        this.send_to_receive_time = a(this.to_tnet_date, this.c);
        this.receive_to_call_back_time = a(this.c, this.d);
        return super.beforeCommit();
    }

    private long a(long j, long j2) {
        if (j <= 0 || j2 <= 0) {
            return 0;
        }
        return j2 - j;
    }
}
