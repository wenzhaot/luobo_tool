package anetwork.channel.cache;

@Deprecated
/* compiled from: Taobao */
public interface ImageCache {
    byte[] get(String str);

    void put(String str, byte[] bArr);
}
