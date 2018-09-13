package com.baidu.mapapi.search.geocode;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult.AddressComponent;

final class c implements Creator<AddressComponent> {
    c() {
    }

    /* renamed from: a */
    public AddressComponent createFromParcel(Parcel parcel) {
        return new AddressComponent(parcel);
    }

    /* renamed from: a */
    public AddressComponent[] newArray(int i) {
        return new AddressComponent[i];
    }
}
