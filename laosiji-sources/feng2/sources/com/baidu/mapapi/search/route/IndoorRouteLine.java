package com.baidu.mapapi.search.route;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.core.RouteStep;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"ParcelCreator"})
public class IndoorRouteLine extends RouteLine<IndoorRouteStep> {
    public static final Creator<IndoorRouteLine> CREATOR = new g();

    public static class IndoorRouteStep extends RouteStep {
        private RouteNode d;
        private RouteNode e;
        private String f;
        private String g;
        private String h;
        private List<IndoorStepNode> i;
        private List<Double> j;

        public static class IndoorStepNode {
            private String a;
            private int b;
            private LatLng c;
            private String d;

            public String getDetail() {
                return this.d;
            }

            public LatLng getLocation() {
                return this.c;
            }

            public String getName() {
                return this.a;
            }

            public int getType() {
                return this.b;
            }

            public void setDetail(String str) {
                this.d = str;
            }

            public void setLocation(LatLng latLng) {
                this.c = latLng;
            }

            public void setName(String str) {
                this.a = str;
            }

            public void setType(int i) {
                this.b = i;
            }
        }

        private List<LatLng> a(List<Double> list) {
            List<LatLng> arrayList = new ArrayList();
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= list.size()) {
                    return arrayList;
                }
                arrayList.add(new LatLng(((Double) list.get(i2)).doubleValue(), ((Double) list.get(i2 + 1)).doubleValue()));
                i = i2 + 2;
            }
        }

        public String getBuildingId() {
            return this.h;
        }

        public RouteNode getEntrace() {
            return this.d;
        }

        public RouteNode getExit() {
            return this.e;
        }

        public String getFloorId() {
            return this.g;
        }

        public String getInstructions() {
            return this.f;
        }

        public List<IndoorStepNode> getStepNodes() {
            return this.i;
        }

        public List<LatLng> getWayPoints() {
            if (this.mWayPoints == null) {
                this.mWayPoints = a(this.j);
            }
            return this.mWayPoints;
        }

        public void setBuildingId(String str) {
            this.h = str;
        }

        public void setEntrace(RouteNode routeNode) {
            this.d = routeNode;
        }

        public void setExit(RouteNode routeNode) {
            this.e = routeNode;
        }

        public void setFloorId(String str) {
            this.g = str;
        }

        public void setInstructions(String str) {
            this.f = str;
        }

        public void setPath(List<Double> list) {
            this.j = list;
        }

        public void setStepNodes(List<IndoorStepNode> list) {
            this.i = list;
        }
    }

    public IndoorRouteLine() {
        setType(TYPE.WALKSTEP);
    }

    protected IndoorRouteLine(Parcel parcel) {
        super(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public List<IndoorRouteStep> getAllStep() {
        return super.getAllStep();
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }
}
