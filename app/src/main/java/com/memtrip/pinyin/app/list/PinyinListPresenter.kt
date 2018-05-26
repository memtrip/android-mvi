package com.memtrip.pinyin.app.list

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import com.memtrip.pinyin.*

import com.memtrip.pinyin.api.PinyinEntity

import com.memtrip.pinyin.audio.PinyinAudio
import com.memtrip.pinyin.audio.PinyinStreamingNavigator
import com.memtrip.pinyin.audio.stream.Notify
import io.reactivex.functions.Consumer

abstract class PinyinListPresenter<V : PinyinListView> : Presenter<V>() {

    val pinyinStream = PinyinStreamingNavigator()

    var pinyinAudioPlaying = false

    abstract fun search(terms: String = defaultSearch)

    abstract val defaultSearch: String

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val type = Notify.getNotifyType(intent)

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

    override fun start() {
        super.start()

        LocalBroadcastManager
                .getInstance(view.context())
                .registerReceiver(broadcastReceiver, Notify.getIntentFilter())
    }

    override fun stop() {
        super.stop()

        LocalBroadcastManager
                .getInstance(view.context())
                .unregisterReceiver(broadcastReceiver)
    }

    override fun event(): Consumer<Event> = Consumer {
        when (it) {
            is SearchEvent -> {
                if (it.terms.isEmpty()) {
                    search()
                } else {
                    search(it.terms)
                }
            }
        }
    }

    protected fun playPinyinAudio(src: String) {
        if (!pinyinAudioPlaying) {
            pinyinStream.play(PinyinAudio(src), view.context())
        }
    }

    internal fun adapterEvent(): Consumer<AdapterEvent<PinyinEntity>> {
        return Consumer {
            when (it) {
                is AdapterClick -> {
                    if (it.id == R.id.pinyin_list_audio_button) {
                        it.value.audioSrc?.let {
                            playPinyinAudio(it)
                        }
                    } else {
                        view.navigateToPinyinDetails(it.value)
                    }
                }
            }
        }
    }
}