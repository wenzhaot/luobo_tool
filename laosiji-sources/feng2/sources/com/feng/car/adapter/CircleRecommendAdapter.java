package com.feng.car.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.CircleRecommendItemBinding;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.utils.FengUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;

public class CircleRecommendAdapter extends MvvmBaseAdapter<CircleInfo, CircleRecommendItemBinding> {
    private int m140 = this.mContext.getResources().getDimensionPixelSize(R.dimen.default_140PX);
    private CircleSelectListener mCircleSelectListener;

    public interface CircleSelectListener {
        void onCircleSelect(int i);
    }

    public CircleRecommendAdapter(Context context, List<CircleInfo> list) {
        super(context, list);
    }

    public CircleRecommendItemBinding getBinding(ViewGroup parent, int viewType) {
        return CircleRecommendItemBinding.inflate(LayoutInflater.from(this.mContext), parent, false);
    }

    public void dataBindingTo(CircleRecommendItemBinding circleRecommendItemBinding, CircleInfo circleInfo) {
    }

    public void onBaseBindViewHolder(MvvmViewHolder<CircleRecommendItemBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        final CircleInfo circleInfo = (CircleInfo) this.mList.get(position);
        ((CircleRecommendItemBinding) holder.binding).image.setAutoImageURI(Uri.parse(FengUtil.getFixedCircleUrl(circleInfo.image, this.m140, this.m140)));
        ((CircleRecommendItemBinding) holder.binding).image.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                circleInfo.intentToCircleFinalPage(CircleRecommendAdapter.this.mContext);
            }
        });
        ((CircleRecommendItemBinding) holder.binding).name.setText(circleInfo.name);
        if (circleInfo.isfans.get() == 0) {
            ((CircleRecommendItemBinding) holder.binding).button.setImageResource(R.drawable.icon_circle_unselect);
        } else {
            ((CircleRecommendItemBinding) holder.binding).button.setImageResource(R.drawable.icon_circle_select);
        }
        ((CircleRecommendItemBinding) holder.binding).button.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (circleInfo.isfans.get() == 0) {
                    circleInfo.isfans.set(1);
                } else {
                    circleInfo.isfans.set(0);
                }
                CircleRecommendAdapter.this.notifyDataSetChanged();
                if (CircleRecommendAdapter.this.mCircleSelectListener != null) {
                    CircleRecommendAdapter.this.mCircleSelectListener.onCircleSelect(circleInfo.id);
                }
            }
        });
    }

    public void setCircleSelectListener(CircleSelectListener l) {
        this.mCircleSelectListener = l;
    }
}
