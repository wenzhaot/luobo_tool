package com.baidu.platform.comapi.map;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Message;
import com.baidu.mapapi.UIMsg.m_AppUI;

class k extends Handler {
    final /* synthetic */ j a;

    k(j jVar) {
        this.a = jVar;
    }

    public void handleMessage(Message message) {
        super.handleMessage(message);
        if (this.a.g == null || ((Long) message.obj).longValue() != this.a.g.h) {
            return;
        }
        if (message.what == m_AppUI.MSG_APP_SAVESCREEN) {
            for (l lVar : this.a.g.f) {
                Bitmap bitmap = null;
                if (message.arg2 == 1) {
                    int[] iArr = new int[(this.a.d * this.a.e)];
                    int[] iArr2 = new int[(this.a.d * this.a.e)];
                    if (this.a.g.g != null) {
                        int[] a = this.a.g.g.a(iArr, this.a.d, this.a.e);
                        for (int i = 0; i < this.a.e; i++) {
                            for (int i2 = 0; i2 < this.a.d; i2++) {
                                int i3 = a[(this.a.d * i) + i2];
                                iArr2[(((this.a.e - i) - 1) * this.a.d) + i2] = ((i3 & -16711936) | ((i3 << 16) & 16711680)) | ((i3 >> 16) & 255);
                            }
                        }
                        bitmap = Bitmap.createBitmap(iArr2, this.a.d, this.a.e, Config.ARGB_8888);
                    } else {
                        return;
                    }
                }
                lVar.a(bitmap);
            }
        } else if (message.what == 39) {
            if (this.a.g != null) {
                if (message.arg1 == 100) {
                    this.a.g.B();
                } else if (message.arg1 == 200) {
                    this.a.g.L();
                } else if (message.arg1 == 1) {
                    this.a.requestRender();
                } else if (message.arg1 == 0) {
                    this.a.requestRender();
                    if (!(this.a.g.b() || this.a.getRenderMode() == 0)) {
                        this.a.setRenderMode(0);
                    }
                } else if (message.arg1 == 2) {
                    for (l lVar2 : this.a.g.f) {
                        lVar2.c();
                    }
                }
                if (!this.a.g.i && this.a.e > 0 && this.a.d > 0 && this.a.g.b(0, 0) != null) {
                    this.a.g.i = true;
                    for (l lVar22 : this.a.g.f) {
                        lVar22.b();
                    }
                }
                for (l lVar222 : this.a.g.f) {
                    lVar222.a();
                }
                if (this.a.g.q()) {
                    for (l lVar2222 : this.a.g.f) {
                        if (this.a.g.E().a >= 18.0f) {
                            lVar2222.a(true);
                        } else {
                            lVar2222.a(false);
                        }
                    }
                }
            }
        } else if (message.what == 41) {
            if (this.a.g == null) {
                return;
            }
            if (this.a.g.l || this.a.g.m) {
                for (l lVar22222 : this.a.g.f) {
                    lVar22222.b(this.a.g.E());
                    if (this.a.g.q()) {
                        if (this.a.g.E().a >= 18.0f) {
                            lVar22222.a(true);
                        } else {
                            lVar22222.a(false);
                        }
                    }
                }
            }
        } else if (message.what == 999) {
            for (l lVar222222 : this.a.g.f) {
                lVar222222.e();
            }
        } else if (message.what == 50) {
            for (l lVar2222222 : this.a.g.f) {
                if (message.arg1 == 0) {
                    lVar2222222.a(false);
                } else if (message.arg1 == 1) {
                    if (this.a.g.E().a >= 18.0f) {
                        lVar2222222.a(true);
                    } else {
                        lVar2222222.a(false);
                    }
                }
            }
        }
    }
}
