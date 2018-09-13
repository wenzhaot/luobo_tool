package com.feng.car.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.feng.car.R;
import com.feng.car.adapter.VideoCacheAdapter;
import com.feng.car.adapter.VideoCacheAdapter.DeleteListener;
import com.feng.car.adapter.VideoCacheAdapter.RefreshListener;
import com.feng.car.databinding.ActivityBlacklistBinding;
import com.feng.car.entity.download.VideoDownloadInfo;
import com.feng.car.video.download.VideoDownloadManager;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VideoCacheActivity extends BaseActivity<ActivityBlacklistBinding> implements RefreshListener, DeleteListener {
    private int color_80_ffffff;
    private int color_ffffff;
    private VideoCacheAdapter mAdapter;
    public boolean mCanExcute = true;
    private List<VideoDownloadInfo> mList = new ArrayList();

    public int setBaseContentView() {
        return R.layout.activity_blacklist;
    }

    public void initView() {
        VideoDownloadManager.newInstance().checkFileExists();
        this.color_80_ffffff = this.mResources.getColor(R.color.color_80_ffffff);
        this.color_ffffff = this.mResources.getColor(R.color.color_ffffff);
        initNormalTitleBar((int) R.string.video_cache);
        initTitleBarRightText(R.string.edit, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (VideoCacheActivity.this.mCanExcute && VideoCacheActivity.this.mAdapter != null) {
                    boolean z;
                    boolean flag = VideoCacheActivity.this.mAdapter.isEdit();
                    VideoCacheAdapter access$000 = VideoCacheActivity.this.mAdapter;
                    if (flag) {
                        z = false;
                    } else {
                        z = true;
                    }
                    access$000.setIsEdit(z);
                    VideoCacheActivity.this.mAdapter.notifyDataSetChanged();
                    if (flag) {
                        ((ActivityBlacklistBinding) VideoCacheActivity.this.mBaseBinding).deleteLine.setVisibility(8);
                        VideoCacheActivity.this.mRootBinding.titleLine.tvRightText.setText(R.string.edit);
                        VideoCacheActivity.this.mAdapter.unSelectAll();
                        return;
                    }
                    VideoCacheActivity.this.mRootBinding.titleLine.tvRightText.setText(R.string.finish);
                    ((ActivityBlacklistBinding) VideoCacheActivity.this.mBaseBinding).deleteLine.setVisibility(0);
                }
            }
        });
        ((ActivityBlacklistBinding) this.mBaseBinding).selectAllImage.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!VideoCacheActivity.this.mAdapter.isSelectAll()) {
                        VideoCacheActivity.this.mAdapter.selectAll();
                    }
                } else if (VideoCacheActivity.this.mAdapter.isSelectAll()) {
                    VideoCacheActivity.this.mAdapter.unSelectAll();
                    VideoCacheActivity.this.mAdapter.notifyDataSetChanged();
                }
                VideoCacheActivity.this.changeDeleteText();
            }
        });
        ((ActivityBlacklistBinding) this.mBaseBinding).deleteText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (VideoCacheActivity.this.mCanExcute) {
                    boolean flag = false;
                    Map<Integer, Integer> map = VideoCacheActivity.this.mAdapter.getDeleteMap();
                    if (map.size() > 0) {
                        for (VideoDownloadInfo info : VideoCacheActivity.this.mList) {
                            if (map.containsKey(Integer.valueOf(info.mediaid))) {
                                if (info.status == 3) {
                                    flag = true;
                                }
                                VideoDownloadManager.newInstance().deleteDownloadInfo(info);
                            }
                        }
                    }
                    ((ActivityBlacklistBinding) VideoCacheActivity.this.mBaseBinding).deleteLine.setVisibility(8);
                    VideoCacheActivity.this.mRootBinding.titleLine.tvRightText.setText(R.string.edit);
                    VideoCacheActivity.this.mAdapter.setIsEdit(false);
                    if (flag) {
                        VideoDownloadManager.newInstance().startNextPendingTask(false);
                    }
                    VideoCacheActivity.this.initData();
                    VideoCacheActivity.this.mCanExcute = true;
                }
            }
        });
    }

    public void onDeleteSelect() {
        changeDeleteText();
    }

    private void changeDeleteText() {
        Map<Integer, Integer> map = this.mAdapter.getDeleteMap();
        if (map.size() == 0) {
            ((ActivityBlacklistBinding) this.mBaseBinding).deleteText.setBackgroundResource(R.color.color_d1d1d1);
            ((ActivityBlacklistBinding) this.mBaseBinding).deleteText.setTextColor(this.color_80_ffffff);
            ((ActivityBlacklistBinding) this.mBaseBinding).selectAllImage.setChecked(false);
            ((ActivityBlacklistBinding) this.mBaseBinding).deleteText.setText("删除（0）");
            return;
        }
        ((ActivityBlacklistBinding) this.mBaseBinding).deleteText.setBackgroundResource(R.color.color_e12c2c);
        ((ActivityBlacklistBinding) this.mBaseBinding).deleteText.setTextColor(this.color_ffffff);
        ((ActivityBlacklistBinding) this.mBaseBinding).deleteText.setText("删除（" + map.size() + "）");
        if (map.size() == this.mList.size()) {
            this.mAdapter.setselectAll();
            ((ActivityBlacklistBinding) this.mBaseBinding).selectAllImage.setChecked(true);
            return;
        }
        this.mAdapter.setUnselectAll();
        ((ActivityBlacklistBinding) this.mBaseBinding).selectAllImage.setChecked(false);
    }

    protected void onResume() {
        super.onResume();
        initData();
    }

    public void finish() {
        super.finish();
        VideoDownloadManager.newInstance().allTaskSetGlobalListener();
    }

    public void onRefresh() {
        initData();
    }

    private void initData() {
        List<VideoDownloadInfo> allList = VideoDownloadManager.newInstance().getAllDownloadInfoList();
        List<VideoDownloadInfo> downloadList = new ArrayList();
        List<VideoDownloadInfo> completeList = new ArrayList();
        for (VideoDownloadInfo info : allList) {
            if (info.status == -3) {
                completeList.add(info);
            } else {
                downloadList.add(info);
            }
        }
        List<VideoDownloadInfo> list = new ArrayList();
        list.addAll(downloadList);
        list.addAll(completeList);
        if (list.size() == 0) {
            showEmptyView((int) R.string.none, (int) R.drawable.no_video_cache);
            this.mRootBinding.titleLine.tvRightText.setVisibility(8);
            return;
        }
        this.mList.clear();
        this.mList.addAll(list);
        if (this.mAdapter == null) {
            this.mAdapter = new VideoCacheAdapter(this, this.mList);
            this.mAdapter.setRefreshListener(this);
            this.mAdapter.setDeleteListener(this);
            LRecyclerViewAdapter adapter = new LRecyclerViewAdapter(this.mAdapter);
            ((ActivityBlacklistBinding) this.mBaseBinding).recyclerview.setLayoutManager(new LinearLayoutManager(this));
            ((ActivityBlacklistBinding) this.mBaseBinding).recyclerview.setAdapter(adapter);
            ((ActivityBlacklistBinding) this.mBaseBinding).recyclerview.setPullRefreshEnabled(false);
        } else {
            this.mAdapter.notifyDataSetChanged();
        }
        this.mRootBinding.titleLine.tvRightText.setVisibility(0);
    }

    public void refreshData() {
        initData();
    }
}
