package com.baidu.platform.base;

import android.support.v4.app.NotificationCompat;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class d {
    protected SearchType a;

    public abstract SearchResult a(String str);

    public SearchType a() {
        return this.a;
    }

    public abstract void a(SearchResult searchResult, Object obj);

    public void a(SearchType searchType) {
        this.a = searchType;
    }

    protected boolean a(String str, SearchResult searchResult, boolean z) {
        if (str != null) {
            try {
                if (str.length() > 0) {
                    JSONObject jSONObject = new JSONObject(str);
                    if (jSONObject == null) {
                        searchResult.error = ERRORNO.RESULT_NOT_FOUND;
                        return true;
                    }
                    int optInt = z ? jSONObject.optInt(NotificationCompat.CATEGORY_STATUS) : jSONObject.optInt("status_sp");
                    if (optInt == 0) {
                        return false;
                    }
                    switch (optInt) {
                        case 104:
                        case 105:
                        case 106:
                        case 107:
                        case 108:
                            searchResult.error = ERRORNO.PERMISSION_UNFINISHED;
                            return true;
                        case 200:
                        case 230:
                            searchResult.error = ERRORNO.KEY_ERROR;
                            return true;
                        default:
                            searchResult.error = ERRORNO.RESULT_NOT_FOUND;
                            return true;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                searchResult.error = ERRORNO.RESULT_NOT_FOUND;
                return true;
            }
        }
        searchResult.error = ERRORNO.SEARCH_SERVER_INTERNAL_ERROR;
        return true;
    }
}
