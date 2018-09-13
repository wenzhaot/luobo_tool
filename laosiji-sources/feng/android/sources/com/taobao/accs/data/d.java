package com.taobao.accs.data;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.feng.car.utils.FengConstant;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.taobao.accs.IAppReceiver;
import com.taobao.accs.IAppReceiverV1;
import com.taobao.accs.base.AccsAbstractDataListener;
import com.taobao.accs.base.AccsDataListener;
import com.taobao.accs.base.TaoBaseService.ConnectInfo;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.Constants.Operate;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.accs.utl.a;
import com.taobao.accs.utl.b;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* compiled from: Taobao */
public class d {
    private static Set<String> a;
    private static volatile d b = null;

    public static d a() {
        if (b == null) {
            synchronized (d.class) {
                if (b == null) {
                    b = new d();
                }
            }
        }
        return b;
    }

    public static void a(Context context, Intent intent) {
        try {
            ThreadPoolExecutorFactory.getScheduledExecutor().execute(new e(context, intent));
        } catch (Throwable th) {
            ALog.e("MsgDistribute", "distribMessage", th, new Object[0]);
            UTMini.getInstance().commitEvent(66001, "MsgToBuss8", "distribMessage" + th.toString(), Integer.valueOf(Constants.SDK_VERSION_CODE));
        }
    }

    private void b(Context context, Intent intent) {
        Object action = intent.getAction();
        if (ALog.isPrintLog(Level.D)) {
            ALog.d("MsgDistribute", "action:" + action, new Object[0]);
        }
        if (TextUtils.isEmpty(action)) {
            ALog.e("MsgDistribute", "action null", new Object[0]);
            UTMini.getInstance().commitEvent(66001, "MsgToBuss9", "action null", Integer.valueOf(Constants.SDK_VERSION_CODE));
            return;
        }
        String str = null;
        int i = 0;
        try {
            if (TextUtils.equals(action, Constants.ACTION_RECEIVE)) {
                i = intent.getIntExtra("command", -1);
                String stringExtra = intent.getStringExtra(Constants.KEY_USER_ID);
                int intExtra = intent.getIntExtra(Constants.KEY_ERROR_CODE, 0);
                str = intent.getStringExtra(Constants.KEY_SERVICE_ID);
                String stringExtra2 = intent.getStringExtra(Constants.KEY_DATA_ID);
                String stringExtra3 = intent.getStringExtra(Constants.KEY_APP_KEY);
                CharSequence stringExtra4 = intent.getStringExtra(Constants.KEY_CONFIG_TAG);
                if (intent.getPackage() == null) {
                    intent.setPackage(context.getPackageName());
                }
                if ("accs".equals(str)) {
                    ALog.e("MsgDistribute", "distribute", "command:", Integer.valueOf(i), " serviceId:", str, " dataId:", stringExtra2, "appkey", stringExtra3, "config", stringExtra4);
                } else {
                    ALog.i("MsgDistribute", "distribute", "command:", Integer.valueOf(i), " serviceId:", str, " dataId:", stringExtra2, "appkey", stringExtra3, "config", stringExtra4);
                }
                if (!a(context, intent, stringExtra2, str)) {
                    if (i < 0) {
                        ALog.e("MsgDistribute", "command error:" + i, Constants.KEY_SERVICE_ID, str);
                        return;
                    } else if (!a(i, str) && !b(context, intent, stringExtra2, str)) {
                        Map appReceiver = GlobalClientInfo.getInstance(context).getAppReceiver();
                        IAppReceiver iAppReceiver = null;
                        if (!TextUtils.isEmpty(stringExtra4)) {
                            iAppReceiver = appReceiver != null ? (IAppReceiver) appReceiver.get(stringExtra4) : null;
                        }
                        if (!a(context, str, stringExtra2, intent, iAppReceiver)) {
                            a(context, intent, stringExtra3, i, stringExtra, str, stringExtra2, iAppReceiver, intExtra);
                            if (TextUtils.isEmpty(str)) {
                                a(context, appReceiver, intent, i, intExtra);
                                return;
                            } else {
                                a(context, iAppReceiver, intent, str, stringExtra2, i, intExtra);
                                return;
                            }
                        }
                        return;
                    } else {
                        return;
                    }
                }
                return;
            }
            ALog.e("MsgDistribute", "action error " + action, Constants.KEY_SERVICE_ID, null);
            UTMini.getInstance().commitEvent(66001, "MsgToBuss10", action, Integer.valueOf(Constants.SDK_VERSION_CODE));
        } catch (Throwable th) {
            ALog.e("MsgDistribute", "distribMessage :", th, Constants.KEY_SERVICE_ID, str);
            b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str, PushConstants.PUSH_TYPE_THROUGH_MESSAGE, "distribute error " + i + UtilityImpl.getStackMsg(th));
        }
    }

    protected boolean a(int i, String str) {
        if (!(i == 100 || GlobalClientInfo.AGOO_SERVICE_ID.equals(str))) {
            long usableSpace = UtilityImpl.getUsableSpace();
            if (usableSpace != -1 && usableSpace <= 5242880) {
                b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str, PushConstants.PUSH_TYPE_THROUGH_MESSAGE, "space low " + usableSpace);
                ALog.e("MsgDistribute", "user space low, don't distribute", FengConstant.SIZE, Long.valueOf(usableSpace), Constants.KEY_SERVICE_ID, str);
                return true;
            }
        }
        return false;
    }

    protected boolean a(Context context, String str, String str2, Intent intent, IAppReceiver iAppReceiver) {
        boolean z = true;
        try {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            CharSequence charSequence = null;
            if (iAppReceiver != null) {
                charSequence = iAppReceiver.getService(str);
            }
            if (TextUtils.isEmpty(charSequence)) {
                charSequence = GlobalClientInfo.getInstance(context).getService(str);
            }
            if (TextUtils.isEmpty(charSequence) && !UtilityImpl.isMainProcess(context)) {
                if ("accs".equals(str)) {
                    ALog.e("MsgDistribute", "start MsgDistributeService", Constants.KEY_DATA_ID, str2);
                } else {
                    ALog.i("MsgDistribute", "start MsgDistributeService", Constants.KEY_DATA_ID, str2);
                }
                intent.setClassName(intent.getPackage(), c());
                context.startService(intent);
                return z;
            }
            z = false;
            return z;
        } catch (Throwable th) {
            ALog.e("MsgDistribute", "handleMsgInChannelProcess", th, new Object[0]);
        }
    }

    private void a(Context context, Intent intent, String str, int i, String str2, String str3, String str4, IAppReceiver iAppReceiver, int i2) {
        if (iAppReceiver != null) {
            switch (i) {
                case 1:
                    if (iAppReceiver instanceof IAppReceiverV1) {
                        ((IAppReceiverV1) iAppReceiver).onBindApp(i2, null);
                    } else {
                        iAppReceiver.onBindApp(i2);
                    }
                    if (i2 == 200) {
                        try {
                            ALog.i("MsgDistribute", "start election by bindapp....", Constants.KEY_SERVICE_ID, str3);
                            a(context);
                            break;
                        } catch (Throwable th) {
                            ALog.e("MsgDistribute", "start election is error", th, Constants.KEY_SERVICE_ID, str3);
                            break;
                        }
                    }
                    break;
                case 2:
                    if (i2 == 200) {
                        UtilityImpl.disableService(context);
                    }
                    iAppReceiver.onUnbindApp(i2);
                    break;
                case 3:
                    iAppReceiver.onBindUser(str2, i2);
                    break;
                case 4:
                    iAppReceiver.onUnbindUser(i2);
                    break;
                case 100:
                    if (TextUtils.isEmpty(str3)) {
                        iAppReceiver.onSendData(str4, i2);
                        break;
                    }
                    break;
                case 101:
                    if (TextUtils.isEmpty(str3)) {
                        ALog.d("MsgDistribute", "serviceId isEmpty", new Object[0]);
                        byte[] byteArrayExtra = intent.getByteArrayExtra("data");
                        if (byteArrayExtra != null) {
                            iAppReceiver.onData(str2, str4, byteArrayExtra);
                            break;
                        }
                    }
                    break;
            }
        }
        if (i == 1 && GlobalClientInfo.b != null && str != null && str.equals(GlobalClientInfo.b.getAppkey())) {
            GlobalClientInfo.b.onBindApp(i2, null);
        } else if (iAppReceiver == null && i != 104 && i != 103) {
            b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str3, PushConstants.PUSH_TYPE_THROUGH_MESSAGE, "appReceiver null return");
            ALog.w("MsgDistribute", "appReceiver null!", Constants.KEY_SERVICE_ID, str3, "appkey", str);
            UTMini.getInstance().commitEvent(66001, "MsgToBuss7", "commandId=" + i, "serviceId=" + str3 + " errorCode=" + i2 + " dataId=" + str4, Integer.valueOf(Constants.SDK_VERSION_CODE));
        }
    }

    protected void a(Context context, IAppReceiver iAppReceiver, Intent intent, String str, String str2, int i, int i2) {
        String str3 = null;
        if (iAppReceiver != null) {
            str3 = iAppReceiver.getService(str);
        }
        if (TextUtils.isEmpty(str3)) {
            str3 = GlobalClientInfo.getInstance(context).getService(str);
        }
        if (TextUtils.isEmpty(str3)) {
            AccsDataListener listener = GlobalClientInfo.getInstance(context).getListener(str);
            if (listener != null) {
                AccsAbstractDataListener.onReceiveData(context, intent, listener);
            } else {
                str3 = intent.getStringExtra(Constants.KEY_APP_KEY);
                ALog.e("MsgDistribute", "callback is null", Constants.KEY_DATA_ID, str2, " serviceId", str, " command", Integer.valueOf(i), "appkey", str3);
                b.a("accs", BaseMonitor.ALARM_POINT_REQ_ERROR, str, PushConstants.PUSH_TYPE_THROUGH_MESSAGE, "service is null");
            }
        } else {
            if (ALog.isPrintLog(Level.D)) {
                ALog.d("MsgDistribute", "to start service:" + str3, new Object[0]);
            }
            intent.setClassName(context, str3);
            context.startService(intent);
        }
        UTMini.getInstance().commitEvent(66001, "MsgToBuss", "commandId=" + i, "serviceId=" + str + " errorCode=" + i2 + " dataId=" + str2, Integer.valueOf(Constants.SDK_VERSION_CODE));
        b.a("accs", BaseMonitor.COUNT_POINT_TO_BUSS, "2commandId=" + i + "serviceId=" + str, 0.0d);
    }

    protected void a(Context context, Map<String, IAppReceiver> map, Intent intent, int i, int i2) {
        Map hashMap = new HashMap();
        if (map != null) {
            for (Entry value : map.entrySet()) {
                Map allServices = ((IAppReceiver) value.getValue()).getAllServices();
                if (allServices != null) {
                    hashMap.putAll(allServices);
                }
            }
        }
        Object obj;
        if (i == 103) {
            if (hashMap != null) {
                for (String str : hashMap.keySet()) {
                    if ("accs".equals(str) || "windvane".equals(str) || "motu-remote".equals(str)) {
                        obj = (String) hashMap.get(str);
                        if (TextUtils.isEmpty(obj)) {
                            obj = GlobalClientInfo.getInstance(context).getService(str);
                        }
                        if (!TextUtils.isEmpty(obj)) {
                            intent.setClassName(context, obj);
                            context.startService(intent);
                        }
                    }
                }
            }
            boolean booleanExtra = intent.getBooleanExtra(Constants.KEY_CONNECT_AVAILABLE, false);
            String stringExtra = intent.getStringExtra("host");
            String stringExtra2 = intent.getStringExtra(Constants.KEY_ERROR_DETAIL);
            boolean booleanExtra2 = intent.getBooleanExtra(Constants.KEY_TYPE_INAPP, false);
            boolean booleanExtra3 = intent.getBooleanExtra(Constants.KEY_CENTER_HOST, false);
            Serializable serializable = null;
            if (!TextUtils.isEmpty(stringExtra)) {
                if (booleanExtra) {
                    serializable = new ConnectInfo(stringExtra, booleanExtra2, booleanExtra3);
                } else {
                    serializable = new ConnectInfo(stringExtra, booleanExtra2, booleanExtra3, i2, stringExtra2);
                }
                serializable.connected = booleanExtra;
            }
            if (serializable != null) {
                Intent intent2 = new Intent(Constants.ACTION_CONNECT_INFO);
                intent2.setPackage(context.getPackageName());
                intent2.putExtra(Constants.KEY_CONNECT_INFO, serializable);
                context.sendBroadcast(intent2);
                return;
            }
            ALog.e("MsgDistribute", "connect info null, host empty", new Object[0]);
        } else if (i != 104) {
            ALog.i("MsgDistribute", "distribMessage serviceId is null!! command:" + i, new Object[0]);
        } else if (hashMap != null) {
            for (String str2 : hashMap.keySet()) {
                obj = (String) hashMap.get(str2);
                if (TextUtils.isEmpty(obj)) {
                    obj = GlobalClientInfo.getInstance(context).getService(str2);
                }
                if (!TextUtils.isEmpty(obj)) {
                    intent.setClassName(context, obj);
                    context.startService(intent);
                }
            }
        }
    }

    protected String b() {
        return a.channelService;
    }

    protected String c() {
        return a.msgService;
    }

    private void a(Context context) {
        if (com.taobao.accs.a.a.b()) {
            Intent intent = new Intent(com.taobao.accs.a.a.c());
            intent.putExtra("operate", Operate.TRY_ELECTION);
            intent.setPackage(context.getPackageName());
            intent.setClassName(context.getPackageName(), b());
            context.startService(intent);
        }
    }

    private boolean a(Context context, Intent intent, String str, String str2) {
        boolean z;
        boolean booleanExtra = intent.getBooleanExtra("routingAck", false);
        boolean booleanExtra2 = intent.getBooleanExtra("routingMsg", false);
        if (booleanExtra) {
            ALog.e("MsgDistribute", "recieve routiong ack", Constants.KEY_DATA_ID, str, Constants.KEY_SERVICE_ID, str2);
            if (a != null) {
                a.remove(str);
            }
            b.a("accs", BaseMonitor.ALARM_MSG_ROUTING_RATE, "");
            z = true;
        } else {
            z = false;
        }
        if (booleanExtra2) {
            try {
                String stringExtra = intent.getStringExtra(Constants.KEY_PACKAGE_NAME);
                ALog.e("MsgDistribute", "send routiong ack", Constants.KEY_DATA_ID, str, "to pkg", stringExtra, Constants.KEY_SERVICE_ID, str2);
                Intent intent2 = new Intent(Constants.ACTION_COMMAND);
                intent2.putExtra("command", 106);
                intent2.setClassName(stringExtra, a.channelService);
                intent2.putExtra("routingAck", true);
                intent2.putExtra(Constants.KEY_PACKAGE_NAME, stringExtra);
                intent2.putExtra(Constants.KEY_DATA_ID, str);
                context.startService(intent2);
            } catch (Throwable th) {
                ALog.e("MsgDistribute", "send routing ack", th, Constants.KEY_SERVICE_ID, str2);
            }
        }
        return z;
    }

    private boolean b(Context context, Intent intent, String str, String str2) {
        if (context.getPackageName().equals(intent.getPackage())) {
            return false;
        }
        try {
            ALog.e("MsgDistribute", "start MsgDistributeService", "receive pkg", context.getPackageName(), "target pkg", intent.getPackage(), Constants.KEY_SERVICE_ID, str2);
            intent.setClassName(intent.getPackage(), a.msgService);
            intent.putExtra("routingMsg", true);
            intent.putExtra(Constants.KEY_PACKAGE_NAME, context.getPackageName());
            context.startService(intent);
            if (a == null) {
                a = new HashSet();
            }
            a.add(str);
            ThreadPoolExecutorFactory.schedule(new f(this, str, str2, context, intent), 10, TimeUnit.SECONDS);
        } catch (Throwable th) {
            b.a("accs", BaseMonitor.ALARM_MSG_ROUTING_RATE, "", com.umeng.analytics.pro.b.ao, th.toString());
            ALog.e("MsgDistribute", "routing msg error, try election", th, Constants.KEY_SERVICE_ID, str2, Constants.KEY_DATA_ID, str);
            a(context);
        }
        return true;
    }
}
