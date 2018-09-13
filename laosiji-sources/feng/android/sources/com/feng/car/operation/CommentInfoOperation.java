package com.feng.car.operation;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.CommentReplyListActivity;
import com.feng.car.activity.LoginActivity;
import com.feng.car.activity.SendCommentActivity;
import com.feng.car.activity.SendPrivateLetterActivity;
import com.feng.car.activity.WebActivity;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.thread.CommentInfo;
import com.feng.car.event.CommentPageRefreshEvent;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.listener.CommentItemListener;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.view.CommonDialog;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class CommentInfoOperation {
    private CommentInfo mCommentInfo;

    public CommentInfoOperation(CommentInfo commentInfo) {
        this.mCommentInfo = commentInfo;
    }

    public void intentToSendCommentActivityFromSns(Context context, SnsInfo snsInfo, int viewDy, String key, CommentItemListener listener) {
        if (!FengApplication.getInstance().getSeverceState()) {
            ((BaseActivity) context).showSecondTypeToast(2131231540);
        } else if (FengApplication.getInstance().isLoginUser()) {
            final CommentItemListener commentItemListener = listener;
            final Context context2 = context;
            final SnsInfo snsInfo2 = snsInfo;
            final int i = viewDy;
            final String str = key;
            checkCommentState(context, "回复", new SuccessFailCallback() {
                public void onSuccess() {
                    if (commentItemListener != null) {
                        commentItemListener.onItemClick();
                    }
                    Intent intent = new Intent(context2, SendCommentActivity.class);
                    intent.putExtra(HttpConstant.SNS_ID, snsInfo2.id);
                    intent.putExtra("userid", CommentInfoOperation.this.mCommentInfo.user.id);
                    intent.putExtra("comment_count", snsInfo2.commentcount.get());
                    intent.putExtra(FengConstant.FENGTYPE, 2);
                    intent.putExtra("resourceid", snsInfo2.resourceid);
                    intent.putExtra(HttpConstant.RESOURCETYPE, snsInfo2.snstype);
                    intent.putExtra(HttpConstant.PARENTID, CommentInfoOperation.this.mCommentInfo.id);
                    intent.putExtra("name", (String) CommentInfoOperation.this.mCommentInfo.user.name.get());
                    intent.putExtra("view_local_slide_dy", i);
                    intent.putExtra("event_key", str);
                    context2.startActivity(intent);
                    ((BaseActivity) context2).overridePendingTransition(2130968593, 0);
                }
            });
        } else {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    public void intentToSendCommentActivity(final Context context, final int viewDy, final String key) {
        if (!FengApplication.getInstance().getSeverceState()) {
            ((BaseActivity) context).showSecondTypeToast(2131231540);
        } else if (FengApplication.getInstance().isLoginUser()) {
            checkCommentState(context, "回复", new SuccessFailCallback() {
                public void onSuccess() {
                    Intent intent = new Intent(context, SendCommentActivity.class);
                    intent.putExtra(FengConstant.FENGTYPE, 2);
                    intent.putExtra("userid", CommentInfoOperation.this.mCommentInfo.user.id);
                    intent.putExtra(HttpConstant.COMMENT_ID, CommentInfoOperation.this.mCommentInfo.id);
                    intent.putExtra("resourceid", CommentInfoOperation.this.mCommentInfo.sns.resourceid);
                    intent.putExtra(HttpConstant.SNS_ID, CommentInfoOperation.this.mCommentInfo.sns.id);
                    intent.putExtra("comment_count", CommentInfoOperation.this.mCommentInfo.sns.commentcount.get());
                    intent.putExtra(HttpConstant.RESOURCETYPE, CommentInfoOperation.this.mCommentInfo.sns.snstype);
                    intent.putExtra(HttpConstant.PARENTID, CommentInfoOperation.this.mCommentInfo.id);
                    intent.putExtra("name", (String) CommentInfoOperation.this.mCommentInfo.user.name.get());
                    intent.putExtra("view_local_slide_dy", viewDy);
                    intent.putExtra("event_key", key);
                    context.startActivity(intent);
                    ((BaseActivity) context).overridePendingTransition(2130968593, 0);
                }
            });
        } else {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    public void intentToCommentReplyListActivity(Context context, int type, int selectCommentID) {
        if (FengApplication.getInstance().getSeverceState()) {
            Intent intent = new Intent(context, CommentReplyListActivity.class);
            if (this.mCommentInfo.originparentid == 0) {
                intent.putExtra(HttpConstant.COMMENT_ID, this.mCommentInfo.id);
            } else {
                intent.putExtra(HttpConstant.COMMENT_ID, this.mCommentInfo.originparentid);
            }
            intent.putExtra("comment_select_id", selectCommentID);
            intent.putExtra(FengConstant.FENGTYPE, type);
            context.startActivity(intent);
            return;
        }
        ((BaseActivity) context).showSecondTypeToast(2131231540);
    }

    public void intentToSendLetterActivity(Context context) {
        if (!FengApplication.getInstance().getSeverceState()) {
            ((BaseActivity) context).showSecondTypeToast(2131231540);
        } else if (FengApplication.getInstance().isLoginUser()) {
            Intent intent = new Intent(context, SendPrivateLetterActivity.class);
            intent.putExtra("userid", this.mCommentInfo.user.id);
            intent.putExtra("name", (String) this.mCommentInfo.user.name.get());
            context.startActivity(intent);
        } else {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    public void intentToReportActivity(final Context context) {
        if (FengApplication.getInstance().isLoginUser()) {
            checkCommentState(context, "举报", new SuccessFailCallback() {
                public void onSuccess() {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("title", context.getString(2131231482));
                    intent.putExtra("url", FengUtil.getReportUrl(context, 2, CommentInfoOperation.this.mCommentInfo.id, CommentInfoOperation.this.mCommentInfo.user.id));
                    context.startActivity(intent);
                }
            });
        } else {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    public void deleteCommentOperation(final Context context, final SnsInfo snsInfo, final OperationCallback callback) {
        if (FengApplication.getInstance().getSeverceState()) {
            checkCommentState(context, "删除", new SuccessFailCallback() {
                public void onSuccess() {
                    CommentInfoOperation.this.doDelete(context, snsInfo, callback);
                }
            });
        } else {
            ((BaseActivity) context).showSecondTypeToast(2131231540);
        }
    }

    public void deleteDraftCommentOperation(Context context, SnsInfo snsInfo, OperationCallback callback) {
        doDelete(context, snsInfo, callback);
    }

    private void doDelete(final Context context, final SnsInfo snsInfo, final OperationCallback callback) {
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.COMMENT_ID, String.valueOf(this.mCommentInfo.id));
        map.put("resourceid", String.valueOf(this.mCommentInfo.resourceid));
        map.put(HttpConstant.RESOURCETYPE, String.valueOf(this.mCommentInfo.sns.snstype));
        FengApplication.getInstance().httpRequest(HttpConstant.DELTE_COMMENT, map, new OkHttpResponseCallback() {
            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject resultJson = new JSONObject(content);
                    if (callback != null) {
                        callback.onSuccess(content);
                        return;
                    }
                    int code = resultJson.getInt("code");
                    if (code == 1) {
                        CommentInfoOperation.this.mCommentInfo.isdel = 1;
                        ((BaseActivity) context).showSecondTypeToast(2131230948);
                        SnsInfo snsInfo1 = new SnsInfo();
                        snsInfo1.id = snsInfo.id;
                        if (CommentInfoOperation.this.mCommentInfo.originparentid == 0) {
                            snsInfo1.commentcount.set((snsInfo.commentcount.get() - 1) - CommentInfoOperation.this.mCommentInfo.reply.count);
                        } else {
                            snsInfo1.commentcount.set(snsInfo.commentcount.get() - 1);
                        }
                        SnsInfoRefreshEvent event = new SnsInfoRefreshEvent(snsInfo1, 2006);
                        event.commentInfo = CommentInfoOperation.this.mCommentInfo;
                        EventBus.getDefault().post(event);
                        return;
                    }
                    FengApplication.getInstance().checkCode(HttpConstant.DELTE_COMMENT, code);
                } catch (JSONException e) {
                    ((BaseActivity) context).showSecondTypeToast(2131230947);
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.DELTE_COMMENT, content, e);
                }
            }

            public void onNetworkError() {
                ((BaseActivity) context).showSecondTypeToast(2131231273);
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
                ((BaseActivity) context).showSecondTypeToast(2131231273);
            }
        });
    }

    public void processMenuClick(int tag, final Context context, final SnsInfo snsInfo, int viewDy, String key, CommentItemListener listener) {
        switch (tag) {
            case 50001:
                intentToSendCommentActivityFromSns(context, snsInfo, viewDy, key, listener);
                return;
            case 50002:
                intentToSendLetterActivity(context);
                return;
            case 50003:
                FengUtil.copyText(context, (String) this.mCommentInfo.content.get(), "已复制");
                return;
            case 50004:
                intentToReportActivity(context);
                return;
            case 50005:
                List<DialogItemEntity> list = new ArrayList();
                list.add(new DialogItemEntity("删除该评论", true));
                CommonDialog.showCommonDialog(context, "", list, new OnDialogItemClickListener() {
                    public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                        CommentInfoOperation.this.deleteCommentOperation(context, snsInfo, null);
                    }
                });
                return;
            default:
                return;
        }
    }

    private void checkCommentState(Context context, String msg, SuccessFailCallback callback) {
        if (callback != null) {
            callback.onSuccess();
        }
    }

    public void clickPraiseOperation(final Context context, final SuccessFailCallback callback) {
        if (FengApplication.getInstance().isLoginUser() && FengApplication.getInstance().getUserInfo().isadministrator == 1) {
            List list = new ArrayList();
            list.add(new DialogItemEntity(context.getString(2131230969), false));
            CommonDialog.showCommonDialog(context, "确定给TA 10个赞", list, new OnDialogItemClickListener() {
                public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                    CommentInfoOperation.this.clickPraise(context, callback);
                }
            }, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (callback != null) {
                        callback.onFail();
                    }
                }
            });
            return;
        }
        clickPraise(context, callback);
    }

    private void clickPraise(final Context context, final SuccessFailCallback callback) {
        if (!(FengApplication.getInstance().isLoginUser() && FengApplication.getInstance().getUserInfo().isadministrator == 1) && this.mCommentInfo.istop.get() == 1) {
            ((BaseActivity) context).showThirdTypeToast(2131231095);
            if (callback != null) {
                callback.onSuccess();
                return;
            }
            return;
        }
        this.mCommentInfo.istop.set(1);
        checkCommentState(context, "顶", new SuccessFailCallback() {
            public void onSuccess() {
                CommentInfoOperation.this.doClickPraise(context, callback);
            }

            public void onFail() {
                CommentInfoOperation.this.mCommentInfo.istop.set(0);
                CommentInfoOperation.this.mCommentInfo.top.set(CommentInfoOperation.this.mCommentInfo.top.get() - 1);
                if (callback != null) {
                    callback.onFail();
                }
            }
        });
    }

    private void doClickPraise(final Context context, final SuccessFailCallback callback) {
        Map<String, Object> map = new HashMap();
        map.put("resourceid", String.valueOf(this.mCommentInfo.id));
        map.put("type", String.valueOf(1));
        map.put(HttpConstant.RESOURCETYPE, String.valueOf(5));
        FengApplication.getInstance().httpRequest(HttpConstant.SNS_CLICKPRAISE, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ((BaseActivity) context).showSecondTypeToast(2131231273);
                if (callback != null) {
                    CommentInfoOperation.this.mCommentInfo.istop.set(0);
                    CommentInfoOperation.this.mCommentInfo.top.set(CommentInfoOperation.this.mCommentInfo.top.get() - 1);
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
                    CommentInfoOperation.this.mCommentInfo.istop.set(0);
                    CommentInfoOperation.this.mCommentInfo.top.set(CommentInfoOperation.this.mCommentInfo.top.get() - 1);
                    callback.onFail();
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code == 1) {
                        if (callback != null) {
                            callback.onSuccess();
                        }
                        int count = CommentInfoOperation.this.mCommentInfo.top.get();
                        if (FengApplication.getInstance().isLoginUser() && FengApplication.getInstance().getUserInfo().isadministrator == 1) {
                            count += 10;
                        } else {
                            count++;
                        }
                        CommentInfoOperation.this.mCommentInfo.istop.set(1);
                        EventBus.getDefault().post(new CommentPageRefreshEvent(CommentInfoOperation.this.mCommentInfo.id, count, true));
                        if (CommentInfoOperation.this.mCommentInfo.sns.ismythread == 1) {
                            CommentInfoOperation.this.mCommentInfo.istopbyauthor.set(1);
                            return;
                        }
                        return;
                    }
                    if (code == -5) {
                        ((BaseActivity) context).showSecondTypeToast(2131230795);
                    } else if (code == -37) {
                        ((BaseActivity) context).showSecondTypeToast(2131231751);
                    } else {
                        FengApplication.getInstance().checkCode(HttpConstant.SNS_CLICKPRAISE, code);
                    }
                    if (callback != null) {
                        CommentInfoOperation.this.mCommentInfo.istop.set(0);
                        CommentInfoOperation.this.mCommentInfo.top.set(CommentInfoOperation.this.mCommentInfo.top.get() - 1);
                        callback.onFail();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ((BaseActivity) context).showSecondTypeToast(2131230831);
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.SNS_CLICKPRAISE, content, e);
                    if (callback != null) {
                        CommentInfoOperation.this.mCommentInfo.istop.set(0);
                        CommentInfoOperation.this.mCommentInfo.top.set(CommentInfoOperation.this.mCommentInfo.top.get() - 1);
                        callback.onFail();
                    }
                }
            }
        });
    }
}
