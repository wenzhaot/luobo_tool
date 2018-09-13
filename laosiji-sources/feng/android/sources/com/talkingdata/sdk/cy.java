package com.talkingdata.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: td */
final class cy extends BroadcastReceiver {
    cg a = new cg();
    ArrayList b;
    JSONArray c;
    cf d;
    cf e;
    long f = 0;
    long g = 0;
    private long h = 180000;
    private WifiManager i;

    public cy(WifiManager wifiManager) {
        this.i = wifiManager;
    }

    public void onReceive(Context context, Intent intent) {
        cq.a.post(new cz(this));
    }

    private void a() {
        try {
            dd ddVar = new dd();
            ddVar.b = "env";
            ddVar.c = "wifiUpdate";
            ddVar.a = a.ENV;
            br.a().post(ddVar);
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    private cf b() {
        try {
            this.d = a(this.c);
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
        return this.d;
    }

    private cf c() {
        try {
            this.b = (ArrayList) this.i.getScanResults();
            if (this.b != null) {
                JSONArray jSONArray = new JSONArray();
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 >= this.b.size()) {
                        break;
                    }
                    if (((ScanResult) this.b.get(i2)).level >= -75) {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("SSID", ((ScanResult) this.b.get(i2)).SSID);
                        jSONObject.put("BSSID", ((ScanResult) this.b.get(i2)).BSSID);
                        jSONObject.put("level", ((ScanResult) this.b.get(i2)).level);
                        jSONArray.put(jSONObject);
                    }
                    i = i2 + 1;
                }
                this.c = jSONArray;
                this.e = a(jSONArray);
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
        return this.e;
    }

    private cf a(JSONArray jSONArray) {
        int i = 0;
        if (jSONArray == null) {
            return null;
        }
        List arrayList = new ArrayList();
        while (true) {
            int i2 = i;
            if (i2 < jSONArray.length()) {
                try {
                    JSONObject jSONObject = jSONArray.getJSONObject(i2);
                    arrayList.add(new cb(jSONObject.getString("SSID"), jSONObject.getString("BSSID"), (byte) jSONObject.getInt("level"), (byte) 0, (byte) 0));
                } catch (Throwable th) {
                    cs.postSDKError(th);
                }
                i = i2 + 1;
            } else {
                cf cfVar = new cf();
                cfVar.setBsslist(arrayList);
                return cfVar;
            }
        }
    }
}
