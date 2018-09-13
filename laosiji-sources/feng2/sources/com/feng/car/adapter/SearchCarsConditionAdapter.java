package com.feng.car.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.feng.car.R;
import com.feng.car.activity.BaseActivity;
import com.feng.car.databinding.PriceDialogLayoutBinding;
import com.feng.car.databinding.SearchConditionItemLayoutBinding;
import com.feng.car.entity.searchcar.SearchCarBean;
import com.feng.car.entity.searchcar.SearchCarGroup;
import com.feng.car.event.SearchConditionEvent;
import com.feng.car.utils.SearchCarManager;
import com.feng.car.view.CommonDialog;
import com.feng.car.view.flowlayout.DLFlowLayout$FlowClickListener;
import com.feng.car.view.flowlayout.DLFlowLayout$OnSelectListener;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class SearchCarsConditionAdapter extends MvvmBaseAdapter<SearchCarGroup, SearchConditionItemLayoutBinding> {
    private boolean mCouldClickPriceConfirm = false;
    private Dialog mPriceDialog;
    private PriceDialogLayoutBinding mPriceLayoutBinding;

    public SearchCarsConditionAdapter(Context context, List<SearchCarGroup> list) {
        super(context, list);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<SearchConditionItemLayoutBinding> holder, int position) {
        final SearchCarGroup groupInfo = (SearchCarGroup) this.mList.get(position);
        if (position == 0) {
            ((SearchConditionItemLayoutBinding) holder.binding).vLine.setVisibility(8);
        } else {
            ((SearchConditionItemLayoutBinding) holder.binding).vLine.setVisibility(0);
        }
        if (groupInfo.hasThreeLevel()) {
            ((SearchConditionItemLayoutBinding) holder.binding).recyclerview.setVisibility(0);
            ((SearchConditionItemLayoutBinding) holder.binding).flowlayout.setVisibility(8);
            ((SearchConditionItemLayoutBinding) holder.binding).recyclerview.setAdapter(new SearchCarsConditionChildAdapter(this.mContext, groupInfo.searchBeanList, groupInfo));
            ((SearchConditionItemLayoutBinding) holder.binding).recyclerview.setLayoutManager(new LinearLayoutManager(this.mContext));
        } else {
            ((SearchConditionItemLayoutBinding) holder.binding).recyclerview.setVisibility(8);
            ((SearchConditionItemLayoutBinding) holder.binding).flowlayout.setVisibility(0);
            ((SearchConditionItemLayoutBinding) holder.binding).flowlayout.setType(SearchCarManager.newInstance().getPriceType());
            ((SearchConditionItemLayoutBinding) holder.binding).flowlayout.setIsSingle(groupInfo.isSingleSelect);
            ((SearchConditionItemLayoutBinding) holder.binding).flowlayout.setCountPerLine(groupInfo.countPerLine);
            ((SearchConditionItemLayoutBinding) holder.binding).flowlayout.setCanCancelAll(true);
            ((SearchConditionItemLayoutBinding) holder.binding).flowlayout.setFlowData(groupInfo.searchBeanList);
            ((SearchConditionItemLayoutBinding) holder.binding).flowlayout.setOnSelectListener(new DLFlowLayout$OnSelectListener() {
                public void onSelect(int position) {
                    for (SearchCarBean bean : groupInfo.searchBeanList) {
                        if (bean.position == position) {
                            groupInfo.selectSecondCondition(bean);
                            SearchCarsConditionAdapter.this.notifyDataSetChanged();
                            EventBus.getDefault().post(new SearchConditionEvent());
                            return;
                        }
                    }
                }

                public void onUnSelect(int position) {
                    for (SearchCarBean bean : groupInfo.searchBeanList) {
                        if (bean.position == position) {
                            groupInfo.cancelSecondCondition(bean);
                            SearchCarsConditionAdapter.this.notifyDataSetChanged();
                            EventBus.getDefault().post(new SearchConditionEvent());
                            return;
                        }
                    }
                }

                public void onOutLimit() {
                }
            });
            ((SearchConditionItemLayoutBinding) holder.binding).flowlayout.setFlowClickListener(new DLFlowLayout$FlowClickListener() {
                public void onFLowClick(SearchCarBean bean, TextView textView) {
                    if (bean.name.equals("自定义")) {
                        SearchCarsConditionAdapter.this.showPriceDialog(bean);
                    }
                }
            });
        }
        ((SearchConditionItemLayoutBinding) holder.binding).tvGroupName.setText(groupInfo.groupName);
    }

    public SearchConditionItemLayoutBinding getBinding(ViewGroup parent, int viewType) {
        return SearchConditionItemLayoutBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(SearchConditionItemLayoutBinding layoutBinding, SearchCarGroup info) {
    }

    private void showPriceDialog(SearchCarBean priceBean) {
        if (this.mPriceLayoutBinding == null) {
            this.mPriceLayoutBinding = PriceDialogLayoutBinding.inflate(LayoutInflater.from(this.mContext));
            this.mPriceLayoutBinding.minPrice.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (StringUtil.isEmpty(s.toString()) && StringUtil.isEmpty(SearchCarsConditionAdapter.this.mPriceLayoutBinding.maxPrice.getText().toString())) {
                        SearchCarsConditionAdapter.this.mCouldClickPriceConfirm = false;
                    } else {
                        SearchCarsConditionAdapter.this.mCouldClickPriceConfirm = true;
                    }
                    SearchCarsConditionAdapter.this.setConfirmClickable(SearchCarsConditionAdapter.this.mCouldClickPriceConfirm);
                }

                public void afterTextChanged(Editable s) {
                }
            });
            this.mPriceLayoutBinding.maxPrice.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (StringUtil.isEmpty(s.toString()) && StringUtil.isEmpty(SearchCarsConditionAdapter.this.mPriceLayoutBinding.minPrice.getText().toString())) {
                        SearchCarsConditionAdapter.this.mCouldClickPriceConfirm = false;
                    } else {
                        SearchCarsConditionAdapter.this.mCouldClickPriceConfirm = true;
                    }
                    SearchCarsConditionAdapter.this.setConfirmClickable(SearchCarsConditionAdapter.this.mCouldClickPriceConfirm);
                }

                public void afterTextChanged(Editable s) {
                }
            });
            this.mPriceLayoutBinding.confirm.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (SearchCarsConditionAdapter.this.mCouldClickPriceConfirm) {
                        int min = 0;
                        int max = 0;
                        String minStr = SearchCarsConditionAdapter.this.mPriceLayoutBinding.minPrice.getText().toString();
                        String maxStr = SearchCarsConditionAdapter.this.mPriceLayoutBinding.maxPrice.getText().toString();
                        if (!StringUtil.isEmpty(minStr)) {
                            min = Integer.parseInt(SearchCarsConditionAdapter.this.mPriceLayoutBinding.minPrice.getText().toString());
                        }
                        if (!StringUtil.isEmpty(maxStr)) {
                            max = Integer.parseInt(SearchCarsConditionAdapter.this.mPriceLayoutBinding.maxPrice.getText().toString());
                        }
                        if (max == 0 || min < max) {
                            SearchCarManager.newInstance().setCustomPrice(min, max);
                            EventBus.getDefault().post(new SearchConditionEvent());
                            SearchCarsConditionAdapter.this.mPriceDialog.dismiss();
                            return;
                        }
                        ((BaseActivity) SearchCarsConditionAdapter.this.mContext).showThirdTypeToast((int) R.string.maxprice_must_higher_minprice);
                    }
                }
            });
        }
        if (this.mPriceDialog == null) {
            this.mPriceDialog = CommonDialog.showViewDialog(this.mContext, this.mPriceLayoutBinding.getRoot());
        }
        SearchCarBean currentBean = SearchCarManager.newInstance().getCurrentPriceValue();
        if (currentBean.name.equals("自定义")) {
            int min = currentBean.min;
            int max = currentBean.max;
            if (min == 0) {
                this.mPriceLayoutBinding.minPrice.setText("");
            } else {
                this.mPriceLayoutBinding.minPrice.setText(String.valueOf(min));
            }
            if (max == 0) {
                this.mPriceLayoutBinding.maxPrice.setText("");
            } else {
                this.mPriceLayoutBinding.maxPrice.setText(String.valueOf(max));
            }
        } else {
            this.mPriceLayoutBinding.maxPrice.setText("");
            this.mPriceLayoutBinding.minPrice.setText("");
        }
        this.mPriceDialog.show();
    }

    private void setConfirmClickable(boolean flag) {
        if (flag) {
            this.mPriceLayoutBinding.confirm.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_87_000000));
        } else {
            this.mPriceLayoutBinding.confirm.setTextColor(ContextCompat.getColor(this.mContext, R.color.color_38_000000));
        }
    }
}
