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

    private val pinyinStream = PinyinStreamingNavigator()

    var pinyinAudioPlaying = false

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val type = Notify.getNotifyType(intent)

            pinyinAudioPlaying = when (type) {
                Notify.NotifyType.PLAYING -> true
                Notify.NotifyType.COMPLETED -> false
            }
        }
    }

    override fun attach(context: Context) {
        LocalBroadcastManager
                .getInstance(context)
                .registerReceiver(broadcastReceiver, Notify.intentFilter)
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