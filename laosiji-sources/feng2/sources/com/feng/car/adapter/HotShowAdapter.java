package com.feng.car.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.ItemHotShowBinding;
import com.feng.car.entity.hotshow.HotShowInfo;
import com.feng.car.utils.FengUtil;
import java.util.List;

public class HotShowAdapter extends MvvmBaseAdapter<HotShowInfo, ItemHotShowBinding> {
    private int m11;
    private int m20;
    private int m32;
    private int m40;

    public HotShowAdapter(Context context, List<HotShowInfo> list) {
        super(context, list);
        Resources resources = context.getResources();
        this.m32 = resources.getDimensionPixelSize(R.dimen.default_32PX);
        this.m11 = resources.getDimensionPixelSize(R.dimen.default_11PX);
        this.m40 = resources.getDimensionPixelSize(R.dimen.default_40PX);
        this.m20 = resources.getDimensionPixelSize(R.dimen.default_20PX);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<ItemHotShowBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        HotShowInfo info = (HotShowInfo) this.mList.get(position);
        ((ItemHotShowBinding) holder.binding).afdImage.setAutoImageURI(Uri.parse(FengUtil.getUniformScaleUrl(info.image, 350, 0.56f)));
        if (position % 2 == 0) {
            ((ItemHotShowBinding) holder.binding).llParent.setPadding(this.m32, position == 0 ? this.m40 : this.m20, this.m11, this.m20);
        } else {
            ((ItemHotShowBinding) holder.binding).llParent.setPadding(this.m11, position == 1 ? this.m40 : this.m20, this.m32, this.m20);
        }
        ((ItemHotShowBinding) holder.binding).tvProgramNum.setText("共有" + FengUtil.numberFormat(info.hotshowcount) + "条内容  阅读" + FengUtil.numberFormat(info.clickcount.get()));
    }

    public ItemHotShowBinding getBinding(ViewGroup parent, int viewType) {
        return ItemHotShowBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(ItemHotShowBinding itemHotShowBinding, HotShowInfo info) {
        itemHotShowBinding.setMHotShowInfo(info);
    }
}
