package anet.channel.security;

/* compiled from: Taobao */
public class c {
    private static volatile ISecurityFactory a = null;

    public static ISecurityFactory a() {
        if (a == null) {
            a = new d();
        }
        return a;
    }
}
