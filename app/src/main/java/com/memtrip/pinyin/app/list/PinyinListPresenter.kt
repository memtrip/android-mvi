package com.memtrip.pinyin.app.list

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import com.memtrip.pinyin.*
import com.memtrip.pinyin.api.PinyinEntity
import com.memtrip.pinyin.api.SearchPinyin
import com.memtrip.pinyin.audio.PinyinAudio
import com.memtrip.pinyin.audio.PinyinStreamingNavigator
import com.memtrip.pinyin.audio.stream.Notify
import io.reactivex.functions.Consumer
import javax.inject.Inject

class PinyinListPresenter @Inject internal constructor(val searchPinyin: SearchPinyin) : Presenter<PinyinListView>() {

    val pinyinStream = PinyinStreamingNavigator()

    var pinyinAudioPlaying = false

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

    override fun first() {
        super.first()
        search("pinyin")
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

    fun search(terms: String = "") {
        i(searchPinyin.search(terms, Consumer {
            view.populate(it)
        }, Consumer {
            view.error()
        }))
    }

    override fun event(): Consumer<Event> = Consumer {
        when (it) {
            is SearchEvent -> search(it.terms)
        }
    }

    private fun playPinyinAudio(src: String) {
        if (!pinyinAudioPlaying) {
            pinyinStream.play(PinyinAudio(src), view.context())
        }
    }

    internal fun adapterEvent(): Consumer<AdapterEvent<PinyinEntity>> {
        return Consumer {
            when (it) {
                is AdapterClick -> {
                    if (it.id == R.id.pinyin_list_item_audio_button) {
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