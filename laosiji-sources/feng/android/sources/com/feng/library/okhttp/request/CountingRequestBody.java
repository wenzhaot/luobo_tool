package com.feng.library.okhttp.request;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class CountingRequestBody extends RequestBody {
    protected CountingSink countingSink;
    protected RequestBody delegate;
    protected Listener listener;

    protected final class CountingSink extends ForwardingSink {
        private long bytesWritten = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            this.bytesWritten += byteCount;
            CountingRequestBody.this.listener.onRequestProgress(this.bytesWritten, CountingRequestBody.this.contentLength());
        }
    }

    public interface Listener {
        void onRequestProgress(long j, long j2);
    }

    public CountingRequestBody(RequestBody delegate, Listener listener) {
        this.delegate = delegate;
        this.listener = listener;
    }

    public MediaType contentType() {
        return this.delegate.contentType();
    }

    public long contentLength() {
        try {
            return this.delegate.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void writeTo(BufferedSink sink) throws IOException {
        this.countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(this.countingSink);
        this.delegate.writeTo(bufferedSink);
        bufferedSink.flush();
    }
}
