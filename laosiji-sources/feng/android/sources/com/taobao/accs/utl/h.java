package com.taobao.accs.utl;

import com.qiniu.android.common.Constants;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/* compiled from: Taobao */
public class h extends ByteArrayInputStream {
    public h(byte[] bArr) {
        super(bArr);
    }

    public int a() {
        return read() & 255;
    }

    public int b() {
        return (a() << 8) | a();
    }

    public String a(int i) throws IOException {
        byte[] bArr = new byte[i];
        int read = read(bArr);
        if (read == i) {
            return new String(bArr, Constants.UTF_8);
        }
        throw new IOException("read len not match. ask for " + i + " but read for " + read);
    }

    public byte[] c() throws IOException {
        byte[] bArr = new byte[available()];
        read(bArr);
        return bArr;
    }
}
