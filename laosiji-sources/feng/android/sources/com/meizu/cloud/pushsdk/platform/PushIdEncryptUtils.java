package com.meizu.cloud.pushsdk.platform;

import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PushIdEncryptUtils {
    private static final String TAG = "PushIdEncryptUtils";
    private static final List<String> keyList = new ArrayList(secretKeyMap.keySet());
    private static Map<String, String> secretKeyMap = initKeyMap();

    private static Map<String, String> initKeyMap() {
        if (isEmpty(secretKeyMap)) {
            synchronized (PushIdEncryptUtils.class) {
                if (isEmpty(secretKeyMap)) {
                    secretKeyMap = new TreeMap();
                    secretKeyMap.put("UCI", "v9tC0Myz1MGwXRFy");
                    secretKeyMap.put("G3G", "XAsFqhhaf4gKpmAi");
                    secretKeyMap.put("V5R", "cOqH18NXwBtZVkvz");
                    secretKeyMap.put("0XC", "IgSEKZ3Ea6Pm4woS");
                    secretKeyMap.put("Z9K", "pH6J9DMPNgqQp8m8");
                    secretKeyMap.put("EIM", "K11Rs9HAKRXeNwq8");
                    secretKeyMap.put("SO7", "T8LquL1DvwVcogiU");
                    secretKeyMap.put("DDI", "d02F6ttOtV05MYCQ");
                    secretKeyMap.put("ULY", "ToZZIhAywnUfHShN");
                    secretKeyMap.put("0EV", "r5D5RRwQhfV0AYLb");
                    secretKeyMap.put("N6A", "QAtSBFcXnQoUgHO2");
                    secretKeyMap.put("S5Q", "sDWLrZINnum227am");
                    secretKeyMap.put("RA5", "4Uq3Ruxo1FTBdHQE");
                    secretKeyMap.put("J04", "N5hViUTdLCpN59H0");
                    secretKeyMap.put("B68", "EY3sH1KKtalg5ZaT");
                    secretKeyMap.put("9IW", "q1u0MiuFyim4pCYY");
                    secretKeyMap.put("UU3", "syLnkkd8AqNykVV7");
                    secretKeyMap.put("Z49", "V00FiWu124yE91sH");
                    secretKeyMap.put("BNA", "rPP7AK1VWpKEry3p");
                    secretKeyMap.put("WXG", "om8w5ahkJJgpAH9v");
                }
            }
        }
        return secretKeyMap;
    }

    private static String encryptPushId(String pushId) {
        if (TextUtils.isEmpty(pushId)) {
            return "";
        }
        String secretkey = (String) keyList.get(Math.abs(pushId.hashCode()) % secretKeyMap.size());
        String secretValue = (String) secretKeyMap.get(secretkey);
        String encryption = secretkey;
        try {
            pushId = new String(pushId.getBytes("UTF-8"), "iso-8859-1");
        } catch (Exception e) {
            DebugLogger.e(TAG, "encryptPushId getBytes error " + e.getMessage());
        }
        char[] cipher = new char[pushId.length()];
        int i = 0;
        int j = 0;
        while (i < pushId.length()) {
            if (j == secretValue.length()) {
                j = 0;
            }
            cipher[i] = (char) (pushId.charAt(i) ^ secretValue.charAt(j));
            String strCipher = Integer.toHexString(cipher[i]);
            if (strCipher.length() == 1) {
                encryption = encryption + PushConstants.PUSH_TYPE_NOTIFY + strCipher;
            } else {
                encryption = encryption + strCipher;
            }
            i++;
            j++;
        }
        return encryption;
    }

    public static String decryptPushId(String encryption) {
        if (TextUtils.isEmpty(encryption)) {
            return encryption;
        }
        String decoding = encryption;
        try {
            if (encryption.length() > 3) {
                String secretkey = encryption.substring(0, 3);
                if (secretKeyMap.containsKey(secretkey)) {
                    String secretValue = (String) secretKeyMap.get(secretkey);
                    encryption = encryption.substring(3, encryption.length());
                    char[] decryption = new char[(encryption.length() / 2)];
                    int i = 0;
                    int j = 0;
                    while (i < encryption.length() / 2) {
                        if (j == secretValue.length()) {
                            j = 0;
                        }
                        decryption[i] = (char) (secretValue.charAt(j) ^ ((char) Integer.valueOf(encryption.substring(i * 2, (i * 2) + 2), 16).intValue()));
                        i++;
                        j++;
                    }
                    decoding = new String(String.valueOf(decryption).getBytes("iso-8859-1"), "UTF-8");
                }
            }
        } catch (Exception e) {
            DebugLogger.e(TAG, "invalid pushId encryption " + encryption);
        }
        return decoding;
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }
}
