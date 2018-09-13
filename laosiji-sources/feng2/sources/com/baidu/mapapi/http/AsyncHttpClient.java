package com.baidu.mapapi.http;

import android.os.Build.VERSION;
import anetwork.channel.util.RequestConstant;
import com.baidu.lbsapi.auth.LBSAuthManager;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.baidu.mapapi.http.HttpClient.ProtoResultCallback;
import com.baidu.platform.comapi.util.PermissionCheck;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncHttpClient {
    private int a = m_AppUI.MSG_APP_SAVESCREEN;
    private int b = m_AppUI.MSG_APP_SAVESCREEN;
    private ExecutorService c = Executors.newCachedThreadPool();

    private static abstract class a implements Runnable {
        private a() {
        }

        /* synthetic */ a(a aVar) {
            this();
        }

        public abstract void a();

        public void run() {
            a();
        }
    }

    static {
        if (VERSION.SDK_INT <= 8) {
            System.setProperty("http.keepAlive", RequestConstant.FALSE);
        }
    }

    public void get(String str, ProtoResultCallback protoResultCallback) {
        if (str == null) {
            throw new IllegalArgumentException("URI cannot be null");
        }
        this.c.submit(new a(this, protoResultCallback, str));
    }

    protected boolean isAuthorized() {
        int permissionCheck = PermissionCheck.permissionCheck();
        return permissionCheck == 0 || permissionCheck == LBSAuthManager.CODE_AUTHENTICATING || permissionCheck == LBSAuthManager.CODE_UNAUTHENTICATE;
    }
}
