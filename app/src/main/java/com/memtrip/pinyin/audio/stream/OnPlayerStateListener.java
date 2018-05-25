package com.memtrip.pinyin.audio.stream;

import java.io.IOException;

public interface OnPlayerStateListener {
    void startBuffering();
    void onBufferingError(IOException e);
    void onPlay();
    void onCompleted();
}