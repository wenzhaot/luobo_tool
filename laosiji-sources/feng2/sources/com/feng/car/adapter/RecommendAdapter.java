package com.feng.car.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import com.feng.car.R;
import com.feng.car.databinding.ArticleRecommendItemBinding;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.utils.FengUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;

public class RecommendAdapter extends MvvmBaseAdapter<SnsInfo, ArticleRecommendItemBinding> {
    private int m24;
    private LayoutParams mParams;
    private SnsInfo mSnsInfo;

    public void setSnsInfo(SnsInfo snsInfo) {
        this.mSnsInfo = snsInfo;
    }

    public RecommendAdapter(Context context, List<SnsInfo> list) {
        super(context, list);
        this.m24 = 0;
        this.m24 = this.mContext.getResources().getDimensionPixelSize(R.dimen.default_24PX);
    }

    public ArticleRecommendItemBinding getBinding(ViewGroup parent, int viewType) {
        return ArticleRecommendItemBinding.inflate(LayoutInflater.from(this.mContext), parent, false);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<ArticleRecommendItemBinding> holder, int position) {
        if (position == 0) {
            ((ArticleRecommendItemBinding) holder.binding).divider.setVisibility(8);
            this.mParams = (LayoutParams) ((ArticleRecommendItemBinding) holder.binding).image.getLayoutParams();
            this.mParams.setMargins(0, 0, 0, this.m24);
        } else {
            ((ArticleRecommendItemBinding) holder.binding).divider.setVisibility(0);
            this.mParams = (LayoutParams) ((ArticleRecommendItemBinding) holder.binding).image.getLayoutParams();
            this.mParams.setMargins(0, this.m24, 0, this.m24);
        }
        ((ArticleRecommendItemBinding) holder.binding).image.setLayoutParams(this.mParams);
        final SnsInfo snsInfo = (SnsInfo) this.mList.get(position);
        ((ArticleRecommendItemBinding) holder.binding).image.setAutoImageURI(Uri.parse(FengUtil.getUniformScaleUrl(snsInfo.image, Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 0.56f)));
        ((ArticleRecommendItemBinding) holder.binding).title.setText((CharSequence) snsInfo.title.get());
        if (snsInfo.id == this.mSnsInfo.id) {
            ((ArticleRecommendItemBinding) holder.binding).title.setTextColor(this.mContext.getResources().getColor(R.color.color_33A4F7));
        } else {
            ((ArticleRecommendItemBinding) holder.binding).title.setTextColor(this.mContext.getResources().getColor(R.color.color_87_000000));
        }
        ((ArticleRecommendItemBinding) holder.binding).userText.setText((CharSequence) snsInfo.user.name.get());
        ((ArticleRecommendItemBinding) holder.binding).infoText.setText(getSnsInfo(snsInfo));
        ((ArticleRecommendItemBinding) holder.binding).parent.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (snsInfo.snstype == 0) {
                    snsInfo.intentToArticle(RecommendAdapter.this.mContext, -1);
                } else if (snsInfo.snstype == 1) {
                } else {
                    if (snsInfo.snstype == 9 || snsInfo.snstype == 10) {
                        snsInfo.intentToViewPoint(RecommendAdapter.this.mContext, false);
                    }
                }
            }
        });
    }

    private String getSnsInfo(SnsInfo snsInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("评论");
        sb.append(FengUtil.numberFormat(snsInfo.commentcount.get()));
        sb.append("    ");
        sb.append("赞");
        sb.append(FengUtil.numberFormat(snsInfo.praisecount.get()));
        return sb.toString();
    }

    public void dataBindingTo(ArticleRecommendItemBinding articleRecommendItemBinding, SnsInfo info) {
    }
}
