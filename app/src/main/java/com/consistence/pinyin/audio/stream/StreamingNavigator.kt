package com.consistence.pinyin.audio.stream

import android.content.Context
import android.content.Intent

abstract class StreamingNavigator<T : Stream>(private val streamIntent: StreamIntent<T>,
                                              private val classDef: Class<out StreamingService<T>>) : Navigator<T> {

    override fun play(data: T, context: Context) {
        val intent = Intent(context, classDef)
        intent.action = ACTION_NOTIFICATION_PLAY

        streamIntent.into(data, intent)

        context.startService(intent)
    }

    companion object {
        private const val ACTION_NOTIFICATION_PLAY = "ACTION_NOTIFICATION_PLAY"
    }
}