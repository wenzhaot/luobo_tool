package com.umeng.commonsdk.internal.utils;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.feng.car.utils.HttpConstant;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.handler.impl.AbstractMessageHandler;
import com.stub.StubApp;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.statistics.common.e;

/* compiled from: BaseStationUtils */
public class b {
    private static final String b = "BaseStationUtils";
    private static boolean c = false;
    private static Context d = null;
    PhoneStateListener a;
    private TelephonyManager e;

    /* compiled from: BaseStationUtils */
    private static class a {
        private static final b a = new b(b.d, null);

        private a() {
        }
    }

    /* synthetic */ b(Context context, AnonymousClass1 anonymousClass1) {
        this(context);
    }

    private b(Context context) {
        this.a = new PhoneStateListener() {
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                super.onSignalStrengthsChanged(signalStrength);
                e.c(b.b, "base station onSignalStrengthsChanged");
                try {
                    b.this.e = (TelephonyManager) b.d.getSystemService(HttpConstant.PHONE);
                    String[] split = signalStrength.toString().split(" ");
                    CharSequence charSequence = null;
                    if (b.this.e.getNetworkType() == 13) {
                        charSequence = "" + Integer.parseInt(split[9]);
                    } else if (b.this.e.getNetworkType() == 8 || b.this.e.getNetworkType() == 10 || b.this.e.getNetworkType() == 9 || b.this.e.getNetworkType() == 3) {
                        Object b = b.this.e();
                        if (!TextUtils.isEmpty(b) && b.equals("中国移动")) {
                            charSequence = PushConstants.PUSH_TYPE_NOTIFY;
                        } else if (!TextUtils.isEmpty(b) && b.equals("中国联通")) {
                            charSequence = signalStrength.getCdmaDbm() + "";
                        } else if (!TextUtils.isEmpty(b) && b.equals("中国电信")) {
                            charSequence = signalStrength.getEvdoDbm() + "";
                        }
                    } else {
                        charSequence = ((signalStrength.getGsmSignalStrength() * 2) - 113) + "";
                    }
                    e.c(b.b, "stationStrength is " + charSequence);
                    if (!TextUtils.isEmpty(charSequence)) {
                        try {
                            UMWorkDispatch.sendEvent(b.d, com.umeng.commonsdk.internal.a.h, com.umeng.commonsdk.internal.b.a(b.d).a(), charSequence);
                        } catch (Throwable th) {
                        }
                    }
                    b.this.c();
                } catch (Exception e) {
                }
            }
        };
        if (context != null) {
            try {
                this.e = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
            } catch (Throwable th) {
            }
        }
    }

    public static b a(Context context) {
        if (d == null && context != null) {
            d = StubApp.getOrigApplicationContext(context.getApplicationContext());
        }
        return a.a;
    }

    public synchronized boolean a() {
        return c;
    }

    private String e() {
        try {
            String simOperator = ((TelephonyManager) d.getSystemService(HttpConstant.PHONE)).getSimOperator();
            if (!TextUtils.isEmpty(simOperator)) {
                if (simOperator.equals("46000") || simOperator.equals("46002")) {
                    return "中国移动";
                }
                if (simOperator.equals("46001")) {
                    return "中国联通";
                }
                if (simOperator.equals("46003")) {
                    return "中国电信";
                }
            }
            return null;
        } catch (Throwable th) {
            return null;
        }
    }

    public synchronized void b() {
        e.c(b, "base station registerListener");
        try {
            if (this.e != null) {
                this.e.listen(this.a, AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS);
            }
            c = true;
        } catch (Throwable th) {
        }
    }

    public synchronized void c() {
        e.c(b, "base station unRegisterListener");
        try {
            if (this.e != null) {
                this.e.listen(this.a, 0);
            }
            c = false;
        } catch (Throwable th) {
        }
    }
}
