package com.feng.library.okhttp.cookie.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class MemoryCookieStore implements CookieStore {
    private final HashMap<String, List<Cookie>> allCookies = new HashMap();
    private Object lock = new Object();

    public void add(HttpUrl url, List<Cookie> cookies) {
        try {
            synchronized (this.lock) {
                List<Cookie> oldCookies = (List) this.allCookies.get(url.host());
                Iterator<Cookie> oldIterator = oldCookies.iterator();
                for (Cookie newCookie : cookies) {
                    while (oldIterator.hasNext()) {
                        if (newCookie.name().equals(((Cookie) oldIterator.next()).name())) {
                            oldIterator.remove();
                        }
                    }
                }
                oldCookies.addAll(cookies);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Cookie> get(HttpUrl uri) {
        List<Cookie> cookies = (List) this.allCookies.get(uri.host());
        if (cookies != null) {
            return cookies;
        }
        cookies = new ArrayList();
        this.allCookies.put(uri.host(), cookies);
        return cookies;
    }

    public boolean removeAll() {
        this.allCookies.clear();
        return true;
    }

    public List<Cookie> getCookies() {
        List<Cookie> cookies = new ArrayList();
        for (String url : this.allCookies.keySet()) {
            cookies.addAll((Collection) this.allCookies.get(url));
        }
        return cookies;
    }

    public boolean remove(HttpUrl uri, Cookie cookie) {
        try {
            List<Cookie> cookies = (List) this.allCookies.get(uri);
            if (cookie == null) {
                return false;
            }
            Iterator<Cookie> iterator = cookies.iterator();
            while (iterator.hasNext()) {
                if (((Cookie) iterator.next()).name().equals(cookie.name())) {
                    iterator.remove();
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
