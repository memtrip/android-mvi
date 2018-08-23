package com.consistence.pinyin.audio

import android.content.Intent
import com.consistence.pinyin.BuildConfig

import com.memtrip.exoeasy.AudioResource
import com.memtrip.exoeasy.AudioResourceIntent

import com.memtrip.exoeasy.notification.NotificationConfig

import com.memtrip.exoeasy.player.StreamingService

data class PinyinAudio(
    override val url: String,
    override val userAgent: String = "${BuildConfig.VERSION_NAME}/${BuildConfig.VERSION_CODE}",
    override val trackProgress: Boolean = false
) : AudioResource

class PinyinAudioIntent : AudioResourceIntent<PinyinAudio>() {

    override fun get(intent: Intent): PinyinAudio {
        return PinyinAudio(
            intent.getStringExtra(HTTP_AUDIO_STREAM_URL),
            intent.getStringExtra(HTTP_AUDIO_STREAM_USER_AGENT),
            intent.getBooleanExtra(HTTP_AUDIO_STREAM_TRACK_PROGRESS, false)
        )
    }
}

class PinyinStreamingService : StreamingService<PinyinAudio>() {

    override fun audioResourceIntent(): PinyinAudioIntent = PinyinAudioIntent()
}