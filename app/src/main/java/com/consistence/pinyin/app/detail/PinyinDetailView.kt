package com.consistence.pinyin.app.detail

import com.consistence.pinyin.ViewIntent
import com.consistence.pinyin.ViewLayout
import com.consistence.pinyin.ViewRender
import com.consistence.pinyin.ViewState
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Inject

sealed class PinyinDetailIntent : ViewIntent {
    object Init: PinyinDetailIntent()
    object PlayAudio: PinyinDetailIntent()
}

sealed class PinyinDetailState : ViewState {
    data class Populate(val phoneticScriptText: String,
                        val englishTranslationText: String,
                        val chineseCharacters: String,
                        val audioSrc: String?) : PinyinDetailState()
    data class PlayAudio(val audioSrc: String) : PinyinDetailState()
}

interface PinyinDetailLayout : ViewLayout {
    fun populate(phoneticScriptText: String,
                 englishTranslationText: String,
                 chineseCharacters: String)
    fun showAudioControl()
    fun playAudio(audioSrc: String)
}

class PinyinDetailRender @Inject internal constructor(): ViewRender<PinyinDetailLayout, PinyinDetailState> {
    override fun state(layout: PinyinDetailLayout, state: PinyinDetailState) = when(state) {
        is PinyinDetailState.Populate -> {
            state.audioSrc?.let {
                layout.showAudioControl()
            }
            layout.populate(
                    state.phoneticScriptText,
                    state.englishTranslationText,
                    state.chineseCharacters)
        }
        is PinyinDetailState.PlayAudio -> {
            layout.playAudio(state.audioSrc)
        }
    }
}

@Module
abstract class PinyinDetailActivityModule {

    @ContributesAndroidInjector(modules = [PinyinParcelModule::class])
    internal abstract fun contributesPinyinDetailActivity() : PinyinDetailActivity
}

@Module
class PinyinParcelModule {

    @Provides
    fun bindPinyinParcel(activity: PinyinDetailActivity) = PinyinParcel.out(activity.intent)
}