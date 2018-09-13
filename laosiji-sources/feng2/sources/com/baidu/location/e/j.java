package com.baidu.location.e;

import android.database.Cursor;
import android.database.MatrixCursor;
import anet.channel.strategy.dispatch.DispatchConstants;
import anetwork.channel.util.RequestConstant;
import com.baidu.location.Address;
import com.baidu.location.Address.Builder;
import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.baidu.location.f.f;
import com.baidu.location.h.b;
import com.baidu.location.h.k;
import com.baidu.platform.comapi.location.CoordinateType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

final class j {
    private static final String[] a = new String[]{"CoorType", "Time", "LocType", "Longitude", "Latitude", "Radius", "NetworkLocationType", "Country", "CountryCode", "Province", "City", "CityCode", "District", "Street", "StreetNumber", "PoiList", "LocationDescription"};

    static final class a {
        final String a;
        final String b;
        final boolean c;
        final boolean d;
        final boolean e;
        final int f;
        final BDLocation g;
        final boolean h;
        final LinkedHashMap<String, Integer> i;

        public a(String[] strArr) {
            boolean z;
            if (strArr == null) {
                this.a = null;
                this.b = null;
                this.i = null;
                this.c = false;
                this.d = false;
                this.e = false;
                this.g = null;
                this.h = false;
                this.f = 8;
                return;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            int i = 0;
            int i2 = 8;
            boolean z2 = false;
            boolean z3 = false;
            boolean z4 = false;
            Object obj = null;
            BDLocation bDLocation = null;
            String str = null;
            String str2 = null;
            while (i < strArr.length) {
                try {
                    if (strArr[i].equals("-loc")) {
                        str2 = strArr[i + 1];
                        String[] split = str2.split(DispatchConstants.SIGN_SPLIT_SYMBOL);
                        int i3 = 0;
                        while (true) {
                            int i4 = i3;
                            if (i4 >= split.length) {
                                break;
                            }
                            if (split[i4].startsWith("cl=")) {
                                str = split[i4].substring(3);
                            } else if (split[i4].startsWith("wf=")) {
                                String[] split2 = split[i4].substring(3).split("\\|");
                                for (String split3 : split2) {
                                    String[] split4 = split3.split(";");
                                    if (split4.length >= 2) {
                                        linkedHashMap.put(split4[0], Integer.valueOf(split4[1]));
                                    }
                                }
                            }
                            i3 = i4 + 1;
                        }
                    } else if (strArr[i].equals("-com")) {
                        String[] split5 = strArr[i + 1].split(";");
                        if (split5.length > 0) {
                            BDLocation bDLocation2 = new BDLocation();
                            try {
                                bDLocation2.setLatitude(Double.valueOf(split5[0]).doubleValue());
                                bDLocation2.setLongitude(Double.valueOf(split5[1]).doubleValue());
                                bDLocation2.setLocType(Integer.valueOf(split5[2]).intValue());
                                bDLocation2.setNetworkLocationType(split5[3]);
                                bDLocation = bDLocation2;
                            } catch (Exception e) {
                                bDLocation = bDLocation2;
                            }
                        } else {
                            continue;
                        }
                    } else if (strArr[i].equals("-log")) {
                        if (strArr[i + 1].equals(RequestConstant.TURE)) {
                            obj = 1;
                        }
                    } else if (strArr[i].equals("-rgc")) {
                        if (strArr[i + 1].equals(RequestConstant.TURE)) {
                            z3 = true;
                        }
                    } else if (strArr[i].equals("-poi")) {
                        if (strArr[i + 1].equals(RequestConstant.TURE)) {
                            z4 = true;
                        }
                    } else if (strArr[i].equals("-minap")) {
                        try {
                            i2 = Integer.valueOf(strArr[i + 1]).intValue();
                        } catch (Exception e2) {
                        }
                    } else if (strArr[i].equals("-des") && strArr[i + 1].equals(RequestConstant.TURE)) {
                        z2 = true;
                    }
                    i += 2;
                } catch (Exception e3) {
                    z = false;
                    this.a = str2;
                    this.b = str;
                    this.i = linkedHashMap;
                    this.c = z;
                    this.d = z4;
                    this.e = z3;
                    this.f = i2;
                    this.g = bDLocation;
                    this.h = z2;
                }
            }
            if (obj == null) {
                str2 = null;
            }
            z = true;
            this.a = str2;
            this.b = str;
            this.i = linkedHashMap;
            this.c = z;
            this.d = z4;
            this.e = z3;
            this.f = i2;
            this.g = bDLocation;
            this.h = z2;
        }
    }

    static Cursor a(BDLocation bDLocation) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        String str7;
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date(System.currentTimeMillis()));
        Object matrixCursor = new MatrixCursor(a);
        Object[] objArr = new Object[a.length];
        objArr[matrixCursor.getColumnIndex("CoorType")] = CoordinateType.GCJ02;
        objArr[matrixCursor.getColumnIndex("Time")] = format;
        objArr[matrixCursor.getColumnIndex("LocType")] = Integer.valueOf(bDLocation.getLocType());
        objArr[matrixCursor.getColumnIndex("Longitude")] = Double.valueOf(bDLocation.getLongitude());
        objArr[matrixCursor.getColumnIndex("Latitude")] = Double.valueOf(bDLocation.getLatitude());
        objArr[matrixCursor.getColumnIndex("Radius")] = Float.valueOf(bDLocation.getRadius());
        objArr[matrixCursor.getColumnIndex("NetworkLocationType")] = bDLocation.getNetworkLocationType();
        Address address = bDLocation.getAddress();
        if (address != null) {
            str = address.country;
            str2 = address.countryCode;
            str3 = address.province;
            str4 = address.city;
            str5 = address.cityCode;
            str6 = address.district;
            str7 = address.street;
            format = address.streetNumber;
        } else {
            format = null;
            str7 = null;
            str6 = null;
            str5 = null;
            str4 = null;
            str3 = null;
            str2 = null;
            str = null;
        }
        objArr[matrixCursor.getColumnIndex("Country")] = str;
        objArr[matrixCursor.getColumnIndex("CountryCode")] = str2;
        objArr[matrixCursor.getColumnIndex("Province")] = str3;
        objArr[matrixCursor.getColumnIndex("City")] = str4;
        objArr[matrixCursor.getColumnIndex("CityCode")] = str5;
        objArr[matrixCursor.getColumnIndex("District")] = str6;
        objArr[matrixCursor.getColumnIndex("Street")] = str7;
        objArr[matrixCursor.getColumnIndex("StreetNumber")] = format;
        List poiList = bDLocation.getPoiList();
        if (poiList == null) {
            objArr[matrixCursor.getColumnIndex("PoiList")] = null;
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= poiList.size()) {
                    break;
                }
                Poi poi = (Poi) poiList.get(i2);
                stringBuffer.append(poi.getId()).append(";").append(poi.getName()).append(";").append(poi.getRank()).append(";|");
                i = i2 + 1;
            }
            objArr[matrixCursor.getColumnIndex("PoiList")] = stringBuffer.toString();
        }
        objArr[matrixCursor.getColumnIndex("LocationDescription")] = bDLocation.getLocationDescribe();
        matrixCursor.addRow(objArr);
        return matrixCursor;
    }

    static BDLocation a(Cursor cursor) {
        BDLocation bDLocation = new BDLocation();
        if (cursor == null || cursor.getCount() <= 0 || !cursor.moveToFirst()) {
            bDLocation.setLocType(67);
        } else {
            int i = 0;
            double d = 0.0d;
            double d2 = 0.0d;
            String str = null;
            String str2 = null;
            float f = 0.0f;
            String str3 = null;
            if (cursor.getColumnIndex("LocType") != -1) {
                i = cursor.getInt(cursor.getColumnIndex("LocType"));
            }
            if (cursor.getColumnIndex("Latitude") != -1) {
                d = cursor.getDouble(cursor.getColumnIndex("Latitude"));
            }
            if (cursor.getColumnIndex("Longitude") != -1) {
                d2 = cursor.getDouble(cursor.getColumnIndex("Longitude"));
            }
            if (cursor.getColumnIndex("CoorType") != -1) {
                str = cursor.getString(cursor.getColumnIndex("CoorType"));
            }
            if (cursor.getColumnIndex("NetworkLocationType") != -1) {
                str2 = cursor.getString(cursor.getColumnIndex("NetworkLocationType"));
            }
            if (cursor.getColumnIndex("Radius") != -1) {
                f = cursor.getFloat(cursor.getColumnIndex("Radius"));
            }
            if (cursor.getColumnIndex("Time") != -1) {
                str3 = cursor.getString(cursor.getColumnIndex("Time"));
            }
            String str4 = null;
            String str5 = null;
            String str6 = null;
            String str7 = null;
            String str8 = null;
            String str9 = null;
            String str10 = null;
            String str11 = null;
            if (cursor.getColumnIndex("Country") != -1) {
                str4 = cursor.getString(cursor.getColumnIndex("Country"));
            }
            if (cursor.getColumnIndex("CountryCode") != -1) {
                str5 = cursor.getString(cursor.getColumnIndex("CountryCode"));
            }
            if (cursor.getColumnIndex("Province") != -1) {
                str6 = cursor.getString(cursor.getColumnIndex("Province"));
            }
            if (cursor.getColumnIndex("City") != -1) {
                str7 = cursor.getString(cursor.getColumnIndex("City"));
            }
            if (cursor.getColumnIndex("CityCode") != -1) {
                str8 = cursor.getString(cursor.getColumnIndex("CityCode"));
            }
            if (cursor.getColumnIndex("District") != -1) {
                str9 = cursor.getString(cursor.getColumnIndex("District"));
            }
            if (cursor.getColumnIndex("Street") != -1) {
                str10 = cursor.getString(cursor.getColumnIndex("Street"));
            }
            if (cursor.getColumnIndex("StreetNumber") != -1) {
                str11 = cursor.getString(cursor.getColumnIndex("StreetNumber"));
            }
            Address build = new Builder().country(str4).countryCode(str5).province(str6).city(str7).cityCode(str8).district(str9).street(str10).streetNumber(str11).build();
            List list = null;
            if (cursor.getColumnIndex("PoiList") != -1) {
                list = new ArrayList();
                str5 = cursor.getString(cursor.getColumnIndex("PoiList"));
                if (str5 != null) {
                    try {
                        String[] split = str5.split("\\|");
                        for (String str82 : split) {
                            String[] split2 = str82.split(";");
                            if (split2.length >= 3) {
                                list.add(new Poi(split2[0], split2[1], Double.valueOf(split2[2]).doubleValue()));
                            }
                        }
                    } catch (Exception e) {
                        if (list.size() == 0) {
                            list = null;
                        }
                    } catch (Throwable th) {
                        if (list.size() == 0) {
                        }
                    }
                }
                if (list.size() == 0) {
                    list = null;
                }
            }
            str5 = null;
            if (cursor.getColumnIndex("LocationDescription") != -1) {
                str5 = cursor.getString(cursor.getColumnIndex("LocationDescription"));
            }
            bDLocation.setTime(str3);
            bDLocation.setRadius(f);
            bDLocation.setLocType(i);
            bDLocation.setCoorType(str);
            bDLocation.setLatitude(d);
            bDLocation.setLongitude(d2);
            bDLocation.setNetworkLocationType(str2);
            bDLocation.setAddr(build);
            bDLocation.setPoiList(list);
            bDLocation.setLocationDescribe(str5);
        }
        return bDLocation;
    }

    static String a(BDLocation bDLocation, int i) {
        if (bDLocation == null || bDLocation.getLocType() == 67) {
            return String.format(Locale.CHINA, "&ofl=%s|%d", new Object[]{"1", Integer.valueOf(i)});
        }
        String format = String.format(Locale.CHINA, "&ofl=%s|%d|%f|%f|%d", new Object[]{"1", Integer.valueOf(i), Double.valueOf(bDLocation.getLongitude()), Double.valueOf(bDLocation.getLatitude()), Integer.valueOf((int) bDLocation.getRadius())});
        String str = bDLocation.getAddress() != null ? format + "&ofaddr=" + bDLocation.getAddress().address : format;
        if (bDLocation.getPoiList() != null && bDLocation.getPoiList().size() > 0) {
            Poi poi = (Poi) bDLocation.getPoiList().get(0);
            str = str + String.format(Locale.US, "&ofpoi=%s|%s", new Object[]{poi.getId(), poi.getName()});
        }
        if (b.d == null) {
            return str;
        }
        return str + String.format(Locale.US, "&pack=%s&sdk=%.3f", new Object[]{b.d, Float.valueOf(7.21f)});
    }

    static String a(BDLocation bDLocation, BDLocation bDLocation2, a aVar) {
        StringBuffer stringBuffer = new StringBuffer();
        if (bDLocation2 == null) {
            stringBuffer.append("&ofcl=0");
        } else {
            stringBuffer.append(String.format(Locale.US, "&ofcl=1|%f|%f|%d", new Object[]{Double.valueOf(bDLocation2.getLongitude()), Double.valueOf(bDLocation2.getLatitude()), Integer.valueOf((int) bDLocation2.getRadius())}));
        }
        if (bDLocation == null) {
            stringBuffer.append("&ofwf=0");
        } else {
            stringBuffer.append(String.format(Locale.US, "&ofwf=1|%f|%f|%d", new Object[]{Double.valueOf(bDLocation.getLongitude()), Double.valueOf(bDLocation.getLatitude()), Integer.valueOf((int) bDLocation.getRadius())}));
        }
        if (aVar == null || !aVar.e) {
            stringBuffer.append("&rgcn=0");
        } else {
            stringBuffer.append("&rgcn=1");
        }
        if (aVar == null || !aVar.d) {
            stringBuffer.append("&poin=0");
        } else {
            stringBuffer.append("&poin=1");
        }
        if (aVar == null || !aVar.h) {
            stringBuffer.append("&desc=0");
        } else {
            stringBuffer.append("&desc=1");
        }
        if (aVar != null) {
            stringBuffer.append(String.format(Locale.US, "&aps=%d", new Object[]{Integer.valueOf(aVar.f)}));
        }
        return stringBuffer.toString();
    }

    static String[] a(com.baidu.location.f.a aVar, f fVar, BDLocation bDLocation, String str, boolean z, int i) {
        ArrayList arrayList = new ArrayList();
        StringBuffer stringBuffer = new StringBuffer();
        if (aVar != null) {
            stringBuffer.append(com.baidu.location.f.b.a().b(aVar));
        }
        if (fVar != null) {
            stringBuffer.append(fVar.a(30));
        }
        if (stringBuffer.length() > 0) {
            if (str != null) {
                stringBuffer.append(str);
            }
            arrayList.add("-loc");
            arrayList.add(stringBuffer.toString());
        }
        if (bDLocation != null) {
            String format = String.format(Locale.US, "%f;%f;%d;%s", new Object[]{Double.valueOf(bDLocation.getLatitude()), Double.valueOf(bDLocation.getLongitude()), Integer.valueOf(bDLocation.getLocType()), bDLocation.getNetworkLocationType()});
            arrayList.add("-com");
            arrayList.add(format);
        }
        if (z) {
            arrayList.add("-log");
            arrayList.add(RequestConstant.TURE);
        }
        if (k.g.equals("all")) {
            arrayList.add("-rgc");
            arrayList.add(RequestConstant.TURE);
        }
        if (k.i) {
            arrayList.add("-poi");
            arrayList.add(RequestConstant.TURE);
        }
        if (k.h) {
            arrayList.add("-des");
            arrayList.add(RequestConstant.TURE);
        }
        arrayList.add("-minap");
        arrayList.add(Integer.toString(i));
        String[] strArr = new String[arrayList.size()];
        arrayList.toArray(strArr);
        return strArr;
    }
}
