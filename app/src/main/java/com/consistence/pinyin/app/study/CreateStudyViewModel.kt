package com.consistence.pinyin.app.study

import android.app.Application
import com.memtrip.mxandroid.MxViewModel
import io.reactivex.Observable
import io.reactivex.Single
import java.lang.IllegalStateException
import javax.inject.Inject

class CreateStudyViewModel @Inject internal constructor(
    application: Application
) : MxViewModel<CreateStudyIntent, CreateStudyRenderAction, CreateStudyViewState>(
    CreateStudyViewState(CreateStudyViewState.View.Idle),
    application
) {

    override fun dispatcher(intent: CreateStudyIntent): Observable<CreateStudyRenderAction> = when (intent) {
        CreateStudyIntent.Init, CreateStudyIntent.Retry -> Observable.just(CreateStudyRenderAction.NoResults)
    }

    override fun reducer(previousState: CreateStudyViewState, renderAction: CreateStudyRenderAction): CreateStudyViewState = when (renderAction) {
        is CreateStudyRenderAction.Populate -> previousState.copy(view = CreateStudyViewState.View.Populate(renderAction.study))
        CreateStudyRenderAction.NoResults -> previousState.copy(view = CreateStudyViewState.View.NoResults)
        CreateStudyRenderAction.Error -> previousState.copy(CreateStudyViewState.View.Error)
    }

    override fun filterIntents(intents: Observable<CreateStudyIntent>): Observable<CreateStudyIntent> = Observable.merge(
        intents.ofType(CreateStudyIntent.Init.javaClass).take(1),
        intents.filter {
            !CreateStudyIntent.Init.javaClass.isInstance(it)
        }
    )
}