package com.consistence.pinyin.app.detail

import com.consistence.pinyin.MxViewState

data class PinyinDetailViewState(
    val phoneticScriptText: String,
    val englishTranslationText: String,
    val chineseCharacters: String,
    val audioSrc: String? = null,
    val action: Action = Action.None
) : MxViewState {

    sealed class Action {
        object None : Action()
        data class PlayAudio(val audioSrc: String) : Action()
    }
}