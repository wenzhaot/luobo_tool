package com.tencent.liteav.j;

import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;

/* compiled from: RSAUtils */
public class b {
    public static final byte[] a = "#PART#".getBytes();

    public static byte[] a(byte[] bArr, byte[] bArr2) throws Exception {
        Key generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(bArr2));
        Cipher instance = Cipher.getInstance("RSA/None/PKCS1Padding");
        instance.init(2, generatePrivate);
        return instance.doFinal(bArr);
    }

    public static byte[] b(byte[] bArr, byte[] bArr2) throws Exception {
        int length = a.length;
        if (length <= 0) {
            return a(bArr, bArr2);
        }
        int length2 = bArr.length;
        List<Byte> arrayList = new ArrayList(1024);
        int i = 0;
        int i2 = 0;
        while (i < length2) {
            int i3;
            int i4;
            byte b = bArr[i];
            if (i == length2 - 1) {
                Object obj = new byte[(length2 - i2)];
                System.arraycopy(bArr, i2, obj, 0, obj.length);
                for (byte valueOf : a(obj, bArr2)) {
                    arrayList.add(Byte.valueOf(valueOf));
                }
                i += length;
                i3 = i - 1;
                i2 = 0;
            } else {
                if (b == a[0]) {
                    if (length <= 1) {
                        i3 = i;
                        i = i2;
                        i2 = 1;
                    } else if (i + length < length2) {
                        i4 = 1;
                        i3 = 0;
                        while (i4 < length && a[i4] == bArr[i + i4]) {
                            if (i4 == length - 1) {
                                i3 = 1;
                            }
                            i4++;
                        }
                        int i5 = i3;
                        i3 = i;
                        i = i2;
                        i2 = i5;
                    }
                }
                i3 = i;
                i = i2;
                i2 = 0;
            }
            if (i2 != 0) {
                Object obj2 = new byte[(i3 - i)];
                System.arraycopy(bArr, i, obj2, 0, obj2.length);
                for (byte valueOf2 : a(obj2, bArr2)) {
                    arrayList.add(Byte.valueOf(valueOf2));
                }
                i = i3 + length;
                i3 = i - 1;
            }
            i2 = i;
            i = i3 + 1;
        }
        byte[] bArr3 = new byte[arrayList.size()];
        int i6 = 0;
        for (Byte byteValue : arrayList) {
            i = i6 + 1;
            bArr3[i6] = byteValue.byteValue();
            i6 = i;
        }
        return bArr3;
    }
}
