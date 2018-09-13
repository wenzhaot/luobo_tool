package com.tencent.liteav.videoediter.a;

import android.os.Handler;
import com.tencent.liteav.c.e;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

/* compiled from: TXMultiMediaComposer */
public class c implements Runnable {
    private a a;
    private Handler b;
    private List<String> c;
    private String d;
    private long e;
    private long f;
    private boolean g;
    private long h;
    private Runnable i;

    /* compiled from: TXMultiMediaComposer */
    public interface a {
        void a(float f);

        void a(int i, String str);
    }

    public void run() {
        if (!this.g) {
            return;
        }
        if (this.c == null || this.c.size() <= 0) {
            a(-1, "未设置视频源");
        } else if (this.d == null || this.d.isEmpty()) {
            a(-1, "未设置输出路径");
        } else {
            d dVar = new d();
            a aVar = new a();
            try {
                dVar.a(this.c);
                aVar.a(this.d);
                long c = dVar.c();
                dVar.f();
                b bVar = new b();
                bVar.a((String) this.c.get(0));
                if (bVar.a() != null) {
                    aVar.a(bVar.a());
                }
                if (bVar.b() != null) {
                    aVar.b(bVar.b());
                }
                bVar.e();
                int c2 = aVar.c();
                if (c2 < 0) {
                    String str;
                    switch (c2) {
                        case -8:
                            str = "封装器AddAudioTrack错误";
                            break;
                        case -7:
                            str = "不支持的音频格式";
                            break;
                        case -6:
                            str = "封装器AddVideoTrack错误";
                            break;
                        case -5:
                            str = "不支持的视频格式";
                            break;
                        case -4:
                            str = "创建封装器失败";
                            break;
                        default:
                            str = "封装器启动失败";
                            break;
                    }
                    a(-1, str);
                    return;
                }
                dVar.a(this.e);
                e eVar = new e();
                eVar.a(ByteBuffer.allocate(512000));
                while (true) {
                    dVar.a(eVar);
                    if ((eVar.f() & 4) == 0) {
                        if (this.f > 0 && eVar.e() > this.f) {
                            break;
                        } else if (eVar.a().startsWith("video")) {
                            aVar.a(eVar.b(), eVar.o());
                            this.h++;
                            if (this.h >= 50) {
                                a(eVar.o().presentationTimeUs - this.e, this.f < 0 ? c : this.f - this.e);
                                this.h = 0;
                            }
                        } else {
                            aVar.b(eVar.b(), eVar.o());
                        }
                    }
                    if (this.g) {
                        if ((eVar.f() & 4) != 0) {
                            break;
                        }
                    }
                    break;
                }
                if (aVar.d() < 0) {
                    a(-1, "停止封装器失败");
                } else if (this.g) {
                    a(0, "");
                }
                dVar.e();
                this.g = false;
            } catch (IOException e) {
                e.printStackTrace();
                a(-1, "获取数据格式失败");
            } finally {
                dVar.e();
                this.g = false;
            }
        }
    }

    private void a(long j, long j2) {
        float f = 1.0f;
        if (this.i == null && this.a != null) {
            if (j2 > 0 && j <= j2) {
                f = (1.0f * ((float) j)) / ((float) j2);
            }
            this.i = new Runnable() {
                public void run() {
                    if (c.this.a != null) {
                        c.this.a.a(f);
                    }
                    c.this.i = null;
                }
            };
            this.b.post(this.i);
        }
    }

    private void a(final int i, final String str) {
        this.b.post(new Runnable() {
            public void run() {
                if (c.this.a != null) {
                    c.this.a.a(i, str);
                }
            }
        });
    }
}
