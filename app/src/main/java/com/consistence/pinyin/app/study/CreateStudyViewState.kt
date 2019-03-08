package com.consistence.pinyin.app.study

import com.consistence.pinyin.domain.pinyin.Pinyin
import com.memtrip.mxandroid.MxViewState
import com.memtrip.mxandroid.MxViewState.Companion.id

data class CreateStudyViewState(
    val view: View,
    val step: Step = Step.INITIAL,
    val englishTranslation: String = "",
    val pinyin: MutableList<Pinyin> = mutableListOf()
) : MxViewState {
    sealed class View : MxViewState {
        object Idle : View()
        object EnglishTranslationForm : View()
        data class ChinesePhraseForm(val unique: Int = id()) : View()
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