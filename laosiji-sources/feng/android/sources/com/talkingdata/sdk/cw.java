package com.talkingdata.sdk;

import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

/* compiled from: td */
final class cw extends PhoneStateListener {
    static final long a = 180000;
    long b = 0;
    long c = 0;
    int d;
    int e = 0;

    cw() {
    }

    public void onCellLocationChanged(CellLocation cellLocation) {
        try {
            if (cellLocation.getClass().equals(GsmCellLocation.class)) {
                this.d = ((GsmCellLocation) cellLocation).getLac();
                a();
            } else if (cellLocation.getClass().equals(CdmaCellLocation.class)) {
                this.d = ((CdmaCellLocation) cellLocation).getNetworkId();
                a();
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    private void a() {
        try {
            cq.a.post(new cx(this));
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }
}
