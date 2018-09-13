package com.qiniu.android.common;

import android.util.Log;
import com.qiniu.android.common.Zone.QueryHandler;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public final class FixedZone extends Zone {
    public static final Zone zone0 = new FixedZone(new String[]{"upload.qiniup.com", "upload-jjh.qiniup.com", "upload-xs.qiniup.com", "up.qiniup.com", "up-jjh.qiniup.com", "up-xs.qiniup.com", "upload.qbox.me", "up.qbox.me"});
    public static final Zone zone1 = new FixedZone(new String[]{"upload-z1.qiniup.com", "up-z1.qiniup.com", "upload-z1.qbox.me", "up-z1.qbox.me"});
    public static final Zone zone2 = new FixedZone(new String[]{"upload-z2.qiniup.com", "upload-dg.qiniup.com", "upload-fs.qiniup.com", "up-z2.qiniup.com", "up-dg.qiniup.com", "up-fs.qiniup.com", "upload-z2.qbox.me", "up-z2.qbox.me"});
    public static final Zone zoneAs0 = new FixedZone(new String[]{"upload-as0.qiniup.com", "up-as0.qiniup.com", "upload-as0.qbox.me", "up-as0.qbox.me"});
    public static final Zone zoneNa0 = new FixedZone(new String[]{"upload-na0.qiniup.com", "up-na0.qiniup.com", "upload-na0.qbox.me", "up-na0.qbox.me"});
    private ZoneInfo zoneInfo;

    public FixedZone(ZoneInfo zoneInfo) {
        this.zoneInfo = zoneInfo;
    }

    public FixedZone(String[] upDomains) {
        this.zoneInfo = createZoneInfo(upDomains);
    }

    public static ZoneInfo createZoneInfo(String[] upDomains) {
        List<String> upDomainsList = new ArrayList();
        Map<String, Long> upDomainsMap = new ConcurrentHashMap();
        for (String domain : upDomains) {
            upDomainsList.add(domain);
            upDomainsMap.put(domain, Long.valueOf(0));
        }
        return new ZoneInfo(0, upDomainsList, upDomainsMap);
    }

    public synchronized String upHost(String upToken, boolean useHttps, String frozenDomain) {
        String upHost;
        upHost = upHost(this.zoneInfo, useHttps, frozenDomain);
        for (Entry<String, Long> entry : this.zoneInfo.upDomainsMap.entrySet()) {
            Log.d("Qiniu.FixedZone", ((String) entry.getKey()) + ", " + entry.getValue());
        }
        return upHost;
    }

    public void preQuery(String token, QueryHandler complete) {
        complete.onSuccess();
    }

    public boolean preQuery(String token) {
        return true;
    }

    public synchronized void frozenDomain(String upHostUrl) {
        if (upHostUrl != null) {
            this.zoneInfo.frozenDomain(URI.create(upHostUrl).getHost());
        }
    }
}
