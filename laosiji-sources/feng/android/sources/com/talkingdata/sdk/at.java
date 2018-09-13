package com.talkingdata.sdk;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/* compiled from: td */
final class at implements HostnameVerifier {
    at() {
    }

    public boolean verify(String str, SSLSession sSLSession) {
        return true;
    }
}
