package com.umeng.socialize.shareboard;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText.SHAREBOARD;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

class ShareBoardMenuHelper {
    private static String TAG = ShareBoardMenuHelper.class.getSimpleName();
    private ShareBoardConfig mShareBoardConfig;

    public ShareBoardMenuHelper(ShareBoardConfig shareBoardConfig) {
        this.mShareBoardConfig = shareBoardConfig;
    }

    public List<SnsPlatform[][]> formatPageData(List<SnsPlatform> list) {
        int i = 1;
        int i2 = this.mShareBoardConfig.mMenuColumnNum * 2;
        int size = list.size();
        List<SnsPlatform[][]> arrayList = new ArrayList();
        SnsPlatform[][] snsPlatformArr;
        int i3;
        if (size < this.mShareBoardConfig.mMenuColumnNum) {
            snsPlatformArr = (SnsPlatform[][]) Array.newInstance(SnsPlatform.class, new int[]{1, size});
            for (i3 = 0; i3 < list.size(); i3++) {
                snsPlatformArr[0][i3] = (SnsPlatform) list.get(i3);
            }
            arrayList.add(snsPlatformArr);
            return arrayList;
        }
        int i4;
        int i5 = size / i2;
        i2 = size % i2;
        if (i2 != 0) {
            i4 = i2 / this.mShareBoardConfig.mMenuColumnNum;
            if (i2 % this.mShareBoardConfig.mMenuColumnNum == 0) {
                i = 0;
            }
            i2 = i5 + 1;
            i5 = i + i4;
        } else {
            i2 = i5;
            i5 = -1;
        }
        for (i4 = 0; i4 < i2; i4++) {
            if (i4 != i2 - 1 || i5 == -1) {
                i = 2;
            } else {
                i = i5;
            }
            arrayList.add((SnsPlatform[][]) Array.newInstance(SnsPlatform.class, new int[]{i, this.mShareBoardConfig.mMenuColumnNum}));
        }
        i5 = 0;
        for (i3 = 0; i3 < arrayList.size(); i3++) {
            snsPlatformArr = (SnsPlatform[][]) arrayList.get(i3);
            int length = snsPlatformArr.length;
            int i6 = 0;
            while (i6 < length) {
                SnsPlatform[] snsPlatformArr2 = snsPlatformArr[i6];
                i2 = 0;
                while (true) {
                    i4 = i5;
                    if (i2 >= snsPlatformArr2.length) {
                        break;
                    }
                    if (i4 < size) {
                        snsPlatformArr2[i2] = (SnsPlatform) list.get(i4);
                    }
                    i4++;
                    i5 = i2 + 1;
                }
                i6++;
                i5 = i4;
            }
        }
        return arrayList;
    }

    public View createPageLayout(Context context, SnsPlatform[][] snsPlatformArr) {
        View linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        linearLayout.setGravity(48);
        linearLayout.setLayoutParams(new LayoutParams(-1, -2));
        for (int i = 0; i < snsPlatformArr.length; i++) {
            boolean z;
            SnsPlatform[] snsPlatformArr2 = snsPlatformArr[i];
            if (i != 0) {
                z = true;
            } else {
                z = false;
            }
            linearLayout.addView(createRowLayout(context, snsPlatformArr2, z));
        }
        return linearLayout;
    }

    private View createRowLayout(Context context, SnsPlatform[] snsPlatformArr, boolean z) {
        int i = 0;
        View linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(0);
        linearLayout.setGravity(1);
        ViewGroup.LayoutParams layoutParams = new LayoutParams(-1, -2);
        if (z) {
            layoutParams.topMargin = dip2px(context, 20.0f);
        }
        linearLayout.setLayoutParams(layoutParams);
        while (i < snsPlatformArr.length) {
            linearLayout.addView(createBtnView(context, snsPlatformArr[i]));
            i++;
        }
        return linearLayout;
    }

    private View createBtnView(Context context, final SnsPlatform snsPlatform) {
        SHARE_MEDIA share_media;
        View linearLayout = new LinearLayout(context);
        ViewGroup.LayoutParams layoutParams = new LayoutParams(0, -2);
        layoutParams.weight = 1.0f;
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setGravity(17);
        if (snsPlatform != null) {
            CharSequence charSequence;
            int resourceId;
            ResContainer resContainer = ResContainer.get(context);
            View inflate = LayoutInflater.from(context).inflate(resContainer.layout("socialize_share_menu_item"), null);
            SocializeImageView socializeImageView = (SocializeImageView) inflate.findViewById(resContainer.id("socialize_image_view"));
            TextView textView = (TextView) inflate.findViewById(resContainer.id("socialize_text_view"));
            if (this.mShareBoardConfig.mMenuBgColor == 0 || this.mShareBoardConfig.mMenuBgShape == ShareBoardConfig.BG_SHAPE_NONE) {
                socializeImageView.setPadding(0, 0, 0, 0);
            } else {
                socializeImageView.setBackgroundColor(this.mShareBoardConfig.mMenuBgColor, this.mShareBoardConfig.mMenuBgPressedColor);
                socializeImageView.setBackgroundShape(this.mShareBoardConfig.mMenuBgShape, this.mShareBoardConfig.mMenuBgShapeAngle);
            }
            if (this.mShareBoardConfig.mMenuIconPressedColor != 0) {
                socializeImageView.setPressedColor(this.mShareBoardConfig.mMenuIconPressedColor);
            }
            String str = "";
            try {
                charSequence = snsPlatform.mShowWord;
            } catch (Throwable e) {
                share_media = snsPlatform.mPlatform;
                SLog.error(SHAREBOARD.NULLNAME + (share_media == null ? "" : share_media.toString()), e);
                Object charSequence2 = str;
            }
            if (!TextUtils.isEmpty(charSequence2)) {
                textView.setText(snsPlatform.mShowWord);
            }
            textView.setGravity(17);
            try {
                resourceId = ResContainer.getResourceId(context, "drawable", snsPlatform.mIcon);
            } catch (Throwable e2) {
                Throwable th = e2;
                share_media = snsPlatform.mPlatform;
                SLog.error(SHAREBOARD.NULLNAME + (share_media == null ? "" : share_media.toString()), th);
                resourceId = 0;
            }
            if (resourceId != 0) {
                socializeImageView.setImageResource(resourceId);
            }
            if (this.mShareBoardConfig.mMenuTextColor != 0) {
                textView.setTextColor(this.mShareBoardConfig.mMenuTextColor);
            }
            inflate.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    SHARE_MEDIA share_media = snsPlatform.mPlatform;
                    if (ShareBoardMenuHelper.this.mShareBoardConfig != null && ShareBoardMenuHelper.this.mShareBoardConfig.getShareBoardlistener() != null) {
                        ShareBoardMenuHelper.this.mShareBoardConfig.getShareBoardlistener().onclick(snsPlatform, share_media);
                    }
                }
            });
            linearLayout.addView(inflate);
        }
        return linearLayout;
    }

    private int dip2px(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + 0.5f);
    }
}
