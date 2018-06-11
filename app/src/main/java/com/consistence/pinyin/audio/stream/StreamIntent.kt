package com.consistence.pinyin.audio.stream

import android.content.Intent

interface StreamIntent<T : Stream> {

    fun into(data: T, intent: Intent)

    fun get(intent: Intent): T
}