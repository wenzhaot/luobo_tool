package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.model.LatLng;

public class RouteNode implements Parcelable {
    public static final Creator<RouteNode> CREATOR = new g();
    private String a;
    private LatLng b;
    private String c;

    protected RouteNode(Parcel parcel) {
        this.a = parcel.readString();
        this.b = (LatLng) parcel.readValue(LatLng.class.getClassLoader());
        this.c = parcel.readString();
    }

    public static RouteNode location(LatLng latLng) {
        RouteNode routeNode = new RouteNode();
        routeNode.setLocation(latLng);
        return routeNode;
    }

    public static RouteNode titleAndLocation(String str, LatLng latLng) {
        RouteNode routeNode = new RouteNode();
        routeNode.setTitle(str);
        routeNode.setLocation(latLng);
        return routeNode;
    }

    public int describeContents() {
        return 0;
    }

    public LatLng getLocation() {
        return this.b;
    }

    public String getTitle() {
        return this.a;
    }

    public String getUid() {
        return this.c;
    }

    public void setLocation(LatLng latLng) {
        this.b = latLng;
    }

    public void setTitle(String str) {
        this.a = str;
    }

    public void setUid(String str) {
        this.c = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeValue(this.b);
        parcel.writeString(this.c);
    }
}
