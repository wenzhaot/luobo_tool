package anetwork.channel.aidl;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import anet.channel.util.ALog;
import anetwork.channel.aidl.RemoteNetwork.Stub;
import anetwork.channel.degrade.DegradableNetworkDelegate;
import anetwork.channel.http.HttpNetworkDelegate;
import com.stub.StubApp;

/* compiled from: Taobao */
public class NetworkService extends Service {
    private static final String TAG = "anet.NetworkService";
    private Context context;
    private Stub[] networkDelegates = new Stub[2];
    IRemoteNetworkGetter.Stub stub = new IRemoteNetworkGetter.Stub() {
        public RemoteNetwork get(int i) throws RemoteException {
            if (NetworkService.this.networkDelegates[i] == null) {
                switch (i) {
                    case 1:
                        NetworkService.this.networkDelegates[i] = new DegradableNetworkDelegate(NetworkService.this.context);
                        break;
                    default:
                        NetworkService.this.networkDelegates[i] = new HttpNetworkDelegate(NetworkService.this.context);
                        break;
                }
            }
            return NetworkService.this.networkDelegates[i];
        }
    };

    public IBinder onBind(Intent intent) {
        this.context = StubApp.getOrigApplicationContext(getApplicationContext());
        if (ALog.isPrintLog(2)) {
            ALog.i(TAG, "onBind:" + intent.getAction(), null, new Object[0]);
        }
        if (IRemoteNetworkGetter.class.getName().equals(intent.getAction())) {
            return this.stub;
        }
        return null;
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return 2;
    }

    public void onDestroy() {
    }
}
