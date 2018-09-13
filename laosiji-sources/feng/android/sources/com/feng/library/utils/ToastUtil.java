package com.feng.library.utils;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.stub.StubApp;

public class ToastUtil {
    private static int ENABLED_FLAG = -1;

    private static boolean isNotificationAuthorityEnabled(Context context) {
        if (VERSION.SDK_INT >= 19) {
            return NotificationManagerCompat.from(context).areNotificationsEnabled();
        }
        return true;
    }

    private static void initFlag(Context context) {
        if (isNotificationAuthorityEnabled(context)) {
            ENABLED_FLAG = 0;
        } else {
            ENABLED_FLAG = 1;
        }
    }

    public static void showToast(Context context, String text) {
        if (context != null) {
            try {
                if (!StringUtil.isEmpty(text)) {
                    if (Build.MANUFACTURER.equals("Xiaomi") || Build.MANUFACTURER.equals("Meizu") || Build.MODEL.equals("U10")) {
                        if (ENABLED_FLAG == -1) {
                            initFlag(context);
                        }
                        if (ENABLED_FLAG == 0) {
                            Toast.makeText(StubApp.getOrigApplicationContext(context.getApplicationContext()), text, 0).show();
                            return;
                        } else if (ENABLED_FLAG == 1) {
                            ExToast.makeText(StubApp.getOrigApplicationContext(context.getApplicationContext()), (CharSequence) text, 2000).show();
                            return;
                        } else {
                            return;
                        }
                    }
                    SuperToast.create(StubApp.getOrigApplicationContext(context.getApplicationContext()), text, 2000).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void showLong(Context context, String text) {
        if (context != null) {
            try {
                if (!StringUtil.isEmpty(text)) {
                    if (Build.MANUFACTURER.equals("Xiaomi") || Build.MANUFACTURER.equals("Meizu") || Build.MODEL.equals("U10")) {
                        if (ENABLED_FLAG == -1) {
                            initFlag(context);
                        }
                        if (ENABLED_FLAG == 0) {
                            Toast.makeText(StubApp.getOrigApplicationContext(context.getApplicationContext()), text, 1).show();
                            return;
                        } else if (ENABLED_FLAG == 1) {
                            ExToast.makeText(StubApp.getOrigApplicationContext(context.getApplicationContext()), (CharSequence) text, 3500).show();
                            return;
                        } else {
                            return;
                        }
                    }
                    SuperToast.create(StubApp.getOrigApplicationContext(context.getApplicationContext()), text, 3500).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void showToast(Context context, int resId) {
        if (context != null) {
            try {
                if (Build.MANUFACTURER.equals("Xiaomi") || Build.MANUFACTURER.equals("Meizu") || Build.MODEL.equals("U10")) {
                    if (ENABLED_FLAG == -1) {
                        initFlag(context);
                    }
                    if (ENABLED_FLAG == 0) {
                        Toast.makeText(StubApp.getOrigApplicationContext(context.getApplicationContext()), resId, 0).show();
                        return;
                    } else if (ENABLED_FLAG == 1) {
                        ExToast.makeText(StubApp.getOrigApplicationContext(context.getApplicationContext()), resId, 2000).show();
                        return;
                    } else {
                        return;
                    }
                }
                SuperToast.create(StubApp.getOrigApplicationContext(context.getApplicationContext()), context.getResources().getText(resId), 2000).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void showLong(Context context, int resId) {
        if (context != null) {
            try {
                if (Build.MANUFACTURER.equals("Xiaomi") || Build.MANUFACTURER.equals("Meizu") || Build.MODEL.equals("U10")) {
                    if (ENABLED_FLAG == -1) {
                        initFlag(context);
                    }
                    if (ENABLED_FLAG == 0) {
                        Toast.makeText(StubApp.getOrigApplicationContext(context.getApplicationContext()), resId, 1).show();
                        return;
                    } else if (ENABLED_FLAG == 1) {
                        ExToast.makeText(StubApp.getOrigApplicationContext(context.getApplicationContext()), resId, 3500).show();
                        return;
                    } else {
                        return;
                    }
                }
                SuperToast.create(StubApp.getOrigApplicationContext(context.getApplicationContext()), context.getResources().getText(resId), 3500).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
