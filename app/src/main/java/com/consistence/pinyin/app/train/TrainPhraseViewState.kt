package com.consistence.pinyin.app.train

import com.consistence.pinyin.domain.pinyin.Pinyin
import com.consistence.pinyin.domain.study.Study
import com.memtrip.mxandroid.MxViewState

data class TrainPhraseViewState(val view: View) : MxViewState {
    sealed class View : MxViewState {
        object Idle : View()
        data class ChineseQuestion(
            val chineseQuestion: List<Pinyin>
        ) : View()
        data class EnglishQuestion(
            val englishQuestion: String
        ) : View()
        data class Correct(
            val study: Study
        ) : View()
        data class IncorrectEnglish(
            val englishTranslation: String,
            val answer: Study
        ) : View()
        data class IncorrectChinese(
            val chineseTranslation: String,
            val answer: Study
        ) : View()
    }
}