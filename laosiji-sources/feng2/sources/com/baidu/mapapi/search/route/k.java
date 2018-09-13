package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.route.MassTransitRouteLine.TransitStep.TrafficCondition;

final class k implements Creator<TrafficCondition> {
    k() {
    }

    /* renamed from: a */
    public TrafficCondition createFromParcel(Parcel parcel) {
        return new TrafficCondition(parcel);
    }

    /* renamed from: a */
    public TrafficCondition[] newArray(int i) {
        return new TrafficCondition[i];
    }
}
