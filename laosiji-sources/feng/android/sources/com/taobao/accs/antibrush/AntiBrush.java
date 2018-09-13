package com.taobao.accs.antibrush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.feng.library.okhttp.utils.OkHttpUtils;
import com.stub.StubApp;
import com.taobao.accs.base.TaoBaseService.ExtHeaderType;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.data.d;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import com.umeng.message.util.HttpRequest;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* compiled from: Taobao */
public class AntiBrush {
    private static final String ANTI_ATTACK_ACTION = "mtopsdk.mtop.antiattack.checkcode.validate.activity_action";
    private static final String ANTI_ATTACK_RESULT_ACTION = "mtopsdk.extra.antiattack.result.notify.action";
    public static final int STATUS_BRUSH = 419;
    private static final String TAG = "AntiBrush";
    private static String mHost;
    private static volatile boolean mIsInCheckCodeActivity = false;
    private static ScheduledFuture<?> mTimeoutTask;
    private BroadcastReceiver mAntiAttackReceiver = null;
    private Context mContext;

    /* compiled from: Taobao */
    public static class AntiReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            boolean z = false;
            try {
                String stringExtra = intent.getStringExtra("Result");
                ALog.e(AntiBrush.TAG, "anti onReceive result: " + stringExtra, new Object[0]);
                if (stringExtra.equalsIgnoreCase("success")) {
                    z = true;
                }
                AntiBrush.onResult(GlobalClientInfo.getContext(), z);
            } catch (Throwable e) {
                ALog.e(AntiBrush.TAG, "anti onReceive", e, new Object[0]);
                AntiBrush.onResult(GlobalClientInfo.getContext(), false);
            } catch (Throwable e2) {
                AntiBrush.onResult(GlobalClientInfo.getContext(), false);
                throw e2;
            }
        }
    }

    public boolean checkAntiBrush(URL url, Map<Integer, String> map) {
        boolean z;
        Throwable th;
        if (map != null) {
            try {
                if (UtilityImpl.isForeground(this.mContext)) {
                    int i;
                    String str = (String) map.get(Integer.valueOf(ExtHeaderType.TYPE_STATUS.ordinal()));
                    if (TextUtils.isEmpty(str)) {
                        i = 0;
                    } else {
                        i = Integer.valueOf(str).intValue();
                    }
                    if (i == STATUS_BRUSH) {
                        str = (String) map.get(Integer.valueOf(ExtHeaderType.TYPE_LOCATION.ordinal()));
                        if (!TextUtils.isEmpty(str)) {
                            ALog.e(TAG, "start anti bursh location:" + str, new Object[0]);
                            handleantiBrush(str);
                            if (mTimeoutTask != null) {
                                mTimeoutTask.cancel(true);
                                mTimeoutTask = null;
                            }
                            mTimeoutTask = ThreadPoolExecutorFactory.schedule(new a(this), OkHttpUtils.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
                            mHost = url == null ? null : url.getHost();
                            z = true;
                            if (!TextUtils.isEmpty(GlobalClientInfo.c) && TextUtils.isEmpty(b.a(mHost))) {
                                ALog.e(TAG, "cookie invalid, clear", new Object[0]);
                                UtilityImpl.clearCookie(this.mContext);
                            }
                            return z;
                        }
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                z = false;
                ALog.e(TAG, "checkAntiBrush error", th, new Object[0]);
                return z;
            }
        }
        z = false;
        try {
            ALog.e(TAG, "cookie invalid, clear", new Object[0]);
            UtilityImpl.clearCookie(this.mContext);
        } catch (Throwable th3) {
            th = th3;
            ALog.e(TAG, "checkAntiBrush error", th, new Object[0]);
            return z;
        }
        return z;
    }

    public AntiBrush(Context context) {
        this.mContext = StubApp.getOrigApplicationContext(context.getApplicationContext());
    }

    public static void onResult(Context context, boolean z) {
        mIsInCheckCodeActivity = false;
        Intent intent = new Intent(Constants.ACTION_RECEIVE);
        intent.setPackage(context.getPackageName());
        intent.putExtra("command", 104);
        intent.putExtra(Constants.KEY_ANTI_BRUSH_RET, z);
        d.a(context, intent);
        if (mTimeoutTask != null) {
            mTimeoutTask.cancel(true);
            mTimeoutTask = null;
        }
        if (mHost != null) {
            UtilityImpl.storeCookie(context, b.a(mHost));
        }
    }

    private void handleantiBrush(String str) {
        if (mIsInCheckCodeActivity) {
            ALog.e(TAG, "handleantiBrush return", "mIsInCheckCodeActivity", Boolean.valueOf(mIsInCheckCodeActivity));
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setAction(ANTI_ATTACK_ACTION);
            intent.setPackage(this.mContext.getPackageName());
            intent.setFlags(CommonNetImpl.FLAG_AUTH);
            intent.putExtra(HttpRequest.HEADER_LOCATION, str);
            ALog.e(TAG, "handleAntiBrush:", new Object[0]);
            this.mContext.startActivity(intent);
            mIsInCheckCodeActivity = true;
            if (this.mAntiAttackReceiver == null) {
                this.mAntiAttackReceiver = new AntiReceiver();
            }
            this.mContext.registerReceiver(this.mAntiAttackReceiver, new IntentFilter(ANTI_ATTACK_RESULT_ACTION));
        } catch (Throwable th) {
            ALog.e(TAG, "handleantiBrush", th, new Object[0]);
        }
    }
}
