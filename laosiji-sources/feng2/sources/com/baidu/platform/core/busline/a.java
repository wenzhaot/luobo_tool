package com.baidu.platform.core.busline;

import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineResult.BusStation;
import com.baidu.mapapi.search.busline.BusLineResult.BusStep;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.platform.base.d;
import com.facebook.common.util.UriUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a extends d {
    public SearchResult a(String str) {
        BusLineResult busLineResult = new BusLineResult();
        if (str == null || str.equals("")) {
            busLineResult.error = ERRORNO.RESULT_NOT_FOUND;
        } else {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("SDK_InnerError")) {
                    jSONObject = jSONObject.optJSONObject("SDK_InnerError");
                    if (jSONObject.has("PermissionCheckError")) {
                        busLineResult.error = ERRORNO.PERMISSION_UNFINISHED;
                    } else if (jSONObject.has("httpStateError")) {
                        String optString = jSONObject.optString("httpStateError");
                        if (optString.equals("NETWORK_ERROR")) {
                            busLineResult.error = ERRORNO.NETWORK_ERROR;
                        } else if (optString.equals("REQUEST_ERROR")) {
                            busLineResult.error = ERRORNO.REQUEST_ERROR;
                        } else {
                            busLineResult.error = ERRORNO.SEARCH_SERVER_INTERNAL_ERROR;
                        }
                    }
                }
                if (!(a(str, busLineResult, false) || a(str, busLineResult))) {
                    busLineResult.error = ERRORNO.RESULT_NOT_FOUND;
                }
            } catch (Exception e) {
                busLineResult.error = ERRORNO.RESULT_NOT_FOUND;
            }
        }
        return busLineResult;
    }

    public void a(SearchResult searchResult, Object obj) {
        if (obj != null && (obj instanceof OnGetBusLineSearchResultListener)) {
            ((OnGetBusLineSearchResultListener) obj).onGetBusLineResult((BusLineResult) searchResult);
        }
    }

    public boolean a(String str, BusLineResult busLineResult) {
        int i = 0;
        if (str == null || "".equals(str)) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject optJSONObject = jSONObject.optJSONObject("result");
            JSONArray optJSONArray = jSONObject.optJSONArray(UriUtil.LOCAL_CONTENT_SCHEME);
            if (optJSONObject == null || optJSONArray == null || optJSONArray.length() <= 0) {
                return false;
            }
            optJSONObject = optJSONArray.optJSONObject(0);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            try {
                busLineResult.setStartTime(simpleDateFormat.parse(optJSONObject.optString("startTime")));
                busLineResult.setEndTime(simpleDateFormat.parse(optJSONObject.optString("endTime")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            busLineResult.setBusLineName(optJSONObject.optString("name"));
            busLineResult.setMonthTicket(optJSONObject.optInt("isMonTicket") == 1);
            busLineResult.setUid(optJSONObject.optString("uid"));
            busLineResult.setBasePrice(((float) optJSONObject.optInt("ticketPrice")) / 100.0f);
            busLineResult.setLineDirection(optJSONObject.optString("line_direction"));
            busLineResult.setMaxPrice(((float) optJSONObject.optInt("maxPrice")) / 100.0f);
            List arrayList = new ArrayList();
            List<List> decodeLocationList2D = CoordUtil.decodeLocationList2D(optJSONObject.optString("geo"));
            if (decodeLocationList2D != null) {
                for (List list : decodeLocationList2D) {
                    BusStep busStep = new BusStep();
                    busStep.setWayPoints(list);
                    arrayList.add(busStep);
                }
            }
            if (arrayList.size() > 0) {
                busLineResult.setSteps(arrayList);
            }
            optJSONArray = optJSONObject.optJSONArray("stations");
            if (optJSONArray != null) {
                List arrayList2 = new ArrayList();
                while (i < optJSONArray.length()) {
                    JSONObject optJSONObject2 = optJSONArray.optJSONObject(i);
                    if (optJSONObject2 != null) {
                        BusStation busStation = new BusStation();
                        busStation.setTitle(optJSONObject2.optString("name"));
                        busStation.setLocation(CoordUtil.decodeLocation(optJSONObject2.optString("geo")));
                        busStation.setUid(optJSONObject2.optString("uid"));
                        arrayList2.add(busStation);
                    }
                    i++;
                }
                if (arrayList2.size() > 0) {
                    busLineResult.setStations(arrayList2);
                }
            }
            busLineResult.error = ERRORNO.NO_ERROR;
            return true;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return false;
        }
    }
}
