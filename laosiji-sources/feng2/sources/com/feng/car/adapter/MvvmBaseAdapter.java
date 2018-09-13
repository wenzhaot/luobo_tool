package com.feng.car.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.List;

public abstract class MvvmBaseAdapter<T, V extends ViewDataBinding> extends Adapter<MvvmViewHolder<V>> {
    protected Context mContext;
    protected LayoutInflater mInflater = LayoutInflater.from(this.mContext);
    protected List<T> mList;
    private OnItemClickListener mOnItemClickListener;

    public abstract void dataBindingTo(V v, T t);

    public abstract V getBinding(ViewGroup viewGroup, int i);

    public void setOnItemClickLister(OnItemClickListener lister) {
        this.mOnItemClickListener = lister;
    }

    public MvvmBaseAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mList = list;
    }

    public MvvmViewHolder<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MvvmViewHolder(getBinding(parent, viewType));
    }

    public void onBindViewHolder(final MvvmViewHolder<V> holder, final int position) {
        if (this.mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    MvvmBaseAdapter.this.mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
        onBaseBindViewHolder(holder, position);
        bindTo(holder.binding, this.mList.get(position));
    }

    public int getItemCount() {
        return this.mList != null ? this.mList.size() : 0;
    }

    public void onBaseBindViewHolder(MvvmViewHolder<V> mvvmViewHolder, int position) {
    }

    private void bindTo(V v, T t) {
        dataBindingTo(v, t);
        v.executePendingBindings();
    }

    public void refreshData() {
        notifyDataSetChanged();
    }
}
