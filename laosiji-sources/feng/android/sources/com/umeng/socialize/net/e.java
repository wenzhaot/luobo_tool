package com.umeng.socialize.net;

import com.umeng.socialize.net.utils.URequest;
import com.umeng.socialize.sina.helper.MD5;

public abstract class e extends URequest {
    public e(String str) {
        super(str);
    }

    public String a() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    public String a(String str, String str2, String str3, String str4) {
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append(str).append(str3).append(str4).append("edafw2436ef8a3t4").append(str2);
        String toLowerCase = MD5.hexdigest(stringBuilder.toString()).toLowerCase();
        toLowerCase = toLowerCase.substring(toLowerCase.length() - 6);
        String toLowerCase2 = MD5.hexdigest((toLowerCase + toLowerCase.substring(0, 4)).toString()).toLowerCase();
        return toLowerCase + toLowerCase2.substring(toLowerCase2.length() - 1);
    }
}
