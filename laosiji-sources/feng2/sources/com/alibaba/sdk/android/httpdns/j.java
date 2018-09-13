package com.alibaba.sdk.android.httpdns;

import java.security.MessageDigest;
import java.util.regex.Pattern;

class j {
    private static Pattern a = Pattern.compile("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$");

    static String a(String str) {
        String str2 = "MD5";
        MessageDigest instance = MessageDigest.getInstance("MD5");
        instance.update(str.getBytes());
        byte[] digest = instance.digest();
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : digest) {
            str2 = Integer.toHexString(b & 255);
            while (str2.length() < 2) {
                str2 = "0" + str2;
            }
            stringBuilder.append(str2);
        }
        return stringBuilder.toString();
    }

    static boolean b(String str) {
        if (str == null) {
            return false;
        }
        char[] toCharArray = str.toCharArray();
        if (toCharArray.length <= 0 || toCharArray.length > 255) {
            return false;
        }
        for (char c : toCharArray) {
            if ((c < 'A' || c > 'Z') && ((c < 'a' || c > 'z') && ((c < '0' || c > '9') && c != '.' && c != '-'))) {
                return false;
            }
        }
        return true;
    }

    static boolean c(String str) {
        return str != null && str.length() >= 7 && str.length() <= 15 && !str.equals("") && a.matcher(str).matches();
    }
}
