package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.statistics.common.DataHelper;
import com.umeng.commonsdk.statistics.common.HelperUtils;
import java.io.File;

/* compiled from: OldUMIDTracker */
public class i extends a {
    private static final String a = "oldumid";
    private Context b;
    private String c = null;
    private String d = null;

    public i(Context context) {
        super(a);
        this.b = context;
    }

    public String f() {
        return this.c;
    }

    public boolean g() {
        return h();
    }

    public boolean h() {
        this.d = UMEnvelopeBuild.imprintProperty(this.b, "umid", null);
        if (!TextUtils.isEmpty(this.d)) {
            this.d = DataHelper.encryptBySHA1(this.d);
            Object readFile = HelperUtils.readFile(new File("/sdcard/Android/data/.um/sysid.dat"));
            Object readFile2 = HelperUtils.readFile(new File("/sdcard/Android/obj/.um/sysid.dat"));
            Object readFile3 = HelperUtils.readFile(new File("/data/local/tmp/.um/sysid.dat"));
            if (TextUtils.isEmpty(readFile)) {
                l();
            } else if (!this.d.equals(readFile)) {
                this.c = readFile;
                return true;
            }
            if (TextUtils.isEmpty(readFile2)) {
                k();
            } else if (!this.d.equals(readFile2)) {
                this.c = readFile2;
                return true;
            }
            if (TextUtils.isEmpty(readFile3)) {
                j();
            } else if (!this.d.equals(readFile3)) {
                this.c = readFile3;
                return true;
            }
        }
        return false;
    }

    public void i() {
        try {
            l();
            k();
            j();
        } catch (Exception e) {
        }
    }

    private void j() {
        try {
            b("/data/local/tmp/.um");
            HelperUtils.writeFile(new File("/data/local/tmp/.um/sysid.dat"), this.d);
        } catch (Throwable th) {
        }
    }

    private void k() {
        try {
            b("/sdcard/Android/obj/.um");
            HelperUtils.writeFile(new File("/sdcard/Android/obj/.um/sysid.dat"), this.d);
        } catch (Throwable th) {
        }
    }

    private void l() {
        try {
            b("/sdcard/Android/data/.um");
            HelperUtils.writeFile(new File("/sdcard/Android/data/.um/sysid.dat"), this.d);
        } catch (Throwable th) {
        }
    }

    private void b(String str) {
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
