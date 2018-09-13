package com.taobao.agoo.a;

import android.content.Context;
import android.text.TextUtils;
import com.stub.StubApp;
import com.taobao.accs.client.b;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UtilityImpl;
import com.umeng.commonsdk.proguard.g;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: Taobao */
public class a {
    public static final String SP_AGOO_BIND_FILE_NAME = "AGOO_BIND";
    private ConcurrentMap<String, Integer> a = new ConcurrentHashMap();
    private String b;
    private long c;
    private Context d;

    public a(Context context) {
        if (context == null) {
            throw new RuntimeException("Context is null!!");
        }
        this.d = StubApp.getOrigApplicationContext(context.getApplicationContext());
    }

    public void a(String str) {
        Integer num = (Integer) this.a.get(str);
        if (num == null || num.intValue() != 2) {
            this.a.put(str, Integer.valueOf(2));
            b.a(this.d, "AGOO_BIND", this.c, this.a);
        }
    }

    public boolean b(String str) {
        if (this.a.isEmpty()) {
            b();
        }
        Integer num = (Integer) this.a.get(str);
        ALog.i("AgooBindCache", "isAgooRegistered begin..appStatus=" + num + ",mAgooBindStatus=" + this.a, new Object[0]);
        if (UtilityImpl.utdidChanged("Agoo_AppStore", this.d)) {
            return false;
        }
        return num != null && num.intValue() == 2;
    }

    public void c(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.b = str;
        }
    }

    public void a() {
        this.b = null;
    }

    public boolean d(String str) {
        if (this.b == null || !this.b.equals(str)) {
            return false;
        }
        return true;
    }

    private void b() {
        try {
            Object string = this.d.getSharedPreferences("AGOO_BIND", 0).getString("bind_status", null);
            if (TextUtils.isEmpty(string)) {
                ALog.i("AgooBindCache", "restoreAgooClients packs null return", new Object[0]);
                return;
            }
            JSONArray jSONArray = new JSONArray(string);
            this.c = jSONArray.getLong(0);
            if (System.currentTimeMillis() < this.c + 86400000) {
                for (int i = 1; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    this.a.put(jSONObject.getString(g.ao), Integer.valueOf(jSONObject.getInt("s")));
                }
                ALog.i("AgooBindCache", "restoreAgooClients mAgooBindStatus restore = " + this.a, new Object[0]);
                return;
            }
            ALog.i("AgooBindCache", "restoreAgooClients expired", "agooLastFlushTime", Long.valueOf(this.c));
            this.c = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
