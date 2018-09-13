package com.baidu.platform.base;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.baidu.mapapi.http.AsyncHttpClient;
import com.baidu.mapapi.http.HttpClient.HttpStateError;
import com.baidu.mapapi.http.HttpClient.ProtoResultCallback;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.platform.comapi.util.PermissionCheck;
import com.baidu.platform.core.a.b;
import com.baidu.platform.core.a.c;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class a {
    protected final Lock a = new ReentrantLock();
    private AsyncHttpClient b = new AsyncHttpClient();
    private Handler c = new Handler(Looper.getMainLooper());
    private boolean d = true;
    private DistrictResult e = null;

    private void a(AsyncHttpClient asyncHttpClient, ProtoResultCallback protoResultCallback, SearchResult searchResult) {
        asyncHttpClient.get(new c(((DistrictResult) searchResult).getCityName()).a(), protoResultCallback);
    }

    private void a(HttpStateError httpStateError, d dVar, Object obj) {
        a(dVar.a("{SDK_InnerError:{httpStateError:" + httpStateError + "}}"), obj, dVar);
    }

    private void a(SearchResult searchResult, Object obj, d dVar) {
        this.c.post(new c(this, dVar, searchResult, obj));
    }

    private void a(String str) {
        if (!b(str)) {
            Log.e("BaseSearch", "Permission check unfinished, try again");
            int permissionCheck = PermissionCheck.permissionCheck();
            if (permissionCheck != 0) {
                Log.e("BaseSearch", "The authorized result is: " + permissionCheck);
            }
        }
    }

    private void a(String str, d dVar, Object obj, AsyncHttpClient asyncHttpClient, ProtoResultCallback protoResultCallback) {
        SearchResult a = dVar.a(str);
        a.status = c(str);
        if (a(dVar, a)) {
            a(asyncHttpClient, protoResultCallback, a);
        } else if (dVar instanceof b) {
            if (this.e != null) {
                ((DistrictResult) a).setCityCode(this.e.getCityCode());
                ((DistrictResult) a).setCenterPt(this.e.getCenterPt());
            }
            a(a, obj, dVar);
            this.d = true;
            this.e = null;
            ((b) dVar).a(false);
        } else {
            a(a, obj, dVar);
        }
    }

    private boolean a(d dVar, SearchResult searchResult) {
        if (!(dVar instanceof b)) {
            return false;
        }
        if (ERRORNO.RESULT_NOT_FOUND != ((DistrictResult) searchResult).error) {
            return false;
        }
        if (((DistrictResult) searchResult).getCityName() == null) {
            return false;
        }
        if (!this.d) {
            return false;
        }
        this.d = false;
        this.e = (DistrictResult) searchResult;
        ((b) dVar).a(true);
        return true;
    }

    private boolean b(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.has("SDK_InnerError") || !jSONObject.optJSONObject("SDK_InnerError").has("PermissionCheckError")) {
                return true;
            }
            Log.e("BaseSearch", "Permission check unfinished");
            return false;
        } catch (JSONException e) {
            Log.e("BaseSearch", "Create JSONObject failed");
            return false;
        }
    }

    private int c(String str) {
        if (str == null || str.equals("")) {
            return 10204;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            return jSONObject.has(NotificationCompat.CATEGORY_STATUS) ? jSONObject.getInt(NotificationCompat.CATEGORY_STATUS) : jSONObject.has("status_sp") ? jSONObject.getInt("status_sp") : jSONObject.has("result") ? jSONObject.optJSONObject("result").optInt("error") : 10204;
        } catch (JSONException e) {
            Log.e("BaseSearch", "Create JSONObject failed when get response result status");
            return 10204;
        }
    }

    protected boolean a(e eVar, Object obj, d dVar) {
        if (dVar == null) {
            Log.e(a.class.getSimpleName(), "The SearchParser is null, must be applied.");
            return false;
        }
        String a = eVar.a();
        if (a == null) {
            Log.e("BaseSearch", "The sendurl is: " + a);
            a(dVar.a("{SDK_InnerError:{PermissionCheckError:Error}}"), obj, dVar);
            return false;
        }
        this.b.get(a, new b(this, dVar, obj));
        return true;
    }
}
