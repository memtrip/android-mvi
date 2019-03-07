package com.consistence.pinyin.app.pinyin.detail

import com.memtrip.mxandroid.MxViewState

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