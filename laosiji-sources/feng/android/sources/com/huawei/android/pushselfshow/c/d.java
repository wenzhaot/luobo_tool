package com.huawei.android.pushselfshow.c;

import android.content.Context;
import android.content.Intent;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.b.a;
import com.huawei.android.pushselfshow.richpush.tools.b;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import org.android.agoo.common.AgooConstants;

public class d extends Thread {
    private Context a;
    private a b;

    public d(Context context, a aVar) {
        this.a = context;
        this.b = aVar;
    }

    private static Intent b(Context context, a aVar) {
        if (aVar == null) {
            return null;
        }
        Intent a = com.huawei.android.pushselfshow.utils.a.a(context, aVar.A);
        Intent parseUri;
        if (aVar.g != null) {
            try {
                parseUri = Intent.parseUri(aVar.g, 0);
                c.a("PushSelfShowLog", "Intent.parseUri(msg.intentUri, 0)，" + parseUri.toURI());
                if (!com.huawei.android.pushselfshow.utils.a.a(context, aVar.A, parseUri).booleanValue()) {
                    parseUri = a;
                }
                return parseUri;
            } catch (Throwable e) {
                c.a("PushSelfShowLog", "intentUri error ", e);
                return a;
            }
        }
        if (aVar.B != null) {
            parseUri = new Intent(aVar.B);
            if (com.huawei.android.pushselfshow.utils.a.a(context, aVar.A, parseUri).booleanValue()) {
                a = parseUri;
            }
        }
        a.setPackage(aVar.A);
        return a;
    }

    public boolean a(Context context) {
        return "cosa".equals(this.b.p) ? b(context) : "email".equals(this.b.p) ? c(context) : "rp".equals(this.b.p) ? d(context) : true;
    }

    public boolean a(Context context, a aVar) {
        if (!PushConstants.EXTRA_APPLICATION_PENDING_INTENT.equals(aVar.p) && !"cosa".equals(aVar.p)) {
            return false;
        }
        Intent b = b(context, aVar);
        if (b == null) {
            c.a("PushSelfShowLog", "launchCosaApp,intent == null");
            return true;
        } else if (com.huawei.android.pushselfshow.utils.a.a(context, b)) {
            return false;
        } else {
            c.b("PushSelfShowLog", "no permission to start activity");
            return true;
        }
    }

    public boolean b(Context context) {
        if (com.huawei.android.pushselfshow.utils.a.b(context, this.b.A)) {
            return true;
        }
        com.huawei.android.pushselfshow.utils.a.a(context, "4", this.b);
        return false;
    }

    public boolean c(Context context) {
        if (com.huawei.android.pushselfshow.utils.a.d(context)) {
            return true;
        }
        com.huawei.android.pushselfshow.utils.a.a(context, AgooConstants.ACK_PACK_ERROR, this.b);
        return false;
    }

    public boolean d(Context context) {
        if (this.b.D == null || this.b.D.length() == 0) {
            com.huawei.android.pushselfshow.utils.a.a(context, "6", this.b);
            c.a("PushSelfShowLog", "ilegle richpush param ,rpl is null");
            return false;
        }
        c.a("PushSelfShowLog", "enter checkRichPush, rpl is " + this.b.D + ",psMsg.rpct:" + this.b.F);
        if ("application/zip".equals(this.b.F) || this.b.D.endsWith(".zip")) {
            this.b.F = "application/zip";
            if (this.b.j == 1) {
                String a = new com.huawei.android.pushselfshow.richpush.tools.d().a(context, this.b.D, this.b.k, b.a("application/zip"));
                if (a != null && a.length() > 0) {
                    this.b.D = a;
                    this.b.F = "application/zip_local";
                }
                c.a("PushSelfShowLog", "Download first ,the localfile" + a);
            }
            return true;
        } else if ("text/html".equals(this.b.F) || this.b.D.endsWith(".html")) {
            this.b.F = "text/html";
            return true;
        } else {
            c.a("PushSelfShowLog", "unknow rpl type");
            com.huawei.android.pushselfshow.utils.a.a(context, "6", this.b);
            return false;
        }
    }

    public void run() {
        c.a("PushSelfShowLog", "enter run()");
        try {
            if (a(this.a)) {
                if (a(this.a, this.b)) {
                    com.huawei.android.pushselfshow.utils.a.a(this.a, "17", this.b);
                    return;
                }
                b.a(this.a, this.b);
            }
        } catch (Exception e) {
        }
        super.run();
    }
}
