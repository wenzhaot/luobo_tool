package com.baidu.platform.core.e;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.mapapi.search.share.OnGetShareUrlResultListener;
import com.baidu.mapapi.search.share.ShareUrlResult;
import org.json.JSONException;
import org.json.JSONObject;

public class d extends com.baidu.platform.base.d {
    public SearchResult a(String str) {
        SearchResult shareUrlResult = new ShareUrlResult();
        if (str == null || str.equals("")) {
            shareUrlResult.error = ERRORNO.RESULT_NOT_FOUND;
        } else {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("SDK_InnerError")) {
                    jSONObject = jSONObject.optJSONObject("SDK_InnerError");
                    if (jSONObject.has("PermissionCheckError")) {
                        shareUrlResult.error = ERRORNO.PERMISSION_UNFINISHED;
                    } else if (jSONObject.has("httpStateError")) {
                        String optString = jSONObject.optString("httpStateError");
                        if (optString.equals("NETWORK_ERROR")) {
                            shareUrlResult.error = ERRORNO.NETWORK_ERROR;
                        } else if (optString.equals("REQUEST_ERROR")) {
                            shareUrlResult.error = ERRORNO.REQUEST_ERROR;
                        } else {
                            shareUrlResult.error = ERRORNO.SEARCH_SERVER_INTERNAL_ERROR;
                        }
                    }
                }
                if (!a(str, shareUrlResult, false)) {
                    if (str == null) {
                        shareUrlResult.error = ERRORNO.RESULT_NOT_FOUND;
                    }
                    try {
                        jSONObject = new JSONObject(str);
                        if (str != null) {
                            if (jSONObject.optInt("status_sdk") != 0) {
                                shareUrlResult.error = ERRORNO.RESULT_NOT_FOUND;
                            } else {
                                shareUrlResult.setUrl(jSONObject.optString("shorturl"));
                                shareUrlResult.setType(a().ordinal());
                                shareUrlResult.error = ERRORNO.NO_ERROR;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        shareUrlResult.error = ERRORNO.RESULT_NOT_FOUND;
                    }
                }
            } catch (Exception e2) {
                shareUrlResult.error = ERRORNO.RESULT_NOT_FOUND;
            }
        }
        return shareUrlResult;
    }

    public void a(SearchResult searchResult, Object obj) {
        if (obj != null && (obj instanceof OnGetShareUrlResultListener)) {
            ((OnGetShareUrlResultListener) obj).onGetRouteShareUrlResult((ShareUrlResult) searchResult);
        }
    }
}
