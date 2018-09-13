package com.feng.car.operation;

import android.content.Context;
import android.content.Intent;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.NewSubjectActivity;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.event.AddCircleEvent;
import com.feng.car.utils.HttpConstant;
import com.feng.car.view.CommonDialog;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

public class CircleOperation {
    private CircleInfo mCircleInfo;

    public CircleOperation(CircleInfo info) {
        this.mCircleInfo = info;
    }

    public void intentToCircleFinalPage(Context context) {
        Intent intent = new Intent(context, NewSubjectActivity.class);
        intent.putExtra(HttpConstant.CARS_ID, this.mCircleInfo.autoseriesid);
        intent.putExtra(NewSubjectActivity.CIRCLES_REQUEST_ID, this.mCircleInfo.id);
        context.startActivity(intent);
    }

    public void intentToCarSeriesFinalPage(Context context) {
        if (this.mCircleInfo.type == 0) {
            Intent intent = new Intent(context, NewSubjectActivity.class);
            intent.putExtra(HttpConstant.CARS_ID, this.mCircleInfo.autoseriesid);
            intent.putExtra(NewSubjectActivity.CIRCLES_REQUEST_ID, this.mCircleInfo.id);
            context.startActivity(intent);
        }
    }

    public void accedeSingleOperation(final Context context, final OperationCallback callback) {
        List<DialogItemEntity> list = new ArrayList();
        if (this.mCircleInfo.isfans.get() == 1) {
            list.add(new DialogItemEntity(context.getString(2131230812), false));
            CommonDialog.showCommonDialog(context, context.getString(2131231434), list, new OnDialogItemClickListener() {
                public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                    CircleOperation.this.doHoinOrCancel(context, CircleOperation.this.mCircleInfo.isfans.get(), String.valueOf(CircleOperation.this.mCircleInfo.id), callback);
                }
            });
            return;
        }
        doHoinOrCancel(context, this.mCircleInfo.isfans.get(), String.valueOf(this.mCircleInfo.id), callback);
    }

    public void accedeBatchOperation(Context context, int type, String strIDs, OperationCallback callback) {
        doHoinOrCancel(context, type, strIDs, callback);
    }

    private void doHoinOrCancel(final Context context, final int type, String strIDs, final OperationCallback callback) {
        Map<String, Object> map = new HashMap();
        map.put("type", String.valueOf(type));
        map.put(HttpConstant.COMMUNITYIDLIST, strIDs);
        FengApplication.getInstance().httpRequest(HttpConstant.COMMUNITY_JOIN, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (callback != null) {
                    callback.onNetworkError();
                }
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
                if (callback != null) {
                    callback.onFinish();
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code == 1) {
                        if (type == 1) {
                            CircleOperation.this.mCircleInfo.isfans.set(0);
                            ((BaseActivity) context).showFirstTypeToast(2131231432);
                        } else {
                            CircleOperation.this.mCircleInfo.isfans.set(1);
                            ((BaseActivity) context).showFourthTypeToast(2131230851);
                        }
                        if (callback != null) {
                            callback.onSuccess(content);
                        }
                        EventBus.getDefault().post(new AddCircleEvent(AddCircleEvent.ATTENTION_CANCEL_CIRCLE_TYPE, CircleOperation.this.mCircleInfo));
                        return;
                    }
                    FengApplication.getInstance().checkCode(HttpConstant.COMMUNITY_JOIN, code);
                } catch (Exception e) {
                    ((BaseActivity) context).showSecondTypeToast(2131231273);
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.COMMUNITY_JOIN, content, e);
                }
            }
        });
    }
}
