package com.qiniu.android.dns;

import com.qiniu.android.dns.local.Hosts;
import com.qiniu.android.dns.local.Hosts.Value;
import com.qiniu.android.dns.util.LruCache;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

public final class DnsManager {
    private final LruCache<String, Record[]> cache;
    private final Hosts hosts;
    private volatile int index;
    private volatile NetworkInfo info;
    private final IResolver[] resolvers;
    private final IpSorter sorter;

    private static class DummySorter implements IpSorter {
        private AtomicInteger pos;

        private DummySorter() {
            this.pos = new AtomicInteger();
        }

        public String[] sort(String[] ips) {
            return ips;
        }
    }

    public DnsManager(NetworkInfo info, IResolver[] resolvers) {
        this(info, resolvers, null);
    }

    public DnsManager(NetworkInfo info, IResolver[] resolvers, IpSorter sorter) {
        this.hosts = new Hosts();
        this.info = null;
        this.index = 0;
        if (info == null) {
            info = NetworkInfo.normal;
        }
        this.info = info;
        this.resolvers = (IResolver[]) resolvers.clone();
        this.cache = new LruCache();
        if (sorter == null) {
            sorter = new DummySorter();
        }
        this.sorter = sorter;
    }

    private static Record[] trimCname(Record[] records) {
        ArrayList<Record> a = new ArrayList(records.length);
        for (Record r : records) {
            if (r != null && r.type == 1) {
                a.add(r);
            }
        }
        return (Record[]) a.toArray(new Record[a.size()]);
    }

    private static void rotate(Record[] records) {
        if (records != null && records.length > 1) {
            Record first = records[0];
            System.arraycopy(records, 1, records, 0, records.length - 1);
            records[records.length - 1] = first;
        }
    }

    private static String[] records2Ip(Record[] records) {
        if (records == null || records.length == 0) {
            return null;
        }
        ArrayList<String> a = new ArrayList(records.length);
        for (Record r : records) {
            a.add(r.value);
        }
        if (a.size() != 0) {
            return (String[]) a.toArray(new String[a.size()]);
        }
        return null;
    }

    public static boolean validIP(String ip) {
        if (ip == null || ip.length() < 7 || ip.length() > 15 || ip.contains("-")) {
            return false;
        }
        try {
            int y = ip.indexOf(46);
            if (y != -1 && Integer.parseInt(ip.substring(0, y)) > 255) {
                return false;
            }
            y++;
            int x = ip.indexOf(46, y);
            if (x != -1 && Integer.parseInt(ip.substring(y, x)) > 255) {
                return false;
            }
            x++;
            y = ip.indexOf(46, x);
            if (y == -1 || Integer.parseInt(ip.substring(x, y)) <= 255 || Integer.parseInt(ip.substring(y + 1, ip.length() - 1)) <= 255 || ip.charAt(ip.length() - 1) == '.') {
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean needHttpDns() {
        try {
            String id = TimeZone.getDefault().getID();
            if ("Asia/Shanghai".equals(id) || "Asia/Chongqing".equals(id) || "Asia/Harbin".equals(id) || "Asia/Urumqi".equals(id)) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public String[] query(String domain) throws IOException {
        return query(new Domain(domain));
    }

    public String[] query(Domain domain) throws IOException {
        if (domain == null) {
            throw new IOException("null domain");
        } else if (domain.domain == null || domain.domain.trim().length() == 0) {
            throw new IOException("empty domain " + domain.domain);
        } else if (validIP(domain.domain)) {
            return new String[]{domain.domain};
        } else {
            String[] r = queryInternal(domain);
            return (r == null || r.length <= 1) ? r : this.sorter.sort(r);
        }
    }

    /* JADX WARNING: Missing block: B:21:0x0045, code:
            r8 = null;
            r4 = r16.index;
            r5 = 0;
     */
    /* JADX WARNING: Missing block: B:23:0x0050, code:
            if (r5 >= r16.resolvers.length) goto L_0x0102;
     */
    /* JADX WARNING: Missing block: B:24:0x0052, code:
            r9 = (r4 + r5) % r16.resolvers.length;
            r1 = r16.info;
            r6 = com.qiniu.android.dns.Network.getIp();
     */
    /* JADX WARNING: Missing block: B:26:?, code:
            r10 = r16.resolvers[r9].resolve(r17, r16.info);
     */
    /* JADX WARNING: Missing block: B:66:0x00e8, code:
            r2 = move-exception;
     */
    /* JADX WARNING: Missing block: B:67:0x00e9, code:
            r8 = r2;
            r2.printStackTrace();
     */
    /* JADX WARNING: Missing block: B:68:0x00ee, code:
            r3 = move-exception;
     */
    /* JADX WARNING: Missing block: B:70:0x00f3, code:
            if (android.os.Build.VERSION.SDK_INT >= 9) goto L_0x00f5;
     */
    /* JADX WARNING: Missing block: B:71:0x00f5, code:
            r8 = new java.io.IOException(r3);
     */
    /* JADX WARNING: Missing block: B:72:0x00fa, code:
            r3.printStackTrace();
     */
    private java.lang.String[] queryInternal(com.qiniu.android.dns.Domain r17) throws java.io.IOException {
        /*
        r16 = this;
        r10 = 0;
        r0 = r17;
        r13 = r0.hostsFirst;
        if (r13 == 0) goto L_0x001b;
    L_0x0007:
        r0 = r16;
        r13 = r0.hosts;
        r0 = r16;
        r14 = r0.info;
        r0 = r17;
        r11 = r13.query(r0, r14);
        if (r11 == 0) goto L_0x001b;
    L_0x0017:
        r13 = r11.length;
        if (r13 == 0) goto L_0x001b;
    L_0x001a:
        return r11;
    L_0x001b:
        r0 = r16;
        r14 = r0.cache;
        monitor-enter(r14);
        r0 = r16;
        r13 = r0.info;	 Catch:{ all -> 0x00b4 }
        r15 = com.qiniu.android.dns.NetworkInfo.normal;	 Catch:{ all -> 0x00b4 }
        r13 = r13.equals(r15);	 Catch:{ all -> 0x00b4 }
        if (r13 == 0) goto L_0x00b7;
    L_0x002c:
        r13 = com.qiniu.android.dns.Network.isNetworkChanged();	 Catch:{ all -> 0x00b4 }
        if (r13 == 0) goto L_0x00b7;
    L_0x0032:
        r0 = r16;
        r13 = r0.cache;	 Catch:{ all -> 0x00b4 }
        r13.clear();	 Catch:{ all -> 0x00b4 }
        r0 = r16;
        r15 = r0.resolvers;	 Catch:{ all -> 0x00b4 }
        monitor-enter(r15);	 Catch:{ all -> 0x00b4 }
        r13 = 0;
        r0 = r16;
        r0.index = r13;	 Catch:{ all -> 0x00b1 }
        monitor-exit(r15);	 Catch:{ all -> 0x00b1 }
    L_0x0044:
        monitor-exit(r14);	 Catch:{ all -> 0x00b4 }
        r8 = 0;
        r0 = r16;
        r4 = r0.index;
        r5 = 0;
    L_0x004b:
        r0 = r16;
        r13 = r0.resolvers;
        r13 = r13.length;
        if (r5 >= r13) goto L_0x0102;
    L_0x0052:
        r13 = r4 + r5;
        r0 = r16;
        r14 = r0.resolvers;
        r14 = r14.length;
        r9 = r13 % r14;
        r0 = r16;
        r1 = r0.info;
        r6 = com.qiniu.android.dns.Network.getIp();
        r0 = r16;
        r13 = r0.resolvers;	 Catch:{ DomainNotOwn -> 0x00e6, IOException -> 0x00e8, Exception -> 0x00ee }
        r13 = r13[r9];	 Catch:{ DomainNotOwn -> 0x00e6, IOException -> 0x00e8, Exception -> 0x00ee }
        r0 = r16;
        r14 = r0.info;	 Catch:{ DomainNotOwn -> 0x00e6, IOException -> 0x00e8, Exception -> 0x00ee }
        r0 = r17;
        r10 = r13.resolve(r0, r14);	 Catch:{ DomainNotOwn -> 0x00e6, IOException -> 0x00e8, Exception -> 0x00ee }
    L_0x0073:
        r7 = com.qiniu.android.dns.Network.getIp();
        r0 = r16;
        r13 = r0.info;
        if (r13 != r1) goto L_0x0102;
    L_0x007d:
        if (r10 == 0) goto L_0x0082;
    L_0x007f:
        r13 = r10.length;
        if (r13 != 0) goto L_0x0102;
    L_0x0082:
        r13 = r6.equals(r7);
        if (r13 == 0) goto L_0x0102;
    L_0x0088:
        r0 = r16;
        r14 = r0.resolvers;
        monitor-enter(r14);
        r0 = r16;
        r13 = r0.index;	 Catch:{ all -> 0x00ff }
        if (r13 != r4) goto L_0x00ad;
    L_0x0093:
        r0 = r16;
        r13 = r0.index;	 Catch:{ all -> 0x00ff }
        r13 = r13 + 1;
        r0 = r16;
        r0.index = r13;	 Catch:{ all -> 0x00ff }
        r0 = r16;
        r13 = r0.index;	 Catch:{ all -> 0x00ff }
        r0 = r16;
        r15 = r0.resolvers;	 Catch:{ all -> 0x00ff }
        r15 = r15.length;	 Catch:{ all -> 0x00ff }
        if (r13 != r15) goto L_0x00ad;
    L_0x00a8:
        r13 = 0;
        r0 = r16;
        r0.index = r13;	 Catch:{ all -> 0x00ff }
    L_0x00ad:
        monitor-exit(r14);	 Catch:{ all -> 0x00ff }
    L_0x00ae:
        r5 = r5 + 1;
        goto L_0x004b;
    L_0x00b1:
        r13 = move-exception;
        monitor-exit(r15);	 Catch:{ all -> 0x00b1 }
        throw r13;	 Catch:{ all -> 0x00b4 }
    L_0x00b4:
        r13 = move-exception;
        monitor-exit(r14);	 Catch:{ all -> 0x00b4 }
        throw r13;
    L_0x00b7:
        r0 = r16;
        r13 = r0.cache;	 Catch:{ all -> 0x00b4 }
        r0 = r17;
        r15 = r0.domain;	 Catch:{ all -> 0x00b4 }
        r13 = r13.get(r15);	 Catch:{ all -> 0x00b4 }
        r0 = r13;
        r0 = (com.qiniu.android.dns.Record[]) r0;	 Catch:{ all -> 0x00b4 }
        r10 = r0;
        if (r10 == 0) goto L_0x0044;
    L_0x00c9:
        r13 = r10.length;	 Catch:{ all -> 0x00b4 }
        if (r13 == 0) goto L_0x0044;
    L_0x00cc:
        r13 = 0;
        r13 = r10[r13];	 Catch:{ all -> 0x00b4 }
        r13 = r13.isExpired();	 Catch:{ all -> 0x00b4 }
        if (r13 != 0) goto L_0x00e3;
    L_0x00d5:
        r13 = r10.length;	 Catch:{ all -> 0x00b4 }
        r15 = 1;
        if (r13 <= r15) goto L_0x00dc;
    L_0x00d9:
        rotate(r10);	 Catch:{ all -> 0x00b4 }
    L_0x00dc:
        r11 = records2Ip(r10);	 Catch:{ all -> 0x00b4 }
        monitor-exit(r14);	 Catch:{ all -> 0x00b4 }
        goto L_0x001a;
    L_0x00e3:
        r10 = 0;
        goto L_0x0044;
    L_0x00e6:
        r2 = move-exception;
        goto L_0x00ae;
    L_0x00e8:
        r2 = move-exception;
        r8 = r2;
        r2.printStackTrace();
        goto L_0x0073;
    L_0x00ee:
        r3 = move-exception;
        r13 = android.os.Build.VERSION.SDK_INT;
        r14 = 9;
        if (r13 < r14) goto L_0x00fa;
    L_0x00f5:
        r8 = new java.io.IOException;
        r8.<init>(r3);
    L_0x00fa:
        r3.printStackTrace();
        goto L_0x0073;
    L_0x00ff:
        r13 = move-exception;
        monitor-exit(r14);	 Catch:{ all -> 0x00ff }
        throw r13;
    L_0x0102:
        if (r10 == 0) goto L_0x0107;
    L_0x0104:
        r13 = r10.length;
        if (r13 != 0) goto L_0x0130;
    L_0x0107:
        r0 = r17;
        r13 = r0.hostsFirst;
        if (r13 != 0) goto L_0x0123;
    L_0x010d:
        r0 = r16;
        r13 = r0.hosts;
        r0 = r16;
        r14 = r0.info;
        r0 = r17;
        r12 = r13.query(r0, r14);
        if (r12 == 0) goto L_0x0123;
    L_0x011d:
        r13 = r12.length;
        if (r13 == 0) goto L_0x0123;
    L_0x0120:
        r11 = r12;
        goto L_0x001a;
    L_0x0123:
        if (r8 == 0) goto L_0x0126;
    L_0x0125:
        throw r8;
    L_0x0126:
        r13 = new java.net.UnknownHostException;
        r0 = r17;
        r14 = r0.domain;
        r13.<init>(r14);
        throw r13;
    L_0x0130:
        r10 = trimCname(r10);
        r13 = r10.length;
        if (r13 != 0) goto L_0x0140;
    L_0x0137:
        r13 = new java.net.UnknownHostException;
        r14 = "no A records";
        r13.<init>(r14);
        throw r13;
    L_0x0140:
        r0 = r16;
        r14 = r0.cache;
        monitor-enter(r14);
        r0 = r16;
        r13 = r0.cache;	 Catch:{ all -> 0x0157 }
        r0 = r17;
        r15 = r0.domain;	 Catch:{ all -> 0x0157 }
        r13.put(r15, r10);	 Catch:{ all -> 0x0157 }
        monitor-exit(r14);	 Catch:{ all -> 0x0157 }
        r11 = records2Ip(r10);
        goto L_0x001a;
    L_0x0157:
        r13 = move-exception;
        monitor-exit(r14);	 Catch:{ all -> 0x0157 }
        throw r13;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qiniu.android.dns.DnsManager.queryInternal(com.qiniu.android.dns.Domain):java.lang.String[]");
    }

    public InetAddress[] queryInetAdress(Domain domain) throws IOException {
        String[] ips = query(domain);
        InetAddress[] addresses = new InetAddress[ips.length];
        for (int i = 0; i < ips.length; i++) {
            addresses[i] = InetAddress.getByName(ips[i]);
        }
        return addresses;
    }

    public void onNetworkChange(NetworkInfo info) {
        clearCache();
        if (info == null) {
            info = NetworkInfo.normal;
        }
        this.info = info;
        synchronized (this.resolvers) {
            this.index = 0;
        }
    }

    private void clearCache() {
        synchronized (this.cache) {
            this.cache.clear();
        }
    }

    public DnsManager putHosts(String domain, String ip, int provider) {
        this.hosts.put(domain, new Value(ip, provider));
        return this;
    }

    public DnsManager putHosts(String domain, String ip) {
        this.hosts.put(domain, ip);
        return this;
    }
}
