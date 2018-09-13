package com.umeng.socialize.editorpage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.utils.UmengText.SHARE;
import java.io.File;

public class ShareActivity extends Activity implements OnClickListener {
    private static final String c = "ShareActivity";
    private static int d = 140;
    protected ImageView a;
    TextWatcher b = new TextWatcher() {
        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            ShareActivity.this.m = ShareActivity.this.e();
        }
    };
    private String e = "6.9.2";
    private String f;
    private String g;
    private String h;
    private ResContainer i;
    private EditText j;
    private TextView k;
    private Context l;
    private boolean m;
    private SHARE_MEDIA n;
    private boolean o = false;
    private ImageView p;
    private TextView q;

    protected void onCreate(Bundle bundle) {
        this.i = ResContainer.get(this);
        this.o = SocializeUtils.isFloatWindowStyle(this);
        super.onCreate(bundle);
        this.l = this;
        setContentView(this.i.layout("umeng_socialize_share"));
        LayoutParams attributes = getWindow().getAttributes();
        attributes.softInputMode = 32;
        if (this.o) {
            int[] floatWindowSize = SocializeUtils.getFloatWindowSize(this.l);
            attributes.width = floatWindowSize[0];
            attributes.height = floatWindowSize[1];
        }
        getWindow().setAttributes(attributes);
        SLog.E(SHARE.SHAREVIEWV + this.e);
        Bundle extras = getIntent().getExtras();
        this.n = a(extras.getString(SocializeConstants.KEY_PLATFORM));
        if (this.n == SHARE_MEDIA.RENREN) {
            d = 120;
        } else {
            d = 140;
        }
        this.f = extras.getString(SocializeConstants.KEY_TEXT);
        this.h = extras.getString("pic");
        this.g = extras.getString("title");
        b();
        this.p = (ImageView) findViewById(this.i.id("umeng_del"));
        this.j.addTextChangedListener(this.b);
        ((TextView) findViewById(this.i.id("umeng_title"))).setText(b(extras.getString(SocializeConstants.KEY_PLATFORM)));
        findViewById(this.i.id("umeng_back")).setOnClickListener(this);
        findViewById(this.i.id("umeng_share_btn")).setOnClickListener(this);
        this.p.setOnClickListener(this);
        this.k = (TextView) findViewById(this.i.id("umeng_socialize_share_word_num"));
        this.m = e();
    }

    private SHARE_MEDIA a(String str) {
        if (str.equals("TENCENT")) {
            return SHARE_MEDIA.TENCENT;
        }
        if (str.equals("RENREN")) {
            return SHARE_MEDIA.RENREN;
        }
        if (str.equals("DOUBAN")) {
            return SHARE_MEDIA.DOUBAN;
        }
        if (str.equals("TWITTER")) {
            return SHARE_MEDIA.TWITTER;
        }
        if (str.equals("LINKEDIN")) {
            return SHARE_MEDIA.LINKEDIN;
        }
        return SHARE_MEDIA.SINA;
    }

    private String b(String str) {
        if (str.equals("TENCENT")) {
            return getResources().getString(this.i.string("umeng_socialize_sharetotencent"));
        }
        if (str.equals("RENREN")) {
            return getResources().getString(this.i.string("umeng_socialize_sharetorenren"));
        }
        if (str.equals("DOUBAN")) {
            return getResources().getString(this.i.string("umeng_socialize_sharetodouban"));
        }
        if (str.equals("TWITTER")) {
            return getResources().getString(this.i.string("umeng_socialize_sharetotwitter"));
        }
        if (str.equals("LINKEDIN")) {
            return getResources().getString(this.i.string("umeng_socialize_sharetolinkin"));
        }
        return getResources().getString(this.i.string("umeng_socialize_sharetosina"));
    }

    protected void onResume() {
        this.j.requestFocus();
        super.onResume();
    }

    private void b() {
        this.j = (EditText) findViewById(this.i.id("umeng_socialize_share_edittext"));
        if (!TextUtils.isEmpty(this.f)) {
            this.j.setText(this.f);
            this.j.setSelection(this.f.length());
        }
        this.q = (TextView) findViewById(this.i.id("umeng_web_title"));
        this.a = (ImageView) findViewById(this.i.id("umeng_share_icon"));
        if (this.h != null) {
            findViewById(this.i.id("umeng_socialize_share_bottom_area")).setVisibility(0);
            this.a = (ImageView) findViewById(this.i.id("umeng_share_icon"));
            this.a.setVisibility(0);
            if (this.h.equals("video")) {
                this.a.setImageResource(ResContainer.getResourceId(this.l, "drawable", "umeng_socialize_share_video"));
            } else if (this.h.equals("music")) {
                this.a.setImageResource(ResContainer.getResourceId(this.l, "drawable", "umeng_socialize_share_music"));
            } else if (this.h.equals("web")) {
                this.a.setImageResource(ResContainer.getResourceId(this.l, "drawable", "umeng_socialize_share_web"));
            } else {
                this.a.setImageURI(Uri.fromFile(new File(this.h)));
            }
            if (!TextUtils.isEmpty(this.g)) {
                this.q.setVisibility(0);
                this.q.setText(this.g);
            }
            findViewById(this.i.id("root")).setBackgroundColor(-1);
        } else if (!TextUtils.isEmpty(this.g)) {
            this.a.setImageResource(ResContainer.getResourceId(this.l, "drawable", "umeng_socialize_share_web"));
            this.q.setVisibility(0);
            this.q.setText(this.g);
        }
    }

    private void c() {
        String obj = this.j.getText().toString();
        if (TextUtils.isEmpty(obj.trim()) && this.n == SHARE_MEDIA.SINA && (TextUtils.isEmpty(this.h) || this.h.equals("web") || this.h.equals("video") || this.h.equals("music"))) {
            Toast.makeText(this.l, SHARE.CONTEXT_EMPTY, 0).show();
        } else if (SocializeUtils.countContentLength(obj) > d && this.n != SHARE_MEDIA.TWITTER && this.n != SHARE_MEDIA.LINKEDIN) {
        } else {
            if (!this.m || this.n == SHARE_MEDIA.TWITTER) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString(SocializeConstants.KEY_TEXT, obj);
                bundle.putString("pic", this.h);
                intent.putExtras(bundle);
                setResult(-1, intent);
                a();
                return;
            }
            Toast.makeText(this.l, SHARE.CONTEXT_LONG, 0).show();
        }
    }

    public void onCancel(View view) {
        setResult(1000);
        a();
    }

    private void d() {
        this.h = null;
        findViewById(this.i.id("root")).setBackgroundColor(Color.parseColor("#D4E0E5"));
        findViewById(this.i.id("umeng_socialize_share_bottom_area")).setVisibility(8);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == this.i.id("umeng_back")) {
            onCancel(view);
        } else if (id == this.i.id("umeng_share_btn")) {
            c();
        } else if (id == this.i.id("umeng_del")) {
            d();
        }
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return super.onKeyUp(i, keyEvent);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4 && keyEvent.getRepeatCount() == 0) {
            setResult(1000);
        }
        return super.onKeyDown(i, keyEvent);
    }

    protected void a() {
        finish();
    }

    private boolean e() {
        int countContentLength = d - SocializeUtils.countContentLength(this.j.getText().toString());
        this.k.setText(SocializeUtils.countContentLength(this.j.getText().toString()) + "/" + d);
        if (countContentLength >= 0) {
            return false;
        }
        return true;
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (!SocializeConstants.BACKKEY_COMPLETE_CLOSE || keyEvent.getKeyCode() != 4) {
            return super.dispatchKeyEvent(keyEvent);
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                ShareActivity.this.setResult(1000);
                ShareActivity.this.finish();
            }
        }, 400);
        return true;
    }
}
