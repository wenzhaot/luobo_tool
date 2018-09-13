package com.feng.car.listener;

import android.app.ProgressDialog;
import android.content.Context;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.utils.HttpConstant;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;
import java.util.HashMap;
import java.util.Map;

public class FengUMShareListener implements UMShareListener {
    protected ProgressDialog dialog;
    private Context mContext;
    private SnsInfo mSnsInfo;

    public FengUMShareListener(Context context, SnsInfo snsInfo) {
        this.mContext = context;
        this.mSnsInfo = snsInfo;
        this.dialog = new ProgressDialog(context, 3);
        this.dialog.setMessage("请稍候...");
    }

    public void onStart(SHARE_MEDIA share_media) {
        SocializeUtils.safeShowDialog(this.dialog);
    }

    public void onResult(SHARE_MEDIA platform) {
        SocializeUtils.safeCloseDialog(this.dialog);
        shareOnSuccess();
        ((BaseActivity) this.mContext).showFirstTypeToast(2131231555);
    }

    public void onError(SHARE_MEDIA platform, Throwable t) {
        SocializeUtils.safeCloseDialog(this.dialog);
        ((BaseActivity) this.mContext).showSecondTypeToast(2131231552);
    }

    public void onCancel(SHARE_MEDIA platform) {
        SocializeUtils.safeCloseDialog(this.dialog);
        ((BaseActivity) this.mContext).showSecondTypeToast(2131231550);
    }

    private void shareOnSuccess() {
        Map<String, Object> map = new HashMap();
        map.put("resourceid", String.valueOf(this.mSnsInfo.resourceid));
        map.put(HttpConstant.RESOURCETYPE, String.valueOf(this.mSnsInfo.snstype));
        FengApplication.getInstance().httpRequest(HttpConstant.SHARE_THREAD, map, new OkHttpResponseCallback() {
            public void onSuccess(int statusCode, String content) {
            }

            public void onNetworkError() {
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
            }
        });
    }
}
