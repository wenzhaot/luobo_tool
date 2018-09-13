package com.talkingdata.sdk;

import java.io.File;
import java.util.Comparator;

/* compiled from: td */
class dk implements Comparator {
    final /* synthetic */ dj this$0;

    dk(dj djVar) {
        this.this$0 = djVar;
    }

    public int compare(File file, File file2) {
        return file.getName().compareTo(file2.getName());
    }
}
