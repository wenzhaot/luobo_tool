package anet.channel.monitor;

/* compiled from: Taobao */
public class f {
    boolean a = false;
    protected long b;
    private final double c = 40.0d;
    private boolean d = true;

    public int a() {
        return 0;
    }

    public boolean a(double d) {
        if (d < 40.0d) {
            return true;
        }
        return false;
    }

    protected final boolean b() {
        if (!this.d) {
            return false;
        }
        if (System.currentTimeMillis() - this.b <= ((long) (a() * 1000))) {
            return true;
        }
        this.d = false;
        return false;
    }
}
