package com.feng.car.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.FindFollowAdapter;
import com.feng.car.adapter.FindFollowAdapter.OnSelChangeListener;
import com.feng.car.databinding.ActivityFindFollowBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.entity.user.UserInfoList;
import com.feng.car.event.OpenActivityEvent;
import com.feng.car.event.RefreshEvent;
import com.feng.car.event.UserFirstFollowEvent;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class FindFollowActivity extends BaseActivity<ActivityFindFollowBinding> {
    private FindFollowAdapter mAdapter;
    private UserInfoList mList = new UserInfoList();
    private Map<String, Object> mMap = new HashMap();

    public int setBaseContentView() {
        return R.layout.activity_find_follow;
    }

    public void initView() {
        FengApplication.getInstance().setAlreadyFollowShow(true);
        initNormalTitleBar((int) R.string.findFansFollow);
        initTitleBarRightText(R.string.skip_follow, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                FindFollowActivity.this.finish();
            }
        });
        this.mRootBinding.titleLine.ivTitleLeft.setVisibility(8);
        this.mRootBinding.titleLine.tvRightText.setTextColor(this.mResources.getColorStateList(R.color.selector_black54_pressed_black87));
        ((ActivityFindFollowBinding) this.mBaseBinding).lrvFindFollow.setLayoutManager(new LinearLayoutManager(this));
        this.mAdapter = new FindFollowAdapter(this, this.mList, new OnSelChangeListener() {
            public void onSelChange(int count) {
                if (count > 0) {
                    ((ActivityFindFollowBinding) FindFollowActivity.this.mBaseBinding).tvFindFollowAll.setSelected(true);
                } else {
                    ((ActivityFindFollowBinding) FindFollowActivity.this.mBaseBinding).tvFindFollowAll.setSelected(false);
                }
            }
        });
        ((ActivityFindFollowBinding) this.mBaseBinding).lrvFindFollow.setAdapter(this.mAdapter);
        getData();
        ((ActivityFindFollowBinding) this.mBaseBinding).tvFindFollowAll.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (FindFollowActivity.this.mAdapter.getUserIDList().size() > 0) {
                    FindFollowActivity.this.requestFollowAll();
                }
            }
        });
    }

    private void requestFollowAll() {
        this.mMap.clear();
        Map<String, Object> map = new HashMap();
        map.put("useridlist", this.mAdapter.getUserIDList());
        this.mMap.put("useridlist", map);
        this.mMap.put("type", String.valueOf(1));
        FengApplication.getInstance().httpRequest("user/followuserlist/", this.mMap, new OkHttpResponseCallback() {
            public void onNetworkError() {
                FindFollowActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
                FindFollowActivity.this.showProgress("", "请稍等...");
            }

            public void onFinish() {
                FindFollowActivity.this.hideProgress();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                FindFollowActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                FindFollowActivity.this.hideProgress();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code == 1) {
                        EventBus.getDefault().post(new RefreshEvent(2));
                        EventBus.getDefault().post(new UserFirstFollowEvent());
                        EventBus.getDefault().post(new OpenActivityEvent());
                        FindFollowActivity.this.finish();
                        return;
                    }
                    FengApplication.getInstance().checkCode("user/followuserlist/", code);
                } catch (JSONException e) {
                    e.printStackTrace();
                    FindFollowActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    FengApplication.getInstance().upLoadTryCatchLog("user/followuserlist/", content, e);
                }
            }
        });
    }

    private void getData() {
        this.mMap.clear();
        FengApplication.getInstance().httpRequest("user/ywf/guidefollow/", this.mMap, new OkHttpResponseCallback() {
            public void onNetworkError() {
                FindFollowActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        FindFollowActivity.this.getData();
                    }
                });
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                FindFollowActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        FindFollowActivity.this.getData();
                    }
                });
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        JSONObject jsonUsers = jsonResult.getJSONObject("body").getJSONObject("user");
                        BaseListModel<UserInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(UserInfo.class, jsonUsers);
                        List<UserInfo> list = baseListModel.list;
                        FindFollowActivity.this.hideEmptyView();
                        if (list.size() > 0) {
                            FindFollowActivity.this.mList.addAll(list);
                        }
                        if (FindFollowActivity.this.mList.size() > 0) {
                            ((ActivityFindFollowBinding) FindFollowActivity.this.mBaseBinding).tvFindFollowAll.setSelected(true);
                        } else {
                            ((ActivityFindFollowBinding) FindFollowActivity.this.mBaseBinding).tvFindFollowAll.setSelected(false);
                        }
                        FindFollowActivity.this.mAdapter.setUserIDList();
                        FindFollowActivity.this.mAdapter.notifyDataSetChanged();
                        return;
                    }
                    FindFollowActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            FindFollowActivity.this.getData();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("user/fins/", content, e);
                }
            }
        });
    }
}
