package com.talkingdata.sdk;

import com.talkingdata.sdk.zz.a;
import com.tencent.tauth.AuthActivity;
import java.util.Map;

/* compiled from: td */
public class cn {
    private static volatile cn a = null;

    public final void onTDEBEventIAP(a aVar) {
        if (aVar != null) {
            try {
                if (aVar.paraMap != null && Integer.parseInt(String.valueOf(aVar.paraMap.get("apiType"))) == 8) {
                    dd ddVar = new dd();
                    Object obj = aVar.paraMap.get("data");
                    a aVar2 = (a) aVar.paraMap.get("service");
                    ddVar.b = String.valueOf(aVar.paraMap.get("domain"));
                    ddVar.c = String.valueOf(aVar.paraMap.get(AuthActivity.ACTION_KEY));
                    if (obj != null && (obj instanceof Map)) {
                        ddVar.d = (Map) obj;
                    }
                    ddVar.a = aVar2;
                    br.a().post(ddVar);
                    if (aVar.paraMap.get(AuthActivity.ACTION_KEY) == null) {
                        return;
                    }
                    if (aVar.paraMap.get(AuthActivity.ACTION_KEY).equals("addItem") || aVar.paraMap.get(AuthActivity.ACTION_KEY).equals("recharge") || aVar.paraMap.get(AuthActivity.ACTION_KEY).equals("currencyPurchase") || aVar.paraMap.get(AuthActivity.ACTION_KEY).equals("placeOrder") || aVar.paraMap.get(AuthActivity.ACTION_KEY).equals("deeplink") || aVar.paraMap.get(AuthActivity.ACTION_KEY).equals("viewItem") || aVar.paraMap.get(AuthActivity.ACTION_KEY).equals("viewItems") || aVar.paraMap.get(AuthActivity.ACTION_KEY).equals("reward") || aVar.paraMap.get(AuthActivity.ACTION_KEY).equals("onRechargeSucc") || aVar.paraMap.get(AuthActivity.ACTION_KEY).equals("virtualCurrencyPurchase") || aVar.paraMap.get(AuthActivity.ACTION_KEY).equals("itemUsedFor") || aVar.paraMap.get(AuthActivity.ACTION_KEY).equals("pay") || aVar.paraMap.get(AuthActivity.ACTION_KEY).equals("apply") || aVar.paraMap.get(AuthActivity.ACTION_KEY).equals("activate")) {
                        dc dcVar = new dc();
                        dcVar.a = aVar2;
                        dcVar.b = dc.a.IMMEDIATELY;
                        br.a().post(dcVar);
                    }
                }
            } catch (Throwable th) {
            }
        }
    }

    static {
        try {
            br.a().register(a());
        } catch (Throwable th) {
        }
    }

    public static cn a() {
        if (a == null) {
            synchronized (cn.class) {
                if (a == null) {
                    a = new cn();
                }
            }
        }
        return a;
    }

    private cn() {
    }
}
