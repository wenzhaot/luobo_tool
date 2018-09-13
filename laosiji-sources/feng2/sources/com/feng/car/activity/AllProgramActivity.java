package com.feng.car.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.adapter.ProgramGroupAdapter;
import com.feng.car.adapter.ProgramListAdapter;
import com.feng.car.adapter.ProgramListAdapter.HotShowFollowListener;
import com.feng.car.databinding.ActivityAllprogramBinding;
import com.feng.car.databinding.DialogOpenNotifyBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.hotshow.HotShowInfo;
import com.feng.car.entity.hotshow.HotShowInfoList;
import com.feng.car.event.MessageCountRefreshEvent;
import com.feng.car.event.ProgramFollowEvent;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.MessageCountManager;
import com.feng.car.view.CommonDialog;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import com.umeng.analytics.MobclickAgent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class AllProgramActivity extends BaseActivity<ActivityAllprogramBinding> implements HotShowFollowListener {
    private Dialog mCommonDialog;
    private int mCurrentPage = 1;
    private int mCurrentProgramId = 0;
    private ProgramGroupAdapter mGroupAdapter;
    private List<HotShowInfo> mGroupList = new ArrayList();
    private int mHotShowid = 0;
    private boolean mIsSetNotification = false;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private DialogOpenNotifyBinding mOpenNotifyBinding;
    private ProgramListAdapter mProgramAdapter;
    private HotShowInfoList mProgramList = new HotShowInfoList();
    private int mTotalPage = 1;

    public int setBaseContentView() {
        return R.layout.activity_allprogram;
    }

    public void initView() {
        hideDefaultTitleBar();
        ((ActivityAllprogramBinding) this.mBaseBinding).ivMessage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                AllProgramActivity.this.startActivity(new Intent(AllProgramActivity.this, MessageActivity.class));
            }
        });
        ((ActivityAllprogramBinding) this.mBaseBinding).back.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                AllProgramActivity.this.finish();
            }
        });
        this.mCurrentProgramId = getIntent().getIntExtra("id", 0);
        this.mProgramAdapter = new ProgramListAdapter(this, this.mProgramList.getHotShowInfoList());
        this.mProgramAdapter.setHotShowFollowListener(this);
        ((ActivityAllprogramBinding) this.mBaseBinding).programRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mProgramAdapter);
        ((ActivityAllprogramBinding) this.mBaseBinding).programRecyclerView.setAdapter(this.mLRecyclerViewAdapter);
        ((ActivityAllprogramBinding) this.mBaseBinding).programRecyclerView.setPullRefreshEnabled(false);
        ((ActivityAllprogramBinding) this.mBaseBinding).programRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((ActivityAllprogramBinding) AllProgramActivity.this.mBaseBinding).programRecyclerView) != State.Loading) {
                    if (AllProgramActivity.this.mCurrentPage <= AllProgramActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(AllProgramActivity.this, ((ActivityAllprogramBinding) AllProgramActivity.this.mBaseBinding).programRecyclerView, 20, State.Loading, null);
                        AllProgramActivity.this.getProgramData();
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(AllProgramActivity.this, ((ActivityAllprogramBinding) AllProgramActivity.this.mBaseBinding).programRecyclerView, 20, State.TheEnd, null);
                }
            }
        });
        this.mProgramAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                HotShowInfo info = AllProgramActivity.this.mProgramList.get(position);
                Intent intent = new Intent(AllProgramActivity.this, PopularProgramListActivity.class);
                intent.putExtra("hotshowid", info.id);
                intent.putExtra("hot_show_name", info.name);
                AllProgramActivity.this.startActivity(intent);
            }
        });
        this.mGroupAdapter = new ProgramGroupAdapter(this, this.mGroupList);
        ((ActivityAllprogramBinding) this.mBaseBinding).groupRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ((ActivityAllprogramBinding) this.mBaseBinding).groupRecyclerView.setAdapter(this.mGroupAdapter);
        this.mGroupAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                AllProgramActivity.this.mCurrentProgramId = ((HotShowInfo) AllProgramActivity.this.mGroupList.get(position)).id;
                AllProgramActivity.this.mGroupAdapter.setCurrentId(AllProgramActivity.this.mCurrentProgramId);
                AllProgramActivity.this.mGroupAdapter.notifyDataSetChanged();
                AllProgramActivity.this.mCurrentPage = 1;
                AllProgramActivity.this.getProgramData();
            }
        });
        ((ActivityAllprogramBinding) this.mBaseBinding).groupReloadButton.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                AllProgramActivity.this.getGroupData();
            }
        });
        ((ActivityAllprogramBinding) this.mBaseBinding).programReloadButton.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                AllProgramActivity.this.getProgramData();
            }
        });
        getGroupData();
    }

    protected void onResume() {
        super.onResume();
        MessageCountManager.getInstance().requestMessageCount();
        if (this.mIsSetNotification) {
            this.mIsSetNotification = false;
            openRemind();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageCountRefreshEvent event) {
        if (MessageCountManager.getInstance().getMessagePageTotalCount() <= 0 || !FengApplication.getInstance().isLoginUser()) {
            ((ActivityAllprogramBinding) this.mBaseBinding).tvMessageCommentNum.setVisibility(8);
            return;
        }
        ((ActivityAllprogramBinding) this.mBaseBinding).tvMessageCommentNum.setText(FengUtil.numberFormat99(MessageCountManager.getInstance().getMessagePageTotalCount()));
        ((ActivityAllprogramBinding) this.mBaseBinding).tvMessageCommentNum.setVisibility(0);
    }

    private void getGroupData() {
        FengApplication.getInstance().httpRequest("snshotshow/categorylist/", new HashMap(), new OkHttpResponseCallback() {
            public void onNetworkError() {
                AllProgramActivity.this.showGroupEmptyLine();
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                AllProgramActivity.this.showGroupEmptyLine();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        JSONObject jsonCategory = jsonResult.getJSONObject("body").getJSONObject("category");
                        BaseListModel<HotShowInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(HotShowInfo.class, jsonCategory);
                        if (baseListModel.list.size() > 0) {
                            AllProgramActivity.this.mGroupList.clear();
                            AllProgramActivity.this.mGroupList.addAll(baseListModel.list);
                            if (AllProgramActivity.this.mCurrentProgramId == 0) {
                                AllProgramActivity.this.mCurrentProgramId = ((HotShowInfo) AllProgramActivity.this.mGroupList.get(0)).id;
                            }
                            AllProgramActivity.this.mGroupAdapter.setCurrentId(AllProgramActivity.this.mCurrentProgramId);
                            AllProgramActivity.this.mGroupAdapter.notifyDataSetChanged();
                            AllProgramActivity.this.hideGroupEmptyLine();
                            AllProgramActivity.this.getProgramData();
                            return;
                        }
                        AllProgramActivity.this.showGroupEmptyLine();
                        return;
                    }
                    AllProgramActivity.this.showGroupEmptyLine();
                } catch (JSONException e) {
                    FengApplication.getInstance().upLoadTryCatchLog("snshotshow/categorylist/", content, e);
                    AllProgramActivity.this.showGroupEmptyLine();
                }
            }
        });
    }

    private void getProgramData() {
        Map<String, Object> map = new HashMap();
        map.put("categoryid", String.valueOf(this.mCurrentProgramId));
        map.put("page", String.valueOf(this.mCurrentPage));
        map.put("sorttype", String.valueOf(1));
        FengApplication.getInstance().httpRequest("snshotshow/listbycategoryid/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                AllProgramActivity.this.showProgramEmptyLine();
            }

            public void onStart() {
            }

            public void onFinish() {
                ((ActivityAllprogramBinding) AllProgramActivity.this.mBaseBinding).programRecyclerView.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ActivityAllprogramBinding) AllProgramActivity.this.mBaseBinding).programRecyclerView, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (AllProgramActivity.this.mProgramList.size() == 0) {
                    AllProgramActivity.this.showProgramEmptyLine();
                } else {
                    AllProgramActivity.this.showSecondTypeToast((int) R.string.load_faile);
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        JSONObject jsonHotshow = jsonResult.getJSONObject("body").getJSONObject("hotshow");
                        BaseListModel<HotShowInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(HotShowInfo.class, jsonHotshow);
                        AllProgramActivity.this.mTotalPage = baseListModel.pagecount;
                        if (AllProgramActivity.this.mCurrentPage == 1) {
                            AllProgramActivity.this.mProgramList.clear();
                        }
                        if (baseListModel.list.size() > 0) {
                            AllProgramActivity.this.mProgramList.addAll(baseListModel.list);
                        }
                        AllProgramActivity.this.mProgramAdapter.notifyDataSetChanged();
                        if (((ActivityAllprogramBinding) AllProgramActivity.this.mBaseBinding).programEmptyLine.isShown()) {
                            AllProgramActivity.this.hideProgramEmptyLine();
                        }
                        if (AllProgramActivity.this.mCurrentPage == 1) {
                            ((ActivityAllprogramBinding) AllProgramActivity.this.mBaseBinding).programRecyclerView.scrollToPosition(0);
                        }
                        AllProgramActivity.this.mCurrentPage = AllProgramActivity.this.mCurrentPage + 1;
                    } else if (AllProgramActivity.this.mProgramList.size() == 0) {
                        AllProgramActivity.this.showProgramEmptyLine();
                    } else {
                        AllProgramActivity.this.showSecondTypeToast((int) R.string.load_faile);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (AllProgramActivity.this.mProgramList.size() == 0) {
                        AllProgramActivity.this.showProgramEmptyLine();
                    } else {
                        AllProgramActivity.this.showSecondTypeToast((int) R.string.load_faile);
                    }
                    FengApplication.getInstance().upLoadTryCatchLog("snshotshow/listbycategoryid/", content, e);
                }
            }
        });
    }

    private void showGroupEmptyLine() {
        ((ActivityAllprogramBinding) this.mBaseBinding).groupEmptyLine.setVisibility(0);
    }

    private void hideGroupEmptyLine() {
        ((ActivityAllprogramBinding) this.mBaseBinding).groupEmptyLine.setVisibility(8);
    }

    private void showProgramEmptyLine() {
        ((ActivityAllprogramBinding) this.mBaseBinding).programEmptyLine.setVisibility(0);
    }

    private void hideProgramEmptyLine() {
        ((ActivityAllprogramBinding) this.mBaseBinding).programEmptyLine.setVisibility(8);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ProgramFollowEvent event) {
        for (HotShowInfo info : this.mProgramList.getHotShowInfoList()) {
            if (info.id == event.id) {
                this.mHotShowid = info.id;
                info.isfollow.set(event.isFollow);
                info.isremind.set(event.isRemind);
                this.mProgramAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    private void showRemindDialog() {
        if (this.mOpenNotifyBinding == null) {
            this.mOpenNotifyBinding = DialogOpenNotifyBinding.inflate(LayoutInflater.from(this));
            this.mOpenNotifyBinding.ivClose.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    AllProgramActivity.this.mCommonDialog.dismiss();
                }
            });
            this.mOpenNotifyBinding.openText.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    AllProgramActivity.this.openRemind();
                    AllProgramActivity.this.mCommonDialog.dismiss();
                }
            });
            this.mOpenNotifyBinding.cancelText.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    AllProgramActivity.this.mCommonDialog.dismiss();
                }
            });
        }
        if (this.mCommonDialog == null) {
            this.mCommonDialog = new Dialog(this, R.style.ArticleShareDialog);
            this.mCommonDialog.setCanceledOnTouchOutside(true);
            this.mCommonDialog.setCancelable(true);
            Window window = this.mCommonDialog.getWindow();
            window.setGravity(17);
            window.setContentView(this.mOpenNotifyBinding.getRoot());
            window.setLayout(-1, -2);
        }
        this.mCommonDialog.show();
    }

    public void finish() {
        super.finish();
        if (this.mCommonDialog != null && this.mCommonDialog.isShowing()) {
            this.mCommonDialog.dismiss();
        }
    }

    private void openRemind() {
        if (FengUtil.isNotificationAuthorityEnabled(this)) {
            setRemind(this.mHotShowid);
            return;
        }
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity(getString(R.string.pop_program_open_remind_confirm), false));
        CommonDialog.showCommonDialog(this, getString(R.string.pop_program_open_remind_tips), "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (position == 0) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.addFlags(268435456);
                    intent.setData(Uri.fromParts("package", AllProgramActivity.this.getPackageName(), null));
                    AllProgramActivity.this.startActivity(intent);
                    AllProgramActivity.this.mIsSetNotification = true;
                }
            }
        }, true);
    }

    private void setRemind(final int hotshowid) {
        Map<String, Object> map = new HashMap();
        map.put("hotshowid", String.valueOf(hotshowid));
        map.put("type", String.valueOf(0));
        FengApplication.getInstance().httpRequest("snshotshow/setremind/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                AllProgramActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                AllProgramActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code == 1) {
                        MobclickAgent.onEvent(AllProgramActivity.this, "program_remind");
                        AllProgramActivity.this.showFirstTypeToast((int) R.string.pop_program_open_remind_succeed_tips);
                        for (HotShowInfo info : AllProgramActivity.this.mProgramList.getHotShowInfoList()) {
                            if (info.id == hotshowid) {
                                info.isremind.set(1);
                                return;
                            }
                        }
                        return;
                    }
                    FengApplication.getInstance().checkCode("snshotshow/getlistbyid/", code);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onFollowChnage(int id, int isfollow, int isRemind) {
        if (isfollow != 1) {
            for (HotShowInfo info : this.mProgramList.getHotShowInfoList()) {
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
}
