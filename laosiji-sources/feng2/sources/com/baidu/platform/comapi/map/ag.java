package com.baidu.platform.comapi.map;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Message;
import com.baidu.mapapi.UIMsg.m_AppUI;

class ag extends Handler {
    final /* synthetic */ af a;

    ag(af afVar) {
        this.a = afVar;
    }

    public void handleMessage(Message message) {
        super.handleMessage(message);
        if (this.a.f != null && this.a.f.g != null && ((Long) message.obj).longValue() == this.a.f.h) {
            if (message.what == m_AppUI.MSG_APP_SAVESCREEN) {
                for (l lVar : this.a.f.f) {
                    Bitmap bitmap = null;
                    if (message.arg2 == 1) {
                        int[] iArr = new int[(af.a * af.b)];
                        int[] iArr2 = new int[(af.a * af.b)];
                        if (this.a.f.g != null) {
                            int[] a = this.a.f.g.a(iArr, af.a, af.b);
                            for (int i = 0; i < af.b; i++) {
                                for (int i2 = 0; i2 < af.a; i2++) {
                                    int i3 = a[(af.a * i) + i2];
                                    iArr2[(((af.b - i) - 1) * af.a) + i2] = ((i3 & -16711936) | ((i3 << 16) & 16711680)) | ((i3 >> 16) & 255);
                                }
                            }
                            bitmap = Bitmap.createBitmap(iArr2, af.a, af.b, Config.ARGB_8888);
                        } else {
                            return;
                        }
                    }
                    lVar.a(bitmap);
                }
            } else if (message.what == 39) {
                if (this.a.f != null) {
                    if (message.arg1 == 100) {
                        this.a.f.B();
                    } else if (message.arg1 == 200) {
                        this.a.f.L();
                    } else if (message.arg1 == 1) {
                        if (this.a.e != null) {
                            this.a.e.a();
                        }
                    } else if (message.arg1 == 0) {
                        if (this.a.e != null) {
                            this.a.e.a();
                        }
                    } else if (message.arg1 == 2) {
                        for (l lVar2 : this.a.f.f) {
                            lVar2.c();
                        }
                    }
                    if (!this.a.f.i && af.b > 0 && af.a > 0 && this.a.f.b(0, 0) != null) {
                        this.a.f.i = true;
                        for (l lVar22 : this.a.f.f) {
                            lVar22.b();
                        }
                    }
                    for (l lVar222 : this.a.f.f) {
                        lVar222.a();
                    }
                    if (this.a.f.q()) {
                        for (l lVar2222 : this.a.f.f) {
                            if (this.a.f.E().a >= 18.0f) {
                                lVar2222.a(true);
                            } else {
                                lVar2222.a(false);
                            }
                        }
                    }
                }
            } else if (message.what == 41) {
                if (this.a.f == null) {
                    return;
                }
                if (this.a.f.l || this.a.f.m) {
                    for (l lVar22222 : this.a.f.f) {
                        lVar22222.b(this.a.f.E());
                        if (this.a.f.q()) {
                            if (this.a.f.E().a >= 18.0f) {
                                lVar22222.a(true);
                            } else {
                                lVar22222.a(false);
                            }
                        }
                    }
                }
            } else if (message.what == 999) {
                for (l lVar222222 : this.a.f.f) {
                    lVar222222.e();
                }
            } else if (message.what == 50) {
                for (l lVar2222222 : this.a.f.f) {
                    if (message.arg1 == 0) {
                        lVar2222222.a(false);
                    } else if (message.arg1 == 1) {
                        if (this.a.f.E().a >= 18.0f) {
                            lVar2222222.a(true);
                        } else {
                            lVar2222222.a(false);
                        }
                    }
                }
            }
        }
    }
}
