package com.baidu.mapapi.search.poi;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class c implements Creator<PoiResult> {
    c() {
    }

    /* renamed from: a */
    public PoiResult createFromParcel(Parcel parcel) {
        return new PoiResult(parcel);
    }

    /* renamed from: a */
    public PoiResult[] newArray(int i) {
        return new PoiResult[i];
    }
}
