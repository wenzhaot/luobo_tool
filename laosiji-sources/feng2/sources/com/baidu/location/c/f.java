package com.baidu.location.c;

import android.os.Message;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.a.a;
import com.baidu.location.f.d;

class f implements BDLocationListener {
    final /* synthetic */ e a;

    f(e eVar) {
        this.a = eVar;
    }

    public void onReceiveLocation(BDLocation bDLocation) {
        if (!(bDLocation == null || this.a.ab == null)) {
            if (bDLocation.getAddrStr() == null && this.a.ab.getAddrStr() != null) {
                bDLocation.setAddr(this.a.ab.getAddress());
                bDLocation.setAddrStr(this.a.ab.getAddrStr());
            }
            if (bDLocation.getPoiList() == null && this.a.ab.getPoiList() != null) {
                bDLocation.setPoiList(this.a.ab.getPoiList());
            }
            if (bDLocation.getLocationDescribe() == null && this.a.ab.getLocationDescribe() != null) {
                bDLocation.setLocationDescribe(this.a.ab.getLocationDescribe());
            }
        }
        if (this.a.m.c() == 1) {
            if (bDLocation != null && !d.a().j()) {
                bDLocation.setUserIndoorState(1);
                bDLocation.setIndoorNetworkState(this.a.aa);
                a.a().a(bDLocation);
            } else if (bDLocation != null && d.a().j() && this.a.ac) {
                bDLocation.setUserIndoorState(1);
                bDLocation.setIndoorNetworkState(this.a.aa);
                a.a().a(bDLocation);
            }
        }
        if (bDLocation != null && bDLocation.getNetworkLocationType().equals("ml")) {
            Message obtainMessage = this.a.c.obtainMessage(801);
            obtainMessage.obj = bDLocation;
            obtainMessage.sendToTarget();
        }
    }
}
