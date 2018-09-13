package okio;

import com.tencent.ijk.media.player.IjkMediaMeta;
import javax.annotation.Nullable;

final class SegmentPool {
    static final long MAX_SIZE = 65536;
    static long byteCount;
    @Nullable
    static Segment next;

    private SegmentPool() {
    }

    static Segment take() {
        synchronized (SegmentPool.class) {
            if (next != null) {
                Segment result = next;
                next = result.next;
                result.next = null;
                byteCount -= IjkMediaMeta.AV_CH_TOP_FRONT_CENTER;
                return result;
            }
            return new Segment();
        }
    }

    static void recycle(Segment segment) {
        if (segment.next != null || segment.prev != null) {
            throw new IllegalArgumentException();
        } else if (!segment.shared) {
            synchronized (SegmentPool.class) {
                if (byteCount + IjkMediaMeta.AV_CH_TOP_FRONT_CENTER > 65536) {
                    return;
                }
                byteCount += IjkMediaMeta.AV_CH_TOP_FRONT_CENTER;
                segment.next = next;
                segment.limit = 0;
                segment.pos = 0;
                next = segment;
            }
        }
    }
}
