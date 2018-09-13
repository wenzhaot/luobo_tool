package com.feng.car.view.largeimage.factory;

import android.graphics.BitmapRegionDecoder;
import java.io.File;
import java.io.IOException;

public class FileBitmapDecoderFactory implements BitmapDecoderFactory {
    private String path;

    public FileBitmapDecoderFactory(String filePath) {
        this.path = filePath;
    }

    public FileBitmapDecoderFactory(File file) {
        this.path = file.getAbsolutePath();
    }

    public BitmapRegionDecoder made() throws IOException {
        return BitmapRegionDecoder.newInstance(this.path, false);
    }
}
