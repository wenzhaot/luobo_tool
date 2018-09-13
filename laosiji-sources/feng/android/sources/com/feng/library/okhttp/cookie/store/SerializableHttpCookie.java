package com.feng.library.okhttp.cookie.store;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import okhttp3.Cookie;
import okhttp3.Cookie.Builder;

public class SerializableHttpCookie implements Serializable {
    private static final long serialVersionUID = 6374381323722046732L;
    private transient Cookie clientCookie;
    private final transient Cookie cookie;

    public SerializableHttpCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public Cookie getCookie() {
        Cookie bestCookie = this.cookie;
        if (this.clientCookie != null) {
            return this.clientCookie;
        }
        return bestCookie;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(this.cookie.name());
        out.writeObject(this.cookie.value());
        out.writeLong(this.cookie.expiresAt());
        out.writeObject(this.cookie.domain());
        out.writeObject(this.cookie.path());
        out.writeBoolean(this.cookie.secure());
        out.writeBoolean(this.cookie.httpOnly());
        out.writeBoolean(this.cookie.hostOnly());
        out.writeBoolean(this.cookie.persistent());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        String name = (String) in.readObject();
        String value = (String) in.readObject();
        long expiresAt = in.readLong();
        String domain = (String) in.readObject();
        String path = (String) in.readObject();
        boolean secure = in.readBoolean();
        boolean httpOnly = in.readBoolean();
        boolean hostOnly = in.readBoolean();
        boolean persistent = in.readBoolean();
        Builder builder = new Builder().name(name).value(value).expiresAt(expiresAt);
        builder = (hostOnly ? builder.hostOnlyDomain(domain) : builder.domain(domain)).path(path);
        if (secure) {
            builder = builder.secure();
        }
        if (httpOnly) {
            builder = builder.httpOnly();
        }
        this.clientCookie = builder.build();
    }
}
