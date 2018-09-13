package com.baidu.platform.core.d;

import android.support.v4.app.NotificationCompat;
import com.baidu.mapapi.common.Logger;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.mapapi.search.core.TaxiInfo;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRouteLine.DrivingStep;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.facebook.common.util.UriUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c extends k {
    private RouteNode a(JSONArray jSONArray, List<RouteNode> list) {
        if (jSONArray != null) {
            int length = jSONArray.length();
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    JSONObject optJSONObject = jSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        RouteNode a = a(optJSONObject);
                        if (i == length - 1) {
                            return a;
                        }
                        list.add(a);
                    }
                }
                return null;
            }
        }
        return null;
    }

    private RouteNode a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        RouteNode routeNode = new RouteNode();
        routeNode.setTitle(jSONObject.optString("wd"));
        routeNode.setUid(jSONObject.optString("uid"));
        GeoPoint geoPoint = new GeoPoint(0.0d, 0.0d);
        JSONArray optJSONArray = jSONObject.optJSONArray("spt");
        if (optJSONArray != null) {
            geoPoint.setLongitudeE6((double) optJSONArray.optInt(0));
            geoPoint.setLatitudeE6((double) optJSONArray.optInt(1));
        }
        routeNode.setLocation(CoordUtil.mc2ll(geoPoint));
        return routeNode;
    }

    private List<LatLng> a(JSONArray jSONArray) {
        double d = 0.0d;
        if (jSONArray != null) {
            int length = jSONArray.length();
            if (length >= 6) {
                List<LatLng> arrayList = new ArrayList();
                double d2 = 0.0d;
                for (int i = 5; i < length; i++) {
                    if (i % 2 != 0) {
                        d += (double) jSONArray.optInt(i);
                    } else {
                        d2 += (double) jSONArray.optInt(i);
                        arrayList.add(CoordUtil.mc2ll(new GeoPoint(d2, d)));
                    }
                }
                return arrayList;
            }
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0024  */
    private java.util.List<com.baidu.mapapi.search.route.DrivingRouteLine.DrivingStep> a(org.json.JSONArray r17, org.json.JSONArray r18) {
        /*
        r16 = this;
        if (r17 == 0) goto L_0x0008;
    L_0x0002:
        r9 = r17.length();
        if (r9 > 0) goto L_0x000a;
    L_0x0008:
        r2 = 0;
    L_0x0009:
        return r2;
    L_0x000a:
        r3 = 1;
        r2 = 0;
        if (r18 == 0) goto L_0x0014;
    L_0x000e:
        r2 = r18.length();
        if (r2 > 0) goto L_0x0162;
    L_0x0014:
        r3 = 0;
        r4 = r3;
        r3 = r2;
    L_0x0017:
        r7 = new java.util.ArrayList;
        r7.<init>();
        r6 = 0;
        r2 = "";
        r2 = 0;
        r8 = r2;
    L_0x0022:
        if (r8 >= r9) goto L_0x015f;
    L_0x0024:
        r0 = r17;
        r10 = r0.optJSONObject(r8);
        if (r10 != 0) goto L_0x0032;
    L_0x002c:
        r2 = r6;
    L_0x002d:
        r5 = r8 + 1;
        r8 = r5;
        r6 = r2;
        goto L_0x0022;
    L_0x0032:
        r11 = new com.baidu.mapapi.search.route.DrivingRouteLine$DrivingStep;
        r11.<init>();
        r2 = "distance";
        r2 = r10.optInt(r2);
        r11.setDistance(r2);
        r2 = "direction";
        r2 = r10.optInt(r2);
        r2 = r2 * 30;
        r11.setDirection(r2);
        r2 = "instructions";
        r2 = r10.optString(r2);
        if (r2 != 0) goto L_0x005d;
    L_0x0056:
        r5 = r2.length();
        r12 = 4;
        if (r5 < r12) goto L_0x007b;
    L_0x005d:
        r5 = "/?[a-zA-Z]{1,10};";
        r12 = "";
        r2 = r2.replaceAll(r5, r12);
        r5 = "<[^>]*>";
        r12 = "";
        r2 = r2.replaceAll(r5, r12);
        r5 = "[(/>)<]";
        r12 = "";
        r2 = r2.replaceAll(r5, r12);
    L_0x007b:
        r11.setInstructions(r2);
        r2 = "start_instructions";
        r5 = r10.optString(r2);
        if (r5 != 0) goto L_0x00ce;
    L_0x0087:
        r2 = r11.getDistance();
        r5 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        if (r2 >= r5) goto L_0x013a;
    L_0x008f:
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r12 = " - ";
        r5 = r5.append(r12);
        r2 = r5.append(r2);
        r5 = "米";
        r2 = r2.append(r5);
        r2 = r2.toString();
        r5 = r2;
    L_0x00ab:
        r2 = r7.size();
        if (r6 > r2) goto L_0x00ce;
    L_0x00b1:
        r12 = new java.lang.StringBuilder;
        r12.<init>();
        r2 = r6 + -1;
        r2 = r7.get(r2);
        r2 = (com.baidu.mapapi.search.route.DrivingRouteLine.DrivingStep) r2;
        r2 = r2.getExitInstructions();
        r2 = r12.append(r2);
        r2 = r2.append(r5);
        r5 = r2.toString();
    L_0x00ce:
        r11.setEntranceInstructions(r5);
        r2 = "end_instructions";
        r2 = r10.optString(r2);
        r11.setExitInstructions(r2);
        r2 = "turn";
        r2 = r10.optInt(r2);
        r11.setNumTurns(r2);
        r2 = "spath";
        r2 = r10.optJSONArray(r2);
        r0 = r16;
        r5 = r0.a(r2);
        r11.setPathList(r5);
        if (r5 == 0) goto L_0x0120;
    L_0x00f7:
        r10 = new com.baidu.mapapi.search.core.RouteNode;
        r10.<init>();
        r2 = 0;
        r2 = r5.get(r2);
        r2 = (com.baidu.mapapi.model.LatLng) r2;
        r10.setLocation(r2);
        r11.setEntrance(r10);
        r10 = new com.baidu.mapapi.search.core.RouteNode;
        r10.<init>();
        r2 = r5.size();
        r2 = r2 + -1;
        r2 = r5.get(r2);
        r2 = (com.baidu.mapapi.model.LatLng) r2;
        r10.setLocation(r2);
        r11.setExit(r10);
    L_0x0120:
        if (r4 == 0) goto L_0x0133;
    L_0x0122:
        if (r8 >= r3) goto L_0x0133;
    L_0x0124:
        r0 = r18;
        r2 = r0.optJSONObject(r8);
        r0 = r16;
        r2 = r0.b(r2);
        r11.setTrafficList(r2);
    L_0x0133:
        r2 = r6 + 1;
        r7.add(r11);
        goto L_0x002d;
    L_0x013a:
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r12 = " - ";
        r5 = r5.append(r12);
        r12 = (double) r2;
        r14 = 4652007308841189376; // 0x408f400000000000 float:0.0 double:1000.0;
        r12 = r12 / r14;
        r2 = r5.append(r12);
        r5 = "公里";
        r2 = r2.append(r5);
        r2 = r2.toString();
        r5 = r2;
        goto L_0x00ab;
    L_0x015f:
        r2 = r7;
        goto L_0x0009;
    L_0x0162:
        r4 = r3;
        r3 = r2;
        goto L_0x0017;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.core.d.c.a(org.json.JSONArray, org.json.JSONArray):java.util.List<com.baidu.mapapi.search.route.DrivingRouteLine$DrivingStep>");
    }

    private List<TaxiInfo> b(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        List<TaxiInfo> arrayList = new ArrayList();
        try {
            JSONArray jSONArray = new JSONArray(str);
            if (jSONArray == null) {
                return null;
            }
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (jSONObject != null) {
                    TaxiInfo taxiInfo = new TaxiInfo();
                    String optString = jSONObject.optString("total_price");
                    if (optString == null || optString.equals("")) {
                        taxiInfo.setTotalPrice(0.0f);
                    } else {
                        taxiInfo.setTotalPrice(Float.parseFloat(optString));
                    }
                    arrayList.add(taxiInfo);
                }
            }
            return arrayList;
        } catch (JSONException e) {
            if (!Logger.debugEnable()) {
                return null;
            }
            e.printStackTrace();
            return null;
        }
    }

    private List<DrivingStep> b(JSONArray jSONArray, List<DrivingStep> list) {
        if (jSONArray != null) {
            int length = jSONArray.length();
            if (length > 0 && list != null) {
                List<DrivingStep> arrayList = new ArrayList();
                for (int i = 0; i < length; i++) {
                    JSONObject optJSONObject = jSONArray.optJSONObject(i);
                    if (optJSONObject != null) {
                        int optInt = optJSONObject.optInt("n");
                        int optInt2 = optJSONObject.optInt("s");
                        for (int i2 = 0; i2 < optInt; i2++) {
                            if (optInt2 + i2 < list.size()) {
                                arrayList.add(list.get(optInt2 + i2));
                            }
                        }
                    }
                }
                return arrayList;
            }
        }
        return null;
    }

    private boolean b(String str, DrivingRouteResult drivingRouteResult) {
        if (str == null || "".equals(str)) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject == null) {
                return false;
            }
            JSONObject optJSONObject = jSONObject.optJSONObject("result");
            if (optJSONObject == null) {
                return false;
            }
            switch (optJSONObject.optInt("error")) {
                case 0:
                    JSONObject optJSONObject2 = jSONObject.optJSONObject("cars");
                    if (optJSONObject2 == null) {
                        return false;
                    }
                    jSONObject = optJSONObject2.optJSONObject("option");
                    optJSONObject = optJSONObject2.optJSONObject(UriUtil.LOCAL_CONTENT_SCHEME);
                    if (jSONObject == null || optJSONObject == null) {
                        return false;
                    }
                    RouteNode a = a(jSONObject.optJSONObject("start"));
                    List arrayList = new ArrayList();
                    RouteNode a2 = a(jSONObject.optJSONArray("end"), arrayList);
                    List a3 = a(optJSONObject.optJSONArray("steps"), optJSONObject.optJSONArray("stepts"));
                    List arrayList2 = new ArrayList();
                    JSONArray optJSONArray = optJSONObject.optJSONArray("routes");
                    if (optJSONArray == null) {
                        return false;
                    }
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        DrivingRouteLine drivingRouteLine = new DrivingRouteLine();
                        JSONObject optJSONObject3 = optJSONArray.optJSONObject(i);
                        if (optJSONObject3 != null) {
                            JSONArray optJSONArray2 = optJSONObject3.optJSONArray("legs");
                            if (optJSONArray2 == null) {
                                return false;
                            }
                            int length = optJSONArray2.length();
                            List arrayList3 = new ArrayList();
                            int i2 = 0;
                            int i3 = 0;
                            for (int i4 = 0; i4 < length; i4++) {
                                JSONObject optJSONObject4 = optJSONArray2.optJSONObject(i4);
                                if (optJSONObject4 != null) {
                                    i3 += optJSONObject4.optInt("distance");
                                    i2 += optJSONObject4.optInt("duration");
                                    Collection b = b(optJSONObject4.optJSONArray("stepis"), a3);
                                    if (b != null) {
                                        arrayList3.addAll(b);
                                    }
                                }
                            }
                            drivingRouteLine.setStarting(a);
                            drivingRouteLine.setTerminal(a2);
                            if (arrayList.size() == 0) {
                                drivingRouteLine.setWayPoints(null);
                            } else {
                                drivingRouteLine.setWayPoints(arrayList);
                            }
                            drivingRouteLine.setDistance(i3);
                            drivingRouteLine.setDuration(i2);
                            drivingRouteLine.setCongestionDistance(optJSONObject3.optInt("congestion_length"));
                            drivingRouteLine.setLightNum(optJSONObject3.optInt("light_num"));
                            if (arrayList3.size() == 0) {
                                drivingRouteLine.setSteps(null);
                            } else {
                                drivingRouteLine.setSteps(arrayList3);
                            }
                            arrayList2.add(drivingRouteLine);
                        }
                    }
                    drivingRouteResult.setRouteLines(arrayList2);
                    drivingRouteResult.setTaxiInfos(b(optJSONObject2.optString("taxis")));
                    return true;
                case 4:
                    drivingRouteResult.error = ERRORNO.ST_EN_TOO_NEAR;
                    return true;
                default:
                    return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int[] b(JSONObject jSONObject) {
        int i = 0;
        if (jSONObject == null) {
            return null;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("end");
        JSONArray optJSONArray2 = jSONObject.optJSONArray(NotificationCompat.CATEGORY_STATUS);
        if (optJSONArray == null || optJSONArray2 == null) {
            return null;
        }
        List arrayList = new ArrayList();
        int length = optJSONArray.length();
        int length2 = optJSONArray2.length();
        int i2 = 0;
        while (i2 < length) {
            int optInt = optJSONArray.optInt(i2);
            int optInt2 = i2 < length2 ? optJSONArray2.optInt(i2) : 0;
            for (int i3 = 0; i3 < optInt; i3++) {
                arrayList.add(Integer.valueOf(optInt2));
            }
            i2++;
        }
        int[] iArr = new int[arrayList.size()];
        while (i < arrayList.size()) {
            iArr[i] = ((Integer) arrayList.get(i)).intValue();
            i++;
        }
        return iArr;
    }

    public void a(String str, DrivingRouteResult drivingRouteResult) {
        if (str == null || str.equals("")) {
            drivingRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("SDK_InnerError")) {
                jSONObject = jSONObject.optJSONObject("SDK_InnerError");
                if (jSONObject.has("PermissionCheckError")) {
                    drivingRouteResult.error = ERRORNO.PERMISSION_UNFINISHED;
                    return;
                } else if (jSONObject.has("httpStateError")) {
                    String optString = jSONObject.optString("httpStateError");
                    if (optString.equals("NETWORK_ERROR")) {
                        drivingRouteResult.error = ERRORNO.NETWORK_ERROR;
                        return;
                    } else if (optString.equals("REQUEST_ERROR")) {
                        drivingRouteResult.error = ERRORNO.REQUEST_ERROR;
                        return;
                    } else {
                        drivingRouteResult.error = ERRORNO.SEARCH_SERVER_INTERNAL_ERROR;
                        return;
                    }
                }
            }
            if (!a(str, drivingRouteResult, false) && !b(str, drivingRouteResult)) {
                drivingRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
            }
        } catch (Exception e) {
            drivingRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
        }
    }
}
