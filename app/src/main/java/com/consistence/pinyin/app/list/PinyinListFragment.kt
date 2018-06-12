package com.consistence.pinyin.app.list

import android.os.Bundle
import com.consistence.pinyin.ViewFragment
import com.consistence.pinyin.api.PinyinEntity
import com.consistence.pinyin.app.PinyinLayout
import com.consistence.pinyin.app.detail.PinyinDetailActivity
import com.consistence.pinyin.audio.PlayPinyAudioInPresenter
import javax.inject.Inject

abstract class PinyinListFragment
    : ViewFragment<PinyinListIntent, PinyinListState, PinyinListLayout>(), PinyinListLayout {

    @Inject lateinit var render: PinyinListRender

    private val pinyinAudio = PlayPinyAudioInPresenter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model().intents.onNext(PinyinListIntent.Search((context as PinyinLayout).currentSearchQuery))
    }

    override fun onStart() {
        super.onStart()
        context?.let { pinyinAudio.attach(it) }
    }

    override fun onStop() {
        super.onStop()
        context?.let { pinyinAudio.detach(it) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currentSearchQuery", (context as PinyinLayout).currentSearchQuery)
    }

    override fun render(): PinyinListRender = render

    override fun initIntent() =
            PinyinListIntent.Search((context as PinyinLayout).currentSearchQuery)

    override fun restoreStateIntent(savedInstanceState: Bundle) =
            PinyinListIntent.Search(savedInstanceState.getString("currentSearchQuery"))

    override fun navigateToPinyinDetails(pinyinEntity: PinyinEntity) {
        startActivity(PinyinDetailActivity.newIntent(context!!, pinyinEntity))
    }

    override fun playAudio(audioSrc: String) {
        context?.let { pinyinAudio.playPinyinAudio(audioSrc, it) }
    }
}