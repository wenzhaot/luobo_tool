package com.tencent.ijk.media.player;

public interface IjkLibLoader {
    void loadLibrary(String str) throws UnsatisfiedLinkError, SecurityException;
}
