package com.feng.car.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.feng.car.R;
import com.feng.car.activity.ShowBigImageActivity;
import com.feng.car.activity.WebActivity;
import com.feng.car.databinding.ArticleCommentItemLayoutBinding;
import com.feng.car.databinding.ArticleItemEmptyLayoutBinding;
import com.feng.car.databinding.ArticleItemLayoutBinding;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.ad.AdvertInfo;
import com.feng.car.entity.circle.CircleInfoList;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsPostResources;
import com.feng.car.entity.thread.ArticleInfo;
import com.feng.car.entity.thread.CommentInfo;
import com.feng.car.entity.thread.GratuityRecordInfo;
import com.feng.car.listener.CommentItemListener;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.VideoManager;
import com.feng.car.view.ArticleRecommendView;
import com.feng.car.view.SwipeBackLayout;
import com.feng.car.view.largeimage.LargeImageView$LargeImageClickListener;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ArticleDetailAdapter extends Adapter<ArticleHolder> {
    private final String ARRAYHOLDER = "arrayholder";
    private final int HOT_COMMENT_ARRAY = 1;
    private final int REQUEST_COMMENT = 1;
    private final int REQUEST_PRAISE = 3;
    private final String TABHOLDER = "tabHolder";
    private final String TABPOSITION = "tabPosition";
    private int m24;
    private int m8;
    private AdvertInfo mAdvertInfo;
    private CircleInfoList mCircleList;
    private int mCommentId = 0;
    private int mCommentOrderid = 1;
    private Context mContext;
    private List<ImageInfo> mImageList = new ArrayList();
    private InteractionChangeListener mInteractionChangeListener;
    private boolean mIsHideTabType = false;
    private List<ArticleInfo> mList = new ArrayList();
    private LayoutParams mOutLinkeImageParams;
    public Map<Integer, ArticleRecommendView> mRecommendMap = new ArrayMap();
    private SnsInfo mSnsInfo;
    private String mSoleKey = "";
    private SwipeBackLayout mSwipeBackLayout;
    private int mType = 1;
    private int mVideoMaxHeight;
    private int mVideoMaxWidth;
    private Map<String, Object> map = new HashMap();

    public interface InteractionChangeListener {
        void onArrayChanged(View view);

        void onCommentSelected();

        void onPraiseSelected();
    }

    public class ArticleHolder extends ViewHolder {
        public ArticleHolder(ViewDataBinding binding) {
            super(binding.getRoot());
        }
    }

    public class ArticleCommentHolder extends ArticleHolder {
        public ArticleCommentItemLayoutBinding mBinding;

        public ArticleCommentHolder(ViewDataBinding binding) {
            super(binding);
            this.mBinding = (ArticleCommentItemLayoutBinding) binding;
        }
    }

    public class ArticleContentHolder extends ArticleHolder {
        public ArticleItemLayoutBinding mBinding;

        public ArticleContentHolder(ViewDataBinding binding) {
            super(binding);
            this.mBinding = (ArticleItemLayoutBinding) binding;
        }
    }

    public class ArticleEmptyHolder extends ArticleHolder {
        public ArticleItemEmptyLayoutBinding mBinding;

        public ArticleEmptyHolder(ViewDataBinding binding) {
            super(binding);
            this.mBinding = (ArticleItemEmptyLayoutBinding) binding;
        }
    }

    public void setmCommentId(int commentId) {
        this.mCommentId = commentId;
    }

    public void setmImageList(List<ImageInfo> imageList) {
        this.mImageList = imageList;
    }

    public void setmSnsInfo(SnsInfo mSnsInfo, SwipeBackLayout swipeBackLayout) {
        this.mSnsInfo = mSnsInfo;
        this.mSwipeBackLayout = swipeBackLayout;
    }

    public void setmSnsInfo(SnsInfo mSnsInfo) {
        this.mSnsInfo = mSnsInfo;
    }

    public void setAdvertInfo(AdvertInfo advertInfo) {
        this.mAdvertInfo = advertInfo;
    }

    public void setInteractionChangeListener(InteractionChangeListener mInteractionChangeListener) {
        this.mInteractionChangeListener = mInteractionChangeListener;
    }

    public String getSoleKey() {
        return this.mSoleKey;
    }

    public ArticleContentHolder getTabHolder() {
        return (ArticleContentHolder) this.map.get("tabHolder");
    }

    public int getTabPosition() {
        if (this.map.get("tabPosition") == null) {
            return 0;
        }
        return ((Integer) this.map.get("tabPosition")).intValue();
    }

    private void setTabHolder(ArticleContentHolder holder) {
        this.map.put("tabHolder", holder);
    }

    private void setTabPosition(int position) {
        this.map.put("tabPosition", Integer.valueOf(position));
    }

    public void setArrayHolder(ArticleCommentHolder holder) {
        this.map.put("arrayholder", holder);
    }

    public ArticleCommentHolder getArrayHolder() {
        return (ArticleCommentHolder) this.map.get("arrayholder");
    }

    public ArticleDetailAdapter(Context mContext, List<ArticleInfo> mList) {
        this.mContext = mContext;
        this.mList = mList;
        Resources resources = mContext.getResources();
        this.m8 = resources.getDimensionPixelSize(R.dimen.default_8PX);
        this.m24 = resources.getDimensionPixelSize(R.dimen.default_24PX);
        this.mVideoMaxWidth = resources.getDisplayMetrics().widthPixels;
        this.mVideoMaxHeight = (int) (((float) (this.mVideoMaxWidth * 9)) / 16.0f);
        this.mOutLinkeImageParams = new LayoutParams(this.mVideoMaxWidth, (this.mVideoMaxWidth * 2) / 3);
        this.mSoleKey = UUID.randomUUID().toString();
    }

    public void setCircleList(CircleInfoList circleList) {
        this.mCircleList = circleList;
    }

    public int getItemViewType(int position) {
        ArticleInfo info = (ArticleInfo) this.mList.get(position);
        if (info != null) {
            return info.type;
        }
        return super.getItemViewType(position);
    }

    public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new ArticleContentHolder(ArticleItemLayoutBinding.inflate(LayoutInflater.from(this.mContext), parent, false));
        }
        if (viewType == 5) {
            return new ArticleContentHolder(ArticleItemLayoutBinding.inflate(LayoutInflater.from(this.mContext), parent, false));
        }
        if (viewType == 7 || viewType == 6) {
            return new ArticleEmptyHolder(ArticleItemEmptyLayoutBinding.inflate(LayoutInflater.from(this.mContext), parent, false));
        }
        return new ArticleCommentHolder(ArticleCommentItemLayoutBinding.inflate(LayoutInflater.from(this.mContext), parent, false));
    }

    public void setmCommentOrderid(int commentOrderid) {
        this.mCommentOrderid = commentOrderid;
    }

    public ArticleRecommendView getRecommendView() {
        return (ArticleRecommendView) this.mRecommendMap.get(Integer.valueOf(0));
    }

    public void onBindViewHolder(ArticleHolder holder, int position) {
        ArticleInfo info = (ArticleInfo) this.mList.get(position);
        if (info == null) {
            return;
        }
        final ArticleContentHolder contentHolder;
        String imageUrl;
        final ArticleCommentHolder commentHolder;
        String userImageUrl;
        ArticleEmptyHolder emptyHolder;
        ViewGroup.LayoutParams params;
        if (info.type == 1) {
            contentHolder = (ArticleContentHolder) holder;
            contentHolder.itemView.setTag(position + "");
            if (position == this.mList.size() - 1 || (position + 1 < this.mList.size() && ((ArticleInfo) this.mList.get(position + 1)).type != ((ArticleInfo) this.mList.get(position)).type)) {
                contentHolder.mBinding.recommendView.setSnsInfo(this.mSnsInfo, this.mSwipeBackLayout);
                contentHolder.mBinding.recommendView.setAdInfo(this.mAdvertInfo);
                contentHolder.mBinding.recommendView.setVisibility(0);
                this.mRecommendMap.put(Integer.valueOf(0), contentHolder.mBinding.recommendView);
            } else {
                contentHolder.mBinding.recommendView.setVisibility(8);
            }
            contentHolder.mBinding.getRoot().setPadding(0, this.m24, 0, 0);
            SnsPostResources postResources = info.resources;
            final SnsPostResources snsPostResources;
            switch (postResources.type) {
                case 1:
                    contentHolder.mBinding.postText.setVisibility(0);
                    contentHolder.mBinding.postImage.setVisibility(8);
                    contentHolder.mBinding.longImage.setVisibility(8);
                    contentHolder.mBinding.videoPlayer.setVisibility(8);
                    contentHolder.mBinding.describe.setVisibility(8);
                    contentHolder.mBinding.outLinkLine.setVisibility(8);
                    if (TextUtils.isEmpty(postResources.description)) {
                        contentHolder.mBinding.postText.setVisibility(8);
                        return;
                    }
                    contentHolder.mBinding.postText.setContent(postResources.description, false);
                    FengUtil.nubiaMannager(contentHolder.mBinding.postText, this.mContext);
                    return;
                case 2:
                    contentHolder.mBinding.postText.setVisibility(8);
                    contentHolder.mBinding.videoPlayer.setVisibility(8);
                    contentHolder.mBinding.outLinkLine.setVisibility(8);
                    imageUrl = FengUtil.getUniformScaleUrl(postResources.image, 640) + "|watermark/1/image/aHR0cDovLzd4c2N3NS5jb20wLnowLmdsYi5jbG91ZGRuLmNvbS9sYW9zam1rLnBuZw==/gravity/SouthEast";
                    if (FengUtil.isLongImage(postResources.image)) {
                        contentHolder.mBinding.postImage.setVisibility(8);
                        contentHolder.mBinding.longImage.setVisibility(0);
                        setPostImageSize(postResources, contentHolder.mBinding.longImage);
                        FengUtil.downLoadImageToLargeImageView(this.mContext, imageUrl, contentHolder.mBinding.longImage);
                        contentHolder.mBinding.longImage.setEnableZoom(false);
                        contentHolder.mBinding.longImage.setTag(imageUrl);
                        snsPostResources = postResources;
                        contentHolder.mBinding.longImage.setLargeImageClickListener(new LargeImageView$LargeImageClickListener() {
                            public void onClick() {
                                Intent intent = new Intent(ArticleDetailAdapter.this.mContext, ShowBigImageActivity.class);
                                intent.putExtra("mImageList", JsonUtil.toJson(ArticleDetailAdapter.this.mImageList));
                                intent.putExtra("position", ArticleDetailAdapter.this.mImageList.indexOf(snsPostResources.image));
                                intent.putExtra("show_type", 1004);
                                intent.putExtra("entrance_type", ShowBigImageActivity.ENTRANCE_TYPE_ARTICLE_IMAGE);
                                intent.putExtra("location_info_json", JsonUtil.toJson(FengUtil.getImageLocationInfo(contentHolder.mBinding.longImage, ArticleDetailAdapter.this.mImageList.indexOf(snsPostResources.image), snsPostResources.image.hash)));
                                ((Activity) ArticleDetailAdapter.this.mContext).startActivityForResult(intent, 1111);
                                ((Activity) ArticleDetailAdapter.this.mContext).overridePendingTransition(0, 0);
                            }
                        });
                    } else {
                        contentHolder.mBinding.postImage.setVisibility(0);
                        contentHolder.mBinding.longImage.setVisibility(8);
                        setPostImageSize(postResources, contentHolder.mBinding.postImage);
                        contentHolder.mBinding.postImage.setTag(imageUrl);
                        GenericDraweeHierarchy hierarchy = (GenericDraweeHierarchy) contentHolder.mBinding.postImage.getHierarchy();
                        hierarchy.setPlaceholderImage(ContextCompat.getDrawable(this.mContext, R.color.color_dfdfdf));
                        contentHolder.mBinding.postImage.setBackgroundResource(R.color.color_dfdfdf);
                        contentHolder.mBinding.postImage.setHierarchy(hierarchy);
                        contentHolder.mBinding.postImage.setAutoImageURI(Uri.parse(imageUrl));
                        contentHolder.mBinding.postImage.setTag(imageUrl);
                        snsPostResources = postResources;
                        contentHolder.mBinding.postImage.setOnClickListener(new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                Intent intent = new Intent(ArticleDetailAdapter.this.mContext, ShowBigImageActivity.class);
                                intent.putExtra("mImageList", JsonUtil.toJson(ArticleDetailAdapter.this.mImageList));
                                intent.putExtra("position", ArticleDetailAdapter.this.mImageList.indexOf(snsPostResources.image));
                                intent.putExtra("show_type", 1004);
                                intent.putExtra("entrance_type", ShowBigImageActivity.ENTRANCE_TYPE_ARTICLE_IMAGE);
                                intent.putExtra("location_info_json", JsonUtil.toJson(FengUtil.getImageLocationInfo(contentHolder.mBinding.postImage, ArticleDetailAdapter.this.mImageList.indexOf(snsPostResources.image), snsPostResources.image.hash)));
                                ((Activity) ArticleDetailAdapter.this.mContext).startActivityForResult(intent, 1111);
                                ((Activity) ArticleDetailAdapter.this.mContext).overridePendingTransition(0, 0);
                            }
                        });
                    }
                    if (StringUtil.isEmpty(postResources.description)) {
                        contentHolder.mBinding.describe.setVisibility(8);
                    } else {
                        contentHolder.mBinding.describe.setContent(postResources.description, false);
                        contentHolder.mBinding.describe.setVisibility(0);
                    }
                    FengUtil.textViewCopy(contentHolder.mBinding.describe, this.mContext);
                    return;
                case 3:
                    contentHolder.mBinding.postText.setVisibility(8);
                    contentHolder.mBinding.postImage.setVisibility(8);
                    contentHolder.mBinding.videoPlayer.setVisibility(0);
                    contentHolder.mBinding.outLinkLine.setVisibility(8);
                    contentHolder.mBinding.longImage.setVisibility(8);
                    LayoutParams imageParams = (LayoutParams) contentHolder.mBinding.videoPlayer.getLayoutParams();
                    if (postResources.image.width == 0 || postResources.image.height == 0) {
                        imageParams.width = -1;
                        imageParams.height = this.mVideoMaxHeight;
                    } else {
                        imageParams.width = -1;
                        imageParams.height = FengUtil.getViewHeight(postResources.image, this.mVideoMaxWidth);
                    }
                    contentHolder.mBinding.videoPlayer.setLayoutParams(imageParams);
                    contentHolder.mBinding.videoPlayer.setMediaInfo(VideoManager.newInstance().createMediaInfo(postResources));
                    contentHolder.mBinding.videoPlayer.setSnsInfo(this.mSnsInfo.id, this.mSnsInfo.resourceid, this.mSnsInfo.snstype);
                    contentHolder.mBinding.videoPlayer.setKeySole(postResources.id);
                    if (StringUtil.isEmpty(postResources.description)) {
                        contentHolder.mBinding.describe.setVisibility(8);
                    } else {
                        contentHolder.mBinding.describe.setContent(postResources.description, false);
                        contentHolder.mBinding.describe.setVisibility(0);
                    }
                    FengUtil.textViewCopy(contentHolder.mBinding.describe, this.mContext);
                    return;
                case 4:
                    contentHolder.mBinding.postText.setVisibility(8);
                    contentHolder.mBinding.postImage.setVisibility(8);
                    contentHolder.mBinding.longImage.setVisibility(8);
                    contentHolder.mBinding.videoPlayer.setVisibility(8);
                    contentHolder.mBinding.outLinkLine.setVisibility(0);
                    contentHolder.mBinding.outLinkLine.setLayoutParams(this.mOutLinkeImageParams);
                    contentHolder.mBinding.outLinkImage.setImageURI(Uri.parse(FengUtil.getFixedSizeUrl(postResources.image, this.mVideoMaxWidth, (this.mVideoMaxWidth * 2) / 3)));
                    contentHolder.mBinding.tvOutLinkTitle.setText(postResources.title);
                    snsPostResources = postResources;
                    contentHolder.mBinding.outLinkLine.setOnClickListener(new OnSingleClickListener() {
                        public void onSingleClick(View view) {
                            Intent webIntent = new Intent(ArticleDetailAdapter.this.mContext, WebActivity.class);
                            webIntent.putExtra("type", 2);
                            webIntent.putExtra("url", snsPostResources.url);
                            ArticleDetailAdapter.this.mContext.startActivity(webIntent);
                        }
                    });
                    snsPostResources = postResources;
                    contentHolder.mBinding.outLinkImage.setOnClickListener(new OnSingleClickListener() {
                        public void onSingleClick(View view) {
                            Intent webIntent = new Intent(ArticleDetailAdapter.this.mContext, WebActivity.class);
                            webIntent.putExtra("type", 2);
                            webIntent.putExtra("url", snsPostResources.url);
                            ArticleDetailAdapter.this.mContext.startActivity(webIntent);
                        }
                    });
                    if (StringUtil.isEmpty(postResources.description)) {
                        contentHolder.mBinding.describe.setVisibility(8);
                    } else {
                        contentHolder.mBinding.describe.setContent(postResources.description, false);
                        contentHolder.mBinding.describe.setVisibility(0);
                    }
                    FengUtil.textViewCopy(contentHolder.mBinding.describe, this.mContext);
                    return;
                default:
                    contentHolder.mBinding.postText.setVisibility(8);
                    contentHolder.mBinding.postImage.setVisibility(8);
                    contentHolder.mBinding.videoPlayer.setVisibility(8);
                    contentHolder.mBinding.outLinkLine.setVisibility(8);
                    contentHolder.mBinding.longImage.setVisibility(8);
                    return;
            }
        } else if (info.type == 2) {
            commentHolder = (ArticleCommentHolder) holder;
            final CommentInfo commentInfo = info.commentInfo;
            if (commentInfo.level.get() == 1) {
                commentHolder.mBinding.ivSafa.setVisibility(0);
            } else {
                commentHolder.mBinding.ivSafa.setVisibility(8);
            }
            commentHolder.mBinding.rlUserInfo.setVisibility(0);
            commentHolder.mBinding.tvAnonymouspraise.setVisibility(8);
            commentHolder.mBinding.setCommentInfo(commentInfo);
            commentHolder.mBinding.setUserInfo(commentInfo.user);
            commentHolder.mBinding.setMSnsInfo(this.mSnsInfo);
            commentHolder.itemView.setTag(position + " 评论");
            if (this.mCommentId == 0 || this.mCommentId != commentInfo.id) {
                commentHolder.mBinding.parentLine.setBackgroundResource(R.drawable.ffffff_5000000_selector);
            } else {
                commentHolder.mBinding.parentLine.setBackgroundResource(R.drawable.fff9e8_faedce_selector);
            }
            commentHolder.mBinding.parentLine.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View view) {
                    FengUtil.showCommentOperationDialog(ArticleDetailAdapter.this.mContext, commentInfo, ArticleDetailAdapter.this.mSnsInfo, true, ArticleDetailAdapter.this.getY(commentHolder.mBinding.parentLine) + commentHolder.mBinding.parentLine.getHeight(), ArticleDetailAdapter.this.mSoleKey, new CommentItemListener() {
                        public void onItemClick() {
                            ArticleInfo info = new ArticleInfo();
                            info.isextend = true;
                            info.type = 7;
                            info.viewhight = 1920;
                            ArticleDetailAdapter.this.mList.add(info);
                            ArticleDetailAdapter.this.notifyDataSetChanged();
                        }
                    });
                }
            });
            if (position == 0 || (position - 1 >= 0 && ((ArticleInfo) this.mList.get(position - 1)).type == 5)) {
                commentHolder.mBinding.arrayLine.setVisibility(0);
                if (this.mCommentOrderid == 1) {
                    commentHolder.mBinding.arrayText.setText(R.string.comment_hotarray);
                } else {
                    commentHolder.mBinding.arrayText.setText(R.string.comment_timearray);
                }
                commentHolder.mBinding.arrayText.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View view) {
                        if (ArticleDetailAdapter.this.mInteractionChangeListener != null) {
                            ArticleDetailAdapter.this.mInteractionChangeListener.onArrayChanged(view);
                        }
                    }
                });
                setArrayHolder(commentHolder);
            } else {
                commentHolder.mBinding.arrayLine.setVisibility(8);
            }
            userImageUrl = FengUtil.getHeadImageUrl(commentInfo.user.getHeadImageInfo());
            commentHolder.mBinding.userImage.setTag(userImageUrl);
            commentHolder.mBinding.userImage.setImageURI(Uri.parse(userImageUrl));
            commentHolder.mBinding.userImage.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View view) {
                    commentInfo.user.intentToPersonalHome(ArticleDetailAdapter.this.mContext);
                }
            });
            commentHolder.mBinding.userName.setText((CharSequence) commentInfo.user.name.get());
            commentHolder.mBinding.topText.setCompoundDrawablePadding(this.m8);
            commentHolder.mBinding.topText.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View view) {
                    commentHolder.mBinding.topText.setEnabled(false);
                    commentInfo.clickPraiseOperation(ArticleDetailAdapter.this.mContext, new SuccessFailCallback() {
                        public void onSuccess() {
                            super.onSuccess();
                            commentHolder.mBinding.topText.setEnabled(true);
                        }

                        public void onFail() {
                            super.onFail();
                            commentHolder.mBinding.topText.setEnabled(true);
                        }
                    });
                }
            });
            commentHolder.mBinding.tvAuthorPraise.setVisibility(commentInfo.istopbyauthor.get() == 1 ? 0 : 8);
            if (StringUtil.isEmpty(commentInfo.image.url)) {
                if (!StringUtil.isEmpty((String) commentInfo.content.get())) {
                    commentHolder.mBinding.content.setVisibility(0);
                    commentHolder.mBinding.content.setContent((String) commentInfo.content.get(), true, commentInfo.imagelist, commentInfo.videolist, null);
                }
                commentHolder.mBinding.image.setVisibility(8);
            } else {
                commentHolder.mBinding.image.setVisibility(0);
                int index = ((String) commentInfo.content.get()).lastIndexOf(MessageFormat.format(" [http://15feng.cn/p/{0}] ", new Object[]{commentInfo.image.hash}).trim());
                if (index >= 0) {
                    commentInfo.content.set(((String) commentInfo.content.get()).substring(0, index));
                }
                if (TextUtils.isEmpty((CharSequence) commentInfo.content.get())) {
                    commentHolder.mBinding.content.setVisibility(8);
                } else {
                    commentHolder.mBinding.content.setVisibility(0);
                    commentHolder.mBinding.content.setContent((String) commentInfo.content.get(), true, commentInfo.imagelist, commentInfo.videolist, null);
                }
                imageUrl = FengUtil.getSingleNineScaleUrl(commentInfo.image, 200);
                commentInfo.image.lowUrl = imageUrl;
                commentHolder.mBinding.image.setTag(imageUrl);
                commentHolder.mBinding.image.setImageURI(Uri.parse(imageUrl));
                commentHolder.mBinding.image.setVisibility(0);
                commentHolder.mBinding.image.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View view) {
                        Intent intent = new Intent(ArticleDetailAdapter.this.mContext, ShowBigImageActivity.class);
                        List<ImageInfo> list = new ArrayList();
                        list.add(commentInfo.image);
                        intent.putExtra("mImageList", JsonUtil.toJson(list));
                        intent.putExtra("position", 0);
                        intent.putExtra("show_type", 1005);
                        intent.putExtra("location_info_json", JsonUtil.toJson(FengUtil.getImageLocationInfo(commentHolder.mBinding.image, 0, commentInfo.image.hash)));
                        intent.putExtra("entrance_type", ShowBigImageActivity.ENTRANCE_TYPE_SINGLE_IMAGE);
                        ArticleDetailAdapter.this.mContext.startActivity(intent);
                        ((Activity) ArticleDetailAdapter.this.mContext).overridePendingTransition(0, 0);
                    }
                });
            }
            if (commentInfo.reply.count > 0) {
                commentHolder.mBinding.llReplyLine.setVisibility(0);
                int commentSize = commentInfo.reply.list.size();
                if (commentInfo.reply.count > 3) {
                    if (commentSize >= 3) {
                        commentSize = 2;
                    }
                    commentHolder.mBinding.llReplyCount.setVisibility(0);
                    commentHolder.mBinding.replyCount.setText("共" + commentInfo.reply.count + "条回复");
                    commentHolder.mBinding.llReplyCount.setOnClickListener(new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            commentInfo.intentToCommentReplyListActivity(ArticleDetailAdapter.this.mContext, 0, -1);
                        }
                    });
                } else {
                    commentHolder.mBinding.llReplyCount.setVisibility(8);
                }
                CommentInfo infoFirst;
                CommentInfo infoSecond;
                if (commentSize == 1) {
                    commentHolder.mBinding.llReplyLine.setVisibility(0);
                    infoFirst = (CommentInfo) commentInfo.reply.list.get(0);
                    commentHolder.mBinding.replyText1.setVisibility(0);
                    commentHolder.mBinding.replyText2.setVisibility(8);
                    commentHolder.mBinding.replyText3.setVisibility(8);
                    commentHolder.mBinding.replyText1.setContent(getReplyText(infoFirst), true, infoFirst.imagelist, infoFirst.videolist, null);
                } else if (commentSize == 2) {
                    commentHolder.mBinding.llReplyLine.setVisibility(0);
                    commentHolder.mBinding.replyText1.setVisibility(0);
                    commentHolder.mBinding.replyText2.setVisibility(0);
                    commentHolder.mBinding.replyText3.setVisibility(8);
                    infoFirst = (CommentInfo) commentInfo.reply.list.get(0);
                    infoSecond = (CommentInfo) commentInfo.reply.list.get(1);
                    commentHolder.mBinding.replyText1.setContent(getReplyText(infoFirst), true, infoFirst.imagelist, infoFirst.videolist, null);
                    commentHolder.mBinding.replyText2.setContent(getReplyText(infoSecond), true, infoSecond.imagelist, infoSecond.videolist, null);
                } else if (commentSize == 3) {
                    commentHolder.mBinding.llReplyLine.setVisibility(0);
                    commentHolder.mBinding.replyText1.setVisibility(0);
                    commentHolder.mBinding.replyText2.setVisibility(0);
                    commentHolder.mBinding.replyText3.setVisibility(0);
                    infoFirst = (CommentInfo) commentInfo.reply.list.get(0);
                    infoSecond = (CommentInfo) commentInfo.reply.list.get(1);
                    CommentInfo infoThree = (CommentInfo) commentInfo.reply.list.get(2);
                    commentHolder.mBinding.replyText1.setContent(getReplyText(infoFirst), true, infoFirst.imagelist, infoFirst.videolist, null);
                    commentHolder.mBinding.replyText2.setContent(getReplyText(infoSecond), true, infoSecond.imagelist, infoSecond.videolist, null);
                    commentHolder.mBinding.replyText3.setContent(getReplyText(infoThree), true, infoThree.imagelist, infoThree.videolist, null);
                } else {
                    commentHolder.mBinding.replyText1.setVisibility(8);
                    commentHolder.mBinding.replyText2.setVisibility(8);
                    commentHolder.mBinding.replyText3.setVisibility(8);
                    commentHolder.mBinding.llReplyLine.setVisibility(8);
                }
                commentHolder.mBinding.replyText1.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        if (commentInfo.reply.list.size() > 0) {
                            commentInfo.intentToCommentReplyListActivity(ArticleDetailAdapter.this.mContext, 0, ((CommentInfo) commentInfo.reply.list.get(0)).id);
                        }
                    }
                });
                commentHolder.mBinding.replyText2.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        if (commentInfo.reply.list.size() > 1) {
                            commentInfo.intentToCommentReplyListActivity(ArticleDetailAdapter.this.mContext, 0, ((CommentInfo) commentInfo.reply.list.get(1)).id);
                        }
                    }
                });
                commentHolder.mBinding.replyText3.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        if (commentInfo.reply.list.size() > 2) {
                            commentInfo.intentToCommentReplyListActivity(ArticleDetailAdapter.this.mContext, 0, ((CommentInfo) commentInfo.reply.list.get(2)).id);
                        }
                    }
                });
                return;
            }
            commentHolder.mBinding.llReplyLine.setVisibility(8);
        } else if (info.type == 3) {
            commentHolder = (ArticleCommentHolder) holder;
            final GratuityRecordInfo gratuityRecordInfo = info.gratuityInfo;
            commentHolder.mBinding.ivSafa.setVisibility(8);
            if (gratuityRecordInfo.local_praise_num > 0) {
                commentHolder.mBinding.tvAnonymouspraise.setVisibility(0);
                commentHolder.mBinding.tvAnonymouspraise.setText("有" + gratuityRecordInfo.local_praise_num + "个赞来自匿名用户");
                commentHolder.mBinding.parentLine.setClickable(false);
                commentHolder.mBinding.rlUserInfo.setVisibility(8);
            } else {
                commentHolder.mBinding.parentLine.setClickable(true);
                commentHolder.mBinding.rlUserInfo.setVisibility(0);
                commentHolder.mBinding.tvAnonymouspraise.setVisibility(8);
                commentHolder.mBinding.setUserInfo(gratuityRecordInfo.user);
                commentHolder.itemView.setTag(gratuityRecordInfo.user.name.get());
                commentHolder.mBinding.parentLine.setBackgroundResource(R.drawable.ffffff_5000000_selector);
                commentHolder.mBinding.parentLine.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View view) {
                        gratuityRecordInfo.user.intentToPersonalHome(ArticleDetailAdapter.this.mContext);
                    }
                });
                userImageUrl = FengUtil.getHeadImageUrl(gratuityRecordInfo.user.getHeadImageInfo());
                commentHolder.mBinding.userImage.setTag(userImageUrl);
                commentHolder.mBinding.userImage.setImageURI(Uri.parse(userImageUrl));
                commentHolder.mBinding.userName.setText((CharSequence) gratuityRecordInfo.user.name.get());
            }
            commentHolder.mBinding.topText.setVisibility(8);
            commentHolder.mBinding.content.setVisibility(8);
            commentHolder.mBinding.image.setVisibility(8);
            commentHolder.mBinding.llReplyLine.setVisibility(8);
            commentHolder.mBinding.tvAuthorPraise.setVisibility(8);
        } else if (info.type == 5) {
            contentHolder = (ArticleContentHolder) holder;
            contentHolder.itemView.setTag("tab_bar");
            setTabHolder(contentHolder);
            setTabPosition(position);
            contentHolder.mBinding.postText.setVisibility(8);
            contentHolder.mBinding.postImage.setVisibility(8);
            contentHolder.mBinding.longImage.setVisibility(8);
            contentHolder.mBinding.videoPlayer.setVisibility(8);
            contentHolder.mBinding.outLinkLine.setVisibility(8);
            contentHolder.mBinding.describe.setVisibility(8);
            contentHolder.mBinding.recommendView.setVisibility(8);
            contentHolder.mBinding.getRoot().setPadding(0, 0, 0, 0);
            if (this.mIsHideTabType) {
                contentHolder.mBinding.choiceLine.setVisibility(8);
                return;
            }
            contentHolder.mBinding.choiceLine.setVisibility(0);
            changeTab(contentHolder, this.mType);
            if (this.mSnsInfo != null) {
                contentHolder.mBinding.setMSnsInfo(this.mSnsInfo);
            }
            contentHolder.mBinding.commentText.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View view) {
                    if (ArticleDetailAdapter.this.mInteractionChangeListener != null) {
                        ArticleDetailAdapter.this.mInteractionChangeListener.onCommentSelected();
                    }
                    ArticleDetailAdapter.this.changeTab(contentHolder, 1);
                }
            });
            contentHolder.mBinding.praiseText.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View view) {
                    if (ArticleDetailAdapter.this.mInteractionChangeListener != null) {
                        ArticleDetailAdapter.this.mInteractionChangeListener.onPraiseSelected();
                    }
                    ArticleDetailAdapter.this.changeTab(contentHolder, 3);
                }
            });
        } else if (info.type == 7) {
            emptyHolder = (ArticleEmptyHolder) holder;
            emptyHolder.itemView.setTag("empty_view");
            params = (RecyclerView.LayoutParams) emptyHolder.mBinding.llEmpty.getLayoutParams();
            params.height = ((ArticleInfo) this.mList.get(position)).viewhight;
            emptyHolder.mBinding.llEmpty.setLayoutParams(params);
        } else if (info.type == 6) {
            emptyHolder = (ArticleEmptyHolder) holder;
            emptyHolder.itemView.setTag("empty_view");
            if (this.mType == 1) {
                emptyHolder.mBinding.emptyText.setText(R.string.nocomment_tips);
            } else if (this.mType == 3) {
                emptyHolder.mBinding.emptyText.setText(R.string.nopraise_tips);
            }
            emptyHolder.mBinding.emptyText.setVisibility(0);
            params = (RecyclerView.LayoutParams) emptyHolder.mBinding.llEmpty.getLayoutParams();
            params.height = ((ArticleInfo) this.mList.get(position)).viewhight;
            emptyHolder.mBinding.llEmpty.setLayoutParams(params);
        }
    }

    private int getY(View view) {
        int[] rect = new int[2];
        view.getLocationOnScreen(rect);
        return rect[1];
    }

    public int getItemCount() {
        return this.mList.size();
    }

    private String getReplyText(CommentInfo commentInfo) {
        if (commentInfo.replyuser.id <= 0 || TextUtils.isEmpty((CharSequence) commentInfo.replyuser.name.get())) {
            if (commentInfo.isdel == 1) {
                return "@" + ((String) commentInfo.user.name.get()) + "：" + this.mContext.getString(R.string.comment_delete_tips);
            }
            return "@" + ((String) commentInfo.user.name.get()) + "：" + ((String) commentInfo.content.get());
        } else if (commentInfo.isdel == 1) {
            return "@" + ((String) commentInfo.user.name.get()) + " 回复@" + ((String) commentInfo.replyuser.name.get()) + "：" + this.mContext.getString(R.string.comment_delete_tips);
        } else {
            return "@" + ((String) commentInfo.user.name.get()) + " 回复@" + ((String) commentInfo.replyuser.name.get()) + "：" + ((String) commentInfo.content.get());
        }
    }

    public void setType(int mType) {
        this.mType = mType;
    }

    public void changeTab(ArticleHolder holder, int type) {
        if (holder != null) {
            this.mType = type;
            if (holder instanceof ArticleContentHolder) {
                ArticleContentHolder contentHolder = (ArticleContentHolder) holder;
                if (type == 1) {
                    contentHolder.mBinding.commentText.setSelected(true);
                    contentHolder.mBinding.praiseText.setSelected(false);
                } else if (type == 3) {
                    contentHolder.mBinding.commentText.setSelected(false);
                    contentHolder.mBinding.praiseText.setSelected(true);
                }
            }
        }
    }

    public void setPostImageSize(SnsPostResources resources, View view) {
        LayoutParams imageParams;
        if (resources.image.width == 0 || resources.image.height == 0) {
            imageParams = new LayoutParams(this.mVideoMaxWidth, -2);
        } else {
            imageParams = new LayoutParams(this.mVideoMaxWidth, FengUtil.getViewHeight(resources.image, this.mVideoMaxWidth));
        }
        imageParams.setMargins(0, 0, 0, -1);
        view.setLayoutParams(imageParams);
    }
}
