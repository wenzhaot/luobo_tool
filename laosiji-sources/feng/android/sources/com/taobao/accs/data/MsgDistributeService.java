package com.taobao.accs.data;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import com.stub.StubApp;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.ACCSManager.AccsRequest;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.tencent.tauth.AuthActivity;

/* compiled from: Taobao */
public class MsgDistributeService extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        try {
            ALog.i("MsgDistributeService", "onStartCommand", AuthActivity.ACTION_KEY, intent.getAction());
            if (TextUtils.isEmpty(intent.getAction()) || !TextUtils.equals(intent.getAction(), Constants.ACTION_SEND)) {
                d.a(StubApp.getOrigApplicationContext(getApplicationContext()), intent);
                return 2;
            }
            AccsRequest accsRequest = (AccsRequest) intent.getSerializableExtra(Constants.KEY_SEND_REQDATA);
            String stringExtra = intent.getStringExtra(Constants.KEY_PACKAGE_NAME);
            String stringExtra2 = intent.getStringExtra(Constants.KEY_APP_KEY);
            String stringExtra3 = intent.getStringExtra(Constants.KEY_CONFIG_TAG);
            if (TextUtils.isEmpty(stringExtra3)) {
                stringExtra3 = stringExtra2;
            }
            ACCSManager.getAccsInstance(StubApp.getOrigApplicationContext(getApplicationContext()), stringExtra2, stringExtra3).sendRequest(StubApp.getOrigApplicationContext(getApplicationContext()), accsRequest, stringExtra, false);
            return 2;
        } catch (Throwable th) {
            ALog.e("MsgDistributeService", "onStartCommand", th, new Object[0]);
        }
    }
}
