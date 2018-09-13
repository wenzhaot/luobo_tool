package com.feng.car.view.vhtableview;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.feng.car.adapter.VHTableAdapter;
import com.feng.car.entity.car.carconfig.CarConfigureParent;
import com.feng.car.view.vhtableview.HListViewScrollView.ScrollChangedListener;
import com.feng.car.view.vhtableview.SectionDecoration.DecorationCallback;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.List;

public class VHTableView extends LinearLayout implements ScrollChangedListener {
    private HListViewScrollView currentTouchView;
    private boolean firstColumnIsMove;
    private LayoutInflater inflater;
    private int m140;
    private int m160;
    private int m192;
    private int m200;
    private int m60;
    private int m90;
    private boolean mCanAdd;
    private Context mContext;
    protected List<HListViewScrollView> mHScrollViews;
    private ItemExcuteListener mItemDeleteListener;
    private LinearLayoutManager mLinearLayoutManager;
    SparseArray<View> mMap;
    private OnScrollChangedListener mOnUIScrollChanged;
    private List<String> mOwnerPriceList;
    private RecyclerView mRecyclerView;
    private int mScrollX;
    private TitleAdapter mTitleAdapter;
    private List<String> priceList;
    private boolean showTitle;
    private List<CarConfigureParent> titleData;

    public class ContentAdapter extends Adapter<ViewHolder> {
        private VHBaseAdapter conentAdapter;

        public ContentAdapter(VHBaseAdapter conentAdapter) {
            this.conentAdapter = conentAdapter;
        }

        public int getItemViewType(int position) {
            return position == this.conentAdapter.getContentRows() + -1 ? 1 : 0;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 0) {
                return new ViewHolder(LayoutInflater.from(VHTableView.this.mContext).inflate(2130903422, parent, false));
            }
            return new ViewHolder(LayoutInflater.from(VHTableView.this.mContext).inflate(2130903421, parent, false));
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            if (holder.getItemViewType() == 0) {
                final HListViewScrollView chs_datagroup = (HListViewScrollView) holder.itemView.findViewById(2131625539);
                chs_datagroup.setTag(Integer.valueOf(position));
                chs_datagroup.setOverScrollMode(2);
                chs_datagroup.post(new Runnable() {
                    public void run() {
                        chs_datagroup.scrollTo(VHTableView.this.mScrollX, 0);
                    }
                });
                VHTableView.this.addHViews(chs_datagroup);
                holder.views = new View[this.conentAdapter.getContentColumn()];
                holder.ll_firstcolumn = (LinearLayout) holder.itemView.findViewById(2131625538);
                holder.ll_datagroup = (LinearLayout) holder.itemView.findViewById(2131625540);
                holder.rowClickListener = new RowClickListener();
                VHTableView.this.updateViews(this.conentAdapter, holder, holder.ll_firstcolumn, holder.ll_datagroup, position);
                VHTableView.this.updateUI(this.conentAdapter, holder.ll_firstcolumn, holder.ll_datagroup, holder.views, 0);
                return;
            }
            if (holder.getItemViewType() == 1) {
            }
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public int getItemCount() {
            return this.conentAdapter.getContentRows();
        }
    }

    public class RowClickListener implements OnClickListener {
        private VHBaseAdapter conentAdapter;
        private View convertView;
        private int row;

        public void setData(VHBaseAdapter conentAdapter, int row, View convertView) {
            this.conentAdapter = conentAdapter;
            this.row = row;
            this.convertView = convertView;
        }

        public void onClick(View v) {
            if (this.conentAdapter != null && this.convertView != null) {
                this.conentAdapter.OnClickContentRowItem(this.row, this.convertView);
            }
        }
    }

    public class TitleAdapter extends BaseAdapter {
        VHBaseAdapter conentAdapter;

        public class ViewHolder {
            HListViewScrollView chs_datagroup;
            LinearLayout ll_datagroup;
            LinearLayout ll_firstcolumn;
        }

        public TitleAdapter(VHBaseAdapter conentAdapter) {
            this.conentAdapter = conentAdapter;
        }

        public int getCount() {
            if (VHTableView.this.mOwnerPriceList == null || VHTableView.this.mOwnerPriceList.size() <= 0) {
                return 2;
            }
            return 3;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View titleView, ViewGroup parent) {
            ViewHolder holder;
            if (titleView == null) {
                holder = new ViewHolder();
                titleView = LayoutInflater.from(VHTableView.this.mContext).inflate(2130903422, parent, false);
                titleView.setTag(holder);
                holder.ll_firstcolumn = (LinearLayout) titleView.findViewById(2131625538);
                holder.chs_datagroup = (HListViewScrollView) titleView.findViewById(2131625539);
                holder.ll_datagroup = (LinearLayout) titleView.findViewById(2131625540);
            } else {
                holder = (ViewHolder) titleView.getTag();
            }
            holder.chs_datagroup.setOverScrollMode(2);
            VHTableView.this.addHViews(holder.chs_datagroup);
            holder.ll_firstcolumn.removeAllViews();
            holder.ll_datagroup.removeAllViews();
            View view;
            int i;
            View v;
            if (position == 0) {
                view = VHTableView.this.getTitleView(0, holder.ll_firstcolumn);
                view.measure(0, 0);
                holder.ll_firstcolumn.addView(view, VHTableView.this.m140, VHTableView.this.m200);
                for (i = 1; i < VHTableView.this.titleData.size(); i++) {
                    v = VHTableView.this.getTitleView(i, holder.ll_datagroup);
                    v.measure(0, 0);
                    holder.ll_datagroup.addView(v, VHTableView.this.m192, VHTableView.this.m200);
                }
            } else if (position == 1) {
                view = VHTableView.this.getPriceView(0);
                view.measure(0, 0);
                holder.ll_firstcolumn.addView(view, VHTableView.this.m140, VHTableView.this.m60);
                for (i = 1; i < VHTableView.this.titleData.size(); i++) {
                    v = VHTableView.this.getPriceView(i);
                    v.measure(0, 0);
                    holder.ll_datagroup.addView(v, VHTableView.this.m192, VHTableView.this.m60);
                }
            } else {
                view = VHTableView.this.getOwnerPriceView(0);
                view.measure(0, 0);
                holder.ll_firstcolumn.addView(view, VHTableView.this.m140, VHTableView.this.m60);
                for (i = 1; i < VHTableView.this.titleData.size(); i++) {
                    v = VHTableView.this.getOwnerPriceView(i);
                    v.measure(0, 0);
                    holder.ll_datagroup.addView(v, VHTableView.this.m192, VHTableView.this.m60);
                }
            }
            return titleView;
        }
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        LinearLayout ll_datagroup;
        LinearLayout ll_firstcolumn;
        RowClickListener rowClickListener;
        View[] views;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    public VHTableView(Context context) {
        this(context, null);
    }

    public VHTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mScrollX = 0;
        this.mHScrollViews = new ArrayList();
        this.mCanAdd = true;
        this.mMap = new SparseArray();
        this.mContext = context;
        init();
    }

    private void init() {
        this.inflater = LayoutInflater.from(this.mContext);
        this.showTitle = true;
        this.firstColumnIsMove = false;
        this.m90 = this.mContext.getResources().getDimensionPixelSize(2131296868);
        this.m140 = this.mContext.getResources().getDimensionPixelSize(2131296307);
        this.m160 = this.mContext.getResources().getDimensionPixelSize(2131296324);
        this.m192 = this.mContext.getResources().getDimensionPixelSize(2131296359);
        this.m200 = this.mContext.getResources().getDimensionPixelSize(2131296369);
        this.m60 = this.mContext.getResources().getDimensionPixelSize(2131296826);
    }

    public void setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
    }

    public void setFirstColumnIsMove(boolean firstColumnIsMove) {
        this.firstColumnIsMove = firstColumnIsMove;
    }

    public void setAdapter(VHBaseAdapter conentAdapter) {
        cleanup();
        initTitles(conentAdapter);
        initContentList(conentAdapter);
        if (!this.showTitle) {
            getChildAt(0).setVisibility(8);
        }
    }

    public void cleanup() {
        removeAllViews();
        this.mHScrollViews.clear();
    }

    public TitleAdapter getTitleAdapter() {
        return this.mTitleAdapter;
    }

    private void initTitles(VHBaseAdapter conentAdapter) {
        int titleHeight;
        ListView listView = new ListView(this.mContext);
        listView.setVerticalScrollBarEnabled(false);
        listView.setOverScrollMode(2);
        listView.setDividerHeight(0);
        this.mTitleAdapter = new TitleAdapter(conentAdapter);
        listView.setAdapter(this.mTitleAdapter);
        if (this.mOwnerPriceList == null || this.mOwnerPriceList.size() <= 0) {
            titleHeight = this.mContext.getResources().getDimensionPixelSize(2131296440);
        } else {
            titleHeight = this.mContext.getResources().getDimensionPixelSize(2131296513);
        }
        addView(listView, -1, titleHeight);
    }

    public void notifyDataSetChanged() {
        this.mTitleAdapter.notifyDataSetChanged();
        this.mRecyclerView.getAdapter().notifyDataSetChanged();
        for (final HListViewScrollView scrollView : this.mHScrollViews) {
            if (scrollView != null) {
                scrollView.post(new Runnable() {
                    public void run() {
                        scrollView.scrollTo(VHTableView.this.mScrollX, 0);
                    }
                });
            }
        }
    }

    public void setTitleData(List<CarConfigureParent> titleData) {
        this.titleData = titleData;
    }

    public void setCanAdd(boolean mCanAdd) {
        this.mCanAdd = mCanAdd;
    }

    public void setOwnerPriceList(List<String> priceList) {
        this.mOwnerPriceList = priceList;
    }

    public View getOwnerPriceView(int columnPosition) {
        Resources res = this.mContext.getResources();
        int m4 = this.mContext.getResources().getDimensionPixelSize(2131296701);
        TextView textView = new TextView(this.mContext);
        if (((String) this.mOwnerPriceList.get(columnPosition)).equals("车主价格(元)")) {
            textView.setText("成交价");
        } else {
            textView.setText((CharSequence) this.mOwnerPriceList.get(columnPosition));
        }
        if (columnPosition == 0) {
            textView.setBackgroundResource(2130838529);
        } else if (columnPosition == this.mOwnerPriceList.size() - 1) {
            textView.setBackgroundResource(2130838529);
        } else {
            boolean flag = true;
            if (this.mOwnerPriceList.size() > 3) {
                for (int i = 2; i < this.mOwnerPriceList.size(); i++) {
                    if (!((String) this.mOwnerPriceList.get(i)).equals((String) this.mOwnerPriceList.get(i - 1))) {
                        flag = false;
                        break;
                    }
                }
            } else {
                flag = true;
            }
            if (flag) {
                textView.setBackgroundResource(2130838530);
            } else {
                textView.setBackgroundResource(2130838531);
            }
        }
        textView.setPadding(m4, 0, m4, 0);
        textView.setGravity(17);
        if (columnPosition == 0) {
            textView.setTextColor(res.getColor(2131558478));
        } else {
            textView.setTextColor(res.getColor(2131558513));
        }
        textView.setTextSize(12.0f);
        return textView;
    }

    public void setPriceList(List<String> priceList) {
        this.priceList = priceList;
    }

    public View getPriceView(int columnPosition) {
        Resources res = this.mContext.getResources();
        int m4 = this.mContext.getResources().getDimensionPixelSize(2131296701);
        TextView textView = new TextView(this.mContext);
        textView.setText((CharSequence) this.priceList.get(columnPosition));
        if (columnPosition == 0) {
            textView.setBackgroundResource(2130838529);
        } else if (columnPosition == this.priceList.size() - 1) {
            textView.setBackgroundResource(2130838529);
        } else {
            boolean flag = true;
            if (this.priceList.size() > 3) {
                for (int i = 2; i < this.priceList.size(); i++) {
                    if (!((String) this.priceList.get(i)).equals((String) this.priceList.get(i - 1))) {
                        flag = false;
                        break;
                    }
                }
            } else {
                flag = true;
            }
            if (flag) {
                textView.setBackgroundResource(2130838530);
            } else {
                textView.setBackgroundResource(2130838531);
            }
        }
        textView.setPadding(m4, 0, m4, 0);
        textView.setGravity(17);
        if (columnPosition == 0) {
            textView.setTextColor(res.getColor(2131558478));
        } else {
            textView.setTextColor(res.getColor(2131558513));
        }
        textView.setTextSize(12.0f);
        return textView;
    }

    public View getTitleView(final int columnPosition, ViewGroup parent) {
        View view = LayoutInflater.from(this.mContext).inflate(2130903207, parent, false);
        view.setBackgroundResource(2130838530);
        ImageView delete = (ImageView) view.findViewById(2131624830);
        ImageView add = (ImageView) view.findViewById(2131624086);
        TextView text = (TextView) view.findViewById(2131624009);
        CarConfigureParent carConfigureParent = (CarConfigureParent) this.titleData.get(columnPosition);
        if (carConfigureParent.spec_id == 0) {
            delete.setVisibility(8);
        } else if (((CarConfigureParent) this.titleData.get(columnPosition)).spec_id == -1) {
            text.setVisibility(8);
            delete.setVisibility(8);
            add.setVisibility(0);
            add.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (VHTableView.this.mItemDeleteListener != null) {
                        VHTableView.this.mItemDeleteListener.onAddClick(columnPosition);
                    }
                }
            });
            if (this.mCanAdd) {
                add.setBackgroundResource(2130838023);
                add.setClickable(true);
            } else {
                add.setClickable(false);
                add.setBackgroundResource(2130838025);
            }
            this.mMap.clear();
            this.mMap.put(0, add);
        } else {
            add.setVisibility(8);
            text.setVisibility(0);
            text.setText(carConfigureParent.spec_name);
            delete.setVisibility(0);
            delete.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (VHTableView.this.mItemDeleteListener != null) {
                        VHTableView.this.mItemDeleteListener.onDeleteClick(columnPosition);
                    }
                }
            });
        }
        return view;
    }

    public void forbidAdd() {
        View v = (View) this.mMap.get(0);
        if (v != null) {
            v.setBackgroundResource(2130838025);
            v.setClickable(false);
        }
    }

    public void allowAdd() {
        View v = (View) this.mMap.get(0);
        if (v != null) {
            v.setBackgroundResource(2130838023);
            v.setClickable(true);
        }
    }

    public void setOnAddDelteListener(ItemExcuteListener itemDeleteListener) {
        this.mItemDeleteListener = itemDeleteListener;
    }

    public void scrollToPosition(int position) {
        ((LinearLayoutManager) this.mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(position, 0);
    }

    private void initContentList(final VHBaseAdapter conentAdapter) {
        this.mRecyclerView = new RecyclerView(this.mContext);
        this.mRecyclerView.setOverScrollMode(2);
        this.mLinearLayoutManager = new LinearLayoutManager(this.mContext);
        this.mRecyclerView.addOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisiblePosition = VHTableView.this.mLinearLayoutManager.findFirstVisibleItemPosition();
                int lastVisiblePosition = VHTableView.this.mLinearLayoutManager.findLastVisibleItemPosition();
                if (lastVisiblePosition - firstVisiblePosition > 1) {
                    HListViewScrollView chs_datagroup;
                    View view = VHTableView.this.mLinearLayoutManager.getChildAt(0);
                    if (view != null) {
                        chs_datagroup = (HListViewScrollView) view.findViewById(2131625539);
                        if (chs_datagroup != null) {
                            chs_datagroup.setOverScrollMode(2);
                            if (chs_datagroup.getScrollX() != VHTableView.this.mScrollX) {
                                chs_datagroup.scrollTo(VHTableView.this.mScrollX, 0);
                            }
                        }
                    }
                    view = VHTableView.this.mLinearLayoutManager.getChildAt(lastVisiblePosition - firstVisiblePosition);
                    if (view != null) {
                        chs_datagroup = (HListViewScrollView) view.findViewById(2131625539);
                        if (chs_datagroup != null) {
                            chs_datagroup.setOverScrollMode(2);
                            if (chs_datagroup.getScrollX() != VHTableView.this.mScrollX) {
                                chs_datagroup.scrollTo(VHTableView.this.mScrollX, 0);
                            }
                        }
                    }
                }
            }
        });
        ContentAdapter adapter = new ContentAdapter(conentAdapter);
        this.mRecyclerView.addItemDecoration(new SectionDecoration(this.mContext, ((VHTableAdapter) conentAdapter).intList, new DecorationCallback() {
            public String getGroupId(int position) {
                if (position >= ((VHTableAdapter) conentAdapter).intList.size() || ((VHTableAdapter) conentAdapter).intList.get(position) == null) {
                    return "-1";
                }
                return (String) ((VHTableAdapter) conentAdapter).intList.get(position);
            }

            public String getGroupFirstLine(int position) {
                if (position < ((VHTableAdapter) conentAdapter).intList.size()) {
                    return (String) ((VHTableAdapter) conentAdapter).intList.get(position);
                }
                return "-1";
            }
        }));
        this.mRecyclerView.setAdapter(adapter);
        this.mRecyclerView.setLayoutManager(this.mLinearLayoutManager);
        addView(this.mRecyclerView, -1, -1);
    }

    public void addHViews(HListViewScrollView hScrollView) {
        hScrollView.setScrollChangedListener(this);
        if (!this.mHScrollViews.contains(hScrollView)) {
            this.mHScrollViews.add(hScrollView);
        }
    }

    public void setCurrentTouchView(HListViewScrollView currentTouchView) {
        this.currentTouchView = currentTouchView;
    }

    public HListViewScrollView getCurrentTouchView() {
        return this.currentTouchView;
    }

    public void onUIScrollChanged(int l, int t, int oldl, int oldt) {
        if (this.mOnUIScrollChanged != null) {
            HListViewScrollView hListViewScrollView = (HListViewScrollView) this.mHScrollViews.get(0);
            this.mOnUIScrollChanged.onScrollChanged(l, oldl, hListViewScrollView.getChildAt(0).getMeasuredWidth() - hListViewScrollView.getMeasuredWidth(), hListViewScrollView.getScrollX());
            this.mScrollX = l;
        }
        for (HListViewScrollView scrollView : this.mHScrollViews) {
            if (scrollView != null) {
                scrollView.scrollTo(l, t);
            }
        }
    }

    private ViewHolder updateViews(VHBaseAdapter conentAdapter, ViewHolder viewHolder, LinearLayout ll_firstcolumn, LinearLayout ll_datagroup, int row) {
        int i = 0;
        while (i < conentAdapter.getContentColumn()) {
            if (this.firstColumnIsMove || i != 0) {
                viewHolder.views[i] = conentAdapter.getTableCellView(row, i, viewHolder.views[i], ll_datagroup);
            } else {
                viewHolder.views[0] = conentAdapter.getTableCellView(row, 0, viewHolder.views[0], ll_firstcolumn);
            }
            i++;
        }
        return viewHolder;
    }

    private void updateUI(VHBaseAdapter conentAdapter, LinearLayout ll_firstcolumn, LinearLayout ll_datagroup, View[] views, int maxHeight) {
        ll_firstcolumn.removeAllViews();
        ll_datagroup.removeAllViews();
        int i = 0;
        while (i < conentAdapter.getContentColumn()) {
            if (this.firstColumnIsMove || i != 0) {
                ll_datagroup.addView(views[i], this.m192, this.m90);
            } else {
                ll_firstcolumn.addView(views[i], this.m140, this.m90);
            }
            i++;
        }
    }

    public void scrollTo(final int l, final int t) {
        for (final HListViewScrollView scrollView : this.mHScrollViews) {
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.scrollTo(l, t);
                }
            });
        }
    }

    public void setOnScrollChangedListener(OnScrollChangedListener l) {
        this.mOnUIScrollChanged = l;
    }
}
