package com.consistence.pinyin.app.list

import com.consistence.pinyin.*

import com.consistence.pinyin.api.PinyinEntity
import com.consistence.pinyin.app.PinyinView

import com.consistence.pinyin.audio.PlayPinyAudioInPresenter
import com.consistence.pinyin.audio.PlayPinyinAudio
import com.consistence.pinyin.legacy.*

import io.reactivex.functions.Consumer

abstract class PinyinListPresenter<V : PinyinListView> : Presenter<V>() {

    val pinyinAudio: PlayPinyinAudio = PlayPinyAudioInPresenter()

    abstract fun search(terms: String = defaultSearch)

    abstract val defaultSearch: String

    override fun first() {
        super.first()
        search()
    }

    override fun start() {
        super.start()

        pinyinAudio.attach(view.context())

        searchWith((view.context() as PinyinView).currentSearchQuery)
    }

    override fun stop() {
        super.stop()

        pinyinAudio.detach(view.context())
    }

    private fun searchWith(terms: String) {
        if (terms.isEmpty()) {
            search()
        } else {
            search(terms)
        }
    }

    override fun event(): Consumer<Event> = Consumer {
        when (it) {
            is SearchEvent ->
                searchWith(it.terms.toString())
        }
    }

    internal fun adapterEvent(): Consumer<AdapterEvent<PinyinEntity>> {
        return Consumer {
            when (it) {
                is AdapterClick -> {
                    if (it.id == R.id.pinyin_list_audio_button) {
                        it.value.audioSrc?.let {
                            pinyinAudio.playPinyinAudio(it, view.context())
                        }
                    } else {
                        view.navigateToPinyinDetails(it.value)
                    }
                }
            }
        }
    }
}