package com.qiniu.android.common;

import com.umeng.socialize.net.dplus.CommonNetImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ZoneInfo {
    private static int DOMAIN_FROZEN_SECONDS = 600;
    private final int ttl;
    public final List<String> upDomainsList;
    public final Map<String, Long> upDomainsMap;

    public ZoneInfo(int ttl, List<String> upDomainsList, Map<String, Long> upDomainsMap) {
        this.ttl = ttl;
        this.upDomainsList = upDomainsList;
        this.upDomainsMap = upDomainsMap;
    }

    public static ZoneInfo buildFromJson(JSONObject obj) throws JSONException {
        int ttl = obj.getInt("ttl");
        List<String> domainsList = new ArrayList();
        ConcurrentHashMap<String, Long> domainsMap = new ConcurrentHashMap();
        JSONObject upObj = obj.getJSONObject(CommonNetImpl.UP);
        for (String tag : new String[]{"acc", "src", "old_acc", "old_src"}) {
            int i;
            JSONObject tagRootObj = upObj.getJSONObject(tag);
            JSONArray tagMainObj = tagRootObj.getJSONArray("main");
            for (i = 0; i < tagMainObj.length(); i++) {
                String upDomain = tagMainObj.getString(i);
                domainsList.add(upDomain);
                domainsMap.put(upDomain, Long.valueOf(0));
            }
            try {
                JSONArray tagBackupObj = tagRootObj.getJSONArray("backup");
                if (tagBackupObj != null) {
                    for (i = 0; i < tagBackupObj.length(); i++) {
                        String upHost = tagBackupObj.getString(i);
                        domainsList.add(upHost);
                        domainsMap.put(upHost, Long.valueOf(0));
                    }
                }
            } catch (JSONException e) {
            }
        }
        return new ZoneInfo(ttl, domainsList, domainsMap);
    }

    public void frozenDomain(String domain) {
        this.upDomainsMap.put(domain, Long.valueOf((System.currentTimeMillis() / 1000) + ((long) DOMAIN_FROZEN_SECONDS)));
    }

    public String toString() {
        Map<String, Object> m = new HashMap();
        m.put("ttl", Integer.valueOf(this.ttl));
        m.put("upDomainList", this.upDomainsList);
        m.put("upDomainMap", this.upDomainsMap);
        return new JSONObject(m).toString();
    }
}
