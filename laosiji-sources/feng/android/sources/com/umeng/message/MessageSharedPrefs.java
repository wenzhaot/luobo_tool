package com.umeng.message;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Log;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.proguard.l;
import com.umeng.message.proguard.m;
import com.umeng.message.provider.a;
import java.util.Calendar;
import org.android.agoo.common.AgooConstants;

public class MessageSharedPrefs {
    private static final String a = MessageSharedPrefs.class.getName();
    private static MessageSharedPrefs c = null;
    private static final String d = "tempkey";
    private static final String e = "tempvalue";
    private Context b;
    private SharedPreferences f;
    private int g = 0;

    private MessageSharedPrefs(Context context) {
        this.b = context;
        if (VERSION.SDK_INT > 11) {
            this.g |= 4;
        }
        this.f = context.getSharedPreferences(MsgConstant.PUSH_SHARED_PREFERENCES_FILE_NAME, this.g);
    }

    public static synchronized MessageSharedPrefs getInstance(Context context) {
        MessageSharedPrefs messageSharedPrefs;
        synchronized (MessageSharedPrefs.class) {
            if (c == null) {
                c = new MessageSharedPrefs(context);
            }
            messageSharedPrefs = c;
        }
        return messageSharedPrefs;
    }

    public boolean hasAppLaunchLogSentToday() {
        Calendar instance = Calendar.getInstance();
        try {
            instance.setTimeInMillis(m.a(this.b).f());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(a, e.toString());
        }
        Calendar instance2 = Calendar.getInstance();
        if (instance.get(6) == instance2.get(6) && instance.get(1) == instance2.get(1)) {
            return true;
        }
        return false;
    }

    public void setDisplayNotificationNumber(int i) {
        c(MsgConstant.KEY_NOTIFICATION_NUMBER, i + "");
    }

    public int getDisplayNotificationNumber() {
        return Integer.valueOf(b(MsgConstant.KEY_NOTIFICATION_NUMBER, PushConstants.PUSH_TYPE_THROUGH_MESSAGE)).intValue();
    }

    public void setMessageAppKey(String str) {
        if (str != null && !str.equals("")) {
            this.f.edit().putString(MsgConstant.KEY_UMENG_MESSAGE_APP_KEY, str).commit();
        }
    }

    public void removeMessageAppKey() {
        this.f.edit().remove(MsgConstant.KEY_UMENG_MESSAGE_APP_KEY).commit();
    }

    public String getMessageAppKey() {
        return this.f.getString(MsgConstant.KEY_UMENG_MESSAGE_APP_KEY, "");
    }

    public void setMessageAppSecret(String str) {
        if (str != null && !str.equals("")) {
            this.f.edit().putString(MsgConstant.KEY_UMENG_MESSAGE_APP_SECRET, str).commit();
        }
    }

    public void removeMessageAppSecret() {
        this.f.edit().remove(MsgConstant.KEY_UMENG_MESSAGE_APP_SECRET).commit();
    }

    public String getMessageAppSecret() {
        return this.f.getString(MsgConstant.KEY_UMENG_MESSAGE_APP_SECRET, "");
    }

    public void setMessageChannel(String str) {
        this.f.edit().putString(MsgConstant.KEY_UMENG_MESSAGE_APP_CHANNEL, str).commit();
    }

    public String getMessageChannel() {
        return this.f.getString(MsgConstant.KEY_UMENG_MESSAGE_APP_CHANNEL, "");
    }

    public void setAppLaunchLogSendPolicy(int i) {
        c(MsgConstant.KEY_APP_LAUNCH_LOG_SEND_POLICY, i + "");
    }

    public void setDaRegisterSendPolicy(int i) {
        c(MsgConstant.KEY_APP_DAREGISTER_LOG_SEND_POLICY, i + "");
    }

    public int getDaRegisterSendPolicy() {
        return Integer.valueOf(b(MsgConstant.KEY_APP_DAREGISTER_LOG_SEND_POLICY, "-1")).intValue();
    }

    public void setTagSendPolicy(int i) {
        c(MsgConstant.KEY_TAG_SEND_POLICY, i + "");
    }

    public int getAppLaunchLogSendPolicy() {
        return Integer.valueOf(b(MsgConstant.KEY_APP_LAUNCH_LOG_SEND_POLICY, "-1")).intValue();
    }

    public int getTagSendPolicy() {
        return Integer.valueOf(b(MsgConstant.KEY_TAG_SEND_POLICY, "-1")).intValue();
    }

    public void addAlias(String str, String str2, int i, int i2, String str3) {
        try {
            a(str2, str3);
            String str4 = "alias=? and type=? and exclusive=?";
            String[] strArr = new String[]{str, str2, i + ""};
            ContentResolver contentResolver = this.b.getContentResolver();
            a.a(this.b);
            Cursor query = contentResolver.query(a.d, new String[]{"error"}, str4, strArr, "time desc");
            ContentValues contentValues;
            ContentResolver contentResolver2;
            if (query == null) {
                contentValues = new ContentValues();
                contentValues.put("time", Long.valueOf(System.currentTimeMillis()));
                contentValues.put("type", str2);
                contentValues.put("alias", str);
                contentValues.put(l.A, Integer.valueOf(i));
                contentValues.put("error", Integer.valueOf(i2));
                contentValues.put("message", str3);
                contentResolver2 = this.b.getContentResolver();
                a.a(this.b);
                contentResolver2.insert(a.d, contentValues);
            } else if (query.getCount() > 0) {
                query.moveToFirst();
                contentValues = new ContentValues();
                contentValues.put("time", Long.valueOf(System.currentTimeMillis()));
                contentValues.put("type", str2);
                contentValues.put("alias", str);
                contentValues.put(l.A, Integer.valueOf(i));
                contentValues.put("error", Integer.valueOf(i2));
                contentValues.put("message", str3);
                contentResolver2 = this.b.getContentResolver();
                a.a(this.b);
                contentResolver2.update(a.d, contentValues, str4, strArr);
            } else {
                contentValues = new ContentValues();
                contentValues.put("time", Long.valueOf(System.currentTimeMillis()));
                contentValues.put("type", str2);
                contentValues.put("alias", str);
                contentValues.put(l.A, Integer.valueOf(i));
                contentValues.put("error", Integer.valueOf(i2));
                contentValues.put("message", str3);
                contentResolver2 = this.b.getContentResolver();
                a.a(this.b);
                contentResolver2.insert(a.d, contentValues);
            }
            if (query != null) {
                query.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void a(String str, String str2) {
        String[] strArr = new String[]{str, str2};
        ContentResolver contentResolver = this.b.getContentResolver();
        a.a(this.b);
        contentResolver.delete(a.d, "type=? and message=? ", strArr);
    }

    public void removeAlias(int i, String str, String str2) {
        try {
            String[] strArr = new String[]{str2, str, i + ""};
            ContentResolver contentResolver = this.b.getContentResolver();
            a.a(this.b);
            contentResolver.delete(a.d, "type=? and alias=? and exclusive=? ", strArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLastAlias(int i, String str) {
        String str2;
        Exception e;
        String str3 = "";
        try {
            String[] strArr = new String[]{str, i + "", PushConstants.PUSH_TYPE_NOTIFY, PushConstants.PUSH_TYPE_UPLOAD_LOG};
            ContentResolver contentResolver = this.b.getContentResolver();
            a.a(this.b);
            Cursor query = contentResolver.query(a.d, new String[]{"alias"}, "type=? and exclusive=? and (error=? or error = ?)", strArr, "time desc");
            if (query == null || query.getCount() <= 0) {
                str2 = str3;
            } else {
                query.moveToFirst();
                str2 = query.getString(query.getColumnIndex("alias"));
            }
            if (query != null) {
                try {
                    query.close();
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    return str2;
                }
            }
        } catch (Exception e3) {
            e = e3;
            str2 = str3;
            e.printStackTrace();
            return str2;
        }
        return str2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x011f A:{SYNTHETIC, Splitter: B:12:0x011f} */
    public boolean isAliasSet(int r12, java.lang.String r13, java.lang.String r14) {
        /*
        r11 = this;
        r7 = 1;
        r6 = 0;
        r3 = "type=? and alias=? and exclusive=? and (error=? or error = ?)";
        r0 = 5;
        r4 = new java.lang.String[r0];	 Catch:{ Exception -> 0x0123 }
        r0 = 0;
        r4[r0] = r14;	 Catch:{ Exception -> 0x0123 }
        r0 = 1;
        r4[r0] = r13;	 Catch:{ Exception -> 0x0123 }
        r0 = 2;
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0123 }
        r1.<init>();	 Catch:{ Exception -> 0x0123 }
        r1 = r1.append(r12);	 Catch:{ Exception -> 0x0123 }
        r2 = "";
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x0123 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x0123 }
        r4[r0] = r1;	 Catch:{ Exception -> 0x0123 }
        r0 = 3;
        r1 = "0";
        r4[r0] = r1;	 Catch:{ Exception -> 0x0123 }
        r0 = 4;
        r1 = "2";
        r4[r0] = r1;	 Catch:{ Exception -> 0x0123 }
        r0 = com.umeng.commonsdk.UMConfigure.umDebugLog;	 Catch:{ Exception -> 0x0123 }
        r0 = a;	 Catch:{ Exception -> 0x0123 }
        r1 = 2;
        r2 = 1;
        r2 = new java.lang.String[r2];	 Catch:{ Exception -> 0x0123 }
        r5 = 0;
        r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0123 }
        r8.<init>();	 Catch:{ Exception -> 0x0123 }
        r9 = "type:";
        r8 = r8.append(r9);	 Catch:{ Exception -> 0x0123 }
        r8 = r8.append(r14);	 Catch:{ Exception -> 0x0123 }
        r9 = ",alias:";
        r8 = r8.append(r9);	 Catch:{ Exception -> 0x0123 }
        r8 = r8.append(r13);	 Catch:{ Exception -> 0x0123 }
        r9 = ",exclusive:";
        r8 = r8.append(r9);	 Catch:{ Exception -> 0x0123 }
        r8 = r8.append(r12);	 Catch:{ Exception -> 0x0123 }
        r8 = r8.toString();	 Catch:{ Exception -> 0x0123 }
        r2[r5] = r8;	 Catch:{ Exception -> 0x0123 }
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r0, r1, r2);	 Catch:{ Exception -> 0x0123 }
        r0 = r11.b;	 Catch:{ Exception -> 0x0123 }
        r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x0123 }
        r1 = r11.b;	 Catch:{ Exception -> 0x0123 }
        com.umeng.message.provider.a.a(r1);	 Catch:{ Exception -> 0x0123 }
        r1 = com.umeng.message.provider.a.d;	 Catch:{ Exception -> 0x0123 }
        r2 = 2;
        r2 = new java.lang.String[r2];	 Catch:{ Exception -> 0x0123 }
        r5 = 0;
        r8 = "type";
        r2[r5] = r8;	 Catch:{ Exception -> 0x0123 }
        r5 = 1;
        r8 = "alias";
        r2[r5] = r8;	 Catch:{ Exception -> 0x0123 }
        r5 = 0;
        r1 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x0123 }
        if (r1 == 0) goto L_0x0128;
    L_0x008c:
        r0 = r1.getCount();	 Catch:{ Exception -> 0x0123 }
        r2 = com.umeng.commonsdk.UMConfigure.umDebugLog;	 Catch:{ Exception -> 0x0123 }
        r2 = a;	 Catch:{ Exception -> 0x0123 }
        r3 = 2;
        r4 = 1;
        r4 = new java.lang.String[r4];	 Catch:{ Exception -> 0x0123 }
        r5 = 0;
        r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0123 }
        r8.<init>();	 Catch:{ Exception -> 0x0123 }
        r9 = "count:";
        r8 = r8.append(r9);	 Catch:{ Exception -> 0x0123 }
        r8 = r8.append(r0);	 Catch:{ Exception -> 0x0123 }
        r8 = r8.toString();	 Catch:{ Exception -> 0x0123 }
        r4[r5] = r8;	 Catch:{ Exception -> 0x0123 }
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r2, r3, r4);	 Catch:{ Exception -> 0x0123 }
        if (r0 <= 0) goto L_0x0128;
    L_0x00b4:
        r1.moveToFirst();	 Catch:{ Exception -> 0x0123 }
        r0 = "type";
        r0 = r1.getColumnIndex(r0);	 Catch:{ Exception -> 0x0123 }
        r0 = r1.getString(r0);	 Catch:{ Exception -> 0x0123 }
        r2 = "alias";
        r2 = r1.getColumnIndex(r2);	 Catch:{ Exception -> 0x0123 }
        r2 = r1.getString(r2);	 Catch:{ Exception -> 0x0123 }
        r3 = com.umeng.commonsdk.UMConfigure.umDebugLog;	 Catch:{ Exception -> 0x0123 }
        r3 = a;	 Catch:{ Exception -> 0x0123 }
        r4 = 2;
        r5 = 1;
        r5 = new java.lang.String[r5];	 Catch:{ Exception -> 0x0123 }
        r8 = 0;
        r9 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0123 }
        r9.<init>();	 Catch:{ Exception -> 0x0123 }
        r10 = "typeTmp:";
        r9 = r9.append(r10);	 Catch:{ Exception -> 0x0123 }
        r9 = r9.append(r0);	 Catch:{ Exception -> 0x0123 }
        r10 = ",aliasTmp:";
        r9 = r9.append(r10);	 Catch:{ Exception -> 0x0123 }
        r9 = r9.append(r2);	 Catch:{ Exception -> 0x0123 }
        r10 = ",type:";
        r9 = r9.append(r10);	 Catch:{ Exception -> 0x0123 }
        r9 = r9.append(r14);	 Catch:{ Exception -> 0x0123 }
        r10 = ",alis:";
        r9 = r9.append(r10);	 Catch:{ Exception -> 0x0123 }
        r9 = r9.append(r13);	 Catch:{ Exception -> 0x0123 }
        r9 = r9.toString();	 Catch:{ Exception -> 0x0123 }
        r5[r8] = r9;	 Catch:{ Exception -> 0x0123 }
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r3, r4, r5);	 Catch:{ Exception -> 0x0123 }
        r0 = r0.equalsIgnoreCase(r14);	 Catch:{ Exception -> 0x0123 }
        if (r0 == 0) goto L_0x0128;
    L_0x0116:
        r0 = r2.equalsIgnoreCase(r13);	 Catch:{ Exception -> 0x0123 }
        if (r0 == 0) goto L_0x0128;
    L_0x011c:
        r0 = r7;
    L_0x011d:
        if (r1 == 0) goto L_0x0122;
    L_0x011f:
        r1.close();	 Catch:{ Exception -> 0x0126 }
    L_0x0122:
        return r0;
    L_0x0123:
        r0 = move-exception;
        r0 = r6;
        goto L_0x0122;
    L_0x0126:
        r1 = move-exception;
        goto L_0x0122;
    L_0x0128:
        r0 = r6;
        goto L_0x011d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.MessageSharedPrefs.isAliasSet(int, java.lang.String, java.lang.String):boolean");
    }

    public void addTags(String... strArr) {
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            String format = String.format("UMENG_TAG_%s", new Object[]{strArr[i]});
            if (!Boolean.valueOf(b(format, "false")).booleanValue()) {
                c(format, ITagManager.STATUS_TRUE);
                c("UMENG_TAG_COUNT", (getTagCount() + 1) + "");
            }
        }
    }

    public void removeTags(String... strArr) {
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            String format = String.format("UMENG_TAG_%s", new Object[]{strArr[i]});
            if (Boolean.valueOf(b(format, "false")).booleanValue()) {
                removeKeyAndValue(format);
                c("UMENG_TAG_COUNT", (getTagCount() - 1) + "");
            }
        }
    }

    public boolean isTagSet(String str) {
        return Boolean.valueOf(b(String.format("UMENG_TAG_%s", new Object[]{str}), "false")).booleanValue();
    }

    public int getTagCount() {
        return Integer.valueOf(b("UMENG_TAG_COUNT", PushConstants.PUSH_TYPE_NOTIFY)).intValue();
    }

    public void add_addTagsInterval(String str) {
        if (str != null) {
            c(MsgConstant.KEY_ADDTAGS, str);
        }
    }

    public String get_addTagsInterval() {
        return b(MsgConstant.KEY_ADDTAGS, null);
    }

    public void add_deleteTagsInterval(String str) {
        if (str != null) {
            c(MsgConstant.KEY_DELETETAGS, str);
        }
    }

    public String get_deleteTagsInterval() {
        return b(MsgConstant.KEY_DELETETAGS, null);
    }

    public void add_getTagsInteral(String str) {
        if (str != null) {
            c(MsgConstant.KEY_GETTAGS, str);
        }
    }

    public String get_getTagsInterval() {
        return b(MsgConstant.KEY_GETTAGS, null);
    }

    public void setAddWeightedTagsInterval(String str) {
        if (!TextUtils.isEmpty(str)) {
            c(MsgConstant.KEY_ADD_WEIGHTED_TAGS, str);
        }
    }

    public String getAddWeightedTagsInterval() {
        return b(MsgConstant.KEY_ADD_WEIGHTED_TAGS, null);
    }

    public void setDeleteWeightedTagsInterval(String str) {
        if (!TextUtils.isEmpty(str)) {
            c(MsgConstant.KEY_DELETE_WEIGHTED_TAGS, str);
        }
    }

    public String getDeleteWeightedTagsInterval() {
        return b(MsgConstant.KEY_DELETE_WEIGHTED_TAGS, null);
    }

    public void setListWeightedTagsInterval(String str) {
        if (!TextUtils.isEmpty(str)) {
            c(MsgConstant.KEY_LIST_WEIGHTED_TAGS, str);
        }
    }

    public String getListWeightedTagsInterval() {
        return b(MsgConstant.KEY_LIST_WEIGHTED_TAGS, null);
    }

    public void add_addAliasInterval(String str) {
        if (str != null) {
            c(MsgConstant.KEY_ADDALIAS, str);
        }
    }

    public String get_addAliasInterval() {
        return b(MsgConstant.KEY_ADDALIAS, null);
    }

    public void add_setAliasInterval(String str) {
        if (str != null) {
            c("setAlias", str);
        }
    }

    public String get_setAliasInterval() {
        return b("setAlias", null);
    }

    public void add_deleteAliasInterval(String str) {
        if (str != null) {
            c(MsgConstant.KEY_DELETEALIAS, str);
        }
    }

    public String get_deleteALiasInterval() {
        return b(MsgConstant.KEY_DELETEALIAS, null);
    }

    public void setTagRemain(int i) {
        c(MsgConstant.KET_UMENG_TAG_REMAIN, i + "");
    }

    public int getTagRemain() {
        return Integer.valueOf(b(MsgConstant.KET_UMENG_TAG_REMAIN, "64")).intValue();
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0088  */
    public void resetTags() {
        /*
        r9 = this;
        r7 = 0;
        r6 = 0;
        r8 = new java.util.ArrayList;	 Catch:{ Exception -> 0x008e, all -> 0x0084 }
        r8.<init>();	 Catch:{ Exception -> 0x008e, all -> 0x0084 }
        r0 = r9.b;	 Catch:{ Exception -> 0x008e, all -> 0x0084 }
        r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x008e, all -> 0x0084 }
        r1 = r9.b;	 Catch:{ Exception -> 0x008e, all -> 0x0084 }
        com.umeng.message.provider.a.a(r1);	 Catch:{ Exception -> 0x008e, all -> 0x0084 }
        r1 = com.umeng.message.provider.a.c;	 Catch:{ Exception -> 0x008e, all -> 0x0084 }
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r1 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x008e, all -> 0x0084 }
        if (r1 == 0) goto L_0x0054;
    L_0x001e:
        r0 = r1.getCount();	 Catch:{ Exception -> 0x0048 }
        if (r0 <= 0) goto L_0x0054;
    L_0x0024:
        r1.moveToFirst();	 Catch:{ Exception -> 0x0048 }
    L_0x0027:
        r0 = r1.isAfterLast();	 Catch:{ Exception -> 0x0048 }
        if (r0 != 0) goto L_0x0054;
    L_0x002d:
        r0 = "tempkey";
        r0 = r1.getColumnIndex(r0);	 Catch:{ Exception -> 0x0048 }
        r0 = r1.getString(r0);	 Catch:{ Exception -> 0x0048 }
        r2 = "UMENG_TAG";
        r2 = r0.contains(r2);	 Catch:{ Exception -> 0x0048 }
        if (r2 == 0) goto L_0x0044;
    L_0x0041:
        r8.add(r0);	 Catch:{ Exception -> 0x0048 }
    L_0x0044:
        r1.moveToNext();	 Catch:{ Exception -> 0x0048 }
        goto L_0x0027;
    L_0x0048:
        r0 = move-exception;
    L_0x0049:
        if (r0 == 0) goto L_0x004e;
    L_0x004b:
        r0.printStackTrace();	 Catch:{ all -> 0x008c }
    L_0x004e:
        if (r1 == 0) goto L_0x0053;
    L_0x0050:
        r1.close();
    L_0x0053:
        return;
    L_0x0054:
        r2 = r7;
    L_0x0055:
        r0 = r8.size();	 Catch:{ Exception -> 0x0048 }
        if (r2 >= r0) goto L_0x007e;
    L_0x005b:
        r3 = "tempkey=?";
        r0 = 1;
        r4 = new java.lang.String[r0];	 Catch:{ Exception -> 0x0048 }
        r5 = 0;
        r0 = r8.get(r2);	 Catch:{ Exception -> 0x0048 }
        r0 = (java.lang.String) r0;	 Catch:{ Exception -> 0x0048 }
        r4[r5] = r0;	 Catch:{ Exception -> 0x0048 }
        r0 = r9.b;	 Catch:{ Exception -> 0x0048 }
        r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x0048 }
        r5 = r9.b;	 Catch:{ Exception -> 0x0048 }
        com.umeng.message.provider.a.a(r5);	 Catch:{ Exception -> 0x0048 }
        r5 = com.umeng.message.provider.a.c;	 Catch:{ Exception -> 0x0048 }
        r0.delete(r5, r3, r4);	 Catch:{ Exception -> 0x0048 }
        r0 = r2 + 1;
        r2 = r0;
        goto L_0x0055;
    L_0x007e:
        if (r1 == 0) goto L_0x0053;
    L_0x0080:
        r1.close();
        goto L_0x0053;
    L_0x0084:
        r0 = move-exception;
        r1 = r6;
    L_0x0086:
        if (r1 == 0) goto L_0x008b;
    L_0x0088:
        r1.close();
    L_0x008b:
        throw r0;
    L_0x008c:
        r0 = move-exception;
        goto L_0x0086;
    L_0x008e:
        r0 = move-exception;
        r1 = r6;
        goto L_0x0049;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.MessageSharedPrefs.resetTags():void");
    }

    void a(int i, int i2, int i3, int i4) {
        c(MsgConstant.KEY_NO_DISTURB_START_HOUR, i + "");
        c(MsgConstant.KEY_NO_DISTURB_START_HOUR, i2 + "");
        c(MsgConstant.KEY_NO_DISTURB_END_HOUR, i3 + "");
        c(MsgConstant.KEY_NO_DISTURB_END_MINUTE, i4 + "");
    }

    int a() {
        return Integer.valueOf(b(MsgConstant.KEY_NO_DISTURB_START_HOUR, AgooConstants.REPORT_DUPLICATE_FAIL)).intValue();
    }

    int b() {
        return Integer.valueOf(b(MsgConstant.KEY_NO_DISTURB_START_MINUTE, PushConstants.PUSH_TYPE_NOTIFY)).intValue();
    }

    int c() {
        return Integer.valueOf(b(MsgConstant.KEY_NO_DISTURB_END_HOUR, MsgConstant.MESSAGE_NOTIFY_ARRIVAL)).intValue();
    }

    int d() {
        return Integer.valueOf(b(MsgConstant.KEY_NO_DISTURB_END_MINUTE, PushConstants.PUSH_TYPE_NOTIFY)).intValue();
    }

    void e() {
        c(MsgConstant.KEY_ENEABLED, ITagManager.STATUS_TRUE);
    }

    void f() {
        c(MsgConstant.KEY_ENEABLED, "false");
    }

    boolean g() {
        if (b(MsgConstant.KEY_ENEABLED, "false").equalsIgnoreCase(ITagManager.STATUS_TRUE)) {
            return true;
        }
        return false;
    }

    boolean a(String str) {
        if (b("device_token", "").equalsIgnoreCase(str)) {
            return true;
        }
        return false;
    }

    public void setIsEnabled(boolean z) {
        c(MsgConstant.KEY_ISENABLED, String.valueOf(z));
    }

    public boolean isEnabled() {
        String b = b(MsgConstant.KEY_ISENABLED, "");
        if (b.equalsIgnoreCase(ITagManager.STATUS_TRUE) || b.equalsIgnoreCase("")) {
            return true;
        }
        return false;
    }

    public boolean hasTransferedCacheFileDataToSQL() {
        if (b(MsgConstant.KEY_CACHE_FILE_TRANSFER_TO_SQL, "false").equalsIgnoreCase(ITagManager.STATUS_TRUE)) {
            return true;
        }
        return false;
    }

    public boolean finishTransferedCacheFileDataToSQL() {
        if (b(MsgConstant.KEY_CACHE_FILE_TRANSFER_TO_SQL, ITagManager.STATUS_TRUE).equalsIgnoreCase(ITagManager.STATUS_TRUE)) {
            return true;
        }
        return false;
    }

    public <U extends UmengMessageService> void setPushIntentServiceClass(Class<U> cls) {
        if (cls == null) {
            removeKeyAndValue(MsgConstant.KEY_PUSH_INTENT_SERVICE_CLASSNAME);
        } else {
            c(MsgConstant.KEY_PUSH_INTENT_SERVICE_CLASSNAME, cls.getName());
        }
    }

    public String getPushIntentServiceClass() {
        String b = b(MsgConstant.KEY_PUSH_INTENT_SERVICE_CLASSNAME, "");
        if (b.equalsIgnoreCase("")) {
            return "";
        }
        try {
            Class.forName(b);
            return b;
        } catch (ClassNotFoundException e) {
            return "";
        }
    }

    public boolean hasMessageResourceDownloaded(String str) {
        if (b(MsgConstant.KEY_MSG_RESOURCE_DOWNLOAD_PREFIX + str, "false").equals(ITagManager.STATUS_TRUE)) {
            return true;
        }
        return false;
    }

    public void setMessageResourceDownloaded(String str) {
        c(MsgConstant.KEY_MSG_RESOURCE_DOWNLOAD_PREFIX + str, ITagManager.STATUS_TRUE);
    }

    public void removeMessageResouceRecord(String str) {
        removeKeyAndValue(MsgConstant.KEY_MSG_RESOURCE_DOWNLOAD_PREFIX + str);
    }

    public void setLastMessageMsgID(String str) {
        c(MsgConstant.KEY_LAST_MSG_ID, str);
    }

    public String getLastMessageMsgID() {
        return b(MsgConstant.KEY_LAST_MSG_ID, "");
    }

    public void setMuteDuration(int i) {
        c(MsgConstant.KEY_MUTE_DURATION, i + "");
    }

    public int getMuteDuration() {
        return Integer.valueOf(b(MsgConstant.KEY_MUTE_DURATION, "60")).intValue();
    }

    public void setSerialNo(int i) {
        c(MsgConstant.KEY_SERIA_NO, i + "");
    }

    public int getSerialNo() {
        return Integer.valueOf(b(MsgConstant.KEY_SERIA_NO, PushConstants.PUSH_TYPE_THROUGH_MESSAGE)).intValue();
    }

    public boolean getNotificaitonOnForeground() {
        if (b(MsgConstant.KEY_SET_NOTIFICATION_ON_FOREGROUND, ITagManager.STATUS_TRUE).equals(ITagManager.STATUS_TRUE)) {
            return true;
        }
        return false;
    }

    public void setNotificaitonOnForeground(boolean z) {
        c(MsgConstant.KEY_SET_NOTIFICATION_ON_FOREGROUND, String.valueOf(z));
    }

    public String getResourcePackageName() {
        return b(MsgConstant.KEY_SET_RESOURCE_PACKAGENAME, "");
    }

    public void setResourcePackageName(String str) {
        c(MsgConstant.KEY_SET_RESOURCE_PACKAGENAME, str);
    }

    public int getNotificationPlayVibrate() {
        return Integer.valueOf(b(MsgConstant.KEY_SET_NOTIFICATION_PLAY_VIBRATE, PushConstants.PUSH_TYPE_NOTIFY)).intValue();
    }

    public void setNotificationPlayVibrate(int i) {
        c(MsgConstant.KEY_SET_NOTIFICATION_PLAY_VIBRATE, i + "");
    }

    public int getNotificationPlayLights() {
        return Integer.valueOf(b(MsgConstant.KEY_SET_NOTIFICATION_PLAY_LIGHTS, PushConstants.PUSH_TYPE_NOTIFY)).intValue();
    }

    public void setNotificationPlayLights(int i) {
        c(MsgConstant.KEY_SET_NOTIFICATION_PLAY_LIGHTS, i + "");
    }

    public int getNotificationPlaySound() {
        return Integer.valueOf(b(MsgConstant.KEY_SET_NOTIFICATION_PLAY_SOUND, PushConstants.PUSH_TYPE_NOTIFY)).intValue();
    }

    public void setNotificationPlaySound(int i) {
        c(MsgConstant.KEY_SET_NOTIFICATION_PLAY_SOUND, i + "");
    }

    public void setAppVersion(String str) {
        if (str == null) {
            removeKeyAndValue("app_version");
        } else {
            c("app_version", str);
        }
    }

    public String getAppVersion() {
        return b("app_version", "");
    }

    public void setDeviceToken(String str) {
        if (str == null) {
            removeKeyAndValue("device_token");
        } else {
            c("device_token", str);
        }
    }

    public String getDeviceToken() {
        return b("device_token", "");
    }

    public void setUmid(String str) {
        this.f.edit().putString("KEY_SET_UMID", str).apply();
    }

    public String getUmid() {
        return this.f.getString("KEY_SET_UMID", "");
    }

    void b(String str) {
        this.f.edit().putString(MsgConstant.KEY_NOTIFICATION_CHANNEL, str).apply();
    }

    String h() {
        return this.f.getString(MsgConstant.KEY_NOTIFICATION_CHANNEL, "");
    }

    public void setLocationInterval(int i) {
        if (i < 2 || i > 1800) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo("LBS", 2, "LBS的请求间隔应该在2至1800秒之间");
            return;
        }
        c("interval", i + "");
    }

    public int getLocationInterval() {
        return Integer.valueOf(b("interval", "600")).intValue();
    }

    public void setHasResgister(boolean z) {
        c(MsgConstant.KEY_HASREGISTER, String.valueOf(z));
    }

    public boolean getHasRegister() {
        if (b(MsgConstant.KEY_HASREGISTER, "false").equalsIgnoreCase(ITagManager.STATUS_TRUE)) {
            return true;
        }
        return false;
    }

    public int getRegisterTimes() {
        return Integer.valueOf(getInstance(this.b).b(MsgConstant.KEY_REGISTER_TIMES, PushConstants.PUSH_TYPE_NOTIFY)).intValue();
    }

    public void setRegisterTimes(int i) {
        c(MsgConstant.KEY_REGISTER_TIMES, i + "");
    }

    public String getUcode() {
        return getInstance(this.b).b(MsgConstant.KEY_UCODE, "");
    }

    public void setUcode(String str) {
        c(MsgConstant.KEY_UCODE, str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x005f  */
    private java.lang.String b(java.lang.String r9, java.lang.String r10) {
        /*
        r8 = this;
        r6 = 0;
        r0 = new android.content.ContentValues;	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r0.<init>();	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r1 = "tempkey";
        r0.put(r1, r9);	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r3 = "tempkey=?";
        r0 = 1;
        r4 = new java.lang.String[r0];	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r0 = 0;
        r4[r0] = r9;	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r0 = r8.b;	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r1 = r8.b;	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        com.umeng.message.provider.a.a(r1);	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r1 = com.umeng.message.provider.a.c;	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r2 = 1;
        r2 = new java.lang.String[r2];	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r5 = 0;
        r7 = "tempvalue";
        r2[r5] = r7;	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        r5 = 0;
        r1 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x004f, all -> 0x005c }
        if (r1 != 0) goto L_0x0038;
    L_0x0032:
        if (r1 == 0) goto L_0x0037;
    L_0x0034:
        r1.close();
    L_0x0037:
        return r10;
    L_0x0038:
        r0 = r1.moveToFirst();	 Catch:{ Exception -> 0x0066 }
        if (r0 == 0) goto L_0x0049;
    L_0x003e:
        r0 = "tempvalue";
        r0 = r1.getColumnIndex(r0);	 Catch:{ Exception -> 0x0066 }
        r10 = r1.getString(r0);	 Catch:{ Exception -> 0x0066 }
    L_0x0049:
        if (r1 == 0) goto L_0x0037;
    L_0x004b:
        r1.close();
        goto L_0x0037;
    L_0x004f:
        r0 = move-exception;
        r1 = r6;
    L_0x0051:
        if (r0 == 0) goto L_0x0056;
    L_0x0053:
        r0.printStackTrace();	 Catch:{ all -> 0x0063 }
    L_0x0056:
        if (r1 == 0) goto L_0x0037;
    L_0x0058:
        r1.close();
        goto L_0x0037;
    L_0x005c:
        r0 = move-exception;
    L_0x005d:
        if (r6 == 0) goto L_0x0062;
    L_0x005f:
        r6.close();
    L_0x0062:
        throw r0;
    L_0x0063:
        r0 = move-exception;
        r6 = r1;
        goto L_0x005d;
    L_0x0066:
        r0 = move-exception;
        goto L_0x0051;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.MessageSharedPrefs.b(java.lang.String, java.lang.String):java.lang.String");
    }

    private void c(final String str, final String str2) {
        ThreadPoolExecutorFactory.execute(new Runnable() {
            /* JADX WARNING: Removed duplicated region for block: B:22:0x00cd  */
            public void run() {
                /*
                r8 = this;
                r6 = 0;
                r3 = "tempkey=?";
                r0 = 1;
                r4 = new java.lang.String[r0];	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r0 = 0;
                r1 = r2;	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r4[r0] = r1;	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r0 = com.umeng.message.MessageSharedPrefs.this;	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r0 = r0.b;	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r1 = com.umeng.message.MessageSharedPrefs.this;	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r1 = r1.b;	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                com.umeng.message.provider.a.a(r1);	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r1 = com.umeng.message.provider.a.c;	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r2 = 1;
                r2 = new java.lang.String[r2];	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r5 = 0;
                r7 = "tempvalue";
                r2[r5] = r7;	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                r5 = 0;
                r1 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x00d4, all -> 0x00d1 }
                if (r1 != 0) goto L_0x0064;
            L_0x0031:
                r0 = new android.content.ContentValues;	 Catch:{ Exception -> 0x0090 }
                r0.<init>();	 Catch:{ Exception -> 0x0090 }
                r2 = "tempkey";
                r3 = r2;	 Catch:{ Exception -> 0x0090 }
                r0.put(r2, r3);	 Catch:{ Exception -> 0x0090 }
                r2 = "tempvalue";
                r3 = r3;	 Catch:{ Exception -> 0x0090 }
                r0.put(r2, r3);	 Catch:{ Exception -> 0x0090 }
                r2 = com.umeng.message.MessageSharedPrefs.this;	 Catch:{ Exception -> 0x0090 }
                r2 = r2.b;	 Catch:{ Exception -> 0x0090 }
                r2 = r2.getContentResolver();	 Catch:{ Exception -> 0x0090 }
                r3 = com.umeng.message.MessageSharedPrefs.this;	 Catch:{ Exception -> 0x0090 }
                r3 = r3.b;	 Catch:{ Exception -> 0x0090 }
                com.umeng.message.provider.a.a(r3);	 Catch:{ Exception -> 0x0090 }
                r3 = com.umeng.message.provider.a.c;	 Catch:{ Exception -> 0x0090 }
                r2.insert(r3, r0);	 Catch:{ Exception -> 0x0090 }
            L_0x005e:
                if (r1 == 0) goto L_0x0063;
            L_0x0060:
                r1.close();
            L_0x0063:
                return;
            L_0x0064:
                r0 = r1.moveToFirst();	 Catch:{ Exception -> 0x0090 }
                if (r0 == 0) goto L_0x009c;
            L_0x006a:
                r0 = new android.content.ContentValues;	 Catch:{ Exception -> 0x0090 }
                r0.<init>();	 Catch:{ Exception -> 0x0090 }
                r2 = "tempvalue";
                r5 = r3;	 Catch:{ Exception -> 0x0090 }
                r0.put(r2, r5);	 Catch:{ Exception -> 0x0090 }
                r2 = com.umeng.message.MessageSharedPrefs.this;	 Catch:{ Exception -> 0x0090 }
                r2 = r2.b;	 Catch:{ Exception -> 0x0090 }
                r2 = r2.getContentResolver();	 Catch:{ Exception -> 0x0090 }
                r5 = com.umeng.message.MessageSharedPrefs.this;	 Catch:{ Exception -> 0x0090 }
                r5 = r5.b;	 Catch:{ Exception -> 0x0090 }
                com.umeng.message.provider.a.a(r5);	 Catch:{ Exception -> 0x0090 }
                r5 = com.umeng.message.provider.a.c;	 Catch:{ Exception -> 0x0090 }
                r2.update(r5, r0, r3, r4);	 Catch:{ Exception -> 0x0090 }
                goto L_0x005e;
            L_0x0090:
                r0 = move-exception;
            L_0x0091:
                if (r0 == 0) goto L_0x0096;
            L_0x0093:
                r0.printStackTrace();	 Catch:{ all -> 0x00ca }
            L_0x0096:
                if (r1 == 0) goto L_0x0063;
            L_0x0098:
                r1.close();
                goto L_0x0063;
            L_0x009c:
                r0 = new android.content.ContentValues;	 Catch:{ Exception -> 0x0090 }
                r0.<init>();	 Catch:{ Exception -> 0x0090 }
                r2 = "tempkey";
                r3 = r2;	 Catch:{ Exception -> 0x0090 }
                r0.put(r2, r3);	 Catch:{ Exception -> 0x0090 }
                r2 = "tempvalue";
                r3 = r3;	 Catch:{ Exception -> 0x0090 }
                r0.put(r2, r3);	 Catch:{ Exception -> 0x0090 }
                r2 = com.umeng.message.MessageSharedPrefs.this;	 Catch:{ Exception -> 0x0090 }
                r2 = r2.b;	 Catch:{ Exception -> 0x0090 }
                r2 = r2.getContentResolver();	 Catch:{ Exception -> 0x0090 }
                r3 = com.umeng.message.MessageSharedPrefs.this;	 Catch:{ Exception -> 0x0090 }
                r3 = r3.b;	 Catch:{ Exception -> 0x0090 }
                com.umeng.message.provider.a.a(r3);	 Catch:{ Exception -> 0x0090 }
                r3 = com.umeng.message.provider.a.c;	 Catch:{ Exception -> 0x0090 }
                r2.insert(r3, r0);	 Catch:{ Exception -> 0x0090 }
                goto L_0x005e;
            L_0x00ca:
                r0 = move-exception;
            L_0x00cb:
                if (r1 == 0) goto L_0x00d0;
            L_0x00cd:
                r1.close();
            L_0x00d0:
                throw r0;
            L_0x00d1:
                r0 = move-exception;
                r1 = r6;
                goto L_0x00cb;
            L_0x00d4:
                r0 = move-exception;
                r1 = r6;
                goto L_0x0091;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.MessageSharedPrefs.1.run():void");
            }
        });
    }

    public void removeKeyAndValue(final String str) {
        ThreadPoolExecutorFactory.execute(new Runnable() {
            /* JADX WARNING: Removed duplicated region for block: B:15:0x006c  */
            public void run() {
                /*
                r7 = this;
                r6 = 0;
                r0 = new android.content.ContentValues;	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r0.<init>();	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r1 = "tempkey";
                r2 = r2;	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r0.put(r1, r2);	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r0 = com.umeng.message.MessageSharedPrefs.this;	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r0 = r0.b;	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r1 = com.umeng.message.MessageSharedPrefs.this;	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r1 = r1.b;	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                com.umeng.message.provider.a.a(r1);	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r1 = com.umeng.message.provider.a.c;	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r2 = 1;
                r2 = new java.lang.String[r2];	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r3 = 0;
                r4 = "tempvalue";
                r2[r3] = r4;	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                r3 = 0;
                r4 = 0;
                r5 = 0;
                r1 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x0073, all -> 0x0069 }
                if (r1 != 0) goto L_0x003b;
            L_0x0035:
                if (r1 == 0) goto L_0x003a;
            L_0x0037:
                r1.close();
            L_0x003a:
                return;
            L_0x003b:
                r0 = "tempkey=?";
                r2 = 1;
                r2 = new java.lang.String[r2];	 Catch:{ Exception -> 0x005f }
                r3 = 0;
                r4 = r2;	 Catch:{ Exception -> 0x005f }
                r2[r3] = r4;	 Catch:{ Exception -> 0x005f }
                r3 = com.umeng.message.MessageSharedPrefs.this;	 Catch:{ Exception -> 0x005f }
                r3 = r3.b;	 Catch:{ Exception -> 0x005f }
                r3 = r3.getContentResolver();	 Catch:{ Exception -> 0x005f }
                r4 = com.umeng.message.MessageSharedPrefs.this;	 Catch:{ Exception -> 0x005f }
                r4 = r4.b;	 Catch:{ Exception -> 0x005f }
                com.umeng.message.provider.a.a(r4);	 Catch:{ Exception -> 0x005f }
                r4 = com.umeng.message.provider.a.c;	 Catch:{ Exception -> 0x005f }
                r3.delete(r4, r0, r2);	 Catch:{ Exception -> 0x005f }
                goto L_0x0035;
            L_0x005f:
                r0 = move-exception;
            L_0x0060:
                r0.printStackTrace();	 Catch:{ all -> 0x0070 }
                if (r1 == 0) goto L_0x003a;
            L_0x0065:
                r1.close();
                goto L_0x003a;
            L_0x0069:
                r0 = move-exception;
            L_0x006a:
                if (r6 == 0) goto L_0x006f;
            L_0x006c:
                r6.close();
            L_0x006f:
                throw r0;
            L_0x0070:
                r0 = move-exception;
                r6 = r1;
                goto L_0x006a;
            L_0x0073:
                r0 = move-exception;
                r1 = r6;
                goto L_0x0060;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.MessageSharedPrefs.2.run():void");
            }
        });
    }
}
