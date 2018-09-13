package com.talkingdata.sdk;

import android.content.Context;

/* compiled from: td */
final class av implements Runnable {
    final /* synthetic */ Context val$context;

    av(Context context) {
        this.val$context = context;
    }

    public void run() {
        try {
            au.j = true;
            au.i = (String) Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient$Info").getMethod("getId", new Class[0]).invoke(Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient").getMethod("getAdvertisingIdInfo", new Class[]{Context.class}).invoke(null, new Object[]{this.val$context}), new Object[0]);
        } catch (Throwable th) {
        }
    }
}
