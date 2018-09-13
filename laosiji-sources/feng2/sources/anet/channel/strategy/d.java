package anet.channel.strategy;

import java.io.File;

/* compiled from: Taobao */
class d implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ StrategyInfoHolder b;

    d(StrategyInfoHolder strategyInfoHolder, String str) {
        this.b = strategyInfoHolder;
        this.a = str;
    }

    public void run() {
        int i = 0;
        try {
            File[] b = l.b();
            if (b != null) {
                for (int i2 = 0; i2 < b.length && i < 2; i2++) {
                    String name = b[i2].getName();
                    if (!(name.equals(this.a) || name.equals(StrategyInfoHolder.a) || name.equals(StrategyInfoHolder.b))) {
                        this.b.a(name, null);
                        i++;
                    }
                }
            }
        } catch (Exception e) {
        }
    }
}
