package com.umeng.commonsdk.statistics.noise;

import android.content.Context;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.statistics.idtracking.Envelope;
import com.umeng.commonsdk.statistics.idtracking.ImprintHandler.a;
import com.umeng.commonsdk.statistics.internal.d;
import com.xiaomi.mipush.sdk.MiPushClient;

public class ABTest implements d {
    private static ABTest instance = null;
    private Context context = null;
    private boolean isInTest = false;
    private int mGroup = -1;
    private int mInterval = -1;
    private String mPoli = null;
    private int mPolicy = -1;
    private float mProb07 = 0.0f;
    private float mProb13 = 0.0f;

    public static synchronized ABTest getService(Context context) {
        ABTest aBTest;
        synchronized (ABTest.class) {
            if (instance == null) {
                instance = new ABTest(context, UMEnvelopeBuild.imprintProperty(context, "client_test", null), Integer.valueOf(UMEnvelopeBuild.imprintProperty(context, "test_report_interval", PushConstants.PUSH_TYPE_NOTIFY)).intValue());
            }
            aBTest = instance;
        }
        return aBTest;
    }

    private ABTest(Context context, String str, int i) {
        this.context = context;
        onExperimentChanged(str, i);
    }

    private float prob(String str, int i) {
        int i2 = i * 2;
        if (str == null) {
            return 0.0f;
        }
        return ((float) Integer.valueOf(str.substring(i2, i2 + 5), 16).intValue()) / 1048576.0f;
    }

    public void onExperimentChanged(String str, int i) {
        this.mInterval = i;
        String signature = Envelope.getSignature(this.context);
        if (TextUtils.isEmpty(signature) || TextUtils.isEmpty(str)) {
            this.isInTest = false;
            return;
        }
        try {
            this.mProb13 = prob(signature, 12);
            this.mProb07 = prob(signature, 6);
            if (str.startsWith("SIG7")) {
                parseSig7(str);
            } else if (str.startsWith("FIXED")) {
                parseFIXED(str);
            }
        } catch (Throwable e) {
            this.isInTest = false;
            MLog.e("v:" + str, e);
        }
    }

    public static boolean validate(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String[] split = str.split("\\|");
        if (split.length != 6) {
            return false;
        }
        if (split[0].startsWith("SIG7") && split[1].split(MiPushClient.ACCEPT_TIME_SEPARATOR).length == split[5].split(MiPushClient.ACCEPT_TIME_SEPARATOR).length) {
            return true;
        }
        if (!split[0].startsWith("FIXED")) {
            return false;
        }
        int length = split[5].split(MiPushClient.ACCEPT_TIME_SEPARATOR).length;
        int parseInt = Integer.parseInt(split[1]);
        if (length < parseInt || parseInt < 1) {
            return false;
        }
        return true;
    }

    private void parseSig7(String str) {
        if (str != null) {
            float floatValue;
            String[] split = str.split("\\|");
            if (split[2].equals("SIG13")) {
                floatValue = Float.valueOf(split[3]).floatValue();
            } else {
                floatValue = 0.0f;
            }
            if (this.mProb13 > floatValue) {
                this.isInTest = false;
                return;
            }
            String[] split2;
            float[] fArr = null;
            if (split[0].equals("SIG7")) {
                split2 = split[1].split(MiPushClient.ACCEPT_TIME_SEPARATOR);
                float[] fArr2 = new float[split2.length];
                for (int i = 0; i < split2.length; i++) {
                    fArr2[i] = Float.valueOf(split2[i]).floatValue();
                }
                fArr = fArr2;
            }
            int[] iArr = null;
            if (split[4].equals("RPT")) {
                this.mPoli = "RPT";
                split2 = split[5].split(MiPushClient.ACCEPT_TIME_SEPARATOR);
                int[] iArr2 = new int[split2.length];
                for (int i2 = 0; i2 < split2.length; i2++) {
                    iArr2[i2] = Integer.valueOf(split2[i2]).intValue();
                }
                iArr = iArr2;
            } else if (split[4].equals("DOM")) {
                this.isInTest = true;
                this.mPoli = "DOM";
                try {
                    split2 = split[5].split(MiPushClient.ACCEPT_TIME_SEPARATOR);
                    iArr = new int[split2.length];
                    for (int i3 = 0; i3 < split2.length; i3++) {
                        iArr[i3] = Integer.valueOf(split2[i3]).intValue();
                    }
                } catch (Exception e) {
                }
            }
            float f = 0.0f;
            int i4 = 0;
            while (i4 < fArr.length) {
                f += fArr[i4];
                if (this.mProb07 < f) {
                    break;
                }
                i4++;
            }
            i4 = -1;
            if (i4 != -1) {
                this.isInTest = true;
                this.mGroup = i4 + 1;
                if (iArr != null) {
                    this.mPolicy = iArr[i4];
                    return;
                }
                return;
            }
            this.isInTest = false;
        }
    }

    private void parseFIXED(String str) {
        if (str != null) {
            String[] split = str.split("\\|");
            float f = 0.0f;
            if (split[2].equals("SIG13")) {
                f = Float.valueOf(split[3]).floatValue();
            }
            if (this.mProb13 > f) {
                this.isInTest = false;
                return;
            }
            int intValue;
            if (split[0].equals("FIXED")) {
                intValue = Integer.valueOf(split[1]).intValue();
            } else {
                intValue = -1;
            }
            int[] iArr = null;
            String[] split2;
            if (split[4].equals("RPT")) {
                this.mPoli = "RPT";
                split2 = split[5].split(MiPushClient.ACCEPT_TIME_SEPARATOR);
                int[] iArr2 = new int[split2.length];
                for (int i = 0; i < split2.length; i++) {
                    iArr2[i] = Integer.valueOf(split2[i]).intValue();
                }
                iArr = iArr2;
            } else if (split[4].equals("DOM")) {
                this.mPoli = "DOM";
                this.isInTest = true;
                try {
                    split2 = split[5].split(MiPushClient.ACCEPT_TIME_SEPARATOR);
                    iArr = new int[split2.length];
                    for (int i2 = 0; i2 < split2.length; i2++) {
                        iArr[i2] = Integer.valueOf(split2[i2]).intValue();
                    }
                } catch (Exception e) {
                }
            }
            if (intValue != -1) {
                this.isInTest = true;
                this.mGroup = intValue;
                if (iArr != null) {
                    this.mPolicy = iArr[intValue - 1];
                    return;
                }
                return;
            }
            this.isInTest = false;
        }
    }

    public boolean isInTest() {
        return this.isInTest;
    }

    public int getTestPolicy() {
        return this.mPolicy;
    }

    public int getTestInterval() {
        return this.mInterval;
    }

    public int getGroup() {
        return this.mGroup;
    }

    public String getGroupInfo() {
        if (this.isInTest) {
            return String.valueOf(this.mGroup);
        }
        return "error";
    }

    public String getTestName() {
        return this.mPoli;
    }

    public void onImprintChanged(a aVar) {
        onExperimentChanged(aVar.a("client_test", null), Integer.valueOf(aVar.a("test_report_interval", PushConstants.PUSH_TYPE_NOTIFY)).intValue());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" p13:");
        stringBuilder.append(this.mProb13);
        stringBuilder.append(" p07:");
        stringBuilder.append(this.mProb07);
        stringBuilder.append(" policy:");
        stringBuilder.append(this.mPolicy);
        stringBuilder.append(" interval:");
        stringBuilder.append(this.mInterval);
        return stringBuilder.toString();
    }
}
