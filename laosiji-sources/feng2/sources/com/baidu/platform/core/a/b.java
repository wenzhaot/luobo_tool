package com.baidu.platform.core.a;

import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;
import com.baidu.platform.base.d;
import com.facebook.common.util.UriUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b extends d {
    boolean b = false;
    String c = null;

    private boolean a(String str, DistrictResult districtResult) {
        if (str == null || "".equals(str) || districtResult == null) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject == null) {
                return false;
            }
            JSONObject optJSONObject = jSONObject.optJSONObject("result");
            jSONObject = jSONObject.optJSONObject("city_result");
            if (optJSONObject == null || jSONObject == null) {
                return false;
            }
            if (optJSONObject.optInt("error") != 0) {
                return false;
            }
            JSONObject optJSONObject2 = jSONObject.optJSONObject(UriUtil.LOCAL_CONTENT_SCHEME);
            if (optJSONObject2 == null) {
                return false;
            }
            jSONObject = optJSONObject2.optJSONObject("sgeo");
            if (jSONObject != null) {
                JSONArray optJSONArray = jSONObject.optJSONArray("geo_elements");
                if (optJSONArray != null && optJSONArray.length() > 0) {
                    List arrayList = new ArrayList();
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        optJSONObject = optJSONArray.optJSONObject(i);
                        if (optJSONObject != null) {
                            JSONArray optJSONArray2 = optJSONObject.optJSONArray("point");
                            if (optJSONArray2 != null) {
                                int length = optJSONArray2.length();
                                if (length > 0) {
                                    List arrayList2 = new ArrayList();
                                    int i2 = 0;
                                    int i3 = 0;
                                    for (int i4 = 0; i4 < length; i4++) {
                                        int optInt = optJSONArray2.optInt(i4);
                                        if (i4 % 2 == 0) {
                                            i3 += optInt;
                                        } else {
                                            i2 += optInt;
                                            arrayList2.add(CoordUtil.mc2ll(new GeoPoint((double) i2, (double) i3)));
                                        }
                                    }
                                    arrayList.add(arrayList2);
                                }
                            }
                        }
                    }
                    if (arrayList.size() > 0) {
                        districtResult.setPolylines(arrayList);
                        districtResult.setCenterPt(CoordUtil.decodeLocation(optJSONObject2.optString("geo")));
                        districtResult.setCityCode(optJSONObject2.optInt("code"));
                        districtResult.setCityName(optJSONObject2.optString("cname"));
                        districtResult.error = ERRORNO.NO_ERROR;
                        return true;
                    }
                }
            }
            districtResult.setCityName(optJSONObject2.optString("uid"));
            this.c = optJSONObject2.optString("cname");
            districtResult.setCenterPt(CoordUtil.decodeLocation(optJSONObject2.optString("geo")));
            districtResult.setCityCode(optJSONObject2.optInt("code"));
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0048  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0086  */
    private boolean b(java.lang.String r7, com.baidu.mapapi.search.district.DistrictResult r8) {
        /*
        r6 = this;
        r1 = 0;
        r0 = 0;
        if (r7 == 0) goto L_0x000f;
    L_0x0004:
        r2 = "";
        r2 = r7.equals(r2);
        if (r2 != 0) goto L_0x000f;
    L_0x000d:
        if (r8 != 0) goto L_0x0010;
    L_0x000f:
        return r0;
    L_0x0010:
        r2 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x0071 }
        r2.<init>(r7);	 Catch:{ JSONException -> 0x0071 }
        if (r2 == 0) goto L_0x000f;
    L_0x0017:
        r3 = "result";
        r3 = r2.optJSONObject(r3);
        r4 = "content";
        r2 = r2.optJSONObject(r4);
        if (r3 == 0) goto L_0x000f;
    L_0x0027:
        if (r2 == 0) goto L_0x000f;
    L_0x0029:
        r4 = "error";
        r3 = r3.optInt(r4);
        if (r3 != 0) goto L_0x000f;
    L_0x0032:
        r3 = new java.util.ArrayList;
        r3.<init>();
        r0 = r6.c;
        if (r0 == 0) goto L_0x007a;
    L_0x003b:
        r0 = "geo";
        r0 = r2.optString(r0);	 Catch:{ Exception -> 0x0076 }
        r0 = com.baidu.mapapi.model.CoordUtil.decodeLocationList2D(r0);	 Catch:{ Exception -> 0x0076 }
    L_0x0046:
        if (r0 == 0) goto L_0x0080;
    L_0x0048:
        r2 = r0.iterator();
    L_0x004c:
        r0 = r2.hasNext();
        if (r0 == 0) goto L_0x0080;
    L_0x0052:
        r0 = r2.next();
        r0 = (java.util.List) r0;
        r4 = new java.util.ArrayList;
        r4.<init>();
        r5 = r0.iterator();
    L_0x0061:
        r0 = r5.hasNext();
        if (r0 == 0) goto L_0x007c;
    L_0x0067:
        r0 = r5.next();
        r0 = (com.baidu.mapapi.model.LatLng) r0;
        r4.add(r0);
        goto L_0x0061;
    L_0x0071:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x000f;
    L_0x0076:
        r0 = move-exception;
        r0.printStackTrace();
    L_0x007a:
        r0 = r1;
        goto L_0x0046;
    L_0x007c:
        r3.add(r4);
        goto L_0x004c;
    L_0x0080:
        r0 = r3.size();
        if (r0 <= 0) goto L_0x0089;
    L_0x0086:
        r8.setPolylines(r3);
    L_0x0089:
        r0 = r6.c;
        r8.setCityName(r0);
        r0 = com.baidu.mapapi.search.core.SearchResult.ERRORNO.NO_ERROR;
        r8.error = r0;
        r6.c = r1;
        r0 = 1;
        goto L_0x000f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.core.a.b.b(java.lang.String, com.baidu.mapapi.search.district.DistrictResult):boolean");
    }

    public SearchResult a(String str) {
        DistrictResult districtResult = new DistrictResult();
        if (str == null || str.equals("")) {
            districtResult.error = ERRORNO.RESULT_NOT_FOUND;
        } else {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("SDK_InnerError")) {
                    jSONObject = jSONObject.optJSONObject("SDK_InnerError");
                    if (jSONObject.has("PermissionCheckError")) {
                        districtResult.error = ERRORNO.PERMISSION_UNFINISHED;
                    } else if (jSONObject.has("httpStateError")) {
                        String optString = jSONObject.optString("httpStateError");
                        if (optString.equals("NETWORK_ERROR")) {
                            districtResult.error = ERRORNO.NETWORK_ERROR;
                        } else if (optString.equals("REQUEST_ERROR")) {
                            districtResult.error = ERRORNO.REQUEST_ERROR;
                        } else {
                            districtResult.error = ERRORNO.SEARCH_SERVER_INTERNAL_ERROR;
                        }
                    }
                }
                if (!a(str, districtResult, false)) {
                    if (this.b) {
                        b(str, districtResult);
                    } else if (!a(str, districtResult)) {
                        districtResult.error = ERRORNO.RESULT_NOT_FOUND;
                    }
                }
            } catch (Exception e) {
                districtResult.error = ERRORNO.RESULT_NOT_FOUND;
            }
        }
        return districtResult;
    }

    public void a(SearchResult searchResult, Object obj) {
        if (obj != null && (obj instanceof OnGetDistricSearchResultListener)) {
            ((OnGetDistricSearchResultListener) obj).onGetDistrictResult((DistrictResult) searchResult);
        }
    }

    public void a(boolean z) {
        this.b = z;
    }
}
