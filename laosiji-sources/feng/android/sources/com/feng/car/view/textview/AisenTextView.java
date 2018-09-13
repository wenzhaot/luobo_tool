package com.feng.car.view.textview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.text.util.Linkify.TransformFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.PersonalHomePageNewActivity;
import com.feng.car.activity.ShowBigImageActivity;
import com.feng.car.activity.VideoFinalPageActivity;
import com.feng.car.entity.sns.ResourcesInfoList;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsPostResources;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.VideoManager;
import com.feng.car.view.textview.MyURLSpan.OnUrlClickListener;
import com.feng.library.emoticons.keyboard.EmoticonsRule;
import com.feng.library.emoticons.keyboard.SimpleCommonUtils;
import com.tencent.rtmp.TXLiveConstants;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class AisenTextView extends AppCompatTextView {
    private String mContent = "";
    private Context mContext;
    private boolean mHasComment = false;
    private ResourcesInfoList mImageList;
    private boolean mIsMatchLink = true;
    private String mScheme = "com.feng.car.intent://?content=";
    private SnsInfo mSnsInfo;
    private OnUrlLongClickListener mUrlLongClickListener;
    private ResourcesInfoList mVideoList;
    private OnTouchListener onTouchListener = new OnTouchListener() {
        ClickableTextViewMentionLinkOnTouchListener listener = new ClickableTextViewMentionLinkOnTouchListener();

        public boolean onTouch(View v, MotionEvent event) {
            return this.listener.onTouch(v, event);
        }
    };

    public AisenTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
    }

    public AisenTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public AisenTextView(Context context) {
        super(context);
        this.mContext = context;
    }

    public void setMatchLink(boolean isMatchLink) {
        this.mIsMatchLink = isMatchLink;
    }

    public void setUrlLongClickListener(OnUrlLongClickListener listener) {
        this.mUrlLongClickListener = listener;
    }

    public void setContent(String text, boolean isMatchAt) {
        setContent(text, isMatchAt, false, null, null);
        this.mHasComment = false;
    }

    public void setContent(String text, boolean isMatchAt, ResourcesInfoList imageList, ResourcesInfoList videoList, SnsInfo info) {
        setContent(text, isMatchAt, true, imageList, videoList);
        if (info != null) {
            this.mHasComment = true;
            this.mSnsInfo = info;
            return;
        }
        this.mHasComment = false;
    }

    private void setContent(String text, boolean isMatchAt, boolean isMatchImageVideo, ResourcesInfoList imageList, ResourcesInfoList videoList) {
        if (TextUtils.isEmpty(text)) {
            super.setText(text);
        } else if (!TextUtils.isEmpty(this.mContent) || !this.mContent.equals(text)) {
            this.mContent = text;
            Matcher emoticon = HttpConstant.PATTERN_EMOTICON.matcher(text);
            while (emoticon.find()) {
                String key = emoticon.group();
                String value = (String) EmoticonsRule.sXhsEmoticonTextHashMap.get(key);
                if (!TextUtils.isEmpty(value)) {
                    text = text.replace(key, value);
                }
            }
            this.mImageList = imageList;
            this.mVideoList = videoList;
            final Spannable spannableString = new SpannableStringBuilder(text);
            if (isMatchAt) {
                Linkify.addLinks(spannableString, HttpConstant.PATTERN_NAME, this.mScheme, null, new TransformFilter() {
                    public String transformUrl(Matcher match, String url) {
                        return url + "&type=1";
                    }
                });
            }
            if (isMatchImageVideo) {
                matchImageVideo(spannableString);
            }
            if (this.mIsMatchLink) {
                Matcher matcherUrl = HttpConstant.PATTERN_URL.matcher(spannableString);
                if (matcherUrl != null) {
                    int offset = 0;
                    int nLength = "查看链接".length();
                    while (matcherUrl.find()) {
                        if (matcherUrl.group().indexOf("查看") <= 0) {
                            spannableString.insert(matcherUrl.end() + offset, "查看链接");
                            offset += nLength;
                        }
                    }
                }
                Linkify.addLinks(spannableString, HttpConstant.PATTERN_TRANSFORM_URL, this.mScheme, null, new TransformFilter() {
                    public String transformUrl(Matcher match, String url) {
                        int startIndex = match.start();
                        int nKeyWordLength = "查看链接".length();
                        spannableString.setSpan(new CenteredImageSpan(AisenTextView.this.getContext(), 2130838111), startIndex, match.end() - nKeyWordLength, 33);
                        return AisenTextView.this.encode(url) + "&type=2";
                    }
                });
            }
            for (final URLSpan urlSpan : (URLSpan[]) spannableString.getSpans(0, spannableString.length(), URLSpan.class)) {
                MyURLSpan weiboSpan = new MyURLSpan(urlSpan.getURL());
                weiboSpan.setOnUrlClickListener(new OnUrlClickListener() {
                    public void onUrlClick() {
                        AisenTextView.this.handlerOnClick(urlSpan.getURL());
                    }

                    public void onLongClick() {
                        if (AisenTextView.this.mUrlLongClickListener != null) {
                            AisenTextView.this.mUrlLongClickListener.onLongClick();
                        }
                    }
                });
                weiboSpan.setColor(ContextCompat.getColor(getContext(), 2131558446));
                int start = spannableString.getSpanStart(urlSpan);
                int end = spannableString.getSpanEnd(urlSpan);
                try {
                    spannableString.removeSpan(urlSpan);
                } catch (Exception e) {
                }
                spannableString.setSpan(weiboSpan, start, end, 33);
            }
            SimpleCommonUtils.spannableEmoticonFilterWithBuilder((TextView) this, spannableString.toString(), spannableString);
            setOnTouchListener(this.onTouchListener);
        }
    }

    private String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    private String decode(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    private void handlerOnClick(String url) {
        Uri uri = Uri.parse(url);
        if (uri.getQueryParameterNames().contains("type")) {
            Intent intent;
            Matcher matcherImage;
            String strUrl;
            int type = Integer.parseInt(uri.getQueryParameter("type"));
            if (type == 1) {
                String content = uri.getQueryParameter("content");
                if (content.indexOf("@") == 0) {
                    String userName = content.substring(1);
                    intent = new Intent(this.mContext, PersonalHomePageNewActivity.class);
                    intent.putExtra("name", userName);
                    this.mContext.startActivity(intent);
                }
            } else if (type == 2) {
                url = decode(url);
                matcherImage = HttpConstant.PATTERN_URL.matcher(url);
                while (matcherImage.find()) {
                    strUrl = matcherImage.group();
                    if (strUrl.lastIndexOf("查看链接&type=2") > 0) {
                        strUrl = strUrl.replace("查看链接&type=2", "");
                    }
                    FengApplication.getInstance().handlerUrlSkip(this.mContext, strUrl, "", false, null);
                }
            }
            int pos;
            if (type == 3) {
                matcherImage = HttpConstant.PATTERN_IMAGE.matcher(url);
                if (matcherImage.find()) {
                    strUrl = matcherImage.group();
                    pos = this.mImageList.getPosition(strUrl.substring(1, strUrl.length() - 1));
                    if (pos != -1) {
                        List list = new ArrayList();
                        list.add(this.mImageList.get(pos).image);
                        intent = new Intent(this.mContext, ShowBigImageActivity.class);
                        intent.putExtra(FengConstant.IMAGE_LIST, JsonUtil.toJson(list));
                        intent.putExtra("position", 0);
                        intent.putExtra(FengConstant.BIG_IMAGE_SHOW_TYPE, TXLiveConstants.PUSH_EVT_CHANGE_RESOLUTION);
                        intent.putExtra(FengConstant.BIG_IMAGE_ENTRANCE_TYPE, 2003);
                        this.mContext.startActivity(intent);
                        ((Activity) this.mContext).overridePendingTransition(0, 0);
                        return;
                    }
                    ((BaseActivity) this.mContext).showSecondTypeToast(2131231315);
                }
            } else if (type == 4) {
                matcherImage = HttpConstant.PATTERN_VIDEO.matcher(url);
                if (matcherImage.find()) {
                    strUrl = matcherImage.group();
                    pos = this.mVideoList.getPosition(strUrl.substring(1, strUrl.length() - 1));
                    if (pos != -1) {
                        SnsPostResources info = this.mVideoList.get(pos);
                        if (info != null && info.type == 3) {
                            intent = new Intent(this.mContext, VideoFinalPageActivity.class);
                            intent.putExtra(FengConstant.FROM_KEY, true);
                            VideoManager.newInstance().updateVideoInfo(VideoManager.newInstance().createMediaInfo(info));
                            intent.putExtra(FengConstant.MEDIA, info.videourl.hash);
                            this.mContext.startActivity(intent);
                            return;
                        }
                        return;
                    }
                    ((BaseActivity) this.mContext).showSecondTypeToast(2131231320);
                }
            } else if (type == 6 && this.mSnsInfo != null) {
                if (this.mSnsInfo.snstype == 0 || this.mSnsInfo.snstype == 1) {
                    this.mSnsInfo.intentToArticle(this.mContext, -1);
                } else if (this.mSnsInfo.snstype == 9 || this.mSnsInfo.snstype == 10) {
                    this.mSnsInfo.intentToViewPoint(this.mContext, false);
                }
            }
        }
    }

    private void matchImageVideo(final SpannableStringBuilder spannableString) {
        int offset;
        int nLength;
        Matcher matcherImage = HttpConstant.PATTERN_IMAGE.matcher(spannableString);
        if (matcherImage != null) {
            offset = 0;
            nLength = "查看图片".length();
            while (matcherImage.find()) {
                spannableString.insert(matcherImage.end() + offset, "查看图片");
                offset += nLength;
            }
        }
        Linkify.addLinks(spannableString, HttpConstant.PATTERN_TRANSFORM_IMAGE, this.mScheme, null, new TransformFilter() {
            public String transformUrl(Matcher match, String url) {
                int startIndex = match.start();
                int nKeyWordLength = "查看图片".length();
                spannableString.setSpan(new CenteredImageSpan(AisenTextView.this.getContext(), 2130838091), startIndex, match.end() - nKeyWordLength, 33);
                return url + "&type=3";
            }
        });
        Matcher matcherVideo = HttpConstant.PATTERN_VIDEO.matcher(spannableString);
        if (matcherVideo != null) {
            offset = 0;
            nLength = "查看视频".length();
            while (matcherVideo.find()) {
                spannableString.insert(matcherVideo.end() + offset, "查看视频");
                offset += nLength;
            }
        }
        Linkify.addLinks(spannableString, HttpConstant.PATTERN_TRANSFORM_VIDEO, this.mScheme, null, new TransformFilter() {
            public String transformUrl(Matcher match, String url) {
                int startIndex = match.start();
                int nKeyWordLength = "查看视频".length();
                spannableString.setSpan(new CenteredImageSpan(AisenTextView.this.getContext(), 2130838262), startIndex, match.end() - nKeyWordLength, 33);
                return url + "&type=4";
            }
        });
        Matcher matcherLookAll = HttpConstant.PATTERN_LOOK_ALL.matcher(spannableString);
        if (matcherLookAll != null) {
            offset = 0;
            nLength = "全文".length();
            while (matcherLookAll.find()) {
                spannableString.insert(matcherLookAll.end() + offset, "全文");
                offset += nLength;
            }
        }
        Linkify.addLinks(spannableString, HttpConstant.PATTERN_TRANSFORM_LOOK_ALL, this.mScheme, null, new TransformFilter() {
            public String transformUrl(Matcher match, String url) {
                int startIndex = match.start();
                int nKeyWordLength = "全文".length();
                spannableString.setSpan(new CenteredImageSpan(AisenTextView.this.getContext(), 2130838116), startIndex, match.end() - nKeyWordLength, 33);
                return url + "&type=6";
            }
        });
    }
}
