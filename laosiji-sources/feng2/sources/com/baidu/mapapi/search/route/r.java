package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class r implements Creator<WalkingRouteLine> {
    r() {
    }

    /* renamed from: a */
    public WalkingRouteLine createFromParcel(Parcel parcel) {
        return new WalkingRouteLine(parcel);
    }

    /* renamed from: a */
    public WalkingRouteLine[] newArray(int i) {
        return new WalkingRouteLine[i];
    }
}
