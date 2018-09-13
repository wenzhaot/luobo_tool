package com.feng.library.emoticons.keyboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import com.feng.library.R;
import com.feng.library.emoticons.keyboard.adpater.PageSetAdapter;
import com.feng.library.emoticons.keyboard.data.PageSetEntity;
import com.feng.library.emoticons.keyboard.utils.EmoticonsKeyboardUtils;
import com.feng.library.emoticons.keyboard.widget.AutoHeightLayout;
import com.feng.library.emoticons.keyboard.widget.EmoticonsFuncView;
import com.feng.library.emoticons.keyboard.widget.EmoticonsFuncView.OnEmoticonsPageViewListener;
import com.feng.library.emoticons.keyboard.widget.EmoticonsIndicatorView;
import com.feng.library.emoticons.keyboard.widget.EmoticonsToolBarView;
import com.feng.library.emoticons.keyboard.widget.EmoticonsToolBarView.OnToolBarItemClickListener;
import com.feng.library.emoticons.keyboard.widget.FuncLayout;
import com.feng.library.emoticons.keyboard.widget.FuncLayout.OnFuncChangeListener;
import com.feng.library.emoticons.keyboard.widget.FuncLayout.OnFuncKeyBoardListener;
import java.util.ArrayList;
import java.util.Iterator;

public class XhsEmoticonsKeyBoard extends AutoHeightLayout implements OnEmoticonsPageViewListener, OnToolBarItemClickListener, OnFuncChangeListener {
    public static final int EMOTICONS_KEYBOARD_CLOSE = 1002;
    public static final int EMOTICONS_KEYBOARD_OPEN = 1001;
    public static final int FUNC_TYPE_APPPS = -2;
    public static final int FUNC_TYPE_EMOTION = -1;
    protected EditText inputEditText;
    protected boolean mDispatchKeyEventPreImeLock = false;
    protected EmoticonsFuncView mEmoticonsFuncView;
    protected EmoticonsIndicatorView mEmoticonsIndicatorView;
    protected EmoticonsToolBarView mEmoticonsToolBarView;
    protected LayoutInflater mInflater;
    protected OnKeyboardStatusChangeListener mKeyboardStatusChangeListener;
    protected FuncLayout mLyKvml;
    protected RelativeLayout mRlBarContainer;

    public EditText getInputEditText() {
        return this.inputEditText;
    }

    public void setInputEditText(EditText inputEditText) {
        this.inputEditText = inputEditText;
    }

    public XhsEmoticonsKeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mInflater.inflate(R.layout.layout_common_emoticons_bar, this);
        inflateFunc();
        initView();
        initFuncView();
    }

    protected View inflateFunc() {
        return this.mInflater.inflate(R.layout.emoji_view_func_emoticon, null);
    }

    protected void initView() {
        this.mRlBarContainer = (RelativeLayout) findViewById(R.id.rl_bar_container);
        this.mLyKvml = (FuncLayout) findViewById(R.id.ly_kvml);
    }

    protected void initFuncView() {
        initEmoticonFuncView();
        initEditView();
    }

    protected void initEmoticonFuncView() {
        this.mLyKvml.addFuncView(-1, inflateFunc());
        this.mEmoticonsFuncView = (EmoticonsFuncView) findViewById(R.id.view_epv);
        this.mEmoticonsIndicatorView = (EmoticonsIndicatorView) findViewById(R.id.view_eiv);
        this.mEmoticonsToolBarView = (EmoticonsToolBarView) findViewById(R.id.view_etv);
        this.mEmoticonsFuncView.setOnIndicatorListener(this);
        this.mEmoticonsToolBarView.setOnToolBarItemClickListener(this);
        this.mLyKvml.setOnFuncChangeListener(this);
    }

    public void addView(View view) {
        this.mRlBarContainer.addView(view);
    }

    public void removeContainerView() {
        this.mRlBarContainer.removeAllViews();
    }

    public void hideBarContainer() {
        this.mRlBarContainer.setVisibility(8);
    }

    public void showBarContainer() {
        this.mRlBarContainer.setVisibility(0);
    }

    protected void initEditView() {
    }

    public void setAdapter(PageSetAdapter pageSetAdapter) {
        if (pageSetAdapter != null) {
            ArrayList<PageSetEntity> pageSetEntities = pageSetAdapter.getPageSetEntityList();
            if (pageSetEntities != null) {
                Iterator it = pageSetEntities.iterator();
                while (it.hasNext()) {
                    this.mEmoticonsToolBarView.addToolItemView((PageSetEntity) it.next());
                }
            }
        }
        this.mEmoticonsFuncView.setAdapter(pageSetAdapter);
    }

    public void addFuncView(View view) {
        this.mLyKvml.addFuncView(-2, view);
    }

    public void reset() {
        if (this.mKeyboardStatusChangeListener != null) {
            this.mKeyboardStatusChangeListener.onStatusChange(1002);
        }
        EmoticonsKeyboardUtils.closeSoftKeyboard((View) this);
        this.mLyKvml.hideAllFuncView();
    }

    public void closeEmojiFuncView() {
        if (this.mKeyboardStatusChangeListener != null) {
            this.mKeyboardStatusChangeListener.onStatusChange(1002);
        }
        this.mLyKvml.hideAllFuncView();
    }

    protected void toggleFuncView(int key) {
        this.mLyKvml.toggleFuncView(key, isSoftKeyboardPop(), this.inputEditText);
    }

    public void onFuncChange(int key) {
        if (this.mKeyboardStatusChangeListener == null) {
            return;
        }
        if (-1 == key) {
            this.mKeyboardStatusChangeListener.onStatusChange(1001);
        } else {
            this.mKeyboardStatusChangeListener.onStatusChange(1002);
        }
    }

    protected void setFuncViewHeight(int height) {
        LayoutParams params = (LayoutParams) this.mLyKvml.getLayoutParams();
        params.height = height;
        this.mLyKvml.setLayoutParams(params);
    }

    public void onSoftKeyboardHeightChanged(int height) {
        this.mLyKvml.updateHeight(height);
    }

    public void OnSoftPop(int height) {
        super.OnSoftPop(height);
        this.mLyKvml.setVisibility(true);
        this.mLyKvml.getClass();
        onFuncChange(Integer.MIN_VALUE);
    }

    public void OnSoftClose() {
        super.OnSoftClose();
        if (this.mLyKvml.isOnlyShowSoftKeyboard()) {
            reset();
        } else {
            onFuncChange(this.mLyKvml.getCurrentFuncKey());
        }
    }

    public void addOnFuncKeyBoardListener(OnFuncKeyBoardListener l) {
        this.mLyKvml.addOnKeyBoardListener(l);
    }

    public void emoticonSetChanged(PageSetEntity pageSetEntity) {
        this.mEmoticonsToolBarView.setToolBtnSelect(pageSetEntity.getUuid());
    }

    public void playTo(int position, PageSetEntity pageSetEntity) {
        this.mEmoticonsIndicatorView.playTo(position, pageSetEntity);
    }

    public void playBy(int oldPosition, int newPosition, PageSetEntity pageSetEntity) {
        this.mEmoticonsIndicatorView.playBy(oldPosition, newPosition, pageSetEntity);
    }

    public void onToolBarItemClick(PageSetEntity pageSetEntity) {
        this.mEmoticonsFuncView.setCurrentPageSet(pageSetEntity);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case 4:
                if (this.mDispatchKeyEventPreImeLock) {
                    this.mDispatchKeyEventPreImeLock = false;
                    return true;
                } else if (!this.mLyKvml.isShown()) {
                    return super.dispatchKeyEvent(event);
                } else {
                    reset();
                    return true;
                }
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        if (EmoticonsKeyboardUtils.isFullScreen((Activity) getContext())) {
            return false;
        }
        return super.requestFocus(direction, previouslyFocusedRect);
    }

    public void requestChildFocus(View child, View focused) {
        if (!EmoticonsKeyboardUtils.isFullScreen((Activity) getContext())) {
            super.requestChildFocus(child, focused);
        }
    }

    public boolean dispatchKeyEventInFullScreen(KeyEvent event) {
        if (event == null) {
            return false;
        }
        switch (event.getKeyCode()) {
            case 4:
                if (EmoticonsKeyboardUtils.isFullScreen((Activity) getContext()) && this.mLyKvml.isShown()) {
                    reset();
                    return true;
                }
        }
        if (event.getAction() == 0) {
            boolean isFocused;
            if (VERSION.SDK_INT >= 21) {
                isFocused = this.inputEditText.getShowSoftInputOnFocus();
            } else {
                isFocused = this.inputEditText.isFocused();
            }
            if (isFocused) {
                this.inputEditText.onKeyDown(event.getKeyCode(), event);
            }
        }
        return false;
    }

    public EmoticonsFuncView getEmoticonsFuncView() {
        return this.mEmoticonsFuncView;
    }

    public EmoticonsIndicatorView getEmoticonsIndicatorView() {
        return this.mEmoticonsIndicatorView;
    }

    public EmoticonsToolBarView getEmoticonsToolBarView() {
        return this.mEmoticonsToolBarView;
    }

    public void setKeyboardStatusChangeListener(OnKeyboardStatusChangeListener keyboardStatusChangeListener) {
        this.mKeyboardStatusChangeListener = keyboardStatusChangeListener;
    }

    public void openEmoticonsKeyboard() {
        toggleFuncView(-1);
    }
}
