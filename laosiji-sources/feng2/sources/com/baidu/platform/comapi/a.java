package com.baidu.platform.comapi;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.UIMsg.l_ErrorNo;
import com.baidu.mapapi.VersionInfo;
import com.baidu.platform.comapi.util.PermissionCheck;
import com.baidu.platform.comapi.util.PermissionCheck.b;
import com.baidu.platform.comapi.util.PermissionCheck.c;
import com.baidu.platform.comapi.util.SysUpdateObservable;
import com.baidu.platform.comapi.util.e;

public class a implements c {
    private static final String a = a.class.getSimpleName();
    private static a f;
    private static int g = -100;
    private Context b;
    private Handler c;
    private d d;
    private int e;

    static {
        NativeLoader.getInstance().loadLibrary(VersionInfo.getKitName());
        com.baidu.platform.comjni.tools.a.b();
    }

    private a() {
    }

    public static a a() {
        if (f == null) {
            f = new a();
        }
        return f;
    }

    private void f() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        if (this.b != null && this.d != null) {
            this.b.registerReceiver(this.d, intentFilter);
        }
    }

    private void g() {
        if (this.d != null && this.b != null) {
            this.b.unregisterReceiver(this.d);
        }
    }

    public void a(Context context) {
        this.b = context;
    }

    public void a(Message message) {
        if (message.what == 2012) {
            Intent intent;
            if (message.arg1 == 0) {
                intent = new Intent(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
            } else {
                intent = new Intent(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
                intent.putExtra(SDKInitializer.SDK_BROADTCAST_INTENT_EXTRA_INFO_KEY_ERROR_CODE, message.arg1);
            }
            this.b.sendBroadcast(intent);
            return;
        }
        if (message.arg2 == 3) {
            this.b.sendBroadcast(new Intent(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR));
        }
        if (message.arg2 == 2 || message.arg2 == l_ErrorNo.NETWORK_ERROR_404 || message.arg2 == 5 || message.arg2 == 8) {
            this.b.sendBroadcast(new Intent(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR));
        }
    }

    public void a(b bVar) {
        if (bVar != null) {
            if (bVar.a == 0) {
                e.z = bVar.e;
                e.a(bVar.b, bVar.c);
            } else {
                Log.e("baidumapsdk", "Authentication Error\n" + bVar.toString());
            }
            if (this.c != null && bVar.a != g) {
                g = bVar.a;
                Message.obtain(this.c, 2012, bVar.a, bVar.a, null).sendToTarget();
            }
        }
    }

    public void b() {
        if (this.e == 0) {
            if (this.b == null) {
                throw new IllegalStateException("you have not supplyed the global app context info from SDKInitializer.initialize(Context) function.");
            }
            this.d = new d();
            f();
            SysUpdateObservable.getInstance().updateNetworkInfo(this.b);
        }
        this.e++;
    }

    public boolean c() {
        if (this.b == null) {
            throw new IllegalStateException("you have not supplyed the global app context info from SDKInitializer.initialize(Context) function.");
        }
        this.c = new b(this);
        e.b(this.b);
        e.f();
        PermissionCheck.init(this.b);
        PermissionCheck.setPermissionCheckResultListener(this);
        PermissionCheck.permissionCheck();
        return true;
    }

    public void d() {
        this.e--;
        if (this.e == 0) {
            g();
            e.a();
        }
    }

    public Context e() {
        if (this.b != null) {
            return this.b;
        }
        throw new IllegalStateException("you have not supplyed the global app context info from SDKInitializer.initialize(Context) function.");
    }
}
