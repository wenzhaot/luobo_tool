package com.feng.library.utils;

import android.text.TextUtils;
import com.feng.car.video.shortvideo.FileUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.message.common.inter.ITagManager;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static Pattern mPatternFace = Pattern.compile("\\[5X:[a-zA-Z0-9\\u4e00-\\u9fa5]+\\]");
    private static Pattern mPatternFaceText = Pattern.compile("\\[\\$([^x00-xff]+|[A-Za-z0-9]+)\\$\\]");
    private static Pattern mPatternImage = Pattern.compile("\\[http:\\/\\/15feng\\.cn\\/p\\/[^\\]]+\\]");
    private static Pattern mPatternVideo = Pattern.compile("\\[http:\\/\\/15feng\\.cn\\/v\\/[^\\]]+\\]");

    public static String parseEmpty(String str) {
        if (str == null || "null".equals(str.trim())) {
            str = "";
        }
        return str.trim();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static int chineseLength(String str) {
        int valueLength = 0;
        String chinese = "[Α-￥]";
        if (!isEmpty(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (str.substring(i, i + 1).matches(chinese)) {
                    valueLength += 2;
                }
            }
        }
        return valueLength;
    }

    public static int strLength(String str) {
        int valueLength = 0;
        String chinese = "[Α-￥]";
        if (!isEmpty(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (str.substring(i, i + 1).matches(chinese)) {
                    valueLength += 2;
                } else {
                    valueLength++;
                }
            }
        }
        return valueLength;
    }

    public static int strLenthLikeWeiBo(String str, boolean isMatcherImageLink) {
        int chineseLength = 0;
        int englishLength = 0;
        String chinese = "[Α-￥]";
        if (!isEmpty(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (str.substring(i, i + 1).matches(chinese)) {
                    chineseLength += 2;
                } else {
                    englishLength++;
                }
            }
        }
        int valueLength = (chineseLength / 2) + ((englishLength + 1) / 2);
        if (!isMatcherImageLink) {
            return valueLength;
        }
        Matcher matcherImage = mPatternImage.matcher(str);
        if (matcherImage != null) {
            while (matcherImage.find()) {
                englishLength = (englishLength - matcherImage.group().length()) + 40;
            }
        }
        Matcher matcherVideo = mPatternVideo.matcher(str);
        if (matcherVideo != null) {
            while (matcherVideo.find()) {
                englishLength = (englishLength - matcherVideo.group().length()) + 40;
            }
        }
        Matcher matcherFace = mPatternFace.matcher(str);
        if (matcherFace != null) {
            while (matcherFace.find()) {
                englishLength = (englishLength - matcherFace.group().length()) + 10;
            }
        }
        Matcher matcherFaceText = mPatternFaceText.matcher(str);
        if (matcherFaceText != null) {
            while (matcherFaceText.find()) {
                englishLength -= 4;
                if (TextUtils.isEmpty(matcherFaceText.group(1)) || !matcherFaceText.group(1).equals(ITagManager.SUCCESS)) {
                    chineseLength = (chineseLength - ((matcherFaceText.group().length() - 4) * 2)) + 10;
                } else {
                    englishLength = (englishLength - matcherFaceText.group(1).length()) + 10;
                }
            }
        }
        return (chineseLength / 2) + ((englishLength + 1) / 2);
    }

    public static int strLenthIndex(String str, int nLength) {
        int chineseLength = 0;
        int englishLength = 0;
        String chinese = "[Α-￥]";
        if (!isEmpty(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (str.substring(i, i + 1).matches(chinese)) {
                    chineseLength += 2;
                } else {
                    englishLength++;
                }
                if ((chineseLength / 2) + ((englishLength + 1) / 2) >= nLength) {
                    return i;
                }
            }
        }
        return str.length();
    }

    public static int subStringLength(String str, int maxL) {
        int valueLength = 0;
        String chinese = "[Α-￥]";
        for (int i = 0; i < str.length(); i++) {
            if (str.substring(i, i + 1).matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength++;
            }
            if (valueLength >= maxL) {
                return i;
            }
        }
        return 0;
    }

    public static Boolean isNumberLetter(String str) {
        Boolean isNoLetter = Boolean.valueOf(false);
        if (str.matches("^[A-Za-z0-9]+$")) {
            return Boolean.valueOf(true);
        }
        return isNoLetter;
    }

    public static Boolean isNumber(String str) {
        Boolean isNumber = Boolean.valueOf(false);
        if (str.matches("^[0-9]+$")) {
            return Boolean.valueOf(true);
        }
        return isNumber;
    }

    public static Boolean isChinese(String str) {
        Boolean isChinese = Boolean.valueOf(true);
        String chinese = "[Α-￥]";
        if (!isEmpty(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (!str.substring(i, i + 1).matches(chinese)) {
                    isChinese = Boolean.valueOf(false);
                }
            }
        }
        return isChinese;
    }

    public static Boolean isContainChinese(String str) {
        Boolean isChinese = Boolean.valueOf(false);
        String chinese = "[Α-￥]";
        if (!isEmpty(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (str.substring(i, i + 1).matches(chinese)) {
                    isChinese = Boolean.valueOf(true);
                }
            }
        }
        return isChinese;
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line + "\n");
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    is.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } catch (Throwable th) {
                try {
                    is.close();
                } catch (IOException e22) {
                    e22.printStackTrace();
                }
                throw th;
            }
        }
        if (sb.indexOf("\n") != -1 && sb.lastIndexOf("\n") == sb.length() - 1) {
            sb.delete(sb.lastIndexOf("\n"), sb.lastIndexOf("\n") + 1);
        }
        try {
            is.close();
        } catch (IOException e222) {
            e222.printStackTrace();
        }
        return sb.toString();
    }

    public static String dateTimeFormat(String dateTime) {
        StringBuilder sb = new StringBuilder();
        try {
            if (isEmpty(dateTime)) {
                return null;
            }
            String[] dateAndTime = dateTime.split(" ");
            if (dateAndTime.length > 0) {
                for (String str : dateAndTime) {
                    String[] date;
                    int i;
                    if (str.indexOf("-") != -1) {
                        date = str.split("-");
                        for (i = 0; i < date.length; i++) {
                            sb.append(strFormat2(date[i]));
                            if (i < date.length - 1) {
                                sb.append("-");
                            }
                        }
                    } else if (str.indexOf(":") != -1) {
                        sb.append(" ");
                        date = str.split(":");
                        for (i = 0; i < date.length; i++) {
                            sb.append(strFormat2(date[i]));
                            if (i < date.length - 1) {
                                sb.append(":");
                            }
                        }
                    }
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String strFormat2(String str) {
        try {
            if (str.length() <= 1) {
                return PushConstants.PUSH_TYPE_NOTIFY + str;
            }
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    public static String cutString(String str, int length) {
        return cutString(str, length, "");
    }

    public static String cutString(String str, int length, String dot) {
        if (strlen(str, "GBK") <= length) {
            return str;
        }
        int temp = 0;
        StringBuffer sb = new StringBuffer(length);
        for (char c : str.toCharArray()) {
            sb.append(c);
            if (c > 256) {
                temp += 2;
            } else {
                temp++;
            }
            if (temp >= length) {
                if (dot != null) {
                    sb.append(dot);
                }
                return sb.toString();
            }
        }
        return sb.toString();
    }

    public static String cutStringFromChar(String str1, String str2, int offset) {
        if (isEmpty(str1)) {
            return "";
        }
        int start = str1.indexOf(str2);
        if (start == -1 || str1.length() <= start + offset) {
            return "";
        }
        return str1.substring(start + offset);
    }

    public static int strlen(String str, String charset) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int length = 0;
        try {
            return str.getBytes(charset).length;
        } catch (Exception e) {
            e.printStackTrace();
            return length;
        }
    }

    public static String getSizeDesc(long size) {
        String suffix = "B";
        if (size >= 1024) {
            suffix = "K";
            size >>= 10;
            if (size >= 1024) {
                suffix = "M";
                size >>= 10;
                if (size >= 1024) {
                    suffix = "G";
                    size >>= 10;
                }
            }
        }
        return size + suffix;
    }

    public static long ip2int(String ip) {
        String[] items = ip.replace(FileUtils.FILE_EXTENSION_SEPARATOR, MiPushClient.ACCEPT_TIME_SEPARATOR).split(MiPushClient.ACCEPT_TIME_SEPARATOR);
        return (((Long.valueOf(items[0]).longValue() << 24) | (Long.valueOf(items[1]).longValue() << 16)) | (Long.valueOf(items[2]).longValue() << 8)) | Long.valueOf(items[3]).longValue();
    }

    public static Boolean isEmail(String str) {
        Boolean isEmail = Boolean.valueOf(false);
        if (str.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")) {
            return Boolean.valueOf(true);
        }
        return isEmail;
    }

    public static void main(String[] args) {
        System.out.println(dateTimeFormat("2012-3-2 12:2:20"));
    }
}
