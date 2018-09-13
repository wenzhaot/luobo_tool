package com.huawei.android.pushselfshow.utils.b;

import com.google.zxing.common.StringUtils;
import com.huawei.android.pushagent.a.a.c;
import java.io.File;

public class a {
    private String a;
    private String b;

    public a(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public static File a(String str, String str2) {
        File file;
        Exception e;
        String[] split = str2.split("/");
        File file2 = new File(str);
        int i = 0;
        while (i < split.length - 1) {
            try {
                i++;
                file2 = new File(file2, new String(split[i].getBytes("8859_1"), StringUtils.GB2312));
            } catch (Exception e2) {
                Exception exception = e2;
                file = file2;
                e = exception;
                e.printStackTrace();
                return file;
            }
        }
        c.a("PushSelfShowLog", "file1 = " + file2);
        if (!(file2.exists() || file2.mkdirs())) {
            c.a("PushSelfShowLog", "ret.mkdirs faild");
        }
        String str3 = new String(split[split.length - 1].getBytes("8859_1"), StringUtils.GB2312);
        c.a("PushSelfShowLog", "substr = " + str3);
        file = new File(file2, str3);
        try {
            c.a("PushSelfShowLog", "file2 = " + file);
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            return file;
        }
        return file;
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x0193 A:{SYNTHETIC, Splitter: B:48:0x0193} */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0198 A:{SYNTHETIC, Splitter: B:51:0x0198} */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x019d A:{SYNTHETIC, Splitter: B:54:0x019d} */
    /* JADX WARNING: Removed duplicated region for block: B:338:0x0038 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x01a2 A:{SYNTHETIC, Splitter: B:57:0x01a2} */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x040e A:{SYNTHETIC, Splitter: B:140:0x040e} */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x0413 A:{SYNTHETIC, Splitter: B:143:0x0413} */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x0418 A:{SYNTHETIC, Splitter: B:146:0x0418} */
    /* JADX WARNING: Removed duplicated region for block: B:344:0x0038 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x041d A:{SYNTHETIC, Splitter: B:149:0x041d} */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x04c9 A:{SYNTHETIC, Splitter: B:166:0x04c9} */
    /* JADX WARNING: Removed duplicated region for block: B:169:0x04ce A:{SYNTHETIC, Splitter: B:169:0x04ce} */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x04d3 A:{SYNTHETIC, Splitter: B:172:0x04d3} */
    /* JADX WARNING: Removed duplicated region for block: B:347:0x0038 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:175:0x04d8 A:{SYNTHETIC, Splitter: B:175:0x04d8} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01c8 A:{Splitter: B:6:0x0030, ExcHandler: java.util.zip.ZipException (e java.util.zip.ZipException)} */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02af A:{Splitter: B:6:0x0030, ExcHandler: java.lang.IllegalStateException (e java.lang.IllegalStateException)} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0317 A:{Splitter: B:6:0x0030, ExcHandler: java.util.NoSuchElementException (e java.util.NoSuchElementException)} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01c8 A:{Splitter: B:6:0x0030, ExcHandler: java.util.zip.ZipException (e java.util.zip.ZipException)} */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02af A:{Splitter: B:6:0x0030, ExcHandler: java.lang.IllegalStateException (e java.lang.IllegalStateException)} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0317 A:{Splitter: B:6:0x0030, ExcHandler: java.util.NoSuchElementException (e java.util.NoSuchElementException)} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01c8 A:{Splitter: B:6:0x0030, ExcHandler: java.util.zip.ZipException (e java.util.zip.ZipException)} */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02af A:{Splitter: B:6:0x0030, ExcHandler: java.lang.IllegalStateException (e java.lang.IllegalStateException)} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0317 A:{Splitter: B:6:0x0030, ExcHandler: java.util.NoSuchElementException (e java.util.NoSuchElementException)} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01c8 A:{Splitter: B:6:0x0030, ExcHandler: java.util.zip.ZipException (e java.util.zip.ZipException)} */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02af A:{Splitter: B:6:0x0030, ExcHandler: java.lang.IllegalStateException (e java.lang.IllegalStateException)} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0317 A:{Splitter: B:6:0x0030, ExcHandler: java.util.NoSuchElementException (e java.util.NoSuchElementException)} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01c8 A:{Splitter: B:6:0x0030, ExcHandler: java.util.zip.ZipException (e java.util.zip.ZipException)} */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02af A:{Splitter: B:6:0x0030, ExcHandler: java.lang.IllegalStateException (e java.lang.IllegalStateException)} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0317 A:{Splitter: B:6:0x0030, ExcHandler: java.util.NoSuchElementException (e java.util.NoSuchElementException)} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01c8 A:{Splitter: B:6:0x0030, ExcHandler: java.util.zip.ZipException (e java.util.zip.ZipException)} */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02af A:{Splitter: B:6:0x0030, ExcHandler: java.lang.IllegalStateException (e java.lang.IllegalStateException)} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0317 A:{Splitter: B:6:0x0030, ExcHandler: java.util.NoSuchElementException (e java.util.NoSuchElementException)} */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x040e A:{SYNTHETIC, Splitter: B:140:0x040e} */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x0413 A:{SYNTHETIC, Splitter: B:143:0x0413} */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x0418 A:{SYNTHETIC, Splitter: B:146:0x0418} */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x041d A:{SYNTHETIC, Splitter: B:149:0x041d} */
    /* JADX WARNING: Removed duplicated region for block: B:344:0x0038 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x04c9 A:{SYNTHETIC, Splitter: B:166:0x04c9} */
    /* JADX WARNING: Removed duplicated region for block: B:169:0x04ce A:{SYNTHETIC, Splitter: B:169:0x04ce} */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x04d3 A:{SYNTHETIC, Splitter: B:172:0x04d3} */
    /* JADX WARNING: Removed duplicated region for block: B:175:0x04d8 A:{SYNTHETIC, Splitter: B:175:0x04d8} */
    /* JADX WARNING: Removed duplicated region for block: B:347:0x0038 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0193 A:{SYNTHETIC, Splitter: B:48:0x0193} */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0198 A:{SYNTHETIC, Splitter: B:51:0x0198} */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x019d A:{SYNTHETIC, Splitter: B:54:0x019d} */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x01a2 A:{SYNTHETIC, Splitter: B:57:0x01a2} */
    /* JADX WARNING: Removed duplicated region for block: B:338:0x0038 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x040e A:{SYNTHETIC, Splitter: B:140:0x040e} */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x0413 A:{SYNTHETIC, Splitter: B:143:0x0413} */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x0418 A:{SYNTHETIC, Splitter: B:146:0x0418} */
    /* JADX WARNING: Removed duplicated region for block: B:344:0x0038 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x041d A:{SYNTHETIC, Splitter: B:149:0x041d} */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x04c9 A:{SYNTHETIC, Splitter: B:166:0x04c9} */
    /* JADX WARNING: Removed duplicated region for block: B:169:0x04ce A:{SYNTHETIC, Splitter: B:169:0x04ce} */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x04d3 A:{SYNTHETIC, Splitter: B:172:0x04d3} */
    /* JADX WARNING: Removed duplicated region for block: B:347:0x0038 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:175:0x04d8 A:{SYNTHETIC, Splitter: B:175:0x04d8} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01c8 A:{Splitter: B:6:0x0030, ExcHandler: java.util.zip.ZipException (e java.util.zip.ZipException)} */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02af A:{Splitter: B:6:0x0030, ExcHandler: java.lang.IllegalStateException (e java.lang.IllegalStateException)} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0317 A:{Splitter: B:6:0x0030, ExcHandler: java.util.NoSuchElementException (e java.util.NoSuchElementException)} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01c8 A:{Splitter: B:6:0x0030, ExcHandler: java.util.zip.ZipException (e java.util.zip.ZipException)} */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02af A:{Splitter: B:6:0x0030, ExcHandler: java.lang.IllegalStateException (e java.lang.IllegalStateException)} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0317 A:{Splitter: B:6:0x0030, ExcHandler: java.util.NoSuchElementException (e java.util.NoSuchElementException)} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01c8 A:{Splitter: B:6:0x0030, ExcHandler: java.util.zip.ZipException (e java.util.zip.ZipException)} */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02af A:{Splitter: B:6:0x0030, ExcHandler: java.lang.IllegalStateException (e java.lang.IllegalStateException)} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0317 A:{Splitter: B:6:0x0030, ExcHandler: java.util.NoSuchElementException (e java.util.NoSuchElementException)} */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x0382 A:{SYNTHETIC, Splitter: B:125:0x0382} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01c8 A:{Splitter: B:6:0x0030, ExcHandler: java.util.zip.ZipException (e java.util.zip.ZipException)} */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02af A:{Splitter: B:6:0x0030, ExcHandler: java.lang.IllegalStateException (e java.lang.IllegalStateException)} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0317 A:{Splitter: B:6:0x0030, ExcHandler: java.util.NoSuchElementException (e java.util.NoSuchElementException)} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01c8 A:{Splitter: B:6:0x0030, ExcHandler: java.util.zip.ZipException (e java.util.zip.ZipException)} */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02af A:{Splitter: B:6:0x0030, ExcHandler: java.lang.IllegalStateException (e java.lang.IllegalStateException)} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0317 A:{Splitter: B:6:0x0030, ExcHandler: java.util.NoSuchElementException (e java.util.NoSuchElementException)} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01c8 A:{Splitter: B:6:0x0030, ExcHandler: java.util.zip.ZipException (e java.util.zip.ZipException)} */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02af A:{Splitter: B:6:0x0030, ExcHandler: java.lang.IllegalStateException (e java.lang.IllegalStateException)} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0317 A:{Splitter: B:6:0x0030, ExcHandler: java.util.NoSuchElementException (e java.util.NoSuchElementException)} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01c8 A:{Splitter: B:6:0x0030, ExcHandler: java.util.zip.ZipException (e java.util.zip.ZipException)} */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02af A:{Splitter: B:6:0x0030, ExcHandler: java.lang.IllegalStateException (e java.lang.IllegalStateException)} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0317 A:{Splitter: B:6:0x0030, ExcHandler: java.util.NoSuchElementException (e java.util.NoSuchElementException)} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01c8 A:{Splitter: B:6:0x0030, ExcHandler: java.util.zip.ZipException (e java.util.zip.ZipException)} */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02af A:{Splitter: B:6:0x0030, ExcHandler: java.lang.IllegalStateException (e java.lang.IllegalStateException)} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0317 A:{Splitter: B:6:0x0030, ExcHandler: java.util.NoSuchElementException (e java.util.NoSuchElementException)} */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0193 A:{SYNTHETIC, Splitter: B:48:0x0193} */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0198 A:{SYNTHETIC, Splitter: B:51:0x0198} */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x019d A:{SYNTHETIC, Splitter: B:54:0x019d} */
    /* JADX WARNING: Removed duplicated region for block: B:338:0x0038 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x01a2 A:{SYNTHETIC, Splitter: B:57:0x01a2} */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x040e A:{SYNTHETIC, Splitter: B:140:0x040e} */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x0413 A:{SYNTHETIC, Splitter: B:143:0x0413} */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x0418 A:{SYNTHETIC, Splitter: B:146:0x0418} */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x041d A:{SYNTHETIC, Splitter: B:149:0x041d} */
    /* JADX WARNING: Removed duplicated region for block: B:344:0x0038 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x04c9 A:{SYNTHETIC, Splitter: B:166:0x04c9} */
    /* JADX WARNING: Removed duplicated region for block: B:169:0x04ce A:{SYNTHETIC, Splitter: B:169:0x04ce} */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x04d3 A:{SYNTHETIC, Splitter: B:172:0x04d3} */
    /* JADX WARNING: Removed duplicated region for block: B:175:0x04d8 A:{SYNTHETIC, Splitter: B:175:0x04d8} */
    /* JADX WARNING: Removed duplicated region for block: B:347:0x0038 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01c8 A:{Splitter: B:6:0x0030, ExcHandler: java.util.zip.ZipException (e java.util.zip.ZipException)} */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02af A:{Splitter: B:6:0x0030, ExcHandler: java.lang.IllegalStateException (e java.lang.IllegalStateException)} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0317 A:{Splitter: B:6:0x0030, ExcHandler: java.util.NoSuchElementException (e java.util.NoSuchElementException)} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01c8 A:{Splitter: B:6:0x0030, ExcHandler: java.util.zip.ZipException (e java.util.zip.ZipException)} */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02af A:{Splitter: B:6:0x0030, ExcHandler: java.lang.IllegalStateException (e java.lang.IllegalStateException)} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0317 A:{Splitter: B:6:0x0030, ExcHandler: java.util.NoSuchElementException (e java.util.NoSuchElementException)} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01c8 A:{Splitter: B:6:0x0030, ExcHandler: java.util.zip.ZipException (e java.util.zip.ZipException)} */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02af A:{Splitter: B:6:0x0030, ExcHandler: java.lang.IllegalStateException (e java.lang.IllegalStateException)} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0317 A:{Splitter: B:6:0x0030, ExcHandler: java.util.NoSuchElementException (e java.util.NoSuchElementException)} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01c8 A:{Splitter: B:6:0x0030, ExcHandler: java.util.zip.ZipException (e java.util.zip.ZipException)} */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02af A:{Splitter: B:6:0x0030, ExcHandler: java.lang.IllegalStateException (e java.lang.IllegalStateException)} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0317 A:{Splitter: B:6:0x0030, ExcHandler: java.util.NoSuchElementException (e java.util.NoSuchElementException)} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01c8 A:{Splitter: B:6:0x0030, ExcHandler: java.util.zip.ZipException (e java.util.zip.ZipException)} */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02af A:{Splitter: B:6:0x0030, ExcHandler: java.lang.IllegalStateException (e java.lang.IllegalStateException)} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0317 A:{Splitter: B:6:0x0030, ExcHandler: java.util.NoSuchElementException (e java.util.NoSuchElementException)} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01c8 A:{Splitter: B:6:0x0030, ExcHandler: java.util.zip.ZipException (e java.util.zip.ZipException)} */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x02af A:{Splitter: B:6:0x0030, ExcHandler: java.lang.IllegalStateException (e java.lang.IllegalStateException)} */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x0317 A:{Splitter: B:6:0x0030, ExcHandler: java.util.NoSuchElementException (e java.util.NoSuchElementException)} */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x0382 A:{SYNTHETIC, Splitter: B:125:0x0382} */
    /* JADX WARNING: Removed duplicated region for block: B:193:0x056b A:{SYNTHETIC, Splitter: B:193:0x056b} */
    /* JADX WARNING: Removed duplicated region for block: B:196:0x0570 A:{SYNTHETIC, Splitter: B:196:0x0570} */
    /* JADX WARNING: Removed duplicated region for block: B:199:0x0575 A:{SYNTHETIC, Splitter: B:199:0x0575} */
    /* JADX WARNING: Removed duplicated region for block: B:196:0x0570 A:{SYNTHETIC, Splitter: B:196:0x0570} */
    /* JADX WARNING: Removed duplicated region for block: B:199:0x0575 A:{SYNTHETIC, Splitter: B:199:0x0575} */
    /* JADX WARNING: Removed duplicated region for block: B:199:0x0575 A:{SYNTHETIC, Splitter: B:199:0x0575} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:60:0x01a7, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:62:?, code:
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "tempFOS.close error:" + r0.getMessage());
     */
    /* JADX WARNING: Missing block: B:64:0x01c8, code:
            r0 = e;
     */
    /* JADX WARNING: Missing block: B:65:0x01c9, code:
            r2 = r6;
     */
    /* JADX WARNING: Missing block: B:86:0x0226, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:88:?, code:
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "tempFOS.close error:" + r0.getMessage());
     */
    /* JADX WARNING: Missing block: B:90:0x0247, code:
            r0 = e;
     */
    /* JADX WARNING: Missing block: B:98:0x028e, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:100:?, code:
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "zFileIn.close error:" + r0.getMessage());
     */
    /* JADX WARNING: Missing block: B:101:0x02af, code:
            r0 = e;
     */
    /* JADX WARNING: Missing block: B:109:0x02f6, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:111:?, code:
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "is.close error:" + r0.getMessage());
     */
    /* JADX WARNING: Missing block: B:112:0x0317, code:
            r0 = e;
     */
    /* JADX WARNING: Missing block: B:120:0x035e, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:122:?, code:
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "os.close error:" + r0.getMessage());
     */
    /* JADX WARNING: Missing block: B:128:0x0386, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:130:?, code:
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "zFileIn.close error:" + r0.getMessage());
     */
    /* JADX WARNING: Missing block: B:131:0x03a7, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:132:0x03a8, code:
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "is.close error:" + r0.getMessage());
     */
    /* JADX WARNING: Missing block: B:133:0x03c8, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:134:0x03c9, code:
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "os.close error:" + r0.getMessage());
     */
    /* JADX WARNING: Missing block: B:152:0x0422, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:154:?, code:
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "tempFOS.close error:" + r0.getMessage());
     */
    /* JADX WARNING: Missing block: B:155:0x0443, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:156:0x0444, code:
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "zFileIn.close error:" + r0.getMessage());
     */
    /* JADX WARNING: Missing block: B:157:0x0463, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:158:0x0464, code:
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "is.close error:" + r0.getMessage());
     */
    /* JADX WARNING: Missing block: B:178:0x04dd, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:180:?, code:
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "tempFOS.close error:" + r0.getMessage());
     */
    /* JADX WARNING: Missing block: B:181:0x04fe, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:182:0x04ff, code:
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "zFileIn.close error:" + r0.getMessage());
     */
    /* JADX WARNING: Missing block: B:183:0x051e, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:184:0x051f, code:
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "is.close error:" + r0.getMessage());
     */
    /* JADX WARNING: Missing block: B:185:0x053e, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:186:0x053f, code:
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "os.close error:" + r0.getMessage());
     */
    /* JADX WARNING: Missing block: B:203:0x0579, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:204:0x057a, code:
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "zFileIn.close error:" + r1.getMessage());
     */
    /* JADX WARNING: Missing block: B:205:0x0599, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:206:0x059a, code:
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "is.close error:" + r1.getMessage());
     */
    /* JADX WARNING: Missing block: B:207:0x05b9, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:208:0x05ba, code:
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "os.close error:" + r1.getMessage());
     */
    /* JADX WARNING: Missing block: B:209:0x05d9, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:210:0x05da, code:
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "tempFOS.close error:" + r1.getMessage());
     */
    public void a() {
        /*
        r12 = this;
        r2 = 0;
        r0 = r12.b;	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r1 = "/";
        r0 = r0.endsWith(r1);	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        if (r0 != 0) goto L_0x0024;
    L_0x000c:
        r0 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r0.<init>();	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r1 = r12.b;	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r0 = r0.append(r1);	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r1 = "/";
        r0 = r0.append(r1);	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r12.b = r0;	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
    L_0x0024:
        r6 = new java.util.zip.ZipFile;	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r0 = new java.io.File;	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r1 = r12.a;	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r0.<init>(r1);	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r6.<init>(r0);	 Catch:{ ZipException -> 0x0656, IOException -> 0x0652, IllegalStateException -> 0x064e, NoSuchElementException -> 0x064a, all -> 0x0642 }
        r7 = r6.entries();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r8 = new byte[r0];	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x0038:
        r0 = r7.hasMoreElements();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        if (r0 == 0) goto L_0x05f9;
    L_0x003e:
        r0 = r7.nextElement();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = (java.util.zip.ZipEntry) r0;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r0.isDirectory();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        if (r1 == 0) goto L_0x00b3;
    L_0x004a:
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "ze.getName() = ";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = r0.getName();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3 = r3.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r1, r3);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3 = r12.b;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.append(r3);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3 = r0.getName();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.append(r3);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3 = new java.lang.String;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "8859_1";
        r1 = r1.getBytes(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "GB2312";
        r3.<init>(r1, r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = "PushSelfShowLog";
        r4 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r5 = "str = ";
        r4 = r4.append(r5);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = r4.append(r3);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = r4.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r1, r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = new java.io.File;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1.<init>(r3);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.mkdir();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        if (r1 != 0) goto L_0x0038;
    L_0x00b3:
        r1 = r12.b;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3 = r0.getName();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3 = a(r1, r3);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r3.isDirectory();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        if (r1 == 0) goto L_0x00e9;
    L_0x00c3:
        if (r6 == 0) goto L_0x00c8;
    L_0x00c5:
        r6.close();	 Catch:{ IOException -> 0x00c9 }
    L_0x00c8:
        return;
    L_0x00c9:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "zfile.close error:";
        r2 = r2.append(r3);
        r0 = r0.getMessage();
        r0 = r2.append(r0);
        r0 = r0.toString();
        com.huawei.android.pushagent.a.a.c.a(r1, r0);
        goto L_0x00c8;
    L_0x00e9:
        r1 = "PushSelfShowLog";
        r4 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r5 = "ze.getName() = ";
        r4 = r4.append(r5);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r5 = r0.getName();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = r4.append(r5);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r5 = ",output file :";
        r4 = r4.append(r5);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r5 = r3.getAbsolutePath();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = r4.append(r5);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = r4.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r1, r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r0.getName();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = android.text.TextUtils.isEmpty(r1);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        if (r1 == 0) goto L_0x0150;
    L_0x0120:
        r0 = "PushSelfShowLog";
        r1 = "ze.getName() is empty= ";
        com.huawei.android.pushagent.a.a.c.a(r0, r1);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        if (r6 == 0) goto L_0x00c8;
    L_0x012b:
        r6.close();	 Catch:{ IOException -> 0x012f }
        goto L_0x00c8;
    L_0x012f:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "zfile.close error:";
        r2 = r2.append(r3);
        r0 = r0.getMessage();
        r0 = r2.append(r0);
        r0 = r0.toString();
        com.huawei.android.pushagent.a.a.c.a(r1, r0);
        goto L_0x00c8;
    L_0x0150:
        r1 = r6.getInputStream(r0);	 Catch:{ IOException -> 0x068f, IllegalStateException -> 0x03e9, IndexOutOfBoundsException -> 0x04a4, all -> 0x055f }
        r5 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x0696, IllegalStateException -> 0x067d, IndexOutOfBoundsException -> 0x066b, all -> 0x0659 }
        r5.<init>(r3);	 Catch:{ IOException -> 0x0696, IllegalStateException -> 0x067d, IndexOutOfBoundsException -> 0x066b, all -> 0x0659 }
        r4 = new java.io.BufferedOutputStream;	 Catch:{ IOException -> 0x069c, IllegalStateException -> 0x0683, IndexOutOfBoundsException -> 0x0671, all -> 0x065f }
        r4.<init>(r5);	 Catch:{ IOException -> 0x069c, IllegalStateException -> 0x0683, IndexOutOfBoundsException -> 0x0671, all -> 0x065f }
        r3 = new java.io.BufferedInputStream;	 Catch:{ IOException -> 0x06a1, IllegalStateException -> 0x0688, IndexOutOfBoundsException -> 0x0676, all -> 0x0664 }
        r3.<init>(r1);	 Catch:{ IOException -> 0x06a1, IllegalStateException -> 0x0688, IndexOutOfBoundsException -> 0x0676, all -> 0x0664 }
    L_0x0163:
        r0 = 0;
        r9 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r0 = r3.read(r8, r0, r9);	 Catch:{ IOException -> 0x0172, IllegalStateException -> 0x068c, IndexOutOfBoundsException -> 0x067a }
        r9 = -1;
        if (r0 == r9) goto L_0x0210;
    L_0x016d:
        r9 = 0;
        r4.write(r8, r9, r0);	 Catch:{ IOException -> 0x0172, IllegalStateException -> 0x068c, IndexOutOfBoundsException -> 0x067a }
        goto L_0x0163;
    L_0x0172:
        r0 = move-exception;
    L_0x0173:
        r9 = "PushSelfShowLog";
        r10 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0668 }
        r10.<init>();	 Catch:{ all -> 0x0668 }
        r11 = "os.write error:";
        r10 = r10.append(r11);	 Catch:{ all -> 0x0668 }
        r0 = r0.getMessage();	 Catch:{ all -> 0x0668 }
        r0 = r10.append(r0);	 Catch:{ all -> 0x0668 }
        r0 = r0.toString();	 Catch:{ all -> 0x0668 }
        com.huawei.android.pushagent.a.a.c.a(r9, r0);	 Catch:{ all -> 0x0668 }
        if (r1 == 0) goto L_0x0196;
    L_0x0193:
        r1.close();	 Catch:{ IOException -> 0x0386, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x0196:
        if (r3 == 0) goto L_0x019b;
    L_0x0198:
        r3.close();	 Catch:{ IOException -> 0x03a7, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x019b:
        if (r4 == 0) goto L_0x01a0;
    L_0x019d:
        r4.close();	 Catch:{ IOException -> 0x03c8, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x01a0:
        if (r5 == 0) goto L_0x0038;
    L_0x01a2:
        r5.close();	 Catch:{ IOException -> 0x01a7, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0038;
    L_0x01a7:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "tempFOS.close error:";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0038;
    L_0x01c8:
        r0 = move-exception;
        r2 = r6;
    L_0x01ca:
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0646 }
        r3.<init>();	 Catch:{ all -> 0x0646 }
        r4 = "upZipFile error:";
        r3 = r3.append(r4);	 Catch:{ all -> 0x0646 }
        r0 = r0.getMessage();	 Catch:{ all -> 0x0646 }
        r0 = r3.append(r0);	 Catch:{ all -> 0x0646 }
        r0 = r0.toString();	 Catch:{ all -> 0x0646 }
        com.huawei.android.pushagent.a.a.c.a(r1, r0);	 Catch:{ all -> 0x0646 }
        if (r2 == 0) goto L_0x00c8;
    L_0x01ea:
        r2.close();	 Catch:{ IOException -> 0x01ef }
        goto L_0x00c8;
    L_0x01ef:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "zfile.close error:";
        r2 = r2.append(r3);
        r0 = r0.getMessage();
        r0 = r2.append(r0);
        r0 = r0.toString();
        com.huawei.android.pushagent.a.a.c.a(r1, r0);
        goto L_0x00c8;
    L_0x0210:
        if (r1 == 0) goto L_0x0215;
    L_0x0212:
        r1.close();	 Catch:{ IOException -> 0x028e, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x0215:
        if (r3 == 0) goto L_0x021a;
    L_0x0217:
        r3.close();	 Catch:{ IOException -> 0x02f6, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x021a:
        if (r4 == 0) goto L_0x021f;
    L_0x021c:
        r4.close();	 Catch:{ IOException -> 0x035e, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x021f:
        if (r5 == 0) goto L_0x0038;
    L_0x0221:
        r5.close();	 Catch:{ IOException -> 0x0226, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0038;
    L_0x0226:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "tempFOS.close error:";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0038;
    L_0x0247:
        r0 = move-exception;
    L_0x0248:
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x037f }
        r2.<init>();	 Catch:{ all -> 0x037f }
        r3 = "upZipFile error:";
        r2 = r2.append(r3);	 Catch:{ all -> 0x037f }
        r0 = r0.getMessage();	 Catch:{ all -> 0x037f }
        r0 = r2.append(r0);	 Catch:{ all -> 0x037f }
        r0 = r0.toString();	 Catch:{ all -> 0x037f }
        com.huawei.android.pushagent.a.a.c.a(r1, r0);	 Catch:{ all -> 0x037f }
        if (r6 == 0) goto L_0x00c8;
    L_0x0268:
        r6.close();	 Catch:{ IOException -> 0x026d }
        goto L_0x00c8;
    L_0x026d:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "zfile.close error:";
        r2 = r2.append(r3);
        r0 = r0.getMessage();
        r0 = r2.append(r0);
        r0 = r0.toString();
        com.huawei.android.pushagent.a.a.c.a(r1, r0);
        goto L_0x00c8;
    L_0x028e:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r9 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r9.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r10 = "zFileIn.close error:";
        r9 = r9.append(r10);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r9.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0215;
    L_0x02af:
        r0 = move-exception;
    L_0x02b0:
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x037f }
        r2.<init>();	 Catch:{ all -> 0x037f }
        r3 = "upZipFile error:";
        r2 = r2.append(r3);	 Catch:{ all -> 0x037f }
        r0 = r0.getMessage();	 Catch:{ all -> 0x037f }
        r0 = r2.append(r0);	 Catch:{ all -> 0x037f }
        r0 = r0.toString();	 Catch:{ all -> 0x037f }
        com.huawei.android.pushagent.a.a.c.a(r1, r0);	 Catch:{ all -> 0x037f }
        if (r6 == 0) goto L_0x00c8;
    L_0x02d0:
        r6.close();	 Catch:{ IOException -> 0x02d5 }
        goto L_0x00c8;
    L_0x02d5:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "zfile.close error:";
        r2 = r2.append(r3);
        r0 = r0.getMessage();
        r0 = r2.append(r0);
        r0 = r0.toString();
        com.huawei.android.pushagent.a.a.c.a(r1, r0);
        goto L_0x00c8;
    L_0x02f6:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r9 = "is.close error:";
        r3 = r3.append(r9);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x021a;
    L_0x0317:
        r0 = move-exception;
    L_0x0318:
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x037f }
        r2.<init>();	 Catch:{ all -> 0x037f }
        r3 = "upZipFile error:";
        r2 = r2.append(r3);	 Catch:{ all -> 0x037f }
        r0 = r0.getMessage();	 Catch:{ all -> 0x037f }
        r0 = r2.append(r0);	 Catch:{ all -> 0x037f }
        r0 = r0.toString();	 Catch:{ all -> 0x037f }
        com.huawei.android.pushagent.a.a.c.a(r1, r0);	 Catch:{ all -> 0x037f }
        if (r6 == 0) goto L_0x00c8;
    L_0x0338:
        r6.close();	 Catch:{ IOException -> 0x033d }
        goto L_0x00c8;
    L_0x033d:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "zfile.close error:";
        r2 = r2.append(r3);
        r0 = r0.getMessage();
        r0 = r2.append(r0);
        r0 = r0.toString();
        com.huawei.android.pushagent.a.a.c.a(r1, r0);
        goto L_0x00c8;
    L_0x035e:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "os.close error:";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x021f;
    L_0x037f:
        r0 = move-exception;
    L_0x0380:
        if (r6 == 0) goto L_0x0385;
    L_0x0382:
        r6.close();	 Catch:{ IOException -> 0x0621 }
    L_0x0385:
        throw r0;
    L_0x0386:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r9 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r9.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r10 = "zFileIn.close error:";
        r9 = r9.append(r10);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r9.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0196;
    L_0x03a7:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r9 = "is.close error:";
        r3 = r3.append(r9);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x019b;
    L_0x03c8:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "os.close error:";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x01a0;
    L_0x03e9:
        r0 = move-exception;
        r1 = r2;
        r3 = r2;
        r4 = r2;
        r5 = r2;
    L_0x03ee:
        r9 = "PushSelfShowLog";
        r10 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0668 }
        r10.<init>();	 Catch:{ all -> 0x0668 }
        r11 = "os.write error:";
        r10 = r10.append(r11);	 Catch:{ all -> 0x0668 }
        r0 = r0.getMessage();	 Catch:{ all -> 0x0668 }
        r0 = r10.append(r0);	 Catch:{ all -> 0x0668 }
        r0 = r0.toString();	 Catch:{ all -> 0x0668 }
        com.huawei.android.pushagent.a.a.c.a(r9, r0);	 Catch:{ all -> 0x0668 }
        if (r1 == 0) goto L_0x0411;
    L_0x040e:
        r1.close();	 Catch:{ IOException -> 0x0443, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x0411:
        if (r3 == 0) goto L_0x0416;
    L_0x0413:
        r3.close();	 Catch:{ IOException -> 0x0463, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x0416:
        if (r4 == 0) goto L_0x041b;
    L_0x0418:
        r4.close();	 Catch:{ IOException -> 0x0483, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x041b:
        if (r5 == 0) goto L_0x0038;
    L_0x041d:
        r5.close();	 Catch:{ IOException -> 0x0422, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0038;
    L_0x0422:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "tempFOS.close error:";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0038;
    L_0x0443:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r9 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r9.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r10 = "zFileIn.close error:";
        r9 = r9.append(r10);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r9.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0411;
    L_0x0463:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r9 = "is.close error:";
        r3 = r3.append(r9);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0416;
    L_0x0483:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "os.close error:";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x041b;
    L_0x04a4:
        r0 = move-exception;
        r1 = r2;
        r3 = r2;
        r4 = r2;
        r5 = r2;
    L_0x04a9:
        r9 = "PushSelfShowLog";
        r10 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0668 }
        r10.<init>();	 Catch:{ all -> 0x0668 }
        r11 = "os.write error:";
        r10 = r10.append(r11);	 Catch:{ all -> 0x0668 }
        r0 = r0.getMessage();	 Catch:{ all -> 0x0668 }
        r0 = r10.append(r0);	 Catch:{ all -> 0x0668 }
        r0 = r0.toString();	 Catch:{ all -> 0x0668 }
        com.huawei.android.pushagent.a.a.c.a(r9, r0);	 Catch:{ all -> 0x0668 }
        if (r1 == 0) goto L_0x04cc;
    L_0x04c9:
        r1.close();	 Catch:{ IOException -> 0x04fe, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x04cc:
        if (r3 == 0) goto L_0x04d1;
    L_0x04ce:
        r3.close();	 Catch:{ IOException -> 0x051e, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x04d1:
        if (r4 == 0) goto L_0x04d6;
    L_0x04d3:
        r4.close();	 Catch:{ IOException -> 0x053e, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x04d6:
        if (r5 == 0) goto L_0x0038;
    L_0x04d8:
        r5.close();	 Catch:{ IOException -> 0x04dd, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0038;
    L_0x04dd:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "tempFOS.close error:";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0038;
    L_0x04fe:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r9 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r9.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r10 = "zFileIn.close error:";
        r9 = r9.append(r10);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r9.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x04cc;
    L_0x051e:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r9 = "is.close error:";
        r3 = r3.append(r9);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x04d1;
    L_0x053e:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "os.close error:";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r3.append(r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r0 = r0.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r1, r0);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x04d6;
    L_0x055f:
        r0 = move-exception;
        r1 = r2;
        r3 = r2;
        r4 = r2;
        r5 = r2;
    L_0x0564:
        if (r1 == 0) goto L_0x0569;
    L_0x0566:
        r1.close();	 Catch:{ IOException -> 0x0579, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x0569:
        if (r3 == 0) goto L_0x056e;
    L_0x056b:
        r3.close();	 Catch:{ IOException -> 0x0599, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x056e:
        if (r4 == 0) goto L_0x0573;
    L_0x0570:
        r4.close();	 Catch:{ IOException -> 0x05b9, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x0573:
        if (r5 == 0) goto L_0x0578;
    L_0x0575:
        r5.close();	 Catch:{ IOException -> 0x05d9, ZipException -> 0x01c8, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x0578:
        throw r0;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
    L_0x0579:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r7 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r7.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r8 = "zFileIn.close error:";
        r7 = r7.append(r8);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r7.append(r1);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r2, r1);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0569;
    L_0x0599:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r7 = "is.close error:";
        r3 = r3.append(r7);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r3.append(r1);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r2, r1);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x056e;
    L_0x05b9:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "os.close error:";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r3.append(r1);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r2, r1);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0573;
    L_0x05d9:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r3.<init>();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r4 = "tempFOS.close error:";
        r3 = r3.append(r4);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.getMessage();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r3.append(r1);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        r1 = r1.toString();	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        com.huawei.android.pushagent.a.a.c.a(r2, r1);	 Catch:{ ZipException -> 0x01c8, IOException -> 0x0247, IllegalStateException -> 0x02af, NoSuchElementException -> 0x0317 }
        goto L_0x0578;
    L_0x05f9:
        if (r6 == 0) goto L_0x00c8;
    L_0x05fb:
        r6.close();	 Catch:{ IOException -> 0x0600 }
        goto L_0x00c8;
    L_0x0600:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "zfile.close error:";
        r2 = r2.append(r3);
        r0 = r0.getMessage();
        r0 = r2.append(r0);
        r0 = r0.toString();
        com.huawei.android.pushagent.a.a.c.a(r1, r0);
        goto L_0x00c8;
    L_0x0621:
        r1 = move-exception;
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "zfile.close error:";
        r3 = r3.append(r4);
        r1 = r1.getMessage();
        r1 = r3.append(r1);
        r1 = r1.toString();
        com.huawei.android.pushagent.a.a.c.a(r2, r1);
        goto L_0x0385;
    L_0x0642:
        r0 = move-exception;
        r6 = r2;
        goto L_0x0380;
    L_0x0646:
        r0 = move-exception;
        r6 = r2;
        goto L_0x0380;
    L_0x064a:
        r0 = move-exception;
        r6 = r2;
        goto L_0x0318;
    L_0x064e:
        r0 = move-exception;
        r6 = r2;
        goto L_0x02b0;
    L_0x0652:
        r0 = move-exception;
        r6 = r2;
        goto L_0x0248;
    L_0x0656:
        r0 = move-exception;
        goto L_0x01ca;
    L_0x0659:
        r0 = move-exception;
        r3 = r2;
        r4 = r2;
        r5 = r2;
        goto L_0x0564;
    L_0x065f:
        r0 = move-exception;
        r3 = r2;
        r4 = r2;
        goto L_0x0564;
    L_0x0664:
        r0 = move-exception;
        r3 = r2;
        goto L_0x0564;
    L_0x0668:
        r0 = move-exception;
        goto L_0x0564;
    L_0x066b:
        r0 = move-exception;
        r3 = r2;
        r4 = r2;
        r5 = r2;
        goto L_0x04a9;
    L_0x0671:
        r0 = move-exception;
        r3 = r2;
        r4 = r2;
        goto L_0x04a9;
    L_0x0676:
        r0 = move-exception;
        r3 = r2;
        goto L_0x04a9;
    L_0x067a:
        r0 = move-exception;
        goto L_0x04a9;
    L_0x067d:
        r0 = move-exception;
        r3 = r2;
        r4 = r2;
        r5 = r2;
        goto L_0x03ee;
    L_0x0683:
        r0 = move-exception;
        r3 = r2;
        r4 = r2;
        goto L_0x03ee;
    L_0x0688:
        r0 = move-exception;
        r3 = r2;
        goto L_0x03ee;
    L_0x068c:
        r0 = move-exception;
        goto L_0x03ee;
    L_0x068f:
        r0 = move-exception;
        r1 = r2;
        r3 = r2;
        r4 = r2;
        r5 = r2;
        goto L_0x0173;
    L_0x0696:
        r0 = move-exception;
        r3 = r2;
        r4 = r2;
        r5 = r2;
        goto L_0x0173;
    L_0x069c:
        r0 = move-exception;
        r3 = r2;
        r4 = r2;
        goto L_0x0173;
    L_0x06a1:
        r0 = move-exception;
        r3 = r2;
        goto L_0x0173;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.utils.b.a.a():void");
    }
}
