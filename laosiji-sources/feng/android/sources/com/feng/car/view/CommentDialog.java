package com.feng.car.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.feng.car.activity.AtUserActivity;
import com.feng.car.activity.SelectPhotoActivity;
import com.feng.car.databinding.CommentDialogLayoutBinding;
import com.feng.car.listener.EditInputFilter;
import com.feng.car.utils.AtFilter;
import com.feng.car.utils.FengConstant;
import com.feng.library.emoticons.keyboard.EmojiBean;
import com.feng.library.emoticons.keyboard.SimpleCommonUtils;
import com.feng.library.emoticons.keyboard.XhsEmoticonsKeyBoard.OnKeyboardStatusChangeListener;
import com.feng.library.emoticons.keyboard.data.EmoticonEntity;
import com.feng.library.emoticons.keyboard.interfaces.EmoticonClickListener;
import com.feng.library.emoticons.keyboard.widget.FuncLayout.OnFuncKeyBoardListener;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;

public class CommentDialog implements OnFuncKeyBoardListener {
    EmoticonClickListener emoticonClickListener = new EmoticonClickListener() {
        public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {
            if (isDelBtn) {
                SimpleCommonUtils.delClick(CommentDialog.this.mDialogLayoutBinding.atContent);
            } else if (o != null) {
                String content = null;
                if (o instanceof EmojiBean) {
                    content = ((EmojiBean) o).emoji;
                } else if (o instanceof EmoticonEntity) {
                    content = ((EmoticonEntity) o).getContent();
                }
                if (!TextUtils.isEmpty(content)) {
                    CommentDialog.this.mDialogLayoutBinding.atContent.getText().insert(CommentDialog.this.mDialogLayoutBinding.atContent.getSelectionStart(), content);
                }
            }
        }
    };
    private boolean mContentOk;
    private Context mContext;
    private int mCountLimitCount = 140;
    private Dialog mDialog;
    private CommentDialogLayoutBinding mDialogLayoutBinding;
    private String mImagePath = "";
    private EditInputFilter mInputFilter;

    public CommentDialog(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        this.mDialogLayoutBinding = CommentDialogLayoutBinding.inflate(LayoutInflater.from(this.mContext));
        this.mDialog = new Dialog(this.mContext, 16973840);
        this.mDialog.setContentView(this.mDialogLayoutBinding.getRoot());
        this.mDialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
            }
        });
        initBarView();
    }

    public void initBarView() {
        this.mDialogLayoutBinding.ivFullScreen.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (CommentDialog.this.mDialogLayoutBinding.ivImage.isShown()) {
                    CommentDialog.this.mDialogLayoutBinding.ivImage.setVisibility(8);
                } else {
                    CommentDialog.this.mDialogLayoutBinding.ivImage.setVisibility(0);
                }
            }
        });
        this.mDialogLayoutBinding.ivAddEmoji.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                CommentDialog.this.mDialogLayoutBinding.kbDefEmoticons.openEmoticonsKeyboard();
            }
        });
        this.mDialogLayoutBinding.ivAddImage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                Intent intent = new Intent(CommentDialog.this.mContext, SelectPhotoActivity.class);
                intent.putExtra(FengConstant.FENGTYPE, 1);
                CommentDialog.this.mContext.startActivity(intent);
            }
        });
        this.mDialogLayoutBinding.ivAddAt.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                CommentDialog.this.mContext.startActivity(new Intent(CommentDialog.this.mContext, AtUserActivity.class));
            }
        });
        this.mDialogLayoutBinding.kbDefEmoticons.setKeyboardStatusChangeListener(new OnKeyboardStatusChangeListener() {
            public void onStatusChange(int status) {
                switch (status) {
                    case 1001:
                        CommentDialog.this.mDialogLayoutBinding.ivAddEmoji.setImageResource(2130838093);
                        return;
                    case 1002:
                        CommentDialog.this.mDialogLayoutBinding.ivAddEmoji.setImageResource(2130838057);
                        return;
                    default:
                        return;
                }
            }
        });
        initEmoticonsKeyBoardBar();
    }

    private void initEmoticonsKeyBoardBar() {
        this.mInputFilter = new EditInputFilter(this.mContext);
        this.mDialogLayoutBinding.atContent.setFilters(new InputFilter[]{this.mInputFilter});
        this.mDialogLayoutBinding.atContent.addEmoticonFilter(new AtFilter());
        this.mDialogLayoutBinding.kbDefEmoticons.setAdapter(SimpleCommonUtils.getCommonAdapter(this.mContext, this.emoticonClickListener));
        this.mDialogLayoutBinding.kbDefEmoticons.addOnFuncKeyBoardListener(this);
        this.mDialogLayoutBinding.kbDefEmoticons.setInputEditText(this.mDialogLayoutBinding.atContent);
        this.mDialogLayoutBinding.llContent.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                CommentDialog.this.mDialogLayoutBinding.kbDefEmoticons.reset();
                if (CommentDialog.this.mDialog != null) {
                    CommentDialog.this.mDialog.dismiss();
                }
            }
        });
        this.mDialogLayoutBinding.rlDialogLayoutComment.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
            }
        });
        this.mDialogLayoutBinding.atContent.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                boolean z = true;
                int count = StringUtil.strLenthLikeWeiBo(CommentDialog.this.mDialogLayoutBinding.atContent.getText().toString().trim(), true);
                CommentDialog.this.mDialogLayoutBinding.tvTextCount.setText(String.valueOf(count));
                if (count > CommentDialog.this.mCountLimitCount) {
                    CommentDialog.this.mDialogLayoutBinding.tvTextCount.setTextColor(ContextCompat.getColor(CommentDialog.this.mContext, 2131558509));
                } else {
                    CommentDialog.this.mDialogLayoutBinding.tvTextCount.setTextColor(ContextCompat.getColor(CommentDialog.this.mContext, 2131558448));
                }
                CommentDialog commentDialog = CommentDialog.this;
                if (count <= 0 || count > CommentDialog.this.mCountLimitCount) {
                    z = false;
                }
                commentDialog.mContentOk = z;
                CommentDialog.this.changeRightBtn();
            }
        });
    }

    private void changeRightBtn() {
        TextView textView = this.mDialogLayoutBinding.tvSend;
        boolean z = this.mContentOk || !TextUtils.isEmpty(this.mImagePath);
        textView.setSelected(z);
    }

    public void showInputComment(View view, final RecyclerView recyclerView) {
        if (this.mDialog == null) {
            init();
        } else {
            this.mDialog.show();
        }
        final int rvInputY = getY(view);
        final int rvInputHeight = view.getHeight();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                recyclerView.smoothScrollBy(0, rvInputY - (CommentDialog.this.getY(CommentDialog.this.mDialogLayoutBinding.rlDialogLayoutComment) - rvInputHeight));
            }
        }, 300);
    }

    private int getY(View view) {
        int[] rect = new int[2];
        view.getLocationOnScreen(rect);
        return rect[1];
    }

    public void OnFuncPop(int height) {
    }

    public void OnFuncClose() {
    }
}
