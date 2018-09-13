package com.tencent.liteav.h;

import com.feng.car.utils.FengConstant;
import com.feng.car.utils.HttpConstant;
import com.tencent.ijk.media.player.IjkMediaMeta;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: TXPlayInfoResponse */
public class c {
    protected JSONObject a;

    /* compiled from: TXPlayInfoResponse */
    public static class a {
        public String a;
        public String b;
        public List<Integer> c;
    }

    public c(JSONObject jSONObject) {
        this.a = jSONObject;
    }

    public String a() {
        if (e() != null) {
            return e().a;
        }
        if (c().size() != 0) {
            List j = j();
            if (j != null) {
                for (d dVar : c()) {
                    if (j.contains(Integer.valueOf(dVar.a()))) {
                        return dVar.a;
                    }
                }
            }
            return ((d) c().get(0)).a;
        } else if (d() != null) {
            return d().a;
        } else {
            return null;
        }
    }

    public String b() {
        try {
            JSONObject jSONObject = this.a.getJSONObject("coverInfo");
            if (jSONObject != null) {
                return jSONObject.getString("coverUrl");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<d> c() {
        List arrayList = new ArrayList();
        try {
            JSONArray jSONArray = this.a.getJSONObject("videoInfo").getJSONArray("transcodeList");
            if (jSONArray != null) {
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    d dVar = new d();
                    dVar.a = jSONObject.getString("url");
                    dVar.e = jSONObject.getInt("duration");
                    dVar.c = jSONObject.getInt("width");
                    dVar.b = jSONObject.getInt("height");
                    dVar.d = jSONObject.getInt(FengConstant.SIZE);
                    dVar.f = jSONObject.getInt(IjkMediaMeta.IJKM_KEY_BITRATE);
                    dVar.g = jSONObject.getInt("definition");
                    arrayList.add(dVar);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public d d() {
        try {
            JSONObject jSONObject = this.a.getJSONObject("videoInfo").getJSONObject("sourceVideo");
            d dVar = new d();
            dVar.a = jSONObject.getString("url");
            dVar.e = jSONObject.getInt("duration");
            dVar.c = jSONObject.getInt("width");
            dVar.b = jSONObject.getInt("height");
            dVar.d = jSONObject.getInt(FengConstant.SIZE);
            dVar.f = jSONObject.getInt(IjkMediaMeta.IJKM_KEY_BITRATE);
            return dVar;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public d e() {
        try {
            JSONObject jSONObject = this.a.getJSONObject("videoInfo").getJSONObject("masterPlayList");
            d dVar = new d();
            dVar.a = jSONObject.getString("url");
            return dVar;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String f() {
        try {
            JSONObject jSONObject = this.a.getJSONObject("videoInfo").getJSONObject("basicInfo");
            if (jSONObject != null) {
                return jSONObject.getString("name");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String g() {
        try {
            JSONObject jSONObject = this.a.getJSONObject("videoInfo").getJSONObject("basicInfo");
            if (jSONObject != null) {
                return jSONObject.getString(HttpConstant.DESCRIPTION);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String h() {
        try {
            return this.a.getJSONObject("playerInfo").getString("defaultVideoClassification");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<a> i() {
        try {
            List<a> arrayList = new ArrayList();
            JSONArray jSONArray = this.a.getJSONObject("playerInfo").getJSONArray("videoClassification");
            for (int i = 0; i < jSONArray.length(); i++) {
                a aVar = new a();
                aVar.a = jSONArray.getJSONObject(i).getString("id");
                aVar.b = jSONArray.getJSONObject(i).getString("name");
                aVar.c = new ArrayList();
                JSONArray jSONArray2 = jSONArray.getJSONObject(i).getJSONArray("definitionList");
                for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                    aVar.c.add(Integer.valueOf(jSONArray2.getInt(i2)));
                }
                arrayList.add(aVar);
            }
            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Integer> j() {
        List<a> i = i();
        String h = h();
        if (!(h == null || i == null)) {
            for (a aVar : i) {
                if (aVar.a.equals(h)) {
                    return aVar.c;
                }
            }
        }
        return null;
    }
}
