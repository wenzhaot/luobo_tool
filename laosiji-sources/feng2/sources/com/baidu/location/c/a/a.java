package com.baidu.location.c.a;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import com.baidu.location.h.b;
import com.baidu.location.h.f;
import com.baidu.location.h.k;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import org.json.JSONObject;

final class a extends f {
    private static a p = null;
    private String a;
    private String b;
    private String c;
    private String d;
    private SharedPreferences e;
    private Handler f;

    private a() {
        this.a = null;
        this.f = null;
        this.f = new Handler();
        this.k = new HashMap();
    }

    private boolean a(String str, String str2) {
        File file = new File(str2);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[4096];
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            while (true) {
                int read = bufferedInputStream.read(bArr);
                if (read > 0) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    httpURLConnection.disconnect();
                    fileOutputStream.close();
                    bufferedInputStream.close();
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
    }

    static a b() {
        if (p == null) {
            p = new a();
        }
        return p;
    }

    private Handler f() {
        return this.f;
    }

    private boolean g() {
        if (this.c == null || this.a == null) {
            return false;
        }
        String str = k.h() + File.separator + "download" + File.separator + this.a;
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!a(this.c, str + File.separator + "data.zip")) {
            return false;
        }
        file = new File(k.h() + File.separator + "indoorinfo" + File.separator + this.a + "/");
        if (file.exists()) {
            file.delete();
            file.mkdirs();
        } else {
            file.mkdirs();
        }
        try {
            new f().a(str + File.separator + "data.zip", k.h() + File.separator + "indoorinfo" + File.separator + this.a + "/");
            Editor edit = this.e.edit();
            edit.putString("indoor_roadnet_" + this.a, this.d);
            edit.commit();
            d.a().b();
            return true;
        } catch (Exception e) {
            file.delete();
            return false;
        }
    }

    public void a() {
        this.k.clear();
        this.k.put("bldg", this.a);
        this.k.put("vernum", this.b);
        this.k.put("mb", Build.MODEL);
        this.k.put("cuid", b.a().b);
        this.h = "http://loc.map.baidu.com/apigetindoordata.php";
    }

    void a(String str) {
        this.e = PreferenceManager.getDefaultSharedPreferences(com.baidu.location.f.getServiceContext());
        this.a = str;
        this.b = this.e.getString("indoor_roadnet_" + str, "null");
        f().postDelayed(new b(this), 1000);
    }

    public void a(boolean z) {
        if (z) {
            try {
                JSONObject jSONObject = new JSONObject(this.j);
                int i = jSONObject.getInt("error");
                if (i == 0) {
                    this.c = jSONObject.getString("downloadlink");
                    if (jSONObject.has("vernum")) {
                        this.d = jSONObject.getString("vernum");
                    }
                    f().post(new c(this));
                }
                if (i == 1) {
                    d.a().b();
                }
                if (i != -1 && i == -2) {
                }
            } catch (Exception e) {
            }
        }
    }

    void c() {
        Editor edit = this.e.edit();
        edit.putString("indoor_roadnet_" + this.a, "0");
        edit.commit();
    }
}
