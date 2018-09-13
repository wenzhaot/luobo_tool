package com.feng.car.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Handler;
import com.feng.car.FengApplication;
import com.feng.car.event.NetWorkConnectEvent;
import org.greenrobot.eventbus.EventBus;

public class ConnectionChangeReceiver extends BroadcastReceiver {
    private boolean mAlready = false;
    public Context mContext;

    public void onReceive(Context context, Intent intent) {
        if (VERSION.SDK_INT < 21) {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService("connectivity");
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(1);
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(0);
            if (wifiNetworkInfo != null && dataNetworkInfo != null) {
                if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                    if (FengApplication.getInstance().getConnectState() != 1) {
                        FengApplication.getInstance().setConnectState(1);
                        FengApplication.getInstance().setIsWifiConnect(true);
                        EventBus.getDefault().post(new NetWorkConnectEvent(true));
                    }
                } else if (!wifiNetworkInfo.isConnected() || dataNetworkInfo.isConnected()) {
                    if (!(wifiNetworkInfo.isConnected() || !dataNetworkInfo.isConnected() || FengApplication.getInstance().getConnectState() == 2)) {
                        FengApplication.getInstance().setConnectState(2);
                        FengApplication.getInstance().setIsWifiConnect(false);
                        EventBus.getDefault().post(new NetWorkConnectEvent(false));
                    }
                } else if (FengApplication.getInstance().getConnectState() != 1) {
                    FengApplication.getInstance().setConnectState(1);
                    FengApplication.getInstance().setIsWifiConnect(true);
                    EventBus.getDefault().post(new NetWorkConnectEvent(true));
                }
            } else {
                return;
            }
        }
        NetworkInfo activeNetInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == 1 && activeNetInfo.isConnected()) {
            if (FengApplication.getInstance().getConnectState() != 1) {
                FengApplication.getInstance().setConnectState(1);
                FengApplication.getInstance().setIsWifiConnect(true);
                EventBus.getDefault().post(new NetWorkConnectEvent(true));
            }
        } else if (activeNetInfo != null && activeNetInfo.getType() == 0 && activeNetInfo.isConnected() && FengApplication.getInstance().getConnectState() != 2) {
            FengApplication.getInstance().setConnectState(2);
            FengApplication.getInstance().setIsWifiConnect(false);
            EventBus.getDefault().post(new NetWorkConnectEvent(false));
        }
        resetState();
    }

    private void resetState() {
        if (!this.mAlready) {
            this.mAlready = true;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    ConnectionChangeReceiver.this.mAlready = false;
                    FengApplication.getInstance().setConnectState(0);
                }
            }, 3000);
        }
    }
}
