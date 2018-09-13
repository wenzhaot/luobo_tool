package com.baidu.platform.core.c;

import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiIndoorInfo;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.platform.base.d;
import com.facebook.common.util.UriUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b extends d {
    private boolean a(String str, PoiIndoorResult poiIndoorResult) {
        boolean z = false;
        if (str == null || "".equals(str)) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            switch (jSONObject.optInt("errNo")) {
                case 0:
                    JSONObject optJSONObject = jSONObject.optJSONObject(UriUtil.DATA_SCHEME);
                    if (optJSONObject == null) {
                        return false;
                    }
                    JSONArray optJSONArray = optJSONObject.optJSONArray("poi_list");
                    if (optJSONArray == null || optJSONArray.length() <= 0) {
                        poiIndoorResult.error = ERRORNO.RESULT_NOT_FOUND;
                    } else {
                        List arrayList = new ArrayList();
                        while (true) {
                            boolean z2 = z;
                            if (z2 < optJSONArray.length()) {
                                JSONObject jSONObject2 = (JSONObject) optJSONArray.opt(z2);
                                if (jSONObject2 != null) {
                                    PoiIndoorInfo poiIndoorInfo = new PoiIndoorInfo();
                                    poiIndoorInfo.address = jSONObject2.optString("address");
                                    poiIndoorInfo.bid = jSONObject2.optString("bd_id");
                                    poiIndoorInfo.cid = jSONObject2.optInt("cid");
                                    poiIndoorInfo.discount = jSONObject2.optInt("discount");
                                    poiIndoorInfo.floor = jSONObject2.optString("floor");
                                    poiIndoorInfo.name = jSONObject2.optString("name");
                                    poiIndoorInfo.phone = jSONObject2.optString("phone");
                                    poiIndoorInfo.price = (double) jSONObject2.optInt("price");
                                    poiIndoorInfo.starLevel = jSONObject2.optInt("star_level");
                                    poiIndoorInfo.tag = jSONObject2.optString("tag");
                                    poiIndoorInfo.uid = jSONObject2.optString("uid");
                                    poiIndoorInfo.groupNum = jSONObject2.optInt("tuan_nums");
                                    int parseInt = Integer.parseInt(jSONObject2.optString("twp"));
                                    if ((parseInt & 1) == 1) {
                                        poiIndoorInfo.isGroup = true;
                                    }
                                    if ((parseInt & 2) == 1) {
                                        poiIndoorInfo.isTakeOut = true;
                                    }
                                    if ((parseInt & 4) == 1) {
                                        poiIndoorInfo.isWaited = true;
                                    }
                                    poiIndoorInfo.latLng = CoordUtil.mc2ll(new GeoPoint(jSONObject2.optDouble("pt_y"), jSONObject2.optDouble("pt_x")));
                                    arrayList.add(poiIndoorInfo);
                                }
                                z = z2 + 1;
                            } else {
                                poiIndoorResult.error = ERRORNO.NO_ERROR;
                                poiIndoorResult.setmArrayPoiInfo(arrayList);
                            }
                        }
                    }
                    poiIndoorResult.pageNum = optJSONObject.optInt("page_num");
                    poiIndoorResult.poiNum = optJSONObject.optInt("poi_num");
                    poiIndoorResult.error = ERRORNO.NO_ERROR;
                    return true;
                case 1:
                    String optString = jSONObject.optString("Msg");
                    if (optString.contains("bid")) {
                        poiIndoorResult.error = ERRORNO.POIINDOOR_BID_ERROR;
                        return true;
                    } else if (!optString.contains("floor")) {
                        return false;
                    } else {
                        poiIndoorResult.error = ERRORNO.POIINDOOR_FLOOR_ERROR;
                        return true;
                    }
                case 5:
                    return false;
                default:
                    poiIndoorResult.error = ERRORNO.POIINDOOR_SERVER_ERROR;
                    return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public SearchResult a(String str) {
        PoiIndoorResult poiIndoorResult = new PoiIndoorResult();
        if (str == null || str.equals("")) {
            poiIndoorResult.error = ERRORNO.RESULT_NOT_FOUND;
        } else {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("SDK_InnerError")) {
                    jSONObject = jSONObject.optJSONObject("SDK_InnerError");
                    if (jSONObject.has("PermissionCheckError")) {
                        poiIndoorResult.error = ERRORNO.PERMISSION_UNFINISHED;
                    } else if (jSONObject.has("httpStateError")) {
                        String optString = jSONObject.optString("httpStateError");
                        if (optString.equals("NETWORK_ERROR")) {
                            poiIndoorResult.error = ERRORNO.NETWORK_ERROR;
                        } else if (optString.equals("REQUEST_ERROR")) {
                            poiIndoorResult.error = ERRORNO.REQUEST_ERROR;
                        } else {
                            poiIndoorResult.error = ERRORNO.SEARCH_SERVER_INTERNAL_ERROR;
                        }
                    }
                }
                if (!(a(str, poiIndoorResult, false) || a(str, poiIndoorResult))) {
                    poiIndoorResult.error = ERRORNO.RESULT_NOT_FOUND;
                }
            } catch (Exception e) {
                poiIndoorResult.error = ERRORNO.RESULT_NOT_FOUND;
            }
        }
        return poiIndoorResult;
    }

    public void a(SearchResult searchResult, Object obj) {
        if (obj != null && (obj instanceof OnGetPoiSearchResultListener)) {
            ((OnGetPoiSearchResultListener) obj).onGetPoiIndoorResult((PoiIndoorResult) searchResult);
        }
    }
}
