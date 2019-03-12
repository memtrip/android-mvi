package com.consistence.pinyin.app.train

import android.app.Application
import com.consistence.pinyin.domain.pinyin.formatChineseCharacterString
import com.consistence.pinyin.domain.study.Study
import com.memtrip.mxandroid.MxViewModel
import io.reactivex.Observable
import javax.inject.Inject

class TrainPhraseViewModel @Inject internal constructor(
    application: Application
) : MxViewModel<TrainPhraseIntent, TrainPhraseRenderAction, TrainPhraseViewState>(
    TrainPhraseViewState(TrainPhraseViewState.View.Idle),
    application
) {
    override fun dispatcher(intent: TrainPhraseIntent): Observable<TrainPhraseRenderAction> = when (intent) {
        is TrainPhraseIntent.Init -> pickQuestion(intent.study)
        is TrainPhraseIntent.AnswerEnglishToChinese -> checkChineseAnswer(intent.translation, intent.study)
        is TrainPhraseIntent.AnswerChineseToEnglish -> checkEnglishAnswer(intent.translation, intent.study)
    }

    override fun reducer(previousState: TrainPhraseViewState, renderAction: TrainPhraseRenderAction): TrainPhraseViewState = when (renderAction) {
        is TrainPhraseRenderAction.EnglishQuestion -> previousState.copy(
            view = TrainPhraseViewState.View.EnglishQuestion(renderAction.englishQuestion)
        )
        is TrainPhraseRenderAction.ChineseQuestion -> previousState.copy(
            view = TrainPhraseViewState.View.ChineseQuestion(renderAction.chineseQuestion)
        )
        is TrainPhraseRenderAction.Correct -> previousState.copy(
            view = TrainPhraseViewState.View.Correct(renderAction.study)
        )
        is TrainPhraseRenderAction.IncorrectEnglish -> previousState.copy(
            view = TrainPhraseViewState.View.IncorrectEnglish(
                renderAction.englishTranslation, renderAction.answer)
        )
        is TrainPhraseRenderAction.IncorrectChinese -> previousState.copy(
            view = TrainPhraseViewState.View.IncorrectChinese(
                renderAction.chineseTranslation, renderAction.answer)
        )
    }

    override fun filterIntents(intents: Observable<TrainPhraseIntent>): Observable<TrainPhraseIntent> = Observable.merge(
        intents.ofType(TrainPhraseIntent.Init::class.java).take(1),
        intents.filter {
            !TrainPhraseIntent.Init::class.java.isInstance(it)
        }
    )

    private fun pickQuestion(study: Study): Observable<TrainPhraseRenderAction> {
        return Observable.just(if (useChinese()) {
            TrainPhraseRenderAction.ChineseQuestion(study.pinyin)
        } else {
            TrainPhraseRenderAction.EnglishQuestion(study.englishTranslation)
        })
    }

    private fun useChinese(): Boolean {
        return (Math.random() * 50 + 1).toInt() % 2 == 0
    }

    private fun checkEnglishAnswer(
        englishTranslation: String,
        study: Study
    ): Observable<TrainPhraseRenderAction> {
        return Observable.just(
            if (englishTranslation.toLowerCase().trim() == study.englishTranslation.toLowerCase().trim()) {
                TrainPhraseRenderAction.Correct(study)
            } else {
                TrainPhraseRenderAction.IncorrectEnglish(englishTranslation, study)
            }
        )
    }

    private fun checkChineseAnswer(
        chineseTranslation: String,
        study: Study
    ): Observable<TrainPhraseRenderAction> {
        return Observable.just(
            if (chineseTranslation == study.pinyin.formatChineseCharacterString()) {
                TrainPhraseRenderAction.Correct(study)
            } else {
                TrainPhraseRenderAction.IncorrectChinese(
                    chineseTranslation,
                    study)
            }
        )
    }
}