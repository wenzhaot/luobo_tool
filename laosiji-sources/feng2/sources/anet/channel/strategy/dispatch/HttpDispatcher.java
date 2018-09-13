package anet.channel.strategy.dispatch;

import android.text.TextUtils;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.util.ALog;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: Taobao */
public class HttpDispatcher {
    private b executor;
    private Set<String> initHosts;
    private volatile boolean isEnable;
    private AtomicBoolean isInitHostsFilled;
    private CopyOnWriteArraySet<IDispatchEventListener> listeners;
    private Set<String> uniqueIdSet;

    /* compiled from: Taobao */
    public interface IDispatchEventListener {
        void onEvent(DispatchEvent dispatchEvent);
    }

    /* compiled from: Taobao */
    private static class Singleton {
        static HttpDispatcher instance = new HttpDispatcher();

        private Singleton() {
        }
    }

    public static HttpDispatcher getInstance() {
        return Singleton.instance;
    }

    private HttpDispatcher() {
        this.listeners = new CopyOnWriteArraySet();
        this.executor = new b();
        this.isEnable = true;
        this.uniqueIdSet = Collections.newSetFromMap(new ConcurrentHashMap());
        this.initHosts = new TreeSet();
        this.isInitHostsFilled = new AtomicBoolean();
        fillInitHosts();
    }

    public void sendAmdcRequest(Set<String> set, String str, int i) {
        if (this.isEnable && set != null && !set.isEmpty()) {
            if (ALog.isPrintLog(2)) {
                ALog.i("awcn.HttpDispatcher", "sendAmdcRequest", null, DispatchConstants.HOSTS, set.toString());
            }
            Map hashMap = new HashMap();
            hashMap.put(DispatchConstants.HOSTS, set);
            hashMap.put(DispatchConstants.PRE_IP, str);
            hashMap.put(DispatchConstants.CONFIG_VERSION, String.valueOf(i));
            this.executor.a(hashMap);
        }
    }

    public void addListener(IDispatchEventListener iDispatchEventListener) {
        this.listeners.add(iDispatchEventListener);
    }

    public void removeListener(IDispatchEventListener iDispatchEventListener) {
        this.listeners.remove(iDispatchEventListener);
    }

    void fireEvent(DispatchEvent dispatchEvent) {
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            try {
                ((IDispatchEventListener) it.next()).onEvent(dispatchEvent);
            } catch (Exception e) {
            }
        }
    }

    public void setEnable(boolean z) {
        this.isEnable = z;
    }

    public synchronized void addHosts(List<String> list) {
        if (list != null) {
            this.initHosts.addAll(list);
            this.uniqueIdSet.clear();
        }
    }

    public static void setInitHosts(List<String> list) {
        if (list != null) {
            DispatchConstants.initHostArray = (String[]) list.toArray(new String[0]);
        }
    }

    public synchronized Set<String> getInitHosts() {
        fillInitHosts();
        return new HashSet(this.initHosts);
    }

    private void fillInitHosts() {
        if (!this.isInitHostsFilled.get() && GlobalAppRuntimeInfo.getContext() != null && this.isInitHostsFilled.compareAndSet(false, true)) {
            this.initHosts.add(DispatchConstants.getAmdcServerDomain());
            if (GlobalAppRuntimeInfo.isTargetProcess()) {
                this.initHosts.addAll(Arrays.asList(DispatchConstants.initHostArray));
            }
        }
    }

    public boolean isInitHostsChanged(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        boolean contains = this.uniqueIdSet.contains(str);
        if (!contains) {
            this.uniqueIdSet.add(str);
        }
        if (contains) {
            return false;
        }
        return true;
    }

    public void switchENV() {
        this.uniqueIdSet.clear();
        this.initHosts.clear();
        this.isInitHostsFilled.set(false);
    }
}
