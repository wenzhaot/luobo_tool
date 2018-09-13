package com.talkingdata.sdk;

import android.os.Parcel;
import com.umeng.message.proguard.l;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: td */
public class ba extends File {
    public final String content;

    /* compiled from: td */
    static class a extends ba {
        public ArrayList groups;

        public static a get(int i) {
            try {
                return new a(String.format("/proc/%d/cgroup", new Object[]{Integer.valueOf(i)}));
            } catch (Throwable th) {
                return null;
            }
        }

        private a(String str) {
            super(str);
            try {
                this.groups = new ArrayList();
                for (String bVar : this.content.split("\n")) {
                    try {
                        this.groups.add(new b(bVar));
                    } catch (Throwable th) {
                    }
                }
            } catch (Throwable th2) {
            }
        }

        public b getGroup(String str) {
            try {
                Iterator it = this.groups.iterator();
                while (it.hasNext()) {
                    b bVar = (b) it.next();
                    for (String equals : bVar.subsystems.split(MiPushClient.ACCEPT_TIME_SEPARATOR)) {
                        if (equals.equals(str)) {
                            return bVar;
                        }
                    }
                }
            } catch (Throwable th) {
            }
            return null;
        }
    }

    /* compiled from: td */
    static class b {
        protected String group;
        protected int id;
        protected String subsystems;

        protected b(String str) {
            try {
                String[] split = str.split(":");
                this.id = Integer.parseInt(split[0]);
                this.subsystems = split[1];
                this.group = split[2];
            } catch (Throwable th) {
            }
        }

        protected b(Parcel parcel) {
            try {
                this.id = parcel.readInt();
                this.subsystems = parcel.readString();
                this.group = parcel.readString();
            } catch (Throwable th) {
            }
        }
    }

    /* compiled from: td */
    public static class c extends ba {
        private String[] fields;

        public static c get(int i) {
            try {
                return new c(String.format("/proc/%d/stat", new Object[]{Integer.valueOf(i)}));
            } catch (Throwable th) {
                return null;
            }
        }

        private c(String str) {
            super(str);
            this.fields = new String[0];
            try {
                this.fields = this.content.split("\\s+");
            } catch (Throwable th) {
            }
        }

        private c(Parcel parcel) {
            super(parcel);
            this.fields = new String[0];
            try {
                this.fields = parcel.createStringArray();
            } catch (Throwable th) {
            }
        }

        public long startTime() {
            try {
                return Long.parseLong(this.fields[21]);
            } catch (Throwable th) {
                return 0;
            }
        }

        public String getComm() {
            try {
                return this.fields[1].replace(l.s, "").replace(l.t, "");
            } catch (Throwable th) {
                return "";
            }
        }

        public char state() {
            char c = 0;
            try {
                return this.fields[2].charAt(0);
            } catch (Throwable th) {
                return c;
            }
        }
    }

    /* compiled from: td */
    static class d extends ba {
        public static d get(int i) {
            try {
                return new d(String.format("/proc/%d/status", new Object[]{Integer.valueOf(i)}));
            } catch (Throwable th) {
                return null;
            }
        }

        private d(String str) {
            super(str);
        }

        private d(Parcel parcel) {
            super(parcel);
        }

        public String getValue(String str) {
            try {
                for (String str2 : this.content.split("\n")) {
                    if (str2.startsWith(str + ":")) {
                        return str2.split(str + ":")[1].trim();
                    }
                }
            } catch (Throwable th) {
            }
            return null;
        }

        public int getUid() {
            try {
                return Integer.parseInt(getValue("Uid").split("\\s+")[0]);
            } catch (Throwable th) {
                return -1;
            }
        }

        public int getGid() {
            try {
                return Integer.parseInt(getValue("Gid").split("\\s+")[0]);
            } catch (Throwable th) {
                return -1;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x003a A:{SYNTHETIC, Splitter: B:17:0x003a} */
    public static java.lang.String readFile(java.lang.String r5) {
        /*
        r0 = 0;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r1 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x0032, all -> 0x0040 }
        r2 = new java.io.FileReader;	 Catch:{ Throwable -> 0x0032, all -> 0x0040 }
        r2.<init>(r5);	 Catch:{ Throwable -> 0x0032, all -> 0x0040 }
        r1.<init>(r2);	 Catch:{ Throwable -> 0x0032, all -> 0x0040 }
        r2 = r1.readLine();	 Catch:{ Throwable -> 0x0050 }
        r0 = "";
    L_0x0017:
        if (r2 == 0) goto L_0x0028;
    L_0x0019:
        r0 = r3.append(r0);	 Catch:{ Throwable -> 0x0050 }
        r0.append(r2);	 Catch:{ Throwable -> 0x0050 }
        r0 = "\n";
        r2 = r1.readLine();	 Catch:{ Throwable -> 0x0050 }
        goto L_0x0017;
    L_0x0028:
        r0 = r3.toString();	 Catch:{ Throwable -> 0x0050 }
        if (r1 == 0) goto L_0x0031;
    L_0x002e:
        r1.close();	 Catch:{ Throwable -> 0x004a }
    L_0x0031:
        return r0;
    L_0x0032:
        r1 = move-exception;
        r1 = r0;
    L_0x0034:
        r0 = r3.toString();	 Catch:{ all -> 0x004e }
        if (r1 == 0) goto L_0x0031;
    L_0x003a:
        r1.close();	 Catch:{ Throwable -> 0x003e }
        goto L_0x0031;
    L_0x003e:
        r1 = move-exception;
        goto L_0x0031;
    L_0x0040:
        r1 = move-exception;
        r4 = r1;
        r1 = r0;
        r0 = r4;
    L_0x0044:
        if (r1 == 0) goto L_0x0049;
    L_0x0046:
        r1.close();	 Catch:{ Throwable -> 0x004c }
    L_0x0049:
        throw r0;
    L_0x004a:
        r1 = move-exception;
        goto L_0x0031;
    L_0x004c:
        r1 = move-exception;
        goto L_0x0049;
    L_0x004e:
        r0 = move-exception;
        goto L_0x0044;
    L_0x0050:
        r0 = move-exception;
        goto L_0x0034;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.talkingdata.sdk.ba.readFile(java.lang.String):java.lang.String");
    }

    protected ba(String str) {
        super(str);
        this.content = readFile(str);
    }

    protected ba(Parcel parcel) {
        super(parcel.readString());
        this.content = parcel.readString();
    }

    public long length() {
        return (long) this.content.length();
    }
}
