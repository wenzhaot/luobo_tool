package com.umeng.socialize;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMEmoji;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.shareboard.ShareBoard;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.ShareBoardlistener;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShareAction {
    private ShareContent a = new ShareContent();
    private SHARE_MEDIA b = null;
    private UMShareListener c = null;
    private ShareBoardlistener d = null;
    private Activity e;
    private List<SHARE_MEDIA> f = null;
    private List<SnsPlatform> g = new ArrayList();
    private List<ShareContent> h = new ArrayList();
    private List<UMShareListener> i = new ArrayList();
    private int j = 80;
    private View k = null;
    private ShareBoard l;
    private ShareBoardlistener m = new ShareBoardlistener() {
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            ShareAction.this.setPlatform(share_media);
            ShareAction.this.share();
        }
    };
    private ShareBoardlistener n = new ShareBoardlistener() {
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            int indexOf = ShareAction.this.f.indexOf(share_media);
            int size = ShareAction.this.h.size();
            if (size != 0) {
                ShareContent shareContent;
                if (indexOf < size) {
                    shareContent = (ShareContent) ShareAction.this.h.get(indexOf);
                } else {
                    shareContent = (ShareContent) ShareAction.this.h.get(size - 1);
                }
                ShareAction.this.a = shareContent;
            }
            size = ShareAction.this.i.size();
            if (size != 0) {
                if (indexOf < size) {
                    ShareAction.this.c = (UMShareListener) ShareAction.this.i.get(indexOf);
                } else {
                    ShareAction.this.c = (UMShareListener) ShareAction.this.i.get(size - 1);
                }
            }
            ShareAction.this.setPlatform(share_media);
            ShareAction.this.share();
        }
    };

    public ShareAction(Activity activity) {
        if (activity != null) {
            this.e = (Activity) new WeakReference(activity).get();
        }
    }

    public ShareContent getShareContent() {
        return this.a;
    }

    public boolean getUrlValid() {
        if (this.a == null || this.a.mMedia == null || !(this.a.mMedia instanceof UMWeb) || this.a.mMedia.toUrl() == null || this.a.mMedia.toUrl().startsWith("http")) {
            return true;
        }
        return false;
    }

    public SHARE_MEDIA getPlatform() {
        return this.b;
    }

    public ShareAction setPlatform(SHARE_MEDIA share_media) {
        this.b = share_media;
        return this;
    }

    public ShareAction setCallback(UMShareListener uMShareListener) {
        this.c = uMShareListener;
        return this;
    }

    public ShareAction setShareboardclickCallback(ShareBoardlistener shareBoardlistener) {
        this.d = shareBoardlistener;
        return this;
    }

    public ShareAction setShareContent(ShareContent shareContent) {
        this.a = shareContent;
        return this;
    }

    public ShareAction setDisplayList(SHARE_MEDIA... share_mediaArr) {
        this.f = Arrays.asList(share_mediaArr);
        this.g.clear();
        for (SHARE_MEDIA toSnsPlatform : this.f) {
            this.g.add(toSnsPlatform.toSnsPlatform());
        }
        return this;
    }

    @Deprecated
    public ShareAction setListenerList(UMShareListener... uMShareListenerArr) {
        this.i = Arrays.asList(uMShareListenerArr);
        return this;
    }

    @Deprecated
    public ShareAction setContentList(ShareContent... shareContentArr) {
        if (shareContentArr == null || Arrays.asList(shareContentArr).size() == 0) {
            ShareContent shareContent = new ShareContent();
            shareContent.mText = "empty";
            this.h.add(shareContent);
        } else {
            this.h = Arrays.asList(shareContentArr);
        }
        return this;
    }

    public ShareAction addButton(String str, String str2, String str3, String str4) {
        this.g.add(SHARE_MEDIA.createSnsPlatform(str, str2, str3, str4, 0));
        return this;
    }

    public ShareAction withText(String str) {
        this.a.mText = str;
        return this;
    }

    public ShareAction withSubject(String str) {
        this.a.subject = str;
        return this;
    }

    public ShareAction withFile(File file) {
        this.a.file = file;
        return this;
    }

    public ShareAction withApp(File file) {
        this.a.app = file;
        return this;
    }

    public ShareAction withMedia(UMImage uMImage) {
        this.a.mMedia = uMImage;
        return this;
    }

    public ShareAction withMedias(UMImage... uMImageArr) {
        if (uMImageArr != null && uMImageArr.length > 0) {
            this.a.mMedia = uMImageArr[0];
        }
        this.a.mMedias = uMImageArr;
        return this;
    }

    public ShareAction withMedia(UMMin uMMin) {
        this.a.mMedia = uMMin;
        return this;
    }

    public ShareAction withMedia(UMEmoji uMEmoji) {
        this.a.mMedia = uMEmoji;
        return this;
    }

    public ShareAction withMedia(UMWeb uMWeb) {
        this.a.mMedia = uMWeb;
        return this;
    }

    public ShareAction withFollow(String str) {
        this.a.mFollow = str;
        return this;
    }

    public ShareAction withExtra(UMImage uMImage) {
        this.a.mExtra = uMImage;
        return this;
    }

    public ShareAction withMedia(UMusic uMusic) {
        this.a.mMedia = uMusic;
        return this;
    }

    public ShareAction withMedia(UMVideo uMVideo) {
        this.a.mMedia = uMVideo;
        return this;
    }

    public ShareAction withShareBoardDirection(View view, int i) {
        this.j = i;
        this.k = view;
        return this;
    }

    public void share() {
        UMShareAPI.get(this.e).doShare(this.e, this, this.c);
    }

    public void open(ShareBoardConfig shareBoardConfig) {
        Map hashMap;
        if (this.g.size() != 0) {
            hashMap = new HashMap();
            hashMap.put("listener", this.c);
            hashMap.put("content", this.a);
            try {
                this.l = new ShareBoard(this.e, this.g, shareBoardConfig);
                if (this.d == null) {
                    this.l.setShareBoardlistener(this.n);
                } else {
                    this.l.setShareBoardlistener(this.d);
                }
                this.l.setFocusable(true);
                this.l.setBackgroundDrawable(new BitmapDrawable());
                if (this.k == null) {
                    this.k = this.e.getWindow().getDecorView();
                }
                this.l.showAtLocation(this.k, this.j, 0, 0);
                return;
            } catch (Throwable e) {
                SLog.error(e);
                return;
            }
        }
        this.g.add(SHARE_MEDIA.WEIXIN.toSnsPlatform());
        this.g.add(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform());
        this.g.add(SHARE_MEDIA.SINA.toSnsPlatform());
        this.g.add(SHARE_MEDIA.QQ.toSnsPlatform());
        hashMap = new HashMap();
        hashMap.put("listener", this.c);
        hashMap.put("content", this.a);
        this.l = new ShareBoard(this.e, this.g, shareBoardConfig);
        if (this.d == null) {
            this.l.setShareBoardlistener(this.m);
        } else {
            this.l.setShareBoardlistener(this.d);
        }
        this.l.setFocusable(true);
        this.l.setBackgroundDrawable(new BitmapDrawable());
        if (this.k == null) {
            this.k = this.e.getWindow().getDecorView();
        }
        this.l.showAtLocation(this.k, 80, 0, 0);
    }

    public void open() {
        open(null);
    }

    public void close() {
        if (this.l != null) {
            this.l.dismiss();
            this.l = null;
        }
    }

    public static Rect locateView(View view) {
        int[] iArr = new int[2];
        if (view == null) {
            return null;
        }
        try {
            view.getLocationOnScreen(iArr);
            Rect rect = new Rect();
            rect.left = iArr[0];
            rect.top = iArr[1];
            rect.right = rect.left + view.getWidth();
            rect.bottom = rect.top + view.getHeight();
            return rect;
        } catch (Throwable e) {
            SLog.error(e);
            return null;
        }
    }
}
