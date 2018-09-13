package com.feng.car.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.R;
import com.feng.car.activity.MoreConditionActivity;
import com.feng.car.databinding.SearchCarItemBinding;
import com.feng.car.entity.searchcar.SearchCarGroup;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.SearchCarManager;
import com.feng.car.view.flowlayout.DLFlowLayout$FlowDataSetListener;
import com.feng.car.view.flowlayout.DLFlowLayout$OnSelectListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;

public class SearchCarAdapter extends MvvmBaseAdapter<SearchCarGroup, SearchCarItemBinding> {
    private ConditionChangeListener mConditionChangeListener;
    private int mType = 0;
    private int mWidth = (FengUtil.getScreenWidth(this.mContext) - this.mContext.getResources().getDimensionPixelSize(R.dimen.default_172PX));

    public interface ConditionChangeListener {
        void onChange();
    }

    public void setType(int type) {
        this.mType = type;
        notifyDataSetChanged();
    }

    public void setConditionChangeListener(ConditionChangeListener l) {
        this.mConditionChangeListener = l;
    }

    public SearchCarAdapter(Context context, List<SearchCarGroup> list) {
        super(context, list);
    }

    public SearchCarItemBinding getBinding(ViewGroup parent, int viewType) {
        return SearchCarItemBinding.inflate(this.mInflater, parent, false);
    }

    public void dataBindingTo(SearchCarItemBinding searchCarItemBinding, SearchCarGroup searchCarItem) {
    }

    public void onBaseBindViewHolder(final MvvmViewHolder<SearchCarItemBinding> holder, int position) {
        super.onBaseBindViewHolder(holder, position);
        holder.setIsRecyclable(false);
        final SearchCarGroup group = (SearchCarGroup) this.mList.get(position);
        if (group != null) {
            if (!group.groupName.equals("价格区间")) {
                ((SearchCarItemBinding) holder.binding).groupName.setText(group.groupName);
            } else if (this.mType == SearchCarManager.GUIDE_PRICE_TYPE) {
                ((SearchCarItemBinding) holder.binding).groupName.setText("指导价区间");
            } else {
                ((SearchCarItemBinding) holder.binding).groupName.setText("成交价区间");
            }
            if (group.hasExpendTr) {
                ((SearchCarItemBinding) holder.binding).expendTr.setVisibility(0);
                if (group.isTakeUp) {
                    ((SearchCarItemBinding) holder.binding).flowlayout.setVisibility(8);
                } else {
                    ((SearchCarItemBinding) holder.binding).flowlayout.setVisibility(0);
                }
                ((SearchCarItemBinding) holder.binding).nameLine.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        if (group.isTakeUp) {
                            ((SearchCarItemBinding) holder.binding).flowlayout.setVisibility(0);
                            ((SearchCarItemBinding) holder.binding).expendTr.setImageResource(R.drawable.icon_tr_up);
                            group.isTakeUp = false;
                            return;
                        }
                        ((SearchCarItemBinding) holder.binding).flowlayout.setVisibility(8);
                        ((SearchCarItemBinding) holder.binding).expendTr.setImageResource(R.drawable.icon_tr_down);
                        group.isTakeUp = true;
                    }
                });
                ((SearchCarItemBinding) holder.binding).expendTr.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        if (group.isTakeUp) {
                            ((SearchCarItemBinding) holder.binding).flowlayout.setVisibility(0);
                            ((SearchCarItemBinding) holder.binding).expendTr.setImageResource(R.drawable.icon_tr_up);
                            group.isTakeUp = false;
                            return;
                        }
                        ((SearchCarItemBinding) holder.binding).flowlayout.setVisibility(8);
                        ((SearchCarItemBinding) holder.binding).expendTr.setImageResource(R.drawable.icon_tr_down);
                        group.isTakeUp = true;
                    }
                });
            } else {
                ((SearchCarItemBinding) holder.binding).expendTr.setVisibility(8);
            }
            ((SearchCarItemBinding) holder.binding).flowlayout.setTag(Integer.valueOf(position));
            ((SearchCarItemBinding) holder.binding).flowlayout.setWidth(this.mWidth);
            ((SearchCarItemBinding) holder.binding).flowlayout.setType(this.mType);
            ((SearchCarItemBinding) holder.binding).flowlayout.setMar(this.mContext.getResources().getDimensionPixelSize(R.dimen.default_25PX));
            ((SearchCarItemBinding) holder.binding).flowlayout.setCountPerLine(group.countPerLine);
            ((SearchCarItemBinding) holder.binding).flowlayout.setIsSingle(group.isSingleSelect);
            ((SearchCarItemBinding) holder.binding).flowlayout.setDataSetListener(new DLFlowLayout$FlowDataSetListener() {
                public void onDataSetFinish() {
                }
            });
            ((SearchCarItemBinding) holder.binding).flowlayout.setFlowData(group.beanList);
            ((SearchCarItemBinding) holder.binding).flowlayout.setFlowClickListener((MoreConditionActivity) this.mContext);
            ((SearchCarItemBinding) holder.binding).flowlayout.setOnSelectListener(new DLFlowLayout$OnSelectListener() {
                public void onSelect(int p) {
                    if (SearchCarAdapter.this.mConditionChangeListener != null) {
                        SearchCarAdapter.this.mConditionChangeListener.onChange();
                    }
                }

                public void onUnSelect(int p) {
                    if (SearchCarAdapter.this.mConditionChangeListener != null) {
                        SearchCarAdapter.this.mConditionChangeListener.onChange();
                    }
                }

                public void onOutLimit() {
                }
            });
            if (position == this.mList.size() - 1) {
                ((SearchCarItemBinding) holder.binding).divider.setVisibility(8);
            } else {
                ((SearchCarItemBinding) holder.binding).divider.setVisibility(0);
            }
        }
    }
}
