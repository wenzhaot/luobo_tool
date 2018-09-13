package com.feng.car.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.CircleFindActivity;
import com.feng.car.adapter.CircleRecommendAdapter;
import com.feng.car.adapter.CircleRecommendAdapter.CircleSelectListener;
import com.feng.car.databinding.CircleRecommendLayoutBinding;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.utils.HttpConstant;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class CircleRecommendView extends FrameLayout implements CircleSelectListener {
    private CircleRecommendLayoutBinding mBinding;
    private List<Integer> mCircleIdList = new ArrayList();
    private CircleRecommendAdapter mCircleRecommendAdapter;
    private Context mContext;
    private FollowCircleListener mFollowCircleListener;
    private List<CircleInfo> mList = new ArrayList();

    public interface FollowCircleListener {
        void onFollowedCircle();
    }

    public CircleRecommendView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CircleRecommendView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CircleRecommendView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.mBinding = CircleRecommendLayoutBinding.inflate(LayoutInflater.from(this.mContext));
        addView(this.mBinding.getRoot());
        this.mCircleRecommendAdapter = new CircleRecommendAdapter(this.mContext, this.mList);
        this.mCircleRecommendAdapter.setCircleSelectListener(this);
        this.mBinding.recyclerView.setLayoutManager(new GridLayoutManager(this.mContext, 3) {
            public boolean canScrollVertically() {
                return false;
            }

            public boolean canScrollHorizontally() {
                return super.canScrollHorizontally();
            }
        });
        this.mBinding.recyclerView.setAdapter(this.mCircleRecommendAdapter);
        this.mBinding.findCircle.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                CircleRecommendView.this.mContext.startActivity(new Intent(CircleRecommendView.this.mContext, CircleFindActivity.class));
            }
        });
        this.mBinding.finish.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (CircleRecommendView.this.mCircleIdList.size() > 0) {
                    CircleRecommendView.this.followCircle();
                }
            }
        });
    }

    public void setCircleList(List<CircleInfo> list) {
        this.mList.clear();
        this.mList.addAll(list);
        this.mCircleRecommendAdapter.notifyDataSetChanged();
    }

    public void onCircleSelect(int circleid) {
        if (this.mCircleIdList.contains(Integer.valueOf(circleid))) {
            this.mCircleIdList.remove(Integer.valueOf(circleid));
        } else {
            this.mCircleIdList.add(Integer.valueOf(circleid));
        }
        if (this.mCircleIdList.size() > 0) {
            this.mBinding.finishLine.setVisibility(0);
        } else {
            this.mBinding.finishLine.setVisibility(8);
        }
    }

    private String getCircleIds() {
        StringBuilder sb = new StringBuilder();
        int size = this.mCircleIdList.size();
        for (int i = 0; i < size; i++) {
            sb.append(((Integer) this.mCircleIdList.get(i)).intValue());
            if (i != size - 1) {
                sb.append(MiPushClient.ACCEPT_TIME_SEPARATOR);
            }
        }
        return sb.toString();
    }

    private void followCircle() {
        Map<String, Object> map = new HashMap();
        map.put("type", String.valueOf(0));
        map.put(HttpConstant.COMMUNITYIDLIST, getCircleIds());
        FengApplication.getInstance().httpRequest(HttpConstant.COMMUNITY_JOIN, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ((BaseActivity) CircleRecommendView.this.mContext).showSecondTypeToast(2131231028);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code != 1) {
                        FengApplication.getInstance().checkCode(HttpConstant.COMMUNITY_JOIN, code);
                    } else if (CircleRecommendView.this.mFollowCircleListener != null) {
                        CircleRecommendView.this.mFollowCircleListener.onFollowedCircle();
                    }
                } catch (Exception e) {
                    ((BaseActivity) CircleRecommendView.this.mContext).showSecondTypeToast(2131231273);
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.COMMUNITY_JOIN, content, e);
                }
            }
        });
    }

    public void setFollowCircleListener(FollowCircleListener l) {
        this.mFollowCircleListener = l;
    }
}
