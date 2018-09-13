package com.umeng.socialize.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.feng.car.utils.FengConstant;
import com.umeng.socialize.Config;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.dplus.cache.DplueCache;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class SocializeUtils {
    protected static final String TAG = "SocializeUtils";
    public static Set<Uri> deleteUris = new HashSet();
    private static final char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static Pattern mDoubleByte_Pattern = null;
    private static int smDip = 0;

    public static String getAppkey(Context context) {
        if (context == null) {
            return "";
        }
        String str = SocializeConstants.APPKEY;
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo == null) {
                return str;
            }
            Object obj = applicationInfo.metaData.get("UMENG_APPKEY");
            if (obj != null) {
                return obj.toString();
            }
            return str;
        } catch (Throwable e) {
            SLog.error(e);
            return str;
        }
    }

    public static void safeCloseDialog(Dialog dialog) {
        if (dialog != null) {
            try {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            } catch (Throwable e) {
                SLog.error(e);
            }
        }
    }

    public static void openApplicationMarket(Context context, String str) throws Exception {
        if (Config.isJumptoAppStore) {
            String str2 = "market://details?id=" + str;
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(str2));
            context.startActivity(intent);
        }
    }

    public static void safeShowDialog(Dialog dialog) {
        if (dialog != null) {
            try {
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            } catch (Throwable e) {
                SLog.error(e);
            }
        }
    }

    public static Bundle parseUrl(String str) {
        try {
            URL url = new URL(str);
            Bundle decodeUrl = decodeUrl(url.getQuery());
            decodeUrl.putAll(decodeUrl(url.getRef()));
            return decodeUrl;
        } catch (MalformedURLException e) {
            return new Bundle();
        }
    }

    public static Bundle decodeUrl(String str) {
        Bundle bundle = new Bundle();
        if (str != null) {
            for (String split : str.split("&")) {
                String[] split2 = split.split("=");
                bundle.putString(URLDecoder.decode(split2[0]), URLDecoder.decode(split2[1]));
            }
        }
        return bundle;
    }

    public static int countContentLength(String str) {
        Object trim = str.trim();
        int i = 0;
        while (getDoubleBytePattern().matcher(trim).find()) {
            i++;
        }
        int length = trim.length() - i;
        if (length % 2 != 0) {
            return i + ((length + 1) / 2);
        }
        return i + (length / 2);
    }

    private static Pattern getDoubleBytePattern() {
        if (mDoubleByte_Pattern == null) {
            mDoubleByte_Pattern = Pattern.compile("[^\\x00-\\xff]");
        }
        return mDoubleByte_Pattern;
    }

    public static int[] getFloatWindowSize(Context context) {
        if (context == null) {
            return new int[2];
        }
        return new int[]{580, FengConstant.IMAGEMIDDLEWIDTH_350};
    }

    public static boolean isFloatWindowStyle(Context context) {
        if (context == null) {
            return false;
        }
        if (SocializeConstants.SUPPORT_PAD) {
            if (smDip == 0) {
                WindowManager windowManager = (WindowManager) context.getSystemService("window");
                Display defaultDisplay = windowManager.getDefaultDisplay();
                int width = defaultDisplay.getWidth();
                int height = defaultDisplay.getHeight();
                if (width <= height) {
                    height = width;
                }
                DisplayMetrics displayMetrics = new DisplayMetrics();
                windowManager.getDefaultDisplay().getMetrics(displayMetrics);
                smDip = (int) ((((float) height) / displayMetrics.density) + 0.5f);
            }
            if ((context.getResources().getConfiguration().screenLayout & 15) >= 3 && smDip >= 550) {
                return true;
            }
        }
        return false;
    }

    public static Uri insertImage(Context context, String str) {
        if (TextUtils.isEmpty(str) || !new File(str).exists()) {
            return null;
        }
        try {
            Object insertImage = Media.insertImage(context.getContentResolver(), str, "umeng_social_shareimg", null);
            if (TextUtils.isEmpty(insertImage)) {
                return null;
            }
            return Uri.parse(insertImage);
        } catch (Throwable th) {
            SLog.error(th);
            return null;
        }
    }

    public static int dip2Px(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + 0.5f);
    }

    public static Map<String, String> jsonToMap(String str) {
        Map<String, String> hashMap = new HashMap();
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str2 = (String) keys.next();
                hashMap.put(str2, jSONObject.get(str2) + "");
            }
        } catch (Throwable e) {
            SLog.error(e);
        }
        return hashMap;
    }

    public static byte[] File2byte(File file) {
        byte[] bArr = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr2 = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr2);
                if (read != -1) {
                    byteArrayOutputStream.write(bArr2, 0, read);
                } else {
                    fileInputStream.close();
                    byteArrayOutputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } catch (Throwable e) {
            SLog.error(e);
            return bArr;
        } catch (Throwable e2) {
            SLog.error(e2);
            return bArr;
        }
    }

    public static Map<String, String> bundleTomap(Bundle bundle) {
        if (bundle == null || bundle.isEmpty()) {
            return null;
        }
        Set<String> keySet = bundle.keySet();
        Map<String, String> hashMap = new HashMap();
        for (String str : keySet) {
            if (str.equals("com.sina.weibo.intent.extra.USER_ICON")) {
                hashMap.put("icon_url", bundle.getString(str));
            }
            hashMap.put(str, bundle.getString(str));
        }
        return hashMap;
    }

    public static Bundle mapToBundle(Map<String, String> map) {
        Bundle bundle = new Bundle();
        for (String str : map.keySet()) {
            bundle.putString(str, (String) map.get(str));
        }
        return bundle;
    }

    public static boolean assertBinaryInvalid(byte[] bArr) {
        return bArr != null && bArr.length > 0;
    }

    public static boolean isToday(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date(System.currentTimeMillis()));
        Calendar instance2 = Calendar.getInstance();
        instance2.setTime(new Date(j));
        if (instance2.get(1) == instance.get(1) && instance2.get(6) - instance.get(6) == 0) {
            return true;
        }
        return false;
    }

    public static boolean isHasDplusCache() {
        File filePath = DplueCache.getFilePath("s_e");
        File filePath2 = DplueCache.getFilePath("auth");
        File filePath3 = DplueCache.getFilePath("userinfo");
        File filePath4 = DplueCache.getFilePath("dau");
        File filePath5 = DplueCache.getFilePath("stats");
        if ((filePath == null || filePath.listFiles() == null || filePath.listFiles().length <= 0) && ((filePath2 == null || filePath2.listFiles() == null || filePath2.listFiles().length <= 0) && ((filePath3 == null || filePath3.listFiles() == null || filePath3.listFiles().length <= 0) && ((filePath4 == null || filePath4.listFiles() == null || filePath4.listFiles().length <= 0) && (filePath5 == null || filePath5.listFiles() == null || filePath5.listFiles().length <= 0))))) {
            return false;
        }
        return true;
    }

    public static String hexdigest(String str) {
        String str2 = null;
        try {
            return md5(str.getBytes());
        } catch (Throwable e) {
            SLog.error(e);
            return str2;
        }
    }

    public static String md5(byte[] bArr) {
        int i = 0;
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(bArr);
            byte[] digest = instance.digest();
            char[] cArr = new char[32];
            int i2 = 0;
            while (i < 16) {
                byte b = digest[i];
                int i3 = i2 + 1;
                cArr[i2] = hexDigits[(b >>> 4) & 15];
                i2 = i3 + 1;
                cArr[i3] = hexDigits[b & 15];
                i++;
            }
            return new String(cArr);
        } catch (Throwable e) {
            SLog.error(e);
            return null;
        }
    }
}
