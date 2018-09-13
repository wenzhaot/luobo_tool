package com.feng.car.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.feng.car.R;
import java.util.List;

public class CameraPickAdapter extends BaseAdapter {
    private Context mContexct;
    private List<String> mList;
    private int mSelectPosition = 1;

    public void setSelectPosition(int selectPosition) {
        this.mSelectPosition = selectPosition;
    }

    public CameraPickAdapter(List<String> mList, Context mContexct) {
        this.mList = mList;
        this.mContexct = mContexct;
    }

    public int getCount() {
        return this.mList.size();
    }

    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(this.mContexct);
        textView.setTextSize(16.0f);
        if (position == this.mSelectPosition) {
            textView.setTextColor(this.mContexct.getResources().getColor(R.color.color_ffb90a));
        } else {
            textView.setTextColor(this.mContexct.getResources().getColor(R.color.color_ffffff));
        }
        textView.setText((CharSequence) this.mList.get(position));
        return textView;
    }
}
