package com.taobao.accs.net;

import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.Session;
import anet.channel.SessionCenter;
import anet.channel.entity.ConnType.TypeLevel;
import com.feng.library.okhttp.utils.OkHttpUtils;
import com.taobao.accs.common.Constants;
import com.taobao.accs.data.Message;
import com.taobao.accs.data.Message.b;
import com.taobao.accs.ut.monitor.TrafficsMonitor.a;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.BaseMonitor;

/* compiled from: Taobao */
class l implements Runnable {
    final /* synthetic */ Message a;
    final /* synthetic */ k b;

    l(k kVar, Message message) {
        this.b = kVar;
        this.a = message;
    }

    public void run() {
        if (this.a != null) {
            if (this.a.e() != null) {
                this.a.e().onTakeFromQueue();
            }
            int a = this.a.a();
            try {
                boolean z;
                if (ALog.isPrintLog(Level.I)) {
                    ALog.i(this.b.d(), "try send:" + b.b(a) + " dataId:" + this.a.q, "appkey", this.b.b);
                }
                if (a != 1) {
                    ALog.e(this.b.d(), "skip msg type" + b.b(a), new Object[0]);
                    z = true;
                } else if (this.a.f == null) {
                    this.b.e.a(this.a, -5);
                    z = true;
                } else {
                    SessionCenter instance = SessionCenter.getInstance(this.b.i.getAppKey());
                    this.b.a(instance, this.a.f.getHost(), false);
                    Session session = instance.get(this.a.f.toString(), TypeLevel.SPDY, OkHttpUtils.DEFAULT_MILLISECONDS);
                    if (session != null) {
                        byte[] a2 = this.a.a(this.b.d, this.b.c);
                        String d;
                        String str;
                        Object[] objArr;
                        int i;
                        if ("accs".equals(this.a.F)) {
                            d = this.b.d();
                            str = "send data ";
                            objArr = new Object[10];
                            objArr[0] = "len";
                            if (a2 == null) {
                                i = 0;
                            } else {
                                i = a2.length;
                            }
                            objArr[1] = Integer.valueOf(i);
                            objArr[2] = Constants.KEY_DATA_ID;
                            objArr[3] = this.a.b();
                            objArr[4] = "command";
                            objArr[5] = this.a.t;
                            objArr[6] = "host";
                            objArr[7] = this.a.f;
                            objArr[8] = "utdid";
                            objArr[9] = this.b.j;
                            ALog.e(d, str, objArr);
                        } else if (ALog.isPrintLog(Level.I)) {
                            d = this.b.d();
                            str = "send data ";
                            objArr = new Object[10];
                            objArr[0] = "len";
                            if (a2 == null) {
                                i = 0;
                            } else {
                                i = a2.length;
                            }
                            objArr[1] = Integer.valueOf(i);
                            objArr[2] = Constants.KEY_DATA_ID;
                            objArr[3] = this.a.b();
                            objArr[4] = "command";
                            objArr[5] = this.a.t;
                            objArr[6] = "host";
                            objArr[7] = this.a.f;
                            objArr[8] = "utdid";
                            objArr[9] = this.b.j;
                            ALog.i(d, str, objArr);
                        }
                        this.a.a(System.currentTimeMillis());
                        if (a2.length <= 16384 || this.a.t.intValue() == 102) {
                            this.b.e.a(this.a);
                            if (this.a.c) {
                                this.b.l.put(Integer.valueOf(this.a.d()), this.a);
                            }
                            session.sendCustomFrame(this.a.d(), a2, 200);
                            if (this.a.e() != null) {
                                this.a.e().onSendData();
                            }
                            this.b.a(this.a.b(), (long) this.a.Q);
                            this.b.e.a(new a(this.a.F, GlobalAppRuntimeInfo.isAppBackground(), this.a.f.toString(), (long) a2.length));
                        } else {
                            this.b.e.a(this.a, -4);
                        }
                        z = true;
                    } else {
                        z = false;
                    }
                }
                if (!z) {
                    if (a == 1) {
                        if (this.a.g() || !this.b.a(this.a, 2000)) {
                            this.b.e.a(this.a, -11);
                        }
                        if (this.a.P == 1 && this.a.e() != null) {
                            com.taobao.accs.utl.b.a("accs", BaseMonitor.COUNT_POINT_RESEND, "total_accs", 0.0d);
                        }
                    } else {
                        this.b.e.a(this.a, -11);
                    }
                }
                if ("accs".equals(this.a.F)) {
                    ALog.e(this.b.d(), "sendSucc " + z + " dataId:" + this.a.b(), new Object[0]);
                } else if (ALog.isPrintLog(Level.D)) {
                    ALog.d(this.b.d(), "sendSucc " + z + " dataId:" + this.a.b(), new Object[0]);
                }
            } catch (Throwable th) {
                if ("accs".equals(this.a.F)) {
                    ALog.e(this.b.d(), "sendSucc " + true + " dataId:" + this.a.b(), new Object[0]);
                } else if (ALog.isPrintLog(Level.D)) {
                    ALog.d(this.b.d(), "sendSucc " + true + " dataId:" + this.a.b(), new Object[0]);
                }
            }
        }
    }
}
