package com.feng.library.okhttp.cookie;

import com.feng.library.okhttp.cookie.store.CookieStore;
import com.feng.library.okhttp.cookie.store.HasCookieStore;
import com.feng.library.okhttp.utils.Exceptions;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookieJarImpl implements CookieJar, HasCookieStore {
    private CookieStore cookieStore;

    public CookieJarImpl(CookieStore cookieStore) {
        if (cookieStore == null) {
            Exceptions.illegalArgument("cookieStore can not be null.", new Object[0]);
        }
        this.cookieStore = cookieStore;
    }

    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        this.cookieStore.add(url, cookies);
    }

    public List<Cookie> loadForRequest(HttpUrl url) {
        return this.cookieStore.get(url);
    }

    public CookieStore getCookieStore() {
        return this.cookieStore;
    }
}
