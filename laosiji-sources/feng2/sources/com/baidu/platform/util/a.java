package com.baidu.platform.util;

import anet.channel.strategy.dispatch.DispatchConstants;
import com.baidu.platform.comjni.util.AppMD5;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class a implements ParamBuilder<a> {
    protected Map<String, String> a;

    public a a(String str, String str2) {
        if (this.a == null) {
            this.a = new LinkedHashMap();
        }
        this.a.put(str, str2);
        return this;
    }

    public String a() {
        if (this.a == null || this.a.isEmpty()) {
            return null;
        }
        String str = new String();
        Iterator it = this.a.keySet().iterator();
        int i = 0;
        while (true) {
            int i2 = i;
            String str2 = str;
            if (!it.hasNext()) {
                return str2;
            }
            String str3 = (String) it.next();
            str = AppMD5.encodeUrlParamsValue((String) this.a.get(str3));
            str = i2 == 0 ? str2 + str3 + "=" + str : str2 + DispatchConstants.SIGN_SPLIT_SYMBOL + str3 + "=" + str;
            i = i2 + 1;
        }
    }
}
