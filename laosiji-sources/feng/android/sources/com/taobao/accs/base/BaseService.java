package com.taobao.accs.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.stub.StubApp;
import com.taobao.accs.common.Constants;
import com.taobao.accs.d.a;
import com.taobao.accs.utl.ALog;

/* compiled from: Taobao */
public class BaseService extends Service {
    private static final String TAG = "BaseService";
    IBaseService mBaseService = null;

    public void onCreate() {
        super.onCreate();
        try {
            this.mBaseService = (IBaseService) a.a().a(StubApp.getOrigApplicationContext(getApplicationContext())).loadClass(Constants.SERVICE_IMPL_NAME).getDeclaredConstructor(new Class[]{Service.class}).newInstance(new Object[]{this});
            if (this.mBaseService == null) {
                try {
                    this.mBaseService = (IBaseService) Class.forName(Constants.SERVICE_IMPL_NAME).getDeclaredConstructor(new Class[]{Service.class}).newInstance(new Object[]{this});
                } catch (Throwable th) {
                }
            }
        } catch (Throwable th2) {
        }
        ALog.d(TAG, "onCreate", new Object[0]);
        if (this.mBaseService != null) {
            this.mBaseService.onCreate();
        } else {
            ALog.e(TAG, "cann't start ServiceImpl!", new Object[0]);
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (this.mBaseService != null) {
            return this.mBaseService.onStartCommand(intent, i, i2);
        }
        ALog.e(TAG, "onStartCommand mBaseService null", new Object[0]);
        return 2;
    }

    public IBinder onBind(Intent intent) {
        ALog.d(TAG, "onBind:" + intent, new Object[0]);
        return this.mBaseService.onBind(intent);
    }

    public boolean onUnbind(Intent intent) {
        return this.mBaseService.onUnbind(intent);
    }

    public void onDestroy() {
        if (this.mBaseService != null) {
            this.mBaseService.onDestroy();
            this.mBaseService = null;
        }
        super.onDestroy();
    }
}
