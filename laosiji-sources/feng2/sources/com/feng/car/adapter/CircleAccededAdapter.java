package com.feng.car.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.ItemCircleAccededBinding;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.utils.FengUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;

public class CircleAccededAdapter extends MvvmBaseAdapter<CircleInfo, ItemCircleAccededBinding> {
    public static int ALL_TYPE = 1;
    public static int NORMAL_TYPE = 0;
    private int m140;
    private boolean mShowSel;
    private int mType;

    public CircleAccededAdapter(Context context, List<CircleInfo> list, int type) {
        super(context, list);
        this.mType = NORMAL_TYPE;
        this.mType = type;
        this.m140 = this.mContext.getResources().getDimensionPixelSize(R.dimen.default_140PX);
    }

    public CircleAccededAdapter(Context context, List<CircleInfo> mList, boolean showSel) {
        super(context, mList);
        this.mType = NORMAL_TYPE;
        this.m140 = this.mContext.getResources().getDimensionPixelSize(R.dimen.default_140PX);
        this.mShowSel = showSel;
    }

    public void onBaseBindViewHolder(MvvmViewHolder<ItemCircleAccededBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        final CircleInfo info = (CircleInfo) this.mList.get(position);
        ((ItemCircleAccededBinding) holder.binding).afdCircle.setAutoImageURI(Uri.parse(FengUtil.getFixedCircleUrl(info.image, this.m140, this.m140)));
        ((ItemCircleAccededBinding) holder.binding).tvCircleName.setText(info.getCircleName());
        if (!this.mShowSel) {
            ((ItemCircleAccededBinding) holder.binding).ivCircleAccede.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    info.accedeSingleOperation(CircleAccededAdapter.this.mContext, null);
                }
            });
        }
    }

    public ItemCircleAccededBinding getBinding(ViewGroup parent, int viewType) {
        return ItemCircleAccededBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(ItemCircleAccededBinding itemCircleAccededBinding, CircleInfo info) {
        itemCircleAccededBinding.setInfo(info);
        itemCircleAccededBinding.setMIsShowSel(Boolean.valueOf(this.mShowSel));
    }
}
