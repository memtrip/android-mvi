package com.consistence.pinyin.audio.stream

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager

class Notify internal constructor(private val context: Context) {

    enum class NotifyType {
        PLAYING,
        COMPLETED
    }

    internal fun startBuffering() {
        val intent = notifyIntent()
        intent.putExtra(EXTRA_NOTIFY_TYPE, NotifyType.PLAYING)
        sendBroadcast(intent)
    }

    internal fun bufferingError() {
        val intent = notifyIntent()
        intent.putExtra(EXTRA_NOTIFY_TYPE, NotifyType.COMPLETED)
        sendBroadcast(intent)
    }

    internal fun play() {
        val intent = notifyIntent()
        intent.putExtra(EXTRA_NOTIFY_TYPE, NotifyType.PLAYING)
        sendBroadcast(intent)
    }

    internal fun completed() {
        val intent = notifyIntent()
        intent.putExtra(EXTRA_NOTIFY_TYPE, NotifyType.COMPLETED)
        sendBroadcast(intent)
    }

    private fun sendBroadcast(intent: Intent) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    private fun notifyIntent(): Intent {
        val intent = Intent()
        intent.action = ACTION_STREAM_NOTIFY
        return intent
    }

    companion object {

        private const val ACTION_STREAM_NOTIFY = "ACTION_STREAM_NOTIFY"

        private const val EXTRA_NOTIFY_TYPE = "EXTRA_NOTIFY_TYPE"

        val intentFilter = IntentFilter(Notify.ACTION_STREAM_NOTIFY)

        fun getNotifyType(intent: Intent) =
                intent.getSerializableExtra(EXTRA_NOTIFY_TYPE) as NotifyType
    }
}