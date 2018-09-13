package com.feng.car.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.feng.car.R;
import com.feng.car.activity.VideoFinalPageActivity;
import com.feng.car.databinding.CommonPostItemLayoutBinding;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.model.LogGatherInfo;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsInfoList;
import com.feng.car.entity.sns.SnsPostResources;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.event.SnsInfoModifyEvent;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.RecommendRecordUtil;
import com.feng.car.utils.VideoManager;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;
import java.util.Map;

public class CommonPostAdapter extends MvvmBaseAdapter<SnsInfo, CommonPostItemLayoutBinding> {
    public static final int FROM_CHOICENESS_FRAGMENT = 1;
    public static final int FROM_FOLLOW_PROGRAM_PAGE = 5;
    public static final int FROM_FOLLOW_TOPIC_PAGE = 4;
    public static final int FROM_HISTORY_DEL_PAGE = 3;
    public static final int FROM_OTHER = 0;
    public static final int FROM_TOPIC_FINAL_PAGE = 2;
    private int m24;
    private int m34;
    private Map<Integer, String> mDelSeeMap;
    private int mFromType = 0;
    private LayoutParams mImageParams;
    private boolean mIsHomeRecommend = false;
    public OnCheckItemDeleteListener mItemDeleteListener;
    private LogGatherInfo mLogGatherInfo;
    private int mMaxHeight = 0;
    private int mMinHeight = 0;
    private RecyclerView.LayoutParams mParentParams;
    private boolean mShowTopSpaces = false;
    private int mTopicId = 0;
    private int mViewWidth = 0;
    private SnsInfoList snsInfoList;

    public interface OnCheckItemDeleteListener {
        void onCheckItem(int i);
    }

    public void setFromType(int fromType) {
        this.mFromType = fromType;
    }

    public void setTopicId(int topicId) {
        this.mTopicId = topicId;
    }

    public void setItemDeleteListener(Map<Integer, String> map, OnCheckItemDeleteListener itemDeleteListener) {
        this.mDelSeeMap = map;
        this.mItemDeleteListener = itemDeleteListener;
    }

    public CommonPostAdapter(Context context, SnsInfoList list, int fromType, boolean isShowTopSpaces, LogGatherInfo logGatherInfo) {
        super(context, list.getSnsList());
        this.mLogGatherInfo = logGatherInfo;
        this.snsInfoList = list;
        this.mFromType = fromType;
        Resources resources = this.mContext.getResources();
        this.mViewWidth = (FengUtil.getScreenWidth(this.mContext) - resources.getDimensionPixelSize(R.dimen.default_68PX)) / 2;
        this.m24 = resources.getDimensionPixelSize(R.dimen.default_24PX);
        this.m34 = resources.getDimensionPixelSize(R.dimen.default_34PX);
        this.mMaxHeight = (int) (((float) (this.mViewWidth * 4)) / 3.0f);
        this.mMinHeight = (int) (((float) (this.mViewWidth * 3)) / 4.0f);
        this.mShowTopSpaces = isShowTopSpaces;
    }

    public void onBaseBindViewHolder(final MvvmViewHolder<CommonPostItemLayoutBinding> holder, final int position) {
        final SnsInfo info = (SnsInfo) this.mList.get(position);
        ImageInfo imageInfo = info.getCoverImage();
        if (((CommonPostItemLayoutBinding) holder.binding).getRoot().getTag(R.string.feed_log_tag) != null) {
            int oldIndex = Integer.parseInt(((CommonPostItemLayoutBinding) holder.binding).getRoot().getTag(R.string.feed_log_tag).toString());
            if (oldIndex != position && oldIndex < this.mList.size() && ((SnsInfo) this.mList.get(oldIndex)).snstype == 1000) {
                ((SnsInfo) this.mList.get(oldIndex)).advertInfo.adPvHandle(this.mContext, false);
            }
        }
        if (info.snstype == 1000) {
            info.advertInfo.adPvHandle(this.mContext, true);
        } else {
            this.mLogGatherInfo.addLocationReadPv(info.id, info.resourceid, info.snstype, getExpendedJson(position, info));
        }
        ((CommonPostItemLayoutBinding) holder.binding).getRoot().setTag(R.string.feed_log_tag, String.valueOf(position));
        ((CommonPostItemLayoutBinding) holder.binding).getRoot().setTag(R.string.feed_item_space_tag, this.mContext.getString(R.string.feed_item_space_tag));
        if (info.snstype == 1000) {
            ((CommonPostItemLayoutBinding) holder.binding).llTag.setVisibility(0);
            ((CommonPostItemLayoutBinding) holder.binding).ivTag.setVisibility(8);
            ((CommonPostItemLayoutBinding) holder.binding).tvTagName.setVisibility(0);
            ((CommonPostItemLayoutBinding) holder.binding).tvTagName.setText(info.advertInfo.tmpmap.label);
            ((CommonPostItemLayoutBinding) holder.binding).tvTagName.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_38_000000));
        } else if (this.mFromType == 1 && info.ontop > 0) {
            ((CommonPostItemLayoutBinding) holder.binding).llTag.setVisibility(0);
            ((CommonPostItemLayoutBinding) holder.binding).ivTag.setVisibility(0);
            ((CommonPostItemLayoutBinding) holder.binding).tvTagName.setVisibility(8);
            ((CommonPostItemLayoutBinding) holder.binding).ivTag.setImageResource(R.drawable.icon_post_top);
        } else if (this.mFromType == 2 && info.ontop > 0) {
            ((CommonPostItemLayoutBinding) holder.binding).llTag.setVisibility(0);
            ((CommonPostItemLayoutBinding) holder.binding).ivTag.setVisibility(0);
            ((CommonPostItemLayoutBinding) holder.binding).tvTagName.setVisibility(8);
            ((CommonPostItemLayoutBinding) holder.binding).ivTag.setImageResource(R.drawable.icon_post_top);
        } else if (this.mFromType == 4) {
            ((CommonPostItemLayoutBinding) holder.binding).llTag.setVisibility(0);
            ((CommonPostItemLayoutBinding) holder.binding).ivTag.setVisibility(0);
            ((CommonPostItemLayoutBinding) holder.binding).tvTagName.setVisibility(0);
            ((CommonPostItemLayoutBinding) holder.binding).ivTag.setImageResource(R.drawable.topic_orange);
            ((CommonPostItemLayoutBinding) holder.binding).tvTagName.setText(info.getMineHotCircleName());
            ((CommonPostItemLayoutBinding) holder.binding).tvTagName.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_fb6c06));
        } else if (this.mFromType == 5) {
            ((CommonPostItemLayoutBinding) holder.binding).llTag.setVisibility(0);
            ((CommonPostItemLayoutBinding) holder.binding).ivTag.setVisibility(0);
            ((CommonPostItemLayoutBinding) holder.binding).tvTagName.setVisibility(0);
            ((CommonPostItemLayoutBinding) holder.binding).ivTag.setImageResource(R.drawable.icon_program_vedio);
            ((CommonPostItemLayoutBinding) holder.binding).tvTagName.setText(info.hotshow.name);
            ((CommonPostItemLayoutBinding) holder.binding).tvTagName.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_fb6c06));
        } else {
            ((CommonPostItemLayoutBinding) holder.binding).llTag.setVisibility(8);
        }
        if (info.isonlyvideo != 1 || info.snstype == 1000) {
            ((CommonPostItemLayoutBinding) holder.binding).ivVideoTime.setVisibility(8);
        } else {
            ((CommonPostItemLayoutBinding) holder.binding).ivVideoTime.setVisibility(0);
        }
        this.mImageParams = (LayoutParams) ((CommonPostItemLayoutBinding) holder.binding).adCover.getLayoutParams();
        this.mImageParams.width = this.mViewWidth;
        this.mImageParams.height = getImageHeight(imageInfo);
        ((CommonPostItemLayoutBinding) holder.binding).adCover.setLayoutParams(this.mImageParams);
        ((CommonPostItemLayoutBinding) holder.binding).adCover.setAutoImageURI(Uri.parse(FengUtil.getFeedScaleUrl(imageInfo, 350)));
        ((CommonPostItemLayoutBinding) holder.binding).atDigestDescription.setMatchLink(false);
        ((CommonPostItemLayoutBinding) holder.binding).atDigestDescription.setContent(info.getFeedTitleOrDes(), false);
        ((CommonPostItemLayoutBinding) holder.binding).rlParent.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (CommonPostAdapter.this.mFromType == 3) {
                    if (CommonPostAdapter.this.mDelSeeMap != null) {
                        if (CommonPostAdapter.this.mDelSeeMap.containsKey(Integer.valueOf(info.id))) {
                            ((CommonPostItemLayoutBinding) holder.binding).ivSelectDel.setSelected(false);
                            CommonPostAdapter.this.mDelSeeMap.remove(Integer.valueOf(info.id));
                        } else {
                            ((CommonPostItemLayoutBinding) holder.binding).ivSelectDel.setSelected(true);
                            CommonPostAdapter.this.mDelSeeMap.put(Integer.valueOf(info.id), info.getLocalkey());
                        }
                        if (CommonPostAdapter.this.mItemDeleteListener != null) {
                            CommonPostAdapter.this.mItemDeleteListener.onCheckItem(CommonPostAdapter.this.mDelSeeMap.size());
                        }
                    }
                } else if (info.snstype == 1000) {
                    info.advertInfo.adClickHandle(CommonPostAdapter.this.mContext);
                } else {
                    CommonPostAdapter.this.addLocalClick(position, info);
                    CommonPostAdapter.this.startToArticle(info);
                }
            }
        });
        ((CommonPostItemLayoutBinding) holder.binding).llOperation.setVisibility(0);
        ((CommonPostItemLayoutBinding) holder.binding).adUserImage.setVisibility(0);
        ((CommonPostItemLayoutBinding) holder.binding).tvUserName.setVisibility(0);
        if (info.snstype != 1000) {
            if (info.isHasText()) {
                ((CommonPostItemLayoutBinding) holder.binding).atDigestDescription.setMaxLines(5);
            } else {
                ((CommonPostItemLayoutBinding) holder.binding).atDigestDescription.setMaxLines(3);
            }
            ((CommonPostItemLayoutBinding) holder.binding).adUserImage.setHeadUrl(FengUtil.getHeadImageUrl(info.user.getHeadImageInfo()));
            if (this.mFromType == 3) {
                ((CommonPostItemLayoutBinding) holder.binding).llOperation.setClickable(false);
                ((CommonPostItemLayoutBinding) holder.binding).ivSelectDel.setVisibility(0);
                ((CommonPostItemLayoutBinding) holder.binding).ivGood.setVisibility(8);
                ((CommonPostItemLayoutBinding) holder.binding).tvGoodNum.setVisibility(8);
                ((CommonPostItemLayoutBinding) holder.binding).ivSelectDel.setImageResource(R.drawable.private_selector);
            } else {
                ((CommonPostItemLayoutBinding) holder.binding).ivSelectDel.setVisibility(8);
                ((CommonPostItemLayoutBinding) holder.binding).ivGood.setVisibility(0);
                ((CommonPostItemLayoutBinding) holder.binding).tvGoodNum.setVisibility(0);
                ((CommonPostItemLayoutBinding) holder.binding).llOperation.setClickable(true);
                ((CommonPostItemLayoutBinding) holder.binding).llOperation.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        ((CommonPostItemLayoutBinding) holder.binding).llOperation.setEnabled(false);
                        info.praiseOperation(CommonPostAdapter.this.mContext, true, new SuccessFailCallback() {
                            public void onSuccess() {
                                ((CommonPostItemLayoutBinding) holder.binding).llOperation.setEnabled(true);
                            }

                            public void onFail() {
                                ((CommonPostItemLayoutBinding) holder.binding).llOperation.setEnabled(true);
                            }
                        });
                    }
                });
            }
        } else if (info.advertInfo.isinner == 1) {
            ((CommonPostItemLayoutBinding) holder.binding).adUserImage.setHeadUrl(FengUtil.getHeadImageUrl(info.user.getHeadImageInfo()));
        } else {
            ((CommonPostItemLayoutBinding) holder.binding).llOperation.setVisibility(8);
            ((CommonPostItemLayoutBinding) holder.binding).adUserImage.setVisibility(8);
            ((CommonPostItemLayoutBinding) holder.binding).tvUserName.setVisibility(8);
        }
        if ((this.mFromType == 1 || (this.mFromType == 2 && info.ontop == 0)) && info.snstype != 1000) {
            ((CommonPostItemLayoutBinding) holder.binding).ivArrowDown.setVisibility(0);
            ((CommonPostItemLayoutBinding) holder.binding).atDigestDescription.setVisibility(0);
            ((CommonPostItemLayoutBinding) holder.binding).ivArrowDown.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (CommonPostAdapter.this.mFromType == 1) {
                        info.showDislikeDialog(CommonPostAdapter.this.mContext);
                    } else if (CommonPostAdapter.this.mFromType == 2) {
                        info.showReportTopicDialog(CommonPostAdapter.this.mContext, CommonPostAdapter.this.mTopicId, null);
                    }
                }
            });
        } else {
            ((CommonPostItemLayoutBinding) holder.binding).ivArrowDown.setVisibility(8);
            if (TextUtils.isEmpty(info.getFeedTitleOrDes())) {
                ((CommonPostItemLayoutBinding) holder.binding).atDigestDescription.setVisibility(8);
            } else {
                ((CommonPostItemLayoutBinding) holder.binding).atDigestDescription.setVisibility(0);
            }
        }
        if (this.mFromType == 1) {
            RecommendRecordUtil.getInstance().addData(info, false);
        }
        this.mParentParams = new RecyclerView.LayoutParams(this.mViewWidth + this.m34, -2);
        if (this.mShowTopSpaces) {
            if (position == 0 || position == 1) {
                this.mParentParams.setMargins(0, this.m24, 0, 0);
            } else {
                this.mParentParams.setMargins(0, 0, 0, 0);
            }
        }
        ((CommonPostItemLayoutBinding) holder.binding).rlParent.setLayoutParams(this.mParentParams);
    }

    public CommonPostItemLayoutBinding getBinding(ViewGroup parent, int viewType) {
        return CommonPostItemLayoutBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(CommonPostItemLayoutBinding commonPostItemLayoutBinding, SnsInfo info) {
        commonPostItemLayoutBinding.setSnsInfo(info);
    }

    public void refreshUserStatus(UserInfoRefreshEvent userInfoRefreshEvent) {
        if (userInfoRefreshEvent != null && userInfoRefreshEvent.userInfo != null) {
            UserInfo info = userInfoRefreshEvent.userInfo;
            List<Integer> positionList = this.snsInfoList.getUserSnsPosByUserID(info.id);
            if (userInfoRefreshEvent.type == 1 && positionList != null && positionList.size() > 0) {
                for (int i = 0; i < positionList.size(); i++) {
                    UserInfo userInfo = ((SnsInfo) this.mList.get(((Integer) positionList.get(i)).intValue())).user;
                    if (!(StringUtil.isEmpty((String) info.name.get()) || ((String) userInfo.name.get()).equals(info.name.get()))) {
                        userInfo.name.set(info.name.get());
                    }
                    if (!(StringUtil.isEmpty((String) info.signature.get()) || ((String) userInfo.signature.get()).equals(info.signature.get()))) {
                        userInfo.signature.set(info.signature.get());
                    }
                    if (!(StringUtil.isEmpty(info.image.url) || userInfo.getHeadImageInfo().url.equals(info.getHeadImageInfo().url))) {
                        userInfo.getHeadImageInfo().width = info.getHeadImageInfo().width;
                        userInfo.getHeadImageInfo().height = info.getHeadImageInfo().height;
                        userInfo.getHeadImageInfo().url = info.getHeadImageInfo().url;
                        userInfo.getHeadImageInfo().hash = info.getHeadImageInfo().hash;
                        userInfo.getHeadImageInfo().mimetype = info.getHeadImageInfo().mimetype;
                    }
                    notifyItemChanged(((Integer) positionList.get(i)).intValue());
                }
            }
        }
    }

    public void modifySnsInfo(SnsInfoModifyEvent snsInfoModifyEvent) {
        if (snsInfoModifyEvent != null) {
            SnsInfo snsInfoModify = snsInfoModifyEvent.snsInfo;
            int position = this.snsInfoList.getPosition(snsInfoModifyEvent.snsInfo.getLocalkey());
            if (position >= 0) {
                SnsInfo snsInfo = (SnsInfo) this.mList.get(position);
                if (snsInfoModify.id == snsInfo.id) {
                    snsInfo.title.set(snsInfoModify.title.get());
                    snsInfo.description.set(snsInfoModify.description.get());
                    snsInfo.modifytime.set(snsInfoModify.modifytime.get());
                    snsInfo.image.url = snsInfoModify.image.url;
                    snsInfo.image.width = snsInfoModify.image.width;
                    snsInfo.image.height = snsInfoModify.image.height;
                    snsInfo.image.description = snsInfoModify.image.description;
                    notifyItemChanged(position);
                }
            }
        }
    }

    public void refreshSnsInfo(SnsInfoRefreshEvent snsInfoRefreshEvent) {
        if (snsInfoRefreshEvent != null) {
            SnsInfo snsInfoRefresh = snsInfoRefreshEvent.snsInfo;
            int position = this.snsInfoList.getPosition(snsInfoRefresh.getLocalkey());
            if (position != -1) {
                SnsInfo snsInfo = this.snsInfoList.get(position);
                switch (snsInfoRefreshEvent.refreshType) {
                    case 2004:
                        snsInfo.praisecount.set(snsInfoRefresh.praisecount.get());
                        snsInfo.ispraise.set(snsInfoRefresh.ispraise.get());
                        return;
                    case m_AppUI.MSG_APP_VERSION_FORCE /*2005*/:
                        snsInfo.isflag = snsInfoRefresh.isflag;
                        this.snsInfoList.remove(position);
                        removeNotifyItem(position);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private void startToArticle(SnsInfo info) {
        if (info.isonlyvideo == 1 && info.list.size() > 0 && ((SnsPostResources) info.list.get(0)).type == 3) {
            Intent intent = new Intent(this.mContext, VideoFinalPageActivity.class);
            intent.putExtra("MEDIA", VideoManager.newInstance().createMediaInfo((SnsPostResources) info.list.get(0)).key);
            intent.putExtra("snsid", info.id);
            intent.putExtra("resourceid", info.resourceid);
            intent.putExtra("resourcetype", info.snstype);
            intent.putExtra("from_key", false);
            intent.putExtra("from_article_final", 0);
            this.mContext.startActivity(intent);
        } else {
            info.intentToArticle(this.mContext, -1);
        }
        if ((info.snstype == 0 || info.snstype == 1) && this.mFromType == 1 && this.mIsHomeRecommend) {
            RecommendRecordUtil.getInstance().addData(info, true);
        }
    }

    private int getImageHeight(ImageInfo info) {
        if (info.width == 0 || info.height == 0) {
            return 1;
        }
        int[] xy = FengUtil.getRelativeWH(info, this.mViewWidth, 350);
        if (xy[1] > this.mMaxHeight) {
            return this.mMaxHeight;
        }
        if (xy[1] < this.mMinHeight) {
            return this.mMinHeight;
        }
        return xy[1];
    }

    public void removeNotifyItem(int pos) {
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, this.mList.size() - pos);
    }

    public void insertedNotifyItem(int pos) {
        notifyItemInserted(pos);
        notifyItemRangeChanged(pos, this.mList.size() - pos);
    }

    private void addLocalClick(int position, SnsInfo snsInfo) {
        this.mLogGatherInfo.addLocationClick(snsInfo.id, snsInfo.resourceid, snsInfo.snstype, getExpendedJson(position, snsInfo));
    }

    private String getExpendedJson(int position, SnsInfo info) {
        return "{\"position\":\"" + position + "\"" + (info.rts != -1 ? ",\"rts\":\"" + info.rts + "\"" : "") + "}";
    }
}
