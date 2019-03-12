package com.consistence.pinyin.app.study

import android.app.Application
import com.consistence.pinyin.domain.study.GetStudy
import com.consistence.pinyin.domain.study.db.CountStudy
import com.memtrip.mxandroid.MxViewModel
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class StudyViewModel @Inject internal constructor(
    private val getStudy: GetStudy,
    private val countStudy: CountStudy,
    application: Application
) : MxViewModel<StudyIntent, StudyRenderAction, StudyViewState>(
    StudyViewState(StudyViewState.View.Idle),
    application
) {

    override fun dispatcher(intent: StudyIntent): Observable<StudyRenderAction> = when (intent) {
        StudyIntent.Init, StudyIntent.Retry, StudyIntent.Refresh -> getStudy()
        StudyIntent.Idle -> Observable.just(StudyRenderAction.Idle)
        is StudyIntent.TrainPhrase -> Observable.just(StudyRenderAction.NavigateTrainPhrase(intent.study))
        is StudyIntent.SelectStudy -> Observable.just(StudyRenderAction.NavigateToStudy(intent.study))
    }

    override fun reducer(previousState: StudyViewState, renderAction: StudyRenderAction): StudyViewState = when (renderAction) {
        StudyRenderAction.Idle -> previousState.copy(view = StudyViewState.View.Idle)
        is StudyRenderAction.Populate -> previousState.copy(view = StudyViewState.View.Populate(renderAction.study))
        StudyRenderAction.NoResults -> previousState.copy(view = StudyViewState.View.NoResults)
        StudyRenderAction.Error -> previousState.copy(view = StudyViewState.View.Error)
        is StudyRenderAction.NavigateToStudy -> previousState.copy(view = StudyViewState.View.NavigateToStudy(renderAction.study))
        is StudyRenderAction.NavigateTrainPhrase -> previousState.copy(view = StudyViewState.View.NavigateToTrainPhrase(renderAction.study))
    }

    override fun filterIntents(intents: Observable<StudyIntent>): Observable<StudyIntent> = Observable.merge(
        intents.ofType(StudyIntent.Init.javaClass).take(1),
        intents.filter {
            !StudyIntent.Init.javaClass.isInstance(it)
        }
    )

    private fun getStudy(): Observable<StudyRenderAction> {
        return countStudy.count().flatMap { count ->
            if (count > 0) {
                getStudy.get().flatMap {
                    Single.just(StudyRenderAction.Populate(it))
                }
            } else {
                Single.just(StudyRenderAction.NoResults)
            }
        }.onErrorReturnItem(StudyRenderAction.Error).toObservable()
    }
}