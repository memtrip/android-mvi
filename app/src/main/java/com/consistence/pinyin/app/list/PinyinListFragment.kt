package com.consistence.pinyin.app.list

import android.os.Bundle
import com.consistence.pinyin.MxViewFragment
import com.consistence.pinyin.api.PinyinEntity
import com.consistence.pinyin.app.PinyinLayout
import com.consistence.pinyin.app.detail.PinyinDetailActivity
import com.consistence.pinyin.audio.PlayPinyAudioInPresenter
import io.reactivex.Observable
import javax.inject.Inject

abstract class PinyinListFragment
    : MxViewFragment<PinyinListIntent, PinyinListRenderAction, PinyinListViewState, PinyinListLayout>(), PinyinListLayout {

    @Inject lateinit var render: PinyinListRenderer

    private val pinyinAudio = PlayPinyAudioInPresenter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model().publish(PinyinListIntent.Search((context as PinyinLayout).currentSearchQuery))
    }

    override fun onStart() {
        super.onStart()
        context?.let { pinyinAudio.attach(it) }
    }

    override fun onStop() {
        super.onStop()
        context?.let { pinyinAudio.detach(it) }
    }

    override fun intents(): Observable<PinyinListIntent> =
            Observable.just(PinyinListIntent.Init((context as PinyinLayout).currentSearchQuery))

    override fun render(): PinyinListRenderer = render

    override fun navigateToPinyinDetails(pinyinEntity: PinyinEntity) {
        model().publish(PinyinListIntent.Idle)
        startActivity(PinyinDetailActivity.newIntent(context!!, pinyinEntity))
    }

    override fun playAudio(audioSrc: String) {
        context?.let { pinyinAudio.playPinyinAudio(audioSrc, it) }
    }
}