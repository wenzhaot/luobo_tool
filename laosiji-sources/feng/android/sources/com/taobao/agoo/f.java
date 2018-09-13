package com.taobao.agoo;

import android.content.Context;
import android.text.TextUtils;
import com.taobao.accs.ACCSManager.AccsRequest;
import com.taobao.accs.IACCSManager;
import com.taobao.accs.IAgooAppReceiver;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.a.a.c;
import com.taobao.agoo.a.b;
import org.android.agoo.common.Config;

/* compiled from: Taobao */
final class f extends IAgooAppReceiver {
    final /* synthetic */ Context a;
    final /* synthetic */ IACCSManager b;
    final /* synthetic */ IRegister c;
    final /* synthetic */ String d;
    final /* synthetic */ String e;

    f(Context context, IACCSManager iACCSManager, IRegister iRegister, String str, String str2) {
        this.a = context;
        this.b = iACCSManager;
        this.c = iRegister;
        this.d = str;
        this.e = str2;
    }

    public void onBindApp(int i, String str) {
        try {
            ALog.i("TaobaoRegister", "onBindApp", Constants.KEY_ERROR_CODE, Integer.valueOf(i));
            if (i == 200) {
                if (TaobaoRegister.mRequestListener == null) {
                    TaobaoRegister.mRequestListener = new b(this.a);
                }
                this.b.registerDataListener(this.a, TaobaoConstants.SERVICE_ID_DEVICECMD, TaobaoRegister.mRequestListener);
                TaobaoRegister.mRequestListener;
                if (b.b.b(this.a.getPackageName())) {
                    ALog.i("TaobaoRegister", "agoo aready Registered return ", new Object[0]);
                    if (this.c != null) {
                        this.c.onSuccess(Config.g(this.a));
                        return;
                    }
                    return;
                }
                byte[] a = c.a(this.a, this.d, this.e);
                if (a != null) {
                    CharSequence sendRequest = this.b.sendRequest(this.a, new AccsRequest(null, TaobaoConstants.SERVICE_ID_DEVICECMD, a, null));
                    if (TextUtils.isEmpty(sendRequest)) {
                        if (this.c != null) {
                            this.c.onFailure(TaobaoConstants.REGISTER_ERROR, "accs channel disabled!");
                        }
                    } else if (this.c != null) {
                        TaobaoRegister.mRequestListener.a.put(sendRequest, this.c);
                    }
                } else if (this.c != null) {
                    this.c.onFailure(TaobaoConstants.REGISTER_ERROR, "req data null");
                }
            } else if (this.c != null) {
                this.c.onFailure(String.valueOf(i), "accs bindapp error!");
            }
        } catch (Throwable th) {
            ALog.e("TaobaoRegister", "register onBindApp", th, new Object[0]);
        }
    }

    public String getAppkey() {
        return this.d;
    }
}
