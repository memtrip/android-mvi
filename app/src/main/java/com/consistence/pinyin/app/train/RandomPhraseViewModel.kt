package com.consistence.pinyin.app.train

import android.app.Application
import com.consistence.pinyin.domain.study.GetRandomStudy
import com.memtrip.mxandroid.MxViewModel
import io.reactivex.Observable
import javax.inject.Inject

class RandomPhraseViewModel @Inject internal constructor(
    private val getRandomStudy: GetRandomStudy,
    application: Application
) : MxViewModel<RandomPhraseIntent, RandomPhraseRenderAction, RandomPhraseViewState>(
    RandomPhraseViewState(RandomPhraseViewState.View.Idle),
    application
) {
    override fun dispatcher(intent: RandomPhraseIntent): Observable<RandomPhraseRenderAction> = when (intent) {
        RandomPhraseIntent.Idle -> Observable.just(RandomPhraseRenderAction.Idle)
        RandomPhraseIntent.Init -> Observable.just(RandomPhraseRenderAction.Idle)
        RandomPhraseIntent.Start -> randomStudy()
        is RandomPhraseIntent.Result -> Observable.just(RandomPhraseRenderAction.Result(intent.correct))
    }

    override fun reducer(previousState: RandomPhraseViewState, renderAction: RandomPhraseRenderAction): RandomPhraseViewState = when (renderAction) {
        RandomPhraseRenderAction.Idle -> previousState.copy(
            view = RandomPhraseViewState.View.Idle
        )
        is RandomPhraseRenderAction.PickRandomPhrases -> previousState.copy(
            view = RandomPhraseViewState.View.Next(renderAction.study[0]),
            study = renderAction.study
        )
        is RandomPhraseRenderAction.Result -> previousState.copy(
            view = studyNextPhrase(previousState),
            results = previousState.results.plus(
                Pair(previousState.study[previousState.currentPosition], renderAction.correct)),
            currentPosition = incrementCurrentPosition(previousState)
        )
    }

    override fun filterIntents(intents: Observable<RandomPhraseIntent>): Observable<RandomPhraseIntent> = Observable.merge(
        intents.ofType(RandomPhraseIntent.Init.javaClass).take(1),
        intents.filter {
            !RandomPhraseIntent.Init.javaClass.isInstance(it)
        }
    )

    private fun randomStudy(): Observable<RandomPhraseRenderAction> {
        return getRandomStudy.random().map<RandomPhraseRenderAction> {
            RandomPhraseRenderAction.PickRandomPhrases(it)
        }.toObservable()
    }

    private fun incrementCurrentPosition(state: RandomPhraseViewState): Int {
        return if (state.currentPosition == state.study.size - 1) {
            state.currentPosition
        } else {
            state.currentPosition.inc()
        }
    }

    private fun studyNextPhrase(state: RandomPhraseViewState): RandomPhraseViewState.View {
        return if (state.currentPosition == state.study.size - 1) {
            RandomPhraseViewState.View.Finished
        } else {
            RandomPhraseViewState.View.Next(state.study[state.currentPosition.inc()])
        }
    }
}