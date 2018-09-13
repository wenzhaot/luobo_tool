package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.route.TransitRouteLine.TransitStep;

final class p implements Creator<TransitStep> {
    p() {
    }

    /* renamed from: a */
    public TransitStep createFromParcel(Parcel parcel) {
        return new TransitStep(parcel);
    }

    /* renamed from: a */
    public TransitStep[] newArray(int i) {
        return new TransitStep[i];
    }
}
