package com.consistence.pinyin.audio.stream

import android.os.Handler

internal class NotifyOnPlayerStateListener(
        private val notify: Notify,
        private val mainThreadHandler: Handler) : OnPlayerStateListener {

    override fun startBuffering() {
        mainThreadHandler.post { notify.startBuffering() }
    }

    override fun onBufferingError() {
        mainThreadHandler.post { notify.bufferingError() }
    }

    override fun onPlay() {
        mainThreadHandler.post { notify.play() }
    }

    override fun onCompleted() {
        mainThreadHandler.post { notify.completed() }
    }
}