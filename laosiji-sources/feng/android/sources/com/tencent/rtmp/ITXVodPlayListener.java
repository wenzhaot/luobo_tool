package com.tencent.rtmp;

import android.os.Bundle;

public interface ITXVodPlayListener {
    void onNetStatus(TXVodPlayer tXVodPlayer, Bundle bundle);

    void onPlayEvent(TXVodPlayer tXVodPlayer, int i, Bundle bundle);
}
