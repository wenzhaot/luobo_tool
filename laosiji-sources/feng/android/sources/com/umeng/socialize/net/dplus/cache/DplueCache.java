package com.umeng.socialize.net.dplus.cache;

import android.text.TextUtils;
import com.umeng.socialize.utils.ContextUtil;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.File;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public class DplueCache {
    public static boolean save(JSONObject jSONObject, File file) throws JSONException, IOException {
        if (file.exists()) {
            return a(readFile(file) + MiPushClient.ACCEPT_TIME_SEPARATOR + jSONObject, file);
        }
        file.createNewFile();
        return a(jSONObject.toString(), file);
    }

    private static JSONObject a(JSONObject jSONObject) {
        if (jSONObject != null) {
            JSONObject optJSONObject = jSONObject.optJSONObject("content");
            if (optJSONObject != null) {
                return optJSONObject.optJSONObject("share");
            }
        }
        return new JSONObject();
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0033 A:{SYNTHETIC, Splitter: B:17:0x0033} */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0041 A:{SYNTHETIC, Splitter: B:23:0x0041} */
    private static boolean a(java.lang.String r5, java.io.File r6) {
        /*
        r0 = 0;
        r3 = 0;
        r1 = com.umeng.socialize.utils.ContextUtil.getContext();	 Catch:{ Exception -> 0x002a, all -> 0x003e }
        r2 = r6.getName();	 Catch:{ Exception -> 0x002a, all -> 0x003e }
        r4 = 0;
        r1 = r1.openFileOutput(r2, r4);	 Catch:{ Exception -> 0x002a, all -> 0x003e }
        r4 = new java.io.OutputStreamWriter;	 Catch:{ Exception -> 0x002a, all -> 0x003e }
        r4.<init>(r1);	 Catch:{ Exception -> 0x002a, all -> 0x003e }
        r2 = new java.io.BufferedWriter;	 Catch:{ Exception -> 0x002a, all -> 0x003e }
        r2.<init>(r4);	 Catch:{ Exception -> 0x002a, all -> 0x003e }
        r2.write(r5);	 Catch:{ Exception -> 0x004f }
        r0 = 1;
        if (r2 == 0) goto L_0x0022;
    L_0x001f:
        r2.close();	 Catch:{ Exception -> 0x0023 }
    L_0x0022:
        return r0;
    L_0x0023:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.CACHE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0022;
    L_0x002a:
        r1 = move-exception;
        r2 = r3;
    L_0x002c:
        r3 = com.umeng.socialize.utils.UmengText.CACHE.CACHEFILE;	 Catch:{ all -> 0x004c }
        com.umeng.socialize.utils.SLog.error(r3, r1);	 Catch:{ all -> 0x004c }
        if (r2 == 0) goto L_0x0022;
    L_0x0033:
        r2.close();	 Catch:{ Exception -> 0x0037 }
        goto L_0x0022;
    L_0x0037:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.CACHE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0022;
    L_0x003e:
        r0 = move-exception;
    L_0x003f:
        if (r3 == 0) goto L_0x0044;
    L_0x0041:
        r3.close();	 Catch:{ Exception -> 0x0045 }
    L_0x0044:
        throw r0;
    L_0x0045:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.CACHE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0044;
    L_0x004c:
        r0 = move-exception;
        r3 = r2;
        goto L_0x003f;
    L_0x004f:
        r1 = move-exception;
        goto L_0x002c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.socialize.net.dplus.cache.DplueCache.a(java.lang.String, java.io.File):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x005a A:{SYNTHETIC, Splitter: B:29:0x005a} */
    public static java.lang.String readFile(java.io.File r4) {
        /*
        r0 = r4.exists();
        if (r0 != 0) goto L_0x000a;
    L_0x0006:
        r0 = "";
    L_0x0009:
        return r0;
    L_0x000a:
        r2 = 0;
        r0 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0068, all -> 0x0057 }
        r0.<init>();	 Catch:{ Exception -> 0x0068, all -> 0x0057 }
        r1 = com.umeng.socialize.utils.ContextUtil.getContext();	 Catch:{ Exception -> 0x0068, all -> 0x0057 }
        r3 = r4.getName();	 Catch:{ Exception -> 0x0068, all -> 0x0057 }
        r1 = r1.openFileInput(r3);	 Catch:{ Exception -> 0x0068, all -> 0x0057 }
        r3 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x0068, all -> 0x0057 }
        r3.<init>(r1);	 Catch:{ Exception -> 0x0068, all -> 0x0057 }
        r1 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x0068, all -> 0x0057 }
        r1.<init>(r3);	 Catch:{ Exception -> 0x0068, all -> 0x0057 }
    L_0x0026:
        r2 = r1.readLine();	 Catch:{ Exception -> 0x0030 }
        if (r2 == 0) goto L_0x003f;
    L_0x002c:
        r0.append(r2);	 Catch:{ Exception -> 0x0030 }
        goto L_0x0026;
    L_0x0030:
        r0 = move-exception;
    L_0x0031:
        r2 = com.umeng.socialize.utils.UmengText.CACHE.CACHEFILE;	 Catch:{ all -> 0x0065 }
        com.umeng.socialize.utils.SLog.error(r2, r0);	 Catch:{ all -> 0x0065 }
        if (r1 == 0) goto L_0x003b;
    L_0x0038:
        r1.close();	 Catch:{ Exception -> 0x0050 }
    L_0x003b:
        r0 = "";
        goto L_0x0009;
    L_0x003f:
        r0 = r0.toString();	 Catch:{ Exception -> 0x0030 }
        if (r1 == 0) goto L_0x0009;
    L_0x0045:
        r1.close();	 Catch:{ Exception -> 0x0049 }
        goto L_0x0009;
    L_0x0049:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.CACHE.CACHEFILE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0009;
    L_0x0050:
        r0 = move-exception;
        r1 = com.umeng.socialize.utils.UmengText.CACHE.CACHEFILE;
        com.umeng.socialize.utils.SLog.error(r1, r0);
        goto L_0x003b;
    L_0x0057:
        r0 = move-exception;
    L_0x0058:
        if (r2 == 0) goto L_0x005d;
    L_0x005a:
        r2.close();	 Catch:{ Exception -> 0x005e }
    L_0x005d:
        throw r0;
    L_0x005e:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.CACHE.CACHEFILE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x005d;
    L_0x0065:
        r0 = move-exception;
        r2 = r1;
        goto L_0x0058;
    L_0x0068:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.socialize.net.dplus.cache.DplueCache.readFile(java.io.File):java.lang.String");
    }

    public static boolean deleteFile(File file) {
        return file.delete();
    }

    public static File getFilePath(String str) {
        if (ContextUtil.getContext() == null) {
            return null;
        }
        Object packageName = ContextUtil.getContext().getPackageName();
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        Object obj = File.separator + "data" + File.separator + "data" + File.separator + packageName + File.separator + "files" + File.separator + str;
        if (TextUtils.isEmpty(obj)) {
            return null;
        }
        File file = new File(obj);
        if (file.exists() || file.mkdirs()) {
            return file;
        }
        return null;
    }

    public static String getFileName() {
        return "dpluscache";
    }
}
