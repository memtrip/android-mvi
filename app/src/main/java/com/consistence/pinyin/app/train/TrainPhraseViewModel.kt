package com.consistence.pinyin.app.train

import android.app.Application
import com.consistence.pinyin.domain.pinyin.FetchAndSavePinyin
import com.consistence.pinyin.domain.pinyin.db.CountPinyin
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
        is TrainPhraseIntent.AnswerEnglish -> TODO()
        is TrainPhraseIntent.AnswerChinese -> TODO()
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
        is TrainPhraseRenderAction.Incorrect -> previousState.copy(
            view = TrainPhraseViewState.View.Incorrect(renderAction.entered, renderAction.answer)
        )
    }

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
}