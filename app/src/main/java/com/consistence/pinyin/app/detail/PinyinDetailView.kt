package com.consistence.pinyin.app.detail

import com.consistence.pinyin.ViewIntent
import com.consistence.pinyin.ViewLayout
import com.consistence.pinyin.ViewRender
import com.consistence.pinyin.ViewState
import dagger.Module
import dagger.android.ContributesAndroidInjector

sealed class PinyinDetailIntent : ViewIntent {
    object Init: PinyinDetailIntent()
    object PlayAudio: PinyinDetailIntent()
}

sealed class PinyinDetailState : ViewState {
    data class Populate(val pinyinParcel: PinyinParcel) : PinyinDetailState()
    data class PlayAudio(val audioSrc: String) : PinyinDetailState()
}

interface PinyinDetailLayout : ViewLayout {
    fun populate(pinyinParcel: PinyinParcel)
    fun showAudioControl()
    fun playAudio(audioSrc: String)
}

class PinyinDetailRender(private val layout: PinyinDetailLayout) : ViewRender<PinyinDetailState> {
    override fun state(state: PinyinDetailState) = when(state) {
        is PinyinDetailState.Populate -> {
            state.pinyinParcel.audioSrc?.let {
                layout.showAudioControl()
            }
            layout.populate(state.pinyinParcel)
        }
        is PinyinDetailState.PlayAudio -> {
            layout.playAudio(state.audioSrc)
        }
    }
}

@Module
abstract class PinyinDetailActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributesPinyinDetailActivity() : PinyinDetailActivity
}