package com.consistence.pinyin.app.train

import com.consistence.pinyin.domain.pinyin.Pinyin
import com.consistence.pinyin.domain.study.Study
import com.memtrip.mxandroid.MxRenderAction
import com.memtrip.mxandroid.MxViewLayout
import com.memtrip.mxandroid.MxViewRenderer
import javax.inject.Inject

sealed class TrainPhraseRenderAction : MxRenderAction {
    data class EnglishQuestion(
        val englishQuestion: String
    ) : TrainPhraseRenderAction()
    data class ChineseQuestion(
        val chineseQuestion: List<Pinyin>
    ) : TrainPhraseRenderAction()
    data class Correct(
        val study: Study
    ) : TrainPhraseRenderAction()
    data class IncorrectEnglish(
        val englishTranslation: String,
        val answer: Study
    ) : TrainPhraseRenderAction()
    data class IncorrectChinese(
        val chineseTranslation: String,
        val answer: Study
    ) : TrainPhraseRenderAction()
}

interface TrainPhraseLayout : MxViewLayout {
    fun englishQuestion(englishTranslation: String)
    fun chineseQuestion(chineseQuestion: List<Pinyin>)
    fun correct(study: Study)
    fun incorrectEnglish(englishTranslation: String, answer: Study)
    fun incorrectChinese(chineseTranslation: String, answer: Study)
}

class TrainPhraseRenderer @Inject internal constructor() : MxViewRenderer<TrainPhraseLayout, TrainPhraseViewState> {
    override fun layout(layout: TrainPhraseLayout, state: TrainPhraseViewState) = when (state.view) {
        TrainPhraseViewState.View.Idle -> {
        }
        is TrainPhraseViewState.View.ChineseQuestion -> {
            layout.chineseQuestion(state.view.chineseQuestion)
        }
        is TrainPhraseViewState.View.EnglishQuestion -> {
            layout.englishQuestion(state.view.englishQuestion)
        }
        is TrainPhraseViewState.View.Correct -> {
            layout.correct(state.view.study)
        }
        is TrainPhraseViewState.View.IncorrectEnglish -> {
            layout.incorrectEnglish(state.view.englishTranslation, state.view.answer)
        }
        is TrainPhraseViewState.View.IncorrectChinese -> {
            layout.incorrectChinese(state.view.chineseTranslation, state.view.answer)
        }
    }
}