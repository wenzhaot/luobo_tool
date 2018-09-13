package com.umeng.message.proguard;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Process;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.feng.library.utils.DateUtil;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.a;
import com.tencent.ijk.media.player.IjkMediaMeta;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.MessageSharedPrefs;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.entity.Ucode;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.android.agoo.common.AgooConstants;

/* compiled from: Helper */
public class h {
    public static final String a = System.getProperty("line.separator");
    private static final String b = h.class.getName();
    private static final AtomicInteger c = new AtomicInteger(1);

    public static String a(String str) {
        if (str == null) {
            return null;
        }
        try {
            byte[] bytes = str.getBytes();
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.reset();
            instance.update(bytes);
            bytes = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                stringBuffer.append(String.format("%02X", new Object[]{Byte.valueOf(bytes[i])}));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            return str.replaceAll("[^[a-z][A-Z][0-9][.][_]]", "");
        }
    }

    public static String b(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                stringBuffer.append(Integer.toHexString(b & 255));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(b, 2, "getMD5 error");
            return "";
        }
    }

    public static String a(File file) {
        byte[] bArr = new byte[1024];
        try {
            if (!file.isFile()) {
                return "";
            }
            MessageDigest instance = MessageDigest.getInstance("MD5");
            FileInputStream fileInputStream = new FileInputStream(file);
            while (true) {
                int read = fileInputStream.read(bArr, 0, 1024);
                if (read != -1) {
                    instance.update(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    BigInteger bigInteger = new BigInteger(1, instance.digest());
                    return String.format("%1$032x", new Object[]{bigInteger});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String a(Context context, long j) {
        String str = "";
        if (j < 1000) {
            return ((int) j) + "B";
        }
        if (j < 1000000) {
            return Math.round(((double) ((float) j)) / 1000.0d) + "K";
        }
        if (j < 1000000000) {
            return new DecimalFormat("#0.0").format(((double) ((float) j)) / 1000000.0d) + "M";
        }
        return new DecimalFormat("#0.00").format(((double) ((float) j)) / 1.0E9d) + "G";
    }

    public static String c(String str) {
        String str2 = "";
        try {
            long longValue = Long.valueOf(str).longValue();
            if (longValue < 1024) {
                return ((int) longValue) + "B";
            }
            if (longValue < 1048576) {
                return new DecimalFormat("#0.00").format(((double) ((float) longValue)) / 1024.0d) + "K";
            } else if (longValue < IjkMediaMeta.AV_CH_STEREO_RIGHT) {
                return new DecimalFormat("#0.00").format(((double) ((float) longValue)) / 1048576.0d) + "M";
            } else {
                return new DecimalFormat("#0.00").format(((double) ((float) longValue)) / 1.073741824E9d) + "G";
            }
        } catch (NumberFormatException e) {
            return str;
        }
    }

    public static void a(Context context, String str) {
        context.startActivity(context.getPackageManager().getLaunchIntentForPackage(str));
    }

    public static boolean b(Context context, String str) {
        try {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean d(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean e(String str) {
        if (d(str)) {
            return false;
        }
        String toLowerCase = str.trim().toLowerCase(Locale.US);
        if (toLowerCase.startsWith("http://") || toLowerCase.startsWith("https://")) {
            return true;
        }
        return false;
    }

    public static String a() {
        return a(new Date());
    }

    public static String a(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(DateUtil.dateFormatYMDHMS).format(date);
    }

    public static boolean a(Context context) {
        try {
            List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses();
            if (runningAppProcesses == null) {
                return false;
            }
            String packageName = context.getPackageName();
            for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.importance == 100 && runningAppProcessInfo.processName.equals(packageName)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] a(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (-1 == read) {
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }

    public static void b(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (Exception e) {
            }
        }
    }

    public static void a(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (Exception e) {
            }
        }
    }

    public static boolean b(Context context) {
        String packageName = ((RunningTaskInfo) ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningTasks(1).get(0)).topActivity.getPackageName();
        if (packageName == null || !packageName.equals(context.getPackageName())) {
            return false;
        }
        return true;
    }

    public static boolean c(Context context) {
        return context.getPackageManager().checkPermission(MsgConstant.PERMISSION_GET_TASKS, context.getPackageName()) == 0;
    }

    public static boolean c(Context context, String str) {
        List runningServices = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningServices(Integer.MAX_VALUE);
        if (runningServices == null || runningServices.isEmpty()) {
            return false;
        }
        int i = 0;
        while (i < runningServices.size()) {
            if (((RunningServiceInfo) runningServices.get(i)).service.getClassName().equals(str) && TextUtils.equals(((RunningServiceInfo) runningServices.get(i)).service.getPackageName(), context.getPackageName())) {
                return true;
            }
            i++;
        }
        return false;
    }

    public static String a(Context context, int i) {
        List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return "";
        }
        for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == i) {
                return runningAppProcessInfo.processName;
            }
        }
        return "";
    }

    public static void a(Context context, Class<?> cls) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null && packageManager.getApplicationEnabledSetting(context.getPackageName()) > -1) {
                ComponentName componentName = new ComponentName(context, cls);
                int componentEnabledSetting = packageManager.getComponentEnabledSetting(componentName);
                if (componentEnabledSetting != 1 && componentEnabledSetting != 0) {
                    packageManager.setComponentEnabledSetting(componentName, 1, 1);
                }
            }
        } catch (Throwable th) {
        }
    }

    public static final void b(Context context, Class<?> cls) {
        if (context != null) {
            try {
                PackageManager packageManager = context.getPackageManager();
                if (cls != null) {
                    try {
                        ComponentName componentName = new ComponentName(context, cls);
                        if (packageManager.getComponentEnabledSetting(componentName) == 2) {
                            UMLog uMLog = UMConfigure.umDebugLog;
                            UMLog.mutlInfo(b, 2, "rebootReceiver[" + componentName.toString() + "]--->[ENABLED]");
                            packageManager.setComponentEnabledSetting(componentName, 1, 1);
                        }
                    } catch (Throwable th) {
                    }
                }
            } catch (Throwable th2) {
            }
        }
    }

    public static Object f(String str) {
        Object obj = null;
        try {
            try {
                return new ObjectInputStream(new ByteArrayInputStream(d.h(str.getBytes()))).readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return obj;
            }
        } catch (StreamCorruptedException e2) {
            e2.printStackTrace();
            return obj;
        } catch (IOException e3) {
            e3.printStackTrace();
            return obj;
        }
    }

    public static String a(Object obj) {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            new ObjectOutputStream(byteArrayOutputStream).writeObject(obj);
            return new String(d.c(byteArrayOutputStream.toByteArray()));
        } catch (IOException e) {
            return null;
        }
    }

    public static String b() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static boolean a(Context context, Handler handler) {
        if (!PushAgent.getInstance(context).isPushCheck()) {
            return true;
        }
        try {
            final Context context2;
            if (TextUtils.isEmpty(PushAgent.getInstance(context).getMessageAppkey())) {
                context2 = context;
                handler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(context2, "Please set umeng appkey!", 1).show();
                    }
                });
                return false;
            } else if (TextUtils.isEmpty(PushAgent.getInstance(context).getMessageSecret())) {
                context2 = context;
                handler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(context2, "Please set umeng appsecret!", 1).show();
                    }
                });
                return false;
            } else {
                CharSequence charSequence;
                Object obj;
                String str = "";
                Intent intent = new Intent();
                intent.setPackage(context.getPackageName());
                intent.setAction(Constants.ACTION_SERVICE);
                for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentServices(intent, 64)) {
                    if (resolveInfo.serviceInfo.name.equals(a.channelService)) {
                        charSequence = resolveInfo.serviceInfo.processName;
                        if (!(TextUtils.isEmpty(charSequence) || TextUtils.equals(charSequence, context.getPackageName()) || !resolveInfo.serviceInfo.exported)) {
                            obj = 1;
                            break;
                        }
                    }
                }
                obj = null;
                if (obj == null) {
                    context2 = context;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context2, "Please add or correct ChannelService in AndroidManifest!", 1).show();
                        }
                    });
                    return false;
                }
                intent = new Intent();
                intent.setPackage(context.getPackageName());
                intent.setAction("com.taobao.accs.intent.action.ELECTION");
                for (ResolveInfo resolveInfo2 : context.getPackageManager().queryIntentServices(intent, 64)) {
                    if (resolveInfo2.serviceInfo.name.equals(a.channelService)) {
                        charSequence = resolveInfo2.serviceInfo.processName;
                        if (!(TextUtils.isEmpty(charSequence) || TextUtils.equals(charSequence, context.getPackageName()) || !resolveInfo2.serviceInfo.exported)) {
                            obj = 1;
                            break;
                        }
                    }
                }
                obj = null;
                if (obj == null) {
                    context2 = context;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context2, "Please add or correct ChannelService in AndroidManifest!", 1).show();
                        }
                    });
                    return false;
                }
                intent = new Intent();
                intent.setPackage(context.getPackageName());
                intent.setAction(Constants.ACTION_RECEIVE);
                for (ResolveInfo resolveInfo22 : context.getPackageManager().queryIntentServices(intent, 64)) {
                    if (resolveInfo22.serviceInfo.name.equals(a.msgService) && resolveInfo22.serviceInfo.exported) {
                        obj = 1;
                        break;
                    }
                }
                obj = null;
                if (obj == null) {
                    context2 = context;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context2, "Please add or correct MsgDistributeService in AndroidManifest!", 1).show();
                        }
                    });
                    return false;
                }
                CharSequence charSequence2;
                intent = new Intent();
                intent.setPackage(context.getPackageName());
                intent.setAction("android.intent.action.BOOT_COMPLETED");
                for (ResolveInfo resolveInfo222 : context.getPackageManager().queryBroadcastReceivers(intent, 64)) {
                    if (resolveInfo222.activityInfo.name.equals("com.taobao.accs.EventReceiver")) {
                        charSequence2 = resolveInfo222.activityInfo.processName;
                        if (!(TextUtils.isEmpty(charSequence2) || TextUtils.equals(charSequence2, context.getPackageName()))) {
                            obj = 1;
                            break;
                        }
                    }
                }
                obj = null;
                if (obj == null) {
                    context2 = context;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context2, "Please add or correct EventReceiver in AndroidManifest!", 1).show();
                        }
                    });
                    return false;
                }
                intent = new Intent();
                intent.setPackage(context.getPackageName());
                intent.setAction("android.net.conn.CONNECTIVITY_CHANGE");
                for (ResolveInfo resolveInfo2222 : context.getPackageManager().queryBroadcastReceivers(intent, 64)) {
                    if (resolveInfo2222.activityInfo.name.equals("com.taobao.accs.EventReceiver")) {
                        charSequence2 = resolveInfo2222.activityInfo.processName;
                        if (!(TextUtils.isEmpty(charSequence2) || TextUtils.equals(charSequence2, context.getPackageName()))) {
                            obj = 1;
                            break;
                        }
                    }
                }
                obj = null;
                if (obj == null) {
                    context2 = context;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context2, "Please add or correct EventReceiver in AndroidManifest!", 1).show();
                        }
                    });
                    return false;
                }
                intent = new Intent();
                intent.setPackage(context.getPackageName());
                intent.setAction("android.intent.action.PACKAGE_REMOVED");
                intent.setData(Uri.parse("package:"));
                for (ResolveInfo resolveInfo22222 : context.getPackageManager().queryBroadcastReceivers(intent, 64)) {
                    if (resolveInfo22222.activityInfo.name.equals("com.taobao.accs.EventReceiver")) {
                        charSequence2 = resolveInfo22222.activityInfo.processName;
                        if (!(TextUtils.isEmpty(charSequence2) || TextUtils.equals(charSequence2, context.getPackageName()))) {
                            obj = 1;
                            break;
                        }
                    }
                }
                obj = null;
                if (obj == null) {
                    context2 = context;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context2, "Please add or correct EventReceiver in AndroidManifest!", 1).show();
                        }
                    });
                    return false;
                }
                intent = new Intent();
                intent.setPackage(context.getPackageName());
                intent.setAction("android.intent.action.USER_PRESENT");
                for (ResolveInfo resolveInfo222222 : context.getPackageManager().queryBroadcastReceivers(intent, 64)) {
                    if (resolveInfo222222.activityInfo.name.equals("com.taobao.accs.EventReceiver")) {
                        charSequence2 = resolveInfo222222.activityInfo.processName;
                        if (!(TextUtils.isEmpty(charSequence2) || TextUtils.equals(charSequence2, context.getPackageName()))) {
                            obj = 1;
                            break;
                        }
                    }
                }
                obj = null;
                if (obj == null) {
                    context2 = context;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context2, "Please add or correct EventReceiver in AndroidManifest!", 1).show();
                        }
                    });
                    return false;
                }
                intent = new Intent();
                intent.setPackage(context.getPackageName());
                intent.setAction(Constants.ACTION_COMMAND);
                for (ResolveInfo resolveInfo2222222 : context.getPackageManager().queryBroadcastReceivers(intent, 64)) {
                    if (resolveInfo2222222.activityInfo.name.equals("com.taobao.accs.ServiceReceiver")) {
                        charSequence2 = resolveInfo2222222.activityInfo.processName;
                        if (!(TextUtils.isEmpty(charSequence2) || TextUtils.equals(charSequence2, context.getPackageName()))) {
                            obj = 1;
                            break;
                        }
                    }
                }
                obj = null;
                if (obj == null) {
                    context2 = context;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context2, "Please add or correct ServiceReceiver in AndroidManifest!", 1).show();
                        }
                    });
                    return false;
                }
                intent = new Intent();
                intent.setPackage(context.getPackageName());
                intent.setAction(Constants.ACTION_START_FROM_AGOO);
                for (ResolveInfo resolveInfo22222222 : context.getPackageManager().queryBroadcastReceivers(intent, 64)) {
                    if (resolveInfo22222222.activityInfo.name.equals("com.taobao.accs.ServiceReceiver")) {
                        charSequence2 = resolveInfo22222222.activityInfo.processName;
                        if (!(TextUtils.isEmpty(charSequence2) || TextUtils.equals(charSequence2, context.getPackageName()))) {
                            obj = 1;
                            break;
                        }
                    }
                }
                obj = null;
                if (obj == null) {
                    context2 = context;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context2, "Please add or correct ServiceReceiver in AndroidManifest!", 1).show();
                        }
                    });
                    return false;
                }
                intent = new Intent();
                intent.setPackage(context.getPackageName());
                intent.setAction(Constants.ACTION_RECEIVE);
                for (ResolveInfo resolveInfo222222222 : context.getPackageManager().queryIntentServices(intent, 64)) {
                    if (resolveInfo222222222.serviceInfo.name.equals("org.android.agoo.accs.AgooService") && resolveInfo222222222.serviceInfo.exported) {
                        obj = 1;
                        break;
                    }
                }
                obj = null;
                if (obj == null) {
                    context2 = context;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context2, "Please add or correct AgooService in AndroidManifest!", 1).show();
                        }
                    });
                    return false;
                }
                intent = new Intent();
                intent.setPackage(context.getPackageName());
                intent.setAction(AgooConstants.INTENT_FROM_AGOO_MESSAGE);
                for (ResolveInfo resolveInfo2222222222 : context.getPackageManager().queryIntentServices(intent, 64)) {
                    if (resolveInfo2222222222.serviceInfo.name.equals("com.umeng.message.UmengIntentService") && resolveInfo2222222222.serviceInfo.exported) {
                        obj = 1;
                        break;
                    }
                }
                obj = null;
                if (obj == null) {
                    context2 = context;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context2, "Please add or correct UmengIntentService in AndroidManifest!", 1).show();
                        }
                    });
                    return false;
                }
                intent = new Intent();
                intent.setPackage(context.getPackageName());
                intent.setAction(context.getPackageName() + ".intent.action.COMMAND");
                for (ResolveInfo resolveInfo22222222222 : context.getPackageManager().queryBroadcastReceivers(intent, 64)) {
                    if (resolveInfo22222222222.activityInfo.name.equals("com.taobao.agoo.AgooCommondReceiver") && resolveInfo22222222222.activityInfo.exported) {
                        obj = 1;
                        break;
                    }
                }
                obj = null;
                if (obj == null) {
                    context2 = context;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context2, "Please replace '${applicationId}.intent.action.COMMAND' with application's applicationId for AgooCommondReceiver in AndroidManifest!", 1).show();
                        }
                    });
                    return false;
                }
                intent = new Intent();
                intent.setPackage(context.getPackageName());
                intent.setAction("android.intent.action.PACKAGE_REMOVED");
                intent.setData(Uri.parse("package:"));
                for (ResolveInfo resolveInfo222222222222 : context.getPackageManager().queryBroadcastReceivers(intent, 64)) {
                    if (resolveInfo222222222222.activityInfo.name.equals("com.taobao.agoo.AgooCommondReceiver") && resolveInfo222222222222.activityInfo.exported) {
                        obj = 1;
                        break;
                    }
                }
                obj = null;
                if (obj == null) {
                    context2 = context;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context2, "Please add or correct AgooCommondReceiver in AndroidManifest!", 1).show();
                        }
                    });
                    return false;
                }
                intent = new Intent();
                intent.setClassName(context.getPackageName(), "com.umeng.message.NotificationProxyBroadcastReceiver");
                for (ResolveInfo resolveInfo2222222222222 : context.getPackageManager().queryBroadcastReceivers(intent, 65536)) {
                    if (resolveInfo2222222222222.activityInfo.name.equals("com.umeng.message.NotificationProxyBroadcastReceiver") && TextUtils.equals(resolveInfo2222222222222.activityInfo.processName, context.getPackageName()) && !resolveInfo2222222222222.activityInfo.exported) {
                        obj = 1;
                        break;
                    }
                }
                obj = null;
                if (obj == null) {
                    context2 = context;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context2, "Please add or correct NotificationProxyBroadcastReceiver in AndroidManifest!", 1).show();
                        }
                    });
                    return false;
                }
                intent = new Intent();
                intent.setPackage(context.getPackageName());
                intent.setAction(MsgConstant.MESSAGE_REGISTER_CALLBACK_ACTION);
                for (ResolveInfo resolveInfo22222222222222 : context.getPackageManager().queryIntentServices(intent, 64)) {
                    if (resolveInfo22222222222222.serviceInfo.name.equals("com.umeng.message.UmengMessageCallbackHandlerService") && TextUtils.equals(context.getPackageName(), resolveInfo22222222222222.serviceInfo.processName) && !resolveInfo22222222222222.serviceInfo.exported) {
                        obj = 1;
                        break;
                    }
                }
                obj = null;
                if (obj == null) {
                    context2 = context;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context2, "Please add or correct UmengMessageCallbackHandlerService in AndroidManifest!", 1).show();
                        }
                    });
                    return false;
                }
                intent = new Intent();
                intent.setPackage(context.getPackageName());
                intent.setAction(MsgConstant.MESSAGE_ENABLE_CALLBACK_ACTION);
                for (ResolveInfo resolveInfo222222222222222 : context.getPackageManager().queryIntentServices(intent, 64)) {
                    if (resolveInfo222222222222222.serviceInfo.name.equals("com.umeng.message.UmengMessageCallbackHandlerService") && !resolveInfo222222222222222.serviceInfo.exported) {
                        obj = 1;
                        break;
                    }
                }
                obj = null;
                if (obj == null) {
                    context2 = context;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context2, "Please add or correct UmengMessageCallbackHandlerService in AndroidManifest!", 1).show();
                        }
                    });
                    return false;
                }
                intent = new Intent();
                intent.setPackage(context.getPackageName());
                intent.setAction(MsgConstant.MESSAGE_DISABLE_CALLBACK_ACTION);
                for (ResolveInfo resolveInfo2222222222222222 : context.getPackageManager().queryIntentServices(intent, 64)) {
                    if (resolveInfo2222222222222222.serviceInfo.name.equals("com.umeng.message.UmengMessageCallbackHandlerService") && !resolveInfo2222222222222222.serviceInfo.exported) {
                        obj = 1;
                        break;
                    }
                }
                obj = null;
                if (obj == null) {
                    context2 = context;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context2, "Please add or correct UmengMessageCallbackHandlerService in AndroidManifest!", 1).show();
                        }
                    });
                    return false;
                }
                intent = new Intent();
                intent.setPackage(context.getPackageName());
                intent.setAction(MsgConstant.MESSAGE_MESSAGE_HANDLER_ACTION);
                for (ResolveInfo resolveInfo22222222222222222 : context.getPackageManager().queryIntentServices(intent, 64)) {
                    if (resolveInfo22222222222222222.serviceInfo.name.equals("com.umeng.message.UmengMessageCallbackHandlerService") && !resolveInfo22222222222222222.serviceInfo.exported) {
                        obj = 1;
                        break;
                    }
                }
                obj = null;
                if (obj == null) {
                    context2 = context;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context2, "Please add or correct UmengMessageCallbackHandlerService in AndroidManifest!", 1).show();
                        }
                    });
                    return false;
                }
                intent = new Intent();
                intent.setClassName(context.getPackageName(), "com.umeng.message.UmengDownloadResourceService");
                for (ResolveInfo resolveInfo222222222222222222 : context.getPackageManager().queryIntentServices(intent, 65536)) {
                    if (resolveInfo222222222222222222.serviceInfo.name.equals("com.umeng.message.UmengDownloadResourceService")) {
                        obj = 1;
                        break;
                    }
                }
                obj = null;
                if (obj == null) {
                    context2 = context;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context2, "Please add or correct UmengDownloadResourceService in AndroidManifest!", 1).show();
                        }
                    });
                    return false;
                }
                intent = new Intent();
                intent.setPackage(context.getPackageName());
                intent.setAction(AgooConstants.BINDER_MSGRECEIVER_ACTION);
                for (ResolveInfo resolveInfo2222222222222222222 : context.getPackageManager().queryIntentServices(intent, 64)) {
                    if (resolveInfo2222222222222222222.serviceInfo.name.equals("com.umeng.message.UmengMessageIntentReceiverService") && resolveInfo2222222222222222222.serviceInfo.exported) {
                        charSequence2 = resolveInfo2222222222222222222.serviceInfo.processName;
                        if (!(TextUtils.isEmpty(charSequence2) || TextUtils.equals(charSequence2, context.getPackageName()))) {
                            obj = 1;
                            break;
                        }
                    }
                }
                obj = null;
                if (obj == null) {
                    context2 = context;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context2, "Please add or correct UmengMessageIntentReceiverService in AndroidManifest!", 1).show();
                        }
                    });
                    return false;
                }
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096);
                Object obj2 = null;
                Object obj3 = null;
                Object obj4 = null;
                Object obj5 = null;
                Object obj6 = null;
                Object obj7 = null;
                Object obj8 = null;
                Object obj9 = null;
                Object obj10 = null;
                Object obj11 = null;
                Object obj12 = null;
                Object obj13 = null;
                obj = null;
                if (packageInfo.requestedPermissions != null) {
                    for (int i = 0; i < packageInfo.requestedPermissions.length; i++) {
                        if (MsgConstant.PERMISSION_INTERNET.equals(packageInfo.requestedPermissions[i])) {
                            obj2 = 1;
                        } else if (MsgConstant.PERMISSION_ACCESS_WIFI_STATE.equals(packageInfo.requestedPermissions[i])) {
                            obj4 = 1;
                        } else if (MsgConstant.PERMISSION_ACCESS_NETWORK_STATE.equals(packageInfo.requestedPermissions[i])) {
                            obj3 = 1;
                        } else if (MsgConstant.PERMISSION_WRITE_EXTERNAL_STORAGE.equals(packageInfo.requestedPermissions[i])) {
                            obj5 = 1;
                        } else if (MsgConstant.PERMISSION_WAKE_LOCK.equals(packageInfo.requestedPermissions[i])) {
                            obj6 = 1;
                        } else if (MsgConstant.PERMISSION_READ_PHONE_STATE.equals(packageInfo.requestedPermissions[i])) {
                            obj7 = 1;
                        } else if (MsgConstant.PERMISSION_BROADCAST_PACKAGE_ADDED.equals(packageInfo.requestedPermissions[i])) {
                            obj8 = 1;
                        } else if (MsgConstant.PERMISSION_BROADCAST_PACKAGE_CHANGED.equals(packageInfo.requestedPermissions[i])) {
                            obj9 = 1;
                        } else if (MsgConstant.PERMISSION_BROADCAST_PACKAGE_INSTALL.equals(packageInfo.requestedPermissions[i])) {
                            obj10 = 1;
                        } else if (MsgConstant.PERMISSION_BROADCAST_PACKAGE_REPLACED.equals(packageInfo.requestedPermissions[i])) {
                            obj11 = 1;
                        } else if (MsgConstant.PERMISSION_RESTART_PACKAGES.equals(packageInfo.requestedPermissions[i])) {
                            obj12 = 1;
                        } else if (MsgConstant.PERMISSION_GET_TASKS.equals(packageInfo.requestedPermissions[i])) {
                            obj13 = 1;
                        } else if (MsgConstant.PERMISSION_RECEIVE_BOOT_COMPLETED.equals(packageInfo.requestedPermissions[i])) {
                            obj = 1;
                        }
                    }
                }
                obj = (obj2 == null || obj4 == null || obj3 == null || obj5 == null || obj6 == null || obj7 == null || obj8 == null || obj9 == null || obj10 == null || obj11 == null || obj12 == null || obj13 == null || obj == null) ? null : 1;
                if (obj == null) {
                    context2 = context;
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context2, "Please add required permission in AndroidManifest!", 1).show();
                        }
                    });
                    return false;
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String b(File file) throws IOException {
        Throwable th;
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            try {
                String str = "";
                StringBuilder stringBuilder = new StringBuilder();
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    stringBuilder.append(readLine);
                }
                str = stringBuilder.toString();
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return str;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static boolean d(Context context) {
        String a = a(context, Process.myPid());
        if (a != null) {
            return a.equals(context.getPackageName());
        }
        return false;
    }

    public static void b(final Context context, final Handler handler) {
        MessageSharedPrefs.getInstance(context).setRegisterTimes(MessageSharedPrefs.getInstance(context).getRegisterTimes() + 1);
        if (d(context)) {
            handler.postDelayed(new Runnable() {
                public void run() {
                    int i = 0;
                    if (MessageSharedPrefs.getInstance(context).getRegisterTimes() <= 1) {
                        UMLog uMLog = UMConfigure.umDebugLog;
                        UMLog.mutlInfo(h.b, 0, "mPushAgent.register方法应该在主进程和channel进程中都被调用!");
                        uMLog = UMConfigure.umDebugLog;
                        UMLog.aq(k.b, 0, "\\|");
                        while (i < 3) {
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    Toast.makeText(context, "mPushAgent.register方法应该在主进程和channel进程中都被调用!", 1).show();
                                }
                            }, (long) (i * 3500));
                            i++;
                        }
                    }
                }
            }, 20000);
        }
    }

    public static String a(List<Ucode> list) throws IOException {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(list);
        String encode = URLEncoder.encode(byteArrayOutputStream.toString(f.a), "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return encode;
    }

    public static List<Ucode> g(String str) throws IOException, ClassNotFoundException {
        InputStream byteArrayInputStream = new ByteArrayInputStream(URLDecoder.decode(str, "UTF-8").getBytes(f.a));
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        List<Ucode> list = (List) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return list;
    }

    public static String d(Context context, String str) {
        String str2 = context.getCacheDir() + "/umeng_push_inapp/";
        if (str != null) {
            return str2 + str + "/";
        }
        return str2;
    }

    public static int c() {
        if (VERSION.SDK_INT >= 17) {
            return View.generateViewId();
        }
        int i;
        int i2;
        do {
            i = c.get();
            i2 = i + 1;
            if (i2 > 16777215) {
                i2 = 1;
            }
        } while (!c.compareAndSet(i, i2));
        return i;
    }
}
