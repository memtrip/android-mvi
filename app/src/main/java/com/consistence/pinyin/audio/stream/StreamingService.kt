package com.consistence.pinyin.audio.stream

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.consistence.pinyin.audio.stream.interrupt.AudioFocusInterrupt
import com.consistence.pinyin.audio.stream.interrupt.BecomingNoisyInterrupt
import timber.log.Timber

abstract class StreamingService<T : Stream> : Service() {

    private lateinit var player: Player
    private var becomingNoisyInterrupt: BecomingNoisyInterrupt? = null
    private var audioFocusInterrupt: AudioFocusInterrupt? = null

    protected abstract fun streamIntent(): StreamIntent<T>

    protected abstract fun createPlayer(audioStreamUrl: String,
                                        onPlayerStateListener: OnPlayerStateListener,
                                        context: Context): Player

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        Timber.d(">> Streaming service onStartCommand: " + this + "" +
                "\n data: " + intent)

        val stream = streamIntent().get(intent)

        start(stream, Notify(this))

        setupInterruptReceiver()

        return Service.START_NOT_STICKY
    }

    private fun setupInterruptReceiver() {
        becomingNoisyInterrupt = BecomingNoisyInterrupt({ player.pause() }, application)
        becomingNoisyInterrupt!!.register()

        audioFocusInterrupt = AudioFocusInterrupt({ player.pause() }, application)
        audioFocusInterrupt!!.attach()
    }

    private fun start(stream: T, notify: Notify) {

        Timber.d(">> Start with stream: %s", stream)

        player = createPlayer(
                stream.streamUrl(),
                NotifyOnPlayerStateListener(notify, Handler(Looper.getMainLooper())),
                this)

        player.prepare()
    }

    override fun onDestroy() {
        super.onDestroy()
        becomingNoisyInterrupt?.unregister()
        audioFocusInterrupt?.remove()
        player.release()
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        player.release()
        stopSelf()
    }

    override fun onBind(intent: Intent)= null
}