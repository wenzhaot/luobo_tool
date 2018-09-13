package com.baidu.mapapi.map.offline;

import com.baidu.mapapi.model.CoordUtil;
import com.baidu.platform.comapi.map.t;
import com.baidu.platform.comapi.map.w;
import java.util.ArrayList;
import java.util.Iterator;

public class OfflineMapUtil {
    public static MKOLSearchRecord getSearchRecordFromLocalCityInfo(t tVar) {
        if (tVar == null) {
            return null;
        }
        long j;
        MKOLSearchRecord mKOLSearchRecord = new MKOLSearchRecord();
        mKOLSearchRecord.cityID = tVar.a;
        mKOLSearchRecord.cityName = tVar.b;
        mKOLSearchRecord.cityType = tVar.d;
        long j2 = 0;
        if (tVar.a() != null) {
            ArrayList arrayList = new ArrayList();
            Iterator it = tVar.a().iterator();
            while (true) {
                j = j2;
                if (!it.hasNext()) {
                    break;
                }
                t tVar2 = (t) it.next();
                arrayList.add(getSearchRecordFromLocalCityInfo(tVar2));
                j2 = ((long) tVar2.c) + j;
                mKOLSearchRecord.childCities = arrayList;
            }
        } else {
            j = 0;
        }
        if (mKOLSearchRecord.cityType == 1) {
            mKOLSearchRecord.dataSize = j;
        } else {
            mKOLSearchRecord.dataSize = (long) tVar.c;
        }
        return mKOLSearchRecord;
    }

    public static MKOLUpdateElement getUpdatElementFromLocalMapElement(w wVar) {
        if (wVar == null) {
            return null;
        }
        MKOLUpdateElement mKOLUpdateElement = new MKOLUpdateElement();
        mKOLUpdateElement.cityID = wVar.a;
        mKOLUpdateElement.cityName = wVar.b;
        if (wVar.g != null) {
            mKOLUpdateElement.geoPt = CoordUtil.mc2ll(wVar.g);
        }
        mKOLUpdateElement.level = wVar.e;
        mKOLUpdateElement.ratio = wVar.i;
        mKOLUpdateElement.serversize = wVar.h;
        if (wVar.i == 100) {
            mKOLUpdateElement.size = wVar.h;
        } else {
            mKOLUpdateElement.size = (wVar.h / 100) * wVar.i;
        }
        mKOLUpdateElement.status = wVar.l;
        mKOLUpdateElement.update = wVar.j;
        return mKOLUpdateElement;
    }
}
