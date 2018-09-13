package com.feng.car.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.databinding.SearchCarsConditionLayoutBinding;
import com.feng.car.entity.searchcar.SearchCarBean;
import java.util.List;

public class CarsConditionAdapter extends MvvmBaseAdapter<SearchCarBean, SearchCarsConditionLayoutBinding> {
    private int m32 = this.mContext.getResources().getDimensionPixelSize(R.dimen.default_32PX);
    private LayoutParams mParams;

    public CarsConditionAdapter(Context context, List<SearchCarBean> list) {
        super(context, list);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<SearchCarsConditionLayoutBinding> holder, int position) {
        this.mParams = (LayoutParams) ((SearchCarsConditionLayoutBinding) holder.binding).tvConditionName.getLayoutParams();
        if (position == 0) {
            this.mParams.leftMargin = this.m32;
        } else {
            this.mParams.leftMargin = 0;
        }
        this.mParams.topMargin = this.m32;
        this.mParams.bottomMargin = this.m32;
        ((SearchCarsConditionLayoutBinding) holder.binding).tvConditionName.setLayoutParams(this.mParams);
    }

    public SearchCarsConditionLayoutBinding getBinding(ViewGroup parent, int viewType) {
        return SearchCarsConditionLayoutBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(SearchCarsConditionLayoutBinding layoutBinding, SearchCarBean info) {
        layoutBinding.setCarBean(info);
    }
}
