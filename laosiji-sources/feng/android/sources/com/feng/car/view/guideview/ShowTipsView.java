package com.feng.car.view.guideview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class ShowTipsView extends RelativeLayout {
    private int background_alpha = 220;
    private int background_color;
    private Bitmap bitmap;
    private Paint bitmapPaint;
    private String button_text;
    private ShowTipsViewInterface callback;
    private int circleColor;
    private Paint circleline;
    private boolean custom;
    private int delay = 0;
    private String description;
    private int description_color;
    private boolean displayOneTime;
    private int displayOneTimeID = 0;
    boolean isMeasured;
    private Context mContext;
    private Paint paint;
    private PorterDuffXfermode porterDuffXfermode;
    private int radius = 0;
    private int screenX;
    private int screenY;
    private StoreUtils showTipsStore;
    private Point showhintPoints;
    private int statusBarHeight;
    private View targetView;
    private Canvas temp;
    private String title;
    private int title_color;
    private Paint transparentPaint;

    public ShowTipsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ShowTipsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShowTipsView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        setVisibility(8);
        setBackgroundColor(0);
        setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            }
        });
        this.showTipsStore = new StoreUtils(getContext());
        this.paint = new Paint();
        if (this.background_color != 0) {
            this.paint.setColor(this.background_color);
        } else {
            this.paint.setColor(Color.parseColor("#000000"));
        }
        this.paint.setAlpha(170);
        this.bitmapPaint = new Paint();
        this.transparentPaint = new Paint();
        this.porterDuffXfermode = new PorterDuffXfermode(Mode.CLEAR);
        this.circleline = new Paint();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.screenX = w;
        this.screenY = h;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Config.ARGB_4444);
        this.temp = new Canvas(this.bitmap);
        this.temp.drawRect(0.0f, 0.0f, (float) this.temp.getWidth(), (float) this.temp.getHeight(), this.paint);
        this.transparentPaint.setColor(getResources().getColor(17170445));
        this.transparentPaint.setXfermode(this.porterDuffXfermode);
        RectF rectF = new RectF();
        if (this.targetView != null) {
            int[] location = new int[2];
            this.targetView.getLocationOnScreen(location);
            rectF.left = (float) (location[0] - 10);
            rectF.top = (float) ((location[1] - this.statusBarHeight) - 10);
            rectF.right = (float) ((location[0] + this.targetView.getWidth()) + 10);
            rectF.bottom = (float) (((location[1] - this.statusBarHeight) + this.targetView.getHeight()) + 10);
        }
        this.temp.drawRoundRect(rectF, 10.0f, 10.0f, this.transparentPaint);
        canvas.drawBitmap(this.bitmap, 0.0f, 0.0f, this.bitmapPaint);
    }

    public void show(final Activity activity) {
        if (isDisplayOneTime() && this.showTipsStore.hasShown(getDisplayOneTimeID())) {
            setVisibility(8);
            ((ViewGroup) ((Activity) getContext()).getWindow().getDecorView()).removeView(this);
            return;
        }
        if (isDisplayOneTime()) {
            this.showTipsStore.storeShownId(getDisplayOneTimeID());
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                ShowTipsView.this.setVisibility(0);
                ShowTipsView.this.startAnimation(AnimationUtils.loadAnimation(ShowTipsView.this.getContext(), 2130968592));
                Rect frame = new Rect();
                activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                ShowTipsView.this.statusBarHeight = frame.top;
                if (ShowTipsView.this.targetView != null) {
                    ShowTipsView.this.targetView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                        public void onGlobalLayout() {
                            if (!ShowTipsView.this.isMeasured) {
                                if (ShowTipsView.this.targetView.getHeight() > 0 && ShowTipsView.this.targetView.getWidth() > 0) {
                                    ShowTipsView.this.isMeasured = true;
                                }
                                int[] location;
                                if (ShowTipsView.this.custom) {
                                    location = new int[2];
                                    ShowTipsView.this.targetView.getLocationInWindow(location);
                                    ShowTipsView.this.showhintPoints = new Point(location[0] + ShowTipsView.this.showhintPoints.x, location[1] + ShowTipsView.this.showhintPoints.y);
                                } else {
                                    location = new int[2];
                                    ShowTipsView.this.targetView.getLocationOnScreen(location);
                                    ShowTipsView.this.showhintPoints = new Point(location[0] + (ShowTipsView.this.targetView.getWidth() / 2), location[1] + (ShowTipsView.this.targetView.getHeight() / 2));
                                    ShowTipsView.this.radius = ShowTipsView.this.targetView.getWidth() / 2;
                                }
                                ShowTipsView.this.invalidate();
                            }
                        }
                    });
                }
            }
        }, (long) getDelay());
    }

    public void setButtonText(String text) {
        this.button_text = text;
    }

    public String getButtonText() {
        if (this.button_text == null || this.button_text.equals("")) {
            return "Got it";
        }
        return this.button_text;
    }

    public void setTarget(View v) {
        this.targetView = v;
    }

    public void setTarget(View v, int x, int y, int radius) {
        this.custom = true;
        this.targetView = v;
        this.showhintPoints = new Point(x, y);
        this.radius = radius;
    }

    static Point getShowcasePointFromView(View view) {
        Point result = new Point();
        result.x = view.getLeft() + (view.getWidth() / 2);
        result.y = view.getTop() + (view.getHeight() / 2);
        return result;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDisplayOneTime() {
        return this.displayOneTime;
    }

    public void setDisplayOneTime(boolean displayOneTime) {
        this.displayOneTime = displayOneTime;
    }

    public ShowTipsViewInterface getCallback() {
        return this.callback;
    }

    public void setCallback(ShowTipsViewInterface callback) {
        this.callback = callback;
    }

    public int getDelay() {
        return this.delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getDisplayOneTimeID() {
        return this.displayOneTimeID;
    }

    public void setDisplayOneTimeID(int displayOneTimeID) {
        this.displayOneTimeID = displayOneTimeID;
    }

    public int getTitle_color() {
        return this.title_color;
    }

    public void setTitle_color(int title_color) {
        this.title_color = title_color;
    }

    public int getDescription_color() {
        return this.description_color;
    }

    public void setDescription_color(int description_color) {
        this.description_color = description_color;
    }

    public int getBackground_color() {
        return this.background_color;
    }

    public void setBackground_color(int background_color) {
        this.background_color = background_color;
    }

    public int getCircleColor() {
        return this.circleColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
    }

    public int getBackground_alpha() {
        return this.background_alpha;
    }

    public void setBackground_alpha(int background_alpha) {
        if (background_alpha > 255) {
            this.background_alpha = 255;
        } else if (background_alpha < 0) {
            this.background_alpha = 0;
        } else {
            this.background_alpha = background_alpha;
        }
    }

    public void hide() {
        setVisibility(8);
    }
}
