package com.feng.library.emoticons.keyboard.utils.imageloader;

import android.widget.ImageView;
import com.talkingdata.sdk.aa;
import java.io.IOException;
import java.util.Locale;

public interface ImageBase {

    public enum Scheme {
        HTTP("http"),
        HTTPS("https"),
        FILE("file"),
        CONTENT("content"),
        ASSETS("assets"),
        DRAWABLE("drawable"),
        UNKNOWN("");
        
        private String scheme;
        private String uriPrefix;

        private Scheme(String scheme) {
            this.scheme = scheme;
            this.uriPrefix = scheme + aa.a;
        }

        public static Scheme ofUri(String uri) {
            if (uri != null) {
                for (Scheme s : values()) {
                    if (s.belongsTo(uri)) {
                        return s;
                    }
                }
            }
            return UNKNOWN;
        }

        public String toUri(String path) {
            return this.uriPrefix + path;
        }

        public String crop(String uri) {
            if (belongsTo(uri)) {
                return uri.substring(this.uriPrefix.length());
            }
            throw new IllegalArgumentException(String.format("URI [%1$s] doesn't have expected scheme [%2$s]", new Object[]{uri, this.scheme}));
        }

        protected boolean belongsTo(String uri) {
            return uri.toLowerCase(Locale.US).startsWith(this.uriPrefix);
        }

        public static String cropScheme(String uri) throws IllegalArgumentException {
            return ofUri(uri).crop(uri);
        }
    }

    void displayImage(String str, ImageView imageView) throws IOException;
}
