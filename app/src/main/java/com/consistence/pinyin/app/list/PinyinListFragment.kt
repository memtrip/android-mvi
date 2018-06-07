package com.consistence.pinyin.app.list

import com.consistence.pinyin.ViewFragment
import com.consistence.pinyin.api.PinyinEntity
import com.consistence.pinyin.app.PinyinLayout
import com.consistence.pinyin.app.detail.PinyinDetailActivity
import com.consistence.pinyin.audio.PlayPinyAudioInPresenter

abstract class PinyinListFragment
    : ViewFragment<PinyinListIntent, PinyinListState, PinyinListModel, PinyinListRender>(), PinyinListLayout {

    private val pinyinAudio = PlayPinyAudioInPresenter()

    override fun onStart() {
        super.onStart()
        context?.let { pinyinAudio.attach(it) }
    }

    override fun onStop() {
        super.onStop()
        context?.let { pinyinAudio.detach(it) }
    }

    override fun render() = lazy {
        PinyinListRender(this)
    }.value

    override fun initIntent(): PinyinListIntent {
        return PinyinListIntent.Search((context as PinyinLayout).currentSearchQuery)
    }

    override fun navigateToPinyinDetails(pinyinEntity: PinyinEntity) {
        startActivity(PinyinDetailActivity.newIntent(context!!, pinyinEntity))
    }

    override fun playAudio(audioSrc: String) {
        context?.let { pinyinAudio.playPinyinAudio(audioSrc, it) }
    }
}