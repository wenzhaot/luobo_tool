package com.umeng.socialize.b.b;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import com.umeng.socialize.utils.SLog;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

/* compiled from: CacheUtil */
public class a {

    /* compiled from: CacheUtil */
    private static class a implements Comparator<File> {
        private a() {
        }

        /* renamed from: a */
        public int compare(File file, File file2) {
            if (file.lastModified() > file2.lastModified()) {
                return 1;
            }
            if (file.lastModified() == file2.lastModified()) {
                return 0;
            }
            return -1;
        }
    }

    public static void a() {
        Object obj = (Environment.getExternalStorageDirectory() == null || TextUtils.isEmpty(Environment.getExternalStorageDirectory().getPath())) ? null : 1;
        if (obj != null) {
            c.d = Environment.getExternalStorageDirectory().getPath() + File.separator + c.e + File.separator;
        } else {
            c.d = Environment.getDataDirectory().getPath() + File.separator + c.e + File.separator;
        }
        File file = new File(c.d);
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            a(c.d);
        } catch (Throwable e) {
            SLog.error(e);
        }
    }

    private static void a(String str) {
        int i = 0;
        File[] listFiles = new File(str).listFiles();
        if (listFiles != null && listFiles.length != 0) {
            int length;
            int i2 = 0;
            for (File length2 : listFiles) {
                i2 = (int) (((long) i2) + length2.length());
            }
            if (i2 > 0 || 40 > c()) {
                length = listFiles.length;
                Arrays.sort(listFiles, new a());
                while (i < length) {
                    listFiles[i].delete();
                    i++;
                }
            }
        }
    }

    private static int c() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return (int) ((((double) statFs.getBlockSize()) * ((double) statFs.getAvailableBlocks())) / 1048576.0d);
    }

    public static void b() {
        a();
    }
}
