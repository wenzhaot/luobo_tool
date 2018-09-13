package com.umeng.socialize.net.dplus;

import android.content.Context;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.framework.UMLogDataProtocol;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.dplus.cache.DplusCacheApi;
import com.umeng.socialize.net.dplus.db.DBManager;
import com.umeng.socialize.utils.SLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CommonNetImpl implements UMLogDataProtocol {
    public static final String AID = "aid";
    public static final String AM = "am";
    public static final String AS = "as";
    public static final String AT = "at";
    public static final String AUTH = "auth";
    public static final String A_B = "a_b";
    public static final String CANCEL = "cancel";
    public static final String CONTENT = "content";
    public static final String CT = "ct";
    public static final String DAU = "dau";
    public static final String DURL = "durl";
    public static final String E_M = "e_m";
    public static final String FAIL = "fail";
    public static final int FLAG_AUTH = 268435456;
    public static final int FLAG_SHARE = 536870912;
    public static final int FLAG_SHARE_EDIT = 16777216;
    public static final int FLAG_SHARE_JUMP = 33554432;
    public static final String HEADER = "header";
    public static final String IMEI = "imei";
    public static final int MAX_FILE_SIZE_IN_KB = 65536;
    public static final int MAX_SEND_SIZE_IN_KB = 524288;
    public static final int MAX_SIZE_IN_KB = 5242880;
    public static final String MENUBG = "menubg";
    public static final String M_P = "m_p";
    public static final String M_U = "m_u";
    public static final String NAME = "name";
    public static final String PCV = "s_pcv";
    public static final String PF = "pf";
    public static final String PIC = "pic";
    public static final String PICURL = "picurl";
    public static final String POSITION = "position";
    public static final String REGION = "regn";
    public static final String RESULT = "result";
    public static final String SDKT = "sdkt";
    public static final String SDKVERSON = "s_sdk_v";
    public static final String SEX = "sex";
    public static final String SHARE = "share";
    public static final String SHARETYPE = "s_t";
    public static final String SM = "sm";
    public static final String STATS = "stats";
    public static final String STATS_TAG = "stats";
    public static final String STYPE = "stype";
    public static final String SUCCESS = "success";
    public static final String S_A_E = "s_a_e";
    public static final String S_A_S = "s_a_s";
    public static final String S_DAU = "s_dau";
    public static final String S_E = "s_e";
    public static final String S_I = "s_i";
    public static final String S_I_E = "s_i_e";
    public static final String S_I_S = "s_i_s";
    public static final String S_S_E = "s_s_e";
    public static final String S_S_S = "s_s_s";
    public static final String TAG = "tag";
    public static final String TITLE = "title";
    public static final String TS = "ts";
    public static final String UID = "uid";
    public static final String UMID = "umid";
    public static final String UN = "un";
    public static final String UNIONID = "unionid";
    public static final String UP = "up";
    public static final String URL = "url";
    public static final String USERINFO = "userinfo";
    public static final String U_C = "u_c";
    private static boolean isSendStats = false;
    private static CommonNetImpl singleton = null;
    private ArrayList<Integer> authList = new ArrayList();
    private ArrayList<Integer> dauList = new ArrayList();
    private ArrayList<Integer> infoList = new ArrayList();
    private Context mConetxt;
    private ArrayList<Integer> shareList = new ArrayList();
    private ArrayList<Integer> statsList = new ArrayList();

    private CommonNetImpl(Context context) {
        this.mConetxt = context;
    }

    public static CommonNetImpl get(Context context) {
        if (singleton == null) {
            singleton = new CommonNetImpl(context);
        }
        return singleton;
    }

    public void workEvent(Object obj, int i) {
        if (i == SocializeConstants.SEND_EMPTY) {
            JSONObject object = getObject();
            if (object != null) {
                JSONObject optJSONObject = object.optJSONObject("header");
                object = object.optJSONObject("content");
                if (optJSONObject != null && object != null) {
                    UMEnvelopeBuild.buildEnvelopeWithExtHeader(this.mConetxt, optJSONObject, object);
                }
            }
        }
    }

    public void removeCacheData(Object obj) {
        if (this.shareList.size() > 0) {
            DBManager.get(this.mConetxt).delete(this.shareList, "s_e");
            this.shareList.clear();
        }
        if (this.authList.size() > 0) {
            DBManager.get(this.mConetxt).delete(this.authList, "auth");
            this.authList.clear();
        }
        if (this.dauList.size() > 0) {
            DBManager.get(this.mConetxt).delete(this.dauList, "dau");
            this.dauList.clear();
        }
        if (this.infoList.size() > 0) {
            DBManager.get(this.mConetxt).delete(this.infoList, "userinfo");
            this.infoList.clear();
        }
        if (this.statsList.size() > 0) {
            isSendStats = false;
            DBManager.get(this.mConetxt).delete(this.statsList, "stats");
            this.statsList.clear();
        }
    }

    private JSONObject getObject() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("header", constructHeader());
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("share", new JSONObject());
            jSONObject.put("content", jSONObject2);
        } catch (Throwable e) {
            SLog.error(e);
        }
        return jSONObject;
    }

    public JSONObject setupReportData(long j) {
        double d = (double) j;
        boolean z = true;
        if (((double) j) > DplusCacheApi.checkFile() + 24576.0d) {
            z = false;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("header", constructHeader());
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObject3 = new JSONObject();
            JSONArray select = DBManager.get(this.mConetxt).select("s_e", this.shareList, d - 1024.0d, z);
            double length = 1024.0d + ((double) select.toString().getBytes().length);
            JSONArray select2 = DBManager.get(this.mConetxt).select("auth", this.authList, d - length, z);
            length += (double) select2.toString().getBytes().length;
            JSONArray select3 = DBManager.get(this.mConetxt).select("userinfo", this.infoList, d - length, z);
            length += (double) select3.toString().getBytes().length;
            JSONArray select4 = DBManager.get(this.mConetxt).select("dau", this.dauList, d - length, z);
            double length2 = length + ((double) select4.toString().getBytes().length);
            double checkFile = DplusCacheApi.checkFile();
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
            if (checkFile >= 524288.0d || isSendStats) {
                jSONArray = DBManager.get(this.mConetxt).select("stats", this.statsList, d - length2, z);
                if (jSONArray.length() != 0) {
                    jSONObject3.put("stats", jSONArray);
                }
            }
            jSONObject2.put("share", jSONObject3);
            jSONObject.put("content", jSONObject2);
            if (select.length() == 0 && select2.length() == 0 && select3.length() == 0 && select4.length() == 0 && jSONArray.length() == 0) {
                jSONObject = null;
            }
        } catch (Throwable e) {
            SLog.error(e);
        }
        if (jSONObject == null || ((long) jSONObject.toString().getBytes().length) <= j) {
            return null;
        }
        return null;
    }

    private static JSONObject constructHeader() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("s_sdk_v", "6.9.2");
        jSONObject.put(PCV, SocializeConstants.PROTOCOL_VERSON);
        return jSONObject;
    }

    private void saveFile(JSONObject jSONObject, int i) {
        if (jSONObject != null) {
            switch (i) {
                case SocializeConstants.DAU_EVENT /*24577*/:
                    DBManager.get(this.mConetxt).insertDau(jSONObject);
                    return;
                case SocializeConstants.SHARE_EVENT /*24578*/:
                    DBManager.get(this.mConetxt).insertS_E(jSONObject);
                    return;
                case SocializeConstants.AUTH_EVENT /*24579*/:
                    DBManager.get(this.mConetxt).insertAuth(jSONObject);
                    return;
                case SocializeConstants.GET_EVENT /*24580*/:
                    DBManager.get(this.mConetxt).insertUserInfo(jSONObject);
                    return;
                case SocializeConstants.SAVE_STATS_EVENT /*24581*/:
                case SocializeConstants.SEND_DAU_STATS_EVENT /*24583*/:
                    DBManager.get(this.mConetxt).insertStats(jSONObject);
                    return;
                default:
                    DBManager.get(this.mConetxt).insertStats(jSONObject);
                    return;
            }
        }
    }
}
