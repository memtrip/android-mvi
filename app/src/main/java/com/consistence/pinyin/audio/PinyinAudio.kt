package com.consistence.pinyin.audio

import android.content.Context
import android.content.Intent
import com.consistence.pinyin.audio.stream.*

class PinyinAudio(private val audioUrl: String) : Stream {

    override fun streamUrl(): String = audioUrl
}

class PinyinStreamingNavigator : StreamingNavigator<PinyinAudio>(
        PinyinStreamIntent(),
        PinyinStreamingService::class.java)

class PinyinStreamingService : StreamingService<PinyinAudio>() {

    override fun streamIntent(): StreamIntent<PinyinAudio> {
        return PinyinStreamIntent()
    }

    override fun createPlayer(audioStreamUrl: String,
                              onPlayerStateListener: OnPlayerStateListener,
                              context: Context): Player {
        return Player(audioStreamUrl, onPlayerStateListener, context)
    }
}

class PinyinStreamIntent : StreamIntent<PinyinAudio> {

    override fun into(pinyinAudio: PinyinAudio, intent: Intent) {
        intent.putExtra(STREAM_URL, pinyinAudio.streamUrl())
    }

    override fun get(intent: Intent): PinyinAudio {
        return PinyinAudio(intent.getStringExtra(STREAM_URL))
    }

    companion object {
        private val STREAM_URL = "pinyin.streamUrl"
    }
}