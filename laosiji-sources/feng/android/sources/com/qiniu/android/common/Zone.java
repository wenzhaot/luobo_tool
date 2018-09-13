package com.qiniu.android.common;

import java.net.URI;

public abstract class Zone {

    public interface QueryHandler {
        void onFailure(int i);

        void onSuccess();
    }

    public abstract void frozenDomain(String str);

    public abstract void preQuery(String str, QueryHandler queryHandler);

    public abstract boolean preQuery(String str);

    public abstract String upHost(String str, boolean z, String str2);

    protected synchronized String upHost(ZoneInfo zoneInfo, boolean useHttps, String lastUpHost) {
        String upHost;
        String domain;
        upHost = null;
        String upDomain = null;
        if (lastUpHost != null) {
            zoneInfo.frozenDomain(URI.create(lastUpHost).getHost());
        }
        for (int index = 0; index < zoneInfo.upDomainsList.size(); index++) {
            domain = (String) zoneInfo.upDomainsList.get(index);
            long frozenTill = ((Long) zoneInfo.upDomainsMap.get(domain)).longValue();
            if (frozenTill == 0 || frozenTill <= System.currentTimeMillis() / 1000) {
                upDomain = domain;
                break;
            }
        }
        if (upDomain != null) {
            zoneInfo.upDomainsMap.put(upDomain, Long.valueOf(0));
        } else {
            for (String domain2 : zoneInfo.upDomainsList) {
                zoneInfo.upDomainsMap.put(domain2, Long.valueOf(0));
            }
            if (zoneInfo.upDomainsList.size() > 0) {
                upDomain = (String) zoneInfo.upDomainsList.get(0);
            }
        }
        if (upDomain != null) {
            if (useHttps) {
                upHost = String.format("https://%s", new Object[]{upDomain});
            } else {
                upHost = String.format("http://%s", new Object[]{upDomain});
            }
        }
        return upHost;
    }
}
