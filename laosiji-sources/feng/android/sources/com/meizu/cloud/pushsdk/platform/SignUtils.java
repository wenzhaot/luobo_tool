package com.meizu.cloud.pushsdk.platform;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class SignUtils {
    public static String getSignature(Map<String, String> paramMap, String secret) {
        Set<Entry<String, String>> entrys = new TreeMap(paramMap).entrySet();
        StringBuilder basestring = new StringBuilder();
        for (Entry<String, String> param : entrys) {
            basestring.append((String) param.getKey()).append("=").append((String) param.getValue());
        }
        basestring.append(secret);
        return parseStrToMd5L32(basestring.toString());
    }

    public static String parseStrToMd5L32(String str) {
        try {
            byte[] bytes = MessageDigest.getInstance("MD5").digest(str.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes) {
                int bt = b & 255;
                if (bt < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
