package com.feng.car.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.VideoFinalPageActivity;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.VideoCacheItemBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.download.VideoDownloadInfo;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.ImageUtil;
import com.feng.car.utils.VideoManager;
import com.feng.car.video.download.VideoDownloadManager;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.CommonDialog;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoCacheAdapter extends MvvmBaseAdapter<VideoDownloadInfo, VideoCacheItemBinding> {
    private int color_61_000000 = this.mContext.getResources().getColor(R.color.color_61_000000);
    private int color_e12c2c = this.mContext.getResources().getColor(R.color.color_e12c2c);
    private boolean isSelectAll = false;
    private long lastClickTime = 0;
    private int m124 = this.mContext.getResources().getDimensionPixelSize(R.dimen.default_124PX);
    private int m220 = this.mContext.getResources().getDimensionPixelSize(R.dimen.default_220PX);
    private DeleteListener mDeleteListener;
    private Map<Integer, Integer> mDeleteMap = new HashMap();
    private boolean mIsEdit = false;
    private RefreshListener mRefreshListener;

    public interface RefreshListener {
        void onRefresh();
    }

    public interface DeleteListener {
        void onDeleteSelect();
    }

    public boolean isEdit() {
        return this.mIsEdit;
    }

    public void setIsEdit(boolean mIsEdit) {
        this.mIsEdit = mIsEdit;
        this.mDeleteMap.clear();
    }

    public Map<Integer, Integer> getDeleteMap() {
        return this.mDeleteMap;
    }

    public VideoCacheAdapter(Context context, List<VideoDownloadInfo> list) {
        super(context, list);
    }

    public VideoCacheItemBinding getBinding(ViewGroup parent, int viewType) {
        return VideoCacheItemBinding.inflate(LayoutInflater.from(this.mContext), parent, false);
    }

    public void dataBindingTo(VideoCacheItemBinding videoCacheItemBinding, VideoDownloadInfo videoDownloadInfo) {
    }

    public void onBaseBindViewHolder(final MvvmViewHolder<VideoCacheItemBinding> holder, int position) {
        final VideoDownloadInfo videoDownloadInfo = (VideoDownloadInfo) this.mList.get(position);
        BaseDownloadTask downloadTaskTask = VideoDownloadManager.newInstance().getCurrentBaseDownloadTask();
        ((VideoCacheItemBinding) holder.binding).title.setText(videoDownloadInfo.snsInfo.getFeedTitleOrDes());
        String imageUrl = FengUtil.getFixedSizeUrl(videoDownloadInfo.mediaInfo.image, this.m220, this.m124);
        ((VideoCacheItemBinding) holder.binding).image.setAutoImageURI(Uri.parse(imageUrl));
        if (this.mIsEdit) {
            ((VideoCacheItemBinding) holder.binding).selectImage.setVisibility(0);
            if (this.mDeleteMap.containsKey(Integer.valueOf(videoDownloadInfo.mediaid))) {
                ((VideoCacheItemBinding) holder.binding).selectImage.setImageResource(R.drawable.icon_videocache_select);
            } else {
                ((VideoCacheItemBinding) holder.binding).selectImage.setImageResource(R.drawable.icon_videocache_unselect);
            }
        } else {
            ((VideoCacheItemBinding) holder.binding).selectImage.setVisibility(8);
        }
        if (videoDownloadInfo.status == 3) {
            ((VideoCacheItemBinding) holder.binding).detailLine.setVisibility(8);
            updateUIByStatus(holder, videoDownloadInfo);
            if (downloadTaskTask != null && videoDownloadInfo.url.equals(downloadTaskTask.getUrl())) {
                downloadTaskTask.setListener(new FileDownloadLargeFileListener() {
                    protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        if (videoDownloadInfo.downloadId == 0) {
                            videoDownloadInfo.downloadId = task.getId();
                        }
                        if (videoDownloadInfo.status != 1) {
                            videoDownloadInfo.status = 1;
                            VideoDownloadManager.newInstance().updateDownloadInfo(videoDownloadInfo);
                        }
                        VideoCacheAdapter.this.updatePendingUI(holder, videoDownloadInfo);
                    }

                    protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        VideoDownloadManager.newInstance().setCurrentBaseDownloadTask(task);
                        if (videoDownloadInfo.downloadId == 0) {
                            videoDownloadInfo.downloadId = task.getId();
                        }
                        if (videoDownloadInfo.status != 3) {
                            videoDownloadInfo.status = 3;
                            videoDownloadInfo.tips = "";
                            VideoDownloadManager.newInstance().updateDownloadInfo(videoDownloadInfo);
                        }
                        if (totalBytes > 0) {
                            VideoCacheAdapter.this.updateProgressUI(holder, (soFarBytes * 100) / totalBytes);
                            videoDownloadInfo.progress = (soFarBytes * 100) / totalBytes;
                            return;
                        }
                        VideoCacheAdapter.this.updateProgressUI(holder, (soFarBytes * 100) / videoDownloadInfo.size);
                        videoDownloadInfo.progress = (soFarBytes * 100) / videoDownloadInfo.size;
                    }

                    protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        if (videoDownloadInfo.downloadId == 0) {
                            videoDownloadInfo.downloadId = task.getId();
                        }
                        if (videoDownloadInfo.status != -2) {
                            videoDownloadInfo.status = -2;
                            VideoDownloadManager.newInstance().updateDownloadInfo(videoDownloadInfo);
                        }
                        VideoCacheAdapter.this.updatePausedUI(holder, videoDownloadInfo);
                    }

                    protected void completed(BaseDownloadTask task) {
                        if (videoDownloadInfo.downloadId == 0) {
                            videoDownloadInfo.downloadId = task.getId();
                        }
                        videoDownloadInfo.status = -3;
                        VideoDownloadManager.newInstance().updateDownloadInfo(videoDownloadInfo);
                        task.setListener(null);
                        if (FengUtil.isWifiConnectivity(FengApplication.getInstance())) {
                            VideoDownloadManager.newInstance().startNextPendingTask(false);
                        }
                        if (VideoCacheAdapter.this.mRefreshListener != null) {
                            VideoCacheAdapter.this.mRefreshListener.onRefresh();
                        }
                    }

                    protected void error(BaseDownloadTask task, Throwable e) {
                        if (videoDownloadInfo.downloadId == 0) {
                            videoDownloadInfo.downloadId = task.getId();
                        }
                        if (videoDownloadInfo.status != -1) {
                            videoDownloadInfo.status = -1;
                            VideoDownloadManager.newInstance().updateDownloadInfo(videoDownloadInfo);
                        }
                        VideoCacheAdapter.this.updateErrorUI(holder);
                    }

                    protected void warn(BaseDownloadTask task) {
                        if (videoDownloadInfo.downloadId == 0) {
                            videoDownloadInfo.downloadId = task.getId();
                        }
                        if (videoDownloadInfo.status != -4) {
                            videoDownloadInfo.status = -4;
                            VideoDownloadManager.newInstance().updateDownloadInfo(videoDownloadInfo);
                        }
                    }
                });
            }
        } else if (videoDownloadInfo.status == -3) {
            ((VideoCacheItemBinding) holder.binding).image.setAutoImageURI(Uri.parse(imageUrl));
            if (videoDownloadInfo.hasWatch) {
                ((VideoCacheItemBinding) holder.binding).pointImage.setVisibility(8);
            } else {
                ((VideoCacheItemBinding) holder.binding).pointImage.setVisibility(0);
            }
            ((VideoCacheItemBinding) holder.binding).tipsText.setVisibility(8);
            ((VideoCacheItemBinding) holder.binding).statusLine.setVisibility(8);
            ((VideoCacheItemBinding) holder.binding).detailLine.setVisibility(0);
            ((VideoCacheItemBinding) holder.binding).userName.setText((CharSequence) videoDownloadInfo.snsInfo.user.name.get());
            StringBuffer sb = new StringBuffer();
            sb.append(FengUtil.formatVideoSize(videoDownloadInfo.size));
            sb.append("/");
            if (!videoDownloadInfo.hasWatch) {
                sb.append("未观看");
            } else if (videoDownloadInfo.hasWatchProgress == 100) {
                sb.append("已看完");
            } else {
                sb.append("观看至");
                sb.append(videoDownloadInfo.hasWatchProgress);
                sb.append("%");
            }
            ((VideoCacheItemBinding) holder.binding).infoText.setText(sb.toString());
        } else {
            updateUIByStatus(holder, videoDownloadInfo);
        }
        ((VideoCacheItemBinding) holder.binding).getRoot().setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                if (!VideoCacheAdapter.this.mIsEdit) {
                    DialogItemEntity item;
                    List<DialogItemEntity> list = new ArrayList();
                    if (videoDownloadInfo.status == -3) {
                        item = new DialogItemEntity("删除缓存", true);
                    } else {
                        item = new DialogItemEntity("删除下载任务", true);
                    }
                    list.add(item);
                    CommonDialog.showCommonDialog(VideoCacheAdapter.this.mContext, "", list, new OnDialogItemClickListener() {
                        public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                            videoDownloadInfo.mediaInfo.path = null;
                            VideoManager.newInstance().updateVideoInfo(videoDownloadInfo.mediaInfo);
                            VideoDownloadManager.newInstance().deleteDownloadInfo(videoDownloadInfo);
                            VideoCacheAdapter.this.mList.remove(videoDownloadInfo);
                            if (VideoCacheAdapter.this.mList.size() == 0) {
                                ((BaseActivity) VideoCacheAdapter.this.mContext).showEmptyView((int) R.string.none, (int) R.drawable.no_video_cache);
                            }
                            VideoCacheAdapter.this.notifyDataSetChanged();
                            ((BaseActivity) VideoCacheAdapter.this.mContext).showFirstTypeToast((int) R.string.delete_myarticle_success);
                        }
                    });
                }
                return false;
            }
        });
        ((VideoCacheItemBinding) holder.binding).getRoot().setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (VideoCacheAdapter.this.mIsEdit) {
                    if (VideoCacheAdapter.this.mDeleteMap.containsKey(Integer.valueOf(videoDownloadInfo.mediaid))) {
                        VideoCacheAdapter.this.mDeleteMap.remove(Integer.valueOf(videoDownloadInfo.mediaid));
                    } else {
                        VideoCacheAdapter.this.mDeleteMap.put(Integer.valueOf(videoDownloadInfo.mediaid), Integer.valueOf(videoDownloadInfo.mediaid));
                    }
                    if (VideoCacheAdapter.this.mDeleteListener != null) {
                        VideoCacheAdapter.this.mDeleteListener.onDeleteSelect();
                    }
                    VideoCacheAdapter.this.notifyDataSetChanged();
                } else if (videoDownloadInfo.status != -3) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - VideoCacheAdapter.this.lastClickTime <= 2000) {
                        VideoCacheAdapter.this.lastClickTime = currentTime;
                        return;
                    }
                    VideoCacheAdapter.this.lastClickTime = currentTime;
                    if (videoDownloadInfo.status == 3) {
                        VideoDownloadManager.newInstance().pauseCurrentBaseDownloadTask();
                        videoDownloadInfo.status = -2;
                        VideoDownloadManager.newInstance().updateDownloadInfo(videoDownloadInfo);
                        VideoDownloadManager.newInstance().pendingCurrentAndStartNext(videoDownloadInfo);
                    } else if (FengUtil.isNetworkAvailable(VideoCacheAdapter.this.mContext)) {
                        VideoDownloadInfo info;
                        VideoDownloadManager.newInstance().pauseCurrentBaseDownloadTask();
                        BaseDownloadTask currentTask = VideoDownloadManager.newInstance().getCurrentBaseDownloadTask();
                        if (currentTask != null) {
                            info = VideoDownloadManager.newInstance().getVideoDownloadInfoByUrl(currentTask.getUrl());
                        } else {
                            info = VideoDownloadManager.newInstance().getVideoDownloadInfoByUrl(VideoDownloadManager.newInstance().getCurrentUrl());
                        }
                        if (info != null && info.status == 3) {
                            info.status = 1;
                            VideoDownloadManager.newInstance().updateDownloadInfo(info);
                        }
                        VideoDownloadManager.newInstance().startCurrentTask(videoDownloadInfo);
                    } else {
                        ((BaseActivity) VideoCacheAdapter.this.mContext).showSecondTypeToast((int) R.string.network_not_connect);
                        return;
                    }
                    if (VideoCacheAdapter.this.mRefreshListener != null) {
                        VideoCacheAdapter.this.mRefreshListener.onRefresh();
                    }
                } else if (new File(videoDownloadInfo.path).exists()) {
                    VideoManager.newInstance().updateVideoInfo(videoDownloadInfo.mediaInfo);
                    Intent intent = new Intent(VideoCacheAdapter.this.mContext, VideoFinalPageActivity.class);
                    intent.putExtra("MEDIA", videoDownloadInfo.mediaInfo.key);
                    intent.putExtra("snsid", videoDownloadInfo.snsInfo.id);
                    intent.putExtra("resourceid", videoDownloadInfo.snsInfo.resourceid);
                    intent.putExtra("resourcetype", videoDownloadInfo.snsInfo.snstype);
                    intent.putExtra("from_key", false);
                    intent.putExtra("from_article_final", 0);
                    VideoCacheAdapter.this.mContext.startActivity(intent);
                    if (!videoDownloadInfo.hasWatch) {
                        videoDownloadInfo.hasWatch = true;
                        VideoDownloadManager.newInstance().updateDownloadInfo(videoDownloadInfo);
                        VideoCacheAdapter.this.notifyDataSetChanged();
                    }
                } else {
                    ((BaseActivity) VideoCacheAdapter.this.mContext).showSecondTypeToast((int) R.string.not_find_video);
                }
            }
        });
    }

    public void updateUIByStatus(MvvmViewHolder<VideoCacheItemBinding> holder, VideoDownloadInfo info) {
        if (info.status == 1) {
            updatePendingUI(holder, info);
        } else if (info.status == -2) {
            updatePausedUI(holder, info);
        } else if (info.status == -1) {
            updateErrorUI(holder);
        } else if (info.status == 3) {
            updateProgressUI(holder, info.progress);
        }
    }

    public void updatePendingUI(MvvmViewHolder<VideoCacheItemBinding> holder, VideoDownloadInfo info) {
        ((VideoCacheItemBinding) holder.binding).pointImage.setVisibility(8);
        ((VideoCacheItemBinding) holder.binding).detailLine.setVisibility(8);
        ((VideoCacheItemBinding) holder.binding).tipsText.setVisibility(0);
        if (StringUtil.isEmpty(info.tips)) {
            ((VideoCacheItemBinding) holder.binding).tipsText.setText("队列中...");
            ((VideoCacheItemBinding) holder.binding).tipsText.setTextColor(this.color_61_000000);
        } else {
            ((VideoCacheItemBinding) holder.binding).tipsText.setText(info.tips);
            ((VideoCacheItemBinding) holder.binding).tipsText.setTextColor(this.color_e12c2c);
        }
        ((VideoCacheItemBinding) holder.binding).statusLine.setVisibility(0);
        ((VideoCacheItemBinding) holder.binding).progress.setVisibility(8);
        ((VideoCacheItemBinding) holder.binding).statusImage.setVisibility(0);
        ((VideoCacheItemBinding) holder.binding).statusImage.setImageResource(R.drawable.icon_cache_pause);
        ((VideoCacheItemBinding) holder.binding).statusText.setText("暂停");
    }

    public void updateProgressUI(MvvmViewHolder<VideoCacheItemBinding> holder, long progress) {
        ((VideoCacheItemBinding) holder.binding).pointImage.setVisibility(8);
        ((VideoCacheItemBinding) holder.binding).detailLine.setVisibility(8);
        ((VideoCacheItemBinding) holder.binding).tipsText.setVisibility(0);
        ((VideoCacheItemBinding) holder.binding).tipsText.setText("下载中...");
        ((VideoCacheItemBinding) holder.binding).tipsText.setTextColor(this.color_61_000000);
        ((VideoCacheItemBinding) holder.binding).statusLine.setVisibility(0);
        ((VideoCacheItemBinding) holder.binding).progress.setVisibility(0);
        ((VideoCacheItemBinding) holder.binding).progress.setPercent((float) progress);
        ((VideoCacheItemBinding) holder.binding).statusImage.setVisibility(8);
        ((VideoCacheItemBinding) holder.binding).statusText.setText(progress + "%");
    }

    public void updatePausedUI(MvvmViewHolder<VideoCacheItemBinding> holder, VideoDownloadInfo info) {
        ((VideoCacheItemBinding) holder.binding).pointImage.setVisibility(8);
        ((VideoCacheItemBinding) holder.binding).detailLine.setVisibility(8);
        ((VideoCacheItemBinding) holder.binding).tipsText.setVisibility(0);
        if (StringUtil.isEmpty(info.tips)) {
            ((VideoCacheItemBinding) holder.binding).tipsText.setText("队列中...");
            ((VideoCacheItemBinding) holder.binding).tipsText.setTextColor(this.color_61_000000);
        } else {
            ((VideoCacheItemBinding) holder.binding).tipsText.setText(info.tips);
            ((VideoCacheItemBinding) holder.binding).tipsText.setTextColor(this.color_e12c2c);
        }
        ((VideoCacheItemBinding) holder.binding).statusLine.setVisibility(0);
        ((VideoCacheItemBinding) holder.binding).progress.setVisibility(8);
        ((VideoCacheItemBinding) holder.binding).statusImage.setVisibility(0);
        ((VideoCacheItemBinding) holder.binding).statusImage.setImageResource(R.drawable.icon_cache_pause);
        ((VideoCacheItemBinding) holder.binding).statusText.setText("暂停");
    }

    public void updateErrorUI(MvvmViewHolder<VideoCacheItemBinding> holder) {
        ((VideoCacheItemBinding) holder.binding).pointImage.setVisibility(8);
        ((VideoCacheItemBinding) holder.binding).detailLine.setVisibility(8);
        ((VideoCacheItemBinding) holder.binding).tipsText.setVisibility(0);
        ((VideoCacheItemBinding) holder.binding).tipsText.setText("下载失败，点击重试");
        ((VideoCacheItemBinding) holder.binding).tipsText.setTextColor(this.color_e12c2c);
        ((VideoCacheItemBinding) holder.binding).statusLine.setVisibility(0);
        ((VideoCacheItemBinding) holder.binding).progress.setVisibility(8);
        ((VideoCacheItemBinding) holder.binding).statusImage.setVisibility(0);
        ((VideoCacheItemBinding) holder.binding).statusImage.setImageResource(R.drawable.icon_cache_retry);
        ((VideoCacheItemBinding) holder.binding).statusText.setText("重新下载");
    }

    public void setRefreshListener(RefreshListener l) {
        this.mRefreshListener = l;
    }

    public void setDeleteListener(DeleteListener mDeleteListener) {
        this.mDeleteListener = mDeleteListener;
    }

    private void downHeadImg(String imageUrl, AutoFrescoDraweeView image) {
        ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imageUrl));
        imageRequestBuilder.setPostprocessor(new BasePostprocessor() {
            public void process(Bitmap bitmap) {
                ImageUtil.doBlur(bitmap, 8, true);
            }
        });
        ImageRequest imageRequest = imageRequestBuilder.setResizeOptions(new ResizeOptions(this.m220, this.m124)).build();
        PipelineDraweeControllerBuilder draweeControllerBuilder = Fresco.newDraweeControllerBuilder();
        draweeControllerBuilder.setOldController(image.getController());
        draweeControllerBuilder.setImageRequest(imageRequest);
        image.setController(draweeControllerBuilder.build());
    }

    public boolean isSelectAll() {
        return this.isSelectAll;
    }

    public void setUnselectAll() {
        this.isSelectAll = false;
    }

    public void setselectAll() {
        this.isSelectAll = true;
    }

    public void selectAll() {
        for (VideoDownloadInfo info : this.mList) {
            this.mDeleteMap.put(Integer.valueOf(info.mediaid), Integer.valueOf(info.mediaid));
        }
        setselectAll();
        notifyDataSetChanged();
    }

    public void unSelectAll() {
        this.mDeleteMap.clear();
        setUnselectAll();
        notifyDataSetChanged();
    }
}
