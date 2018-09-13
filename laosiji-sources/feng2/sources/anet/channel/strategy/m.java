package anet.channel.strategy;

import java.io.File;
import java.io.FileFilter;

/* compiled from: Taobao */
final class m implements FileFilter {
    m() {
    }

    public boolean accept(File file) {
        return file.isFile();
    }
}
