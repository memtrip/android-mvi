package com.memtrip.pinyin.audio.stream;

import android.content.Intent;

public interface StreamIntent<T extends Stream> {

    String EXTRA_STREAM_PROGRESS = "EXTRA_STREAM_PROGRESS";

    void into(T t, Intent intent);

    T get(Intent intent);
}