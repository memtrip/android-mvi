package com.consistence.pinyin.app.study

import com.consistence.pinyin.domain.pinyin.Pinyin
import com.memtrip.mxandroid.MxViewState

data class CreateStudyViewState(
    val view: View,
    val step: Step = Step.ENGLISH_TRANSLATION,
    val englishTranslation: String = "",
    val pinyin: List<Pinyin> = listOf()
) : MxViewState {
    sealed class View : MxViewState {
        object Idle : View()
        data class EnterEnglishTranslation(
            val englishTranslation: String = ""
        ) : View()
        data class EnterChinesePhrase(
            val pinyin: List<Pinyin> = listOf()
        ) : View()
        object ConfirmPhrase : View()
        object Exit : View()
        object LoseChanges : View()
    }

    enum class Step {
        ENGLISH_TRANSLATION,
        CHINESE_PHRASE,
        CONFIRM
    }
}