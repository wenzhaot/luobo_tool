package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.core.RouteStep;
import java.util.ArrayList;
import java.util.List;

public class DrivingRouteLine extends RouteLine<DrivingStep> implements Parcelable {
    public static final Creator<DrivingRouteLine> CREATOR = new d();
    private boolean b;
    private List<RouteNode> c;
    private int d;
    private int e;

    public static class DrivingStep extends RouteStep implements Parcelable {
        public static final Creator<DrivingStep> CREATOR = new e();
        List<LatLng> d;
        int[] e;
        private int f;
        private RouteNode g;
        private RouteNode h;
        private String i;
        private String j;
        private String k;
        private String l;
        private int m;

        protected DrivingStep(Parcel parcel) {
            super(parcel);
            this.f = parcel.readInt();
            this.g = (RouteNode) parcel.readParcelable(RouteNode.class.getClassLoader());
            this.h = (RouteNode) parcel.readParcelable(RouteNode.class.getClassLoader());
            this.i = parcel.readString();
            this.j = parcel.readString();
            this.k = parcel.readString();
            this.l = parcel.readString();
            this.m = parcel.readInt();
            this.d = parcel.createTypedArrayList(LatLng.CREATOR);
            this.e = parcel.createIntArray();
        }

        public int describeContents() {
            return 0;
        }

        public int getDirection() {
            return this.f;
        }

        public RouteNode getEntrance() {
            return this.g;
        }

        public String getEntranceInstructions() {
            return this.j;
        }

        public RouteNode getExit() {
            return this.h;
        }

        public String getExitInstructions() {
            return this.k;
        }

        public String getInstructions() {
            return this.l;
        }

        public int getNumTurns() {
            return this.m;
        }

        public int[] getTrafficList() {
            return this.e;
        }

        public List<LatLng> getWayPoints() {
            if (this.mWayPoints == null) {
                this.mWayPoints = CoordUtil.decodeLocationList(this.i);
            }
            return this.d;
        }

        public void setDirection(int i) {
            this.f = i;
        }

        public void setEntrance(RouteNode routeNode) {
            this.g = routeNode;
        }

        public void setEntranceInstructions(String str) {
            this.j = str;
        }

        public void setExit(RouteNode routeNode) {
            this.h = routeNode;
        }

        public void setExitInstructions(String str) {
            this.k = str;
        }

        public void setInstructions(String str) {
            this.l = str;
        }

        public void setNumTurns(int i) {
            this.m = i;
        }

        public void setPathList(List<LatLng> list) {
            this.d = list;
        }

        public void setPathString(String str) {
            this.i = str;
        }

        public void setTrafficList(int[] iArr) {
            this.e = iArr;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.f);
            parcel.writeParcelable(this.g, 1);
            parcel.writeParcelable(this.h, 1);
            parcel.writeString(this.i);
            parcel.writeString(this.j);
            parcel.writeString(this.k);
            parcel.writeString(this.l);
            parcel.writeInt(this.m);
            parcel.writeTypedList(this.d);
            parcel.writeIntArray(this.e);
        }
    }

    protected DrivingRouteLine(Parcel parcel) {
        super(parcel);
        this.b = parcel.readByte() != (byte) 0;
        this.c = new ArrayList();
        parcel.readList(this.c, RouteNode.class.getClassLoader());
        this.d = parcel.readInt();
        this.e = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public int getCongestionDistance() {
        return this.d;
    }

    public int getLightNum() {
        return this.e;
    }

    public List<RouteNode> getWayPoints() {
        return this.c;
    }

    @Deprecated
    public boolean isSupportTraffic() {
        return this.b;
    }

    public void setCongestionDistance(int i) {
        this.d = i;
    }

    public void setLightNum(int i) {
        this.e = i;
    }

    public void setSupportTraffic(boolean z) {
        this.b = z;
    }

    public void setWayPoints(List<RouteNode> list) {
        this.c = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.setType(TYPE.DRIVESTEP);
        super.writeToParcel(parcel, i);
        parcel.writeByte(this.b ? (byte) 1 : (byte) 0);
        parcel.writeList(this.c);
        parcel.writeInt(this.d);
        parcel.writeInt(this.e);
    }
}
