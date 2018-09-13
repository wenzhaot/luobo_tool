package com.feng.car.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import com.feng.car.R;
import com.feng.car.databinding.SearchConditionChildItemLayoutBinding;
import com.feng.car.entity.searchcar.SearchCarBean;
import com.feng.car.entity.searchcar.SearchCarGroup;
import com.feng.car.event.SearchConditionEvent;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.SearchCarManager;
import com.feng.car.view.flowlayout.DLFlowLayout$OnSelectListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class SearchCarsConditionChildAdapter extends MvvmBaseAdapter<SearchCarBean, SearchConditionChildItemLayoutBinding> {
    private SearchCarGroup mGroupInfo;
    private LayoutParams mParams;
    private int mTvWidth = ((FengUtil.getScreenWidth(this.mContext) - (this.mContext.getResources().getDimensionPixelSize(R.dimen.default_32PX) * 5)) / 4);

    public SearchCarsConditionChildAdapter(Context context, List<SearchCarBean> list, SearchCarGroup groupInfo) {
        super(context, list);
        this.mGroupInfo = groupInfo;
    }

    public void onBaseBindViewHolder(final MvvmViewHolder<SearchConditionChildItemLayoutBinding> holder, int position) {
        final SearchCarBean info = (SearchCarBean) this.mList.get(position);
        if (info.subList.size() > 0) {
            ((SearchConditionChildItemLayoutBinding) holder.binding).childFlowlayout.setVisibility(0);
            ((SearchConditionChildItemLayoutBinding) holder.binding).tvConditionName.setBackgroundResource(R.drawable.bg_f3f3f3_border_left_right);
            ((SearchConditionChildItemLayoutBinding) holder.binding).childFlowlayout.setType(SearchCarManager.newInstance().getPriceType());
            ((SearchConditionChildItemLayoutBinding) holder.binding).childFlowlayout.setCountPerLine(info.countPerLine);
            ((SearchConditionChildItemLayoutBinding) holder.binding).childFlowlayout.setIsSingle(info.isSingleSelect);
            ((SearchConditionChildItemLayoutBinding) holder.binding).childFlowlayout.setFlowData(info.subList);
            ((SearchConditionChildItemLayoutBinding) holder.binding).childFlowlayout.setCanCancelAll(true);
            ((SearchConditionChildItemLayoutBinding) holder.binding).childFlowlayout.setOnSelectListener(new DLFlowLayout$OnSelectListener() {
                public void onSelect(int position) {
                    SearchCarsConditionChildAdapter.this.mGroupInfo.selectThirdCondition((SearchCarBean) info.subList.get(position));
                    SearchCarsConditionChildAdapter.this.notifyDataSetChanged();
                    EventBus.getDefault().post(new SearchConditionEvent());
                }

                public void onUnSelect(int position) {
                    SearchCarsConditionChildAdapter.this.mGroupInfo.cancelThirdCondition((SearchCarBean) info.subList.get(position));
                    SearchCarsConditionChildAdapter.this.notifyDataSetChanged();
                    EventBus.getDefault().post(new SearchConditionEvent());
                }

                public void onOutLimit() {
                }
            });
            ((SearchConditionChildItemLayoutBinding) holder.binding).tvConditionName.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                }
            });
        } else {
            ((SearchConditionChildItemLayoutBinding) holder.binding).childFlowlayout.setVisibility(8);
            if (info.isChecked) {
                ((SearchConditionChildItemLayoutBinding) holder.binding).tvConditionName.setBackgroundResource(SearchCarManager.newInstance().getPriceType() == SearchCarManager.GUIDE_PRICE_TYPE ? R.drawable.bg_fff9e9_border_ffb90a_shape : R.drawable.bg_e8f6ff_border_33a4f7_shape);
                ((SearchConditionChildItemLayoutBinding) holder.binding).tvConditionName.setSelected(true);
            } else {
                ((SearchConditionChildItemLayoutBinding) holder.binding).tvConditionName.setBackgroundResource(R.drawable.bg_ffffff_border_d1d1d1_shape);
                ((SearchConditionChildItemLayoutBinding) holder.binding).tvConditionName.setSelected(false);
            }
            ((SearchConditionChildItemLayoutBinding) holder.binding).tvConditionName.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (info.isChecked) {
                        info.isChecked = false;
                        ((SearchConditionChildItemLayoutBinding) holder.binding).tvConditionName.setBackgroundResource(R.drawable.bg_ffffff_border_d1d1d1_shape);
                        ((SearchConditionChildItemLayoutBinding) holder.binding).tvConditionName.setSelected(false);
                        SearchCarsConditionChildAdapter.this.mGroupInfo.cancelSecondCondition(info);
                        SearchCarsConditionChildAdapter.this.notifyDataSetChanged();
                    } else {
                        info.isChecked = true;
                        ((SearchConditionChildItemLayoutBinding) holder.binding).tvConditionName.setBackgroundResource(SearchCarManager.newInstance().getPriceType() == SearchCarManager.GUIDE_PRICE_TYPE ? R.drawable.bg_fff9e9_border_ffb90a_shape : R.drawable.bg_e8f6ff_border_33a4f7_shape);
                        ((SearchConditionChildItemLayoutBinding) holder.binding).tvConditionName.setSelected(true);
                        SearchCarsConditionChildAdapter.this.mGroupInfo.selectSecondCondition(info);
                        SearchCarsConditionChildAdapter.this.notifyDataSetChanged();
                    }
                    SearchCarsConditionChildAdapter.this.notifyDataSetChanged();
                    EventBus.getDefault().post(new SearchConditionEvent());
                }
            });
        }
        ((SearchConditionChildItemLayoutBinding) holder.binding).tvConditionName.setText(info.name);
        this.mParams = (LayoutParams) ((SearchConditionChildItemLayoutBinding) holder.binding).tvConditionName.getLayoutParams();
        this.mParams.width = this.mTvWidth;
        ((SearchConditionChildItemLayoutBinding) holder.binding).tvConditionName.setLayoutParams(this.mParams);
    }

    public SearchConditionChildItemLayoutBinding getBinding(ViewGroup parent, int viewType) {
        return SearchConditionChildItemLayoutBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(SearchConditionChildItemLayoutBinding layoutBinding, SearchCarBean info) {
    }
}
