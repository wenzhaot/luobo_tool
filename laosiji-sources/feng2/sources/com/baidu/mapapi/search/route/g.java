package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class g implements Creator<IndoorRouteLine> {
    g() {
    }

    /* renamed from: a */
    public IndoorRouteLine createFromParcel(Parcel parcel) {
        return new IndoorRouteLine(parcel);
    }

    /* renamed from: a */
    public IndoorRouteLine[] newArray(int i) {
        return new IndoorRouteLine[i];
    }
}
