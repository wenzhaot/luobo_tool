package com.feng.car.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.SearchDefaultItemBinding;
import com.feng.car.db.SparkDB;
import com.feng.car.entity.search.SearchItem;
import com.feng.car.event.SearchRecordEvent;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class SearchRecordAdapter extends Adapter<SearchHolder> {
    private Context mContext;
    private boolean mIsClearAll = false;
    private List<SearchItem> mList;
    private List<SearchItem> mSearchList;
    private SparkDB mSparkDB;

    public class SearchHolder extends ViewHolder {
        public SearchDefaultItemBinding binding;

        public SearchHolder(SearchDefaultItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public SearchRecordAdapter(Context context, List<SearchItem> list) {
        this.mContext = context;
        this.mList = list;
        this.mSparkDB = FengApplication.getInstance().getSparkDB();
        this.mSearchList = new ArrayList();
        initData(false);
    }

    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchHolder(SearchDefaultItemBinding.inflate(LayoutInflater.from(this.mContext), parent, false));
    }

    public void onBindViewHolder(SearchHolder holder, final int position) {
        final SearchItem item = (SearchItem) this.mSearchList.get(position);
        if (item.type == SearchItem.SEARCH_SHOW_ALL_RECORD_TYPE || item.type == SearchItem.SEARCH_SHOW_DEL_RECORD_TYPE) {
            holder.binding.clearHistory.setVisibility(0);
            holder.binding.defaultLine.setVisibility(8);
            if (item.type == SearchItem.SEARCH_SHOW_ALL_RECORD_TYPE) {
                this.mIsClearAll = false;
                holder.binding.clearHistory.setText(R.string.all_search_history);
            } else {
                this.mIsClearAll = true;
                holder.binding.clearHistory.setText(R.string.clear_search_history);
            }
            holder.binding.clearHistory.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (item.type == SearchItem.SEARCH_SHOW_ALL_RECORD_TYPE) {
                        EventBus.getDefault().post(new SearchRecordEvent(null, 1));
                    } else {
                        EventBus.getDefault().post(new SearchRecordEvent(null, 2));
                    }
                }
            });
            return;
        }
        holder.binding.clearHistory.setVisibility(8);
        holder.binding.defaultLine.setVisibility(0);
        holder.binding.tvContent.setText(item.content);
        holder.binding.tvContent.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                EventBus.getDefault().post(new SearchRecordEvent(item, 3));
            }
        });
        holder.binding.ivDel.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                try {
                    SearchRecordAdapter.this.mSparkDB.delSearchRecond(item._id);
                    if (SearchRecordAdapter.this.mList.size() > 3) {
                        SearchRecordAdapter.this.mSearchList.remove(position);
                        SearchRecordAdapter.this.mList.remove(item);
                        SearchRecordAdapter.this.adapterNotifyDataSetChanged(SearchRecordAdapter.this.mIsClearAll);
                        return;
                    }
                    SearchRecordAdapter.this.mSearchList.remove(position);
                    SearchRecordAdapter.this.mList.remove(item);
                    SearchRecordAdapter.this.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public int getItemCount() {
        return this.mSearchList.size();
    }

    private void initData(boolean isAll) {
        this.mSearchList.clear();
        int size = this.mList.size();
        if (size <= 3) {
            this.mSearchList.addAll(this.mList);
        } else if (size <= 3) {
        } else {
            SearchItem searchItem;
            if (isAll) {
                this.mSearchList.addAll(this.mList);
                searchItem = new SearchItem();
                searchItem.type = SearchItem.SEARCH_SHOW_DEL_RECORD_TYPE;
                this.mSearchList.add(searchItem);
                return;
            }
            this.mSearchList.addAll(this.mList.subList(0, 3));
            searchItem = new SearchItem();
            searchItem.type = SearchItem.SEARCH_SHOW_ALL_RECORD_TYPE;
            this.mSearchList.add(searchItem);
        }
    }

    public void adapterNotifyDataSetChanged(boolean isAll) {
        initData(isAll);
        notifyDataSetChanged();
    }
}
