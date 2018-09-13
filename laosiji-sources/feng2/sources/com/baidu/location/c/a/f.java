package com.baidu.location.c.a;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class f {
    private byte[] a;
    private int b;

    public f() {
        this(512);
    }

    private f(int i) {
        this.b = i;
        this.a = new byte[this.b];
    }

    public void a(String str, String str2) {
        try {
            ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(str)));
            while (true) {
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                if (nextEntry != null) {
                    String name = nextEntry.getName();
                    if (name == null || !name.contains("../")) {
                        name = str2 + nextEntry.getName();
                        File file = new File(name);
                        if (nextEntry.isDirectory()) {
                            file.mkdirs();
                        } else {
                            File parentFile = file.getParentFile();
                            if (!(parentFile == null || parentFile.exists())) {
                                parentFile.mkdirs();
                            }
                            FileOutputStream fileOutputStream = new FileOutputStream(name);
                            while (true) {
                                int read = zipInputStream.read(this.a);
                                if (read <= 0) {
                                    break;
                                }
                                fileOutputStream.write(this.a, 0, read);
                            }
                            fileOutputStream.close();
                        }
                        zipInputStream.closeEntry();
                    }
                } else {
                    zipInputStream.close();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
