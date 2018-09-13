package com.feng.car.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.databinding.UploadProgressLayoutBinding;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.taobao.accs.AccsClientConfig;
import com.tencent.ijk.media.player.misc.IMediaFormat;
import java.text.DecimalFormat;
import java.util.HashMap;
import org.json.JSONObject;

public class UploadSingleImage {
    private String mBaseUrl = "";
    private Context mContext;
    private HashMap<String, String> mImageInfoMap = new HashMap();
    private String mImagePath = "";
    private boolean mIsBreakUpload;
    private Dialog mProgressDialog;
    private UploadManager mUploadManager;
    private UploadProgressLayoutBinding mUploadProgressLayoutBinding;
    private OnUploadResult mUploadResult;

    public UploadSingleImage(Context context, OnUploadResult onUploadResult) {
        this.mContext = context;
        this.mUploadResult = onUploadResult;
    }

    public void startUpload(String path) {
        this.mUploadResult.onStart();
        this.mImagePath = path;
        this.mIsBreakUpload = false;
        showProgressDialog();
        initQiNiuPath();
    }

    private void initQiNiuPath() {
        if (TextUtils.isEmpty(this.mBaseUrl)) {
            FengApplication.getInstance().httpRequest("home/state/", new HashMap(), new OkHttpResponseCallback() {
                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject jsonObject = new JSONObject(content);
                        int code = jsonObject.getInt("code");
                        if (code == 1) {
                            String baseUrl = jsonObject.getJSONObject("body").getJSONObject(AccsClientConfig.DEFAULT_CONFIGTAG).getString("imageurl");
                            if (TextUtils.isEmpty(baseUrl)) {
                                UploadSingleImage.this.mBaseUrl = HttpConstant.QINIUIMAGEBASEPATH;
                                return;
                            } else {
                                UploadSingleImage.this.mBaseUrl = baseUrl;
                                return;
                            }
                        }
                        FengApplication.getInstance().upLoadLog(true, "home/state/  " + code);
                    } catch (Exception e) {
                        e.printStackTrace();
                        FengApplication.getInstance().upLoadTryCatchLog("home/state/", content, e);
                    }
                }

                public void onNetworkError() {
                    UploadSingleImage.this.initQiNiuToken();
                }

                public void onStart() {
                }

                public void onFinish() {
                    UploadSingleImage.this.initQiNiuToken();
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                }
            });
            return;
        }
        initQiNiuToken();
    }

    private void initQiNiuToken() {
        FengApplication.getInstance().httpRequest(HttpConstant.QINIUTOKEN, new HashMap(), new OkHttpResponseCallback() {
            public void onNetworkError() {
                ((BaseActivity) UploadSingleImage.this.mContext).showSecondTypeToast(2131231273);
                UploadSingleImage.this.mUploadResult.onFailure();
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ((BaseActivity) UploadSingleImage.this.mContext).showSecondTypeToast(2131231273);
                UploadSingleImage.this.mUploadResult.onFailure();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    int code = jsonObject.getInt("code");
                    if (code == 1) {
                        UploadSingleImage.this.upLoadQiNiu(jsonObject.getJSONObject("body").getJSONObject(HttpConstant.TOKEN).getString("image"));
                    } else if (code == -5) {
                        ((BaseActivity) UploadSingleImage.this.mContext).showSecondTypeToast(2131230795);
                        UploadSingleImage.this.mUploadResult.onFailure();
                    } else {
                        ((BaseActivity) UploadSingleImage.this.mContext).showSecondTypeToast(2131231273);
                        FengApplication.getInstance().upLoadLog(true, "home/qiniutoken/    " + code);
                        UploadSingleImage.this.mUploadResult.onFailure();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ((BaseActivity) UploadSingleImage.this.mContext).showSecondTypeToast(2131231273);
                    UploadSingleImage.this.mUploadResult.onFailure();
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.QINIUTOKEN, content, e);
                }
            }
        });
    }

    private void upLoadQiNiu(String strToken) {
        if (this.mUploadManager == null) {
            this.mUploadManager = new UploadManager();
        }
        this.mUploadManager.put(this.mImagePath, null, strToken, new UpCompletionHandler() {
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if (info.statusCode == 200) {
                    try {
                        UploadSingleImage.this.mImageInfoMap.clear();
                        UploadSingleImage.this.mImageInfoMap.put("hash", response.getString("hash"));
                        UploadSingleImage.this.mImageInfoMap.put("url", response.getString("key"));
                        UploadSingleImage.this.mImageInfoMap.put(IMediaFormat.KEY_MIME, response.getString("mimeType"));
                        UploadSingleImage.this.mImageInfoMap.put(FengConstant.SIZE, response.getString("fsize"));
                        String orient = response.getString("orient");
                        if (orient == null || !(orient.equalsIgnoreCase("left-bottom") || orient.equalsIgnoreCase("right-top"))) {
                            UploadSingleImage.this.mImageInfoMap.put("width", response.getString("w"));
                            UploadSingleImage.this.mImageInfoMap.put("height", response.getString("h"));
                        } else {
                            UploadSingleImage.this.mImageInfoMap.put("width", response.getString("h"));
                            UploadSingleImage.this.mImageInfoMap.put("height", response.getString("w"));
                        }
                        UploadSingleImage.this.mUploadResult.onResult(UploadSingleImage.this.mImageInfoMap);
                        if (UploadSingleImage.this.mProgressDialog != null) {
                            UploadSingleImage.this.mProgressDialog.dismiss();
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        ((BaseActivity) UploadSingleImage.this.mContext).showSecondTypeToast(2131231273);
                        if (UploadSingleImage.this.mProgressDialog != null) {
                            UploadSingleImage.this.mProgressDialog.dismiss();
                            return;
                        }
                        return;
                    }
                }
                ((BaseActivity) UploadSingleImage.this.mContext).showSecondTypeToast(2131231273);
                if (UploadSingleImage.this.mProgressDialog != null) {
                    UploadSingleImage.this.mProgressDialog.dismiss();
                }
            }
        }, new UploadOptions(null, null, false, new UpProgressHandler() {
            public void progress(String arg0, double arg1) {
                if (UploadSingleImage.this.mUploadProgressLayoutBinding.progressbar != null) {
                    UploadSingleImage.this.mUploadProgressLayoutBinding.progressbar.setProgress((int) (arg1 * 100.0d));
                    UploadSingleImage.this.mUploadProgressLayoutBinding.tvNum.setText(new DecimalFormat("0.0").format(100.0d * arg1) + "%");
                }
            }
        }, new UpCancellationSignal() {
            public boolean isCancelled() {
                if (UploadSingleImage.this.mIsBreakUpload) {
                    return true;
                }
                return false;
            }
        }));
    }

    private void showProgressDialog() {
        if (this.mUploadProgressLayoutBinding == null) {
            this.mUploadProgressLayoutBinding = UploadProgressLayoutBinding.inflate(LayoutInflater.from(this.mContext));
        }
        if (this.mProgressDialog == null) {
            this.mProgressDialog = new Dialog(this.mContext, 2131361986);
            this.mProgressDialog.setCanceledOnTouchOutside(true);
            this.mProgressDialog.setCancelable(false);
            Window window = this.mProgressDialog.getWindow();
            window.setGravity(17);
            window.setWindowAnimations(2131362223);
            window.setContentView(this.mUploadProgressLayoutBinding.getRoot());
            window.setLayout(-1, -1);
            this.mUploadProgressLayoutBinding.ivCancel.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    UploadSingleImage.this.mIsBreakUpload = true;
                }
            });
        }
        this.mUploadProgressLayoutBinding.progressbar.setProgress(0);
        this.mUploadProgressLayoutBinding.tvProgDescribe.setText("正在拼命上传中，请勿退出。");
        this.mUploadProgressLayoutBinding.tvNum.setText("0.0%");
        this.mProgressDialog.show();
    }
}
