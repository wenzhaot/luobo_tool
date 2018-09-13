package com.feng.car.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.feng.car.R;
import com.feng.car.activity.CarConfigureCompareActivity.ConfigLineBean;
import com.feng.car.entity.car.carconfig.CarConfigureParent;
import com.feng.car.view.vhtableview.VHBaseAdapter;
import java.util.ArrayList;
import java.util.List;

public class VHTableAdapter implements VHBaseAdapter {
    private List<ConfigLineBean> dataList;
    public List<String> intList;
    private int m4;
    private Context mContext;
    private boolean mIsHideSameData = false;
    private List<String> redcolsList;
    private List<CarConfigureParent> titleData;

    public void setIsHideSameData(boolean isHideSameData) {
        this.mIsHideSameData = isHideSameData;
    }

    public VHTableAdapter(Context context, List<CarConfigureParent> titleData, ArrayList<ConfigLineBean> dataList, List<String> list, List<String> redcolsList) {
        this.mContext = context;
        this.titleData = titleData;
        this.dataList = dataList;
        this.intList = list;
        this.redcolsList = redcolsList;
        this.m4 = context.getResources().getDimensionPixelSize(R.dimen.default_4PX);
    }

    public int getContentRows() {
        return this.dataList.size() + 1;
    }

    public int getContentColumn() {
        return this.titleData.size();
    }

    public View getPriceView(int columnPosition) {
        return new TextView(this.mContext);
    }

    public View getTitleView(int columnPosition, ViewGroup parent) {
        return LayoutInflater.from(this.mContext).inflate(R.layout.configure_title_item, parent, false);
    }

    public View getTableCellView(int contentRow, int contentColum, View view, ViewGroup parent) {
        if (view == null) {
            view = new TextView(this.mContext);
        }
        Resources res = this.mContext.getResources();
        ((TextView) view).setText((CharSequence) ((ConfigLineBean) this.dataList.get(contentRow)).list.get(contentColum));
        boolean flag = ((ConfigLineBean) this.dataList.get(contentRow)).isAllSame();
        if (contentColum == 0) {
            view.setBackgroundResource(R.drawable.tablebg_f5f5f5_border_dfdfdf);
        } else if (contentColum == ((ConfigLineBean) this.dataList.get(contentRow)).list.size() - 1) {
            view.setBackgroundResource(R.drawable.tablebg_f5f5f5_border_dfdfdf);
        } else if (flag) {
            view.setBackgroundResource(R.drawable.tablebg_fefefe_border_dfdfdf);
        } else {
            view.setBackgroundResource(R.drawable.tablebg_fff9e8_border_dfdfdf);
        }
        view.setPadding(this.m4, 0, this.m4, 0);
        ((TextView) view).setGravity(17);
        if (contentColum == 0) {
            ((TextView) view).setTextColor(res.getColor(R.color.color_87_000000));
        } else if (this.redcolsList.contains(((ConfigLineBean) this.dataList.get(contentRow)).list.get(0))) {
            ((TextView) view).setTextColor(res.getColor(R.color.color_e12c2c));
        } else {
            ((TextView) view).setTextColor(res.getColor(R.color.color_54_000000));
        }
        ((TextView) view).setTextSize(11.5f);
        if (this.mIsHideSameData && flag) {
            view.setVisibility(8);
        } else if (!view.isShown()) {
            view.setVisibility(0);
        }
        return view;
    }

    public Object getItem(int contentRow) {
        return this.dataList.get(contentRow);
    }

    public void OnClickContentRowItem(int row, View convertView) {
    }
}
