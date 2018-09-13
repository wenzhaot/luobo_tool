package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.route.BikingRouteLine.BikingStep;

final class b implements Creator<BikingStep> {
    b() {
    }

    /* renamed from: a */
    public BikingStep createFromParcel(Parcel parcel) {
        return new BikingStep(parcel);
    }

    /* renamed from: a */
    public BikingStep[] newArray(int i) {
        return new BikingStep[i];
    }
}
