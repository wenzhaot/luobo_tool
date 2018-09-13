package anet.channel.strategy;

import android.content.Context;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.util.ALog;
import anet.channel.util.f;
import com.feng.car.activity.CameraPreviewActivity;
import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

/* compiled from: Taobao */
class l {
    private static File a = null;
    private static volatile boolean b = false;
    private static FileFilter c = new m();
    private static Comparator<File> d = new n();

    l() {
    }

    public static void a(Context context) {
        if (context != null) {
            try {
                a = new File(context.getExternalFilesDir(null), "awcn_strategy");
                if (!a(a)) {
                    a = new File(context.getFilesDir(), "awcn_strategy");
                    if (!a(a)) {
                        ALog.e("awcn.StrategySerializeHelper", "create directory failed!!!", null, "dir", a.getAbsolutePath());
                    }
                }
                if (!GlobalAppRuntimeInfo.isTargetProcess()) {
                    String currentProcess = GlobalAppRuntimeInfo.getCurrentProcess();
                    a = new File(a, currentProcess.substring(currentProcess.indexOf(58) + 1));
                    if (!a(a)) {
                        ALog.e("awcn.StrategySerializeHelper", "create directory failed!!!", null, "dir", a.getAbsolutePath());
                    }
                }
                ALog.i("awcn.StrategySerializeHelper", "StrateyFolder", null, CameraPreviewActivity.PATH, a.getAbsolutePath());
                if (b) {
                    a();
                    b = false;
                    return;
                }
                c();
            } catch (Throwable th) {
                ALog.e("awcn.StrategySerializeHelper", "StrategySerializeHelper initialize failed!!!", null, th, new Object[0]);
            }
        }
    }

    private static boolean a(File file) {
        if (file == null || file.exists()) {
            return true;
        }
        return file.mkdir();
    }

    public static File a(String str) {
        a(a);
        return new File(a, str);
    }

    static synchronized void a() {
        synchronized (l.class) {
            ALog.i("awcn.StrategySerializeHelper", "clear start.", null, new Object[0]);
            if (a == null) {
                ALog.w("awcn.StrategySerializeHelper", "folder path not initialized, wait to clear", null, new Object[0]);
                b = true;
            } else {
                File[] listFiles = a.listFiles();
                if (listFiles != null) {
                    for (File file : listFiles) {
                        if (file.isFile()) {
                            file.delete();
                        }
                    }
                    ALog.i("awcn.StrategySerializeHelper", "clear end.", null, new Object[0]);
                }
            }
        }
    }

    static synchronized File[] b() {
        File[] fileArr;
        synchronized (l.class) {
            if (a == null) {
                fileArr = null;
            } else {
                fileArr = a.listFiles(c);
                if (fileArr != null) {
                    Arrays.sort(fileArr, d);
                }
            }
        }
        return fileArr;
    }

    static synchronized void c() {
        int i = 0;
        synchronized (l.class) {
            File[] b = b();
            if (b != null) {
                for (File file : b) {
                    if (System.currentTimeMillis() - file.lastModified() >= 604800000) {
                        file.delete();
                    } else if (!file.getName().equalsIgnoreCase("config")) {
                        int i2 = i + 1;
                        if (((long) i) > 10) {
                            file.delete();
                        }
                        i = i2;
                    }
                }
            }
        }
    }

    static synchronized void a(Serializable serializable, String str) {
        synchronized (l.class) {
            f.a(serializable, a(str));
        }
    }

    static synchronized <T> T b(String str) {
        T a;
        synchronized (l.class) {
            a = f.a(a(str));
        }
        return a;
    }
}
