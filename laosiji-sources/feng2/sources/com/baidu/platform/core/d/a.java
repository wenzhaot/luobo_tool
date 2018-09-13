package com.baidu.platform.core.d;

import anet.channel.strategy.dispatch.DispatchConstants;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.mapapi.search.route.BikingRouteLine;
import com.baidu.mapapi.search.route.BikingRouteLine.BikingStep;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.SuggestAddrInfo;
import com.baidu.platform.base.d;
import com.baidu.platform.comapi.util.CoordTrans;
import com.facebook.common.util.UriUtil;
import com.feng.car.activity.CameraPreviewActivity;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a extends d {
    private LatLng a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        LatLng latLng = new LatLng(jSONObject.optDouble(DispatchConstants.LATITUDE), jSONObject.optDouble(DispatchConstants.LONGTITUDE));
        return SDKInitializer.getCoordType() == CoordType.GCJ02 ? CoordTrans.baiduToGcj(latLng) : latLng;
    }

    private RouteNode a(JSONObject jSONObject, String str, String str2) {
        if (jSONObject == null || str == null || "".equals(str)) {
            return null;
        }
        JSONObject optJSONObject = jSONObject.optJSONObject(str);
        if (optJSONObject == null) {
            return null;
        }
        RouteNode routeNode = new RouteNode();
        routeNode.setTitle(optJSONObject.optString("cname"));
        routeNode.setUid(optJSONObject.optString("uid"));
        optJSONObject = optJSONObject.optJSONObject(str2);
        if (optJSONObject != null) {
            LatLng latLng = new LatLng(optJSONObject.optDouble(DispatchConstants.LATITUDE), optJSONObject.optDouble(DispatchConstants.LONGTITUDE));
            if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
                latLng = CoordTrans.baiduToGcj(latLng);
            }
            routeNode.setLocation(latLng);
        }
        return routeNode;
    }

    private List<BikingStep> a(JSONArray jSONArray) {
        int i = 1;
        int i2 = 0;
        int i3 = jSONArray == null ? 1 : 0;
        int length = jSONArray.length();
        if (length > 0) {
            i = 0;
        }
        if ((i3 | i) != 0) {
            return null;
        }
        List<BikingStep> arrayList = new ArrayList();
        while (i2 < length) {
            JSONObject optJSONObject = jSONArray.optJSONObject(i2);
            if (optJSONObject != null) {
                BikingStep bikingStep = new BikingStep();
                bikingStep.setDirection(optJSONObject.optInt("direction") * 30);
                bikingStep.setDistance(optJSONObject.optInt("distance"));
                bikingStep.setDuration(optJSONObject.optInt("duration"));
                bikingStep.setName(optJSONObject.optString("name"));
                bikingStep.setTurnType(optJSONObject.optString("turn_type"));
                bikingStep.setEntrance(RouteNode.location(a(optJSONObject.optJSONObject("stepOriginLocation"))));
                bikingStep.setExit(RouteNode.location(a(optJSONObject.optJSONObject("stepDestinationLocation"))));
                String optString = optJSONObject.optString("instructions");
                if (optString != null || optString.length() >= 4) {
                    optString = optString.replaceAll("</?[a-z]>", "");
                }
                bikingStep.setInstructions(optString);
                bikingStep.setEntranceInstructions(optJSONObject.optString("stepOriginInstruction"));
                bikingStep.setExitInstructions(optJSONObject.optString("stepDestinationInstruction"));
                bikingStep.setPathString(optJSONObject.optString(CameraPreviewActivity.PATH));
                arrayList.add(bikingStep);
            }
            i2++;
        }
        return arrayList.size() > 0 ? arrayList : null;
    }

    private boolean a(String str, BikingRouteResult bikingRouteResult) {
        if (str == null || str.length() <= 0) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject == null) {
                return false;
            }
            switch (jSONObject.optInt("status_sdk")) {
                case 0:
                    JSONObject optJSONObject = jSONObject.optJSONObject("result");
                    if (optJSONObject == null) {
                        return false;
                    }
                    int optInt = jSONObject.optInt("type");
                    if (optInt == 1) {
                        bikingRouteResult.setSuggestAddrInfo(b(optJSONObject));
                        bikingRouteResult.error = ERRORNO.AMBIGUOUS_ROURE_ADDR;
                    } else if (optInt != 2) {
                        return false;
                    } else {
                        JSONArray optJSONArray = optJSONObject.optJSONArray("routes");
                        if (optJSONArray == null || optJSONArray.length() <= 0) {
                            return false;
                        }
                        RouteNode a = a(optJSONObject, "origin", "originPt");
                        RouteNode a2 = a(optJSONObject, "destination", "destinationPt");
                        List arrayList = new ArrayList();
                        optInt = 0;
                        while (optInt < optJSONArray.length()) {
                            BikingRouteLine bikingRouteLine = new BikingRouteLine();
                            try {
                                JSONObject optJSONObject2 = optJSONArray.optJSONObject(optInt);
                                if (optJSONObject2 == null) {
                                    return false;
                                }
                                bikingRouteLine.setStarting(a);
                                bikingRouteLine.setTerminal(a2);
                                bikingRouteLine.setDistance(optJSONObject2.optInt("distance"));
                                bikingRouteLine.setDuration(optJSONObject2.optInt("duration"));
                                bikingRouteLine.setSteps(a(optJSONObject2.optJSONArray("steps")));
                                arrayList.add(bikingRouteLine);
                                optInt++;
                            } catch (Exception e) {
                            }
                        }
                        bikingRouteResult.setRouteLines(arrayList);
                    }
                    return true;
                case 1:
                    bikingRouteResult.error = ERRORNO.SEARCH_SERVER_INTERNAL_ERROR;
                    return true;
                case 2:
                    bikingRouteResult.error = ERRORNO.SEARCH_OPTION_ERROR;
                    return false;
                default:
                    return false;
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private SuggestAddrInfo b(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        SuggestAddrInfo suggestAddrInfo = new SuggestAddrInfo();
        JSONObject optJSONObject = jSONObject.optJSONObject("origin");
        JSONObject optJSONObject2 = jSONObject.optJSONObject("destination");
        if (optJSONObject != null) {
            int optInt = optJSONObject.optInt("listType");
            String optString = optJSONObject.optString("cityName");
            if (optInt == 1) {
                suggestAddrInfo.setSuggestStartCity(a(optJSONObject, UriUtil.LOCAL_CONTENT_SCHEME));
            } else if (optInt == 0) {
                suggestAddrInfo.setSuggestStartNode(b(optJSONObject, UriUtil.LOCAL_CONTENT_SCHEME, optString));
            }
        }
        if (optJSONObject2 == null) {
            return suggestAddrInfo;
        }
        int optInt2 = optJSONObject2.optInt("listType");
        String optString2 = optJSONObject2.optString("cityName");
        if (optInt2 == 1) {
            suggestAddrInfo.setSuggestEndCity(a(optJSONObject2, UriUtil.LOCAL_CONTENT_SCHEME));
            return suggestAddrInfo;
        } else if (optInt2 != 0) {
            return suggestAddrInfo;
        } else {
            suggestAddrInfo.setSuggestEndNode(b(optJSONObject2, UriUtil.LOCAL_CONTENT_SCHEME, optString2));
            return suggestAddrInfo;
        }
    }

    private List<PoiInfo> b(JSONObject jSONObject, String str, String str2) {
        if (jSONObject == null || str == null || "".equals(str)) {
            return null;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray(str);
        if (optJSONArray != null) {
            List<PoiInfo> arrayList = new ArrayList();
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= optJSONArray.length()) {
                    break;
                }
                JSONObject jSONObject2 = (JSONObject) optJSONArray.opt(i2);
                if (jSONObject2 != null) {
                    PoiInfo poiInfo = new PoiInfo();
                    if (jSONObject2.has("address")) {
                        poiInfo.address = jSONObject2.optString("address");
                    }
                    poiInfo.uid = jSONObject2.optString("uid");
                    poiInfo.name = jSONObject2.optString("name");
                    jSONObject2 = jSONObject2.optJSONObject("location");
                    if (jSONObject2 != null) {
                        poiInfo.location = new LatLng(jSONObject2.optDouble(DispatchConstants.LATITUDE), jSONObject2.optDouble(DispatchConstants.LONGTITUDE));
                        if (SDKInitializer.getCoordType() == CoordType.GCJ02) {
                            poiInfo.location = CoordTrans.baiduToGcj(poiInfo.location);
                        }
                    }
                    poiInfo.city = str2;
                    arrayList.add(poiInfo);
                }
                i = i2 + 1;
            }
            if (arrayList.size() > 0) {
                return arrayList;
            }
        }
        return null;
    }

    public SearchResult a(String str) {
        BikingRouteResult bikingRouteResult = new BikingRouteResult();
        if (str == null || str.equals("")) {
            bikingRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
        } else {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("SDK_InnerError")) {
                    jSONObject = jSONObject.optJSONObject("SDK_InnerError");
                    if (jSONObject.has("PermissionCheckError")) {
                        bikingRouteResult.error = ERRORNO.PERMISSION_UNFINISHED;
                    } else if (jSONObject.has("httpStateError")) {
                        String optString = jSONObject.optString("httpStateError");
                        if (optString.equals("NETWORK_ERROR")) {
                            bikingRouteResult.error = ERRORNO.NETWORK_ERROR;
                        } else if (optString.equals("REQUEST_ERROR")) {
                            bikingRouteResult.error = ERRORNO.REQUEST_ERROR;
                        } else {
                            bikingRouteResult.error = ERRORNO.SEARCH_SERVER_INTERNAL_ERROR;
                        }
                    }
                }
                if (!(a(str, bikingRouteResult, false) || a(str, bikingRouteResult))) {
                    bikingRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
                }
            } catch (Exception e) {
                bikingRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
            }
        }
        return bikingRouteResult;
    }

    public List<CityInfo> a(JSONObject jSONObject, String str) {
        if (jSONObject == null || str == null || str.equals("")) {
            return null;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray(str);
        if (optJSONArray == null || optJSONArray.length() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < optJSONArray.length()) {
                JSONObject jSONObject2 = (JSONObject) optJSONArray.opt(i2);
                if (jSONObject2 != null) {
                    CityInfo cityInfo = new CityInfo();
                    cityInfo.num = jSONObject2.optInt("number");
                    cityInfo.city = jSONObject2.optString("name");
                    arrayList.add(cityInfo);
                }
                i = i2 + 1;
            } else {
                arrayList.trimToSize();
                return arrayList;
            }
        }
    }

    public void a(SearchResult searchResult, Object obj) {
        if (obj != null && (obj instanceof OnGetRoutePlanResultListener)) {
            ((OnGetRoutePlanResultListener) obj).onGetBikingRouteResult((BikingRouteResult) searchResult);
        }
    }
}
