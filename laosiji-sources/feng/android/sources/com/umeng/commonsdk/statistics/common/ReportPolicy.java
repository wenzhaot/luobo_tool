package com.umeng.commonsdk.statistics.common;

import android.content.Context;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.framework.UMModuleRegister;
import com.umeng.commonsdk.statistics.internal.StatTracer;
import com.umeng.commonsdk.statistics.noise.Defcon;

public class ReportPolicy {
    public static final int BATCH_AT_LAUNCH = 1;
    static final int BATCH_AT_TERMINATE = 2;
    public static final int BATCH_BY_INTERVAL = 6;
    public static final int DAILY = 4;
    static final int IMMEDIATE = 3;
    public static final int REALTIME = 0;
    public static final int SMART_POLICY = 8;
    public static final int WIFIONLY = 5;

    public static class ReportStrategy {
        public boolean shouldSendMessage(boolean z) {
            return true;
        }

        public boolean isValid() {
            return true;
        }
    }

    public static class DebugPolicy extends ReportStrategy {
        private final long ReportInterval = 15000;
        private StatTracer mTracer;

        public DebugPolicy(StatTracer statTracer) {
            this.mTracer = statTracer;
        }

        public boolean shouldSendMessage(boolean z) {
            if (System.currentTimeMillis() - UMEnvelopeBuild.lastSuccessfulBuildTime(UMModuleRegister.getAppContext()) >= 15000) {
                return true;
            }
            return false;
        }
    }

    public static class DefconPolicy extends ReportStrategy {
        private Defcon defcon;
        private StatTracer tracer;

        public DefconPolicy(StatTracer statTracer, Defcon defcon) {
            this.tracer = statTracer;
            this.defcon = defcon;
        }

        public boolean shouldSendMessage(boolean z) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - UMEnvelopeBuild.lastSuccessfulBuildTime(UMModuleRegister.getAppContext()) >= this.defcon.getReqInterval()) {
                return true;
            }
            return false;
        }

        public boolean isValid() {
            return this.defcon.isOpen();
        }
    }

    public static class LatentPolicy extends ReportStrategy {
        private long latency;
        private long start = 0;

        public LatentPolicy(int i) {
            this.latency = (long) i;
            this.start = System.currentTimeMillis();
        }

        public boolean shouldSendMessage(boolean z) {
            if (System.currentTimeMillis() - this.start >= this.latency) {
                return true;
            }
            return false;
        }

        public boolean isValid() {
            return System.currentTimeMillis() - this.start < this.latency;
        }
    }

    public static class ReportAtLaunch extends ReportStrategy {
        public boolean shouldSendMessage(boolean z) {
            return z;
        }
    }

    public static class ReportByInterval extends ReportStrategy {
        private static long MAX_REPORT_INTERVAL = 86400000;
        private static long MIN_REPORT_INTERVAL = 90000;
        private long mReportInterval;
        private StatTracer mTracer;

        public ReportByInterval(StatTracer statTracer, long j) {
            this.mTracer = statTracer;
            setReportInterval(j);
        }

        public boolean shouldSendMessage(boolean z) {
            if (System.currentTimeMillis() - UMEnvelopeBuild.lastSuccessfulBuildTime(UMModuleRegister.getAppContext()) >= this.mReportInterval) {
                return true;
            }
            return false;
        }

        public void setReportInterval(long j) {
            if (j < MIN_REPORT_INTERVAL || j > MAX_REPORT_INTERVAL) {
                this.mReportInterval = MIN_REPORT_INTERVAL;
            } else {
                this.mReportInterval = j;
            }
        }

        public long getReportInterval() {
            return this.mReportInterval;
        }

        public static boolean isValidValue(int i) {
            if (((long) i) < MIN_REPORT_INTERVAL) {
                return false;
            }
            return true;
        }
    }

    public static class ReportDaily extends ReportStrategy {
        private long HOURS_DAY = 86400000;
        private StatTracer mTracer;

        public ReportDaily(StatTracer statTracer) {
            this.mTracer = statTracer;
        }

        public boolean shouldSendMessage(boolean z) {
            if (System.currentTimeMillis() - UMEnvelopeBuild.lastSuccessfulBuildTime(UMModuleRegister.getAppContext()) >= this.HOURS_DAY) {
                return true;
            }
            return false;
        }
    }

    public static class ReportRealtime extends ReportStrategy {
        public boolean shouldSendMessage(boolean z) {
            return true;
        }
    }

    public static class ReportWifiOnly extends ReportStrategy {
        private Context mContext = null;

        public ReportWifiOnly(Context context) {
            this.mContext = context;
        }

        public boolean shouldSendMessage(boolean z) {
            return DeviceConfig.isWiFiAvailable(this.mContext);
        }
    }

    public static class SmartPolicy extends ReportStrategy {
        private final long ReportInterval = 10800000;
        private StatTracer mTracer;

        public SmartPolicy(StatTracer statTracer) {
            this.mTracer = statTracer;
        }

        public boolean shouldSendMessage(boolean z) {
            if (System.currentTimeMillis() - UMEnvelopeBuild.lastSuccessfulBuildTime(UMModuleRegister.getAppContext()) >= 10800000) {
                return true;
            }
            return false;
        }
    }

    public static boolean isValid(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
                return true;
            default:
                return false;
        }
    }
}
