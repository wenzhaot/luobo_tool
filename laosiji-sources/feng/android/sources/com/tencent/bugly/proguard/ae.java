package com.tencent.bugly.proguard;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: BUGLY */
public final class ae implements ag {
    private String a = null;

    public final byte[] a(byte[] bArr) throws Exception {
        int i = 0;
        if (this.a == null || bArr == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            stringBuffer.append(b + " ");
        }
        Key secretKeySpec = new SecretKeySpec(this.a.getBytes(), "AES");
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(2, secretKeySpec, new IvParameterSpec(this.a.getBytes()));
        byte[] doFinal = instance.doFinal(bArr);
        stringBuffer = new StringBuffer();
        int length = doFinal.length;
        while (i < length) {
            stringBuffer.append(doFinal[i] + " ");
            i++;
        }
        return doFinal;
    }

    public final byte[] b(byte[] bArr) throws Exception, NoSuchAlgorithmException {
        int i = 0;
        if (this.a == null || bArr == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            stringBuffer.append(b + " ");
        }
        Key secretKeySpec = new SecretKeySpec(this.a.getBytes(), "AES");
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(1, secretKeySpec, new IvParameterSpec(this.a.getBytes()));
        byte[] doFinal = instance.doFinal(bArr);
        stringBuffer = new StringBuffer();
        int length = doFinal.length;
        while (i < length) {
            stringBuffer.append(doFinal[i] + " ");
            i++;
        }
        return doFinal;
    }

    public final void a(String str) {
        if (str != null) {
            for (int length = str.length(); length < 16; length++) {
                str = str + PushConstants.PUSH_TYPE_NOTIFY;
            }
            this.a = str.substring(0, 16);
        }
    }
}
