package anetwork.channel.util;

import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: Taobao */
public class SeqGen {
    private static final int mask = Integer.MAX_VALUE;
    private static AtomicInteger seq = new AtomicInteger(0);

    public static String createSeqNo(String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder(16);
        if (str != null) {
            stringBuilder.append(str).append('.');
        }
        if (str2 != null) {
            stringBuilder.append(str2).append(seq.incrementAndGet() & Integer.MAX_VALUE);
        }
        return stringBuilder.toString();
    }
}
