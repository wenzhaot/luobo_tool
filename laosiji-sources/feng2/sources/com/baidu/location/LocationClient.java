package com.baidu.location;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.text.TextUtils;
import anet.channel.request.Request;
import com.baidu.location.a.i;
import java.util.ArrayList;
import java.util.Iterator;

public final class LocationClient implements com.baidu.location.a.b.b {
    public static final int CONNECT_HOT_SPOT_FALSE = 0;
    public static final int CONNECT_HOT_SPOT_TRUE = 1;
    public static final int CONNECT_HOT_SPOT_UNKNOWN = -1;
    public static final int LOC_DIAGNOSTIC_TYPE_BETTER_OPEN_GPS = 1;
    public static final int LOC_DIAGNOSTIC_TYPE_BETTER_OPEN_WIFI = 2;
    public static final int LOC_DIAGNOSTIC_TYPE_FAIL_UNKNOWN = 9;
    public static final int LOC_DIAGNOSTIC_TYPE_NEED_CHECK_LOC_PERMISSION = 4;
    public static final int LOC_DIAGNOSTIC_TYPE_NEED_CHECK_NET = 3;
    public static final int LOC_DIAGNOSTIC_TYPE_NEED_CLOSE_FLYMODE = 7;
    public static final int LOC_DIAGNOSTIC_TYPE_NEED_INSERT_SIMCARD_OR_OPEN_WIFI = 6;
    public static final int LOC_DIAGNOSTIC_TYPE_NEED_OPEN_PHONE_LOC_SWITCH = 5;
    public static final int LOC_DIAGNOSTIC_TYPE_SERVER_FAIL = 8;
    private static final int MIN_REQUEST_SPAN = 1000;
    private static final int MSG_REG_LISTENER = 5;
    private static final int MSG_REG_LISTENER_V2 = 1300;
    private static final int MSG_REG_NOTIFY_LISTENER = 8;
    private static final int MSG_REMOVE_NOTIFY = 10;
    private static final int MSG_REQ_LOC = 4;
    private static final int MSG_REQ_NOTIFY_LOC = 11;
    private static final int MSG_REQ_OFFLINE_LOC = 12;
    private static final int MSG_REQ_POI = 7;
    private static final int MSG_RIGSTER_NOTIFY = 9;
    private static final int MSG_SET_OPT = 3;
    private static final int MSG_START = 1;
    private static final int MSG_STOP = 2;
    private static final int MSG_UNREG_LISTENER = 6;
    private static final int MSG_UNREG_LISTENER_V2 = 1400;
    private static final String mTAG = "baidu_location_Client";
    private BDLocationListener NotifyLocationListenner = null;
    private boolean clientFirst = false;
    private Boolean firstConnected = Boolean.valueOf(true);
    private boolean inDoorState = false;
    private boolean isScheduled = false;
    private boolean isStop = true;
    private boolean isWaitingForLocation = false;
    private boolean isWaitingLocTag = false;
    private long lastReceiveGpsTime = 0;
    private long lastReceiveLocationTime = 0;
    private Boolean mConfig_map = Boolean.valueOf(false);
    private Boolean mConfig_preimport = Boolean.valueOf(false);
    private ServiceConnection mConnection = new b(this);
    private Context mContext = null;
    private boolean mDebugByDev;
    private boolean mGpsStatus = false;
    private a mHandler;
    private boolean mIsStarted = false;
    private String mKey;
    private BDLocation mLastLocation = null;
    private long mLastRequestTime = 0;
    private ArrayList<BDLocationListener> mLocationListeners = null;
    private ArrayList<BDAbstractLocationListener> mLocationListeners2 = null;
    private final Object mLock = new Object();
    private final Messenger mMessenger;
    private com.baidu.location.d.a mNotifyCache = null;
    private LocationClientOption mOption = new LocationClientOption();
    private String mPackName = null;
    private b mScheduledRequest = null;
    private Messenger mServer = null;
    private com.baidu.location.a.b mloc = null;
    private boolean serverFirst = false;
    private String serviceName = null;

    private class a extends Handler {
        a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            Bundle data;
            int i;
            switch (message.what) {
                case 1:
                    LocationClient.this.onStart();
                    return;
                case 2:
                    LocationClient.this.onStop();
                    return;
                case 3:
                    LocationClient.this.onSetOption(message);
                    return;
                case 4:
                    LocationClient.this.onRequestLocation();
                    return;
                case 5:
                    LocationClient.this.onRegisterListener(message);
                    return;
                case 6:
                    LocationClient.this.onUnRegisterListener(message);
                    return;
                case 7:
                    return;
                case 8:
                    LocationClient.this.onRegisterNotifyLocationListener(message);
                    return;
                case 9:
                    LocationClient.this.onRegisterNotify(message);
                    return;
                case 10:
                    LocationClient.this.onRemoveNotifyEvent(message);
                    return;
                case 11:
                    LocationClient.this.onRequestNotifyLocation();
                    return;
                case 12:
                    LocationClient.this.onRequestOffLineLocation();
                    return;
                case 21:
                    data = message.getData();
                    data.setClassLoader(BDLocation.class.getClassLoader());
                    BDLocation bDLocation = (BDLocation) data.getParcelable("locStr");
                    if (LocationClient.this.serverFirst || !LocationClient.this.clientFirst || bDLocation.getLocType() != 66) {
                        if (LocationClient.this.serverFirst || !LocationClient.this.clientFirst) {
                            if (!LocationClient.this.serverFirst) {
                                LocationClient.this.serverFirst = true;
                            }
                            LocationClient.this.onNewLocation(message, 21);
                            return;
                        }
                        LocationClient.this.serverFirst = true;
                        return;
                    }
                    return;
                case 26:
                    LocationClient.this.onNewLocation(message, 26);
                    return;
                case 27:
                    LocationClient.this.onNewNotifyLocation(message);
                    return;
                case 54:
                    if (LocationClient.this.mOption.location_change_notify) {
                        LocationClient.this.mGpsStatus = true;
                        return;
                    }
                    return;
                case 55:
                    if (LocationClient.this.mOption.location_change_notify) {
                        LocationClient.this.mGpsStatus = false;
                        return;
                    }
                    return;
                case 303:
                    try {
                        data = message.getData();
                        int i2 = data.getInt("loctype");
                        i = data.getInt("diagtype");
                        byte[] byteArray = data.getByteArray("diagmessage");
                        if (i2 > 0 && i > 0 && byteArray != null && LocationClient.this.mLocationListeners2 != null) {
                            Iterator it = LocationClient.this.mLocationListeners2.iterator();
                            while (it.hasNext()) {
                                ((BDAbstractLocationListener) it.next()).onLocDiagnosticMessage(i2, i, new String(byteArray, Request.DEFAULT_CHARSET));
                            }
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        return;
                    }
                case 406:
                    try {
                        Bundle data2 = message.getData();
                        byte[] byteArray2 = data2.getByteArray("mac");
                        String str = byteArray2 != null ? new String(byteArray2, Request.DEFAULT_CHARSET) : null;
                        i = data2.getInt("hotspot", -1);
                        if (LocationClient.this.mLocationListeners2 != null) {
                            Iterator it2 = LocationClient.this.mLocationListeners2.iterator();
                            while (it2.hasNext()) {
                                ((BDAbstractLocationListener) it2.next()).onConnectHotSpotMessage(str, i);
                            }
                            return;
                        }
                        return;
                    } catch (Exception e2) {
                        return;
                    }
                case 701:
                    LocationClient.this.sendFirstLoc((BDLocation) message.obj);
                    return;
                case LocationClient.MSG_REG_LISTENER_V2 /*1300*/:
                    LocationClient.this.onRegisterListener2(message);
                    return;
                case LocationClient.MSG_UNREG_LISTENER_V2 /*1400*/:
                    LocationClient.this.onUnRegisterListener2(message);
                    return;
                default:
                    super.handleMessage(message);
                    return;
            }
        }
    }

    private class b implements Runnable {
        private b() {
        }

        /* synthetic */ b(LocationClient locationClient, b bVar) {
            this();
        }

        /* JADX WARNING: Missing block: B:30:?, code:
            return;
     */
        /* JADX WARNING: Missing block: B:31:?, code:
            return;
     */
        public void run() {
            /*
            r6 = this;
            r3 = 1;
            r0 = com.baidu.location.LocationClient.this;
            r1 = r0.mLock;
            monitor-enter(r1);
            r0 = com.baidu.location.LocationClient.this;	 Catch:{ all -> 0x004a }
            r2 = 0;
            r0.isScheduled = r2;	 Catch:{ all -> 0x004a }
            r0 = com.baidu.location.LocationClient.this;	 Catch:{ all -> 0x004a }
            r0 = r0.mServer;	 Catch:{ all -> 0x004a }
            if (r0 == 0) goto L_0x001e;
        L_0x0016:
            r0 = com.baidu.location.LocationClient.this;	 Catch:{ all -> 0x004a }
            r0 = r0.mMessenger;	 Catch:{ all -> 0x004a }
            if (r0 != 0) goto L_0x0020;
        L_0x001e:
            monitor-exit(r1);	 Catch:{ all -> 0x004a }
        L_0x001f:
            return;
        L_0x0020:
            r0 = com.baidu.location.LocationClient.this;	 Catch:{ all -> 0x004a }
            r0 = r0.mLocationListeners;	 Catch:{ all -> 0x004a }
            if (r0 == 0) goto L_0x0034;
        L_0x0028:
            r0 = com.baidu.location.LocationClient.this;	 Catch:{ all -> 0x004a }
            r0 = r0.mLocationListeners;	 Catch:{ all -> 0x004a }
            r0 = r0.size();	 Catch:{ all -> 0x004a }
            if (r0 >= r3) goto L_0x004d;
        L_0x0034:
            r0 = com.baidu.location.LocationClient.this;	 Catch:{ all -> 0x004a }
            r0 = r0.mLocationListeners2;	 Catch:{ all -> 0x004a }
            if (r0 == 0) goto L_0x0048;
        L_0x003c:
            r0 = com.baidu.location.LocationClient.this;	 Catch:{ all -> 0x004a }
            r0 = r0.mLocationListeners2;	 Catch:{ all -> 0x004a }
            r0 = r0.size();	 Catch:{ all -> 0x004a }
            if (r0 >= r3) goto L_0x004d;
        L_0x0048:
            monitor-exit(r1);	 Catch:{ all -> 0x004a }
            goto L_0x001f;
        L_0x004a:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x004a }
            throw r0;
        L_0x004d:
            r0 = com.baidu.location.LocationClient.this;	 Catch:{ all -> 0x004a }
            r0 = r0.isWaitingLocTag;	 Catch:{ all -> 0x004a }
            if (r0 == 0) goto L_0x0083;
        L_0x0055:
            r0 = com.baidu.location.LocationClient.this;	 Catch:{ all -> 0x004a }
            r0 = r0.mScheduledRequest;	 Catch:{ all -> 0x004a }
            if (r0 != 0) goto L_0x0069;
        L_0x005d:
            r0 = com.baidu.location.LocationClient.this;	 Catch:{ all -> 0x004a }
            r2 = new com.baidu.location.LocationClient$b;	 Catch:{ all -> 0x004a }
            r3 = com.baidu.location.LocationClient.this;	 Catch:{ all -> 0x004a }
            r2.<init>();	 Catch:{ all -> 0x004a }
            r0.mScheduledRequest = r2;	 Catch:{ all -> 0x004a }
        L_0x0069:
            r0 = com.baidu.location.LocationClient.this;	 Catch:{ all -> 0x004a }
            r0 = r0.mHandler;	 Catch:{ all -> 0x004a }
            r2 = com.baidu.location.LocationClient.this;	 Catch:{ all -> 0x004a }
            r2 = r2.mScheduledRequest;	 Catch:{ all -> 0x004a }
            r3 = com.baidu.location.LocationClient.this;	 Catch:{ all -> 0x004a }
            r3 = r3.mOption;	 Catch:{ all -> 0x004a }
            r3 = r3.scanSpan;	 Catch:{ all -> 0x004a }
            r4 = (long) r3;	 Catch:{ all -> 0x004a }
            r0.postDelayed(r2, r4);	 Catch:{ all -> 0x004a }
            monitor-exit(r1);	 Catch:{ all -> 0x004a }
            goto L_0x001f;
        L_0x0083:
            r0 = com.baidu.location.LocationClient.this;	 Catch:{ all -> 0x004a }
            r0 = r0.mHandler;	 Catch:{ all -> 0x004a }
            r2 = 4;
            r0 = r0.obtainMessage(r2);	 Catch:{ all -> 0x004a }
            r0.sendToTarget();	 Catch:{ all -> 0x004a }
            monitor-exit(r1);	 Catch:{ all -> 0x004a }
            goto L_0x001f;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.LocationClient.b.run():void");
        }
    }

    public LocationClient(Context context) {
        this.mContext = context;
        this.mOption = new LocationClientOption();
        this.mHandler = new a(Looper.getMainLooper());
        this.mMessenger = new Messenger(this.mHandler);
    }

    public LocationClient(Context context, LocationClientOption locationClientOption) {
        this.mContext = context;
        this.mOption = locationClientOption;
        this.mHandler = new a(Looper.getMainLooper());
        this.mMessenger = new Messenger(this.mHandler);
    }

    private void callListeners(int i) {
        if (this.mLastLocation.getCoorType() == null) {
            this.mLastLocation.setCoorType(this.mOption.coorType);
        }
        if (this.isWaitingForLocation || ((this.mOption.location_change_notify && this.mLastLocation.getLocType() == 61) || this.mLastLocation.getLocType() == 66 || this.mLastLocation.getLocType() == 67 || this.inDoorState || this.mLastLocation.getLocType() == BDLocation.TypeNetWorkLocation)) {
            Iterator it;
            if (this.mLocationListeners != null) {
                it = this.mLocationListeners.iterator();
                while (it.hasNext()) {
                    ((BDLocationListener) it.next()).onReceiveLocation(this.mLastLocation);
                }
            }
            if (this.mLocationListeners2 != null) {
                it = this.mLocationListeners2.iterator();
                while (it.hasNext()) {
                    ((BDAbstractLocationListener) it.next()).onReceiveLocation(this.mLastLocation);
                }
            }
            if (this.mLastLocation.getLocType() != 66 && this.mLastLocation.getLocType() != 67) {
                this.isWaitingForLocation = false;
                this.lastReceiveLocationTime = System.currentTimeMillis();
            }
        }
    }

    public static BDLocation getBDLocationInCoorType(BDLocation bDLocation, String str) {
        BDLocation bDLocation2 = new BDLocation(bDLocation);
        double[] coorEncrypt = Jni.coorEncrypt(bDLocation.getLongitude(), bDLocation.getLatitude(), str);
        bDLocation2.setLatitude(coorEncrypt[1]);
        bDLocation2.setLongitude(coorEncrypt[0]);
        return bDLocation2;
    }

    private Bundle getOptionBundle() {
        if (this.mOption == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putString("packName", this.mPackName);
        bundle.putString("prodName", this.mOption.prodName);
        bundle.putString("coorType", this.mOption.coorType);
        bundle.putString("addrType", this.mOption.addrType);
        bundle.putBoolean("openGPS", this.mOption.openGps);
        bundle.putBoolean("location_change_notify", this.mOption.location_change_notify);
        bundle.putInt("scanSpan", this.mOption.scanSpan);
        bundle.putBoolean("enableSimulateGps", this.mOption.enableSimulateGps);
        bundle.putInt("timeOut", this.mOption.timeOut);
        bundle.putInt("priority", this.mOption.priority);
        bundle.putBoolean("map", this.mConfig_map.booleanValue());
        bundle.putBoolean("import", this.mConfig_preimport.booleanValue());
        bundle.putBoolean("needDirect", this.mOption.mIsNeedDeviceDirect);
        bundle.putBoolean("isneedaptag", this.mOption.isNeedAptag);
        bundle.putBoolean("isneedpoiregion", this.mOption.isNeedPoiRegion);
        bundle.putBoolean("isneedregular", this.mOption.isNeedRegular);
        bundle.putBoolean("isneedaptagd", this.mOption.isNeedAptagd);
        bundle.putBoolean("isneedaltitude", this.mOption.isNeedAltitude);
        bundle.putInt("autoNotifyMaxInterval", this.mOption.getAutoNotifyMaxInterval());
        bundle.putInt("autoNotifyMinTimeInterval", this.mOption.getAutoNotifyMinTimeInterval());
        bundle.putInt("autoNotifyMinDistance", this.mOption.getAutoNotifyMinDistance());
        bundle.putFloat("autoNotifyLocSensitivity", this.mOption.getAutoNotifyLocSensitivity());
        bundle.putInt("wifitimeout", this.mOption.wifiCacheTimeOut);
        return bundle;
    }

    private void onNewLocation(Message message, int i) {
        if (this.mIsStarted) {
            try {
                Bundle data = message.getData();
                data.setClassLoader(BDLocation.class.getClassLoader());
                this.mLastLocation = (BDLocation) data.getParcelable("locStr");
                if (this.mLastLocation.getLocType() == 61) {
                    this.lastReceiveGpsTime = System.currentTimeMillis();
                }
                callListeners(i);
            } catch (Exception e) {
            }
        }
    }

    private void onNewNotifyLocation(Message message) {
        try {
            Bundle data = message.getData();
            data.setClassLoader(BDLocation.class.getClassLoader());
            BDLocation bDLocation = (BDLocation) data.getParcelable("locStr");
            if (this.NotifyLocationListenner == null) {
                return;
            }
            if (this.mOption == null || !this.mOption.isDisableCache() || bDLocation.getLocType() != 65) {
                this.NotifyLocationListenner.onReceiveLocation(bDLocation);
            }
        } catch (Exception e) {
        }
    }

    private void onRegisterListener(Message message) {
        if (message != null && message.obj != null) {
            BDLocationListener bDLocationListener = (BDLocationListener) message.obj;
            if (this.mLocationListeners == null) {
                this.mLocationListeners = new ArrayList();
            }
            if (!this.mLocationListeners.contains(bDLocationListener)) {
                this.mLocationListeners.add(bDLocationListener);
            }
        }
    }

    private void onRegisterListener2(Message message) {
        if (message != null && message.obj != null) {
            BDAbstractLocationListener bDAbstractLocationListener = (BDAbstractLocationListener) message.obj;
            if (this.mLocationListeners2 == null) {
                this.mLocationListeners2 = new ArrayList();
            }
            if (!this.mLocationListeners2.contains(bDAbstractLocationListener)) {
                this.mLocationListeners2.add(bDAbstractLocationListener);
            }
        }
    }

    private void onRegisterNotify(Message message) {
        if (message != null && message.obj != null) {
            BDNotifyListener bDNotifyListener = (BDNotifyListener) message.obj;
            if (this.mNotifyCache == null) {
                this.mNotifyCache = new com.baidu.location.d.a(this.mContext, this);
            }
            this.mNotifyCache.a(bDNotifyListener);
        }
    }

    private void onRegisterNotifyLocationListener(Message message) {
        if (message != null && message.obj != null) {
            this.NotifyLocationListenner = (BDLocationListener) message.obj;
        }
    }

    private void onRemoveNotifyEvent(Message message) {
        if (message != null && message.obj != null) {
            BDNotifyListener bDNotifyListener = (BDNotifyListener) message.obj;
            if (this.mNotifyCache != null) {
                this.mNotifyCache.c(bDNotifyListener);
            }
        }
    }

    private void onRequestLocation() {
        if (this.mServer != null) {
            if ((System.currentTimeMillis() - this.lastReceiveGpsTime > 3000 || !this.mOption.location_change_notify || this.isWaitingLocTag) && (!this.inDoorState || System.currentTimeMillis() - this.lastReceiveLocationTime > 20000 || this.isWaitingLocTag)) {
                Message obtain = Message.obtain(null, 22);
                if (this.isWaitingLocTag) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isWaitingLocTag", this.isWaitingLocTag);
                    this.isWaitingLocTag = false;
                    obtain.setData(bundle);
                }
                try {
                    obtain.replyTo = this.mMessenger;
                    this.mServer.send(obtain);
                    this.mLastRequestTime = System.currentTimeMillis();
                    this.isWaitingForLocation = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            synchronized (this.mLock) {
                if (!(this.mOption == null || this.mOption.scanSpan < 1000 || this.isScheduled)) {
                    if (this.mScheduledRequest == null) {
                        this.mScheduledRequest = new b(this, null);
                    }
                    this.mHandler.postDelayed(this.mScheduledRequest, (long) this.mOption.scanSpan);
                    this.isScheduled = true;
                }
            }
        }
    }

    private void onRequestNotifyLocation() {
        if (this.mServer != null) {
            Message obtain = Message.obtain(null, 22);
            try {
                obtain.replyTo = this.mMessenger;
                this.mServer.send(obtain);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void onRequestOffLineLocation() {
        Message obtain = Message.obtain(null, 28);
        try {
            obtain.replyTo = this.mMessenger;
            this.mServer.send(obtain);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onSetOption(Message message) {
        this.isWaitingLocTag = false;
        if (message != null && message.obj != null) {
            LocationClientOption locationClientOption = (LocationClientOption) message.obj;
            if (!this.mOption.optionEquals(locationClientOption)) {
                if (this.mOption.scanSpan != locationClientOption.scanSpan) {
                    try {
                        synchronized (this.mLock) {
                            if (this.isScheduled) {
                                this.mHandler.removeCallbacks(this.mScheduledRequest);
                                this.isScheduled = false;
                            }
                            if (locationClientOption.scanSpan >= 1000 && !this.isScheduled) {
                                if (this.mScheduledRequest == null) {
                                    this.mScheduledRequest = new b(this, null);
                                }
                                this.mHandler.postDelayed(this.mScheduledRequest, (long) locationClientOption.scanSpan);
                                this.isScheduled = true;
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                this.mOption = new LocationClientOption(locationClientOption);
                if (this.mServer != null) {
                    try {
                        Message obtain = Message.obtain(null, 15);
                        obtain.replyTo = this.mMessenger;
                        obtain.setData(getOptionBundle());
                        this.mServer.send(obtain);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    private void onStart() {
        if (!this.mIsStarted) {
            if (this.firstConnected.booleanValue()) {
                new c(this).start();
                this.firstConnected = Boolean.valueOf(false);
            }
            this.mPackName = this.mContext.getPackageName();
            this.serviceName = this.mPackName + "_bdls_v2.9";
            Intent intent = new Intent(this.mContext, f.class);
            try {
                intent.putExtra("debug_dev", this.mDebugByDev);
            } catch (Exception e) {
            }
            if (this.mOption == null) {
                this.mOption = new LocationClientOption();
            }
            intent.putExtra("cache_exception", this.mOption.isIgnoreCacheException);
            intent.putExtra("kill_process", this.mOption.isIgnoreKillProcess);
            try {
                this.mContext.bindService(intent, this.mConnection, 1);
            } catch (Exception e2) {
                e2.printStackTrace();
                this.mIsStarted = false;
            }
        }
    }

    private void onStop() {
        if (this.mIsStarted && this.mServer != null) {
            Message obtain = Message.obtain(null, 12);
            obtain.replyTo = this.mMessenger;
            try {
                this.mServer.send(obtain);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                this.mContext.unbindService(this.mConnection);
            } catch (Exception e2) {
            }
            synchronized (this.mLock) {
                try {
                    if (this.isScheduled) {
                        this.mHandler.removeCallbacks(this.mScheduledRequest);
                        this.isScheduled = false;
                    }
                } catch (Exception e3) {
                }
            }
            if (this.mNotifyCache != null) {
                this.mNotifyCache.a();
            }
            this.mServer = null;
            this.isWaitingLocTag = false;
            this.inDoorState = false;
            this.mIsStarted = false;
            this.clientFirst = false;
            this.serverFirst = false;
        }
    }

    private void onUnRegisterListener(Message message) {
        if (message != null && message.obj != null) {
            BDLocationListener bDLocationListener = (BDLocationListener) message.obj;
            if (this.mLocationListeners != null && this.mLocationListeners.contains(bDLocationListener)) {
                this.mLocationListeners.remove(bDLocationListener);
            }
        }
    }

    private void onUnRegisterListener2(Message message) {
        if (message != null && message.obj != null) {
            BDAbstractLocationListener bDAbstractLocationListener = (BDAbstractLocationListener) message.obj;
            if (this.mLocationListeners2 != null && this.mLocationListeners2.contains(bDAbstractLocationListener)) {
                this.mLocationListeners2.remove(bDAbstractLocationListener);
            }
        }
    }

    private void sendFirstLoc(BDLocation bDLocation) {
        if (!this.isStop) {
            Iterator it;
            this.mLastLocation = bDLocation;
            if (!this.serverFirst && bDLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                this.clientFirst = true;
            }
            if (this.mLocationListeners != null) {
                it = this.mLocationListeners.iterator();
                while (it.hasNext()) {
                    ((BDLocationListener) it.next()).onReceiveLocation(bDLocation);
                }
            }
            if (this.mLocationListeners2 != null) {
                it = this.mLocationListeners2.iterator();
                while (it.hasNext()) {
                    ((BDAbstractLocationListener) it.next()).onReceiveLocation(bDLocation);
                }
            }
        }
    }

    private boolean sendServerMessage(int i) {
        if (this.mServer == null || !this.mIsStarted) {
            return false;
        }
        try {
            this.mServer.send(Message.obtain(null, i));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getAccessKey() {
        try {
            this.mKey = i.b(this.mContext);
            if (TextUtils.isEmpty(this.mKey)) {
                throw new IllegalStateException("please setting key from Manifest.xml");
            }
            return String.format("KEY=%s", new Object[]{this.mKey});
        } catch (Exception e) {
            return null;
        }
    }

    public BDLocation getLastKnownLocation() {
        return this.mLastLocation;
    }

    public LocationClientOption getLocOption() {
        return this.mOption;
    }

    public String getVersion() {
        return "7.2.1";
    }

    public boolean isStarted() {
        return this.mIsStarted;
    }

    public void onReceiveLocation(BDLocation bDLocation) {
        if ((!this.serverFirst || this.clientFirst) && bDLocation != null) {
            Message obtainMessage = this.mHandler.obtainMessage(701);
            obtainMessage.obj = bDLocation;
            obtainMessage.sendToTarget();
        }
    }

    public void registerLocationListener(BDAbstractLocationListener bDAbstractLocationListener) {
        if (bDAbstractLocationListener == null) {
            throw new IllegalStateException("please set a non-null listener");
        }
        Message obtainMessage = this.mHandler.obtainMessage(MSG_REG_LISTENER_V2);
        obtainMessage.obj = bDAbstractLocationListener;
        obtainMessage.sendToTarget();
    }

    public void registerLocationListener(BDLocationListener bDLocationListener) {
        if (bDLocationListener == null) {
            throw new IllegalStateException("please set a non-null listener");
        }
        Message obtainMessage = this.mHandler.obtainMessage(5);
        obtainMessage.obj = bDLocationListener;
        obtainMessage.sendToTarget();
    }

    public void registerNotify(BDNotifyListener bDNotifyListener) {
        Message obtainMessage = this.mHandler.obtainMessage(9);
        obtainMessage.obj = bDNotifyListener;
        obtainMessage.sendToTarget();
    }

    public void registerNotifyLocationListener(BDLocationListener bDLocationListener) {
        Message obtainMessage = this.mHandler.obtainMessage(8);
        obtainMessage.obj = bDLocationListener;
        obtainMessage.sendToTarget();
    }

    public void removeNotifyEvent(BDNotifyListener bDNotifyListener) {
        Message obtainMessage = this.mHandler.obtainMessage(10);
        obtainMessage.obj = bDNotifyListener;
        obtainMessage.sendToTarget();
    }

    public boolean requestHotSpotState() {
        if (this.mServer == null || !this.mIsStarted) {
            return false;
        }
        try {
            this.mServer.send(Message.obtain(null, 406));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int requestLocation() {
        if (this.mServer == null || this.mMessenger == null) {
            return 1;
        }
        if ((this.mLocationListeners == null || this.mLocationListeners.size() < 1) && (this.mLocationListeners2 == null || this.mLocationListeners2.size() < 1)) {
            return 2;
        }
        if (System.currentTimeMillis() - this.mLastRequestTime < 1000) {
            return 6;
        }
        this.isWaitingLocTag = true;
        Message obtainMessage = this.mHandler.obtainMessage(4);
        obtainMessage.arg1 = 0;
        obtainMessage.sendToTarget();
        return 0;
    }

    public void requestNotifyLocation() {
        this.mHandler.obtainMessage(11).sendToTarget();
    }

    public int requestOfflineLocation() {
        if (this.mServer == null || this.mMessenger == null) {
            return 1;
        }
        if ((this.mLocationListeners == null || this.mLocationListeners.size() < 1) && (this.mLocationListeners2 == null || this.mLocationListeners2.size() < 1)) {
            return 2;
        }
        this.mHandler.obtainMessage(12).sendToTarget();
        return 0;
    }

    public void restart() {
        stop();
        this.isStop = false;
        this.mHandler.sendEmptyMessageDelayed(1, 1000);
    }

    public void setLocOption(LocationClientOption locationClientOption) {
        if (locationClientOption == null) {
            locationClientOption = new LocationClientOption();
        }
        if (locationClientOption.getAutoNotifyMaxInterval() > 0) {
            locationClientOption.setScanSpan(0);
            locationClientOption.setLocationNotify(true);
        }
        Message obtainMessage = this.mHandler.obtainMessage(3);
        obtainMessage.obj = locationClientOption;
        obtainMessage.sendToTarget();
    }

    public void start() {
        this.isStop = false;
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    public boolean startIndoorMode() {
        boolean sendServerMessage = sendServerMessage(110);
        if (sendServerMessage) {
            this.inDoorState = true;
        }
        return sendServerMessage;
    }

    public void stop() {
        this.isStop = true;
        this.mHandler.obtainMessage(2).sendToTarget();
        this.mloc = null;
    }

    public boolean stopIndoorMode() {
        boolean sendServerMessage = sendServerMessage(111);
        if (sendServerMessage) {
            this.inDoorState = false;
        }
        return sendServerMessage;
    }

    public void unRegisterLocationListener(BDAbstractLocationListener bDAbstractLocationListener) {
        if (bDAbstractLocationListener == null) {
            throw new IllegalStateException("please set a non-null listener");
        }
        Message obtainMessage = this.mHandler.obtainMessage(MSG_UNREG_LISTENER_V2);
        obtainMessage.obj = bDAbstractLocationListener;
        obtainMessage.sendToTarget();
    }

    public void unRegisterLocationListener(BDLocationListener bDLocationListener) {
        if (bDLocationListener == null) {
            throw new IllegalStateException("please set a non-null listener");
        }
        Message obtainMessage = this.mHandler.obtainMessage(6);
        obtainMessage.obj = bDLocationListener;
        obtainMessage.sendToTarget();
    }

    public boolean updateLocation(Location location) {
        if (this.mServer == null || this.mMessenger == null || location == null) {
            return false;
        }
        try {
            Message obtain = Message.obtain(null, 57);
            obtain.obj = location;
            this.mServer.send(obtain);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
