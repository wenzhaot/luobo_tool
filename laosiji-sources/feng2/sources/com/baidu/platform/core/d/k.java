package com.baidu.platform.core.d;

import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.SuggestAddrInfo;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.platform.base.SearchType;
import com.baidu.platform.base.d;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class k extends d {
    SuggestAddrInfo b = null;
    protected boolean c;

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0090  */
    private com.baidu.mapapi.search.route.SuggestAddrInfo a(org.json.JSONObject r14) {
        /*
        r13 = this;
        r3 = 1;
        r4 = 0;
        r2 = 0;
        if (r14 != 0) goto L_0x0006;
    L_0x0005:
        return r2;
    L_0x0006:
        r0 = "traffic_pois";
        r0 = r14.optJSONObject(r0);
        if (r0 == 0) goto L_0x0005;
    L_0x000f:
        r1 = "option";
        r5 = r0.optJSONObject(r1);
        r1 = "content";
        r6 = r0.optJSONObject(r1);
        if (r5 == 0) goto L_0x0005;
    L_0x001f:
        if (r6 == 0) goto L_0x0005;
    L_0x0021:
        r0 = "start_city";
        r0 = r5.optJSONObject(r0);
        if (r0 == 0) goto L_0x00ff;
    L_0x002a:
        r1 = "cname";
        r0 = r0.optString(r1);
        r1 = r0;
    L_0x0032:
        r0 = "end_city";
        r0 = r5.optJSONArray(r0);
        if (r0 == 0) goto L_0x00fc;
    L_0x003b:
        r0 = r0.opt(r4);
        r0 = (org.json.JSONObject) r0;
        if (r0 == 0) goto L_0x00fc;
    L_0x0043:
        r7 = "cname";
        r0 = r0.optString(r7);
    L_0x004a:
        r7 = "city_list";
        r7 = r5.optJSONArray(r7);
        r8 = "prio_flag";
        r8 = r5.optJSONArray(r8);
        if (r7 == 0) goto L_0x0005;
    L_0x005a:
        if (r8 == 0) goto L_0x0005;
    L_0x005c:
        r9 = r7.length();
        r10 = new boolean[r9];
        r11 = new boolean[r9];
        r5 = r4;
    L_0x0065:
        if (r5 >= r9) goto L_0x0089;
    L_0x0067:
        r2 = r7.optString(r5);
        r2 = java.lang.Integer.parseInt(r2);
        r12 = r8.optString(r5);
        r12 = java.lang.Integer.parseInt(r12);
        if (r2 != r3) goto L_0x0085;
    L_0x0079:
        r2 = r3;
    L_0x007a:
        r10[r5] = r2;
        if (r12 != r3) goto L_0x0087;
    L_0x007e:
        r2 = r3;
    L_0x007f:
        r11[r5] = r2;
        r2 = r5 + 1;
        r5 = r2;
        goto L_0x0065;
    L_0x0085:
        r2 = r4;
        goto L_0x007a;
    L_0x0087:
        r2 = r4;
        goto L_0x007f;
    L_0x0089:
        r2 = new com.baidu.mapapi.search.route.SuggestAddrInfo;
        r2.<init>();
    L_0x008e:
        if (r4 >= r9) goto L_0x0005;
    L_0x0090:
        r3 = r11[r4];
        if (r3 == 0) goto L_0x00a8;
    L_0x0094:
        r3 = r10[r4];
        if (r3 == 0) goto L_0x00cb;
    L_0x0098:
        if (r4 != 0) goto L_0x00ab;
    L_0x009a:
        r3 = "start";
        r3 = r6.optJSONArray(r3);
        r3 = r13.a(r3);
        r2.setSuggestStartCity(r3);
    L_0x00a8:
        r4 = r4 + 1;
        goto L_0x008e;
    L_0x00ab:
        r3 = r9 + -1;
        if (r4 != r3) goto L_0x00c0;
    L_0x00af:
        if (r4 <= 0) goto L_0x00c0;
    L_0x00b1:
        r3 = "end";
        r3 = r6.optJSONArray(r3);
        r3 = r13.a(r3);
        r2.setSuggestEndCity(r3);
        goto L_0x00a8;
    L_0x00c0:
        r3 = "multi_waypoints";
        r3 = r13.a(r6, r3);
        r2.setSuggestWpCity(r3);
        goto L_0x00a8;
    L_0x00cb:
        if (r4 != 0) goto L_0x00dc;
    L_0x00cd:
        r3 = "start";
        r3 = r6.optJSONArray(r3);
        r3 = r13.a(r3, r1);
        r2.setSuggestStartNode(r3);
        goto L_0x00a8;
    L_0x00dc:
        r3 = r9 + -1;
        if (r4 != r3) goto L_0x00f1;
    L_0x00e0:
        if (r4 <= 0) goto L_0x00f1;
    L_0x00e2:
        r3 = "end";
        r3 = r6.optJSONArray(r3);
        r3 = r13.a(r3, r0);
        r2.setSuggestEndNode(r3);
        goto L_0x00a8;
    L_0x00f1:
        r3 = "multi_waypoints";
        r3 = r13.b(r6, r3);
        r2.setSuggestWpNode(r3);
        goto L_0x00a8;
    L_0x00fc:
        r0 = r2;
        goto L_0x004a;
    L_0x00ff:
        r1 = r2;
        goto L_0x0032;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.core.d.k.a(org.json.JSONObject):com.baidu.mapapi.search.route.SuggestAddrInfo");
    }

    private List<CityInfo> a(JSONArray jSONArray) {
        if (jSONArray == null || jSONArray.length() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < jSONArray.length()) {
                JSONObject jSONObject = (JSONObject) jSONArray.opt(i2);
                if (jSONObject != null) {
                    CityInfo cityInfo = new CityInfo();
                    cityInfo.num = jSONObject.optInt("num");
                    cityInfo.city = jSONObject.optString("name");
                    arrayList.add(cityInfo);
                }
                i = i2 + 1;
            } else {
                arrayList.trimToSize();
                return arrayList;
            }
        }
    }

    private List<PoiInfo> a(JSONArray jSONArray, String str) {
        if (jSONArray != null) {
            List<PoiInfo> arrayList = new ArrayList();
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= jSONArray.length()) {
                    break;
                }
                JSONObject jSONObject = (JSONObject) jSONArray.opt(i2);
                if (jSONObject != null) {
                    PoiInfo poiInfo = new PoiInfo();
                    poiInfo.address = jSONObject.optString("addr");
                    poiInfo.uid = jSONObject.optString("uid");
                    poiInfo.name = jSONObject.optString("name");
                    poiInfo.location = CoordUtil.decodeLocation(jSONObject.optString("geo"));
                    poiInfo.city = str;
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

    private List<List<CityInfo>> a(JSONObject jSONObject, String str) {
        List<List<CityInfo>> arrayList = new ArrayList();
        if (jSONObject == null) {
            return null;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray(str);
        if (optJSONArray == null) {
            return null;
        }
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= optJSONArray.length()) {
                return arrayList;
            }
            List a = a((JSONArray) optJSONArray.opt(i2));
            if (a != null) {
                arrayList.add(a);
            }
            i = i2 + 1;
        }
    }

    private List<List<PoiInfo>> b(JSONObject jSONObject, String str) {
        List<List<PoiInfo>> arrayList = new ArrayList();
        if (jSONObject == null) {
            return null;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray(str);
        if (optJSONArray == null) {
            return null;
        }
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= optJSONArray.length()) {
                return arrayList;
            }
            List a = a((JSONArray) optJSONArray.opt(i2), "");
            if (a != null) {
                arrayList.add(a);
            }
            i = i2 + 1;
        }
    }

    private boolean b(String str) {
        if (str == null || str.length() <= 0) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject == null) {
                return false;
            }
            JSONObject optJSONObject = jSONObject.optJSONObject("result");
            if (optJSONObject == null || optJSONObject.optInt("type") != 23 || optJSONObject.optInt("error") != 0) {
                return false;
            }
            this.b = a(jSONObject);
            return this.b != null;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public SearchResult a(String str) {
        SearchType a = a();
        if (b(str)) {
            this.c = true;
        } else {
            this.c = false;
        }
        switch (a) {
            case TRANSIT_ROUTE:
                TransitRouteResult transitRouteResult = new TransitRouteResult();
                if (this.c) {
                    transitRouteResult.setSuggestAddrInfo(this.b);
                    transitRouteResult.error = ERRORNO.AMBIGUOUS_ROURE_ADDR;
                    return transitRouteResult;
                }
                ((l) this).a(str, transitRouteResult);
                return transitRouteResult;
            case DRIVE_ROUTE:
                DrivingRouteResult drivingRouteResult = new DrivingRouteResult();
                if (this.c) {
                    drivingRouteResult.setSuggestAddrInfo(this.b);
                    drivingRouteResult.error = ERRORNO.AMBIGUOUS_ROURE_ADDR;
                    return drivingRouteResult;
                }
                ((c) this).a(str, drivingRouteResult);
                return drivingRouteResult;
            case WALK_ROUTE:
                WalkingRouteResult walkingRouteResult = new WalkingRouteResult();
                if (this.c) {
                    walkingRouteResult.setSuggestAddrInfo(this.b);
                    walkingRouteResult.error = ERRORNO.AMBIGUOUS_ROURE_ADDR;
                    return walkingRouteResult;
                }
                ((n) this).a(str, walkingRouteResult);
                return walkingRouteResult;
            default:
                return null;
        }
    }

    public void a(SearchResult searchResult, Object obj) {
        if (obj != null && (obj instanceof OnGetRoutePlanResultListener)) {
            OnGetRoutePlanResultListener onGetRoutePlanResultListener = (OnGetRoutePlanResultListener) obj;
            switch (a()) {
                case TRANSIT_ROUTE:
                    onGetRoutePlanResultListener.onGetTransitRouteResult((TransitRouteResult) searchResult);
                    return;
                case DRIVE_ROUTE:
                    onGetRoutePlanResultListener.onGetDrivingRouteResult((DrivingRouteResult) searchResult);
                    return;
                case WALK_ROUTE:
                    onGetRoutePlanResultListener.onGetWalkingRouteResult((WalkingRouteResult) searchResult);
                    return;
                default:
                    return;
            }
        }
    }
}
