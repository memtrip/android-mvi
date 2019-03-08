package com.consistence.pinyin.app.pinyin.list

import android.os.Bundle
import com.consistence.pinyin.app.pinyin.detail.PinyinDetailActivity
import com.consistence.pinyin.audio.PlayPinyAudioInPresenter
import com.consistence.pinyin.domain.pinyin.Pinyin
import com.memtrip.mxandroid.MxViewFragment
import io.reactivex.Observable
import javax.inject.Inject

abstract class PinyinListFragment
    : MxViewFragment<PinyinListIntent, PinyinListRenderAction, PinyinListViewState, PinyinListLayout>(), PinyinListLayout {

    @Inject lateinit var render: PinyinListRenderer

    private val pinyinAudio = PlayPinyAudioInPresenter()

    internal val delegate: PinyinListDelegate by lazy {
        (context as PinyinListDelegate)
    }

    interface PinyinListDelegate {
        var currentSearchQuery: String
        val consumeSelection: Boolean
        val fullListStyle: Boolean
        fun pinyinSelection(pinyin: Pinyin)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model().publish(PinyinListIntent.Search(delegate.currentSearchQuery))
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
        Observable.just(PinyinListIntent.Init(delegate.currentSearchQuery))

    override fun render(): PinyinListRenderer = render

    override fun pinyinItemSelected(pinyin: Pinyin) {
        if (delegate.consumeSelection) {
            delegate.pinyinSelection(pinyin)
        } else {
            model().publish(PinyinListIntent.Idle)
            startActivity(PinyinDetailActivity.newIntent(context!!, pinyin))
        }
    }

    override fun playAudio(audioSrc: String) {
        context?.let { pinyinAudio.playPinyinAudio(audioSrc, it) }
    }
}