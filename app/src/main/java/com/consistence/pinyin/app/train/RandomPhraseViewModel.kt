package com.consistence.pinyin.app.train

import android.app.Application
import com.consistence.pinyin.domain.pinyin.formatChineseCharacterString
import com.consistence.pinyin.domain.study.Study
import com.memtrip.mxandroid.MxViewModel
import io.reactivex.Observable
import javax.inject.Inject

class RandomPhraseViewModel @Inject internal constructor(
    application: Application
) : MxViewModel<RandomPhraseIntent, RandomPhraseRenderAction, RandomPhraseViewState>(
    RandomPhraseViewState(RandomPhraseViewState.View.Idle),
    application
) {
    override fun dispatcher(intent: RandomPhraseIntent): Observable<RandomPhraseRenderAction> = when (intent) {
        RandomPhraseIntent.Init -> TODO()
        is RandomPhraseIntent.Result -> TODO()
    }

    override fun reducer(previousState: RandomPhraseViewState, renderAction: RandomPhraseRenderAction): RandomPhraseViewState = when (renderAction) {
        is RandomPhraseRenderAction.PickRandomPhrases -> previousState.copy(
            study = renderAction.study,
            view = RandomPhraseViewState.View.Next(renderAction.study[0])
        )
        is RandomPhraseRenderAction.Result -> previousState.copy(
            results = previousState.results.plus(
                Pair(previousState.study[previousState.currentPosition], renderAction.correct)),
            currentPosition = previousState.currentPosition.inc(),
            view = RandomPhraseViewState.View.Next(previousState.study[previousState.currentPosition.inc()])
        )
    }

    override fun filterIntents(intents: Observable<RandomPhraseIntent>): Observable<RandomPhraseIntent> = Observable.merge(
        intents.ofType(RandomPhraseIntent.Init::class.java).take(1),
        intents.filter {
            !RandomPhraseIntent.Init::class.java.isInstance(it)
        }
    )
}