package com.tencent.liteav.txcvodplayer;

import android.support.annotation.NonNull;
import android.view.View;
import com.tencent.ijk.media.player.IMediaPlayer;

/* compiled from: IRenderView */
public interface a {

    /* compiled from: IRenderView */
    public interface b {
        @NonNull
        a a();

        void a(IMediaPlayer iMediaPlayer);
    }

    /* compiled from: IRenderView */
    public interface a {
        void a(@NonNull b bVar);

        void a(@NonNull b bVar, int i, int i2);

        void a(@NonNull b bVar, int i, int i2, int i3);
    }

    void addRenderCallback(@NonNull a aVar);

    View getView();

    void removeRenderCallback(@NonNull a aVar);

    void setAspectRatio(int i);

    void setVideoRotation(int i);

    void setVideoSampleAspectRatio(int i, int i2);

    void setVideoSize(int i, int i2);

    boolean shouldWaitForResize();
}
