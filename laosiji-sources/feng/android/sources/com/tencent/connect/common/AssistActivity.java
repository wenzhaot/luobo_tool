package com.tencent.connect.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.stub.StubApp;
import com.umeng.socialize.utils.SLog;
import org.json.JSONObject;

public class AssistActivity extends Activity {
    public static final String EXTRA_INTENT = "openSDK_LOG.AssistActivity.ExtraIntent";
    protected static final int FINISH_BY_TIMEOUT = 0;
    private static final String RESTART_FLAG = "RESTART_FLAG";
    private static final String RESUME_FLAG = "RESUME_FLAG";
    private static final String TAG = "openSDK_LOG.AssistActivity";
    protected Handler handler = new Handler() {
        public void handleMessage(Message var1) {
            switch (var1.what) {
                case 0:
                    if (!AssistActivity.this.isFinishing()) {
                        AssistActivity.this.finish();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    private boolean isRestart = false;
    private String mAppId;
    protected boolean mOnResumeIsInited = false;

    static {
        StubApp.interface11(7157);
    }

    protected native void onCreate(Bundle bundle);

    public static Intent getAssistActivityIntent(Context var0) {
        return new Intent(var0, AssistActivity.class);
    }

    protected void onStart() {
        super.onStart();
    }

    protected void onResume() {
        super.onResume();
        Intent var1 = getIntent();
        if (!var1.getBooleanExtra("is_login", false)) {
            if (!(var1.getBooleanExtra("is_qq_mobile_share", false) || !this.isRestart || isFinishing())) {
                finish();
            }
            if (this.mOnResumeIsInited) {
                this.handler.sendMessage(this.handler.obtainMessage(0));
                return;
            }
            this.mOnResumeIsInited = true;
        }
    }

    protected void onPause() {
        this.handler.removeMessages(0);
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onNewIntent(Intent var1) {
        super.onNewIntent(var1);
        var1.putExtra("key_action", "action_share");
        setResult(-1, var1);
        if (!isFinishing()) {
            finish();
        }
    }

    protected void onSaveInstanceState(Bundle var1) {
        var1.putBoolean(RESTART_FLAG, true);
        var1.putBoolean(RESUME_FLAG, this.mOnResumeIsInited);
        super.onSaveInstanceState(var1);
    }

    protected void onActivityResult(int var1, int var2, Intent var3) {
        super.onActivityResult(var1, var2, var3);
        if (var1 != 0) {
            if (var3 != null) {
                var3.putExtra("key_action", "action_login");
            }
            setResultData(var1, var3);
            finish();
        }
    }

    public void setResultData(int var1, Intent var2) {
        if (var2 == null) {
            setResult(0);
            return;
        }
        try {
            String var3 = var2.getStringExtra("key_response");
            if (TextUtils.isEmpty(var3)) {
                setResult(-1, var2);
                return;
            }
            JSONObject var4 = new JSONObject(var3);
            String var5 = var4.optString("openid");
            String var6 = var4.optString("access_token");
            if (TextUtils.isEmpty(var5) || TextUtils.isEmpty(var6)) {
                setResult(0, var2);
            } else {
                setResult(-1, var2);
            }
        } catch (Exception var7) {
            SLog.error(var7);
        }
    }
}
