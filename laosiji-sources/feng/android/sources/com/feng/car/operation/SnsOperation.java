package com.feng.car.operation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.feng.car.FengApplication;
import com.feng.car.activity.ArticleDetailActivity;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.LoginActivity;
import com.feng.car.activity.PostInitActivity;
import com.feng.car.activity.SendCommentActivity;
import com.feng.car.activity.VideoBaseActivity;
import com.feng.car.activity.ViewPointActivity;
import com.feng.car.activity.WebActivity;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.EditarticleApplyDialogBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.thread.GratuityRecordInfo;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.event.UserDisLikeEvent;
import com.feng.car.event.UserPointRefreshEvent;
import com.feng.car.listener.OnDownloadImageListener;
import com.feng.car.utils.EditConstant;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.ImageUtil;
import com.feng.car.utils.ShareUtils;
import com.feng.car.view.CommonDialog;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class SnsOperation {
    private final int COPY_ITEM_TAG = 60004;
    private final int DELETE_ITEM_TAG = 60006;
    private final int REPORT_ITEM_TAG = 60005;
    private final int SEE_ORIGINAL_TAG = 60001;
    private final int SEND_COMMENT_TAG = 60003;
    private Dialog mEditApplyDialog;
    private EditarticleApplyDialogBinding mEditApplyDialogBinding;
    private SnsInfo mSnsInfo;

    public SnsOperation(SnsInfo info) {
        this.mSnsInfo = info;
    }

    public void intentToArticle(Context context, int commentID, int type) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(HttpConstant.SNS_ID, this.mSnsInfo.id);
        intent.putExtra("resourceid", this.mSnsInfo.resourceid);
        intent.putExtra(HttpConstant.RESOURCETYPE, this.mSnsInfo.snstype);
        intent.putExtra(HttpConstant.COMMENT_ID, commentID);
        if (commentID != -1) {
            intent.putExtra(HttpConstant.LOCATION_TYPE, type);
        }
        context.startActivity(intent);
    }

    public void intentToSendComment(final Context context, final int viewDy, final String key) {
        if (!FengApplication.getInstance().getSeverceState()) {
            ((BaseActivity) context).showSecondTypeToast(2131231540);
        } else if (FengApplication.getInstance().isLoginUser()) {
            checkSnsState(context, false, "评论", new SuccessFailCallback() {
                public void onSuccess() {
                    Intent intent = new Intent(context, SendCommentActivity.class);
                    intent.putExtra("userid", SnsOperation.this.mSnsInfo.user.id);
                    intent.putExtra(HttpConstant.SNS_ID, SnsOperation.this.mSnsInfo.id);
                    intent.putExtra("comment_count", SnsOperation.this.mSnsInfo.commentcount.get());
                    intent.putExtra("resourceid", SnsOperation.this.mSnsInfo.resourceid);
                    intent.putExtra(HttpConstant.RESOURCETYPE, SnsOperation.this.mSnsInfo.snstype);
                    intent.putExtra("view_local_slide_dy", viewDy);
                    intent.putExtra("event_key", key);
                    intent.putExtra(FengConstant.FENGTYPE, 1);
                    context.startActivity(intent);
                    ((BaseActivity) context).overridePendingTransition(2130968593, 0);
                }
            });
        } else {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    private void checkSnsState(Context context, boolean isSendTranspond, boolean isOldDriverRecommend, String msg, SuccessFailCallback callback) {
        if (callback != null) {
            callback.onSuccess();
        }
    }

    private void checkSnsState(Context context, boolean isSendTranspond, String msg, SuccessFailCallback callback) {
        checkSnsState(context, isSendTranspond, false, msg, callback);
    }

    public void collectOperation(final Context context, final OperationCallback callback) {
        checkSnsState(context, false, "收藏", new SuccessFailCallback() {
            public void onSuccess() {
                if (SnsOperation.this.mSnsInfo.isfavorite.get() == 1) {
                    List<DialogItemEntity> list = new ArrayList();
                    list.add(new DialogItemEntity(context.getString(2131230969), false));
                    CommonDialog.showCommonDialog(context, context.getString(2131231679), list, new OnDialogItemClickListener() {
                        public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                            SnsOperation.this.doCollect(context, callback);
                        }
                    });
                    return;
                }
                SnsOperation.this.doCollect(context, callback);
            }
        });
    }

    private void doCollect(final Context context, final OperationCallback callback) {
        Map<String, Object> map = new HashMap();
        map.put("resourceid", String.valueOf(this.mSnsInfo.resourceid));
        map.put(HttpConstant.RESOURCETYPE, String.valueOf(this.mSnsInfo.snstype));
        if (this.mSnsInfo.isfavorite.get() == 0) {
            map.put("type", String.valueOf(1));
        } else {
            map.put("type", String.valueOf(0));
        }
        map.put(HttpConstant.SNS_ID, String.valueOf(this.mSnsInfo.id));
        FengApplication.getInstance().httpRequest(HttpConstant.SNS_FAVORITE, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (callback != null) {
                    callback.onNetworkError();
                } else {
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                }
            }

            public void onStart() {
                if (callback != null) {
                    callback.onStart();
                }
            }

            public void onFinish() {
                if (callback != null) {
                    callback.onFinish();
                }
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (callback != null) {
                    callback.onFailure(statusCode, content, error);
                } else {
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    if (callback != null) {
                        callback.onSuccess(content);
                        return;
                    }
                    int code = new JSONObject(content).getInt("code");
                    if (code == 1) {
                        if (SnsOperation.this.mSnsInfo.isfavorite.get() == 1) {
                            SnsOperation.this.mSnsInfo.isfavorite.set(0);
                            ((BaseActivity) context).showFirstTypeToast(2131231678);
                        } else {
                            SnsOperation.this.mSnsInfo.isfavorite.set(1);
                            ((BaseActivity) context).showFirstTypeToast(2131230943);
                        }
                        EventBus.getDefault().post(new SnsInfoRefreshEvent(SnsOperation.this.mSnsInfo, 2001));
                    } else if (code == -5) {
                        ((BaseActivity) context).showSecondTypeToast(2131230795);
                    } else {
                        ((BaseActivity) context).showSecondTypeToast(2131230889);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ((BaseActivity) context).showSecondTypeToast(2131230945);
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.SNS_FAVORITE, content, e);
                }
            }
        });
    }

    public void reportOperation(final Context context) {
        if (FengApplication.getInstance().isLoginUser()) {
            checkSnsState(context, false, "举报", new SuccessFailCallback() {
                public void onSuccess() {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("title", "举报");
                    int type = 1;
                    if (SnsOperation.this.mSnsInfo.snstype == 0 || SnsOperation.this.mSnsInfo.snstype == 1) {
                        type = 1;
                    } else if (SnsOperation.this.mSnsInfo.snstype == 3) {
                        type = 5;
                    } else if (SnsOperation.this.mSnsInfo.snstype == 2) {
                        type = 6;
                    } else if (SnsOperation.this.mSnsInfo.snstype == 10) {
                        type = 7;
                    }
                    intent.putExtra("url", FengUtil.getReportUrl(context, type, SnsOperation.this.mSnsInfo.resourceid, SnsOperation.this.mSnsInfo.user.id));
                    context.startActivity(intent);
                }
            });
        } else {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    public void editOperation(Context context) {
        if (FengApplication.getInstance().getSeverceState()) {
            checkCanEditOrDel(context, true, null);
        } else {
            ((BaseActivity) context).showSecondTypeToast(2131231540);
        }
    }

    public void deleteOperation(final Context context, final OperationCallback callback) {
        if (FengApplication.getInstance().getSeverceState()) {
            checkSnsState(context, false, "删除", new SuccessFailCallback() {
                public void onSuccess() {
                    SnsOperation.this.checkCanEditOrDel(context, false, callback);
                }
            });
        } else {
            ((BaseActivity) context).showSecondTypeToast(2131231540);
        }
    }

    private void checkCanEditOrDel(final Context context, final boolean isEdit, final OperationCallback callback) {
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.SNS_ID, String.valueOf(this.mSnsInfo.id));
        map.put("resourceid", String.valueOf(this.mSnsInfo.resourceid));
        map.put(HttpConstant.RESOURCETYPE, String.valueOf(this.mSnsInfo.snstype));
        FengApplication.getInstance().httpRequest(HttpConstant.ISEDITORORDEL, map, new OkHttpResponseCallback() {
            public void onSuccess(int statusCode, String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code == 1) {
                        if (!isEdit) {
                            SnsOperation.this.doDelete(context, callback);
                        } else if (FengApplication.getInstance().getSeverceState()) {
                            Intent intent = new Intent(context, PostInitActivity.class);
                            intent.putExtra(HttpConstant.SNS_ID, SnsOperation.this.mSnsInfo.id);
                            intent.putExtra("resourceid", SnsOperation.this.mSnsInfo.resourceid);
                            intent.putExtra(HttpConstant.RESOURCETYPE, SnsOperation.this.mSnsInfo.snstype);
                            intent.putExtra(EditConstant.FROM_TYPE_KEY, 2);
                            context.startActivity(intent);
                        } else {
                            ((BaseActivity) context).showSecondTypeToast(2131231540);
                        }
                    } else if (code == -12 || code == -13 || code == -71 || code == -72) {
                        SnsOperation.this.showEditApplyDialog(context, code);
                    } else {
                        FengApplication.getInstance().checkCode(HttpConstant.ISEDITORORDEL, code);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.ISEDITORORDEL, content, e);
                }
            }

            public void onNetworkError() {
                ((BaseActivity) context).showSecondTypeToast(2131231273);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ((BaseActivity) context).showSecondTypeToast(2131231273);
            }
        });
    }

    private void doDelete(final Context context, final OperationCallback callback) {
        List<DialogItemEntity> list = new ArrayList();
        String str = "删除文章";
        if (this.mSnsInfo.snstype == 0 || this.mSnsInfo.snstype == 1) {
            str = "删除文章";
        } else if (this.mSnsInfo.snstype == 2) {
            str = "删除微文";
        } else if (this.mSnsInfo.snstype == 3) {
            str = "删除转发";
        } else if (this.mSnsInfo.snstype == 9 || this.mSnsInfo.snstype == 10) {
            str = "删除观点";
        }
        list.add(new DialogItemEntity(str, true));
        CommonDialog.showCommonDialog(context, "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                SnsOperation.this.deleteSns(context, callback);
            }
        });
    }

    private void deleteSns(final Context context, final OperationCallback callback) {
        String url;
        Map<String, Object> map = new HashMap();
        if (this.mSnsInfo.snstype == 10) {
            url = HttpConstant.SNSAUTODISCUSS_DELETE;
            map.put(HttpConstant.USERDISCUSSID, String.valueOf(this.mSnsInfo.resourceid));
            map.put(HttpConstant.AUTODISCUSSID, String.valueOf(this.mSnsInfo.discussinfo.id));
        } else {
            map.put(HttpConstant.SNS_ID, String.valueOf(this.mSnsInfo.id));
            map.put("resourceid", String.valueOf(this.mSnsInfo.resourceid));
            map.put(HttpConstant.RESOURCETYPE, String.valueOf(this.mSnsInfo.snstype));
            url = HttpConstant.SNS_DELETE;
        }
        FengApplication.getInstance().httpRequest(url, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (callback != null) {
                    callback.onNetworkError();
                } else {
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                }
            }

            public void onStart() {
                if (callback != null) {
                    callback.onStart();
                }
            }

            public void onFinish() {
                if (callback != null) {
                    callback.onFinish();
                }
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (callback != null) {
                    callback.onFailure(statusCode, content, error);
                } else {
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    if (callback != null) {
                        callback.onSuccess(content);
                        return;
                    }
                    int code = new JSONObject(content).getInt("code");
                    if (code == 1) {
                        ((BaseActivity) context).showFirstTypeToast(2131230984);
                        if (!(SnsOperation.this.mSnsInfo.snstype == 9 || SnsOperation.this.mSnsInfo.snstype == 10)) {
                            EventBus.getDefault().post(new SnsInfoRefreshEvent(SnsOperation.this.mSnsInfo, 2005));
                        }
                        if (SnsOperation.this.mSnsInfo.snstype == 9 || SnsOperation.this.mSnsInfo.snstype == 10) {
                            EventBus.getDefault().post(new UserPointRefreshEvent(2002, SnsOperation.this.mSnsInfo));
                            return;
                        }
                        return;
                    }
                    FengApplication.getInstance().checkCode(HttpConstant.SNS_DELETE, code);
                } catch (JSONException e) {
                    e.printStackTrace();
                    ((BaseActivity) context).showSecondTypeToast("删除帖子失败");
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.SNS_DELETE, content, e);
                }
            }
        });
    }

    private void showEditApplyDialog(final Context context, int code) {
        if (this.mEditApplyDialogBinding == null) {
            this.mEditApplyDialogBinding = EditarticleApplyDialogBinding.inflate(LayoutInflater.from(context));
            this.mEditApplyDialogBinding.applyCancel.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    SnsOperation.this.mEditApplyDialog.dismiss();
                }
            });
            this.mEditApplyDialogBinding.submit.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    SnsOperation.this.submitEditApply(context, SnsOperation.this.mEditApplyDialogBinding.reaseon.getText().toString());
                }
            });
        }
        this.mEditApplyDialogBinding.reaseon.setHint("请输入修改或删除的理由");
        if (code == -71) {
            this.mEditApplyDialogBinding.tvText.setText("您的文章已被设为老司机精选，如需修改或删除，请注明理由，我们会尽快给您答复！");
        } else if (code == -72) {
            if (this.mSnsInfo.snstype == 3 || this.mSnsInfo.snstype == 2) {
                this.mEditApplyDialogBinding.tvText.setText("您的微文已被设为老司机焦点图，如需删除，请注明理由，我们会尽快给您答复！");
                this.mEditApplyDialogBinding.reaseon.setHint("请输入删除的理由");
            } else {
                this.mEditApplyDialogBinding.tvText.setText("您的文章已被设为老司机焦点图，如需修改或删除，请注明理由，我们会尽快给您答复！");
            }
        } else if (code == -12) {
            this.mEditApplyDialogBinding.tvText.setText("您的文章已被设为5X兴趣社区选题，如需修改或删除，请注明理由，我们会尽快给您答复！");
        } else if (code == -13) {
            this.mEditApplyDialogBinding.tvText.setText("您的文章已被设为5X兴趣社区焦点图，如需修改或删除，请注明理由，我们会尽快给您答复！");
        }
        if (this.mEditApplyDialog == null) {
            this.mEditApplyDialog = new Dialog(context, 2131361986);
            this.mEditApplyDialog.setCanceledOnTouchOutside(true);
            this.mEditApplyDialog.setCancelable(false);
            Window window = this.mEditApplyDialog.getWindow();
            window.setGravity(17);
            window.setContentView(this.mEditApplyDialogBinding.getRoot());
            window.setLayout(-1, -1);
            this.mEditApplyDialog.setOnDismissListener(new OnDismissListener() {
                public void onDismiss(DialogInterface dialog) {
                    if (context instanceof VideoBaseActivity) {
                        ((VideoBaseActivity) context).videoPlaying();
                    }
                }
            });
        }
        this.mEditApplyDialogBinding.reaseon.setText("");
        this.mEditApplyDialog.show();
        if (context instanceof VideoBaseActivity) {
            ((VideoBaseActivity) context).videoPause();
        }
        try {
            this.mEditApplyDialogBinding.reaseon.postDelayed(new Runnable() {
                public void run() {
                    FengUtil.showSoftInput(context);
                }
            }, 150);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDislikeDialog(final Context context, final SuccessFailCallback callback) {
        if (FengApplication.getInstance().isLoginUser()) {
            List<DialogItemEntity> list = new ArrayList();
            if (this.mSnsInfo.snstype == 9 || this.mSnsInfo.snstype == 10) {
                list.add(new DialogItemEntity(context.getString(2131230993), false));
                list.add(new DialogItemEntity(context.getString(2131230992), false));
            } else {
                list.add(new DialogItemEntity(context.getString(2131230990), false));
                list.add(new DialogItemEntity(context.getString(2131230991, new Object[]{this.mSnsInfo.user.name.get()}), false));
                if (this.mSnsInfo.communitylist != null && this.mSnsInfo.communitylist.size() > 0) {
                    for (int i = 0; i < this.mSnsInfo.communitylist.size(); i++) {
                        list.add(new DialogItemEntity(context.getString(2131230989, new Object[]{this.mSnsInfo.communitylist.get(i).getCircleName()}), false));
                    }
                }
            }
            CommonDialog.showCommonDialog(context, "", list, new OnDialogItemClickListener() {
                public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                    if (position == 0) {
                        SnsOperation.this.doDislike(context, 0, 0, callback);
                    } else if (position == 1) {
                        if (SnsOperation.this.mSnsInfo.snstype == 9 || SnsOperation.this.mSnsInfo.snstype == 10) {
                            SnsOperation.this.doDislike(context, 0, 0, callback);
                        } else {
                            SnsOperation.this.doDislike(context, 1, 0, callback);
                        }
                    } else if (SnsOperation.this.mSnsInfo.communitylist.size() > position - 2) {
                        SnsOperation.this.doDislike(context, 2, SnsOperation.this.mSnsInfo.communitylist.get(position - 2).id, callback);
                    }
                }
            });
            return;
        }
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public void showReportTopicDialog(final Context context, final int circleId, final SuccessFailCallback callback) {
        if (FengApplication.getInstance().isLoginUser()) {
            List<DialogItemEntity> list = new ArrayList();
            list.add(new DialogItemEntity(context.getString(2131231094), false));
            CommonDialog.showCommonDialog(context, "", list, new OnDialogItemClickListener() {
                public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                    if (position == 0) {
                        SnsOperation.this.doReportTopic(context, circleId, callback);
                    }
                }
            });
            return;
        }
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    private void submitEditApply(final Context context, String content) {
        if (StringUtil.isEmpty(content)) {
            ((BaseActivity) context).showThirdTypeToast(2131231003);
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.SNS_ID, String.valueOf(this.mSnsInfo.id));
        map.put("resourceid", String.valueOf(this.mSnsInfo.resourceid));
        map.put(HttpConstant.RESOURCETYPE, String.valueOf(this.mSnsInfo.snstype));
        map.put("content", content);
        FengApplication.getInstance().httpRequest(HttpConstant.THREAD_APPLYFORREVISION, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ((BaseActivity) context).showSecondTypeToast(2131231273);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ((BaseActivity) context).showSecondTypeToast(2131231273);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code == 1) {
                        ((BaseActivity) context).showFirstTypeToast(2131231002);
                        if (SnsOperation.this.mEditApplyDialog != null && SnsOperation.this.mEditApplyDialog.isShowing()) {
                            SnsOperation.this.mEditApplyDialog.dismiss();
                            return;
                        }
                        return;
                    }
                    FengApplication.getInstance().checkCode(HttpConstant.THREAD_APPLYFORREVISION, code);
                } catch (Exception e) {
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.THREAD_APPLYFORREVISION, content, e);
                }
            }
        });
    }

    public void praiseOperation(final Context context, final boolean isChangePraiseCount, final SuccessFailCallback callback) {
        if (this.mSnsInfo.ispraise.get() == 1) {
            this.mSnsInfo.ispraise.set(0);
            if (isChangePraiseCount) {
                this.mSnsInfo.praisecount.set(this.mSnsInfo.praisecount.get() - 1);
            }
        } else {
            this.mSnsInfo.ispraise.set(1);
            if (isChangePraiseCount) {
                this.mSnsInfo.praisecount.set(this.mSnsInfo.praisecount.get() + 1);
            }
        }
        checkSnsState(context, false, "点赞", new SuccessFailCallback() {
            public void onSuccess() {
                SnsOperation.this.doPraise(context, isChangePraiseCount, callback);
            }

            public void onFail() {
                super.onFail();
                if (callback != null) {
                    if (SnsOperation.this.mSnsInfo.ispraise.get() == 1) {
                        SnsOperation.this.mSnsInfo.ispraise.set(0);
                        if (isChangePraiseCount) {
                            SnsOperation.this.mSnsInfo.praisecount.set(SnsOperation.this.mSnsInfo.praisecount.get() - 1);
                        }
                    } else {
                        SnsOperation.this.mSnsInfo.ispraise.set(1);
                        if (isChangePraiseCount) {
                            SnsOperation.this.mSnsInfo.praisecount.set(SnsOperation.this.mSnsInfo.praisecount.get() + 1);
                        }
                    }
                    callback.onFail();
                }
            }
        });
    }

    private void doPraise(final Context context, final boolean isChangePraiseCount, final SuccessFailCallback callback) {
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.RESOURCETYPE, String.valueOf(this.mSnsInfo.snstype));
        map.put("resourceid", String.valueOf(this.mSnsInfo.resourceid));
        if (this.mSnsInfo.ispraise.get() == 1) {
            map.put("type", String.valueOf(1));
        } else {
            map.put("type", String.valueOf(2));
        }
        map.put(HttpConstant.SNS_ID, String.valueOf(this.mSnsInfo.id));
        FengApplication.getInstance().httpRequest(HttpConstant.SNS_CLICKPRAISE, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ((BaseActivity) context).showSecondTypeToast(2131231273);
                if (callback != null) {
                    if (SnsOperation.this.mSnsInfo.ispraise.get() == 1) {
                        SnsOperation.this.mSnsInfo.ispraise.set(0);
                        if (isChangePraiseCount) {
                            SnsOperation.this.mSnsInfo.praisecount.set(SnsOperation.this.mSnsInfo.praisecount.get() - 1);
                        }
                    } else {
                        SnsOperation.this.mSnsInfo.ispraise.set(1);
                        if (isChangePraiseCount) {
                            SnsOperation.this.mSnsInfo.praisecount.set(SnsOperation.this.mSnsInfo.praisecount.get() + 1);
                        }
                    }
                    callback.onFail();
                }
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ((BaseActivity) context).showSecondTypeToast(2131231273);
                if (callback != null) {
                    if (SnsOperation.this.mSnsInfo.ispraise.get() == 1) {
                        SnsOperation.this.mSnsInfo.ispraise.set(0);
                        if (isChangePraiseCount) {
                            SnsOperation.this.mSnsInfo.praisecount.set(SnsOperation.this.mSnsInfo.praisecount.get() - 1);
                        }
                    } else {
                        SnsOperation.this.mSnsInfo.ispraise.set(1);
                        if (isChangePraiseCount) {
                            SnsOperation.this.mSnsInfo.praisecount.set(SnsOperation.this.mSnsInfo.praisecount.get() + 1);
                        }
                    }
                    callback.onFail();
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject resultJson = new JSONObject(content);
                    int code = resultJson.getInt("code");
                    if (code == 1) {
                        if (callback != null) {
                            callback.onSuccess();
                        }
                        if (!isChangePraiseCount) {
                            if (SnsOperation.this.mSnsInfo.ispraise.get() == 1) {
                                SnsOperation.this.mSnsInfo.praisecount.set(SnsOperation.this.mSnsInfo.praisecount.get() + 1);
                            } else {
                                SnsOperation.this.mSnsInfo.praisecount.set(SnsOperation.this.mSnsInfo.praisecount.get() - 1);
                            }
                        }
                        GratuityRecordInfo gratuityRecordInfo = new GratuityRecordInfo();
                        gratuityRecordInfo.parser(resultJson.getJSONObject("body").getJSONObject(HttpConstant.PRAISE));
                        if (gratuityRecordInfo.id == 0) {
                            gratuityRecordInfo.id = 1;
                        }
                        EventBus.getDefault().post(new SnsInfoRefreshEvent(SnsOperation.this.mSnsInfo, gratuityRecordInfo, 2004));
                    } else if (code == -5) {
                        if (callback != null) {
                            if (SnsOperation.this.mSnsInfo.ispraise.get() == 1) {
                                SnsOperation.this.mSnsInfo.ispraise.set(0);
                                if (isChangePraiseCount) {
                                    SnsOperation.this.mSnsInfo.praisecount.set(SnsOperation.this.mSnsInfo.praisecount.get() - 1);
                                }
                            } else {
                                SnsOperation.this.mSnsInfo.ispraise.set(1);
                                if (isChangePraiseCount) {
                                    SnsOperation.this.mSnsInfo.praisecount.set(SnsOperation.this.mSnsInfo.praisecount.get() + 1);
                                }
                            }
                            callback.onFail();
                        }
                        ((BaseActivity) context).showSecondTypeToast(2131230795);
                    } else {
                        if (callback != null) {
                            if (SnsOperation.this.mSnsInfo.ispraise.get() == 1) {
                                SnsOperation.this.mSnsInfo.ispraise.set(0);
                                if (isChangePraiseCount) {
                                    SnsOperation.this.mSnsInfo.praisecount.set(SnsOperation.this.mSnsInfo.praisecount.get() - 1);
                                }
                            } else {
                                SnsOperation.this.mSnsInfo.ispraise.set(1);
                                if (isChangePraiseCount) {
                                    SnsOperation.this.mSnsInfo.praisecount.set(SnsOperation.this.mSnsInfo.praisecount.get() + 1);
                                }
                            }
                            callback.onFail();
                        }
                        FengApplication.getInstance().checkCode(HttpConstant.SNS_CLICKPRAISE, code);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ((BaseActivity) context).showSecondTypeToast(2131230937);
                    if (callback != null) {
                        if (SnsOperation.this.mSnsInfo.ispraise.get() == 1) {
                            SnsOperation.this.mSnsInfo.ispraise.set(0);
                            if (isChangePraiseCount) {
                                SnsOperation.this.mSnsInfo.praisecount.set(SnsOperation.this.mSnsInfo.praisecount.get() - 1);
                            }
                        } else {
                            SnsOperation.this.mSnsInfo.ispraise.set(1);
                            if (isChangePraiseCount) {
                                SnsOperation.this.mSnsInfo.praisecount.set(SnsOperation.this.mSnsInfo.praisecount.get() + 1);
                            }
                        }
                        callback.onFail();
                    }
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.SNS_CLICKPRAISE, content, e);
                }
            }
        });
    }

    public void showForwardMenuOperation(final Context context) {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity(context.getString(2131231725), false, 60001));
        list.add(new DialogItemEntity(context.getString(2131230950), false, 60003));
        list.add(new DialogItemEntity(context.getString(2131230973), false, 60004));
        if (!FengApplication.getInstance().isLoginUser() || this.mSnsInfo.user.getIsMy() == 0) {
            list.add(new DialogItemEntity(context.getString(2131231482), false, 60005));
        } else {
            list.add(new DialogItemEntity(context.getString(2131230982), false, 60006));
        }
        CommonDialog.showCommonDialog(context, "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                switch (dialogItemEntity.itemTag) {
                    case 60003:
                        SnsOperation.this.intentToSendComment(context, 0, "");
                        return;
                    case 60004:
                        FengUtil.copyText(context, (String) SnsOperation.this.mSnsInfo.description.get(), "已复制");
                        return;
                    case 60005:
                        SnsOperation.this.reportOperation(context);
                        return;
                    case 60006:
                        SnsOperation.this.deleteOperation(context, null);
                        return;
                    default:
                        return;
                }
            }
        });
    }

    public void socialShare(Activity activity, SHARE_MEDIA media, UMShareListener umShareListener, int type) {
        ShareUtils.socialShare(activity, this.mSnsInfo, media, umShareListener, type);
    }

    public void copySnsUrl(Context context, int type) {
        if (type != 1) {
            return;
        }
        if (this.mSnsInfo.snstype == 9) {
            FengUtil.copyText(context, MessageFormat.format(HttpConstant.SHARE_P_DISCUSS, new Object[]{this.mSnsInfo.resourceid + ""}), "已复制");
        } else if (this.mSnsInfo.snstype == 10) {
            FengUtil.copyText(context, MessageFormat.format(HttpConstant.SHARE_U_DISCUSS, new Object[]{this.mSnsInfo.resourceid + ""}), "已复制");
        } else if (FengApplication.getInstance().isLoginUser()) {
            FengUtil.copyText(context, MessageFormat.format(HttpConstant.SHARE_POST, new Object[]{this.mSnsInfo.resourceid + ""}) + "?shareuid=" + FengApplication.getInstance().getUserInfo().id, "已复制");
        } else {
            FengUtil.copyText(context, MessageFormat.format(HttpConstant.SHARE_POST, new Object[]{this.mSnsInfo.resourceid + ""}), "已复制");
        }
    }

    public void intetntToViewPoint(Context context, boolean isNeedClose) {
        Intent intent = new Intent(context, ViewPointActivity.class);
        intent.putExtra(HttpConstant.SNS_ID, this.mSnsInfo.id);
        intent.putExtra("resourceid", this.mSnsInfo.resourceid);
        intent.putExtra(HttpConstant.RESOURCETYPE, this.mSnsInfo.snstype);
        intent.putExtra("isNeedClose", isNeedClose);
        context.startActivity(intent);
    }

    public void intetntToViewPoint(Context context, boolean isNeedClose, int type, int commentid) {
        Intent intent = new Intent(context, ViewPointActivity.class);
        intent.putExtra(HttpConstant.SNS_ID, this.mSnsInfo.id);
        intent.putExtra("resourceid", this.mSnsInfo.resourceid);
        intent.putExtra(HttpConstant.RESOURCETYPE, this.mSnsInfo.snstype);
        intent.putExtra("isNeedClose", isNeedClose);
        intent.putExtra(HttpConstant.LOCATION_TYPE, type);
        intent.putExtra(HttpConstant.COMMENT_ID, commentid);
        context.startActivity(intent);
    }

    public void checkSnsState(Context context, SuccessFailCallback callback) {
        checkSnsState(context, false, "", callback);
    }

    public void checkSnsState(Context context, boolean isOldDriverRecommend, SuccessFailCallback callback) {
        checkSnsState(context, false, isOldDriverRecommend, "", callback);
    }

    private void doDislike(final Context context, int type, int circleID, final SuccessFailCallback callback) {
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.SNS_ID, String.valueOf(this.mSnsInfo.id));
        map.put("type", String.valueOf(type));
        if (type == 1) {
            map.put("dislikeid", String.valueOf(this.mSnsInfo.user.id));
        } else if (type == 2) {
            map.put("dislikeid", String.valueOf(circleID));
        } else {
            map.put("dislikeid", "");
        }
        FengApplication.getInstance().httpRequest(HttpConstant.RECOMMEND_DISLIKE, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ((BaseActivity) context).showSecondTypeToast(2131231273);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ((BaseActivity) context).showSecondTypeToast(2131231273);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    if (new JSONObject(content).getInt("code") == 1) {
                        EventBus.getDefault().post(new UserDisLikeEvent(SnsOperation.this.mSnsInfo));
                        if (callback != null) {
                            callback.onSuccess();
                            return;
                        }
                        return;
                    }
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                } catch (JSONException e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.RECOMMEND_DISLIKE, content, e);
                }
            }
        });
    }

    private void doReportTopic(final Context context, int circleID, final SuccessFailCallback callback) {
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.COMMUNITY_ID, String.valueOf(circleID));
        map.put(HttpConstant.RESOURCETYPE, String.valueOf(this.mSnsInfo.snstype));
        map.put("resourceid", String.valueOf(this.mSnsInfo.resourceid));
        FengApplication.getInstance().httpRequest(HttpConstant.REPORT_COMMUNITY, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ((BaseActivity) context).showSecondTypeToast(2131231273);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ((BaseActivity) context).showSecondTypeToast(2131231273);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    if (new JSONObject(content).getInt("code") == 1) {
                        ((BaseActivity) context).showFirstTypeToast(2131231604);
                        if (callback != null) {
                            callback.onSuccess();
                            return;
                        }
                        return;
                    }
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                } catch (JSONException e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.REPORT_COMMUNITY, content, e);
                }
            }
        });
    }

    public void shareToWeiXin(final Context context, final IWXAPI iwxapi) {
        String strUrl = FengUtil.getUniformScaleUrl(this.mSnsInfo.image, FengConstant.IMAGEMIDDLEWIDTH, 0.56f);
        FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainDiskStorageCache().getResource(new SimpleCacheKey(strUrl));
        File file = null;
        if (resource != null) {
            file = resource.getFile();
        }
        Bitmap bitmap = null;
        if (file != null && file.exists()) {
            bitmap = ImageUtil.getBitmap(file);
        }
        if (bitmap != null) {
            shareMiniProgram(context, iwxapi, bitmap);
        } else if (TextUtils.isEmpty(this.mSnsInfo.image.url)) {
            shareMiniProgram(context, iwxapi, null);
        } else {
            ImageUtil.downLoadImage(strUrl, new OnDownloadImageListener() {
                public void onDownloadSuccess(final Bitmap bitmap2) {
                    ((BaseActivity) context).runOnUiThread(new Runnable() {
                        public void run() {
                            SnsOperation.this.shareMiniProgram(context, iwxapi, bitmap2);
                        }
                    });
                }

                public void onDownloadFailed() {
                    ((BaseActivity) context).runOnUiThread(new Runnable() {
                        public void run() {
                            SnsOperation.this.shareMiniProgram(context, iwxapi, null);
                        }
                    });
                }
            });
        }
    }

    private void shareMiniProgram(Context context, IWXAPI mIwxapi, Bitmap bitmap) {
        try {
            WXMiniProgramObject miniProgram = new WXMiniProgramObject();
            if (FengApplication.getInstance().getIsOpenTest()) {
                miniProgram.miniprogramType = 2;
            } else {
                miniProgram.miniprogramType = 0;
            }
            String url = "";
            miniProgram.userName = FengConstant.MINI_APP_ID;
            if (this.mSnsInfo.snstype == 9) {
                url = MessageFormat.format(HttpConstant.SHARE_P_DISCUSS, new Object[]{this.mSnsInfo.resourceid + ""});
                miniProgram.path = "pages/articles/articles?type=9&id=" + this.mSnsInfo.resourceid;
            } else {
                if (FengApplication.getInstance().isLoginUser()) {
                    url = MessageFormat.format(HttpConstant.SHARE_POST, new Object[]{this.mSnsInfo.resourceid + ""}) + "?shareuid=" + FengApplication.getInstance().getUserInfo().id;
                } else {
                    url = MessageFormat.format(HttpConstant.SHARE_POST, new Object[]{this.mSnsInfo.resourceid + ""});
                }
                miniProgram.path = "pages/articles/articles?type=0&id=" + this.mSnsInfo.resourceid;
            }
            miniProgram.webpageUrl = url;
            WXMediaMessage msg = new WXMediaMessage(miniProgram);
            msg.title = this.mSnsInfo.getShareTitleOrDes();
            msg.mediaObject = miniProgram;
            if (bitmap == null) {
                msg.thumbData = ImageUtil.bitmap2Bytes(BitmapFactory.decodeResource(context.getResources(), 2130837823), 32768, true);
            } else {
                msg.thumbData = ImageUtil.bitmap2Bytes(bitmap, 32768, true);
            }
            Req rep = new Req();
            rep.transaction = buildTransaction("miniProgram");
            rep.message = msg;
            rep.scene = 0;
            mIwxapi.sendReq(rep);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String buildTransaction(String type) {
        return type == null ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
