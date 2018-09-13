package anet.channel.strategy;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.dispatch.DispatchEvent;
import anet.channel.strategy.dispatch.HttpDispatcher;
import anet.channel.strategy.dispatch.HttpDispatcher.IDispatchEventListener;
import anet.channel.strategy.dispatch.a;
import anet.channel.util.ALog;
import anet.channel.util.StringUtils;
import anet.channel.util.Utils;
import anet.channel.util.c;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import org.json.JSONObject;

/* compiled from: Taobao */
class g implements IStrategyInstance, IDispatchEventListener {
    protected StrategyInfoHolder a = null;
    private boolean b = false;
    private long c = 0;
    private CopyOnWriteArraySet<IStrategyListener> d = new CopyOnWriteArraySet();

    g() {
    }

    public synchronized void initialize(Context context) {
        if (!(this.b || context == null)) {
            try {
                ALog.i("awcn.StrategyCenter", "StrategyCenter initialize started.", null, new Object[0]);
                a.a(context);
                l.a(context);
                NetworkStatusHelper.a(context);
                HttpDispatcher.getInstance().addListener(this);
                this.a = StrategyInfoHolder.a();
                this.b = true;
                ALog.i("awcn.StrategyCenter", "StrategyCenter initialize finished.", null, new Object[0]);
            } catch (Throwable e) {
                ALog.e("awcn.StrategyCenter", "StrategyCenter initialize failed.", null, e, new Object[0]);
            }
        }
        return;
    }

    public synchronized void switchEnv() {
        if (this.a == null) {
            this.a.b();
            this.a = StrategyInfoHolder.a();
        }
        l.a();
        HttpDispatcher.getInstance().switchENV();
    }

    @Deprecated
    public String getSchemeByHost(String str) {
        return getSchemeByHost(str, null);
    }

    public String getSchemeByHost(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (a()) {
            return str2;
        }
        String a = this.a.e.a(str);
        if (a == null && !TextUtils.isEmpty(str2)) {
            a = str2;
        }
        if (a == null) {
            a = a.a.a(str);
            if (a == null) {
                a = "http";
            }
        }
        ALog.d("awcn.StrategyCenter", "getSchemeByHost", null, "host", str, "scheme", a);
        return a;
    }

    public String getCNameByHost(String str) {
        if (a() || TextUtils.isEmpty(str)) {
            return null;
        }
        return this.a.d().getCnameByHost(str);
    }

    public String getFormalizeUrl(String str) {
        Throwable e;
        c a = c.a(str);
        if (a == null) {
            ALog.e("awcn.StrategyCenter", "url is invalid.", null, "URL", str, "stack", Utils.getStackMsg(new Exception("getFormalizeUrl")));
            return null;
        }
        String str2;
        try {
            String schemeByHost = getSchemeByHost(a.b(), a.a());
            if (schemeByHost.equalsIgnoreCase(a.a())) {
                str2 = str;
            } else {
                str2 = StringUtils.concatString(schemeByHost, ":", str.substring(str.indexOf("//")));
            }
            try {
                if (!ALog.isPrintLog(1)) {
                    return str2;
                }
                ALog.d("awcn.StrategyCenter", "", null, "raw", StringUtils.simplifyString(str, 128), "ret", StringUtils.simplifyString(str2, 128));
                return str2;
            } catch (Exception e2) {
                e = e2;
                ALog.e("awcn.StrategyCenter", "getFormalizeUrl failed", null, e, "raw", str);
                return str2;
            }
        } catch (Throwable e3) {
            e = e3;
            str2 = str;
            ALog.e("awcn.StrategyCenter", "getFormalizeUrl failed", null, e, "raw", str);
            return str2;
        }
    }

    @Deprecated
    public String getFormalizeUrl(String str, String str2) {
        return getFormalizeUrl(str);
    }

    public List<IConnStrategy> getConnStrategyListByHost(String str) {
        if (TextUtils.isEmpty(str) || a()) {
            return Collections.EMPTY_LIST;
        }
        CharSequence cnameByHost = this.a.d().getCnameByHost(str);
        if (!TextUtils.isEmpty(cnameByHost)) {
            str = cnameByHost;
        }
        List<IConnStrategy> queryByHost = this.a.d().queryByHost(str);
        if (queryByHost.isEmpty()) {
            queryByHost = this.a.f.a(str);
        }
        if (!ALog.isPrintLog(1)) {
            return queryByHost;
        }
        ALog.d("getConnStrategyListByHost", null, "host", str, "result", queryByHost);
        return queryByHost;
    }

    public void forceRefreshStrategy(String str) {
        if (!a() && !TextUtils.isEmpty(str)) {
            ALog.i("awcn.StrategyCenter", "force refresh strategy", null, "host", str);
            this.a.d().a(str, true);
        }
    }

    public void registerListener(IStrategyListener iStrategyListener) {
        if (iStrategyListener != null) {
            this.d.add(iStrategyListener);
        }
    }

    public void unregisterListener(IStrategyListener iStrategyListener) {
        this.d.remove(iStrategyListener);
    }

    public String getUnitPrefix(String str, String str2) {
        if (a()) {
            return null;
        }
        return this.a.d.a(str, str2);
    }

    public void setUnitPrefix(String str, String str2, String str3) {
        if (!a()) {
            this.a.d.a(str, str2, str3);
        }
    }

    public String getClientIp() {
        if (a()) {
            return "";
        }
        return this.a.d().b;
    }

    public void notifyConnEvent(String str, IConnStrategy iConnStrategy, ConnEvent connEvent) {
        if (!a() && iConnStrategy != null && (iConnStrategy instanceof IPConnStrategy)) {
            if (((IPConnStrategy) iConnStrategy).a == 2) {
                this.a.f.a(str, iConnStrategy, connEvent);
            } else {
                this.a.d().a(str, iConnStrategy, connEvent);
            }
        }
    }

    private boolean a() {
        if (this.a != null) {
            return false;
        }
        ALog.w("StrategyCenter not initialized", null, "isInitialized", Boolean.valueOf(this.b));
        return true;
    }

    public void onEvent(DispatchEvent dispatchEvent) {
        if (dispatchEvent.eventType == 1 && this.a != null) {
            ALog.d("awcn.StrategyCenter", "receive DNS event", null, new Object[0]);
            k.c a = k.a((JSONObject) dispatchEvent.extraObject);
            if (a != null) {
                this.a.a(a);
                saveData();
                Iterator it = this.d.iterator();
                while (it.hasNext()) {
                    ((IStrategyListener) it.next()).onStrategyUpdated(a);
                }
            }
        }
    }

    public synchronized void saveData() {
        ALog.i("awcn.StrategyCenter", "saveData", null, new Object[0]);
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.c > 60000) {
            this.c = currentTimeMillis;
            anet.channel.strategy.utils.a.a(new h(this), 500);
        }
    }
}
