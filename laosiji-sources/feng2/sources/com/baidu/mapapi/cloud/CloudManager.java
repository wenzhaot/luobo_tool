package com.baidu.mapapi.cloud;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.common.BaiduMapSDKException;
import com.baidu.platform.comjni.map.cloud.ICloudCenter;
import com.baidu.platform.comjni.map.cloud.a;

public class CloudManager {
    private static final String a = CloudManager.class.getSimpleName();
    private static CloudManager b;
    private ICloudCenter c;

    private CloudManager() {
    }

    private boolean a(BaseCloudSearchInfo baseCloudSearchInfo) {
        if (baseCloudSearchInfo == null) {
            return false;
        }
        String a = baseCloudSearchInfo.a();
        return (a == null || a.equals("")) ? false : this.c.a(a);
    }

    public static CloudManager getInstance() {
        if (b == null) {
            b = new CloudManager();
        }
        return b;
    }

    public boolean boundSearch(BoundSearchInfo boundSearchInfo) {
        return a(boundSearchInfo);
    }

    public void destroy() {
        if (this.c != null) {
            this.c = null;
            BMapManager.destroy();
        }
    }

    public boolean detailSearch(DetailSearchInfo detailSearchInfo) {
        if (detailSearchInfo == null) {
            return false;
        }
        String a = detailSearchInfo.a();
        return (a == null || a.equals("")) ? false : this.c.b(a);
    }

    public void init() {
        if (this.c == null) {
            BMapManager.init();
            this.c = new a();
        }
    }

    public void init(CloudListener cloudListener) {
        if (this.c == null) {
            BMapManager.init();
            this.c = new a();
            this.c.a(cloudListener);
        }
    }

    public boolean localSearch(LocalSearchInfo localSearchInfo) {
        return a(localSearchInfo);
    }

    public boolean nearbySearch(NearbySearchInfo nearbySearchInfo) {
        return a(nearbySearchInfo);
    }

    public void registerListener(CloudListener cloudListener) {
        if (this.c != null) {
            if (cloudListener == null) {
                throw new BaiduMapSDKException("the CloudListener should not be null.");
            }
            this.c.a(cloudListener);
        }
    }

    public boolean rgcSearch(CloudRgcInfo cloudRgcInfo) {
        if (cloudRgcInfo == null) {
            return false;
        }
        String a = cloudRgcInfo.a();
        return (a == null || a.equals("")) ? false : this.c.c(a);
    }

    public void unregisterListener() {
        if (this.c != null) {
            this.c.a(null);
        }
    }
}
