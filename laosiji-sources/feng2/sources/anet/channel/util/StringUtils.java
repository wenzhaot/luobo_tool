package anet.channel.util;

import android.text.TextUtils;
import java.security.MessageDigest;

/* compiled from: Taobao */
public class StringUtils {
    private static final char[] DIGITS_LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String[] parseURL(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        if (str.startsWith("//")) {
            str = "http:" + str;
        }
        int indexOf = str.indexOf(HttpConstant.SCHEME_SPLIT);
        if (indexOf == -1) {
            return null;
        }
        String[] strArr = new String[2];
        String substring = str.substring(0, indexOf);
        if (!"http".equalsIgnoreCase(substring) && !"https".equalsIgnoreCase(substring)) {
            return null;
        }
        strArr[0] = substring;
        int length = str.length();
        int i = indexOf + 3;
        while (i < length) {
            char charAt = str.charAt(i);
            if (charAt == '/' || charAt == ':' || charAt == '?' || charAt == '#') {
                strArr[1] = str.substring(indexOf + 3, i);
                return strArr;
            }
            i++;
        }
        if (i != length) {
            return null;
        }
        strArr[1] = str.substring(indexOf + 3);
        return strArr;
    }

    public static String concatString(String str, String str2) {
        return new StringBuilder(str.length() + str2.length()).append(str).append(str2).toString();
    }

    public static String concatString(String str, String str2, String str3) {
        return new StringBuilder((str.length() + str2.length()) + str3.length()).append(str).append(str2).append(str3).toString();
    }

    public static String buildKey(String str, String str2) {
        return concatString(str, HttpConstant.SCHEME_SPLIT, str2);
    }

    public static String simplifyString(String str, int i) {
        return str.length() <= i ? str : concatString(str.substring(0, i), "......");
    }

    public static String stringNull2Empty(String str) {
        return str == null ? "" : str;
    }

    public static String md5ToHex(String str) {
        String str2 = null;
        if (str == null) {
            return str2;
        }
        try {
            return bytesToHexString(MessageDigest.getInstance("MD5").digest(str.getBytes("utf-8")));
        } catch (Exception e) {
            return str2;
        }
    }

    private static String bytesToHexString(byte[] bArr, char[] cArr) {
        int i = 0;
        int length = bArr.length;
        char[] cArr2 = new char[(length << 1)];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i + 1;
            cArr2[i] = cArr[(bArr[i2] & 240) >>> 4];
            i = i3 + 1;
            cArr2[i3] = cArr[bArr[i2] & 15];
        }
        return new String(cArr2);
    }

    public static String bytesToHexString(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        return bytesToHexString(bArr, DIGITS_LOWER);
    }

    public static String longToIP(long j) {
        StringBuilder stringBuilder = new StringBuilder(16);
        long j2 = 1000000000;
        do {
            stringBuilder.append(j / j2);
            stringBuilder.append('.');
            j %= j2;
            j2 /= 1000;
        } while (j2 > 0);
        stringBuilder.setLength(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isBlank(String str) {
        if (str != null) {
            int length = str.length();
            if (length != 0) {
                for (int i = 0; i < length; i++) {
                    if (!Character.isWhitespace(str.charAt(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return true;
    }
}
