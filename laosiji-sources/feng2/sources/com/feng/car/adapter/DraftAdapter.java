package com.feng.car.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.CommonPostItemLayoutBinding;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.utils.FengUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;

public class DraftAdapter extends MvvmBaseAdapter<SnsInfo, CommonPostItemLayoutBinding> {
    private int m24;
    private int m34;
    public OnDraftItemDeleteListener mDraftItemDeleteListener;
    private LayoutParams mImageParams;
    private int mMaxHeight = 0;
    private int mMinHeight = 0;
    private RecyclerView.LayoutParams mParentParams;
    private int mViewWidth = 0;

    public interface OnDraftItemDeleteListener {
        void onDraftDel(SnsInfo snsInfo, int i);
    }

    public void setOnDraftItemDeleteListener(OnDraftItemDeleteListener listener) {
        this.mDraftItemDeleteListener = listener;
    }

    public DraftAdapter(Context context, List<SnsInfo> list) {
        super(context, list);
        Resources resources = this.mContext.getResources();
        this.mViewWidth = (FengUtil.getScreenWidth(this.mContext) - resources.getDimensionPixelSize(R.dimen.default_68PX)) / 2;
        this.m24 = resources.getDimensionPixelSize(R.dimen.default_24PX);
        this.m34 = resources.getDimensionPixelSize(R.dimen.default_34PX);
        this.mMaxHeight = (int) (((float) (this.mViewWidth * 4)) / 3.0f);
        this.mMinHeight = (int) (((float) (this.mViewWidth * 3)) / 4.0f);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<CommonPostItemLayoutBinding> holder, final int position) {
        final SnsInfo info = (SnsInfo) this.mList.get(position);
        info.user.name.set(FengApplication.getInstance().getUserInfo().name.get());
        ((CommonPostItemLayoutBinding) holder.binding).ivArrowDown.setVisibility(8);
        ImageInfo imageInfo = info.image;
        if (info.snstype == 1 || info.snstype == 0) {
            imageInfo = info.image;
        }
        ((CommonPostItemLayoutBinding) holder.binding).getRoot().setTag(R.string.feed_item_space_tag, this.mContext.getString(R.string.feed_item_space_tag));
        ((CommonPostItemLayoutBinding) holder.binding).llTag.setVisibility(8);
        if (info.isonlyvideo == 1) {
            ((CommonPostItemLayoutBinding) holder.binding).ivVideoTime.setVisibility(0);
        } else {
            ((CommonPostItemLayoutBinding) holder.binding).ivVideoTime.setVisibility(8);
        }
        if (TextUtils.isEmpty(info.getDraftFeedTitleOrDes())) {
            ((CommonPostItemLayoutBinding) holder.binding).atDigestDescription.setVisibility(8);
        } else {
            ((CommonPostItemLayoutBinding) holder.binding).atDigestDescription.setVisibility(0);
        }
        if (info.isHasText()) {
            ((CommonPostItemLayoutBinding) holder.binding).atDigestDescription.setMaxLines(5);
        } else {
            ((CommonPostItemLayoutBinding) holder.binding).atDigestDescription.setMaxLines(3);
        }
        ((CommonPostItemLayoutBinding) holder.binding).adUserImage.setHeadUrl(FengUtil.getHeadImageUrl(FengApplication.getInstance().getUserInfo().getHeadImageInfo()));
        this.mImageParams = (LayoutParams) ((CommonPostItemLayoutBinding) holder.binding).adCover.getLayoutParams();
        this.mImageParams.width = this.mViewWidth;
        this.mImageParams.height = getImageHeight(imageInfo);
        ((CommonPostItemLayoutBinding) holder.binding).adCover.setLayoutParams(this.mImageParams);
        ((CommonPostItemLayoutBinding) holder.binding).adCover.setAutoImageURI(Uri.parse(FengUtil.getUniformScaleUrl(imageInfo, 350)));
        ((CommonPostItemLayoutBinding) holder.binding).atDigestDescription.setMatchLink(false);
        ((CommonPostItemLayoutBinding) holder.binding).atDigestDescription.setContent(info.getDraftFeedTitleOrDes(), false);
        ((CommonPostItemLayoutBinding) holder.binding).llOperation.setClickable(true);
        ((CommonPostItemLayoutBinding) holder.binding).ivSelectDel.setVisibility(0);
        ((CommonPostItemLayoutBinding) holder.binding).ivGood.setVisibility(8);
        ((CommonPostItemLayoutBinding) holder.binding).tvGoodNum.setVisibility(8);
        ((CommonPostItemLayoutBinding) holder.binding).ivSelectDel.setImageResource(R.drawable.icon_record_del);
        ((CommonPostItemLayoutBinding) holder.binding).ivSelectDel.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (DraftAdapter.this.mDraftItemDeleteListener != null) {
                    DraftAdapter.this.mDraftItemDeleteListener.onDraftDel(info, position);
                }
            }
        });
        this.mParentParams = new RecyclerView.LayoutParams(this.mViewWidth + this.m34, -2);
        if (position == 0 || position == 1) {
            this.mParentParams.setMargins(0, this.m24, 0, 0);
        } else {
            this.mParentParams.setMargins(0, 0, 0, 0);
        }
        ((CommonPostItemLayoutBinding) holder.binding).rlParent.setLayoutParams(this.mParentParams);
    }

    public CommonPostItemLayoutBinding getBinding(ViewGroup parent, int viewType) {
        return CommonPostItemLayoutBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(CommonPostItemLayoutBinding commonPostItemLayoutBinding, SnsInfo info) {
        commonPostItemLayoutBinding.setSnsInfo(info);
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
}
