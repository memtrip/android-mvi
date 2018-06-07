package com.consistence.pinyin.audio

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import com.consistence.pinyin.audio.stream.Notify

interface PlayPinyinAudio {
    fun attach(context: Context)
    fun detach(context: Context)
    fun playPinyinAudio(src: String, context: Context)
}

class PlayPinyAudioInPresenter : PlayPinyinAudio {

    val pinyinStream = PinyinStreamingNavigator()

    var pinyinAudioPlaying = false

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val type = Notify.getNotifyType(intent)

            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
            when (type) {
                Notify.NotifyType.PLAYING -> {
                    pinyinAudioPlaying = true
                }
                Notify.NotifyType.COMPLETED -> {
                    pinyinAudioPlaying = false
                }
            }
        }
    }

    override fun attach(context: Context) {
        LocalBroadcastManager
                .getInstance(context)
                .registerReceiver(broadcastReceiver, Notify.getIntentFilter())
    }

    override fun detach(context: Context) {
        LocalBroadcastManager
                .getInstance(context)
                .unregisterReceiver(broadcastReceiver)
    }

    override fun playPinyinAudio(src: String, context: Context) {
        if (!pinyinAudioPlaying) {
            pinyinStream.play(PinyinAudio(src), context)
        }
    }
}