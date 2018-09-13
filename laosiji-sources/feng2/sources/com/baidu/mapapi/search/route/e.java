package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.route.DrivingRouteLine.DrivingStep;

final class e implements Creator<DrivingStep> {
    e() {
    }

    /* renamed from: a */
    public DrivingStep createFromParcel(Parcel parcel) {
        return new DrivingStep(parcel);
    }

    /* renamed from: a */
    public DrivingStep[] newArray(int i) {
        return new DrivingStep[i];
    }
}
