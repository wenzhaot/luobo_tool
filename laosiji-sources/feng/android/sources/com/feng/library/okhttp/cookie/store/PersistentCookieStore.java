package com.feng.library.okhttp.cookie.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class PersistentCookieStore implements CookieStore {
    private static final String COOKIE_NAME_PREFIX = "cookie_";
    private static final String COOKIE_PREFS = "CookiePrefsFile";
    private static final String LOG_TAG = "PersistentCookieStore";
    private static PersistentCookieStore instance;
    private SharedPreferences cookiePrefs;
    private HashMap<String, ConcurrentHashMap<String, Cookie>> cookies;

    public static PersistentCookieStore getInstance(Context context) {
        if (instance == null) {
            instance = new PersistentCookieStore(context);
        }
        return instance;
    }

    private PersistentCookieStore(Context context) {
        this.cookiePrefs = context.getSharedPreferences(COOKIE_PREFS, 0);
        this.cookies = new HashMap();
        for (Entry<String, ?> entry : this.cookiePrefs.getAll().entrySet()) {
            if (!(((String) entry.getValue()) == null || ((String) entry.getValue()).startsWith(COOKIE_NAME_PREFIX))) {
                for (String name : TextUtils.split((String) entry.getValue(), MiPushClient.ACCEPT_TIME_SEPARATOR)) {
                    String encodedCookie = this.cookiePrefs.getString(COOKIE_NAME_PREFIX + name, null);
                    if (encodedCookie != null) {
                        Cookie decodedCookie = decodeCookie(encodedCookie);
                        if (decodedCookie != null) {
                            if (!this.cookies.containsKey(entry.getKey())) {
                                this.cookies.put(entry.getKey(), new ConcurrentHashMap());
                            }
                            ((ConcurrentHashMap) this.cookies.get(entry.getKey())).put(name, decodedCookie);
                        }
                    }
                }
            }
        }
    }

    private PersistentCookieStore() {
    }

    protected void add(HttpUrl uri, Cookie cookie) {
        try {
            String name = getCookieToken(cookie);
            if (cookie.persistent()) {
                if (!this.cookies.containsKey(uri.host())) {
                    this.cookies.put(uri.host(), new ConcurrentHashMap());
                }
                ((ConcurrentHashMap) this.cookies.get(uri.host())).put(name, cookie);
            } else if (this.cookies.containsKey(uri.host())) {
                ((ConcurrentHashMap) this.cookies.get(uri.host())).remove(name);
            } else {
                return;
            }
            Editor prefsWriter = this.cookiePrefs.edit();
            prefsWriter.putString(uri.host(), TextUtils.join(MiPushClient.ACCEPT_TIME_SEPARATOR, ((ConcurrentHashMap) this.cookies.get(uri.host())).keySet()));
            prefsWriter.putString(COOKIE_NAME_PREFIX + name, encodeCookie(new SerializableHttpCookie(cookie)));
            prefsWriter.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String getCookieToken(Cookie cookie) {
        return cookie.name() + cookie.domain();
    }

    public void add(HttpUrl uri, List<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            add(uri, cookie);
        }
    }

    public List<Cookie> get(HttpUrl uri) {
        ArrayList<Cookie> ret = new ArrayList();
        if (this.cookies.containsKey(uri.host())) {
            for (Cookie cookie : ((ConcurrentHashMap) this.cookies.get(uri.host())).values()) {
                if (isCookieExpired(cookie)) {
                    remove(uri, cookie);
                } else {
                    ret.add(cookie);
                }
            }
        }
        return ret;
    }

    private static boolean isCookieExpired(Cookie cookie) {
        return cookie.expiresAt() < System.currentTimeMillis();
    }

    public boolean removeAll() {
        Editor prefsWriter = this.cookiePrefs.edit();
        prefsWriter.clear();
        prefsWriter.commit();
        this.cookies.clear();
        return true;
    }

    public boolean remove(HttpUrl uri, Cookie cookie) {
        String name = getCookieToken(cookie);
        if (!this.cookies.containsKey(uri.host()) || !((ConcurrentHashMap) this.cookies.get(uri.host())).containsKey(name)) {
            return false;
        }
        ((ConcurrentHashMap) this.cookies.get(uri.host())).remove(name);
        Editor prefsWriter = this.cookiePrefs.edit();
        if (this.cookiePrefs.contains(COOKIE_NAME_PREFIX + name)) {
            prefsWriter.remove(COOKIE_NAME_PREFIX + name);
        }
        prefsWriter.putString(uri.host(), TextUtils.join(MiPushClient.ACCEPT_TIME_SEPARATOR, ((ConcurrentHashMap) this.cookies.get(uri.host())).keySet()));
        prefsWriter.commit();
        return true;
    }

    public List<Cookie> getCookies() {
        ArrayList<Cookie> ret = new ArrayList();
        for (String key : this.cookies.keySet()) {
            ret.addAll(((ConcurrentHashMap) this.cookies.get(key)).values());
        }
        return ret;
    }

    protected String encodeCookie(SerializableHttpCookie cookie) {
        if (cookie == null) {
            return null;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            new ObjectOutputStream(os).writeObject(cookie);
            return byteArrayToHexString(os.toByteArray());
        } catch (IOException e) {
            Log.d(LOG_TAG, "IOException in encodeCookie", e);
            return null;
        }
    }

    protected Cookie decodeCookie(String cookieString) {
        Cookie cookie = null;
        try {
            return ((SerializableHttpCookie) new ObjectInputStream(new ByteArrayInputStream(hexStringToByteArray(cookieString))).readObject()).getCookie();
        } catch (IOException e) {
            Log.d(LOG_TAG, "IOException in decodeCookie", e);
            return cookie;
        } catch (ClassNotFoundException e2) {
            Log.d(LOG_TAG, "ClassNotFoundException in decodeCookie", e2);
            return cookie;
        }
    }

    protected String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 255;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    protected byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[(len / 2)];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
}
