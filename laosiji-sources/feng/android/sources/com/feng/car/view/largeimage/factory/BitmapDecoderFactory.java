package com.feng.car.view.largeimage.factory;

import android.graphics.BitmapRegionDecoder;
import java.io.IOException;

public interface BitmapDecoderFactory {
    BitmapRegionDecoder made() throws IOException;
}
