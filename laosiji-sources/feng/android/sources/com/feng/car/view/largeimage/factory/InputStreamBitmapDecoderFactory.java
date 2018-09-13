package com.feng.car.view.largeimage.factory;

import android.graphics.BitmapRegionDecoder;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamBitmapDecoderFactory implements BitmapDecoderFactory {
    private InputStream inputStream;

    public InputStreamBitmapDecoderFactory(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public BitmapRegionDecoder made() throws IOException {
        return BitmapRegionDecoder.newInstance(this.inputStream, false);
    }
}
