package com.consistence.pinyin.app.study

import com.consistence.pinyin.domain.pinyin.Pinyin
import com.memtrip.mxandroid.MxViewState

data class CreateStudyViewState(
    val view: View,
    val step: Step = Step.INITIAL,
    val englishTranslation: String = "",
    val pinyin: List<Pinyin> = listOf()
) : MxViewState {
    sealed class View : MxViewState {
        object Idle : View()
        object EnglishTranslationForm : View()
        object ChinesePhraseForm : View()
        object ConfirmPhrase : View()
        object Exit : View()
        object LoseChangesConfirmation : View()
        object Success : View()
    }

    enum class Step {
        INITIAL,
        ENGLISH_TRANSLATION,
        CHINESE_PHRASE,
        CONFIRM
    }
}