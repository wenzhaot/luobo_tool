package com.baidu.platform.core.b;

import android.support.v4.app.NotificationCompat;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult.AddressComponent;
import com.baidu.platform.base.d;
import com.baidu.platform.comapi.util.CoordTrans;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class e extends d {
    private AddressComponent a(JSONObject jSONObject, String str) {
        if (jSONObject == null || str == null || "".equals(str)) {
            return null;
        }
        JSONObject optJSONObject = jSONObject.optJSONObject(str);
        if (optJSONObject == null) {
            return null;
        }
        AddressComponent addressComponent = new AddressComponent();
        addressComponent.city = optJSONObject.optString("city");
        addressComponent.district = optJSONObject.optString("district");
        addressComponent.province = optJSONObject.optString("province");
        addressComponent.adcode = optJSONObject.optInt("adcode");
        addressComponent.street = optJSONObject.optString("street");
        addressComponent.streetNumber = optJSONObject.optString("street_number");
        addressComponent.countryName = optJSONObject.optString("country");
        addressComponent.countryCode = optJSONObject.optInt("country_code");
        return addressComponent;
    }

    private List<PoiInfo> a(JSONObject jSONObject, String str, String str2) {
        if (jSONObject == null || str == null || "".equals(str)) {
            return null;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray(str);
        if (optJSONArray == null) {
            return null;
        }
        List<PoiInfo> arrayList = new ArrayList();
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                PoiInfo poiInfo = new PoiInfo();
                poiInfo.address = optJSONObject.optString("addr");
                poiInfo.phoneNum = optJSONObject.optString("tel");
                poiInfo.uid = optJSONObject.optString("uid");
                poiInfo.postCode = optJSONObject.optString("zip");
                poiInfo.name = optJSONObject.optString("name");
                poiInfo.location = b(optJSONObject, "point");
                poiInfo.city = str2;
                arrayList.add(poiInfo);
            }
        }
        return arrayList;
    }

    private boolean a(String str, ReverseGeoCodeResult reverseGeoCodeResult) {
        if (str != null) {
            try {
                if (str.length() > 0) {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject == null) {
                        reverseGeoCodeResult.error = ERRORNO.RESULT_NOT_FOUND;
                        return false;
                    }
                    int optInt = jSONObject.optInt(NotificationCompat.CATEGORY_STATUS);
                    if (optInt != 0) {
                        switch (optInt) {
                            case 1:
                                reverseGeoCodeResult.error = ERRORNO.SEARCH_SERVER_INTERNAL_ERROR;
                                return false;
                            case 2:
                                reverseGeoCodeResult.error = ERRORNO.SEARCH_OPTION_ERROR;
                                return false;
                            default:
                                reverseGeoCodeResult.error = ERRORNO.RESULT_NOT_FOUND;
                                return false;
                        }
                    } else if (a(jSONObject, reverseGeoCodeResult)) {
                        return true;
                    } else {
                        reverseGeoCodeResult.error = ERRORNO.RESULT_NOT_FOUND;
                        return false;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                reverseGeoCodeResult.error = ERRORNO.RESULT_NOT_FOUND;
                return false;
            }
        }
        reverseGeoCodeResult.error = ERRORNO.SEARCH_SERVER_INTERNAL_ERROR;
        return false;
    }

    private boolean a(JSONObject jSONObject, ReverseGeoCodeResult reverseGeoCodeResult) {
        if (jSONObject == null) {
            return false;
        }
        JSONObject optJSONObject = jSONObject.optJSONObject("result");
        if (optJSONObject == null) {
            return false;
        }
        reverseGeoCodeResult.setCityCode(optJSONObject.optInt("cityCode"));
        reverseGeoCodeResult.setAddress(optJSONObject.optString("formatted_address"));
        reverseGeoCodeResult.setBusinessCircle(optJSONObject.optString("business"));
        reverseGeoCodeResult.setAddressDetail(a(optJSONObject, "addressComponent"));
        reverseGeoCodeResult.setLocation(c(optJSONObject, "location"));
        String str = "";
        if (reverseGeoCodeResult.getAddressDetail() != null) {
            str = reverseGeoCodeResult.getAddressDetail().city;
        }
        reverseGeoCodeResult.setPoiList(a(optJSONObject, "pois", str));
        reverseGeoCodeResult.setSematicDescription(optJSONObject.optString("sematic_description"));
        reverseGeoCodeResult.error = ERRORNO.NO_ERROR;
        return true;
    }

    private LatLng b(JSONObject jSONObject, String str) {
        if (jSONObject == null || str == null || "".equals(str)) {
            return null;
        }
        JSONObject optJSONObject = jSONObject.optJSONObject(str);
        if (optJSONObject == null) {
            return null;
        }
        LatLng latLng = new LatLng(optJSONObject.optDouble("y"), optJSONObject.optDouble("x"));
        return SDKInitializer.getCoordType() == CoordType.GCJ02 ? CoordTrans.baiduToGcj(latLng) : latLng;
    }

    private LatLng c(JSONObject jSONObject, String str) {
        if (jSONObject == null || str == null || "".equals(str)) {
            return null;
        }
        JSONObject optJSONObject = jSONObject.optJSONObject(str);
        if (optJSONObject == null) {
            return null;
        }
        LatLng latLng = new LatLng(optJSONObject.optDouble(DispatchConstants.LATITUDE), optJSONObject.optDouble(DispatchConstants.LONGTITUDE));
        return SDKInitializer.getCoordType() == CoordType.GCJ02 ? CoordTrans.baiduToGcj(latLng) : latLng;
    }

    public SearchResult a(String str) {
        ReverseGeoCodeResult reverseGeoCodeResult = new ReverseGeoCodeResult();
        if (str == null || str.equals("")) {
            reverseGeoCodeResult.error = ERRORNO.RESULT_NOT_FOUND;
        } else {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("SDK_InnerError")) {
                    jSONObject = jSONObject.optJSONObject("SDK_InnerError");
                    if (jSONObject.has("PermissionCheckError")) {
                        reverseGeoCodeResult.error = ERRORNO.PERMISSION_UNFINISHED;
                    } else if (jSONObject.has("httpStateError")) {
                        String optString = jSONObject.optString("httpStateError");
                        if (optString.equals("NETWORK_ERROR")) {
                            reverseGeoCodeResult.error = ERRORNO.NETWORK_ERROR;
                        } else if (optString.equals("REQUEST_ERROR")) {
                            reverseGeoCodeResult.error = ERRORNO.REQUEST_ERROR;
                        } else {
                            reverseGeoCodeResult.error = ERRORNO.SEARCH_SERVER_INTERNAL_ERROR;
                        }
                    }
                }
                if (!a(str, reverseGeoCodeResult, true)) {
                    a(str, reverseGeoCodeResult);
                }
            } catch (Exception e) {
                reverseGeoCodeResult.error = ERRORNO.RESULT_NOT_FOUND;
            }
        }
        return reverseGeoCodeResult;
    }

    public void a(SearchResult searchResult, Object obj) {
        if (obj != null && (obj instanceof OnGetGeoCoderResultListener)) {
            ((OnGetGeoCoderResultListener) obj).onGetReverseGeoCodeResult((ReverseGeoCodeResult) searchResult);
        }
    }
}
