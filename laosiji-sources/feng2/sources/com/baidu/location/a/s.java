package com.baidu.location.a;

import android.location.Location;
import com.baidu.location.BDLocation;
import com.baidu.location.Jni;
import com.baidu.location.b.e;
import com.baidu.location.e.h;
import com.baidu.location.e.h.b;
import com.baidu.location.f.d;
import com.baidu.location.f.f;
import com.baidu.location.f.g;
import com.baidu.location.h.j;
import com.baidu.location.h.k;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.json.JSONObject;

public class s {
    private static s A = null;
    private static ArrayList<String> b = new ArrayList();
    private static ArrayList<String> c = new ArrayList();
    private static ArrayList<String> d = new ArrayList();
    private static String e = (j.a + "/yo.dat");
    private static final String f = (j.a + "/yoh.dat");
    private static final String g = (j.a + "/yom.dat");
    private static final String h = (j.a + "/yol.dat");
    private static final String i = (j.a + "/yor.dat");
    private static File j = null;
    private static int k = 8;
    private static int l = 8;
    private static int m = 16;
    private static int n = 1024;
    private static double o = 0.0d;
    private static double p = 0.1d;
    private static double q = 30.0d;
    private static double r = 100.0d;
    private static int s = 0;
    private static int t = 64;
    private static int u = 128;
    private static Location v = null;
    private static Location w = null;
    private static Location x = null;
    private static f y = null;
    private int B;
    long a;
    private a z;

    private class a extends com.baidu.location.h.f {
        boolean a;
        int b;
        int c;
        private ArrayList<String> e;
        private boolean f;

        public a() {
            this.a = false;
            this.b = 0;
            this.c = 0;
            this.e = null;
            this.f = true;
            this.k = new HashMap();
        }

        public void a() {
            this.h = k.c();
            if (this.b != 1) {
                this.h = k.e();
            }
            this.i = 2;
            if (this.e != null) {
                for (int i = 0; i < this.e.size(); i++) {
                    if (this.b == 1) {
                        this.k.put("cldc[" + i + "]", this.e.get(i));
                    } else {
                        this.k.put("cltr[" + i + "]", this.e.get(i));
                    }
                }
                this.k.put("trtm", String.format(Locale.CHINA, "%d", new Object[]{Long.valueOf(System.currentTimeMillis())}));
                if (this.b != 1) {
                    this.k.put("qt", "cltrg");
                }
            }
        }

        public void a(boolean z) {
            if (z && this.j != null) {
                if (this.e != null) {
                    this.e.clear();
                }
                try {
                    JSONObject jSONObject = new JSONObject(this.j);
                    if (jSONObject.has("ison") && jSONObject.getInt("ison") == 0) {
                        this.f = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (this.k != null) {
                this.k.clear();
            }
            this.a = false;
        }

        public void b() {
            if (!this.a) {
                if (o <= 4 || this.c >= o) {
                    this.c = 0;
                    this.a = true;
                    this.b = 0;
                    if (this.e == null || this.e.size() < 1) {
                        if (this.e == null) {
                            this.e = new ArrayList();
                        }
                        this.b = 0;
                        int i = 0;
                        while (true) {
                            String b = this.b < 2 ? s.b() : null;
                            if (b == null && this.b != 1 && this.f) {
                                this.b = 2;
                                try {
                                    b = f.b();
                                } catch (Exception e) {
                                    b = null;
                                }
                            } else {
                                this.b = 1;
                            }
                            if (b == null) {
                                break;
                            } else if (!b.contains("err!")) {
                                this.e.add(b);
                                i += b.length();
                                if (i >= com.baidu.location.h.a.i) {
                                    break;
                                }
                            }
                        }
                    }
                    if (this.e == null || this.e.size() < 1) {
                        this.e = null;
                        this.a = false;
                        return;
                    } else if (this.b != 1) {
                        c(k.e());
                        return;
                    } else {
                        c(k.f);
                        return;
                    }
                }
                this.c++;
            }
        }
    }

    private s() {
        this.z = null;
        this.B = 0;
        this.a = 0;
        this.z = new a();
        this.B = 0;
    }

    private static synchronized int a(List<String> list, int i) {
        int i2;
        synchronized (s.class) {
            if (list == null || i > 256 || i < 0) {
                i2 = -1;
            } else {
                try {
                    if (j == null) {
                        j = new File(e);
                        if (!j.exists()) {
                            j = null;
                            i2 = -2;
                        }
                    }
                    RandomAccessFile randomAccessFile = new RandomAccessFile(j, "rw");
                    if (randomAccessFile.length() < 1) {
                        randomAccessFile.close();
                        i2 = -3;
                    } else {
                        randomAccessFile.seek((long) i);
                        i2 = randomAccessFile.readInt();
                        int readInt = randomAccessFile.readInt();
                        int readInt2 = randomAccessFile.readInt();
                        int readInt3 = randomAccessFile.readInt();
                        long readLong = randomAccessFile.readLong();
                        if (!a(i2, readInt, readInt2, readInt3, readLong) || readInt < 1) {
                            randomAccessFile.close();
                            i2 = -4;
                        } else {
                            byte[] bArr = new byte[n];
                            int i3 = readInt;
                            readInt = k;
                            while (readInt > 0 && i3 > 0) {
                                randomAccessFile.seek(((long) ((((i2 + i3) - 1) % readInt2) * readInt3)) + readLong);
                                int readInt4 = randomAccessFile.readInt();
                                if (readInt4 > 0 && readInt4 < readInt3) {
                                    randomAccessFile.read(bArr, 0, readInt4);
                                    if (bArr[readInt4 - 1] == (byte) 0) {
                                        list.add(new String(bArr, 0, readInt4 - 1));
                                    }
                                }
                                readInt--;
                                i3--;
                            }
                            randomAccessFile.seek((long) i);
                            randomAccessFile.writeInt(i2);
                            randomAccessFile.writeInt(i3);
                            randomAccessFile.writeInt(readInt2);
                            randomAccessFile.writeInt(readInt3);
                            randomAccessFile.writeLong(readLong);
                            randomAccessFile.close();
                            i2 = k - readInt;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    i2 = -5;
                }
            }
        }
        return i2;
    }

    public static synchronized s a() {
        s sVar;
        synchronized (s.class) {
            if (A == null) {
                A = new s();
            }
            sVar = A;
        }
        return sVar;
    }

    public static String a(int i) {
        String str;
        List list;
        String str2 = null;
        String str3;
        Object list2;
        if (i == 1) {
            str3 = f;
            str = str3;
            list2 = b;
        } else if (i == 2) {
            str3 = g;
            str = str3;
            list2 = c;
        } else if (i == 3) {
            str3 = h;
            str = str3;
            list2 = d;
        } else if (i != 4) {
            return null;
        } else {
            str3 = i;
            str = str3;
            list2 = d;
        }
        if (list2 == null) {
            return null;
        }
        if (list2.size() < 1) {
            a(str, list2);
        }
        synchronized (s.class) {
            int size = list2.size();
            if (size > 0) {
                try {
                    str = (String) list2.get(size - 1);
                    try {
                        list2.remove(size - 1);
                    } catch (Exception e) {
                        str2 = str;
                        str = str2;
                        return str;
                    }
                } catch (Exception e2) {
                    str = str2;
                    return str;
                }
            }
            str = null;
        }
        return str;
    }

    public static void a(int i, boolean z) {
        String str;
        List list;
        Object list2;
        String str2;
        if (i == 1) {
            str2 = f;
            if (!z) {
                str = str2;
                list2 = b;
            } else {
                return;
            }
        } else if (i == 2) {
            str2 = g;
            if (z) {
                str = str2;
                list2 = b;
            } else {
                str = str2;
                list2 = c;
            }
        } else if (i == 3) {
            str2 = h;
            if (z) {
                str = str2;
                list2 = c;
            } else {
                str = str2;
                list2 = d;
            }
        } else if (i == 4) {
            str2 = i;
            if (z) {
                str = str2;
                list2 = d;
            } else {
                return;
            }
        } else {
            return;
        }
        File file = new File(str);
        if (!file.exists()) {
            a(str);
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(4);
            int readInt = randomAccessFile.readInt();
            int readInt2 = randomAccessFile.readInt();
            int readInt3 = randomAccessFile.readInt();
            int readInt4 = randomAccessFile.readInt();
            int readInt5 = randomAccessFile.readInt();
            int size = list2.size();
            while (true) {
                int i2 = size;
                size = readInt5;
                if (i2 <= l) {
                    list2 = null;
                    break;
                }
                readInt5 = z ? size + 1 : size;
                byte[] bytes;
                if (readInt3 >= readInt) {
                    if (!z) {
                        list2 = 1;
                        size = readInt5;
                        break;
                    }
                    randomAccessFile.seek((long) ((readInt4 * readInt2) + 128));
                    bytes = (((String) list2.get(0)) + 0).getBytes();
                    randomAccessFile.writeInt(bytes.length);
                    randomAccessFile.write(bytes, 0, bytes.length);
                    list2.remove(0);
                    size = readInt4 + 1;
                    if (size > readInt3) {
                        size = 0;
                    }
                    readInt4 = readInt3;
                } else {
                    randomAccessFile.seek((long) ((readInt2 * readInt3) + 128));
                    bytes = (((String) list2.get(0)) + 0).getBytes();
                    randomAccessFile.writeInt(bytes.length);
                    randomAccessFile.write(bytes, 0, bytes.length);
                    list2.remove(0);
                    int i3 = readInt4;
                    readInt4 = readInt3 + 1;
                    size = i3;
                }
                i2--;
                readInt3 = readInt4;
            }
            randomAccessFile.seek(12);
            randomAccessFile.writeInt(readInt3);
            randomAccessFile.writeInt(readInt4);
            randomAccessFile.writeInt(size);
            randomAccessFile.close();
            if (list2 != null && i < 4) {
                a(i + 1, true);
            }
        } catch (Exception e) {
        }
    }

    public static void a(com.baidu.location.f.a aVar, f fVar, Location location, String str) {
        if (!e.a().a) {
            return;
        }
        if ((k.u != 3 || a(location, fVar) || a(location, false)) && aVar != null && !aVar.c()) {
            BDLocation a;
            String str2;
            if (k.a(com.baidu.location.f.getServiceContext())) {
                a = h.a().a(aVar, fVar, null, b.IS_MIX_MODE, com.baidu.location.e.h.a.NO_NEED_TO_LOG);
            } else {
                a = h.a().a(aVar, fVar, null, b.IS_NOT_MIX_MODE, com.baidu.location.e.h.a.NO_NEED_TO_LOG);
            }
            if (a == null || a.getLocType() == 67) {
                str2 = str + String.format(Locale.CHINA, "&ofl=%s|0", new Object[]{"1"});
            } else {
                int i = 0;
                String str3 = null;
                if (!(a == null || a.getNetworkLocationType() == null)) {
                    str3 = a.getNetworkLocationType();
                }
                if (str3 != null && str3.equals("cl")) {
                    i = 1;
                } else if (str3 != null && str3.equals("wf")) {
                    i = 2;
                }
                str2 = str + String.format(Locale.CHINA, "&ofl=%s|%d|%f|%f|%d", new Object[]{"1", Integer.valueOf(i), Double.valueOf(a.getLongitude()), Double.valueOf(a.getLatitude()), Integer.valueOf((int) a.getRadius())});
            }
            if (aVar != null && aVar.a()) {
                if (!a(location, fVar)) {
                    fVar = null;
                }
                str2 = k.a(aVar, fVar, location, str2, 1);
                if (str2 != null) {
                    c(Jni.encode(str2));
                    w = location;
                    v = location;
                    if (fVar != null) {
                        y = fVar;
                    }
                }
            } else if (fVar != null && fVar.k() && a(location, fVar)) {
                if (!a(location) && !com.baidu.location.f.b.a().d()) {
                    str2 = "&cfr=1" + str2;
                } else if (!a(location) && com.baidu.location.f.b.a().d()) {
                    str2 = "&cfr=3" + str2;
                } else if (com.baidu.location.f.b.a().d()) {
                    str2 = "&cfr=2" + str2;
                }
                str2 = k.a(aVar, fVar, location, str2, 2);
                if (str2 != null) {
                    d(Jni.encode(str2));
                    x = location;
                    v = location;
                    if (fVar != null) {
                        y = fVar;
                    }
                }
            } else {
                if (!a(location) && !com.baidu.location.f.b.a().d()) {
                    str2 = "&cfr=1" + str2;
                } else if (!a(location) && com.baidu.location.f.b.a().d()) {
                    str2 = "&cfr=3" + str2;
                } else if (com.baidu.location.f.b.a().d()) {
                    str2 = "&cfr=2" + str2;
                }
                if (!a(location, fVar)) {
                    fVar = null;
                }
                if (aVar != null || fVar != null) {
                    str2 = k.a(aVar, fVar, location, str2, 3);
                    if (str2 != null) {
                        e(Jni.encode(str2));
                        v = location;
                        if (fVar != null) {
                            y = fVar;
                        }
                    }
                }
            }
        }
    }

    public static void a(String str) {
        try {
            File file = new File(str);
            if (!file.exists()) {
                File file2 = new File(j.a);
                if (!file2.exists()) {
                    file2.mkdirs();
                }
                if (!file.createNewFile()) {
                    file = null;
                }
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(0);
                randomAccessFile.writeInt(32);
                randomAccessFile.writeInt(2048);
                randomAccessFile.writeInt(1040);
                randomAccessFile.writeInt(0);
                randomAccessFile.writeInt(0);
                randomAccessFile.writeInt(0);
                randomAccessFile.close();
            }
        } catch (Exception e) {
        }
    }

    private static boolean a(int i, int i2, int i3, int i4, long j) {
        return i >= 0 && i < i3 && i2 >= 0 && i2 <= i3 && i3 >= 0 && i3 <= 1024 && i4 >= 128 && i4 <= 1024;
    }

    private static boolean a(Location location) {
        if (location == null) {
            return false;
        }
        if (w == null || v == null) {
            w = location;
            return true;
        }
        double distanceTo = (double) location.distanceTo(w);
        return ((double) location.distanceTo(v)) > ((distanceTo * ((double) k.S)) + ((((double) k.R) * distanceTo) * distanceTo)) + ((double) k.T);
    }

    private static boolean a(Location location, f fVar) {
        if (location == null || fVar == null || fVar.a == null || fVar.a.isEmpty() || fVar.b(y)) {
            return false;
        }
        if (x != null) {
            return true;
        }
        x = location;
        return true;
    }

    public static boolean a(Location location, boolean z) {
        return d.a(v, location, z);
    }

    public static boolean a(String str, List<String> list) {
        File file = new File(str);
        if (!file.exists()) {
            return false;
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(8);
            int readInt = randomAccessFile.readInt();
            int readInt2 = randomAccessFile.readInt();
            int readInt3 = randomAccessFile.readInt();
            byte[] bArr = new byte[n];
            int i = readInt2;
            readInt2 = l + 1;
            boolean z = false;
            while (readInt2 > 0 && i > 0) {
                if (i < readInt3) {
                    readInt3 = 0;
                }
                try {
                    randomAccessFile.seek((long) (((i - 1) * readInt) + 128));
                    int readInt4 = randomAccessFile.readInt();
                    if (readInt4 > 0 && readInt4 < readInt) {
                        randomAccessFile.read(bArr, 0, readInt4);
                        if (bArr[readInt4 - 1] == (byte) 0) {
                            list.add(0, new String(bArr, 0, readInt4 - 1));
                            z = true;
                        }
                    }
                    readInt2--;
                    i--;
                } catch (Exception e) {
                    return z;
                }
            }
            randomAccessFile.seek(12);
            randomAccessFile.writeInt(i);
            randomAccessFile.writeInt(readInt3);
            randomAccessFile.close();
            return z;
        } catch (Exception e2) {
            return false;
        }
    }

    public static String b() {
        return d();
    }

    public static synchronized void b(String str) {
        synchronized (s.class) {
            if (!str.contains("err!")) {
                List list;
                int i = k.p;
                if (i == 1) {
                    list = b;
                } else if (i == 2) {
                    list = c;
                } else if (i == 3) {
                    list = d;
                }
                if (list != null) {
                    if (list.size() <= m) {
                        list.add(str);
                    }
                    if (list.size() >= m) {
                        a(i, false);
                    }
                    while (list.size() > m) {
                        list.remove(0);
                    }
                }
            }
        }
    }

    private static void c(String str) {
        b(str);
    }

    public static String d() {
        String str = null;
        for (int i = 1; i < 5; i++) {
            str = a(i);
            if (str != null) {
                return str;
            }
        }
        a(d, t);
        if (d.size() > 0) {
            str = (String) d.get(0);
            d.remove(0);
        }
        if (str != null) {
            return str;
        }
        a(d, s);
        if (d.size() > 0) {
            str = (String) d.get(0);
            d.remove(0);
        }
        if (str != null) {
            return str;
        }
        a(d, u);
        if (d.size() <= 0) {
            return str;
        }
        str = (String) d.get(0);
        d.remove(0);
        return str;
    }

    private static void d(String str) {
        b(str);
    }

    public static void e() {
        l = 0;
        a(1, false);
        a(2, false);
        a(3, false);
        l = 8;
    }

    private static void e(String str) {
        b(str);
    }

    public static String f() {
        RandomAccessFile randomAccessFile;
        int readInt;
        String str;
        File file = new File(g);
        if (file.exists()) {
            try {
                randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(20);
                readInt = randomAccessFile.readInt();
                if (readInt > 128) {
                    str = "&p1=" + readInt;
                    randomAccessFile.seek(20);
                    randomAccessFile.writeInt(0);
                    randomAccessFile.close();
                    return str;
                }
                randomAccessFile.close();
            } catch (Exception e) {
            }
        }
        file = new File(h);
        if (file.exists()) {
            try {
                randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(20);
                readInt = randomAccessFile.readInt();
                if (readInt > 256) {
                    str = "&p2=" + readInt;
                    randomAccessFile.seek(20);
                    randomAccessFile.writeInt(0);
                    randomAccessFile.close();
                    return str;
                }
                randomAccessFile.close();
            } catch (Exception e2) {
            }
        }
        file = new File(i);
        if (!file.exists()) {
            return null;
        }
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(20);
            readInt = randomAccessFile.readInt();
            if (readInt > 512) {
                str = "&p3=" + readInt;
                randomAccessFile.seek(20);
                randomAccessFile.writeInt(0);
                randomAccessFile.close();
                return str;
            }
            randomAccessFile.close();
            return null;
        } catch (Exception e3) {
            return null;
        }
    }

    public void c() {
        if (g.j()) {
            this.z.b();
        }
    }
}
