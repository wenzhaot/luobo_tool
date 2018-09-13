package com.umeng.message.tag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import com.stub.StubApp;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.MessageSharedPrefs;
import com.umeng.message.UTrack;
import com.umeng.message.common.UmengMessageDeviceConfig;
import com.umeng.message.common.d;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.common.inter.ITagManager.Result;
import com.umeng.message.util.HttpRequest;
import com.umeng.message.util.e;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class TagManager {
    private static final String a = TagManager.class.getName();
    private static final String b = "ok";
    private static final String c = "fail";
    @SuppressLint({"StaticFieldLeak"})
    private static TagManager d;
    private static ITagManager f;
    private Context e;

    public interface TCallBack {
        void onMessage(boolean z, Result result);
    }

    public interface TagListCallBack {
        void onMessage(boolean z, List<String> list);
    }

    public interface WeightedTagListCallBack {
        void onMessage(boolean z, Hashtable<String, Integer> hashtable);
    }

    private TagManager(Context context) {
        this.e = StubApp.getOrigApplicationContext(context.getApplicationContext());
    }

    public static synchronized TagManager getInstance(Context context) {
        TagManager tagManager;
        synchronized (TagManager.class) {
            if (d == null) {
                d = new TagManager(StubApp.getOrigApplicationContext(context.getApplicationContext()));
                try {
                    f = (ITagManager) Class.forName("com.umeng.message.common.impl.json.JTagManager").getConstructor(new Class[]{Context.class}).newInstance(new Object[]{context});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            tagManager = d;
        }
        return tagManager;
    }

    public void addTags(final TCallBack tCallBack, final String... strArr) {
        d.a(new Runnable() {
            public void run() {
                Result result = new Result();
                UMLog uMLog;
                if (strArr == null || strArr.length == 0) {
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(TagManager.a, 0, "No tags");
                    result.setErrors("No tags");
                    tCallBack.onMessage(false, result);
                } else if (!TagManager.this.d()) {
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(TagManager.a, 0, "No utdid or device_token");
                    result.setErrors("No utdid or device_token");
                    tCallBack.onMessage(false, result);
                } else if (TagManager.this.e()) {
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(TagManager.a, 0, "Tag API is disabled by the server");
                    result.setErrors("Tag API is disabled by the server");
                    tCallBack.onMessage(false, result);
                } else {
                    Object a = TagManager.this.a(MessageSharedPrefs.getInstance(TagManager.this.e).get_addTagsInterval(), strArr);
                    if (TextUtils.isEmpty(a)) {
                        List arrayList = new ArrayList();
                        if (strArr.length > 0) {
                            for (String str : strArr) {
                                if (!(MessageSharedPrefs.getInstance(TagManager.this.e).isTagSet(str) || arrayList.contains(str))) {
                                    byte[] bArr = null;
                                    try {
                                        bArr = str.getBytes("UTF-8");
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    if (bArr.length <= 128 && bArr.length >= 0) {
                                        arrayList.add(str);
                                    }
                                }
                            }
                        }
                        if (arrayList.size() == 0) {
                            tCallBack.onMessage(true, TagManager.this.f());
                            return;
                        }
                        try {
                            JSONObject e2 = TagManager.this.c();
                            e2.put("tags", e.a(arrayList));
                            tCallBack.onMessage(true, TagManager.f.addTags(e2, strArr));
                            return;
                        } catch (Exception e3) {
                            UMLog uMLog2 = UMConfigure.umDebugLog;
                            UMLog.mutlInfo(TagManager.a, 0, "添加tag异常");
                            return;
                        }
                    }
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(TagManager.a, 0, a);
                    result.setErrors(a);
                    tCallBack.onMessage(false, result);
                }
            }
        });
    }

    public void addWeightedTags(final TCallBack tCallBack, final Hashtable<String, Integer> hashtable) {
        d.a(new Runnable() {
            public void run() {
                Result result = new Result();
                UMLog uMLog;
                if (!TagManager.this.d()) {
                    result.setErrors("No utdid or device token");
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(TagManager.a, 0, "No utdid or device token");
                    tCallBack.onMessage(false, result);
                } else if (hashtable == null || hashtable.size() == 0) {
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(TagManager.a, 0, "No weighted tags");
                    result.setErrors("No weighted tags");
                    tCallBack.onMessage(false, result);
                } else if (hashtable.size() > 64) {
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(TagManager.a, 0, "The maximum number of adding weighted tags per request is 64");
                    result.setErrors("The maximum number of adding weighted tags per request is 64");
                    tCallBack.onMessage(false, result);
                } else {
                    Object a = TagManager.this.a(MessageSharedPrefs.getInstance(TagManager.this.e).getAddWeightedTagsInterval(), hashtable);
                    if (TextUtils.isEmpty(a)) {
                        try {
                            JSONObject jSONObject = new JSONObject();
                            for (String str : hashtable.keySet()) {
                                jSONObject.put(str, ((Integer) hashtable.get(str)).intValue());
                            }
                            JSONObject e = TagManager.this.c();
                            e.put("tags", jSONObject);
                            tCallBack.onMessage(true, TagManager.f.addWeightedTags(e, hashtable));
                            return;
                        } catch (Exception e2) {
                            if (e2 != null) {
                                e2.printStackTrace();
                            }
                            UMLog uMLog2 = UMConfigure.umDebugLog;
                            UMLog.mutlInfo(TagManager.a, 0, "添加加权标签异常");
                            return;
                        }
                    }
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(TagManager.a, 0, a);
                    result.setErrors(a);
                    tCallBack.onMessage(false, result);
                }
            }
        });
    }

    private String a(String str, String... strArr) {
        ArrayList arrayList = new ArrayList();
        if (strArr != null && strArr.length > 0) {
            for (String str2 : strArr) {
                if (!(MessageSharedPrefs.getInstance(this.e).isTagSet(str2) || arrayList.contains(str2))) {
                    byte[] bytes;
                    try {
                        bytes = str2.getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        bytes = null;
                    }
                    if (bytes.length > 128 || bytes.length < 0) {
                        return "部分Tags长度不在限制0到128个字符之间";
                    }
                    arrayList.add(str2);
                }
            }
        }
        if (str == null) {
            return null;
        }
        try {
            Result result = new Result(new JSONObject(str), false);
            long currentTimeMillis = System.currentTimeMillis();
            if (arrayList.size() <= 0) {
                return null;
            }
            if (result.remain < 0 || arrayList.size() > result.remain) {
                return "Tags数量不能超过1024";
            }
            if (result.interval == 0 || (currentTimeMillis - result.last_requestTime) / 1000 > result.interval) {
                return null;
            }
            return "interval限制";
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private String a(String str, Hashtable<String, Integer> hashtable) {
        String str2;
        for (String str22 : hashtable.keySet()) {
            try {
                byte[] bytes = str22.getBytes("UTF-8");
                if (bytes.length > 128 && bytes.length <= 0) {
                    return "部分Tags长度不在限制0到128个字符之间";
                }
                if (((Integer) hashtable.get(str22)).intValue() >= -10) {
                    if (((Integer) hashtable.get(str22)).intValue() > 10) {
                    }
                }
                return "部分Tags权值不在-10到10之间";
            } catch (UnsupportedEncodingException e) {
                UMLog uMLog = UMConfigure.umDebugLog;
                UMLog.mutlInfo(a, 0, "UnsupportedEncodingException");
                return "部分Tags长度不在限制0到128个字符之间";
            }
        }
        if (str == null) {
            return null;
        }
        try {
            Result result = new Result(new JSONObject(str), true);
            long currentTimeMillis = System.currentTimeMillis();
            if (result.interval == 0 || (currentTimeMillis - result.last_requestTime) / 1000 > result.interval) {
                str22 = null;
            } else {
                str22 = "interval限制";
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
            str22 = null;
        }
        return str22;
    }

    private String b(String str, String... strArr) {
        ArrayList arrayList = new ArrayList();
        int length = strArr.length;
        int i = 0;
        while (i < length) {
            String str2 = strArr[i];
            try {
                byte[] bytes = str2.getBytes("UTF-8");
                if (bytes.length > 128 || bytes.length < 0) {
                    return "部分Tags长度不在限制0到128个字符之间";
                }
                arrayList.add(str2);
                i++;
            } catch (UnsupportedEncodingException e) {
                UMLog uMLog = UMConfigure.umDebugLog;
                UMLog.mutlInfo(a, 0, "UnsupportedEncodingException");
                return "部分Tags长度不在限制0到128个字符之间";
            }
        }
        if (str == null) {
            return null;
        }
        try {
            Result result = new Result(new JSONObject(str), true);
            long currentTimeMillis = System.currentTimeMillis();
            if (arrayList.size() <= 0 || result.interval == 0 || (currentTimeMillis - result.last_requestTime) / 1000 > result.interval) {
                return null;
            }
            return "interval限制";
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private String a(String str, boolean z) {
        if (str == null) {
            return null;
        }
        try {
            Result result = new Result(new JSONObject(str), z);
            long currentTimeMillis = System.currentTimeMillis();
            if (result.interval == 0 || (currentTimeMillis - result.last_requestTime) / 1000 > result.interval) {
                return null;
            }
            return "interval限制";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void a(final TCallBack tCallBack, final String... strArr) {
        d.a(new Runnable() {
            public void run() {
                Result result = null;
                if (TagManager.this.e()) {
                    try {
                        throw new Exception("Tag API is disabled by the server.");
                    } catch (Exception e) {
                        e.printStackTrace();
                        tCallBack.onMessage(false, null);
                    }
                } else if (!TagManager.this.d()) {
                    try {
                        throw new Exception("No utdid or device_token");
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        tCallBack.onMessage(false, null);
                    }
                } else if (strArr == null || strArr.length == 0) {
                    try {
                        throw new Exception("No tags");
                    } catch (Exception e22) {
                        e22.printStackTrace();
                        tCallBack.onMessage(false, null);
                    }
                } else {
                    List arrayList = new ArrayList();
                    for (Object add : strArr) {
                        arrayList.add(add);
                    }
                    if (arrayList.size() == 0) {
                        tCallBack.onMessage(true, TagManager.this.f());
                        return;
                    }
                    try {
                        JSONObject e3 = TagManager.this.c();
                        e3.put("tags", e.a(arrayList));
                        result = TagManager.f.update(e3, strArr);
                        tCallBack.onMessage(true, result);
                    } catch (Exception e222) {
                        e222.printStackTrace();
                        tCallBack.onMessage(true, result);
                    }
                }
            }
        });
    }

    public void deleteTags(final TCallBack tCallBack, final String... strArr) {
        d.a(new Runnable() {
            public void run() {
                UMLog uMLog;
                Result result = null;
                Object a = TagManager.this.a(MessageSharedPrefs.getInstance(TagManager.this.e).get_deleteTagsInterval(), strArr);
                if (!TextUtils.isEmpty(a)) {
                    try {
                        throw new Exception(a);
                    } catch (Exception e) {
                        if (e != null) {
                            uMLog = UMConfigure.umDebugLog;
                            UMLog.mutlInfo(TagManager.a, 0, "exception:" + e.getMessage());
                        }
                        tCallBack.onMessage(false, null);
                    }
                } else if (TagManager.this.e()) {
                    try {
                        throw new Exception("Tag API is disabled by the server.");
                    } catch (Exception e2) {
                        if (e2 != null) {
                            uMLog = UMConfigure.umDebugLog;
                            UMLog.mutlInfo(TagManager.a, 0, "exception:" + e2.getMessage());
                        }
                        tCallBack.onMessage(false, null);
                    }
                } else if (!TagManager.this.d()) {
                    try {
                        throw new Exception("No utdid or device_token");
                    } catch (Exception e22) {
                        if (e22 != null) {
                            uMLog = UMConfigure.umDebugLog;
                            UMLog.mutlInfo(TagManager.a, 0, "exception:" + e22.getMessage());
                        }
                        tCallBack.onMessage(false, null);
                    }
                } else if (strArr == null || strArr.length == 0) {
                    try {
                        throw new Exception("No tags");
                    } catch (Exception e222) {
                        if (e222 != null) {
                            uMLog = UMConfigure.umDebugLog;
                            UMLog.mutlInfo(TagManager.a, 0, "exception:" + e222.getMessage());
                        }
                        tCallBack.onMessage(false, null);
                    }
                } else {
                    try {
                        JSONObject e3 = TagManager.this.c();
                        e3.put("tags", e.a(strArr));
                        result = TagManager.f.deleteTags(e3, strArr);
                        tCallBack.onMessage(true, result);
                    } catch (Exception e2222) {
                        if (e2222 != null) {
                            uMLog = UMConfigure.umDebugLog;
                            UMLog.mutlInfo(TagManager.a, 0, "exception:" + e2222.getMessage());
                        }
                        tCallBack.onMessage(false, result);
                    }
                }
            }
        });
    }

    public void deleteWeightedTags(final TCallBack tCallBack, final String... strArr) {
        d.a(new Runnable() {
            public void run() {
                Result result = new Result();
                UMLog uMLog;
                if (!TagManager.this.d()) {
                    result.setErrors("缺少utdid或device token");
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(TagManager.a, 0, "缺少utdid或device token");
                    tCallBack.onMessage(false, result);
                } else if (strArr == null || strArr.length == 0) {
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(TagManager.a, 0, "没有加权标签");
                    result.setErrors("没有加权标签");
                    tCallBack.onMessage(false, result);
                } else if (strArr.length > 64) {
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(TagManager.a, 0, "每次请求最多删除64个加权标签");
                    result.setErrors("每次请求最多删除64个加权标签");
                    tCallBack.onMessage(false, result);
                } else {
                    Object b = TagManager.this.b(MessageSharedPrefs.getInstance(TagManager.this.e).getDeleteWeightedTagsInterval(), strArr);
                    if (TextUtils.isEmpty(b)) {
                        try {
                            JSONObject e = TagManager.this.c();
                            e.put("tags", e.a(strArr));
                            tCallBack.onMessage(true, TagManager.f.deleteWeightedTags(e, strArr));
                            return;
                        } catch (Exception e2) {
                            UMLog uMLog2 = UMConfigure.umDebugLog;
                            UMLog.mutlInfo(TagManager.a, 0, "删除加权标签异常");
                            return;
                        }
                    }
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(TagManager.a, 0, b);
                    result.setErrors(b);
                    tCallBack.onMessage(false, result);
                }
            }
        });
    }

    private void a(final TCallBack tCallBack) {
        d.a(new Runnable() {
            public void run() {
                Result result = null;
                if (TagManager.this.e()) {
                    try {
                        throw new Exception("Tag API被服务器禁止");
                    } catch (Exception e) {
                        e.printStackTrace();
                        tCallBack.onMessage(false, null);
                    }
                } else if (TagManager.this.d()) {
                    try {
                        result = TagManager.f.reset(TagManager.this.c());
                        tCallBack.onMessage(true, result);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        tCallBack.onMessage(false, result);
                    }
                } else {
                    try {
                        throw new Exception("缺少utdid或device token");
                    } catch (Exception e22) {
                        e22.printStackTrace();
                        tCallBack.onMessage(false, null);
                    }
                }
            }
        });
    }

    public void getTags(final TagListCallBack tagListCallBack) {
        d.a(new Runnable() {
            public void run() {
                UMLog uMLog;
                List list = null;
                Object a = TagManager.this.a(MessageSharedPrefs.getInstance(TagManager.this.e).get_getTagsInterval(), false);
                if (!TextUtils.isEmpty(a)) {
                    try {
                        throw new Exception(a);
                    } catch (Exception e) {
                        if (e != null) {
                            uMLog = UMConfigure.umDebugLog;
                            UMLog.mutlInfo(TagManager.a, 0, "exception:" + e.getMessage());
                        }
                        tagListCallBack.onMessage(false, null);
                    }
                } else if (TagManager.this.e()) {
                    try {
                        throw new Exception("Tag API被服务器禁止.");
                    } catch (Exception e2) {
                        if (e2 != null) {
                            uMLog = UMConfigure.umDebugLog;
                            UMLog.mutlInfo(TagManager.a, 0, "exception:" + e2.getMessage());
                        }
                        tagListCallBack.onMessage(false, null);
                    }
                } else if (TagManager.this.d()) {
                    try {
                        list = TagManager.f.getTags(TagManager.this.c());
                        tagListCallBack.onMessage(true, list);
                    } catch (Exception e22) {
                        if (e22 != null) {
                            uMLog = UMConfigure.umDebugLog;
                            UMLog.mutlInfo(TagManager.a, 0, "exception:" + e22.getMessage());
                        }
                        tagListCallBack.onMessage(false, list);
                    }
                } else {
                    try {
                        throw new Exception("缺少utdid或device token");
                    } catch (Exception e222) {
                        if (e222 != null) {
                            uMLog = UMConfigure.umDebugLog;
                            UMLog.mutlInfo(TagManager.a, 0, "exception:" + e222.getMessage());
                        }
                        tagListCallBack.onMessage(false, null);
                    }
                }
            }
        });
    }

    public void getWeightedTags(final WeightedTagListCallBack weightedTagListCallBack) {
        d.a(new Runnable() {
            public void run() {
                Hashtable hashtable = new Hashtable();
                UMLog uMLog;
                if (TagManager.this.d()) {
                    if (TextUtils.isEmpty(TagManager.this.a(MessageSharedPrefs.getInstance(TagManager.this.e).getListWeightedTagsInterval(), true))) {
                        try {
                            hashtable = TagManager.f.getWeightedTags(TagManager.this.c());
                            weightedTagListCallBack.onMessage(true, hashtable);
                            return;
                        } catch (Exception e) {
                            UMLog uMLog2 = UMConfigure.umDebugLog;
                            UMLog.mutlInfo(TagManager.a, 0, "获取加权标签列表异常");
                            weightedTagListCallBack.onMessage(false, hashtable);
                            return;
                        }
                    }
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(TagManager.a, 0, r1);
                    weightedTagListCallBack.onMessage(false, hashtable);
                    return;
                }
                uMLog = UMConfigure.umDebugLog;
                UMLog.mutlInfo(TagManager.a, 0, "缺少utdid或device token");
                weightedTagListCallBack.onMessage(false, hashtable);
            }
        });
    }

    private JSONObject c() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("header", UTrack.getInstance(this.e).getHeader());
        jSONObject.put("utdid", UmengMessageDeviceConfig.getUtdid(this.e));
        jSONObject.put("device_token", MessageSharedPrefs.getInstance(this.e).getDeviceToken());
        jSONObject.put("ts", System.currentTimeMillis());
        return jSONObject;
    }

    private static JSONObject a(JSONObject jSONObject, String str) throws JSONException {
        String body = HttpRequest.post((CharSequence) str).acceptJson().contentType("application/json").send(jSONObject.toString()).body("UTF-8");
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 2, "postJson() url=" + str + "\n request = " + jSONObject + "\n response = " + body);
        return new JSONObject(body);
    }

    private boolean d() {
        UMLog uMLog;
        if (TextUtils.isEmpty(UmengMessageDeviceConfig.getUtdid(this.e))) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, "UTDID为空");
            return false;
        } else if (!TextUtils.isEmpty(MessageSharedPrefs.getInstance(this.e).getDeviceToken())) {
            return true;
        } else {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, "Device token为空");
            return false;
        }
    }

    private boolean e() {
        boolean z;
        if (MessageSharedPrefs.getInstance(this.e).getTagSendPolicy() == 1) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 2, "Tag API被服务器禁止");
        }
        return z;
    }

    private Result f() {
        Result result = new Result(new JSONObject(), false);
        result.remain = MessageSharedPrefs.getInstance(this.e).getTagRemain();
        result.status = "ok";
        result.jsonString = "status:" + result.status + MiPushClient.ACCEPT_TIME_SEPARATOR + " remain:" + result.remain + MiPushClient.ACCEPT_TIME_SEPARATOR + "description:" + result.status;
        return result;
    }
}
