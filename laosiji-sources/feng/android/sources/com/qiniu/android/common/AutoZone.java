package com.qiniu.android.common;

import com.qiniu.android.common.Zone.QueryHandler;
import com.qiniu.android.dns.DnsManager;
import com.qiniu.android.http.Client;
import com.qiniu.android.http.CompletionHandler;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpToken;
import com.qiniu.android.utils.UrlSafeBase64;
import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public final class AutoZone extends Zone {
    public static final AutoZone autoZone = new AutoZone(null);
    private Client client;
    private final String ucServer;
    private Map<ZoneIndex, ZoneInfo> zones;

    static class ZoneIndex {
        final String accessKey;
        final String bucket;

        ZoneIndex(String accessKey, String bucket) {
            this.accessKey = accessKey;
            this.bucket = bucket;
        }

        static ZoneIndex getFromToken(String token) {
            Exception e;
            String[] strings = token.split(":");
            String ak = strings[0];
            try {
                String policy = new String(UrlSafeBase64.decode(strings[2]), Constants.UTF_8);
                String str;
                try {
                    str = policy;
                    return new ZoneIndex(ak, new JSONObject(policy).getString("scope").split(":")[0]);
                } catch (Exception e2) {
                    e = e2;
                    str = policy;
                    e.printStackTrace();
                    return null;
                }
            } catch (Exception e3) {
                e = e3;
                e.printStackTrace();
                return null;
            }
        }

        public int hashCode() {
            return (this.accessKey.hashCode() * 37) + this.bucket.hashCode();
        }

        public boolean equals(Object obj) {
            return obj == this || (obj != null && (obj instanceof ZoneIndex) && ((ZoneIndex) obj).accessKey.equals(this.accessKey) && ((ZoneIndex) obj).bucket.equals(this.bucket));
        }
    }

    public AutoZone(DnsManager dns) {
        this("https://uc.qbox.me", dns);
    }

    AutoZone(String ucServer, DnsManager dns) {
        this.zones = new ConcurrentHashMap();
        this.client = new Client();
        this.ucServer = ucServer;
    }

    private void getZoneJsonAsync(ZoneIndex index, CompletionHandler handler) {
        this.client.asyncGet(this.ucServer + "/v2/query?ak=" + index.accessKey + "&bucket=" + index.bucket, null, UpToken.NULL, handler);
    }

    private ResponseInfo getZoneJsonSync(ZoneIndex index) {
        return this.client.syncGet(this.ucServer + "/v2/query?ak=" + index.accessKey + "&bucket=" + index.bucket, null);
    }

    ZoneInfo zoneInfo(String ak, String bucket) {
        return (ZoneInfo) this.zones.get(new ZoneIndex(ak, bucket));
    }

    ZoneInfo queryByToken(String token) {
        try {
            String[] strings = token.split(":");
            return zoneInfo(strings[0], new JSONObject(new String(UrlSafeBase64.decode(strings[2]), Constants.UTF_8)).getString("scope").split(":")[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    void preQueryIndex(final ZoneIndex index, final QueryHandler complete) {
        if (index == null) {
            complete.onFailure(-5);
        } else if (((ZoneInfo) this.zones.get(index)) != null) {
            complete.onSuccess();
        } else {
            getZoneJsonAsync(index, new CompletionHandler() {
                public void complete(ResponseInfo info, JSONObject response) {
                    if (!info.isOK() || response == null) {
                        complete.onFailure(info.statusCode);
                        return;
                    }
                    try {
                        AutoZone.this.zones.put(index, ZoneInfo.buildFromJson(response));
                        complete.onSuccess();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        complete.onFailure(-1);
                    }
                }
            });
        }
    }

    boolean preQueryIndex(ZoneIndex index) {
        if (index == null) {
            return false;
        }
        if (((ZoneInfo) this.zones.get(index)) != null) {
            return true;
        }
        try {
            this.zones.put(index, ZoneInfo.buildFromJson(getZoneJsonSync(index).response));
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized String upHost(String token, boolean useHttps, String frozenDomain) {
        String upHost;
        ZoneInfo info = queryByToken(token);
        if (info != null) {
            upHost = super.upHost(info, useHttps, frozenDomain);
        } else {
            upHost = null;
        }
        return upHost;
    }

    public void preQuery(String token, QueryHandler complete) {
        preQueryIndex(ZoneIndex.getFromToken(token), complete);
    }

    public boolean preQuery(String token) {
        return preQueryIndex(ZoneIndex.getFromToken(token));
    }

    public synchronized void frozenDomain(String upHostUrl) {
        if (upHostUrl != null) {
            String frozenDomain = URI.create(upHostUrl).getHost();
            ZoneInfo zoneInfo = null;
            for (Entry<ZoneIndex, ZoneInfo> entry : this.zones.entrySet()) {
                ZoneInfo eachZoneInfo = (ZoneInfo) entry.getValue();
                if (eachZoneInfo.upDomainsList.contains(frozenDomain)) {
                    zoneInfo = eachZoneInfo;
                    break;
                }
            }
            if (zoneInfo != null) {
                zoneInfo.frozenDomain(frozenDomain);
            }
        }
    }
}
