package com.feng.library.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.feng.library.R;
import com.feng.library.emoticons.keyboard.Constants;
import com.feng.library.emoticons.keyboard.EmoticonsRule;
import com.feng.library.emoticons.keyboard.adpater.EmoticonsAdapter;
import com.feng.library.emoticons.keyboard.adpater.EmoticonsAdapter.ViewHolder;
import com.feng.library.emoticons.keyboard.adpater.PageSetAdapter;
import com.feng.library.emoticons.keyboard.data.EmoticonEntity;
import com.feng.library.emoticons.keyboard.data.EmoticonPageEntity;
import com.feng.library.emoticons.keyboard.data.EmoticonPageEntity.DelBtnStatus;
import com.feng.library.emoticons.keyboard.data.EmoticonPageSetEntity.Builder;
import com.feng.library.emoticons.keyboard.filter.XhsFilter;
import com.feng.library.emoticons.keyboard.interfaces.EmoticonClickListener;
import com.feng.library.emoticons.keyboard.interfaces.EmoticonDisplayListener;
import com.feng.library.emoticons.keyboard.interfaces.PageViewInstantiateListener;
import com.feng.library.emoticons.keyboard.utils.EmoticonsKeyboardUtils;
import com.feng.library.emoticons.keyboard.utils.ParseDataUtils;
import com.feng.library.emoticons.keyboard.utils.imageloader.ImageBase.Scheme;
import com.feng.library.emoticons.keyboard.utils.imageloader.ImageLoader;
import com.feng.library.emoticons.keyboard.widget.EmoticonPageView;
import com.feng.library.emoticons.keyboard.widget.UserDefEmoticonsEditText;
import java.io.IOException;

public class EmoticonsUtils {
    public static PageSetAdapter sCommonPageSetAdapter;

    public static void initEmoticonsEditText(UserDefEmoticonsEditText etContent) {
        etContent.addEmoticonFilter(new XhsFilter());
    }

    public static PageSetAdapter getCommonAdapter(Context context, EmoticonClickListener emoticonClickListener) {
        if (sCommonPageSetAdapter != null) {
            return sCommonPageSetAdapter;
        }
        PageSetAdapter pageSetAdapter = new PageSetAdapter();
        addXhsPageSetEntity(pageSetAdapter, context, emoticonClickListener);
        return pageSetAdapter;
    }

    public static void addXhsPageSetEntity(PageSetAdapter pageSetAdapter, Context context, EmoticonClickListener emoticonClickListener) {
        pageSetAdapter.add(new Builder().setLine(3).setRow(7).setEmoticonList(ParseDataUtils.ParseXhsData(EmoticonsRule.xhsEmoticonArray, Scheme.DRAWABLE)).setIPageViewInstantiateItem(getDefaultEmoticonPageViewInstantiateItem(getCommonEmoticonDisplayListener(emoticonClickListener, Constants.EMOTICON_CLICK_TEXT))).setShowDelBtn(DelBtnStatus.LAST).setIconUri(Scheme.DRAWABLE.toUri("xhsemoji_19")).build());
    }

    public static Object newInstance(Class _Class, Object... args) throws Exception {
        return newInstance(_Class, 0, args);
    }

    public static Object newInstance(Class _Class, int constructorIndex, Object... args) throws Exception {
        return _Class.getConstructors()[constructorIndex].newInstance(args);
    }

    public static PageViewInstantiateListener<EmoticonPageEntity> getDefaultEmoticonPageViewInstantiateItem(EmoticonDisplayListener<Object> emoticonDisplayListener) {
        return getEmoticonPageViewInstantiateItem(EmoticonsAdapter.class, null, emoticonDisplayListener);
    }

    public static PageViewInstantiateListener<EmoticonPageEntity> getEmoticonPageViewInstantiateItem(Class _class, EmoticonClickListener onEmoticonClickListener) {
        return getEmoticonPageViewInstantiateItem(_class, onEmoticonClickListener, null);
    }

    public static PageViewInstantiateListener<EmoticonPageEntity> getEmoticonPageViewInstantiateItem(final Class _class, final EmoticonClickListener onEmoticonClickListener, final EmoticonDisplayListener<Object> emoticonDisplayListener) {
        return new PageViewInstantiateListener<EmoticonPageEntity>() {
            public View instantiateItem(ViewGroup container, int position, EmoticonPageEntity pageEntity) {
                if (pageEntity.getRootView() == null) {
                    EmoticonPageView pageView = new EmoticonPageView(container.getContext());
                    pageView.setNumColumns(pageEntity.getRow());
                    pageEntity.setRootView(pageView);
                    try {
                        EmoticonsAdapter adapter = (EmoticonsAdapter) EmoticonsUtils.newInstance(_class, container.getContext(), pageEntity, onEmoticonClickListener);
                        if (emoticonDisplayListener != null) {
                            adapter.setOnDisPlayListener(emoticonDisplayListener);
                        }
                        pageView.getEmoticonsGridView().setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return pageEntity.getRootView();
            }
        };
    }

    public static EmoticonDisplayListener<Object> getCommonEmoticonDisplayListener(final EmoticonClickListener onEmoticonClickListener, final int type) {
        return new EmoticonDisplayListener<Object>() {
            public void onBindView(int position, ViewGroup parent, ViewHolder viewHolder, Object object, final boolean isDelBtn) {
                final EmoticonEntity emoticonEntity = (EmoticonEntity) object;
                if (emoticonEntity != null || isDelBtn) {
                    viewHolder.ly_root.setBackgroundResource(R.drawable.emoji_bg_emoticon);
                    if (isDelBtn) {
                        viewHolder.iv_emoticon.setImageResource(R.drawable.emoji_icon_del);
                    } else {
                        try {
                            ImageLoader.getInstance(viewHolder.iv_emoticon.getContext()).displayImage(emoticonEntity.getIconUri(), viewHolder.iv_emoticon);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    viewHolder.rootView.setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            if (onEmoticonClickListener != null) {
                                onEmoticonClickListener.onEmoticonClick(emoticonEntity, type, isDelBtn);
                            }
                        }
                    });
                }
            }
        };
    }

    public static void delClick(EditText editText) {
        editText.onKeyDown(67, new KeyEvent(0, 67));
    }

    public static void spannableEmoticonFilter(TextView tv_content, String content) {
        tv_content.setText(XhsFilter.spannableFilter(tv_content.getContext(), new SpannableStringBuilder(content), content, EmoticonsKeyboardUtils.getFontHeight(tv_content), null));
    }

    public static void spannableEmoticonFilterWithBuilder(TextView tv_content, String content, Spannable spannable) {
        tv_content.setText(XhsFilter.spannableFilter(tv_content.getContext(), spannable, content, EmoticonsKeyboardUtils.getFontHeight(tv_content), null));
    }

    public static void spannableEmoticonFilter(EditText et_content, String content) {
        et_content.setText(XhsFilter.spannableFilter(et_content.getContext(), new SpannableStringBuilder(content), content, EmoticonsKeyboardUtils.getFontHeight(et_content), null));
    }

    public static void spannableEmoticonFilterWithBuilder(EditText et_content, String content, Spannable spannable) {
        et_content.setText(XhsFilter.spannableFilter(et_content.getContext(), spannable, content, EmoticonsKeyboardUtils.getFontHeight(et_content), null));
    }
}
