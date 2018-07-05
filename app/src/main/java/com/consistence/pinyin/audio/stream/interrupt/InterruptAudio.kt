package com.consistence.pinyin.audio.stream.interrupt

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT

typealias InterruptAudio = () -> Unit

class BecomingNoisyInterrupt constructor(
    private val interruptAudio: InterruptAudio,
    private val context: Context,
    private val intentFilter: IntentFilter = IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY)
) : BroadcastReceiver() {

    fun register() {
        context.registerReceiver(this, intentFilter)
    }

    fun unregister() {
        context.unregisterReceiver(this)
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (AudioManager.ACTION_AUDIO_BECOMING_NOISY == intent.action) {
            interruptAudio()
        }
    }
}

@Suppress("DEPRECATION")
class AudioFocusInterrupt constructor(
    private val interruptAudio: InterruptAudio,
    context: Context,
    private val audioManager: AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
) : AudioManager.OnAudioFocusChangeListener {

    fun attach() {
        audioManager.requestAudioFocus(this,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN)
    }

    fun remove() {
        audioManager.abandonAudioFocus(this)
    }

    override fun onAudioFocusChange(focusChange: Int) {
        if (focusChange == AudioManager.AUDIOFOCUS_LOSS || focusChange == AUDIOFOCUS_LOSS_TRANSIENT) {
            interruptAudio()
        }
    }
}