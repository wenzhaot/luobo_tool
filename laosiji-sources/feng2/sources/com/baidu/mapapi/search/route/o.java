package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class o implements Creator<TransitRouteLine> {
    o() {
    }

    /* renamed from: a */
    public TransitRouteLine createFromParcel(Parcel parcel) {
        return new TransitRouteLine(parcel);
    }

    /* renamed from: a */
    public TransitRouteLine[] newArray(int i) {
        return new TransitRouteLine[i];
    }
}
