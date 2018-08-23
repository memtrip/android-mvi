package com.consistence.pinyin.audio

import android.content.Context

import com.memtrip.exoeasy.AudioStreamController
import com.memtrip.exoeasy.NotificationInfo
import com.memtrip.exoeasy.broadcast.PlayBackState
import com.memtrip.exoeasy.broadcast.PlayBackStateUpdates

interface PlayPinyinAudio {
    fun attach(context: Context)
    fun detach(context: Context)
    fun playPinyinAudio(src: String, context: Context)
}

class PlayPinyAudioInPresenter : PlayPinyinAudio {

    private var playBackStateUpdates: PlayBackStateUpdates<PinyinAudio>? = null
    private var pinyinAudioPlaying = false

    override fun attach(context: Context) {
        playBackStateUpdates?.register(context)
    }

    override fun detach(context: Context) {
        playBackStateUpdates?.unregister(context)
    }

    override fun playPinyinAudio(src: String, context: Context) {

        if (!pinyinAudioPlaying) {

            val pinyinAudio = PinyinAudio(src)

            playBackStateUpdates = PlayBackStateUpdates(pinyinAudio)

            playBackStateUpdates!!.playBackStateChanges().subscribe {
                pinyinAudioPlaying = isPlaying(it)
            }

            AudioStreamController(
                pinyinAudio,
                PinyinAudioIntent(),
                NotificationInfo("", "", null),
                PinyinStreamingService::class.java,
                context
            ).play()
        }
    }

    private fun isPlaying(state: PlayBackState): Boolean = when (state) {
        PlayBackState.Play -> true
        PlayBackState.Completed -> false
        PlayBackState.Buffering -> true
        PlayBackState.Pause -> false
        PlayBackState.Stop -> false
        is PlayBackState.Progress -> false
        is PlayBackState.BufferingError -> false
    }
}