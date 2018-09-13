package anetwork.channel.aidl.adapter;

import android.os.Build.VERSION;
import android.os.RemoteException;
import anet.channel.util.ErrorConstant;
import anetwork.channel.NetworkCallBack.FinishListener;
import anetwork.channel.NetworkCallBack.InputStreamListener;
import anetwork.channel.NetworkCallBack.ResponseCodeListener;
import anetwork.channel.NetworkEvent.FinishEvent;
import anetwork.channel.aidl.Connection.Stub;
import anetwork.channel.aidl.ParcelableFuture;
import anetwork.channel.aidl.ParcelableInputStream;
import anetwork.channel.entity.RequestConfig;
import anetwork.channel.statist.StatisticData;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* compiled from: Taobao */
public class ConnectionDelegate extends Stub implements FinishListener, InputStreamListener, ResponseCodeListener {
    private RequestConfig config;
    private String desc;
    private ParcelableFuture future;
    private Map<String, List<String>> header;
    private ParcelableInputStreamImpl inputStream;
    private StatisticData statisticData;
    private int statusCode;
    private CountDownLatch statusLatch = new CountDownLatch(1);
    private CountDownLatch streamLatch = new CountDownLatch(1);

    public ConnectionDelegate(int i) {
        this.statusCode = i;
        this.desc = ErrorConstant.getErrMsg(i);
    }

    public ConnectionDelegate(RequestConfig requestConfig) {
        this.config = requestConfig;
    }

    public String getDesc() throws RemoteException {
        waitCountDownLatch(this.statusLatch);
        return this.desc;
    }

    public StatisticData getStatisticData() {
        return this.statisticData;
    }

    public ParcelableInputStream getInputStream() throws RemoteException {
        waitCountDownLatch(this.streamLatch);
        return this.inputStream;
    }

    public int getStatusCode() throws RemoteException {
        waitCountDownLatch(this.statusLatch);
        return this.statusCode;
    }

    public Map<String, List<String>> getConnHeadFields() throws RemoteException {
        waitCountDownLatch(this.statusLatch);
        return this.header;
    }

    public void cancel() throws RemoteException {
        if (this.future != null) {
            this.future.cancel(true);
        }
    }

    public void setFuture(ParcelableFuture parcelableFuture) {
        this.future = parcelableFuture;
    }

    public void onFinished(FinishEvent finishEvent, Object obj) {
        if (this.inputStream != null) {
            this.inputStream.writeEnd();
        }
        this.statusCode = finishEvent.getHttpCode();
        this.desc = finishEvent.getDesc() != null ? finishEvent.getDesc() : ErrorConstant.getErrMsg(this.statusCode);
        this.statisticData = finishEvent.getStatisticData();
        this.streamLatch.countDown();
        this.statusLatch.countDown();
    }

    private void waitCountDownLatch(CountDownLatch countDownLatch) throws RemoteException {
        try {
            if (!countDownLatch.await((long) this.config.getWaitTimeout(), TimeUnit.MILLISECONDS)) {
                if (this.future != null) {
                    this.future.cancel(true);
                }
                throw buildRemoteException("wait time out");
            }
        } catch (InterruptedException e) {
            throw buildRemoteException("thread interrupt");
        }
    }

    private RemoteException buildRemoteException(String str) {
        if (VERSION.SDK_INT >= 15) {
            return new RemoteException(str);
        }
        return new RemoteException();
    }

    public boolean onResponseCode(int i, Map<String, List<String>> map, Object obj) {
        this.statusCode = i;
        this.desc = ErrorConstant.getErrMsg(this.statusCode);
        this.header = map;
        this.statusLatch.countDown();
        return false;
    }

    public void onInputStreamGet(ParcelableInputStream parcelableInputStream, Object obj) {
        this.inputStream = (ParcelableInputStreamImpl) parcelableInputStream;
        this.streamLatch.countDown();
    }
}
