package com.feng.library.emoticons.keyboard.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.feng.library.R;
import com.feng.library.emoticons.keyboard.data.EmoticonPageEntity;
import com.feng.library.emoticons.keyboard.data.EmoticonPageEntity.DelBtnStatus;
import com.feng.library.emoticons.keyboard.interfaces.EmoticonClickListener;
import com.feng.library.emoticons.keyboard.interfaces.EmoticonDisplayListener;
import java.util.ArrayList;

public class EmoticonsAdapter<T> extends BaseAdapter {
    protected final int DEF_HEIGHTMAXTATIO = 2;
    protected Context mContext;
    protected ArrayList<T> mData = new ArrayList();
    protected final int mDefalutItemHeight;
    protected int mDelbtnPosition;
    protected EmoticonPageEntity mEmoticonPageEntity;
    protected LayoutInflater mInflater;
    protected int mItemHeight;
    protected int mItemHeightMax;
    protected double mItemHeightMaxRatio;
    protected int mItemHeightMin;
    protected EmoticonDisplayListener mOnDisPlayListener;
    protected EmoticonClickListener mOnEmoticonClickListener;

    public static class ViewHolder {
        public ImageView iv_emoticon;
        public LinearLayout ly_root;
        public View rootView;
    }

    public EmoticonsAdapter(Context context, EmoticonPageEntity emoticonPageEntity, EmoticonClickListener onEmoticonClickListener) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mEmoticonPageEntity = emoticonPageEntity;
        this.mOnEmoticonClickListener = onEmoticonClickListener;
        this.mItemHeightMaxRatio = 2.0d;
        this.mDelbtnPosition = -1;
        int dimension = (int) context.getResources().getDimension(R.dimen.item_emoticon_size_default);
        this.mItemHeight = dimension;
        this.mDefalutItemHeight = dimension;
        this.mData.addAll(emoticonPageEntity.getEmoticonList());
        checkDelBtn(emoticonPageEntity);
    }

    private void checkDelBtn(EmoticonPageEntity entity) {
        DelBtnStatus delBtnStatus = entity.getDelBtnStatus();
        if (!DelBtnStatus.GONE.equals(delBtnStatus)) {
            if (DelBtnStatus.FOLLOW.equals(delBtnStatus)) {
                this.mDelbtnPosition = getCount();
                this.mData.add(null);
            } else if (DelBtnStatus.LAST.equals(delBtnStatus)) {
                int max = entity.getLine() * entity.getRow();
                while (getCount() < max) {
                    this.mData.add(null);
                }
                this.mDelbtnPosition = getCount() - 1;
            }
        }
    }

    public int getCount() {
        return this.mData == null ? 0 : this.mData.size();
    }

    public Object getItem(int position) {
        return this.mData == null ? null : this.mData.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = this.mInflater.inflate(R.layout.emoji_item_emoticon, null);
            viewHolder.rootView = convertView;
            viewHolder.ly_root = (LinearLayout) convertView.findViewById(R.id.ly_root);
            viewHolder.iv_emoticon = (ImageView) convertView.findViewById(R.id.iv_emoticon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bindView(position, parent, viewHolder);
        updateUI(viewHolder, parent);
        return convertView;
    }

    protected void bindView(int position, ViewGroup parent, ViewHolder viewHolder) {
        if (this.mOnDisPlayListener != null) {
            this.mOnDisPlayListener.onBindView(position, parent, viewHolder, this.mData.get(position), position == this.mDelbtnPosition);
        }
    }

    protected boolean isDelBtn(int position) {
        return position == this.mDelbtnPosition;
    }

    protected void updateUI(ViewHolder viewHolder, ViewGroup parent) {
        if (this.mDefalutItemHeight != this.mItemHeight) {
            viewHolder.iv_emoticon.setLayoutParams(new LayoutParams(-1, this.mItemHeight));
        }
        this.mItemHeightMax = this.mItemHeightMax != 0 ? this.mItemHeightMax : (int) (((double) this.mItemHeight) * this.mItemHeightMaxRatio);
        this.mItemHeightMin = this.mItemHeightMin != 0 ? this.mItemHeightMin : this.mItemHeight;
        viewHolder.ly_root.setLayoutParams(new LayoutParams(-1, Math.max(Math.min(((View) parent.getParent()).getMeasuredHeight() / this.mEmoticonPageEntity.getLine(), this.mItemHeightMax), this.mItemHeightMin)));
    }

    public void setOnDisPlayListener(EmoticonDisplayListener mOnDisPlayListener) {
        this.mOnDisPlayListener = mOnDisPlayListener;
    }

    public void setItemHeightMaxRatio(double mItemHeightMaxRatio) {
        this.mItemHeightMaxRatio = mItemHeightMaxRatio;
    }

    public void setItemHeightMax(int mItemHeightMax) {
        this.mItemHeightMax = mItemHeightMax;
    }

    public void setItemHeightMin(int mItemHeightMin) {
        this.mItemHeightMin = mItemHeightMin;
    }

    public void setItemHeight(int mItemHeight) {
        this.mItemHeight = mItemHeight;
    }

    public void setDelbtnPosition(int mDelbtnPosition) {
        this.mDelbtnPosition = mDelbtnPosition;
    }
}
