package com.baidu.platform.core.c;

import android.support.v4.app.NotificationCompat;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.platform.comapi.util.CoordTrans;
import org.json.JSONException;
import org.json.JSONObject;

public class d extends com.baidu.platform.base.d {
    private boolean a(String str, PoiDetailResult poiDetailResult) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.optInt(NotificationCompat.CATEGORY_STATUS) != 0) {
                return false;
            }
            jSONObject = jSONObject.optJSONObject("result");
            if (jSONObject == null) {
                return false;
            }
            poiDetailResult.name = jSONObject.optString("name");
            JSONObject optJSONObject = jSONObject.optJSONObject("location");
            if (optJSONObject != null) {
                double optDouble = optJSONObject.optDouble(DispatchConstants.LATITUDE);
                double optDouble2 = optJSONObject.optDouble(DispatchConstants.LONGTITUDE);
                if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
                    poiDetailResult.location = CoordTrans.baiduToGcj(new LatLng(optDouble, optDouble2));
                } else {
                    poiDetailResult.location = new LatLng(optDouble, optDouble2);
                }
            }
            poiDetailResult.address = jSONObject.optString("address");
            poiDetailResult.telephone = jSONObject.optString("telephone");
            poiDetailResult.uid = jSONObject.optString("uid");
            jSONObject = jSONObject.optJSONObject("detail_info");
            if (jSONObject != null) {
                poiDetailResult.tag = jSONObject.optString("tag");
                poiDetailResult.detailUrl = jSONObject.optString("detail_url");
                poiDetailResult.type = jSONObject.optString("type");
                poiDetailResult.price = jSONObject.optDouble("price", 0.0d);
                poiDetailResult.overallRating = jSONObject.optDouble("overall_rating", 0.0d);
                poiDetailResult.tasteRating = jSONObject.optDouble("taste_rating", 0.0d);
                poiDetailResult.serviceRating = jSONObject.optDouble("service_rating", 0.0d);
                poiDetailResult.environmentRating = jSONObject.optDouble("environment_rating", 0.0d);
                poiDetailResult.facilityRating = jSONObject.optDouble("facility_rating", 0.0d);
                poiDetailResult.hygieneRating = jSONObject.optDouble("hygiene_rating", 0.0d);
                poiDetailResult.technologyRating = jSONObject.optDouble("technology_rating", 0.0d);
                poiDetailResult.imageNum = jSONObject.optInt("image_num");
                poiDetailResult.grouponNum = jSONObject.optInt("groupon_num");
                poiDetailResult.commentNum = jSONObject.optInt("comment_num");
                poiDetailResult.favoriteNum = jSONObject.optInt("favorite_num");
                poiDetailResult.checkinNum = jSONObject.optInt("checkin_num");
                poiDetailResult.shopHours = jSONObject.optString("shop_hours");
            }
            poiDetailResult.error = ERRORNO.NO_ERROR;
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public SearchResult a(String str) {
        PoiDetailResult poiDetailResult = new PoiDetailResult();
        if (str == null || str.equals("")) {
            poiDetailResult.error = ERRORNO.RESULT_NOT_FOUND;
        } else {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("SDK_InnerError")) {
                    jSONObject = jSONObject.optJSONObject("SDK_InnerError");
                    if (jSONObject.has("PermissionCheckError")) {
                        poiDetailResult.error = ERRORNO.PERMISSION_UNFINISHED;
                    } else if (jSONObject.has("httpStateError")) {
                        String optString = jSONObject.optString("httpStateError");
                        if (optString.equals("NETWORK_ERROR")) {
                            poiDetailResult.error = ERRORNO.NETWORK_ERROR;
                        } else if (optString.equals("REQUEST_ERROR")) {
                            poiDetailResult.error = ERRORNO.REQUEST_ERROR;
                        } else {
                            poiDetailResult.error = ERRORNO.SEARCH_SERVER_INTERNAL_ERROR;
                        }
                    }
                }
                if (!a(str, poiDetailResult)) {
                    poiDetailResult.error = ERRORNO.RESULT_NOT_FOUND;
                }
            } catch (Exception e) {
                poiDetailResult.error = ERRORNO.RESULT_NOT_FOUND;
            }
        }
        return poiDetailResult;
    }

    public void a(SearchResult searchResult, Object obj) {
        if (obj != null && (obj instanceof OnGetPoiSearchResultListener)) {
            ((OnGetPoiSearchResultListener) obj).onGetPoiDetailResult((PoiDetailResult) searchResult);
        }
    }
}
