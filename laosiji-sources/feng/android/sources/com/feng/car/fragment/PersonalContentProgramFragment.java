package com.feng.car.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.Window;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.PopularProgramListActivity;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.adapter.ProgramListAdapter;
import com.feng.car.adapter.ProgramListAdapter.HotShowFollowListener;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.databinding.DialogOpenNotifyBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.hotshow.HotShowInfo;
import com.feng.car.entity.hotshow.HotShowInfoList;
import com.feng.car.event.CircleFragmentRefreshFinishEvent;
import com.feng.car.event.ProgramFollowEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.UmengConstans;
import com.feng.car.view.CommonDialog;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView.LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.common.a;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

public class PersonalContentProgramFragment extends BaseFragment<CommonRecyclerviewBinding> implements HotShowFollowListener {
    private Dialog mCommonDialog;
    private int mHotShowid = 0;
    private boolean mIsSetNotification = false;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private HotShowInfoList mList = new HotShowInfoList();
    private DialogOpenNotifyBinding mOpenNotifyBinding;
    private ProgramListAdapter mProgramAdapter;
    private int mUserId;

    protected int setLayoutId() {
        return 2130903204;
    }

    public static PersonalContentProgramFragment newInstance(int userID) {
        Bundle arg = new Bundle();
        arg.putInt("userid", userID);
        PersonalContentProgramFragment fragment = new PersonalContentProgramFragment();
        fragment.setArguments(arg);
        return fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mUserId = getArguments().getInt("userid");
    }

    protected void initView() {
        this.mProgramAdapter = new ProgramListAdapter(this.mActivity, this.mList.getHotShowInfoList());
        this.mProgramAdapter.setHotShowFollowListener(this);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mProgramAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setPullRefreshEnabled(false);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLayoutManager(new LinearLayoutManager(this.mActivity));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) PersonalContentProgramFragment.this.mBind).recyclerview, State.Normal);
                PersonalContentProgramFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                PersonalContentProgramFragment.this.getData();
            }
        });
        this.mProgramAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                HotShowInfo info = PersonalContentProgramFragment.this.mList.get(position);
                Intent intent = new Intent(PersonalContentProgramFragment.this.mActivity, PopularProgramListActivity.class);
                intent.putExtra(HttpConstant.HOT_SHOW_ID, info.id);
                intent.putExtra(HttpConstant.HOT_SHOW_NAME, info.name);
                PersonalContentProgramFragment.this.startActivity(intent);
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshing(true);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLScrollListener(new LScrollListener() {
            public void onScrollUp() {
                EventBus.getDefault().post(new RecyclerviewDirectionEvent(true));
            }

            public void onScrollDown() {
                EventBus.getDefault().post(new RecyclerviewDirectionEvent(false));
            }

            public void onScrolled(int distanceX, int distanceY) {
            }

            public void onScrollStateChanged(int state) {
            }
        });
    }

    protected void onFragmentFirstVisible() {
        if (this.mBind != null) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.setPullRefreshEnabled(true);
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
        }
    }

    public void getData() {
        Map<String, Object> map = new HashMap();
        map.put("userid", String.valueOf(this.mUserId));
        FengApplication.getInstance().httpRequest(HttpConstant.SNS_HOTSHOWLIST, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ((CommonRecyclerviewBinding) PersonalContentProgramFragment.this.mBind).recyclerview.refreshComplete();
                if (PersonalContentProgramFragment.this.mList.size() <= 0) {
                    PersonalContentProgramFragment.this.showEmptyViewFailed();
                } else {
                    ((BaseActivity) PersonalContentProgramFragment.this.mActivity).showSecondTypeToast(2131231273);
                }
            }

            public void onStart() {
            }

            public void onFinish() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) PersonalContentProgramFragment.this.mBind).recyclerview, State.Normal);
                ((CommonRecyclerviewBinding) PersonalContentProgramFragment.this.mBind).recyclerview.refreshComplete();
                EventBus.getDefault().post(new CircleFragmentRefreshFinishEvent());
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (PersonalContentProgramFragment.this.mList.size() <= 0) {
                    PersonalContentProgramFragment.this.showEmptyViewFailed();
                } else {
                    ((BaseActivity) PersonalContentProgramFragment.this.mActivity).showSecondTypeToast(2131231273);
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    int code = jsonObject.getInt("code");
                    if (code == 1) {
                        PersonalContentProgramFragment.this.hideEmptyView();
                        ((CommonRecyclerviewBinding) PersonalContentProgramFragment.this.mBind).recyclerview.setPullRefreshEnabled(false);
                        JSONObject bodyJson = jsonObject.getJSONObject("body");
                        BaseListModel<HotShowInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(HotShowInfo.class, bodyJson);
                        List<HotShowInfo> list = baseListModel.list;
                        if (list.size() > 0) {
                            PersonalContentProgramFragment.this.mList.clear();
                            PersonalContentProgramFragment.this.mList.addAll(list);
                            PersonalContentProgramFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                            return;
                        }
                        PersonalContentProgramFragment.this.showEmptyView();
                    } else if (PersonalContentProgramFragment.this.mList.size() <= 0) {
                        PersonalContentProgramFragment.this.showEmptyViewFailed();
                    } else {
                        FengApplication.getInstance().checkCode(HttpConstant.SNS_HOTSHOWLIST, code);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setRemind(final int hotshowid) {
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.HOT_SHOW_ID, String.valueOf(hotshowid));
        map.put("type", String.valueOf(0));
        FengApplication.getInstance().httpRequest(HttpConstant.SET_REMIND, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ((BaseActivity) PersonalContentProgramFragment.this.mActivity).showSecondTypeToast(2131231273);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ((BaseActivity) PersonalContentProgramFragment.this.mActivity).showSecondTypeToast(2131231273);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code == 1) {
                        MobclickAgent.onEvent(PersonalContentProgramFragment.this.mActivity, UmengConstans.PROGRAM_REMIND);
                        ((BaseActivity) PersonalContentProgramFragment.this.mActivity).showFirstTypeToast(2131231402);
                        for (HotShowInfo info : PersonalContentProgramFragment.this.mList.getHotShowInfoList()) {
                            if (info.id == hotshowid) {
                                info.isremind.set(1);
                                return;
                            }
                        }
                        return;
                    }
                    FengApplication.getInstance().checkCode(HttpConstant.POPULAR_PROGRAM_DETAIL_LIST, code);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ProgramFollowEvent event) {
        for (HotShowInfo info : this.mList.getHotShowInfoList()) {
            if (info.id == event.id) {
                this.mHotShowid = info.id;
                info.isfollow.set(event.isFollow);
                info.isremind.set(event.isRemind);
                this.mProgramAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    public void onFollowChnage(int id, int isfollow, int isRemind) {
        if (isfollow != 1) {
            for (HotShowInfo info : this.mList.getHotShowInfoList()) {
                if (info.id == id) {
                    info.isremind.set(0);
                    return;
                }
            }
        } else if (isRemind != 1) {
            this.mHotShowid = id;
            showRemindDialog();
        }
    }

    private void showRemindDialog() {
        if (this.mOpenNotifyBinding == null) {
            this.mOpenNotifyBinding = DialogOpenNotifyBinding.inflate(this.mInflater);
            this.mOpenNotifyBinding.ivClose.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    PersonalContentProgramFragment.this.mCommonDialog.dismiss();
                }
            });
            this.mOpenNotifyBinding.openText.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    PersonalContentProgramFragment.this.openRemind();
                    PersonalContentProgramFragment.this.mCommonDialog.dismiss();
                }
            });
            this.mOpenNotifyBinding.cancelText.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    PersonalContentProgramFragment.this.mCommonDialog.dismiss();
                }
            });
        }
        if (this.mCommonDialog == null) {
            this.mCommonDialog = new Dialog(this.mActivity, 2131361986);
            this.mCommonDialog.setCanceledOnTouchOutside(true);
            this.mCommonDialog.setCancelable(true);
            Window window = this.mCommonDialog.getWindow();
            window.setGravity(17);
            window.setContentView(this.mOpenNotifyBinding.getRoot());
            window.setLayout(-1, -2);
        }
        this.mCommonDialog.show();
    }

    public void onResume() {
        super.onResume();
        if (this.mIsSetNotification) {
            this.mIsSetNotification = false;
            openRemind();
        }
    }

    private void openRemind() {
        if (FengUtil.isNotificationAuthorityEnabled(this.mActivity)) {
            setRemind(this.mHotShowid);
            return;
        }
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity(getString(2131231401), false));
        CommonDialog.showCommonDialog(this.mActivity, getString(2131231403), "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (position == 0) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.addFlags(CommonNetImpl.FLAG_AUTH);
                    intent.setData(Uri.fromParts(a.c, PersonalContentProgramFragment.this.mActivity.getPackageName(), null));
                    PersonalContentProgramFragment.this.startActivity(intent);
                    PersonalContentProgramFragment.this.mIsSetNotification = true;
                }
            }
        }, true);
    }

    private void showEmptyView() {
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setVisibility(8);
        ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(0);
        ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyImage(2130838388);
    }

    private void hideEmptyView() {
        ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(8);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setVisibility(0);
    }

    private void showEmptyViewFailed() {
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setVisibility(8);
        ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(0);
        ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyText(2131231188);
        ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyImage(2130838112);
        ((CommonRecyclerviewBinding) this.mBind).emptyView.setButtonListener(2131231470, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ((CommonRecyclerviewBinding) PersonalContentProgramFragment.this.mBind).recyclerview.forceToRefresh();
            }
        });
    }
}
