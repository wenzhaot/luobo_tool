package anet.channel.security;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.util.ALog;
import com.alibaba.wireless.security.open.SecurityGuardManager;
import com.alibaba.wireless.security.open.SecurityGuardParamContext;
import com.alibaba.wireless.security.open.dynamicdatastore.IDynamicDataStoreComponent;
import com.alibaba.wireless.security.open.securesignature.ISecureSignatureComponent;
import com.alibaba.wireless.security.open.staticdataencrypt.IStaticDataEncryptComponent;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Taobao */
class b implements ISecurity {
    private static String a = "awcn.DefaultSecurityGuard";
    private static boolean b;
    private static Map<String, Integer> c;
    private String d = null;

    static {
        b = false;
        c = null;
        try {
            Class.forName("com.alibaba.wireless.security.open.SecurityGuardManager");
            b = true;
            c = new HashMap();
            c.put(ISecurity.SIGN_ALGORITHM_HMAC_SHA1, Integer.valueOf(3));
            c.put(ISecurity.CIPHER_ALGORITHM_AES128, Integer.valueOf(16));
        } catch (Throwable th) {
            b = false;
        }
    }

    b(String str) {
        this.d = str;
    }

    public String sign(Context context, String str, String str2, String str3) {
        if (!b || context == null || TextUtils.isEmpty(str2) || !c.containsKey(str)) {
            return null;
        }
        try {
            ISecureSignatureComponent secureSignatureComp = SecurityGuardManager.getInstance(context).getSecureSignatureComp();
            if (secureSignatureComp != null) {
                SecurityGuardParamContext securityGuardParamContext = new SecurityGuardParamContext();
                securityGuardParamContext.appKey = str2;
                securityGuardParamContext.paramMap.put("INPUT", str3);
                securityGuardParamContext.requestType = ((Integer) c.get(str)).intValue();
                return secureSignatureComp.signRequest(securityGuardParamContext, this.d);
            }
        } catch (Throwable th) {
            ALog.e(a, "Securityguard sign request failed.", null, th, new Object[0]);
        }
        return null;
    }

    public byte[] decrypt(Context context, String str, String str2, byte[] bArr) {
        if (!b || context == null || bArr == null || TextUtils.isEmpty(str2) || !c.containsKey(str)) {
            return null;
        }
        Integer num = (Integer) c.get(str);
        if (num == null) {
            return null;
        }
        try {
            SecurityGuardManager instance = SecurityGuardManager.getInstance(context);
            if (instance != null) {
                IStaticDataEncryptComponent staticDataEncryptComp = instance.getStaticDataEncryptComp();
                if (staticDataEncryptComp != null) {
                    return staticDataEncryptComp.staticBinarySafeDecryptNoB64(num.intValue(), str2, bArr, this.d);
                }
            }
        } catch (Throwable th) {
            ALog.e(a, "staticBinarySafeDecryptNoB64", null, th, new Object[0]);
        }
        return null;
    }

    public boolean saveBytes(Context context, String str, byte[] bArr) {
        if (context == null || bArr == null || TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            SecurityGuardManager instance = SecurityGuardManager.getInstance(context);
            if (instance == null) {
                return false;
            }
            IDynamicDataStoreComponent dynamicDataStoreComp = instance.getDynamicDataStoreComp();
            if (dynamicDataStoreComp == null || dynamicDataStoreComp.putByteArray(str, bArr) == 0) {
                return false;
            }
            return true;
        } catch (Throwable th) {
            ALog.e(a, "saveBytes", null, th, new Object[0]);
            return false;
        }
    }

    public byte[] getBytes(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            SecurityGuardManager instance = SecurityGuardManager.getInstance(context);
            if (instance == null) {
                return null;
            }
            IDynamicDataStoreComponent dynamicDataStoreComp = instance.getDynamicDataStoreComp();
            if (dynamicDataStoreComp != null) {
                return dynamicDataStoreComp.getByteArray(str);
            }
            return null;
        } catch (Throwable th) {
            ALog.e(a, "getBytes", null, th, new Object[0]);
            return null;
        }
    }

    public boolean isSecOff() {
        return false;
    }
}
