package com.consistence.pinyin.app.detail

import com.consistence.pinyin.MxRenderAction
import com.consistence.pinyin.MxViewLayout
import com.consistence.pinyin.MxViewRenderer
import javax.inject.Inject

sealed class PinyinDetailRenderAction : MxRenderAction {
    object Idle : PinyinDetailRenderAction()
    data class PlayAudio(val audioSrc: String) : PinyinDetailRenderAction()
}

interface PinyinDetailLayout : MxViewLayout {
    fun populate(
        phoneticScriptText: String,
        englishTranslationText: String,
        chineseCharacters: String
    )
    fun showAudioControl()
    fun playAudio(audioSrc: String)
}

class PinyinDetailRenderer @Inject internal constructor() : MxViewRenderer<PinyinDetailLayout, PinyinDetailViewState> {

    override fun layout(layout: PinyinDetailLayout, state: PinyinDetailViewState) {

        layout.populate(
                state.phoneticScriptText,
                state.englishTranslationText,
                state.chineseCharacters)

        if (state.audioSrc != null) {
            layout.showAudioControl()
        }

        renderAction(layout, state.action)
    }

    private fun renderAction(layout: PinyinDetailLayout, action: PinyinDetailViewState.Action): Unit = when (action) {
        PinyinDetailViewState.Action.None -> {
        }
        is PinyinDetailViewState.Action.PlayAudio -> {
            layout.playAudio(audioSrc = action.audioSrc)
        }
    }
}