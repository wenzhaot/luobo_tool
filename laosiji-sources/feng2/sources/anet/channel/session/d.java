package anet.channel.session;

import android.content.Context;
import anet.channel.RequestCb;
import anet.channel.Session;
import anet.channel.Session.Status;
import anet.channel.a.c;
import anet.channel.entity.ConnType;
import anet.channel.entity.a;
import anet.channel.request.Cancelable;
import anet.channel.request.FutureCancelable;
import anet.channel.request.Request;
import anet.channel.request.Request.Builder;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;
import anet.channel.util.ErrorConstant;
import anet.channel.util.Utils;
import anet.channel.util.e;
import anet.channel.util.h;
import javax.net.ssl.SSLSocketFactory;

/* compiled from: Taobao */
public class d extends Session {
    private SSLSocketFactory a;

    public d(Context context, a aVar) {
        super(context, aVar, aVar.c());
        if (this.mConnStrategy == null) {
            ConnType connType = (this.mHost == null || !this.mHost.startsWith("https")) ? ConnType.HTTP : ConnType.HTTPS;
            this.mConnType = connType;
        } else if (anet.channel.a.b() && this.mConnType.equals(ConnType.HTTPS)) {
            this.a = new h(this.mRealHost);
        }
    }

    public boolean isAvailable() {
        return this.mStatus == Status.AUTH_SUCC;
    }

    public void connect() {
        try {
            ALog.i("awcn.HttpSession", "HttpSession connect", null, "host", this.mHost);
            Builder redirectEnable = new Builder().setUrl(this.mHost).setSeq(this.mSeq).setConnectTimeout((int) (((float) this.mConnTimeout) * Utils.getNetworkTimeFactor())).setReadTimeout((int) (((float) this.mReqTimeout) * Utils.getNetworkTimeFactor())).setRedirectEnable(false);
            if (this.a != null) {
                redirectEnable.setSslSocketFactory(this.a);
            }
            Request build = redirectEnable.build();
            build.setDnsOptimize(this.mIp, this.mPort);
            c.a(new e(this, build), 6);
        } catch (Throwable th) {
            ALog.e("awcn.HttpSession", "HTTP connect fail.", null, th, new Object[0]);
        }
    }

    public void close() {
        a(Status.DISCONNECTED, null);
    }

    public void close(boolean z) {
        this.autoReCreate = false;
        close();
    }

    protected Runnable getRecvTimeOutRunnable() {
        return null;
    }

    public void ping(boolean z) {
    }

    public Cancelable request(Request request, RequestCb requestCb) {
        RequestStatistic requestStatistic;
        FutureCancelable futureCancelable = FutureCancelable.NULL;
        if (request != null) {
            requestStatistic = request.rs;
        } else {
            requestStatistic = new RequestStatistic(this.mRealHost, null);
        }
        requestStatistic.setConnType(this.mConnType);
        if (requestStatistic.start == 0) {
            requestStatistic.start = System.currentTimeMillis();
        }
        if (request == null || requestCb == null) {
            if (requestCb != null) {
                requestCb.onFinish(-102, ErrorConstant.getErrMsg(-102), requestStatistic);
            }
            return futureCancelable;
        }
        try {
            if (request.getSslSocketFactory() == null && this.a != null) {
                request = request.newBuilder().setSslSocketFactory(this.a).build();
                requestStatistic.isSni = "sni";
            }
            request.setDnsOptimize(this.mIp, this.mPort);
            request.setUrlScheme(this.mConnType.isSSL());
            return new FutureCancelable(c.a(new f(this, request, requestStatistic, requestCb), e.a(request)), request.getSeq());
        } catch (Throwable th) {
            if (requestCb != null) {
                requestCb.onFinish(-101, ErrorConstant.formatMsg(-101, th.toString()), requestStatistic);
            }
            return futureCancelable;
        }
    }
}
