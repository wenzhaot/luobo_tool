package com.umeng.socialize.sina.params;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.stub.StubApp;

public abstract class BrowserRequestParamBase {
    public static final int EXEC_REQUEST_ACTION_CANCEL = 3;
    public static final int EXEC_REQUEST_ACTION_ERROR = 2;
    public static final int EXEC_REQUEST_ACTION_OK = 1;
    public static final String EXTRA_KEY_LAUNCHER = "key_launcher";
    protected static final String EXTRA_KEY_SPECIFY_TITLE = "key_specify_title";
    protected static final String EXTRA_KEY_URL = "key_url";
    protected Context mContext;
    protected String mSpecifyTitle;
    protected String mUrl;

    public BrowserRequestParamBase(Context context) {
        this.mContext = StubApp.getOrigApplicationContext(context.getApplicationContext());
    }

    public Bundle createRequestParamBundle() {
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(this.mUrl)) {
            bundle.putString(EXTRA_KEY_URL, this.mUrl);
        }
        if (!TextUtils.isEmpty(this.mSpecifyTitle)) {
            bundle.putString(EXTRA_KEY_SPECIFY_TITLE, this.mSpecifyTitle);
        }
        onCreateRequestParamBundle(bundle);
        return bundle;
    }

    public abstract void execRequest(Activity activity, int i);

    public String getSpecifyTitle() {
        return this.mSpecifyTitle;
    }

    public String getUrl() {
        return this.mUrl;
    }

    protected abstract void onCreateRequestParamBundle(Bundle bundle);

    protected abstract void onSetupRequestParam(Bundle bundle);

    public void setSpecifyTitle(String str) {
        this.mSpecifyTitle = str;
    }

    public void setUrl(String str) {
        this.mUrl = str;
    }

    public void setupRequestParam(Bundle bundle) {
        this.mUrl = bundle.getString(EXTRA_KEY_URL);
        this.mSpecifyTitle = bundle.getString(EXTRA_KEY_SPECIFY_TITLE);
        onSetupRequestParam(bundle);
    }
}
