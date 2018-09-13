package com.talkingdata.sdk;

import com.umeng.commonsdk.proguard.g;
import java.io.File;
import java.io.FileFilter;

/* compiled from: td */
final class ax implements FileFilter {
    ax() {
    }

    public boolean accept(File file) {
        if (file == null) {
            return false;
        }
        try {
            String name = file.getName();
            if (name == null || !name.startsWith(g.v)) {
                return false;
            }
            int i = 3;
            while (i < name.length()) {
                if (name.charAt(i) < '0' || name.charAt(i) > '9') {
                    return false;
                }
                i++;
            }
            return true;
        } catch (Throwable th) {
            return false;
        }
    }
}
