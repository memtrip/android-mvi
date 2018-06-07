package com.consistence.pinyin.app.detail

import com.consistence.pinyin.legacy.Event
import com.consistence.pinyin.legacy.Presenter
import com.consistence.pinyin.R
import com.consistence.pinyin.audio.PlayPinyAudioInPresenter
import io.reactivex.functions.Consumer
import javax.inject.Inject

class PinyinDetailPresenter @Inject internal constructor(
        private val pinyinParcel: PinyinParcel) : Presenter<PinyinDetailView>() {

    val pinyinAudio = PlayPinyAudioInPresenter()

    override fun first() {
        super.first()

        view.populate(pinyinParcel)

        pinyinParcel.audioSrc?.let {
            view.showAudioControl()
        }
    }

    override fun start() {
        super.start()

        pinyinAudio.attach(view.context())
    }

    override fun stop() {
        super.stop()

        pinyinAudio.detach(view.context())
    }

    override fun event(): Consumer<Event> {
        return Consumer {
            when(it.id) {
                R.id.pinyin_detail_activity_audio_button ->
                    pinyinAudio.playPinyinAudio(pinyinParcel.audioSrc!!, view.context())
            }
        }
    }
}