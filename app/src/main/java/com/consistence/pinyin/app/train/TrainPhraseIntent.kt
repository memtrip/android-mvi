package com.consistence.pinyin.app.train

import com.consistence.pinyin.domain.study.Study
import com.memtrip.mxandroid.MxViewIntent

sealed class TrainPhraseIntent : MxViewIntent {
    data class Init(val study: Study) : TrainPhraseIntent()
    data class AnswerEnglishToChinese(
        val translation: String,
        val study: Study
    ) : TrainPhraseIntent()
    data class AnswerChineseToEnglish(
        val translation: String,
        val study: Study
    ) : TrainPhraseIntent()
}