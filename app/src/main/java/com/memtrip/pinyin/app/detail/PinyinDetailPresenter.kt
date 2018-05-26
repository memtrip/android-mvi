package com.memtrip.pinyin.app.detail

import com.memtrip.pinyin.Event
import com.memtrip.pinyin.Presenter
import com.memtrip.pinyin.R
import com.memtrip.pinyin.audio.PlayPinyAudioInPresenter
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