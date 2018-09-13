package com.taobao.accs.utl;

/* compiled from: Taobao */
public class d extends j {
    public String a() {
        String emuiVersion = UtilityImpl.getEmuiVersion();
        if (emuiVersion != null || this.a == null) {
            return emuiVersion;
        }
        return this.a.a();
    }
}
