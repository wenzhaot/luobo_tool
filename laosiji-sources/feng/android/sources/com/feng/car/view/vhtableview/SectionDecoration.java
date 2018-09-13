package com.feng.car.view.vhtableview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import com.feng.car.utils.FengUtil;
import java.util.List;

public class SectionDecoration extends ItemDecoration {
    private int alignBottom;
    private DecorationCallback callback;
    private FontMetrics fontMetrics;
    private Paint linePaint;
    private Bitmap mBitmap;
    private Context mContext;
    private List<String> mList;
    private Paint paint = new Paint();
    private TextPaint textPaint;
    private int topGap;

    public SectionDecoration(Context context, List<String> list, DecorationCallback decorationCallback) {
        this.mContext = context;
        this.callback = decorationCallback;
        this.mList = list;
        Resources res = context.getResources();
        this.paint.setColor(res.getColor(2131558529));
        this.linePaint = new Paint();
        this.linePaint.setColor(res.getColor(2131558512));
        this.textPaint = new TextPaint();
        this.textPaint.setAntiAlias(true);
        this.textPaint.setTextSize(30.0f);
        this.textPaint.setColor(res.getColor(2131558448));
        this.textPaint.setTextAlign(Align.LEFT);
        this.fontMetrics = new FontMetrics();
        this.topGap = res.getDimensionPixelSize(2131296815);
        this.alignBottom = res.getDimensionPixelSize(2131296423);
        this.mBitmap = BitmapFactory.decodeResource(res, 2130838026);
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        if (!this.callback.getGroupId(pos).equals("-1")) {
            if (pos == 0 || isFirstInGroup(pos)) {
                outRect.top = this.topGap;
            } else {
                outRect.top = 0;
            }
        }
    }

    public void onDraw(Canvas c, RecyclerView parent, State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        int i = 0;
        while (i < childCount) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            if (!this.callback.getGroupId(position).equals("-1")) {
                String textLine = this.callback.getGroupFirstLine(position).toUpperCase();
                float top;
                float bottom;
                if (textLine.equals("")) {
                    top = (float) view.getTop();
                    bottom = (float) view.getTop();
                    c.drawRect((float) left, top, (float) right, bottom, this.paint);
                    c.drawRect((float) left, top, (float) right, top + 1.0f, this.linePaint);
                    c.drawRect((float) left, bottom - 1.0f, (float) right, bottom, this.linePaint);
                    return;
                }
                if (position == 0 || isFirstInGroup(position)) {
                    top = (float) (view.getTop() - this.topGap);
                    bottom = (float) view.getTop();
                    c.drawRect((float) left, top - ((float) this.topGap), (float) right, bottom, this.paint);
                    c.drawRect((float) left, top, (float) right, top + 1.0f, this.linePaint);
                    c.drawRect((float) left, bottom - 1.0f, (float) right, bottom, this.linePaint);
                    c.drawText(textLine, (float) left, bottom, this.textPaint);
                }
                i++;
            } else {
                return;
            }
        }
    }

    public void onDrawOver(Canvas c, RecyclerView parent, State state) {
        super.onDrawOver(c, parent, state);
        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        float lineHeight = this.textPaint.getTextSize() + this.fontMetrics.descent;
        String preGroupId = "";
        String groupId = "-1";
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            preGroupId = groupId;
            groupId = this.callback.getGroupId(position);
            if (!(groupId.equals("-1") || groupId.equals(preGroupId))) {
                String textLine = this.callback.getGroupFirstLine(position).toUpperCase();
                if (!TextUtils.isEmpty(textLine)) {
                    int viewBottom = view.getBottom();
                    float textY = (float) Math.max(this.topGap, view.getTop());
                    if (position + 1 < itemCount && !this.callback.getGroupId(position + 1).equals(groupId) && ((float) viewBottom) < textY) {
                        textY = (float) viewBottom;
                    }
                    c.drawRect((float) left, textY - ((float) this.topGap), (float) right, textY, this.paint);
                    c.drawRect((float) left, textY - ((float) this.topGap), (float) right, (textY - ((float) this.topGap)) + 1.0f, this.linePaint);
                    c.drawRect((float) left, textY - 1.0f, (float) right, textY, this.linePaint);
                    c.drawText(textLine, (float) (this.alignBottom + left), textY - ((float) this.alignBottom), this.textPaint);
                    c.drawBitmap(this.mBitmap, (float) ((FengUtil.getScreenWidth(this.mContext) - this.mBitmap.getWidth()) - 20), (textY - ((float) this.alignBottom)) - 25.0f, this.textPaint);
                }
            }
        }
    }

    private boolean isFirstInGroup(int pos) {
        if (pos != 0 && this.callback.getGroupId(pos - 1).equals(this.callback.getGroupId(pos))) {
            return false;
        }
        return true;
    }
}
