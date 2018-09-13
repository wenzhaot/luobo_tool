package com.feng.car.utils;

import android.content.Context;
import com.feng.car.activity.BaseActivity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class ActivityManager {
    private static final Object LOCK = new Object();
    private static final String TAG = "AbsFrame";
    private static volatile ActivityManager mManager = null;
    private Stack<BaseActivity> mActivityStack = new Stack();
    private Context mContext;

    private ActivityManager(Context context) {
        this.mContext = context;
    }

    public static ActivityManager init(Context applicationContext) {
        if (mManager == null) {
            synchronized (LOCK) {
                if (mManager == null) {
                    mManager = new ActivityManager(applicationContext);
                }
            }
        }
        return mManager;
    }

    public static ActivityManager getInstance() {
        if (mManager != null) {
            return mManager;
        }
        throw new NullPointerException("请在application 的 onCreate 方法里面使用ActivityManager.init()方法进行初始化操作");
    }

    public Stack<BaseActivity> getActivityStack() {
        return this.mActivityStack;
    }

    public List getActitysList() {
        return new ArrayList(this.mActivityStack);
    }

    public int getActivitySize() {
        return this.mActivityStack.size();
    }

    public BaseActivity getActivity(int location) {
        return (BaseActivity) this.mActivityStack.get(location);
    }

    public void addActivity(BaseActivity activity) {
        if (this.mActivityStack == null) {
            this.mActivityStack = new Stack();
        }
        this.mActivityStack.add(activity);
    }

    public BaseActivity getCurrentActivity() {
        if (this.mActivityStack == null || this.mActivityStack.size() < 1) {
            return null;
        }
        return (BaseActivity) this.mActivityStack.lastElement();
    }

    public void finishActivity() {
        finishActivity((BaseActivity) this.mActivityStack.lastElement());
    }

    public void finishActivity(BaseActivity activity) {
        if (activity != null) {
            this.mActivityStack.remove(activity);
            activity.finish();
        }
    }

    public void removeActivity(BaseActivity activity) {
        if (activity != null) {
            this.mActivityStack.remove(activity);
        }
    }

    public void finishActivity(Class<?> cls) {
        Iterator it = this.mActivityStack.iterator();
        while (it.hasNext()) {
            BaseActivity activity = (BaseActivity) it.next();
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    public boolean hasContainsActivity(Class<?> cls) {
        Iterator it = this.mActivityStack.iterator();
        while (it.hasNext()) {
            if (((BaseActivity) it.next()).getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    public void finishAllActivity() {
        while (this.mActivityStack.size() > 0) {
            if (this.mActivityStack.lastElement() != null) {
                ((BaseActivity) this.mActivityStack.lastElement()).finish();
            }
        }
        this.mActivityStack.clear();
    }

    /* JADX WARNING: Failed to extract finally block: empty outs */
    public void exitApp(java.lang.Boolean r6) {
        /*
        r5 = this;
        r4 = 0;
        r5.finishAllActivity();	 Catch:{ Exception -> 0x0022 }
        r2 = r5.mContext;	 Catch:{ Exception -> 0x0022 }
        r3 = "activity";
        r0 = r2.getSystemService(r3);	 Catch:{ Exception -> 0x0022 }
        r0 = (android.app.ActivityManager) r0;	 Catch:{ Exception -> 0x0022 }
        r2 = r5.mContext;	 Catch:{ Exception -> 0x0022 }
        r2 = r2.getPackageName();	 Catch:{ Exception -> 0x0022 }
        r0.restartPackage(r2);	 Catch:{ Exception -> 0x0022 }
        r2 = r6.booleanValue();
        if (r2 != 0) goto L_0x0021;
    L_0x001e:
        java.lang.System.exit(r4);
    L_0x0021:
        return;
    L_0x0022:
        r1 = move-exception;
        r1.printStackTrace();	 Catch:{ all -> 0x0030 }
        r2 = r6.booleanValue();
        if (r2 != 0) goto L_0x0021;
    L_0x002c:
        java.lang.System.exit(r4);
        goto L_0x0021;
    L_0x0030:
        r2 = move-exception;
        r3 = r6.booleanValue();
        if (r3 != 0) goto L_0x003a;
    L_0x0037:
        java.lang.System.exit(r4);
    L_0x003a:
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.utils.ActivityManager.exitApp(java.lang.Boolean):void");
    }
}
