package com.consistence.pinyin.app.study

import android.app.Application
import com.consistence.pinyin.domain.study.GetStudy
import com.consistence.pinyin.domain.study.db.CountStudy
import com.consistence.pinyin.domain.study.db.GetStudyOrderedByDesc
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
        is StudyIntent.DeleteStudy -> TODO()
        is StudyIntent.SelectStudy -> TODO()
    }

    override fun reducer(previousState: StudyViewState, renderAction: StudyRenderAction): StudyViewState = when (renderAction) {
        is StudyRenderAction.Populate -> previousState.copy(view = StudyViewState.View.Populate(renderAction.study))
        StudyRenderAction.NoResults -> previousState.copy(view = StudyViewState.View.NoResults)
        StudyRenderAction.Error -> previousState.copy(StudyViewState.View.Error)
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