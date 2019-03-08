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
        CreateStudyIntent.Init ->
            Observable.just(CreateStudyRenderAction.EnterEnglishTranslation())
        is CreateStudyIntent.EnterEnglishTranslation ->
            Observable.just(CreateStudyRenderAction.EnterEnglishTranslation(intent.englishTranslation))
        is CreateStudyIntent.EnterChinesePhrase ->
            Observable.just(CreateStudyRenderAction.EnterChinesePhrase(intent.chinesePhrase))
        is CreateStudyIntent.ConfirmPhrase ->
            Observable.just(CreateStudyRenderAction.ConfirmPhrase)
        CreateStudyIntent.GoBack ->
            Observable.just(CreateStudyRenderAction.GoBack)
        CreateStudyIntent.LoseChangesAndExit ->
            Observable.just(CreateStudyRenderAction.LoseChangesAndExit)
    }

    override fun reducer(previousState: CreateStudyViewState, renderAction: CreateStudyRenderAction): CreateStudyViewState = when (renderAction) {
        is CreateStudyRenderAction.EnterEnglishTranslation ->
            previousState.copy(
                view = CreateStudyViewState.View.EnterEnglishTranslation(renderAction.englishTranslation),
                step = CreateStudyViewState.Step.ENGLISH_TRANSLATION
            )
        is CreateStudyRenderAction.EnterChinesePhrase ->
            previousState.copy(
                view = CreateStudyViewState.View.EnterChinesePhrase(renderAction.pinyin),
                step = CreateStudyViewState.Step.ENGLISH_TRANSLATION
            )
        is CreateStudyRenderAction.ConfirmPhrase ->
            previousState.copy(
                view = CreateStudyViewState.View.ConfirmPhrase,
                step = CreateStudyViewState.Step.CONFIRM
            )
        CreateStudyRenderAction.GoBack -> previousState.copy(
            view = when (previousState.step) {
                CreateStudyViewState.Step.ENGLISH_TRANSLATION -> {
                    if (previousState.englishTranslation.isEmpty() && previousState.pinyin.isEmpty()) {
                        CreateStudyViewState.View.LoseChanges
                    } else {
                        CreateStudyViewState.View.Exit
                    }
                }
                CreateStudyViewState.Step.CHINESE_PHRASE -> {
                    CreateStudyViewState.View.EnterEnglishTranslation(previousState.englishTranslation)
                }
                CreateStudyViewState.Step.CONFIRM -> {
                    CreateStudyViewState.View.EnterChinesePhrase(previousState.pinyin)
                }
            }
        )
        CreateStudyRenderAction.LoseChangesAndExit -> previousState.copy(
            view = CreateStudyViewState.View.Exit
        )
    }

    override fun filterIntents(intents: Observable<CreateStudyIntent>): Observable<CreateStudyIntent> = Observable.merge(
        intents.ofType(CreateStudyIntent.Init.javaClass).take(1),
        intents.filter {
            !CreateStudyIntent.Init.javaClass.isInstance(it)
        }
    )
}