package com.feng.car.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.activity.ShowBigImageActivity;
import com.feng.car.databinding.MessageCommentItemBinding;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.thread.CommentInfo;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.JsonUtil;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommentAdapter extends MvvmBaseAdapter<CommentInfo, MessageCommentItemBinding> {
    private int m120;
    private int mCommentType = 0;
    private Resources mResources;
    private String mSoleKey = "";

    public String getSoleKey() {
        return this.mSoleKey;
    }

    public CommentAdapter(Context context, List<CommentInfo> list) {
        super(context, list);
        this.mResources = context.getResources();
        this.m120 = this.mResources.getDimensionPixelSize(R.dimen.default_120PX);
        this.mSoleKey = UUID.randomUUID().toString();
    }

    public void setCommentType(int commentType) {
        this.mCommentType = commentType;
    }

    public void onBaseBindViewHolder(final MvvmViewHolder<MessageCommentItemBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        final CommentInfo item = (CommentInfo) this.mList.get(position);
        ((MessageCommentItemBinding) holder.binding).fdvCommentUserPortrait.setHeadUrl(FengUtil.getHeadImageUrl(item.user.getHeadImageInfo()));
        ((MessageCommentItemBinding) holder.binding).ivCommentContentImg.setImageURI(Uri.parse(FengUtil.getFixedSizeUrl(item.sns.getQuoteImageInfo(), this.m120, this.m120)));
        ((MessageCommentItemBinding) holder.binding).rlUserInfo.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                item.user.intentToPersonalHome(CommentAdapter.this.mContext);
            }
        });
        ((MessageCommentItemBinding) holder.binding).tvCommentUsername.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                item.user.intentToPersonalHome(CommentAdapter.this.mContext);
            }
        });
        if (this.mCommentType == 2) {
            ((MessageCommentItemBinding) holder.binding).llOperation.setVisibility(0);
            ((MessageCommentItemBinding) holder.binding).llCommentReply.setVisibility(0);
            if (item.isreply == 1) {
                ((MessageCommentItemBinding) holder.binding).tvCommentReply.setText(R.string.replied);
            } else {
                ((MessageCommentItemBinding) holder.binding).tvCommentReply.setText(R.string.reply);
            }
            ((MessageCommentItemBinding) holder.binding).llCommentReply.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    item.intentToSendCommentActivity(CommentAdapter.this.mContext, ((MessageCommentItemBinding) holder.binding).llCommentReply.getHeight() + CommentAdapter.this.getY(((MessageCommentItemBinding) holder.binding).llCommentReply), CommentAdapter.this.mSoleKey);
                }
            });
            ((MessageCommentItemBinding) holder.binding).llGood.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    ((MessageCommentItemBinding) holder.binding).llGood.setEnabled(false);
                    item.clickPraiseOperation(CommentAdapter.this.mContext, new SuccessFailCallback() {
                        public void onSuccess() {
                            super.onSuccess();
                            ((MessageCommentItemBinding) holder.binding).llGood.setEnabled(true);
                        }

                        public void onFail() {
                            super.onFail();
                            ((MessageCommentItemBinding) holder.binding).llGood.setEnabled(true);
                        }
                    });
                }
            });
            ((MessageCommentItemBinding) holder.binding).divider.setVisibility(8);
        } else {
            ((MessageCommentItemBinding) holder.binding).llOperation.setVisibility(8);
            ((MessageCommentItemBinding) holder.binding).divider.setVisibility(0);
        }
        if (item.isdel == 0) {
            ((MessageCommentItemBinding) holder.binding).atvCommentDetail.setVisibility(0);
            if (item.image == null || StringUtil.isEmpty(item.image.url)) {
                if (item.originparentid <= 0 || item.parent == null) {
                    ((MessageCommentItemBinding) holder.binding).atvCommentDetail.setContent((String) item.content.get(), true, item.imagelist, item.videolist, null);
                } else {
                    ((MessageCommentItemBinding) holder.binding).atvCommentDetail.setContent("回复@" + ((String) item.parent.user.name.get()) + ": " + ((String) item.content.get()), true, item.imagelist, item.videolist, null);
                }
                ((MessageCommentItemBinding) holder.binding).fdvCommentImage.setVisibility(8);
            } else {
                int index = ((String) item.content.get()).lastIndexOf(MessageFormat.format(" [http://15feng.cn/p/{0}] ", new Object[]{item.image.hash}).trim());
                if (index >= 0) {
                    item.content.set(((String) item.content.get()).substring(0, index));
                }
                if (item.originparentid > 0 && item.parent != null) {
                    ((MessageCommentItemBinding) holder.binding).atvCommentDetail.setContent("回复@" + ((String) item.parent.user.name.get()) + ": " + ((String) item.content.get()), true, item.imagelist, item.videolist, null);
                } else if (TextUtils.isEmpty((CharSequence) item.content.get())) {
                    ((MessageCommentItemBinding) holder.binding).atvCommentDetail.setVisibility(8);
                } else {
                    ((MessageCommentItemBinding) holder.binding).atvCommentDetail.setVisibility(0);
                    ((MessageCommentItemBinding) holder.binding).atvCommentDetail.setContent((String) item.content.get(), true, item.imagelist, item.videolist, null);
                }
                String comment_image_url = FengUtil.getSingleNineScaleUrl(item.image, 200);
                ((MessageCommentItemBinding) holder.binding).fdvCommentImage.setTag(comment_image_url);
                item.image.lowUrl = comment_image_url;
                ((MessageCommentItemBinding) holder.binding).fdvCommentImage.setAutoImageURI(Uri.parse(comment_image_url));
                ((MessageCommentItemBinding) holder.binding).fdvCommentImage.setVisibility(0);
                ((MessageCommentItemBinding) holder.binding).fdvCommentImage.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        List<ImageInfo> list = new ArrayList();
                        list.add(item.image);
                        Intent intent = new Intent(CommentAdapter.this.mContext, ShowBigImageActivity.class);
                        intent.putExtra("mImageList", JsonUtil.toJson(list));
                        intent.putExtra("position", 0);
                        intent.putExtra("show_type", 1005);
                        intent.putExtra("location_info_json", JsonUtil.toJson(FengUtil.getImageLocationInfo(((MessageCommentItemBinding) holder.binding).fdvCommentImage, 0, item.image.hash)));
                        intent.putExtra("entrance_type", ShowBigImageActivity.ENTRANCE_TYPE_SINGLE_IMAGE);
                        CommentAdapter.this.mContext.startActivity(intent);
                        ((Activity) CommentAdapter.this.mContext).overridePendingTransition(0, 0);
                    }
                });
            }
        }
        ((MessageCommentItemBinding) holder.binding).llCommentContainer.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (item.originparentid != 0) {
                    item.intentToCommentReplyListActivity(CommentAdapter.this.mContext, 1, item.id);
                } else if (item.sns.snstype == 0 || item.sns.snstype == 1) {
                    item.sns.intentToArticle(CommentAdapter.this.mContext, item.id);
                } else if (item.sns.snstype == 9 || item.sns.snstype == 10) {
                    item.sns.intentToViewPoint(CommentAdapter.this.mContext, false, 1, item.id);
                }
            }
        });
        ((MessageCommentItemBinding) holder.binding).tvThreadDetailUsername.setVisibility(0);
        if (StringUtil.isEmpty((String) item.sns.user.name.get())) {
            ((MessageCommentItemBinding) holder.binding).tvThreadDetailUsername.setVisibility(8);
        } else {
            ((MessageCommentItemBinding) holder.binding).tvThreadDetailUsername.setText(MessageFormat.format(this.mContext.getString(R.string.at_user_name), new Object[]{item.sns.user.name.get()}));
        }
        ((MessageCommentItemBinding) holder.binding).tvThreadDetailContent.setText(item.sns.getQuoteDescription());
        if (item.originparentid > 0 && item.parent != null) {
            ((MessageCommentItemBinding) holder.binding).atvReplyCommentDetail.setVisibility(0);
            ((MessageCommentItemBinding) holder.binding).rlOriginalReplyContainer.setBackgroundResource(R.drawable.f7f7f7_list_selector);
            ((MessageCommentItemBinding) holder.binding).divider.setVisibility(0);
            if (item.parent.isdel == 0) {
                String replyCommentDetail = "@" + ((String) item.parent.user.name.get()) + ":";
                if (!TextUtils.isEmpty((CharSequence) item.parent.content.get())) {
                    replyCommentDetail = replyCommentDetail + ((String) item.parent.content.get());
                }
                ((MessageCommentItemBinding) holder.binding).atvReplyCommentDetail.setContent(replyCommentDetail, true, item.parent.imagelist, item.parent.videolist, null);
            } else {
                ((MessageCommentItemBinding) holder.binding).atvReplyCommentDetail.setContent("@" + ((String) item.parent.user.name.get()) + ":" + this.mContext.getResources().getString(R.string.comment_delete_tips), true);
            }
            ((MessageCommentItemBinding) holder.binding).rlOriginalReplyContainer.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (item.parent.originparentid != 0) {
                        item.parent.intentToCommentReplyListActivity(CommentAdapter.this.mContext, 1, item.parent.id);
                    } else if (item.sns.snstype == 0 || item.sns.snstype == 1) {
                        item.sns.intentToArticle(CommentAdapter.this.mContext, item.parent.id);
                    } else if (item.sns.snstype == 9 || item.sns.snstype == 10) {
                        item.sns.intentToViewPoint(CommentAdapter.this.mContext, false, 1, item.parent.id);
                    }
                }
            });
        } else if (item.originparentid == 0) {
            ((MessageCommentItemBinding) holder.binding).atvReplyCommentDetail.setVisibility(8);
            ((MessageCommentItemBinding) holder.binding).rlOriginalReplyContainer.setBackground(null);
            ((MessageCommentItemBinding) holder.binding).rlOriginalReplyContainer.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (item.sns.snstype == 0 || item.sns.snstype == 1) {
                        item.sns.intentToArticle(CommentAdapter.this.mContext, item.id);
                    } else if (item.sns.snstype == 9 || item.sns.snstype == 10) {
                        item.sns.intentToViewPoint(CommentAdapter.this.mContext, false, 1, item.id);
                    }
                }
            });
        } else {
            ((MessageCommentItemBinding) holder.binding).atvReplyCommentDetail.setVisibility(8);
            ((MessageCommentItemBinding) holder.binding).rlOriginalReplyContainer.setBackground(null);
        }
    }

    public MessageCommentItemBinding getBinding(ViewGroup parent, int viewType) {
        return MessageCommentItemBinding.inflate(LayoutInflater.from(this.mContext));
    }

    public void dataBindingTo(MessageCommentItemBinding messageCommentItemBinding, CommentInfo commentInfo) {
        messageCommentItemBinding.setCommentInfo(commentInfo);
        messageCommentItemBinding.setNType(Integer.valueOf(this.mCommentType));
    }

    private int getY(View view) {
        int[] rect = new int[2];
        view.getLocationOnScreen(rect);
        return rect[1];
    }
}
