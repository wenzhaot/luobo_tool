package anet.channel.session;

import anet.channel.security.ISecurity;
import anet.channel.util.ALog;
import org.android.spdy.AccsSSLCallback;

/* compiled from: Taobao */
class j implements AccsSSLCallback {
    final /* synthetic */ TnetSpdySession a;

    j(TnetSpdySession tnetSpdySession) {
        this.a = tnetSpdySession;
    }

    public byte[] getSSLPublicKey(int i, byte[] bArr) {
        byte[] decrypt;
        Throwable th;
        try {
            decrypt = this.a.iSecurity.decrypt(this.a.mContext, ISecurity.CIPHER_ALGORITHM_AES128, "tnet_pksg_key", bArr);
            if (decrypt != null) {
                try {
                    if (ALog.isPrintLog(2)) {
                        ALog.i("getSSLPublicKey", null, "decrypt", new String(decrypt));
                    }
                } catch (Throwable th2) {
                    th = th2;
                    ALog.e("awcn.TnetSpdySession", "getSSLPublicKey", null, th, new Object[0]);
                    return decrypt;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            decrypt = null;
        }
        return decrypt;
    }
}
