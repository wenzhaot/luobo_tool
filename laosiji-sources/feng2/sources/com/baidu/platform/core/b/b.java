package com.baidu.platform.core.b;

import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.platform.base.d;
import com.facebook.common.util.UriUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class b extends d {
    private boolean a(String str, GeoCodeResult geoCodeResult) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject == null) {
                return false;
            }
            JSONObject optJSONObject = jSONObject.optJSONObject("result");
            if (optJSONObject == null || optJSONObject.optInt("error") != 0) {
                return false;
            }
            jSONObject = jSONObject.optJSONObject(UriUtil.LOCAL_CONTENT_SCHEME);
            if (jSONObject == null) {
                return false;
            }
            optJSONObject = jSONObject.optJSONObject("coord");
            if (optJSONObject == null) {
                return false;
            }
            geoCodeResult.setLocation(CoordUtil.mc2ll(new GeoPoint((double) optJSONObject.optInt("y"), (double) optJSONObject.optInt("x"))));
            geoCodeResult.setAddress(jSONObject.optString("wd"));
            geoCodeResult.error = ERRORNO.NO_ERROR;
            return true;
        } catch (JSONException e) {
            geoCodeResult.error = ERRORNO.RESULT_NOT_FOUND;
            e.printStackTrace();
        }
    }

    public SearchResult a(String str) {
        GeoCodeResult geoCodeResult = new GeoCodeResult();
        if (str == null || str.equals("")) {
            geoCodeResult.error = ERRORNO.RESULT_NOT_FOUND;
        } else {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("SDK_InnerError")) {
                    jSONObject = jSONObject.optJSONObject("SDK_InnerError");
                    if (jSONObject.has("PermissionCheckError")) {
                        geoCodeResult.error = ERRORNO.PERMISSION_UNFINISHED;
                    } else if (jSONObject.has("httpStateError")) {
                        String optString = jSONObject.optString("httpStateError");
                        if (optString.equals("NETWORK_ERROR")) {
                            geoCodeResult.error = ERRORNO.NETWORK_ERROR;
                        } else if (optString.equals("REQUEST_ERROR")) {
                            geoCodeResult.error = ERRORNO.REQUEST_ERROR;
                        } else {
                            geoCodeResult.error = ERRORNO.SEARCH_SERVER_INTERNAL_ERROR;
                        }
                    }
                }
                if (!(a(str, geoCodeResult, false) || a(str, geoCodeResult))) {
                    geoCodeResult.error = ERRORNO.RESULT_NOT_FOUND;
                }
            } catch (Exception e) {
                geoCodeResult.error = ERRORNO.RESULT_NOT_FOUND;
            }
        }
        return geoCodeResult;
    }

    public void a(SearchResult searchResult, Object obj) {
        if (obj != null && (obj instanceof OnGetGeoCoderResultListener)) {
            ((OnGetGeoCoderResultListener) obj).onGetGeoCodeResult((GeoCodeResult) searchResult);
        }
    }
}
