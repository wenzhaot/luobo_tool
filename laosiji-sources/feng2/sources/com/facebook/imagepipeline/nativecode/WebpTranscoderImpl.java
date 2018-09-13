package com.facebook.imagepipeline.nativecode;

import android.os.Build.VERSION;
import com.facebook.common.internal.DoNotStrip;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.soloader.SoLoaderShim;
import com.facebook.common.webp.WebpSupportStatus;
import com.facebook.imageformat.ImageFormat;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@DoNotStrip
public class WebpTranscoderImpl implements WebpTranscoder {
    @DoNotStrip
    private static native void nativeTranscodeWebpToJpeg(InputStream inputStream, OutputStream outputStream, int i) throws IOException;

    @DoNotStrip
    private static native void nativeTranscodeWebpToPng(InputStream inputStream, OutputStream outputStream) throws IOException;

    static {
        SoLoaderShim.loadLibrary("static-webp");
    }

    public boolean isWebpNativelySupported(ImageFormat webpFormat) {
        switch (webpFormat) {
            case WEBP_SIMPLE:
                if (VERSION.SDK_INT >= 14) {
                    return true;
                }
                return false;
            case WEBP_LOSSLESS:
            case WEBP_EXTENDED:
            case WEBP_EXTENDED_WITH_ALPHA:
                return WebpSupportStatus.sIsExtendedWebpSupported;
            case WEBP_ANIMATED:
                return false;
            default:
                Preconditions.checkArgument(false);
                return false;
        }
    }

    public void transcodeWebpToJpeg(InputStream inputStream, OutputStream outputStream, int quality) throws IOException {
        nativeTranscodeWebpToJpeg((InputStream) Preconditions.checkNotNull(inputStream), (OutputStream) Preconditions.checkNotNull(outputStream), quality);
    }

    public void transcodeWebpToPng(InputStream inputStream, OutputStream outputStream) throws IOException {
        nativeTranscodeWebpToPng((InputStream) Preconditions.checkNotNull(inputStream), (OutputStream) Preconditions.checkNotNull(outputStream));
    }
}
