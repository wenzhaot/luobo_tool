package com.tencent.liteav.d;

import com.tencent.liteav.b.i;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.videoediter.ffmpeg.jni.TXFFQuickJointerJNI;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: MoovHeaderProcessor */
public class k {
    private static k a;
    private final i b = i.a();

    public static k a() {
        if (a == null) {
            a = new k();
        }
        return a;
    }

    private k() {
    }

    public void b() {
        long currentTimeMillis = System.currentTimeMillis();
        if (!this.b.b() && this.b.c()) {
            File file = new File(i.a().i);
            File file2 = new File(file.getParentFile(), "moov_tmp.mp4");
            if (file2.exists()) {
                file2.delete();
            }
            try {
                file2.createNewFile();
                TXFFQuickJointerJNI tXFFQuickJointerJNI = new TXFFQuickJointerJNI();
                tXFFQuickJointerJNI.setDstPath(file2.getAbsolutePath());
                List arrayList = new ArrayList();
                arrayList.add(file.getAbsolutePath());
                tXFFQuickJointerJNI.setSrcPaths(arrayList);
                Object obj = tXFFQuickJointerJNI.start() == 0 ? 1 : null;
                tXFFQuickJointerJNI.stop();
                tXFFQuickJointerJNI.destroy();
                if (obj == null) {
                    TXCLog.e("MoovHeaderProcessor", "moov: change to moov type video file error!!");
                } else if (file.delete()) {
                    TXCLog.i("MoovHeaderProcessor", "moov: rename file success = " + file2.renameTo(file));
                    TXCLog.d("MoovHeaderProcessor", "doProcessMoovHeader cost time:" + String.valueOf(System.currentTimeMillis() - currentTimeMillis));
                } else {
                    TXCLog.e("MoovHeaderProcessor", "moov: delete original file error!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                TXCLog.e("MoovHeaderProcessor", "moov: create moov tmp file error!");
            }
        }
    }
}
