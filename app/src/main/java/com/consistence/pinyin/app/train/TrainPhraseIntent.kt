package com.consistence.pinyin.app.train

import com.consistence.pinyin.domain.study.Study
import com.memtrip.mxandroid.MxViewIntent

sealed class TrainPhraseIntent : MxViewIntent {
    data class Init(val study: Study) : TrainPhraseIntent()
    data class AnswerEnglish(
        val englishTranslation: String
    ) : TrainPhraseIntent()
    data class AnswerChinese(
        val chineseTranslation: String
    ) : TrainPhraseIntent()
}