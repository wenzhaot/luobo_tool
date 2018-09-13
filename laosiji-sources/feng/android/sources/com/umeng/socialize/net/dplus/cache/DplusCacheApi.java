package com.umeng.socialize.net.dplus.cache;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.umeng.socialize.b.b.c;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.umeng.socialize.net.dplus.db.DBConfig;
import com.umeng.socialize.net.dplus.db.DBManager;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText.CACHE;
import java.io.File;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DplusCacheApi {
    private static final String a = "DplusCacheApi";
    private HandlerThread b;
    private Handler c;
    private final int d;
    private ArrayList<Integer> e;
    private ArrayList<Integer> f;
    private ArrayList<Integer> g;
    private ArrayList<Integer> h;
    private ArrayList<Integer> i;

    private static class SingletonHolder {
        private static final DplusCacheApi a = new DplusCacheApi();

        private SingletonHolder() {
        }
    }

    /* synthetic */ DplusCacheApi(AnonymousClass1 anonymousClass1) {
        this();
    }

    public static final DplusCacheApi getInstance() {
        return SingletonHolder.a;
    }

    private DplusCacheApi() {
        this.d = c.a;
        this.e = new ArrayList();
        this.f = new ArrayList();
        this.g = new ArrayList();
        this.h = new ArrayList();
        this.i = new ArrayList();
        this.b = new HandlerThread(a, 10);
        this.b.start();
        this.c = new Handler(this.b.getLooper());
    }

    public void saveFile(Context context, JSONObject jSONObject, int i, DplusCacheListener dplusCacheListener) {
        final int i2 = i;
        final Context context2 = context;
        final JSONObject jSONObject2 = jSONObject;
        final DplusCacheListener dplusCacheListener2 = dplusCacheListener;
        this.c.post(new Runnable() {
            public void run() {
                switch (i2) {
                    case SocializeConstants.DAU_EVENT /*24577*/:
                        DBManager.get(context2).insertDau(jSONObject2);
                        break;
                    case SocializeConstants.SHARE_EVENT /*24578*/:
                        DBManager.get(context2).insertS_E(jSONObject2);
                        break;
                    case SocializeConstants.AUTH_EVENT /*24579*/:
                        DBManager.get(context2).insertAuth(jSONObject2);
                        break;
                    case SocializeConstants.GET_EVENT /*24580*/:
                        DBManager.get(context2).insertUserInfo(jSONObject2);
                        break;
                    case SocializeConstants.SAVE_STATS_EVENT /*24581*/:
                    case SocializeConstants.SEND_DAU_STATS_EVENT /*24583*/:
                        DBManager.get(context2).insertStats(jSONObject2);
                        break;
                    default:
                        DBManager.get(context2).insertStats(jSONObject2);
                        break;
                }
                dplusCacheListener2.onResult(null);
            }
        });
    }

    public JSONObject readFileAsnc(Context context, int i) {
        double checkFile = checkFile();
        boolean z = true;
        if (checkFile >= 5242880.0d) {
            DBManager.get(ContextUtil.getContext()).deleteTable("stats");
            return null;
        }
        if (1048576.0d > checkFile + 24576.0d) {
            z = false;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObject3 = new JSONObject();
            JSONArray select = DBManager.get(context).select("s_e", this.e, 1048576.0d - 1024.0d, z);
            double length = 1024.0d + ((double) select.toString().getBytes().length);
            JSONArray select2 = DBManager.get(context).select("auth", this.f, 1048576.0d - length, z);
            length += (double) select2.toString().getBytes().length;
            JSONArray select3 = DBManager.get(context).select("userinfo", this.g, 1048576.0d - length, z);
            length += (double) select3.toString().getBytes().length;
            JSONArray select4 = DBManager.get(context).select("dau", this.h, 1048576.0d - length, z);
            double length2 = length + ((double) select4.toString().getBytes().length);
            double checkFile2 = checkFile();
            if (select4.length() != 0) {
                jSONObject3.put("dau", select4);
            }
            if (select.length() != 0) {
                jSONObject3.put("s_e", select);
            }
            if (select2.length() != 0) {
                jSONObject3.put("auth", select2);
            }
            if (select3.length() != 0) {
                jSONObject3.put("userinfo", select3);
            }
            JSONArray jSONArray = new JSONArray();
            if (checkFile2 >= 524288.0d || i == 24583) {
                jSONArray = DBManager.get(context).select("stats", this.i, 1048576.0d - length2, z);
                if (jSONArray.length() != 0) {
                    jSONObject3.put("stats", jSONArray);
                }
            }
            jSONObject.put("share", jSONObject3);
            if (select.length() == 0 && select2.length() == 0 && select3.length() == 0 && select4.length() == 0 && jSONArray.length() == 0) {
                jSONObject = null;
            }
        } catch (Throwable e) {
            SLog.error(CACHE.CACHEFILE, e);
        }
        if (jSONObject == null || ((double) jSONObject.toString().getBytes().length) <= 1048576.0d) {
            return jSONObject;
        }
        return null;
    }

    public void readFile(final Context context, final int i, final DplusCacheListener dplusCacheListener) {
        this.c.post(new Runnable() {
            public void run() {
                double checkFile = DplusCacheApi.checkFile();
                boolean z = true;
                if (checkFile >= 5242880.0d) {
                    DBManager.get(ContextUtil.getContext()).deleteTable("stats");
                    return;
                }
                if (1048576.0d > checkFile + 24576.0d) {
                    z = false;
                }
                JSONObject jSONObject = new JSONObject();
                try {
                    JSONObject jSONObject2 = new JSONObject();
                    JSONObject jSONObject3 = new JSONObject();
                    JSONArray select = DBManager.get(context).select("s_e", DplusCacheApi.this.e, 1048576.0d - 1024.0d, z);
                    double length = 1024.0d + ((double) select.toString().getBytes().length);
                    JSONArray select2 = DBManager.get(context).select("auth", DplusCacheApi.this.f, 1048576.0d - length, z);
                    length += (double) select2.toString().getBytes().length;
                    JSONArray select3 = DBManager.get(context).select("userinfo", DplusCacheApi.this.g, 1048576.0d - length, z);
                    length += (double) select3.toString().getBytes().length;
                    JSONArray select4 = DBManager.get(context).select("dau", DplusCacheApi.this.h, 1048576.0d - length, z);
                    double length2 = length + ((double) select4.toString().getBytes().length);
                    double checkFile2 = DplusCacheApi.checkFile();
                    if (select4.length() != 0) {
                        jSONObject3.put("dau", select4);
                    }
                    if (select.length() != 0) {
                        jSONObject3.put("s_e", select);
                    }
                    if (select2.length() != 0) {
                        jSONObject3.put("auth", select2);
                    }
                    if (select3.length() != 0) {
                        jSONObject3.put("userinfo", select3);
                    }
                    JSONArray jSONArray = new JSONArray();
                    if (checkFile2 >= 524288.0d || i == SocializeConstants.SEND_DAU_STATS_EVENT) {
                        jSONArray = DBManager.get(context).select("stats", DplusCacheApi.this.i, 1048576.0d - length2, z);
                        if (jSONArray.length() != 0) {
                            jSONObject3.put("stats", jSONArray);
                        }
                    }
                    jSONObject.put("share", jSONObject3);
                    if (select.length() == 0 && select2.length() == 0 && select3.length() == 0 && select4.length() == 0 && jSONArray.length() == 0) {
                        jSONObject = null;
                    }
                } catch (Throwable e) {
                    SLog.error(CACHE.CACHEFILE, e);
                }
                if (jSONObject != null && ((double) jSONObject.toString().getBytes().length) > 1048576.0d) {
                    dplusCacheListener.onResult(null);
                }
                dplusCacheListener.onResult(jSONObject);
            }
        });
    }

    private static JSONObject a() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("s_sdk_v", "6.9.2");
        jSONObject.put(CommonNetImpl.PCV, SocializeConstants.PROTOCOL_VERSON);
        return jSONObject;
    }

    public void deleteFile(final Context context) {
        this.c.post(new Runnable() {
            public void run() {
                if (DplusCacheApi.this.e.size() > 0) {
                    DBManager.get(context).delete(DplusCacheApi.this.e, "s_e");
                    DplusCacheApi.this.e.clear();
                }
                if (DplusCacheApi.this.f.size() > 0) {
                    DBManager.get(context).delete(DplusCacheApi.this.f, "auth");
                    DplusCacheApi.this.f.clear();
                }
                if (DplusCacheApi.this.h.size() > 0) {
                    DBManager.get(context).delete(DplusCacheApi.this.h, "dau");
                    DplusCacheApi.this.h.clear();
                }
                if (DplusCacheApi.this.g.size() > 0) {
                    DBManager.get(context).delete(DplusCacheApi.this.g, "userinfo");
                    DplusCacheApi.this.g.clear();
                }
                if (DplusCacheApi.this.i.size() > 0) {
                    DBManager.get(context).delete(DplusCacheApi.this.i, "stats");
                    DplusCacheApi.this.i.clear();
                }
            }
        });
    }

    public void deleteFileAsnc(Context context) {
        if (this.e.size() > 0) {
            DBManager.get(context).delete(this.e, "s_e");
            this.e.clear();
        }
        if (this.f.size() > 0) {
            DBManager.get(context).delete(this.f, "auth");
            this.f.clear();
        }
        if (this.h.size() > 0) {
            DBManager.get(context).delete(this.h, "dau");
            this.h.clear();
        }
        if (this.g.size() > 0) {
            DBManager.get(context).delete(this.g, "userinfo");
            this.g.clear();
        }
        if (this.i.size() > 0) {
            DBManager.get(context).delete(this.i, "stats");
            this.i.clear();
        }
    }

    public void deleteAllAsnc(Context context) {
        DBManager.get(ContextUtil.getContext()).deleteTable("stats");
    }

    public void deleteAll(Context context) {
        this.c.post(new Runnable() {
            public void run() {
                DBManager.get(ContextUtil.getContext()).deleteTable("stats");
            }
        });
    }

    public static double checkFile() {
        File dataFile = ContextUtil.getDataFile(DBConfig.DB_NAME);
        if (dataFile == null || !dataFile.exists()) {
            return 0.0d;
        }
        return (double) dataFile.length();
    }
}
