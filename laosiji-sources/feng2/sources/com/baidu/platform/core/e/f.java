package com.baidu.platform.core.e;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.mapapi.search.share.OnGetShareUrlResultListener;
import com.baidu.mapapi.search.share.ShareUrlResult;
import com.baidu.platform.base.d;
import org.json.JSONException;
import org.json.JSONObject;

public class f extends d {
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
                if (str == null) {
                    shareUrlResult.error = ERRORNO.RESULT_NOT_FOUND;
                } else {
                    try {
                        jSONObject = new JSONObject(str);
                        if (jSONObject != null) {
                            if (jSONObject.optString("state").equals("success")) {
                                shareUrlResult.setUrl(jSONObject.optString("url"));
                                shareUrlResult.setType(a().ordinal());
                                shareUrlResult.error = ERRORNO.NO_ERROR;
                            } else {
                                shareUrlResult.error = ERRORNO.RESULT_NOT_FOUND;
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
            OnGetShareUrlResultListener onGetShareUrlResultListener = (OnGetShareUrlResultListener) obj;
            switch (a()) {
                case POI_DETAIL_SHARE:
                    onGetShareUrlResultListener.onGetPoiDetailShareUrlResult((ShareUrlResult) searchResult);
                    return;
                case LOCATION_SEARCH_SHARE:
                    onGetShareUrlResultListener.onGetLocationShareUrlResult((ShareUrlResult) searchResult);
                    return;
                default:
                    return;
            }
        }
    }
}
