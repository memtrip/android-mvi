package com.consistence.pinyin.audio.stream;

import android.content.Context;

public interface Navigator<T extends Stream> {
    void play(T data, Context context);
}