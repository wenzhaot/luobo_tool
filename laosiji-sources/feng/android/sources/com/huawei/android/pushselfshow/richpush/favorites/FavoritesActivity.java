package com.huawei.android.pushselfshow.richpush.favorites;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huawei.android.pushselfshow.richpush.html.HtmlViewer;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FavoritesActivity implements com.huawei.android.pushselfshow.utils.c.a {
    com.huawei.android.pushselfshow.utils.c a = new com.huawei.android.pushselfshow.utils.c(this);
    private Activity b;
    private ImageView c;
    private TextView d;
    private TextView e;
    private ListView f;
    private LinearLayout g;
    private a h;
    private MenuItem i;
    private MenuItem j;
    private boolean k = false;
    private byte[] l = null;
    private byte[] m = null;
    private AlertDialog n = null;

    private class a implements OnClickListener {
        private Context b;

        private a(Context context) {
            this.b = context;
        }

        /* synthetic */ a(FavoritesActivity favoritesActivity, Context context, b bVar) {
            this(context);
        }

        public void onClick(View view) {
            if (FavoritesActivity.this.k) {
                FavoritesActivity.this.e();
                return;
            }
            ActionBar actionBar = FavoritesActivity.this.b.getActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayShowCustomEnabled(false);
                actionBar.setTitle(this.b.getString(com.huawei.android.pushselfshow.utils.d.a(this.b, "hwpush_msg_collect")));
            }
            FavoritesActivity.this.c.setVisibility(4);
            FavoritesActivity.this.e.setVisibility(8);
            FavoritesActivity.this.e.setText("");
            FavoritesActivity.this.d.setText(this.b.getString(com.huawei.android.pushselfshow.utils.d.a(this.b, "hwpush_msg_collect")));
            FavoritesActivity.this.a(false);
            FavoritesActivity.this.h.a(true);
            FavoritesActivity.this.f.setOnItemClickListener(new d(FavoritesActivity.this, null));
            FavoritesActivity.this.f.setLongClickable(true);
        }
    }

    private class b implements OnItemClickListener {
        private Context b;

        private b(Context context) {
            this.b = context;
        }

        /* synthetic */ b(FavoritesActivity favoritesActivity, Context context, b bVar) {
            this(context);
        }

        public void onItemClick(AdapterView adapterView, View view, int i, long j) {
            CheckBox checkBox = (CheckBox) view.findViewById(com.huawei.android.pushselfshow.utils.d.e(this.b, "hwpush_delCheck"));
            e a = FavoritesActivity.this.h.getItem(i);
            if (checkBox.isChecked()) {
                checkBox.setChecked(false);
                a.a(false);
            } else {
                checkBox.setChecked(true);
                a.a(true);
            }
            FavoritesActivity.this.h.a(i, a);
            List<e> a2 = FavoritesActivity.this.h.a();
            int i2 = 0;
            for (e a3 : a2) {
                i2 = a3.a() ? i2 + 1 : i2;
            }
            if (i2 > 0) {
                FavoritesActivity.this.e.setVisibility(0);
                FavoritesActivity.this.e.setText(String.valueOf(i2));
                FavoritesActivity.this.i.setEnabled(true);
                if (i2 == a2.size()) {
                    FavoritesActivity.this.a(this.b, true);
                    return;
                } else {
                    FavoritesActivity.this.a(this.b, false);
                    return;
                }
            }
            FavoritesActivity.this.e.setVisibility(8);
            FavoritesActivity.this.e.setText("");
            FavoritesActivity.this.i.setEnabled(false);
            FavoritesActivity.this.a(this.b, false);
        }
    }

    private class c implements OnItemLongClickListener {
        private c() {
        }

        /* synthetic */ c(FavoritesActivity favoritesActivity, b bVar) {
            this();
        }

        public boolean onItemLongClick(AdapterView adapterView, View view, int i, long j) {
            FavoritesActivity.this.d();
            FavoritesActivity.this.i.setEnabled(true);
            Set hashSet = new HashSet();
            hashSet.add(Integer.valueOf(i));
            FavoritesActivity.this.h.a(false, hashSet);
            FavoritesActivity.this.e.setVisibility(0);
            FavoritesActivity.this.e.setText(PushConstants.PUSH_TYPE_THROUGH_MESSAGE);
            return true;
        }
    }

    private class d implements OnItemClickListener {
        private d() {
        }

        /* synthetic */ d(FavoritesActivity favoritesActivity, b bVar) {
            this();
        }

        public void onItemClick(AdapterView adapterView, View view, int i, long j) {
            e a = FavoritesActivity.this.h.getItem(i);
            Intent intent = new Intent("com.huawei.android.push.intent.RICHPUSH");
            intent.putExtra("type", a.b().E);
            intent.putExtra("selfshow_info", a.b().c());
            intent.putExtra("selfshow_token", a.b().d());
            intent.putExtra("selfshow_from_list", true);
            intent.setFlags(268468240);
            intent.setPackage(FavoritesActivity.this.b.getPackageName());
            FavoritesActivity.this.b.finish();
            FavoritesActivity.this.b.startActivity(intent);
        }
    }

    private View a() {
        View inflate = this.b.getLayoutInflater().inflate(com.huawei.android.pushselfshow.utils.d.c(this.b, "hwpush_collection_listview"), null);
        this.f = (ListView) inflate.findViewById(com.huawei.android.pushselfshow.utils.d.e(this.b, "hwpush_collection_list"));
        this.h = new a(this.b);
        this.f.setAdapter(this.h);
        this.f.setLongClickable(true);
        this.f.setOnItemLongClickListener(new c(this, null));
        this.f.setOnItemClickListener(new d(this, null));
        return inflate;
    }

    private void a(Context context, boolean z) {
        if (z) {
            this.j.setTitle(com.huawei.android.pushselfshow.utils.d.a(context, "hwpush_unselectall"));
            Drawable drawable = context.getResources().getDrawable(com.huawei.android.pushselfshow.utils.d.g(context, "hwpush_ic_toolbar_multiple1"));
            try {
                int identifier = context.getResources().getIdentifier("colorful_emui", "color", "androidhwext");
                if (identifier != 0) {
                    identifier = context.getResources().getColor(identifier);
                    if (identifier != 0) {
                        drawable.setTint(identifier);
                    }
                }
            } catch (NotFoundException e) {
                com.huawei.android.pushagent.a.a.c.d("PushSelfShowLog", e.toString());
            } catch (Exception e2) {
                com.huawei.android.pushagent.a.a.c.d("PushSelfShowLog", e2.toString());
            }
            this.j.setIcon(drawable);
            return;
        }
        this.j.setIcon(context.getResources().getDrawable(com.huawei.android.pushselfshow.utils.d.g(context, "hwpush_ic_toolbar_multiple")));
        this.j.setTitle(com.huawei.android.pushselfshow.utils.d.a(context, "hwpush_selectall"));
    }

    private void a(View view) {
        this.c = (ImageView) view.findViewById(com.huawei.android.pushselfshow.utils.d.e(this.b, "hwpush_bt_delete"));
        this.d = (TextView) view.findViewById(com.huawei.android.pushselfshow.utils.d.e(this.b, "hwpush_txt_delitem"));
        this.e = (TextView) view.findViewById(com.huawei.android.pushselfshow.utils.d.e(this.b, "hwpush_txt_delnum"));
        com.huawei.android.pushselfshow.utils.a.a(this.b, this.d);
        com.huawei.android.pushselfshow.utils.a.a(this.b, this.e);
        if (com.huawei.android.pushselfshow.utils.a.d()) {
            int j = com.huawei.android.pushselfshow.utils.a.j(this.b);
            if (-1 != j) {
                if (j == 0) {
                    j = this.b.getResources().getColor(com.huawei.android.pushselfshow.utils.d.f(this.b, "hwpush_black"));
                    this.c.setImageDrawable(this.b.getResources().getDrawable(com.huawei.android.pushselfshow.utils.d.g(this.b, "hwpush_ic_cancel_light")));
                    this.e.setBackground(this.b.getResources().getDrawable(com.huawei.android.pushselfshow.utils.d.g(this.b, "hwpush_pic_ab_number_light")));
                } else {
                    j = this.b.getResources().getColor(com.huawei.android.pushselfshow.utils.d.f(this.b, "hwpush_white"));
                    this.c.setImageDrawable(this.b.getResources().getDrawable(com.huawei.android.pushselfshow.utils.d.g(this.b, "hwpush_ic_cancel")));
                    this.e.setBackground(this.b.getResources().getDrawable(com.huawei.android.pushselfshow.utils.d.g(this.b, "hwpush_pic_ab_number")));
                    this.e.setTextColor(j);
                }
                this.d.setTextColor(j);
            }
        }
        this.c.setOnClickListener(new a(this, this.b, null));
    }

    private void a(boolean z) {
        this.i.setVisible(z);
        this.j.setVisible(z);
    }

    private void b() {
        if (this.h != null && this.f != null && this.g != null) {
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "count:" + this.h.getCount());
            if (this.h.getCount() == 0) {
                this.f.setVisibility(8);
                this.g.setVisibility(0);
                return;
            }
            this.f.setVisibility(0);
            this.g.setVisibility(8);
        }
    }

    private int c() {
        int i = 0;
        if (this.h == null) {
            return 0;
        }
        Iterator it = this.h.a().iterator();
        while (true) {
            int i2 = i;
            if (it.hasNext()) {
                e eVar = (e) it.next();
                if (eVar != null && eVar.a()) {
                    i2++;
                }
                i = i2;
            } else {
                com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "selectItemsNum:" + i2);
                return i2;
            }
        }
    }

    private void d() {
        ActionBar actionBar = this.b.getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            View inflate = this.b.getLayoutInflater().inflate(com.huawei.android.pushselfshow.utils.d.c(this.b, "hwpush_custom_titlebar"), null);
            a(inflate);
            actionBar.setCustomView(inflate);
        }
        a(true);
        this.c.setVisibility(0);
        this.d.setText(com.huawei.android.pushselfshow.utils.d.a(this.b, "hwpush_deltitle"));
        this.f.setOnItemClickListener(new b(this, this.b, null));
        this.h.a(false);
        this.f.setLongClickable(false);
        if (1 == this.h.a().size()) {
            a(this.b, true);
        } else {
            a(this.b, false);
        }
    }

    private void e() {
        Intent intent = new Intent("com.huawei.android.push.intent.RICHPUSH");
        intent.putExtra("type", "html");
        intent.putExtra("selfshow_info", this.l);
        intent.putExtra("selfshow_token", this.m);
        intent.setFlags(268468240);
        intent.setPackage(this.b.getPackageName());
        this.b.finish();
        this.b.startActivity(intent);
    }

    public void handleMessage(Message message) {
        try {
            switch (message.what) {
                case 1000:
                    com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "mHandler MSG_LOAD_DONE");
                    this.f.setAdapter(this.h);
                    b();
                    if (this.k) {
                        d();
                        return;
                    }
                    return;
                case 1001:
                    com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "mHandler MSG_DELETE_DONE");
                    if (this.k) {
                        e();
                        return;
                    }
                    this.f.setAdapter(this.h);
                    this.c.performClick();
                    b();
                    return;
                default:
                    return;
            }
        } catch (Throwable e) {
            com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", "handleMessage error:" + message.what + MiPushClient.ACCEPT_TIME_SEPARATOR + e.toString(), e);
        }
        com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", "handleMessage error:" + message.what + MiPushClient.ACCEPT_TIME_SEPARATOR + e.toString(), e);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "FavoritesActivity onActivityResult");
    }

    public void onCreate(Intent intent) {
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "FavoritesActivity onCreate");
        try {
            ActionBar actionBar = this.b.getActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setTitle(this.b.getString(com.huawei.android.pushselfshow.utils.d.a(this.b, "hwpush_msg_favorites")));
            }
            this.k = intent.getBooleanExtra("selfshowMsgOutOfBound", false);
            this.l = intent.getByteArrayExtra("selfshow_info");
            this.m = intent.getByteArrayExtra("selfshow_token");
            View relativeLayout = new RelativeLayout(this.b);
            View a = a();
            this.g = (LinearLayout) a.findViewById(com.huawei.android.pushselfshow.utils.d.e(this.b, "hwpush_no_collection_view"));
            com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "mNoCollectionLayout:" + this.g);
            relativeLayout.addView(a);
            new Thread(new b(this)).start();
            this.b.setContentView(relativeLayout);
            if (this.k && this.i != null) {
                this.i.setEnabled(false);
            }
        } catch (Throwable e) {
            com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", "call" + HtmlViewer.class.getName() + " onCreate(Intent intent) err: " + e.toString(), e);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.b.getMenuInflater().inflate(com.huawei.android.pushselfshow.utils.d.d(this.b, "hwpush_collection_menu"), menu);
        return true;
    }

    public void onDestroy() {
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "FavoritesActivity onDestroy");
        if (this.n != null && this.n.isShowing()) {
            this.n.dismiss();
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        boolean z = false;
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "FavoritesActivity onKeyDown");
        if (i == 4 && keyEvent.getAction() == 0) {
            if (this.c != null && this.c.getVisibility() == 0) {
                z = true;
            }
            if (this.k) {
                e();
            } else if (z) {
                this.c.performClick();
            } else {
                this.b.finish();
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        com.huawei.android.pushagent.a.a.c.b("PushSelfShowLog", "FavoritesActivity onOptionsItemSelected:" + menuItem);
        if (menuItem == null) {
            com.huawei.android.pushagent.a.a.c.d("PushSelfShowLog", "onOptionsItemSelected, item is null");
            return false;
        }
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onKeyDown(4, new KeyEvent(0, 4));
        } else if (itemId == com.huawei.android.pushselfshow.utils.d.e(this.b, "hwpush_menu_delete")) {
            CharSequence quantityString;
            String str = "";
            try {
                quantityString = this.b.getResources().getQuantityString(com.huawei.android.pushselfshow.utils.d.b(this.b, "hwpush_delete_tip"), c());
            } catch (Throwable e) {
                Throwable th = e;
                quantityString = "";
                com.huawei.android.pushagent.a.a.c.c("PushSelfShowLog", th.toString(), th);
            }
            this.n = new Builder(this.b, com.huawei.android.pushselfshow.utils.a.h(this.b)).setTitle(quantityString).setPositiveButton(com.huawei.android.pushselfshow.utils.d.a(this.b, "hwpush_delete"), new c(this)).setNegativeButton(com.huawei.android.pushselfshow.utils.d.a(this.b, "hwpush_cancel"), null).create();
            this.n.show();
            this.n.getButton(-1).setTextColor(Color.parseColor("#ffd43e25"));
        } else if (itemId == com.huawei.android.pushselfshow.utils.d.e(this.b, "hwpush_menu_selectall")) {
            boolean z;
            for (e a : this.h.a()) {
                if (!a.a()) {
                    z = true;
                    break;
                }
            }
            z = false;
            this.h.a(z, null);
            if (z) {
                this.e.setVisibility(0);
                this.e.setText(String.valueOf(this.h.getCount()));
                this.i.setEnabled(true);
                a(this.b, true);
            } else {
                this.e.setVisibility(8);
                this.e.setText("");
                this.i.setEnabled(false);
                a(this.b, false);
            }
        }
        return true;
    }

    public void onPause() {
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "FavoritesActivity onPause");
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        com.huawei.android.pushagent.a.a.c.b("PushSelfShowLog", "FavoritesActivity onPrepareOptionsMenu:" + menu);
        this.i = menu.findItem(com.huawei.android.pushselfshow.utils.d.e(this.b, "hwpush_menu_delete"));
        this.j = menu.findItem(com.huawei.android.pushselfshow.utils.d.e(this.b, "hwpush_menu_selectall"));
        a(false);
        return true;
    }

    public void onRestart() {
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "FavoritesActivity onRestart");
    }

    public void onResume() {
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "FavoritesActivity onResume");
    }

    public void onStart() {
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "FavoritesActivity onStart");
    }

    public void onStop() {
        com.huawei.android.pushagent.a.a.c.a("PushSelfShowLog", "FavoritesActivity onStop");
    }

    public void setActivity(Activity activity) {
        this.b = activity;
    }
}
