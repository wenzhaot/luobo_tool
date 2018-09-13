package anetwork.channel.cache;

import anet.channel.util.HttpConstant;
import anet.channel.util.a;
import anetwork.channel.cache.Cache.Entry;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/* compiled from: Taobao */
public class CacheHelper {
    private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
    private static final DateFormat STANDARD_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);

    static {
        STANDARD_FORMAT.setTimeZone(GMT);
    }

    public static String toGMTDate(long j) {
        return STANDARD_FORMAT.format(new Date(j));
    }

    private static long parseGMTDate(String str) {
        if (str.length() == 0) {
            return 0;
        }
        try {
            ParsePosition parsePosition = new ParsePosition(0);
            Date parse = STANDARD_FORMAT.parse(str, parsePosition);
            if (parsePosition.getIndex() == str.length()) {
                return parse.getTime();
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static Entry parseCacheHeaders(Map<String, List<String>> map) {
        long j;
        long parseGMTDate;
        long parseGMTDate2;
        long parseGMTDate3;
        int i = 0;
        long j2 = 0;
        long currentTimeMillis = System.currentTimeMillis();
        String b = a.b(map, HttpConstant.CACHE_CONTROL);
        if (b != null) {
            String[] split = b.split(",");
            j = 0;
            while (i < split.length) {
                String trim = split[i].trim();
                if (trim.equals("no-cache") || trim.equals("no-store")) {
                    return null;
                }
                if (trim.startsWith("max-age=")) {
                    try {
                        j = Long.parseLong(trim.substring(8));
                    } catch (Exception e) {
                    }
                }
                i++;
            }
            i = 1;
        } else {
            j = 0;
        }
        String b2 = a.b(map, "Date");
        if (b2 != null) {
            parseGMTDate = parseGMTDate(b2);
        } else {
            parseGMTDate = 0;
        }
        b2 = a.b(map, "Expires");
        if (b2 != null) {
            parseGMTDate2 = parseGMTDate(b2);
        } else {
            parseGMTDate2 = 0;
        }
        b2 = a.b(map, "Last-Modified");
        if (b2 != null) {
            parseGMTDate3 = parseGMTDate(b2);
        } else {
            parseGMTDate3 = 0;
        }
        b2 = a.b(map, "ETag");
        if (i != 0) {
            j2 = currentTimeMillis + (j * 1000);
        } else if (parseGMTDate > 0 && parseGMTDate2 >= parseGMTDate) {
            j2 = currentTimeMillis + (parseGMTDate2 - parseGMTDate);
        }
        Entry entry = new Entry();
        entry.etag = b2;
        entry.ttl = j2;
        entry.serverDate = parseGMTDate;
        entry.lastModified = parseGMTDate3;
        entry.responseHeaders = map;
        return entry;
    }
}
