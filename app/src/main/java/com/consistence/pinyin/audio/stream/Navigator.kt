package com.consistence.pinyin.audio.stream

import android.content.Context

interface Navigator<T : Stream> {
    fun play(data: T, context: Context)
}